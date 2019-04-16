<template>
    <div id="header" class="editor-header">
        <ul class="editor-menu">
            <li
                v-for="(item, index) in editorMenu"
                :key="index"
                @click="clickFn(item)"
            >{{item.name}}</li>
        </ul>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { uuid } from "../../../../common/utils";
export default {
    name: "editorHeader",
    data() {
        return {
            //editorTitle: modelName,
            editorMenu: [
                // { name: "Open", event: "open" },
                { name: "保存", event: "save" },
                { name: "取消", event: "close" }
            ]
        };
    },
    computed: {
        ...mapState("editor", ["modelData", "nodeData", "lineData"]),
        modelName() {
            return this.modelData.properties.name;
        }
    },
    methods: {
        ...mapMutations("editor", ["UPDATE_MODEL"]),
        clickFn(item) {
            let eventFn = {
                save() {
                    let name = this.modelData.properties.name;
                    let key = this.modelData.properties.process_id;
                    let description = this.modelData.properties.desc;
                    let taskNameRules = this.modelData.properties.taskNameRules;
                    //let formData = this.modelData.formData;
                    let childShapes = [];
                    for (var nodeKey in this.nodeData) {
                        childShapes.push(this.nodeData[nodeKey]);
                    }
                    for (var lineKey in this.lineData) {
                        childShapes.push(this.lineData[lineKey]);
                    }
                    this.UPDATE_MODEL({
                        ...this.modelData,
                        childShapes: childShapes
                    });

                    this.$http.post("/bpm/models/saveModel", {
                        modelId: this.modelData.modelId,
                        json_xml: JSON.stringify(this.modelData),
                        name: name,
                        taskNameRules:taskNameRules,
                        
                        key: key,
                        description: description
                    });
                    this.$router.push("/modelList");
                },
                close() {
                    this.$router.push("/modelList");
                }
            };
            eventFn[item.event].call(this);
        }
    }
};
</script>
<style lang="scss">
.editor-header {
    background: #1f88d6;
    color: #fff;
    display: flex;
    align-items: center;
    padding-left: 15px;
    .editor-title {
        padding: 2px 8px;
        font-size: 16px;
    }
    .editor-menu {
        display: flex;
        li {
            padding: 6px 8px;
            font-weight: bold;
            cursor: pointer;
            list-style: none;
        }
    }
}
</style>
