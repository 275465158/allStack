<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">新增产品</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px" :rules="rules">
            <el-row :gutter="30">
                <el-col :span="6">
                    <el-form-item label="产品编号">
                        <el-input v-model="form.materialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="工厂产品编号" prop="factoryMaterialCode">
                        <el-input v-model="form.factoryMaterialCode"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="产品类型">
                        <el-select v-model="form.number" placeholder="数据来自产品类型模块" @change="selectProcessById($event)">
                            <el-option v-for="item in this.productTypeList" :key="item.number" :label="item.type" :value="item.number" >
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="产品名称" prop="materialName">
                        <el-input v-model="form.materialName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="原图材料">
                        <el-input v-model="form.originalMaterial"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="单位">
                        <el-input v-model="form.materialUnit"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="状态:">
                        <el-select v-model="form.source" @change="openTable($event)" placeholder="请选择">
                            <el-option value="0"  label="自制"></el-option>
                            <el-option value="1"  label="外购"></el-option>
                            <!--后期添加（自制/外购）这个选项 新增订单的时候也要判断来源是自制/外购这个状态下是自制还是外购-->
                            <!--<el-option value="2"  label="自制/外购"></el-option>-->
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom" />
            <span class="text">参数</span>
            <el-button v-if="editFlag" size="small" @click="addParameterName()">添加参数</el-button>
            <el-table :data="dynamicParameterName" border style="width:100%" height="250" >
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="参数值">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialParamNameValue"></el-input>
                    </template>
                </el-table-column>
                <el-table-column v-if="editFlag" label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="removeParamName(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <hr class="marginBottom" />
            <div v-show="local">
                <span class="text">工艺流程</span><br/>
                <span class="text">工艺名称:</span>
                <el-select v-model="selectProcessForm.processName" placeholder="数据来自工艺设置模块" @change="selectProcessById($event)">
                    <el-option v-for="item in this.processList" :key="item.processName" :label="item.processName" :value="item.processName" >
                    </el-option>
                </el-select>
                <el-button v-if="editFlag" size="small" @click="addProcess()">添加制程</el-button>
                <hr class="marginBottom" />
                <el-table :data="processInfo" border style="width:100%" height="250" id="processInfoTable">
                    <el-table-column v-for="(process, index) in processInfoTableHead"
                                     :label="process.processFormatName==''?('参数'+(index+1))
                                     :process.processFormatName"
                                     :key="process.processFormatName">
                        <template slot-scope="scope">
                            <el-input v-if="process.processFormatName != '作业指导书' && process.processFormatName != '人数'
                                            && process.processFormatName != '额定工时' && process.processFormatName != '设备名称'"
                                      v-model="scope.row[scope.column.label]"></el-input>
                            <el-input v-if="process.processFormatName === '额定工时' || process.processFormatName === '人数'" type="number" v-model="scope.row[scope.column.label]"></el-input>
                            <el-select  v-if="process.processFormatName === '作业指导书'" v-model="scope.row[scope.column.label]">
                                <el-option
                                        v-for="item in jobGuideList"
                                        :key="item.name"
                                        :label="item.name"
                                        :value="item.name">
                                </el-option>
                            </el-select>
                            <el-select  v-if="process.processFormatName === '设备名称'" v-model="scope.row[scope.column.label]">
                                <el-option
                                        v-for="item in equipmentList"
                                        :key="item.equipmentName"
                                        :label="item.equipmentName"
                                        :value="item.equipmentName">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="editFlag" label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="removeProcess(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <hr class="marginBottom" />

                <span class="text">物料列表</span>
                <el-button @click="selectMaterial">选择物料</el-button>
                <hr class="marginBottom" />
                <el-table :data="materialInfo" border style="width:100%" height="250">
                    <el-table-column label="序号" prop="id">
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                    <el-table-column label="物料名称" prop="materialName"></el-table-column>
                    <el-table-column label="原图材料" prop="originalMaterial"></el-table-column>
                    <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column label="单位" prop="materialUnit"></el-table-column>
                    <el-table-column label="数量" prop="quantity">
                        <template slot-scope="scope">
                            <el-input type="number" v-model="scope.row.quantity" :min="0" v-on:input="changeMapList(scope.row,scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="detail(scope.$index, scope.row)">明细</el-button>
                            <el-button size="small" @click="removeMaterial(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom" />
            <input id="accpeptInfoId" type="checkbox" @change="changeAccpeptTable($event)"/><span class="text">验收标准</span>
            <div v-show="accpeptTable">
                <el-button @click="selectAccpept">选择验收标准</el-button>
                <el-table :data="accpeptInfo" border style="width:100%" height="250">
                    <el-table-column label="验收编号" prop="id"></el-table-column>
                    <el-table-column label="验收名称" prop="name"></el-table-column>
                    <el-table-column label="制作人" prop="owner"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="removeAccpept(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom" />
            <input id="drawingId" type="checkbox" @change="changeDrawingTable($event)"/><span class="text">图纸</span>
            <div v-show="drawingTable">
                <el-button @click="selectDrawing">选择图纸</el-button>
                <el-table :data="drawingInfo" border style="width:100%" height="250">
                    <el-table-column label="图号" prop="drawingCode"></el-table-column>
                    <el-table-column label="图纸名称" prop="name"></el-table-column>
                    <el-table-column label="制作人" prop="owner"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="removeDrawing(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginTop"/>
            <el-button @click="goBack">返回</el-button>
            <el-button round type="primary" @click="save">保存修改</el-button>
        </el-form>
        <!-- 编辑弹出框 -->
        <el-dialog title="选择物料" :visible.sync="materialFormVisible" width="60%">
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="物料编号:">
                    <el-input v-model="search.materialCode"></el-input>
                </el-form-item>
                <el-form-item label="物料名称:">
                    <el-input v-model="search.materialName"></el-input>
                </el-form-item>
                <el-form-item label="原图材料:">
                    <el-input v-model="search.originalMaterial"></el-input>
                </el-form-item>
                <el-form-item label="参数:">
                    <el-input v-model="search.materialBomParamValueStr"></el-input>
                </el-form-item>
                <el-button round @click="selectLike">查询</el-button>
                <!--<el-button round @click="clearData">清空</el-button>-->
            </el-form>
            <el-table :data="materialInfoTable" border style="width:100%" height="300">
                <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materialName"></el-table-column>
                <el-table-column label="原图材料" prop="originalMaterial"></el-table-column>
                <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                <el-table-column label="单位" prop="materialUnit"></el-table-column>
                <el-table-column label="来源" prop="source"></el-table-column>
                <el-table-column label="数量" prop="quantity">
                    <template slot-scope="scope">
                        <el-input type="number" v-model="scope.row.quantity" :min="0" v-on:input="changeMapList(scope.row,scope.$index)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="addMaterial(scope.$index, scope.row)">添加物料</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="5" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="materialFormVisible = false">取 消</el-button>
            </span>
        </el-dialog>
        <el-dialog title="选择验收标准" :visible.sync="accpeptFormVisible" width="30%">
            <el-form ref="selectAccpeptForm" :model="selectAccpeptForm" label-width="100px">
                <el-form-item label="验收编号:" prop="id">
                    <el-select v-model="selectAccpeptForm.id" placeholder="请选择" @change="selectAccpeptById($event)">
                        <el-option v-for="item in this.accpeptList" :key="item.id" :label="item.id" :value="item.id" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="验收名称:" prop="name">
                    <el-select v-model="selectAccpeptForm.name" placeholder="请选择" @change="selectAccpeptByName($event)">
                        <el-option v-for="item in this.accpeptList" :key="item.name" :label="item.name" :value="item.name">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="制作人:">
                    <el-input v-model="selectAccpeptForm.owner" disabled></el-input>
                </el-form-item>
                <el-table :data="accpept" border style="width:100%" height="250">
                    <el-table-column label="检测名称" prop="checkName"></el-table-column>
                    <el-table-column label="设备名称" prop="equipmentName"></el-table-column>
                    <el-table-column label="设备规格" prop="equipmentSpecification"></el-table-column>
                    <el-table-column label="单位" prop="unit"></el-table-column>
                    <el-table-column label="最小值" prop="minValue"></el-table-column>
                    <el-table-column label="最大值" prop="maxValue"></el-table-column>
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="accpeptFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="appendAccpept()">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="选择图纸" :visible.sync="drawingFormVisible" width="30%">
            <el-form ref="selectDrawingForm" :model="selectDrawingForm" label-width="100px">
                <el-form-item label="图号:" prop="id">
                    <el-select v-model="selectDrawingForm.id" placeholder="请选择" @change="selectDrawingById($event)">
                        <el-option v-for="item in this.drawingList" :key="item.id" :label="item.drawingCode" :value="item.id" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图纸名称:" prop="name">
                    <el-select v-model="selectDrawingForm.name" placeholder="请选择" @change="selectDrawingByName($event)">
                        <el-option v-for="item in this.drawingList" :key="item.name" :label="item.name" :value="item.name">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图纸说明:">
                    <el-input v-model="selectDrawingForm.drawingRemark" disabled></el-input>
                </el-form-item>
                <el-form-item label="工艺说明:">
                    <el-input v-model="selectDrawingForm.technologyRemark" disabled></el-input>
                </el-form-item>
                <el-form-item label="制作人:">
                    <el-input v-model="selectDrawingForm.owner" disabled></el-input>
                </el-form-item>
                <el-form-item label="审批人:">
                    <el-input v-model="selectDrawingForm.checkName" disabled></el-input>
                </el-form-item>
                <el-form-item label="CAD图纸:">
                    <el-input v-model="selectDrawingForm.cadFile" disabled></el-input>
                </el-form-item>
                <el-form-item label="PDF图纸:">
                    <el-input v-model="selectDrawingForm.pdfFile" disabled></el-input>
                </el-form-item>
                <el-form-item label="工艺附件:">
                    <el-input v-model="selectDrawingForm.technologyFile" disabled></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="drawingFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="appendDrawing()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                processInfo: [],
                processInfoTableHead:[],
                materialInfo:[],
                materialInfoTable:[],
                accpeptInfo:[],
                drawingInfo:[],
                selectParamSpecificationForm:{
                    paramType:'',
                },
                pages:1,
                dynamicParameterName:[],
                selectParamNameForm:{},
                selectProcessForm:[],
                selectMaterialForm:{
                    quantity:'',
                    materialBomParamNameStr:'',
                    materialBomParamValueStr:'',
                },
                selectDrawingForm:{
                    drawingCode:'',
                    id:'',
                    name:'',
                    drawingRemark:'',
                    technologyRemark:'',
                    owner:'',
                    checkName:'',
                    cadFile:'',
                    pdfFile:'',
                    technologyFile:'',
                },
                selectAccpeptForm:{
                    id:'',
                    name:'',
                    owner:'',
                },
                paramSpecificationList:[],
                paramNameList:'',
                processList:[],
                materialList:[],
                materialList1:[],
                materialList2:[],
                drawingList:[],
                drawingList1:[],
                accpeptList:[],
                accpeptList1:[],
                jobGuideList:[],
                equipmentList:[],
                accpept:[],
                parameterTable:[],
                processContentJson: '',
                productTypeList:[],
                local:false,
                accpeptTable:false,
                drawingTable:false,
                materialFormVisible:false,
                drawingFormVisible:false,
                accpeptFormVisible:false,
                editFlag:true,
                form: {
                    id:'',
                    materialCode:'',
                    factoryMaterialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    materialUnit:'',
                    number:'',
                    checkId:'',
                    drawingId:'',
                    processId:'',
                    mapList:[],
                    flag:'1',
                },
                search:{
                    id:'',
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    materialBomParamValueStr:'',
                    flag:0,
                    pageNum:1,
                    pageSize:5
                },
                rules: {
                    materialName: [
                        { required: true, message: '请输入物料名称', trigger: 'blur' }
                    ]
                }
            };
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.selectLike();
            },
            getData() {
                if (this.form.source == null){
                    this.form.source = "1"
                }
                this.$http.post("/productDrawing/searchDrawingList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.drawingList = res.data.data;
                    }
                });
                this.$http.post("/productAcceptance/searchAccpetList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.accpeptList = res.data.data;
                    }
                });
                this.$http.post("/materialInfo/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materialList = res.data.data;
                    }
                });
                this.$http.post("/process/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.processList = res.data.data;
                        if (this.processList.length!=0){
                            for (var i=0;i<this.processList.length;i++){
                                if (this.processList[i].defaultProcess==1){
                                    this.selectProcessForm.processName = this.processList[i].processName
                                    this.form.processId = this.processList[i].id
                                    this.processInfoTableHead = [];
                                    this.processContentJson =  this.processList[i].contentJson;
                                    for (var j=0;j<this.processList[i].processFormatInfoList.length;j++){
                                        this.processInfoTableHead.push(this.processList[i].processFormatInfoList[j])
                                    }
                                }
                            }
                        }
                    }
                });
                this.$http.post("/productParameterSpecification/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.paramSpecificationList = res.data.data;
                    }
                });
                this.$http.post("/productParameterName/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.paramNameList = res.data.data;
                    }
                });
                this.$http.post("/productJobGuide/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.jobGuideList = res.data.data;
                    }
                });
                this.$http.post("/equipmentManage/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.equipmentList = res.data.data;
                    }
                });
                this.$http.post("/productType/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.productTypeList = res.data.data;
                    if (this.productTypeList.length!=0){
                        this.form.number = this.productTypeList[0].number
                    }
                }
            });
            },
            selectLike(){
                this.$http.post("/materialInfo/searchMaterialList", this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materialInfoTable = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                });
            },
            addMaterial(index,row){
                this.materialList2 = []
                this.materialList2 = row
                if (this.materialInfo.length!=0){
                    for (var i=0;i<this.materialInfo.length;i++){
                        if (this.materialList2.materialCode == this.materialInfo[i].materialCode){
                            this.materialList2 = []
                        }
                    }
                }
                if (this.materialList2.length != 0){
                    this.materialInfo.push(this.materialList2)
                }
                if (this.materialInfo.length != 0){
                    this.form.mapList = []
                    for (var i=0;i<this.materialInfo.length;i++){
                        this.form.mapList.push({id:this.materialInfo[i].id,quantity:this.materialInfo[i].quantity})
                    }
                }
                this.materialFormVisible = false
            },
            goBack() {
                this.$router.push("/productList");
            },
            save(){
                this.insert();
            },
            insert(){
                if(this.dynamicParameterName.length>0){
                    this.form.dynamicParameterName = this.dynamicParameterName
                }else {
                    this.form.dynamicParameterName = []
                }
                if (this.processInfo.length>0){
                    this.form.processInfo = this.processInfo
                } else {
                    this.form.processInfo = []
                }
                this.$http.post("/materialInfo/saveMaterial", this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.$message.success('添加成功');
                        //跳转到列表页面
                        this.$router.push({ path: '/productList'})
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            addParameterName() {
                let param = {
                    materialParamNameValue: ""
                };
                this.dynamicParameterName.push(param)
            },
            addProcess() {
                var json = JSON.parse(this.processContentJson)
                json["id"] = ""
                this.processInfo.push(json);
            },
            removeParamName(index, row) {
                this.dynamicParameterName.splice(index, 1);
            },
            selectProcessById(value){
                this.form.processId = ""
                for (var i=0;i<this.processList.length;i++){
                    if (value == this.processList[i].processName){
                        this.processInfoTableHead = [];
                        this.processContentJson =  this.processList[i].contentJson;
                        for (var j=0;j<this.processList[i].processFormatInfoList.length;j++){
                            this.processInfoTableHead.push(this.processList[i].processFormatInfoList[j])
                        }
                        this.form.processId = this.processList[i].id
                    }
                }
                this.processInfo = []
            },
            selectMaterial(){
                this.materialFormVisible = true
            },
            selectAccpept(){
                this.accpeptFormVisible = true
            },
            selectAccpeptById(value){
                for (var i=0;i<this.accpeptList.length;i++){
                    if (value == this.accpeptList[i].id){
                        this.accpeptList1 = [];
                        this.selectAccpeptForm.name = this.accpeptList[i].name;
                        this.selectAccpeptForm.owner = this.accpeptList[i].owner;
                        this.accpept = this.accpeptList[i].productAcceptanceDetailInfoList
                        this.accpeptList1.push(this.accpeptList[i])
                    }
                }
            },
            selectAccpeptByName(value){
                for (var i=0;i<this.accpeptList.length;i++){
                    if (value == this.accpeptList[i].name){
                        this.accpeptList1 = [];
                        this.selectAccpeptForm.id = this.accpeptList[i].id;
                        this.selectAccpeptForm.owner = this.accpeptList[i].owner;
                        this.accpept = this.accpeptList[i].productAcceptanceDetailInfoList
                        this.accpeptList1.push(this.accpeptList[i])
                    }
                }
            },
            appendAccpept(){
                this.accpeptInfo = this.accpeptList1
                if (this.accpeptInfo.length>0){
                    this.form.checkId = this.accpeptInfo[0].id
                }
                this.accpeptFormVisible = false
            },
            selectDrawing(){
                this.drawingFormVisible = true
            },
            selectDrawingById(value){
                for(var i=0;i<this.drawingList.length;i++){
                    if(value == this.drawingList[i].id){
                        this.drawingList1 = [];
                        this.selectDrawingForm.name = this.drawingList[i].name;
                        this.selectDrawingForm.drawingCode = this.drawingList[i].drawingCode;
                        this.selectDrawingForm.drawingRemark = this.drawingList[i].drawingRemark;
                        this.selectDrawingForm.technologyRemark = this.drawingList[i].technologyRemark;
                        this.selectDrawingForm.owner = this.drawingList[i].owner;
                        this.selectDrawingForm.checkName = this.drawingList[i].checkName;
                        this.selectDrawingForm.cadFile = this.drawingList[i].cadFile;
                        this.selectDrawingForm.pdfFile = this.drawingList[i].pdfFile;
                        this.selectDrawingForm.technologyFile = this.drawingList[i].technologyFile;
                        this.drawingList1.push(this.drawingList[i])
                    }
                }
            },
            selectDrawingByName(value){
                for(var i=0;i<this.drawingList.length;i++){
                    if(value == this.drawingList[i].name){
                        this.drawingList1 = [];
                        this.selectDrawingForm.id = this.drawingList[i].id;
                        this.selectDrawingForm.drawingCode = this.drawingList[i].drawingCode;
                        this.selectDrawingForm.drawingRemark = this.drawingList[i].drawingRemark;
                        this.selectDrawingForm.technologyRemark = this.drawingList[i].technologyRemark;
                        this.selectDrawingForm.owner = this.drawingList[i].owner;
                        this.selectDrawingForm.checkName = this.drawingList[i].checkName;
                        this.selectDrawingForm.cadFile = this.drawingList[i].cadFile;
                        this.selectDrawingForm.pdfFile = this.drawingList[i].pdfFile;
                        this.selectDrawingForm.technologyFile = this.drawingList[i].technologyFile;
                        this.drawingList1.push(this.drawingList[i])
                    }
                }
            },
            appendDrawing(){
                this.drawingInfo = this.drawingList1
                if (this.drawingInfo.length>0){
                    this.form.drawingId = this.drawingInfo[0].id
                }
                this.drawingFormVisible=false
            },
            openTable(value){
                if (value==0 || value==2){
                    this.local = true
                }else{
                    this.local = false
                    this.materialInfo = []
                }
            },
            changeAccpeptTable(){
                var checked = document.getElementById("accpeptInfoId")
                if (checked.checked){
                    this.accpeptTable = true
                }else {
                    this.accpeptTable = false
                    this.accpeptInfo = []
                }
            },
            changeDrawingTable(){
                var checked = document.getElementById("drawingId")
                if (checked.checked){
                    this.drawingTable = true
                }else {
                    this.drawingTable = false
                    this.drawingInfo = []
                }
            },
            detail(index,row) {
                this.$router.push({
                    path: "/productSearch",
                    query: {
                        materialId: row.id
                    }
                });
            },
            removeMaterial(index, row){
                this.materialInfo.splice(index,1)
                this.form.mapList.splice(index,1)
            },
            removeAccpept(index, row){
                this.accpeptInfo.splice(index,1)
                this.form.checkId = ""
            },
            removeDrawing(index, row){
                this.drawingInfo.splice(index,1)
                this.form.drawingId = ""
            },
            removeProcess(index, row){
                this.processInfo.splice(index,1)
                this.form.processInfo.splice(index,1)
            },
            changeMapList(row,index){
                for (var i=0;i<this.form.mapList.length;i++){
                    if (this.form.mapList[i].id == row.id){
                        this.form.mapList.splice(i,1)
                        this.form.mapList.push({id:row.id,quantity:row.quantity})
                    }
                }
            }
        },
        watch: {
            $route: "getData"
        }
    };
</script>
<style scoped>
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
