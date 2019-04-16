<template>
    <div style="width:120px">
        <el-dropdown split-button @command="addNode">
            插入节点
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="UserTask">个人任务</el-dropdown-item>
                <el-dropdown-item command="ExclusiveGateway">分支</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";

import { uuid, nodeOffsetPoint } from "../../../../common/utils";

export default {
    name: "EditorControlNode",
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
        addNode(command) {
            this.stencil = this.stencilSet[command];
            let { top, left, height } = this.nodeData[this.option.startId];
            switch (command) {
                case "UserTask":
                    top = top + height + 100;
                    this.addUserTask(top, left, this.option.startId);
                    break;
                case "ExclusiveGateway":
                    top = top + height + 100;
                    this.addExclusive(top, left, this.option.startId);
                    break;
            }
        },
        addUserTask(top, left, startNodeId) {
            //获取节点宽高
            let width = this.stencil.width,
                height = this.stencil.height;
            //初始化节点id
            let nodeId = "sid-" + uuid();
            //获取起点节点
            let startNode = this.nodeData[startNodeId];
            //获取原有结束节点id
            let eldLineId = startNode.outgoing[0].resourceId;
            let endLine = this.lineData[eldLineId];
            let endNode = this.nodeData[endLine.endId];
            let curNodeCenterPosition = {
                x: left + startNode.width / 2,
                y: top + startNode.height / 2
            };
            //修改之后的节点的父节点
            for (let nodeIds in this.nodeData) {
                if (
                    this.nodeData[nodeIds].parentNodeId == startNode.resourceId
                ) {
                    this.UPDATE_NODE({
                        [this.nodeData[nodeIds].id]: {
                            ...this.nodeData[nodeIds],
                            parentNodeId: nodeId
                        }
                    });
                }
            }
            //新增节点
            this.UPDATE_NODE({
                [nodeId]: {
                    id: nodeId,
                    resourceId: nodeId,
                    name: this.stencil.name,
                    stencil: { id: this.stencil.id },
                    outgoing: [],
                    view: this.stencil.view,
                    property: {
                        assignee: { id: "", name: "" },
                        assigneeGroup: { id: "", name: "" }
                    },
                    top: top,
                    left: curNodeCenterPosition.x - width / 2,
                    width: width,
                    height: height,
                    text: "",
                    parentNodeId: startNode.resourceId
                }
            });

            //删除起点的连线
            this.DELETE_NODE_OUTGOING({
                [startNodeId]: {
                    outgoing: { resourceId: endLine.resourceId }
                }
            });
            this.DELETE_LINE({
                resourceId: endLine.resourceId
            });
            //重画起点至新节点连线
            let lineId = this.addLine(startNode.id, nodeId, "SequenceFlow");

            this.UPDATE_NODE_OUTGOING({
                [startNode.id]: {
                    outgoing: { resourceId: lineId }
                }
            });
            let outgoingLineId = this.addLine(
                nodeId,
                endNode.resourceId,
                "SequenceFlow"
            );
            this.UPDATE_NODE_OUTGOING({
                [nodeId]: {
                    outgoing: { resourceId: outgoingLineId }
                }
            });

            this.upgradeOutgoing(endNode.resourceId);
            this.getNodeRoot(nodeId);
            this.updateAllLines();
            return nodeId;
        },
        addExclusive(top, left, startNodeId) {
            //获取节点宽高
            let width = this.stencil.width,
                height = this.stencil.height;
            //初始化节点id
            let exNodeId = "sid-" + uuid();
            //获取起点节点
            let startNode = this.nodeData[startNodeId];
            //获取原有结束节点id
            let eldLineId = startNode.outgoing[0].resourceId;
            //获取原有指向结束节点连线
            let endLine = this.lineData[eldLineId];
            let endNode = this.nodeData[endLine.endId];
            //获取起点中心点
            let curNodeCenterPosition = {
                x: left + startNode.width / 2,
                y: top + startNode.height / 2
            };

            //初始化分支参数
            let userTaskStencil = this.stencilSet["UserTask"];
            let userTaskWidth = userTaskStencil.width,
                userTaskHeight = userTaskStencil.height;
            let userTaskLeftNodeId = "sid-" + uuid(),
                userTaskRightNodeId = "sid-" + uuid();
            for (let nodeIds in this.nodeData) {
                if (this.nodeData[nodeIds].parentNodeId == startNode.id) {
                    this.UPDATE_NODE({
                        [this.nodeData[nodeIds].id]: {
                            ...this.nodeData[nodeIds],
                            parentNodeId: exNodeId
                        }
                    });
                }
            }
            //新增分支节点
            this.UPDATE_NODE({
                [exNodeId]: {
                    id: exNodeId,
                    resourceId: exNodeId,
                    name: this.stencil.name,
                    stencil: { id: this.stencil.id },
                    outgoing: [],
                    view: this.stencil.view,
                    property: {
                        assignee: { id: "", name: "" },
                        assigneeGroup: { id: "", name: "" }
                    },
                    top: top,
                    left: curNodeCenterPosition.x - width / 2,
                    width: width,
                    height: height,
                    text: "",
                    endId: endNode.resourceId,
                    subNodeList: {
                        [userTaskLeftNodeId]: {
                            resourceId: userTaskLeftNodeId
                        },
                        [userTaskRightNodeId]: {
                            resourceId: userTaskRightNodeId
                        }
                    },
                    parentNodeId: startNode.resourceId
                }
            });
            //删除起点的连线
            this.DELETE_NODE_OUTGOING({
                [startNodeId]: {
                    outgoing: { resourceId: endLine.resourceId }
                }
            });
            this.DELETE_LINE({
                resourceId: endLine.resourceId
            });
            //重画起点至新节点连线
            let exLineId = this.addLine(startNode.id, exNodeId, "SequenceFlow");

            this.UPDATE_NODE_OUTGOING({
                [startNode.id]: {
                    outgoing: { resourceId: exLineId }
                }
            });

            let startExNode = this.nodeData[exNodeId];
            let exNodeCenterPosition = {
                x: startExNode.left + startExNode.width / 2,
                y: startExNode.top + startExNode.height / 2
            };
            //插入左侧任务节点
            this.UPDATE_NODE({
                [userTaskLeftNodeId]: {
                    id: userTaskLeftNodeId,
                    resourceId: userTaskLeftNodeId,
                    name: userTaskStencil.name,
                    stencil: { id: userTaskStencil.id },
                    outgoing: [],
                    view: userTaskStencil.view,
                    property: {
                        assignee: { id: "", name: "" },
                        assigneeGroup: { id: "", name: "" }
                    },
                    top: exNodeCenterPosition.y + 180 - userTaskHeight / 2,
                    left: exNodeCenterPosition.x - 120 - userTaskWidth / 2,
                    width: userTaskWidth,
                    height: userTaskHeight,
                    text: "",
                    parentNodeId: exNodeId
                }
            });
            //添加左侧任务接入连线
            let userTaskLeftIncomeLineId = this.addLine(
                exNodeId,
                userTaskLeftNodeId,
                "GatewayFlow"
            );

            this.UPDATE_NODE_OUTGOING({
                [exNodeId]: {
                    outgoing: { resourceId: userTaskLeftIncomeLineId }
                }
            });
            //插入右侧任务节点
            this.UPDATE_NODE({
                [userTaskRightNodeId]: {
                    id: userTaskRightNodeId,
                    resourceId: userTaskRightNodeId,
                    name: userTaskStencil.name,
                    stencil: { id: userTaskStencil.id },
                    outgoing: [],
                    view: userTaskStencil.view,
                    property: {
                        assignee: { id: "", name: "" },
                        assigneeGroup: { id: "", name: "" }
                    },
                    top: exNodeCenterPosition.y + 180 - userTaskHeight / 2,
                    left: exNodeCenterPosition.x + 120 - userTaskWidth / 2,
                    width: userTaskWidth,
                    height: userTaskHeight,
                    text: "",
                    parentNodeId: exNodeId
                }
            });
            //添加右侧接入连线
            let userTaskRightIncomeLineId = this.addLine(
                exNodeId,
                userTaskRightNodeId,
                "GatewayFlow"
            );

            this.UPDATE_NODE_OUTGOING({
                [exNodeId]: {
                    outgoing: { resourceId: userTaskRightIncomeLineId }
                }
            });
            //添加左右出口连线
            let userTaskLeftOutgoingLineId = this.addLine(
                    userTaskLeftNodeId,
                    endNode.resourceId,
                    "SequenceFlow"
                ),
                userTaskRightOutgoingLineId = this.addLine(
                    userTaskRightNodeId,
                    endNode.resourceId,
                    "SequenceFlow"
                );
            this.UPDATE_NODE_OUTGOING({
                [userTaskLeftNodeId]: {
                    outgoing: {
                        resourceId: userTaskLeftOutgoingLineId
                    }
                }
            });
            this.UPDATE_NODE_OUTGOING({
                [userTaskRightNodeId]: {
                    outgoing: {
                        resourceId: userTaskRightOutgoingLineId
                    }
                }
            });
            this.upgradeOutgoing(endNode.resourceId, 1);
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
                    this.updateTree(this.lineData[lineIds.resourceId]);
                });
            });
        },
        updateNode(startNodeId, endNodeId) {
            let startNode = this.nodeData[startNodeId];
            let endNode = this.nodeData[endNodeId];
            let nextNodeTop = startNode.top + 180;
            if (endNode.top < nextNodeTop) {
                this.UPDATE_NODE({
                    [endNodeId]: {
                        ...this.nodeData[endNodeId],
                        top: nextNodeTop
                    }
                });
                startNode.outgoing.forEach(lineIds => {
                    let startPosition = {
                            ...this.getPointPosition(startNodeId, endNodeId)
                        },
                        endPosition = {
                            ...this.getPointPosition(endNodeId, startNodeId)
                        };
                    this.UPDATE_LINE({
                        [lineIds.resourceId]: {
                            ...this.lineData[lineIds.resourceId],
                            startPosition: startPosition,
                            endPosition: endPosition
                        }
                    });
                });
            }
            endNode.outgoing.forEach(lineIds => {
                this.updateNode(
                    this.lineData[lineIds.resourceId].startId,
                    this.lineData[lineIds.resourceId].endId
                );
            });
        },
        updateOutgoing(startNode, endNode) {
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
                    this.upgradeOutgoing(
                        this.lineData[lineId.resourceId].endId
                    );
                }
            });
        },
        upgradeOutgoing(startNodeId, step) {
            let startNode = this.nodeData[startNodeId];
            //更新节点top
            let top = startNode.top + 180;
            if (step != undefined) {
                top = startNode.top + 360;
            }
            this.UPDATE_NODE({
                [startNodeId]: {
                    ...this.nodeData[startNodeId],
                    top: top
                }
            });
            for (let lineIdx in this.lineData) {
                if (this.lineData[lineIdx].endId == startNodeId) {
                    this.UPDATE_LINE({
                        [this.lineData[lineIdx].resourceId]: {
                            ...this.lineData[lineIdx],
                            startPosition: {
                                ...this.getPointPosition(
                                    this.lineData[lineIdx].startId,
                                    this.lineData[lineIdx].endId
                                )
                            },
                            endPosition: {
                                ...this.getPointPosition(
                                    this.lineData[lineIdx].endId,
                                    this.lineData[lineIdx].startId
                                )
                            }
                        }
                    });
                }
            }
            if (startNode.outgoing.length > 0) {
                this.upgradeOutgoing(
                    this.lineData[startNode.outgoing[0].resourceId].endId,
                    step
                );
                this.upgradeSibling(
                    this.lineData[startNode.outgoing[0].resourceId].endId
                );
            }
            startNode.outgoing.forEach(lineIds => {
                this.UPDATE_LINE({
                    [lineIds.resourceId]: {
                        ...this.lineData[lineIds.resourceId],
                        startPosition: {
                            ...this.getPointPosition(
                                this.lineData[lineIds.resourceId].startId,
                                this.lineData[lineIds.resourceId].endId
                            )
                        },
                        endPosition: {
                            ...this.getPointPosition(
                                this.lineData[lineIds.resourceId].endId,
                                this.lineData[lineIds.resourceId].startId
                            )
                        }
                    }
                });
            });
        },
        upgradeSibling(startNodeId) {
            let parentNode = null;
            let startNodeTop = this.nodeData[startNodeId].top;
            for (let lineIdx in this.lineData) {
                if (this.lineData[lineIdx].endId == startNodeId) {
                    parentNode = this.nodeData[this.lineData[lineIdx].startId];
                }
            }
            if (parentNode != null) {
                for (let lineIds in parentNode.outgoing) {
                    let siblingId = this.lineData[
                        parentNode.outgoing[lineIds].resourceId
                    ].endId;
                    if (siblingId != startNodeId) {
                        this.UPDATE_NODE({
                            [siblingId]: {
                                ...this.nodeData[siblingId],
                                top: startNodeTop
                            }
                        });
                    }
                }
            }
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
        getNodeRoot(nodeId) {
            /**
             * 向上递归发现分支根
             */
            let deepth = 0;
            let exNode = "";
            for (let nodeIds in this.nodeData) {
                if (this.nodeData[nodeIds].id == nodeId) {
                    if (this.nodeData[nodeIds].stencil.id == "UserTask") {
                        this.getNodeRoot(this.nodeData[nodeIds].parentNodeId);
                    }
                    if (
                        this.nodeData[nodeIds].stencil.id == "ExclusiveGateway"
                    ) {
                        //获取最大深度
                        exNode = nodeIds;
                        for (let subNodeIds in this.nodeData[nodeIds]
                            .subNodeList) {
                            let childDeepth = this.getNodeChildDeep(subNodeIds);
                            if (childDeepth > deepth) {
                                deepth = childDeepth;
                            }
                        }
                    }
                }
            }
            if (exNode != "") {
                for (let endNodeIds in this.nodeData) {
                    if (
                        this.nodeData[endNodeIds].parentNodeId == exNode &&
                        !this.contains(
                            this.nodeData[exNode].subNodeList,
                            this.nodeData[endNodeIds].id
                        )
                    ) {
                        let top =
                            this.nodeData[exNode].top + 180 * (deepth + 1);
                        this.UPDATE_NODE({
                            [this.nodeData[endNodeIds].id]: {
                                ...this.nodeData[endNodeIds],
                                top: top
                            }
                        });
                    }
                }
            }
        },
        contains(arr, obj) {
            for (let nodeIds in arr) {
                if (nodeIds === obj) {
                    return true;
                }
            }
            return false;
        },
        getNodeChildDeep(nodeId) {
            /**
             * 递归向下找到最大深度
             */
            let deepth = 1;
            for (let nodeIds in this.nodeData) {
                if (this.nodeData[nodeIds].parentNodeId == nodeId) {
                    this.UPDATE_NODE({
                        [this.nodeData[nodeIds].id]: {
                            ...this.nodeData[nodeIds],
                            left: this.nodeData[nodeId].left,
                            top: this.nodeData[nodeId].top + 180
                        }
                    });
                    deepth = this.getNodeChildDeep(this.nodeData[nodeIds].id);
                    deepth = deepth + 1;
                }
            }
            return deepth;
        }
    }
};
</script>

