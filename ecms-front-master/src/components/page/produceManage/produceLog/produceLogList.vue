<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/producePlanDetail">生产计划详情</el-menu-item>
                    <el-menu-item index="/materialIssueReturnList">领料退料</el-menu-item>
                    <el-menu-item index="/produceLogList">生产记录</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">制程信息</span>
            </div>
            <el-table :data="processInfo" border style="width:100%" height="250" id="processInfoTable">
                <el-table-column v-for="(process, index) in processInfoTableHead"
                                 :label="process.processFormatName==''?('参数'+(index+1)) :process.processFormatName" :key="process.processFormatName">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row[scope.column.label]" disabled></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.操作 =='1'" type="text" @click="startProduction(scope.$index, scope.row)">开始生产</el-button>
                        <el-button v-if="scope.row.操作 =='2'" type="text" @click="alertForm(scope.$index, scope.row)">完成生产</el-button>
                        <el-button type="text" @click="getProductLog(scope.$index, scope.row)">查看记录</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog title="完成制程" @close="closeing" :visible.sync="completeFormVisible" width="30%">
                <el-form :inline="true" @keyup.enter.native="searchOne" :model="search" class="demo-form-inline">
                    <el-form-item label="完成数量:">
                        <el-input v-model="saveForm.processProductQty"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="saveProductQty">确 定</el-button>
                  <el-button @click="completeFormVisible = false">取 消</el-button>
              </span>
            </el-dialog>
            <!--<div class="handle-box">-->
                <!--<el-button type="primary" @click="startProduction()">开始生产</el-button>-->
                <!--<el-button type="primary" @click="saveProductQty()">完成生产</el-button>-->
            <!--</div>-->
            <div class="handle-box">
                <span class="el-form-item_label">生产记录</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="processName" label="制程名称">
                </el-table-column>
                <el-table-column align="center" prop="operatName" label="操作人">
                </el-table-column>
                <el-table-column align="center" prop="startDate" label="开始时间">
                </el-table-column>
                <el-table-column align="center" prop="startDate" label="完成时间">
                </el-table-column>
                <el-table-column align="center" prop="completeQty" label="完成数">
                </el-table-column>
                <el-table-column align="center" prop="qualifiedNum" label="合格数">
                </el-table-column>
                <el-table-column align="center" prop="productStatus" label="状态" :formatter="statusFormatter">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        name: '',
        data() {
            return {
                loading: false,
                tableData: [],
                url: '/productionLog/getProductionLog',
                editVisible: false,
                pages: 1,
                processSearch:{
                    id:'',
                    productionNumber:'',
                    planId:''
                },
                processInfo:[],
                processInfoTableHead:[],
                form:{
                    checkFlag:'',
                    processId:'',
                    processName:'',
                    materialId:'',
                    productionNumber:'',
                    productionPlanId:''
                },
                saveForm:{
                    processId:'',
                    processName:'',
                    materialId:'',
                    productionNumber:'',
                    productionPlanId:'',
                    processProductQty:0,
                    checkFlag:''
                },
                search:{
                    processId:'',
                    processName:'',
                    materialId:'',
                    productionNumber:'',
                    productionPlanId:''
                },
                completeFormVisible:false,
                row:[],
                processRow:[]
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    return d;
                })
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            handleSelect(key, keyPath) {
                this.$router.push({
                    path: key,
                    query: {
                        productionNumber: this.$route.query.productionNumber,
                        materialId:this.$route.query.materialId,
                        id:this.$route.query.id,
                        materialNum:this.$route.query.materialNum
                    }
                });
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                console.log(this.$route.query.materialId);
                this.form.materialId = this.$route.query.materialId;
                this.form.productionNumber = this.$route.query.productionNumber;
                this.form.productionPlanId = this.$route.query.id;
                this.processSearch.id = this.$route.query.materialId;
                this.processSearch.productionNumber = this.$route.query.productionNumber;
                this.processSearch.planId = this.$route.query.id;
                this.$http.post("/materialProcess/getProcessDetail", this.processSearch ).then(res => {
                    this.processInfo = []
                    this.inforList = []
                    if (res != undefined && res.data.code == 1000) {
                        for (let i=0;i<res.data.data.length;i++){
                            let json = JSON.parse(res.data.data[i].jsonParam)
                            this.processInfo.push(json)
                        }
                    }
                });
                this.$http.post("/processFormat/searchByMaterialId",this.processSearch).then(res => {
                    this.processInfoTableHead = []
                    if (res != undefined && res.data.code == 1000) {
                        let processInfoTableHeadList = res.data.data
                        let aaa = {
                            processFormatName:'投料数'
                        }
                        let bbb = {
                            processFormatName:'生产完成数'
                        }
                        /*let ccc = {
                            processFormatName:'完成检状态'
                        }
                        let eee = {
                            processFormatName:'id'
                        }*/
                        processInfoTableHeadList.push(aaa)
                        processInfoTableHeadList.push(bbb)
                        /*processInfoTableHeadList.push(ccc)
                        processInfoTableHeadList.push(eee)*/
                        this.processInfoTableHead = processInfoTableHeadList;
                    }
                    this.search.materialId = this.$route.query.materialId;
                    this.search.productionNumber = this.$route.query.productionNumber;
                    this.search.productionPlanId = this.$route.query.id;
                    this.$http.post("/productionLogDetail/getLogList",this.search).then(res => {
                        this.tableData = res.data.data
                    });
                });
            },
            startProduction(index,row){
                if(row.投料数 == 0){
                    this.$message.error("投料数不足");
                }else{
                    this.form.checkFlag = row.完成检状态;
                    this.form.processId = row.id;
                    this.form.processName = row.制程名称;
                    this.saveForm.materialId = this.$route.query.materialId;
                    this.saveForm.productionNumber = this.$route.query.productionNumber;
                    this.saveForm.productionPlanId = this.$route.query.id;
                    this.$http.post("/productionLog/startProduct",this.form).then(res => {
                        this.$message.success("开始生产");
                        this.getData();
                    });
                }
            },
            saveProductQty(){
                if(Number(this.saveForm.processProductQty) > Number(this.row.投料数)){
                    this.$message.error("请核对输入");
                }else{
                    this.form.checkFlag = this.row.完成检状态;
                    this.saveForm.processId = this.row.id;
                    this.saveForm.processName = this.row.制程名称;
                    this.saveForm.materialId = this.$route.query.materialId;
                    this.saveForm.productionNumber = this.$route.query.productionNumber;
                    this.saveForm.productionPlanId = this.$route.query.id;
                    this.saveForm.materialNum = this.$route.query.materialNum;
                    this.$http.post("/productionLog/saveProductQty",this.saveForm).then(res => {
                        this.$message.success("生产完成");
                        this.completeFormVisible = false
                        this.getData();
                    });
                }
            },
            getProductLog(index,row){
                this.search.materialId = this.$route.query.materialId;
                this.search.productionNumber = this.$route.query.productionNumber;
                this.search.productionPlanId = this.$route.query.id;
                this.search.processId = row.id
                this.$http.post("/productionLogDetail/getLogList",this.search).then(res => {
                    this.tableData = res.data.data
                });
            },
            statusFormatter(row) {
                if (row.productStatus == 1) {
                    return "正在生产";
                } else {
                    return "完成生产";
                }
            },
            alertForm(index,row){
                this.row = [];
                this.completeFormVisible = true;
                this.row = row;
            },
            closeing () {
                this.saveForm.processProductQty = 0;
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/produceLogList') {
                    this.getData();
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
