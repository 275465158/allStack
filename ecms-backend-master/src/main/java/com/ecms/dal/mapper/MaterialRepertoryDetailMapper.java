package com.ecms.dal.mapper;

import com.ecms.bean.MaterialRepertoryDetailInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 仓库物料明细mapper
 * @author Richie
 * @version 2018/10/25 17:27
 */
public interface MaterialRepertoryDetailMapper extends BasicMapper<MaterialRepertoryDetailEntity> {

    @Select({"<script>SELECT factory_material_code AS factoryMaterialCode,number AS number,drawing_code AS drawingCode,material_bom_param_value_str AS materialBomParamValueStr, id AS id,material_id AS materialId,SUM(inventory_qty) AS inventoryQty," +
            "material_code AS materialCode,material_name AS materialName,SUM(unqualified_qty) AS unqualifiedQty FROM material_repertory_detail " +
            "WHERE repertory_id = #{repertoryId} " +
            "<if test = 'materialName != null'> AND material_name LIKE CONCAT('%',#{materialName},'%') </if>" +
            "<if test = 'materialCode != null'> AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
            "<if test = 'number != null'> AND number LIKE CONCAT('%',#{number},'%') </if>" +
            "<if test = 'originalMaterial != null'> AND original_material LIKE CONCAT('%',#{originalMaterial},'%') </if>" +
            "<if test = 'materialBomParamValueStr != null'> AND material_bom_param_value_str LIKE CONCAT('%', #{materialBomParamValueStr},'%') </if>" +
            "<if test = 'drawingCode != null'> AND drawing_code LIKE CONCAT('%', #{drawingCode},'%') </if>" +
            "<if test = 'factoryMaterialCode != null'> AND factory_material_code LIKE CONCAT('%', #{factoryMaterialCode},'%') </if>" +
            "<if test = 'source != null'> AND source LIKE CONCAT('%',#{source},'%') </if>" +
            "GROUP BY material_id limit #{pageNum},#{pageSize}</script> "})
    List<MaterialRepertoryDetailInfo> getListByLike(@Param("pageNum")int pageNum, @Param("pageSize")int pageSize, @Param("repertoryId")Integer repertoryId,
                                                    @Param("materialCode")String materialCode,@Param("materialName")String materialName,
                                                    @Param("factoryMaterialCode")String factoryMaterialCode,@Param("number")String number,
                                                    @Param("originalMaterial")String originalMaterial,@Param("materialBomParamValueStr")String materialBomParamValueStr,
                                                    @Param("source")String source,@Param("drawingCode")String drawingCode);

    @Select({"<script>SELECT factory_material_code AS factoryMaterialCode,number AS number,drawing_code AS drawingCode,material_bom_param_value_str AS materialBomParamValueStr,id AS id,material_id AS materialId,SUM(inventory_qty) AS inventoryQty," +
            "material_code AS materialCode,material_name AS materialName,SUM(unqualified_qty) AS unqualifiedQty FROM material_repertory_detail " +
            "WHERE repertory_id = #{repertoryId} " +
            "<if test = 'materialName != null'> AND material_name LIKE CONCAT('%',#{materialName},'%') </if>" +
            "<if test = 'materialCode != null'> AND material_code LIKE CONCAT('%',#{materialCode},'%') </if> " +
            "<if test = 'number != null'> AND number LIKE CONCAT('%',#{number},'%') </if>" +
            "<if test = 'originalMaterial != null'> AND original_material LIKE CONCAT('%',#{originalMaterial},'%') </if>" +
            "<if test = 'materialBomParamValueStr != null'> AND material_bom_param_value_str LIKE CONCAT('%', #{materialBomParamValueStr},'%') </if>" +
            "<if test = 'drawingCode != null'> AND drawing_code LIKE CONCAT('%', #{drawingCode},'%') </if>" +
            "<if test = 'factoryMaterialCode != null'> AND factory_material_code LIKE CONCAT('%', #{factoryMaterialCode},'%') </if>" +
            "<if test = 'source != null'> AND source LIKE CONCAT('%',#{source},'%') </if>" +
            "GROUP BY material_id</script>"})
    List<MaterialRepertoryDetailInfo> getPageSizeList(@Param("repertoryId")Integer repertoryId,@Param("materialCode")String materialCode,
                                                      @Param("materialName")String materialName, @Param("factoryMaterialCode")String factoryMaterialCode,
                                                      @Param("number")String number,@Param("originalMaterial")String originalMaterial,
                                                      @Param("materialBomParamValueStr")String materialBomParamValueStr,@Param("source")String source,@Param("drawingCode")String drawingCode);

