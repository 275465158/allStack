<script>
import { mapState, mapMutations } from "vuex";
import {
    smoothPoint,
    calculateDirection,
    offsetPoint
} from "../../../../common/utils";
import * as d3 from "d3";
import { nextTick } from "q";

export default {
    name: "EditorPath",
    render(h) {
        const a = (
            <path
                id="link-1-2"
                class="link"
                marker-end="url(#markerArrow)"
                d={this.optionD}
            />
        );
        return a;
    },
    data() {
        return {};
    },
    props: {
        lineOption: {
            type: Object
        }
    },
    computed: {
        ...mapState("editor", ["lineData", "nodeData"]),
        option() {
            return {
                source: {
                    ...this.lineOption.startPosition,
                    startId: this.lineOption.startId,
                    endId: this.lineOption.endId
                },
                target: {
                    ...this.lineOption.endPosition,
                    startId: this.lineOption.startId,
                    endId: this.lineOption.endId
                }
            };
        },
        link() {
            const { sc, tc } = this.controlPointLayout();
            return {
                source: this.option.source,
                target: this.option.target,
                sc,
                tc
            };
        },
        optionD() {
            let points = this.controlPointLayout();
            const line = d3
                .line()
                .x(d => d.x)
                .y(d => d.y);
            return line(points);
        }
    },
    methods: {
        distance(a, b) {
            return Math.sqrt(
                (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)
            );
        },
        generateCenterPoint(source, target) {
            let ct = {
                x: (source.x + target.x) / 2,
                y: (source.y + target.y) / 2
            };
            return {
                x: target.x,
                y: ct.y
            };
        },
        controlPointLayout() {
            let sc = this.generateCenterPoint(
                this.option.source,
                this.option.target
            );
            let tc = this.generateCenterPoint(
                this.option.target,
                this.option.source
            );
            let pointList = [this.option.source];
            let branchs = [];
            //获取同终点线条以及该分支向上深度
            for (let lineId in this.lineData) {
                if (this.lineData[lineId].endId == this.option.source.endId) {
                    if (
                        this.lineData[this.lineOption.resourceId].startPosition
                            .y <= this.lineData[lineId].startPosition.y
                    ) {
                        branchs.push({
                            ...this.lineData[lineId],
                            deepth: this.getDeepth(
                                this.lineData[lineId].startId
                            )
                        });
                    }
                }
            }
            //按深度由大至小排序
            if (branchs.length > 1) {
                branchs.sort(function(b1, b2) {
                    return b2.deepth - b1.deepth;
                });
            }
            //取最大深度分支
            let maxBranch = branchs[0];
            let deepestStartPoint = {
                x: this.lineData[this.lineOption.resourceId].startPosition.x,
                y: maxBranch.startPosition.y
            };
            //插入一个和最大分支相同y值的控制点，将原有起点下移
            //如果图例不是向下延伸则需要修改这里上面的设点方法
            pointList.push(deepestStartPoint);
            //起点和中点之间的转折点
            sc = this.generateCenterPoint(
                deepestStartPoint,
                this.option.target
            );
            //终点和中点的转折点
            tc = this.generateCenterPoint(
                this.option.target,
                deepestStartPoint
            );
            pointList.push(tc);
            pointList.push(sc);
            pointList.push(this.option.target);
            return pointList;
        },
        getDeepth(nodeId) {
            let deepth = 1;
            for (let nodeIds in this.nodeData) {
                if (
                    this.nodeData[nodeIds].resourceId ==
                    this.nodeData[nodeId].parentNodeId
                ) {
                    deepth = this.getDeepth(this.nodeData[nodeIds].resourceId);
                    deepth += 1;
                }
            }
            return deepth;
        }
    },
    mounted() {}
};
</script>

<style lang="scss">
.link {
    stroke: #000;
    stroke-width: 1.5px;
    fill: none;
}
</style>