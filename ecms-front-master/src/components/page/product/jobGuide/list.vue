<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <el-form @keyup.enter.native="searchEnterFun" :inline="true" ref="form" :model="search" class="demo-form-inline">
                <el-form-item label="指导书名称:">
                    <el-input v-model="search.name"></el-input>
                </el-form-item>
                <el-form-item label="制作人:">
                    <el-input v-model="search.owner"></el-input>
                </el-form-item>
                <el-form-item label="文件编号:">
                    <el-input v-model="search.fileNo"></el-input>
                </el-form-item>
                <el-button @click="searchLike">查询</el-button>
            </el-form>
            <hr class="marginTop" style="margin-top:-12px;"/>
            <div class="handle-box">
                <span class="text">作业指导书列表</span>
                <el-button @click="add()">新增</el-button>
            </div>
            <el-table :data="tableData" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="指导书编号" sortable/>
                <el-table-column align="center" prop="name" label="名称"/>
                <el-table-column align="center" prop="fileVersion" label="版本号"/>
                <el-table-column align="center" prop="fileNo" label="文件编号"/>
                <el-table-column align="center" prop="owner" label="制作人"/>
                <el-table-column align="center" prop="createdDate" label="制作日期"/>
                <el-table-column align="center" prop="remark" label="备注"/>
                <el-table-column align="center" label="操作" width="250">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateInfo(scope.$index, scope.row)">
                            修改
                        </el-button>
                        <el-button type="text" @click="detail(scope.$index, scope.row)">
                            明细
                        </el-button>
                        <el-button type="text" @click="remove(scope.$index, scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="search.pageSize" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog @close="closeing" title="作业指导书" :visible.sync="editVisible" width="60%">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="指导书名称:" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="文件编号:">
                    <el-input v-model="form.fileNo"></el-input>
                </el-form-item>
                <el-form-item label="版本号:">
                    <el-input v-model="form.fileVersion"></el-input>
                </el-form-item>
                <el-form-item label="作业指导书:">
                    <el-upload v-if="editFlag"
                            class="upload-demo"
                            :action="this.$path + '/productJobGuide/uploadSimple'"
                            :on-success="fileUpload"
                            :file-list="fileList"
                            :with-credentials="true"
                            >
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                    <el-button v-else-if="form.filePath != ''"  @click="downloadFile">下载</el-button>
                    <el-progress v-if="show" :percentage="load" :status="progress"></el-progress>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.remark"></el-input>
                </el-form-item>
            </el-form>
            <span v-if="editFlag" slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import * as utils from '../../../../../static/js/utils.js';
    import fileDownload from 'js-file-download';
    export default {
        data() {
            return {
                tableData: [],
                editVisible: false,
                editFlag: true,
                total: 1,
                fileList:[],
                show: false,
                load: 0,
                progress: '',
                search: {
                    name: '',
                    fileNo: '',
                    owner: '',
                    pageNum: 1,
                    pageSize: 20
                },
                form: {
                    id: '',
                    name: '',
                    fileNo: '',
                    fileVersion: '',
                    filePath: '',
                    remark: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入作业指导书名称', trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            closeing () {
                this.show = false
                this.load = 0
                this.progress = ''
            },
            searchEnterFun (e) {
                let keyCode = window.event? e.keyCode:e.which;
                if(keyCode == 13){
                    this.searchLike()
                }
            },
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            fileUpload(response, file, fileList){
                if (response.code == 1000) {
                    this.form.filePath = response.data;
                }
            },
            downloadFile() {
                if (this.form.filePath == null || this.form.filePath == '') {
                    this.$message.error("未上传作业指导书");
                    return;
                }
                let _this = this;
                _this.show = true;
                _this.load = 0;
                _this.progress = '';
                let schedule = setInterval(function () {
                        _this.load > 97 ? _this.load += 0 : _this.load += 1
                    }
                    , 100)
                this.$http.post('/productJobGuide/download', {filePath: this.form.filePath}, {responseType: 'arraybuffer'}).then((res) => {
                    fileDownload(res.data, utils.getFileName(this.form.filePath));
                    _this.load = 100
                    clearInterval(schedule)
                    _this.progress = 'success'
                })
                    .catch(err => {
                        clearInterval(schedule)
                        _this.progress = 'exception'
                    })
            },
            add() {
                this.$refs['form'].resetFields()
                this.form = {
                    id: '',
                    name: '',
                    fileNo: '',
                    fileVersion: '',
                    filePath: '',
                    remark: ''
                };
                this.fileList = [];
                this.editVisible = true;
                this.editFlag = true;
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                this.$http.post('/productJobGuide/like', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.total = res.data.data.total;
                    }
                })
            },
            handleChange(file, fileList, index) {
                this.files[index].name = file.name;
                this.files[index].file = file.raw;
            },
            detail(index, row) {
                this.editVisible = true;
                this.editFlag = false;
                this.assembleInfo(row);
            },
            updateInfo(index, row) {
                this.editVisible = true;
                this.editFlag = true;
                this.assembleInfo(row);
            },
            remove(index, row) {
                this.$confirm('此操作将永久删除该作业指导书, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.post('/productJobGuide/remove', {
                        id: row.id
                    }).then((res) => {
                        if (res != undefined && res.data.code == 1000) {
                            this.getData();
                            this.$message.success('操作成功');
                        } else {
                            this.$message.error(res.data.message);
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });          
                });
            },
            assembleInfo(row) {
                this.form = {
                    id: row.id,
                    name: row.name,
                    fileNo: row.fileNo,
                    fileVersion: row.fileVersion,
                    remark: row.remark,
                    filePath: row.filePath,
                    fileUrl: row.fileUrl
                }
                this.fileList = [];
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/productJobGuide/save', this.form).then((res) => {
                            this.editVisible = false;
                            if (res.data.code == 1000) {
                                this.$message.success('保存成功');
                                this.editVisible = false;
                                this.getData();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        })
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            }
        }
    }

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
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .text {
        font-size: 14px;
        color: #606266;
        margin-right: 30px;
    }
</style>
