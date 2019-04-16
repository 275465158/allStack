<template>
    <div id="editor" class="editor">
        <editor-header></editor-header>
        <editor-left-tool class="xmp"></editor-left-tool>
        <editor-main-draw></editor-main-draw>
        <!-- <editor-bottom></editor-bottom> -->
        <editor-right></editor-right>
    </div>
</template>
<script>
import EditorHeader from "./editorHeader";
import EditorTopTool from "./editorTopTool";
import EditorLeftTool from "./editorLeftTool";
import EditorRight from "./editorRight";
import EditorMainDraw from "./editorMainDraw";
import EditorBottom from "./editorBottom";
export default {
    name: "editor",
    components: {
        EditorHeader,
        //EditorTopTool,
        EditorLeftTool,
        EditorRight,
        EditorMainDraw,
        EditorBottom
    }
};
</script>
