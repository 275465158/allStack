package com.ecms.dal.mapper;

import org.apache.ibatis.annotations.Results;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.StorageEntity;

public interface StorageMapper extends BasicMapper<StorageEntity> {
	@Select("select max(storage_column) as maxcolumn, max(storage_num) as maxnum, max(storage_layer) as maxlayer, max(storage_row) as maxrow from storage where repertory_id=#{repertoryId}")
	@Results(id="maxColumn", value= {@Result(column="maxcolumn",property="storageColumn"),
			@Result(column="maxnum", property="storageNum"),
			@Result(column="maxlayer", property="storageLayer"),
			@Result(column="maxrow", property="storageRow")
	})
	StorageEntity maxColumn(int repertoryId);
	
	@Select("select distinct(storage_num) as storagenum from storage where repertory_id=#{repertoryId} ORDER BY storage_num")
	@Results(id="listNum",value= {@Result(column="storagenum", property="storageNum")})
	List<StorageEntity> numList(int repertoryId);
	
	@Select("select distinct(storage_layer) as storagelayer from storage where repertory_id=#{repertoryId} and storage_num=#{storageNum}")
	@Results(id="listLayer",value= {@Result(column="storagelayer", property="storageLayer")})
	List<StorageEntity> layerList(@Param("repertoryId")int repertoryId,@Param("storageNum")int storageNum);
	
	@Select("select distinct(storage_row) as storagerow from storage where storage_layer=#{storageLayer} and storage_num=#{storageNum} and repertory_id=#{repertoryId}")
	@Results(id="listRow",value= {@Result(column="storagerow", property="storageRow")})
	List<StorageEntity> rowList(@Param("repertoryId")int repertoryId, @Param("storageNum")int storageNum, @Param("storageLayer")int storageLayer);
	
	@Select("select distinct(storage_column) as storagecolumn, visible from storage where storage_row=#{storageRow} and  storage_layer=#{storageLayer} and storage_num=#{storageNum} and repertory_id=#{repertoryId}")
	@Results(id="listColumn",value= {@Result(column="storagecolumn", property="storageColumn"),@Result(column="visible", property="visible")})
	List<StorageEntity> columnList(@Param("repertoryId")int repertoryId, @Param("storageNum")int storageNum, @Param("storageLayer")int storageLayer, @Param("storageRow")int storageRow);

	@Select("select storage_num as storageNum from storage where repertory_id=#{repertoryId} group by storage_num")
	List<Map<String,Object>> getStorageNumList(@Param("repertoryId") int repertoryId);

	@Select("select storage_row as storageRow from storage where repertory_id=#{repertoryId} and storage_num = #{storageNum} group by storage_row")
	List<Map<String,Object>> getStorageRowList(@Param("repertoryId") int repertoryId,@Param("storageNum") String storageNum);

	@Select("select storage_layer as storageLayer from storage where repertory_id=#{repertoryId} and storage_num = #{storageNum} and storage_row = #{storageRow} group by storage_layer")
	List<Map<String,Object>> getStorageLayerList(@Param("repertoryId")int repertoryId,@Param("storageNum")String storageNum,@Param("storageRow")String storageRow);

	@Select("select storage_column as storageColumn from storage where repertory_id=#{repertoryId} and storage_num = #{storageNum} and storage_row = #{storageRow} and storage_layer = #{storageLayer} group by storage_column")
	List<Map<String,Object>> getStorageColumnList(@Param("repertoryId")int repertoryId,@Param("storageNum")String storageNum,@Param("storageRow")String storageRow,@Param("storageLayer")String storageLayer);

	@Select("SELECT MAX(id) as id,max(storage_num) as storageNum,max(storage_layer) as storageLayer,max(storage_row) as storageRow,max(storage_column) as storageColumn FROM storage WHERE storage_num = #{storageNum} AND repertory_id = #{repertoryId}")
	Map<String,Object> getMaxStorage(@Param("storageNum")String storageNum,@Param("repertoryId")int repertoryId);
}
