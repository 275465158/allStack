<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="每周工作天数:">
                        <el-input v-model="setForm.workWeekDay" type="number" min="0"></el-input>
                    </el-form-item>
                    <el-form-item label="每天每人工时:">
                        <el-input v-model="setForm.workDayHours" type="number" min="0"></el-input>
                    </el-form-item>
                    <el-button round @click="setUp" type="primary">设置</el-button>
                </el-form>
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="计划年份:">
                        <el-cascader :options="options" v-model="setForm.scheduleYear" @change="handleChange"></el-cascader>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">月计划</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="scheduleMonth" label="月份">
                </el-table-column>
                <el-table-column prop="workDays" label="上班天数">
                </el-table-column>
                <el-table-column prop="workHours" label="上班工时">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleDetail(scope.$index, scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
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
                url: '/scheduleDetail/scheduleList',
                selectionUrl: '/scheduleDetail/selectionList',
                insertUrl: '/produceSchedule/insertSchedule',
                editVisible: false,
                pages: 1,
                setForm: {
                    workWeekDay: '',
                    workDayHours: '',
                    scheduleYear: [],
                    pageNum: 1
                },
                search: {
                    scheduleYear: '',
                    pageNum: 1
                },
                form: {
                    workMonth: '',
                    workDays: '',
                    workHours: ''
                },
                options: []
            }
        },
        created() {
            this.getData();
            this.getSelectionData();
        },
        watch: {
            '$route': 'fetchData'
        },
        computed: {
            tables(){
                return this.tableData
            }
        },
        methods: {
            fetchData () {
                this.error = this.post = null
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取数据
            getData() {
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data
                        this.pages = 1
                    }
                })
            },
            getSelectionData() {
                this.$http.post(this.selectionUrl, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        for (var i = 0; i < res.data.data.length; i++) {
                            this.options.push({value: res.data.data[i].scheduleYear, label: res.data.data[i].scheduleYear})
                        }
                    }
                })
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleDetail(index,row) {
                this.$router.push({
                    path: 'produceScheduleDetail',
                    name: 'produceScheduleDetail',
                    params: {
                        rowDataObj: row,
                        workWeekDay: this.setForm.workWeekDay,
                        workDayHours: this.setForm.workDayHours
                    }
                })
            },
            setUp(){
                var workWeekDay = this.setForm.workWeekDay
                var workDayHours = this.setForm.workDayHours
                if (workWeekDay > 7) {
                    this.$message.error('每周工作天数不能大于7天！')
                    return false
                } else if (workDayHours > 24) {
                    this.$message.error('每天每人工时不能大于24小时！')
                    return false
                } if (workWeekDay === '') {
                    this.$message.error('请填写每周工作天数！')
                    return false
                } else if (workDayHours === '') {
                    this.$message.error('请填写每天每人工时！')
                    return false
                } else {
                    this.insert()
                }
            },
            // 设置保存
            insert() {
                this.$http.post(this.insertUrl, this.setForm).then((res) => {
                    if (res.data.code == 1000) {
                        this.$set(this.tableData, 0, this.setForm);
                        this.editVisible = false;
                        this.getData();
                        this.$message.success('设置成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            handleChange(val) {
                this.search.scheduleYear = val[0]
                this.getData()
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/produceSchedule') {
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
