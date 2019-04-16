<template>
    <div id="editorMainCont" class="editor-main-cont">
        <div id="drawContainer" class="draw-wrap">
            <!-- <editor-crumbs></editor-crumbs> -->
            <div
                id="draw"
                class="main-bg"
                ref="mainbg"
                :style="{transform:`scale(${drawStyle.zoomRate})`,transformOrigin:`${drawStyle.origin}`}"
                @drop.prevent="dropHandle"
                @dragover.stop.prevent
                @mousewheel.alt.prevent="wheelHandle"
            >
                <editor-node-draw></editor-node-draw>
                <editor-control-node-draw></editor-control-node-draw>
                <editor-line-draw></editor-line-draw>
            </div>
        </div>
    </div>
</template>

<script>
import { uuid } from "../../../../common/utils";
import { mapState, mapMutations } from "vuex";
import EditorNodeDraw from "./editorNodeDraw";
import EditorLineDraw from "./editorLineDraw";
import EditorControlNodeDraw from "./editorControlNodeDraw";
// import EditorCrumbs from './EditorCrumbs';

export default {
    name: "editorMainDraw",
    data() {
        return {
            zoomType: "zoomIn"
        };
    },

    components: {
        EditorNodeDraw,
        EditorLineDraw,
        EditorControlNodeDraw
        // EditorCrumbs
    },
    computed: {
        ...mapState("editor", [
            "nodeData",
            "selNodeType",
            "selNodeView",
            "stencilSet",
            "lineData",
            "selLineType",
            "drawStyle",
            "hoverNodeData",
            "selectedNode"
        ])
    },
    methods: {
        ...mapMutations("editor", [
            "UPDATE_NODE",
            "UPDATE_DRAWSTYLE",
            "UPDATE_LINE",
            "UPDATE_SELECTED_NODE"
        ]),
        wheelHandle(ev) {
            if (ev.deltaY < 0) {
                this.zoomType = "zoomIn";
            } else {
                this.zoomType = "zoomOut";
            }
            let x = ev.offsetX;
            let y = ev.offsetY;
            if (this.zoomType === "zoomIn") {
                if (this.drawStyle.zoomRate < 3) {
                    let zoomRate = this.drawStyle.zoomRate + 0.25;
                    this.UPDATE_DRAWSTYLE({
                        zoomRate,
                        origin: `${x}px ${y}px`
                    });
                }
            } else if (this.zoomType === "zoomOut") {
                if (this.drawStyle.zoomRate > 0.25) {
                    let zoomRate = this.drawStyle.zoomRate - 0.25;
                    this.UPDATE_DRAWSTYLE({
                        zoomRate,
                        origin: `${x}px ${y}px`
                    });
                }
            }
        },
        updateLine(nodeOffsetX, nodeOffsetY) {
            let { id } = this.hoverNodeData;
            let data = {};
            for (var key in this.lineData) {
                const {
                    startId,
                    endId,
                    startPosition: { x: sx, y: sy },
                    endPosition: { x: ex, y: ey }
                } = this.lineData[key];
                if (startId === id) {
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            startPosition: {
                                x: sx + nodeOffsetX,
                                y: sy + nodeOffsetY
                            }
                        }
                    };
                } else if (endId === id) {
                    data = {
                        ...data,
                        [key]: {
                            ...this.lineData[key],
                            endPosition: {
                                x: ex + nodeOffsetX,
                                y: ey + nodeOffsetY
                            }
                        }
                    };
                }
            }
            this.UPDATE_LINE(data);
        },
        dropHandle(ev) {
            // 防止拖拽速度过快导致定位错误
            if (ev.target.tagName !== "DIV") return;
            let cont = event.dataTransfer.getData("Text"),
                // 为了解决节点移动时位置问题，节点位置为 update:nodeID的形式
                key = cont.replace(/\:.*/g, ""),
                x = parseInt(ev.offsetX / 20) * 20, // 这边的50是设置dragImage时的偏移量，(由于使 iconfont 实际icon与svg存在间隙)
                y = parseInt(ev.offsetY / 20) * 20;
            ev.dataTransfer.dropEffect = "copy";
            let fn = {
                update() {
                    let id = cont.replace(/.*\:/g, "");
                    const { left, top } = this.nodeData[id];
                    const nodeOffsetX = x - left;
                    const nodeOffsetY = y - top;
                    this.UPDATE_NODE({
                        [id]: {
                            ...this.nodeData[id],
                            top: y,
                            left: x
                        }
                    });
                    // this.updateLine(nodeOffsetX, nodeOffsetY);
                },
                add() {
                    let id = "sid-" + uuid();
                    let imgSrc = ev.dataTransfer.getData("URL");
                    this.UPDATE_NODE({
                        [id]: {
                            id: id,
                            resourceId: id,
                            name: this.selNodeType,
                            stencil: { id: this.selNodeType },
                            // type: this.selNodeType,
                            outgoing: [],
                            view: this.selNodeView,
                            property: {
                                assignee: { id: "", name: "" },
                                assigneeGroup: { id: "", name: "" }
                            },
                            top: y,
                            left: x,
                            width: this.stencilSet[this.selNodeType].width,
                            height: this.stencilSet[this.selNodeType].height,
                            text: "",
                            imgSrc
                        }
                    });
                }
            };
            if (typeof key !== "undefined") {
                fn[key].call(this);
            }
        }
    },
    mounted() {
        document.addEventListener("contextmenu", ev => {
            ev.preventDefault();
        });
    }
};
</script>

<style lang="scss">
.editor-main-cont {
    right: 228px;
    left: 0;
    top: 66px;
    bottom: 0;
    overflow: auto;
    background: #ebebeb;
    position: absolute;
    display: flex;
    flex-direction: column;
    .main-bg {
        overflow: hidden;
        width: 5000px;
        height: 5000px;
        background: url("../../../../../../static/img/bg.png") #fff;
    }
    .draw-wrap {
        flex: 1;
        overflow: auto;
    }
}
</style>