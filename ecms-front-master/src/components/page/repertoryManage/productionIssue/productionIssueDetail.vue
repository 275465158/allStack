<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="生产单号:">
                    <el-input v-model="search.produceCode"></el-input>
                </el-form-item>
                <el-form-item label="出库时间:">
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
                <el-form-item label="领料用途:">
                    <el-input v-model="search.useage"></el-input>
                </el-form-item>
                <el-form-item label="领料人:">
                    <el-input v-model="search.pickedBy"></el-input>
                </el-form-item>
                <!--<el-form-item label="操作人:">
                    <el-input v-model="search.preparedBy"></el-input>
                </el-form-item>-->
                <el-button round @click="searchLike" type="primary">搜索</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
        </div>
        <div class="handle-box">
            <span class="el-form-item__label">出库列表</span>
            <el-button round type="primary" @click="issueCheck">发料确认</el-button>
            <el-button round @click="issuePrint">发料单打印</el-button>
        </div>
        <el-table :data="dynamicMaterialParts" border style="width: 100%">
            <el-table-column prop="id" label="序号">
            </el-table-column>
            <el-table-column prop="materialBom.materialCode" label="物料编号" >
            </el-table-column>
            <el-table-column prop="materialBom.materialName" label="物料名称" >
            </el-table-column>
            <el-table-column prop="materialBom.originalMaterial" label="原图材料" >
            </el-table-column>
            <el-table-column label="参数列表">
                <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                    <template slot-scope="scope">
                        {{scope.row.materialBom.materialParameter[param.parameterName]}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="supplierName" label="供应商名称" >
            </el-table-column>
            <el-table-column prop="materielBatch" label="物料批次" >
            </el-table-column>
            <el-table-column prop="materialNum" label="数量" >
            </el-table-column>
            <el-table-column prop="materialUnit" label="单位" >
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
            </el-pagination>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="goBack">返 回</el-button>
        </span>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/productionIssue/list",
                pages: 1,
                search: {
                    produceCode:'',
                    dateParam:'',
                    useage:'',
                    pickedBy:'',
                    preparedBy:'',
                    status: 1,
                    pageNum: 1
                },
                searchParams:[],
                dynamicMaterialParts:[],
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
                repertoryId:''
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
                this.$http.post(this.url, this.search).then(res => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.pages;
                    }
                });
                this.$http.post("/material/paramNames").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.searchParams = res.data.data;
                    }
                });
                this.$http.post("/material/info", { materialId:1 }).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.form = res.data.data;
                        this.dynamicMaterialParts = res.data.data.materialParts;
                    }
                });
            },
            clearData() {
                this.search.produceCode = ''
                this.search.dateParam = ''
                this.search.useage = ''
                this.search.pickedBy = ''
                this.search.preparedBy = ''
            },
            issueCheck() {
            },
            issuePrint() {
            },
            goBack() {
                this.$router.push("/productionIssue");
            }
        }
    };
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
</style>
