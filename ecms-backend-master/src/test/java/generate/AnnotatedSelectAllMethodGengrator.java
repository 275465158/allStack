package generate;

import com.google.common.base.CaseFormat;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

import java.util.Iterator;

import static org.mybatis.generator.api.dom.OutputUtilities.javaIndent;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getSelectListPhrase;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class AnnotatedSelectAllMethodGengrator extends SelectAllMethodGengrator {

    private boolean useResultMapIfAvailable;

    AnnotatedSelectAllMethodGengrator(IntrospectedTable introspectedTable, boolean useResultMapIfAvailable, boolean isSimple) {
        super(isSimple);
        this.useResultMapIfAvailable = useResultMapIfAvailable;
        this.introspectedTable = introspectedTable;
    }

    @Override
    public void addMapperAnnotations(Interface interfaze, Method method) {
        if (useResultMapIfAvailable) {
            if (introspectedTable.getRules().generateBaseResultMap() || introspectedTable.getRules().generateResultMapWithBLOBs()) {
                addResultMapAnnotation(interfaze, method);
            } else {
                addAnnotatedResults(interfaze, method);
            }
        } else {
            addAnnotatedResults(interfaze, method);
        }

        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Select"));

        StringBuilder sb = new StringBuilder();
        method.addAnnotation("@Select({");
        javaIndent(sb, 1);
        sb.append("\"select \",");
        method.addAnnotation(sb.toString());

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        sb.setLength(0);
        javaIndent(sb, 1);
        sb.append('"');
        boolean hasColumns = false;
        while (iter.hasNext()) {
            sb.append(escapeStringForJava(getSelectListPhrase(iter.next())));
            hasColumns = true;

            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                sb.append("\",");
                method.addAnnotation(sb.toString());

                sb.setLength(0);
                javaIndent(sb, 1);
                sb.append('"');
                hasColumns = false;
            }
        }

        if (hasColumns) {
            sb.append("\",");
            method.addAnnotation(sb.toString());
        }

        sb.setLength(0);
        javaIndent(sb, 1);
        sb.append("\"from ");
        sb.append(escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        sb.append("\"");
        method.addAnnotation(sb.toString() + "})");

    }

    private void addResultMapAnnotation(Interface interfaze, Method method) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ResultMap"));

        String annotation = String.format("@ResultMap(\"%s\")", introspectedTable.getBaseRecordType().substring(introspectedTable.getBaseRecordType().lastIndexOf(".") + 1).toLowerCase());
        method.addAnnotation(annotation);
    }

    private void addAnnotatedResults(Interface interfaze, Method method) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));

        if (introspectedTable.isConstructorBased()) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Arg"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ConstructorArgs"));
            method.addAnnotation("@ConstructorArgs({");
        } else {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Result"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Results"));
            String mapId = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, introspectedTable.getTableConfiguration().getTableName());
            mapId += "Map";
            method.addAnnotation(String.format("@Results(id = \"%s\", value= {", mapId));
        }
        StringBuilder sb = new StringBuilder();

        Iterator<IntrospectedColumn> iterPk = introspectedTable.getPrimaryKeyColumns().iterator();
        Iterator<IntrospectedColumn> iterNonPk = introspectedTable.getNonPrimaryKeyColumns().iterator();
        while (iterPk.hasNext()) {
            IntrospectedColumn introspectedColumn = iterPk.next();
            sb.setLength(0);
            javaIndent(sb, 1);
            sb.append(getResultAnnotation(interfaze, introspectedColumn, true, introspectedTable.isConstructorBased()));

            if (iterPk.hasNext() || iterNonPk.hasNext()) {
                sb.append(',');
            }
            method.addAnnotation(sb.toString());
        }

        while (iterNonPk.hasNext()) {
            IntrospectedColumn introspectedColumn = iterNonPk.next();
            if (introspectedColumn.getJavaProperty().equals(introspectedColumn.getActualColumnName())) {
                continue;
            }
            sb.setLength(0);
            javaIndent(sb, 1);
            sb.append(getResultAnnotation(interfaze, introspectedColumn, false, introspectedTable.isConstructorBased()));

            if (iterNonPk.hasNext()) {
                sb.append(',');
            }
            method.addAnnotation(sb.toString());
        }
        method.addAnnotation("})");
    }

    @Override
    protected String getResultAnnotation(Interface interfaze, IntrospectedColumn introspectedColumn,
                                         boolean idColumn, boolean constructorBased) {
        StringBuilder sb = new StringBuilder();
        if (constructorBased) {
            interfaze.addImportedType(introspectedColumn.getFullyQualifiedJavaType());
            sb.append("@Arg(column=\""); //$NON-NLS-1$
            sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", javaType="); //$NON-NLS-1$
            sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
            sb.append(".class"); //$NON-NLS-1$
        } else {
            sb.append("@Result(column=\""); //$NON-NLS-1$
            sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", property=\""); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append('\"');
        }

        if (stringHasValue(introspectedColumn.getTypeHandler())) {
            FullyQualifiedJavaType fqjt =
                    new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
            interfaze.addImportedType(fqjt);
            sb.append(", typeHandler="); //$NON-NLS-1$
            sb.append(fqjt.getShortName());
            sb.append(".class"); //$NON-NLS-1$
        }

//        sb.append(", jdbcType=JdbcType."); //$NON-NLS-1$
//        sb.append(introspectedColumn.getJdbcTypeName());
        if (idColumn) {
            sb.append(", id=true"); //$NON-NLS-1$
        }
        sb.append(')');

        return sb.toString();
    }
}
