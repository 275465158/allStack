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
                    <el-form-item label="订单编号：">
                        <el-input v-model="search.orderCode"></el-input>
                    </el-form-item>
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
                <span class="el-form-item__label">待发货列表</span>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="contractId" label="合同编号">
                </el-table-column>
                <el-table-column prop="orderCode" label="订单编号">
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
                <el-table-column prop="sendQty" label="发货总数量">
                </el-table-column>
                <el-table-column prop="alreadySendQty" label="已发货数量">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="delivery(scope.row)">发货</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <el-dialog title="发货" :visible.sync="noticeArrivedVisible" width="60%">
                <el-form ref="deliveryForm" :model="deliveryForm" label-width="80px">
                    <el-form-item label="物料名称:">
                        <el-input v-model="deliveryForm.materialName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料编码:">
                        <el-input v-model="deliveryForm.materialCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="发货总数:">
                        <el-input v-model="deliveryForm.sendQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="已发货数:">
                        <el-input v-model="deliveryForm.alreadySendQty" disabled></el-input>
                    </el-form-item>
                </el-form>
                <el-table :data="deliveryData" border style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="materialCode" label="物料编码">
                    </el-table-column>
                    <el-table-column prop="factoryMaterialCode" label="工厂内部编号">
                    </el-table-column>
                    <el-table-column prop="materialName" label="物料名称">
                    </el-table-column>
                    <el-table-column prop="number" label="物料类型">
                    </el-table-column>
                    <el-table-column prop="originalMaterial" label="材质">
                    </el-table-column>
                    <el-table-column prop="materialBomParamValueStr" label="规格">
                    </el-table-column>
                    <el-table-column prop="drawingCode" label="图号">
                    </el-table-column>
                    <el-table-column prop="source" label="来源">
                    </el-table-column>
                    <el-table-column prop="supplierName" label="供应商">
                    </el-table-column>
                    <el-table-column prop="materialBatch" label="批次号">
                    </el-table-column>
                    <el-table-column prop="storageLocation" label="库存位置">
                    </el-table-column>
                    <el-table-column prop="inventoryQty" label="库存数量">
                    </el-table-column>
                    <el-table-column label="发货数量" align="center">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.sendMaterialQty" min="0" :max="scope.row.inventoryQty" type="number" v-on:input="change(scope.row,scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                </el-table>
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
            deliveryData:[],
            tableData: [],
            url: "/materialSendDetail/like",
            materialDetailUrl:"/materialRepertoryDetail/detailListByLike",
            materialDetailUpdateUrl:"/materialSendDetail/materialSend",
            pages: 1,
            noticeArrivedVisible:false,
            search: {
                status: 1,
                pageNum: 1,
                materialCode:'',
                materialName:'',
                contractId:'',
                orderCode:'',
                pageSize:20
            },
            materialSearch:{
                repertoryId:0,
                materialCode:'',
                status: 1,
                pageNum: 1,
                pageSize:1000
            },
            deliveryForm:{
                materialName:'',
                materialCode:'',
                sendQty:''
            },
            form:{
                id:0, //material_send_detail表id
                materialSendList:[],
                orderCode:''
            },
            deliveryTotalQty:0, //发货总数量
            alreadySendQty:0,   //已发货数量
            sendQty:0,   //代发货数量
            insertFlag:1,    //插入flag,判断页面数字输入是否有误，1为执行插入方法，0为不执行方法
            orderCode:'',    //订单编号
            repertoryName: ''
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
        searchLike(){
            this.search.pageNum = 1;
            this.getData();
        },
        getData() {
            this.search.repertoryId = this.$route.query.repertoryId;
            this.repertoryName = this.$route.query.repertoryName;
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
            this.search.orderCode = ''
            this.search.contractId = ''
            this.search.source = ''
            this.search.drawingCode = ''
            this.search.materialBomParamValueStr = ''
            this.search.originalMaterial = ''
            this.search.number = ''
            this.search.factoryMaterialCode = ''
        },
        delivery(row){
            this.form.orderCode = row.orderCode
            this.alreadySendQty = row.alreadySendQty
            this.sendQty = row.sendQty
            this.form.id = row.id
            this.deliveryForm.materialName = row.materialName
            this.deliveryForm.materialCode = row.materialCode
            this.deliveryForm.sendQty = row.sendQty
            this.deliveryForm.alreadySendQty = row.alreadySendQty
            this.materialSearch.repertoryId = this.$route.query.repertoryId;
            this.materialSearch.materialCode = row.materialCode;
            this.form.materialSendList = [];
            this.$http.post(this.materialDetailUrl, this.materialSearch).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.deliveryData = res.data.data.list;
                    // this.pages = res.data.data.total;
                }
            });
            this.noticeArrivedVisible = true;
        },
        change(row,index){
            this.deliveryTotalQty = 0;
            if(row.sendMaterialQty > row.inventoryQty ){
                this.$message.error("发货数量不能库存数");
                this.insertFlag = 0
            }else{
                this.insertFlag = 1
            }
            let sendList = {
                repertoryId:row.repertoryId,
                materialCode:row.materialCode,
                materialId:row.materialId,
                sendMaterialQty:row.sendMaterialQty,
                materialDetailId:row.id
            }
            this.form.materialSendList.splice(index,1,sendList)
            if(row.sendMaterialQty == null || row.sendMaterialQty == ""){
                this.form.materialSendList.splice(index,1)
            }
            for(var i=0;i<this.form.materialSendList.length;i++){
                this.deliveryTotalQty += parseFloat(this.form.materialSendList[i].sendMaterialQty)
            }

            if(parseFloat(this.deliveryTotalQty)+parseFloat(this.alreadySendQty) > parseFloat(this.sendQty)){
                this.$message.error("发货总数和已发货数量不能大于总待发数量");
                this.insertFlag = 0
            }else{
                this.insertFlag = 1
            }
        },
        insertDelivery(){
            if(this.insertFlag == 1){
                this.$http.post(this.materialDetailUpdateUrl, this.form).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("发货成功");
                        this.noticeArrivedVisible = false;
                        this.getData();
                    }
                });
            }else{
                this.$message.error("请核对输入");
            }
        }
    },
    watch: {
        '$route' (to, from) {
                if (to.path == '/sendDeliveryList') {
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
