<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span class="el-form-item__label">不合格类型</span>
                <el-button round type="primary" @click="addUnqualifiedType">新增不合格类型</el-button>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="unqualifiedType" label="不合格类型">
                </el-table-column>
                <!--<el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="deleteRow(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>-->
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <!--编辑弹出框-->
        <el-dialog title="新增不合格类型" @close="closeing" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-col :span="12">
                    <el-form-item label="不合格类型:">
                        <el-input v-model="form.unqualifiedType"></el-input>
                    </el-form-item>
                </el-col>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveUnqualifiedType">保存</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                tableData: [],
                url: '/unqualifiedType/list',
                saveUrl:'/unqualifiedType/insert',
                deleteUrl:'/unqualifiedType/update',
                editVisible: false,
                pages: 1,
                form: {
                    unqualifiedType: ''
                },
                search: {
                    pageNum: 1,
                    pageSize:20
                }
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                return d;
            })
            }
        },
        methods: {
            closeing () {
                this.$refs['form'].resetFields()
                this.form.unqualifiedType = ''
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取待检测列表数据
            getData() {
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                })
            },
            //新增页面弹框
            addUnqualifiedType() {
                this.editVisible = true;
            },
            // 新增保存
            saveUnqualifiedType() {
                this.$http.post(this.saveUrl, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.editVisible = false
                        this.$message.success("添加成功")
                        this.getData()
                    }
                })
            },
            //删除
            deleteRow(index,row){
                this.$http.post(this.saveUrl, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.editVisible = false
                        this.$message.success("添加成功")
                        this.getData()
                    }
                })
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
