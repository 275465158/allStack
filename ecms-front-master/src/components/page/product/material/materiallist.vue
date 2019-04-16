<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button size="small" @click="editVisible = true" :loading="importLoading">
                    导入产品
                    <i class="el-icon-upload el-icon--right"></i>
                </el-button>
                <el-button
                    style="float:right;"
                    size="small"
                    :loading="buttonLoading"
                    @click="exportTemplate"
                >
                    模板下载
                    <i class="el-icon-download el-icon--right"></i>
                </el-button>
                <el-form
                    @keyup.enter.native="searchEnterFun"
                    :inline="true"
                    :model="search"
                    class="demo-form-inline"
                    label-width="100px"
                >
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="工厂内部编号:">
                        <el-input v-model="search.factoryMaterialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型:">
                        <el-input v-model="search.type"></el-input>
                    </el-form-item>
                    <el-form-item label="原图材料:">
                        <el-input v-model="search.originalMaterial"></el-input>
                    </el-form-item>
                    <el-form-item label="参数:">
                        <el-input v-model="search.materialBomParamValueStr"></el-input>
                    </el-form-item>
                    <el-form-item label="图号:">
                        <el-input v-model="search.drawingCode"></el-input>
                    </el-form-item>
                    <el-form-item label="来源:">
                        <el-select style="width: 200px;" v-model="search.source">
                            <el-option label="全部" value></el-option>
                            <el-option label="自制" value="自制"></el-option>
                            <el-option label="外购" value="外购"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="审核状态:">
                        <el-select style="width: 200px;" v-model="search.auditingStatus">
                            <el-option label="全部" value></el-option>
                            <el-option label="审核通过" value="0"></el-option>
                            <el-option label="审核中" value="1"></el-option>
                            <el-option label="待审核" value="2"></el-option>
                            <el-option label="审核不通过" value="3"></el-option>
                            <el-option label="停用" value="4"></el-option>
                            <el-option label="待启动" value="5"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-button round @click="searchMaterial" type="primary">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box" style="margin-top: 10px">
                <span class="el-form-item__label">物料列表</span>
                <el-button round type="primary" @click="add">新增物料</el-button>
                <el-button round type="primary" @click="merge">物料合并</el-button>
                <el-button size="small" @click="materialImportVisible = true">库存导入
                    <i  class="el-icon-upload el-icon--right"></i>
                </el-button>
                <el-button style="float:right;"  size="small"  @click="exportRepertoryTemplate">库存模板下载
                    <i class="el-icon-download el-icon--right"></i>
                </el-button>
            </div>
            <div class="materialTableBox">
                <el-table v-loading="loading" :data="tableData" border style="width:100%">
                    <!--<el-table-column prop="id" label="序号" sortable>-->
                    <!--<template slot-scope="scope">-->
                    <!--{{scope.$index+1}}-->
                    <!--</template>-->
                    <!--</el-table-column>-->
                    <el-table-column label="物料编号" align="center" prop="materialCode"></el-table-column>
                    <el-table-column label="工厂内部编号" align="center" prop="factoryMaterialCode"></el-table-column>
                    <el-table-column label="物料类型" align="center" prop="type" width="120px"></el-table-column>
                    <el-table-column label="物料名称" align="center" prop="materialName"></el-table-column>
                    <el-table-column label="原图材料" align="center" prop="originalMaterial"></el-table-column>
                    <el-table-column label="参数" align="center" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column label="来源" align="center" prop="source" width="80px"></el-table-column>
                    <!--<el-table-column label="添加时间" align="center" prop="materialBomCreated">-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="验收标准" align="center" prop="ifCheck">-->
                    <!--<template slot-scope="scope">-->
                    <!--<div v-if="scope.row.ifCheck === 0">无</div>-->
                    <!--<div v-if="scope.row.ifCheck === 1">有</div>-->
                    <!--</template>-->
                    <!--</el-table-column>-->
                    <el-table-column label="图号" align="center" prop="drawingCode"></el-table-column>
                    <el-table-column
                        label="审核状态"
                        align="center"
                        prop="auditingStatus"
                        :formatter="auditingStatus"
                    ></el-table-column>
                    <el-table-column label="操作" align="center" width="80">
                        <template slot-scope="scope">
                            <el-popover placement="top" trigger="click">
                                <div style="text-align: center">
                                    <el-button
                                        v-if="(scope.row.auditingStatus == 2 || scope.row.auditingStatus == 3) && scope.row.versionNum == 0"
                                        type="text"
                                        @click="updateInfo(scope.$index, scope.row)"
                                    >修改</el-button>
                                    <el-button
                                            v-if="scope.row.versionNum > 0"
                                            type="text"
                                            @click="upInfo(scope.$index, scope.row)"
                                    >修改</el-button>
                                    <el-button
                                        v-if="scope.row.auditingStatus == 0 || scope.row.auditingStatus == 4"
                                        type="text"
                                        @click="upgraded(scope.$index, scope.row)"
                                    >升级</el-button>
                                    <el-button
                                        type="text"
                                        @click="detail(scope.$index, scope.row)"
                                    >明细</el-button>
                                    <el-button
                                        type="text"
                                        @click="copyEdit(scope.$index, scope.row)"
                                    >复制修改</el-button>
                                    <el-button
                                        type="text"
                                        @click="startTask(scope.$index, scope.row)"
                                        v-if="workflowSuspended==false && scope.row.auditingStatus == 2"
                                    >启动评审</el-button>
                                    <el-button
                                        type="text"
                                        @click="auditingPass(scope.$index, scope.row)"
                                        v-if="workflowSuspended==false && scope.row.auditingStatus == 2"
                                    >审核通过</el-button>
                                    <el-button
                                        type="text"
                                        @click="view(scope.$index, scope.row)"
                                    >分解清单</el-button>
                                    <el-button
                                        v-if="scope.row.auditingStatus == 0"
                                        type="text"
                                        @click="blockUp(scope.$index, scope.row)"
                                    >停用</el-button>
                                    <el-button
                                        v-if="scope.row.auditingStatus == 4"
                                        type="text"
                                        @click="startUsing(scope.$index, scope.row)"
                                    >启用</el-button>
                                </div>
                                <el-button type="text" slot="reference">管理</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleCurrentChange"
                    layout="total, prev, pager, next"
                    :total="pages"
                ></el-pagination>
            </div>
            <!-- 编辑弹出框 -->
            <el-dialog title="导入产品" :visible.sync="editVisible" width="30%">
                <el-form ref="form" label-width="100px">
                    <el-form-item label="产品列表:">
                        <input type="file" name="file" @change="setFile">
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="importMaterial">导入</el-button>
                </span>
            </el-dialog>

            <!-- 导入弹出框 -->
            <el-dialog title="导入库存" :visible.sync="materialImportVisible" width="30%">
                <el-form ref="form" label-width="100px">
                    <el-form-item label="库存列表:">
                        <input type="file" name="file" @change="setFile" />
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="materialImportVisible = false">取 消</el-button>
                  <el-button type="primary" @click="importRepertoryMaterial">导入</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import fileDownload from "js-file-download";
