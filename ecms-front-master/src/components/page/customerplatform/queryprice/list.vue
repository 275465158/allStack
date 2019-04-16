<template>
  <div class="container">
    <el-button size="small" @click="add()">新增</el-button>
    <div class="handle-box">
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="询价单号:">
          <el-input v-model="search.queryCode"></el-input>
        </el-form-item>

        <el-form-item label="供应商:">
          <el-input v-model="search.supplierName"></el-input>
        </el-form-item>
        <el-form-item label="询价状态:">
          <el-select v-model="search.status">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="询价日期:">
          <div>
            <template>
              <el-date-picker v-model="search.queryDate" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
              </el-date-picker>

            </template>
          </div>
        </el-form-item>

      </el-form>
    </div>

    <el-table :data="tables" border style="width: 100%">
      <el-table-column prop="id" label="序号" sortable>
      </el-table-column>
      <el-table-column prop="queryCode" label="询价编号">
      </el-table-column>
      <el-table-column prop="supplierName" label="供应商">
      </el-table-column>
      <el-table-column prop="repertoryDepartmentId" label="销售代表">
      </el-table-column>
      <el-table-column prop="queryDate" label="询价日期">
      </el-table-column>
      <el-table-column prop="offerDeadline" label="报价截止日期">
      </el-table-column>
      <el-table-column prop="created" label="报价日期">
      </el-table-column>
      <el-table-column prop="created" label="报价有效期">
      </el-table-column>
      <el-table-column prop="queryPriceFlag" label="询价状态" :formatter="checkQueryPriceFlag">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="closeQueryPrice(scope.row)" v-if="scope.row.queryPriceFlag===1">关闭询价</el-button>
          <el-button size="small" @click="queryPriceDetail(scope.$index, scope.row)">详情</el-button>
          <el-button size="small" @click="transformOrder(scope.row)" v-if="scope.row.queryPriceFlag===2">转为订单</el-button>
        </template>
        <!--  -->
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
      tables: [],
      pages: 1,
      search: {
        queryCode: "",
        supplierName: "",
        queryDate: "",
        status: 1,
        pageNum: 1
      },
      closeInfo: {
        id: 0,
        queryPriceFlag: 0
      },
      //是否关闭询价单看queryPriceFlag，
      //报价状态什么的看offerPriceFlag
      options: [
        {
          label: "已报价",
          value: 2
        },
        {
          label: "正在询价",
          value: 1
        },
        {
          label: "未报价",
          value: 0
        }
      ]
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    checkQueryPriceFlag(row) {
      if (row.queryPriceFlag == 0) {
        return "询价已关闭";
      } else {
        return "正在询价";
      }
    },
    closeQueryPrice(row) {
      this.closeInfo.id = row.id;
      this.$http.post("/queryPrice/update", this.closeInfo).then(res => {
        if (res.data.code == 1000) {
          this.$message.success("关闭成功");
          this.getData();
        }
      });
    },
    transformOrder() {},
    // handleChange(value) {},
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http.post("/queryPrice/list", this.search).then(res => {
        if (res.data.code == 1000) {
          this.tables = res.data.data.list;
          // console.log("this.tables",this.tables);
          this.pages = res.data.data.total;
        }
      });
    },
    addaddress() {},
    add() {
      this.$router.push("/addqueryprice");
    },

    queryPriceDetail(index, row) {
      this.$router.push({
        path: "/detialQueryPrice",
        query: { id: row.id }
      });
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
    $route(to, from) {
      if (to.path == "/queryprice") {
        this.getData();
      }
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
</style>
