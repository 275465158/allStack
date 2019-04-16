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
              <el-form-item label="物料编号：">
              <el-input v-model="search.materielCode"></el-input>
              </el-form-item>
              <el-form-item label="物料名称：">
                  <el-input v-model="search.materielName"></el-input>
              </el-form-item>
              <el-button round @click="getData">查询</el-button>
          </el-form>
        </div>
        <div class="handle-box">
          <span class="el-form-item__label">出库列表</span>
          <el-button round type="primary" @click="add">新建发货单</el-button>
        </div>
        <el-table border style="width:100%" :data="tables">
          <el-table-column label="全选打印"></el-table-column>
          <el-table-column label="发货单号" prop="deliveryCode"></el-table-column>
          <el-table-column label="合同编号" prop="orderId"></el-table-column>
          <el-table-column label="数量" prop="totalQty"></el-table-column>
          <el-table-column label="计划发货时间" prop="planDate"></el-table-column>
          <el-table-column label="操作人" prop="preparedBy"></el-table-column>
          <el-table-column label="发货状态" prop="deliveryStatus"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <span v-if="scope.row.deliveryStatus=='取消'">
                取消
              </span>
              <span v-if="scope.row.deliveryStatus=='待确认'">
                <el-button @click="deliveryComplete(scope.row)">发货</el-button>
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
    </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/materiel/delivery/list",
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
        path: "/deliveryInfo",
        query: { repertoryId: this.search.repertoryId }
      });
    },
    edit(row) {
      this.$router.push({
        path: "/deliveryInfo",
        query: { deliveryId: row.id, repertoryId: this.search.repertoryId }
      });
    },
    deliveryComplete(row) {
      this.$confirm("是否确认发货?", "确认提示", {
        confirmButtomText: "确认",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          this.$http
            .post("/materiel/delivery/saveOrUpdate", {
              id: row.id,
              deliveryStatus: "完成"
            })
            .then(res => {
              this.$message.success("完成发货");
              this.getData();
            });
          this.$http
            .post("/materiel/delivery/deliveryById", { deliveryId: row.id })
            .then(res => {});
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消发货"
          });
        });
    }
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