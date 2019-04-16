<template>
    <section class="arrow-wrap" :style="arrow" v-show="nodeId">
        <img
            src="../../../../../../static/img/arrow.png"
            draggable="false"
            class="arrow"
            v-for="(item,index) in ['c']"
            :key="index"
            :class="`arrow-${item}`"
            @mouseover="arrowPointEnter(item)"
            @mouseleave="arrowPointLeave"
            @mousedown="drawLineStart(item)"
            @mouseup="drawLineSuccess(item)"
        >
    </section>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import {
    deepCopy,
    pxToNumber,
    getRelativePosition,
    uuid
} from "../../../../common/utils";
import * as d3 from "d3";

export default {
    name: "Arrow",
    data() {
        return {
            pointRectPadding: 0,
            lineDrawing: false,
            lineInfo: {
                lineDrawing: false,
                //type: "",
                stencil: { id: "SequenceFlow" },
                startPosition: {
                    x: 0,
                    y: 0
                },
                endPosition: {
                    x: 0,
                    y: 0
                },
                startId: "",
                endId: ""
            }
        };
    },
    computed: {
        ...mapState("editor", [
            "nodeData",
            "lineData",
            "selLineType",
            "hoverNodeData",
            "hoverArrowPoint"
        ]),
        nodeId() {
            return this.hoverNodeData.id;
        },
        arrow() {
            if (!this.nodeId || !this.nodeData[this.nodeId]) {
                return;
            }
            let { width, height, left, top, x, y } = this.nodeData[this.nodeId];
            left = left + x + "px";
            top = top - 9 + height / 2 + y + "px";
            width = `${(width + this.pointRectPadding).toFixed(0)}px`;
            height = `${(height + this.pointRectPadding).toFixed(0)}px`;
            return {
                width,
                height,
                left,
                top
            };
        }
    },
    methods: {
        ...mapMutations("editor", [
            "UPDATE_LINE",
            "UPDATE_SHADOW_LINE",
            "UPDATE_NODE",
            "UPDATE_NODE_OUTGOING"
        ]),
        arrowPointEnter(direction) {},
        arrowPointLeave() {},
        drawLineStart(direction) {
            this.lineDrawing = true;
            this.lineInfo = {
                ...this.lineInfo,
                startPosition: { ...this.getPointPosition(event.target) },
                startId: this.nodeId
            };
            this.$parent.$el.addEventListener(
                "mousemove",
                this.lineDrawingHandle
            );
        },
        lineDrawingHandle() {
            const { target, currentTarget, clientX, clientY } = event;
            const { top, left } = this.$parent.$el.getBoundingClientRect();
            const { startPosition } = this.lineInfo;
            this.UPDATE_SHADOW_LINE({
                startPosition,
                endPosition: {
                    x: clientX - left,
                    y: clientY - top
                }
                //type: this.selLineType
            });
        },
        drawLineSuccess(direction) {
            const { startId } = this.lineInfo;
            this.$parent.$el.removeEventListener(
                "mousemove",
                this.lineDrawingHandle
            );
            if (!this.lineDrawing || startId === this.nodeId) {
                return;
            }

            this.lineInfo = {
                ...this.lineInfo,
                endPosition: { ...this.getPointPosition(event.target) },
                endId: this.nodeId
            };
            const lineId = "sid-" + uuid();
            const lineData = deepCopy(this.lineInfo);
            this.UPDATE_LINE({
                [lineId]: {
                    ...lineData,
                    lineId,
                    //type: this.selLineType,
                    resourceId: lineId,
                    outgoing: [{ resourceId: this.nodeId }]
                }
            });
            this.UPDATE_NODE_OUTGOING({
                [startId]: {
                    outgoing: { resourceId: lineId }
                }
            });
            this.lineInfo.lineDrawing = false;
        },
        getSourceTargetPosition(sourceId, targetId) {
            const { s_top, s_left, s_width, s_height } = this.nodeData[
                sourceId
            ];
            const { t_top, t_left, t_width, t_height } = this.nodeData[
                targetId
            ];
            if (s_top + s_height > t_top) {
            }
        },
        getPointPosition(target) {
            const node = this.nodeData[this.nodeId];
            const { top, left, width, height } = node;
            // const { top, left } = getRelativePosition(target, this.$parent.$el);
            // const { width, height } = target.getBoundingClientRect();
            console.log(top, height, left, width);
            return {
                x: Number(left) + Number(width) * 0.5,
                y: Number(height) * 0.5 + Number(top)
            };
        },
        drawLineEnd() {
            this.lineDrawing = false;
            this.UPDATE_SHADOW_LINE(null);
            this.$parent.$el.removeEventListener(
                "mousemove",
                this.lineDrawingHandle
            );
        }
    },
    mounted() {
        document.addEventListener("mouseup", this.drawLineEnd);
    },
    destroyed() {
        document.removeEventListener("mouseup", this.drawLineEnd);
    }
};
</script>

<style lang="scss">
.arrow-wrap {
    position: absolute;
    cursor: move;
    .arrow {
        opacity: 0.3;
        cursor: crosshair;
        position: absolute;
        z-index: 99999;
        &-c {
            top: 0;
            left: 50%;
            transform: translate(-50%, 50%);
        }
        &-t {
            top: 0;
            left: 50%;
            transform: translate(-50%, -50%);
        }
        &-b {
            bottom: 0;
            left: 50%;
            transform: translate(-50%, 50%);
        }
        &-l {
            bottom: 50%;
            left: 0;
            transform: translate(-50%, 50%);
        }
        &-r {
            bottom: 50%;
            right: 0;
            transform: translate(50%, 50%);
        }
        &:hover {
            opacity: 50;
        }
    }
}
</style>