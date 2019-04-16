<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-row>
                        <el-form-item label="产品编号:">
                            <el-input v-model="search.materialCode"></el-input>
                        </el-form-item>
                        <el-form-item label="成品编号:">
                            <el-input v-model="search.productCode"></el-input>
                        </el-form-item>
                        <el-form-item label="产品名称:">
                            <el-input v-model="search.materialName"></el-input>
                        </el-form-item>
                        <el-form-item label="制作人:">
                            <el-input v-model="search.author"></el-input>
                        </el-form-item>
                    </el-row>
                    <el-row>
                        <el-form-item label="创建时间:">
                            <el-date-picker v-model="search.created"></el-date-picker>
                        </el-form-item>
                        <el-button round type="primary" @click="searchLike">查询</el-button>
                        <el-button round @click="clearData">清空</el-button>
                    </el-row>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
            </div>
            <el-table :data="tables" border style="width:100%" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <!--<el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>-->
                <el-table-column label="序号" prop="id" sortable>
                </el-table-column>
                <el-table-column label="产品编号" prop="materialCode">
                </el-table-column>
                <el-table-column label="成品编号" prop="productCode">
                </el-table-column>
                <el-table-column label="产品名称" prop="materialName"></el-table-column>
                <el-table-column label="制作人" prop="author"></el-table-column>
                <el-table-column label="制作时间" prop="materialBomCreated"></el-table-column>
                <!--<el-table-column label="合同号"></el-table-column>-->
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
                url: "/material/list",
                insertUrl: "/material/saveOrUpdateData",
                pages: 1,
                search: {
                    status: 1,
                    pageNum: 1
                },
                form: {
                    materialList: [],
                    repertoryId:''
                }
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
        mounted(){
            // 取到路由带过来的参数
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
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                });
            },
            clearData() {
                this.search.materialCode = '',
                this.search.productCode = '',
                this.search.materialName = '',
                this.search.author = '',
                this.search.created =''
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
                this.$router.push({ path: '/materielList' })
            },
            // 保存编辑
            insert() {
                this.$http.post(this.insertUrl, this.form).then((res) => {
                    if (res.data.code == 1000) {
                        // this.$set(this.tableData, 0, this.form);
                        this.editVisible = false;
                        this.$message.success('添加成功');
                        //跳转到列表页面
                        this.$router.push({ path: '/materielList',query:{repertoryId:this.form.repertoryId}})
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
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
