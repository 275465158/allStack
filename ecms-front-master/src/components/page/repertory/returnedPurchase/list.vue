<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materialRepertoryList">库存总表</el-menu-item>
                    <el-menu-item index="/repertoryStayDelivery">待收货</el-menu-item>
                    <el-menu-item index="/repertoryStayInbound">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/inboundLogList">入库明细</el-menu-item>
                    <el-menu-item index="/outboundLogList">出库明细</el-menu-item>
                    <!--<el-menu-item index="/sendMaterialLogList">发货记录</el-menu-item>-->
                    <el-menu-item index="/productionIssue">生产发料</el-menu-item>
                    <el-menu-item index="/sendDeliveryList">发货</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form :inline="true" :model="form" class="demo-form-inline">
                    <el-form-item label="采购单号：">
                        <el-input v-model="form.purchaseCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="供应商名称：">
                        <el-input v-model="form.supplierName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="退货数量：">
                        <el-input v-model="form.returnedPurchaseQty"></el-input>
                    </el-form-item>
                    <el-form-item label="退货原因：">
                        <el-input v-model="form.returnedPurchaseReason"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">退货明细</span>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="numberType" label="物料类型">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="originalMaterial" label="材料">
                </el-table-column>
                <el-table-column prop="sendQty" label="规格">
                </el-table-column>
                <el-table-column prop="materialBatch" label="物料批次">
                </el-table-column>
                <el-table-column prop="returnedPurchaseQty" label="退货数量">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.returnedPurchaseQty" type="number" style="width: 100px"
                                  :max="scope.row.qty" :min="0" onkeypress='return(/[\d]/.test(String.fromCharCode(event.keyCode)))'
                                  v-on:input="change(scope.row,scope.$index)">
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="sendDate" label="实际退货数量">
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertInbound">退 料</el-button>
                <el-button @click="goBack">关 闭</el-button>
              </span>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            tableData: [],
            url: "/materialSendLog/like",
            pages: 1,
            form: {
                // returnedPurchaseNumber: '',
                purchaseCode:'',
                // returnedPurchasePerson:'',
                // returnedPurchaseTime:20,
                returnedPurchaseQty:0,
                returnedPurchaseReason:'',
                supplierName:''
            },
            repertoryName:'',
        };
    },
    created() {
        this.form.purchaseCode = this.$route.query.purchaseCode
        this.form.supplierName = this.$route.query.supplierName
        this.tableData.push("materialCode",materialCode)
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                return d;
            });
        }
    },
    methods: {
        handleSelect(key, keyPath) {
            this.$router.push({
                path: key,
                query: { repertoryId: this.search.repertoryId }
            });
        },
        handleChange(value) {
        },
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        getData() {
            this.$http.post(this.url, this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
        },
        goBack() {
            this.$router.push("/repertoryStayInbound");
        },
        change(row,index){
            console.log("row:",row)
        }
    },
    watch: {
        '$route' (to, from) {
                if (to.path == '/returnedPurchaseList') {
                    this.getData();
                }
            }
    }
};
</script>
<style scoped>
.handle-box {
  margin-bottom: 20px;
}
</style>
