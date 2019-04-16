<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
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
                    <el-row :gutter="30">
                        <el-col :span="12">
                            <el-form-item label="供应商名称:">
                                <el-select @change="selectSupplierId" v-model="inboundForm.supplierName" clearable placeholder="请选择供应商名称">
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
                                <el-select @change="selectSupplierName" v-model="inboundForm.supplierCode" clearable placeholder="请选择供应商编号">
                                    <el-option
                                            v-for="item in supplierList"
                                            :key="item.id"
                                            :label="item.supplierCode"
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
                            <el-form-item label="物料批次:" :prop="'moreNotifyObject.' + index +'.materialBatch'">
                                <el-input v-model="item.materialBatch" placeholder="请输入物料批次" style="width: 50%"></el-input>
                            </el-form-item>
                            <el-form-item label="收货数量:" :prop="'moreNotifyObject.'+ index +'.qty'">
                                <el-input type="number" min="0" v-model="item.qty" placeholder="请输入数量" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="change(item.qty,index)" style="width: 50%"></el-input>
                            </el-form-item>
                            <el-form-item label="所在库位:" :prop="'moreNotifyObject.'+ index +'.shelfPosition'">
                                <el-cascader v-model="item.shelfPosition" :options="optionsRepertory" placeholder="货架号/层/排/列">
                                </el-cascader>
                            </el-form-item>
                            <el-form-item>
                                <el-button @click="deleteRules(item, index)" :disabled="isReadonly">删除</el-button>
                            </el-form-item>
                        </div>
                    </div>
                    <el-row :gutter="30">
                        <el-col :span="12">
                            <el-form-item label="收货总数量:">
                                <el-input v-model="inboundForm.totalNum" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="insert('')">保 存</el-button>
                    <el-button @click="goBack">取 消</el-button>
                </span>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                url: "/materialRepertoryDetail/saveInboundInfo",
                pages: 1,
                inboundForm:{
                    ordeNeedFlag:1,
                    purchasePrice:0,
                    materialSearchId:'',
                    materialId:'',
                    materialCode:'',
                    materialName:'',
                    supplierId:'',
                    totalNum:0,
                    repertoryId:'',
                    moreNotifyObject: [{
                        materialBatch:'',
                        qty:0,
                        shelfPosition:[]
                    }]
                },
                search:{
                    repertoryId:0
                },
                isReadonly:false,
                isRead:false,
                repertoryName:'',
                supplierList:[],
                materialCode:'',
                totalNumList:[],
                optionsRepertory: [],
            };
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            getData() {
                this.inboundForm.materialCode = this.$route.query.row.materialCode;
                this.inboundForm.materialName = this.$route.query.row.materialName;
                this.inboundForm.materialId = this.$route.query.row.materialId;
                this.inboundForm.originalMaterial = this.$route.query.row.originalMaterial;
                this.repertoryName = this.$route.query.repertoryName;
                this.inboundForm.repertoryId = this.$route.query.repertoryId;
                this.search.repertoryId = this.$route.query.repertoryId;
                if(this.inboundForm.repertoryId != null && this.inboundForm.repertoryId != undefined){
                    this.$http.post("/supplier/supplierList", {materialId: this.inboundForm.materialId}).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.supplierList = res.data.data;
                            this.pages = res.data.data.pages;
                        }
                    });
                    this.$http.post("/storage/getJson", { repertoryId: this.inboundForm.repertoryId }).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.optionsRepertory = JSON.parse(res.data.data);
                        }
                    });
                }
            },
            addUser() {
                this.inboundForm.moreNotifyObject.push({
                    materialBatch:'',
                    qty:0,
                    shelfPosition:[]
                })
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
            insert(){
                // this.inboundForm.moreNotifyObject[0].shelfPosition = ''
                if(this.inboundForm.purchasePrice == 0){
                    this.$message.error("请输入采购价");
                    return;
                }
                if(this.inboundForm.supplierId == "" || this.inboundForm.supplierName == ""){
                    this.$message.error("请选择供应商");
                    return;
                }
                if(this.inboundForm.moreNotifyObject.length != 0){
                    for(var i = 0;i<this.inboundForm.moreNotifyObject.length;i++){
                        var storageLocationStr = '';
                        for(var j = 0;j<4;j++){
                            if(this.inboundForm.moreNotifyObject[i].materialBatch == null || this.inboundForm.moreNotifyObject[i].materialBatch == ""){
                                this.$message.error("请输入物料批次");
                                return;
                            }
                            if(this.inboundForm.moreNotifyObject[i].shelfPosition[j] != null && this.inboundForm.moreNotifyObject[i].shelfPosition[j] != ""){
                                if(j<3){
                                    storageLocationStr += this.inboundForm.moreNotifyObject[i].shelfPosition[j]+"_"
                                }else{
                                    storageLocationStr += this.inboundForm.moreNotifyObject[i].shelfPosition[j]
                                }
                            }
                        }
                        this.inboundForm.moreNotifyObject[i].shelfPosition = storageLocationStr
                    }
                }
                this.$http.post(this.url,this.inboundForm).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("保存成功");
                        this.goBack();
                    }
                });
            },
            selectSupplierName(e) {
                this.$http.post("supplier/material/getPrice",{materialId:this.inboundForm.materialId,supplierId:e}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.inboundForm.purchasePrice = res.data.data[0].price
                    }
                });
                this.inboundForm.supplierName = e
                this.inboundForm.supplierId = e
            },
            selectSupplierId(e){
                this.$http.post("supplier/material/getPrice",{materialId:this.inboundForm.materialId,supplierId:e}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.inboundForm.purchasePrice = res.data.data[0].price
                    }
                });
                this.inboundForm.supplierCode = e
                this.inboundForm.supplierId = e
            },
            goBack() {
                this.$router.push({
                    path: "/materialRepertoryList",
                    query: { repertoryId: this.inboundForm.repertoryId, closeFlag: 1, repertoryName: this.$route.query.repertoryName }
                });
            },
        },
        watch: {
            '$route' (to, inboundForm) {
                if (to.path == '/materialInboundList' && this.$route.query.works !== 1) {
                    Object.assign(this.$data, this.$options.data())

                    this.getData();
                }
            }
        },
    };
</script>
<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
    hr {
        border-top: 1px;
    }
</style>