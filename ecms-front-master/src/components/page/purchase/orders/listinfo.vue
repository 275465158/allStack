<template>
    <div class="container">
        <div clas="handle-box">
            <el-form label-width="100px" v-model="purchaseForm">
                <el-row :gutter="30">
                    <el-col :span="6">
                        <el-form-item label="采购单号">
                            <el-input v-model="purchaseForm.purchaseCode" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购时间">
                            <el-date-picker v-model="purchaseForm.purchaseDate" value-format="yyyy-MM-dd" disabled></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购人">
                            <el-input v-model="purchaseForm.purchasePerson" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购原因">
                            <el-input v-model="purchaseForm.purchaseReason" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="供应商">
                            <el-input v-model="purchaseForm.supplierName" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料编码">
                            <el-input v-model="purchaseForm.materialCode" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料类型">
                            <el-input v-model="purchaseForm.materialBomInfo.type" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料名称">
                            <el-input v-model="purchaseForm.materialName" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料材料">
                            <el-input v-model="purchaseForm.materialBomInfo.originalMaterial" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料参数">
                            <el-input v-model="purchaseForm.materialBomInfo.materialBomParamValueStr" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="物料重量">
                            <el-input v-model="purchaseForm.weight" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="结算方式">
                            <el-input v-model="purchaseForm.paymentMethod===0?'按重量':'按数量'" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="采购数量">
                            <el-input v-model="purchaseForm.purchaseQty" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="实际收货数">
                            <el-input v-model="purchaseForm.actualDeliveryQty" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="收货数">
                            <el-input v-model="purchaseForm.deliveryQty" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="退货数">
                            <el-input v-model="purchaseForm.returnQty" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="结算数量">
                            <el-input v-model="purchaseForm.settlementQty" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="单价">
                            <el-input v-model="purchaseForm.purchasePrice" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="总价">
                            <el-input v-model="purchaseForm.purchasePriceTotal" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="核销人">
                            <el-input v-model="purchaseForm.verificatPerson" disabled></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="核销时间">
                            <el-input v-model="purchaseForm.verificatDate" disabled></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <hr class="marginTop" />
            <span class="text">收货退货记录</span>
            <hr class="marginBottom" />
            <el-table :data="deliveryReturnTable" border style="width:100%">
                <el-table-column prop="id" label="序号">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="deliveryCode" label="收货单号"></el-table-column>
                <el-table-column prop="returnedCode" label="退货单号"></el-table-column>
                <el-table-column prop="materialBatch" label="物料批次"></el-table-column>
                <el-table-column prop="inventoryQty" label="收货数量"></el-table-column>
                <el-table-column prop="returnedPurchaseQty" label="退货数量"></el-table-column>
                <el-table-column prop="person" label="操作人"></el-table-column>
                <el-table-column prop="date" label="操作时间"></el-table-column>
            </el-table>
            <hr class="marginTop" />
            <el-button @click="abort">取消</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                purchaseForm: {
                    materialBomInfo:{
                        type:'',
                        originalMaterial:'',
                        materialBomParamValueStr:'',
                    },
                    verificatPerson:'',
                    verificatDate:'',
                },
                editVisible: false,
                search:{
                    id:'',
                },
                deliveryReturnTable:[],
            };
        },
        created() {
            this.search.id = this.$route.query.id;
            this.getData();
        },
        computed: {},
        methods: {
            abort() {
                this.$router.push({path: "/purchaseOrderInfo", query: {closeFlag: 1, works: 2}});
            },
            handle_select(row) {
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                if (this.$route.query.id != undefined) {
                    this.search.id = this.$route.query.id;
                }
                let _this = this
                this.$http.post("/purchaseOrdersList/searchById",{id:this.search.id}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.purchaseForm = res.data.data;
                    //获取到基本数据后去获取退货单数据
                    _this.$http.post("/returnedPurchase/searchList",{id:_this.search.id,materialCode:this.purchaseForm.materialCode}).then(res => {
                        _this.purchaseForm.returnQty = 0;
                    if (res != undefined && res.data.code == 1000) {
                        _this.deliveryReturnTable = res.data.data
                        if (_this.deliveryReturnTable.length!=0){
                            for (var i=0;i<_this.deliveryReturnTable.length;i++){
                                _this.purchaseForm.returnQty = _this.purchaseForm.returnQty + _this.deliveryReturnTable[i].returnedPurchaseQty
                            }
                        }
                        //最后获取收货单数据
                        _this.$http.post("/materialRepertoryDelivery/searchList",{id:_this.search.id,materialCode:this.purchaseForm.materialCode}).then(res => {
                            this.purchaseForm.deliveryQty = 0;
                        if (res != undefined && res.data.code == 1000) {
                            //如果又退货单数据，就往原来的table里面把收货单数据加进去
                            if (_this.deliveryReturnTable.length!=0){
                                if (res.data.data.length!=0){
                                    for (var i=0;i<res.data.data.length;i++){
                                        _this.deliveryReturnTable.push(res.data.data[i])
                                        _this.purchaseForm.deliveryQty = _this.purchaseForm.deliveryQty+res.data.data[i].inventoryQty
                                    }
                                }
                            } else {
                                //如果没有则直接把res.data.data给table
                                _this.deliveryReturnTable = res.data.data
                                if (_this.deliveryReturnTable.length!=0){
                                    for (var i=0;i<_this.deliveryReturnTable.length;i++){
                                        _this.purchaseForm.deliveryQty = _this.purchaseForm.deliveryQty + _this.deliveryReturnTable[i].inventoryQty
                                    }
                                }
                            }
                        }
                        //最后根据收货数和退货数算出实际收货数
                        _this.purchaseForm.actualDeliveryQty = _this.purchaseForm.deliveryQty-_this.purchaseForm.returnQty;
                    });
                    }
                });
                }
            });
            },
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/purchaseOrderListInfo') {
                    let newId = this.$route.query.id;
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if (this.search.id != newId) {
                        this.search.id = newId;
                        this.getData();
                    }
                }
            }
        }
    };
</script>

<style scoped>
    .handle-box {
        margin-bottom: 50px;
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
    hr {
        border-top: 1px;
    }
    .marginTop {
        margin-top: 10px;
        margin-bottom: 5px;
    }
    .marginBottom {
        margin-top: 5px;
        margin-bottom: 10px;
    }
    .text {
        font-size: 12px;
        color: #606266;
        margin-right: 30px;
    }
</style>
