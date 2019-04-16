<template>
    <div
        id="EditorNodeDraw"
        class="node-draw"
        ref="EditorNodeDraw"
        @mouseover.capture="drawMouseover"
    >
        <!-- <editor-arrow></editor-arrow> -->
        <!-- <editor-node
            v-for="(item,index) in nodeData"
            :key="index"
            :option="item"
            @nodeDragStart="nodeDragStart"
            @nodeDragging="nodeDragging"
            @nodeDragEnd="nodeDragEnd"
        ></editor-node>-->
        <!-- <editor-ref-line :refLineData="refLineData"></editor-ref-line> -->
        <editor-start-node
            :option="item"
            v-for="(item, index) in nodeData"
            v-if="item.stencil.id=='StartNoneEvent'"
            :key="index"
        ></editor-start-node>
        <editor-end-node
            :option="item"
            v-for="(item, index) in nodeData"
            v-if="item.stencil.id=='EndNoneEvent'"
            :key="index"
        ></editor-end-node>
        <editor-user-task-node
            :option="item"
            v-for="(item, index) in nodeData"
            v-if="item.stencil.id=='UserTask'"
            :key="index"
        ></editor-user-task-node>
        <editor-exclusive-node
            :option="item"
            v-for="(item, index) in nodeData"
            v-if="item.stencil.id=='ExclusiveGateway'"
            :key="index"
        ></editor-exclusive-node>
    </div>
</template>

<script>
import EditorNode from "./editorNode";
import EditorStartNode from "./nodes/editorStartNode";
import EditorEndNode from "./nodes/editorEndNode";
import EditorUserTaskNode from "./nodes/editorUserTaskNode";
import EditorExclusiveNode from "./nodes/editorExclusiveNode";
import EditorRefLine from "./editorRefLine";
import EditorArrow from "./editorArrow";

import { mapState, mapMutations } from "vuex";

export default {
    name: "EditorNodeDraw",
    data() {
        return {
            dragNodeId: "",
            dragging: false,
            dragNodeInfo: {},
            checkNodesData: [],
            refLineData: [],
            mouseInfo: {
                left: "",
                top: ""
            }
        };
    },
    components: {
        EditorNode,
        EditorEndNode,
        EditorStartNode,
        EditorUserTaskNode,
        EditorExclusiveNode,
        EditorRefLine,
        EditorArrow
    },
    computed: {
        ...mapState("editor", ["nodeData"])
    },
    watch: {
        dragNodeId() {
            this.dragNodeInfo = {
                ...this.nodeData[this.dragNodeId]
            };
        }
    },
    methods: {
        ...mapMutations("editor", ["UPDATE_HOVER_NODE"]),
        drawMouseover() {
            if (event.target === event.currentTarget) {
                this.UPDATE_HOVER_NODE({
                    id: ""
                });
            }
        },
        nodeDragEnd(payload) {
            this.refLineData = [];
        },
        nodeDragging(payload) {
            // let fn = () => {
            //   let { event } = payload;
            //   // 在HTML的结构固定的情况下，可以使用一下方式快捷获取元素定位。
            //   // 否则不建议使用该方法。只可惜drag事件的event无法获取鼠标在当前画布的偏移量。
            //   let { clientX: X, clientY: Y } = event;
            //   let drawContainer = document.getElementById('drawContainer');
            //   let { top: oTop, left: oLeft } = drawContainer.getBoundingClientRect();
            //   let { scrollTop: sTop, scrollLeft: sLeft } = drawContainer;
            //   let left = X - oLeft + sLeft - 20;
            //   let top = Y - oTop + sTop - 23;
            //   let { height, width } = this.nodeData[payload.id];
            //   let right = left + width;
            //   let bottom = top + height;
            //   let dragData = {
            //     left,
            //     top,
            //     right,
            //     bottom,
            //     width,
            //     height,
            //     halfHeight: height / 2,
            //     halfWidth: width / 2
            //   };
            //   this.checkNodesData.forEach(target => {
            //     let {
            //       id,
            //       left,
            //       top,
            //       right,
            //       bottom,
            //       halfWidth,
            //       halfHeight,
            //       width,
            //       height
            //     } = target;
            //     if (id === payload.id) {
            //       return;
            //     }
            //     let conditions = [
            //       // 水平参考线 - 上
            //       {
            //         isNearly: this.isNearly(dragData.top, top),
            //         refLine: ['top', top]
            //       },
            //       {
            //         isNearly: this.isNearly(dragData.top, bottom),
            //         refLine: ['top', bottom]
            //       },
            //       // 水平参考线 - 中
            //       {
            //         isNearly: this.isNearly(
            //           dragData.top + dragData.halfHeight,
            //           top + halfHeight
            //         ),
            //         refLine: ['top', top + halfHeight]
            //       },
            //       // 水平参考线 - 下
            //       {
            //         isNearly: this.isNearly(dragData.bottom, top),
            //         refLine: ['top', top]
            //       },
            //       {
            //         isNearly: this.isNearly(dragData.bottom, bottom),
            //         refLine: ['top', bottom]
            //       },
            //       // 垂直参考线 - 左
            //       {
            //         isNearly: this.isNearly(dragData.left, left),
            //         refLine: ['left', left]
            //       },
            //       {
            //         isNearly: this.isNearly(dragData.left, right),
            //         refLine: ['left', right]
            //       },
            //       // 垂直参考线 - 右
            //       {
            //         isNearly: this.isNearly(dragData.right, left),
            //         refLine: ['left', left]
            //       },
            //       {
            //         isNearly: this.isNearly(dragData.right, right),
            //         refLine: ['left', right]
            //       },
            //       // 垂直参考线 - 中
            //       {
            //         isNearly: this.isNearly(
            //           dragData.left + dragData.halfWidth,
            //           left + halfWidth
            //         ),
            //         refLine: ['left', left + halfWidth]
            //       }
            //     ];
            //     this.refLineData = conditions;
            //   });
            // };
            // // 函数节流
            // fn();
        },
        isNearly(dragValue, targetValue) {
            return Math.abs(dragValue - targetValue) <= 3;
        },
        nodeDragStart(id) {
            this.checkNodes(id);
        },
        checkNodes(id) {
            let checkNodes = Object.values(this.nodeData);
            let top, height, bottom, left, width, right;

            this.checkNodesData = checkNodes.map(element => {
                let { width, height, left, top, x, y, id } = element;
                let right, bottom, halfWidth, halfHeight;
                left += x;
                top += y;
                right = left + width;
                bottom = top + height;
                halfWidth = width / 2;
                halfHeight = height / 2;
                return {
                    id,
                    left,
                    top,
                    right,
                    bottom,
                    halfWidth,
                    halfHeight,
                    width,
                    height
                };
            });
        }
    },
    mounted() {}
};
</script>

<style scoped>
.node-draw {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 9999;
}
</style>