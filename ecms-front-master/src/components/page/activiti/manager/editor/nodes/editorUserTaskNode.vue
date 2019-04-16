<template>
    <div class="editor-node" @click="selectedNode(option)" :style="nodePosition">
        <svg
            xmlns="http://www.w3.org/2000/svg"
            xmlns:svg="http://www.w3.org/2000/svg"
            width="102"
            height="82"
            version="1.0"
        >
            <g pointer-events="fill" oryx:minimumSize="50 40">
                <rect
                    id="text_frame"
                    oryx:anchors="bottom top right left"
                    x="1"
                    y="1"
                    width="94"
                    height="79"
                    rx="10"
                    ry="10"
                    stroke="none"
                    stroke-width="0"
                    fill="none"
                ></rect>
                <rect
                    id="bg_frame"
                    oryx:resize="vertical horizontal"
                    x="0"
                    y="0"
                    width="100"
                    height="80"
                    rx="10"
                    ry="10"
                    stroke="#bbbbbb"
                    stroke-width="1"
                    fill="#f9f9f9"
                ></rect>
                <text
                    font-size="12"
                    id="text_name"
                    x="0"
                    y="40"
                    oryx:align="middle center"
                    oryx:fittoelem="text_frame"
                    stroke="#373e48"
                >{{option.name}}</text>
                <g id="userTask" transform="translate(3,3)">
                    <path
                        oryx:anchors="top left"
                        style="fill:#d1b575;stroke:none;"
                        d="m 1,17 16,0 0,-1.7778 -5.333332,-3.5555 0,-1.7778 c 1.244444,0 1.244444,-2.3111 1.244444,-2.3111 l 0,-3.0222 C 12.555557,0.8221 9.0000001,1.0001 9.0000001,1.0001 c 0,0 -3.5555556,-0.178 -3.9111111,3.5555 l 0,3.0222 c 0,0 0,2.3111 1.2444443,2.3111 l 0,1.7778 L 1,15.2222 1,17 17,17"
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
    name: "EditorUserTaskNode",

    data() {
        return {
            width: 100,
            height: 80,
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
                height: item.height,
                parentNodeId: item.parentNodeId
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