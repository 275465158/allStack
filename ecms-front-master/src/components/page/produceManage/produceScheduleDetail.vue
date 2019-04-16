<template>
    <div class="table">
        <div class="container">
            <!--<div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="每周工作天数:">
                        <el-input v-model="formTitle.workWeekDay" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="每天每人工时:">
                        <el-input v-model="formTitle.workDayHours" readonly></el-input>
                    </el-form-item>
                </el-form>
            </div>-->
            <div class="handle-box">
                <span class="el-form-item__label">{{this.monthStr}}月份生产排班计划</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="scheduleDate" label="日期">
                </el-table-column>
                <el-table-column prop="scheduleWeek" label="星期">
                </el-table-column>
                <el-table-column prop="workStatus" label="状态">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.workStatus" placeholder="请选择" style="width: 100px">
                            <el-option key="zc" label="上班" value="1"></el-option>
                            <el-option key="by" label="休息" value="2"></el-option>
                        </el-select>
                        <!--<span>{{scope.row.workStatus===1?'上班':'休息'}}</span>-->
                    </template>
                </el-table-column>
                <el-table-column prop="workHours" label="工时">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.workHours" type="number" min="0" max="24" @change="editFunc(scope.row)" style="width: 100px"></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="1">
                </el-pagination>
            </div>
            <div class="handle-box">
                <el-button round type="primary" @click="save">保 存</el-button>
                <el-button round @click="cancel">返 回</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: '',
        data() {
            return {
                tableData: [],
                url: '/scheduleDetail/detail',
                updateUrl: '/scheduleDetail/updateDetail',
                editVisible: false,
                /*formTitle: {
                    workWeekDay : '',
                    workDayHours : ''
                },*/
                form: {
                    scheduleDate: '',
                    scheduleWeek: '',
                    workStatus: '',
                    workHours: '',
                    detailList: ''
                },
                search: {
                    scheduleId: '',
                    pageNum: 1
                },
                monthStr:''
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
                    d.status = status == 0 ? '正常':'失效';
                    d.type = type == 0 ? '普通用户':'管理员';
                    return d;
                })
            }
        },
        mounted(){
            // 取到路由带过来的参数
            this.rowDataObj = this.$route.params.rowDataObj
            /*this.formTitle.workWeekDay = this.$route.params.workWeekDay
            this.formTitle.workDayHours = this.$route.params.workDayHours*/
            //初始化参数
            this.search.scheduleId = this.rowDataObj.scheduleId
            this.monthStr = this.search.scheduleId.substr(4,6)
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data;
                    }
                })
            },
            editFunc(val) {
            },
            save() {
                this.form.detailList = this.tableData
                this.$http.post(this.updateUrl, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.$message.success('修改成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            cancel() {
                this.$router.push({path: '/produceSchedule'})
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/produceScheduleDetail') {
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
