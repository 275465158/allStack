<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="仓库编号:">
                    <el-input v-model="search.repertoryCode"></el-input>
                </el-form-item>
                <el-form-item label="仓库名称:">
                    <el-input v-model="search.repertoryName"></el-input>
                </el-form-item>
                <el-form-item label="所属部门:" prop="">
                    <el-select v-model="search.repertoryDepartmentId" clearable placeholder="请选择">
                        <el-option
                                v-for="item in departmentList"
                                :key="item.id"
                                :label="item.departmentName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="仓库管理员:" prop="">
                    <el-select v-model="search.repertoryManager" value-key="id" multiple placeholder="请选择">
                        <el-option
                                v-for="item in employee"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="仓库类型:">
                    <el-select v-model="search.repertoryType">
                        <el-option value="原材料"/>
                        <el-option value="半成品"/>
                        <el-option value="成品"/>
                    </el-select>
                </el-form-item>
                <el-button round type="primary" @click="searchLike">搜索</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
        </div>
        <div class="handle-box">
            <span class="el-form-item__label">仓库列表</span>
            <el-button round type="primary" @click="add">创建仓库</el-button>
        </div>
        <el-table :data="tables" border style="width: 100%">
            <!--<el-table-column prop="id" label="序号" sortable>
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>-->
            <el-table-column prop="id" label="序号" sortable>
            </el-table-column>
            <el-table-column prop="repertoryCode" label="仓库编号" >
            </el-table-column>
            <el-table-column prop="repertoryName" label="仓库名称" >
            </el-table-column>
            <el-table-column prop="repertoryDepartmentId" label="所属部门">
            </el-table-column>
            <el-table-column prop="repertoryType" label="仓库类型">
            </el-table-column>
            <el-table-column prop="repertoryManager" label="仓库管理员">
            </el-table-column>
            <el-table-column prop="created" label="创建时间">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="small" @click="edit(scope.row)">仓库管理</el-button>
                    <el-button size="small" @click="repertoryDetail(scope.$index, scope.row)">进入仓库</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination">
            <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/repertory/list",
                departmentUrl:"/department/list",
                pages: 1,
                search: {
                    status: 1,
                    pageNum: 1
                },
                departmentSearch: {
                    status: 1,
                    pageNum: 1
                },
                departmentList:[],
                employee: [],
            };
        },
        created() {
            this.getData();
        },
        computed: {
            tables() {
                return this.tableData.filter(d => {
                    let repType = d.repertoryType;
                    switch (repType) {
                        case "WG":
                            d.repertoryType = "原材料";
                            break;
                        case "ZZ":
                            d.repertoryType = "半成品";
                            break;
                        case "CP":
                            d.repertoryType = "成品";
                            break;
                    }
                    if (d.repertoryManager != null) {
                        let repertoryManager = JSON.parse(d.repertoryManager);
                        let managerNames = [];
                        for (var i = 0; i < repertoryManager.length; i++) {
                            managerNames.push(repertoryManager[i].employeename);
                        }
                        d.repertoryManager = managerNames.join(",");
                    }
                    return d;
                });
            }
        },
        methods: {
            handleChange(value) {
            },
            // 分页导航
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
                if(this.search.repertoryManager != null && this.search.repertoryManager != ""){
                    let managerSelected = this.search.repertoryManager;
                    let repmanager = [];
                    for (var managerind in managerSelected) {
                        var manager = this.findmanager(
                            managerSelected[managerind],
                            this.employee
                        );
                        repmanager.push({ id: manager.id, employeename: manager.name });
                    }
                    this.search.repertoryManager = JSON.stringify(repmanager);
                }
                this.$http.post(this.departmentUrl, this.departmentSearch).then(res => {
                    if (res.data.code == 1000) {
                        this.departmentList = res.data.data.list;
                    }
                });
                this.$http.post("/employee/get", {}).then(res => {
                    if (res.data.code == 1000) {
                        this.employee = res.data.data;
                    }
                });
                this.$http.post(this.url, this.search).then(res => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                });
            },
            add() {
                this.$router.push("/repertoryInfo");
            },
            edit(row) {
                this.$router.push({
                    path: "/repertoryInfo",
                    query: { repertoryId: row.id }
                });
            },
            repertoryDetail(index, row) {
                this.$router.push({
                    path: "/materialRepertoryList",
                    query: { repertoryId: row.id ,repertoryName:row.repertoryName}
                });
            },
            clearData(){
                this.search.repertoryCode = ''
                this.search.repertoryName = ''
                this.search.departmentId = ''
                this.search.repertoryManager = ''
                this.search.repertoryType = ''
            },
            findmanager(id, arr) {
                var res = arr.find((value, index, arr) => {
                    return value.id == id;
                });
                return res;
            }
        },
        watch: {
            $route(to, from) {
          if (to.path == "/repertoryList") {
              this.getData();
          }
      }
        }
    };
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
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center;
    }
</style>
