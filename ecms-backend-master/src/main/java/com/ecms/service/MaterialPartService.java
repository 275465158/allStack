package com.ecms.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.MaterialPartInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.common.MaterielPartPurchaseType;
import com.ecms.dal.entity.MaterialPartEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialPartManager;
import tk.mybatis.mapper.entity.Example;


@Service
public class MaterialPartService extends BaseService<MaterialPartEntity, MaterialPartInfo> {

	@Resource
	private MaterialPartManager mananger;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterielService materielService;

	@Override
	protected BaseManager<MaterialPartEntity> getManager() {
		return mananger;
	}

	public List<MaterialPartInfo> getPartListByMaterialId(Integer materialId) {
		Boolean fromStock = true;
		MaterialPartInfo searchPart = new MaterialPartInfo();
		searchPart.setParentId(materialId);
		List<MaterialPartInfo> searchPartList = getListBy(searchPart),rtnList = new ArrayList<MaterialPartInfo>();
		if (searchPartList!=null&&!searchPartList.isEmpty()) {
			
			for (MaterialPartInfo part : searchPartList) {
				MaterialBomInfo materialInfo = bomService.getModel(part.getMaterialBomId());
				List<MaterialPartInfo> partList = getPartListByMaterialId(materialInfo.getId());
				if (partList!=null&&!partList.isEmpty()) {
					rtnList.addAll(partList);
				}
			}
		}
		rtnList.addAll(searchPartList);
		return rtnList;
	}
	
	public List<MaterialPartInfo> getNeedProduce(Integer materialId, int needNumber){
		List<MaterialPartInfo> needProduce = new ArrayList<MaterialPartInfo>();
		MaterialPartInfo searchPart = new MaterialPartInfo();
		searchPart.setParentId(materialId);
		searchPart.setPurchaseType(MaterielPartPurchaseType.SELFMAKE.getId());
		List<MaterialPartInfo> searchPartList = getListBy(searchPart);
		if (searchPartList!=null&&!searchPartList.isEmpty()) {
			for (MaterialPartInfo part : searchPartList) {
				MaterialBomInfo materialInfo = bomService.getModel(part.getMaterialBomId());
				MaterielInfo materielInfo = materielService.getByMaterialId(part.getMaterialBomId());
				if(materielInfo==null||materielInfo.getOnhand()<part.getConsumeQty()*needNumber) {
					needProduce.add(part);
				}
			}
		}
		return needProduce;
	}
	
	public List<MaterialPartInfo> getListByMaterialId(Integer materialId){
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialBomId", materialId);
		return getByCondition();
	}

}
