<template>
    <svg id="EditorLineDraw" style="position: absolute;top:0;left:0;width:100%;height:100%">
        <defs>
            <marker
                id="markerArrow"
                markerWidth="13"
                markerHeight="13"
                refX="5"
                refY="5"
                orient="auto"
                markerUnits="strokeWidth"
            >
                <path
                    d="M 0 0 L 12 6 L 0 12  L 4 6"
                    style="fill: rgb(0,0,0);"
                    transform="scale(0.8)"
                ></path>
            </marker>
        </defs>
        <!-- <flow-line v-for="(item,index) in lineData"1
      :key="index"
      :option="item"
      v-if="lineData"></flow-line>
    <flow-line :option="shadowLineData"
        v-if="shadowLineData"></flow-line>-->
        <editor-path
            v-for="(item,index) in lineData"
            :key="index"
            :lineOption="item"
            style="cursor:move"
        ></editor-path>
        <editor-path :lineOption="shadowLineData" v-if="shadowLineData"></editor-path>
    </svg>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import EditorLine from "./editorLine";
import EditorPath from "./editorPath";

export default {
    name: "EditorLineDraw",
    components: {
        EditorLine,
        EditorPath
    },
    computed: {
        ...mapState("editor", ["lineData", "shadowLineData"])
    },
    methods: {}
};
</script>

<style scoped>
</style>