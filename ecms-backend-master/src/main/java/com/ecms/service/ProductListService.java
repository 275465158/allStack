package com.ecms.service;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.ProductListInfo;
import com.ecms.bean.SellerInvoiceInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.ProductListEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductListManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ProductListService extends BaseService<ProductListEntity, ProductListInfo> {

    @Resource
    private ProductListManager manager;
    @Resource
    private MaterialBomService service;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private QueryPriceService queryPriceService;
    @Resource
    private SellerInvoiceService sellerInvoiceService;

    @Override
    protected BaseManager<ProductListEntity> getManager() {
        return manager;
    }

    /**
     * @Author:
     * @date
     * @Description:
     */
    public void splice(Pager<ProductListInfo> pager) {
        for (int i=0;i<pager.getList().size();i++) {
            ProductListInfo info=pager.getList().get(i);
			MaterialBomInfo materialBomInfo= service.getModel(pager.getList().get(i).getProductId());
            info.setMaterialName(materialBomInfo.getMaterialName());
            info.setMaterialUnit(materialBomInfo.getMaterialUnit());
            info.setProductType(productTypeService.getProductTypeByNumber(materialBomInfo.getNumber(), LoginFilter.getCurrentUser().getCompanyId()).getType());
            info.setMaterialBomParamNameStr(service.getParamValue(materialBomInfo.getId()));
            SellerInvoiceInfo sellerInvoiceInfo=new SellerInvoiceInfo();
            sellerInvoiceInfo.setName(sellerCustomerService.getModel(queryPriceService.getModel(info.getQueryId()).getCustomerId()).getInvoice());
            ExchangeUtils.convertBlank2Null(sellerInvoiceInfo);
           if( sellerInvoiceService.getListBy(sellerInvoiceInfo).size()!=0){
               info.setTaxRate(sellerInvoiceService.getListBy(sellerInvoiceInfo).get(0).getTaxRate());
           }

        }
    }


}
