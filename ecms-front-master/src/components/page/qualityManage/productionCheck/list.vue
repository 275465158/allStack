<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="getData" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input v-model="search.productionNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="生产项次:">
                        <el-input v-model="search.productBatch"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-button round type="primary" @click="getData">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>

            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待检测列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column align="center" prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column align="center" prop="factoryMaterialCode" label="工厂物料编号">
                </el-table-column>
                <el-table-column align="center" prop="contractId" label="合同编号">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="processName" label="制程名称">
                </el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="参数">
                </el-table-column>
                <el-table-column align="center" prop="productBatch" label="生产项次">
                </el-table-column>
                <el-table-column align="center" prop="completeQty" label="数量">
                </el-table-column>
                <el-table-column align="center" prop="produceDate" label="完成时间">
                </el-table-column>
                <el-table-column align="center" prop="operatName" label="操作人">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">检测</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <!--<el-dialog title="检测" :visible.sync="editVisible" width="80%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-col :span="12">
                    <el-form-item label="生产检测单号:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="合同编号:">
                        <el-input v-model="form.productBatch" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="生产单号:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="生产项次:">
                        <el-input v-model="form.productBatch" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="工厂物料编码:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="生产时间:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="产品编号:">
                        <el-input v-model="form.materialCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="产品名称:">
                        <el-input v-model="form.materialName" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="材料:">
                        <el-input v-model="form.originalMaterial" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="参数:">
                        <el-input v-model="form.originalMaterial" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="数量:">
                        <el-input v-model="form.materialNum" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="检验比例（%）:">
                        <el-input v-model="form.unQualifiedNum" type="number" min="0" :max="form.materialNum" v-on:input="change(item)"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="检验数量:">
                        <el-input v-model="form.materialNum" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="合格数:">
                        <el-input v-model="form.qualifiedNum" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="不合格数:">
                        <el-input v-model="form.unQualifiedNum" type="number" min="0" :max="form.materialNum" v-on:input="change(item)"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="检测合格率:">
                        <el-input v-model="form.qualifiedPercent" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="检验员:">
                        <el-input v-model="form.qualifiedPercent" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="检验时间:">
                        <el-input v-model="form.qualifiedPercent" readonly></el-input>
                    </el-form-item>
                </el-col>




            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">保存</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>-->
    </div>
</template>

<script>

    export default {
        data() {
            return {
                tableData: [],
                url: '/productionLogDetail/getProductionLogDetail',
                checkUrl:'/productionLog/saveCheck',
                editVisible: false,
                pages: 1,
                loading: false,
                search: {
                    productionNumber: '',
                    productBatch: '',
                    materialCode:'',
                    pageNum: 1,
                    pageSize:20,
                    checkStatus:0
                },
                form: {
                    id: '',
                    productionNumber: '',
                    materialName: '',
                    materialCode:'',
                    materialId: '',
                    productType: '',
                    qualifiedPercent:'',
                    originalMaterial:'',
                    productBatch:'',
                    materialNum: '',
                    qualifiedNum : 0,
                    unQualifiedNum : 0
                },
                item : [],
            }
        },
        created() {
            this.getData();
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/production') {
                    this.getData();
                }
            }
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
                this.search.pageNum = val;
                this.getData();
            },
            // 获取待检测列表数据
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
            clearData() {
                this.search.productionNumber = '',
                this.search.productBatch = '',
                this.search.materialCode = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            //检测
            handleEdit(index, row) {
                this.$router.push({
                    path: "/productionCheck",
                    query: { row: row }
                });
                /*this.idx = index;
                const item = this.tableData[this.idx];
                var ind = index
                this.form = {
                    id : item.id,
                    materialNum : item.materialNum,
                    productionNumber : item.productionNumber,
                    materialName : item.materialName,
                    materialId : item.materialId,
                    materialCode : item.materialCode,
                    productType : item.productType,
                    originalMaterial : item.originalMaterial,
                    productBatch : item.productBatch,
                    qualifiedNum : item.qualifiedNum,
                    ind : ind
                }
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.form.tableData = res.data.data.list
                        var materialNum = parseInt(this.form.tableData[index].materialNum)
                        var qualifiedNum = parseInt(this.form.tableData[index].qualifiedNum)
                        this.form.materialNum = materialNum
                        this.form.qualifiedNum = materialNum
                        this.form.unqualifiedNum = 0
                    }
                })
                this.editVisible = true;*/
            },
            // 保存编辑
            saveEdit() {
                //this.form = this.form.tableData[0]
                var unQualifiedNum = this.form.unQualifiedNum
                var materialNum = this.form.materialNum
                if (parseInt(unQualifiedNum)>parseInt(materialNum)) {
                    this.$message.error("不合格数不能大于总数")
                }else {
                    this.$http.post(this.checkUrl, this.form).then((res) => {
                        if (res.data.code == 1000) {
                            this.editVisible = false
                            this.$message.success("检测完成")
                            this.getData()
                        }
                    })
                }
            },
            // 物料批次不合格数改变时触发事件，对应的合格数，总数，合格率数值发生联动
            change(value) {
                var materialNum = parseInt(this.form.tableData[this.form.ind].materialNum)
                var unQualifiedNum = parseInt(this.form.tableData[this.form.ind].unQualifiedNum)
                this.form.tableData[this.form.ind].qualifiedNum = materialNum - unQualifiedNum
                this.form.qualifiedNum = this.form.materialNum-this.form.unQualifiedNum
                var qualifiedPercent = this.form.qualifiedNum/materialNum*100+""
                if (parseInt(qualifiedPercent) == 100){
                    this.form.qualifiedPercent = '100%'
                } else {
                    this.form.qualifiedPercent = qualifiedPercent.substring(0,qualifiedPercent.indexOf('.')+3)+'%'
                }
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/production') {
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
