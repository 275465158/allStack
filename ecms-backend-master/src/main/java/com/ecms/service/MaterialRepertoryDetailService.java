package com.ecms.service;

import com.ecms.bean.MaterialBomParamNameInfo;
import com.ecms.bean.MaterialRelationInfo;
import com.ecms.bean.MaterialRepertoryDetailInfo;
import com.ecms.dal.entity.*;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 仓库物料明细service
 * @author Richie
 * @version 2018/10/25 17:21
 */
@Service
public class MaterialRepertoryDetailService extends BaseService<MaterialRepertoryDetailEntity, MaterialRepertoryDetailInfo> {

    @Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private MaterialRepertoryOutboundManager materialRepertoryOutboundManager;

    @Resource
    private MaterialRepertoryOutboundDetailManager materialRepertoryOutboundDetailManager;

    @Resource
    private PurchaseOrdersManager purchaseOrdersManager;

    @Resource
    private MaterialRepertoryInboundlogManager materialRepertoryInboundlogManager;

    @Resource
    private MaterialRepertoryOutboundlogManager materialRepertoryOutboundlogManager;

    @Resource
    private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;

    @Resource
    private MaterialBomParamNameManager materialBomParamNameManager;

    @Resource
    private MaterialRelationService materialRelationService;

    @Resource
    private MaterialBomManager materialBomManager;

    @Resource
    private ProductTypeManager productTypeManager;

    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;

    @Override
    protected BaseManager<MaterialRepertoryDetailEntity> getManager() {
        return materialRepertoryDetailManager;
    }

