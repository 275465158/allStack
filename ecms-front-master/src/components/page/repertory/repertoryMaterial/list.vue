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
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <!--<el-form-item label="通用搜索框🔍:">-->
                        <!--<el-input v-model="search.concatFieldStr"></el-input>-->
                    <!--</el-form-item>-->
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
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
                <el-button round @click="add" type="primary">新增物料</el-button>
                <el-button round @click="barCodePrint">条码打印</el-button>

            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection">
                </el-table-column>
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{10*(indexPageNum-1)+scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编号">
                </el-table-column>
                <el-table-column align="center" prop="number" label="物料类型">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="材质">
                </el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="规格">
                </el-table-column>
                <el-table-column align="center" prop="source" label="来源">
                </el-table-column>
                <el-table-column align="center" prop="drawingCode" label="图号">
                </el-table-column>
                <el-table-column align="center" prop="materialUnit" label="单位">
                </el-table-column>
                <el-table-column align="center" prop="inventoryQty" label="库存数">
                </el-table-column>
                <el-table-column align="center" prop="unqualifiedQty" label="不合格数量">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="outbound(scope.row)">出库</el-button>
                        <el-button type="text" @click="inbound(scope.row)">入库</el-button>
                        <el-button type="text" @click="checkMaterial(scope.row)">盘点</el-button>
                        <!--<el-button type="text" @click="sendMaterial(scope.row)">发货</el-button>-->
                        <el-button type="text" @click="detail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="10" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div id="subOutputRank-print" style="display: none">
                <div v-for="(v,k) in barCodelist" style="width:100%;text-align: center;display:inline-block;clear: both;border:1px solid black;font-size:12px;">
                    <ul style="list-style: none;text-align: left;float: left;padding: 0 0 0 8px;">
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料名称：</span>{{v.materialName}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料编号：</span>{{v.barcodes}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料类型：</span>{{v.productType}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">工厂内部编号：</span>{{v.factoryMaterialCode}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">材料：</span>{{v.originalMaterial}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">规格：</span>{{v.materialParam}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">图号：</span>{{v.drawingCode}}</li>
                    </ul>
                    <div id="qrcode3" style="float: right;margin-top: 60px;margin-right: 20px">
                        <vue-qr :text="v.barcodes" :size="90" :margin="0"></vue-qr>
                    </div>
                </div>
            </div>
            <el-dialog title="发货" :visible.sync="sendMaterialVisible" v-if="sendMaterialVisible" width="70%">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料名称:">
                        <el-input v-model="sendMaterialForm.materialCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料编码:">
                        <el-input v-model="sendMaterialForm.materialName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="库存数:">
                        <el-input v-model="sendMaterialForm.inventoryQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="合同编号:" prop="">
                        <el-select v-model="sendForm.contractId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in this.orderList"
                                    :key="item.id"
                                    :label="item.contractId"
                                    :value="item.contractId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div class="handle-box">
                    <span class="el-form-item__label">物料明细列表</span>
                </div>
                <div class="handle-box">
                    <el-table :data="sendList" border stripe style="width: 100%">
                        <el-table-column prop="id" label="序号" sortable>
                            <template slot-scope="scope">
                                {{scope.$index+1}}
                            </template>
                        </el-table-column>
                        <el-table-column prop="supplierName" label="供应商名称"/>
                        <el-table-column prop="materialBatch" label="物料批次"/>
                        <el-table-column prop="inventoryQty" label="数量"/>
                        <el-table-column prop="purchaseReason" label="采购原因"/>
                        <el-table-column prop="materialSource" label="物料来源"/>
                        <el-table-column prop="storageLocation" label="货架位置"/>
                        <el-table-column prop="inboundDate" label="入库时间"/>
                        <el-table-column prop="operatingPersonnelName" label="收货人"/>
                        <el-table-column label="发货数">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.sendQty" type="number" style="width: 100px"
                                          :max="scope.row.inventoryQty" :min="0" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                          v-on:input="change(scope.row,scope.$index)">
                                </el-input>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="saveSendMaterial" type="primary">保 存</el-button>
                    <el-button @click="sendMaterialVisible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    import VueQr from 'vue-qr'; //导入二维码插件
    import jsbarcode from 'jsbarcode';
    export default {
        components: { VueQr },
        data() {
            return {
                tableData: [],
                url: "/materialRepertoryDetail/listByLike",
                // url: "/materialRepertoryDetail/listByLikeForMobile",
                sendUrl:"materialRepertoryDetail/detailListByLike",
                orderUrl:"seller/order/getContractIdList",
                pages: 1,
                search: {
                    pageSize: 10,
                    // materialCode:'',
                    // materialName:'',
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0,
                    // concatFieldStr:''
                },
                orderSearch:{
                    progress:4,
                    status:1
                },
                repertoryName:'',
                barcode_option:{
                    displayValue: true, //是否默认显示条形码数据
                    //textPosition  :'top', //条形码数据显示的位置
                    background: '#fff', //条形码背景颜色
                    valid: function (valid) {
                    },
                    width:'2px',
                    height: '90px',
                    fontSize: '18px' //字体大小
                },
                barCodelist:[],
                sendMaterialVisible:false,
                sendMaterialForm: {
                    pageSize:20,
                    materialCode:'',
                    materialName:'',
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0,
                },
                sendSearch:{
                    pageNum:1,
                    pageSize:100000,
                    materialCode : '',
                    repertoryId : ''
                },
                sendList:[],
                insertFlag:0,
                sendForm:{
                    contractId:'',
                    tableDataRow:[]
                },
                orderList:[],
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
            handleSelectionChange (val) {
                console.log(val);
                this.barCodelist = []
                val.forEach((item, i) => {
                    this.barCodelist.push({
                        barcodes: item.materialCode,
                        materialName: item.materialName,
                        originalMaterial: item.originalMaterial,
                        materialParam: item.materialParam,
                        factoryMaterialCode: item.factoryMaterialCode,
                        drawingCode: item.drawingCode ? item.drawingCode : '暂无'
                    })
                })
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            handleSelect(key, keyPath) {
                this.$router.push({
                    path: key,
                    query: { repertoryId: this.search.repertoryId,repertoryName :this.repertoryName }
                });
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.indexPageNum = val;
                this.search.pageNum = val;
                this.getData();
            },
            getData() {
                this.search.repertoryId = this.$route.query.repertoryId;
                this.repertoryName = this.$route.query.repertoryName
                this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                // this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    this.loading = false
                })
                    .catch(err => {
                        this.loading = false
                    });
            },
            barCodePrint(){
                // 打印
                if (this.barCodelist.length === 0) {
                    this.$message.warning('请勾选要打印的物料')
                } else {
                    let subOutputRankPrint = document.getElementById('subOutputRank-print');
                    let newContent =subOutputRankPrint.innerHTML;
                    let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                    wind.document.body.innerHTML = newContent;
                    wind.print();
                    wind.location.reload();
                    wind.close();
                    return false;
                    alert("条码打印")
                }
            },
            add(){
                this.$router.push({
                    path: "/materielInfo",
                    query: {
                        repertoryId: this.search.repertoryId, repertoryName: this.repertoryName
                    }
                });
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.source = ''
                this.search.drawingCode = ''
                this.search.materialBomParamValueStr = ''
                this.search.originalMaterial = ''
                this.search.number = ''
                this.search.factoryMaterialCode = ''
            },
            detail(row){
                this.$router.push({
                    path: "/repertoryMaterialInfo",
                    query: { row: row,repertoryId:this.search.repertoryId,repertoryName :this.repertoryName }
                });
            },
            checkMaterial(row){
                this.$router.push({
                    path: "/checkMaterialList",
                    query: { row: row,repertoryId:this.search.repertoryId,repertoryName :this.repertoryName }
                });
            },
            outbound(row){
                this.$router.push({
                    path: "/materialOutboundList",
                    query: { row: row,repertoryId:this.search.repertoryId,repertoryName :this.repertoryName }
                });
            },
            inbound(row){
                this.$router.push({
                    path: "/materialInboundList",
                    query: { row: row,repertoryId:this.search.repertoryId,repertoryName :this.repertoryName }
                });
            },
            sendMaterial(row){
                this.sendForm.tableDataRow = []
                this.sendMaterialForm = row
                this.sendMaterialVisible = true
                this.sendSearch.materialCode = row.materialCode
                this.sendSearch.repertoryId = this.$route.query.repertoryId
                this.$http.post(this.sendUrl, this.sendSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.sendList = res.data.data.list;
                    }
                });
                this.$http.post(this.orderUrl, this.orderSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.orderList = res.data.data;
                    }
                });
            },
            saveSendMaterial(){
                if(this.sendForm.contractId != ''){
                    var sendQty;
                    for(var i=0;i<this.sendList.length;i++){
                        sendQty = this.sendList[i].sendQty;
                        if(sendQty != null && sendQty != "" && sendQty != undefined){
                            this.sendForm.tableDataRow.push({
                                detailId: this.sendList[i].id,
                                materialId: this.sendList[i].materialId,
                                sendQty: this.sendList[i].sendQty,
                                materialCode : this.sendList[i].materialCode,
                                materialName : this.sendList[i].materialName,
                                repertoryId : this.$route.query.repertoryId,
                                shelfPosition : this.sendList[i].storageLocation
                            });
                        }
                    }
                    this.$http.post("/materialSendLog/saveSendMaterial",this.sendForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$message.success("发货成功");
                            this.getData();
                            this.sendMaterialVisible = false
                            this.sendForm.tableDataRow = []
                        }
                    });
                }else if(this.sendForm.contractId == ""){
                    this.$message.error("请选择合同编号");
                }
            },
            change(row,index){
                if(row.sendQty > row.inventoryQty) {
                    this.$message.error("发货数不能大于库存数量");
                }
            }
        },
        watch: {
            '$route' (to, search) {
                if (search.path === '/materialInboundList' || search.path === '/materialOutboundList' || search.path === '/repertoryMaterialInfo' || search.path === '/checkMaterialList') {
                    this.search.repertoryId = search.query.repertoryId
                    this.repertoryName = search.query.repertoryName
                    this.getData()
                }else if (to.path == '/materialRepertoryList') {
                    this.search.repertoryId = this.$route.query.repertoryId;
                    this.repertoryName = this.$route.query.repertoryName
                    this.getData()
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
