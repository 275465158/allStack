<template>
    <div class="editor-node-line">
        <span class="enl-title">节点操作</span>
        <el-button @click="delNode">删除当前节点</el-button>
        <!-- <el-table :data="outgoingTable">
            <el-table-column>
                <template slot-scope="scope">{{scope.row.startId}}===>{{scope.row.endId}}</template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="delLine(scope.row)">删除连接</el-button>
                </template>
            </el-table-column>
        </el-table>-->
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { uuid, nodeOffsetPoint } from "../../../../../common/utils";

export default {
    name: "EditorNodeProperty",
    data() {
        return {
            outgoingTable: [],
            vnode: "",
            stencil: "",
            lineInfo: "",
            nodeInfo: ""
        };
    },
    props: {
        option: { type: Object }
    },
    computed: {
        ...mapState("editor", [
            "nodeData",
            "lineData",
            "selectedNode",
            "stencilSet"
        ])
    },
    methods: {
        ...mapMutations("editor", [
            "DELETE_LINE",
            "DELETE_NODE",
            "UPDATE_LINE",
            "UPDATE_NODE",
            "UPDATE_SELECTED_NODE",
            "UPDATE_NODE_OUTGOING",
            "DELETE_NODE_OUTGOING"
        ]),
        delNode() {
            if (this.option != undefined) {
                let endPoint = this.nodeData[
                    this.lineData[this.option.outgoing[0].resourceId].endId
                ];

                for (let lineIdx in this.lineData) {
                    const { startId, endId } = this.lineData[lineIdx];
                    if (startId === this.option.id) {
                        this.DELETE_LINE(this.lineData[lineIdx]);
                    } else if (endId === this.option.id) {
                        this.UPDATE_LINE({
                            [this.lineData[lineIdx].lineId]: {
                                ...this.lineData[lineIdx],
                                endId: endPoint.resourceId
                            }
                        });
                        this.UPDATE_NODE({
                            [endPoint.resourceId]: {
                                ...this.nodeData[endPoint.resourceId],
                                top: this.option.top
                            }
                        });
                    }
                }
                this.DELETE_NODE(this.option);
                this.UPDATE_SELECTED_NODE("");
            }
        },
        addNode(command) {
            this.stencil = this.stencilSet[command];
            this.vnode = this.stencil.view;
        },
        getPointPosition(sourceId, targetId) {
            const sourceNode = this.nodeData[sourceId],
                targetNode = this.nodeData[targetId];

            return nodeOffsetPoint(
                { x: sourceNode.left, y: sourceNode.top },
                sourceNode.width != undefined ? sourceNode.width : 0,
                sourceNode.height != undefined ? sourceNode.height : 0,
                { x: targetNode.left, y: targetNode.top },
                targetNode.width != undefined ? targetNode.width : 0,
                targetNode.height != undefined ? targetNode.height : 0
            ).source;
        },
        delLine(row) {
            this.DELETE_LINE(row);
        },
        getData() {
            this.outgoingTable = [];
            let outGoingResourceIds = this.option.outgoing;
            for (let resIdx in outGoingResourceIds) {
                for (let lineIdx in this.lineData) {
                    if (
                        this.lineData[lineIdx].resourceId ==
                        outGoingResourceIds[resIdx].resourceId
                    ) {
                        this.outgoingTable.push(this.lineData[lineIdx]);
                    }
                }
            }
        },
        updateTree(startLine) {
            let startNode = this.nodeData[
                this.lineData[startLine.resourceId].startId
            ];
            let nodeMoveWight = startNode.height;
            this.lineData[startLine.resourceId].outgoing.forEach(nodeIds => {
                let top =
                    this.nodeData[nodeIds.resourceId].top + nodeMoveWight + 80;
                this.UPDATE_NODE({
                    [nodeIds.resourceId]: {
                        ...this.nodeData[nodeIds.resourceId],
                        top: top
                    }
                });

                let startPosition = {
                    ...this.getPointPosition(
                        this.lineData[startLine.resourceId].startId,
                        nodeIds.resourceId
                    )
                };

                this.UPDATE_LINE({
                    [startLine.resourceId]: {
                        ...this.lineData[startLine.resourceId],
                        startPosition: startPosition,
                        endPosition: {
                            ...this.getPointPosition(
                                nodeIds.resourceId,
                                this.lineData[startLine.resourceId].startId
                            )
                        }
                    }
                });
                this.nodeData[nodeIds.resourceId].outgoing.forEach(lineIds => {
                    console.log(lineIds);
                    this.updateTree(this.lineData[lineIds.resourceId]);
                });
            });
        },
        insertNode(startNode, endNode) {
            let top =
                this.nodeData[endNode.resourceId].top +
                this.nodeData[endNode.resourceId].height +
                60;
            startNode.outgoing.forEach(lineId => {
                this.DELETE_NODE_OUTGOING({
                    [startNode.resourceId]: {
                        outgoing: { resourceId: lineId.resourceId }
                    }
                });
                this.UPDATE_NODE_OUTGOING({
                    [endNode.resourceId]: {
                        outgoing: { resourceId: lineId.resourceId }
                    }
                });
                this.UPDATE_LINE({
                    [lineId.resourceId]: {
                        ...this.lineData[lineId.resourceId],
                        startId: endNode.resourceId,
                        startPosition: {
                            ...this.getPointPosition(
                                startNode.resourceId,
                                endNode.resourceId
                            )
                        }
                    }
                });
                if (this.lineData[lineId.resourceId].outgoing.length > 0) {
                    this.updateTree(this.lineData[lineId.resourceId]);
                }
            });
        }
    },
    mounted() {
        this.getData();
    },
    watch: {
        option(curVal, oldVal) {
            this.getData();
        },
        lineData(curVal, oldVal) {
            this.getData();
        }
    }
};
</script>

<style lang="scss">
.editor-node-line {
    .enl-title {
        display: flex;
    }
}
</style>
