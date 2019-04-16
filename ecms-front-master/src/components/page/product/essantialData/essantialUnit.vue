<template>
    <div class="container">
        <el-form :model="parentForm" ref="parentForm" :inline="true" label-width="100px">
            <el-form-item label="物料材料编号">
                <el-input v-model="parentForm.number" disabled></el-input>
            </el-form-item>
            <el-form-item label="物料材料名称">
                <el-input v-model="parentForm.type" disabled></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-input v-model="parentForm.status" disabled></el-input>
            </el-form-item>
        </el-form>
        <hr class="marginTop" style="margin-bottom: 10px"/>
        <div class="form">
            <el-form
                    @keyup.enter.native="getData"
                    :inline="true"
                    class="demo-form-inline"
                    label-width="120px"
                    :model="search">
                <el-form-item label="物料单位编号：">
                    <el-input v-model="search.number"></el-input>
                </el-form-item>
                <el-form-item label="物料单位名称：">
                    <el-input v-model="search.type"></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select v-model="search.status" clearable>
                        <el-option
                                v-for="(item, index) in options"
                                :key="index"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-button @click="searchBy">查询</el-button>
            </el-form>
        </div>
        <div class="materialList">
            <div class="handle-box" style="margin-top: 10px">
                <span class="text">物料单位列表</span>
                <el-button round type="primary" @click="add" style="margin-bottom: 10px">新增物料单位</el-button>
            </div>
            <div class="materialTable">
                <el-table v-loading="loading" :data="tables" border style="width:100%">
                    <el-table-column label="序号" prop="id" align="center" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column label="物料单位编号" prop="number" align="center"></el-table-column>
                    <el-table-column label="物料单位名称" prop="type" align="center"></el-table-column>
                    <el-table-column label="状态" prop="statusDisplay" align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" @click="updateStatus(scope.$index, scope.row)">
                                <font v-if="scope.row.status=='1'">停用</font>
                                <font v-else>启用</font>
                            </el-button>
                            <el-button type="text" @click="updateInfo(scope.$index, scope.row)">
                                编辑
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination :page-size="search.pageSize" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="total">
                    </el-pagination>
                </div>
            </div>
        </div>

        <el-dialog title="新增物料单位" @close="closeing" :visible.sync="editVisible" width="24%">
            <el-form ref="addForm" :rules="rules" :model="addForm" label-width="120px">
                <el-form-item label="物料单位编号:" prop="number">
                    <el-input :maxlength="2" style="width: 215px;" v-model="addForm.number"></el-input>
                </el-form-item>
                <el-form-item label="物料单位名称:" prop="type">
                    <el-input style="width: 215px;" v-model="addForm.type"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="addForm.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addForm')">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "essantialType",
        data () {
            return {
                loading: false,
                tableData: [],
                editVisible:false,
                total: 1,
                search: {
                    type: '',
                    number: '',
                    status: '',
                    productOriginalId: '',
                    pageNum: 1,
                    pageSize: 20
                },
                rules: {
                    number: [
                        { required: true, message: '请输入单位编号', trigger: 'blur' }
                    ],
                    type: [
                        { required: true, message: '请输入单位名称', trigger: 'blur' }
                    ]
                },
                options: [
                    {
                        value: 1,
                        label: '启用'
                    },
                    {
                        value: 0,
                        label: '停用'
                    }
                ],
                parentForm:{
                    number:'',
                    type:'',
                    status:'',
                },
                addForm:{
                    status:'1',
                    number:'',
                    type:'',
                    productOriginalId:'',
                },
            }
        },
        mounted () {
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
            searchBy(){
                this.search.pageNum = 1
                this.getData()
            },
            closeing () {
                this.addForm.status = '1'
                this.$refs['addForm'].resetFields()
            },
            getData(){
                this.search.productOriginalId = this.$route.query.row.id;
                this.parentForm = this.$route.query.row
                this.$http.post('/productUnit/searchProductUnitPager', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.total = res.data.data.total;
                    }
                })
            },
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            updateStatus(index, row) {
                this.$http.post('/productUnit/status/update', {
                    id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        row.status = row.status == 0 ? 1 : 0;
                        this.$message.success('操作成功');
                        this.getData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            updateInfo(index, row) {
                this.$router.push({
                    path: "/essantialName",
                    query: {
                        row: row
                    }
                });
            },
            add() {
                this.editVisible = true;
            },
            insert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.addForm.productOriginalId = this.$route.query.row.id;
                        this.addForm.number = this.addForm.number.toUpperCase();
                        this.$http.post('/productUnit/insertProductUnit', this.addForm).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.editVisible = false;
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
                if (to.path == "/essantialUnit" && this.$route.query.works != 1) {
                    this.getData();
                }
            }
        }
    }
</script>

<style scoped>

</style>