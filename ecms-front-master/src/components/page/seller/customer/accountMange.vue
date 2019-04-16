<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <!-- <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/sellerCustomer">客户资料</el-menu-item>
          <el-menu-item index="/customerAccount">客户账号</el-menu-item>
        </el-menu> -->
      </div>

      <div class="handle-box">
        <el-form :inline="true" :model="search" class="demo-form-inline">
          <el-form-item label="客户账号：">
            <el-input v-model="search.materialCode"></el-input>
          </el-form-item>
          <el-form-item label="公司名称：">
            <el-input v-model="search.materialName"></el-input>
          </el-form-item>
          <el-form-item label="状态:">
            <el-select v-model="search.supplierId" clearable placeholder="请选择">
              <el-option v-for="item in option" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-button round @click="searchLike" type="primary">查询</el-button>
        </el-form>
      </div>
      <div class="handle-box">
        <el-button round @click="add">新增</el-button>
      </div>
      <el-table :data="tableData" border style="width:100%">
        <el-table-column prop="id" label="序号" sortable>
          <template slot-scope="scope">
            {{scope.$index+1}}
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="客户名称">
        </el-table-column>
        <el-table-column prop="userAccount" label="客户账号">
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            {{scope.row.status|formatStatus}}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button v-if="scope.row.type != '100'" size="small" @click="updateStatus(scope.$index, scope.row)">
              <font v-if="scope.row.status=='0'">失效</font>
              <font v-else>生效</font>
            </el-button>
            <el-button size="small" @click="delivery(scope.row)">重置密码</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
        </el-pagination>
      </div>
      <el-dialog title="新增客户账号" :visible.sync="addVisible" width="60%">
        <el-form ref="addForm" :model="addForm" label-width="100px">
          <el-form-item label="客户账号:">
            <el-input v-model="addForm.userAccount"></el-input>
          </el-form-item>
          <el-form-item label="公司名称:">
            <el-select v-model="addForm.customerId" clearable placeholder="请选择公司名称">
              <el-option v-for="item in companyList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="addForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="状态:">
            <el-select v-model="addForm.status" clearable placeholder="请选择状态">
              <el-option v-for="item in option" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="insert">保存</el-button>
          <el-button @click="addVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  filters: {
    formatStatus(row) {
      return row == 0 ? "正常" : "失效";
    }
  },
  data() {
    return {
      tableData: [],
      url: "/purchaseOrders/getStayInboundPage",
      pages: 1,
      option: [
        {
          label: "停用",
          value: 0
        },
        {
          label: "启用",
          value: 1
        }
      ],
      search: {
        // status: 1,
        pageNum: 1,
        pageSize: 20,
        type: 2 //2为客户
      },
      searchCompany: {
        status: 1,
        pageNum: 1,
        pageSize: 20,
        type: 2 //2为客户
      },
      repertoryName: "",
      //对话框现实控制
      addVisible: false,
      addForm: {
        userAccount: "",
        userName: "",
        password: "",
        customerId: "",
        // companyId: "",
        type: 2, //2为客户
        status: 1,
        pageNum: 1,
        pageSize: 20
      },
      companyList: []
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    searchLike(){
        this.search.pageNum = 1;
        this.getData();
    },
    insert() {
      // this.addForm.customerId;
      this.$http.post("/user/insertCustomer", this.addForm).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("新增成功");
          this.addVisible = false;
        }
      });
    },
    add() {
      this.addVisible = true;
    },
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key
      });
    },
    handleChange(value) {},
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    updateStatus(index, row) {
      this.$http
        .post("/user/status/update", { id: row.id, status: row.status })
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            row.status = row.status == 0 ? 10 : 0;
            this.$message.success("操作成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
    },
    getData() {
      this.$http.post("seller/customer/table", this.searchCompany).then(res => {
        if (res != undefined && res.data.code == 1000) {
          //   console.log("res.data.data.list", res.data.data.pager.list);
          for (
            let index = 0;
            index < res.data.data.pager.list.length;
            index++
          ) {
            var obj = {
              label: "",
              value: ""
            };
            obj.value = res.data.data.pager.list[index].id;
            obj.label = res.data.data.pager.list[index].customerName;
            this.companyList.push(obj);
          }
        }
      });
      this.$http.post("/user/list", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          //   console.log("res.data.", res.data.data);
          this.tableData = res.data.data.list;
        }
      });
    }
  },
  watch: {
    $route(to, from) {
      if (to.path == "/customerAccount") {
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
</style>
