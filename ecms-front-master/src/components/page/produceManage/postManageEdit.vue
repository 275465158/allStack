<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="工号:">
                        <el-input v-model="form.employeeId" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="姓名:">
                        <el-input v-model="form.employeeName" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="岗位:">
                        <el-input v-model="form.postId" readonly></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">设备列表</span>
                <el-button round type="primary" @click="add">添 加</el-button>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="id" label="序号">
                </el-table-column>
                <el-table-column prop="equipmentName" label="设备名称">
                </el-table-column>
                <el-table-column prop="equipmentNumber" label="设备编号">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <!-- 编辑弹出框 -->
            <el-dialog title="新增设备" :visible.sync="editVisible" width="30%">
                <el-table :data="alertTableData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="序号">
                    </el-table-column>
                    <el-table-column prop="equipmentName" label="设备名称">
                    </el-table-column>
                    <el-table-column prop="equipmentNumber" label="设备编号">
                    </el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: '',
        data() {
            return {
                tableData: [],
                alertTableData:[],
                url: '/equipmentManage/equiList',
                insertUrl: '/equipmentUser/updateEquimentUser',
                equipmentUrl: '/equipmentUser/equimentList',
                deleteUrl: '/equipmentUser/deleteEquipment',
                editVisible: false,
                pages: 1,
                form: {
                    id:'',
                    employeeId: '',
                    employeeName: '',
                    postId:'',
                    equipmentList:'',
                    tableDataList:''
                },
                search:{
                    employeeId:'',
                    id:'',
                    pageNum: 1
                },
                alertForm: {
                    id:'',
                    equipmentName:'',
                    equipmentNumber:''
                },
                alertSearch:{
                    pageNum: 1
                }
            }
        },
        created() {
            this.getData();
        },
        mounted(){
            // 取到路由带过来的参数
            this.rowDataObj = this.$route.params.rowDataObj

            this.form.employeeId = this.$route.params.rowDataObj.id
            this.form.employeeName = this.$route.params.rowDataObj.name
            this.form.postId = this.$route.params.rowDataObj.postId
            this.form.id = this.$route.params.rowDataObj.id
            this.search.employeeId = this.$route.params.rowDataObj.id
        },
        computed: {
            tables(){
                return this.tableData
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取数据
            getData() {
                this.$http.post(this.equipmentUrl, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data
                        this.form.tableDataList = this.tableData
                        this.pages = 1
                    }
                })
            },
            getAlertData() {
                this.$http.post(this.url, this.alertSearch).then((res) => {
                    if (res.data.code == 1000) {
                        this.alertTableData = res.data.data.list
                        this.pages = res.data.data.total;
                    }
                })
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleDelete(index,row) {
                this.search.id = row.id
                this.$http.post(this.deleteUrl, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data
                        this.form.tableDataList = this.tableData
                        this.$message.success('删除成功');
                        this.getData()
                    }else{
                        this.$message.success('删除失败');
                    }
                })
            },
            // 设置保存
            save() {
                this.$http.post(this.insertUrl, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.$set(this.tableData, 0, this.setForm);
                        this.editVisible = false;
                        this.$message.success('修改成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            // 取消按钮事件
            cancel() {
                this.$router.push({ path: '/postManage' })
            },
            // 复选框
            handleSelectionChange(val) {
                console.log(val);
                this.multipleSelection = val;
                this.form.equipmentList = this.multipleSelection
            },
            add() {
                this.getAlertData();
                this.editVisible = true;
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/postManageEdit') {
                    this.getData();
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
