<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="orderForm" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input disabled v-model="orderForm.productionNumber"/>
                    </el-form-item>
                    <el-form-item label="订单号:">
                        <el-input disabled v-model="orderForm.orderCode"/>
                    </el-form-item>
                    <el-form-item label="合同编号:">
                        <el-input disabled v-model="orderForm.contractId"/>
                    </el-form-item>
                    <el-form-item label="物料编号:">
                        <el-input disabled v-model="orderForm.materialCode"/>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input disabled v-model="orderForm.materialName"/>
                    </el-form-item>
                    <el-form-item label="原图材料:">
                        <el-input disabled v-model="orderForm.originalMaterial"/>
                    </el-form-item>
                    <el-form-item label="单位:">
                        <el-input disabled v-model="orderForm.materialUnit"/>
                    </el-form-item>
                    <el-form-item label="完成数量:">
                        <el-input disabled v-model="orderForm.completeQty"/>
                    </el-form-item>
                    <!--<el-form-item label="良品数:">
                        <el-input disabled v-model="orderForm.materialNum-orderForm.unQualifiedNum"/>
                    </el-form-item>
                    <el-form-item label="不良品数:">
                        <el-input disabled v-model="orderForm.unQualifiedNum"/>
                    </el-form-item>-->
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">生产工序</span>
            </div>
            <el-table :data="processInfo" border style="width:100%" height="250" id="processInfoTable">
                <el-table-column v-for="(process, index) in processInfoTableHead"
                                 :label="process.processFormatName==''?('参数'+(index+1)) :process.processFormatName" :key="process.processFormatName">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row[scope.column.label]" disabled></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">物料清单</span>
            </div>
            <el-table :data="materialListTable" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="materialBomInfo.materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialBomInfo.materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialBomInfo.originalMaterial" label="材料">
                </el-table-column>
                <el-table-column prop="materialBomParamValueStr" label="参数">
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">图纸</span>
            </div>
            <el-table :data="drawingTables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="productDrawingInfo.drawingCode" label="图纸编号">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.name" label="图纸名称">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.drawingCode" label="图号">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.productBatch" label="版本">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.pdfFile" label="PDF图纸">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.cadFile" label="CAD图纸">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.technologyFile" label="工艺图纸">
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">生产制程记录列表</span>
            </div>
            <el-table :data="logTables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column prop="processName" label="制程名称">
                </el-table-column>
                <el-table-column prop="productBatch" label="生产项次">
                </el-table-column>
                <el-table-column prop="completeQty" label="完成数量">
                </el-table-column>
                <el-table-column prop="qualifiedNum" label="合格数">
                </el-table-column>
                <el-table-column prop="checkStatus" label="检测状态" :formatter="checkStatusStr">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        name: '',
        data() {
            return {
                tableData: [],
                dynamicMaterialProcessing:[],
                dynamicMaterialParts:[],
                url: '/productionLog/getProductionLog',
                editVisible: false,
                pages: 1,
                orderForm: {
                    materialId:'',
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    materialUnit:'',
                    materialParam:'',
                    latestProductDate:'',
                    productTime:'',
                    materialNum:'',
                    unQualifiedNum:'',
                    qualifiedNum:'',
                    productionNumber:'',
                    completedQty:''
                },
                processSearch:{
                    id:'',
                    pageNum:1,
                    pageSize:20
                },
                logTablesSearch:{
                    logId:'',
                    pageNum:1,
                    pageSize:20
                },
                processInfoTableHead:[],
                processInfo:[],
                materialListTable:[],
                materialListSearch:{
                    parentProductionNumber:'',
                    materialId:''
                },
                drawingTables:[],
                drawingSearch:{
                    materialId:'',
                    pageNum:1
                },
                logTables:[]
            }
        },
        created() {
            this.getData();
        },
        mounted(){

        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    return d;
                })
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                // 取到路由带过来的参数
                this.rowDataObj = this.$route.params.rowDataObj
                //初始化参数
                this.orderForm =  this.$route.params.rowDataObj
                console.log(this.orderForm);
                this.processSearch.id = this.$route.params.rowDataObj.materialId
                this.$http.post("/materialProcess/detail", this.processSearch ).then(res => {
                    this.processInfo = []
                    if (res != undefined && res.data.code == 1000) {
                        for (var i=0;i<res.data.data.length;i++){
                            this.processInfo.push(JSON.parse(res.data.data[i].jsonParam))
                        }
                    }
                });
                this.$http.post("/processFormat/searchByMaterialId",this.processSearch).then(res => {
                    this.processInfoTableHead = []
                    if (res != undefined && res.data.code == 1000) {
                        this.processInfoTableHead = res.data.data;
                    }
                });
                this.materialListSearch.parentProductionNumber = this.$route.params.rowDataObj.productionNumber
                this.materialListSearch.materialId = this.$route.params.rowDataObj.materialId
                this.$http.post("/materialRelation/detail",
                    {id:this.materialListSearch.materialId}).then(res => {
                    this.materialListTable = []
                    if (res != undefined && res.data.code == 1000) {
                        this.materialListTable = res.data.data;
                    }
                });
                this.drawingSearch.materialId = this.$route.params.rowDataObj.materialId
                this.$http.post("/materialDrawing/materialDrawingList",this.drawingSearch).then(res => {
                    this.drawingTables = []
                    if (res != undefined && res.data.code == 1000) {
                        this.drawingTables = res.data.data;
                    }
                });
                this.logTablesSearch.logId = this.$route.params.rowDataObj.id
                this.$http.post("/productionLogDetail/getLogList",this.logTablesSearch).then(res => {
                    console.log(res)
                    this.logTables = []
                    if (res != undefined && res.data.code == 1000) {
                        this.logTables = res.data.data;
                    }
                });
            },
            checkStatusStr(row) {
                if (row.checkStatus == 0) {
                    return "待检测";
                } else {
                    return "已检测";
                }
            },
            filterTag(value, row) {
                return row.tag === value;
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/produceLogDetail') {
                    this.getData();
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