    @Select({"<script>SELECT id AS id ,repertory_id AS repertoryId,SUM(inventory_qty) AS inventoryQty from material_repertory_detail WHERE material_id = #{materialId} AND company_id = #{companyId} GROUP BY repertory_id</script>"})
    List<MaterialRepertoryDetailInfo> getDetailList(@Param("materialId")Integer materialId,@Param("companyId")Integer companyId);

    @Select({"<script>SELECT IFNULL(SUM(inventory_qty),0) AS inventoryQty from material_repertory_detail WHERE detail_id = #{detailId} </script>"})
    Float getUseQty(@Param("detailId")Integer detailId);

    @Select({"<script>SELECT material_id AS materialId, id AS id, inventory_qty AS inventoryQty, supplier_id AS supplierId , storage_location AS storageLocation ,material_batch AS material_batch," +
            "material_code AS materialCode, material_name AS materialName, operating_personnel_id AS operating_personnelId,material_source AS materialSource,`number` AS `number`,repertory_id AS repertoryId from material_repertory_detail WHERE material_id = #{materialId} AND repertory_id = #{repertoryId} AND company_id = #{companyId} AND status = 1 ORDER BY id ASC</script>"})
    List<MaterialRepertoryDetailInfo> getListByOrder(@Param("materialId")Integer materialId,@Param("repertoryId")Integer repertoryId, @Param("companyId")Integer companyId);

    @Select({"<script>SELECT factory_material_code AS factoryMaterialCode,number AS number,drawing_code AS drawingCode," +
            "material_bom_param_value_str AS materialBomParamValueStr, id AS id,material_id AS materialId,SUM(inventory_qty) AS inventoryQty," +
            "material_code AS materialCode,material_name AS materialName,SUM(unqualified_qty) AS unqualifiedQty FROM material_repertory_detail " +
            "WHERE repertory_id = #{repertoryId} " +
            "<if test = 'concatFieldList != null'> " +
            " <foreach collection=\"concatFieldList\" item=\"concatField\">\n" +
            "  AND CONCAT_WS( `material_code`, `factory_material_code`, `material_name`, `original_material`, `material_bom_param_value_str`,`drawing_code` ) LIKE CONCAT('%', #{concatField},'%')\n" +
            " </foreach>" +
            "</if>" +
            "GROUP BY material_id limit #{pageNum},#{pageSize}</script> "})
    List<MaterialRepertoryDetailInfo> getMobileListByLike(@Param("pageNum")int pageNum, @Param("pageSize")int pageSize, @Param("repertoryId")Integer repertoryId,@Param("concatFieldList")List<String> concatFieldList);

    @Select({"<script>SELECT factory_material_code AS factoryMaterialCode,number AS number,drawing_code AS drawingCode," +
            "material_bom_param_value_str AS materialBomParamValueStr, id AS id,material_id AS materialId,SUM(inventory_qty) AS inventoryQty," +
            "material_code AS materialCode,material_name AS materialName,SUM(unqualified_qty) AS unqualifiedQty FROM material_repertory_detail " +
            "WHERE repertory_id = #{repertoryId} " +
            "<if test = 'concatFieldList != null'> " +
            " <foreach collection=\"concatFieldList\" item=\"concatField\">\n" +
            "  AND CONCAT_WS( `material_code`, `factory_material_code`, `material_name`, `original_material`, `material_bom_param_value_str`,`drawing_code` ) LIKE CONCAT('%', #{concatField},'%')\n" +
            " </foreach>" +
            "</if>" +
            "GROUP BY material_id</script> "})
    List<MaterialRepertoryDetailInfo> getMobilePageListByLike(@Param("repertoryId")Integer repertoryId,@Param("concatFieldList")List<String> concatFieldList);
}
