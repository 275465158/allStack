<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <span class="text">部门信息</span>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-form :inline="true" :model="departmentForm" class="demo-form-inline">
                    <el-input type="hidden" v-model="departmentForm.id"></el-input>
                    <el-form-item label="部门名称:">
                    <el-input v-model="departmentForm.departmentName"></el-input>
                    </el-form-item>
                    <el-form-item label="部门主管:" prop="departmentAdminId">
                        <el-select v-model="departmentForm.departmentAdminId" clearable placeholder="请选择">
                            <el-option
                            v-for="item in employee"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="部门人数:">
                        <el-input style="width:50px;" :disabled="true" v-model="departmentForm.employees"></el-input>
                    </el-form-item>
                    <el-form-item label="部门总人数:">
                        <el-input style="width:50px;" :disabled="true" v-model="departmentForm.totalEmployees"></el-input>
                    </el-form-item>
                    <el-button @click="saveDepartment">修改</el-button>
                </el-form>
            </div>

            <hr class="marginTop"/>
            <span class="text">员工列表</span>
            <el-button size="small" @click="addEmployee">添加员工</el-button>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-table :data="childEmployeeInfoList" border stripe style="width: 100%">
                    <el-table-column prop="employeeNumber" label="工号" sortable/>
                    <el-table-column prop="name" label="姓名"/>
                    <el-table-column prop="postName" label="岗位"/>
                    <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button size="small" @click="detail(scope.$index, scope.row)">
                                资料
                            </el-button>
                            <el-button size="small" @click="delEmployee(scope.$index, scope.row)">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div v-if="!isThirdDepartment">
            <hr class="marginTop"/>
            <span class="text">下级部门列表</span>
            <el-button size="small" @click="addChild">添加子级部门</el-button>
            <hr class="marginBottom"/>
            <el-table :data="children" border stripe style="width: 100%">
                <el-table-column prop="departmentNumberDisplay" label="部门编号" sortable/>
                <el-table-column prop="departmentName" label="部门名称"/>
                <el-table-column prop="adminName" label="部门主管"/>
                <el-table-column prop="children" label="下级部门数"/>
                <el-table-column prop="employees" label="部门人数"/>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="manager(scope.$index, scope.row)">
                            管理部门
                        </el-button>
                        <el-button size="small" @click="delDepartment(scope.$index, scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            </div>
        </div>

        <!-- 新增子部门 -->
        <el-dialog v-if="!isThirdDepartment" title="新增子部门" :visible.sync="addChildVisible" width="30%">
            <el-form ref="childForm" :model="childForm" label-width="100px">
                <el-form-item label="部门名称:">
                    <el-input v-model="childForm.name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addChildVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 添加员工 -->
        <el-dialog title="添加员工" :visible.sync="simpleEmployee" width="40%">
            <el-table :data="simple" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="employeeNumber" label="工号" sortable width="150">
                </el-table-column>
                <el-table-column prop="name" label="姓名" width="120">
                </el-table-column>
                <el-table-column prop="departmentName" label="原部门">
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="simpleEmployee = false">取 消</el-button>
                <el-button type="primary" @click="saveEmployee">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                currentId: '',
                children: [],
                employee: [],
                childEmployeeInfoList: [],
                simple: [],
                multipleSelection: [],
                url: '/department/index?id=',
                addChildVisible: false,
                simpleEmployee: false,
                isThirdDepartment: false,
                childForm: {
                    name: ''
                },
                departmentForm: {
                    id: '',
                    departmentName: '',
                    departmentAdminId: '',
                    employees: '',
                    totalEmployees: ''
                }
            }
        },
        created() {
            this.currentId = this.$route.query.id;
            if (this.currentId != undefined) {
                this.getData();
            }
        },

        watch: {
            '$route' (to, from) {
                if (to.path == '/departmentInfo') {
                    let id = this.$route.query.id;
                    if (id != undefined) {
                        this.currentId = id;
                        this.getData();
                    } 
                }
            }
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
        methods: {
            getData() {
                this.$http.get(this.url+this.currentId).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.children = res.data.data.children;
                        this.departmentForm = res.data.data.departmentInfo;
                        this.employee = res.data.data.employeeInfoList;
                        this.childEmployeeInfoList = res.data.data.childEmployeeInfoList;
                        this.isThirdDepartment = res.data.data.thirdDepartment;
                    }
                })
            },
            saveDepartment() {
                this.$http.post('/department/update', this.departmentForm).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                        this.$message.success('修改成功');
                    } 
                })
            },
            addChild() {
                this.addChildVisible = true;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            detail(index, row) {
                this.$router.push({path:'/employeeInfo',query:{id:row.id}});
            },
            manager(index, row) {
                this.$router.push({path:'/departmentInfo',query:{id:row.id}});
            },
            delEmployee(index, row) {
                this.$http.post('/employee/removeDepartment', {
                        id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.employee.splice(index, 1);
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getData();
                    }
                })
            },
            delDepartment(index, row) {
                this.$confirm('此操作将永久删除该部门及子部门, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.post('/department/del', {
                        id: row.id
                    }).then((res) => {
                        if (res != undefined && res.data.code == 1000) {
                            this.children.splice(index, 1);
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
            addEmployee() {
                this.simpleEmployee = true;
                this.$http.get('/employee/simple?departmentId='+this.departmentForm.id).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.simple = res.data.data;
                    }
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            saveEmployee() {
                if (this.multipleSelection.length > 0) {
                    let ids = [];
                    this.multipleSelection.forEach(item => {
                        ids.push(item.id);
                    });
                    this.$http.post('/employee/updateDepartment', {
                        departmentId: this.departmentForm.id,
                        idList: ids
                    }).then((res) => {
                        if (res != undefined && res.data.code == 1000) {
                            this.getData();
                            this.simpleEmployee = false;
                            this.$message.success('新增成功');
                        } 
                    })
                }
            },
            // 保存编辑
            insert() {
                this.$http.post('/department/addChild', {
                    name: this.childForm.name,
                    id: this.currentId
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                        this.addChildVisible = false;
                        this.$message.success('新增成功');
                    } 
                })
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 50px;
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
    hr {
        border-top: 1px;
    }
    .marginTop {
        margin-top: 10px;
        margin-bottom: 5px;
    }
    .marginBottom {
        margin-top: 5px;
        margin-bottom: 10px;
    }
    .text {
        font-size: 12px;
        color: #606266;
        margin-right: 30px;
    }
</style>
