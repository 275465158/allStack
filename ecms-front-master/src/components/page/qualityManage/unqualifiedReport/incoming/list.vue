<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect">
                    <el-menu-item index="/unqualifiedProductReport">生产不合品处理</el-menu-item>
                    <el-menu-item index="/incomingUnqualifiedReport">来料不合格品处理</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box"></div>
            <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="不合格品单号">
                    <el-input v-model="search.unqualifiedReviewNo"></el-input>
                </el-form-item>
                <el-form-item label="物料名称">
                    <el-input v-model="search.materialName"></el-input>
                </el-form-item>
                <!--<el-form-item label="合同编号">
                    <el-input v-model="search.purchaseOrderNo"></el-input>
                </el-form-item>-->
                <el-form-item label="采购单号">
                    <el-input v-model="search.purchaseCode"></el-input>
                </el-form-item>
                <el-button round @click="searchLike">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <div class="handle-box">
                <!--<el-button round type="primary">新增处置记录</el-button>-->
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column label="序号" prop="id" sortable></el-table-column>
                <el-table-column label="不合格品记录单号" prop="unqualifiedReviewNo"></el-table-column>
                <el-table-column label="采购单号" prop="purchaseCode"></el-table-column>
                <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="材料" prop="originalMaterial"></el-table-column>
                <el-table-column label="不合格品数量" prop="unqualifiedQty"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" v-if="scope.row.editable!=1" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" v-if="scope.row.editable==1" @click="handleEdit(scope.$index, scope.row)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleCurrentChange"
                    layout="total,prev, pager, next"
                    :total="pages"
                ></el-pagination>
            </div>
        </div>
        <el-dialog @close="getData" title="不合格品详情" :visible.sync="editVisible" width="80%">
            <el-form ref="editForm" :rules="rules" :model="editForm" label-width="120px">
                <el-row>
                    <el-col :span="24">不合格品信息</el-col>
                </el-row>
                <el-row border>
                    <el-col :span="12">
                        <el-form-item label="不合格品单号">
                            <el-input v-model="editForm.unqualifiedReviewNo" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="采购人">
                            <el-input v-model="editForm.purchasePerson" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-col :span="12">
                    <el-form-item label="采购单号">
                        <el-input v-model="editForm.purchaseCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="采购原因">
                        <el-input v-model="editForm.purchaseReason" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="物料编码">
                            <el-input v-model="editForm.materialCode" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料名称">
                            <el-input v-model="editForm.materialName" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="材料">
                            <el-input v-model="editForm.originalMaterial" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="参数">
                            <el-input v-model="editForm.materialBomParamValueStr" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="供应商名称">
                            <el-input v-model="editForm.supplierName" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料批次">
                            <el-input v-model="editForm.materialRepertoryIncomingCheckInfo.materialBatch" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="数量">
                            <el-input v-model="editForm.materialRepertoryIncomingCheckInfo.inventoryQty" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="不合格数量">
                            <el-input v-model="editForm.unqualifiedQty" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="检验员">
                            <el-input v-model="editForm.checkPerson" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="检验时间">
                            <el-input v-model="editForm.materialRepertoryIncomingCheckInfo.checkDate" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="不合格品描述">
                            <el-input v-model="editForm.materialRepertoryIncomingCheckInfo.unqualifiedRemark" type="textarea" readonly></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="附件">
                            <el-input v-model="editForm.fileUrl" readonly></el-input>
                            <el-button type="text" v-if="editForm.fileUrl != ''" @click="downloadFile(editForm.fileUrl)">下载</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">不合格原因分析</el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="不合格原因分析">
                            <el-input v-model="editForm.unqualifiedReasonAnalysis" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="分析人">
                            <el-input v-model="editForm.analysisPerson"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="分析时间">
                            <el-date-picker v-model="editForm.analysedDate"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">纠正及处置措施</el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="纠正及处置措施">
                            <el-radio-group
                                v-model="editForm.disposalHandleId"
                                @change="measureHandle"
                            >
                                <el-radio
                                    v-for="item in disposalMeasure"
                                    :label="item.id"
                                    :key="item.id"
                                >{{item.name}}</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="materialChange">
                    <el-col :span="24">
                        <el-form-item label="选择替换的产品">
                            <el-button @click="changeHandle">选择</el-button>
                            <label>物料名称</label>
                            {{substitute.materialName}}
                            <label>物料编号</label>
                            {{substitute.materialCode}}
                            <label>材料</label>
                            {{substitute.originalMaterial}}
                            <label>参数</label>
                            {{substitute.materialBomParamValueStr}}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="纠正及处理措施依据">
                            <el-input v-model="editForm.disposalCauses" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="处置人">
                            <el-input v-model="editForm.disposalHandler"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="处置时间">
                            <el-date-picker v-model="editForm.disposalDate"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-button v-if="editForm.editable!=1" @click="update('editForm')">保存</el-button>
                <el-button v-if="editForm.editable!=1" @click="execute('editForm')">执行处理</el-button>
                <el-button @click="goback">取消</el-button>
            </el-form>
        </el-dialog>
        <el-dialog title="选择替换产品" :visible.sync="selectVisible" width="50%">
            <el-form ref="seachProduction" :inline="true" :model="searchProduction">
                <el-form-item label="物料编号">
                    <el-input v-model="searchProduction.materialCode"></el-input>
                </el-form-item>
                <el-form-item label="物料名称">
                    <el-input v-model="searchProduction.materialName"></el-input>
                </el-form-item>
                <el-form-item label="材料">
                    <el-input v-model="searchProduction.originalMaterial"></el-input>
                </el-form-item>
                <el-form-item label="参数">
                    <el-input v-model="searchProduction.materialBomParamValueStr"></el-input>
                </el-form-item>
                <el-button size="small" @click="searchLikeMaterial">查询</el-button>
            </el-form>
            <el-table border :data="productList">
                <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="材料" prop="originalMaterial"></el-table-column>
                <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                <el-table-column label="操作" prop="id">
                    <template slot-scope="scope">
                        <el-button @click="selectMaterial(scope.row)">选择此项</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="5" @current-change="handleCurrentChangeMaterial" layout="total,prev, pager, next" :total="materialPages"></el-pagination>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import fileDownload from 'js-file-download';
    import * as utils from '../../../../../../static/js/utils.js';
