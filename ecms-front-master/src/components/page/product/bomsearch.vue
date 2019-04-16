<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">产品明细</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="产品编号">
                        <el-input v-model="form.materialCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="成品编号">
                        <el-input v-model="form.productCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="产品名称">
                        <el-input v-model="form.materialName" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="原图材料">
                        <el-input v-model="form.originalMaterial" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="单位">
                        <el-input v-model="form.materialUnit" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                        <el-form-item label="编制人">
                            <el-input v-model="form.author" readonly></el-input>
                        </el-form-item>
                </el-col>
                <el-col :span="12">
                        <el-form-item label="创建时间">
                            <el-date-picker size="small" v-model="form.materialBomCreated" readonly></el-date-picker>
                        </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginTop" />
            <span class="text">参数列表</span>
            <hr class="marginBottom" />
            <el-table :data="dynamicMaterialParameters" border 
                    style="width:100%" height="250">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="parameterName" label="参数名称">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.parameterName }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input v-model="scope.row.parameterName" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="parameterValue" label="参数值">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.parameterValue }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input v-model="scope.row.parameterValue" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
            </el-table>
            <span class="text">制程列表</span>
            <hr class="marginBottom" />
            <el-table :data="dynamicMaterialProcessing" border 
                    style="width:100%" height="250">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="processingName" label="制程名称">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.processingName }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input v-model="scope.row.processingName" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="speciality" label="特性">
                    <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.speciality }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input v-model="scope.row.speciality" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="costTime" label="额定工时">
                  <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.costTime }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input v-model="scope.row.costTime" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="specialEquipment" label="设备名称">
                  <template slot-scope="scope">
                      <span v-if="!scope.row.editFlag">{{ scope.row.specialEquipment }}</span>
                      <span v-if="scope.row.editFlag" class="cell-edit-input">
                          <el-input v-model="scope.row.specialEquipment" placeholder="请输入内容"></el-input>
                      </span>
                  </template>
                </el-table-column>
                <el-table-column prop="comment" label="备注">
                  <template slot-scope="scope">
                        <span v-if="!scope.row.editFlag">{{ scope.row.comment }}</span>
                        <span v-if="scope.row.editFlag" class="cell-edit-input">
                            <el-input rows="3" v-model="scope.row.comment" placeholder="请输入内容"></el-input>
                        </span>
                    </template>
                </el-table-column>
            </el-table>
            <span class="text">部件列表</span>
            <hr class="marginBottom" />
            <el-table :data="dynamicMaterialParts" border 
                    style="width:100%" height="250">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="物料编号" prop="materialBom.materialCode"></el-table-column>
                <el-table-column label="物料名称" prop="materialBom.materialName"></el-table-column>
                <el-table-column label="原图材料" prop="materialBom.originalMaterial"></el-table-column>
                <el-table-column label="参数列表">
                  <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                    <template slot-scope="scope">
                      {{scope.row.materialBom.materialParameter[param.parameterName]}}
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="原材料编号" prop="originalCode"></el-table-column>
                <el-table-column label="原材料名称" prop="originalName"></el-table-column>
                <el-table-column label="原材料消耗量" prop="consumeQty"></el-table-column>
                <el-table-column label="原材料单位" prop="originalUnit"></el-table-column>
                <el-table-column label="部件来源" prop="purchaseType"></el-table-column>
                <el-table-column label="通用性" prop="reusable"></el-table-column>
            </el-table>
            <hr class="marginTop"/>
            <el-button @click="goBack">返回</el-button>
        </el-form>
    </div>
</template>

<script>
export default {
  data() {
    return {
      form: {},
      dynamicMaterialParameters: [],
      dynamicMaterialProcessing: [],
      dynamicMaterialParts: [],
      materialSearch: {
        pageNum: 1
      },
      search: {
        pageNum: 1
      },
      searchParams: [],
      parameterNames: [],
      partMaterialSearch: {},
      partMaterialTable: [],
      partForm: {},
      partFormVisible: false,
      selectedMaterialPart: []
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    getData() {
      if (this.$route.query.materialId != null) {
        this.$http
          .post("/material/info", { materialId: this.$route.query.materialId })
          .then(res => {
            if (res != undefined && res.data.code == 1000) {
              this.form = res.data.data;
              for (
                var i = 0;
                i < res.data.data.materialParameters.length;
                i++
              ) {
                res.data.data.materialParameters[i].editFlag = false;
              }
              this.dynamicMaterialParameters = res.data.data.materialParameters;
              for (
                var i = 0;
                i < res.data.data.materialProcessing.length;
                i++
              ) {
                res.data.data.materialProcessing[i].editFlag = false;
              }
              this.dynamicMaterialProcessing = res.data.data.materialProcessing;
              this.dynamicMaterialParts = res.data.data.materialParts;
            }
          });
        this.$http.post("/material/paramNames").then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.searchParams = res.data.data;
          }
        });
      }
    },
    goBack() {
      this.$router.push("/materialBomList");
    },
    getPartMaterialData() {
      this.$http.post("/material/search", this.partMaterialSearch).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.partMaterialTable = [];
          res.data.data.forEach(part => {
            this.partMaterialTable.push({ materialBom: part });
          });
          //this.partMaterialTable = res.data.data;
        }
      });
    },
  },
  watch: {
    $route: "getData"
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
