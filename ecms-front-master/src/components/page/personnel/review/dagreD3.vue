<template>
    <div class="dagre-graph-container" :id="containerId">
        <div class="zoom-div">
            <el-button @click="zoomCtrl('in')">缩小</el-button>
            <el-button @click="zoomCtrl('out')">扩大</el-button>
            <p style="color: #ff0000;">红色：代表不同意</p>
            <p style="color: #3d85c6;">蓝色：代表流程走向</p>
            <p style="color: #6aa84f;">绿色：代表同意</p>
        </div>
        <svg class="dagre">
            <g class="container"></g>
        </svg>
        <el-dialog title="评审发起" :visible.sync="dialogFormVisible" width="500px">
            <el-form :model="form" :label-width="formLabelWidth">
                <el-form-item label="评审名称">
                    <el-input class="input" v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="流程状态">
                    <el-input class="input" v-model="form.status"></el-input>
                </el-form-item>
                <el-form-item label="评审发起人">
                    <el-select v-model="form.value">
                        <el-option v-for="item in form.region" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="通知人员">
                    <el-select v-model="form.value1">
                        <el-option v-for="item in form.region1" :key="item.value1" :label="item.label" :value="item.value1">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="处置措施" :visible.sync="dialogFormVisible1" width="500px">
            <el-form :model="form1" :label-width="formLabelWidth">
                <el-form-item label="评审名称">
                    <el-input class="input" v-model="form1.name"></el-input>
                </el-form-item>
                <el-form-item label="评审人">
                    <el-select v-model="form1.value">
                        <el-option v-for="item in form1.region" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible1 = false">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="原因分析" :visible.sync="dialogFormVisible2" width="500px">
            <el-form :model="form2" :label-width="formLabelWidth">
                <el-form-item label="评审名称">
                    <el-input class="input" v-model="form2.name"></el-input>
                </el-form-item>
                <el-form-item label="评审人">
                    <el-radio-group v-model="form2.radio">
                        <el-radio :label="1">责任人</el-radio>
                        <el-radio :label="2">责任部门主管</el-radio>
                        <el-radio :label="3">责任部门</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible2 = false">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
/**
 * 流程图
 */
