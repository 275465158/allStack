<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="采购单号:">
                        <el-input v-model="search.purchaseCode"></el-input>
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
                <span class="el-form-item__label">检测记录列表</span>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="purchaseCode" label="采购编号">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="materialBatch" label="批次号">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column align="center" prop="purchaseReason" label="采购原因">
                </el-table-column>
                <el-table-column align="center" prop="purchasePrice" label="采购单价">
                </el-table-column>
                <el-table-column align="center" prop="checkPersonName" label="检测人">
                </el-table-column>
                <el-table-column align="center" prop="checkDate" label="检测时间">
                </el-table-column>
                <el-table-column align="center" prop="qualifiedQty" label="合格数量">
                </el-table-column>
                <el-table-column align="center" prop="unqualifiedQty" label="不合格数量">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="detail(scope.$index, scope.row)">明细</el-button>
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
                url: "/materialRepertoryIncomingCheck/getCheckLogPager",
                pages: 1,
                search: {
                    purchaseCode:'',
                    materialName:'',
                    materialCode:'',
                    purchaseReason:'',
                    status: 1,
                    pageNum: 1,
                    pageSize:20,
                    materialSource:1
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
                this.search.purchaseCode = '',
                this.search.materialName = '',
                this.search.materialCode = '',
                this.search.purchaseReason = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            //详情
            detail(index, row) {
                this.$router.push({
                    path: "/incomingInspectionLogInfo",
                    query: { row: row }
                });
            },
        },
        watch: {
            '$route' (to, inboundForm) {
                if (to.path == '/incomingInspectionLog') {
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
