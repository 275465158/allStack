<template>
    <div id="editorRight" class="editor-right">
        <span>节点参数</span>
        <editor-user-task-property :option="selectedNode" v-if="userTaskSelected"></editor-user-task-property>
        <editor-exclusive-property :option="selectedNode" v-if="ExclusiveSelected"></editor-exclusive-property>
        <editor-node-property :option="selectedNode" v-if="nodeLineSelected"></editor-node-property>
        <!-- <editor-end-event-property :option="selectedNode" v-if="endEventSelected"></editor-end-event-property> -->
    </div>
</template>

<script>
import { mapState } from "vuex";
import EditorUserTaskProperty from "./properties/editorUserTaskProperty";
import EditorExclusiveProperty from "./properties/editorExclusiveProperty";
import EditorNodeProperty from "./properties/editorNodeProperty";
import EditorEndEventProperty from "./properties/editorEndEventProperty";
export default {
    name: "editorRight",
    components: {
        EditorUserTaskProperty,
        EditorNodeProperty,
        EditorExclusiveProperty,
        EditorEndEventProperty
    },
    computed: {
        ...mapState("editor", ["lineData", "nodeData", "selectedNode"]),
        userTaskSelected() {
            return (
                this.selectedNode.id != undefined &&
                this.selectedNode.type == "UserTask"
            );
        },
        ExclusiveSelected() {
            return (
                this.selectedNode.id != undefined &&
                this.selectedNode.type == "ExclusiveGateway"
            );
        },
        nodeLineSelected() {
            return (
                this.selectedNode.id != undefined &&
                (this.selectedNode.type == "ExclusiveGateway" ||
                    this.selectedNode.type == "UserTask")
            );
        },
        endEventSelected() {
            return (
                this.selectedNode.id != undefined &&
                this.selectedNode.type == "EndNoneEvent"
            );
        }
    }
};
</script>

<style lang="scss">
.editor-right {
    width: 208px;
    padding: 10px;
    position: absolute;
    top: 66px;
    bottom: 0;
    right: 0;
    background: whitesmoke;
    box-shadow: -1px 0px 5px #bbb inset;
    border-left: 1px solid #ddd;
    overflow-y: scroll;
    overflow-x: none;
    .fr-cont {
        margin: 10px 0;
        width: 197px;
        white-space: normal;
        word-break: break-all;
    }
}
</style>
