package com.ecms.service;

import com.ecms.bean.MaterialSendDetailInfo;
import com.ecms.bean.MaterialSendInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2018/11/29 13:46
 */
@Service
public class MaterialSendDetailService extends BaseService<MaterialSendDetailEntity,MaterialSendDetailInfo> {

    @Resource
    private MaterialSendDetailManager materialSendDetailManager;

    @Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private SellerOrderManager sellerOrderManager;

    @Override
    protected BaseManager<MaterialSendDetailEntity> getManager() {
        return materialSendDetailManager;
    }

    public Float getAlreadySendQty(Integer companyId,String contractId,String orderCode,Integer materialId,Integer sellerOrderProductId){
        return materialSendDetailManager.getAlreadySendQty( companyId, contractId, orderCode, materialId,sellerOrderProductId);
    }

    @Transactional
    public void materialSend(MaterialSendDetailInfo materialSendDetailInfo){
        List<Map<String,Object>> materialSendList = materialSendDetailInfo.getMaterialSendList();
        if(materialSendList.size() != 0){
            for(Map<String,Object> msl : materialSendList){
                MaterialSendDetailEntity msde = materialSendDetailManager.getEntityById(materialSendDetailInfo.getId());
                Float sendMaterialQty = Float.parseFloat(msl.get("sendMaterialQty").toString());
                msde.setAlreadySendQty(msde.getAlreadySendQty()+sendMaterialQty);
                materialSendDetailManager.updateByPrimaryKeySelective(msde);
                Integer materialDetailId = Integer.parseInt(msl.get("materialDetailId").toString());
                MaterialRepertoryDetailEntity mrde = materialRepertoryDetailManager.getEntityById(materialDetailId);
                mrde.setInventoryQty(-sendMaterialQty);
                mrde.setDetailid(mrde.getId());
                mrde.setOperatStatus(DISPATCH_CARGO);
                mrde.setId(null);
                materialRepertoryDetailManager.insertBy(mrde);

                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(Integer.parseInt(msl.get("materialId").toString()));
                mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mre.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if(mreList.size() != 0){
                    mre.setId(mreList.get(0).getId());
                    mre.setInventoryQty(mreList.get(0).getInventoryQty()-sendMaterialQty);
                    materialRepertoryManager.updateByPrimaryKeySelective(mre);
                }
            }
            SellerOrderEntity soe = new SellerOrderEntity();
            soe.setOrderCode(materialSendDetailInfo.getOrderCode());
            List<SellerOrderEntity> soeList = sellerOrderManager.getListBy(soe);
            if(soeList.size() != 0){
                soe.setId(soeList.get(0).getId());
                soe.setProgress(5);
                sellerOrderManager.updateByPrimaryKeySelective(soe);
            }
        }
    }
}
