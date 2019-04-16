<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="工艺编号:">
                        <el-input v-model="search.processCode"></el-input>
                    </el-form-item>
                    <el-form-item label="工艺名称:">
                        <el-input v-model="search.processName"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                        <el-select v-model="search.status" placeholder="请选择">
                            <el-option value="1"  label="启用"></el-option>
                            <el-option value="0"  label="停用"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">工艺列表</span>
                <el-button round type="primary" @click="add">新增工艺</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column align="center" label="工艺编号" prop="processCode" sortable>
                </el-table-column>
                <el-table-column align="center" label="工艺名称" prop="processName">
                </el-table-column>
                <el-table-column align="center" label="说明" prop="remark">
                </el-table-column>
                <el-table-column align="center" label="默认工艺" prop="defaultProcess">
                    <template slot-scope="scope">
                        <div v-if="scope.row.defaultProcess == 1"><input type="checkbox" checked disabled/></div>
                        <div v-else><input type="checkbox" disabled/></div>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="状态" prop="statusDisplay">
                </el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button type="text" @click="updateDefaultProcess(scope.$index, scope.row)">设置默认工艺</el-button>
                        <el-button type="text" @click="searchOne(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/process/searchProcessPager",
                pages: 1,
                search: {
                    id:'',
                    status: '',
                    processName:'',
                    processCode:'',
                    pageNum: 1,
                    pageSize:20
                },
                form:{
                    defaultProcess:'',
                    status:''
                },
            };
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    let status = d.status;
                d.statusDisplay = status == 1 ? '启用':'停用';
                return d;
            })
            }
        },
        methods: {
            searchEnterFun (e) {
                let keyCode = window.event? e.keyCode:e.which;
                if(keyCode == 13){
                    this.searchLike()
                }
            },
            handleSelect(key, keyPath) {},
            //明细
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                };
                this.editVisible = true;
            },
            handleChange(value) {
            },
            updateDefaultProcess(index,row){
                this.$http.post('/process/updateDefaultProcess', {id: row.id}).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                    this.getData();
                    this.$message.success('操作成功');
                } else {
                    this.$message.error(res.data.message);
                }
            })
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
            getData() {
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
            },
            clearData() {
                (this.search.id = ""),
                    (this.search.processName = ""),
                    (this.search.status = "")
            },
            add() {
                this.$router.push({
                    path: "/processInfo"
                });
            },
            searchOne(row) {
                this.$router.push({
                    path: "/processSearch",
                    query: {
                        processId: row.id
                    }
                });
            },
            updateStatus(index, row) {
                this.$http.post('/process/updateStatus', {id: row.id}).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                    row.status = row.status == 0 ? 1 : 0;
                    this.$message.success('操作成功');
                } else {
                    this.$message.error(res.data.message);
                }
            })
            },
            // 保存编辑
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('/process/saveProcess', this.addform).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                            this.getData();
                            this.addVisible = false;
                            this.$message.success('新增成功');
                        } else {
                            this.$message.error(res.data.message);
                        }
                    })
                    } else {
                        this.$message.error('带*为必填项');
                return false;
            }
            });
            }
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/processList') {
                    this.getData();
                }
            }
        },
    };
</script>
