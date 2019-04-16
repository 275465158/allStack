<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true" :model="search" label-width="100px">
                <el-form-item label="模型名称">
                    <el-input v-model="search.modelName"/>
                </el-form-item>
                <el-button round @click="searchModel">查询</el-button>
            </el-form>
        </div>
        <div class="handle-box">
            <el-button round type="primary" @click="add">新增模型</el-button>
        </div>
        <el-table :data="tables" border style="width:100%">
            <el-table-column label="流程模型名称" prop="name"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="modify(scope.row)">设计流程</el-button>
                    <!-- <el-button @click="modifyProp(scope.row)">编辑</el-button> -->
                    <el-button @click="deploy(scope.row)" v-if="scope.row.isSuspended==false">启用</el-button>
                    <el-button
                        @click="suspendModel(scope.row)"
                        v-if="scope.row.isSuspended==true"
                    >停用</el-button>
                    <el-button @click="delModel(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                :page-size="20"
                @current-change="handleCurrentChange"
                layout="total, prev, pager,next"
                :total="pages"
            />
        </div>
        <!-- 新增模型 -->
        <el-dialog title="新增流程模型" :visible.sync="addVisible" width="50%">
            <el-form ref="addForm" :model="addForm" label-width="100px" :rules="rules">
                <el-form-item label="模型名称" prop="modelName">
                    <el-input v-model="addForm.modelName"></el-input>
                </el-form-item>
                <el-form-item label="所属模块" prop="formKey">
                    <el-select v-model="addForm.formKey" @change="formKeySelected">
                        <el-option
                            v-for="(item, index) in ecmsCompnonents"
                            :key="index"
                            :value="item.formKey"
                            :label="item.name"
                        >{{item.name}}</el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" v-model="addForm.desc"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible=false">取消</el-button>
                <el-button @click="addModel">下一步</el-button>
            </span>
        </el-dialog>

        <!-- 修改流程属性 -->
        <el-dialog title="编辑流程属性" :visible.sync="modifyProperty" width="50%">
            <el-form ref="propForm" :model="propForm" label-width="100px" :rules="propRules">
                <el-form-item label="模型名称" prop="modelName">
                    <el-input v-model="propForm.modelName"/>
                </el-form-item>
                <el-form-item label="所属模块" prop="formKey">{{propForm.formKey}}</el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" v-model="propForm.desc"></el-input>
                </el-form-item>
                <el-form-item label="任务名称模版">
                    <el-table :data="taskNameTemplates" border style="width:100%">
                        <el-table-column>
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.columnSelected"></el-checkbox>
                            </template>
                        </el-table-column>
                        <el-table-column label="字段名称" prop="columnTitle"></el-table-column>
                        <el-table-column label="显示顺序">
                            <template slot-scope="scope">
                                <el-button>上移</el-button>
                                <el-button>上移</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { uuid } from "../../../common/utils";
