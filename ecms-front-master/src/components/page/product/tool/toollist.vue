<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <el-button  size="small" @click="$router.push('/toolTypeList');" >辅助工具类型</el-button>
            <el-button type="primary" size="small" >工具列表</el-button>
            <hr class="marginTop"/>
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="工具编号:">
                    <el-input v-model="search.toolCode"></el-input>
                </el-form-item>
                <el-form-item label="工具名称:">
                    <el-input v-model="search.toolName"></el-input>
                </el-form-item>
                <el-form-item label="工具型号:">
                    <el-input v-model="search.toolModelNumber"></el-input>
                </el-form-item>
                <el-form-item label="工具类型:">
                    <el-select v-model="search.toolTypeId" placeholder="请选择">
                        <el-option v-for="item in this.toolTypeNameList" :key="item.id" :label="item.toolTypeName" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-button round @click="searchList">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <hr class="marginTop"  style="margin-top:-12px;"/>
            <div class="handle-box">
                <span class="el-form-item__label">工具列表</span>
                <el-button round type="primary" @click="add">新增工具</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column label="工具编号" prop="toolCode" sortable>
                </el-table-column>
                <el-table-column label="工具名称" prop="toolName">
                </el-table-column>
                <el-table-column label="工具型号" prop="toolModelNumber">
                </el-table-column>
                <el-table-column label="工具规格" prop="toolSpec">
                </el-table-column>
                <el-table-column label="工具类型" prop="toolTypeName">
                </el-table-column>
                <el-table-column label="工具备注" prop="remark">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="工具详情" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="工具编号:" prop="idToPage">
                    <el-input v-model="form.toolCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="工具名称:" prop="toolName">
                    <el-input v-model="form.toolName" disabled></el-input>
                </el-form-item>
                <el-form-item label="工具型号:" prop="toolModelNumber">
                    <el-input v-model="form.toolModelNumber" disabled></el-input>
                </el-form-item>
                <el-form-item label="工具类型:" prop="toolTypeName">
                    <el-input v-model="form.toolTypeName" disabled></el-input>
                </el-form-item>
                <el-form-item label="备注:" prop="remark">
                    <el-input v-model="form.remark" disabled></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增工具" :visible.sync="addVisible" width="30%">
            <el-form ref="addform" :rules="rules" :model="addform" label-width="100px">
                <el-form-item label="工具名称:" prop="toolName">
                    <el-input v-model="addform.toolName"></el-input>
                </el-form-item>
                <el-form-item label="工具型号:" prop="toolModelNumber">
                    <el-input v-model="addform.toolModelNumber"></el-input>
                </el-form-item>
                <el-form-item label="工具类型:" prop="toolTypeId">
                    <el-select v-model="addform.toolTypeId" placeholder="请选择">
                        <el-option v-for="item in this.toolTypeNameList" :key="item.id" :label="item.toolTypeName" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="工具规格:" prop="toolSpec">
                    <el-input v-model="addform.toolMinSpec" style="width: 30%"></el-input>-<el-input v-model="addform.toolMaxSpec" style="width: 30%"></el-input>
                </el-form-item>
                <el-form-item label="备注:" prop="toolTypeName">
                    <el-input v-model="addform.remark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addform')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/tool/searchToolList",
                editVisible:false,
                addVisible:false,
                rules: {
                    toolName: [
                        { required: true, message: '请输入工具名称', trigger: 'blur' }
                    ],
                    toolModelNumber: [
                        { required: true, message: '请输入工具型号', trigger: 'blur' }
                    ],
                    toolTypeId: [
                        { required: true, message: '请选择工具类型', trigger: 'blur' }
                    ],
                },
                pages: 1,
                search: {
                    status: '',
                    idToPage:'',
                    toolName:'',
                    toolModelNumber:'',
                    toolTypeName:'',
                    toolCode:'',
                    remark:'',
                    pageNum: 1,
                    pageSize:20
                },
                form:{
                    idToPage:'',
                    toolName:'',
                    toolCode:'',
                    toolModelNumber:'',
                    toolTypeName:'',
                    toolSpec:'',
                    remark:'',
                    toolTypeId:'',
                },
                addform:{
                    toolTypeId:'',
                    toolName:'',
                    toolModelNumber:'',
                    toolSpec:'',
                    toolMinSpec:'',
                    toolMaxSpec:'',
                    remark:''
                },
                toolTypeNameList:[],
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
            handleSelect(key, keyPath) {},
            //明细
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    idToPage: item.idToPage,
                    toolName: item.toolName,
                    toolModelNumber: item.toolModelNumber,
                    toolTypeName: item.toolTypeName,
                    toolSpec: item.toolSpec,
                    remark: item.remark,
                    toolCode : item.toolCode
                };
                this.editVisible = true;
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            searchList(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                this.$http.post("/toolType/searchToolTypeList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.toolTypeNameList = res.data.data;
                    if (res.data.data.length!=0){
                        this.addform.toolTypeId = res.data.data[0].id
                        }
                    }
                });
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                        if (this.search.id !="" && this.search.id!=undefined){
                            this.search.id = "TN"+this.search.id
                        }
                    }
                });
            },
            clearData() {
                (this.search.toolCode = ""),
                (this.search.toolName = ""),
                (this.search.toolModelNumber = ""),
                (this.search.toolTypeId = "")
            },
            add() {
                this.addVisible = true;
            },
            updateStatus(index, row) {
                this.$http.post('/toolType/updateStatus', {
                    id: row.id
                }).then((res) => {
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
                        this.$http.post('/tool/saveTool', this.addform).then((res) => {
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
                if (to.path == '/toolList') {
                    this.getData();
                }
            }
        },
    };
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .marginTop {
        margin-top: 10px;
        margin-bottom: 5px;
    }
    .marginBottom {
        margin-top: 5px;
        margin-bottom: 10px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .text {
        font-size: 14px;
        color: #606266;
        margin-right: 30px;
    }
</style>

