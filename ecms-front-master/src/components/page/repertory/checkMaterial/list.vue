<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">仓库物料列表</span>
            </div>
            <el-table border style="width:100%" :data="tableData">
                <el-table-column prop="id" label="序号" sortable></el-table-column>
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
                <el-table-column label="盘点数">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.checkNum" type="number" style="width: 100px"
                                  :max="scope.row.inventoryQty" :min="0" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                  v-on:input="change(scope.row,scope.$index)">
                        </el-input>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
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
                url: "/materialCheckDetail/insertCheck",
                pages: 1,
                form:{
                    pickingCode:'',
                    pickingUseage:'',
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
                //属性列表
                searchParams: [],
                //出库表单
                pickingForm: {},
                //库存明细列表
                //出库状态
                // statusOptions: ["待确认", "完成", "取消"],
                statusOptions: ["完成"],
                selectedInventory: [],
                checkNumList: [],
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
                            for(var i=0;i<res.data.data.list.length;i++){
                                if(res.data.data.list[i].inventoryQty == 0){
                                    res.data.data.list.splice(i,1)
                                }
                            }
                            if(res.data.data.list.length == 1 && res.data.data.list[0].inventoryQty == 0){
                                this.tableData = [];
                            }else{
                                this.tableData = res.data.data.list;
                            }
                            if (res.data.data.list.length) {
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
                    if(this.$route.query.row.materialId != undefined){
                        this.$http.post("/productionPlan/getProductionBatchList", {pageNum:1,materialId:this.$route.query.row.materialId}).then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.productionBatch = res.data.data
                            }
                        });
                    }
                }
            },
            saveOutbound() {
                this.$http.post(this.url, this.form).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("保存成功");
                        this.form.tableDataRow = []
                        this.goBack();
                    }
                });
            },
            change(row,index){
                this.form.materialId = row.materialId
                var totalcheckNum = 0;
                this.insertFlag = 1;
                this.checkNumList[index] = row.checkNum
                var listLength = this.checkNumList.length;
                for(var i=0;i<listLength;i++){
                    totalcheckNum += parseInt(this.checkNumList[i]);
                }
                this.form.inventoryQty = totalcheckNum
                this.form.tableDataRow.splice(index,1)
                this.form.tableDataRow.push({
                    id: row.id,
                    inventoryQty: row.checkNum,
                    leaveQty: row.checkNum-row.inventoryQty,
                    materialBatch: row.materialBatch,
                    storageLocation: row.storageLocation,
                    materialId: row.materialId,
                    materialCode: row.materialCode,
                    repertoryId: row.repertoryId,
                    supplierId:row.supplierId
                });
            },
            goBack() {
                this.$router.push({
                    path: "/materialRepertoryList",
                    query: { repertoryId: this.search.repertoryId,repertoryName: this.$route.query.repertoryName, closeFlag: 1 }
                });
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/checkMaterialList') {
                    let newId = this.$route.query.repertoryId;
                    let newMaterialId = this.$route.query.row.materialId
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if(newMaterialId == undefined){
                        newMaterialId = 0;
                    }
                    this.repertoryName = this.$route.query.repertoryName;
                    this.form.materialCode = this.$route.query.row.materialCode
                    this.form.materialName = this.$route.query.row.materialName
                    this.form.originalMaterial = this.$route.query.row.originalMaterial
                    this.search.repertoryId = newId;
                    this.form.materialId = newMaterialId;
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
    hr {
        border-top: 1px;
    }
</style>