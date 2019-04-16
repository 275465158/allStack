package generate;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class MapperPlugin extends PluginAdapter {

    private String daoTargetPackage;
    private String targetDir;
    private String managerPackage;
    private String beanPackage = "com.ecms.bean";
    private String servicePackage = "com.ecms.service";
    private String controllerPackage = "com.ecms.web.controller";

    @Override
    public boolean validate(List<String> warnings) {

        targetDir = properties.getProperty("targetProject");
        boolean valid1 = stringHasValue(targetDir);

        daoTargetPackage = properties.getProperty("targetPackage");
        boolean valid2 = stringHasValue(daoTargetPackage);

        managerPackage = properties.getProperty("managerPackage");

        return valid1 && valid2;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        //获取所有生成的文件
        List<GeneratedJavaFile> generatedJavaFiles = introspectedTable.getGeneratedJavaFiles();
        GeneratedJavaFile bean = null;
        for (GeneratedJavaFile javaFile : generatedJavaFiles) {
            //获取编辑项(存放了生成文件时所需要的信息)
            CompilationUnit unit = javaFile.getCompilationUnit();
            FullyQualifiedJavaType baseModelJavaType = unit.getType();
            String shortName = baseModelJavaType.getShortName();
            //判断生成的文件是否是Mapper接口
            if (shortName.endsWith("Mapper")) {
                if (stringHasValue(daoTargetPackage)) {
                    if (unit instanceof Interface) {
                        addSelectAllMethod(unit, introspectedTable);
                    }
                }
            } else if (shortName.endsWith("Entity")) {
//                bean = generatedBeanFile(unit, introspectedTable);
                addFieldAnnotation(unit, introspectedTable);
            }
        }
        if (bean != null) {
            generatedJavaFiles.add(bean);
        }
//        generatedJavaFiles.add(generatedManagerFile(introspectedTable));
//        generatedJavaFiles.add(generatedServiceFile(introspectedTable));
//        generatedJavaFiles.add(generatedControllerFile(introspectedTable));
        return generatedJavaFiles;
    }

    private void addSelectAllMethod(CompilationUnit unit, IntrospectedTable introspectedTable) {
        /*为文件添加扩展的方法*/
        //添加import项
        unit.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        unit.addImportedType(new FullyQualifiedJavaType("com.ecms.common.BasicMapper"));
        unit.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        //新建一个方法
        Method method = new Method();
        //设置访问权限
        method.setVisibility(JavaVisibility.PUBLIC);
        //设置方法名称
        method.setName("selectAll");
        //设置返回类型

        FullyQualifiedJavaType returnFullyQualifiedJavaType = new FullyQualifiedJavaType(String.format("List<%s>", introspectedTable.getTableConfiguration().getDomainObjectName()));
        method.setReturnType(returnFullyQualifiedJavaType);
        //设置注解
        AnnotatedSelectAllMethodGengrator annotatedSelectAllMethodGengrator = new AnnotatedSelectAllMethodGengrator(introspectedTable, false, false);
        annotatedSelectAllMethodGengrator.addMapperAnnotations(((Interface) unit), method);
        //把方法添加到编辑项
        ((Interface) unit).addMethod(method);
        Iterator<FullyQualifiedJavaType> iterator = ((Interface) unit).getSuperInterfaceTypes().iterator();
        while (iterator.hasNext()) {
            FullyQualifiedJavaType type = iterator.next();
//            unit.addImportedType(type);
            type.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getTableConfiguration().getDomainObjectName()));
        }
    }

    private void addFieldAnnotation(CompilationUnit unit, IntrospectedTable introspectedTable) {
        Map<String, Field> map = new HashMap<>();
        if (unit instanceof TopLevelClass) {
            ((TopLevelClass) unit).addAnnotation("@Table(name = \"" + introspectedTable.getTableConfiguration().getTableName() + "\")");
            unit.addImportedType(new FullyQualifiedJavaType("javax.persistence.Table"));
            List<Field> fieldList = ((TopLevelClass) unit).getFields();
            for (Field field : fieldList) {
                map.put(field.getName(), field);
            }
        }
        boolean hasAnnotation = false;
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (!introspectedColumn.getJavaProperty().equals(introspectedColumn.getActualColumnName())) {
                Field field = map.get(introspectedColumn.getJavaProperty());
                if (field != null) {
                    field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
                    hasAnnotation = true;
                }
            }
        }
        if (hasAnnotation) {
            unit.addImportedType(new FullyQualifiedJavaType("javax.persistence.Column"));
        }
    }

    private GeneratedJavaFile generatedBeanFile(CompilationUnit unit, IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String beanName = entityName.replace("Entity", "Info");
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(beanPackage + "." + beanName);
        TopLevelClass bean = new TopLevelClass(type);
        bean.setVisibility(JavaVisibility.PUBLIC);
        bean.setSuperClass(new FullyQualifiedJavaType(beanPackage + ".BaseCompanyAndStatusInfo"));
        List<Field> fieldList = ((TopLevelClass) unit).getFields();
        for (Field field : fieldList) {
            Field newField = new Field();
            newField.setType(field.getType());
            newField.setName(field.getName());
            newField.setVisibility(field.getVisibility());
            bean.addField(newField);
        }
        List<Method> methodList = ((TopLevelClass) unit).getMethods();
        for (Method method : methodList) {
            Method newMethod = new Method();
            newMethod.setReturnType(method.getReturnType());
            newMethod.setName(method.getName());
            newMethod.setVisibility(method.getVisibility());
            List<Parameter> parameters = method.getParameters();
            if (!CollectionUtils.isEmpty(parameters)) {
                for (Parameter parameter : parameters) {
                    newMethod.addParameter(parameter);
                }
            }
            for (String s : method.getBodyLines()) {
                newMethod.addBodyLine(s);
            }
            bean.addMethod(newMethod);
        }
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(bean, targetDir, context.getJavaFormatter());
        return generatedJavaFile;
    }

    /**
     * 生成manager类
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generatedManagerFile(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String managerName = entityName.replace("Entity", "Manager");
        TopLevelClass managerClass = new TopLevelClass(managerPackage + "." + managerName);
        managerClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType entityClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        managerClass.addImportedType(entityClass);
        FullyQualifiedJavaType mapperInterface = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        managerClass.addImportedType(mapperInterface);
        managerClass.addAnnotation("@Component");
        managerClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        managerClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        managerClass.addImportedType(new FullyQualifiedJavaType("com.ecms.common.BasicMapper"));

        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.ecms.manager.BaseManager");
        superClass.addTypeArgument(entityClass);
        managerClass.setSuperClass(superClass);

        // 增加mapper变量
        String mapperFieldName = StringUtils.uncapitalize(mapperInterface.getShortName());
        Field mapperField = new Field(mapperFieldName, mapperInterface);
        mapperField.addAnnotation("@Resource");
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        managerClass.addField(mapperField);

        // 增加getMapper方法
        Method method = new Method("getMapper");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.addBodyLine("return " + mapperFieldName + ";");
        FullyQualifiedJavaType basicMapper = new FullyQualifiedJavaType("com.ecms.common.BasicMapper");
        basicMapper.addTypeArgument(entityClass);
        method.setReturnType(basicMapper);
        method.addAnnotation("@Override");
        managerClass.addMethod(method);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(managerClass, targetDir, context.getJavaFormatter());
        return generatedJavaFile;
    }

    private GeneratedJavaFile generatedServiceFile(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String serviceName = entityName.replace("Entity", "Service");
        TopLevelClass serviceClass = new TopLevelClass(servicePackage + "." + serviceName);
        serviceClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType entityClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType infoClass = new FullyQualifiedJavaType(beanPackage + "." + entityName.replace("Entity", "Info"));
        serviceClass.addImportedType(entityClass);
        serviceClass.addImportedType(infoClass);
        FullyQualifiedJavaType manager = new FullyQualifiedJavaType(managerPackage + "." + entityName.replace("Entity", "Manager"));
        serviceClass.addImportedType(manager);
        serviceClass.addAnnotation("@Service");
        serviceClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        serviceClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        serviceClass.addImportedType(new FullyQualifiedJavaType("com.ecms.manager.BaseManager"));

        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.ecms.service.BaseService");
        superClass.addTypeArgument(entityClass);
        superClass.addTypeArgument(infoClass);
        serviceClass.setSuperClass(superClass);

        // 增加manager变量
        String managerFieldName = StringUtils.uncapitalize(manager.getShortName());
        Field managerField = new Field(managerFieldName, manager);
        managerField.addAnnotation("@Resource");
        managerField.setVisibility(JavaVisibility.PRIVATE);
        serviceClass.addField(managerField);

        // 增加getMapper方法
        Method method = new Method("getManager");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.addBodyLine("return " + managerFieldName + ";");
        FullyQualifiedJavaType basicManager = new FullyQualifiedJavaType("com.ecms.manager.BaseManager");
        basicManager.addTypeArgument(entityClass);
        method.setReturnType(basicManager);
        method.addAnnotation("@Override");
        serviceClass.addMethod(method);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(serviceClass, targetDir, context.getJavaFormatter());
        return generatedJavaFile;
    }

    private GeneratedJavaFile generatedControllerFile(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String controllerName = entityName.replace("Entity", "Controller");
        TopLevelClass controllerClass = new TopLevelClass(controllerPackage + "." + controllerName);
        controllerClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType entityClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType infoClass = new FullyQualifiedJavaType(beanPackage + "." + entityName.replace("Entity", "Info"));
        controllerClass.addImportedType(entityClass);
        controllerClass.addImportedType(infoClass);
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(servicePackage + "." + entityName.replace("Entity", "Service"));
        controllerClass.addImportedType(service);
        controllerClass.addAnnotation("@RestController");
        controllerClass.addAnnotation("@RequestMapping(\"" + StringUtils.uncapitalize(entityName.replace("Entity", "")) + "\")");
        controllerClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        controllerClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        controllerClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        controllerClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));

        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.ecms.web.controller.BaseController");
        superClass.addTypeArgument(entityClass);
        superClass.addTypeArgument(infoClass);
        controllerClass.setSuperClass(superClass);

        // 增加service变量
        String serviceFieldName = StringUtils.uncapitalize(service.getShortName());
        Field serviceField = new Field(serviceFieldName, service);
        serviceField.addAnnotation("@Resource");
        serviceField.setVisibility(JavaVisibility.PRIVATE);
        controllerClass.addField(serviceField);

        // 增加getBaseService方法
        Method method = new Method("getBaseService");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.addBodyLine("return " + serviceFieldName + ";");
        FullyQualifiedJavaType basicService = new FullyQualifiedJavaType("com.ecms.service.BaseService");
        basicService.addTypeArgument(entityClass);
        basicService.addTypeArgument(infoClass);
        method.setReturnType(basicService);
        method.addAnnotation("@Override");
        controllerClass.addMethod(method);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(controllerClass, targetDir, context.getJavaFormatter());
        return generatedJavaFile;
    }
}
