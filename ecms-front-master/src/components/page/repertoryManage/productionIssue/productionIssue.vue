<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="生产单号:">
                    <el-input v-model="search.productionNumber"></el-input>
                </el-form-item>
                <el-form-item label="合同号:">
                    <el-input v-model="search.contractId"></el-input>
                </el-form-item>
                <el-form-item label="生产项次:">
                    <el-input v-model="search.productBatch"></el-input>
                </el-form-item>
                <el-form-item label="生产计划时间:">
                    <el-date-picker
                            v-model="search.dateParam"
                            type="daterange"
                            align="right"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="yyyy-MM-dd"
                            :picker-options="pickerOptions2">
                    </el-date-picker>
                </el-form-item>
                <el-button round @click="searchLike" type="primary">搜索</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
        </div>
        <div class="handle-box">
            <span class="el-form-item__label">生产发料列表</span>
            <!--<el-button round @click="productionIssuePrint">生产发料打印</el-button>-->
        </div>
        <el-table :data="tables" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="序号" sortable>
            </el-table-column>
            <el-table-column prop="productionNumber" label="生产单号" >
            </el-table-column>
            <el-table-column prop="productBatch" label="生产项次" >
            </el-table-column>
            <el-table-column prop="productName" label="生产成品名称">
            </el-table-column>
            <el-table-column prop="materielNum" label="生产数量">
            </el-table-column>
            <el-table-column prop="contractId" label="合同号">
            </el-table-column>
            <el-table-column prop="planProductDate" label="计划生产时间">
            </el-table-column>
            <el-table-column prop="materielStatus" label="物料状态" :formatter = "statusFormat">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="small" type="primary" @click="productionIssue(scope.row)">生产发料</el-button>
                    <!--<el-button size="small" @click="openDetail(scope.$index, scope.row)">明细</el-button>-->
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination">
            <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/productionIssue/list",
                productionPlanUrl:"/productionPlan/list",
                pages: 1,
                search: {
                    contractId:'',
                    productionNumber:'',
                    productBatch:'',
                    dateParam:'',
                    status: 1,
                    pageNum: 1
                },
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                form: {
                    deliverList:[]
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
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.repertoryId = this.$route.query.repertoryId
                this.$http.post(this.productionPlanUrl, this.search).then(res => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.pages;
                    }
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.form.deliverList = this.multipleSelection
            },
            clearData() {
                this.search.contractId = ''
                this.search.produceCode = ''
                this.search.produceBatch = ''
                this.search.dateParam = ''
            },
            productionIssuePrint() {
            },
            productionIssue() {
                this.$router.push({
                    path: "/productionIssueDetail",
                    query: {
                        repertoryId: 1
                    }
                });
            },
            openDetail() {
            },
            statusFormat(row,column) {
                if (row.materielStatus === 1) {
                    return '备齐'
                } else if (row.materielStatus === 2) {
                    return '缺失'
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
