<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span>当前仓库：{{this.repertoryName}}</span>
            </div>
            <div class="handle-box">
                <el-form :model="form" ref="form" label-width="100px">
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="物料编号:">
                                <el-input v-model="form.materialCode" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="物料名称:">
                                <el-input v-model="form.materialName" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="材质:">
                                <el-input v-model="form.originalMaterial" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="来源:">
                                <el-input v-model="form.source" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="规格:">
                                <el-input v-model="form.materialParam" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="订单需求数:">
                                <el-input v-model="form.orderNeedQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="安全库存数:">
                                <el-input v-model="form.inventorySafeQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="待入库总数:">
                                <el-input v-model="form.stayInboundQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="待采购数:">
                                <el-input v-model="form.stayPurchaseQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="待收货数:">
                                <el-input v-model="form.stayDeliveredQty" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
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
                <el-table-column prop="productDrawingInfo.drawingCode" label="图纸编号">
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
                        <el-button type="text" v-if="scope.row.productDrawingInfo.pdfFile != ''" @click="downloadFile(scope.row.productDrawingInfo.pdfFile)">下载</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="CAD图纸">
                    <template slot-scope="scope">
                        <span>{{ scope.row.productDrawingInfo.cadDisplayName }}</span>
                        <el-button type="text" v-if="scope.row.productDrawingInfo.cadFile != ''" @click="downloadFile(scope.row.productDrawingInfo.cadFile)">下载</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="productDrawingInfo.technologyFile" label="工艺图纸">
                    <template slot-scope="scope">
                        <span>{{ scope.row.productDrawingInfo.technologyDisplayName }}</span>
                        <el-button type="text" v-if="scope.row.productDrawingInfo.technologyFile != ''" @click="downloadFile(scope.row.productDrawingInfo.technologyFile)">下载</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="handle-box">
                <span class="el-form-item__label">物料明细列表</span>
            </div>
            <div class="handle-box">
                <el-table :data="tables" border stripe style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="supplierName" label="供应商名称"/>
                    <el-table-column prop="materialBatch" label="物料批次"/>
                    <el-table-column prop="inventoryQty" label="数量"/>
                    <el-table-column prop="purchaseReason" label="采购原因"/>
                    <el-table-column prop="source" label="物料来源"/>
                    <el-table-column prop="storageLocation" label="货架位置"/>
                    <el-table-column prop="inboundDate" label="入库时间"/>
                    <el-table-column prop="operatingPersonnelName" label="收货人"/>
                    <el-table-column align="center" label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button type="text" @click="shiftPosition(scope.row)">移位</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-dialog title="物料移位" @close="closeing" :visible.sync="shiftPositionVisible" width="30%">
                    <el-form :inline="true" @keyup.enter.native="searchOne" :model="search" class="demo-form-inline">
                        <el-form-item label="移动数量:">
                            <el-input v-model="shiftPositionForm.moveQty" v-on:input="inputValue" type="number"></el-input>
                        </el-form-item>
                        <el-form-item label="目标库位:">
                            <el-cascader v-model="shiftPositionForm.targetPosition" :options="optionsRepertory" placeholder="货架号/层/排/列">
                            </el-cascader>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="saveShiftPosition">确 定</el-button>
                        <el-button @click="shiftPositionVisible = false">取 消</el-button>
                    </span>
                </el-dialog>
                <div class="pagination">
                    <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                    </el-pagination>
                </div>
                <span class="dialog-footer">
                    <el-button @click="goBack" type="primary">返回</el-button>
                </span>
            </div>
        </div>
        <!--<div id="subOutputRank-print" style="display: none">
            <div class="content" v-for="(v,k) in barCodelist">
                <span>{{v.materielName}}</span>
                <barcode :value="v.barcodes" :options="barcode_option" tag="svg" ></barcode>
            </div>
        </div>-->
    </div>
