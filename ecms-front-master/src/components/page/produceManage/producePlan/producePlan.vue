<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input v-model="search.productionNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="合同编号:">
                        <el-input v-model="search.contractId"></el-input>
                    </el-form-item>
                    <el-form-item label="生产类型:">
                        <el-select v-model="search.productionType">
                            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="产品名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="生产项次:">
                        <el-input v-model="search.productBatch"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="计划生产日期:">
                        <el-col :span="11">
                            <el-date-picker type="date" placeholder="选择日期" v-model="search.startDate" style="width: 150px"></el-date-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="至:">
                        <el-col :span="11">
                            <el-date-picker type="date" placeholder="选择日期" v-model="search.endDate" style="width: 150px"></el-date-picker>
                        </el-col>
                    </el-form-item>-->
                    <!--<el-form-item label="物料状态:">
                        <el-select v-model="search.materialStatus" placeholder="请选择">
                            <el-option key="zc" label="备齐" value="1"></el-option>
                            <el-option key="by" label="缺失" value="2"></el-option>
                        </el-select>
                    </el-form-item>-->
                    <el-button round @click="searchLike" type="primary">搜索</el-button>
                    <el-button round @click="clearFrom">清空</el-button>
                </el-form>

            </div>
            <div class="handle-box">
                <span class="el-form-item_label">待生产列表</span>
                <el-button round @click="add" type="primary">添加</el-button>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{20*(indexPageNum-1)+scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column align="center" prop="contractId" label="合同编号">
                </el-table-column>
                <el-table-column align="left" prop="sequenceNumber" label="编号">
                </el-table-column>
                <el-table-column align="center" prop="productBatch" label="订单编号">
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编号">
                </el-table-column>
                <el-table-column align="center" prop="number" label="物料类型">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="产品名称">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="参数">
                </el-table-column>
                <el-table-column align="center" prop="drawingCode" label="图号">
                </el-table-column>
                <!--<el-table-column prop="source" label="来源">-->
                <!--</el-table-column>-->
                <el-table-column align="center" prop="materialNum" label="生产数量">
                </el-table-column>
                <!--<el-table-column align="center" prop="completedQty" label="已完成数量">
                </el-table-column>-->
                <el-table-column align="center" prop="productionType" label="生产类型" :formatter = "productionTypeFormat">
                </el-table-column>
                <!--<el-table-column align="center" prop="planProductDate" label="计划生产日期">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="latestProductDate" label="最晚生产日期">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="materialStatus" label="物料状态" :formatter = "stateFormat">
                </el-table-column>-->
                <el-table-column align="center" label="操作" width="90">
                    <template slot-scope="scope">
                        <!--<el-button size="small" type="primary" @click="handleComplete(scope.$index, scope.row)">完成</el-button>-->
                        <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                        <!--<el-button size="small" @click="issueLog(scope.$index, scope.row)">物料清单</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <el-dialog title="添加生产计划" @close="closeing" :visible.sync="addProductPlanVisible" width="80%">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="产品编号:">
                        <el-input v-model="materialSearch.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="产品名称:">
                        <el-input v-model="materialSearch.materialName"></el-input>
                    </el-form-item>
                    <el-button round @click="searchTwo">查询</el-button>
                </el-form>
                <el-table :data="productList" border style="width:100%" height="400">
                    <el-table-column label="产品编号" prop="materialCode"></el-table-column>
                    <el-table-column label="产品名称" prop="materialName"></el-table-column>
                    <el-table-column label="单位" prop="materialUnit"></el-table-column>
                    <el-table-column label="数量" prop="materialNum">
                        <template slot-scope="scope">
                            <el-input type="number" v-model.number="scope.row.materialNum" :min="0"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="计划生产日期" prop="planProductDate">
                        <template slot-scope="scope">
                            <el-date-picker type="date" clearable :editable="false" v-model="scope.row.planProductDate"></el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column label="最晚生产日期" prop="latestProductDate">
                        <template slot-scope="scope">
                            <el-date-picker type="date" clearable :editable="false" v-model="scope.row.latestProductDate"></el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="primary" @click="addProductPlan(scope.$index, scope.row)">添加</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination :page-size="5" @current-change="handleCurrentChangeTwo" layout="total, prev, pager, next" :total="pages">
                    </el-pagination>
                </div>
                <span slot="footer" class="dialog-footer">
                <el-button @click="addProductPlanVisible = false">关 闭</el-button>
            </span>
            </el-dialog>
            <el-dialog title="完成" v-loading="loading" @close="completeCloseing" :visible.sync="noticeArrivedVisible" width="60%">
                <el-form :inline="true" :model="rowData" class="demo-form-inline">
                    <el-form-item label="生产单号:">
                        <el-input v-model="rowData.productionNumber" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="rowData.materialName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料编码:">
                        <el-input v-model="rowData.materialCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="生产项次:">
                        <el-input v-model="rowData.productBatch" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="生产总数:">
                        <el-input v-model="rowData.materialNum" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="已完成生产数:">
                        <el-input v-model="rowData.completedQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="可完成数量:">
                        <el-input v-model="rowData.produceQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="完成数:">
                        <el-input v-model="rowData.completeQty" type="number" min="0" placeholder="请输入数量" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="change"></el-input>
                    </el-form-item>
                    <el-button round @click="handleCompleteButton()" type="primary">完成</el-button>
                </el-form>
                <!--<el-table :data="processInfo" border style="width:100%" height="250" id="processInfoTable">
                    <el-table-column v-for="(process, index) in processInfoTableHead"
                                     :label="process.processFormatName==''?('参数'+(index+1)) :process.processFormatName" :key="process.processFormatName">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row[scope.column.label]" disabled></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="materialStatus" label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" type="primary" @click="handleCompleteButton(scope.$index, scope.row)">完成</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <span class="dialog-footer">
                  <el-button type="primary" @click="goBack">返回</el-button>
                </span>-->
            </el-dialog>
            <el-dialog title="物料清单" :visible.sync="issueLogVisible" width="60%">
                <el-table :data="issueLogData" border style="width:100%" height="200">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column label="物料名称" prop="materialBomInfo.materialName"></el-table-column>
                    <el-table-column label="物料编码" prop="materialBomInfo.materialCode"></el-table-column>
                    <el-table-column label="原图材料" prop="materialBomInfo.originalMaterial"></el-table-column>
                    <el-table-column label="来源" prop="materialBomInfo.source"></el-table-column>
                    <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column prop="quantity" label="配套比例">
                        <template slot-scope="scope">
                            1:{{scope.row.quantity}}
                        </template>
                    </el-table-column>
                    <el-table-column label="可生产数量" prop="produceQty"></el-table-column>
                    <el-table-column label="已发料数量" prop="issueQty"></el-table-column>
                    <el-table-column label="已使用数量" prop="usedQty"></el-table-column>
                    <el-table-column label="已退料数量" prop="returnedQty"></el-table-column>
                    <el-table-column label="待发料数量" prop="stayIssueQty"></el-table-column>
                </el-table>
                <el-button round @click="issueLogVisible = false" type="primary">关 闭</el-button>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    export default {
        name: '',
        data() {
            return {
                productList:[],
                tableData: [],
                issueLogData:[],
                relationUrl:"materialRelation/materialList",
                url: '/productionPlan/productionPlanList',
                completeUrl:'productionLog/completeProduce',
                addUrl: '/productionPlan/addProductionPlan',
                issueLogUrl:'/productionIssue/getIssueLogList',
                editVisible: false,
                pages: 1,
                search: {
                    productName: '',
                    productBatch: '',
                    startDate: '',
                    endDate: '',
                    materialStatus: '',
                    pageNum: 1
                },
                rowData:{
                    productBatch:'',
                    pageNum: 1,
                    completedQty:0
                },
                productionNumber: '',
                materialName:'',
                materialCode:'',
                columnList: ['统标流水号', '制程', '数量', '日期', '责废', '料废', '订单', '操作工', '合格数', '检验员', '特殊控制项'],
                inforList: [],
                productColumnList: ['项次', '物料编码/名称', '计划数量', '实发数', '批次号', '货位'],
                productInforList: [],
                flag:0,
                //对话框显示控制
                noticeArrivedVisible: false,
                issueLogVisible: false,
                processInfo:[],
                processInfoTableHead:[],
                processSearch:{
                    id:'',
                    pageNum:1,
                    pageSize:20
                },
                addProductPlanVisible:false,
                materialSearch:{
                    source:'自制',
                    materialCode:'',
                    materialName:'',
                    pageNum:1,
                    pageSize:5,
                    auditingStatus:0
                },
                addPlanData : [],
                logSearch:{
                    productionNumber:'',
                    pageNum:1
                },
                options: [
                    {
                        label: "计划单",
                        value: 1
                    },
                    {
                        label: "手工添加",
                        value: 2
                    },
                    {
                        label: "返工",
                        value: 3
                    },
                    {
                        label: "返修",
                        value: 4
                    },
                    {
                        label: "报废生产",
                        value: 5
                    }
                ],
                issueLogSearch:{
                    id:0,
                    productionNumber:'',
                    materialNum:0,
                    pageNum:1,
                    pageSize:20
                },
                loading: false,
                indexPageNum:1
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    return d;
            })
            }
        },
        methods: {
            closeing () {
                this.materialSearch.materialCode = ''
                this.materialSearch.materialName = ''
            },
            completeCloseing () {
                this.rowData.completeQty = ''
            },
            // 分页导航
            handleCurrentChange(val) {
                this.indexPageNum = val;
                this.search.pageNum = val;
                this.getData();
            },
            // 分页导航
            handleCurrentChangeTwo(val) {
                this.materialSearch.pageNum = val;
                this.selectLike();
            },
            searchTwo(){
                this.materialSearch.pageNum = 1;
                this.selectLike();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            // 获取数据
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    })
            },
            change(){
                var completeQty = parseFloat(this.rowData.completeQty)+parseFloat(this.rowData.completedQty)
                if( this.rowData.produceQty<this.rowData.completeQty){
                    this.$message.error("数量不能大于可完成数");
                    return false;
                }
                if(completeQty>parseFloat(this.rowData.materialNum)){
                    this.$message.error("数量不能大于生产总数");
                    return false;
                }
            },
            clearFrom() {
                this.search.productionNumber = '',
                this.search.productionType = '',
                this.search.productName = '',
                this.search.productBatch = '',
                this.search.startDate = '',
                this.search.endDate = '',
                this.search.materialStatus = ''
                this.search.contractId = ''
                this.search.materialName = ''
            },
            filterTag(value, row) {
                return row.tag === value
            },
            handleEdit(index,row) {
                this.$router.push({
                    path: 'producePlanDetail',
                    name: 'producePlanDetail',
                    query:{id:row.id, rowDataObj: row}
                })
            },
            issueLog(index,row){
                this.issueLogSearch.id = row.materialId
                this.issueLogSearch.productionNumber = row.productionNumber
                this.issueLogSearch.materialNum = row.materialNum
                this.$http.post(this.relationUrl, this.issueLogSearch).then((res) => {
                    if (res.data.code == 1000) {
                        this.issueLogData = res.data.data
                    }
                })
                this.issueLogVisible = true
            },
            handleCompleteButton(){
                let completeQty = 0;
                if(this.rowData.completeQty == undefined){
                    this.$message.error("请输入完成数");
                }else{
                    completeQty = parseFloat(this.rowData.completeQty)+parseFloat(this.rowData.completedQty)
                }
                if( this.rowData.produceQty<this.rowData.completeQty){
                    this.$message.error("数量不能大于可完成数");
                }else if(completeQty>parseFloat(this.rowData.materialNum)){
                    this.$message.error("数量不能大于生产总数");
                }else{
                    this.$confirm("此操作将完成生产进程, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }).then(() => {
                        this.$http.post(this.completeUrl, this.rowData).then((res) => {
                            if (res.data.code == 1000) {
                                this.getData();
                                this.$message({
                                    type: "success",
                                    message: "操作成功!"
                                });
                                this.noticeArrivedVisible = false
                            }
                        })
                    }).catch(() => {
                            this.$message({
                            type: "info",
                            message: "操作取消"
                        });
                    });
                }
            },
            handleComplete(index,row) {
                this.loading = true
                this.noticeArrivedVisible = true
                this.logSearch.productionNumber = row.productionNumber
                this.issueLogSearch.id = row.materialId
                this.issueLogSearch.productionNumber = row.productionNumber
                this.issueLogSearch.materialNum = row.materialNum
                this.rowData.completedQty = row.completedQty
                this.rowData.productBatch = row.productBatch
                this.rowData.materialId = row.materialId
                this.rowData.productId = row.productId
                this.rowData.materialCode = row.materialCode
                this.rowData.materialUnit = row.materialUnit
                this.rowData.materialCode = row.materialCode
                this.rowData.productionNumber = row.productionNumber
                this.rowData.materialName = row.materialName
                this.rowData.materialNum = row.materialNum
                this.rowData.originalMaterial = row.originalMaterial
                this.rowData.latestProductDate = row.latestProductDate
                this.rowData.planProductDate = row.planProductDate
                this.rowData.productionPlanId = row.id
                let _this = this
                this.$http.post("/materialRelation/getMaxProduceQty ", this.issueLogSearch ).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        _this.rowData.produceQty = res.data.data
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                        console.log(err);
                    });
            },
            barCodePrint(){
                // 打印
                let subOutputRankPrint = document.getElementById('subOutputRank-print');
                let newContent =subOutputRankPrint.innerHTML;
                let oldContent = document.body.innerHTML;
                document.body.innerHTML = newContent;
                window.print();
                window.location.reload();
                document.body.innerHTML = oldContent;
                return false;
                alert("条码打印")
            },
            goBack() {
                this.noticeArrivedVisible = false
            },
            add(){
                this.selectLike()
                this.addProductPlanVisible = true
            },
            productionTypeFormat(row){
                if (row.productionType == 1) {
                    return "计划单";
                } else if(row.productionType == 2){
                    return "手工添加";
                }else if(row.productionType == 3){
                    return "返工";
                }else if(row.productionType == 4){
                    return "返修";
                }else{
                    return "报废生产";
                }
            },
            selectLike(){
                this.$http.post("/materialInfo/searchMaterialList", this.materialSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.productList = res.data.data.list;
                        this.pages = res.data.data.total;
                        // if (this.productList.length!=0){
                        //     for (var i=0;i<this.productList.length;i++){
                        //         if (this.productListCenter.length!=0){
                        //             for (var j=0;j<this.productListCenter.length;j++){
                        //                 if (this.productListCenter[j].id == this.productList[i].id){
                        //                     this.productList.splice(i,1)
                        //                 }
                        //             }
                        //         }
                        //         this.productList[i].taxRate = this.taxRate
                        //         this.productList[i].taxInclude = this.taxInclude
                        //     }
                        // }
                    }
                });
            },
            addProductPlan(index,row){
                if (row.materialNum==undefined){
                    this.$message.warning('请输入数量')
                } else {
                    this.addPlanData = row
                    this.addPlanData.productType = row.flag
                    this.addPlanData.materialId = row.id
                    this.$http.post(this.addUrl, this.addPlanData).then((res) => {
                        if (res.data.code == 1000) {
                            this.getData();
                            this.$message({
                                type: "success",
                                message: "操作成功!"
                            });
                            this.addProductPlanVisible = false;
                        }
                    })
                }
            }
        },
        watch: {
            '$route' (to, orderForm) {
                if (to.path == '/producePlan') {
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