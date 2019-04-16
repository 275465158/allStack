<template>
    <div class="sidebar">
        <el-menu
            ref="sidebar"
            style="float: left;"
            :style="{width: sidebarWidth + 'px'}"
            :default-active="onRoutes"
            :collapse="false"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in roleItems">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <template v-if="subItem.subs">
                                <el-submenu :index="subItem.index" :key="subItem.index">
                                    <template slot="title">
                                        <i :class="subItem.icon"></i>
                                        <span slot="title">{{ subItem.title }}</span>
                                    </template>
                                    <el-menu-item
                                        v-for="(ssi,i) in subItem.subs"
                                        :key="i"
                                        :index="ssi.index"
                                    >
                                        <i :class="ssi.icon"></i>
                                        <span slot="title">{{ ssi.title }}</span>
                                    </el-menu-item>
                                </el-submenu>
                            </template>
                            <template v-else>
                                <el-menu-item :index="subItem.index" :key="subItem.index">
                                    <i :class="item.icon"></i>
                                    <span slot="title">{{ subItem.title }}</span>
                                </el-menu-item>
                            </template>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
        <!--<div class="handle-box" :style="{transform: 'translateX(' + translateX + 'px)'}">-->
        <!--<el-menu :default-active="$route.path" class="el-menu-demo" @select="handleSelect">-->
        <!--<el-menu-item index="/materialRepertoryList">库存总表</el-menu-item>-->
        <!--<el-menu-item index="/repertoryStayDelivery">待收货</el-menu-item>-->
        <!--<el-menu-item index="/repertoryStayInbound">待入库</el-menu-item>-->
        <!--<el-menu-item index="/storageList">货架管理</el-menu-item>-->
        <!--<el-menu-item index="/inboundLogList">入库明细</el-menu-item>-->
        <!--<el-menu-item index="/outboundLogList">出库明细</el-menu-item>-->
        <!--&lt;!&ndash;<el-menu-item index="/sendMaterialLogList">发货记录</el-menu-item>&ndash;&gt;-->
        <!--<el-menu-item index="/productionIssue">生产发料</el-menu-item>-->
        <!--<el-menu-item index="/sendDeliveryList">发货</el-menu-item>-->
        <!--</el-menu>-->
        <!--</div>-->
    </div>
</template>

<script>
import bus from "../common/bus";

