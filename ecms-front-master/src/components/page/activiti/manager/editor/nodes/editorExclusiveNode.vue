<template>
    <div class="editor-node" @click="selectedNode(option)" :style="nodePosition">
        <svg
            xmlns:svg="http://www.w3.org/2000/svg"
            xmlns="http://www.w3.org/2000/svg"
            version="1.0"
            width="40"
            height="40"
        >
            <g>
                <path
                    d="M -4.5,16 L 16,-4.5 L 35.5,16 L 16,35.5z"
                    id="bg_frame"
                    fill="#ffffff"
                    stroke="#585858"
                    style="stroke-width:1"
                ></path>
                <g id="cross">
                    <path
                        id="crosspath"
                        stroke="#585858"
                        fill="#585858"
                        d="M 8.75,7.55 L 12.75,7.55 L 23.15,24.45 L 19.25,24.45 z"
                        style="stroke-width:1"
                    ></path>
                    <path
                        id="crosspath2"
                        stroke="#585858"
                        fill="#585858"
                        d="M 8.75,24.45 L 19.25,7.55 L 23.15,7.55 L 12.75,24.45 z"
                        style="stroke-width:1"
                    ></path>
                </g>
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
    name: "EditorExclusiveNode",

    data() {
        return {
            width: 30,
            height: 30,
            top: 0,
            left: 0,
            subNodeList: {}
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
        },
        init() {
            this.subNodeList = this.option.subNodeList;
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