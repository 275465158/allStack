<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materielList">物料列表</el-menu-item>
                    <el-menu-item index="/entryList">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="materielDetailList">出入库明细</el-menu-item>
                    <!--<el-menu-item index="/sendMaterialLogList">发货记录</el-menu-item>-->
                    <!--<el-menu-item index="/pickingList">出库管理</el-menu-item>
                    <el-menu-item index="/deliveryList">发货管理</el-menu-item>-->
                    <!--<el-menu-item index="/productionIssue">生产发料</el-menu-item>-->
                    <el-menu-item index="/sendDeliveryList">发货</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form label-width="100px" :model="pickingForm">
                    <el-row :gutter="30">
                        <!--<el-col :span="6">
                            <el-form-item label="出库单号">
                                <el-input v-model="form.pickingCode" disabled></el-input>
                            </el-form-item>
                        </el-col>-->
                        <el-col :span="6">
                            <el-form-item label="领料用途">
                                <el-input v-model="form.useage" ></el-input>
                            </el-form-item>
                        </el-col>
                        <!--<el-col :span="6">
                            <el-form-item label="操作人">
                                <el-input v-model="form.preparedBy"></el-input>
                            </el-form-item>
                        </el-col>-->
                        <el-col :span="8">
                            <el-form-item label="领料人:" prop="">
                                <el-select v-model="form.pickedBy" clearable placeholder="请选择">
                                    <el-option
                                            v-for="item in this.employeeList"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.name">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出库总数">
                                <el-input v-model="form.qty" disabled></el-input>
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
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="领料时间">
                                <el-date-picker value-format="yyyy-MM-dd" v-model="form.pickDate"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出库状态">
                                <el-input v-model="form.pickingStatus" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <!--<el-col :span="6">
                            <el-form-item label="出库状态">
                                <el-select v-model="form.pickingStatus" placeholder="请选择">
                                    <el-option v-for="item in statusOptions"
                                               :key="item"
                                               :label="item"
                                               :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>-->
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item  v-for="(project,index) in searchParams" :key="index" :offset="1" :label="project.parameterName==''?('规格'+(index+1)):project.parameterName">
                                <el-input v-model="project.parameterValue" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
           <!-- <div class="handle-box">
                <el-button round type="primary" @click="add">增加物料</el-button>
            </div>-->
            <el-table border style="width:100%" :data="tableData">
                <el-table-column prop="id" label="序号" sortable></el-table-column>
               <!-- <el-table-column label="物料编号" prop="materielInventory.materiel.materialBom.materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materielInventory.materiel.materialBom.materialName"></el-table-column>
                <el-table-column label="材质" prop="materielInventory.materiel.materialBom.originalMaterial"></el-table-column>
                <el-table-column label="规格列表">
                    <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('规格'+(index+1)):param.parameterName" :key="param.parameterName">
                        <template slot-scope="scope">
                            {{scope.row.materielInventory.materiel.materialBom.materialParameter[param.parameterName]}}
                        </template>
                    </el-table-column>
                </el-table-column>-->
                <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
                <el-table-column label="物料批次" prop="materielBatch"></el-table-column>
                <el-table-column label="物料位置" prop="shelfPosition"></el-table-column>
                <el-table-column label="库存数" prop="qty">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{scope.row.qty}}</span>
                        <div class="el-input" v-if="scope.row.editFlag">
                            <input class="el-input__inner" type="text" v-model="scope.row.qty" @input="handleQtyChange(scope.row, scope.$index)" />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="出库数">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.outBoundNum" type="number" style="width: 100px"
                                  :max="scope.row.qty" :min="0" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                  v-on:input="change(scope.row,scope.$index)">
                        </el-input>
                    </template>
                </el-table-column>
            </el-table>
            <span class="dialog-footer">
                <el-button type="primary" @click="insertPicking">保存</el-button>
                <el-button @click="goBack">返回</el-button>
            </span>
            <!--<el-dialog title="选择物料" :visible.sync="materielListVisible" width="60%">
                <div class="handle-box">
                    <el-form :inline="true" :model="materielSearch" class="demo-form-inline">
                        <el-form-item label="物料编号：">
                            <el-input v-model="materielSearch.materialCode"></el-input>
                        </el-form-item>
                        <el-form-item label="物料名称：">
                            <el-input v-model="materielSearch.materialName"></el-input>
                        </el-form-item>
                        <el-button round @click="getMaterielData">查询</el-button>
                    </el-form>
                </div>
                <el-table :data="materielInventoryTable" border style="width:100%" @selection-change="handleInventorySelection">
                    <el-table-column label="选择" type="selection" prop="id">
                    </el-table-column>
                    <el-table-column label="物料编号" prop="materiel.materialBom.materialCode"></el-table-column>
                    <el-table-column label="物料名称" prop="materiel.materialBom.materialName"></el-table-column>
                    <el-table-column label="材质" prop="materiel.materialBom.originalMaterial"></el-table-column>
                    <el-table-column label="规格列表">
                        <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('规格'+(index+1)):param.parameterName" :key="param.parameterName">
                            <template slot-scope="scope">
                                {{scope.row.materiel.materialBom.materialParameter[param.parameterName]}}
                            </template>
                        </el-table-column>
                    </el-table-column>
                    <el-table-column label="供应商名称" prop="supplier.supplierName"></el-table-column>
                    <el-table-column label="物料批次" prop="materielBatch"></el-table-column>
                    <el-table-column label="数量" prop="qty"></el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="materielListVisible = false">取 消</el-button>
                  <el-button type="primary" @click="appendDetail">确 定</el-button>
                </span>
            </el-dialog>-->
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
                    useage:'',
                    preparedBy:'',
                    pickedBy:'',
                    pickDate:'',
                    pickingStatus:'完成',
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    qty:0,
                    materielId:'',
                    materialUnit:'',
                    tableDataRow:[]
                },
                search: {
                    materielId: '',
                    pickingId: '',
                    /*
                    repertoryId: 0,*/
                    pageNum: 1
                },
                materielSearch: {
                    pageNum: 1
                },
                //属性列表
                searchParams: [],
                //出库表单
                pickingForm: {},
                //库存明细列表
                materielListVisible: false,
                materielPages: 1,
                materielInventoryTable: [],
                //出库状态
                // statusOptions: ["待确认", "完成", "取消"],
                statusOptions: ["完成"],
                selectedInventory: [],
                outBoundNumList: [],
                insertFlag: 1,//插入标识
                employeeList:[],
                tableRowData:[]
            };
        },
        created() {
            /*this.form.pickingCode = this.$route.query.row.materialBom.
            this.form.useage = this.$route.query.row
            this.form.preparedBy = this.$route.query.row
            this.form.pickedBy = this.$route.query.row
            this.form.pickDate = this.$route.query.row
            this.form.pickingStatus = this.$route.query.row
            */
            this.form.materielTableId = this.$route.query.row.id
            this.search.materielId = this.$route.query.row.materialBom.id
            this.form.materielId = this.$route.query.row.materialBom.id
            this.form.materialUnit = this.$route.query.row.materialBom.materialUnit
            this.form.materialCode = this.$route.query.row.materialBom.materialCode
            this.form.materialName = this.$route.query.row.materialBom.materialName
            this.form.originalMaterial = this.$route.query.row.materialBom.originalMaterial
            this.searchParams = this.$route.query.row.materialBom.materialParameters
            this.getData();
        },
        computed: {},
        methods: {
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
            getData() {
                if (this.$route.query.repertoryId != null) {
                    this.search.repertoryId = this.$route.query.repertoryId;
                    this.$http.post("materiel/inventory/getListNoPageSize", this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            var newList = [];
                            for(var i=0;i<res.data.data.length;i++){
                                if(res.data.data[i].qty == 0){
                                    res.data.data.splice(i,1)
                                }
                            }
                            if(res.data.data.length == 1 && res.data.data[0].qty == 0){
                                this.tableData = [];
                            }else{
                                this.tableData = res.data.data;
                            }
                            this.pages = res.data.data.pages;
                        }
                    });
                    /*this.$http.post(this.url, this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.pickingForm = res.data.data;
                        }
                    });*/
                    this.$http.post('/employee/listEmployeeByInfo', this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.employeeList = res.data.data.list;
                        }
                    });
                }
                // this.$route.query.pickingId = 1;
                // if (this.$route.query.pickingId != undefined) {
                    /*this.$http.post("/material/paramNames").then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.searchParams = res.data.data;
                        }
                    });*/

                    /*this.$http.post("/materiel/picking/detail/listByPickingId", this.search).then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.tableData = res.data.data.list;
                                this.pages = res.data.data.pages;
                            }
                        });*/
                // }
            },
            getMaterielData() {
                if (this.$route.query.repertoryId != undefined) {
                    this.materielSearch.repertoryId = this.$route.query.repertoryId;
                }
                this.materielSearch.materiel = JSON.stringify(
                    this.materielSearch.materiel
                );
                this.$http.post("/materiel/inventory/seachByNameAndCode", this.materielSearch).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materielInventoryTable = res.data.data.list;
                        this.materielPages = res.data.data.total;
                    }
                });
            },
            add() {
                this.materielListVisible = true;
            },
            appendDetail() {
                let existData = this.tableData;
                this.selectedInventory = this.selectedInventory.filter(function(item, index, arr) {
                    var rtn = true;
                    existData.forEach(row => {
                        if (row.materielInventory.id == item.materielInventory.id) {
                            rtn = false;
                        }
                    });
                    return rtn;
                });
                this.selectedInventory.forEach(selectedRow => {
                    this.tableData.push(selectedRow);
                });
                this.materielInventoryTable = [];
                this.materielListVisible = false;
            },
            edit(row) {},
            insertPicking() {
                if(this.insertFlag == 1){
                    this.$http.post("/materiel/picking/insertByInfo", this.form).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$http.post("/materiel/inventory/saveOrUpdateObject", this.form).then(res => {
                                if (res != undefined && res.data.code == 1000) {
                                    this.$message.success("保存成功");
                                    this.goBack();
                                }
                            });
                        }
                    });
                } else {
                    this.$message.error("保存失败，出库数不能大于库存数量");
                }
            },
            goBack() {
                this.$router.push({
                    path: "/pickingList",
                    query: { repertoryId: this.search.repertoryId }
                });
            },
            handleInventorySelection(vals) {
                this.selectedInventory = [];
                vals.forEach(val => {
                    this.selectedInventory.push({
                        editFlag: false,
                        qty: val.qty,
                        materielInventory: val
                    });
                });
            },
            editQty: function(row, index) {
                this.$set(this.tableData[index], "editFlag", true);
            },
            confirmQty: function(row, index) {
                this.$set(this.tableData[index], "editFlag", false);
            },
            deleteDetail(row) {
                var index = this.tableData.indexOf(row);
                if (index !== -1) {
                    this.tableData.splice(index, 1);
                }
            },
            handleQtyChange(row, index) {
                var qty = parseFloat(row.qty);
                if (qty > row.materielInventory.qty) {
                    this.$set(
                        this.tableData[index],
                        "qty",
                        this.tableData[index].materielInventory.qty
                    );
                }
            },
            change(row,index){
                var totalOutBoundNum = 0;
                if(row.outBoundNum > row.qty){
                    this.insertFlag = 0;
                    this.$message.success("出库数不能大于库存数量");
                }else{
                    this.insertFlag = 1;
                    this.outBoundNumList[index] = row.outBoundNum
                    var listLength = this.outBoundNumList.length;
                    for(var i=0;i<listLength;i++){
                        totalOutBoundNum += parseInt(this.outBoundNumList[i]);
                    }
                }
                this.form.qty = totalOutBoundNum
                this.form.tableDataRow.push({
                    id: row.id,
                    qty: row.qty-row.outBoundNum
                });
            }
        },
        watch: {
            $route: "getData"
        }
    };
</script>
<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
    .img-style{
        margin-left: 50px;
        width: 150px;
        height: 80px;
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