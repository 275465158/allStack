<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="form" class="demo-form-inline">
                    <el-form-item label="合同编号:">
                        <el-input v-model="form.contractId" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="生产单号:">
                        <el-input v-model="form.productionNumber" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="form.materialName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料编码:">
                        <el-input v-model="form.materialCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型:">
                        <el-input v-model="form.number" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="材料:">
                        <el-input v-model="form.originalMaterial" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="规格:">
                        <el-input v-model="form.materialBomParamValueStr" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="生产数量:">
                        <el-input v-model="form.materialNum" disabled></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">发料明细</span>
            </div>
            <div class="handle-box">
                <el-table v-loading="loading" :data="tableData" border stripe style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="materialBomInfo.materialCode" label="物料编号"/>
                    <el-table-column prop="materialBomInfo.materialName" label="物料名称"/>
                    <el-table-column prop="materialBomInfo.originalMaterial" label="材料"/>
                    <el-table-column prop="materialBomInfo.type" label="物料类型"/>
                    <el-table-column prop="materialBomInfo.materialBomParamValueStr" label="规格"/>
                    <el-table-column prop="quantity" label="配套比例">
                        <template slot-scope="scope">
                            1:{{scope.row.quantity}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="quantity" label="应发数量">
                        <template slot-scope="scope">
                            {{form.materialNum*(scope.row.quantity)}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="issueQty" label="已发数量"/>
                    <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button size="small" @click="productionIssue(scope.row)">发料</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-dialog v-loading="productionIssueLoading" title="发料" :visible.sync="productionIssueVisible" width="75%">

                <el-form :inline="true" :model="issueForm" class="demo-form-inline">
                    <el-form-item label="生产单号：">
                        <el-input v-model="issueForm.productionNumber" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号：">
                        <el-input v-model="issueForm.materialCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="issueForm.materialName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型：">
                        <el-input v-model="issueForm.numberType" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="材料：">
                        <el-input v-model="issueForm.originalMaterial" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="规格：">
                        <el-input v-model="issueForm.materialBomParamValueStr" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="应发总数：">
                        <el-input v-model="issueForm.needQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="已发数量：">
                        <el-input v-model="issueForm.productionIssueQty" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="剩余应发数量：">
                        <el-input v-model="issueForm.surplusNeedQty" disabled></el-input>
                    </el-form-item>
                </el-form>
                <div class="handle-box">
                    <span class="el-form-item__label">发料信息列表</span>
                </div>
                <el-table :data="materialTableData" border style="width:100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="repertoryName" label="仓库名称">
                    </el-table-column>
                    <el-table-column prop="storageLocation" label="物料位置">
                    </el-table-column>
                    <el-table-column prop="supplierName" label="供应商名称">
                    </el-table-column>
                    <el-table-column prop="materialBatch" label="物料批次">
                    </el-table-column>
                    <el-table-column prop="numberType" label="物料类型">
                    </el-table-column>
                    <el-table-column prop="inventoryQty" label="库存数">
                    </el-table-column>
                    <el-table-column prop="productionIssueQty" label="发料数">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.productionIssueQty" type="number" style="width: 100px"
                                      :max="scope.row.inventoryQty" :min="0" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                      v-on:input="change(scope.row,scope.$index)">
                            </el-input>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                    </el-pagination>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="saveProductionIssue">确 定</el-button>
                    <el-button @click="productionIssueVisible = false">取 消</el-button>
                </span>
            </el-dialog>
            <span slot="footer" class="dialog-footer">
                <el-button @click="autoIssue" type="primary">发 料</el-button>
                <el-button @click="goPrint">打 印</el-button>
                <el-button @click="goBack">返 回</el-button>
            </span>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                productionIssueLoading:false,
                tableData: [],
                materialTableData: [],
                url:"materialRelation/detail",
                materialUrl:"materialRepertoryDetail/getIssueMaterialList",
                issueUrl:"productionPlan/insertIssue",
                autoIssueUrl:"productionPlan/autoIssue",
                pages: 1,
                productionIssueVisible: false,
                form: {
                    productionNumber:'',
                    materialName:'',
                    materialCode:'',
                    numberType: '',
                    originalMaterial: '',
                    materialBomParamValueStr: '',
                    materialNum: 0,
                    productionIssueQty: 0,
                    contractId:''
                },
                issueForm:{
                    materialName:'',
                    materialCode:'',
                    numberType: '',
                    originalMaterial: '',
                    materialBomParamValueStr: '',
                    productionIssueQty: 0,
                    materialNum:0,
                    contractId:'',
                    id:''
                },
                search:{
                    pageNum:1,
                    pageSize:20,
                    materialId:'',
                    surplusNeedQty:''
                },
                insertIssueForm:{
                    parentProductionNumber:'',
                    issueQty:0,
                    id:'',
                    materialTable:[]
                },
                productionNumberSearch:{
                    contractId:'',
                    materialId:''
                },
                saveFlag:1,
                loading: false,
                autoIssueForm:{
                    id:'',
                    productionNumber:'',
                    materialTable:[]
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
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取数据
            getData() {
                this.form = JSON.parse(this.$route.query.row);
                this.loading = true;
                this.$http.post(this.url,{id:this.form.materialId,productionNumber:this.form.productionNumber}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data;
                        this.pages = res.data.data.total;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.loading = false;
                });
            },
            goBack() {
                this.$router.push({
                    path: "/productionIssue",
                    query: {closeFlag: 1}
                });
            },
            goPrint () {
                let data = JSON.stringify({form: this.form, tableData: this.tableData})
                this.$router.push({
                    path: "/productionIssuePrint",
                    query: {data}
                });
            },
            saveProductionIssue(){
                this.productionIssueLoading = true;
                if(this.saveFlag == 1){
                    this.insertIssueForm.parentProductionNumber = this.form.productionNumber;
                    this.insertIssueForm.id = this.form.id;
                    this.insertIssueForm.materialTable = this.materialTableData;
                    let issueQty = 0;
                    for(let i=0;i<this.materialTableData.length;i++){
                        let issueQtyStr = this.materialTableData[i].productionIssueQty;
                        if(issueQtyStr != "" && issueQtyStr != undefined && issueQtyStr != null){
                            issueQty += parseFloat(this.materialTableData[i].productionIssueQty);
                        }
                    }
                    this.insertIssueForm.issueQty = issueQty.toFixed(2);
                    this.$http.post(this.issueUrl,this.insertIssueForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$message.success("发料成功");
                            this.productionIssueVisible = false;
                            this.getData();
                        }
                        this.productionIssueLoading = false;
                    });
                }else{
                    this.$message.error("请检查发料数量！");
                }

            },
            productionIssue(row){
                this.productionIssueLoading = true;
                this.search.materialId = row.materialBomInfo.id;
                this.search.repertoryId = this.$route.query.repertoryId;
                this.productionNumberSearch.contractId = this.form.contractId;
                this.productionNumberSearch.materialId = row.materialBomInfo.id;
                this.issueForm.needQty = this.form.materialNum*(row.quantity);
                this.search.surplusNeedQty = this.issueForm.needQty - row.issueQty;
                this.productionIssueVisible = true;
                this.$http.post(this.materialUrl,this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materialTableData = res.data.data;
                        this.pages = this.materialTableData.length;
                        this.issueForm.materialName = row.materialBomInfo.materialName;
                        this.issueForm.materialCode = row.materialBomInfo.materialCode;
                        this.issueForm.numberType = row.numberType;
                        this.issueForm.originalMaterial = row.materialBomInfo.originalMaterial;
                        this.issueForm.materialBomParamValueStr = row.materialBomParamValueStr;
                        this.issueForm.productionIssueQty = row.issueQty;
                        this.issueForm.materialNum = row.materialNum;
                        this.issueForm.productionNumber = this.form.productionNumber;
                        this.issueForm.surplusNeedQty = this.issueForm.needQty - row.issueQty;
                    }
                    this.productionIssueLoading = false;
                }).catch(err => {
                    this.productionIssueLoading = false;
                });

            },
            change(row, index){
                if(row.productionIssueQty > row.inventoryQty){
                    this.$message.error("发料数量不能大于库存数量！");
                    this.saveFlag = 0;
                }else{
                    this.saveFlag = 1;
                }
                let issueQty = 0;
                for(let i=0;i<this.materialTableData.length;i++){
                    if(this.materialTableData[i].productionIssueQty != undefined){
                        issueQty += parseFloat(this.materialTableData[i].productionIssueQty);
                    }
                }
                if(issueQty>this.issueForm.surplusNeedQty){
                    this.$message.error("发料数量不能大于剩余应发数量!");
                    this.saveFlag = 0;
                }else{
                    this.saveFlag = 1;
                }
            },
            selectProductionNumber(e){
            },
            autoIssue(){
                this.autoIssueForm.productionNumber = this.form.productionNumber;
                this.autoIssueForm.id = this.form.id;
                this.autoIssueForm.materialTable = this.tableData;
                let materialTableArr = [];
                for(var i=0;i<this.tableData.length;i++){
                    materialTableArr.push({
                        materialId : this.tableData[i].materialBomInfo.id,
                        quantity : this.tableData[i].quantity,
                        needIssueQty : this.form.materialNum*this.tableData[i].quantity,
                        issueQty : this.tableData[i].issueQty
                    });
                }
                this.autoIssueForm.materialTable = materialTableArr
                this.$confirm("是否一键发料，点击确定继续", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    this.$http.post(this.autoIssueUrl, this.autoIssueForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$message.success("发料成功");
                            this.getData();
                        }
                    });
                }).catch(() => {
                    this.$message.error("发料取消");
                });
            }
        },
        mounted() {},
        watch: {
            '$route' (to, form) {
                if (to.path == '/productionIssueInfo' && this.$route.query.works != 1) {
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

