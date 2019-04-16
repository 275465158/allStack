<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="询价编号:">
          <el-input v-model="search.queryCode"></el-input>
        </el-form-item>
        <el-form-item label="客户名称:">
          <el-input v-model="search.queryCode"></el-input>
        </el-form-item>
        <el-form-item label="询价日期:">
          <template>
            <el-date-picker v-model="search.queryDate" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>

          </template>
        </el-form-item>
        <el-form-item label="询价状态:">
          <el-select v-model="search.offerPriceFlag"></el-select>

        </el-form-item>

        <el-form-item label="合同编号:">
          <el-input v-model="search.repertoryCode"></el-input>
        </el-form-item>

        <el-form-item label="报价人:">
          <el-input v-model="search.repertoryCode"></el-input>
        </el-form-item>

        <el-form-item label="报价日期:">
          <template>
            <el-date-picker v-model="search.of" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </template>
        </el-form-item>

        <el-button round @click="searchLike">查询</el-button>

      </el-form>
    </div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="序号" sortable>
      </el-table-column>
      <el-table-column prop="offerCode" label="报价单号">
      </el-table-column>
      <el-table-column prop="queryCode" label="询价单号">
      </el-table-column>
      <el-table-column prop="contractNumber" label="合同编号">
      </el-table-column>
      <el-table-column prop="customerName" label="客户名称">
      </el-table-column>
      <el-table-column prop="repertoryManager" label="报价人">
      </el-table-column>
      <el-table-column prop="queryDate" label="询价日期">
      </el-table-column>

      <el-table-column prop="offerPriceFlag" label="询价进度">
        <template slot-scope="tableData">
          {{tableData.row.offerPriceFlag|filterMethod}}
        </template>
      </el-table-column>
      <el-table-column prop="contractProgress" label="合同评审进度">
      </el-table-column>
      <el-table-column prop="repertoryType" label="产品数量">
      </el-table-column>
      <el-table-column prop="repertoryManager" label="总价">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- <el-button size="small" @click="edit(scope.row)">明细</el-button> -->
          <el-button size="small" @click="edit(scope.row)" v-if="scope.row.offerPriceFlag===1">报价</el-button>
          <el-button size="small" @click="edit(scope.row)">转为合同评审</el-button>

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
      url: "/offerPrice/list",
      pages: 1,
      offerPrice: {
        customerName: "",
        deliveryDate: "",
        plannedDeliveryDate: "",
        contractNumber: "",
        queryPriceProgress: "", //这个应该就是询价状态
        contractReviewProgress: ""
      },
      search: {
        // supplierName:"",
        // queryCode:"",
        // offerPriceFlag:"",
        // deliveryDate:new Date(),
        // queryDate: new Date(),
        // offerDeadline: new Date(),
        status: 1,
        pageNum: 1
      }
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  filters: {
    filterMethod(row) {
      if (row == 1) {
        return "正在询价";
      } else if (row == 0) {
        return "报价已拒绝";
      } else if (row == 2) {
        return "报价完成";
      }
    }
  },
  methods: {
    handleChange(value) {},
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    searchLike(){
        this.search.pageNum = 1;
        this.getData();
    },

    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res.data.code == 1000) {
          console.log(res.data.data.list);

          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    addaddress() {},
    add() {
      this.$router.push("/repertoryInfo");
    },
    edit(row) {
      // console.log(row.queryPriceId);

      this.$router.push({
        path: "/editoffer",
        query: { id: row.id, queryId: row.queryPriceId }
      });
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
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
</style>
