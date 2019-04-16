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
            <!--<div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>-->
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号：">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="采购原因：">
                        <el-input v-model="search.purchaseReason"></el-input>
                    </el-form-item>
                    <el-form-item label="采购编号：">
                        <el-input v-model="search.purchaseCode"></el-input>
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
                    <el-button round @click="searchLike" type="primary">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待入库列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column prop="purchaseCode" label="采购编号">
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
                <el-table-column prop="materialBatch" label="批次号">
                </el-table-column>
                <el-table-column prop="purchaseReason" label="采购原因">
                </el-table-column>
                <el-table-column prop="purchasePrice" label="采购单价">
                </el-table-column>
                <el-table-column prop="checkPersonName" label="检测人">
                </el-table-column>
                <el-table-column prop="materialSource" label="检测来源" :formatter="materialSourceFormatter">
                </el-table-column>
                <el-table-column prop="checkDate" label="检测时间">
                </el-table-column>
                <el-table-column prop="qualifiedQty" label="数量">
                </el-table-column>
                <el-table-column prop="unqualifiedQty" label="不合格数量">
                </el-table-column>
                <el-table-column prop="unqualifiedFlag" label="物料状态" :formatter="formatterStr" >
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="inbound(scope.row)">入库</el-button>
                        <!--<el-button size="small" @click="returnedPurchase(scope.row)">退货</el-button>-->
                        <!--<el-button size="small" @click="replacePurchase(scope.row)">换货</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <el-dialog title="入库" :visible.sync="noticeArrivedVisible" width="60%">
              <el-form ref="arrivedForm" :model="arrivedForm" label-width="100px">
                <el-form-item label="物料名称:">
                    <el-input v-model="arrivedForm.materialName" disabled></el-input>
                </el-form-item>
                  <el-form-item label="供应商:" >
                      <el-input v-model="arrivedForm.supplierName" disabled></el-input>
                  </el-form-item>
                <el-form-item label="批次号">
                  <el-input v-model="arrivedForm.materialBatch" disabled></el-input>
                </el-form-item>
                <el-form-item label="入库数量:">
                  <el-input v-model="arrivedForm.inventoryQty" disabled></el-input>
                </el-form-item>
                <el-form-item label="选择库位">
                    <el-cascader v-model="arrivedForm.storageLocationList" :options="optionsRepertory" placeholder="货架号/层/排/列">
                    </el-cascader>
                </el-form-item>
                <!--<el-form-item label="入库时间:">
                  <el-date-picker v-model="arrivedForm.inboundDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择入库时间" disabled></el-date-picker>
                </el-form-item>-->
                <!--<el-form-item label="入库人:" prop="">
                  <el-select v-model="employeeListQuery" clearable placeholder="请选择入库人" @change="selectEmployeeName" disabled>
                      <el-option
                              v-for="item in this.employeeList"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                      </el-option>
                  </el-select>
                </el-form-item>-->
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertInbound">保存</el-button>
                <el-button @click="noticeArrivedVisible = false">取 消</el-button>
              </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            tableData: [],
            url: "/materialRepertoryIncomingCheck/getStayInboundPage",
            pages: 1,
            search: {
                status: 1,
                pageNum: 1,
                repertoryId: 0,
                materialCode:'',
                materialName:'',
                pageSize:20,
                inboundStatus:2,
                purchaseReason:'',
                purchaseCode:''
            },
            repertoryName:'',
            //对话框现实控制
            noticeArrivedVisible: false,
            //入库
            arrivedForm: {
                purchaseId:0,
                materialId:0,
                materialCode:'',
                materialName:'',
                supplierId:'',
                materialBatch:'',
                inventoryQty:'',
                // inboundDate:new Date(),
                operatingPersonnelId:0,
                repertoryId:0,
                purchaseReason:'',
                purchasePrice:0,
                storageLocation:'',
                storageLocationList:[],
                status: 1,
                pageNum: 1,
                pageSize:20
            },
            supplierList:[],
            employeeList:[],
            optionsRepertory: [],
            // employeeListQuery:'',
            supplierListQuery:'',
            loading: false
        };
    },
    created() {
        this.search.repertoryId = this.$route.query.repertoryId;
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
                query: { repertoryId: this.search.repertoryId, repertoryName: this.repertoryName }
            });
        },
        handleChange(value) {
            console.log(value);
        },
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        getData() {
            this.search.repertoryId = this.$route.query.repertoryId;
            this.arrivedForm.repertoryId = this.$route.query.repertoryId;
            this.repertoryName = this.$route.query.repertoryName;
            if(this.$route.query.repertoryId != null && this.$route.query.repertoryId != undefined){
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                        console.log("this.tableData:",this.tableData)
                        // this.employeeListQuery = res.data.data.list[0].employeeId;
                        this.arrivedForm.operatingPersonnelId = res.data.data.list[0].employeeId;
                        this.arrivedForm.supplierId = res.data.data.list[0].supplierId
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        console.log(err);
                        _this.loading = false
                    });
                this.$http.post('/employee/listEmployeeByInfo', this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.employeeList = res.data.data.list;
                    }
                });
                this.$http.post("/storage/getJson", { repertoryId: this.search.repertoryId }).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.optionsRepertory = JSON.parse(res.data.data);
                    }
                });
            }
        },
        formatterStr(row) {
            if (row.unqualifiedFlag == 0) {
                return "不合格";
            } else {
                return "合格";
            }
        },
        insertInbound() {
            var storageLocationStr = '';
            for(var i = 0;i<this.arrivedForm.storageLocationList.length;i++){
                if(i<this.arrivedForm.storageLocationList.length-1){
                    storageLocationStr += this.arrivedForm.storageLocationList[i]+"_"
                }else{
                    storageLocationStr += this.arrivedForm.storageLocationList[i]
                }
            }
            this.arrivedForm.storageLocation = storageLocationStr;
            this.arrivedForm.repertoryId = this.$route.query.repertoryId;
            this.$http.post("/materialRepertoryDetail/insertInbound", this.arrivedForm).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.$message.success("保存成功!");
                    this.noticeArrivedVisible = false;
                    this.getData();
                }
            });
        },
        inbound(row){
            console.log(row)
            if(row.supplierId != null){
                this.$http.post("/supplier/siList", this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.supplierList = res.data.data;
                        this.pages = res.data.data.pages;
                    }
                });
            }
            this.noticeArrivedVisible = true
            this.arrivedForm.materialCode = row.materialCode
            this.arrivedForm.materialName = row.materialName
            this.arrivedForm.inventoryQty = row.qualifiedQty
            this.arrivedForm.purchaseReason = row.purchaseReason
            this.arrivedForm.repertoryId = row.repertoryId
            this.arrivedForm.purchasePrice = row.purchasePrice
            this.arrivedForm.materialId = row.materialId
            this.arrivedForm.materialBatch = row.materialBatch
            this.arrivedForm.purchaseId = row.id
            this.arrivedForm.purchaseOrdersId = row.purchaseOrdersId
            this.arrivedForm.checkId = row.id
            this.arrivedForm.supplierName = row.supplierName
            this.arrivedForm.supplierId = row.supplierId
            this.arrivedForm.materialSource = row.materialSource
            this.arrivedForm.unqualifiedQty = row.unqualifiedQty
            this.arrivedForm.unqualifiedFlag = row.unqualifiedFlag
        },
        returnedPurchase(row){
            this.$router.push({
                path: "/returnedPurchaseList",
                query: {materialId:row.materialId,purchaseCode:row.purchaseCode,supplierName:row.supplierName,repertoryId:this.$route.query.repertoryId}
            });
        },
        replacePurchase(row){
            this.$router.push({
                path: "/replacePurchaseList",
                query: {materialId:row.materialId,purchaseCode:row.purchaseCode,supplierName:row.supplierName,repertoryId:this.$route.query.repertoryId}
            });
        },
        selectSupplierName(e) {
            this.arrivedForm.supplierId = e
        },
        selectEmployeeName(e){
            this.arrivedForm.operatingPersonnelId = e
        },
        clearData(){
            this.search.materialCode = ''
            this.search.materialName = ''
            this.search.purchaseReason = ''
            this.search.purchaseCode = ''
            this.search.source = ''
            this.search.drawingCode = ''
            this.search.materialBomParamValueStr = ''
            this.search.originalMaterial = ''
            this.search.number = ''
            this.search.factoryMaterialCode = ''
        },
        materialSourceFormatter(row,column){
            if (row.materialSource == 1) {
                return "来料";
            } else if(row.materialSource == 2) {
                return "生产";
            }
        }
    },
    watch: {
        '$route' (to, arrivedForm) {
            if (to.path == '/repertoryStayInbound') {
                let newId = this.$route.query.repertoryId;
                if (newId == undefined) {
                    newId = 0;
                }
                if (this.search.repertoryId != newId) {
                    this.search.repertoryId = newId;
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
</style>