export default {
    data() {
        return {
            loading: false,
            editVisible: false,
            selectVisible: false,
            url: "/incomingUnqualified/searchPager",
            search: {
                unqualifiedReviewNo: "",
                materialName: "",
                purchaseOrderNo: "",
                purchaseCode: "",
                pageNum: 1,
                pageSize: 20
            },
            searchProduction: {
                materialName: "",
                materialCode: "",
                originalMaterial: "",
                materialBomParamValueStr: "",
                pageNum: 1,
                pageSize: 5
            },
            productList: [],
            rules: {},
            editForm: {
                materialRepertoryIncomingCheckInfo:'',
            },
            saveForm:{
                id:'',
                unqualifiedReviewNo:'',
                unqualifiedDescribe:'',
                unqualifiedQty:'',
                checkId:'',
                fileUrl:'',
                unqualifiedReasonAnalysis:'',
                analysisPerson:'',
                disposalHandleId:'',
                disposalCauses:'',
                disposalHandler:'',
                analysedDate:'',
                disposalDate:'',
                purchaseOrdersId:'',
                materialName:'',
                materialId:'',
                supplierName:'',
                purchaseReason:'',
                purchaseCode:'',
            },
            types: [],
            tableData: [],
            disposalMeasure: [],
            materialChange: false,
            substitute: {},
            pages: 0,
            materialPages:0,
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
        handleSelect(ok) {
            this.$router.push({
                path: ok
            });
        },
        searchLike(){
            this.search.pageNum = 1
            this.getData();
        },
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
            this.search = {
                unqualifiedReviewNo: "",
                materialName: "",
                purchaseOrderNo: "",
                purchaseCode: "",
                pageNum: 1,
                pageSize: 100
            };
        },
        handleEdit(index, row) {
            this.editForm.disposalHandleId = row.disposalHandleId
            if (this.editForm.disposalHandleId != 5) {
                this.materialChange = false
            } else {
                this.materialChange = true
            }
            row.editable == 1 ? this.disabled = true : this.disabled = false
            if (row.editable == 1) {
                this.disabled = true
                this.opens = true
            } else {
                this.disabled = false
                this.opens = true
            }
            let _this = this
            this.$http.post("/incomingUnqualified/listMeasure").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.disposalMeasure = JSON.parse(res.data.data);
                }
            });
            this.editVisible = true;
            this.editForm = row;
        },
        measureHandle(value) {
            if (value == 5) {
                this.materialChange = true;
            } else {
                this.materialChange = false;
            }
        },
        searchLikeMaterial(){
            this.searchProduction.pageNum = 1
            this.searchMaterial();
        },
        searchMaterial() {
            this.$http.post("/materialInfo/searchMaterialList", this.searchProduction).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.productList = res.data.data.list;
                    this.materialPages = res.data.data.pages
                }
            });
        },
        selectMaterial(materialBom) {
            this.substitute = materialBom;
            this.editForm.changedMaterialId = materialBom.id;
            console.log(this.editForm);
            this.selectVisible = false;
        },
        changeHandle() {
            //this.substitute = {};
            this.selectVisible = true;
        },
        insert(formName) {},
        execute(formName) {
            console.log(this.editForm);
            this.$refs[formName].validate(valid => {
                if(this.editForm.disposalHandleId == 5 && this.editForm.changedMaterialId == null){
                    this.$message.warning('请选择转换的新物料')
                }else{
                    this.saveForm.id = this.editForm.id
                    this.saveForm.unqualifiedReviewNo = this.editForm.unqualifiedReviewNo
                    this.saveForm.unqualifiedDescribe = this.editForm.materialRepertoryIncomingCheckInfo.unqualifiedRemark
                    this.saveForm.materialBatch = this.editForm.materialRepertoryIncomingCheckInfo.materialBatch
                    this.saveForm.unqualifiedQty = this.editForm.unqualifiedQty
                    this.saveForm.checkId = this.editForm.checkId
                    this.saveForm.fileUrl = this.editForm.fileUrl
                    this.saveForm.unqualifiedReasonAnalysis = this.editForm.unqualifiedReasonAnalysis
                    this.saveForm.analysisPerson = this.editForm.analysisPerson
                    this.saveForm.disposalHandleId = this.editForm.disposalHandleId
                    this.saveForm.disposalCauses = this.editForm.disposalCauses
                    this.saveForm.disposalHandler = this.editForm.disposalHandler
                    this.saveForm.analysedDate = this.editForm.analysedDate
                    this.saveForm.disposalDate = this.editForm.disposalDate
                    this.saveForm.purchaseOrdersId = this.editForm.purchaseOrdersId
                    this.saveForm.materialName = this.editForm.materialName
                    this.saveForm.materialCode = this.editForm.materialCode
                    this.saveForm.materialId = this.editForm.materialId
                    this.saveForm.supplierName = this.editForm.supplierName
                    this.saveForm.purchaseReason = this.editForm.purchaseReason
                    this.saveForm.purchaseCode = this.editForm.purchaseCode
                    this.saveForm.changedMaterialId = this.editForm.changedMaterialId
                    this.saveForm.editable = 0
                    this.saveForm.fileUrl = this.editForm.fileUrl
                    this.saveForm.purchaseOrdersListId = this.editForm.purchaseOrdersListId
                    this.$http.post("/incomingUnqualified/executeMeasure", this.saveForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.editVisible = false;
                            this.$message.success('执行处理成功');
                        }
                    });
                }
            });
        },
        update(formName) {
            this.$refs[formName].validate(valid => {
                this.saveForm.id = this.editForm.id
                this.saveForm.unqualifiedReviewNo = this.editForm.unqualifiedReviewNo
                this.saveForm.unqualifiedDescribe = this.editForm.materialRepertoryIncomingCheckInfo.unqualifiedRemark
                this.saveForm.unqualifiedQty = this.editForm.unqualifiedQty
                this.saveForm.checkId = this.editForm.checkId
                this.saveForm.fileUrl = this.editForm.fileUrl
                this.saveForm.unqualifiedReasonAnalysis = this.editForm.unqualifiedReasonAnalysis
                this.saveForm.analysisPerson = this.editForm.analysisPerson
                this.saveForm.disposalHandleId = this.editForm.disposalHandleId
                this.saveForm.disposalCauses = this.editForm.disposalCauses
                this.saveForm.disposalHandler = this.editForm.disposalHandler
                this.saveForm.analysedDate = this.editForm.analysedDate
                this.saveForm.disposalDate = this.editForm.disposalDate
                this.saveForm.purchaseOrdersId = this.editForm.purchaseOrdersId
                this.saveForm.materialName = this.editForm.materialName
                this.saveForm.materialCode = this.editForm.materialCode
                this.saveForm.materialId = this.editForm.materialId
                this.saveForm.supplierName = this.editForm.supplierName
                this.saveForm.purchaseReason = this.editForm.purchaseReason
                this.saveForm.purchaseCode = this.editForm.purchaseCode
                this.saveForm.changedMaterialId = this.editForm.changedMaterialId
                this.saveForm.editable = 0
                this.$http.post("incomingUnqualified/save", this.saveForm).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.editVisible = false;
                        this.$message.success('保存成功');
                    }
                });
            });
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        handleCurrentChangeMaterial(val){
            this.searchProduction.pageNum = val;
            this.searchMaterial();
        },
        goback() {
            this.editVisible = false;
        },
        downloadFile(path) {
            let _this = this;
            let obj = this.editForm;
            obj.load = 0;
            obj.progress = '';
            obj.show = true;
            let schedule = setInterval(function () {
                    obj.load > 97 ? obj.load += 0 : obj.load += 1
                }
                , 100);
            this.$http.post('/productDrawing/download', {filePath: path}, {responseType: 'arraybuffer'}).then((res) => {
                fileDownload(res.data, utils.getFileName(path));
            obj.load = 100;
            clearInterval(schedule);
            obj.progress = 'success'
        })
        .catch(err => {
                clearInterval(schedule);
            obj.progress = 'exception'
        })
        },
    },
    watch: {
        $route(to, from) {
            if (to.path == "/incomingUnqualifiedReport") {
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

.handle-select {
    width: 120px;
}

.marginTop {
    margin-top: 10px;
    margin-bottom: 5px;
}
.marginBottom {
    margin-top: 5px;
    margin-bottom: 10px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.del-dialog-cnt {
    font-size: 16px;
    text-align: center;
}
.text {
    font-size: 14px;
    color: #606266;
    margin-right: 30px;
}
</style>