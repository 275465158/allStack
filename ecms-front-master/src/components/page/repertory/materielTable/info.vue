<template>
<div class="container">
    <div class="handle-box">
        <el-form :inline="true" :model="search" class="demo-form-inline" label-width="120px">
            <el-row>
                <el-form-item label="物料编号:">
                    <el-input v-model="form.materialCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="版本号:">
                    <el-input v-model="form.versionVumStr" disabled></el-input>
                </el-form-item>
                <el-form-item label="工厂内部编号:">
                    <el-input v-model="form.factoryMaterialCode" disabled></el-input>
                </el-form-item>
                <el-form-item label="物料类型:">
                    <el-input v-model="form.number" disabled></el-input>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="物料名称:">
                    <el-input v-model="form.materialName" disabled></el-input>
                </el-form-item>
                <el-form-item label="材质:">
                    <el-input v-model="form.originalMaterial" disabled></el-input>
                </el-form-item>
                <el-form-item label="规格:">
                    <el-input v-model="form.materialBomParamValueStr" disabled></el-input>
                </el-form-item>
                <el-form-item label="单位:">
                    <el-input v-model="form.materialUnit" disabled></el-input>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="图号:">
                    <!--<el-input v-model="form.drawingCode" disabled></el-input>-->
                    <input @click="showDislog(0)" class="listBtn" type="button" v-model="form.drawingCode">
                </el-form-item>
                <el-form-item label="库存状态:">
                    <el-input v-model="form.inventoryStatus" disabled></el-input>
                </el-form-item>
                <el-form-item label="安全库存数:">
                    <el-input v-model="form.inventorySafeQty" disabled></el-input>
                </el-form-item>
                <el-form-item label="库存数:">
                    <el-input v-model="form.inventoryQty" disabled></el-input>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="不合格数:">
                    <el-input v-model="form.unqualifiedQty" disabled></el-input>
                </el-form-item>
                <el-form-item label="订单需求:">
                    <el-input v-model="form.orderNeedQty" disabled></el-input>
                </el-form-item>
                <el-form-item label="待采购数:">
                    <!--<el-input v-model="form.stayPurchaseQty" disabled></el-input>-->
                    <input  @click="showDislog(2)" class="listBtn" type="button" v-model="form.stayPurchaseQty">
                </el-form-item>
                <el-form-item label="待收货数:">
                    <!--<el-input v-model="form.stayDeliveredQty" disabled></el-input>-->
                    <input  @click="showDislog(3)" class="listBtn" type="button" v-model="form.stayDeliveredQty">
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="待检测数:">
                    <!--<el-input v-model="form.stayCheckQty" disabled></el-input>-->
                    <input  @click="showDislog(4)" class="listBtn" type="button" v-model="form.stayCheckQty">
                </el-form-item>
                <el-form-item label="待入库数:">
                    <!--<el-input v-model="form.stayInboundQty" disabled></el-input>-->
                    <input  @click="showDislog(5)" class="listBtn" type="button" v-model="form.stayInboundQty">
                </el-form-item>
                <el-form-item label="待生产数:">
                    <!--<el-input v-model="form.stayProductionQty" disabled></el-input>-->
                    <input  @click="showDislog(6)" class="listBtn" type="button" v-model="form.stayProductionQty">
                </el-form-item>
                <el-form-item label="投料数:">
                    <input  @click="showDislog(7)" class="listBtn" type="button" v-model="form.issueQty">
                </el-form-item>
            </el-row>
        </el-form>
    </div>
    <div class="handle-box">
        <span class="el-form-item__label">物料明细列表</span>
    </div>
    <div class="handle-box">
        <el-table :data="tables" border stripe style="width: 100%">
            <el-table-column prop="id" label="序号" sortable>
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="repertoryName" label="仓库"/>
            <el-table-column prop="inventoryQty" label="数量"/>
        </el-table>
        <div class="pagination">
            <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
            </el-pagination>
        </div>
    </div>
    <el-dialog title="图纸信息" @close="closeing" :visible.sync="issueLogVisible0" width="40%">
        <template v-if="drawingFormShow">
            <el-form :model="drawingForm" label-width="120px">
                <el-form-item label="图纸名称：">
                    <el-input style="width: 200px;" v-model="drawingForm.name"></el-input>
                </el-form-item>
                <el-form-item label="图号：">
                    <el-input style="width: 200px;" v-model="drawingForm.drawingCode"></el-input>
                </el-form-item>
                <el-form-item label="图纸说明：">
                    <el-input style="width: 200px;" v-model="drawingForm.drawingRemark"></el-input>
                </el-form-item>
                <el-form-item label="工艺说明：">
                    <el-input style="width: 200px;" v-model="drawingForm.technologyRemark"></el-input>
                </el-form-item>
            </el-form>
        </template>
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="序号" prop="index"></el-table-column>
            <el-table-column label="CAD图纸" prop="cadFile">
                <template slot-scope="scope">
                    {{scope.row.cadFile}}
                    <el-button type="text" v-if="scope.row.cadFile != ''" @click="downloadFile(scope.row.cadFile)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column label="PDF图纸" prop="pdfFile">
                <template slot-scope="scope">
                    {{scope.row.pdfFile}}
                    <el-button type="text" v-if="scope.row.pdfFile != ''" @click="downloadFile(scope.row.pdfFile)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column label="工艺图纸" prop="technologyFile">
                <template slot-scope="scope">
                    {{scope.row.technologyFile}}
                    <el-button type="text" v-if="scope.row.technologyFile != ''" @click="downloadFile(scope.row.technologyFile)">下载</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible0 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="待采购数" @close="closeing" :visible.sync="issueLogVisible1" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="序号" prop="index"></el-table-column>
            <el-table-column label="待采购编号" prop="purchaseStayCode"></el-table-column>
            <el-table-column label="订单编号" prop="orderCode"></el-table-column>
            <el-table-column label="合同编号" prop="contractId"></el-table-column>
            <el-table-column label="待采购数" prop="stayPurQty"></el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible1 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="待收货数" @close="closeing" :visible.sync="issueLogVisible2" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="序号" prop="index"></el-table-column>
            <el-table-column label="采购单号" prop="purchaseCode"></el-table-column>
            <el-table-column label="待收货数量" prop="unReceivedQty"></el-table-column>
            <el-table-column label="采购原因" prop="purchaseReason"></el-table-column>
        </el-table>
        <!--<div class="pagination" v-if="idx">-->
            <!--<el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">-->
            <!--</el-pagination>-->
        <!--</div>-->
        <el-button round @click="issueLogVisible2 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="待检测数" @close="closeing" :visible.sync="issueLogVisible3" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="订单编号" prop="orderCode"></el-table-column>
            <el-table-column label="采购单号" prop="purchaseCode"></el-table-column>
            <el-table-column label="生产单号" prop="productionNumber"></el-table-column>
            <el-table-column label="合同编号" prop="contractId"></el-table-column>
            <el-table-column label="编号" prop="sequenceNumber"></el-table-column>
            <el-table-column label="待检测数" prop="completedQty"></el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible3 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="待入库列表" @close="closeing" :visible.sync="issueLogVisible4" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="采购单号" prop="purchaseCode"></el-table-column>
            <el-table-column label="生产单号" prop="purchaseReason"></el-table-column>
            <el-table-column label="订单编号"></el-table-column>
            <el-table-column label="合同编号"></el-table-column>
            <el-table-column label="项次"></el-table-column>
            <el-table-column label="待入库数" prop="qualifiedQty"></el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible4 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="待生产数" @close="closeing" :visible.sync="issueLogVisible5" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="生产单号" prop="productionNumber"></el-table-column>
            <el-table-column label="订单编号" prop="orderCode"></el-table-column>
            <el-table-column label="合同编号" prop="contractId"></el-table-column>
            <el-table-column label="项次" prop="sequenceNumber"></el-table-column>
            <el-table-column label="生产类型" prop="productionType"></el-table-column>
            <el-table-column label="待生产数" prop="stayProductionQty"></el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible5 = false" type="primary">关 闭</el-button>
    </el-dialog>
    <el-dialog title="投料数" @close="closeing" :visible.sync="issueLogVisible6" width="40%">
        <el-table :data="dislogTableData" border style="width: 100%;" height="300">
            <el-table-column label="生产单号" prop="productionNumber"></el-table-column>
            <el-table-column label="订单编号" prop="orderCode"></el-table-column>
            <el-table-column label="合同编号" prop="contractId"></el-table-column>
            <el-table-column label="项次" prop="sequenceNumber"></el-table-column>
            <el-table-column label="生产类型" prop="productionType"></el-table-column>
            <el-table-column label="投料数" prop="surplusIssueQty"></el-table-column>
        </el-table>
        <div class="pagination" v-if="idx">
            <el-pagination :page-size="10" @current-change="handleCurrentChange1"  layout="prev, pager, next" :total="pages1">
            </el-pagination>
        </div>
        <el-button round @click="issueLogVisible6 = false" type="primary">关 闭</el-button>
    </el-dialog>
