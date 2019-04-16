package com.ecms.service;

import com.ecms.bean.ProductAcceptanceDetailInfo;
import com.ecms.bean.ProductAcceptanceInfo;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductAcceptanceDetailManager;
import com.ecms.manager.ProductAcceptanceManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductAcceptanceService extends BaseService<ProductAcceptanceEntity, ProductAcceptanceInfo> {
    @Resource
    private ProductAcceptanceManager productAcceptanceManager;
    @Resource
    private ProductAcceptanceDetailManager productAcceptanceDetailManager;

    @Override
    protected BaseManager<ProductAcceptanceEntity> getManager() {
        return productAcceptanceManager;
    }
    public String uploadFileSimple(MultipartFile file) throws IOException {

        return "文件测试";
    }
    //插入、更新
    @Transactional
    public void save(ProductAcceptanceInfo info) {
        List<Map<String, Object>> detailInfoList = info.getDetailInfoList();
        List<ProductAcceptanceDetailInfo> detailInfoList2 = info.getProductAcceptanceDetailInfoList();
        if (CollectionUtils.isEmpty(detailInfoList)) {
            throw new CommonBusinessException("验收明细不能为空");
        }
        // 先更新主表
        //如果当前制作人为空，获取当前用户为制作人
        if (StringUtils.isBlank(info.getOwner())) {
            info.setOwner(LoginFilter.getCurrentUser().getUserName());
        }
        ProductAcceptanceEntity acceptanceEntity = getEntityAfterSaveOrUpdate(info);
       
        // 再删除明细表(不管有没有，先删除再说^_^)
        int acceptanceId = acceptanceEntity.getId();
        productAcceptanceDetailManager.deleteByAcceptanceId(acceptanceId);
        // 再逐一添加明细
        for (Map<String, Object> aDetailInfoList : detailInfoList) {
            ProductAcceptanceDetailInfo detailInfo = new ProductAcceptanceDetailInfo();
            detailInfo.setAcceptanceId(acceptanceId);
            detailInfo.setCheckName(aDetailInfoList.get("checkName").toString());
            detailInfo.setCheckMethod(aDetailInfoList.get("checkMethod").toString());
            detailInfo.setEquipmentName(aDetailInfoList.get("equipmentName").toString());
            detailInfo.setEquipmentSpecification(aDetailInfoList.get("equipmentSpecification").toString());
            detailInfo.setMaxValue(aDetailInfoList.get("maxValue").toString());
            detailInfo.setMinValue(aDetailInfoList.get("minValue").toString());
            detailInfo.setUnit(aDetailInfoList.get("unit").toString());
            //        	((ProductAcceptanceDetailInfo) detailInfo).setAcceptanceId(new Integer(acceptanceId));
            ProductAcceptanceDetailEntity detailEntity = ExchangeUtils.exchangeObject(detailInfo, ProductAcceptanceDetailEntity.class);
            productAcceptanceDetailManager.insertBy(detailEntity);
        }

//        for (ProductAcceptanceDetailInfo detailinfo:detailInfoList2){
////            ProductAcceptanceDetailEntity detailEntity = new ProductAcceptanceDetailEntity();
////            if(!StringUtils.isEmpty(detailinfo.getCheckName())){
////                detailEntity.setCheckName(detailinfo.getCheckName());
////            }
////            if(!StringUtils.isEmpty(detailinfo.getCheckMethod())){
////                detailEntity.setCheckMethod(detailinfo.getCheckMethod());
////            }
////            if(!StringUtils.isEmpty(detailinfo.getEquipmentName())){
////                detailEntity.setEquipmentName(detailinfo.getEquipmentName());
////            }
////            if(!StringUtils.isEmpty(detailinfo.getCheckName())){
////                detailEntity.setCheckName(detailinfo.getCheckName());
////            }
//            ProductAcceptanceDetailEntity detailEntity = ExchangeUtils.exchangeObject(detailinfo, ProductAcceptanceDetailEntity.class);
//            productAcceptanceDetailManager.insertBy(detailEntity);
//        }
    }
    
//   public void updata(ProductAcceptanceInfo info) {
//        List<Map<String, Object>> detailInfoList = info.getDetailInfoList();
//        if (CollectionUtils.isEmpty(detailInfoList)) {
//            throw new CommonBusinessException("验收明细不能为空");
//        }
//        // 先更新主表
//        //如果当前制作人为空，获取当前用户为制作人
//        if (StringUtils.isBlank(info.getOwner())) {
//            info.setOwner(LoginFilter.getCurrentUser().getUserName());
//        }
//        ProductAcceptanceEntity acceptanceEntity = getEntityAfterSaveOrUpdate(info);
//        int acceptanceId = acceptanceEntity.getId();
//        productAcceptanceDetailManager.deleteByAcceptanceId(acceptanceId);
//       
//        // 再逐一添加明细
//        
//        int id=0;
//        for (int i=0;i<detailInfoList.size();i++) {
//        	ProductAcceptanceDetailInfo detailInfo=new ProductAcceptanceDetailInfo();
//        	
//        	if (i<1) {
//        		detailInfo.setId(id);
//				 id=Integer.parseInt(detailInfoList.get(i).get("id").toString());
//			}
//        	
//        	detailInfoList.get(i).put("acceptanceId", acceptanceId);
//        	detailInfo.setAcceptanceId(Integer.parseInt(detailInfoList.get(i).get("acceptanceId").toString()));
//        	detailInfo.setCheckName(detailInfoList.get(i).get("checkName").toString());
//        	detailInfo.setEquipmentName(detailInfoList.get(i).get("equipmentName").toString());
//        	detailInfo.setEquipmentSpecification(detailInfoList.get(i).get("equipmentSpecification").toString());
//        	detailInfo.setMaxValue(detailInfoList.get(i).get("maxValue").toString());
//        	detailInfo.setMinValue(detailInfoList.get(i).get("minValue").toString());
//        	detailInfo.setUnit(detailInfoList.get(i).get("unit").toString());
//        	ProductAcceptanceDetailEntity detailEntity = ExchangeUtils.exchangeObject(detailInfo, ProductAcceptanceDetailEntity.class);
//            productAcceptanceDetailManager.insertBy(detailEntity);
//        }
//        
//    }
}