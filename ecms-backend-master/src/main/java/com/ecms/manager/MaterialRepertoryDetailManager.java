package com.ecms.manager;

import com.ecms.bean.MaterialRepertoryDetailInfo;
import com.ecms.common.BasicMapper;
import com.ecms.common.Pager;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import com.ecms.dal.mapper.MaterialRepertoryDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 仓库物料明细manager
 * @author Richie
 * @version 2018/10/25 17:25
 */
@Component
public class MaterialRepertoryDetailManager extends BaseManager<MaterialRepertoryDetailEntity> {

    @Resource
    private MaterialRepertoryDetailMapper materialRepertoryDetailMapper;

    @Override
    protected BasicMapper<MaterialRepertoryDetailEntity> getMapper() {
        return materialRepertoryDetailMapper;
    }

    public List<MaterialRepertoryDetailInfo> getListByLike(int pageNum, int pageSize, Integer repertoryId,String materialCode,String materialName,String storageLocation,
                                                           String factoryMaterialCode,String number,String originalMaterial,String materialBomParamValueStr,String source,String drawingCode){
        return materialRepertoryDetailMapper.getListByLike(pageNum,pageSize,repertoryId,materialCode,materialName,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
    }

    public List<MaterialRepertoryDetailInfo> getPageSizeList(Integer repertoryId,String materialCode,String materialName,String storageLocation,
                                                             String factoryMaterialCode,String number,String originalMaterial,String materialBomParamValueStr,String source,String drawingCode){
        return materialRepertoryDetailMapper.getPageSizeList(repertoryId,materialCode,materialName,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
    }

    public List<MaterialRepertoryDetailInfo> getDetailList(Integer materialId,Integer companyId){
        return materialRepertoryDetailMapper.getDetailList(materialId, companyId);
    }

    public Float getUseQty(Integer detailId){
        return materialRepertoryDetailMapper.getUseQty(detailId);
    }

    public List<MaterialRepertoryDetailInfo> getListByOrder(Integer materialId,Integer repertoryId, Integer companyId){
        return materialRepertoryDetailMapper.getListByOrder(materialId,repertoryId,companyId);
    }

    public List<MaterialRepertoryDetailInfo> getMobileListByLike(int pageNum, int pageSize, Integer repertoryId,List<String> concatFieldList){
        return materialRepertoryDetailMapper.getMobileListByLike(pageNum,pageSize,repertoryId,concatFieldList);
    }

    public List<MaterialRepertoryDetailInfo> getMobilePageListByLike(Integer repertoryId,List<String> concatFieldList){
        return materialRepertoryDetailMapper.getMobilePageListByLike(repertoryId,concatFieldList);
    }
}
