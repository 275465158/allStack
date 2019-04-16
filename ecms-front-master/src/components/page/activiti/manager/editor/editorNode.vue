<template>
    <div
        class="editor-node"
        :draggable="nodeInfo.draggable"
        :style="nodePosition"
        @dragstart="nodeDragStart(option.id)"
        @drag="nodeDragging(option.id)"
        @dragend="nodeDragEnd(option.id)"
        @mouseenter="mouseEnterNode(option.id)"
        @click="selectedNode(option)"
    >
        <!-- <icon :name="option.type" :size="80" :ref="option.id" style="cursor:move"/> -->
        <!-- <div v-html="option.view" :ref="option.id" style="cursor:move"></div> -->
        <editor-node-view :option="option" :ref="option.id" style="cursor:move;"></editor-node-view>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import {
    smoothPoint,
    calculateDirection,
    offsetPoint,
    nodeOffsetPoint
} from "../../../../common/utils";
import EditorNodeView from "./editorNodeView";
export default {
    name: "EditorNode",
    components: {
        EditorNodeView
    },
    data() {
        return {
            arrowVisible: false,
            nodeInfo: {
                draggable: true
            }
        };
    },
    props: {
        option: {
            type: Object
        }
    },
    computed: {
        ...mapState("editor", ["nodeData", "lineData"]),
        nodePosition() {
            return {
                left: `${this.option.left}px`,
                top: `${this.option.top}px`,
                width: `${this.option.width}px`,
                height: `${this.option.height}px`,
                border: `1px solid #000`
            };
        }
    },
    methods: {
        ...mapMutations("editor", [
            "UPDATE_NODE",
            "UPDATE_HOVER_NODE",
            "UPDATE_SELECTED_NODE",
            "UPDATE_LINE"
        ]),
        nodeDragStart(id) {
            let img = this.$el;
            let dataTransfer = event.dataTransfer;
            dataTransfer.dropEffect = "move";
            dataTransfer.setData("Text", `update:${this.option.id}`);
            dataTransfer.setDragImage(img, 30, 10);
            this.$emit("nodeDragStart", id);
        },
        nodeDragging(id) {
            // this.$emit('nodeDragging', { id, event });
            const cx = parseInt(event.clientX / 20) * 20;
            const cy = parseInt(event.clientY / 20) * 20;
            const { width, height, left, top } = this.nodeData[id];
            const nodeOffsetX = cx - 208; //+ parseInt(width / 20) * 10;
            const nodeOffsetY = cy; //+ parseInt(height / 20) * 10;
            //console.log(width, height, left, top);
            this.updateLine(
                Number(nodeOffsetX),
                Number(nodeOffsetY),
                width,
                height,
                id
            );
        },
        updateLine(nodeOffsetX, nodeOffsetY, width, height, id) {
            let data = {};
            for (var key in this.lineData) {
                let sourcePoint = {
                    x: nodeOffsetX,
                    y: nodeOffsetY,
                    width: this.nodeData[id].width,
                    height: this.nodeData[id].height
                };
                const { startId, endId } = this.lineData[key];

                if (startId === id) {
                    let targetNode = this.nodeData[this.lineData[key].endId];
                    let startPosition = nodeOffsetPoint(
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height,
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height
                    );
                    let endPosition = nodeOffsetPoint(
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height,
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height
                    );
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            startPosition: startPosition.source,
                            endPosition: endPosition.source
                        }
                    };
                } else if (endId === id) {
                    let targetNode = this.nodeData[this.lineData[key].startId];
                    let startPosition = nodeOffsetPoint(
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height,
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height
                    );
                    let endPosition = nodeOffsetPoint(
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height,
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height
                    );
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            startPosition: startPosition.source,
                            endPosition: endPosition.source
                        }
                    };
                }
            }
            this.UPDATE_LINE(data);
        },
        nodeDragEnd(id) {
            let data = {};
            for (var key in this.lineData) {
                let sourcePoint = {
                    x: this.nodeData[id].left,
                    y: this.nodeData[id].top,
                    width: this.nodeData[id].width,
                    height: this.nodeData[id].height
                };
                const { startId, endId } = this.lineData[key];

                if (startId === id) {
                    let targetNode = this.nodeData[this.lineData[key].endId];
                    let startPosition = nodeOffsetPoint(
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height,
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height
                    );
                    let endPosition = nodeOffsetPoint(
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height,
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height
                    );
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            startPosition: startPosition.source,
                            endPosition: endPosition.source
                        }
                    };
                } else if (endId === id) {
                    let targetNode = this.nodeData[this.lineData[key].startId];
                    let startPosition = nodeOffsetPoint(
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height,
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height
                    );
                    let endPosition = nodeOffsetPoint(
                        { x: sourcePoint.x, y: sourcePoint.y },
                        sourcePoint.width,
                        sourcePoint.height,
                        { x: targetNode.left, y: targetNode.top },
                        targetNode.width,
                        targetNode.height
                    );
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            startPosition: startPosition.source,
                            endPosition: endPosition.source
                        }
                    };
                }
            }
            this.UPDATE_LINE(data);
            this.$emit("nodeDragEnd", { id, event });
        },
        mouseEnterNode(id) {
            this.UPDATE_HOVER_NODE({
                id
            });
        },
        getNodeSize() {
            // let id = this.option.id;
            // let { width, height, x, y } = this.$refs[
            //     id
            // ].firstElementChild.getBBox();
            // width = +width.toFixed(0);
            // height = +(height - 3).toFixed(0);
            // x = +x.toFixed(0);
            // y = +y.toFixed(0);
            // this.UPDATE_NODE({
            //     [id]: {
            //         ...this.nodeData[id],
            //         width,
            //         height,
            //         x,
            //         y
            //     }
            // });
        },
        init() {
            // let id = this.option.id;
            // if (typeof this.nodeData[id].width === "undefined") {
            //     this.getNodeSize();
            // }
            // if (this.$refs[id].firstElementChild.children[2].children[2]) {
            //     this.$refs[
            //         id
            //     ].firstElementChild.children[2].children[2].innerHTML = this.option.name;
            // }
        },
        selectedNode(item) {
            console.log(item);
            this.UPDATE_SELECTED_NODE({
                ...this.selectedNode,
                id: item.id,
                name: item.name,
                type: item.stencil.id,
                property: {
                    assignee: item.property.assignee,
                    assigneeGroup: item.property.assigneeGroup
                },
                outgoing: item.outgoing,
                top: item.top,
                left: item.left,
                width: item.width,
                height: item.height
            });
        }
    },
    mounted() {
        // this.init();
    }
};
</script>

<style lang="scss">
.editor-node {
    position: absolute;
    z-index: 999;
    &.editor-node:hover a {
        border-right: 1px solid #000;
    }
}
</style>