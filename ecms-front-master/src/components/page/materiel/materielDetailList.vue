<template>
     <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materielList">物料列表</el-menu-item>
                    <el-menu-item index="/entryList">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/materielDetailList">出入库明细</el-menu-item>
                    <el-menu-item index="/pickingList">出库管理</el-menu-item>
                    <el-menu-item index="/deliveryList">发货管理</el-menu-item>
                    <!--<el-menu-item index="/materielProduceList">生产发料</el-menu-item>-->
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号：">
                        <el-input v-model="search.materielCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="search.materielName"></el-input>
                    </el-form-item>
                    <el-button round @click="getData" type="primary">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="inventoryDetail.materiel.materialBom.materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="inventoryDetail.materiel.materialBom.materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="inventoryDetail.materiel.materialBom.originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column prop="inventoryDetail.shelfPosition" label="物料位置">
                </el-table-column>
                <el-table-column prop="detailClass" label="出入库类型">
                </el-table-column>
                <el-table-column prop="detail.qty" label="出入库数量">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="getDetail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
                
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
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
            url: "/materiel/detailList",
            pages: 1,
            search: {
                materielCode:'',
                materielName:'',
                status: 1,
                pageNum: 1,
                repertoryId: 0
            }
        };
    },
    created() {
        if (this.$route.query.repertoryId != undefined) {
            this.search.repertoryId = this.$route.query.repertoryId;
        }
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                let qty = 0;
                if (d.detail.qty != undefined) {
                    qty = d.detail.qty;
                }
                if (d.detail.entryQty != undefined) {
                    qty = d.detail.entryQty;
                }
                d.detail.qty = qty;
                let detailType = "出库";
                if (d.detailClass == "com.ecms.bean.DeliveryDetailInfo") {
                    detailType = "发货出库";
                } else if (d.detailClass == "com.ecms.bean.PickingDetailInfo") {
                    detailType = "生产发料";
                } else if (
                    d.detailClass == "com.ecms.bean.WarehousingEntryDetailInfo"
                ) {
                    detailType = "采购入库";
                }
                d.detailClass = detailType;
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
        getData() {
            this.$http.post(this.url, this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
        },
        getDetail(row) {
            if (row.detailClass == "发货出库") {
                this.$router.push({
                    path: "/deliveryInfo",
                    query: {
                        deliveryId: row.brief.id,
                        repertoryId: this.search.repertoryId
                    }
                });
            } else if (row.detailClass == "生产发料") {
                this.$router.push({
                    path: "/pickingInfo",
                    query: {
                        pickingId: row.brief.id,
                        repertoryId: this.search.repertoryId
                    }
                });
            } else if (row.detailClass == "采购入库") {
                this.$router.push({
                    path: "/entryInfo",
                    query: {
                        id: row.brief.id,
                        repertoryId: this.search.repertoryId
                    }
                });
            }
        },
        clearData(){
            this.search.materielCode = "",
            this.search.materielName = ""
        }
    }
};
</script>
<style scoped>
.handle-box {
  margin-bottom: 20px;
}
</style>
