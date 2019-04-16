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
        <div class="handle-box">
            <!-- <el-button round type="primary" @click="add">新增流程</el-button> -->
        </div>
        <el-table :data="tables" border style="width:100%" v-loading="loading">
            <el-table-column label="任务类型" prop="deployName"></el-table-column>
            <el-table-column label="任务名称" prop="processInstanceName"></el-table-column>
            <el-table-column label="流程步骤名称" prop="jobName"></el-table-column>
            <el-table-column label="开始时间" prop="created"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="compileTask(scope.row)">执行任务</el-button>
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
            addVisible: false,
            editVisible: false,
            tableData: [],
            rules: {
                deployName: [{ required: true }],
                categoryName: [{ required: true }],
                bpmFileUrl: [{ required: true }]
            },
            pages: 0,
            search: {
                pageNum: 1,
                pageSize: 20
            },
            addForm: { deployName: "", categoryName: "", bpmFileUrl: "" },
            editForm: { deployName: "", deployId: "" },
            valList: [],
            paramList: []
        };
    },
    mounted() {
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                return d;
            });
        }
    },
    methods: {
        getData() {
            this.loading = true;
            this.$http
                .post("/activiti/queryTask", this.search)
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
                    .post("/activiti/searchTask", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData = res.data.data;
                            this.pages = res.data.data.length;
                            this.loading = false;
                        }
                    })
                    .catch(err => {
                        this.loading = true;
                    });
            } else {
                this.getData();
            }
        },
        detail(row) {
            this.editVisible = true;
            this.editForm.deployName = row.deployName;
            this.editForm.deployId = row.id;
            this.$http
                .post("/activiti/getWfAssignee", { deployId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.valList = [];
                        res.data.data.forEach(assignee => {
                            this.valList.push({
                                varName: assignee.TaskName,
                                varKey: assignee.Assignee.substring(
                                    2,
                                    assignee.Assignee.length - 1
                                ),
                                varVal: ""
                            });
                        });
                        this.$http
                            .post("/activiti/getDeployAssignee", {
                                deployId: row.id
                            })
                            .then(res => {
                                if (res != undefined && res.data.code == 1000) {
                                    this.editForm.id = res.data.data[0].id;
                                    let params = JSON.parse(
                                        res.data.data[0].valJson
                                    );
                                    params.forEach(param => {
                                        this.valList.forEach(val => {
                                            if (param.varKey == val.varKey) {
                                                val.varVal = JSON.stringify(
                                                    param.varVal
                                                );
                                            }
                                        });
                                    });
                                }
                            });
                    }
                });
        },
        deleteDeploy(row) {
            this.$http
                .post("/activiti/removeDeployment", { deployId: "" + row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        deploy(formName) {
            this.$http.post("/activiti/deploy", this.addForm).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.addVisible = false;
                    this.getData();
                }
            });
        },
        searchDeploy() {},
        add() {
            this.addVisible = true;
            this.addForm = {
                deployName: "生产不合格品评审",
                categoryName: "品质管理",
                bpmFileUrl: "bpmn/unqualifiedProductReview.bpmn"
            };
        },
        modify() {
            //this.editVisible = false;
            this.valList.forEach(element => {
                this.paramList.push({
                    varKey: element.varKey,
                    varVal: JSON.parse(element.varVal)
                });
            });
            this.editForm.valJson = JSON.stringify(this.paramList);
            this.$http
                .post("/activiti/modifyAssignee", this.editForm)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.editVisible = false;
                        this.getData();
                    }
                });
        },
        delOption(index, row) {
            //this.valList.splice(index, 1);
            row.varVal = "";
        },
        addOption() {
            let param = {
                varKey: "",
                varVal: ""
            };
            this.valList.push(param);
        },
        compileTask(row) {
            this.$router.push({
                path: row.formKey,
                query: { businessKey: row.businessKey, taskId: row.id }
            });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/taskList") {
                this.getData();
            }
        }
    }
};
</script>
<style>
</style>