import axios from "axios";
export default {
    data() {
        return {
            addVisible: false,
            modifyProperty: false,
            search: {
                pageNum: 1
            },
            addForm: {
                modelName: "",
                modelKey: "",
                systemModel: "",
                desc: ""
            },
            propForm: {
                modelName: "",
                formKey: "",
                desc: "",
                taskNameTemplate: ""
            },
            taskNameTemplates: [],
            pages: 1,
            tableData: [],
            ecmsCompnonents: {},
            selectedComponent: {},
            rules: {
                modelName: [{ required: true, message: "请输入流程名称" }],
                formKey: [{ required: true, message: "请选择功能模块" }]
            },
            propRules: {},
            isAdmin: false
        };
    },
    created() {
        this.getData();
        this.getComponents();
    },
    computed: {
        ...mapState("editor", ["modelData", "nodeData", "lineData"]),
        tables() {
            return this.tableData.filter(d => {
                return d;
            });
        }
    },
    methods: {
        ...mapMutations("editor", [
            "UPDATE_MODEL",
            "UPDATE_NODE",
            "UPDATE_LINE",
            "CLEAR_MODEL",
            "CLEAR_NODE",
            "CLEAR_LINE"
        ]),
        formKeySelected(evt) {
            this.selectedComponent = evt;
            for (let cIndex in this.ecmsCompnonents) {
                if (this.ecmsCompnonents[cIndex].formKey == evt) {
                    this.selectedComponent = this.ecmsCompnonents[cIndex];
                }
            }
        },
        getComponents() {
            axios({
                method: "get",
                url: "static/js/components.json?ver=" + new Date().getTime()
            }).then(res => {
                if (res != undefined) {
                    let components = [];
                    for (let cIndex in res.data) {
                        components.push(res.data[cIndex]);
                    }
                    this.ecmsCompnonents = components;
                }
            });
        },
        getData() {
            this.$http.post("/bpm/models/listModel", this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                    this.pages = res.data.data.length;
                }
            });
            this.$http.post("/bpm/models/isAdmin", {}).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.isAdmin = res.data.data.isAdmin;
                }
            });
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        searchModel() {
            if (this.search.modelName != undefined) {
                this.$http
                    .post("/bpm/models/searchModel", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.tableData = res.data.data;
                            this.pages = res.data.data.length;
                        }
                    });
            } else {
                this.getData();
            }
        },
        add() {
            this.addVisible = true;
        },
        modifyProp(row) {
            this.modifyProperty = true;
            this.$http
                .post("/bpm/models/getModel", { modelId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        let metaInfo = JSON.parse(res.data.data.metaInfo);
                        this.propForm.modelName = res.data.data.modelName;
                        this.propForm.desc = metaInfo.description;
                        this.propForm.formKey = res.data.data.formKey;
                    }
                });
        },
        modify(row) {
            this.CLEAR_MODEL("");
            this.CLEAR_NODE("");
            this.CLEAR_LINE("");
            this.$http
                .post("/bpm/models/getModelJson", { modelId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        let modelJSON = JSON.parse(res.data.data);
                        let childShapes = modelJSON.childShapes;
                        this.UPDATE_MODEL({
                            ...this.modelData,
                            modelId: row.id,
                            properties: modelJSON.properties,
                            formData: modelJSON.formData
                        });

                        for (var shape in childShapes) {
                            let stencilId = childShapes[shape].stencil.id;
                            switch (stencilId) {
                                case "StartNoneEvent":
                                    this.createStartEvent(
                                        childShapes[shape].id,
                                        childShapes[shape].name,
                                        childShapes[shape].height,
                                        childShapes[shape].width,
                                        childShapes[shape].left,
                                        childShapes[shape].top,
                                        childShapes[shape].view,
                                        childShapes[shape].outgoing,
                                        childShapes[shape].property,
                                        childShapes[shape].text
                                    );
                                    break;
                                case "UserTask":
                                    this.createUsertask(
                                        childShapes[shape].id,
                                        childShapes[shape].name,
                                        childShapes[shape].height,
                                        childShapes[shape].width,
                                        childShapes[shape].left,
                                        childShapes[shape].top,
                                        childShapes[shape].view,
                                        childShapes[shape].outgoing,
                                        childShapes[shape].property,
                                        childShapes[shape].text,
                                        childShapes[shape].parentNodeId
                                    );
                                    break;
                                case "EndNoneEvent":
                                    this.createEndEvent(
                                        childShapes[shape].id,
                                        childShapes[shape].name,
                                        childShapes[shape].height,
                                        childShapes[shape].width,
                                        childShapes[shape].left,
                                        childShapes[shape].top,
                                        childShapes[shape].view,
                                        childShapes[shape].outgoing,
                                        childShapes[shape].property,
                                        childShapes[shape].text,
                                        childShapes[shape].parentNodeId
                                    );
                                    break;
                                case "SequenceFlow":
                                    this.createSequenceFlow(
                                        childShapes[shape].resourceId,
                                        childShapes[shape].startId,
                                        childShapes[shape].startPosition,
                                        childShapes[shape].endId,
                                        childShapes[shape].endPosition,
                                        childShapes[shape].outgoing,
                                        childShapes[shape].conditionStr,
                                        childShapes[shape].conditionJSON
                                    );
                                    break;
                                case "ExclusiveGateway":
                                    this.createExclusive(
                                        childShapes[shape].id,
                                        childShapes[shape].name,
                                        childShapes[shape].height,
                                        childShapes[shape].width,
                                        childShapes[shape].left,
                                        childShapes[shape].top,
                                        childShapes[shape].view,
                                        childShapes[shape].outgoing,
                                        childShapes[shape].property,
                                        childShapes[shape].text,
                                        childShapes[shape].parentNodeId
                                    );
                                    break;
                            }
                        }
                        this.$router.push("/flow");
                    }
                });
        },
        createStartEvent(
            resourceId,
            name,
            height,
            width,
            left,
            top,
            view,
            outgoing,
            property,
            text
        ) {
            let id = resourceId;
            this.UPDATE_NODE({
                [id]: {
                    id: resourceId,
                    resourceId: resourceId,
                    name: name,
                    stencil: { id: "StartNoneEvent" },
                    outgoing: outgoing,
                    view: view,
                    property: property,
                    top: top,
                    left: left,
                    text: text,
                    width: width,
                    height: height
                    //imgSrc
                }
            });
        },
        createEndEvent(
            resourceId,
            name,
            height,
            width,
            left,
            top,
            view,
            outgoing,
            property,
            text,
            parentNodeId
        ) {
            let id = resourceId;
            this.UPDATE_NODE({
                [id]: {
                    id: resourceId,
                    resourceId: resourceId,
                    name: name,
                    stencil: { id: "EndNoneEvent" },
                    outgoing: outgoing,
                    view: view,
                    property: property,
                    top: top,
                    left: left,
                    text: text,
                    width: width,
                    height: height,
                    parentNodeId: parentNodeId
                }
            });
        },
        createUsertask(
            resourceId,
            name,
            height,
            width,
            left,
            top,
            view,
            outgoing,
            property,
            text,
            parentNodeId
        ) {
            let id = resourceId;
            this.UPDATE_NODE({
                [id]: {
                    id: resourceId,
                    resourceId: resourceId,
                    name: name,
                    stencil: { id: "UserTask" },
                    outgoing: outgoing,
                    view: view,
                    property: property,
                    top: top,
                    left: left,
                    text: text,
                    width: width,
                    height: height,
                    parentNodeId: parentNodeId
                }
            });
        },
        createSequenceFlow(
            resourceId,
            startId,
            startPosition,
            endId,
            endPosition,
            outgoing,
            conditionStr,
            conditionJSON
        ) {
            this.UPDATE_LINE({
                [resourceId]: {
                    stencil: { id: "SequenceFlow" },
                    startId: startId,
                    startPosition: startPosition,
                    endId: endId,
                    endPosition: endPosition,
                    lineId: resourceId,
                    resourceId: resourceId,
                    outgoing: outgoing
                }
            });
        },
        createExclusive(
            resourceId,
            name,
            height,
            width,
            left,
            top,
            view,
            outgoing,
            property,
            text,
            parentNodeId
        ) {
            this.UPDATE_NODE({
                [resourceId]: {
                    id: resourceId,
                    resourceId: resourceId,
                    name: name,
                    stencil: { id: "ExclusiveGateway" },
                    outgoing: outgoing,
                    view: view,
                    property: property,
                    top: top,
                    left: left,
                    text: text,
                    width: width,
                    height: height,
                    parentNodeId: parentNodeId
                }
            });
        },
        design(row) {},
        delModel(row) {
            this.$http
                .post("/bpm/models/deleteModel", { modelId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        suspendModel(row) {
            this.$http
                .post("/bpm/models/suspendModel", { modelId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        deploy(row) {
            this.$http
                .post("/bpm/models/deploy", { modelId: row.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                    }
                });
        },
        addModel() {
            this.$refs["addForm"].validate(valid => {
                if (valid) {
                    this.CLEAR_MODEL("");
                    this.CLEAR_NODE("");
                    this.CLEAR_LINE("");
                    let startNodeId = "sid-" + uuid(),
                        endNodeId = "sid-" + uuid(),
                        lineId = "sid-" + uuid();
                    let data = {
                        ...data,
                        resourceId: uuid(),
                        properties: {
                            process_id: this.addForm.formKey,
                            name: this.addForm.modelName,
                            desc: this.addForm.desc,
                            taskNameRules: this.selectedComponent.taskNameRules
                        },
                        stencil: { id: "BPMDiagram" },
                        childShapes: [],
                        bounds: {
                            lowerRight: { x: 1200, y: 1050 },
                            upperLeft: { x: 0, y: 0 }
                        },
                        stencilset: {
                            url: "stencilsets/bpmn2.0/bpmn2.0.json",
                            namespace: "http://b3mn.org/stencilset/bpmn2.0#"
                        },
                        formData: this.selectedComponent,
                        ssextensions: []
                    };
                    this.UPDATE_MODEL(data);
                    this.createStartEvent(
                        startNodeId,
                        "StartNoneEvent",
                        30,
                        30,
                        600,
                        70,
                        '<svg\n   xmlns="http://www.w3.org/2000/svg"\n   xmlns:oryx="http://www.b3mn.org/oryx"\n   width="40"\n   height="40"\n   version="1.0">\n  <defs></defs>\n  <oryx:magnets>\n  \t<oryx:magnet oryx:cx="16" oryx:cy="16" oryx:default="yes" />\n  </oryx:magnets>\n  <g pointer-events="fill">\n    <circle id="bg_frame" cx="16" cy="16" r="15" stroke="#585858" fill="#ffffff" stroke-width="1"/>\n\t<text font-size="11" \n\t\tid="text_name" \n\t\tx="16" y="33" \n\t\toryx:align="top center" \n\t\tstroke="#373e48"\n\t></text>\n  </g>\n</svg>',
                        [{ resourceId: lineId }],
                        {},
                        ""
                    );
                    this.createEndEvent(
                        endNodeId,
                        "EndNoneEvent",
                        30,
                        30,
                        600,
                        200,
                        '<svg\n   xmlns="http://www.w3.org/2000/svg"\n   xmlns:oryx="http://www.b3mn.org/oryx"\n   width="40"\n   height="40"\n   version="1.0">\n  <defs></defs>\n  <oryx:magnets>\n  \t<oryx:magnet oryx:cx="16" oryx:cy="16" oryx:default="yes" />\n  </oryx:magnets>\n  <g pointer-events="fill">\n    <circle id="bg_frame" cx="16" cy="16" r="14" stroke="#585858" fill="#ffffff" stroke-width="3"/>\n\t<text font-size="11" \n\t\tid="text_name" \n\t\tx="16" y="32" \n\t\toryx:align="top center" \n\t\tstroke="#373e48"\n\t></text>\n  </g>\n</svg>',
                        [],
                        {},
                        "",
                        startNodeId
                    );
                    this.createSequenceFlow(
                        lineId,
                        startNodeId,
                        { x: 615, y: 75 },
                        endNodeId,
                        { x: 615, y: 175 },
                        [{ resourceId: endNodeId }],
                        {},
                        {}
                    );
                    this.addVisible = false;
                    this.$router.push("/flow");
                } else {
                    this.$message.error("带*为必填项");
                    return false;
                }
            });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/modelList") {
                this.getData();
            }
        }
    }
};
</script>

<style>
</style>