import * as d3 from "d3";
import dagreD3 from "dagre-d3";
let container = null;
export default {
  name: "DagreGraph",
  props: ["nodes", "edges", "types"],
  data() {
    return {
      id: "",
      renderer: null,
      graph: null,
      direction: "LR",
      directions: [
        {
          prop: "LR",
          label: "从左至右"
        },
        {
          prop: "RL",
          label: "从右至左"
        },
        {
          prop: "TB",
          label: "从上至下"
        },
        {
          prop: "BT",
          label: "从下至上"
        }
      ],
      zoom: null,
      containerId: "",
      width: 0,
      height: 0,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      formLabelWidth: "120px",
      form: {
        name: "",
        status: "",
        value: "",
        value1: "",
        region: [
          {
            value: 0,
            label: "星爷"
          },
          {
            value: 1,
            label: "张学友"
          },
          {
            value: "2",
            label: "亚索"
          }
        ],
        region1: [
          {
            value: 0,
            label: "梁朝伟"
          },
          {
            value: 1,
            label: "彭于晏"
          }
        ]
      },
      form1: {
        name: "",
        region: [
          {
            value: 0,
            label: "dzz"
          },
          {
            value: 1,
            label: "刘星"
          }
        ]
      },
      form2: {
        name: "",
        radio: 1
      }
    };
  },
  created() {
    this.containerId = this.uuid();
    console.log(this.containerId);
    this.graph = new dagreD3.graphlib.Graph()
      .setGraph({
        rankdir: this.direction
      })
      .setDefaultEdgeLabel(function() {
        return {};
      });
  },
  methods: {
    uuid() {
      function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
          .toString(16)
          .substring(1);
      }
      return (
        s4() +
        s4() +
        "-" +
        s4() +
        "-" +
        s4() +
        "-" +
        s4() +
        "-" +
        s4() +
        s4() +
        s4()
      );
    },
    zoomCtrl(symbal) {
      let scale = symbal === "out" ? 1.1 : 0.8;
      const svg = d3.select(this.$el).select("svg.dagre");
      this.zoom.scaleBy(svg, scale);
    },
    /**
     * @description control the canvas zoom to up or down
     */
    zoomed() {
      d3
        .select(this.$el)
        .select("g.container")
        .attr("transform", d3.event.transform);
    },
    /**
     * @description 画节点
     */
    strokeNodes() {
      // 获取之前的nodes缓存并清除
      let nodes = this.graph.nodes();
      if (nodes.length) {
        nodes.forEach(item => {
          this.graph.removeNode(item);
        });
      }

      //通过operator来画shape(BranchPythonMapOperator: 分支； JoinOperator：合流)
      this.nodes.forEach(item => {
        let state = item.state ? item.state : "no-status";
        let shape = "rect";
        if (item.value.operator === "BranchPythonMapOperator") {
          shape = "ellipse";
        } else if (item.value.operator === "JoinOperator") {
          shape = "circle";
        } else if (item.value.operator === "Sponsor") {
          // shape = 'Sponsor'
        }
        this.graph.setNode(item.id, {
          label: item.value.label,
          shape: shape,
          class: item.value.operator + " dagre " + state
        });
      });
      this.renderer(container, this.graph);
    },
    /**
     * @description 画线
     */
    strokeEdges() {
      //一个脚本节点时：不渲染eage
      if (this.nodes.length > 1) {
        this.edges.forEach(item => {
          if (item.label) {
            this.graph.setEdge(item.from, item.to, { label: item.label });
          } else {
            this.graph.setEdge(item.from, item.to, {
              style: "stroke: " + item.color + "; fill: none;"
            });
          }
        });
        this.renderer(container, this.graph);
      }
    }
  },
  mounted() {
    console.log(this.containerId);
    this.width = document.getElementById(this.containerId).clientWidth;
    this.height = document.getElementById(this.containerId).clientHeight;
    // eslint-disable-next-line
    this.renderer = new dagreD3.render();
    console.log(this.$el);
    let svg = d3
      .select(this.$el)
      .select("svg.dagre")
      .attr("width", this.width)
      .attr("height", this.height);

    let svgv = document.getElementsByClassName("dagre")[0];
    console.log(svgv);
    let _this = this;
    svgv.addEventListener("click", function(e) {
      e.preventDefault();
      console.log(_this.types);
      switch (e.target.textContent) {
        case "来料不合格评审发起":
          _this.form.name = "来料不合格评审发起";
          _this.form.status = "流程开始";
          _this.dialogFormVisible = true;
          break;
        case "纠正及处置措施":
          _this.form1.name = "纠正及处置措施";
          _this.dialogFormVisible1 = true;
          break;
        case "不合格原因分析":
          _this.form2.name = "不合格原因分析";
          _this.dialogFormVisible2 = true;
          break;
      }
    });

    let lineGenerator = d3
      .line()
      .x(function(d) {
        return d[0];
      })
      .y(function(d) {
        return d[1];
      });
    container = svg.select("g.container");
    // transform
    const transform = d3.zoomIdentity
      .translate(this.width / 12, this.height / 8)
      .scale(0.8);
    this.zoom = d3
      .zoom()
      .scaleExtent([1 / 2, 8])
      .on("zoom", this.zoomed);
    container
      .transition()
      .duration(750)
      .call(this.zoom.transform, transform);
    // svg.call(this.zoom)

    this.strokeNodes();
    this.strokeEdges();
  },
  watch: {
    nodes() {
      this.strokeNodes();
    },
    edges() {
      this.strokeEdges();
    },
    direction() {
      this.graph.setGraph({
        rankdir: this.direction
      });
      this.renderer(container, this.graph);
    }
  }
};
</script>
<style lang="scss">
.edgePath path {
  stroke: #333;
  fill: #333;
  stroke-width: 1.5px;
}
/*图表变量*/
$fail: #f77d6b;
$success: #61b2e4;
$running: #87d86f;
$skipped: #faec91;
$queued: #43e3ed;
$retry: #f8b96c;
$no-status: #fff;
$upstream_failed: rgb(163, 108, 108);
/**************** dagre 节点图************************/
g.node.dagre {
  tspan {
    fill: #fff;
    cursor: pointer;
  }
  &.no-status {
    rect {
      stroke: #333;
      fill: #fff;
    }
    ellipse {
      stroke: #333;
      fill: #fff;
    }
    circle {
      stroke: #333;
      fill: #fff;
    }
    tspan {
      fill: #333;
    }
  }
  &.success {
    rect {
      stroke: #fff;
      fill: $success;
    }
    ellipse {
      stroke: #fff;
      fill: $success;
    }
    circle {
      stroke: #fff;
      fill: $success;
    }
  }
  &.failed {
    rect {
      stroke: #fff;
      fill: $fail;
    }
    ellipse {
      stroke: #fff;
      fill: $fail;
    }
    circle {
      stroke: #fff;
      fill: $fail;
    }
  }
  &.upstream_failed {
    rect {
      stroke: #fff;
      fill: $upstream_failed;
    }
    ellipse {
      stroke: #fff;
      fill: $upstream_failed;
    }
    circle {
      stroke: #fff;
      fill: $upstream_failed;
    }
  }
  &.running {
    rect {
      stroke: #fff;
      fill: $running;
    }
    ellipse {
      stroke: #fff;
      fill: $running;
    }
    circle {
      stroke: #fff;
      fill: $running;
    }
  }
  &.skipped {
    rect {
      stroke: #fff;
      fill: $skipped;
    }
    ellipse {
      stroke: #fff;
      fill: $skipped;
    }
    circle {
      stroke: #fff;
      fill: $skipped;
    }
  }
  &.queued {
    rect {
      stroke: #fff;
      fill: $queued;
    }
    ellipse {
      stroke: #fff;
      fill: $queued;
    }
    circle {
      stroke: #fff;
      fill: $queued;
    }
  }
  &.BashOperator {
    &:hover {
      & > rect {
        cursor: pointer;
        fill: #7cc9fa;
      }
    }
  }
  &.BranchPythonMapOperator {
    &:hover {
      & > ellipse {
        cursor: pointer;
        fill: #c52bd3;
      }
    }
  }
  &.JoinOperator {
    &:hover {
      & > circle {
        cursor: pointer;
        fill: #0beb8d;
      }
    }
  }
}
.zoom {
  margin-left: 40px;
}
.input {
  width: 215px;
}
</style>