<template>
    <div class="container materialadd">
        <hr class="marginTop">
        <span class="text">新增物料</span>
        <hr class="marginBottom">
        <el-form
            v-loading="saveLoading"
            :model="form"
            ref="form"
            label-width="100px"
            :rules="rules"
        >
            <el-row :gutter="30">
                <el-col :span="6">
                    <el-form-item label="物料编号">
                        <el-input v-model="form.materialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="工厂物料编号" prop="factoryMaterialCode">
                        <el-input v-model="form.factoryMaterialCode"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料类型" prop="number">
                        <el-select v-model="form.number" placeholder="数据来自物料类型模块">
                            <el-option
                                v-for="item in productTypeList"
                                :key="item.number"
                                :label="item.type"
                                :value="item.number"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料名称" prop="materialName">
                        <el-input v-model="form.materialName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="原图材料" prop="originalMaterial">
                        <el-input v-model="form.originalMaterial"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="单位" prop="materialUnit">
                        <el-input v-model="form.materialUnit"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="来源:">
                        <el-select
                            v-model="form.source"
                            @change="openTable($event)"
                            placeholder="请选择"
                        >
                            <el-option value="0" label="自制"></el-option>
                            <el-option value="1" label="外购"></el-option>
                            <!--后期添加（自制/外购）这个选项 新增订单的时候也要判断来源是自制/外购这个状态下是自制还是外购-->
                            <!--<el-option value="2"  label="自制/外购"></el-option>-->
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom">
            <span class="text">参数</span>
            <el-button v-if="editFlag" size="small" @click="addParameterName()">添加参数</el-button>
            <el-table :data="dynamicParameterName" border style="width:100%" height="250">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
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
            <hr class="marginBottom">
            <div v-show="local" class="technologyBox">
                <span class="text">工艺流程</span>
                <br>
                <span class="text">工艺名称:</span>
                <el-select
                    v-model="selectProcessForm.processName"
                    placeholder="数据来自工艺设置模块"
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
                                filterable
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
                                filterable
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
                    <el-table-column align="center" width="70" type="selection"></el-table-column>
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
                    <el-table-column label="序号" prop="id">
                        <template slot-scope="scope">{{scope.$index+1}}</template>
                    </el-table-column>
                    <el-table-column label="物料编号" prop="materialCode"></el-table-column>
                    <el-table-column label="物料名称" prop="materialName"></el-table-column>
                    <el-table-column label="原图材料" prop="originalMaterial"></el-table-column>
                    <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column label="单位" prop="materialUnit"></el-table-column>
                    <el-table-column label="数量" prop="quantity">
                        <template slot-scope="scope">
                            <el-input
                                type="number"
                                onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
                                v-model.number="scope.row.quantity"
                                :min="0"
                                v-on:input="changeMapList(scope.row,scope.$index)"
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="detail(scope.$index, scope.row)">明细</el-button>
                            <el-button
                                type="text"
                                @click="removeMaterial(scope.$index, scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom">
            <input
                id="accpeptInfoId"
                v-model="checkv"
                type="checkbox"
                @change="changeAccpeptTable($event)"
            >
            <span class="text">验收标准</span>
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
            <input
                id="drawingId"
                type="checkbox"
                v-model="drawingCheck"
                @change="changeDrawingTable($event)"
            >
            <span class="text">图纸</span>
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
            <el-button @click="goBack">返回</el-button>
            <el-button
                round
                type="primary"
                @click="auditingPass"
                v-show="workflowSuspended==true"
            >保存</el-button>
            <el-dropdown
                split-button
                @command="review"
                @click="stayAuditing"
                v-show="workflowSuspended==false"
            >
                保存
                <el-dropdown-menu slot="dropdown">
                    <!-- <el-dropdown-item command="stayAuditing">待审核</el-dropdown-item> -->
                    <el-dropdown-item command="startAuditing">启动审批</el-dropdown-item>
                    <el-dropdown-item command="auditingPass">直接通过</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-form>
        <!-- 编辑弹出框 -->
        <el-dialog
            title="选择物料"
            @close="materialClose"
            :current-page="materialPage"
            :visible.sync="materialFormVisible"
            width="60%"
        >
            <div v-loading="materialLoading">
                <el-form
                    @keyup.enter.native="searchEnterFun"
                    :inline="true"
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
                                oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                                v-model.number="scope.row.quantity"
                                :min="0"
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
        <el-dialog title="选择状态" :visible.sync="selectedAuditingVisible" style="width:100%">
            <el-button round @click="stayAuditing">待审核</el-button>
            <el-button round @click="startAuditing">启动审批</el-button>
            <el-button round @click="auditingPass">直接通过</el-button>
        </el-dialog>
    </div>
