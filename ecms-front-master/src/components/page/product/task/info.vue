<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">BOM 制作任务明细</span>
        <hr class="marginBottom" />
        <el-form :model="form" label-width="100px">
            <el-form-item label="订单编号">
                <el-input v-model="form.orderId"></el-input>
            </el-form-item>
            <el-form-item label="合同编号">
                <el-input v-model="form.purchaseId"></el-input>
            </el-form-item>
            <el-form-item label="BOM制作人">
                <el-input v-model="form.draftsman"></el-input>
            </el-form-item>
        </el-form>
        <div class="handle-box">
            <span class="el-form-item__label">任务内容</span>
            
        </div>
        <el-table :data="tables" border style="width:100%">
            <el-table-column prop="materialBom.id" label="序号"></el-table-column>
            <el-table-column prop="draftName" label="销售产品名称"></el-table-column>
            <el-table-column prop="materialBom.materialCode" label="产品编码"></el-table-column>
            <el-table-column prop="materialBom.materialCode" label="成品编码"></el-table-column>
            <el-table-column prop="materialBom.materialCode" label="产品名称"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    
                    <span><el-button @click="editDetail(scope.row)">编辑</el-button></span>
                    <span><el-button @click="deleteDetail(scope.row)">删除</el-button></span>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-button @click="saveTask">保存</el-button>
            <el-button @click="goBack">取消</el-button>
        </div>

        <el-dialog title="选择产品" :visible.sync="selectionVisible" width="60%">
            <hr class="marginTop" />
        <span class="text">搜索物料:</span>
        <el-form ref="searchForm" :model="searchForm" label-width="100px" :inline="true">
            <el-form-item label="物料名称:">
                <el-input v-model="searchForm.materialName"></el-input>
            </el-form-item>
            <el-form-item label="物料编码:">
                <el-input v-model="searchForm.materialCode"></el-input>
            </el-form-item>
            <el-button @click="materialSearch">查询</el-button>
        </el-form>
        <hr class="marginBottom" />
        <el-table :ref="searchTable" :data="searchTable" border style="width:100%" height="230"
        @current-change="handleSelectedChange"
        highlight-current-row
        >
            <el-table-column width="55" prop="id">
                <template slot-scope="scope">
                    <el-radio v-model="searchRadio" :label="scope.row.id">&nbsp;</el-radio>
                </template>
            </el-table-column>
            <el-table-column label="物料编码" prop="materialCode" width="130"></el-table-column>
            <el-table-column label="物料名称" prop="materialName" width="100"></el-table-column>
            <el-table-column label="原图材料" prop="originalMaterial" width="100"></el-table-column>
            <el-table-column label="参数列表">
                <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                    <template slot-scope="scope">
                        {{scope.row.materialParameter[param.parameterName]}}
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
        <div class="pagination">
                <el-button @click="insertDetail">确定</el-button>
                <el-button @click="selectionVisible=false">取消</el-button>
            </div>
        </el-dialog>
    </div>
    
</template>
<script>
export default {
  data() {
    return {
      form: {},
      tableData: [],
      url: "/bomtask/info",
      pages: 1,
      search: {
        pageNum: 1,
        id: null
      },
      searchForm: {},
      searchTable: [],
      searchRadio: "",
      searchParams: [],
      selectionVisible: false,
      templateSelection: {}
    };
  },
  created() {
    if (this.$route.query.taskId != undefined) {
      this.search.id = this.$route.query.taskId;
      this.getData();
    }
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        return d;
      });
    }
  },
  methods: {
    handleSelectedChange(val) {
      this.templateSelection.materialBom = val;
      this.templateSelection.materialId = val.id;
    },
    materialSearch() {
      this.$http.post("/material/search", this.searchForm).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("完成搜索");
          this.searchTable = res.data.data;
        }
      });
    },
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res.data.code == 1000) {
          this.form = res.data.data;
          this.tableData = res.data.data.taskDetail;
        }
      });
    },
    goBack() {
      this.$router.push("/BomTasksList");
    },
    saveTask() {
      for (var p in this.tableData) {
        delete this.tableData[p].materialBom;
      }
      this.form.taskDetail = JSON.stringify(this.tableData);
      this.$http.post("/bomtask/saveOrUpdate", this.form).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("完成保存");
          this.goBack();
        }
      });
    },
    appendDetail(row) {
      this.selectionVisible = true;
      this.templateSelection = row;
      //this.getData();
    },
    insertDetail() {
      this.selectionVisible = false;
    },
    deleteDetail(row) {
      var index = this.tableData.indexOf(row);
      if (index != -1) {
        this.tableData.splice(index, 1);
      }
    },
    editDetail(row) {
      this.$router.push({
        path: "/materialBomInfo",
        query: { materialId: row.materialId, editFlag: true }
      });
    },
    confirmDetail(row) {
      row.editFlag = false;
    }
  }
};
</script>
<style scoped>
.handle-box {
  margin-bottom: 50px;
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
