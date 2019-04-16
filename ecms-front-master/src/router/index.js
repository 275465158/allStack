import Vue from "vue";
import Router from "vue-router";
Vue.prototype.$path = process.env.API_ROOT;
Vue.use(Router);

export default new Router({
    routes: [{
        path: "/",
        redirect: "/dashboard"
    },
    {
        path: "/",
        component: resolve => require(["../components/common/Home.vue"], resolve),
        meta: {
            title: "自述文件"
        },
        children: [{
            path: "/dashboard",
            component: resolve =>
                require(["../components/page/Dashboard.vue"], resolve),
            meta: {
                title: "系统首页"
            }
        },
        {
            path: "/employeeList",
            component: resolve =>
                require([
                    "../components/page/personnel/employee/list.vue"
                ], resolve),
            meta: {
                title: "人员列表"
            }
        },
        {
            path: "/materielTableList",
            component: resolve =>
                require([
                    "../components/page/repertory/materielTable/list.vue"
                ], resolve),
            meta: {
                title: "物料列表"
            }
        },
        {
            path: "/inboundLogList",
            component: resolve =>
                require([
                    "../components/page/repertory/materialInbound/inboundLog.vue"
                ], resolve),
            meta: {
                title: "入库明细"
            }
        },
        {
            path: "/outboundLogList",
            component: resolve =>
                require([
                    "../components/page/repertory/materialOutbound/outboundLog.vue"
                ], resolve),
            meta: {
                title: "出库明细"
            }
        },
        {
            path: "/materialTableInfo",
            component: resolve =>
                require([
                    "../components/page/repertory/materielTable/info.vue"
                ], resolve),
            meta: {
                title: "物料详情"
            }
        },
        {
            path: "/queryprice",
            component: resolve =>
                require([
                    "../components/page/customerplatform/queryprice/list.vue"
                ], resolve),
            meta: {
                title: "询价列表"
            }
        },
        {
            path: "/addqueryprice",
            component: resolve =>
                require([
                    "../components/page/customerplatform/queryprice/info.vue"
                ], resolve),
            meta: {
                title: "添加询价"
            }
        },
        {
            path: "/detailQueryPrice",
            component: resolve =>
                require([
                    "../components/page/customerplatform/queryprice/detail.vue"
                ], resolve),
            meta: {
                title: "询价详情"
            }
        },
        {
            path: "/procedureList",
            component: resolve =>
                require([
                    "../components/page/product/procedure/procedurelist.vue"
                ], resolve),
            meta: {
                title: "设备程序"
            }
        },
        {
            path: "/procedureSearch",
            component: resolve =>
                require([
                    "../components/page/product/procedure/proceduresearch.vue"
                ], resolve),
            meta: {
                title: "设备程序明细"
            }
        },
        {
            path: "/productJobGuide",
            component: resolve =>
                require([
                    "../components/page/product/jobGuide/list.vue"
                ], resolve),
            meta: {
                title: "作业指导书"
            }
        },
        {
            path: "/deliveryAddress",
            component: resolve =>
                require([
                    "../components/page/customerplatform/deliveryAddress.vue"
                ], resolve),
            meta: {
                title: "收货地址"
            }
        },
        {
            path: "/review",
            component: resolve =>
                require([
                    "../components/page/personnel/review/list.vue"
                ], resolve),
            meta: {
                title: "来料不合格评审"
            }
        },
        {
            path: "/proReview",
            component: resolve =>
                require([
                    "../components/page/personnel/review/list1.vue"
                ], resolve),
            meta: {
                title: "生产不合格评审"
            }
        },
        {
            path: "/ReviewIndex",
            component: resolve =>
                require([
                    "../components/page/personnel/review/index.vue"
                ], resolve),
            meta: {
                title: "评审管理"
            }
        },
        // {
        //     path: "/productparameter",
        //     component: resolve =>
        //         require(["../components/page/product/productparameter.vue"], resolve),
        //     meta: { title: "产品参数", permission: true }
        // },
        {
            path: "/productmix",
            component: resolve =>
                require(["../components/page/product/productmix.vue"], resolve),
            meta: {
                title: "产品结构",
                permission: true
            }
        },
        {
            path: "/structureparameter",
            component: resolve =>
                require(["../components/page/product/structureparameter.vue"], resolve),
            meta: {
                title: "结构参数",
                permission: true
            }
        },
        {
            path: "/producttype",
            component: resolve =>
                require(["../components/page/product/producttype.vue"], resolve),
            meta: {
                title: "产品类型",
                permission: true
            }
        },
        {
            path: "/addproductmix",
            component: resolve =>
                require(["../components/page/product/addproductmix.vue"], resolve),
            meta: {
                title: "添加产品结构",
                permission: true
            }
        },
        //产品结构参数
        {
            path: "/structSelectorList",
            component: resolve =>
                require(["../components/page/product/struct/structSelectorList.vue"], resolve),
            meta: {
                title: "产品结构参数",
                permission: true
            }
        },
        {
            path: "/structList",
            component: resolve =>
                require(["../components/page/product/struct/structList.vue"], resolve),
            meta: {
                title: "产品结构参数",
                permission: true
            }
        },
        {
            path: "/toolTypeList",
            component: resolve =>
                require(["../components/page/product/tool/tooltypelist.vue"], resolve),
            meta: {
                title: "工具类型",
                permission: true
            }
        },
        {
            path: "/toolList",
            component: resolve =>
                require(["../components/page/product/tool/toollist.vue"], resolve),
            meta: {
                title: "工具列表",
                permission: true
            }
        },
        {
            path: "/processList",
            component: resolve =>
                require(["../components/page/product/process/processlist.vue"], resolve),
            meta: {
                title: "工艺列表",
                permission: true
            }
        },
        {
            path: "/processSearch",
            component: resolve =>
                require(["../components/page/product/process/processsearch.vue"], resolve),
            meta: {
                title: "工艺明细",
                permission: true
            }
        },
        {
            path: "/processInfo",
            component: resolve =>
                require(["../components/page/product/process/processinfo.vue"], resolve),
            meta: {
                title: "新增工艺",
                permission: true
            }
        },
        {
            path: "/orderBom",
            component: resolve =>
                require(["../components/page/product/orderBom/orderbomlist.vue"], resolve),
            meta: {
                title: "订单BOM",
                permission: true
            }
        },
        {
            path: "/editOrderBom",
            component: resolve =>
                require(["../components/page/product/orderBom/editorderbom.vue"], resolve),
            meta: {
                title: "修改订单BOM",
                permission: true
            }
        },
        {
            path: "/orderBomSearch",
            component: resolve =>
                require(["../components/page/product/orderBom/orderbomsearch.vue"], resolve),
            meta: {
                title: "修改订单BOM",
                permission: true
            }
        },
        {
            path: "/materialList",
            component: resolve =>
                require(["../components/page/product/material/materiallist.vue"], resolve),
            meta: {
                title: "物料列表",
                permission: true
            }
        },
        {
            path: "/materialSearch",
            component: resolve =>
                require(["../components/page/product/material/materialsearch.vue"], resolve),
            meta: {
                title: "物料明细",
                permission: true
            }
        },
        {
            path: "/materialEdit",
            component: resolve =>
                require(["../components/page/product/material/materialedit.vue"], resolve),
            meta: {
                title: "物料修改",
                permission: true
            }
        },
        {
            path: "/materialUpEdit",
                component: resolve =>
            require(["../components/page/product/material/materialupedit.vue"], resolve),
                meta: {
            title: "修改升级物料",
                permission: true
        }
        },
        {
            path: "/materialUpgraded",
            component: resolve =>
                require(["../components/page/product/material/materialUpgraded.vue"], resolve),
            meta: {
                title: "物料升级",
                permission: true
            }
        },
        {
            path: "/materialCopyEdit",
            component: resolve =>
                require(["../components/page/product/material/materialcopyedit.vue"], resolve),
            meta: {
                title: "复制修改",
                permission: true
            }
        },
        {
            path: "/materialAdd",
            component: resolve =>
                require(["../components/page/product/material/materialadd.vue"], resolve),
            meta: {
                title: "新增物料",
                permission: true
            }
        },
        {
            path: "/materialMerge",
            component: resolve =>
                require(["../components/page/product/material/materialMerge.vue"], resolve),
            meta: {
                title: "物料合并",
                permission: true
            }
        },
        {
            path: "/productList",
            component: resolve =>
                require(["../components/page/product/product/productlist.vue"], resolve),
            meta: {
                title: "产品列表",
                permission: true
            }
        },
        {
            path: "/productSearch",
            component: resolve =>
                require(["../components/page/product/product/productsearch.vue"], resolve),
            meta: {
                title: "产品明细",
                permission: true
            }
        },
        {
            path: "/productAdd",
            component: resolve =>
                require(["../components/page/product/product/productadd.vue"], resolve),
            meta: {
                title: "新增产品",
                permission: true
            }
        },
        {
            path: "/productEdit",
            component: resolve =>
                require(["../components/page/product/product/productedit.vue"], resolve),
            meta: {
                title: "修改产品",
                permission: true
            }
        },
        {
            path: "/productCopyEdit",
            component: resolve =>
                require(["../components/page/product/product/productcopyedit.vue"], resolve),
            meta: {
                title: "复制修改",
                permission: true
            }
        },
        {
            path: "/productView",
            component: resolve =>
                require(["../components/page/product/product/productview.vue"], resolve),
            meta: {
                title: "物料分解",
                permission: true
            }
        },

        {
            path: "/materialBomInfo",
            component: resolve =>
                require(["../components/page/product/bominfo.vue"], resolve),
            meta: {
                title: "BOM清单",
                permission: true
            }
        },
        {
            path: "/materialBomSecrch",
            component: resolve =>
                require(["../components/page/product/bomsearch.vue"], resolve),
            meta: {
                title: "BOM详情",
                permission: true
            }
        },
        {
            path: "/materialBomReview",
            component: resolve =>
                require(["../components/page/activiti/review/materialBomReview.vue"], resolve),
            meta: {
                title: "物料评审",
                permission: true
            }
        },
        {
            path: "/bomTasksList",
            component: resolve =>
                require(["../components/page/product/task/list.vue"], resolve),
            meta: {
                title: "BOM制作",
                permission: true
            }
        },
        {
            path: "/bomTasksInfo",
            component: resolve =>
                require(["../components/page/product/task/info.vue"], resolve),
            meta: {
                title: "BOM制作明细",
                permission: true
            }
        },
        {
            path: "/productParameterSpecification",
            component: resolve =>
                require(["../components/page/product/parameter/specification.vue"], resolve),
            meta: {
                title: "参数规格",
                permission: true
            }
        },
        {
            path: "/productParameterName",
            component: resolve =>
                require(["../components/page/product/parameter/name.vue"], resolve),
            meta: {
                title: "参数名称",
                permission: true
            }
        },
        {
            path: "/productDrawing",
            component: resolve =>
                require(["../components/page/product/drawing/list.vue"], resolve),
            meta: {
                title: "图纸列表",
                permission: true
            }
        },
        {
            path: "/paymentTypeList",
            component: resolve =>
                require([
                    "../components/page/supplier/configuration/PaymentTypeList.vue"
                ], resolve),
            meta: {
                title: "付款方式",
                permission: true
            }
        },
        {
            path: "/paymentPeriodList",
            component: resolve =>
                require([
                    "../components/page/supplier/configuration/PaymentPeriodList.vue"
                ], resolve),
            meta: {
                title: "付款周期",
                permission: true
            }
        },
        {
            path: "/invoiceTypeList",
            component: resolve =>
                require([
                    "../components/page/supplier/configuration/InvoiceTypeList.vue"
                ], resolve),
            meta: {
                title: "发票类型",
                permission: true
            }
        },
        {
            path: "/shippingMethodList",
            component: resolve =>
                require([
                    "../components/page/supplier/configuration/ShippingMethodList.vue"
                ], resolve),
            meta: {
                title: "送货方式",
                permission: true
            }
        },
        {
            path: "/changePassword",
            component: resolve =>
                require(["../components/page/system/changePassword/info.vue"], resolve),
            meta: {
                title: "修改密码",
                permission: true
            }
        },
        {
            path: "/repertoryInfo",
            component: resolve =>
                require(["../components/page/repertory/info.vue"], resolve),
            meta: {
                title: "仓库信息",
                permission: true
            }
        },
        /*{
            path: "/materielList",
            component: resolve =>
                require(["../components/page/materiel/list.vue"], resolve),
            meta: { title: "物料列表", permission: true }
        },*/
        {
            path: "/repertoryStayDelivery",
            component: resolve =>
                require(["../components/page/repertory/stayDelivery/list.vue"], resolve),
            meta: {
                title: "待收货",
                permission: true
            }
        },
        {
            path: "/materialRepertoryList",
            component: resolve =>
                require(["../components/page/repertory/repertoryMaterial/list.vue"], resolve),
            meta: {
                title: "物料列表",
                permission: true
            }
        },
        {
            path: "/repertoryList",
            component: resolve =>
                require(["../components/page/repertory/list.vue"], resolve),
            meta: {
                title: "仓库列表",
                permission: true
            }
        },
        {
            path: "/returnedPurchaseList",
            component: resolve =>
                require(["../components/page/repertory/returnedPurchase/list.vue"], resolve),
            meta: {
                title: "退货单",
                permission: true
            }
        },
        {
            path: "/replacePurchaseList",
            component: resolve =>
                require(["../components/page/repertory/replacePurchase/list.vue"], resolve),
            meta: {
                title: "换货单",
                permission: true
            }
        },
        {
            path: "/materialOutboundList",
            component: resolve =>
                require(["../components/page/repertory/materialOutbound/list.vue"], resolve),
            meta: {
                title: "出库列表",
                permission: true
            }
        },
        {
            path: "/materialInboundList",
            component: resolve =>
                require(["../components/page/repertory/materialInbound/list.vue"], resolve),
            meta: {
                title: "入库列表",
                permission: true
            }
        },
        {
            path: "/repertoryMaterialInfo",
            component: resolve =>
                require(["../components/page/repertory/repertoryMaterial/info.vue"], resolve),
            meta: {
                title: "物料明细",
                permission: true
            }
        },
        {
            path: "/checkMaterialList",
            component: resolve =>
                require(["../components/page/repertory/checkMaterial/list.vue"], resolve),
            meta: {
                title: "盘点",
                permission: true
            }
        },
        {
            path: "/repertoryStayInbound",
            component: resolve =>
                require(["../components/page/repertory/stayInbound/list.vue"], resolve),
            meta: {
                title: "待入库",
                permission: true
            }
        },
        {
            path: "/materialInfoList",
            component: resolve =>
                require(["../components/page/materialBom/list.vue"], resolve),
            meta: {
                title: "物料列表",
                permission: true
            }
        },
        {
            path: "/materialInfoInfo",
            component: resolve =>
                require(["../components/page/materialBom/info.vue"], resolve),
            meta: {
                title: "物料列表明细",
                permission: true
            }
        },
        /*{
          path: "/materielInfo",
          component: resolve =>
            require(["../components/page/materiel/info.vue"], resolve),
          meta: { title: "物料明细", permission: true }
        },*/
        {
            path: "/materielInfo",
            component: resolve =>
                require(["../components/page/repertory/repertoryMaterial/add.vue"], resolve),
            meta: {
                title: "添加物料",
                permission: true
            }
        },
        {
            path: "/outboundList",
            component: resolve =>
                require(["../components/page/repertory/outbound/list.vue"], resolve),
            meta: {
                title: "出库",
                permission: true
            }
        },
        {
            path: "/outboundInfo",
            component: resolve =>
                require(["../components/page/repertory/outbound/info.vue"], resolve),
            meta: {
                title: "出库明细",
                permission: true
            }
        },
        {
            path: "/sendMaterialLogList",
            component: resolve =>
                require(["../components/page/repertory/sendMaterial/list.vue"], resolve),
            meta: {
                title: "发货记录",
                permission: true
            }
        },
        {
            path: "/materielDetailList",
            component: resolve =>
                require(["../components/page/materiel/materielDetailList.vue"], resolve),
            meta: {
                title: "出入库明细",
                permission: true
            }
        },
        {
            path: "/storageList",
            component: resolve =>
                require(["../components/page/repertory/storage/list.vue"], resolve),
            meta: {
                title: "货架列表",
                permission: true
            }
        },
        {
            path: "/entryList",
            component: resolve =>
                require(["../components/page/materiel/inventoryEntry/list.vue"], resolve),
            meta: {
                title: "入库单列表",
                permission: true
            }
        },
        {
            path: "/entryInfo",
            component: resolve =>
                require(["../components/page/materiel/inventoryEntry/info.vue"], resolve),
            meta: {
                title: "入库单明细",
                permission: true
            }
        },
        {
            path: "/deliveryInfo",
            component: resolve =>
                require(["../components/page/materiel/delivery/info.vue"], resolve),
            meta: {
                title: "发货明细",
                permission: true
            }
        },
        {
            path: "/deliveryList",
            component: resolve =>
                require(["../components/page/materiel/delivery/list.vue"], resolve),
            meta: {
                title: "发货管理",
                permission: true
            }
        },
        {
            path: "/pickingInfo",
            component: resolve =>
                require(["../components/page/materiel/picking/info.vue"], resolve),
            meta: {
                title: "出库单明细",
                permission: true
            }
        },
        {
            path: "/pickingList",
            component: resolve =>
                require(["../components/page/materiel/picking/list.vue"], resolve),
            meta: {
                title: "出库管理",
                permission: true
            }
        },
        {
            path: "/materielProduceList",
            component: resolve =>
                require(["../components/page/materiel/produce/list.vue"], resolve),
            meta: {
                title: "生产发料管理",
                permission: true
            }
        },
        {
            path: "/materielProduceInfo",
            component: resolve =>
                require(["../components/page/materiel/produce/info.vue"], resolve),
            meta: {
                title: "生产发料明细",
                permission: true
            }
        },
        {
            path: "/supplierList",
            component: resolve =>
                require(["../components/page/supplier/list.vue"], resolve),
            meta: {
                title: "供应商列表",
                permission: true
            }
        },
        {
            path: "/sendDeliveryList",
            component: resolve =>
                require(["../components/page/repertory/sendDelivery/list.vue"], resolve),
            meta: {
                title: "发货",
                permission: true
            }
        },
        {
            path: "/supplierInfo",
            component: resolve =>
                require(["../components/page/supplier/info.vue"], resolve),
            meta: {
                title: "增加供应商信息",
                permission: true
            }
        },
        {
            path: "/supplieredit",
            component: resolve =>
                require(["../components/page/supplier/edit.vue"], resolve),
            meta: {
                title: "修改供应商信息",
                permission: true
            }
        },
        {
            path: "/supplierdetail",
            component: resolve =>
                require(["../components/page/supplier/detail.vue"], resolve),
            meta: {
                title: "供应商明细",
                permission: true
            }
        },
        {
            path: "/purchaseQueueList",
            component: resolve =>
                require(["../components/page/purchase/queue/list.vue"], resolve),
            meta: {
                title: "待采购列表",
                permission: true
            }
        },
        {
            path: "/purchaseMaterialList",
            component: resolve =>
                require(["../components/page/purchase/materialList/list.vue"], resolve),
            meta: {
                title: "物料列表",
                permission: true
            }
        },
        {
            path: "/purchaseOrderList",
            component: resolve =>
                require(["../components/page/purchase/orders/list.vue"], resolve),
            meta: {
                title: "采购订单",
                permission: true
            }
        },
        {
            path: "/purchaseOrdersAdd",
            component: resolve =>
                require(["../components/page/purchase/orders/add.vue"], resolve),
            meta: {
                title: "手工新增采购订单",
                permission: true
            }
        },
        {
            path: "/purchaseOrderVerificatList",
            component: resolve =>
                require(["../components/page/purchase/verificat/list.vue"], resolve),
            meta: {
                title: "采购单核销",
                permission: true
            }
        },
        {
            path: "/purchaseOrderVerificatInfo",
            component: resolve =>
                require(["../components/page/purchase/verificat/verificatinfo.vue"], resolve),
            meta: {
                title: "核销明细",
                permission: true
            }
        },
        {
            path: "/purchaseByStay",
            component: resolve =>
                require([
                    "../components/page/purchase/orders/purchase.vue"
                ], resolve),
            meta: {
                title: "物料下单",
                permission: true
            }
        },
        {
            path: "/post",
            component: resolve =>
                require(["../components/page/personnel/post/list.vue"], resolve),
            meta: {
                title: "岗位列表"
            }
        },
        {
            path: "/department",
            component: resolve =>
                require([
                    "../components/page/personnel/department/list.vue"
                ], resolve),
            meta: {
                title: "部门列表"
            }
        },
        {
            path: "/departmentInfo",
            component: resolve =>
                require([
                    "../components/page/personnel/department/info.vue"
                ], resolve),
            meta: {
                title: "部门信息"
            }
        },
        {
            path: "/employeeInfo",
            component: resolve =>
                require([
                    "../components/page/personnel/employee/info.vue"
                ], resolve),
            meta: {
                title: "人员信息"
            }
        },
        {
            path: "/tabs",
            component: resolve =>
                require(["../components/page/Tabs.vue"], resolve),
            meta: {
                title: "tab选项卡"
            }
        },
        {
            // 账号管理
            path: "/userList",
            component: resolve =>
                require(["../components/page/system/user/list.vue"], resolve),
            meta: {
                title: "账号管理",
                permission: true
            }
        },
        {
            // 系统设置
            path: "/company",
            component: resolve =>
                require(["../components/page/system/company.vue"], resolve),
            meta: {
                title: "系统设置",
                permission: true
            }
        },
        {
            path: "/materialBomList",
            component: resolve =>
                require(["../components/page/product/bomlist.vue"], resolve),
            meta: {
                title: "产品列表",
                permission: true
            }
        },
        // {
        //     path: "/productparameter",
        //     component: resolve =>
        //         require(["../components/page/product/productparameter.vue"], resolve),
        //     meta: { title: "产品参数", permission: true }
        // },
        {
            path: "/productcustom",
            component: resolve =>
                require(["../components/page/product/product/productcustom.vue"], resolve),
            meta: {
                title: "产品定制",
                permission: true
            }
        },
        {
            path: "/checkstandard",
            component: resolve =>
                require(["../components/page/product/check/checkstandard.vue"], resolve),
            meta: {
                title: "验收标准"
            }
        },
        {
            path: "/addcheck",
            component: resolve =>
                require(["../components/page/product/check/addcheck.vue"], resolve),
            meta: {
                title: "添加验收标准"
            }
        },
        {
            path: "/editcheck",
            component: resolve =>
                require(["../components/page/product/check/editcheck.vue"], resolve),
            meta: {
                title: "修改验收标准"
            }
        },
        {
            path: "/checkdetail",
            component: resolve =>
                require(["../components/page/product/check/checkdetail.vue"], resolve),
            meta: {
                title: "验收标准明细"
            }
        },
        /*{
            path: "/materielList",
            component: resolve =>
                require(["../components/page/materiel/list.vue"], resolve),
            meta: { title: "物料列表", permission: true }
        },*/
        {
            path: "/purchaseBySupplier",
            component: resolve =>
                require([
                    "../components/page/purchase/orders/supplier.vue"
                ], resolve),
            meta: {
                title: "供应商下单",
                permission: true
            }
        },
        {
            path: "/purchaseOrderInfo",
            component: resolve =>
                require([
                    "../components/page/purchase/orders/info.vue"
                ], resolve),
            meta: {
                title: "采购订单明细",
                permission: true
            }
        }, {
            path: "/purchaseOrderReview",
            component: resolve =>
                require([
                    "../components/page/activiti/review/purchaseOrderReview.vue"
                ], resolve),
            meta: {
                title: "采购订单评审",
                permission: true
            }
        },
        {
            path: "/purchaseOrderPrint",
            component: resolve =>
                require([
                    "../components/page/purchase/orders/print.vue"
                ], resolve),
            meta: {
                title: "采购订单打印",
                permission: true
            }
        },
        {
            path: "/purchaseOrderListInfo",
            component: resolve =>
                require([
                    "../components/page/purchase/orders/listinfo.vue"
                ], resolve),
            meta: {
                title: "采购订单明细明细",
                permission: true
            }
        },
        {
            path: "/sellerPayType",
            component: resolve =>
                require([
                    "../components/page/seller/orderParam/payType.vue"
                ], resolve),
            meta: {
                title: "付款方式",
                permission: true
            }
        },
        {
            path: "/sellerPayRule",
            component: resolve =>
                require([
                    "../components/page/seller/orderParam/payRule.vue"
                ], resolve),
            meta: {
                title: "付款规则",
                permission: true
            }
        },
        {
            path: "/editoffer",
            component: resolve =>
                require([
                    "../components/page/seller/offerprice/edit.vue"
                ], resolve),
            meta: {
                title: "报价系统",
                permission: true
            }
        },
        {
            path: "/listoffer",
            component: resolve =>
                require([
                    "../components/page/seller/offerprice/list.vue"
                ], resolve),
            meta: {
                title: "报价系统",
                permission: true
            }
        },
        {
            path: "/sellerInvoice",
            component: resolve =>
                require([
                    "../components/page/seller/orderParam/invoice.vue"
                ], resolve),
            meta: {
                title: "发票类型",
                permission: true
            }
        },
        {
            path: "/sellerDelivery",
            component: resolve =>
                require([
                    "../components/page/seller/orderParam/delivery.vue"
                ], resolve),
            meta: {
                title: "送货方式",
                permission: true
            }
        },
        {
            path: "/sellerCustomer",
            component: resolve =>
                require([
                    "../components/page/seller/customer/list.vue"
                ], resolve),
            meta: {
                title: "客户管理",
                permission: true
            }
        },
        {
            path: "/sellerCustomerInfo",
            component: resolve =>
                require([
                    "../components/page/seller/customer/info.vue"
                ], resolve),
            meta: {
                title: "客户资料",
                permission: true
            }
        },
        {
            path: "/sellerOrder",
            component: resolve =>
                require([
                    "../components/page/seller/order/list.vue"
                ], resolve),
            meta: {
                title: "订单管理",
                permission: true
            }
        },
        {
            path: "/tradeMark",
            component: resolve =>
                require([
                    "../components/page/seller/order/trademark.vue"
                ], resolve),
            meta: {
                title: "商标管理",
                permission: true
            }
        },
        {
            path: "/sellerOrderSearch",
            component: resolve =>
                require([
                    "../components/page/seller/order/search.vue"
                ], resolve),
            meta: {
                title: "订单明细",
                permission: true
            }
        },
        {
            // 来料检测
            path: '/incomingInspection',
            component: resolve => require(['../components/page/qualityManage/incomingCheck/list.vue'], resolve),
            meta: {
                title: '来料检测列表',
                permission: true
            }
        },
        {
            // 来料检测
            path: '/incomingInspectionLog',
            component: resolve => require(['../components/page/qualityManage/incomingCheck/loglist.vue'], resolve),
            meta: {
                title: '来料检测记录',
                permission: true
            }
        },
        {
            // 来料检测
            path: '/incomingInspectionLogInfo',
            component: resolve => require(['../components/page/qualityManage/incomingCheck/loginfo.vue'], resolve),
            meta: {
                title: '来料检测记录详情',
                permission: true
            }
        },
        {
            // 生产检测详情
            path: '/productionLogInfo',
            component: resolve => require(['../components/page/qualityManage/productionCheck/loginfo.vue'], resolve),
            meta: {
                title: '来料检测记录详情',
                permission: true
            }
        },
        {
            // 来料检测
            path: '/incomingCheck',
            component: resolve => require(['../components/page/qualityManage/incomingCheck/check.vue'], resolve),
            meta: {
                title: '来料检测',
                permission: true
            }
        },
        {
            // 生产检测列表
            path: '/production',
            component: resolve => require(['../components/page/qualityManage/productionCheck/list.vue'], resolve),
            meta: {
                title: '生产检测列表',
                permission: true
            }
        },
        {
            // 生产检测
            path: '/productionCheck',
            component: resolve => require(['../components/page/qualityManage/productionCheck/edit.vue'], resolve),
            meta: {
                title: '生产检测',
                permission: true
            }
        },
        {
            // 生产检测记录
            path: '/productionRecord',
            component: resolve => require(['../components/page/qualityManage/productionCheck/logList.vue'], resolve),
            meta: {
                title: '生产检测记录',
                permission: true
            }
        },
        {
            // 不合格类型
            path: '/unqualifiedType',
            component: resolve => require(['../components/page/qualityManage/productionCheck/unqualifiedType.vue'], resolve),
            meta: {
                title: '不合格类型',
                permission: true
            }
        },
        {
            // 不合格品处理-生产
            path: '/unqualifiedProductReport',
            component: resolve => require(['../components/page/qualityManage/unqualifiedReport/production/list.vue'], resolve),
            meta: {
                title: '不合格品处理-生产',
                permission: true
            }
        },
        {
            // 不合格品处理-来料
            path: '/incomingUnqualifiedReport',
            component: resolve => require(['../components/page/qualityManage/unqualifiedReport/incoming/list.vue'], resolve),
            meta: {
                title: '不合格品处理-来料',
                permission: true
            }
        },
        {
            // 设备列表
            path: '/equipmentList',
            component: resolve => require(['../components/page/equipmentManage/equipmentList.vue'], resolve),
            meta: {
                title: '设备列表',
                permission: true
            }
        },
        {
            // 设备列表添加
            path: '/equipmentAdd',
            component: resolve => require(['../components/page/equipmentManage/equipmentAdd.vue'], resolve),
            meta: {
                title: '设备列表添加',
                permission: true
            }
        },
        {
            // 设备列表修改
            path: '/equipmentEdit',
            name: 'equipmentEdit',
            component: resolve => require(['../components/page/equipmentManage/equipmentEdit.vue'], resolve),
            meta: {
                title: '设备列表修改',
                permission: true
            }
        },
        {
            // 生产排班
            path: '/produceSchedule',
            name: 'produceSchedule',
            component: resolve => require(['../components/page/produceManage/produceSchedule.vue'], resolve),
            meta: {
                title: '生产排班',
                permission: true
            }
        },
        {
            // 生产排班明细
            path: '/produceScheduleDetail',
            name: 'produceScheduleDetail',
            component: resolve => require(['../components/page/produceManage/produceScheduleDetail.vue'], resolve),
            meta: {
                title: '生产排班明细',
                permission: true
            }
        },
        {
            // 岗位管理
            path: '/postManage',
            name: 'postManage',
            component: resolve => require(['../components/page/produceManage/postManage.vue'], resolve),
            meta: {
                title: '岗位管理',
                permission: true
            }
        },
        {
            // 岗位修改
            path: '/postManageEdit',
            name: 'postManageEdit',
            component: resolve => require(['../components/page/produceManage/postManageEdit.vue'], resolve),
            meta: {
                title: '岗位设备修改',
                permission: true
            }
        },
        {
            // 生产计划
            path: '/producePlan',
            name: 'producePlan',
            component: resolve => require(['../components/page/produceManage/producePlan/producePlan.vue'], resolve),
            meta: {
                title: '生产计划',
                permission: true
            }
        },
        {
            // 生产记录
            path: '/produceLog',
            name: 'produceLog',
            component: resolve => require(['../components/page/produceManage/produceLog/produceLog.vue'], resolve),
            meta: {
                title: '生产记录',
                permission: true
            }
        },
        {
            // 生产制程记录
            path: '/produceLogList',
            name: 'produceLogList',
            component: resolve => require(['../components/page/produceManage/produceLog/produceLogList.vue'], resolve),
            meta: {
                title: '生产制程记录',
                permission: true
            }
        },
        {
            // 生产记录详情
            path: '/produceLogDetail',
            name: 'produceLogDetail',
            component: resolve => require(['../components/page/produceManage/produceLog/produceLogDetail.vue'], resolve),
            meta: {
                title: '生产记录详情',
                permission: true
            }
        },
        {
            // 生产计划详情
            path: '/producePlanDetail',
            name: 'producePlanDetail',
            component: resolve => require(['../components/page/produceManage/producePlan/producePlanDetail.vue'], resolve),
            meta: {
                title: '生产计划详情',
                permission: true
            }
        },
        {
            // 领料/退料
            path: '/materialIssueReturnList',
            name: 'materialIssueReturnList',
            component: resolve => require(['../components/page/produceManage/producePlan/produceMaterialReturn.vue'], resolve),
            meta: {
                title: '领料/退料',
                permission: true
            }
        },
        {
            path: "/sellerOrder",
            component: resolve =>
                require([
                    "../components/page/seller/order/list.vue"
                ], resolve),
            meta: {
                title: "订单管理",
                permission: true
            }
        },
        {
            path: "/sellerOrderInfo",
            component: resolve =>
                require([
                    "../components/page/seller/order/info.vue"
                ], resolve),
            meta: {
                title: "订单资料",
                permission: true
            }
        },
        {
            path: "/sellerOrderSearch",
            component: resolve =>
                require([
                    "../components/page/seller/order/search.vue"
                ], resolve),
            meta: {
                title: "订单明细",
                permission: true
            }
        },
        {
            path: "/sellerOrderDelivery",
            component: resolve => require(["../components/page/seller/order/delivery.vue"], resolve),
            meta: {
                title: "订单发货",
                permission: true
            }
        },
        {
            path: "/procedureEdit",
            component: resolve =>
                require(["../components/page/product/procedure/procedureedit.vue"], resolve),
            meta: {
                title: "修改设备程序",
                permission: true
            }
        },
        {
            path: "/procedureadd",
            component: resolve =>
                require(["../components/page/product/procedure/procedureadd.vue"], resolve),
            meta: {
                title: "添加设备程序",
                permission: true
            }
        },
        {
            path: "/materielDetail",
            component: resolve =>
                require(["../components/page/materiel/info.vue"], resolve),
            meta: {
                title: "物料明细",
                permission: true
            }
        },
        {
            path: "/productionIssue",
            component: resolve =>
                require([
                    "../components/page/repertory/productionIssue/list.vue"
                ], resolve),
            meta: {
                title: "待发料列表",
                permission: true
            }
        },
        {
            path: "/productionIssueInfo",
            component: resolve =>
                require([
                    "../components/page/repertory/productionIssue/info.vue"
                ], resolve),
            meta: {
                title: "生产发料",
                permission: true
            }
        },
        {
            path: "/productionIssuePrint",
            component: resolve =>
                require([
                    "../components/page/repertory/productionIssue/print.vue"
                ], resolve),
            meta: {
                title: "生产发料打印",
                permission: true
            }
        }, {
            path: "/deployList",
            component: resolve =>
                require([
                    "../components/page/activiti/manager/deployList.vue"
                ], resolve),
            meta: { title: "流程部署", permission: true }
        },
        {
            path: "/modelList",
            component: resolve =>
                require([
                    "../components/page/activiti/manager/modelList.vue"
                ], resolve),
            meta: { title: "流程在线设计", permission: true }
        },
        {
            path: "/taskList",
            component: resolve =>
                require([
                    "../components/page/activiti/task/list.vue"
                ], resolve),
            meta: { title: "当前任务列表", permission: true }
        },
        {
            path: "/taskHistoryList",
            component: resolve =>
                require([
                    "../components/page/activiti/task/historyList.vue"
                ], resolve),
            meta: { title: "历史任务列表", permission: true }
        },
        {
            path: "/essantialName",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialName.vue"
                ], resolve),
            meta: { title: "基础数据修改名称", permission: true }
        },
        {
            path: "/essantialType",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialType.vue"
                ], resolve),
            meta: { title: "基础数据修改类型", permission: true }
        },
        {
            path: "/essantialType",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialType.vue"
                ], resolve),
            meta: { title: "物料类型", permission: true }
        },
        {
            path: "/essantialName",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialName.vue"
                ], resolve),
            meta: { title: "物料名称", permission: true }
        },
        {
            path: "/essantialUnit",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialUnit.vue"
                ], resolve),
            meta: { title: "物料单位", permission: true }
        },
        {
            path: "/essantialOriginal",
            component: resolve =>
                require([
                    "../components/page/product/essantialData/essantialOriginal.vue"
                ], resolve),
            meta: { title: "物料材料", permission: true }
        },
        ]
    },
    {
        path: "/login",
        component: resolve => require(["../components/page/Login.vue"], resolve)
    },
    {
        path: "/404",
        component: resolve => require(["../components/page/404.vue"], resolve)
    },
    {
        path: "/flow",
        meta: {
            title: "流程管理"
        },
        component: resolve => require(["../components/page/activiti/manager/editor/modelEditor.vue"], resolve)
    }
    ]
});
