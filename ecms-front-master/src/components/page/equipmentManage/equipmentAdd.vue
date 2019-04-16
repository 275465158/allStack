<template>
    <div class="table">
        <div class="container" v-loading="loading">
            <div class="handle-box">
                <el-form :inline="true" ref="form" :model="form" class="demo-form-inline">
                    <el-form-item label="设备编号:">
                        <el-input v-model="form.equipmentNumber" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="设备名称:">
                        <el-input v-model="form.equipmentName"></el-input>
                    </el-form-item>
                    <el-form-item label="设备型号:">
                        <el-input v-model="form.equipmentModel"></el-input>
                    </el-form-item>
                    <el-form-item label="厂家:">
                        <el-input v-model="form.vender"></el-input>
                    </el-form-item>
                    <el-form-item label="出厂日期:">
                        <el-col :span="11">
                            <el-date-picker type="date" placeholder="选择日期" v-model="form.produceDate" style="width: 150px"></el-date-picker>
                        </el-col>
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
                        <el-input v-model="form.equipmentPrice"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                        <el-select v-model="equipmentStatus" placeholder="请选择">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                            <!--<el-option key="zc" label="正常" value="0"></el-option>
                            <el-option key="by" label="保养" value="1"></el-option>
                            <el-option key="wx" label="维修" value="2"></el-option>
                            <el-option key="ty" label="停用" value="3"></el-option>-->
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">人员列表</span>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="工号" sortable >
                </el-table-column>
                <el-table-column prop="name" label="姓名" >
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
                loading: false,
                tableData: [],
                userUrl: '/employee/listEmployee',
                departmentUrl: '/department/list',
                postUrl: '/post/all',
                url: '/equipmentManage/equiInsert',
                initUrl: 'equipmentManage/getMaxEquipmentList',
                editVisible: false,
                pages: 1,
                form: {
                    vender: '',
                    equipmentName: '',
                    equipmentNumber: '',
                    produceDate: '',
                    equipmentModel: '',
                    equipmentPrice: 0,
                    equipmentStatus:'',
                    deptId: '',
                    postId:'',
                    userList: [],
                    pageNum: 1
                },
                search: {
                    departmentId: '',
                    postId: '',
                    pageNum: 1,
                    pageSize:1000,
                },
                postOptions:[],
                departmentList:[],
                postList:[],
                departmentSearch: {
                    status: 1,
                    pageNum: 1,
                    pageSize:1000,
                },
                options: [{
                    value: '0',
                    label: '正常'
                }, {
                    value: '1',
                    label: '保养'
                }, {
                    value: '2',
                    label: '维修'
                }, {
                    value: '3',
                    label: '停用'
                }],
                equipmentStatus: "0"
            }
        },
        created() {
            this.getData()
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取人员列表数据
            getData() {
                //加载数据列表
                this.$http.post(this.userUrl, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;

                    }
                });
                //获取部门下拉框数据
                this.$http.post(this.departmentUrl, this.departmentSearch).then(res => {
                    if (res.data.code == 1000) {
                        this.departmentList = res.data.data.list;
                        this.form.deptId = res.data.data.list[0].id;
                    }
                });
                //获取岗位下拉框数据
                this.$http.post(this.postUrl, this.departmentSearch).then(res => {
                    if (res.data.code == 1000) {
                        this.postList = res.data.data;
                        this.form.postId = res.data.data[0].id
                    }
                });
                //获取最大一条数据id+1
                this.$http.post(this.initUrl,this.search).then((res) => {
                    if (res.data.code == 1000) {
                        console.log(res.data.data);
                        this.form.equipmentNumber = res.data.data
                    }
                });
            },
            filterTag(value, row) {
                return row.tag === value;
            },

            //根据下拉框选择的部门加载数据列表
            /*deptHandleChange(val) {
                this.search.departmentId = this.form.deptId
                this.getData()
            },*/

            //根据下拉框选择的岗位加载数据列表
            /*postHandleChange(val) {
                this.search.postId = this.form.postId
                this.getData()
            },*/

            //多选
            handleSelectionChange(val) {
                this.multipleSelection = val
                this.form.userList = this.multipleSelection
            },
            // 保存
            save() {
                this.insert()
            },
            //取消 跳转到列表页面
            cancel() {
                this.$router.push({ path: '/equipmentList', query: {closeFlag: 1}})
            },
            // 保存编辑
            insert() {
                this.form.equipmentStatus = this.equipmentStatus
                this.loading = true
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
                    this.loading = false
                })
                    .catch(err => {
                        this.loading = false
                    })
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/equipmentAdd' && this.$route.query.works !== 1) {
                    this.$refs['form'].resetFields()
                    this.form.equipmentName = ''
                    this.form.equipmentModel = ''
                    this.form.vender = ''
                    this.form.produceDate = ''
                    this.form.equipmentPrice = 0
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