</template>

<script>
function filter(e) {
    return;
}
export default {
    data() {
        return {
            equipmentOptions: [
            ],
            drawingOptions: [
            ],
            workflowSuspended: false,
            processInfo: [],
            processInfoTableHead: [],
            materialInfo: [],
            materialInfoTable: [],
            accpeptInfo: [],
            drawingInfo: [],
            selectParamSpecificationForm: {
                paramType: ""
            },
            pages: 1,
            pages1: 1,
            dynamicParameterName: [],
            selectParamNameForm: {},
            selectProcessForm: [],
            selectMaterialForm: {
                quantity: "",
                materialBomParamNameStr: "",
                materialBomParamValueStr: ""
            },
            selectDrawingForm: {
                drawingCode: "",
                id: "",
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
            paramSpecificationList: [],
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
            processContentJson: "",
            productTypeList: [],
            local: false,
            accpeptTable: false,
            drawingTable: false,
            materialFormVisible: false,
            drawingFormVisible: false,
            accpeptFormVisible: false,
            editFlag: true,
            form: {
                id: "",
                source: "",
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
                checkAuditingStatus: 0
            },
            search: {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialBomParamValueStr: "",
                auditingStatus: 0,
                flag: 0,
                pageNum: 1,
                pageSize: 5
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
                        message: "请选择物料类型",
                        trigger: "blur"
                    }
                ],
                number: [
                    {
                        required: true,
                        message: "请输入物料名称",
                        trigger: "change"
                    }
                ],
                originalMaterial: [
                    {
                        required: true,
                        message: "请输入源于材料",
                        trigger: "blur"
                    }
                ],
                materialUnit: [
                    { required: true, message: "请输入单位", trigger: "blur" }
                ]
            },
            materialLoading: false,
            drawingLoading: false,
            saveLoading: false,
            checkv: false,
            drawingCheck: false,
            selectedAuditingVisible: false,
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
            ]
        };
    },
    created() {
        this.getData();
    },
    computed: {},
    methods: {
        handleSelectMaterial(val) {
            for (let i = 0; i < this.processInfo.length; i++) {
                for (let j = 0; j < val.length; j++) {
                    if (this.processInfo[i].id === val[j].id) {
                        this.processInfo[i].checkStatus = 1;
                    }
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
        accpepClose() {
            this.selectAccpeptForm = {
                id: "",
                name: "",
                owner: ""
            };
            this.accpept = [];
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
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.selectLike();
        },
        handleCurrentChange1(val) {
            this.search2.pageNum = val;
            this.selectLike1();
        },
        searchAll() {
            this.search.pageNum = 1;
            this.selectLike();
        },
        searchAll1() {
            this.search2.pageNum = 1;
            this.selectLike1();
        },
        getData() {
            this.form.source = "1";
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
            this.$http.post("/process/searchList").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.processList = res.data.data;
                    if (this.processList.length != 0) {
                        for (var i = 0; i < this.processList.length; i++) {
                            if (this.processList[i].defaultProcess == 1) {
                                this.selectProcessForm.processName = this.processList[i].processName;
                                this.form.processId = this.processList[i].id;
                                this.processInfoTableHead = [];
                                this.processContentJson = this.processList[i].contentJson;
                                for (
                                    var j = 0;
                                    j <
                                    this.processList[i].processFormatInfoList
                                        .length;
                                    j++
                                ) {
                                    this.processInfoTableHead.push(
                                        this.processList[i]
                                            .processFormatInfoList[j]
                                    );
                                }
                            } else {
                                this.selectProcessForm.processName = ''
                            }
                        }
                    }
                }
            });
            this.$http
                .post("/productParameterSpecification/searchList")
                .then(res => {
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
                    if (this.productTypeList.length != 0) {
                        this.form.number = this.productTypeList[0].number;
                    }
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
            this.search.auditingStatus = 0;
            let _this = this;
            _this.materialLoading = true;
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
        addMaterial(index, row) {
            this.materialList2 = [];
            this.materialList2 = row;
            if (this.materialInfo.length != 0) {
                for (var i = 0; i < this.materialInfo.length; i++) {
                    if (
                        this.materialList2.materialCode ==
                        this.materialInfo[i].materialCode
                    ) {
                        this.materialList2 = [];
                        this.$message.warning("物料已存在");
                    }
                }
            }
            if (this.materialList2.length != 0) {
                this.materialInfo.push(this.materialList2);
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
            this.materialFormVisible = true;
        },
        addDrawing(index, row) {
            this.drawingInfo = [];
            this.drawingInfo.push(row);
            this.form.drawingId = row.id;
            this.drawingFormVisible = false;
        },
        goBack() {
            this.$refs["form"].resetFields();
            this.form.materialCode = "";
            this.form.factoryMaterialCode = "";
            this.form.originalMaterial = "";
            this.form.materialUnit = "";
            this.$router.push({
                path: "/materialList",
                query: {
                    closeFlag: 1
                }
            });
        },
        save() {
            this.selectedAuditingVisible = true;
        },
        insert() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.dynamicParameterName.length > 0) {
                        this.form.dynamicParameterName = this.dynamicParameterName;
                    } else {
                        this.form.dynamicParameterName = [];
                    }
                    if (this.form.source == 0) {
                        if (this.processInfo.length > 0) {
                            this.processInfo.forEach((item, index) => {
                                item['工艺序号'] = this.stepFlag[index]
                            })
                            this.form.processInfo = this.processInfo;
                        } else {
                            this.$message.warning("请添加至少一个制程");
                            return;
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
                    this.saveLoading = true;
                    if (this.form.source == 1) {
                        this.form.processId = "";
                    }
                    this.$http
                        .post("/materialInfo/saveMaterial", this.form)
                        .then(res => {
                            if (res.data.code == 1000) {
                                _this.$message.success("添加成功");
                                //跳转到列表页面
                                this.$router.push({
                                    path: "/materialList",
                                    query: { closeFlag: 1 }
                                });
                                this.selectedAuditingVisible = false;
                            } else {
                                _this.$message.error(res.data.message);
                            }
                            this.saveLoading = false;
                        })
                        .catch(error => {
                            this.saveLoading = false;
                        });
                } else {
                    this.$message.warning("请输入输入框中的必填项！");
                    return false;
                }
            });
        },
        addParameterName() {
            let param = {
                materialParamNameValue: ""
            };
            this.dynamicParameterName.push(param);
        },
        addProcess() {
            var json = JSON.parse(this.processContentJson);
            json["id"] = this.processInfo.length + 1;
            json["checkStatus"] = 0;
            json.price = 0;
            this.processInfo.push(json);
            json["checkStatus"] &&
                this.$refs.processInfoTable.toggleRowSelection(json);
        },
        removeParamName(index, row) {
            this.dynamicParameterName.splice(index, 1);
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
            this.selectLike1();
            this.drawingFormVisible = true;
        },
        openTable(value) {
            if (value == 0 || value == 2) {
                this.local = true;
            } else {
                this.local = false;
                this.materialInfo = [];
            }
        },
        changeAccpeptTable() {
            var checked = document.getElementById("accpeptInfoId");
            if (checked.checked) {
                this.accpeptTable = true;
            } else {
                this.accpeptTable = false;
                this.accpeptInfo = [];
            }
        },
        changeDrawingTable() {
            var checked = document.getElementById("drawingId");
            if (checked.checked) {
                this.drawingTable = true;
            } else {
                this.drawingTable = false;
                this.drawingInfo = [];
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
            this.form.mapList.splice(index, 1);
        },
        removeAccpept(index, row) {
            this.accpeptInfo.splice(index, 1);
            this.form.checkId = "";
        },
        removeDrawing(index, row) {
            this.drawingInfo.splice(index, 1);
            this.form.drawingId = "";
        },
        removeProcess(index, row) {
            this.processInfo.splice(index, 1);
            this.form.processInfo.splice(index, 1);
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
            if(this.materialInfo.length == 0 && this.form.source == 0){
                this.$message.warning("请选择物料清单！");
            }else{
                this.form.auditingStatus = 0;
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        this.insert();
                    }
                });
            }

        },
        review(command) {
            switch (command) {
                case "stayAuditing":
                    this.stayAuditing();
                    break;
                case "startAuditing":
                    this.startAuditing();
                    break;
                case "auditingPass":
                    this.auditingPass();
                    break;
            }
        }
    },
    watch: {
        $route(to, form) {
            if (to.path === "/materialAdd" && this.$route.query.works !== 1) {
                this.$refs["form"].resetFields();
                this.processInfo = [];
                this.dynamicParameterName = [];
                this.accpeptInfo = [];
                this.drawingInfo = [];
                this.materialInfo = [];
                this.checkv = false;
                this.drawingCheck = false;
                this.accpeptTable = false;
                this.drawingTable = false;
                this.local = false;
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
