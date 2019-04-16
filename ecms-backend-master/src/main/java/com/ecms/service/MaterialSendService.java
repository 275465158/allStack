package com.ecms.service;

import com.ecms.bean.MaterialSendInfo;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.MaterialSendDetailEntity;
import com.ecms.dal.entity.MaterialSendEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialBomManager;
import com.ecms.manager.MaterialSendDetailManager;
import com.ecms.manager.MaterialSendManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2018/11/29 13:46
 */
@Service
public class MaterialSendService extends BaseService<MaterialSendEntity,MaterialSendInfo> {

    @Resource
    private MaterialSendManager materialSendManager;

    @Resource
    private MaterialSendDetailManager materialSendDetailManager;

    @Resource
    private MaterialBomManager materialBomManager;

    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

    @Override
    protected BaseManager<MaterialSendEntity> getManager() {
        return materialSendManager;
    }

    @Transactional
    public void saveSendMaterial(MaterialSendInfo materialSendInfo){
    }

    @Transactional
    public void materialSend(MaterialSendInfo materialSendInfo){
        MaterialSendEntity materialSendEntity = ExchangeUtils.exchangeObject(materialSendInfo, MaterialSendEntity.class);
        List<Map<String,Object>> materialSendList = materialSendInfo.getMaterialSendList();
        if(materialSendList.size() != 0){
            List<MaterialSendEntity> mseList = materialSendManager.getListBy(materialSendEntity);
            //主表id
            Integer msId;
            if(mseList.size() == 0){
                materialSendEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                MaterialSendEntity mse = materialSendManager.insertBy(materialSendEntity);
                msId = mse.getId();
            }else {
                msId = mseList.get(0).getId();
            }
            for(Map<String,Object> msl : materialSendList){
                MaterialSendDetailEntity msde = new MaterialSendDetailEntity();

                Integer materialId = Integer.parseInt(msl.get("productId").toString());
                MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                String numberValue = null;
                numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                msde.setNumber(numberValue);
                msde.setOriginalMaterial(mbe.getOriginalMaterial());
                msde.setSource(mbe.getSource());
                msde.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                msde.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                msde.setDrawingCode(mbe.getDrawingCode());

                msde.setContractId(materialSendInfo.getContractId());
                msde.setSendId(msId);
                msde.setMaterialId(materialId);
                msde.setMaterialCode(msl.get("productCode").toString());
                msde.setMaterialName(msl.get("productName").toString());
                msde.setSendQty(Float.parseFloat(msl.get("sendMaterialQty").toString()));
                msde.setSellerOrderProductId(Integer.parseInt(msl.get("sellerOrderProductId").toString()));
                msde.setOrderCode(materialSendInfo.getOrderCode());
                msde.setOperationPersonId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                msde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                msde.setSendDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                materialSendDetailManager.insertBy(msde);
            }
        }
    }
}
