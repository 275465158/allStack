<template>
    <div class="container materialadd">
        <hr class="marginTop">
        <span class="text">修改物料</span>
        <hr class="marginBottom">
        <el-form v-loading="loading" :model="form" ref="form" label-width="100px" :rules="rules">
            <el-row :gutter="30">
                <el-col :span="6">
                    <el-form-item label="物料编号">
                        <el-input v-model="form.materialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="工厂物料编号" prop="factoryMaterialCode">
                        <el-input v-model="form.factoryMaterialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="产品类型">
                        <el-input v-model="form.type" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料名称" prop="materialName">
                        <el-input v-model="form.materialName" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="原图材料">
                        <el-input v-model="form.originalMaterial" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="单位">
                        <el-input v-model="form.materialUnit" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col>
                    <el-form-item label="来源:">
                        <el-select
                            v-model="form.source"
                            @change="openTable($event)"
                            placeholder="请选择"
                            disabled
                        >
                            <el-option value="0" label="自制"></el-option>
                            <el-option value="1" label="外购"></el-option>
                            <!--<el-option value="2"  label="自制/外购"></el-option>-->
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom">
            <span class="text">参数</span>
            <!--<el-button v-if="editFlag" size="small" @click="addParameterName()">添加参数</el-button>-->
            <el-table :data="dynamicParameterName" border style="width:100%" height="250">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column label="参数值">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.materialParamNameValue" disabled></el-input>
                    </template>
                </el-table-column>
                <!--<el-table-column v-if="editFlag" label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="removeParamName(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>-->
            </el-table>
            <hr class="marginBottom">
            <div v-show="local" class="technologyBox">
                <span class="text">工艺流程</span>
                <br>
                <span class="text">工艺名称:</span>
                <el-select
                    v-model="selectProcessForm.processName"
                    placeholder="请选择"
                    @change="selectProcessById($event)"
                >
                    <el-option
                        v-for="item in this.processList"
                        :key="item.processName"
                        :label="item.processName"
                        :value="item.processName"
                    ></el-option>
                </el-select>
                <el-button v-if="editFlag" size="small" @click="addProcess()">添加制程</el-button>
                <hr class="marginBottom">
                <el-table
                    :data="processInfo"
                    border
                    style="width:100%"
                    height="250"
                    ref="processInfoTable"
                    id="processInfoTable"
                    @selection-change="handleSelectMaterial"
                >
                    <el-table-column
                        v-for="(process, index) in processInfoTableHead"
                        :label="process.processFormatName==''?('参数'+(index+1))
                                     :process.processFormatName"
                        :key="process.processFormatName"
                    >
                        <template slot-scope="scope">
                            <el-input
                                v-if="process.processFormatName != '作业指导书' && process.processFormatName != '人数'
                                            && process.processFormatName != '额定工时' && process.processFormatName != '设备名称' && process.processFormatName != '工艺序号'
                                            && process.processFormatName!= '图纸' && process.processFormatName != '加工程序'"
                                v-model="scope.row[scope.column.label]"
                            ></el-input>
                            <el-input
                                readonly="true"
                                v-if="process.processFormatName=='工艺序号'"
                                v-model="scope.row[scope.column.label]"
                            ></el-input>
                            <el-input
                                onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"
                                v-if="process.processFormatName === '额定工时' || process.processFormatName === '人数'"
                                type="tel"
                                v-model="scope.row[scope.column.label]"
                            ></el-input>
                            <el-select
                                v-if="process.processFormatName === '作业指导书'"
                                v-model="scope.row[scope.column.label]"
                            >
                                <el-option
                                    v-for="item in jobGuideList"
                                    :key="item.name"
                                    :label="item.name"
                                    :value="item.name"
                                ></el-option>
                            </el-select>
                            <el-select
                                    v-if="process.processFormatName === '图纸'"
                                    v-model="scope.row[scope.column.label]"
                                    filterable
                            >
                                <el-option
                                        v-for="item in drawingOptions"
                                        :key="item.name"
                                        :label="item.name"
                                        :value="item.name"
                                ></el-option>
                            </el-select>
                            <el-select
                                    v-if="process.processFormatName === '加工程序'"
                                    v-model="scope.row[scope.column.label]"
                                    filterable
                            >
                                <el-option
                                        v-for="item in equipmentOptions"
                                        :key="item.equipmentName"
                                        :label="item.equipmentName"
                                        :value="item.equipmentName"
                                ></el-option>
                            </el-select>
                            <el-select
                                v-if="process.processFormatName === '设备名称'"
                                v-model="scope.row[scope.column.label]"
                            >
                                <el-option
                                    v-for="item in equipmentList"
                                    :key="item.equipmentName"
                                    :label="item.equipmentName"
                                    :value="item.equipmentName"
                                ></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="加工费">
                        <template slot-scope="scope">
                            <el-input
                                type="tel"
                                onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"
                                v-model.number="scope.row.price"
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column width="80" type="selection"></el-table-column>
                    <el-table-column v-if="editFlag" label="操作">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                @click="removeProcess(scope.$index, scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <hr class="marginBottom">

                <span class="text">物料列表</span>
                <el-button @click="selectMaterial">选择物料</el-button>
                <hr class="marginBottom">
                <el-table :data="materialInfo" border style="width:100%" height="250">
                    <el-table-column label="序号" prop="id"></el-table-column>
                    <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                    <el-table-column label="物料名称" prop="materialName"></el-table-column>
                    <el-table-column label="原图材料" prop="originalMaterial"></el-table-column>
                    <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column label="单位" prop="materialUnit"></el-table-column>
                    <el-table-column label="数量" prop="quantity">
                        <template slot-scope="scope">
                            <el-input
                                type="number"
                                v-model="scope.row.quantity"
                                :min="0"
                                v-on:input="changeMapList(scope.row,scope.$index)"
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="detail(scope.$index, scope.row)">明细</el-button>
                            <el-button
                                size="small"
                                @click="removeMaterial(scope.$index, scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom">
            <div v-show="accpeptTable">
                <el-button @click="selectAccpept">选择验收标准</el-button>
                <el-table :data="accpeptInfo" border style="width:100%" height="250">
                    <el-table-column label="验收编号" prop="id"></el-table-column>
                    <el-table-column label="验收名称" prop="name"></el-table-column>
                    <el-table-column label="制作人" prop="owner"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                @click="removeAccpept(scope.$index, scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom">
            <div v-show="drawingTable">
                <el-button @click="selectDrawing">选择图纸</el-button>
                <el-table :data="drawingInfo" border style="width:100%" height="250">
                    <el-table-column label="图号" prop="drawingCode"></el-table-column>
                    <el-table-column label="图纸名称" prop="name"></el-table-column>
                    <el-table-column label="制作人" prop="owner"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                @click="removeDrawing(scope.$index, scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginTop">
            <el-button @click="goBack">返 回</el-button>
            <el-button
                round
                type="primary"
                @click="stayAuditing"
                v-show="workflowSuspended==false"
            >保 存</el-button>
            <el-button
                round
                type="primary"
                @click="auditingPass"
                v-show="workflowSuspended==true"
            >保存</el-button>
        </el-form>
        <!-- 编辑弹出框 -->
        <el-dialog
            title="选择物料"
            @close="materialClose"
            :visible.sync="materialFormVisible"
            width="60%"
        >
            <div v-loading="materialLoading">
                <el-form
                    :inline="true"
                    @keyup.enter.native="searchEnterFun"
                    :model="search"
                    class="demo-form-inline"
                >
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
                    <el-button round @click="searchAll">查询</el-button>
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
                            <el-input
                                type="number"
                                v-model="scope.row.quantity"
                                :min="0"
                                oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                v-on:input="changeMapList(scope.row,scope.$index)"
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                @click="addMaterial(scope.$index, scope.row)"
                            >添加物料</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination
                        :page-size="5"
                        @current-change="handleCurrentChange"
                        :current-page.sync="materialPage"
                        layout="total, prev, pager, next"
                        :total="pages"
                    ></el-pagination>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="materialFormVisible = false">关 闭</el-button>
                </span>
            </div>
        </el-dialog>
        <el-dialog
            title="选择验收标准"
            @close="accpepClose"
            :visible.sync="accpeptFormVisible"
            width="30%"
        >
            <el-form ref="selectAccpeptForm" :model="selectAccpeptForm" label-width="100px">
                <el-form-item label="验收编号:" prop="id">
                    <el-select
                        v-model="selectAccpeptForm.id"
                        placeholder="请选择"
                        @change="selectAccpeptById($event)"
                    >
                        <el-option
                            v-for="item in this.accpeptList"
                            :key="item.id"
                            :label="item.id"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="验收名称:" prop="name">
                    <el-select
                        v-model="selectAccpeptForm.name"
                        placeholder="请选择"
                        @change="selectAccpeptByName($event)"
                    >
                        <el-option
                            v-for="item in this.accpeptList"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name"
                        ></el-option>
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
        <el-dialog
            title="选择图纸"
            @close="drawingClose"
            :visible.sync="drawingFormVisible"
            width="60%"
        >
            <div v-loading="drawingLoading">
                <el-form
                    @keyup.enter.native="searchEnterFun1"
                    :inline="true"
                    ref="selectDrawingForm"
                    label-position="left"
                    :model="selectDrawingForm"
                >
                    <el-form-item label="图号:" prop="drawingCode">
                        <el-input v-model="search2.drawingCode"></el-input>
                    </el-form-item>
                    <el-form-item label="图纸名称:" prop="name">
                        <el-input v-model="search2.name"></el-input>
                    </el-form-item>
                    <el-button round @click="searchAll1()">查询</el-button>
                </el-form>
                <el-table :data="drawingList2" border style="width:100%" height="300">
                    <el-table-column label="图号" prop="drawingCode"></el-table-column>
                    <el-table-column label="图纸名称" prop="name"></el-table-column>
                    <el-table-column label="图纸说明" prop="drawingRemark"></el-table-column>
                    <el-table-column label="工艺说明" prop="technologyRemark"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                @click="addDrawing(scope.$index, scope.row)"
                            >选择图纸</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="pagination">
                    <el-pagination
                        :page-size="5"
                        @current-change="handleCurrentChange1"
                        :current-page.sync="drawingPage"
                        layout="total, prev, pager, next"
                        :total="pages1"
                    ></el-pagination>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="drawingFormVisible = false">关 闭</el-button>
                </span>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            equipmentOptions: [
            ],
            drawingOptions: [
            ],
            workflowSuspended: false,
            pages: 1,
            pages1: 1,
            processInfo: [],
            oldProcessInfo: [],
            processInfoTableHead: [],
            materialInfo: [],
            materialInfoTable: [],
            accpeptInfo: [],
            drawingInfo: [],
            dynamicParameterName: [],
            selectParamNameForm: {},
            selectProcessForm: [],
            materialLoading: false,
            drawingLoading: false,
            selectMaterialForm: {
                quantity: "",
                materialBomParamValueStr: ""
            },
            selectDrawingForm: {
                drawingCode: "",
                name: "",
                drawingRemark: "",
                technologyRemark: "",
                owner: "",
                checkName: "",
                cadFile: "",
                pdfFile: "",
                technologyFile: ""
            },
            selectAccpeptForm: {
                id: "",
                name: "",
                owner: ""
            },
            paramNameList: "",
            processList: [],
            materialList: [],
            materialList1: [],
            materialList2: [],
            drawingList: [],
            drawingList1: [],
            drawingList2: [],
            accpeptList: [],
            accpeptList1: [],
            jobGuideList: [],
            equipmentList: [],
            accpept: [],
            parameterTable: [],
            local: true,
            accpeptTable: true,
            drawingTable: true,
            materialFormVisible: false,
            drawingFormVisible: false,
            accpeptFormVisible: false,
            editFlag: true,
            id2: "",
            form: {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialUnit: "",
                checkId: "",
                drawingId: "",
                mapList: []
            },
            search: {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialBomParamValueStr: "",
                pageNum: 1,
                pageSize: 5,
                auditingStatus: 0
            },
            addMaterialListSearch: {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialBomParamValueStr: "",
                pageNum: 1,
                pageSize: 5,
                auditingStatus: 0
            },
            materialPage: 1,
            search2: {
                drawingCode: "",
                name: "",
                pageNum: 1,
                pageSize: 5
            },
            drawingPage: 1,
            rules: {
                materialName: [
                    {
                        required: true,
                        message: "请输入物料名称",
                        trigger: "blur"
                    }
                ]
            },
            loading: false,
            oldProcessId: "",
            stepFlag: [
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                "H",
                "I",
                "J",
                "K",
                "L",
                "M",
                "N",
                "O",
                "P",
                "Q",
                "R",
                "S",
                "T",
                "U",
                "V",
                "W",
                "X",
                "Y",
                "Z"
            ],
        };
    },
    created() {
        this.getData();
    },
    computed: {},
    methods: {
        notMinus(e) {
        },
        handleSelectMaterial(val) {
            if (val.length) {
                for (let i = 0; i < this.processInfo.length; i++) {
                    for (let j = 0; j < val.length; j++) {
                        if (this.processInfo[i].id === val[j].id) {
                            this.processInfo[i].checkStatus = 1;
                            this.form.processInfo[i].checkStatus = 1;
                        }
                    }
                }
            } else {
                for (let i = 0; i < this.processInfo.length; i++) {
                    this.processInfo[i].checkStatus = 0;
                    this.form.processInfo[i].checkStatus = 0;
                }
            }
        },
        searchEnterFun(e) {
            let keyCode = window.event ? e.keyCode : e.which;
            if (keyCode == 13) {
                this.searchAll();
            }
        },
        searchEnterFun1(e) {
            let keyCode = window.event ? e.keyCode : e.which;
            if (keyCode == 13) {
                this.searchAll1();
            }
        },
        materialClose() {
            this.search = {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialBomParamValueStr: "",
                flag: 0,
                pageNum: 1,
                pageSize: 5
            };
            this.materialPage = 1;
            this.materialInfoTable = [];
        },
        accpepClose() {
            this.selectAccpeptForm = {
                id: "",
                name: "",
                owner: ""
            };
            this.accpept = [];
        },
        drawingClose() {
            this.search2 = {
                drawingCode: "",
                name: "",
                pageNum: 1,
                pageSize: 5
            };
            this.drawingPage = 1;
            this.drawingList2 = [];
        },
        searchAll() {
            this.search.pageNum = 1;
            this.selectLike();
        },
        searchAll1() {
            this.search2.pageNum = 1;
            this.selectLike1();
        },
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.selectLike();
        },
        handleCurrentChange1(val) {
            this.search2.pageNum = val;
            this.selectLike1();
        },
        getData() {
            if (this.$route.query.materialId != null) {
                this.search.id = this.$route.query.materialId;
                this.loading = true;
                this.$http.post('procedureEqui/searchList').then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.equipmentOptions = res.data.data
                    }
                })
                this.$http.post('productDrawing/searchDrawingList').then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.drawingOptions = res.data.data
                    }
                })
                this.$http
                    .post("/materialInfo/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.form = res.data.data;
                            this.selectProcessForm.processName =
                                res.data.data.processName;
                            if (this.form.source == "外购") {
                                this.local = false;
                            }
                            this.oldProcessId = this.form.processId;
                            this.$http
                                .post("/materialProcess/detail", this.search)
                                .then(res => {
                                    this.processInfo = [];
                                    this.form.processInfo = [];
                                    if (
                                        res != undefined &&
                                        res.data.code == 1000
                                    ) {
                                        for (
                                            let i = 0;
                                            i < res.data.data.length;
                                            i++
                                        ) {
                                            var json = JSON.parse(
                                                res.data.data[i].jsonParam
                                            );
                                            json["id"] = res.data.data[i].id;
                                            if (json.checkStatus == 1) {
                                                this.$nextTick(() => {
                                                    this.$refs.processInfoTable.toggleRowSelection(
                                                        this.processInfo[i]
                                                    );
                                                });
                                            } else {
                                                json.checkStatus = 0;
                                            }
                                            this.processInfo.push(json);

                                            this.form.processInfo.push(json);
                                            this.oldProcessInfo = this.form.processInfo;
                                        }
                                        if (this.form.source == "自制") {
                                            if (res.data.data.length != 0) {
                                                this.processContentJson =
                                                    res.data.data[0].jsonParam;
                                                this.loading = false;
                                            }
                                            // else {
                                            this.$http
                                                .post("/process/searchList")
                                                .then(res => {
                                                    if (
                                                        res != undefined &&
                                                        res.data.code == 1000
                                                    ) {
                                                        this.processList =
                                                            res.data.data;
                                                        for (
                                                            var i = 0;
                                                            i <
                                                            this.processList
                                                                .length;
                                                            i++
                                                        ) {
                                                            if (
                                                                this
                                                                    .selectProcessForm
                                                                    .processName ==
                                                                this
                                                                    .processList[
                                                                    i
                                                                ].processName
                                                            ) {
                                                                this.processInfoTableHead = [];
                                                                this.processContentJson = this.processList[
                                                                    i
                                                                ].contentJson;
                                                                for (
                                                                    var j = 0;
                                                                    j <
                                                                    this
                                                                        .processList[
                                                                        i
                                                                    ]
                                                                        .processFormatInfoList
                                                                        .length;
                                                                    j++
                                                                ) {
                                                                    this.processInfoTableHead.push(
                                                                        this
                                                                            .processList[
                                                                            i
                                                                        ]
                                                                            .processFormatInfoList[
                                                                            j
                                                                        ]
                                                                    );
                                                                }
                                                                this.form.processId = this.processList[
                                                                    i
                                                                ].id;
                                                            }
                                                        }
                                                    }
                                                    this.loading = false;
                                                })
                                                .catch(err => {
                                                    this.loading = false;
                                                });
                                            // }
                                        }
                                        this.loading = false;
                                    }
                                });
                        }
                    });
                this.$http
                    .post("/materialCheck/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.accpeptInfo = [];
                            for (var i = 0; i < res.data.data.length; i++) {
                                this.accpeptInfo.push(
                                    res.data.data[i].productAcceptanceInfo
                                );
                                this.form.checkId = res.data.data[0].checkId;
                            }
                        }
                    });
                this.$http
                    .post("/materialDrawing/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.drawingInfo = [];
                            for (var i = 0; i < res.data.data.length; i++) {
                                this.drawingInfo.push(
                                    res.data.data[i].productDrawingInfo
                                );
                                this.form.drawingId =
                                    res.data.data[0].drawingId;
                            }
                        }
                    });
                this.$http
                    .post("/materialRelation/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.materialInfo = [];
                            this.form.mapList = [];
                            for (var i = 0; i < res.data.data.length; i++) {
                                //给对象添加新属性
                                this.$set(
                                    res.data.data[i].materialBomInfo,
                                    "materialBomParamValueStr",
                                    res.data.data[i].materialBomParamValueStr
                                );
                                this.$set(
                                    res.data.data[i].materialBomInfo,
                                    "quantity",
                                    res.data.data[i].quantity
                                );
                                this.materialInfo.push(
                                    res.data.data[i].materialBomInfo
                                );
                                this.form.mapList.push({
                                    id: res.data.data[i].materialChildId,
                                    quantity: res.data.data[i].quantity
                                });
                            }
                        }
                    });

                this.$http
                    .post("/materialBomParamName/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.dynamicParameterName = [];
                            for (var i = 0; i < res.data.data.length; i++) {
                                this.dynamicParameterName.push(
                                    res.data.data[i]
                                );
                            }
                        }
                    });
                this.$http
                    .post("/processFormat/searchByMaterialId", this.search)
                    .then(res => {
                        this.processInfoTableHead = [];
                        if (res != undefined && res.data.code == 1000) {
                            this.processInfoTableHead = res.data.data;
                        }
                    });
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
            this.$http
                .post("/bpm/models/getStatus", {
                    workflowKey: "MaterialBomReview"
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        if (res.data.data.isSuspended == null) {
                            this.workflowSuspended = true;
                        } else {
                            this.workflowSuspended = res.data.data.isSuspended;
                        }
                    }
                });
        },
        selectLike() {
            let _this = this;
            _this.materialLoading = true;
            if (this.search.id != "") {
                this.id2 = this.search.id;
                this.search.id = "";
            }
            this.$http
                .post("/materialInfo/searchMaterialList", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.materialInfoTable = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.materialLoading = false;
                })
                .catch(err => {
                    _this.materialLoading = false;
                });
        },
        selectLike1() {
            let _this = this;
            _this.drawingLoading = true;
            this.$http
                .post("/productDrawing/searchDrawingPager", this.search2)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        _this.drawingList2 = res.data.data.list;
                        this.pages1 = res.data.data.total;
                    }
                    _this.drawingLoading = false;
                })
                .catch(err => {
                    _this.drawingLoading = false;
                });
        },
        addDrawing(index, row) {
            this.drawingInfo = [];
            this.drawingInfo.push(row);
            this.form.drawingId = row.id;
            this.drawingFormVisible = false;
        },
        addMaterial(index, row) {
            this.materialList2 = [];
            this.materialList2 = row;
            let flag = false;

            for (let i = 0; i < this.materialInfo.length; i++) {
                if (
                    this.materialList2.materialCode ==
                    this.materialInfo[i].materialCode
                ) {
                    this.materialList2 = [];
                    this.$message.warning("物料已存在");
                    flag = true;
                }
            }

            if (this.$route.query.materialId == row.id) {
                this.$message.warning("不能添加物料自身");
                flag = true;
            }

            if (flag === false) {
                this.materialInfo.push(this.materialList2);
            }

            if (this.materialInfo.length != 0) {
                this.form.mapList = [];
                for (let i = 0; i < this.materialInfo.length; i++) {
                    this.form.mapList.push({
                        id: this.materialInfo[i].id,
                        quantity: this.materialInfo[i].quantity
                    });
                }
            }
            this.materialFormVisible = true;
        },
        goBack() {
            this.$router.push({
                path: "/materialList",
                query: { closeFlag: 1 }
            });
        },
        save() {
            this.insert();
        },
        insert() {
            if (this.dynamicParameterName.length > 0) {
                this.form.dynamicParameterName = this.dynamicParameterName;
                for (
                    let i = 0;
                    i < this.form.dynamicParameterName.length;
                    i++
                ) {
                    if (
                        this.form.dynamicParameterName[i]
                            .productParameterNameInfo != null
                    ) {
                        this.form.dynamicParameterName[
                            i
                        ].productParameterNameInfo = null;
                    }
                }
            } else {
                this.form.dynamicParameterName = [];
            }
            if (this.form.mapList == null) {
                this.form.mapList = [];
            }
            if (this.form.source == "自制") {
                if (this.processInfo.length == 0) {
                    this.form.processInfo = [];
                    this.$message.warning("请至少添加一个制程");
                    return;
                } else {
                    this.processInfo.forEach((item, index) => {
                        item['工艺序号'] = this.stepFlag[index]
                    })
                    this.form.processInfo = this.processInfo;
                }
            }
            if (this.form.materialList == null) {
                this.form.materialList = [];
            }
            if (this.form.smiList == null) {
                this.form.smiList = [];
            }
            if (this.form.source == 1 && this.form.source == "外购") {
                this.form.mapList = [];
                this.form.processInfo = [];
            }
            if (this.oldProcessId == this.form.processId) {
                this.form.processInfo = this.processInfo;
            } else {
                for (let i = 0; i < this.oldProcessInfo.length; i++) {
                    this.$http
                        .post("/materialProcess/deleteProcess", {
                            id: this.oldProcessInfo[i].id
                        })
                        .then(res => {});
                }
            }
            let _this = this;
            if (this.form.mapList.length > 0) {
                let flag = true;
                this.form.mapList.forEach(item => {
                    if (!item.quantity) {
                        flag = false;
                        this.$message.warning("请输入物料数量");
                    }
                });
                if (flag == false) {
                    return;
                }
            }
            // this.loading = true
            //this.form.auditingStatus = 2;
            console.log(this.form);
            this.$http
                .post("/materialInfo/updateMaterial", this.form)
                .then(res => {
                    if (res.data.code == 1000) {
                        this.$message.success("修改成功");
                        //跳转到列表页面
                        this.$router.push({
                            path: "/materialList",
                            query: { closeFlag: 1 }
                        });
                    } else {
                        this.$message.error(res.data.message);
                    }
                    this.loading = false;
                })
                .catch(err => {
                    this.loading = false;
                });
        },
        addParameterName() {
            let param = {
                materialParamNameValue: ""
            };
            this.dynamicParameterName.push(param);
        },
        selectProcessById(value) {
            this.form.processId = "";
            for (var i = 0; i < this.processList.length; i++) {
                if (value == this.processList[i].processName) {
                    this.processInfoTableHead = [];
                    this.processContentJson = this.processList[i].contentJson;
                    for (
                        var j = 0;
                        j < this.processList[i].processFormatInfoList.length;
                        j++
                    ) {
                        this.processInfoTableHead.push(
                            this.processList[i].processFormatInfoList[j]
                        );
                    }
                    this.form.processId = this.processList[i].id;
                }
            }
            this.processInfo = [];
        },
        selectMaterial() {
            this.materialFormVisible = true;
            this.searchAll();
        },
        selectMaterialById(value) {
            for (var i = 0; i < this.materialList.length; i++) {
                if (value == this.materialList[i].materialCode) {
                    this.materialList1 = [];
                    this.selectMaterialForm.materialName = this.materialList[
                        i
                    ].materialName;
                    this.selectMaterialForm.materialUnit = this.materialList[
                        i
                    ].materialUnit;
                    this.selectMaterialForm.source = this.materialList[
                        i
                    ].source;
                    this.selectMaterialForm.materialBomParamValueStr = this.materialList[
                        i
                    ].materialBomParamValueStr;
                    this.materialList1.push(this.materialList[i]);
                }
            }
        },
        selectMaterialByName(value) {
            for (var i = 0; i < this.materialList.length; i++) {
                if (value == this.materialList[i].materialName) {
                    this.materialList1 = [];
                    this.selectMaterialForm.materialCode = this.materialList[
                        i
                    ].materialCode;
                    this.selectMaterialForm.materialUnit = this.materialList[
                        i
                    ].materialUnit;
                    this.selectMaterialForm.source = this.materialList[
                        i
                    ].source;
                    this.selectMaterialForm.materialBomParamValueStr = this.materialList[
                        i
                    ].materialBomParamValueStr;
                    this.materialList1.push(this.materialList[i]);
                }
            }
        },
        appendMaterial() {
            this.materialList2 = this.materialList1;
            for (var i = 0; i < this.materialList2.length; i++) {
                this.materialList2[
                    i
                ].quantity = this.selectMaterialForm.quantity;
                this.materialInfo.push(this.materialList2[i]);
            }
            if (this.materialInfo.length != 0) {
                this.form.mapList = [];
                for (var i = 0; i < this.materialInfo.length; i++) {
                    this.form.mapList.push({
                        id: this.materialInfo[i].id,
                        quantity: this.materialInfo[i].quantity
                    });
                }
            }
            this.materialFormVisible = false;
        },
        selectAccpept() {
            this.accpeptFormVisible = true;
        },
        selectAccpeptById(value) {
            for (var i = 0; i < this.accpeptList.length; i++) {
                if (value == this.accpeptList[i].id) {
                    this.accpeptList1 = [];
                    this.selectAccpeptForm.name = this.accpeptList[i].name;
                    this.selectAccpeptForm.owner = this.accpeptList[i].owner;
                    this.accpept = this.accpeptList[
                        i
                    ].productAcceptanceDetailInfoList;
                    this.accpeptList1.push(this.accpeptList[i]);
                }
            }
        },
        selectAccpeptByName(value) {
            for (var i = 0; i < this.accpeptList.length; i++) {
                if (value == this.accpeptList[i].name) {
                    this.accpeptList1 = [];
                    this.selectAccpeptForm.id = this.accpeptList[i].id;
                    this.selectAccpeptForm.owner = this.accpeptList[i].owner;
                    this.accpept = this.accpeptList[
                        i
                    ].productAcceptanceDetailInfoList;
                    this.accpeptList1.push(this.accpeptList[i]);
                }
            }
        },
        appendAccpept() {
            this.accpeptInfo = this.accpeptList1;
            if (this.accpeptInfo.length > 0) {
                this.form.checkId = this.accpeptInfo[0].id;
            }
            this.accpeptFormVisible = false;
        },
        selectDrawing() {
            this.drawingFormVisible = true;
            this.searchAll1();
        },
        selectDrawingById(value) {
            for (var i = 0; i < this.drawingList.length; i++) {
                if (value == this.drawingList[i].id) {
                    this.drawingList1 = [];
                    this.selectDrawingForm.drawingCode = this.drawingList[
                        i
                    ].drawingCode;
                    this.selectDrawingForm.name = this.drawingList[i].name;
                    this.selectDrawingForm.drawingRemark = this.drawingList[
                        i
                    ].drawingRemark;
                    this.selectDrawingForm.technologyRemark = this.drawingList[
                        i
                    ].technologyRemark;
                    this.selectDrawingForm.owner = this.drawingList[i].owner;
                    this.selectDrawingForm.checkName = this.drawingList[
                        i
                    ].checkName;
                    this.selectDrawingForm.cadFile = this.drawingList[
                        i
                    ].cadFile;
                    this.selectDrawingForm.pdfFile = this.drawingList[
                        i
                    ].pdfFile;
                    this.selectDrawingForm.technologyFile = this.drawingList[
                        i
                    ].technologyFile;
                    this.drawingList1.push(this.drawingList[i]);
                }
            }
        },
        selectDrawingByName(value) {
            for (var i = 0; i < this.drawingList.length; i++) {
                if (value == this.drawingList[i].name) {
                    this.drawingList1 = [];
                    this.selectDrawingForm.id = this.drawingList[i].id;
                    this.selectDrawingForm.drawingCode = this.drawingList[
                        i
                    ].drawingCode;
                    this.selectDrawingForm.drawingRemark = this.drawingList[
                        i
                    ].drawingRemark;
                    this.selectDrawingForm.technologyRemark = this.drawingList[
                        i
                    ].technologyRemark;
                    this.selectDrawingForm.owner = this.drawingList[i].owner;
                    this.selectDrawingForm.checkName = this.drawingList[
                        i
                    ].checkName;
                    this.selectDrawingForm.cadFile = this.drawingList[
                        i
                    ].cadFile;
                    this.selectDrawingForm.pdfFile = this.drawingList[
                        i
                    ].pdfFile;
                    this.selectDrawingForm.technologyFile = this.drawingList[
                        i
                    ].technologyFile;
                    this.drawingList1.push(this.drawingList[i]);
                }
            }
        },
        appendDrawing() {
            this.drawingInfo = this.drawingList1;
            if (this.drawingInfo.length > 0) {
                this.form.drawingId = this.drawingInfo[0].id;
            }
            this.drawingFormVisible = false;
        },
        openTable(value) {
            if (value == 0 || value == 2) {
                this.local = true;
            } else {
                this.local = false;
                this.materialInfo = [];
            }
        },
        detail(index, row) {
            this.$router.push({
                path: "/materialSearch",
                query: {
                    materialId: row.id
                }
            });
        },
        removeMaterial(index, row) {
            this.materialInfo.splice(index, 1);
            if (this.id2 != "") {
                this.search.id = this.id2;
            }
            this.search.childMaterialId = row.id;
            this.$http
                .post("/materialRelation/deleteMaterial", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    }
                });
            this.form.mapList.splice(index, 1);
        },
        removeParamName(index, row) {
            this.dynamicParameterName.splice(index, 1);
            this.$http
                .post("/materialBomParamName/deleteParamName", { id: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    }
                });
        },
        removeAccpept(index, row) {
            this.accpeptInfo.splice(index, 1);
            if (this.id2 != "") {
                this.search.id = this.id2;
            }
            this.$http
                .post("/materialCheck/deleteCheck", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.form.checkId = ""
                    }
                });
        },
        removeDrawing(index, row) {
            this.drawingInfo.splice(index, 1);
            if (this.id2 != "") {
                this.search.id = this.id2;
            }
            this.$http
                .post("/materialDrawing/deleteDrawing", this.search)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.form.drawingId = ""
                        this.form.drawingCode = ""
                    }
                });
        },
        removeProcess(index, row) {
            this.processInfo.splice(index, 1);
            if (row.id != null) {
                this.$http
                    .post("/materialProcess/deleteProcess", { id: row.id })
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        }
                    });
            }
            this.form.processInfo = this.processInfo;
        },
        changeMapList(row, index) {
            for (var i = 0; i < this.form.mapList.length; i++) {
                if (this.form.mapList[i].id == row.id) {
                    this.form.mapList.splice(i, 1);
                    this.form.mapList.push({
                        id: row.id,
                        quantity: row.quantity
                    });
                }
            }
        },
        addProcess() {
            if (this.processContentJson) {
                var json = JSON.parse(this.processContentJson);
                json["id"] = 0;
                json.checkStatus = 0;
                json.price = 0;
                for (let key in json) {
                    if (key == "checkStatus" || key == "id") {
                        json[key] = 0;
                    } else if (key != "工艺序号") {
                        json[key] = "";
                    }
                }
            }

            this.processInfo.push(json);
            if (this.processInfo.length != 0) {
                this.form.processInfo = this.processInfo;
            }
        },
        stayAuditing() {
            this.form.auditingStatus = 2;
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.insert();
                }
            });
        },
        startAuditing() {
            this.form.auditingStatus = 1;
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.insert();
                }
            });
        },
        auditingPass() {
            this.form.auditingStatus = 0;
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.insert();
                }
            });
        }
    },
    watch: {
        $route(to, form) {
            if (to.path === "/materialEdit" && this.$route.query.works !== 1) {
                this.$refs["form"].resetFields();
                this.processInfo = [];
                this.dynamicParameterName = [];
                this.accpeptInfo = [];
                this.drawingInfo = [];
                this.materialInfo = [];
                this.local = true;
                this.form = {
                    id: "",
                    materialCode: "",
                    factoryMaterialCode: "",
                    materialName: "",
                    originalMaterial: "",
                    materialUnit: "",
                    number: "",
                    checkId: "",
                    drawingId: "",
                    processId: "",
                    mapList: [],
                    flag: "0",
                    source: "1"
                };
                this.getData();
            }
        }
    }
};
</script>
<style>
.materialadd hr {
    border-top: 1px;
}
.materialadd .marginTop {
    margin-top: 10px;
    margin-bottom: 5px;
}
.materialadd .marginBottom {
    margin-top: 5px;
    margin-bottom: 10px;
}
.materialadd .text {
    font-size: 12px;
    color: #606266;
    margin-right: 30px;
}
.materialadd .container .el-dialog .el-form .el-button {
    margin-bottom: 18px;
}
.materialadd .inputClass {
    height: 32px;
    line-height: 32px;
}
.technologyBox
    .el-table__header-wrapper
    .el-table__header
    .has-gutter
    tr
    .el-table-column--selection
    .cell
    .el-checkbox {
    display: none;
}
.technologyBox
    .el-table__header-wrapper
    .el-table__header
    .has-gutter
    tr
    .el-table-column--selection
    .cell:after {
    content: "完成检";
}
</style>