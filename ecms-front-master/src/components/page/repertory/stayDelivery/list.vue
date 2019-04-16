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
                        <el-select style="width: 200px;" v-model="search.materialSource">
                            <el-option label="全部" value=""></el-option>
                            <el-option label="自制" value="自制"></el-option>
                            <el-option label="外购" value="外购"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="供应商名称:">
                        <el-select @change="selectSupplierName" v-model="search.supplierId" clearable placeholder="请选择供应商名称">
                            <el-option
                                    v-for="item in supplierList"
                                    :key="item.id"
                                    :label="item.supplierName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round @click="searchLike" type="primary">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待收货列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
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
                <el-table-column prop="supplierName" label="供应商">
                </el-table-column>
                <el-table-column prop="drawingCode" label="图号">
                </el-table-column>
                <el-table-column prop="materialSource" label="来源">
                </el-table-column>
                <el-table-column prop="purchaseReason" label="采购原因">
                </el-table-column>
                <!--<el-table-column prop="purchasePrice" label="采购单价">
                </el-table-column>
                <el-table-column prop="purchasePriceTotal" label="采购总价">
                </el-table-column>-->
                <el-table-column prop="purchasePerson" label="采购人">
                </el-table-column>
                <!--<el-table-column prop="arriveDate" label="预计到货时间">
                </el-table-column>-->
                <el-table-column prop="purchaseQty" label="采购数量">
                </el-table-column>
                <el-table-column prop="receivedQty" label="已收货数量">
                </el-table-column>
                <el-table-column prop="unReceivedQty" label="未收货数量">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="delivery(scope.row, scope.$index)">收货</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <el-dialog title="收货" :visible.sync="noticeArrivedVisible" width="60%">
                <el-form ref="arrivedForm" :model="arrivedForm" label-width="100px">
                    <el-form-item label="物料名称:">
                        <el-input v-model="arrivedForm.materialName" disabled></el-input>
                    </el-form-item>
                    <!--<el-form-item label="供应商:">
                        <el-select @change="selectSupplierName" v-model="arrivedForm.supplierId" clearable placeholder="请选择供应商名称" disabled>
                            <el-option
                                    v-for="item in supplierList"
                                    :key="item.id"
                                    :label="item.supplierName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>-->
                    <el-form-item label="供应商">
                        <el-input v-model="arrivedForm.supplierName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="批次号">
                        <el-input v-model="arrivedForm.materialBatch"></el-input>
                    </el-form-item>
                    <el-form-item label="收货数量:">
                        <span class="hintColor">（不可小于0）</span>
                        <el-input v-model="arrivedForm.inventoryQty"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="收货时间:">
                      <el-date-picker v-model="arrivedForm.deliveryDate" value-format="yyyy-MM-dd"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="收货人:" prop="">
                      <el-select v-model="arrivedForm.operatingPersonnelId" clearable placeholder="请选择">
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
                <el-button type="primary" @click="insertDelivery">保存</el-button>
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
                url: "/purchaseOrdersList/purchaseOrdersListPager",
                pages: 1,
                search: {
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0,
                    materialCode:'',
                    materialName:'',
                    pageSize:20,
                    inboundStatus:0,
                    supplierId:''
                },
                repertoryName:'',
                //对话框现实控制
                noticeArrivedVisible: false,
                //收货
                arrivedForm: {
                    purchaseOrdersId:0,
                    purchaseId:0,
                    materialId:0,
                    oldDeliveryQty:'',
                    materialCode:'',
                    materialName:'',
                    supplierId:'',
                    materialBatch:'',
                    inventoryQty:'',
                    // deliveryDate:'',
                    // operatingPersonnelId:'',
                    repertoryId:0,
                    purchaseReason:'',
                    purchasePrice:0,
                    purchaseCode:'',
                    status: 1,
                    pageNum: 1,
                    pageSize:20
                },
                supplierList:[],
                employeeList:[],
                zIdx: 0,
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
                    query: { repertoryId: this.search.repertoryId, repertoryName: this.repertoryName }
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
                let _this = this
                _this.loading = true
                this.search.repertoryId = this.$route.query.repertoryId;
                this.arrivedForm.repertoryId = this.$route.query.repertoryId;
                this.repertoryName = this.$route.query.repertoryName;
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
                this.$http.post("/supplier/siList", this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.supplierList = res.data.data;
                    this.pages = res.data.data.pages;
                }
            });
                this.$http.post('/employee/listEmployeeByInfo', this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.employeeList = res.data.data.list;
                }
            });
            },
            insertDelivery() {
                if (this.arrivedForm.inventoryQty > 0) {
                    this.$http.post("/materialRepertoryDelivery/insertDelivery", this.arrivedForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$message.success("保存成功!");
                            this.noticeArrivedVisible = false;
                            this.getData();
                        }
                    });
                } else {
                    this.$message.warning('收获数量不能为0')
                }
            },
            delivery(row, index){
                this.arrivedForm.materialBatch = ''
                this.zIdx = index
                this.noticeArrivedVisible = true
                this.arrivedForm.materialCode = row.materialCode
                this.arrivedForm.materialName = row.materialName
                this.arrivedForm.inventoryQty = row.unReceivedQty
                this.arrivedForm.oldDeliveryQty = row.unReceivedQty
                this.arrivedForm.purchaseReason = row.purchaseReason
                this.arrivedForm.repertoryId = row.repertoryId
                this.arrivedForm.purchasePrice = row.purchasePrice
                this.arrivedForm.materialId = row.materialId
                this.arrivedForm.purchaseCode = row.purchaseCode
                this.arrivedForm.purchaseOrdersListId = row.id
                this.arrivedForm.supplierId = row.supplierId
                this.arrivedForm.supplierName = row.supplierName
                this.arrivedForm.purchaseOrdersId = row.purchaseOrdersId
            },
            selectSupplierName(e) {
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.supplierId = ''
                this.search.materialSource = ''
                this.search.drawingCode = ''
                this.search.materialBomParamValueStr = ''
                this.search.originalMaterial = ''
                this.search.number = ''
                this.search.factoryMaterialCode = ''
            }
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/repertoryStayDelivery') {
                    Object.assign(this.$data, this.$options.data())
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
