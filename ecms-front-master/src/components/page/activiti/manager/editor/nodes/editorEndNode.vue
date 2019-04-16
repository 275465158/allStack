<template>
    <div class="editor-node" @click="selectedNode(option)" :style="nodePosition">
        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" version="1.0">
            <g pointer-events="fill">
                <circle
                    id="bg_frame"
                    cx="16"
                    cy="16"
                    r="14"
                    stroke="#585858"
                    fill="#ffffff"
                    stroke-width="3"
                ></circle>
            </g>
        </svg>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import {
    smoothPoint,
    calculateDirection,
    offsetPoint,
    nodeOffsetPoint
} from "../../../../../common/utils";
export default {
    name: "EditorEndNode",

    data() {
        return {
            width: 30,
            height: 30,
            top: 0,
            left: 0
        };
    },
    props: {
        option: Object
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
        selectedNode(item) {
            this.UPDATE_SELECTED_NODE({
                ...this.selectedNode,
                id: item.id,
                name: item.id,
                type: item.stencil.id,
                parentNodeId: item.parentNodeId,
                outgoing: item.outgoing,
                top: item.top,
                left: item.left,
                width: item.width,
                height: item.height
            });
        },
        init() {
            this.UPDATE_NODE({
                [this.option.id]: {
                    ...this.nodeData[this.option.id],
                    width: this.option.width,
                    height: this.option.height
                }
            });
        }
    },
    mounted() {
        this.init();
    }
};
</script>
<style lang="scss">
.editor-node {
    position: absolute;
    z-index: 999;
}
</style>