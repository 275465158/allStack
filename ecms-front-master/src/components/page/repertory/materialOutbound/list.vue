<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
                <el-form label-width="100px" :model="pickingForm">
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="领料用途">
                                <el-select v-model="form.pickingUseage" clearable placeholder="请选择领料用途" @change="addInput">
                                    <el-option
                                            v-for="item in this.productionBatch"
                                            :key="item.productBatch"
                                            :label="item.productBatch"
                                            :value="item.productBatch">
                                    </el-option>
                                    <el-option  label="其他" value="qt"></el-option>
                                </el-select>
                                <div v-show="productionVisible"><el-input v-model="form.otherPickingUseage" style="width: 215px" placeholder="请输入领料用途"></el-input></div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="领料人" prop="">
                                <el-select v-model="form.takeMaterialId" clearable placeholder="请选择领料人">
                                    <el-option
                                            v-for="item in this.employeeList"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="领料时间">
                                <el-date-picker value-format="yyyy-MM-dd" v-model="form.outboundDate"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出库总数">
                                <el-input v-model="form.inventoryQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="物料编号">
                                <el-input v-model="form.materialCode" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="物料名称">
                                <el-input v-model="form.materialName" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="材质">
                                <el-input v-model="form.originalMaterial" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="单位">
                                <el-input v-model="form.materialUnit" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">仓库物料列表</span>
            </div>
            <el-table border style="width:100%" :data="tableData">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
                <el-table-column label="物料批次" prop="materialBatch"></el-table-column>
                <el-table-column label="物料位置" prop="storageLocation"></el-table-column>
                <el-table-column label="库存数" prop="inventoryQty">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{scope.row.inventoryQty}}</span>
                        <div class="el-input" v-if="scope.row.editFlag">
                            <input class="el-input__inner" type="text" v-model="scope.row.inventoryQty" @input="handleQtyChange(scope.row, scope.$index)" />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="出库数">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.outBoundNum" type="number" style="width: 100px"
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
            <span class="dialog-footer">
                <el-button type="primary" @click="saveOutbound">保存</el-button>
                <el-button @click="goBack">返回</el-button>
            </span>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                tableData: [],
                isAble: false,
                url: "/materiel/picking/info",
                pages: 1,
                form:{
                    pickingCode:'',
                    pickingUseage:'',
                    otherPickingUseage:'',
                    preparedBy:'',
                    takeMaterialId:'',
                    outboundDate:'',
                    pickingStatus:'完成',
                    materialCode:'',
                    materialName:'',
                    materialId:'',
                    originalMaterial:'',
                    inventoryQty:0,
                    materialUnit:'',
                    repertoryId:'',
                    materialTotalQty:0,
                    tableDataRow:[]
                },
                search: {
                    materialCode: '',
                    repertoryId: 0,
                    pageNum: 1,
                    status: 1,
                    pageSize:20
                },
                productionVisible:false,
                //属性列表
                searchParams: [],
                //出库表单
                pickingForm: {},
                //库存明细列表
                //出库状态
                // statusOptions: ["待确认", "完成", "取消"],
                statusOptions: ["完成"],
                selectedInventory: [],
                outBoundNumList: [],
                insertFlag: 1,//插入标识
                employeeList:[],
                tableRowData:[],
                productionBatch:[],
                repertoryName:''
            };
        },
        created() {
            this.repertoryName = this.$route.query.repertoryName;
            this.form.materialCode = this.$route.query.row.materialCode
            this.form.materialName = this.$route.query.row.materialName
            this.form.originalMaterial = this.$route.query.row.originalMaterial
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
                this.search.repertoryId = this.$route.query.repertoryId;
                this.form.repertoryId = this.$route.query.repertoryId;
                this.form.materialTotalQty = this.$route.query.row.inventoryQty;
                this.search.materialCode = this.$route.query.row.materialCode
                if(this.$route.query.repertoryId != null && this.$route.query.repertoryId != undefined){
                    this.$http.post("materialRepertoryDetail/detailListByLike", this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData = res.data.data.list;
                            if(res.data.data.list.length != 0){
                                this.form.materialUnit = res.data.data.list[0].materialUnit
                            }
                            this.pages = res.data.data.total;
                        }
                    });
                    this.$http.post('/employee/listEmployeeByInfo', this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.employeeList = res.data.data.list;
                        }
                    });
                    this.$http.post("/productionPlan/getProductionBatchList", {pageNum:1,materialId:this.$route.query.row.materialId}).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.productionBatch = res.data.data
                        }
                    });
                }
            },
            addInput(event){
                if (event=='qt'){
                    this.productionVisible = true
                }else{
                    this.productionVisible = false
                }
            },
            saveOutbound() {
                if (this.form.pickingUseage=='qt'){
                    this.form.pickingUseage = this.form.otherPickingUseage
                } else if(this.form.outboundDate == "") {
                    this.$message.error("保存失败，请选择领料时间");
                }
                else if(this.form.pickingUseage == "" && this.form.otherPickingUseage){
                    this.$message.error("保存失败，请选择领料用途");
                }
                else if(this.form.takeMaterialId == ""){
                    this.$message.error("保存失败，请填选择领料人");
                }
                else if(this.form.tableDataRow.length == 0){
                    this.$message.error("保存失败，请填选择领料数量");
                }else {
                    if(this.insertFlag == 1){
                        this.$http.post("/materialRepertoryDetail/saveOutbound", this.form).then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.$message.success("保存成功");
                                this.goBack();
                            }
                        });
                    }else{
                        this.$message.error("保存失败，出库数不能大于库存数量");
                    }

                }
            },
            change(row,index){
                this.form.materialId = row.materialId
                var totalOutBoundNum = 0;
                if(row.outBoundNum > row.inventoryQty){
                    this.insertFlag = 0;
                    this.$message.error("出库数不能大于库存数量");
                }else{
                    this.insertFlag = 1;
                    this.outBoundNumList[index] = row.outBoundNum
                    var listLength = this.outBoundNumList.length;
                    for(var i=0;i<listLength;i++){
                        let outBoundStr = this.outBoundNumList[i];
                        if(outBoundStr != "" && outBoundStr != undefined && outBoundStr != null){
                            totalOutBoundNum += parseInt(outBoundStr);
                        }
                    }
                }
                this.form.inventoryQty = totalOutBoundNum
                this.form.tableDataRow.splice(index,1)
                this.form.tableDataRow.push({
                    id: row.id,
                    inventoryQty: row.outBoundNum,
                    leaveQty: row.inventoryQty-row.outBoundNum,
                    materialBatch: row.materialBatch,
                    storageLocation: row.storageLocation,
                    materialId: row.materialId,
                    repertoryId: row.repertoryId
                });
            },
            goBack() {
                this.$router.push({
                    path: "/materialRepertoryList",
                    query: { repertoryId: this.search.repertoryId,repertoryName :this.$route.query.repertoryName, closeFlag: 1 }
                });
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/materialOutboundList') {
                    let newId = this.$route.query.repertoryId;
                    let newMaterialId = this.$route.query.row.materialId
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if(newMaterialId == undefined){
                        newMaterialId = 0;
                    }
                    this.form = {
                            pickingCode:'',
                            pickingUseage:'',
                            otherPickingUseage:'',
                            preparedBy:'',
                            takeMaterialId:'',
                            outboundDate:'',
                            pickingStatus:'完成',
                            materialCode:'',
                            materialName:'',
                            materialId:'',
                            originalMaterial:'',
                            inventoryQty:0,
                            materialUnit:'',
                            repertoryId:'',
                            materialTotalQty:0,
                            tableDataRow:[]
                        }
                    this.productionVisible = false
                    this.repertoryName = this.$route.query.repertoryName;
                    this.form.materialCode = this.$route.query.row.materialCode
                    this.form.materialName = this.$route.query.row.materialName
                    this.form.originalMaterial = this.$route.query.row.originalMaterial
                    this.search.repertoryId = newId;
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