    public List<MaterialRepertoryDetailInfo> getListByLike(int pageNum, int pageSize, Integer repertoryId,String materialCode,String materialName,
                                                           String storageLocation,String factoryMaterialCode,String number,String originalMaterial,String materialBomParamValueStr,String source,String drawingCode){
        return materialRepertoryDetailManager.getListByLike(pageNum,pageSize,repertoryId,materialCode,materialName,storageLocation,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
    }

    public List<MaterialRepertoryDetailInfo> getPageSizeList(Integer repertoryId,String materialCode,String materialName,
                                                             String storageLocation,String factoryMaterialCode,String number,String originalMaterial,String materialBomParamValueStr,String source,String drawingCode){
        return materialRepertoryDetailManager.getPageSizeList(repertoryId,materialCode,materialName,storageLocation,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
    }

    @Transactional
    public void saveOutbound(MaterialRepertoryDetailInfo info){
        List<Map<String,Object>> tableDataRow = info.getTableDataRow();
        if(tableDataRow != null){
            Float outboundQty = 0.00f;
            MaterialRepertoryOutboundEntity mroe = new MaterialRepertoryOutboundEntity();
            MaterialBomEntity mbe = materialBomManager.getEntityById(info.getMaterialId());
            String numberValue = null;
            numberValue = getNumberValue(mbe.getNumber(), numberValue);
            mroe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            mroe.setMaterialCode(info.getMaterialCode());
            mroe.setMaterialName(info.getMaterialName());
            mroe.setMaterialId(info.getMaterialId());
            mroe.setRepertoryId(info.getRepertoryId());
            mroe.setInventoryQty(info.getInventoryQty());
            mroe.setOutboundDate(info.getOutboundDate());
            mroe.setOutboundPersonId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
            mroe.setTakeMaterialId(info.getTakeMaterialId());
            mroe.setPickingUseage(info.getPickingUseage());
            mroe.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
            mroe.setNumber(numberValue);
            mroe.setSource(mbe.getSource());
            mroe.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
            mroe.setOriginalMaterial(mbe.getOriginalMaterial());
            mroe.setDrawingCode(mbe.getDrawingCode());

            MaterialRepertoryOutboundEntity mroEntity = materialRepertoryOutboundManager.insertBy(mroe);

            Float leaveQty = 0.00f;
//            Float leaveQtyTotal = 0.00f;
            for(Map<String,Object> tdrMap: tableDataRow){
                MaterialRepertoryOutboundDetailEntity mrode = new MaterialRepertoryOutboundDetailEntity();
                MaterialBomEntity mbe1 = materialBomManager.getEntityById(Integer.parseInt(tdrMap.get("materialId").toString()));
                String numberValue1 = null;
                numberValue1 = getNumberValue(mbe1.getNumber(), numberValue1);
                mrode.setFactoryMaterialCode(mbe1.getFactoryMaterialCode());
                mrode.setNumber(numberValue1);
                mrode.setSource(mbe1.getSource());
                mrode.setMaterialBomParamValueStr(mbe1.getMaterialBomParamValueStr());
                mrode.setOriginalMaterial(mbe1.getOriginalMaterial());
                mrode.setDrawingCode(mbe1.getDrawingCode());

                outboundQty = Float.parseFloat(tdrMap.get("inventoryQty").toString());
                mrode.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrode.setOutboundId(mroEntity.getId());
                mrode.setMaterialName(info.getMaterialName());
                mrode.setMaterialCode(info.getMaterialCode());
                mrode.setStorageLocation(tdrMap.get("storageLocation").toString());
                mrode.setMaterialBatch(tdrMap.get("materialBatch").toString());
                mrode.setOutboundQty(outboundQty);
                mrode.setMaterialId(Integer.parseInt(tdrMap.get("materialId").toString()));
                materialRepertoryOutboundDetailManager.insertBy(mrode);

                //出库插入负数到明细表
                Integer detailId = Integer.parseInt(tdrMap.get("id").toString());
                MaterialRepertoryDetailEntity mrde = materialRepertoryDetailManager.getEntityById(detailId);
                mrde.setId(null);
                mrde.setDetailid(detailId);
                mrde.setInventoryQty(-outboundQty);
                mrde.setOperatStatus(OUT_REPERTORY);
                materialRepertoryDetailManager.insertBy(mrde);

                //减掉总表里面的库存
                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(Integer.parseInt(tdrMap.get("materialId").toString()));
                mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mre.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if (mreList.size()!=0){
                    mre.setInventoryQty(mreList.get(0).getInventoryQty()-outboundQty);
                    mre.setId(mreList.get(0).getId());
                    materialRepertoryManager.updateByPrimaryKeySelective(mre);
                }

                MaterialRepertoryOutboundlogEntity mrole = new MaterialRepertoryOutboundlogEntity();

                MaterialBomEntity mbe2 = materialBomManager.getEntityById(Integer.parseInt(tdrMap.get("materialId").toString()));
                String numberValue2 = null;
                numberValue2 = getNumberValue(mbe2.getNumber(), numberValue2);
                mrole.setFactoryMaterialCode(mbe2.getFactoryMaterialCode());
                mrole.setNumber(numberValue2);
                mrole.setSource(mbe2.getSource());
                mrole.setMaterialBomParamValueStr(mbe2.getMaterialBomParamValueStr());
                mrole.setOriginalMaterial(mbe2.getOriginalMaterial());
                mrole.setDrawingCode(mbe2.getDrawingCode());

                mrole.setQty(outboundQty);
                mrole.setMaterialBatch(tdrMap.get("materialBatch").toString());
                mrole.setMaterialCode(info.getMaterialCode());
                mrole.setMaterialId(Integer.parseInt(tdrMap.get("materialId").toString()));
                mrole.setMaterialName(info.getMaterialName());
                mrole.setOutboundDate(info.getOutboundDate());
                mrole.setPickingUseage(info.getPickingUseage());
                mrole.setRepertoryId(info.getRepertoryId());
                mrole.setOutboundPersonId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrole.setTakeMaterialId(info.getTakeMaterialId());
                mrole.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrole.setStorageLocation(tdrMap.get("storageLocation").toString());
                materialRepertoryOutboundlogManager.insertBy(mrole);
//                leaveQtyTotal += leaveQty;
            }
            //修改待采购订单
            /*PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
            pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            pose.setStatus(STATUS_DEFAULT);
            pose.setPurchaseStayStatus(NO_ORDER);
            pose.setMaterialId(info.getMaterialId());
            pose.setSupplierId(info.getSupplierId());
            List<PurchaseOrdersStayEntity> poseList = purchaseOrdersStayManager.getListBy(pose);
            if(poseList.size() != 0){
                PurchaseOrdersStayEntity posEntity = poseList.get(0);
                posEntity.setStayPurQty(posEntity.getStayPurQty()+leaveQtyTotal);
                purchaseOrdersStayManager.updateByPrimaryKeySelective(posEntity);

                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(info.getMaterialId());
                mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if(mreList.size() != 0){

                }
            }
            if(poseList.size() != 0){
                Float stayPurQty = 0f;
                for(PurchaseOrdersStayEntity posEntity : poseList){
                    stayPurQty = posEntity.getStayPurQty();
                    if(stayPurQty >= leaveQtyTotal){
                        posEntity.setStayPurQty(stayPurQty-leaveQtyTotal);
                        purchaseOrdersStayManager.updateByPrimaryKeySelective(posEntity);
                        break;
                    }else{
                        posEntity.setStayPurQty(0f);
                        leaveQtyTotal = leaveQtyTotal - stayPurQty;
                        purchaseOrdersStayManager.updateByPrimaryKeySelective(posEntity);
                    }
                }
            }*/
        }else{
            throw new CommonBusinessException("请核对输入");
        }
    }

    @Transactional
    public void saveInboundInfo(MaterialRepertoryDetailInfo info){
        List<Map<String,Object>> moreNotifyObject = info.getMoreNotifyObject();
        if(moreNotifyObject.size() != 0){
            for(Map<String,Object> nob : moreNotifyObject){
                MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
                MaterialRepertoryInboundlogEntity mrile = new MaterialRepertoryInboundlogEntity();
                //格式化时间 入库时间为当前时间
                MaterialBomEntity mbe = materialBomManager.getEntityById(info.getMaterialId());
                String numberValue = null;
                numberValue = getNumberValue(mbe.getNumber(), numberValue);

                mrde.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                mrde.setNumber(numberValue);
                mrde.setSource(mbe.getSource());
                mrde.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                mrde.setOriginalMaterial(mbe.getOriginalMaterial());
                mrde.setDrawingCode(mbe.getDrawingCode());

                mrde.setInboundDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                mrde.setMaterialBatch(nob.get("materialBatch").toString());
                mrde.setMaterialName(info.getMaterialName());
                mrde.setMaterialCode(info.getMaterialCode());
                mrde.setPurchasePrice(info.getPurchasePrice());
                mrde.setRepertoryId(info.getRepertoryId());
                mrde.setMaterialId(info.getMaterialId());
                mrde.setSupplierId(info.getSupplierId());
                mrde.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                if(nob.get("shelfPosition") != null && !"".equals(nob.get("shelfPosition"))){
                    mrde.setStorageLocation(nob.get("shelfPosition").toString());
                    mrile.setStorageLocation(nob.get("shelfPosition").toString());
                }
                mrde.setInventoryQty(Float.parseFloat(nob.get("qty").toString()));
                //插入明细表
                materialRepertoryDetailManager.insertBy(mrde);

                mrile.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                mrile.setNumber(numberValue);
                mrile.setSource(mbe.getSource());
                mrile.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                mrile.setOriginalMaterial(mbe.getOriginalMaterial());
                mrile.setDrawingCode(mbe.getDrawingCode());
                mrile.setInboundDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                mrile.setMaterialId(info.getMaterialId());
                mrile.setMaterialName(info.getMaterialName());
                mrile.setMaterialBatch(nob.get("materialBatch").toString());
                mrile.setSupplierId(info.getSupplierId());
                mrile.setPurchasePrice(info.getPurchasePrice());
                mrile.setQty(Float.parseFloat(nob.get("qty").toString()));
                mrile.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrile.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrile.setMaterialCode(info.getMaterialCode());
                mrile.setRepertoryId(info.getRepertoryId());
                materialRepertoryInboundlogManager.insertBy(mrile);
            }
            Integer mriId = materialRepertoryManager.getMriId(info.getMaterialId());
            MaterialRepertoryEntity materialRepertoryEntity = materialRepertoryManager.getEntityById(mriId);

            if(info.getOrdeNeedFlag() == null){
                MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
                materialRelationInfo.setMaterialParentId(info.getMaterialId());
                materialRelationInfo.setStatus(STATUS_DEFAULT);
                materialRelationInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                //根据父id查询中间表中的子id
                List<MaterialRelationInfo> list = materialRelationService.getListBy(materialRelationInfo);
                //更新下一层物料的订单需求数
                if(list.size() != 0){
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    Float quantity = 0f;
                    for(MaterialRelationInfo mri : list){
                        Integer materialId = mri.getMaterialChildId();
                        mre.setMaterialId(materialId);
                        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        mre.setStatus(STATUS_DEFAULT);
                        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                        if(mreList.size() != 0){
                            quantity = mri.getQuantity();
                            mreList.get(0).setOrderNeedQty(mreList.get(0).getOrderNeedQty()-(info.getTotalNum()*quantity));
                            materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        }
                    }
                }
            }
            //更新库存和订单需求数
            /*if(materialRepertoryEntity.getOrderNeedQty()-info.getTotalNum() >0){
                materialRepertoryEntity.setOrderNeedQty(materialRepertoryEntity.getOrderNeedQty()-info.getTotalNum());
            }else{
                materialRepertoryEntity.setOrderNeedQty(0.00f);
            }*/

            materialRepertoryEntity.setInventoryQty(materialRepertoryEntity.getInventoryQty()+info.getTotalNum());
            if(materialRepertoryEntity.getStayInboundQty()-info.getTotalNum() > 0){
                materialRepertoryEntity.setStayInboundQty(materialRepertoryEntity.getStayInboundQty()-info.getTotalNum());
            }else{
                materialRepertoryEntity.setStayInboundQty(0.00f);
            }
            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);

        }
    }

    @Transactional
    public void insertInbound(MaterialRepertoryDetailInfo info){
        MaterialRepertoryDetailEntity materialRepertoryDetailEntity = ExchangeUtils.exchangeObject(info, MaterialRepertoryDetailEntity.class);
        materialRepertoryDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Integer materialId1 = info.getMaterialId();
        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId1);
        String numberValue = null;
        numberValue = getNumberValue(mbe.getNumber(), numberValue);
        if(materialId1 != null && !"".equals(materialId1)){
            List<Map<String,Object>> materialRepertoryList = materialRepertoryManager.getListSize(materialId1);
            if(materialRepertoryList.size() != 0){
                MaterialRepertoryEntity materialRepertoryEntity = materialRepertoryManager.getEntityById(Integer.parseInt(materialRepertoryList.get(0).get("id").toString()));
                materialRepertoryEntity.setInventoryQty(materialRepertoryEntity.getInventoryQty()+info.getInventoryQty());
                //生产入库减去相应订单需求数
                if("2".equals(info.getMaterialSource())){
                    MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
                    materialRelationInfo.setMaterialParentId(info.getMaterialId());
                    materialRelationInfo.setStatus(1);
                    //根据父id查询中间表中的子id
                    List<MaterialRelationInfo> mriList = materialRelationService.getListBy(materialRelationInfo);
                    if (mriList.size()!=0) {
                        for(MaterialRelationInfo mri : mriList){
                            Integer materialId = mri.getMaterialChildId();
                            MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                            mre.setMaterialId(materialId);
                            mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                            if(mreList.size() != 0){
                                if(mreList.get(0).getOrderNeedQty()-(info.getInventoryQty()*mriList.get(0).getQuantity()) > 0){
                                    mre.setOrderNeedQty(mreList.get(0).getOrderNeedQty()-(info.getInventoryQty()*mriList.get(0).getQuantity()));
                                }else{
                                    mre.setOrderNeedQty(0.00f);
                                }
                                mre.setId(mreList.get(0).getId());
                                materialRepertoryManager.updateByPrimaryKeySelective(mre);
                            }
                        }
                    }
                }
                /*if(materialRepertoryEntity.getOrderNeedQty()-info.getInventoryQty() > 0){
                    materialRepertoryEntity.setOrderNeedQty(materialRepertoryEntity.getOrderNeedQty()-info.getInventoryQty());
                }else{
                    materialRepertoryEntity.setOrderNeedQty(0.00f);
                }*/
                if(info.getUnqualifiedFlag() != null && info.getUnqualifiedFlag() == 1){
                    materialRepertoryDetailEntity.setUnqualifiedFlag(1);
                    materialRepertoryDetailEntity.setUnqualifiedQty(null);
                    if(materialRepertoryEntity.getStayInboundQty()-info.getInventoryQty() > 0){
                        materialRepertoryEntity.setStayInboundQty(materialRepertoryEntity.getStayInboundQty()-info.getInventoryQty());
                    }else{
                        materialRepertoryEntity.setStayInboundQty(0.00f);
                    }
                }
                if(info.getUnqualifiedFlag() != null && info.getUnqualifiedFlag() == 0){
                    materialRepertoryDetailEntity.setUnqualifiedFlag(0);
                    materialRepertoryEntity.setUnqualifiedQty(materialRepertoryEntity.getUnqualifiedQty() + info.getUnqualifiedQty());
                    materialRepertoryDetailEntity.setUnqualifiedQty(info.getUnqualifiedQty());
                }
                materialRepertoryEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                materialRepertoryEntity.setDrawingCode(mbe.getDrawingCode());
                materialRepertoryEntity.setMaterialCode(info.getMaterialCode());
                materialRepertoryEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                materialRepertoryEntity.setNumber(numberValue);
                materialRepertoryEntity.setMaterialSource(mbe.getSource());
                materialRepertoryEntity.setDrawingCode(mbe.getDrawingCode());
                materialRepertoryDetailEntity.setInboundDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                materialRepertoryDetailEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                materialRepertoryDetailEntity.setNumber(numberValue);
                materialRepertoryDetailEntity.setSource(mbe.getSource());
                materialRepertoryDetailEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                materialRepertoryDetailEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                materialRepertoryDetailEntity.setDrawingCode(mbe.getDrawingCode());
                materialRepertoryDetailManager.insertBy(materialRepertoryDetailEntity);
            }else{
                MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                if(info.getUnqualifiedFlag() != null && info.getUnqualifiedFlag() == 0){
                    materialRepertoryDetailEntity.setUnqualifiedFlag(0);
                    materialRepertoryEntity.setUnqualifiedQty(info.getUnqualifiedQty());
                    materialRepertoryDetailEntity.setUnqualifiedQty(info.getUnqualifiedQty());
                }
                if(info.getUnqualifiedFlag() != null && info.getUnqualifiedFlag() == 1){
                    materialRepertoryEntity.setInventoryQty(info.getInventoryQty());
                    materialRepertoryDetailEntity.setUnqualifiedFlag(1);
                }
                materialRepertoryEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                materialRepertoryEntity.setNumber(numberValue);
                materialRepertoryEntity.setDrawingCode(mbe.getDrawingCode());
                materialRepertoryEntity.setMaterialSource(mbe.getSource());
                materialRepertoryEntity.setMaterialCode(info.getMaterialCode());
                materialRepertoryEntity.setMaterialId(info.getMaterialId());
                materialRepertoryEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                materialRepertoryEntity.setOriginalMaterial(info.getOriginalMaterial());
                materialRepertoryEntity.setMaterialName(info.getMaterialName());
                materialRepertoryEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
//                materialRepertoryManager.insertBy(materialRepertoryEntity);
                materialRepertoryDetailEntity.setInboundDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                materialRepertoryDetailEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                materialRepertoryDetailEntity.setNumber(numberValue);
                materialRepertoryDetailEntity.setSource(mbe.getSource());
                materialRepertoryDetailEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                materialRepertoryDetailEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                materialRepertoryDetailEntity.setDrawingCode(mbe.getDrawingCode());
                materialRepertoryDetailManager.insertBy(materialRepertoryDetailEntity);
            }

            MaterialRepertoryInboundlogEntity mrile = new MaterialRepertoryInboundlogEntity();
            mrile.setRepertoryId(info.getRepertoryId());
            mrile.setMaterialCode(info.getMaterialCode());
            mrile.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            mrile.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
            mrile.setMaterialBatch(info.getMaterialBatch());
            mrile.setMaterialName(info.getMaterialName());
            mrile.setMaterialId(info.getMaterialId());
            mrile.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
            mrile.setNumber(numberValue);
            mrile.setSource(mbe.getSource());
            mrile.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
            mrile.setOriginalMaterial(mbe.getOriginalMaterial());
            mrile.setDrawingCode(mbe.getDrawingCode());
            if(info.getSupplierId() != null){
                mrile.setSupplierId(info.getSupplierId());
            }
            if(info.getPurchasePrice() != null){
                mrile.setPurchasePrice(info.getPurchasePrice());
            }
            mrile.setQty(info.getInventoryQty());
            if(info.getStorageLocation() != null && !"".equals(info.getStorageLocation())){
                mrile.setStorageLocation(info.getStorageLocation());
            }
            mrile.setInboundDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
            materialRepertoryInboundlogManager.insertBy(mrile);

            PurchaseOrdersEntity purchaseOrdersEntity = new PurchaseOrdersEntity();
            if(info.getPurchaseOrdersId() != null){
                purchaseOrdersEntity.setId(info.getPurchaseOrdersId());
            }
            purchaseOrdersEntity.setInboundStatus(ALREADY_INBOUND_STATUS);
            purchaseOrdersManager.updateByPrimaryKeySelective(purchaseOrdersEntity);

            MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
            mrice.setId(info.getCheckId());
            mrice.setInboundStutas(ALREADY_INBOUND_STATUS);
            materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(mrice);
        }
    }