export default {
    data() {
        return {
            visible2: false,
            tableData: [],
            buttonLoading: false,
            importLoading: false,
            editVisible:false,
            materialImportVisible:false,
            url: "/materialInfo/searchMaterialList",
            pages: 1,
            loading: false,
            search: {
                materialCode: "",
                materialName: "",
                pageNum: 1,
                pageSize: 20,
                factoryMaterialCode: "",
                type: "",
                materialBomParamValueStr: "",
                source: "",
                drawingCode: "",
                originalMaterial: ""
            },
            form: {
                status: "",
                ifDrawing: "",
                ifCheck: ""
            },
            workflowSuspended: false
        };
    },
    created() {
        this.getData();
    },
    methods: {
        merge() {
            this.$router.push({
                path: "/materialMerge"
            });
        },
        searchEnterFun(e) {
            let keyCode = window.event ? e.keyCode : e.which;
            if (keyCode == 13) {
                this.searchMaterial();
            }
        },
        handleSelect(key, keyPath) {},
        handleChange(value) {},
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        searchMaterial() {
            this.search.pageNum = 1;
            this.getData();
        },
        getData() {
            let _this = this;
            _this.loading = true;
            this.$http
                .post(this.url, this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false;
                })
                .catch(err => {
                    _this.loading = false;
                });
            this.$http
                .post("/bpm/models/getStatus", {
                    workflowKey: "MaterialBomReview"
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data.isSuspended == null) {
                            this.workflowSuspended = true;
                        } else {
                            this.workflowSuspended = res.data.data.isSuspended;
                        }
                    }
                });
        },
        add() {
            this.$router.push({
                path: "/materialAdd"
            });
        },
        updateInfo(index, row) {
            this.$router.push({
                path: "/materialEdit",
                query: {
                    materialId: row.id
                }
            });
        },
        upInfo(index, row) {
            this.$router.push({
                path: "/materialUpEdit",
                query: {
                    materialId: row.id
                }
            });
        },
        //升级
        upgraded(index, row) {
            this.$router.push({
                path: "/materialUpgraded",
                query: {
                    materialId: row.id
                }
            });
        },
        //停用
        blockUp(index, row) {
            this.$http
                .post("/materialInfo/blockUp", { id: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("物料停用成功");
                        this.getData();
                    }
                });
        },
        //启用
        startUsing(index, row) {
            this.$http
                .post("/materialInfo/startUsing", { id: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("物料启用成功，请审核");
                        this.getData();
                    }
                });
        },
        //直接审核通过
        auditingPass(index, row) {
            this.$confirm("是否直接通过评审，点击确定继续", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    this.$http
                        .post("/material/auditingPass", { materialId: row.id })
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.$message.success("直接通过审批成功");
                                this.getData();
                            }
                        });
                })
                .catch(() => {
                    this.$message.error("审批取消");
                });
        },
        copyEdit(index, row) {
            this.$router.push({
                path: "/materialCopyEdit",
                query: {
                    materialId: row.id
                }
            });
        },
        detail(index, row) {
            this.$router.push({
                path: "/materialSearch",
                query: {
                    materialId: row.id
                }
            });
        },
        view(index, row) {
            this.$router.push({
                path: "/productView",
                query: {
                    materialId: row.id
                }
            });
        },
        startTask(index, row) {
            this.$http
                .post("/materialInfo/startTask", {
                    id: row.id,
                    auditingStatus: 1
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        clearData() {
            (this.search.materialCode = ""),
                (this.search.materialName = ""),
                (this.search.factoryMaterialCode = ""),
                (this.search.type = ""),
                (this.search.materialBomParamValueStr = ""),
                (this.search.source = ""),
                (this.search.drawingCode = ""),
                (this.search.originalMaterial = "");
            this.search.auditingStatus = "";
        },
        auditingStatus(row, column) {
            if (row.auditingStatus == 0) {
                return "审核通过";
            } else if (row.auditingStatus == 1) {
                return "审核中";
            } else if (row.auditingStatus == 2) {
                return "待审核";
            } else if (row.auditingStatus == 3) {
                return "审核不通过";
            } else if (row.auditingStatus == 4) {
                return "停用";
            }
        },
        exportTemplate() {
            this.buttonLoading = true;
            this.$http
                .get("/materialInfo/export/template", { responseType: "blob" })
                .then(res => {
                    fileDownload(res.data, "产品模板.xls");
                    this.buttonLoading = false;
                });
        },
        setFile(event) {
            this.file = event.target.files[0]; //获取文件
            var windowURL = window.URL || window.webkitURL;
            this.file = event.target.files[0];
        },
        importMaterial() {
            this.importLoading = true;
            let formdata = new FormData();
            formdata.append("file", this.file);
            let config = {
                headers: {
                    "Content-Type": "multipart/form-data"
                },
                withCredentials: true
            };
            this.$axios
                .post(this.$path + "/materialInfo/upload", formdata, config)
                .then(res => {
                    this.editVisible = false;
                    if (res.data.code == 1000) {
                        this.$message.success("导入成功");
                        this.importLoading = false;
                        this.getData();
                    } else {
                        this.$message.error(res.data.message);
                        this.importLoading = false;
                    }
                });
        },
        exportRepertoryTemplate() {
            this.$http
                .get("/materialExtInfo/export/template", { responseType: "blob" })
                .then(res => {
                    fileDownload(res.data, "库存模板.xls");
                });
        },
        //库存模板导入
        importRepertoryMaterial(){
            let formdata = new FormData();
            formdata.append('file',this.file);
            let config = {
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                withCredentials : true
            };
            this.$axios.post(this.$path + '/materialExtInfo/upload', formdata, config).then((res) => {
                this.materialImportVisible = false;
                if (res.data.code == 1000) {
                    this.$message.success('导入成功');
                    this.getData();
                } else {
                    this.$message.error(res.data.message);
                }
            })
        },
    },
    watch: {
        $route(to, from) {
            if (to.path == "/materialList") {
                this.getData();
            }
        }
    }
};
</script>
<style>
.materialTableBox .el-table--small td,
.el-table--small th {
    padding: 0 0;
}
</style>