</template>
<script>
import Qs from "qs";
import * as utils from '../../../../../static/js/utils.js';
import fileDownload from 'js-file-download';
export default {
    data() {
        return {
            tableData: [],
            url:"materialRepertoryDetail/detailListByLike",
            pages: 1,
            form: {
                materialCode:'',
                materialName:'',
                materialParam:'',
                originalMaterial:'',
                source:''
            },
            search: {
                pageSize:20,
                pageNum: 1,
                materialCode:'',
                repertoryId:''
            },
            repertoryName:'',
            drawingTables:[],
            drawingSearch:{
                materialId:'',
                pageNum:1
            },
            shiftPositionForm:{
                id:'',
                moveQty:0,
                targetPosition:[]
            },
            shiftPositionVisible:false,
            optionsRepertory: [],
            inventoryQty:0
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
        handleChange(value) {
        },
        handleSelect(item) {
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        // 获取数据
        getData() {
            this.search.materialCode = this.$route.query.row.materialCode
            this.search.repertoryId = this.$route.query.repertoryId
            this.repertoryName = this.$route.query.repertoryName
            this.form = this.$route.query.row
            this.drawingSearch.materialId = this.$route.query.row.materialId
            if(this.$route.query.repertoryId != null && this.$route.query.repertoryId != undefined){
                this.$http.post(this.url, this.search).then(res => {
                    console.log(res)
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                        if (res.data.data.list.length) {
                            this.form.materialParam = res.data.data.list[0].materialParam
                            this.form.originalMaterial = res.data.data.list[0].originalMaterial
                        }
                    }
                });
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
            }
        },
        inputValue(event){
            if (event > this.inventoryQty){
                this.$message.error("移位数量不能大于"+this.inventoryQty)
            }
        },
        shiftPosition(row){
            this.inventoryQty = 0;
            this.shiftPositionForm.id = row.id;
            this.inventoryQty = row.inventoryQty;
            this.$http.post("/storage/getJson", { repertoryId: this.search.repertoryId }).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.optionsRepertory = JSON.parse(res.data.data);
                }
            });
            this.shiftPositionVisible = true;
        },
        saveShiftPosition(){
            if(this.shiftPositionForm.moveQty == 0){
                this.$message.error("请输入移动数量");
            }else if(this.shiftPositionForm.moveQty > this.inventoryQty){
                this.$message.error("移位数量不能大于"+this.inventoryQty);
            }else if(this.shiftPositionForm.targetPosition.length == 0){
                this.$message.error("请选择目标库位");
            }else{
                this.$http.post("/materialRepertoryDetail/saveShiftPosition", this.shiftPositionForm).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                        this.shiftPositionVisible = false;
                        this.$message.success("物料移位成功");
                    }
                });
            }
        },
        goBack() {
            this.$router.push({
                path: "/materialRepertoryList",
                query: { repertoryId: this.$route.query.repertoryId, repertoryName: this.$route.query.repertoryName, closeFlag: 1 }
            });
        },
        downloadFile(path) {
            let _this = this;
            let obj = this.drawingTables[0];
            obj.load = 0;
            obj.progress = '';
            obj.show = true;
            let schedule = setInterval(function () {
                    obj.load > 97 ? obj.load += 0 : obj.load += 1
                }
                , 100);
            this.$http.post('/productDrawing/download', {filePath: path}, {responseType: 'arraybuffer'}).then((res) => {
                fileDownload(res.data, utils.getFileName(path));
                obj.load = 100;
                clearInterval(schedule);
                obj.progress = 'success'
            })
                .catch(err => {
                    clearInterval(schedule);
                    obj.progress = 'exception'
                })
        },
        closeing () {
            this.shiftPositionForm.moveQty = 0;
            this.shiftPositionForm.targetPosition = [];
        }
    },
    mounted() {},
    watch: {
        '$route' (to, form) {
            if (to.path == '/repertoryMaterialInfo') {
                let newId = this.$route.query.repertoryId;
                let newMaterialId = this.$route.query.row.materialId
                if (newId == undefined) {
                    newId = 0;
                }
                if(newMaterialId == undefined){
                    newMaterialId = 0;
                }
                this.form = {
                    materialCode: '',
                    materialName: '',
                    materialParam: '',
                    originalMaterial: ''
                }

                this.search.repertoryId = newId;
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
</style>

