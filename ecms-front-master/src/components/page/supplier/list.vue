<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button size="small" @click="editVisible = true">导入供应商
          <i class="el-icon-upload el-icon--right"></i>
        </el-button>
        <el-button style="float:right;" size="small" @click="exportSupplier">导出供应商
          <i class="el-icon-download el-icon--right"></i>
        </el-button>
        <el-button style="float:right;" size="small" @click="exportTemplate">
          模板下载
          <i class="el-icon-download el-icon--right"></i>
        </el-button>

        <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
          <el-form-item label="供应商编号:">
            <el-input v-model="search.supplierCode"></el-input>
          </el-form-item>
          <el-form-item label="供应商名称:">
            <el-input v-model="search.supplierName"></el-input>
          </el-form-item>
          <!-- <el-form-item label="区域:">
                        
                    </el-form-item> -->
          <el-button round @click="searchLike">查询</el-button>
        </el-form>
      </div>
      <!-- 编辑弹出框 -->
      <el-dialog title="导入供应商" :visible.sync="editVisible" width="30%">
        <el-form ref="form" label-width="100px">
          <el-form-item label="供应商列表:">
            <input type="file" name="file" @change="setFile" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="importCustomer">导入</el-button>
        </span>
      </el-dialog>
      <div class="handle-box">
        <span class="el-form-item__label">供应商列表</span>
        <el-button round type="primary" @click="add">新建供应商</el-button>
      </div>
      <div style="width:100%">
        <el-table v-loading="loading" :data="tables" border style="width: 100%">
          <el-table-column align="center" prop="id" label="序号" sortable>
            <template slot-scope="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="supplierCode" label="供应商编号">
          </el-table-column>
          <el-table-column align="center" prop="supplierName" label="供应商名称">
          </el-table-column>
          <el-table-column align="center" prop="contact" label="联系人">
          </el-table-column>
          <el-table-column align="center" prop="taxId" label="税号">
          </el-table-column>
          <el-table-column align="center" prop="province" label="省／直辖市">
          </el-table-column>
          <el-table-column align="center" prop="city" label="市">
          </el-table-column>
          <el-table-column align="center" prop="county" label="区／县／县级市">
          </el-table-column>
          <el-table-column align="center" prop="houseNumber" label="门牌号">
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="edit(scope.row)">修改</el-button>
              <!-- </template>

                    <template slot-scope="scope"> -->
              <el-button type="text" @click="detail(scope.row)">明细</el-button>
            </template>

          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import fileDownload from "js-file-download";
export default {
  data() {
    return {
      tableData: [],
      editVisible: false,
      url: "/supplier/like",
      pages: 1,
      loading: false,
      search: {
        status: 1,
        pageNum: 1,
        pageSize: 20
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
    },
    reqfile() {
      return this.$path + "/supplier/getTemplate";
    }
  },
  methods: {
    setFile() {
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
        .post(this.$path + "/supplier/upload", formdata, config)
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
    exportSupplier() {
      this.$http
        .get("/supplier/export/list", { responseType: "blob" })
        .then(res => {
          fileDownload(res.data, "供应商列表.xls");
        });
    },
    exportTemplate() {
      this.$http
        .get("/supplier/export/template", { responseType: "blob" })
        .then(res => {
          fileDownload(res.data, "供应商模板.xls");
        });
    },
    handleChange(value) {},
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    searchLike() {
      this.search.pageNum = 1;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      let _this = this;
      _this.loading = true;
      this.$http
        .post(this.url, this.search)
        .then(res => {
          if (res.data.code == 1000) {
            this.tableData = res.data.data.list;
            this.pages = res.data.data.total;
          }
          _this.loading = false;
        })
        .catch(err => {
          _this.loading = false;
        });
    },
    add() {
      this.$router.push("/supplierInfo");
    },
    edit(row) {
      this.$router.push({
        path: "/supplieredit",
        query: { supplierId: row.id }
      });
    },
    detail(row) {
      this.$router.push({
        path: "/supplierdetail",
        query: { supplierId: row.id }
      });
    }
  },
  watch: {
    $route(to, from) {
      // this.search.pageNum = 1;
      this.getData();
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
