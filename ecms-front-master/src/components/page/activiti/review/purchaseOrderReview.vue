<template>
    <div class="container">
        <div clas="handle-box">
            <el-form label-width="100px" v-model="orderForm">
                <el-row :gutter="30">
                    <el-col :span="6">
                        <el-form-item label="采购单号">
                            <el-input v-model="orderForm.purchaseCode" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购人">
                            <el-input v-model="orderForm.purchasePerson" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购原因">
                            <el-input v-model="orderForm.purchaseReason" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购总价">
                            <el-input v-model="orderForm.purchasePriceTotal" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购时间">
                            <el-date-picker
                                v-model="orderForm.purchaseDate"
                                value-format="yyyy-MM-dd"
                                disabled
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="供应商">
                            <el-input v-model="orderForm.supplierName" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <hr class="marginTop">
            <span class="text">物料明细</span>
            <hr class="marginBottom">
            <el-button style="margin-bottom: 10px" @click="goPrint">打印</el-button>
            <el-table :data="materialTable" border style="width:100%">
                <el-table-column prop="id" label="序号">
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号"></el-table-column>
                <el-table-column prop="materialName" label="物料名称"></el-table-column>
                <el-table-column prop="type" label="物料类型"></el-table-column>
                <el-table-column prop="originalMaterial" label="材料"></el-table-column>
                <el-table-column prop="materialBomParamValueStr" label="参数"></el-table-column>
                <el-table-column prop="drawingCode" label="图号"></el-table-column>
                <el-table-column prop="arriveDate" label="交货日期"></el-table-column>
                <el-table-column prop="actualDeliveryQty" label="实际到货数"></el-table-column>
                <el-table-column prop="purchaseQty" label="采购数量"></el-table-column>
                <el-table-column prop="purchaseReason" label="采购原因"></el-table-column>
                <el-table-column prop="settlementQty" label="结算数量"></el-table-column>
                <el-table-column prop="purchasePriceTotal" label="金额小计"></el-table-column>
                <el-table-column prop="verificatPerson" label="核销人"></el-table-column>
                <el-table-column prop="verificatDate" label="核销时间"></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="detail(scope.$index,scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <template v-if="reviewFormVisible==true">
                <hr class="marginBottom">
                <span class="text">评审意见</span>
                <el-form v-model="log">
                    <el-form-item label="当前评审意见">
                        <el-input type="textarea" v-model="log.taskComment"></el-input>
                    </el-form-item>
                </el-form>
                <el-button round type="primary" @click="approve">同意</el-button>
                <el-button round @click="reject">不同意</el-button>
            </template>
            <hr class="marginBottom">
            <span class="text">审批历史</span>
            <el-table :data="table" border style="width:100%">
                <el-table-column prop="author" label="评审人"></el-table-column>
                <el-table-column prop="createTime" label="评审时间"></el-table-column>
                <el-table-column prop="comment" label="评审意见"></el-table-column>
            </el-table>
            <hr class="marginTop">

            <el-button @click="abort">取消</el-button>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            viewType: "review",
            reviewFormVisible: true,
            orderForm: [],
            editVisible: false,
            search: {
                id: ""
            },
            materialTable: [],
            log: {
                comment: "",
                taskId: "",
                id: ""
            },
            taskLogs: []
        };
    },
    created() {
        this.search.id = this.$route.query.businessKey;
        this.getData();
    },
    computed: {
        table() {
            return this.taskLogs.filter(d => {
                return d;
            });
        }
    },
    methods: {
        goPrint() {
            let query = {
                orderForm: this.orderForm,
                materialTable: this.materialTable
            };
            this.$router.push({
                path: "/purchaseOrderPrint",
                query: { data: query }
            });
        },
        abort() {
            if (this.viewType == "review") {
                this.$router.push({
                    path: "/taskList",
                    query: { closeFlag: 1 }
                });
            } else if (this.viewType == "detail") {
                this.$router.push({
                    path: "/taskHistoryList",
                    query: { closeFlag: 1 }
                });
            }
        },
        handle_select(row) {},
        // 获取 easy-mock 的模拟数据
        getData() {
            if (this.$route.query.businessKey != undefined) {
                this.search.id = this.$route.query.businessKey;
            }
            if (this.$route.query.viewType != undefined) {
                this.viewType = this.$route.query.viewType;
            }

            if (this.viewType == "review") {
                this.$http
                    .post("/activiti/queryTaskLogs", {
                        taskId: this.$route.query.taskId
                    })
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.taskLogs = res.data.data;
                        }
                    });
                this.reviewFormVisible = true;
            } else if (this.viewType == "detail") {
                this.$http
                    .post("/activiti/queryTaskHistoryLogs", {
                        taskId: this.$route.query.taskId
                    })
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.taskLogs = res.data.data;
                        }
                    });
                this.reviewFormVisible = false;
            }
            this.$http
                .post("/purchaseOrders/searchById", { id: this.search.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.orderForm = res.data.data;
                    }
                });
            this.$http
                .post("/purchaseOrdersList/searchByPurchaseOrdersId", {
                    id: this.search.id
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materialTable = res.data.data;
                    }
                });
        },
        detail(index, row) {
            this.$router.push({
                path: "/purchaseOrderListInfo",
                query: { id: row.id }
            });
        },
        approve() {
            this.log.taskId = this.$route.query.taskId;
            this.log.id = this.$route.query.businessKey;
            this.$http
                .post("/purchaseOrders/completeTask", this.log)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.abort();
                    }
                });
        },
        reject() {
            this.log.taskId = this.$route.query.taskId;
            this.log.id = this.$route.query.businessKey;
            this.$http
                .post("/purchaseOrders/rejectTask", this.log)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.abort();
                    }
                });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/PurchaseOrderReview") {
                this.getData();
                this.log = { comment: "", taskId: "", id: "" };
            }
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 50px;
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
hr {
    border-top: 1px;
}
.marginTop {
    margin-top: 10px;
    margin-bottom: 5px;
}
.marginBottom {
    margin-top: 5px;
    margin-bottom: 10px;
}
.text {
    font-size: 12px;
    color: #606266;
    margin-right: 30px;
}
</style>
