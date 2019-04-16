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
                        <el-col :span="6">
                            <el-form-item label="出库单号">
                                <el-input v-model="form.pickingCode" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="领料用途">
                                <el-input v-model="form.useage" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="操作人">
                                <el-input v-model="form.preparedBy" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="领料人">
                                <el-input v-model="form.pickedBy" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="领料时间">
                                <el-date-picker value-format="yyyy-MM-dd" v-model="form.pickDate" disabled></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出库总数">
                                <el-input v-model="form.totalNum" disabled></el-input>
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
                            <el-form-item label="出库状态">
                                <el-input v-model="form.pickingStatus" disabled></el-input>
                            </el-form-item>
                        </el-col>
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
            <el-table border style="width:100%" :data="tableData">
                <el-table-column label="序号" prop="materielInventory.id"></el-table-column>
                <el-table-column label="供应商名称" prop="materielInventory.supplier.supplierName"></el-table-column>
                <el-table-column label="物料批次" prop="materielInventory.materielBatch"></el-table-column>
                <el-table-column label="物料位置" prop="materielInventory.shelfPosition"></el-table-column>
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
                                  :max="scope.row.qty" :min="0" onkeypress='return(/[\d]/.test(String.fromCharCode(event.keyCode)))'
                                  v-on:input="change(scope.row,scope.$index)">
                        </el-input>
                    </template>
                </el-table-column>
            </el-table>
            <span class="dialog-footer">
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
                    useage:'',
                    preparedBy:'',
                    pickedBy:'',
                    pickDate:'',
                    pickingStatus:'',
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    totalNum:0,
                    materielId:'',
                    materialUnit:''
                },
                search: {
                    materielId: 2,
                    pickingId: 1,
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
                insertFlag: 1 //插入标识
            };
        },
        created() {
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
                    this.$http.post("/materiel/picking/detail/listByPickingId", this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData = res.data.data.list;
                            this.pages = res.data.data.pages;
                        }
                    });
                    this.$http.post(this.url, this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.pickingForm = res.data.data;
                        }
                    });
                }
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
            goBack() {
                this.$router.push({
                    path: "/pickingList",
                    query: { repertoryId: this.search.repertoryId }
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