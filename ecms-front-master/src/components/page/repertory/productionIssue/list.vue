<template xmlns:text-align="http://www.w3.org/1999/xhtml">
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materialRepertoryList">库存总表</el-menu-item>
                    <el-menu-item index="/repertoryStayDelivery">待收货</el-menu-item>
                    <el-menu-item index="/repertoryStayInbound">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/inboundLogList">入库明细</el-menu-item>
                    <el-menu-item index="/outboundLogList">出库明细</el-menu-item>
                    <!--<el-menu-item index="/sendMaterialLogList">发货记录</el-menu-item>-->
                    <!--<el-menu-item index="/returnedPurchaseList">退货记录</el-menu-item>
                    <el-menu-item index="/replacePurchaseList">换货记录</el-menu-item>-->
                    <el-menu-item index="/productionIssue">生产发料</el-menu-item>
                    <el-menu-item index="/sendDeliveryList">发货</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="合同编号:">
                        <el-input v-model="search.contractId"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="生产单号:">
                        <el-input v-model="search.productionNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="工厂内部编号:">
                        <el-input v-model="search.factoryMaterialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型:">
                        <el-input v-model="search.number"></el-input>
                    </el-form-item>
                    <el-form-item label="材质:">
                        <el-input v-model="search.originalMaterial"></el-input>
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
                    <el-button round type="primary" @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待发料列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{20*(indexPageNum-1)+scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="contractId" label="合同编号">
                </el-table-column>
                <el-table-column prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column prop="factoryMaterialCode" label="工厂内部编号">
                </el-table-column>
                <el-table-column prop="number" label="物料类型">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="originalMaterial" label="材质">
                </el-table-column>
                <el-table-column prop="materialBomParamValueStr" label="规格">
                </el-table-column>
                <el-table-column prop="drawingCode" label="图号">
                </el-table-column>
                <el-table-column prop="source" label="来源">
                </el-table-column>
                <el-table-column prop="materialUnit" label="单位">
                </el-table-column>
                <el-table-column prop="materialNum" label="生产数量">
                </el-table-column>
                <el-table-column prop="issueQty" label="已发数量">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="detail(scope.row)">发料</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="this.search.pageSize" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
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
                url: "/productionPlan/getProductionIssueList",
                pages: 1,
                search: {
                    pageSize:20,
                    materialCode:'',
                    materialName:'',
                    contractId: '',
                    pageNum: 1
                },
                repertoryId:0,
                loading: false,
                indexPageNum:1
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
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            handleSelect(key, keyPath) {
                this.$router.push({
                    path: key,
                    query: { repertoryId: this.$route.query.repertoryId,repertoryName :this.$route.query.repertoryName }
                });
            },
            // 分页导航
            handleCurrentChange(val) {
                this.indexPageNum = val;
                this.search.pageNum = val;
                this.getData();
            },
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    });
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.contractId = ''
                this.search.productionNumber = ''
                this.search.source = ''
                this.search.drawingCode = ''
                this.search.materialBomParamValueStr = ''
                this.search.originalMaterial = ''
                this.search.number = ''
                this.search.factoryMaterialCode = ''
            },
            detail(row){
                let rows = JSON.stringify(row)
                let repertoryId = this.$route.query.repertoryId
                this.$router.push({
                    path: "/productionIssueInfo",
                    query: { row: rows,repertoryId: repertoryId}
                });
            }
        },
        watch: {
            '$route' (to, search) {
                if (to.path == '/productionIssue') {
                    let newId = this.$route.query.repertoryId;
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if (this.repertoryId != newId) {
                        this.repertoryId = newId;
                        this.getData();
                    }
                }
            }
        }
    };
</script>
<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
    .content {
        height: 100px;
        width: 250px;
        float: left;
        text-align: center;
        font-size:12px;
        margin-left:20px;
        margin-right:20px;
        margin-top:40px;
        margin-bottom:40px;
    }
</style>
