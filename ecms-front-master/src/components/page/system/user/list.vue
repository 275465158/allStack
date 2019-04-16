<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="工号:">
                    <el-input v-model="search.employeeNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名:">
                        <el-input v-model="search.userName"></el-input>
                    </el-form-item>
                    <el-form-item label="账号:">
                        <el-input v-model="search.userAccount"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike">查 询</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">账号列表</span>
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <el-table v-loading="loading" :data="tables" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column align="center" prop="employeeNumber" label="工号">
                </el-table-column>
                <el-table-column align="center" prop="userName" label="姓名">
                </el-table-column>
                <el-table-column align="center" prop="userAccount" label="账号">
                </el-table-column>
                <el-table-column align="center" prop="statusDisplay" label="状态">
                </el-table-column>
                <el-table-column align="center" prop="typeDisplay" label="账号类型">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.type != '10'" type="text" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='0'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button type="text" @click="updateRoles(scope.$index, scope.row)">
                            更改权限
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增账号" @close="closeing" :visible.sync="editVisible" width="40%">
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
                <el-form-item label="工号:">
                    <el-select v-model="form.employeeNumber" placeholder="请选择" @change="selectEmployeeId($event)">
                        <el-option v-for="item in this.userList" :key="item.employeeNumber" :label="item.employeeNumber" :value="item.employeeNumber" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="姓名:" prop="userName">
                    <el-select v-model="form.userName" placeholder="请选择" @change="selectUserName($event)">
                        <el-option v-for="item in this.userList" :key="item.id" :label="item.name" :value="item.employeeNumber" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="账号:" prop="userAccount">
                    <el-input v-model="form.userAccount"></el-input>
                </el-form-item>
                <el-form-item label="部门:">
                    <el-input v-model="form.department" disabled></el-input>
                </el-form-item>
                <el-form-item label="账号类型:">
                    <el-radio v-model="form.type" label="0">普通账号</el-radio>
                    <el-radio v-model="form.type" label="10">管理员</el-radio>
                </el-form-item>
                <el-form-item label="权限:">
                    <el-checkbox-group v-model="form.checkList">
                        <el-checkbox label="1">人事管理</el-checkbox>
                        <el-checkbox label="2">销售管理</el-checkbox>
                        <el-checkbox label="4">产品管理</el-checkbox>
                        <div style="margin-top:10px;">
                        <el-checkbox label="8">仓库管理</el-checkbox>
                        <el-checkbox label="16">采购管理</el-checkbox>
                        <el-checkbox label="32">品质管理</el-checkbox>
                        </div>
                        <div style="margin-top:10px;">
                        <el-checkbox label="64">设备管理</el-checkbox>
                        <el-checkbox label="128">生产管理</el-checkbox>
                        <el-checkbox label="256">系统管理</el-checkbox>
                        </div>
                    </el-checkbox-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="更新账号权限" :visible.sync="updateVisible" width="40%">
            <el-form ref="updateForm" :model="updateForm" label-width="100px">
                <el-input type="hidden" v-model="updateForm.id"/>
                <el-form-item label="账号类型:">
                    <el-radio v-model="updateForm.type" label="0">普通账号</el-radio>
                    <el-radio v-model="updateForm.type" label="10">管理员</el-radio>
                </el-form-item>
                <el-form-item label="权限:">
                    <!--<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>-->
                    <el-checkbox-group v-model="updateForm.checkList" @change="handleCheckChange">
                        <el-checkbox label="1">人事管理</el-checkbox>
                        <el-checkbox label="2">销售管理</el-checkbox>
                        <el-checkbox label="4">产品管理</el-checkbox>
                        <div style="margin-top:10px;">
                            <el-checkbox label="8">仓库管理</el-checkbox>
                            <el-checkbox label="16">采购管理</el-checkbox>
                            <el-checkbox label="32">品质管理</el-checkbox>
                        </div>
                        <div style="margin-top:10px;">
                            <el-checkbox label="64">设备管理</el-checkbox>
                            <el-checkbox label="128">生产管理</el-checkbox>
                            <el-checkbox label="256">系统管理</el-checkbox>
                        </div>
                    </el-checkbox-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRoles('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                checkAll: false,
                loading: false,
                tableData: [],
                url: '/user/list',
                editVisible: false,
                updateVisible: false,
                userList:'',
                prompt:'',
                pages: 1,
                form: {
                    id: '',
                    employeId: '',
                    employeeNumber:'',
                    userName: '',
                    userAccount: '',
                    status: '',
                    statusDisplay: '',
                    type: '0',
                    typeDisplay: '',
                    checkList: []
                },
                updateForm:{
                    id: '',
                    type: '',
                    checkList: []
                },
                search: {
                    employeId: '',
                    userName: '',
                    userAccount: '',
                    pageNum: 1
                },
                rules: {
                    userName: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    userAccount: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ]
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
                    let type = d.type;
                    d.statusDisplay = status == 0 ? '正常':'停用';
                    d.typeDisplay = type == 0 ? '普通用户':'管理员';
                    return d;
                })
            }
        },
        methods: {
            handleCheckChange (val) {
                console.log(val);
            },
            // 分页导航
            closeing () {
                this.form.employeeNumber = ''
                this.form.department = ''
                this.form.type = '0'
                this.form.type = []
                this.$refs['form'].resetFields()
            },
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    })
            },
            selectEmployeeId(value){
                for(var i=0;i<this.userList.length;i++){
                    if (value == this.userList[i].employeeNumber){
                        this.form.employeeNumber = value;
                        this.form.userName = this.userList[i].name;
                        this.form.employeId = this.userList[i].id;
                        this.form.department = this.userList[i].departmentName
                    }
                }
            },
            selectUserName(value){
                for(var i=0;i<this.userList.length;i++){
                    if (value == this.userList[i].employeeNumber){
                        this.form.employeeNumber = value;
                        this.form.userName = this.userList[i].name;
                        this.form.employeId = this.userList[i].id;
                        this.form.department = this.userList[i].departmentName
                    }
                }
            },
            add() {
                console.log(this.form.checkList);
                this.form.checkList = [];
                this.$http.post("/employee/getListEmployee").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.userList = res.data.data;
                    }
                });
                this.editVisible = true;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            updateStatus(index, row) {
                this.$http.post('/user/status/update', {id: row.id, status: row.status}).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        row.status = row.status == 0 ? 10 : 0;
                        this.$message.success('操作成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            updateRoles(index, row) {
                this.updateForm.id = row.id;
                this.updateForm.type = row.type + '';
                this.updateForm.checkList = [];
                let i = 1;
                while (i <= 256) {
                    let role = row.role & i;
                    if (role > 0) {
                        this.updateForm.checkList.push(i + '');
                    }
                    i = i * 2;
                }
                this.updateVisible = true;
            },
            saveRoles(formName) {
                this.$http.post('/user/saveRole', this.updateForm).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success('操作成功');
                        this.updateVisible = false;
                        this.getData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            // 保存编辑
            insert(formName) {
                this.$http.post("/user/searchByEmployeNumber",{employeNumber:this.form.employeeNumber}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data != null){
                            this.$message.error("该员工已有账号");
                            return false;
                        }else{
                            this.$refs[formName].validate((valid) => {
                                if (valid) {
                                    this.$http.post('/user/insert', this.form).then((res) => {
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
