<template>
    <div @click="selectedNode(nodeOption)">
        <div :v-html="view"></div>
    </div>
</template>

<script>
export default {
    name: "EditorNode",
    data() {
        return {
            width: 100,
            height: 40,
            view: "",
            nodePosition: { x: 0, y: 0 }
        };
    },
    props: {
        nodeOption: {
            type: Object
        }
    },
    methods: {
        selectedNode(item) {
            this.$emit("nodeClick", item);
        }
    }
};
</script>
