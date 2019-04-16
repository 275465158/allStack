<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="订单编号:">
                        <el-input v-model="search.orderCode"></el-input>
                    </el-form-item>
                    <el-form-item label="合同编号:">
                        <el-input v-model="search.contractId"></el-input>
                    </el-form-item>
                    <el-form-item label="客户名称:">
                        <el-input v-model="search.customerName"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="制单日期:">
                        <el-date-picker
                        v-model="search.dateParam"
                        type="daterange"
                        align="right"
                        unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="yyyy-MM-dd"
                        :picker-options="pickerOptions2">
                        </el-date-picker>
                    </el-form-item>-->
                    <!--<el-form-item label="订单进度:">
                    <el-select v-model="search.progress" clearable placeholder="请选择">
                        <el-option
                        v-for="item in progresses"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                        </el-option>
                    </el-select>-->
                <!--</el-form-item>-->
                    <el-button round @click="searchOrder">查询</el-button>
                </el-form>
            </div>
            <el-table v-loading="loading" :data="tableData" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="orderCode" label="订单编号"/>
                <el-table-column align="center" prop="contractId" label="合同编号"/>
                <el-table-column align="center" prop="customerName" label="客户名称"/>
                <el-table-column align="center" prop="contacterName" label="联系人"/>
                <el-table-column align="center" prop="createDate" label="制单日期"/>
                <el-table-column align="center" prop="deliveryDate" label="交货日期"/>
                <el-table-column align="center" prop="owner" label="制单人"/>
                <el-table-column align="center" prop="productNumber" label="产品数量"/>
                <el-table-column align="center" prop="totalMoneyDisplay" label="订单总价"/>
                <!--<el-table-column align="center" prop="progressDisplay" label="订单进度"/>-->
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.type != '100'" type="text" @click="detail(scope.$index, scope.row)">
                            明细
                        </el-button>
                        <el-button type="text" @click="updateInfo(scope.$index, scope.row)">
                            修改
                        </el-button>
                        <!--<el-button v-if="scope.row.progress == 1" type="text" @click="confirm(scope.$index, scope.row)">-->
                            <!--确认-->
                        <!--</el-button>-->
                        <el-button type="text" v-if="scope.row.progress > 1" @click="delivery(scope.$index, scope.row)">
                            发货
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                progresses: [],
                loading: false,
                pages: 1,
                search: {
                    orderCode: '',
                    contractId: '',
                    customerName: '',
                    flag:1,
                    //progress: '',
                    //dateParam: '',
                    pageNum: 1
                },
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                        }
                    }]
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            searchEnterFun (e) {
                let keyCode = window.event? e.keyCode:e.which;
                if(keyCode == 13){
                    this.searchOrder()
                }
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            searchOrder(){
                this.search.pageNum = 1
                this.getData()
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.loading = true
                this.$http.post('seller/order/table', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.pager.list;
                        this.tableData.forEach(item => {
                            item.deliveryDate = this.formatDate(item.deliveryDate)
                        })
                        this.pages = res.data.data.pager.total;
                        this.progresses = res.data.data.progresses;
                    }
                    this.loading = false
                })
                    .catch(err => {
                        this.loading = false
                    })
            },
            add() {
                this.$router.push('/sellerOrderInfo');
            },
            updateInfo(index, row) {
                this.$router.push({path:'/sellerOrderInfo',query:{id:row.id}});
            },
            detail(index, row) {
                this.$router.push({path:'/sellerOrderSearch',query:{id:row.id}});
            },
            delivery(index, row){
                this.$router.push({path:'/sellerOrderDelivery',query:{id:row.id,row:row}});
            },
            confirm(index, row) {
                this.$http.post('seller/order/confirm', {id: row.id}).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        row.progress = 2;
                        row.progressDisplay = '待生产';
                    }
                })
            },
        },
        watch: {
           '$route' (to, from) {
                if (to.path == '/sellerOrder') {
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

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
</style>
