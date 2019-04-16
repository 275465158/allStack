<template>
    <div class="container">
        <div class="handle-box">
            <el-form label-width="100px" :model="orderForm">
                <el-form-item label="出库单编号">
                    <el-input v-model="orderForm.materielProduceNumber"></el-input>
                </el-form-item>
                <el-form-item label="领料人">
                    <el-input v-model="orderForm.pickedSigner"></el-input>
                </el-form-item>
                <el-form-item label="领料时间">
                    <el-date-picker v-model="orderForm.pickedDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="领料状态">
                    <span>{{orderForm.pickStatus==1?"完成领料":"待领料"}}</span>
                </el-form-item>
            </el-form>
        </div>
        <el-table border style="width:100%" :data="tables">
            <el-table-column label="物料编码" prop="materialBomModal.materialCode" width="130"></el-table-column>
            <el-table-column label="物料名称" prop="materialBomModal.materialName"></el-table-column>
            <el-table-column label="原图材料" prop="materialBomModal.originalMaterial" width="100"></el-table-column>
            <el-table-column label="出库数量" prop="pickQty" width="80"></el-table-column>
            <el-table-column label="参数列表">
                <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                    <template slot-scope="scope">
                        {{scope.row.materialBomModal.materialParameter[param.parameterName]}}
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-button @click="save">确认</el-button>
            <span v-if="orderForm.pickStatus!=1">
            <el-button @click="picking">完成领料</el-button>
            </span>
            <el-button @click="goBack">返回</el-button>
        </div>
    </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/materiel/produce/info",
      search: { status: 1, repertoryId: 0, id: null },
      //物料属性字段列表
      searchParams: [],
      orderForm: {}
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
    getData() {
      this.$http.post("/material/paramNames").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.searchParams = res.data.data;
        }
      });
      if (this.$route.query.repertoryId != null) {
        this.search.repertoryId = this.$route.query.repertoryId;
      } else {
        //this.$router.push("/repertoryList");
      }
      if (this.$route.query.id != null) {
        this.search.id = this.$route.query.id;
      }
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.detailList;
          this.orderForm = res.data.data;
        }
      });
    },
    goBack() {
      this.$router.push({
        path: "/materielProduceList",
        query: { repertoryId: this.search.repertoryId }
      });
    },
    save() {
      delete this.orderForm.detailList;
      delete this.orderForm.productionPlan;
      this.$http
        .post("/materiel/produce/saveOrUpdate", this.orderForm)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("保存成功");
          }
        });
    },
    picking() {
      this.$http.post("/materiel/produce/picking", this.orderForm).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("领料成功");
        }
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

