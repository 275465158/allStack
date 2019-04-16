<template xmlns:text-align="http://www.w3.org/1999/xhtml">
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-button round type="primary" @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
                <!--<el-button round @click="barCodePrint" type="primary">条码打印</el-button>-->
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编号">
                </el-table-column>
                <el-table-column align="center" prop="productType" label="物料类型">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="参数">
                </el-table-column>
                <el-table-column align="center" prop="inventoryQty" label="库存数">
                </el-table-column>
                <el-table-column align="center" label="安全库存数">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.inventorySafeQty" type="number" style="width: 90px"
                                  :min="0" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);">
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="orderNeedQty" label="订单需求数">
                </el-table-column>
                <el-table-column align="center" prop="stayDeliveredQty" label="待收货总数">
                </el-table-column>
                <el-table-column align="center" prop="stayInboundQty" label="待入库总数">
                </el-table-column>
                <el-table-column align="center" prop="" label="库存状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.inventoryQty >= scope.row.inventorySafeQty && scope.row.inventoryQty != 0"></span>
                        <span v-else style="color: red">库存不足</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="save(scope.row)">保存</el-button>
                        <el-button size="small" type="primary" @click="detail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="10" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div id="subOutputRank-print" style="display: none">
                <div class="content" v-for="(v,k) in printList">
                    <div style="text-align: center;padding-bottom:10px;float: left;width: 50%;">
                        <span>{{v.materialName}}/{{v.originalMaterial}}/{{v.materialParam}}</span>
                        <br>
                        <barcode :value="v.materialCode" :options="barcode_option" tag="svg" ></barcode>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import jsbarcode from 'jsbarcode';
    export default {
        components: {},
        data() {
            return {
                tableData: [],
                url: "/materialRepertory/getListBylike",
                updateUrl: "/materialRepertory/updateInventorySafeQty",
                pages: 1,
                pageNum:1,
                loading: false,
                search: {
                    materialCode:'',
                    materialName:'',
                    status: 1,
                    pageNum: 1,
                    pageSize:10
                },
                barcode_option:{
                    displayValue: true, //是否默认显示条形码数据
                    background: '#fff', //条形码背景颜色
                    valid: function (valid) {
                    },
                    width:'2px',
                    height: '90px',
                    fontSize: '18px' //字体大小
                },
                barCodelist:[],
                printList: []
            };
        },
        created() {
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
            handleSelectionChange (val) {
                this.printList = val
            },
            //初始化数据
            initData(){
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.pageNum = val;
                this.getData();
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then(res => {
                    this.barCodelist = []
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                        for(var i=0;i<res.data.data.list.length;i++){
                            this.barCodelist.push({barcodes:res.data.data.list[i].materialCode,materialName:res.data.data.list[i].materialName,originalMaterial:res.data.data.list[i].originalMaterial,materialParam:res.data.data.list[i].materialParam})
                        }
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    });
            },
            // barCodePrint(){
            //     // 打印
            //     if (this.printList.length === 0) {
            //         this.$message.warning('请选择要打印的物料');
            //     } else {
            //         let subOutputRankPrint = document.getElementById('subOutputRank-print');
            //         let newContent = subOutputRankPrint.innerHTML;
            //         let wind = window.open("", 'newwindow', 'height=300, width=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
            //         wind.document.body.innerHTML = newContent;
            //         wind.print();
            //         wind.location.reload();
            //         wind.close();
            //         return false;
            //         alert("条码打印")
            //     }
            // },
            detail(row){
                this.$router.push({
                    path: "/materialTableInfo",
                    query: {
                        row:row
                    }
                });
            },
            /**
             * 保存安全库存数
             * @param row
             */
            save(row){
                this.loading = true
                this.$http.post(this.updateUrl, row).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.$message.success("修改安全库存数成功");
                    }
                    this.loading = false
                })
                    .catch(err => {
                        this.loading = false
                    });
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
            }
        },
        watch: {
            // 如果路由发生变化，再次执行该方法
            $route: "getData"
        }
    };
</script>
<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
    .content {
        height: 100px;
        width: 250px;
        float: left;
        text-align: center;
        font-size:12px;
        margin-left:20px;
        margin-right:20px;
        margin-top:40px;
        margin-bottom:40px;
    }
</style>
