<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <el-button type="primary" size="small" >辅助工具类型</el-button>
            <el-button size="small" @click="$router.push('/toolList');">工具列表</el-button>
            <hr class="marginTop"/>
            <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="工具类型编号:">
                    <el-input v-model="search.id"></el-input>
                </el-form-item>
                <el-form-item label="工具类型名称:">
                    <el-input v-model="search.toolTypeName"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="search.status" placeholder="请选择">
                        <el-option value="1"  label="启用"></el-option>
                        <el-option value="0"  label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <el-button round @click="searchLike">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <hr class="marginTop" style="margin-top:-12px;"/>
            <div class="handle-box">
                <span class="el-form-item__label">工具类型列表</span>
                <el-button round type="primary" @click="add">新增工具类型</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column align="center" label="工具类型编号" prop="id" sortable>
                </el-table-column>
                <el-table-column align="center" label="工具类型名称" prop="toolTypeName">
                </el-table-column>
                <el-table-column align="center" label="状态" prop="statusDisplay">
                </el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="工具类型详情" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="工具类型编号:" prop="name">
                    <el-input v-model="form.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="工具类型名称:" prop="name">
                    <el-input v-model="form.toolTypeName" disabled></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-input v-model="form.status==1?'启用':'停用'" disabled></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增工具类型" :visible.sync="addVisible" width="30%">
            <el-form ref="addform" :rules="rules" :model="addform" label-width="100px">
                <el-form-item label="工具类型名称:" prop="toolTypeName">
                    <el-input v-model="addform.toolTypeName"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="addform.status" placeholder="请选择">
                        <el-option value="1"  label="启用"></el-option>
                        <el-option value="0"  label="停用"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addform')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/toolType/searchToolTypePager",
                editVisible:false,
                addVisible:false,
                rules: {
                    toolTypeName: [
                        { required: true, message: '请输入工具类型名称', trigger: 'blur' }
                    ]
                },
                pages: 1,
                search: {
                    status: '',
                    toolTypeName:'',
                    pageNum: 1,
                    pageSize:20
                },
                form:{
                    toolTypeName:'',
                    status:''
                },
                addform:{
                    toolTypeName:'',
                    status:''
                }
            };
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    let status = d.status;
                d.statusDisplay = status == 1 ? '启用':'停用';
                return d;
            })
            }
        },
        methods: {
            searchEnterFun (e) {
                let keyCode = window.event? e.keyCode:e.which;
                if(keyCode == 13){
                    this.searchLike()
                }
            },
            handleSelect(key, keyPath) {},
            //明细
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    id: item.id,
                    toolTypeName: item.toolTypeName,
                    status: item.status
                };
                this.editVisible = true;
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
            },
            clearData() {
                (this.search.id = ""),
                    (this.search.toolTypeName = ""),
                    (this.search.status = "")
            },
            add() {
                this.addform.toolTypeName = "",
                    this.addform.status = "1"
                    this.addVisible = true;
            },
            updateStatus(index, row) {
                this.$http.post('/toolType/updateStatus', {
                    id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                    row.status = row.status == 0 ? 1 : 0;
                    this.$message.success('操作成功');
                } else {
                    this.$message.error(res.data.message);
                }
            })
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/toolType/saveToolType', this.addform).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                            this.getData();
                            this.addVisible = false;
                            this.$message.success('新增成功');
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
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/toolTypeList') {
                    this.getData();
                }
            }
        },
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

