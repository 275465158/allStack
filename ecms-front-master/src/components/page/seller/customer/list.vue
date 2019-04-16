<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/sellerCustomer">客户资料</el-menu-item>
                    <!--<el-menu-item index="/customerAccount">客户账号</el-menu-item>-->
                </el-menu>
            </div>
            <div class="handle-box">
                <el-button size="small" @click="add">新增</el-button>
                <el-button size="small" @click="editVisible = true">导入客户
                    <i class="el-icon-upload el-icon--right"></i>
                </el-button>
                <el-button style="float:right;" size="small" @click="exportCustomer">导出客户
                    <i class="el-icon-download el-icon--right"></i>
                </el-button>
                <el-button style="float:right;" size="small" @click="exportTemplate">模板下载
                    <i class="el-icon-download el-icon--right"></i>
                </el-button>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="客户编号:">
                        <el-input type="number" v-model="search.id"></el-input>
                    </el-form-item>
                    <el-form-item label="公司名称:">
                        <el-input v-model="search.customerName"></el-input>
                    </el-form-item>
                    <el-form-item label="区域:">
                        <el-select v-model="search.addressProvince" clearable placeholder="请选择">
                            <el-option v-for="item in provinces" :key="item" :label="item" :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round @click="searchLike">查询</el-button>
                </el-form>
            </div>
            <el-table v-loading="loading" :data="tableData" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="客户编号" sortable/>
                <el-table-column align="center" prop="customerName" label="公司名称" />
                <!--<el-table-column align="center" prop="contacterName" label="联系人" />-->
                <el-table-column align="center" prop="taxNumber" label="税号" />
                <el-table-column align="center" prop="addressProvince" label="省/直辖市" />
                <el-table-column align="center" prop="addressCity" label="市" />
                <el-table-column align="center" prop="addressCounty" label="区/县/县级市" />
                <el-table-column align="center" prop="addressDetail" label="门牌号" />
                <el-table-column align="center" label="操作" width="100">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.type != '100'" type="text" @click="updateInfo(scope.$index, scope.row)">
                            修改
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="导入客户" :visible.sync="editVisible" width="30%">
            <el-form ref="form" label-width="100px">
                <el-form-item label="客户列表:">
                    <input type="file" name="file" @change="setFile" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="importCustomer">导入</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import fileDownload from "js-file-download";
export default {
  data() {
    return {
      file: "",
      tableData: [],
      provinces: [],
      pages: 1,
      editVisible: false,
        loading: false,
      search: {
        id: "",
        customerName: "",
        addressProvince: "",
        pageNum: 1
      }
    };
  },
  created() {
    this.getData();
  },
  watch: {
    $route(to, from) {
      if (to.path == "/sellerCustomer") {
        this.getData();
      }
    }
  },
  methods: {
    searchEnterFun (e) {
      let keyCode = window.event? e.keyCode:e.which;
      if(keyCode == 13){
          this.searchLike()
      }
    },
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key
      });
    },
    searchLike(){
      this.search.pageNum = 1;
      this.getData();
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
        this.loading = true
      this.$http.post("seller/customer/table", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.pager.list;
          this.pages = res.data.data.pager.total;
          this.provinces = res.data.data.provinces;
        }
        this.loading = false
      })
          .catch(err => {
              this.loading = false
          });
    },
    add() {
      this.$router.push({path: "/sellerCustomerInfo"});
    },
    updateInfo(index, row) {
      this.$router.push({ path: "/sellerCustomerInfo", query: { id: row.id } });
    },
    setFile(event) {
      this.file = event.target.files[0]; //获取文件
      var windowURL = window.URL || window.webkitURL;
      this.file = event.target.files[0];
    },
    importCustomer() {
      let formdata = new FormData();
      formdata.append("file", this.file);
      let config = {
        headers: {
          "Content-Type": "multipart/form-data"
        },
        withCredentials: true
      };
      this.$axios
        .post(this.$path + "/seller/customer/upload", formdata, config)
        .then(res => {
          this.editVisible = false;
          if (res.data.code == 1000) {
            this.$message.success("导入成功");
            this.getData();
          } else {
            this.$message.error(res.data.message);
          }
        });
    },
    exportCustomer() {
      let param = {
        id: this.search.id,
        customerName: this.search.customerName,
        addressProvince: this.search.addressProvince
      };
      this.$http
        .post("/seller/customer/export/search", param, { responseType: "blob" })
        .then(res => {
          fileDownload(res.data, "客户列表.xls");
        });
    },
    exportTemplate() {
      this.$http
        .get("/seller/customer/export/template", { responseType: "blob" })
        .then(res => {
          fileDownload(res.data, "客户模板.xlsx");
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
</style>