export default {
    data() {
        return {
            sid: "",
            sname: "",
            collapse: false,
            sidebarWidth: 250,
            translateX: 0,
            items: [
                {
                    icon: "el-icon-setting",
                    index: "dashboard",
                    title: "系统首页"
                },
                {
                    icon: "el-icon-setting",
                    index: "1",
                    title: "人事管理",
                    priority: 1,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "department",
                            title: "部门管理"
                        },
                        {
                            icon: "el-icon-view",
                            index: "post",
                            title: "岗位管理"
                        },
                        {
                            icon: "el-icon-view",
                            index: "employeeList",
                            title: "人事资料"
                        }
                        // {
                        //     icon: "el-icon-view",
                        //     index: "workflow",
                        //     title: "个人事务",
                        //     subs: [
                        //         // {
                        //         //     icon: "el-icon-view",
                        //         //     index: "taskList",
                        //         //     title: "当前任务"
                        //         // }
                        //     ]
                        // }
                    ]
                },
                {
                    icon: "el-icon-view",
                    index: "workflow",
                    title: "个人事务",
                    priority: 0,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "taskList",
                            title: "当前任务"
                        },
                        {
                            icon: "el-icon-view",
                            index: "taskHistoryList",
                            title: "历史任务"
                        }
                    ]
                },
                {
                    icon: "el-icon-setting",
                    index: "2",
                    title: "销售管理",
                    priority: 2,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "2-1",
                            title: "订单参数",
                            subs: [
                                {
                                    icon: "el-icon-view",
                                    index: "sellerPayType",
                                    title: "付款方式"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "sellerPayRule",
                                    title: "付款规则"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "sellerInvoice",
                                    title: "发票类型"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "sellerDelivery",
                                    title: "送货方式"
                                }
                            ]
                        },
                        {
                            icon: "el-icon-view",
                            index: "sellerCustomer",
                            title: "客户管理"
                        },
                        /*{
                                icon: "el-icon-view",
                                index: "listoffer",
                                title: "报价系统"
                            },*/
                        {
                            icon: "el-icon-view",
                            index: "sellerOrder",
                            title: "订单管理"
                        },
                        {
                            icon: "el-icon-view",
                            index: "tradeMark",
                            title: "商标管理"
                        }
                    ]
                },
                /*{
                        icon: "el-icon-setting",
                        index: "10",
                        title: "客户平台",
                        priority: 512,
                        subs: [
                            {
                                icon: "el-icon-view",
                                index: "queryprice",
                                title: "询价系统"
                            },
                            {
                                icon: "el-icon-view",
                                index: "deliveryAddress",
                                title: "收货地址"
                            },
                        ]
                    },*/

                {
                    icon: "el-icon-setting",
                    index: "3",
                    title: "产品管理",
                    priority: 4,
                    subs: [
                        {
                            icon: "el-icon-setting",
                            index: "producttype",
                            title: "物料类型"
                        },
                        // {
                        //   icon: "el-icon-setting",
                        //   index: "productcustom",
                        //   title: "产品定制"
                        // },
                        {
                            icon: "el-icon-view",
                            index: "checkstandard",
                            title: "验收标准"
                        },
                        /*{
                                icon: "el-icon-view",
                                index: "structList",
                                title: "产品结构"
                            },
*/
                        {
                            icon: "el-icon-view",
                            index: "procedureList",
                            title: "设备程序"
                        },

                        // {
                        //   icon: "el-icon-view",
                        //   index: "bomTasksList",
                        //   title: "BOM制作"
                        // },
                        {
                            icon: "el-icon-view",
                            index: "toolTypeList",
                            title: "辅助工具"
                        },
                        {
                            icon: "el-icon-view",
                            index: "processList",
                            title: "工艺设置"
                        },
                        {
                            icon: "el-icon-view",
                            index: "productDrawing",
                            title: "图纸列表"
                        },
                        {
                            icon: "el-icon-view",
                            index: "productJobGuide",
                            title: "作业指导书"
                        },
                        {
                            icon: "el-icon-view",
                            index: "materialList",
                            title: "物料列表"
                        },
                        {
                            icon: "el-icon-view",
                            index: "orderBom",
                            title: "订单BOM"
                        }
                        // {
                        //     icon: "el-icon-view",
                        //     index: 'essantialType',
                        //     title: '基础数据'
                        // }
                        // {
                        //     icon: "el-icon-view",
                        //         index: "productList",
                        //     title: "产品列表"
                        // }
                    ]
                },
                {
                    icon: "el-icon-view",
                    index: "4",
                    title: "仓库管理",
                    priority: 8,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "repertoryList",
                            title: "仓库列表"
                        },
                        {
                            icon: "el-icon-view",
                            index: "materielTableList",
                            title: "物料列表"
                        }
                    ]
                },
                {
                    icon: "el-icon-view",
                    index: "5",
                    title: "采购管理",
                    priority: 16,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "5-1",
                            title: "供应商参数",
                            subs: [
                                {
                                    icon: "el-icon-view",
                                    index: "paymentTypeList",
                                    title: "付款方式"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "paymentPeriodList",
                                    title: "付款周期"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "invoiceTypeList",
                                    title: "发票类型"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "shippingMethodList",
                                    title: "送货方式"
                                }
                            ]
                        },
                        {
                            icon: "el-icon-view",
                            index: "supplierList",
                            title: "供应商管理"
                        },
                        {
                            icon: "el-icon-view",
                            index: "purchaseQueueList",
                            title: "采购订单"
                        },
                        {
                            icon: "el-icon-view",
                            index: "purchaseMaterialList",
                            title: "物料列表"
                        }
                    ]
                },
                {
                    icon: "el-icon-date",
                    index: "6",
                    title: "品质管理",
                    priority: 32,
                    subs: [
                        {
                            icon: "el-icon-setting",
                            index: "incomingInspectionTest",
                            title: "来料检测",
                            subs: [
                                {
                                    icon: "el-icon-view",
                                    index: "incomingInspection",
                                    title: "来料检测"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "incomingInspectionLog",
                                    title: "来料检测记录"
                                }
                            ]
                        },
                        {
                            icon: "el-icon-setting",
                            index: "productionTest",
                            title: "生产检测",
                            subs: [
                                {
                                    icon: "el-icon-view",
                                    index: "production",
                                    title: "生产检测"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "productionRecord",
                                    title: "生产检测记录"
                                }
                            ]
                        },
                        {
                            icon: "el-icon-setting",
                            index: "unqualifiedReport",
                            title: "不合格品处理",
                            subs: [
                                {
                                    icon: "el-icon-view",
                                    index: "unqualifiedProductReport",
                                    title: "生产不合格品处理"
                                },
                                {
                                    icon: "el-icon-view",
                                    index: "incomingUnqualifiedReport",
                                    title: "来料不合格品处理"
                                }
                            ]
                        }
                        // {
                        //     icon: "el-icon-view",
                        //     index: "unqualifiedType",
                        //     title: "不合格类型"
                        // }
                    ]
                },
                {
                    icon: "el-icon-date",
                    index: "7",
                    title: "设备管理",
                    priority: 64,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "equipmentList",
                            title: "设备列表"
                        }
                    ]
                },
                {
                    icon: "el-icon-date",
                    index: "8",
                    title: "生产管理",
                    priority: 128,
                    subs: [
                        {
                            icon: "el-icon-view",
                            index: "8-1",
                            title: "生产参数",
                            subs: [
                                {
                                    icon: "el-icon-date",
                                    index: "produceSchedule",
                                    title: "生产排班"
                                },
                                {
                                    icon: "el-icon-date",
                                    index: "postManage",
                                    title: "岗位管理"
                                }
                            ]
                        },
                        {
                            icon: "el-icon-view",
                            index: "producePlan",
                            title: "生产计划"
                        },
                        {
                            icon: "el-icon-view",
                            index: "produceLog",
                            title: "生产记录"
                        }
                    ]
                },
                {
                    icon: "el-icon-setting",
                    index: "9",
                    title: "系统管理",
                    priority: 256,
                    subs: [
                        {
                            icon: "el-icon-setting",
                            index: "company",
                            title: "系统设置"
                        },
                        {
                            icon: "el-icon-view",
                            index: "userList",
                            title: "账号管理"
                        },
                        {
                            icon: "el-icon-setting",
                            index: "activiti",
                            title: "流程管理",
                            subs: [
                                // {
                                //     icon: "el-icon-setting",
                                //     index: "deployList",
                                //     title: "流程部署定义"
                                // },
                                {
                                    icon: "el-icon-setting",
                                    index: "modelList",
                                    title: "流程在线设计"
                                }
                            ]
                        }
                    ]
                }
            ]
        };
    },

    computed: {
        onRoutes() {
            return this.$route.path.replace("/", "");
        },
        roleItems() {
            let role = localStorage.getItem("ms_role");
            if (role == undefined || !role) {
                this.$router.push("/login");
            }
            let newItems = new Array();
            newItems.push(this.items[0]);
            console.log(this.items);
            for (let i = 1; i <= this.items.length - 1; i++) {
                let hasRole = role & this.items[i].priority;
                if (hasRole > 0 || this.items[i].priority == 0) {
                    newItems.push(this.items[i]);
                }
            }
            return newItems;
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        // bus.$on('id', (msg) => {
        //     // console.log(msg);
        //     this.sid = msg
        // })
        // bus.$on('name', (msg) => {
        //     // console.log(msg);
        //     this.sname = msg
        // })
        bus.$on("collapse", msg => {
            this.collapse = msg;
            console.log(this.collapse);
        });
    },
    mounted() {
        // let _this = this
        // if (this.$route.path == '/materialRepertoryList') {
        //     let times = setInterval(function () {
        //         _this.translateX -= 8
        //         if (_this.translateX == -200) {
        //             clearInterval(times)
        //         }
        //     }, 1)
        // }
        // console.log(this.$refs.sidebar.width);
    },
    methods: {
        handleSelect(key, keyPath) {
            this.$router.push({
                path: key,
                query: { repertoryId: this.sid, repertoryName: this.sname }
            });
        }
    },
    watch: {
        $route(to, from) {
            // if (to.path == '/materialRepertoryList') {
            //     let _this = this
            //     if (_this.translateX == -200) {
            //     } else {
            //         let times = setInterval(function () {
            //             _this.translateX -= 8
            //             if (_this.translateX == -200) {
            //                 clearInterval(times)
            //             }
            //         }, 1)
            //     }
            // } else if (to.path == '/repertoryStayDelivery' || to.path == '/repertoryStayInbound' || to.path == '/storageList' || to.path == '/inboundLogList' || to.path == '/outboundLogList' || to.path == '/productionIssue' || to.path == '/sendDeliveryList') {
            //
            // } else {
            //     this.translateX = 0
            // }
        }
    }
};
</script>

<style scoped>
.sidebar {
    display: flex;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}

.sidebar > ul {
    height: 100%;
}
.handle-box {
    background-color: #fff;
    height: 100%;
    width: 200px;
    transform: translateX(-200px);
}
</style>