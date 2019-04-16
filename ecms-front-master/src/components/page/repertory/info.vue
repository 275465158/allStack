<template>
    <el-form v-loading="loading" :model="form" ref="form" :rules="rules" label-width="100px">
        <el-input type="hidden" v-model="form.repertoryId"></el-input>
        <el-input type="hidden" v-model="form.status" value="1"></el-input>
        <el-row :gutter="30">
            <el-col :span="12">
                <el-form-item label="仓库名称:" prop="repertoryName">
                    <el-input v-model="form.repertoryName"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="仓库编码:" prop="repertoryCode">
                     <el-input v-model="form.repertoryCode"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row :gutter="30">
            <el-col :span="12">
                <el-form-item label="仓库类型:" prop="repertoryType">
                    <el-select v-model="form.repertoryType" clearable placeholder="请选择">
                        <el-option
                        v-for="item in repertoryType"
                        :key="item.value"
                        :label="item.text"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="所属部门:" prop="repertoryDepartmentId">
                    <el-select v-model="form.repertoryDepartmentId" clearable placeholder="请选择">
                        <el-option
                        v-for="item in department"
                        :key="item.id"
                        :label="item.departmentName"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="仓库管理员:" prop="repertoryManager">
            <el-select v-model="form.repertoryManager" value-key="id" multiple placeholder="请选择">
                <el-option
                        v-for="item in employee"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                        </el-option>
            </el-select>
        </el-form-item>
        <el-form-item size="large">
            <el-button @click="save('form')">保存</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
export default {
  data() {
    return {
      repertoryType: [
        { value: "WG", text: "原材料" },
        { value: "ZZ", text: "半成品" },
        { value: "CP", text: "成品" }
      ],
        loading: false,
      form: {
        repertoryCode: "",
        repertoryName: "",
        repertoryType: "",
        repertoryDepartmentId: "",
        repertoryManager: [],
        status: "",
        companyId: "1"
      },
      rules: {
        repertoryCode: [
          { required: true, message: "请输入仓库编码", trigger: "blur" }
        ],
        repertoryName: [
          { required: true, message: "请输入仓库名称", trigger: "blur" }
        ],
        repertoryType: [
          { required: true, message: "请选择仓库类型", trigger: "change" }
        ],
        repertoryDepartmentId: [
          { required: true, message: "请选择所属部门", trigger: "change" }
        ],
        repertoryManager: [
          { required: true, message: "请选择仓库管理员", trigger: "change" }
        ]
      },
      search: {
        pageNum: 1
      },
      department: [],
      employee: [],
      post: []
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http.post("/department/get", {}).then(res => {
        if (res.data.code == 1000) {
          this.department = res.data.data;
        }
      });
      this.$http.post("/employee/get", {}).then(res => {
        if (res.data.code == 1000) {
          this.employee = res.data.data;
        }
      });
      if (this.$route.query.repertoryId != null) {
        this.$http
          .post("/repertory/info", {
            repertoryId: this.$route.query.repertoryId
          })
          .then(res => {
            if (res.data.code == 1000) {
              /*分割管理员id字符串，并转换格式*/
              let managers = JSON.parse(res.data.data.repertoryManager);
              var managersName = [];
              for (var x in managers) {
                managersName.push(managers[x].id);
              }
              res.data.data.repertoryManager = managersName;
              this.form = res.data.data;
            }
          });
      } else {
        this.$http.post("/repertory/info", this.search).then(res => {
          if (res.data.code == 1000) {
            this.form.status = 1;
          }
        });
      }
    },
    findmanager(id, arr) {
      var res = arr.find((value, index, arr) => {
        return value.id == id;
      });
      return res;
    },
    save(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          /*合并管理员json*/
          let managerSelected = this.form.repertoryManager;
          let repmanager = [];
          for (var managerind in managerSelected) {
            var manager = this.findmanager(
              managerSelected[managerind],
              this.employee
            );
            repmanager.push({ id: manager.id, employeename: manager.name,firstDepartmentName:manager.firstDepartmentName });
          }
          this.form.repertoryManager = JSON.stringify(repmanager);
          //提交
            this.loading = true
          this.$http.post("/repertory/saveOrUpdateRepertoy", this.form).then(res => {
            if (res.data.code == 1000) {
              this.$message.success("保存成功");
              this.$router.push("/repertoryList");
            }
            this.loading = false
          })
              .catch(err => {
                  this.loading = false
              });
        } else {
          this.$message.error("带*为必填项");
          return false;
        }
      });
    }
  },
  watch: {
    $route(to, from) {
          if (to.path == "/repertoryInfo" && this.$route.query.works !== 1) {
              this.$refs['form'].resetFields()
              this.getData();
          }
      }
  }
};
</script>

