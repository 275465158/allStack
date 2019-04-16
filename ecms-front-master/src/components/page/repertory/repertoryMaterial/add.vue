<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-row>
                        <el-form-item label="物料编码:">
                            <el-input v-model="search.materialCode"></el-input>
                        </el-form-item>
                        <el-form-item label="工厂内部编号:">
                            <el-input v-model="search.factoryMaterialCode"></el-input>
                        </el-form-item>
                        <el-form-item label="物料类型:">
                            <el-input v-model="search.type"></el-input>
                        </el-form-item>
                        <el-form-item label="物料名称:">
                            <el-input v-model="search.materialName"></el-input>
                        </el-form-item>
                        <el-form-item label="规格:">
                            <el-input v-model="search.materialBomParamValueStr"></el-input>
                        </el-form-item>
                        <el-form-item label="图号:">
                            <el-input v-model="search.drawingCode"></el-input>
                        </el-form-item>
                        <el-form-item label="来源:">
                            <el-select style="width: 200px;" v-model="search.source">
                                <el-option label="全部" value=""></el-option>
                                <el-option label="自制" value="自制"></el-option>
                                <el-option label="外购" value="外购"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <el-button round type="primary" @click="searchLike">查询</el-button>
                        <el-button round @click="clearData">清空</el-button>
                    </el-row>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="物料编码" prop="materialCode">
                </el-table-column>
                <el-table-column label="工厂内部编号" prop="factoryMaterialCode">
                </el-table-column>
                <el-table-column label="物料类型" prop="type">
                </el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="材质" prop="originalMaterial"></el-table-column>
                <el-table-column label="规格" prop="materialBomParamValueStr"></el-table-column>
                <el-table-column label="图号" prop="drawingCode"></el-table-column>
                <el-table-column label="来源" prop="source"></el-table-column>
                <el-table-column label="制作人" prop="author"></el-table-column>
                <el-table-column label="制作时间" prop="materialBomCreated"></el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div class="handle-box">
                <el-button round type="primary" @click="save">保 存</el-button>
                <el-button round @click="cancel">取 消</el-button>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/material/getAddMaterialBomList",
                insertUrl: "/material/addRepertoryMaterial",
                pages: 1,
                search: {
                    status: 1,
                    pageNum: 1,
                    pageSize:20,
                    materialCode:'',
                    materialName:'',
                    author:'',
                    repertoryId:'',
                    factoryMaterialCode:'',
                    type:'',
                    materialBomParamValueStr:'',
                    drawingCode:'',
                    source:''
                },
                form: {
                    materialList: [],
                    repertoryId:''
                },
                loading: false
            };
        },
        created() {
            this.form.repertoryId = this.$route.query.repertoryId
            this.search.repertoryId = this.$route.query.repertoryId
            this.getData();
        },
        computed: {
            tables() {
                return this.tableData.filter(d => {
                    return d;
                });
            }
        },
        mounted(){
            // 取到路由带过来的规格
            this.form.repertoryId = this.$route.query.repertoryId
        },
        methods: {
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            handleSelect(key, keyPath) {},
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            getData() {
                this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    this.loading = false
                }).catch(err => {
                    this.loading = false
                });
            },
            clearData() {
                this.search.materialCode = '',
                this.search.materialName = '',
                this.search.author = ''
                this.search.factoryMaterialCode = ''
                this.search.type = ''
                this.search.materialBomParamValueStr = ''
                this.search.drawingCode = ''
                this.search.source = ''
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.form.materialList = this.multipleSelection
            },
            save() {
                this.insert()
            },
            //取消 跳转到列表页面
            cancel() {
                this.$router.push({ path: '/materialRepertoryList', query: {repertoryId: this.$route.query.repertoryId, repertoryName: this.$route.query.repertoryName}})
            },
            // 保存编辑
            insert() {
                if(this.form.materialList.length == 0){
                    this.$message.error('请选择物料');
                }else{
                    this.$http.post(this.insertUrl, this.form).then((res) => {
                        if (res.data.code == 1000) {
                            // this.$set(this.tableData, 0, this.form);
                            this.editVisible = false;
                            this.$message.success('添加成功');
                            //跳转到列表页面
                            this.$router.push({ path: '/materialRepertoryList',query:{repertoryId:this.form.repertoryId, repertoryName: this.$route.query.repertoryName}})
                        } else {
                            this.$message.error(res.data.message);
                        }
                    })
                }
            }
        },
        watch: {
            // 如果路由发生变化，再次执行该方法
            // $route: "getData"
        }
    };
</script>
