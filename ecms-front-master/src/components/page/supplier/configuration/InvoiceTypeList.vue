<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <span class="el-form-item__label">发票类型</span>
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column align="center" prop="typeName" label="发票名称">
                </el-table-column>
                <el-table-column align="center" prop="taxRate" label="税率（%）">
                </el-table-column>
                <el-table-column align="center" prop="remark" label="备注">
                </el-table-column>
                <el-table-column align="center" prop="statusDisplay" label="状态">
                </el-table-column>
                <el-table-column align="center" prop="status" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='0'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增发票类型" @close="closeing" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
                <el-form-item label="发票名称:" prop="typeName">
                    <el-input v-model="form.typeName"></el-input>
                </el-form-item>
                <el-form-item label="税率（%）:" prop="taxRate">
                    <el-input v-model="form.taxRate"></el-input>
                </el-form-item>
                <el-form-item label="备注:">
                    <textarea rows="6" cols="33" v-model="form.remark"></textarea>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/purchase/invoice/type/list",
      editVisible: false,
        rules: {
            typeName: [
                { required: true, message: '请输入发票名称', trigger: 'blur' }
            ],
            taxRate: [
                { required: true, message: '请输入税率', trigger: 'blur' }
            ]
        },
      pages: 1,
      form: {
        id: "",
        typeName: "",
        taxRate: "",
        remark: "",
        status: ""
      },
      search: {
        typeName: "",
        taxRate: "",
        remark: "",
        status: "",
        pageNum: 1
      }
    };
  },
  created() {
    this.form.status = 1;
    this.getData();
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        let status = d.status;
        let type = d.type;
        d.statusDisplay = status == 0 ? "正常" : "停用";
        d.type = type == 0 ? "普通用户" : "管理员";
        return d;
      });
    }
  },
  methods: {
      closeing () {
        this.$refs['form'].resetFields()
        this.form.remark = ''
      },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    add() {
      this.editVisible = true;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    updateStatus(index, row) {
      this.$http
        .post("/purchase/invoice/type/status/update", {
          id: row.id,
          status: row.status == 0 ? 1 : 0
        })
        .then(res => {
          if (res.data.code == 1000) {
            row.status = row.status == 0 ? 1 : 0;
            this.$message.success("操作成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
    },
    // 保存编辑
      insert(formName) {
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  this.$http.post('/purchase/invoice/type/insert', this.form).then((res) => {
                      if (res != undefined && res.data.code == 1000) {
                      this.getData();
                      this.editVisible = false;
                      this.$message.success('新增成功');
                  } else {
                      this.$message.error(res.data.message);
                  }
              })
              } else {
                  this.$message.error('带*为必填项');
          return false;
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
</style>
