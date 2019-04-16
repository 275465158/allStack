<template>
    <div style="width:120px">
        <el-button @click="addCondition">插入分支</el-button>
    </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";

import { uuid, nodeOffsetPoint } from "../../../../common/utils";

export default {
    name: "EditorGateControlNode",
    data() {
        return { vnode: "", stencil: "", nodeType: "" };
    },
    props: {
        option: { type: Object }
    },
    computed: {
        ...mapState("editor", ["lineData", "nodeData", "stencilSet"])
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
        addCondition() {
            let startNode = this.nodeData[this.option.startId];
            let { top, left, height, width } = this.nodeData[
                this.option.startId
            ];
            let curNodeCenterPosition = {
                x: left + width / 2,
                y: top + height / 2
            };
            let conditionSize =
                this.nodeData[this.option.startId].outgoing.length + 1;
            let centX = (conditionSize * 100 + (conditionSize - 1) * 140) / 2;

            for (let lineIdx in this.nodeData[this.option.startId].outgoing) {
                let nodeId = this.lineData[
                    this.nodeData[this.option.startId].outgoing[lineIdx]
                        .resourceId
                ].endId;
                let nodeLeft = curNodeCenterPosition.x - centX + 240 * lineIdx;
                this.UPDATE_NODE({
                    [nodeId]: {
                        ...this.nodeData[nodeId],
                        left: nodeLeft
                    }
                });
                this.getNodeChild(nodeId);
            }
            let userTaskStencil = this.stencilSet["UserTask"];
            let userTaskWidth = userTaskStencil.width,
                userTaskHeight = userTaskStencil.height;
            let userTaskRightNodeId = "sid-" + uuid();
            //插入右侧任务节点
            this.UPDATE_NODE({
                [userTaskRightNodeId]: {
                    id: userTaskRightNodeId,
                    resourceId: userTaskRightNodeId,
                    name: userTaskStencil.id,
                    stencil: { id: userTaskStencil.id },
                    outgoing: [],
                    view: userTaskStencil.view,
                    property: {
                        assignee: { id: "", name: "" },
                        assigneeGroup: { id: "", name: "" }
                    },
                    left:
                        curNodeCenterPosition.x -
                        centX +
                        240 * (conditionSize - 1),
                    top: curNodeCenterPosition.y + 180 - userTaskHeight / 2,
                    width: userTaskWidth,
                    height: userTaskHeight,
                    text: "",
                    parentNodeId: this.option.startId
                }
            });
            this.UPDATE_NODE({
                [this.option.startId]: {
                    ...this.nodeData[this.option.startId],
                    subNodeList: {
                        ...this.nodeData[this.option.startId].subNodeList,
                        [userTaskRightNodeId]: {
                            resourceId: userTaskRightNodeId
                        }
                    }
                }
            });
            //添加右侧接入连线
            let userTaskRightIncomeLineId = this.addLine(
                this.option.startId,
                userTaskRightNodeId,
                "GatewayFlow"
            );

            this.UPDATE_NODE_OUTGOING({
                [this.option.startId]: {
                    outgoing: { resourceId: userTaskRightIncomeLineId }
                }
            });
            let userTaskRightOutgoingLineId = this.addLine(
                userTaskRightNodeId,
                startNode.endId,
                "SequenceFlow"
            );
            this.UPDATE_NODE_OUTGOING({
                [userTaskRightNodeId]: {
                    outgoing: {
                        resourceId: userTaskRightOutgoingLineId
                    }
                }
            });
            this.updateAllLines();
        },
        addLine(startId, endId, type) {
            let lineId = "sid-" + uuid();
            this.UPDATE_LINE({
                [lineId]: {
                    outgoing: [{ resourceId: endId }],
                    lineDrawing: false,
                    stencil: { id: type },
                    startId: startId,
                    endId: endId,
                    lineId: lineId,
                    resourceId: lineId,
                    startPosition: {
                        ...this.getPointPosition(startId, endId)
                    },
                    endPosition: {
                        ...this.getPointPosition(endId, startId)
                    }
                }
            });
            return lineId;
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
        updateAllLines() {
            for (let lineIds in this.lineData) {
                this.UPDATE_LINE({
                    [this.lineData[lineIds].resourceId]: {
                        ...this.lineData[lineIds],
                        startPosition: {
                            ...this.getPointPosition(
                                this.lineData[lineIds].startId,
                                this.lineData[lineIds].endId
                            )
                        },
                        endPosition: {
                            ...this.getPointPosition(
                                this.lineData[lineIds].endId,
                                this.lineData[lineIds].startId
                            )
                        }
                    }
                });
            }
        },
        getNodeChild(nodeId) {
            for (let nodeIds in this.nodeData) {
                if (this.nodeData[nodeIds].parentNodeId == nodeId) {
                    this.UPDATE_NODE({
                        [this.nodeData[nodeIds].id]: {
                            ...this.nodeData[nodeIds],
                            left: this.nodeData[nodeId].left
                        }
                    });
                    this.getNodeChild(this.nodeData[nodeIds].id);
                }
            }
        }
    }
};
</script>

