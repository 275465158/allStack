<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">修改物料</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px" :rules="rules">
            <el-row :gutter="30">
                <el-col :span="6">
                    <el-form-item label="工厂物料编号" prop="factoryMaterialCode">
                        <el-input v-model="form.factoryMaterialCode"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="产品类型">
                        <el-select v-model="form.type" placeholder="数据来自产品类型模块" @change="selectProcessById($event)">
                            <el-option v-for="item in this.productTypeList" :key="item.number" :label="item.type" :value="item.number" >
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料名称" prop="materialName">
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
                <el-col>
                    <el-form-item label="状态:">
                        <el-select v-model="form.source" @change="openTable($event)" placeholder="请选择">
                            <el-option value="0"  label="自制"></el-option>
                            <el-option value="1"  label="外购"></el-option>
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
                <el-select v-model="selectProcessForm.processName" placeholder="请选择" @change="selectProcessById($event)">
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
                    <el-table-column label="序号" prop="id"></el-table-column>
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
            <el-table :data="materialInfoTable" border style="width:100%" height="250">
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
                pages:1,
                processInfo: [],
                processInfoTableHead:[],
                materialInfo:[],
                materialInfoTable:[],
                accpeptInfo:[],
                drawingInfo:[],
                dynamicParameterName:[],
                selectParamNameForm:{},
                selectProcessForm:[],
                selectMaterialForm:{
                    quantity:'',
                    materialBomParamValueStr:'',
                },
                selectDrawingForm:{
                    drawingCode:'',
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
                productTypeList:[],
                accpept:[],
                parameterTable:[],
                local:true,
                accpeptTable:true,
                drawingTable:true,
                materialFormVisible:false,
                drawingFormVisible:false,
                accpeptFormVisible:false,
                editFlag:true,
                id2:'',
                form: {
                    id:'',
                    materialCode:'',
                    materialName:'',
                    originalMaterial:'',
                    materialUnit:'',
                    checkId:'',
                    drawingId:'',
                    mapList:[],
                    flag:'0',
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
                if (this.$route.query.materialId != null) {
                    this.search.id = this.$route.query.materialId
                    this.$http.post("/materialInfo/detail", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.form = res.data.data;
                        this.form.number = res.data.data.number
                        this.selectProcessForm.processName = res.data.data.processName
                        if (this.form.source == "外购"){
                            this.local = false
                        }
                    }
                });
                    this.$http.post("/materialCheck/detail", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.accpeptInfo = []
                        for (var i=0;i<res.data.data.length;i++) {
                            this.accpeptInfo.push(res.data.data[i].productAcceptanceInfo)
                        }
                    }
                });
                    this.$http.post("/materialDrawing/detail", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.drawingInfo = []
                        for (var i=0;i<res.data.data.length;i++) {
                            this.drawingInfo.push(res.data.data[i].productDrawingInfo)
                        }
                    }
                });
                    this.$http.post("/materialRelation/detail", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.materialInfo=[]
                        this.form.mapList = []
                        for (var i=0;i<res.data.data.length;i++){
                            //给对象添加新属性
                            this.$set(res.data.data[i].materialBomInfo,"materialBomParamValueStr",res.data.data[i].materialBomParamValueStr)
                            this.$set(res.data.data[i].materialBomInfo,"quantity",res.data.data[i].quantity)
                            this.materialInfo.push(res.data.data[i].materialBomInfo)
                            this.form.mapList.push({id:res.data.data[i].materialChildId,quantity:res.data.data[i].quantity})
                        }
                    }
                });
                    this.$http.post("/materialProcess/detail", this.search ).then(res => {
                        this.processInfo = []
                    this.form.processInfo = []
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data.length != 0){
                            for (var i=0;i<res.data.data.length;i++){
                                var json = JSON.parse(res.data.data[i].jsonParam)
                                json['id']=res.data.data[i].id;
                                this.processInfo.push(json)
                                this.form.processInfo.push(JSON.parse(res.data.data[i].jsonParam))
                            }
                            this.processContentJson = res.data.data[0].jsonParam
                        }else{
                            this.form.processInfo = []
                        }
                    }
                });
                    this.$http.post("/materialBomParamName/detail", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.dynamicParameterName=[]
                        for (var i = 0;i<res.data.data.length;i++){
                            this.dynamicParameterName.push(res.data.data[i])
                        }
                    }
                });
                    this.$http.post("/processFormat/searchByMaterialId", this.search ).then(res => {
                        this.processInfoTableHead = []
                    if (res != undefined && res.data.code == 1000) {
                        this.processInfoTableHead = res.data.data;
                    }
                });
                }
                this.$http.post("/productType/searchList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.productTypeList = res.data.data;
                }
            });
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
            },
            selectLike(){
                if (this.search.id != ""){
                    this.id2 = this.search.id
                    this.search.id = ""
                }
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
                    for (var i = 0;i<this.form.dynamicParameterName.length;i++){
                        if (this.form.dynamicParameterName[i].productParameterNameInfo!=null){
                            this.form.dynamicParameterName[i].productParameterNameInfo = null
                        }
                    }
                }else {
                    this.form.dynamicParameterName = []
                }
                if (this.form.mapList==null){
                    this.form.mapList = [];
                }
                if (this.form.processInfo == null){
                    this.form.processInfo = []
                }else{
                    this.form.processInfo = this.processInfo
                }
                if (this.form.materialList == null){
                    this.form.materialList=[]
                }
                if (this.form.source == 1 && this.form.source=="外购"){
                    this.form.mapList = [];
                    this.form.processInfo = []
                }
                if (this.form.source=="外购") {
                    this.form.source = 1
                }
                if (this.form.source=="自制") {
                    this.form.source = 0
                }
                this.form.id = null
                this.$http.post("/materialInfo/saveMaterial", this.form).then((res) => {
                    if (res.data.code == 1000) {
                    this.$message.success('修改成功');
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
            selectMaterialById(value){
                for (var i=0;i<this.materialList.length;i++){
                    if (value == this.materialList[i].materialCode){
                        this.materialList1 = [];
                        this.selectMaterialForm.materialName = this.materialList[i].materialName
                        this.selectMaterialForm.materialUnit = this.materialList[i].materialUnit
                        this.selectMaterialForm.source = this.materialList[i].source
                        this.selectMaterialForm.materialBomParamValueStr = this.materialList[i].materialBomParamValueStr
                        this.materialList1.push(this.materialList[i]);
                    }
                }
            },
            selectMaterialByName(value){
                for (var i=0;i<this.materialList.length;i++){
                    if (value == this.materialList[i].materialName){
                        this.materialList1 = [];
                        this.selectMaterialForm.materialCode = this.materialList[i].materialCode
                        this.selectMaterialForm.materialUnit = this.materialList[i].materialUnit
                        this.selectMaterialForm.source = this.materialList[i].source
                        this.selectMaterialForm.materialBomParamValueStr = this.materialList[i].materialBomParamValueStr
                        this.materialList1.push(this.materialList[i]);
                    }
                }
            },
            appendMaterial(){
                this.materialList2 = this.materialList1
                for (var i=0;i<this.materialList2.length;i++){
                    this.materialList2[i].quantity = this.selectMaterialForm.quantity
                    this.materialInfo.push(this.materialList2[i]);
                }
                if (this.materialInfo.length != 0){
                    this.form.mapList = []
                    for (var i=0;i<this.materialInfo.length;i++){
                        this.form.mapList.push({id:this.materialInfo[i].id,quantity:this.materialInfo[i].quantity})
                    }
                }
                this.materialFormVisible = false
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
                        this.selectDrawingForm.drawingCode = this.drawingList[i].drawingCode;
                        this.selectDrawingForm.name = this.drawingList[i].name;
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
                if (this.id2 != ""){
                    this.search.id = this.id2
                }
                this.search.childMaterialId = row.id
                this.$http.post("/materialRelation/deleteMaterial",this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                }
            });
                this.form.mapList.splice(index,1)
            },
            removeParamName(index, row) {
                this.dynamicParameterName.splice(index, 1);
                this.$http.post("/materialBomParamName/deleteParamName",{id: row.id}).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                }
            });
            },
            removeAccpept(index, row){
                this.accpeptInfo.splice(index,1)
                if (this.id2 != ""){
                    this.search.id = this.id2
                }
                this.$http.post("/materialCheck/deleteCheck",this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                }
            });
            },
            removeDrawing(index, row){
                this.drawingInfo.splice(index,1)
                if (this.id2 != ""){
                    this.search.id = this.id2
                }
                this.$http.post("/materialDrawing/deleteDrawing",this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                }
            });
            },
            removeProcess(index, row){
                this.processInfo.splice(index,1)
                if (row.id != null) {
                    this.$http.post("/materialProcess/deleteProcess", {id: row.id}).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                    }
                });
                }
                this.form.processInfo = this.processInfo;
            },
            changeMapList(row,index){
                for (var i=0;i<this.form.mapList.length;i++){
                    if (this.form.mapList[i].id == row.id){
                        this.form.mapList.splice(i,1)
                        this.form.mapList.push({id:row.id,quantity:row.quantity})
                    }
                }
            },
            addProcess() {
                var json = JSON.parse(this.processContentJson)
                json["id"] = 0
                this.processInfo.push(json);
                if (this.processInfo.length!=0){
                    this.form.processInfo = this.processInfo
                }
            },
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