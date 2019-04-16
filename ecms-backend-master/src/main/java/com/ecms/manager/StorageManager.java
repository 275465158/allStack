package com.ecms.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ecms.bean.StorageInfo;
import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.StorageEntity;
import com.ecms.dal.mapper.StorageMapper;
import com.ecms.manager.BaseManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class StorageManager extends BaseManager<StorageEntity> {

	@Resource
	private StorageMapper storageMapper;

	@Override
	protected BasicMapper<StorageEntity> getMapper() {
		return storageMapper;
	}

	public StorageEntity getLastColumn(int repertoryId) {
		return storageMapper.maxColumn(repertoryId);
	}

	//获取库存下拉json
	public String getStorageTree(int repertoryId) {
		String rtn = "";
		JSONArray jsonArr = new JSONArray();
		List<StorageEntity> numList = storageMapper.numList(repertoryId);
		for (StorageEntity num : numList) {
			JSONObject numobj = new JSONObject();
			numobj.element("value", num.getStorageNum());
			numobj.element("label", num.getStorageNum());
			JSONArray layerArr = new JSONArray();
			List<StorageEntity> layerList = storageMapper.layerList(repertoryId, Integer.valueOf(num.getStorageNum()));
			for (StorageEntity layer : layerList) {
				JSONObject layerobj = new JSONObject();
				layerobj.element("value", layer.getStorageLayer());
				layerobj.element("label", layer.getStorageLayer());
				JSONArray rowArr = new JSONArray();
				List<StorageEntity> rowList = storageMapper.rowList(repertoryId, Integer.valueOf(num.getStorageNum()),
						Integer.valueOf(layer.getStorageLayer()));
				for (StorageEntity row : rowList) {
					JSONObject rowobj = new JSONObject();
					rowobj.element("value", row.getStorageRow());
					rowobj.element("label", row.getStorageRow());
					JSONArray colArr = new JSONArray();
					List<StorageEntity> columnList = storageMapper.columnList(repertoryId,
							Integer.valueOf(num.getStorageNum()), Integer.valueOf(layer.getStorageLayer()),
							Integer.valueOf(row.getStorageRow()));
					for (StorageEntity column : columnList) {
						JSONObject columnobj = new JSONObject();
						columnobj.element("value", column.getStorageColumn());
						columnobj.element("label", column.getStorageColumn());
						columnobj.element("disabled", column.getVisible() == 0);
						colArr.add(columnobj);
					}
					rowobj.element("children", colArr);
					rowArr.add(rowobj);
				}
				layerobj.element("children", rowArr);
				layerArr.add(layerobj);
			}
			numobj.element("children", layerArr);
			jsonArr.add(numobj);
		}
		return jsonArr.toString();
	}

	public List<Map<String,Object>> getStorageNumList(Integer repertoryId){
		List<Map<String,Object>> storageNumList = storageMapper.getStorageNumList(repertoryId);
		return storageNumList;
	}

	public List<Map<String,Object>> getStorageRowList(Integer repertoryId,String storageNum){
		List<Map<String,Object>> storageRowList = storageMapper.getStorageRowList(repertoryId,storageNum);
		return storageRowList;
	}

	public List<Map<String,Object>> getStorageLayerList(Integer repertoryId,String storageNum,String storageRow){
		List<Map<String,Object>> storageLayerList = storageMapper.getStorageLayerList(repertoryId,storageNum,storageRow);
		return storageLayerList;
	}

	public List<Map<String,Object>> getStorageColumnList(Integer repertoryId,String storageNum,String storageRow,String storageLayer){
		List<Map<String,Object>> storageColumnList = storageMapper.getStorageColumnList(repertoryId,storageNum,storageRow,storageLayer);
		return storageColumnList;
	}

	public Map<String,Object> getMaxStorage(String storageNum,Integer repertoryId){
		Map<String,Object> map = storageMapper.getMaxStorage(storageNum,repertoryId);
		return map;
	}
}
