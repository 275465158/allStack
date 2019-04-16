
<template>
  <div class="table">
    <div class="container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="验收编号:">
          <el-input v-model="search.checkId" disabled></el-input>
        </el-form-item>
        <el-form-item label="验收名称:">
          <el-input v-model="search.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="制作人:">
          <el-input v-model='search.owner' disabled></el-input>
        </el-form-item>
        <el-form-item label=" ">
          <img :src="search.inspectionDiagram" height="100" width="100" />
        </el-form-item>

      </el-form>
      <hr class="marginTop" />
      <!-- <el-col :span="24" align="right">
        
      </el-col> -->

      <div class="handle-box">
        <span class="text">验收明细</span>

      </div>
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="serial_num" type="index" label="序号">

        </el-table-column>
        <el-table-column prop="checkName" label="检测名称">

        </el-table-column>

        <el-table-column prop="checkMethod" label="检测方式">

        </el-table-column>
        <el-table-column prop="equipmentName" label="验收设备">

        </el-table-column>
        <el-table-column prop="equipmentSpecification" label="设备规格">

        </el-table-column>
        <el-table-column prop="unit" label="单位">

        </el-table-column>
        <el-table-column prop="minValue" label="最小值">

        </el-table-column>
        <el-table-column prop="maxValue" label="最大值">

        </el-table-column>
        <!-- 分页 -->
      </el-table>
      <el-row class="operation_btn">
        <el-button @click="back" size="medium">关闭</el-button>
      </el-row>

    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      count: 1, //记录详细的条数
      isshow: false,
      url: "/productAcceptanceDetail/checkdetailinfo",
      tableData: [
        {
          acceptanceId: 1,
          checkName: "",
          equipmentName: "",
          equipmentSpecification: "",
          unit: "",
          minValue: "",
          maxValue: ""
        }
      ],
      search: {
        checkId: "",
        name: "",
        owner: "",
        inspectionDiagram: ""
      }
    };
  },
  methods: {
    back() {
      this.$router.push({
        path: "/checkstandard"
      });
    },
    getData() {
      if (this.$route.query.checkId != null) {
        this.search.checkId = this.$route.query.checkId;
      }

      this.$http
        .post("productAcceptance/getById", { id: this.search.checkId })
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.search = res.data.data;
            if (this.search.inspectionDiagram) {
              this.$http
                .post(
                  "/productAcceptance/download",
                  { filePath: this.search.inspectionDiagram },
                  { responseType: "arraybuffer" }
                )
                .then(res => {
                  this.search.inspectionDiagram =
                    "data:image/png;base64," +
                    btoa(
                      new Uint8Array(res.data).reduce(
                        (res, byte) => res + String.fromCharCode(byte),
                        ""
                      )
                    );
                });
            } else {
              this.search.inspectionDiagram = require('../../../../../static/img/149881997658968.png')
            }
          }
        });

      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data;
        }
      });
    }
  },
  created() {
    this.getData();
  },
  watch: {
    $route(to, from) {
      if (to.path == "/checkdetail") {
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
.addcomponent {
  border: none;
  height: 30px;
  width: 90%;
}
.operation_btn {
  text-align: center;
  padding-top: 20px;
}
</style>

