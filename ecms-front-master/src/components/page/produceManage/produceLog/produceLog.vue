<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input v-model="search.productionNumber"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="生产类型:">
                        <el-input v-model="search.productionType"></el-input>
                    </el-form-item>-->
                    <el-form-item label="产品名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="生产项次:">
                        <el-input v-model="search.productBatch"></el-input>
                    </el-form-item>
                    <el-form-item label="计划生产日期:">
                        <el-col :span="11">
                            <el-date-picker type="date" placeholder="选择日期" v-model="search.startDate" style="width: 150px"></el-date-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="至:">
                        <el-col :span="11">
                            <el-date-picker type="date" placeholder="选择日期" v-model="search.endDate" style="width: 150px"></el-date-picker>
                        </el-col>
                    </el-form-item>-->
                    <el-button round @click="searchLike" type="primary">搜索</el-button>
                    <el-button round @click="clearFrom">清空</el-button>
                </el-form>

            </div>
            <div class="handle-box">
                <span class="el-form-item__label">生产记录列表</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{20*(indexPageNum-1)+scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column align="center" prop="productBatch" label="生产项次">
                </el-table-column>
                <!--<el-table-column align="center" prop="planProductDate" label="计划生产日期">
                </el-table-column>
                <el-table-column align="center" prop="productTime" label="生产日期">
                </el-table-column>-->
                <el-table-column align="center" prop="produceDate" label="完成生产日期">
                </el-table-column>
                <el-table-column align="center" prop="completeQty" label="完成数量">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">详情</el-button>
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
        name: '',
        data() {
            return {
                loading: false,
                tableData: [],
                url: '/productionLogDetail/getProductionLog',
                editVisible: false,
                pages: 1,
                search: {
                    productionNumber: '',
                    materialName: '',
                    pageNum: 1,
                    pageSize:20
                },
                form: {
                    productionNumber: '',
                    productionType: '',
                    productName: '',
                    productionBatch: '',
                    latestProductionDate: '',
                    productionDate: '',
                    materialStatus: '',
                    totalNumber:''
                },
                indexPageNum:1
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
            // 分页导航
            handleCurrentChange(val) {
                this.indexPageNum = val;
                this.search.pageNum = val;
                this.getData();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
                    _this.loading = false
            })
                    .catch(err => {
                        _this.loading = false
                    })
            },
            clearFrom() {
                this.search.productionNumber = '',
                    // this.search.productionType = '',
                    this.search.materialName = ''
                    // this.search.productBatch = '',
                    // this.search.startDate = '',
                    // this.search.endDate = '',
                    // this.search.materialStatus = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index,row) {
                this.$router.push({
                    path: 'produceLogDetail',
                    name: 'produceLogDetail',
                    params: {
                        rowDataObj: row
                    }
                })
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/produceLog') {
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
