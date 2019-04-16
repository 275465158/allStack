<template>
  <div class="table">
    <div class="container" :default-active="$route.path">
      <el-form @keyup.enter.native="searchEnterFun" :inline="true" class="demo-form-inline">
        <el-form-item label="验收名称:">
          <el-input v-model="search.name" @keyup.native="select()"></el-input>
        </el-form-item>
        <el-form-item label="制作人:">
          <el-input v-model='search.owner' @keyup.native="select()"></el-input>
        </el-form-item>
        <el-button @click="select()">查询</el-button>

      </el-form>
      <hr class="marginTop" />
      <!-- style="margin-bottom:10px;" -->

      <div class="handle-box">
        <span class="text">验收列表</span>
        <el-button @click="add">新增</el-button>
      </div>
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column align="center" prop="id" label="验收编号" sortable></el-table-column>
        <el-table-column align="center" prop="name" label="验收名称"></el-table-column>
        <el-table-column align="center" prop="owner" label="制作人"></el-table-column>
        <el-table-column align="center" prop="created" label="制作日期">
          <template slot-scope="tableData">
            {{tableData.row.created|formatDate}}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.$index, scope.row)">修改</el-button>
            <!-- 发送编辑请求 -->
            <el-button type="text" @click="detail(scope.$index, scope.row)">明细</el-button>
            <el-button type="text" @click="del(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
      <div class="pagination">
        <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import { formatDate } from "../../../common/date.js";
export default {
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd");
    }
  },
  data() {
    return {
      tableData: [],
      pages: 1,
      url: "/productAcceptance/list",
      search: {
        id: "",
        name: "",
        owner: "",
        pageNum: 1,
        status: 1
      }
    };
  },
  methods: {
    searchEnterFun(e) {
      let keyCode = window.event ? e.keyCode : e.which;
      if (keyCode == 13) {
        this.select();
      }
    },
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    add() {
      this.$router.push({
        path: "/addcheck"
      });
    },
    select() {
      this.url = "/productAcceptance/selectAccpetance";
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    del(index, row) {
      this.$confirm("是否删除当前验收标准, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http
            .post("/productAcceptance/deleteAccpetance", {
              id: row.id
            })
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
                this.getData();
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    detail(index, row) {
      this.$router.push({
        path: "/checkdetail",
        query: {
          checkId: row.id,
          name: row.name,
          owner: row.owner
        }
      });
    },

    edit(index, row) {
      this.$router.push({
        path: "/editcheck",
        query: {
          checkId: row.id,
          name: row.name,
          owner: row.owner,
          id: row.id
        }
      });
    }
  },
  created() {
    this.getData();
  },
  computed: {},
  watch: {
    $route(to, from) {
      if (to.path == "/checkstandard") {
        this.getData();
      }
    }
  }
};
</script>
<style scoped>
.marginTop {
  margin-top: 10px;
  margin-bottom: 10px;
}
.handle-box {
  margin-bottom: 20px;
}
</style>

