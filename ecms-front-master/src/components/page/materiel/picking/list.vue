<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/materielList">物料列表</el-menu-item>
          <el-menu-item index="/entryList">待入库</el-menu-item>
          <el-menu-item index="/storageList">货架管理</el-menu-item>
          <el-menu-item index="/materielDetailList">出入库明细</el-menu-item>
          <el-menu-item index="/pickingList">出库管理</el-menu-item>
          <el-menu-item index="/deliveryList">发货管理</el-menu-item>
          <!--<el-menu-item index="/materielProduceList">生产发料</el-menu-item>-->
        </el-menu>
      </div>
      <div class="handle-box">
        <el-form :inline="true" :model="search" class="demo-form-inline">
          <el-form-item label="出库单号：">
            <el-input v-model="search.pickingCode"></el-input>
          </el-form-item>
          <el-form-item label="操作人：">
            <el-input v-model="search.preparedBy"></el-input>
          </el-form-item>
          <el-form-item label="领料人：">
            <el-input v-model="search.pickedBy"></el-input>
          </el-form-item>
          <el-form-item label="出库时间:">
            <el-date-picker
                    v-model="search.dateParam"
                    type="daterange"
                    align="right"
                    unlink-panels
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions2">
            </el-date-picker>
          </el-form-item>
          <el-button round @click="getData" type="primary">搜索</el-button>
          <el-button round @click="clearData">清空</el-button>
        </el-form>
      </div>
      <div class="handle-box">
        <span class="el-form-item__label">出库列表</span>
        <el-button round type="primary" @click="add">新建出库单</el-button>
        <el-button round @click="printOrder">出库单打印</el-button>
      </div>
      <el-table border style="width:100%" :data="tables" ref="multipleTable" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="序号" sortable>
        </el-table-column>
        <el-table-column label="出库单号" prop="pickingCode"></el-table-column>
        <el-table-column label="生产单号" prop="productionId"></el-table-column>
        <el-table-column label="领料用途" prop="useage"></el-table-column>
        <el-table-column label="出库时间" prop="pickDate"></el-table-column>
        <el-table-column label="操作人" prop="preparedBy"></el-table-column>
        <el-table-column label="领料人" prop="pickedBy"></el-table-column>
        <el-table-column label="出库状态" prop="pickingStatus"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <span v-if="scope.row.pickingStatus=='取消'">
              取消
            </span>
            <span v-if="scope.row.pickingStatus=='待确认'">
              <el-button @click="pickingComplete(scope.row)">出库</el-button>
            </span>
            <el-button @click="edit(scope.row)">明细</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
        </el-pagination>
      </div>
      <div id="subOutputRank-print" style="display:none">
        <div class="handle-box">
          <el-form label-width="100px" :model="pickingForm">
            <el-row :gutter="30">
              <el-col :span="12">
                <el-form-item label="出库单号：">
                  {{this.pickingForm.id}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="领料用途：">
                  {{this.pickingForm.useage}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="30">
              <el-col :span="12">
                <el-form-item label="操作人：">
                  {{this.pickingForm.preparedBy}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="领料人：">
                  {{this.pickingForm.pickedBy}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="30">
              <el-col :span="12">
                <el-form-item label="出库时间：">
                  {{this.pickingForm.pickDate}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="出库状态：">
                  {{this.pickingForm.pickingStatus}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <el-table border style="width:100%" :data="tablePrintData">
          <el-table-column label="序号" prop="materielInventory.id"></el-table-column>
          <el-table-column label="物料编号" prop="materielInventory.materiel.materialBom.materialCode"></el-table-column>
          <el-table-column label="物料名称" prop="materielInventory.materiel.materialBom.materialName"></el-table-column>
          <el-table-column label="原图材料" prop="materielInventory.materiel.materialBom.originalMaterial"></el-table-column>
          <el-table-column label="参数列表">
            <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
              <template slot-scope="scope">
                {{scope.row.materielInventory.materiel.materialBom.materialParameter[param.parameterName]}}
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column label="数量" prop="qty">
            <template slot-scope="scope">
              <span v-if="!scope.row.editFlag">{{scope.row.qty}}</span>
              <div class="el-input" v-if="scope.row.editFlag">
                <input class="el-input__inner" type="text" v-model="scope.row.qty" @input="handleQtyChange(scope.row, scope.$index)" />
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      tablePrintData:[],
      url: "/materiel/picking/list",
      infoUrl: "/materiel/picking/info",
      pages: 1,
        search: {
            pickingCode:'',
            preparedBy:'',
            pickedBy:'',
            dateParam:'',
            status: 1,
            pageNum: 1,
            repertoryId: 0
        },
        printSearch:{
            pickingId: 0,
            repertoryId: 0,
            pageNum: 1
        },
        pickerOptions2: {
            shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近一个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    picker.$emit('pick', [start, end]);
                }
            }, {
                text: '最近三个月',
                onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    picker.$emit('pick', [start, end]);
                }
            }]
        },
        //属性列表
        searchParams: [],
        pickingForm: {},
        statusOptions: ["待确认", "完成", "取消"],
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        return d;
      });
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key,
        query: { repertoryId: this.search.repertoryId }
      });
    },
    handleChange(value) {
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    getData() {
      if (this.$route.query.repertoryId != null) {
        this.search.repertoryId = this.$route.query.repertoryId;
      } else {
        //this.$router.push("/repertoryList");
      }
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    add() {
      this.$router.push({
        path: "/pickingInfo",
        query: { repertoryId: this.search.repertoryId }
      });
    },
    edit(row) {
      this.$router.push({
        path: "/pickingInfo",
        query: { pickingId: row.id, repertoryId: this.search.repertoryId }
      });
    },
    clearData() {
        this.search.pickingCode = '',
            this.search.preparedBy = '',
            this.search.pickedBy = '',
            this.search.dateParam = ''
    },
    pickingComplete(row) {
      this.$confirm("是否确认出库?", "确认提示", {
        confirmButtomText: "确认",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          this.$http
            .post("/materiel/picking/saveOrUpdate", {
              id: row.id,
              pickingStatus: "完成"
            })
            .then(res => {
              this.$message.success("完成出库");
              this.getData();
            });
          this.$http
            .post("/materiel/picking/pickedById", { id: row.id })
            .then(res => {});
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消出库"
          });
        });
    },
    printOrder(){
        if(this.multipleSelection.length > 1){
            this.$message.success("只能选择一条记录打印");
            return false;
        } else if(this.multipleSelection.length == 1){
            // 打印
            let subOutputRankPrint = document.getElementById('subOutputRank-print');
            let newContent =subOutputRankPrint.innerHTML;
            let oldContent = document.body.innerHTML;
            document.body.innerHTML = newContent;
            window.print();
            window.location.reload();
            document.body.innerHTML = oldContent;
            return false;
        }else{
            this.$message.success("请选择要打印的记录");
        }
    },
    handleSelectionChange(val) {
        this.multipleSelection = val;
        this.printSearch.pickingId = this.multipleSelection[0].id
        this.printSearch.repertoryId = this.search.repertoryId
        this.$http.post("/materiel/picking/detail/listByPickingId", this.printSearch).then(res => {

            if (res != undefined && res.data.code == 1000) {
                this.tablePrintData = res.data.data.list;
            }
        });
        this.$http.post("/material/paramNames").then(res => {
            if (res != undefined && res.data.code == 1000) {
                this.searchParams = res.data.data;
            }
        });
        this.$http.post(this.infoUrl, this.printSearch).then(res => {
            if (res != undefined && res.data.code == 1000) {
                this.pickingForm = res.data.data;
            }
        });
    },
  },
  watch: {
    $route: "getData"
  }
};
</script>
<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
hr {
  border-top: 1px;
}
.marginTop {
  margin-top: 10px;
  margin-bottom: 5px;
}
.marginBottom {
  margin-top: 5px;
  margin-bottom: 10px;
}
.text {
  font-size: 12px;
  color: #606266;
  margin-right: 30px;
}
</style>