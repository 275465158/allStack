<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect"
                >
                    <el-menu-item index="/unqualifiedProductReport">生产不合品处理</el-menu-item>
                    <el-menu-item index="/incomingUnqualifiedReport">来料不合格品处理</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box"></div>
            <el-form @keyup.enter.native="getData" :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="不合格品单号">
                    <el-input v-model="search.orderNo"></el-input>
                </el-form-item>
                <el-form-item label="物料名称">
                    <el-input v-model="search.materialName"></el-input>
                </el-form-item>
                <el-form-item label="合同编号">
                    <el-input v-model="search.purchaseOrderNo"></el-input>
                </el-form-item>
                <el-form-item label="生产单号">
                    <el-input v-model="search.productionNo"></el-input>
                </el-form-item>
                <el-button round @click="getData">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <div class="handle-box">
                <!-- <el-button round type="primary">新增处置记录</el-button> -->
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column label="序号" prop="id" sortable></el-table-column>
                <el-table-column label="不合格品记录单号" prop="orderNo"></el-table-column>
                <el-table-column label="生产单号" prop="productionNo"></el-table-column>
                <el-table-column label="物料编号" prop="materialNumber"></el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="材料" prop="originMaterial"></el-table-column>
                <el-table-column label="不合格品数量" prop="unqualifiedQty"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                            size="small"
                            v-if="scope.row.editable!=1"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                            size="small"
                            v-if="scope.row.editable==1"
                            @click="handleEdit(scope.$index, scope.row)"
                        >查看</el-button>
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
        <el-dialog class="selectperson" title="不合格品详情" :visible.sync="editVisible" width="80%">
            <el-form ref="editForm" :rules="rules" :model="editForm" label-width="140px">
                <el-row>
                    <el-col :span="24">不合格品信息</el-col>
                </el-row>
                <el-row border>
                    <el-col :span="12">
                        <el-form-item label="不合格品单号">
                            <el-input :disabled="disabled || opens" v-model="editForm.orderNo"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="合同编号">
                            <el-input :disabled="disabled || opens" v-model="editForm.purchaseOrderNo"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-col :span="12">
                    <el-form-item label="生产单号">
                        <el-input :disabled="disabled || opens" v-model="editForm.productionNo"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="生产项次号">
                        <el-input :disabled="disabled || opens" v-model="editForm.productionItemNo"></el-input>
                    </el-form-item>
                </el-col>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="物料编码">
                            <el-input :disabled="disabled || opens" v-model="editForm.materialNumber"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料名称">
                            <el-input :disabled="disabled || opens" v-model="editForm.materialName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="材料">
                            <el-input :disabled="disabled || opens" v-model="editForm.originMaterial"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="参数">
                            <el-input :disabled="disabled || opens" v-model="editForm.paramters"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="生产者">
                            <el-input :disabled="disabled || opens" v-model="editForm.productionResponsible"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="生产日期">
                            <el-input :disabled="disabled || opens" v-model="editForm.productionDate"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="数量">
                            <el-input :disabled="disabled || opens" v-model="editForm.qty"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="不合格数量">
                            <el-input :disabled="disabled || opens" v-model="editForm.unqualifiedQty"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="检验员">
                            <el-input :disabled="disabled || opens" v-model="editForm.inspector"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="检验时间">
                            <el-input :disabled="disabled || opens" v-model="editForm.inspectDate"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="不合品类型">
                            <el-tag v-for="(item,index) in types" :key="index">{{item.typeName}}</el-tag>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="不合格品描述">
                            <el-input :disabled="disabled || opens" v-model="editForm.unqualifiedDescription" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="附件"></el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">责任人</el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="责任人">
                            <el-input :disabled="disabled && opens" v-model="editForm.personResponsible"></el-input>

                            <el-button @click="selectPerson(1)">选择</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="责任部门">
                            <el-input :disabled="disabled && opens" v-model="editForm.departResponsible"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="责任评定人">
                            <el-input :disabled="disabled && opens" v-model="editForm.personReviewResponsible"></el-input>
                            <el-button @click="selectPerson(2)">选择</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="责任评定时间">
                            <el-date-picker :disabled="disabled && opens" v-model="editForm.reviewDate"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">不合格原因分析</el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="不合格原因分析">
                            <el-input :disabled="disabled && opens" v-model="editForm.analysisCauses" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="分析人">
                            <el-input :disabled="disabled && opens" v-model="editForm.analyser"></el-input>
                            <el-button @click="selectPerson(3)">选择</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="分析时间">
                            <el-date-picker :disabled="disabled && opens" v-model="editForm.analysedDate"></el-date-picker>
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
                                    :disabled="disabled && opens"
                                    v-for="item in disposalMeasure"
                                    :label="item.id"
                                    :key="item.id"
                                >{{item.name}}
                                </el-radio>
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
                            {{substitute.paramValue}}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="纠正及处理措施依据">
                            <el-input :disabled="disabled && opens" v-model="editForm.disposalCauses" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="处置人">
                            <el-input :disabled="disabled && opens" v-model="editForm.disposalHandler"></el-input>
                            <el-button @click="selectPerson(4)">选择</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="处置时间">
                            <el-date-picker :disabled="disabled && opens" v-model="editForm.disposalDate"></el-date-picker>
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
                    <el-input v-model="searchProduction.paramValue"></el-input>
                </el-form-item>
                <el-button size="small" @click="searchMaterial">查询</el-button>
            </el-form>
            <el-table border :data="productList">
                <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="材料" prop="originalMaterial"></el-table-column>
                <el-table-column label="参数" prop="paramValue"></el-table-column>
                <el-table-column label="操作" prop="id">
                    <template slot-scope="scope">
                        <el-button @click="selectMaterial(scope.row)">选择此项</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog :title="title" @close="ponsnalClose" :visible.sync="selectPonsnalVisible" width="50%">
            <el-table v-loading="loading" :data="staff" border style="width:100%" height="300">
                <el-table-column label="姓名" prop="name"></el-table-column>
                <el-table-column label="一级部门" prop="firstDepartmentName"></el-table-column>
                <el-table-column label="一级部门id" prop="departmentId"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="selectFn(scope.row, title)">{{title}}</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange1" :current-page.sync="ponsnalPage" layout="total,prev, pager, next" :total="staffPages">
                </el-pagination>
            </div>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {
            loading: false,
            disabled: false,
            opens: false,
            editVisible: false,
            selectVisible: false,
            selectPonsnalVisible: false,
            title: '选择责任人',
            ponsnalPage: 1,
            url: "/product/unqualified/listByLike",
            search: {
                orderNo: "",
                materialName: "",
                purchaseOrderNo: "",
                productionNo: "",
                pageNum: 1,
                pageSize: 20
            },
            searchProduction: {
                materialName: "",
                materialCode: "",
                originalMaterial: "",
                paramValue: "",
                pageNum: 1,
                pageSize: 100
            },
            productList: [],
            rules: {},
            editForm: {},
            types: [],
            tableData: [],
            disposalMeasure: [],
            materialChange: false,
            substitute: {},
            pages: 0,
            staff: [],
            staffstatus: [
                {
                    name:'在职',
                    value: 1
                },
                {
                    name:'离职',
                    value:0
                }
            ],
            staffForm: {
                employeeNumber: '',
                name: '',
                postName: '',
                departmentName: '',
                status: 1,
                pageNum: 1,
                pageSize: 20
            },
            staffPages: 1
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
        ponsnalClose () {
            this.ponsnalPage = 1
        },
        selectFn (row, t) {
            console.log(row);
            switch (t) {
                case '选择责任人':
                    this.editForm.personResponsible = row.name
                    this.editForm.departResponsible = row.departmentName
                    break
                case '选择责任评定人':
                    this.editForm.personReviewResponsible = row.name
                    break
                case '选择分析人':
                    this.editForm.analyser = row.name
                    break
                case '选择处置人':
                    this.editForm.disposalHandler = row.name
                    break
            }
            this.selectPonsnalVisible = false
        },
        selectPerson (i) {
            switch (i) {
                case 1:
                    this.title = '选择责任人'
                    break
                case 2:
                    this.title = '选择责任评定人'
                    break
                case 3:
                    this.title = '选择分析人'
                    break
                case 4:
                    this.title = '选择处置人'
                    break
            }
            this.selectPonsnalVisible = true
            let _this = this
            _this.loading = true
            this.$http.post('/employee/listIncludePost', this.staffForm).then((res) => {
                if (res != undefined && res.data.code == 1000) {
                    _this.staff = res.data.data.list;
                    _this.staffPages = res.data.data.total
                }
                _this.loading = false
            })
                .catch(err => {
                    _this.loading = false
                })
        },
        handleCurrentChange1 (val) {
            this.staffForm.pageNum = val;
            this.selectPerson(1)
        },
        handleSelect(ok) {
            this.$router.push({
                path: ok
            });
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
                orderNo: "",
                materialName: "",
                purchaseOrderNo: "",
                productionNo: "",
                pageNum: 1,
                pageSize: 100
            };
        },
        handleEdit(index, row) {
            row.editable == 1 ? this.disabled = true : this.disabled = false
            if (row.editable == 1) {
                this.disabled = true
                this.opens = true
            } else {
                this.disabled = false
                this.opens = true
            }
            let _this = this
            this.$http.post("/product/unqualified/listMeasure").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.disposalMeasure = JSON.parse(res.data.data);
                }
            });
            this.editVisible = true;
            this.editForm = row;
            // this.types = JSON.parse(row.unqualifiedType);
        },
        measureHandle(value) {
            if (value == 5) {
                this.materialChange = true;
            } else {
                this.materialChange = false;
            }
        },
        searchMaterial() {
            this.$http
                .post(
                    "/materialInfo/searchMaterialAndProductList",
                    this.searchProduction
                )
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.productList = res.data.data.list;
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
            this.$refs[formName].validate(valid => {
                console.log(this.editForm);

                this.$http
                    .post("/product/unqualified/executeMeasure", this.editForm)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.editVisible = false;
                        }
                    });
            });
        },
        update(formName) {
            this.$refs[formName].validate(valid => {
                console.log(this.editForm);

                this.$http
                    .post("/product/unqualified/saveOrUpdate", this.editForm)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.editVisible = false;
                        }
                    });
            });
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        goback() {
            this.editVisible = false;
        },
        downloadFile(path) {
            // this.$http
            //     .post(
            //         "/productDrawing/download",
            //         { filePath: path },
            //         { responseType: "arraybuffer" }
            //     )
            //     .then(res => {
            //         fileDownload(res.data, utils.getFileName(path));
            //     });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/unqualifiedProductReport") {
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
    .selectperson .el-input{
        width: 200px;
    }
</style>