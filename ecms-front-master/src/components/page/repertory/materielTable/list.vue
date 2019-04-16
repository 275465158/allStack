<template xmlns:text-align="http://www.w3.org/1999/xhtml">
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchList" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="工厂物料编码:">
                        <el-input v-model="search.factoryMaterialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型:">
                        <el-input v-model="search.number"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="材质:">
                        <el-input v-model="search.originalMaterial"></el-input>
                    </el-form-item>
                    <el-form-item label="规格:">
                        <el-input v-model="search.materialBomParamValueStr"></el-input>
                    </el-form-item>
                    <el-form-item label="图号:">
                        <el-input v-model="search.drawingCode"></el-input>
                    </el-form-item>
                    <el-form-item label="来源:">
                        <el-select style="width: 200px;" v-model="search.materialSource">
                            <el-option label="全部" value=""></el-option>
                            <el-option label="自制" value="自制"></el-option>
                            <el-option label="外购" value="外购"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round type="primary" @click="searchList">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
                <el-button round @click="barCodePrint" type="primary">条码打印</el-button>
                <!--<el-button round @click="barCodePrintAll" type="primary">打印全部</el-button>-->

                <div class="radio-box">
                    <el-radio v-model="radio" label="1">A4纸打印版</el-radio>
                    <el-radio v-model="radio" label="2">不干胶打印版</el-radio>
                </div>
            </div>
            <el-table v-loading="loading" :data="tables"  @selection-change="handleSelectionChange" border style="width:100%">
                <el-table-column
                        align="center"
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column prop="id" align="center" label="序号" sortable>
                    <template slot-scope="scope">
                        {{10*(indexPageNum-1)+scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号">
                </el-table-column>
                <el-table-column align="center" prop="factoryMaterialCode" label="工厂物料编码">
                </el-table-column>
                <el-table-column align="center" prop="number" label="物料类型">
                </el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称">
                </el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="材质">
                </el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="规格">
                </el-table-column>
                <el-table-column align="center" prop="materialSource" label="来源">
                </el-table-column>
                <!--<el-table-column align="center" prop="drawingCode" label="图号">-->
                <!--</el-table-column>-->
                <el-table-column align="center" prop="inventoryQty" label="库存数">
                </el-table-column>
                <el-table-column align="center" prop="inventorySafeQty" label="安全库存数">
                </el-table-column>
                <el-table-column align="center" prop="unqualifiedQty" label="不合格品数">
                </el-table-column>
                <el-table-column align="center" prop="issueQty" label="投料数量">
                </el-table-column>
                <!--<el-table-column align="center" prop="orderNeedQty" label="订单需求数">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="stayDeliveredQty" label="待收货总数">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="stayCheckQty" label="待检测总数">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="stayProductionQty" label="待生产总数">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="stayInboundQty" label="待入库总数">-->
                <!--</el-table-column>-->
                <!--<el-table-column align="center" prop="stayCheckQty" label="待检测数量">
                </el-table-column>
                <el-table-column align="center" prop="stayProductionQty" label="待生产数量">
                </el-table-column>-->
                <el-table-column align="center" prop="inventoryStatus" label="库存状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.inventoryQty >= scope.row.inventorySafeQty && scope.row.inventoryQty != 0"></span>
                        <span v-else style="color: red">库存不足</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="detail(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="10" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div id="subOutputRank-print" style="display: none">
                <div v-for="(v,k) in printList" style="text-align: center;display:inline-block;clear: both;width: 49%;height:150px;border:1px solid black;font-size:12px;">
                    <ul style="list-style: none;text-align: left;float: left;">
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">物料编号：</span>{{v.materialCode}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">工厂内部编号：</span>{{v.factoryMaterialCode}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">物料类型：</span>{{v.number}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">物料名称：</span>{{v.materialName}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">材料：</span>{{v.originalMaterial}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">规格：</span>{{v.materialBomParamValueStr}}</li>
                        <li style="width: 180px;word-wrap:break-word;word-break:break-all;"><span style="width: 85px;display: inline-block">图号：</span>{{v.drawingCode}}</li>
                    </ul>
                    <div id="qrcode2" style="float: right;margin-top: 20px;margin-right: 20px">
                        <vue-qr :text="v.materialCode" :size="90" :margin="0"></vue-qr>
                    </div>
                </div>
            </div>
            <div id="subOutputRank-print1" style="display: none;">
                <div v-for="(v,k) in printList" style="width:100%;text-align: center;display:inline-block;clear: both;border:1px solid black;font-size:12px;">
                    <ul style="list-style: none;text-align: left;float: left;padding:0px; padding-left: 8px;">
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料编号：</span>{{v.materialCode}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">工厂内部编号：</span>{{v.factoryMaterialCode}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料类型：</span>{{v.number}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">物料名称：</span>{{v.materialName}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">材料：</span>{{v.originalMaterial}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">规格：</span>{{v.materialBomParamValueStr}}</li>
                        <li style="width:250px;margin-top:8px;word-wrap:break-word;word-break:break-all;"><span style="display: inline-block">图号：</span>{{v.drawingCode}}</li>
                    </ul>
                    <div id="qrcode3" style="float: right;margin-top: 60px;margin-right: 20px">
                        <vue-qr :text="v.materialCode" :size="90" :margin="0"></vue-qr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import VueQr from 'vue-qr'; //导入二维码插件
    import jsbarcode from 'jsbarcode';
    export default {
        components: { VueQr },
        data() {
            return {
                radio: '1',
                tableData: [],
                url: "/materialRepertory/getListByLikeAll",
                pages: 1,
                pageNum:1,
                printList: [],
                search: {
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    materialBomParamValueStr:'',
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
                loading: false,
                indexPageNum:1
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
                console.log(this.printList);
            },
            //初始化数据
            initData(){
            },
            handleChange(value) {
            },
            // 分页导航
            handleCurrentChange(val) {
                this.indexPageNum = val;
                this.search.pageNum = val;
                this.pageNum = val;
                this.getData();
            },
            searchList(){
                this.search.pageNum = 1
                this.getData()
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
                            this.barCodelist.push({barcodes:res.data.data.list[i].materialCode,materialName:res.data.data.list[i].materialName,originalMaterial:res.data.data.list[i].originalMaterial,materialBomParamValueStr:res.data.data.list[i].materialBomParamValueStr})
                        }
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    });
            },
            barCodePrint(){
                // 打印
                if (this.printList.length === 0) {
                    this.$message.warning('请选择要打印的物料');
                } else {
                    let str, zoom;
                    if (this.radio == '1') {
                        str = 'subOutputRank-print'
                        zoom = 1
                    } else if (this.radio == '2') {
                        str = 'subOutputRank-print1'
                        zoom = 0.6
                    }

                    let subOutputRankPrint = document.getElementById(str);
                    subOutputRankPrint.style.zoom = zoom
                    let newContent =subOutputRankPrint.innerHTML;
                    let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                    wind.document.body.innerHTML = newContent;
                    wind.print();
                    wind.location.reload();
                    wind.close();
                    return false;
                    alert("条码打印")
                }
            },
            detail(row){
                if(row.inventoryQty >= row.inventorySafeQty && row.inventoryQty != 0){
                    row.inventoryStatus = "库存充足"
                }else{
                    row.inventoryStatus = "库存不足"
                }
                this.$router.push({
                    path: "/materialTableInfo",
                    query: {
                        row:row
                    }
                });
            },
            clearData(){
                this.search.materialCode = ''
                this.search.materialName = ''
                this.search.originalMaterial = ''
                this.search.materialBomParamValueStr = ''
                this.search.materialSource = ''
                this.search.drawingCode = ''
                this.search.number = ''
                this.search.factoryMaterialCode = ''
            }
        },
        watch: {
            $route(to, from) {
          if (to.path == "/materielTableList") {
              this.getData();
          }
      }
        }
    };
</script>
<style scoped>
    .handle-box {
        margin-top: 10px;
        /*margin-bottom: 20px;*/
        position: relative;
    }
    .radio-box{
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
    }
</style>
