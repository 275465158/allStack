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
                    <el-menu-item index="/productionIssue">生产发料</el-menu-item>
                    <el-menu-item index="/sendDeliveryList">发货</el-menu-item>
                </el-menu>
            </div>
            <!--<div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>-->
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="物料批次:">
                        <el-input v-model="search.materialBatch"></el-input>
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
                <span class="el-form-item__label">物料列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号">
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
                <el-table-column prop="materialBatch" label="物料批次">
                </el-table-column>
                <el-table-column prop="storageLocation" label="库位">
                </el-table-column>
                <el-table-column prop="outboundDate" label="出库时间">
                </el-table-column>
                <el-table-column prop="takeMaterialName" label="领料人">
                </el-table-column>
                <el-table-column prop="pickingUseage" label="领料用途">
                </el-table-column>
                <el-table-column prop="qty" label="出库数量">
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
                url: "/materialRepertoryOutboundlog/like",
                pages: 1,
                search: {
                    pageSize:20,
                    materialCode:'',
                    materialName:'',
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0,
                    storageLocation: ''
                },
                repertoryName:'',
                loading: false
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
                    query: { repertoryId: this.search.repertoryId,repertoryName :this.$route.query.repertoryName }
                });
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            getData() {
                this.search.repertoryId = this.$route.query.repertoryId;
                this.repertoryName = this.$route.query.repertoryName
                if(this.$route.query.repertoryId != null && this.$route.query.repertoryId != undefined){
                    let _this = this
                    _this.loading=true
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
                }
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.materialBatch = ''
                this.search.source = ''
                this.search.drawingCode = ''
                this.search.materialBomParamValueStr = ''
                this.search.originalMaterial = ''
                this.search.number = ''
                this.search.factoryMaterialCode = ''
            }
        },
        watch: {
            '$route' (to, search) {
                if (to.path == '/outboundLogList') {
                    let newId = this.$route.query.repertoryId;
                    let newRepertoryName = this.$route.query.repertoryName;
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if (this.search.repertoryId != newId) {
                        this.search.repertoryId = newId;
                        this.getData();
                    }
                    this.repertoryName = newRepertoryName
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
        height: 73px;
        width: 100px;
        float: left;
        text-align: center;
        font-size:15px
    }
</style>
