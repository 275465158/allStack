<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" label-width="100px">
                <el-form-item label="任务名称">
                    <el-input v-model="search.taskName"></el-input>
                </el-form-item>
                <el-button round @click="searchTask">查询</el-button>
            </el-form>
        </div>
        <el-table :data="tableData" border style="width:100%" v-loading="loading">
            <el-table-column label="任务类型" prop="deployName"></el-table-column>
            <el-table-column label="任务名称" prop="processInstanceName"></el-table-column>
            <el-table-column label="流程步骤名称" prop="jobName"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="审批时间" prop="endTime"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="taskDetail(scope.row)">查看明细</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                :page-size="20"
                @current-change="handleCurrentChange"
                layout="total, prev, pager, next"
                :total="pages"
            ></el-pagination>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            loading: false,
            tableData: [],
            pages: 0,
            search: {
                pageNum: 1,
                pageSize: 20
            },
            valList: [],
            paramList: []
        };
    },
    created() {
        this.getData();
    },
    computed: {},
    methods: {
        getData() {
            this.loading = true;
            this.$http
                .post("/activiti/queryHistory", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data;
                        this.pages = res.data.data.length;
                        this.loading = false;
                    }
                })
                .catch(err => {
                    this.loading = false;
                });
        },
        searchTask() {
            if (this.search.taskName != undefined) {
                this.loading = true;
                this.$http
                    .post("/activiti/searchHistory", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData = res.data.data;
                            this.pages = res.data.data.length;
                            this.loading = false;
                        }
                    })
                    .catch(err => {
                        this.loading = false;
                    });
            } else {
                this.getData();
            }
        },

        handleCurrentChange(val) {
            console.log(val);
            this.search.pageNum = val;
            this.getData();
        },
        taskDetail(row) {
            this.$router.push({
                path: row.formKey,
                query: {
                    businessKey: row.businessKey,
                    taskId: row.id,
                    viewType: "detail"
                }
            });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/taskHistoryList") {
                this.getData();
            }
        }
    }
};
</script>
<style>
</style>