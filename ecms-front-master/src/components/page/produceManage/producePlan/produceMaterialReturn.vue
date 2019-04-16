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
                <span class="el-form-item_label">领料记录</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column prop="issuanceNumber" label="发料单号">
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column prop="factoryMaterialCode" label="工厂所需物料编号">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialBatch" label="物料批次">
                </el-table-column>
                <el-table-column prop="source" label="来源">
                </el-table-column>
                <el-table-column prop="repertoryName" label="发料仓库">
                </el-table-column>
                <el-table-column prop="issueQty" label="已领料数量">
                </el-table-column>
                <!--<el-table-column prop="isReturnQty" label="可退料数量">
                </el-table-column>-->
                <el-table-column prop="usedQty" label="已使用数量">
                </el-table-column>
                <el-table-column prop="returndQty" label="已退料数量">
                </el-table-column>
                <el-table-column prop="operatingPersonnelName" label="发料人">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" v-if="scope.row.usedQty<scope.row.issueQty" type="primary" @click="materialReturn(scope.$index, scope.row)">退料</el-button>
                        <!--<el-button size="small" @click="handleEdit(scope.$index, scope.row)">详情</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item_label">退料记录</span>
            </div>
            <el-table :data="returnTables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="productionNumber" label="生产单号">
                </el-table-column>
                <el-table-column prop="issuanceNumber" label="发料单号">
                </el-table-column>
                <el-table-column prop="returnNumber" label="退料单号">
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编码">
                </el-table-column>
                <el-table-column prop="factoryMaterialCode" label="工厂所需物料编号">
                </el-table-column>
                <el-table-column prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="source" label="来源">
                </el-table-column>
                <el-table-column prop="materialBatch" label="物料批次">
                </el-table-column>
                <el-table-column prop="repertoryName" label="退料仓库">
                </el-table-column>
                <el-table-column prop="returnQty" label="退料数量">
                </el-table-column>
                <el-table-column prop="returnDate" label="退料时间">
                </el-table-column>
                <el-table-column prop="operatingPersonnelName" label="退料人">
                </el-table-column>
            </el-table>
            <el-dialog title="退料" :visible.sync="noticeArrivedVisible" width="60%">
                <el-form :inline="true" :model="insertForm" class="demo-form-inline">
                    <el-form-item label="退料数量:">
                        <el-input v-model="insertForm.returnQty" type="number" min="0" placeholder="请输入数量" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="change"></el-input>
                    </el-form-item>
                    <el-button round @click="handleCompleteButton()" type="primary">完成</el-button>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    export default {
        name: '',
        data() {
            return {
                tableData: [],
                returnTables:[],
                url: '/productionIssueDetail/getIssueList',
                returnUrl: '/produceMaterialReturnLog/getReturnLogList',
                insertUrl: '/produceMaterialReturnLog/insertInfo',
                pages: 1,
                search: {
                    productionNumber: '',
                    pageNum:1,
                    pageSize:20
                },
                noticeArrivedVisible:false,
                insertForm:{
                    returnQty:0,
                    productionPlanId:0,
                    issuanceNumber:'',
                    isReturnQty:0,
                    usedQty:0,
                    issueQty:0,
                    returndQty:0
                },
                returnNum:0,

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
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取数据
            getData() {
                this.search.productionNumber = this.$route.query.productionNumber
                this.$http.post(this.url, this.search).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data
                        this.pages = res.data.data.total;
                    }
                })
                this.$http.post(this.returnUrl, this.search).then((res) => {
                    console.log(res);
                    if (res.data.code == 1000) {
                        this.returnTables = res.data.data
                    }
                })
            },
            materialReturn(index,row){
                this.noticeArrivedVisible = true
                this.insertForm = row
            },
            change(){
                let returndQtyStr = this.insertForm.returndQty;
                if(returndQtyStr == null){
                    returndQtyStr = 0
                }
                console.log(this.insertForm.returndQty);
                this.returnNum = parseFloat(this.insertForm.issueQty) - parseFloat(this.insertForm.usedQty) - parseFloat(returndQtyStr);
                if(this.insertForm.returnQty > this.returnNum){
                    this.$message.error("退料数量不能大于可退料数量，最大可退料数量为" + this.returnNum);
                    return false;
                }
            },
            handleCompleteButton(){
                if(this.insertForm.returnQty > this.returnNum){
                    this.$message.error("退料数量不能大于可退料数量，最大可退料数量为" + this.returnNum);
                    return false;
                }else{
                    this.$http.post(this.insertUrl, this.insertForm).then((res) => {
                        if (res.data.code == 1000) {
                            this.noticeArrivedVisible = false;
                            this.$message.success("退料成功");
                            this.getData();
                        }
                    })
                }
            }
        },
        watch: {
            '$route' (to, orderForm) {
                if (to.path == '/materialIssueReturnList') {
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