<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号：">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-button round type="primary" @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column prop="inventoryNum" label="库存数">
                </el-table-column>
               <!-- <el-table-column prop="safeStock" label="安全库存数">
                </el-table-column>-->
                <el-table-column prop="requiredByOrders" label="订单需求数">
                </el-table-column>
                <el-table-column label="参数列表">
                    <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                        <template slot-scope="scope">
                            {{scope.row.materialParamList[0].parameterValue}}
                        </template>
                    </el-table-column>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="detail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
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
                url: "/material/getMaterialBomlist",
                pages: 1,
                search: {
                    shelfPosition:'',
                    materialCode:'',
                    materialName:'',
                    status: 1,
                    pageNum: 1
                },
                searchParams:[]
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
            getData() {
                this.$http.post(this.url,this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.pages;
                    }
                });
                this.$http.post("/material/paramNames").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.searchParams = res.data.data;
                    }
                });
            },
            detail(row){
                this.$router.push({
                    path: "/materialInfoInfo",
                    query: {
                        row:row
                    }
                });
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.shelfPosition = ''
            }
        },
        watch: {
            // 如果路由发生变化，再次执行该方法
            $route: "getData"
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
