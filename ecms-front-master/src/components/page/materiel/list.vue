<template xmlns:text-align="http://www.w3.org/1999/xhtml">
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materielList">物料列表</el-menu-item>
                    <el-menu-item index="/entryList">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/materielDetailList">出入库明细</el-menu-item>
                    <!-- <el-menu-item index="/deliveryList">出库管理</el-menu-item>-->
                    <!--<el-menu-item index="/productionIssue">生产发料</el-menu-item>-->
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materielCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materielName"></el-input>
                    </el-form-item>
                    <el-form-item label="货架号:" prop="">
                        <el-select v-model="search.storageNum" @change="changeStorageRow($event)" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in this.storageNumList"
                                    :key="item.storageNum"
                                    :label="item.storageNum"
                                    :value="item.storageNum">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="货架层数:" prop="">
                        <el-select v-model="search.storageRow" @change="changeStorageLayer($event)" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in this.storageRowList"
                                    :key="item.storageRow"
                                    :label="item.storageRow"
                                    :value="item.storageRow">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="货架排数:" prop="">
                        <el-select v-model="search.storageLayer" @change="changeStorageColumn($event)" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in this.storageLayerList"
                                    :key="item.storageLayer"
                                    :label="item.storageLayer"
                                    :value="item.storageLayer">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="货架列数:" prop="">
                        <el-select v-model="search.storageColumn" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in this.storageColumnList"
                                    :key="item.storageColumn"
                                    :label="item.storageColumn"
                                    :value="item.storageColumn">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round type="primary" @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
                <el-button round type="primary" @click="add">添加物料</el-button>
                <el-button round @click="barCodePrint">条码打印</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <!--<el-table-column prop="id" label="序号" sortable>
                </el-table-column>-->
                <el-table-column prop="materialBom.materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialBom.materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialBom.originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column prop="shelfPosition" label="物料位置">
                </el-table-column>
                <el-table-column prop="onhand" label="库存数">
                </el-table-column>
                <el-table-column prop="safeStock" label="安全库存数">
                </el-table-column>
                <el-table-column prop="requiredByOrders" label="订单需求数">
                </el-table-column>
                <el-table-column label="参数列表">
                    <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                        <template slot-scope="scope">
                            {{scope.row.materialBom.materialParameter[param.parameterName]}}
                        </template>
                    </el-table-column>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="outbound(scope.row)">出库</el-button>
                        <el-button size="small" @click="inbound(scope.row)">入库</el-button>
                        <!--<el-button size="small" @click="makeAnInventory(scope.row)">盘点</el-button>-->
                        <el-button size="small" @click="detail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <!-- 编辑弹出框 -->
            <el-dialog title="入库" :visible.sync="inboundVisible" width="50%">
                <el-form ref="inboundForm" :model="inboundForm" label-width="100px">
                    <el-row :gutter="30">
                        <el-col :span="8">
                            <el-form-item label="物料编号:">
                                <el-input v-model="inboundForm.materialCode" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="物料名称:">
                                <el-input v-model="inboundForm.materialName" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="采购价:">
                                <el-input v-model="inboundForm.purchasePrice" type="number" min="0" placeholder="请输入数量" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item  v-for="(project,index) in inboundForm.materielParams" :key="index" :offset="1" :label="project.parameterName==''?('参数'+(index+1)+':'):project.parameterName">
                                <el-input v-model="project.parameterValue" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="12">
                            <el-form-item label="供应商名称:">
                                <el-select @change="selectSupplierName" v-model="inboundForm.supplierName" clearable placeholder="请选择供应商名称">
                                    <el-option
                                            v-for="item in supplierList"
                                            :key="item.id"
                                            :label="item.supplierName"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="供应商编号:">
                                <el-select @change="selectSupplierName" v-model="inboundForm.supplierId" clearable placeholder="请选择供应商编号">
                                    <el-option
                                            v-for="item in supplierList"
                                            :key="item.id"
                                            :label="item.supplierId"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <div>
                        <el-form-item v-show="!isRead">
                            <el-button type="primary" icon="el-icon-plus" @click="addUser" :disabled="isReadonly">添加</el-button>
                        </el-form-item>
                    </div>
                    <div>
                        <div v-for="(item, index) in inboundForm.moreNotifyObject" :key="item.key">
                            <el-form-item label="物料批次:" :prop="'moreNotifyObject.' + index +'.materielBatch'">
                                <el-input v-model="item.materielBatch" placeholder="请输入物料批次" style="width: 50%"></el-input>
                            </el-form-item>
                            <el-form-item label="收货数量:" :prop="'moreNotifyObject.'+ index +'.qty'">
                                <el-input type="number" min="0" v-model="item.qty" placeholder="请输入数量" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="change(item.qty,index)" style="width: 50%"></el-input>
                            </el-form-item>
                            <el-form-item label="所在库位:" :prop="'moreNotifyObject.'+ index +'.shelfPosition'">
                                <el-input v-model="item.shelfPosition" placeholder="请输入库位" style="width: 50%"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button @click="deleteRules(item, index)" :disabled="isReadonly">删除</el-button>
                            </el-form-item>
                        </div>
                    </div>

                    <!--
                    <el-row :gutter="30">
                        <el-col :span="7">
                            <el-form-item label="物料批号:">
                                <el-input v-model="inboundForm.materielBatch"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="收货数量:">
                                <el-input type="number" min="0" v-model="inboundForm.qty" onkeypress='return(/[\d]/.test(String.fromCharCode(event.keyCode)))'></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="仓库位置:">
                                <el-input v-model="inboundForm.shelfPosition"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="3">
                            <el-button type="primary" icon="el-icon-plus" size="small"  @click="addUser"></el-button>
                        </el-col>
                    </el-row>-->
                    <el-row :gutter="30">
                        <el-col :span="12">
                            <el-form-item label="收货总数量:">
                                <el-input v-model="inboundForm.totalNum" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="inboundVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addform')">保 存</el-button>
            </span>
            </el-dialog>
            <div id="subOutputRank-print" style="display: none">
                <div class="content" v-for="(v,k) in barCodelist">
                    <span>{{v.materielName}}</span>
                    <barcode :value="v.barcodes" :options="barcode_option" tag="svg" ></barcode>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import jsbarcode from 'jsbarcode';
    export default {
        components: {},

        data() {
            return {
                tableData: [],
                url: "/materiel/list",
                inboundUrl: "/warehousingEntry/insertByInfo",
                pages: 1,
                inboundVisible:false,
                search: {
                    shelfPosition:null,
                    storageNum:'',
                    storageRow:'',
                    storageLayer:'',
                    storageColumn:'',
                    materielCode:'',
                    materielName:'',
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0
                },
                storageSearch:{
                    status: 1,
                    pageNum: 1,
                    repertoryId: 0
                },
                repertoryName:'',
                inboundForm:{
                    materielShelfPosition:'',
                    purchasePrice:'',
                    materialSearchId:'',
                    materialId:'',
                    materialCode:'',
                    materialName:'',
                    materielBatch:'',
                    supplierId:'',
                    totalNum:0,
                    qty:0,
                    materielParams:[],
                    repertoryId:'',
                    shelfPosition:'',
                    originOfMateriel:'',
                    moreNotifyObject: [{
                        materielBatch:'',
                        qty:0,
                        shelfPosition:''
                    }]
                },
                storageNumList:[],
                storageRowList:[],
                storageLayerList:[],
                storageColumnList:[],
                isReadonly:false,
                isRead:false,
                searchParams: [],
                supplierList:[],
                totalNumList:[],
                barcode_option:{
                    displayValue: true, //是否默认显示条形码数据
                    //textPosition  :'top', //条形码数据显示的位置
                    background: '#fff', //条形码背景颜色
                    valid: function (valid) {
                    },
                    width:'1px',
                    height: '90px',
                    fontSize: '18px' //字体大小
                },
                barCodelist:[]
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
            //初始化数据
            initData(){
                this.inboundForm.moreNotifyObject = []
            },
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
                if (this.search.storageNum != "" && this.search.storageRow == "" && this.search.storageLayer == "" && this.search.storageColumn == "") {
                    this.search.shelfPosition = this.search.storageNum+"-";
                } else if (this.search.storageNum != "" && this.search.storageRow != "" && this.search.storageLayer == "" && this.search.storageColumn == "") {
                    this.search.shelfPosition = this.search.storageNum+"-"+this.search.storageRow+"-";
                } else if (this.search.storageNum != "" && this.search.storageRow != "" && this.search.storageLayer != "" && this.search.storageColumn == "") {
                    this.search.shelfPosition = this.search.storageNum+"-"+this.search.storageRow+"-"+this.search.storageLayer+"-";
                } else if (this.search.storageNum != "" && this.search.storageRow != "" && this.search.storageLayer != "" && this.search.storageColumn != "") {
                    this.search.shelfPosition = this.search.storageNum+"-"+this.search.storageRow+"-"+this.search.storageLayer+"-"+this.search.storageColumn;
                }else{
                    this.search.shelfPosition = null;
                }
                this.repertoryName = this.$route.query.repertoryName
                if (this.$route.query.repertoryId != null) {
                    this.search.repertoryId = this.$route.query.repertoryId;
                    this.storageSearch.repertoryId = this.$route.query.repertoryId;
                    this.inboundForm.repertoryId = this.$route.query.repertoryId;
                } else {
                    //this.$router.push("/repertoryList");
                    this.search.repertoryId = 1;
                }
                this.$http.post("/material/paramNames").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.searchParams = res.data.data;
                    }
                });
                this.$http.post(this.url, this.search).then(res => {
                    this.barCodelist = []
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.pages;
                        for(var i=0;i<res.data.data.list.length;i++){
                            this.barCodelist.push({barcodes:res.data.data.list[i].materielCode,materielName:res.data.data.list[i].materielName})
                        }
                    }
                });
                this.$http.post("/supplier/list", this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.supplierList = res.data.data.list;
                        this.pages = res.data.data.pages;
                    }
                });
                this.$http.post("/productionPlan/getProductionBatchList", {pageNum:1,materielId:this.inboundForm.materialId}).then(res => {
                    if (res != undefined && res.data.code == 1000) {

                    }
                });
                this.$http.post("/storage/getStorageNumList", this.storageSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.storageNumList = res.data.data;
                    }
                });
                /*this.$http.post("/productionPlan/getBarCode", { id: 79 }).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.barcodeUrl = res.data.data.barcodeUrl
                    }
                });*/
            },
            add() {
                this.$router.push({
                    path: "/materielInfo",
                    query: {
                        repertoryId: this.$route.query.repertoryId
                    }
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
            outbound(row) {
                this.$router.push({
                    path: "/outboundList",
                    query: {
                        repertoryId: this.$route.query.repertoryId,
                        row:row
                    }
                });
            },
            inbound(row) {
                this.inboundForm.materialSearchId = row.id
                this.inboundForm.materielParams = row.materialBom.materialParameters
                this.inboundForm.materialCode = row.materialBom.materialCode
                this.inboundForm.materialId = row.materialBom.id
                this.inboundForm.materialName = row.materialBom.materialName
                this.inboundForm.originOfMateriel = row.materialBom.originalMaterial
                this.inboundVisible = true;
            },
            detail(row){
                this.$router.push({
                    path: "/materielDetail",
                    query: {
                        repertoryId: this.$route.query.repertoryId,
                        row:row
                    }
                });
            },
            edit(row) {
                this.$router.push({
                    path: "/materielInfo",
                    query: {
                        materielId: row.id,
                        repertoryId: this.$route.query.repertoryId
                    }
                });
            },
            makeAnInventory(row){
                this.$router.push({
                    path: "/materielInfo",
                    query: {
                        materielId: row.id,
                        repertoryId: this.$route.query.repertoryId
                    }
                });
            },
            clearData(){
                this.search.materielCode = ''
                this.search.materielName = ''
                this.search.storageLayer = ''
                this.search.storageNum = ''
                this.search.storageRow = ''
                this.search.storageColumn = ''
            },
            addMaterial(){
            },
            selectSupplierName(e) {
            },
            insert(){
                this.$http.post(this.inboundUrl,this.inboundForm).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.searchParams = res.data.data;
                        this.$message.success("保存成功");
                        this.inboundVisible = false
                    }
                });
            },
            addUser() {
                this.inboundForm.moreNotifyObject.push({
                    materielBatch:'',
                    qty:0,
                    shelfPosition:''
                })
            },
            change(qty,index) {
                if(qty != null && qty != ""){
                    this.totalNumList[index] = qty
                } else {
                    this.totalNumList[index] = 0
                }
                var totalNum = 0;
                for(var i=0;i<this.totalNumList.length;i++){
                    totalNum += parseFloat(this.totalNumList[i]);
                }
                this.inboundForm.totalNum = parseFloat(totalNum).toFixed(2)*1;
            },
            deleteRules(item, index) {
                this.index = this.inboundForm.moreNotifyObject.indexOf(item)
                if (index !== -1) {
                    this.inboundForm.moreNotifyObject.splice(index, 1)
                }
                this.totalNumList.splice(index,1)
                var totalNum = 0;
                for(var i=0;i<this.totalNumList.length;i++){
                    totalNum += parseFloat(this.totalNumList[i]);
                }
                this.inboundForm.totalNum = parseFloat(totalNum).toFixed(2)*1;
            },
            changeStorageRow(item){
                this.storageSearch.storageNum = item
                this.$http.post("/storage/getStorageRowList", this.storageSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.storageRowList = res.data.data;

                        /*storageNumList:[],
                            storageRowList:[],
                            storageLayerList:[],
                            storageColumnList:[],*/
                    }
                });
            },
            changeStorageLayer(item){
                this.storageSearch.storageRow = item
                this.$http.post("/storage/getStorageLayerList", this.storageSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.storageLayerList = res.data.data;

                        /*storageNumList:[],
                            storageRowList:[],
                            storageLayerList:[],
                            storageColumnList:[],*/
                    }
                });
            },
            changeStorageColumn(item){
                this.storageSearch.storageLayer = item
                this.$http.post("/storage/getStorageColumnList", this.storageSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.storageColumnList = res.data.data;
                    }
                });
            }
        },
        watch: {
            // 如果路由发生变化，再次执行该方法
            $route: "getData"
        }
    };
</script>
<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center;
    }
    .content {
        height: 73px;
        width: 100px;
        float: left;
        text-align: center;
        font-size:15px
    }
</style>
