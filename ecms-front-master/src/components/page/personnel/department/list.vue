<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button round type="primary" @click="add">新增部门</el-button>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="部门编号:">
                        <el-input v-model="search.departmentNumberDisplay"></el-input>
                    </el-form-item>
                    <el-form-item label="部门名称:">
                        <el-input v-model="search.departmentName"></el-input>
                    </el-form-item>
                    <el-form-item label="部门主管:">
                        <el-input v-model="search.adminName"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike">搜索</el-button>
                </el-form>
            </div>
            <el-table :data="tables" border stripe style="width: 100%">
                <el-table-column align="center" prop="departmentNumberDisplay" label="部门编号" />
                <el-table-column align="center" prop="departmentName" label="部门名称"/>
                <el-table-column align="center" prop="adminName" label="部门主管"/>
                <el-table-column align="center" prop="children" label="下级部门数"/>
                <el-table-column align="center" prop="employees" label="部门人数"/>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="manager(scope.$index, scope.row)">
                            管理部门
                        </el-button>
                        <el-button type="text" @click="del(scope.$index, scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增部门" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
                <el-form-item label="部门名称:" prop="departmentName">
                    <el-input v-model="form.departmentName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                url: '/department/first',
                editVisible: false,
                rules: {
                    departmentName: [
                        { required: true, message: '请输入部门名称', trigger: 'blur' }
                    ]
                },
                form: {
                    departmentName: ''
                },
                pages: 1,
                search: {
                    departmentNumberDisplay: '',
                    adminName: '',
                    departmentName: '',
                    pageNum: 1,
                    pageSize: 20
                }
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    let status = d.status;
                    d.statusDisplay = status == 0 ? '正常':'失效';
                    return d;
                })
            }
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/department') {
                    this.getData();
                }
            }
        },
        methods: {
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
                this.$http.post(this.url, this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                })
            },
            add() {
                this.form.departmentName = '';
                this.editVisible = true;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            manager(index, row) {
                this.$router.push({path:'/departmentInfo',query:{id:row.id}});
            },
            del(index, row) {
                this.$confirm('此操作将永久删除该部门及子部门, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.post('/department/del', {
                        id: row.id
                    }).then((res) => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData.splice(index, 1);
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });          
                });
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/department/insertDepartment', this.form).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.editVisible = false;
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

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }
</style>
