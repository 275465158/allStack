<template>
    <div class="container">
        <div class="handle-box">
        </div>
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
              <el-form-item label="物料编号：">
              <el-input v-model="search.materielCode"></el-input>
              </el-form-item>
              <el-form-item label="物料名称：">
                  <el-input v-model="search.materielName"></el-input>
              </el-form-item>
              <el-button round @click="getData">查询</el-button>
          </el-form>
        </div>
        <el-table border style="width:100%" :data="tables">
            <el-table-column label="出库单号" prop="materielProduceNumber"></el-table-column>
            <el-table-column label="生产合同编号" prop="productionPlan.productionNumber"></el-table-column>
            <el-table-column label="物料名称" prop="productionPlan.productName"></el-table-column>
            <el-table-column label="数量" prop="productionPlan.materielNum"></el-table-column>
            <el-table-column label="计划发货时间" prop="productionPlan.planProductDate"></el-table-column>
            <el-table-column label="操作人"></el-table-column>
            <el-table-column label="发货状态" prop="materielStatus"></el-table-column>
            <el-table-column label="操作"><template slot-scope="scope">
              <span v-if="scope.row.materielStatus=='齐备'">
                <el-button @click="picking(scope.row)">生产发料</el-button>
              </span>
              <el-button @click="edit(scope.row)">明细</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
          </el-pagination>
        </div>
      </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/materiel/produce/list",
      pages: 1,
      search: {
        status: 1,
        pageNum: 1,
        repertoryId: 0
      }
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        d.materielStatus =
          d.materielStatus == "1"
            ? d.pickStatus == 1 ? "完成领料" : "齐备"
            : "缺货";
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
    addDetail(row) {
      this.$router.push({
        path: "/materielProduceInfo",
        query: row
      });
    },
    edit(row) {
      this.$router.push({
        path: "/materielProduceInfo",
        query: { id: row.id, repertoryId: this.search.repertoryId }
      });
    },
    picking(row) {
      this.$http.post("/materiel/produce/picking", { id: row.id }).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("领料成功");
        }
      });
    }
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