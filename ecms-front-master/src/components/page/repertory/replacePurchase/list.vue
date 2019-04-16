<template>
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
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="合同编号：">
                        <el-input v-model="search.contractId"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号：">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike" type="primary">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">发货记录列表</span>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="contractId" label="合同编号">
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="shelfPosition" label="库存位置">
                </el-table-column>
                <el-table-column prop="sendQty" label="发货数量">
                </el-table-column>
                <el-table-column prop="sendDate" label="发货时间">
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
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
            url: "/materialSendLog/like",
            pages: 1,
            search: {
                status: 1,
                pageNum: 1,
                repertoryId: 0,
                materialCode:'',
                materialName:'',
                pageSize:20,
                contractId:''
            },
            repertoryName:'',
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
        handleSelect(key, keyPath) {
            this.$router.push({
                path: key,
                query: { repertoryId: this.search.repertoryId }
            });
        },
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

            this.search.repertoryId = this.$route.query.repertoryId;
            this.$http.post(this.url, this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
        },
        clearData(){
            this.search.materialCode = ''
            this.search.materialName = ''
            this.search.contractId = ''
        }
    },
    watch: {
        '$route' (to, from) {
                if (to.path == '/sendMaterialLogList') {
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
</style>
