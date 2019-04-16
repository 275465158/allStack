<template>
    <div class="table">
        <div class="container">
            <!--<hr class="marginTop"/>
            <el-button type="primary" size="small" @click="$router.push('/productParameterSpecification');">参数规格</el-button>
            <el-button size="small" >参数名称</el-button>
            <hr class="marginTop"/>-->
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="参数名称:">
                      <el-input v-model="search.name"></el-input>
                    </el-form-item>
                    <el-form-item label="单位:">
                      <el-input v-model="search.unit"></el-input>
                    </el-form-item>
                    <el-button @click="searchLike">搜索</el-button>
                </el-form>
                <!-- 搜索条件 -->
                
            <hr class="marginTop" style="margin-top:-12px;"/>
            <div class="handle-box">
                <span class="text">参数列表</span>
                <el-button @click="editVisible = true">新增</el-button>
            </div>
            <el-table :data="tableData" border stripe style="width: 100%">
                <el-table-column prop="id" label="参数编号" sortable/>
                <el-table-column prop="name" label="参数名称"/>
                <el-table-column prop="unit" label="单位"/>
                <el-table-column prop="createdDate" label="创建日期"/>
                <el-table-column prop="owner" label="创建人"/>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="search.pageSize" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增参数名称" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="参数名称:" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="单位:">
                    <el-input v-model="form.unit"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">保 存</el-button>
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
                total: 1,
                search: {
                    name: '',
                    unit: '',
                    pageNum: 1,
                    pageSize: 20
                },
                form: {
                    name: '',
                    unit: '',
                    owner: localStorage.getItem('ms_username')
                },
                rules: {
                    name: [
                        { required: true, message: '请输入参数名称', trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                this.$http.post('/productParameterName/like', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.total = res.data.data.total;
                    }
                })
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/productParameterName/save', this.form).then((res) => {
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
