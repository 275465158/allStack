<template>
    <div class="table">
        <div class="container">
            <el-form @keyup.enter.native="searchEnterFun" :inline="true" ref="form" :model="search" class="demo-form-inline">
                <el-form-item label="商标名称:">
                    <el-input v-model="search.tradeMark"></el-input>
                </el-form-item>
                <el-button @click="searchLike">查询</el-button>
                <el-button @click="clearData">清空</el-button>
            </el-form>
            <hr class="marginTop" style="margin-top:-12px;"/>
            <div class="handle-box">
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <el-table :data="tables" border stripe style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="tradeMark" label="商标名称"/>
                <el-table-column align="center" prop="remark" label="备注"/>
                <el-table-column align="center" prop="statusDisplay" label="状态"/>
                <!--<el-table-column align="center" label="操作" width="180">-->
                    <!--<template slot-scope="scope">-->
                        <!--<el-button type="text" @click="updateStatus(scope.$index, scope.row)">-->
                            <!--<font v-if="scope.row.status=='1'">停用</font>-->
                            <!--<font v-else>启用</font>-->
                        <!--</el-button>-->
                    <!--</template>-->
                <!--</el-table-column>-->
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增商标" @close="closeing" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
                <el-form-item label="商标:" prop="tradeMark">
                    <el-input v-model="form.tradeMark"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <el-input v-model="form.remark"></el-input>
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
                editVisible: false,
                pages: 1,
                rules: {
                    tradeMark: [
                        { required: true, message: '请输入商标名称', trigger: 'blur' }
                    ]
                },
                form: {
                    tradeMark: '',
                    remark: ''
                },
                search:{
                    tradeMark:'',
                    pageNum: 1,
                    pageSize:20,
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
            closeing () {
                this.$refs['form'].resetFields()
                this.form.remark = ''
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$http.post("/tradeMark/searchPager",this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                })
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
            add() {
                this.editVisible = true;
            },
            updateStatus(index, row) {
                this.$http.post('/tradeMark/status/update', {
                    id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        row.status = row.status == 0 ? 1 : 0;
                        this.$message.success('操作成功');
                        this.getData()
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/tradeMark/insertTradeMark', this.form).then((res) => {
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
            },
            clearData() {
                (this.search.tradeMark = "")
            },
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
