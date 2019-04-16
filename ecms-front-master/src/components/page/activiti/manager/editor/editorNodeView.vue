<script>
import { mapState, mapMutations } from "vuex";
export default {
    name: "EditorNodeView",
    props: {
        option: { type: Object }
    },
    computed: {
        ...mapState("editor", ["nodeData"]),
        nodeName() {
            return this.option.name;
        },
        nodeId() {
            return this.option.id;
        }
    },
    methods: {
        ...mapMutations("editor", ["UPDATE_NODE"]),
        getNodeSize() {
            let id = this.option.id;
            let { width, height, x, y } = this.$el.children[2].getBBox();
            width = +width.toFixed(0);
            height = +(height - 3).toFixed(0);
            x = +x.toFixed(0);
            y = +y.toFixed(0);
            this.UPDATE_NODE({
                [id]: {
                    ...this.nodeData[id],
                    width,
                    height,
                    x,
                    y
                }
            });
        },
        init() {
            let id = this.option.id;
            if (typeof this.nodeData[id].width === "undefined") {
                this.getNodeSize();
            }
        }
    },
    mounted() {
        this.init();
    },
    created() {
        this.$options.template = `${this.option.view}`;
    }
};
</script>
