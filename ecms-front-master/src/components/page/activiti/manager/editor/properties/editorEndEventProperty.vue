<template>
    <div class="editor-node-property">
        <span>节点操作</span>
        <el-form>
            <el-form-item label="节点名称">
                <el-input v-model="taskName"/>
            </el-form-item>
            <!-- <el-form-item label="任务执行组">
                    <el-input v-model="assigneeGroup.name"/>
                    <el-button size="small" @click="assigneeGroupSelectorVisible=true">...</el-button>
            </el-form-item>-->
        </el-form>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
    name: "EditorEndEventProperty",
    data() {
        return {};
    },
    props: {
        option: {
            type: Object
        }
    },
    computed: {
        ...mapState("editor", ["nodeData", "lineData", "selectedNode"]),
        taskName: {
            get: function() {
                return this.selectedNode.name;
            },
            set: function() {}
        }
    },
    methods: {
        ...mapMutations("editor", ["UPDATE_SELECTED_NODE", "UPDATE_NODE"])
    }
};
</script>

<style lang="scss">
</style>
