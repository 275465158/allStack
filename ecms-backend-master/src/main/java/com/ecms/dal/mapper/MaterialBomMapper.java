package com.ecms.dal.mapper;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialBomEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface MaterialBomMapper extends BasicMapper<MaterialBomEntity> {

	/**
	 * 获取子项BOM
	 */
	@Select("select bom.* from ecms.material_bom bom left join ecms.material_relationship rs on rs.material_child=bom.id where rs.material_parent=#{materialId}")
	//@Results(id="materialBom",value={@Result(column="")})
	List<MaterialBomEntity> getListMaterialPart(Integer materialId);

	@Select("SELECT IFNULL(SUM(onhand),0) AS onhandTotal,IFNULL(SUM(safe_stock),0) AS safeStockTotal,IFNULL(SUM(required_by_orders),0) AS requiredByOrdersTotal FROM materiel WHERE material_id = #{id}")
	Map<String,Object> getTotalNumList(@Param("id")Integer id);

	@Select({"<script>select b.factory_material_code AS factoryMaterialCode,b.`type` AS `type`," +
			"b.material_bom_param_value_str AS materialBomParamValueStr,b.drawing_code AS drawingCode," +
			" b.id AS id,b.source AS source,b.material_code AS materialCode,b.material_name AS materialName," +
			"b.author AS author,b.material_bom_created AS materialBomCreated,b.original_material AS originalmaterial" +
			" from material_bom b where b.material_code not in(select a.material_code from material_repertory_detail a " +
			"WHERE a.repertory_id = #{repertoryId} group by a.material_code) " +
			"<if test = 'materialCode != null'>AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
			"<if test = 'materialName != null'>AND material_name LIKE CONCAT('%',#{materialName},'%') </if> " +
			"<if test = 'factoryMaterialCode != null'>AND factory_material_code LIKE CONCAT('%',#{factoryMaterialCode},'%') </if> " +
			"<if test = 'type != null'>AND type LIKE CONCAT('%',#{type},'%') </if> " +
			"<if test = 'materialBomParamValueStr != null'>AND material_bom_param_value_str LIKE CONCAT('%',#{materialBomParamValueStr},'%') </if> " +
			"<if test = 'drawingCode != null'>AND drawing_code LIKE CONCAT('%',#{drawingCode},'%') </if> " +
			"<if test = 'source != null'>AND source LIKE CONCAT('%',#{source},'%') </if> " +
			"<if test = 'author != null'>AND author LIKE CONCAT('%',#{author},'%') </if>" +
			" AND company_Id = #{companyId} AND b.auditing_status = 0 limit #{pageNum},#{pageSize}</script>"})
	List<MaterialBomInfo> getAddMaterialBomList(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize,@Param("materialCode")String materialCode,@Param("materialName")String materialName,@Param("author")String author,@Param("repertoryId")Integer repertoryId,@Param("companyId") Integer companyId,
												@Param("factoryMaterialCode")String factoryMaterialCode,@Param("type")String type,@Param("materialBomParamValueStr")String materialBomParamValueStr,@Param("drawingCode")String drawingCode,@Param("source")String source);

	@Select({"<script>SELECT id AS id,material_code AS materialCode,material_name AS materialName," +
			"original_material AS originalMaterial " +
			"FROM material_bom WHERE auditing_status = 0 and material_bom.id NOT IN (SELECT DISTINCT material_id FROM supplier_material WHERE supplier_id=#{supplierId}) AND company_id = #{companyId} " +
			"<if test = 'materialCode != null'>AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
			"<if test = 'materialName != null'>AND material_name LIKE CONCAT('%',#{materialName},'%') </if> " +
			"<if test = 'factoryMaterialCode != null'>AND factory_material_code LIKE CONCAT('%',#{factoryMaterialCode},'%') </if> " +
			"<if test = 'type != null'>AND type LIKE CONCAT('%',#{type},'%') </if> " +
			"<if test = 'materialBomParamValueStr != null'>AND material_bom_param_value_str LIKE CONCAT('%',#{materialBomParamValueStr},'%') </if> " +
			"<if test = 'originalMaterial != null'>AND original_material LIKE CONCAT('%',#{originalMaterial},'%') </if> " +
			"<if test = 'drawingCode != null'>AND drawing_code LIKE CONCAT('%',#{drawingCode},'%') </if> " +
			"<if test = 'source != null'>AND source LIKE CONCAT('%',#{source},'%') </if> " +
			"limit #{pageNum},#{pageSize}</script>"})
	List<MaterialBomInfo> getSupplierBomList(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize,
											 @Param("materialCode")String materialCode,
											 @Param("materialName")String materialName,
											 @Param("supplierId")Integer supplierId,
											 @Param("companyId")Integer companyId,
											 @Param("factoryMaterialCode")String factoryMaterialCode,
											 @Param("type")String type,
											 @Param("materialBomParamValueStr")String materialBomParamValueStr,
											 @Param("originalMaterial")String originalMaterial,
											 @Param("drawingCode")String drawingCode,
											 @Param("source")String source);


    @Select({"<script>SELECT id AS id,material_code AS materialCode,material_name AS materialName," +
            "original_material AS originalMaterial " +
            "FROM material_bom WHERE auditing_status = 0 and material_bom.id NOT IN (SELECT DISTINCT material_id FROM supplier_material WHERE supplier_id=#{supplierId}) AND company_id = #{companyId} " +
            "<if test = 'materialCode != null'>AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
            "<if test = 'materialName != null'>AND material_name LIKE CONCAT('%',#{materialName},'%') </if> " +
			"<if test = 'factoryMaterialCode != null'>AND factory_material_code LIKE CONCAT('%',#{factoryMaterialCode},'%') </if> " +
			"<if test = 'type != null'>AND type LIKE CONCAT('%',#{type},'%') </if> " +
			"<if test = 'materialBomParamValueStr != null'>AND material_bom_param_value_str LIKE CONCAT('%',#{materialBomParamValueStr},'%') </if> " +
			"<if test = 'originalMaterial != null'>AND original_material LIKE CONCAT('%',#{originalMaterial},'%') </if> " +
			"<if test = 'drawingCode != null'>AND drawing_code LIKE CONCAT('%',#{drawingCode},'%') </if> " +
			"<if test = 'source != null'>AND source LIKE CONCAT('%',#{source},'%') </if> " +
            "</script>"})
    List<MaterialBomInfo> getSupplierBomList1(@Param("materialCode")String materialCode,
                                              @Param("materialName")String materialName,
                                              @Param("supplierId")Integer supplierId,
                                              @Param("companyId")Integer companyId,
											  @Param("factoryMaterialCode")String factoryMaterialCode,
											  @Param("type")String type,
											  @Param("materialBomParamValueStr")String materialBomParamValueStr,
											  @Param("originalMaterial")String originalMaterial,
											  @Param("drawingCode")String drawingCode,
											  @Param("source")String source);


	@Select({"<script>select b.factory_material_code AS factoryMaterialCode,b.`type` AS `type`," +
			"b.material_bom_param_value_str AS materialBomParamValueStr,b.drawing_code AS drawingCode," +
			"b.id AS id,b.source AS source,b.material_code AS materialCode,b.material_name AS materialName," +
			"b.author AS author,b.material_bom_created AS materialBomCreated,b.original_material AS originalmaterial" +
			" from material_bom b where b.material_code not in(select a.material_code from material_repertory_detail a WHERE" +
			" a.repertory_id = #{repertoryId} group by a.material_code) " +
			"<if test = 'materialCode != null'>AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
			"<if test = 'materialName != null'>AND material_name LIKE CONCAT('%',#{materialName},'%') </if> " +
			"<if test = 'factoryMaterialCode != null'>AND factory_material_code LIKE CONCAT('%',#{factoryMaterialCode},'%') </if> " +
			"<if test = 'type != null'>AND type LIKE CONCAT('%',#{type},'%') </if> " +
			"<if test = 'materialBomParamValueStr != null'>AND material_bom_param_value_str LIKE CONCAT('%',#{materialBomParamValueStr},'%') </if> " +
			"<if test = 'drawingCode != null'>AND drawing_code LIKE CONCAT('%',#{drawingCode},'%') </if> " +
			"<if test = 'source != null'>AND source LIKE CONCAT('%',#{source},'%') </if> " +
			"<if test = 'author != null'>AND author LIKE CONCAT('%',#{author},'%')</if> " +
			"AND company_Id = #{companyId} AND b.auditing_status = 0 </script>"})
	List<MaterialBomInfo> getAddMaterialBomList1(@Param("materialCode")String materialCode,@Param("materialName")String materialName,@Param("author")String author,
												 @Param("repertoryId")Integer repertoryId,@Param("companyId") Integer companyId,
												 @Param("factoryMaterialCode")String factoryMaterialCode,@Param("type")String type,
												 @Param("materialBomParamValueStr")String materialBomParamValueStr,
												 @Param("drawingCode")String drawingCode,@Param("source")String source);


	@Select({"<script>SELECT id AS id,material_code AS materialCode,factory_material_code AS factoryMaterialCode,material_unit AS materialUnit," +
			" source AS source,original_Material AS originalMaterial,material_name AS materialName," +
			" original_material AS originalMaterial " +
			" FROM material_bom WHERE " +
			" status = 1" +
			" AND company_id = #{companyId}  " +
			" AND replace(`material_name`,\" \",\"\") = #{materialName} " +
			" AND replace(`source`,\" \",\"\") = #{source} " +
			"<if test = 'originalMaterial != null'>AND replace(`original_material`,\" \",\"\") = #{originalMaterial} </if> " +
			"<if test = 'factoryMaterialCode != null'>AND `factory_material_code` = #{factoryMaterialCode} </if> " +
			"<if test = 'materialBomParamValueStr != null'>AND replace(`material_bom_param_value_str`,\" \",\"\") = #{materialBomParamValueStr} </if> " +
			"<if test = 'drawingCode != null'>AND `drawing_code` = #{drawingCode} </if> " +
			"<if test = 'number != null'>AND `number` = #{number} </if> " +
			"<if test = 'checkId != null'>AND `check_id` = #{checkId} </if> " +
			"</script>"})
	List<MaterialBomInfo> getMbeTrimListByInfo(@Param("factoryMaterialCode")String factoryMaterialCode,
												   @Param("materialBomParamValueStr")String materialBomParamValueStr,
												   @Param("originalMaterial")String originalMaterial,
												   @Param("materialName")String materialName,
												   @Param("source")String source,
												   @Param("companyId")Integer companyId,
											       @Param("drawingCode")String drawingCode,
											       @Param("checkId")Integer checkId,
											       @Param("number")String number);

	@Select("SELECT IFNULL(MAX(price),0) AS maxPrice FROM supplier_material WHERE material_id = #{materialId} AND company_id = #{companyId}")
	Float getMaxPriceToSupplier(@Param("materialId") Integer materialId,
								@Param("companyId") Integer companyId);

	@Select("SELECT IFNULL(MAX(purchase_price),0) as maxPrice FROM material_repertory_detail WHERE material_id = #{materialId} AND company_id = #{companyId} AND inventory_qty != 0")
	Float getMaxPriceToRepertory(@Param("materialId") Integer materialId,
								 @Param("companyId") Integer companyId);

	@Select("SELECT MAX(material_number) as maxNumber from material_bom where company_id = #{companyId} and `status` = 1")
	Integer getMaxMaterialNumber(@Param("companyId") Integer companyId);
}
