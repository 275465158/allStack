package com.ecms.service;

import com.ecms.bean.MaterialCheckDetailInfo;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.MaterialCheckDetailEntity;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2018/11/8 13:50
 */
@Service
public class MaterialCheckDetailService extends BaseService<MaterialCheckDetailEntity,MaterialCheckDetailInfo> {
    @Resource
    private MaterialCheckDetailManager materialCheckDetailManager;
    @Resource
    private MaterialBomManager materialBomManager;
    @Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;
    @Resource
    private MaterialRepertoryManager materialRepertoryManager;
    @Override
    protected BaseManager<MaterialCheckDetailEntity> getManager() {
        return materialCheckDetailManager;
    }

    @Transactional
    public void insertCheck(MaterialCheckDetailInfo materialCheckDetailInfo){
        List<Map<String,Object>> materialCheckDetailInfoList = materialCheckDetailInfo.getTableDataRow();
        if(materialCheckDetailInfoList.size() != 0){
            for(Map<String,Object> map : materialCheckDetailInfoList){
                MaterialCheckDetailEntity mcde = new MaterialCheckDetailEntity();
                mcde.setInventoryQty(Float.parseFloat(map.get("leaveQty").toString()));
                Integer materialId = Integer.parseInt(map.get("materialId").toString());
                MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                mcde.setMaterialId(materialId);
                mcde.setMaterialName(mbe.getMaterialName());
                mcde.setMaterialBatch(map.get("materialBatch").toString());
                mcde.setStorageLocation(map.get("storageLocation").toString());
                mcde.setSupplierId(Integer.parseInt(map.get("supplierId").toString()));
                mcde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mcde.setMaterialCode(map.get("materialCode").toString());
                mcde.setRepertoryId(materialCheckDetailInfo.getRepertoryId());
                mcde.setMrdId(Integer.parseInt(map.get("id").toString()));
                materialCheckDetailManager.insertBy(mcde);

                MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
                MaterialRepertoryDetailEntity mrde1 = materialRepertoryDetailManager.getEntityById(Integer.parseInt(map.get("id").toString()));
                Float detailOldInventoryQty = mrde1.getInventoryQty();
                Float addInventoryQty = detailOldInventoryQty - Float.parseFloat(map.get("inventoryQty").toString());
                mrde.setId(Integer.parseInt(map.get("id").toString()));
                mrde.setInventoryQty(Float.parseFloat(map.get("inventoryQty").toString()));
                materialRepertoryDetailManager.updateByPrimaryKeySelective(mrde);

                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();

                mre.setMaterialId(Integer.parseInt(map.get("materialId").toString()));
                mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());

                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                mre.setId(mreList.get(0).getId());
                mre.setInventoryQty(mreList.get(0).getInventoryQty()-addInventoryQty);
                materialRepertoryManager.updateByPrimaryKeySelective(mre);
            }
        }
    }
}
