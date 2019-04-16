<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="收货单号:">
                        <el-input v-model="search.deliveryCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="采购原因:">
                        <el-input v-model="search.purchaseReason"></el-input>
                    </el-form-item>
                    <el-button round type="primary" @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待检测列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column align="center" prop="id" label="序号">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="deliveryCode" label="收货单号">
                </el-table-column>
                <el-table-column align="center" prop="supplierName" label="供应商名称">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column align="center" prop="paramValue" label="参数">
                </el-table-column>
                <el-table-column align="center" prop="purchaseReason" label="采购原因">
                </el-table-column>
                <el-table-column align="center" prop="deliveryDate" label="到货时间">
                </el-table-column>
                <el-table-column align="center" prop="inventoryQty" label="到货数量">
                </el-table-column>
                <!--<el-table-column prop="stayInboundQty" label="合格数量">
                </el-table-column>
                <el-table-column prop="unqualifiedQty" label="不合格数量">
                </el-table-column>-->
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">检验</el-button>
                    </template>
                </el-table-column>
            </el-table>
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
                url: "/materialRepertoryDelivery/getStayInboundPage",
                pages: 1,
                loading: false,
                search: {
                    deliveryCode:'',
                    materialName:'',
                    materialCode:'',
                    purchaseReason:'',
                    status: 1,
                    pageNum: 1,
                    pageSize:20
                },
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    return d;
            })
            }
        },
        methods: {
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取待检测列表数据
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
                    _this.loading = false
            })
                    .catch(err => {
                        _this.loading = false
                    });
            },
            clearData() {
                this.search.deliveryCode = '',
                    this.search.materialName = '',
                    this.search.materialCode = '',
                    this.search.purchaseReason = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            //检测
            handleEdit(index, row) {
                this.$router.push({
                    path: "/incomingCheck",
                    query: { row: row }
                });
            },
        },
        watch: {
            '$route' (to, inboundForm) {
                if (to.path == '/incomingInspection') {
                    this.getData();
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