    @Transactional
    public void saveShiftPosition(MaterialRepertoryDetailInfo info){
        MaterialRepertoryDetailEntity materialRepertoryDetailEntity = materialRepertoryDetailManager.getEntityById(info.getId());
        //减少原来的库存
        materialRepertoryDetailEntity.setInventoryQty(materialRepertoryDetailEntity.getInventoryQty()-info.getMoveQty());
        materialRepertoryDetailManager.updateByPrimaryKeySelective(materialRepertoryDetailEntity);
        materialRepertoryDetailEntity.setInventoryQty(info.getMoveQty());
        materialRepertoryDetailEntity.setId(null);
        String storageLocationStr = "";
        //拼接库位，格式以下划线分开，得到字符串格式
        for(int i=0;i<info.getTargetPosition().size();i++){
            if(i<info.getTargetPosition().size()-1){
                storageLocationStr += info.getTargetPosition().get(i)+"_";
            }else{
                storageLocationStr += info.getTargetPosition().get(i);
            }
        }
        //重新生成一条明细
        materialRepertoryDetailEntity.setStorageLocation(storageLocationStr);
        materialRepertoryDetailManager.insertBy(materialRepertoryDetailEntity);

    }

    public String getNumberValue(String number, String numberValue) {
        if(number != null && !"".equals(number)){
            ProductTypeEntity productTypeEntity = new ProductTypeEntity();
            productTypeEntity.setNumber(number);
            productTypeEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            List<ProductTypeEntity> pteList = productTypeManager.getListBy(productTypeEntity);
            if(pteList.size() != 0){
                numberValue = pteList.get(0).getType();
            }
        }
        return numberValue;
    }

