<template>
    <ul id="toolMenu" class="tool-menu" :style="ulStyle" v-if="visible">
        <li
            class="tool-menu-item"
            v-for="(item, index) in menuData"
            :key="index"
            @click="clickFn(item)"
        >{{item.title}}</li>
    </ul>
</template>

<script>
export default {
    name: "editorTopTool",
    props: {
        visible: {
            type: Boolean
        },
        menuData: { type: Array },
        ulStyle: { type: String }
    },
    watch: {
        visible() {
            if (this.visible) {
                document.addEventListener("click", this.documentClick);
            } else {
                document.removeEventListener("click", this.documentClick);
            }
        }
    },
    methods: {
        clickFn(item) {
            this.$emit("update:visible", false);
            this.$emit("selItme", item);
        },
        close() {
            if (this.visible) {
                this.$emit("update:visible", false);
            }
        },
        documentClick() {
            this.$emit("update:visible", false);
        }
    }
};
</script>


<style lang="scss">
.tool-menu {
    border: 3px solid #e0e0e0;
    box-shadow: 2px 2px 3px #d5d5d5;
    background: #fff;
    position: absolute;
    padding: 3px;
    overflow-y: auto;
    overflow-x: hidden;
    z-index: 9999;
    .tool-menu-item {
        transition: all 0.1s ease-in-out;
        padding: 5px;
        border-radius: 10px;
        cursor: pointer;
        &:hover {
            background: #eee;
        }
    }
}
</style>
