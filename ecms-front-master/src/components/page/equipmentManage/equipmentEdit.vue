<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="form" class="demo-form-inline">
                    <el-form-item label="设备编号:">
                        <el-input v-model="form.equipmentNumber" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="设备名称:">
                        <el-input v-model="form.equipmentName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="设备型号:">
                        <el-input v-model="form.equipmentModel" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="厂家:">
                        <el-input v-model="form.vender" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="出厂日期:">
                        <el-input v-model="form.produceDate" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="所属部门:" prop="">
                        <el-select v-model="form.deptId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in departmentList"
                                    :key="item.id"
                                    :label="item.departmentName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属岗位:" prop="">
                        <el-select v-model="form.postId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in postList"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="价格:">
                        <el-input v-model="form.equipmentPrice"disabled></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                        <el-select v-model="equipmentStatus" placeholder="请选择">
                            <el-option key="zc" label="正常" value="0"></el-option>
                            <el-option key="by" label="保养" value="1"></el-option>
                            <el-option key="wx" label="维修" value="2"></el-option>
                            <el-option key="ty" label="停用" value="3"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">人员列表</span>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55">
                    <template slot-scope="scope">
                        <el-checkbox :checked="scope.row.equiFlag == 1"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="工号" sortable>
                </el-table-column>
                <el-table-column prop="name" label="姓名">
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div class="handle-box">
                <el-button round type="primary" @click="save">保 存</el-button>
                <el-button round type="primary" @click="cancel">取 消</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                rowData: '',
                tableData: [],
                defaultUserData:[],
                userUrl: '/employee/listEmployee',
                departmentUrl: '/department/list',
                postUrl: '/post/all',
                defaultUserUrl:'/equipmentUser/getUserList',
                editVisible: false,
                pages: 1,
                form: {
                    vender: '',
                    equipmentName: '',
                    equipmentNumber: '',
                    produceDate: '',
                    equipmentModel: '',
                    equipmentPrice: '',
                    equipmentStatus:'',
                    deptId: '',
                    postId:'',
                    userList: [],
                    pageNum: 1
                },
                search: {
                    departmentId: '',
                    postId: '',
                    pageNum: 1
                },
                defaultUser: {
                    equipmentNumber: '',
                    pageNum: 1
                },
                departmentList:[],
                postList:[],
                options:[],
                postOptions:[],
                rowDataObj:'',
                equipmentStatus: '',
                departmentSearch: {
                    status: 1,
                    pageNum: 1
                }
            }
        },
        created() {
            this.getData()
        },
        mounted(){
            // this.checked1();//每次更新了数据，触发这个函数即可。
            // 取到路由带过来的参数
            this.rowDataObj = this.$route.params.rowDataObj

            this.form = this.$route.params.rowDataObj
            this.equipmentStatus = this.rowDataObj.equipmentStatus.toString()
            /*this.search.postId = this.rowDataObj.postId
            this.search.departmentId = this.rowDataObj.deptId*/
            this.defaultUser.equipmentNumber = this.rowDataObj.equipmentNumber

        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取人员列表数据
            getData() {
                this.defaultUser.equipmentNumber = this.$route.params.rowDataObj.equipmentNumber
                this.form = this.$route.params.rowDataObj
                //加载已选择员工数据列表
                this.$http.post(this.defaultUserUrl, this.defaultUser).then((res) => {
                    if (res.data.code == 1000) {
                        this.defaultUserData = res.data.data
                    }
                });
                //加载数据列表
                this.$http.post(this.userUrl, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        for(var i=0;i<res.data.data.list.length;i++){
                            for(var j=0;j<this.defaultUserData.length;j++){
                                if(res.data.data.list[i].id == this.defaultUserData[j].employeeId){
                                    res.data.data.list[i].equiFlag = 1;
                                }
                            }
                        }
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                });
                //获取部门下拉框数据
                this.$http.post(this.departmentUrl, this.departmentSearch).then(res => {
                    if (res.data.code == 1000) {
                        this.departmentList = res.data.data.list;
                    }
                });
                //获取岗位下拉框数据
                this.$http.post(this.postUrl, this.departmentSearch).then(res => {
                    if (res.data.code == 1000) {
                        this.postList = res.data.data;
                    }
                });
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.form.userList = this.multipleSelection
            },
            // 保存
            save() {
                this.update()
            },
            //取消 跳转到列表页面
            cancel() {
                this.$router.push({ path: '/equipmentList' })
            },
            // 保存编辑
            update() {
                this.$http.post(this.url, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.$set(this.tableData, 0, this.form);
                        this.editVisible = false;
                        this.$message.success('新增成功');
                        //跳转到列表页面
                        this.$router.push({ path: '/equipmentList' })
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            }
        },
        watch: {
            '$route' (to, arrivedForm) {
                if (to.path == '/equipmentEdit') {
                    let newId = this.$route.params.rowDataObj.equipmentNumber
                    let newForm = this.rowDataObj;
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if (newForm == undefined) {
                        newForm = "";
                    }
                    if (this.defaultUser.equipmentNumber != newId) {
                        this.defaultUser.equipmentNumber = newId;
                        this.form = this.$route.params.rowDataObj;
                        this.getData();
                    }
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
