<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" label-width="100px">
                <el-form-item label="流程名称">
                    <el-input v-model="search.deployName"></el-input>
                </el-form-item>
                <el-button round @click="searchDeploy">查询</el-button>
            </el-form>
        </div>
        <!-- <div class="handle-box">
            <el-button round type="primary" @click="add">新增流程</el-button>
        </div>-->
        <el-table :data="tables" border style="width:100%">
            <el-table-column label="流程名称" prop="deployName"></el-table-column>
            <el-table-column label="所属模块" prop="categoryName"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <!-- <el-button @click="detail(scope.row)">查看</el-button> -->
                    <el-button @click="deleteDeploy(scope.row)">删除</el-button>
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
        <!-- 新增流程 -->
        <el-dialog title="新增流程" :visible.sync="addVisible" width="50%">
            <el-form ref="addForm" :model="addForm" label-width="100px">
                <el-form-item label="流程名称" prop="deployName">
                    <el-input v-model="addForm.deployName"></el-input>
                </el-form-item>
                <el-form-item label="所属分类" prop="categoryName">
                    <el-input v-model="addForm.categoryName"></el-input>
                </el-form-item>
                <el-form-item label="部署文件路径" prop="bpmFileUrl">
                    <el-input v-model="addForm.bpmFileUrl"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible=false">取消</el-button>
                <el-button type="primary" @click="deploy('addForm')">确定</el-button>
            </span>
        </el-dialog>
        <!-- 管理流程 -->
        <el-dialog title="管理流程" :visible.sync="editVisible" width="50%">
            <div class="handle-box">
                <label>流程名称：{{editForm.deployName}}</label>
            </div>
            <el-button size="small" @click="addOption()">添加参数</el-button>
            <el-table :data="valList" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column label="流程步骤名称" prop="varName"></el-table-column>
                <!--                 
                <el-table-column label="参数名">
                    <template slot-scope="scope">
                        
                    </template>
                </el-table-column>
                -->
                <el-table-column label="用户id">
                    <template slot-scope="scope">
                        <el-input type="hidden" v-model="scope.row.varKey"></el-input>
                        <el-input v-model="scope.row.varVal"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="delOption(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible=false">取消</el-button>
                <el-button type="primary" @click="modify()">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {
            addVisible: false,
            editVisible: false,
            tableData: [],
            rules: {
                deployName: [{ required: true }],
                categoryName: [{ required: true }],
                bpmFileUrl: [{ required: true }]
            },
            pages: 1,
            search: {},
            addForm: { deployName: "", categoryName: "", bpmFileUrl: "" },
            editForm: { deployName: "", deployId: "" },
            valList: [],
            paramList: []
        };
    },
    created() {
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
            this.$http.post("/activiti/deployList").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                }
            });
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
        }
    }
};
</script>
<style>
</style>