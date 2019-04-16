package com.ecms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.StorageInfo;
import com.ecms.dal.entity.StorageEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.StorageManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class StorageService extends BaseService<StorageEntity, StorageInfo> {

	@Resource
	private StorageManager storageManager;

	@Override
	protected BaseManager<StorageEntity> getManager() {
		return storageManager;
	}

	public StorageInfo getLastColumn(int repertoryId) {
		StorageEntity storage = new StorageEntity();
		if (storageManager.getLastColumn(repertoryId) != null) {
			storage = storageManager.getLastColumn(repertoryId);
		}
		return ExchangeUtils.exchangeObject(storage, StorageInfo.class);
	}

	public String getJson(int repId) {
		return storageManager.getStorageTree(repId);
	}

	//更新记录
	public void updateVisible(String storageCode,Integer repertoryId) {
		String[] storageArr = storageCode.split("-");
		if (storageArr.length > 0) {
			Example.Criteria criteriaStorage = getDefaultCriteria();
			criteriaStorage.andEqualTo("storageNum", storageArr[0]);
			criteriaStorage.andEqualTo("storageLayer", storageArr[1]);
			criteriaStorage.andEqualTo("storageRow", storageArr[2]);
			criteriaStorage.andEqualTo("storageColumn", storageArr[3]);
			criteriaStorage.andEqualTo("repertoryId", repertoryId);
			List<StorageInfo> storageList = getByCondition();
			for (int i = 0; i < storageList.size(); i++) {
				StorageInfo info = storageList.get(i);
				info.setVisable(0);
				storageManager.updateByPrimaryKeySelective(ExchangeUtils.exchangeObject(info, StorageEntity.class));
			}
		}
	}


	/**
	 * 
	public List<MaterialParameterInfo> getListByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		return getByCondition();
	}
	 * @param storageNum
	 * @return
	 */
	public List<StorageInfo> getListByStorageRackId(String  storageNum) { 
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("storageNum", storageNum);
		return getByCondition();
	}

	public List<Map<String,Object>> getStorageNumList(Integer repertoryId){
		List<Map<String,Object>> storageNumList = storageManager.getStorageNumList(repertoryId);
		return storageNumList;
	}

	public List<Map<String,Object>> getStorageRowList(Integer repertoryId,String storageNum){
		List<Map<String,Object>> storageRowList = storageManager.getStorageRowList(repertoryId,storageNum);
		return storageRowList;
	}

	public List<Map<String,Object>> getStorageLayerList(Integer repertoryId,String storageNum,String storageRow){
		List<Map<String,Object>> storageLayerList = storageManager.getStorageLayerList(repertoryId,storageNum,storageRow);
		return storageLayerList;
	}

	public List<Map<String,Object>> getStorageColumnList(Integer repertoryId,String storageNum,String storageRow,String storageLayer){
		List<Map<String,Object>> storageColumnList = storageManager.getStorageColumnList(repertoryId,storageNum,storageRow,storageLayer);
		return storageColumnList;
	}

	public Map<String,Object> getMaxStorage(String storageNum,Integer repertoryId){
		Map<String,Object> map = storageManager.getMaxStorage(storageNum,repertoryId);
		return map;
	}
}
