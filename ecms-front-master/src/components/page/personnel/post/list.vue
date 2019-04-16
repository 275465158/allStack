<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button round type="primary" @click="add">新增岗位</el-button>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="岗位名称:">
                        <el-input v-model="search.name"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                        <el-select v-model="search.status">
                            <el-option
                                    v-for="item in statusArray"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round @click="searchLike">搜索</el-button>
                </el-form>
            </div>
            <el-table :data="tables" border stripe style="width: 100%">
                <el-table-column align="center" prop="postNumberDisplay" label="岗位编号"/>
                <el-table-column align="center" prop="name" label="岗位名称"/>
                <el-table-column align="center" prop="count" label="岗位人数"/>
                <el-table-column align="center" prop="statusDisplay" label="状态"/>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
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
        <el-dialog title="新增岗位" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="岗位名称:" prop="name">
                    <el-input v-model="form.name"></el-input>
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
                statusArray:[
                    {
                        name:'所有',
                        value:2
                    },
                    {
                        name:'正常',
                        value:1
                    },
                    {
                        name:'停用',
                        value:0
                    }
                ],
                tableData: [],
                url: '/post/all',
                editVisible: false,
                form: {
                    name: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入岗位名称', trigger: 'blur' }
                    ]
                },
                pages: 1,
                search: {
                    status: 2,
                    name: '',
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
                    d.statusDisplay = status == 1 ? '正常':'停用';
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
                this.form.name = '';
                this.editVisible = true;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            updateStatus(index, row) {
                this.$http.post('/post/status/update', {
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
                        this.$http.post('/post/insert', this.form).then((res) => {
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
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
</style>
