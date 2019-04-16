<template>
    <div id="editorBottom" class="editor-bottom">
        <span>节点数据</span>
        <div class="fb-cont">{{node}}</div>
        <span>连线数据</span>
        <div class="fb-cont">{{line}}</div>
        <span>图形数据</span>
        <div class="fb-cont">{{model}}</div>
    </div>
</template>
<script>
import { mapState } from "vuex";
export default {
    name: "editorBottom",
    computed: {
        ...mapState("editor", ["nodeData", "lineData", "modelData"]),
        node() {
            return JSON.stringify(this.nodeData);
        },
        line() {
            return JSON.stringify(this.lineData);
        },
        model() {
            return JSON.stringify(this.modelData);
        }
    }
};
</script>
<style lang="scss">
.editor-bottom {
    left: 0;
    top: 630px;
    bottom: 0;
    right: 0;
    height: 208px;
    width: 950px;
    position: absolute;
    display: flex;
    overflow: auto;
    .fb-cont {
        width: 50%;
        overflow-x: hidden;
        overflow: auto;
    }
    #span {
        float: left;
    }
}
</style>
