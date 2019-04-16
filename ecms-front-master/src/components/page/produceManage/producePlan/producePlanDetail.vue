<template>
    <div class="table" id="printTable">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/producePlanDetail">生产计划详情</el-menu-item>
                    <el-menu-item index="/materialIssueReturnList">领料退料</el-menu-item>
                    <el-menu-item index="/produceLogList">生产记录</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-button round @click="barCodePrint" type="primary">打印</el-button>
            </div>
            <div class="handle-box">
                <el-form :inline="true" :model="orderForm" class="demo-form-inline">
                    <el-row :gutter="30">
                        <el-form-item label="生产单号:">
                            <el-input disabled v-model="orderForm.productionNumber"/>
                        </el-form-item>
                        <el-form-item label="订单号:">
                            <el-input disabled v-model="orderForm.orderCode"/>
                        </el-form-item>
                        <el-form-item label="合同编号:">
                            <el-input disabled v-model="orderForm.contractId"/>
                        </el-form-item>
                        <el-form-item label="生产类型:">
                            <el-input disabled v-model="orderForm.productionTypeName"/>
                        </el-form-item>
                    </el-row>
                    <el-row :gutter="30">
                        <el-form-item label="生产项次:">
                            <el-input disabled v-model="orderForm.productBatch"/>
                        </el-form-item>
                        <el-form-item label="物料编号:">
                            <el-input disabled v-model="orderForm.materialCode"/>
                        </el-form-item>
                        <el-form-item label="物料名称:">
                            <el-input disabled v-model="orderForm.materialName"/>
                        </el-form-item>
                        <el-form-item label="原图材料:">
                            <el-input disabled v-model="orderForm.originalMaterial"/>
                        </el-form-item>
                    </el-row>
                    <el-row :gutter="30">
                        <el-form-item label="参数:">
                            <el-input disabled v-model="orderForm.materialParameter"/>
                        </el-form-item>
                        <el-form-item label="生产数量:">
                            <el-input disabled v-model="orderForm.materialNum"/>
                        </el-form-item>
                        <el-form-item label="已生产数:">
                            <el-input disabled v-model="orderForm.completedQty"/>
                        </el-form-item>
                        <el-form-item label="待生产数:">
                            <el-input disabled v-model="orderForm.stayProductionNum"/>
                        </el-form-item>
                    </el-row>
                    <el-row :gutter="30">
                        <el-form-item label="制单人:">
                            <el-input disabled v-model="orderForm.preparedByName"/>
                        </el-form-item>
                        <el-form-item label="制单时间:">
                            <el-input disabled v-model="orderForm.preparedTime"/>
                        </el-form-item>
                    </el-row>
                    <!--<el-form-item label="单位:">
                        <el-input disabled v-model="orderForm.materialUnit"/>
                    </el-form-item>
                    <el-form-item label="计划生产日期:">
                        <el-input disabled v-model="orderForm.planProductDate"/>
                    </el-form-item>
                    <el-form-item label="最晚生产日期:">
                        <el-input disabled v-model="orderForm.latestProductDate"/>
                    </el-form-item>-->
                </el-form>
            </div>

            <div class="handle-box">
                <span class="el-form-item__label">图纸</span>
            </div>
            <el-table :data="drawingTables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="productDrawingInfo.id" label="图纸编号">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.name" label="图纸名称">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.drawingCode" label="图号">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.productBatch" label="版本">
                </el-table-column>
                <el-table-column prop="productDrawingInfo.pdfFile" label="PDF图纸">
                    <template slot-scope="scope">
                        <span>{{ scope.row.productDrawingInfo.pdfDisplayName }}</span>
                            <el-button type="text" v-if="scope.row.productDrawingInfo.pdfFile != ''" @click="downloadFile(scope.row.productDrawingInfo.pdfFile, 'pdfDisplay')">下载</el-button>
                            <el-progress v-if="obj.pdfDisplay.show" :percentage="obj.pdfDisplay.load" :status="obj.pdfDisplay.progress"></el-progress>
                    </template>
                </el-table-column>
                <el-table-column label="CAD图纸">
                    <template slot-scope="scope">
                            <span>{{ scope.row.productDrawingInfo.cadDisplayName }}</span>
                            <el-button type="text" v-if="scope.row.productDrawingInfo.cadFile != ''" @click="downloadFile(scope.row.productDrawingInfo.cadFile, 'cadDisplay')">下载</el-button>
                            <el-progress v-if="obj.cadDisplay.show" :percentage="obj.cadDisplay.load" :status="obj.cadDisplay.progress"></el-progress>
                    </template>
                </el-table-column>
                <el-table-column prop="productDrawingInfo.technologyFile" label="工艺图纸">
                    <template slot-scope="scope">
                        <span>{{ scope.row.productDrawingInfo.technologyDisplayName }}</span>
                            <el-button type="text" v-if="scope.row.productDrawingInfo.technologyFile != ''" @click="downloadFile(scope.row.productDrawingInfo.technologyFile, 'technologyDisplay')">下载</el-button>
                            <el-progress v-if="obj.technologyDisplay.show" :percentage="obj.technologyDisplay.load" :status="obj.technologyDisplay.progress"></el-progress>
                    </template>
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">生产工序</span>
            </div>
            <el-table :data="processInfo" border style="width:100%" id="processInfoTable">
                <el-table-column v-for="(process, index) in processInfoTableHead"
                                 :label="process.processFormatName==''?('参数'+(index+1)) :process.processFormatName" :key="process.processFormatName">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row[scope.column.label]" disabled></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">物料清单</span>
            </div>
            <el-table :data="materialListTable" border style="width:100%" height="200">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="物料名称" prop="materialBomInfo.materialName"></el-table-column>
                <el-table-column label="物料编码" prop="materialBomInfo.materialCode"></el-table-column>
                <el-table-column label="原图材料" prop="materialBomInfo.originalMaterial"></el-table-column>
                <el-table-column label="来源" prop="materialBomInfo.source"></el-table-column>
                <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                <el-table-column prop="quantity" label="配套比例">
                    <template slot-scope="scope">
                        1:{{scope.row.quantity}}
                    </template>
                </el-table-column>
                <el-table-column label="应领数量" prop="receiveNum"></el-table-column>
                <el-table-column label="已领数量" prop="issueQty"></el-table-column>
                <!--<el-table-column label="可生产数量" prop="produceQty"></el-table-column>-->
                <el-table-column label="已使用数量" prop="usedQty"></el-table-column>
                <el-table-column label="已退料数量" prop="returnedQty"></el-table-column>
                <el-table-column label="待发料数量" prop="stayIssueQty"></el-table-column>
            </el-table>
        </div>
        <div id="subOutputRank-print" style="display: none">
            <h3 align="center">生产流程卡</h3>
            <div style="overflow:hidden;">
                <div class="imgbox" style="float: left;">
                    <img class="logo" style="width: 90px;height: 90px;vertical-align: top;border: none;" v-if="imgUrl != 'data:image/png;base64,'" :src="imgUrl" />
                    <!--<img src="" alt="">-->
                </div>
                <div id="qrcode2" style="float: right;">
                    <vue-qr style="display: none" ref="qrvalue" :callback="test" :text="orderForm.productionNumber" :size="90" :margin="0"></vue-qr>
                    <img ref="image" alt="">
                    <p id="productionNumber" style="font-size: 13px">{{orderForm.productionNumber}}</p>
                </div>
            </div>
            <table border="1" cellpadding="0" cellspacing="0" width="100%" style=";text-align: center;margin-top: 10px">
                <tr>
                    <td colspan="16" height="35">生产信息</td>
                </tr>
                <tr>
                    <td width="10%" height="35">订单号：</td>
                    <td width="15%">{{orderForm.productBatch}}</td>
                    <td width="10%">合同号：</td>
                    <td width="15%">{{orderForm.contractId}}</td>
                    <td width="10%">生产数量：</td>
                    <td width="15%">{{orderForm.materialNum}}</td>
                    <td width="10%">生产批次：</td>
                    <td width="15%"></td>
                </tr>
                <tr>
                    <td height="35">物料编号：</td>
                    <td>{{orderForm.materialCode}}</td>
                    <td>工厂内部编号：</td>
                    <td>{{orderForm.factoryMaterialCode}}</td>
                    <td>物料类型：</td>
                    <td>{{orderForm.number}}</td>
                    <td>物料名称：</td>
                    <td>{{orderForm.materialName}}</td>
                </tr>
                <tr>
                    <td height="35">材料：</td>
                    <td>{{orderForm.originalMaterial}}</td>
                    <td>参数：</td>
                    <td>{{orderForm.materialParameter}}</td>
                    <td>图号：</td>
                    <td>{{orderForm.drawingCode}}</td>
                    <td>商标：</td>
                    <td>{{orderForm.tradeMark}}</td>
                </tr>
            </table>
            <table border="1" cellpadding="0" cellspacing="0" width="100%" style="border-top: none;text-align: center">
                <tr>
                    <td colspan="15" height="35">制程信息</td>
                </tr>
                <tr>
                    <td height="35" v-for="(item, index) in columnList">{{item}}</td>
                </tr>
                <tr v-if="!inforList.length">
                    <td colspan="15" height="80">无制程</td>
                </tr>
                <tr v-for="(item, index) in inforList">
                    <!--<td height="70"><vue-qr :text="item['流水号']" :size="50" :margin="0"></vue-qr></td>-->
                    <td>{{item['流水号']}}</td>
                    <td style="padding: 40px 5px;"><vue-qr :text="item['二维码']" :size="80" :margin="0"></vue-qr></td>
                    <td>{{item['制程']}}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            <table border="1" cellpadding="0" cellspacing="0" width="100%" style="border-top: none;text-align: center">
                <tr>
                    <td colspan="9" height="35">物料清单</td>
                </tr>
                <tr>
                    <td height="35">序号</td>
                    <td>物料编号</td>
                    <td>工厂物料编号</td>
                    <td>物料类型</td>
                    <td>物料名称</td>
                    <td>材料</td>
                    <td>参数</td>
                    <td>应领数量</td>
                    <td>实际领料数量</td>
                </tr>
                <tr v-if="!materialListTable.length">
                    <td colspan="9" height="80">无数据</td>
                </tr>
                <tr v-for="(item, index) in materialListTable">
                    <td height="35">{{index+1}}</td>
                    <td>{{item.materialBomInfo.materialCode}}</td>
                    <td>{{item.materialBomInfo.factoryMaterialCode}}</td>
                    <td>{{item.materialBomInfo.type}}</td>
                    <td>{{item.materialBomInfo.materialName}}</td>
                    <td>{{item.materialBomInfo.originalMaterial}}</td>
                    <td>{{item.materialBomInfo.materialBomParamValueStr}}</td>
                    <td>{{item.receiveNum}}</td>
                    <td>{{item.issueQty}}</td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
    import VueQr from 'vue-qr'; //导入二维码插件
    import * as utils from '../../../../../static/js/utils.js';
    import fileDownload from 'js-file-download';
    export default {
        name: "",
        components: {
            VueQr
        },
        data() {
            return {
                // 可以自定义，必填项。
                value: 'http://lxchuan12.github.io/',
                // 二维码大小 默认 100
                sizes: 80,
                // 背景透明度，默认透明 0
                backgroundAlpha: 1,
                imgUrl: '',
                productBatch: '',
                dynamicMaterialProcessing: [],
                dynamicMaterialParts: [],
                tableData: [],
                tableData1: [],
                processInfo:[],
                processInfoTableHead:[],
                url: "/productionPlan/productionProcess",
                relationUrl:"materialRelation/materialList",
                editVisible: false,
                pages: 1,
                processSearch:{
                    id:'',
                    pageNum:1,
                    pageSize:20
                },
                search:{
                    id:''
                },
                orderForm: {
                    id:"",
                    materialId: "",
                    materialCode:'',
                    materialName: "",
                    originalMaterial: "",
                    materialUnit: "",
                    productNum: "",
                    materialParam: "",
                    planProductDate: "",
                    latestProductDate: "",
                    productionNumber:"",
                    materialNum:0
                },
                config: {
                  value: '',
                    imagePath: require('../../../../../static/img/arrow.png'),
                    filter: 'color'
                },
                downloadButton: false,
                materialName:'',
                materialCode:'',
                barCodelist:[],
                columnList: ['制程编号','二维码' , '制程名称', '操作者', '开始时间', '完成时间', '完成数', '首检数', '合格数', '返工数', '返修数', '检验员', '单价', '辅助工时', '负责人', '备注'],
                inforList: [],
                productColumnList: ['项次', '物料编码/名称', '计划数量', '实发数', '批次号', '货位'],
                productInforList: [],
                orderCode:'',
                contractId:'',
                drawingTables:[],
                drawingSearch:{
                    materialId:'',
                    pageNum:1
                },
                materialListTable:[],
                materialListSearch:{
                    parentProductionNumber:'',
                    materialId:''
                },
                logSearch:{
                    productionNumber:''
                },
                issueLogSearch:{
                    id:0,
                    productionNumber:'',
                    materialNum:0,
                    pageNum:1,
                    pageSize:20
                },
                obj: {
                    cadDisplay: {show: false, load: 0, progress: ''},
                    pdfDisplay: {show: false, load: 0, progress: ''},
                    technologyDisplay: {show: false, load: 0, progress: ''}
                }
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
            test (dataUrl,id) {
                this.$refs.image.src = dataUrl
            },
            // 分页导航
            handleCurrentChange(val) {
                this.getData();
            },
            handleSelect(key, keyPath) {
                this.$router.push({
                    path: key,
                    query: {
                        productionNumber: this.orderForm.productionNumber,
                        materialId:this.orderForm.materialId,
                        id:this.orderForm.id,
                        materialNum:this.orderForm.materialNum
                    }
                });
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                if(this.$route.query.id != null){
                    this.orderForm.id = this.$route.query.id
                    this.$http.post("/productionPlan/getEntityById",this.orderForm).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.$nextTick(() => {
                                this.orderForm = res.data.data;
                            })
                            this.search.id = res.data.data.id
                            this.barCodelist.push({productionNumber:res.data.data.productionNumber})
                            this.materialName = res.data.data.productName;
                            this.materialCode = res.data.data.materialCode;
                            this.orderCode = res.data.data.orderCode;
                            this.contractId = res.data.data.contractId;
                            this.processSearch.id = res.data.data.materialId;
                            this.productInforList = []
                            this.productInforList.push({'项次': '1', '物料编码/名称': res.data.data.materialCode+"/"+res.data.data.productName, '计划数量': res.data.data.materialNum, '实发数': '', '批次号': '', '货位': ''})
                            this.$http.post("/materialProcess/detail", this.processSearch ).then(res1 => {
                                this.processInfo = []
                                this.inforList = []
                                if (res != undefined && res.data.code == 1000) {
                                    for (let i=0; i < res1.data.data.length; i++){
                                        let qrCode = {
                                            productionPlanId: res.data.data.id,
                                            productionNumber: res.data.data.productionNumber,
                                            orderCode: res.data.data.orderCode,
                                            materialId: res.data.data.materialId,
                                            materialNum: res.data.data.materialNum,
                                            producingProcessId: res1.data.data[i].id
                                        }
                                        let json = JSON.parse(res1.data.data[i].jsonParam)
                                        this.processInfo.push(json)
                                        qrCode.producingProcessId = res1.data.data[i].id
                                        qrCode = '*+ZC*+' + JSON.stringify(qrCode)
                                        this.inforList.push({'流水号': String(res1.data.data[i].id),'二维码': qrCode, '制程': json['制程名称']})
                                    }
                                }
                            });
                            this.$http.post("/processFormat/searchByMaterialId",this.processSearch).then(res => {
                                this.processInfoTableHead = []
                                if (res != undefined && res.data.code == 1000) {
                                    this.processInfoTableHead = res.data.data;
                                }
                            });
                            this.drawingSearch.materialId = res.data.data.materialId
                            this.$http.post("/materialDrawing/materialDrawingList",this.drawingSearch).then(res => {
                                this.drawingTables = []
                                if (res != undefined && res.data.code == 1000) {
                                    let drawingData = res.data.data;
                                    for(let i = 0;i<drawingData.length;i++){
                                        let obj = drawingData[i].productDrawingInfo
                                        let key1 = "cadDisplayName";
                                        let value1 = utils.getFileName(drawingData[i].productDrawingInfo.cadFile);

                                        let key2 = "pdfDisplayName";
                                        let value2 = utils.getFileName(drawingData[i].productDrawingInfo.pdfFile);

                                        let key3 = "technologyDisplayName";
                                        let value3 = utils.getFileName(drawingData[i].productDrawingInfo.technologyFile);

                                        obj[key1] = value1;
                                        obj[key2] = value2;
                                        obj[key3] = value3;
                                    }
                                    this.drawingTables = drawingData;
                                }
                            });
                            this.issueLogSearch.id = res.data.data.materialId
                            this.issueLogSearch.productionNumber = res.data.data.productionNumber
                            this.issueLogSearch.materialNum = res.data.data.materialNum
                            this.$http.post(this.relationUrl, this.issueLogSearch).then((res) => {
                                this.materialListTable = []
                                if (res.data.code == 1000) {
                                    this.materialListTable = res.data.data
                                }
                            })
                            /*this.materialListSearch.parentProductionNumber = res.data.data.productionNumber
                            this.materialListSearch.materialId = res.data.data.materialId
                            this.$http.post("/productionPlan/getProductionByProductionNumber",
                                {parentProductionNumber:this.materialListSearch.parentProductionNumber,
                                    materialId:this.materialListSearch.materialId}).then(res => {
                                this.materialListTable = []
                                if (res != undefined && res.data.code == 1000) {
                                    this.materialListTable = res.data.data;
                                }
                            });*/
                        }
                    });
                    this.$http.get(this.$path + '/company/get').then((res) => {
                        if (res.data.code == 1000) {
                            let company = res.data.data;
                            if (company.logo != '' && company.logo != null) {
                                this.$http.post('/company/download', {filePath: company.logo}, {responseType: 'arraybuffer'}).then((res) => {
                                    this.imgUrl = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((res, byte) => res + String.fromCharCode(byte), ''));
                                })
                            }
                        }
                    })
                }
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            barCodePrint(){
                // 打印
                let subOutputRankPrint = document.getElementById('subOutputRank-print');
                subOutputRankPrint.style.transform = 'scale(0.8)'
                let newContent =subOutputRankPrint.innerHTML;
                let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');

                wind.document.body.innerHTML = newContent;
                wind.print();
                wind.location.reload();
                wind.close();
                return false;
            },
            downloadFile(path, name) {
                this.obj[name].show = true;
                this.obj[name].load = 0;
                this.obj[name].progress = '';
                let schedule = setInterval(() => {
                    this.obj[name].load > 97 ? this.obj[name].load += 0 : this.obj[name].load += 1;
                }, 100)
                this.$http.post('/productDrawing/download', {filePath: path}, {responseType: 'arraybuffer'}).then((res) => {
                    fileDownload(res.data, utils.getFileName(path));
                    this.obj[name].load = 100;
                    clearInterval(schedule);
                    this.obj[name].progress = 'success';
                })
                    .catch(err => {
                        clearInterval(schedule);
                        this.obj[name].progress = 'exception';
                    })
            },
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/producePlanDetail') {
                    this.getData()
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
