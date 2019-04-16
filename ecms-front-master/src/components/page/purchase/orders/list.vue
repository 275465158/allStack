<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect"
                >
                    <el-menu-item index="/purchaseQueueList">待采购</el-menu-item>
                    <el-menu-item index="/purchaseOrderVerificatList">采购单核销</el-menu-item>
                    <el-menu-item index="/purchaseOrderList">采购记录</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form
                    @keyup.enter.native="searchLike"
                    :inline="true"
                    :model="search"
                    class="demo-form-inline"
                >
                    <el-form-item label="采购单号">
                        <el-input v-model="search.purchaseCode"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商">
                        <el-input v-model="search.supplierName"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike">查询</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">采购订单</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column align="center" prop="id" label="序号">
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column align="center" prop="purchaseCode" label="采购单号"></el-table-column>
                <el-table-column align="center" prop="supplierName" label="供应商"></el-table-column>
                <el-table-column align="center" prop="purchaseDate" label="采购时间"></el-table-column>
                <el-table-column align="center" prop="purchasePerson" label="采购人"></el-table-column>
                <!--<el-table-column align="center" prop="purchaseReason" label="采购原因"></el-table-column>-->
                <!--<el-table-column align="center" prop="verificatPerson" label="核销人"></el-table-column>-->
                <el-table-column align="center" prop="verificatStatus" label="核销状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.verificatStatus === 0">待核销</div>
                        <div v-if="scope.row.verificatStatus === 1">已核销</div>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="reviewStatus"
                    label="审核状态"
                    :formatter="auditingStatus"
                    v-if="workflowSuspended==false"
                ></el-table-column>
                <el-table-column align="center" prop="purchasePriceTotal" label="采购总价"></el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="detail(scope.$index,scope.row)">明细</el-button>
                        <el-button
                            type="text"
                            @click="task(scope.$index,scope.row)"
                            v-if="workflowSuspended==false && scope.row.reviewStatus==2"
                        >启动评审</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleCurrentChange"
                    layout="prev, pager, next"
                    :total="pages"
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            tableData: [],
            pages: 1,
            editVisible: false,
            form: {
                name: "",
                remark: ""
            },
            verificatStatus: "",
            search: {
                purchaseCode: "",
                materialCode: "",
                supplierName: "",
                status: 1,
                pageNum: 1,
                pageSize: 20
            },
            noticeEntryVisible: false,
            workflowSuspended: false
        };
    },
    created() {
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                d.arrived = d.arrived == true ? "到货" : "未到货";
                return d;
            });
        }
    },
    methods: {
        auditingStatus(row, column) {
            if (row.reviewStatus == 0) {
                return "审核通过";
            } else if (row.reviewStatus == 1) {
                return "审核中";
            } else if (row.reviewStatus == 2) {
                return "待审核";
            } else if (row.reviewStatus == 3) {
                return "审核不通过";
            } else if (row.reviewStatus == 4) {
                return "停用";
            } else if (row.reviewStatus == 5) {
                return "待启动";
            } else if (
                row.reviewStatus == "" ||
                row.reviewStatus == undefined
            ) {
                return "待审核";
            }
        },
        handleSelect(key, keyPath) {
            this.$router.push({
                path: key
            });
        }, // 获取 easy-mock 的模拟数据
        searchLike() {
            this.search.pageNum = 1;
            this.getData();
        },
        getData() {
            let _this = this;
            _this.loading = true;
            this.$http
                .post("/purchaseOrders/purchaseOrdersPager", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false;
                })
                .catch(err => {
                    _this.loading = false;
                });
            this.$http
                .post("/bpm/models/getStatus", {
                    workflowKey: "PurchaseOrderReview"
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data.isSuspended == null) {
                            this.workflowSuspended = true;
                        } else {
                            this.workflowSuspended = res.data.data.isSuspended;
                        }
                    }
                });
        },
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        detail(index, row) {
            let rows = JSON.stringify(row);
            this.$router.push({
                path: "/purchaseOrderInfo",
                query: { row: rows }
            });
        },
        turnback(index, row) {
            this.$http
                .post("/purchaseOrdersStayDetail/turnBack", { orderId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        //通知到货
        noticeArrived(row) {
            this.$http
                .post("/purchase/order/arrived", { id: row.id, arrived: true })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        //确认下单
        approved(row) {
            this.$http
                .post("/purchase/order/approved", {
                    id: row.id,
                    approved: true
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
            this.$http
                .post("/materiel/entry/insertByPurchaseOrder", { id: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        task(index, row) {
            this.$http
                .post("/purchaseOrders/startTask", { id: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        }
    },
    watch: {
        $route: "getData"
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.del-dialog-cnt {
    font-size: 16px;
    text-align: center;
}
</style>
