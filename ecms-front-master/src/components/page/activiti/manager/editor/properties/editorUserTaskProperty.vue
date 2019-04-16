<template>
    <div>
        <div>
            <el-form>
                <el-form-item label="节点名称">
                    <el-input v-model="taskName" @input="updateName"/>
                </el-form-item>
                <el-form-item label="任务执行人">
                    <el-input v-model="assignee.name"/>
                    <el-button size="small" @click="assigneeSelectorVisible=true">...</el-button>
                </el-form-item>
                <!-- <el-form-item label="任务执行组">
                    <el-input v-model="assigneeGroup.name"/>
                    <el-button size="small" @click="assigneeGroupSelectorVisible=true">...</el-button>
                </el-form-item>-->
            </el-form>
        </div>
        <div v-show="bpmVisible">
            <el-form></el-form>
        </div>
        <el-dialog title="选择任务执行人" :visible.sync="assigneeSelectorVisible" width="80%">
            <el-form label-width="100px" :inline="true" :model="userSearch">
                <el-form-item label="姓名">
                    <el-input v-model="userSearch.name"/>
                </el-form-item>
                <el-form-item label="部门">
                    <el-input v-model="userSearch.departmentName"/>
                </el-form-item>
                <el-button @click="searchUser">查询</el-button>
            </el-form>
            <el-table :data="userTables">
                <el-table-column label="姓名" prop="userName"/>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="assigneeSelected(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleCurrentChange"
                    layout="total,prev, pager, next"
                    :total="userPages"
                ></el-pagination>
            </div>
        </el-dialog>
        <el-dialog title="选择任务执行用户组" :visible.sync="assigneeGroupSelectorVisible" width="80%">
            <el-form label-width="100px" :inline="true" :model="groupSearch">
                <el-form-item label="部门">
                    <el-input v-model="groupSearch.departmentName"/>
                </el-form-item>
                <el-button>查询</el-button>
            </el-form>
            <el-table :data="groupTables">
                <el-table-column label="名称" prop="departmentName"/>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="assigneeGroupSelected(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleGroupCurrentChange"
                    layout="total,prev, pager, next"
                    :total="groupPages"
                ></el-pagination>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
    name: "EditorUserTaskProperty",
    data() {
        return {
            assigneeSelectorVisible: false,
            assigneeGroupSelectorVisible: false,
            usertaskVisible: false,
            bpmVisible: false,
            userTableData: [],
            groupTableData: [],
            userPages: 1,
            groupPages: 1,
            userSearch: {
                employeeNumber: "",
                name: "",
                postName: "",
                departmentName: "",
                status: "",
                pageNum: 1,
                pageSize: 20
            },
            groupSearch: {
                departmentName: "",
                adminName: "",
                status: "",
                pageNum: 1,
                pageSize: 20
            }
        };
    },
    props: {
        option: {
            type: Object
        }
    },
    created() {
        this.getData();
        this.getGroupData();
    },
    computed: {
        ...mapState("editor", ["nodeData", "lineData", "selectedNode"]),
        taskName: {
            get: function() {
                return this.selectedNode.name;
            },
            set: function() {}
        },
        assignee() {
            if (this.selectedNode.property != undefined) {
                return this.selectedNode.property.assignee;
            }
            return null;
        },
        assigneeGroup() {
            if (this.selectedNode.property != undefined) {
                return this.selectedNode.property.assigneeGroup;
            }
            return null;
        },
        userTables() {
            return this.userTableData.filter(d => {
                let status = d.status;
                d.statusDisplay = status == 0 ? "离职" : "在职";
                return d;
            });
        },
        groupTables() {
            return this.groupTableData.filter(d => {
                return d;
            });
        }
    },
    methods: {
        ...mapMutations("editor", ["UPDATE_SELECTED_NODE", "UPDATE_NODE"]),
        assigneeSelected(row) {
            const {
                id,
                name,
                type,
                view,
                property,
                top,
                left,
                text,
                imgSrc
            } = this.selectedNode;
            this.UPDATE_NODE({
                [id]: {
                    ...this.nodeData[id],
                    property: {
                        assignee: { id: row.id, name: row.userName },
                        assigneeGroup: property.assigneeGroup,
                        usertaskassignment: {
                            assignment: {
                                type: "static",
                                assignee: row.name
                            }
                        }
                    }
                }
            });
            this.UPDATE_SELECTED_NODE({
                ...this.selectedNode,
                id: id,
                property: {
                    assignee: { id: row.id, name: row.userName },
                    usertaskassignment: {
                        assignment: {
                            type: "static",
                            assignee: row.name
                        }
                    },
                    assigneeGroup: property.assigneeGroup
                }
            });

            this.assigneeSelectorVisible = false;
        },
        assigneeGroupSelected(row) {
            const {
                id,
                name,
                type,
                view,
                property,
                top,
                left,
                text,
                imgSrc
            } = this.selectedNode;
            this.UPDATE_NODE({
                [id]: {
                    ...this.nodeData[id],
                    property: {
                        assignee: property.assignee,
                        assigneeGroup: { id: row.id, name: row.departmentName }
                    }
                }
            });
            this.UPDATE_SELECTED_NODE({
                ...this.selectedNode,
                id: id,
                property: {
                    assignee: property.assignee,
                    assigneeGroup: { id: row.id, name: row.departmentName }
                }
            });

            this.assigneeGroupSelectorVisible = false;
        },
        updateName(ev) {
            let updatedName = ev;
            const {
                id,
                name,
                type,
                view,
                property,
                top,
                left,
                text,
                imgSrc
            } = this.selectedNode;
            this.UPDATE_NODE({
                [id]: {
                    ...this.nodeData[id],
                    name: updatedName
                }
            });
            this.UPDATE_SELECTED_NODE({
                ...this.selectedNode,
                name: updatedName
            });
        },
        // 分页导航
        handleCurrentChange(val) {
            this.userSearch.pageNum = val;
            this.getData();
        },
        handleGroupCurrentChange(val) {
            this.groupSearch.pageNum = val;
            this.getGroupData();
        },
        searchUser() {
            this.$http
                .post("/bpm/searchUserList", this.userSearch)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.userTableData = res.data.data.list;
                        this.userPages = res.data.data.total;
                    }
                });
        },
        getData() {
            this.$http.post("/bpm/userList", this.userSearch).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.userTableData = res.data.data.list;
                    this.userPages = res.data.data.total;
                }
            });
        },
        getGroupData() {
            this.$http.post("/department/first", this.groupSearch).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.groupTableData = res.data.data.list;
                    this.groupPages = res.data.data.total;
                }
            });
        }
    }
};
</script>

<style lang="scss">
</style>
