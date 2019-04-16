<template>
    <div>
        <div>
            <el-form>
                <el-form-item label="节点名称">
                    <el-input v-model="nodeName"/>
                </el-form-item>
                <el-form-item
                    :label="'分支'+(index+1)+'的条件'"
                    v-for="(item, index) in outLine"
                    :key="index"
                >
                    <el-input v-model="item.conditionStr"/>
                    <el-button @click="addCondition($event,item,index)">添加条件</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-dialog :title="conditionName" :visible.sync="conditionVisible">
            <el-button @click="addCondItem">添加普通条件</el-button>
            <el-table :data="conditionTable" :span-method="objectSpanMethod">
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button small @click="delItem(scope.row, scope.$index)">删除条件</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="条件字段">
                    <template slot-scope="scope">
                        <el-select
                            v-model="scope.row.conditionColumn"
                            @change="columnSelected($event,scope.row)"
                        >
                            <el-option
                                v-for="(item, index) in conditionKeyData"
                                :key="index"
                                :value="item.columnName"
                                :label="item.label"
                            >{{item.label}}</el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="比较方式">
                    <template slot-scope="scope">
                        <el-select
                            v-model="scope.row.conditionMethod"
                            @change="methodSelected($event, scope.row)"
                        >
                            <el-option
                                v-for="(item, index) in condMethods"
                                :key="index"
                                :value="item.id"
                                :label="item.name"
                            >{{item.name}}</el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="结果">
                    <template slot-scope="scope">
                        <el-select
                            v-if="scope.row.showType=='select'"
                            v-model="scope.row.conditionValue"
                            @change="valueSelected($event, scope.row)"
                        >
                            <el-option
                                v-for="(item, index) in scope.row.conditionOptionData"
                                :key="index"
                                :value="item.id"
                                :label="item.name"
                            >{{item.name}}</el-option>
                        </el-select>
                        <el-input
                            v-if="scope.row.showType=='input'"
                            v-model="scope.row.conditionValue"
                        ></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="联合条件" v-if="conditionTable.length>1">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.conditionUnion">
                            <el-option
                                v-for="(item, index) in condUnions"
                                :key="index"
                                :value="item.id"
                                :label="item.name"
                            >{{item.name}}</el-option>
                        </el-select>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="conditionVisible=false">取消</el-button>
                <el-button @click="updateLineCondition">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { uuid } from "../../../../../common/utils";
export default {
    name: "EditorExclusiveProperty",
    data() {
        return {
            condForm: {
                conditionColumn: null
            },
            selectItem: null,
            selectedLine: {},
            conditionName: "",
            conditionVisible: false,
            conditionKeyData: null,
            conditionOptionData: [],
            conditionTable: [],
            condMethods: [
                { id: "eq", name: "等于" },
                //{ id: "eqig", name: "等于(忽略大小写)" },
                { id: "contain", name: "包含" },
                { id: "neq", name: "不等于" },
                { id: "lte", name: "小于等于" },
                { id: "lt", name: "小于" },
                { id: "gte", name: "大于等于" },
                { id: "gt", name: "大于" }
            ],
            condUnions: [{ id: "and", name: "与" }, { id: "or", name: "或" }]
        };
    },
    props: {
        option: {
            type: Object
        }
    },
    computed: {
        ...mapState("editor", [
            "nodeData",
            "lineData",
            "selectedNode",
            "modelData"
        ]),
        nodeName: {
            get: function() {
                return this.selectedNode.name;
            },
            set: function() {}
        },
        outLine() {
            let outline = [];
            for (let outIndex in this.selectedNode.outgoing) {
                for (let lineIndex in this.lineData) {
                    if (
                        this.selectedNode.outgoing[outIndex].resourceId ==
                        this.lineData[lineIndex].resourceId
                    ) {
                        outline.push(this.lineData[lineIndex]);
                    }
                }
            }
            return outline;
        }
    },
    methods: {
        ...mapMutations("editor", [
            "UPDATE_SELECTED_NODE",
            "UPDATE_NODE",
            "UPDATE_LINE"
        ]),
        delItem(row, index) {
            this.conditionTable.splice(index, 1);
            if (
                row.conditionRowId == row.conditionUnionId &&
                this.conditionTable.length > 0
            ) {
                let unionId = this.conditionTable[0].conditionRowId;
                for (let i = 0; i < this.conditionTable.length; i++) {
                    this.conditionTable[i].conditionUnionId = unionId;
                }
            }
        },
        columnSelected(evt, row) {
            this.conditionKeyData.forEach(keyData => {
                if (keyData.columnName == evt) {
                    row.conditionColumnLabel = keyData.label;
                    if (
                        keyData.options != undefined &&
                        keyData.options.length > 0
                    ) {
                        row.showType = "select";
                        row.conditionOptionData = keyData.options;
                    } else {
                        row.showType = "input";
                    }
                }
            });
        },
        methodSelected(evt, row) {
            let method = {};
            method = this.condMethods.find(item => {
                return item.id == evt;
            });
            row.conditionMethodLabel = method.name;
        },
        valueSelected(evt, row) {
            let s_value = {};
            s_value = row.conditionOptionData.find(item => {
                return item.id == evt;
            });
            row.conditionValueLabel = s_value.name;
        },
        addCondition(evt, item, index) {
            this.conditionName = "编辑分支" + (index + 1) + "的条件";
            this.conditionVisible = true;
            this.conditionKeyData = this.modelData.formData.keyData;
            this.selectedLine = item;
            if (item.conditionJSON != undefined && item.conditionJSON != "") {
                this.conditionTable = JSON.parse(item.conditionJSON);
            }
        },
        addCondItem() {
            let rowId = uuid();
            this.conditionTable.push({
                conditionRowId: rowId,
                conditionUnionId:
                    this.conditionTable.length > 0
                        ? this.conditionTable[0].conditionRowId
                        : rowId,
                conditionColumn: "",
                conditionColumnLabel: "",
                conditionMethod: "",
                conditionMethodLabel: "",
                conditionValue: "",
                conditionValueLabel: "",
                conditionUnion: "or",
                showType: ""
            });
        },
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            if (columnIndex === 4) {
                let rowNum = 0;
                for (let i = 0; i < this.conditionTable.length; i++) {
                    let elem = this.conditionTable[i];
                    if (elem.conditionUnionId == row.conditionRowId) {
                        rowNum++;
                    }
                }
                if (rowNum > 0) {
                    return {
                        rowspan: rowNum,
                        colspan: 1
                    };
                } else {
                    return {
                        rowspan: 0,
                        colspan: 0
                    };
                }
            }
        },
        updateLineCondition() {
            let conditionStr = "";
            this.conditionTable.forEach(condition => {
                conditionStr +=
                    "(" +
                    condition.conditionColumnLabel +
                    " " +
                    condition.conditionMethodLabel +
                    " " +
                    condition.conditionValueLabel +
                    ")";
                if (this.conditionTable.length > 1) {
                    conditionStr += condition.conditionUnion;
                }
            });
            // let lineSelected = this.outLine.find(l => {
            //     return l.resourceId == this.selectedLine.resourceId;
            // });
            // lineSelected.conditionStr = conditionStr;
            // lineSelected.conditionJSON = this.conditionTable;
            this.UPDATE_LINE({
                [this.selectedLine.resourceId]: {
                    ...this.lineData[this.selectedLine.resourceId],
                    conditionStr: conditionStr,
                    conditionJSON: JSON.stringify(this.conditionTable)
                }
            });
            this.conditionVisible = false;
        }
    }
};
</script>

<style lang="scss">
</style>
