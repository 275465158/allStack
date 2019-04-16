package com.ecms.manager;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.mapper.MaterialBomMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class MaterialBomManager extends BaseManager<MaterialBomEntity> {

	@Resource
	private MaterialBomMapper mapper;
	@Override
	protected BasicMapper<MaterialBomEntity> getMapper() {
		return mapper;
	}

	public Map<String,Object> getTotalNumList(Integer id){
		return mapper.getTotalNumList(id);
	}

	public List<MaterialBomInfo> getAddMaterialBomList(int pageNum, int pageSize,String materialCode,String materialName,String author,Integer repertoryId,Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String drawingCode,String source){
		return mapper.getAddMaterialBomList(pageNum, pageSize,materialCode,materialName,author,repertoryId,companyId,factoryMaterialCode, type, materialBomParamValueStr, drawingCode, source);
	}

	public List<MaterialBomInfo> getAddMaterialBomList1(String materialCode,String materialName,String author,Integer repertoryId,Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String drawingCode,String source){
		return mapper.getAddMaterialBomList1(materialCode,materialName,author,repertoryId,companyId,factoryMaterialCode, type, materialBomParamValueStr, drawingCode, source);
	}

	public List<MaterialBomInfo> getSupplierBomList(int pageNum, int pageSize,String materialCode,String materialName,Integer supplierId,Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String originalMaterial,String drawingCode,String source){
		return mapper.getSupplierBomList(pageNum, pageSize,materialCode,materialName,supplierId,companyId, factoryMaterialCode, type, materialBomParamValueStr, originalMaterial, drawingCode, source);
	}

    public List<MaterialBomInfo> getSupplierBomList1(String materialCode,String materialName,Integer supplierId,Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String originalMaterial,String drawingCode,String source){
         return mapper.getSupplierBomList1(materialCode,materialName,supplierId,companyId, factoryMaterialCode, type, materialBomParamValueStr, originalMaterial, drawingCode, source);
    }

    public List<MaterialBomInfo> getMbeTrimListByInfo(MaterialBomInfo mbi,String drawingCode,Integer checkId){
		return mapper.getMbeTrimListByInfo(mbi.getFactoryMaterialCode(),mbi.getMaterialBomParamValueStr(),mbi.getOriginalMaterial(),
				mbi.getMaterialName(),mbi.getSource(),mbi.getCompanyId(),drawingCode,checkId,mbi.getNumber());
	}

	public Float getMaxPriceToSupplier(Integer materialId,Integer companyId){
		return mapper.getMaxPriceToSupplier(materialId,companyId);
	}

	public Float getMaxPriceToRepertory(Integer materialId,Integer companyId){
		return mapper.getMaxPriceToRepertory(materialId,companyId);
	}

	public Integer getMaxMaterialNumber(Integer companyId){
		return mapper.getMaxMaterialNumber(companyId);
	}
}