</div>
</template>
<script>
import Qs from "qs";
import * as utils from '../../../../../static/js/utils.js';
import fileDownload from 'js-file-download';
export default {
    data() {
        return {
            drawingUrl:"materialDrawing/detail",
            stayProductionUrl:"productionPlan/getStayProductionList",
            issueUrl:"productionIssue/getProductionIssueList",
            stayInboundUrl:"materialRepertoryIncomingCheck/getStayInboundPage",
            stayCheckUrl:"materialRepertoryDelivery/getStayInboundPage",
            stayCheckUrl1:"productionLogDetail/getStayCheckList",
            stayDeliveryUrl:"purchaseOrdersList/getPurchaseOrdersList",//规格materialId,receivedStatus:0
            orderNeedUrl:"sellerOrderProduct/getOrderNeedList",//规格materialId,receivedStatus:0
            stayPurchaseUrl:"purchaseOrdersStay/getStayPurchaseList",//规格materialId,receivedStatus:0
            // drawingUrl:"purchaseOrdersStay/purchaseList",
            tableData: [],
            tableData1: [],
            pages:1,
            pages1:1,
            form: {
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialUnit:"",
                materialParam: "",
                inventoryQty: "",
                inventorySafeQty: "",
                orderNeedQty:"",
                inventoryStatus:''
            },
            dialogLoading: false,
            dialogTitleArr: ['图纸信息', '订单需求', '待采购数', '待收货数', '待检测数', '待入库列表', '待生产数', '投料数'],
            dialogTitle: '',
            idx: 0,
            dislogTableData: [],
            drawingForm: {
                name: '',
                drawingCode: '',
                drawingRemark: '',
                technologyRemark: ''
            },
            drawingFormShow: false,
            issueLogVisible0: false,
            issueLogVisible1: false,
            issueLogVisible2: false,
            issueLogVisible3: false,
            issueLogVisible4: false,
            issueLogVisible5: false,
            issueLogVisible6: false,
            issueLogVisible7: false,
            search: {
                materialId:'',
                pageNum:1,
                status:1
            },
            issueSearch: {
                materialId:'',
                pageNum:1,
                pageSize:10,
                status:1
            },
            orderNeedSearch:{
                productId:'',
                status:1
            },
            stayDeliverySearch:{
                materialId:'',
                receivedStatus:0
            },
            drawingSearch:{
                id:''
            }
        };
    },
    created() {
        this.form = this.$route.query.row
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
        downloadFile (file) {
            this.$http.post('/productDrawing/download', {filePath: file}, {responseType: 'arraybuffer'}).then((res) => {
                fileDownload(res.data, utils.getFileName(file));
            })
        },
        closeing () {
            this.dislogTableData = []
        },
        showDislog (i) {
            this.dialogTitle = this.dialogTitleArr[i]
            this.drawingFormShow = false
            this.idx = i
            this.dialogLoading = true
            console.log(i);
            if (i == 0) {
                this.issueLogVisible0 = true
                //图纸弹出框请求
                this.$http.post(this.drawingUrl, this.drawingSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data.length == 0) {
                            this.dislogTableData = []
                            this.drawingForm = {
                                name: '',
                                drawingCode: '',
                                drawingRemark: '',
                                technologyRemark: ''
                            }
                        } else {
                            this.drawingForm = res.data.data[0].productDrawingInfo
                            this.dislogTableData = res.data.data.map((item, i) => {
                                return {
                                    index: i + 1,
                                    cadFile: item.productDrawingInfo.cadFile,
                                    pdfFile: item.productDrawingInfo.pdfFile,
                                    technologyFile: item.productDrawingInfo.technologyFile
                                }
                            })
                        }
                    }
                    this.dialogLoading = false
                });
                this.drawingFormShow = true
            } else if(i == 2){
                this.issueLogVisible1 = true
                this.$http.post(this.stayPurchaseUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                        this.dislogTableData.forEach((item, i) => {
                            item.index = i + 1
                        })
                    }
                    this.dialogLoading = false
                });
            }else if(i == 3){
                this.issueLogVisible2 = true
                this.$http.post(this.stayDeliveryUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                        this.dislogTableData.forEach((item, i) => {
                            item.index = i + 1
                        })
                    }
                    this.dialogLoading = false
                });
            }else if(i == 4){
                this.issueLogVisible3 = true
                this.idx = i
                this.$http.post(this.stayCheckUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                        this.$http.post(this.stayCheckUrl1, this.issueSearch).then(res1 => {
                            if (res1.data.data != undefined && res1.data.code == 1000) {
                                for (let i=0;i<res1.data.data.length;i++){
                                    this.dislogTableData.push(res1.data.data[i])
                                }
                            }
                        });
                    }
                    this.dialogLoading = false
            });
            }else if(i == 5){
                this.issueLogVisible4 = true
                this.idx = i
                this.$http.post(this.stayInboundUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                    }
                    this.dialogLoading = false
                });
            }else if(i == 6){
                this.issueLogVisible5 = true
                this.idx = i
                this.$http.post(this.stayProductionUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                        this.dislogTableData.forEach(item => {
                            item.productionType = this.productionTypeFormatter(item)
                        })
                    }
                    this.dialogLoading = false
                });
            }else if(i == 7){
                this.issueLogVisible6 = true
                this.idx = i
                this.$http.post(this.issueUrl, this.issueSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.dislogTableData = res.data.data.list
                        this.dislogTableData.forEach(item => {
                            item.productionType = this.productionTypeFormatter(item)
                        })
                    }
                    this.dialogLoading = false
                });
            }
        },
        handleChange(value) {},
        handleSelect(item) {
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        handleCurrentChange1(val) {
            this.issueSearch.pageNum = val;
            let url;
            switch (this.idx) {
                case 4:
                    this.$http.post(this.stayCheckUrl, this.issueSearch).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$http.post(this.stayCheckUrl1, this.issueSearch).then(res1 => {
                                if (res != undefined && res.data.code == 1000) {
                                    let arr = res1.data.data
                                    res.data.data.list.forEach((item, index) => {
                                        let obj = {
                                            purchaseCode: item.purchaseCode,
                                            orderCode: arr[index].orderCode,
                                            productionNumber: arr[index].productionNumber,
                                            contractId: arr[index].contractId,
                                            sequenceNumber: arr[index].sequenceNumber,
                                            completedQty: arr[index].completedQty
                                        }
                                        this.dislogTableData.push(obj)
                                    })
                                }
                            });
                        }
                    });
                    break;
                case 5:
                    this.$http.post(this.stayInboundUrl, this.issueSearch).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.dislogTableData = res.data.data.list
                        }
                    });
                    break;
                case 6:
                    this.$http.post(this.stayProductionUrl, this.issueSearch).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.dislogTableData = res.data.data.list
                        }
                    });
                    break;
                case 7:
                    this.$http.post(this.issueUrl, this.issueSearch).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.dislogTableData = res.data.data.list
                        }
                    });
                    break;
            }
        },
        // 获取 easy-mock 的模拟数据
        getData() {
            this.form = this.$route.query.row;
            this.search.materialId = this.$route.query.row.materialId;
            this.issueSearch.materialId = this.$route.query.row.materialId;
            this.orderNeedSearch.productId = this.$route.query.row.materialId;
            this.stayDeliverySearch.materialId = this.$route.query.row.materialId;
            this.drawingSearch.id = this.$route.query.row.materialId;
            //获取物料列表
            this.$http.post("/materialRepertoryDetail/getDetailList", this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                    this.pages = res.data.data.pages;
                }
            });
        },
        purchaseReasonFormat(row, column) {
            if (row.purchaseReason == 1) {
                return '数量'
            } else if (row.purchaseReason == 2) {
                return '批量'
            }
        },
        goBack() {
            this.$router.push({
                path: "/materielList",
                query: { repertoryId: this.$route.query.repertoryId }
            });
        },
        productionTypeFormatter(row, column){
            if (row.productionType == 1) {
                return "计划单";
            } else if(row.productionType == 2){
                return "手工添加";
            } else if(row.productionType == 3){
                return "返工";
            } else if(row.productionType == 4){
                return "返修";
            } else if(row.productionType == 5){
                return "报废生产";
            }
        }
    },
    mounted() {},
    watch: {
        '$route' (to, search) {
            if (to.path == '/materialTableInfo' && this.$route.query.works != 1) {
                this.getData();
            }
        }
    }
};
</script>
<style scoped>
    .listBtn{
        background-color: #f5f7fa;
        border: 1px solid #e4e7ed;
        height: 32px;
        line-height: 32px;
        width: 200px;
        cursor: pointer;
        border-radius: 4px;
        padding: 0 15px;
        text-align: left;
        color: #c0c4cc;
    }
</style>

