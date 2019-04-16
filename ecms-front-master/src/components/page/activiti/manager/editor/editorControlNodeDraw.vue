<template>
    <div id="EditorControlNodeDraw" class="control-node-draw" ref="EditorControlNodeDraw">
        <editor-control-node
            v-for="(item, index) in lineData"
            v-if="item.stencil.id=='SequenceFlow'"
            :key="index"
            :option="item"
            :ref="'cn'+item.id"
            :style="nodePosition(item)"
            class="editor-control-node"
        ></editor-control-node>
        <editor-gate-control-node
            v-for="(item, index) in lineData"
            v-if="item.stencil.id=='GatewayFlow'"
            :key="index"
            :option="item"
            :ref="'cn'+item.id"
            :style="gateNodePosition(item)"
            class="editor-control-node"
        ></editor-gate-control-node>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import EditorControlNode from "./editorControlNode";
import EditorGateControlNode from "./editorGateControlNode";
export default {
    name: "EditorControlNodeDraw",
    components: {
        EditorControlNode,
        EditorGateControlNode
    },
    data() {
        return {
            cnodePosition: { left: `0px`, top: `0px` }
        };
    },
    computed: {
        ...mapState("editor", ["lineData"]),
        nodePosition() {
            return function(item) {
                let { startPosition, endPosition } = item;
                let st = this.generateCenterPoint(endPosition, startPosition);
                return {
                    left: `${st.x - 60}px`,
                    top: `${st.y - 15}px`
                };
            };
        },
        gateNodePosition() {
            return function(item) {
                let { startPosition, endPosition } = item;
                let st = this.generateCenterPoint(endPosition, startPosition);
                return {
                    left: `${st.x - 40}px`,
                    top: `${st.y - 15}px`
                };
            };
        }
    },
    methods: {
        generateCenterPoint(source, target) {
            let ct = {
                x: (source.x + target.x) / 2,
                y: (source.y + target.y) / 2
            };
            return {
                x: target.x,
                y: ct.y
            };
        }
    }
};
</script>

<style lang="scss">
.control-node-draw {
    position: absolute;
    width: 100px;
    top: 0;
    left: 0;
    z-index: 10000;
    .editor-control-node {
        position: absolute;
    }
}
</style>