    public List<MaterialRepertoryDetailInfo> getDetailList(Integer materialId,Integer companyId){
        return materialRepertoryDetailManager.getDetailList(materialId,companyId);
    }

    public Float getUseQty(Integer detailId){
        return materialRepertoryDetailManager.getUseQty(detailId);
    }

    public List<MaterialRepertoryDetailInfo> getListByOrder(MaterialRepertoryDetailInfo mrdi){
        return materialRepertoryDetailManager.getListByOrder(mrdi.getMaterialId(),mrdi.getRepertoryId(),LoginFilter.getCurrentUser().getCompanyId());
    }
    /**
     * 获取拼接的参数值
     * @param id
     * @return
     */
    public String getParamValue(Integer id){
        //根据子id去查部件-参数表
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialId(id);
        materialBomParamNameInfo.setStatus(1);
        MaterialBomParamNameEntity mbpne = ExchangeUtils.exchangeObject(materialBomParamNameInfo,MaterialBomParamNameEntity.class);
        List<MaterialBomParamNameEntity> mbpniList = materialBomParamNameManager.getListBy(mbpne);
        String materialBomParamNameValueStr = "";
        if (mbpniList.size()!=0){
            for (int j = 0;j < mbpniList.size(); j++){
                if (j<mbpniList.size()-1) {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
                }else {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
                }
            }
        }
        if (materialBomParamNameValueStr != ""){
            return (materialBomParamNameValueStr);
        }else {
            return (null);
        }
    }

    public List<MaterialRepertoryDetailInfo> getMobileListByLike(int pageNum, int pageSize, Integer repertoryId,List<String> concatFieldList){
        return materialRepertoryDetailManager.getMobileListByLike(pageNum,pageSize,repertoryId,concatFieldList);
    }

    public List<MaterialRepertoryDetailInfo> getMobilePageListByLike(Integer repertoryId,List<String> concatFieldList){
        return materialRepertoryDetailManager.getMobilePageListByLike(repertoryId,concatFieldList);
    }
}
