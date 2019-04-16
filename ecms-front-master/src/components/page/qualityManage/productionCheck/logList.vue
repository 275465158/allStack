<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="getData" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input v-model="search.productionNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="生产项次:">
                        <el-input v-model="search.productionBatch"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编码:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-button round type="primary" @click="getData">搜索</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>

            </div>
            <div class="handle-box">
                <span class="el-form-item__label">检测记录</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column align="center" prop="checkNumber" label="检测单号">
                </el-table-column>
                <el-table-column align="center" prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="productionBatch" label="生产项次">
                </el-table-column>
                <el-table-column align="center" prop="materialNum" label="数量">
                </el-table-column>
                <el-table-column align="center" prop="qualifiedNum" label="合格数">
                </el-table-column>
                <el-table-column align="center" prop="unQualifiedNum" label="不合格数">
                </el-table-column>
                <el-table-column align="center" prop="qualifiedPercent" label="合格率">
                </el-table-column>
                <el-table-column align="center" prop="checkPersonName" label="检测人">
                </el-table-column>
                <el-table-column align="center" prop="checkDate" label="检测日期">
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
                url: "/productionCheckLog/like",
                editVisible: false,
                pages: 1,
                loading: false,
                search: {
                    productionNumber: "",
                    productionBatch: "",
                    materialCode: "",
                    pageNum: 1,
                    pageSize:20
                }
            };
        },
        created() {
            this.getData();
        },
        computed: {
            tables() {
                return this.tableData.filter(d => {
                    return d;
                });
            }
        },
        methods: {
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
                    if (res.data.code == 1000) {
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
                this.search.productionNumber = "",
                this.search.productionBatch = "",
                this.search.materialCode = ""
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            detail(index, row) {
                this.$router.push({
                    path: "/productionLogInfo",
                    query: { row: row }
                });
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/productionRecord') {
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
