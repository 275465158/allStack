
<template>
  <div class="table">
    <div class="container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="验收编号:">
          <el-input v-model="search.checkId" disabled></el-input>
        </el-form-item>
        <el-form-item label="验收名称:">
          <el-input v-model="search.name"></el-input>
        </el-form-item>
        <el-form-item label="制作人:">
          <el-input v-model='search.owner'></el-input>
        </el-form-item>
        <el-form-item label=" ">
          <img :src="search.inspectionDiagram" height="100" width="100" />
        </el-form-item>

      </el-form>
      <hr class="marginTop" />
      <div class="handle-box">
        <span class="text">检验示意图</span>
        <el-upload class="upload-demo" :on-success="fileUpload" :limit="1" :action="this.$path + '/productAcceptance/uploadSimple'" :file-list="pictureList" accept="image/*" list-type="picture" :with-credentials="true">
          <el-button size="medium">上传</el-button>
        </el-upload>
        <div slot="tip" class="el-upload__tip">只能图片文件</div>
      </div>

      <div class="handle-box">
        <span class="text">验收明细</span>

      </div>
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="serial_num" type="index" label="序号">

        </el-table-column>
        <el-table-column prop="detectionname" label="检测名称">
          <template slot-scope="tableData">
            <input type="text" v-model="tableData.row.checkName" class="addcomponent" />
          </template>
        </el-table-column>

        <el-table-column prop="checkMethod" label="检测方式">
          <template slot-scope="tableData">
            <input type="text" v-model="tableData.row.checkMethod" class="addcomponent" />
          </template>
        </el-table-column>

        <el-table-column prop="equipmentName" label="验收设备">
          <template slot-scope="tableData">
            <input type="text" v-model="tableData.row.equipmentName" class="addcomponent" />
          </template>
        </el-table-column>
        <el-table-column prop="equipmentSpecification" label="设备规格">
          <template slot-scope="tableData">
            <input type="text" v-model="tableData.row.equipmentSpecification" class="addcomponent" />
          </template>
        </el-table-column>
        <el-table-column prop="unit" label="单位">
          <template slot-scope="tableData">
            <input type="text" v-model="tableData.row.unit" class="addcomponent" />
          </template>
        </el-table-column>
        <el-table-column prop="minValue" label="最小值">
          <template slot-scope="tableData">
            <input type="number" :ref="'input' + tableData.$index" @keyup="minImport($event, tableData.$index)" v-model.number="tableData.row.minValue" class="addcomponent" />
          </template>
        </el-table-column>
        <el-table-column prop="maxValue" label="最大值">
          <template slot-scope="tableData">
            <input type="number" :ref="'input1' + tableData.$index" @keyup="maxImport($event, tableData.$index)" v-model.number="tableData.row.maxValue" class="addcomponent" />
          </template>
        </el-table-column>
        <el-table-column prop="opration">
          <template slot-scope="tableData">
            <i class="el-icon-circle-plus-outline" @click="adddetail" style="font-size:26px;" />
            <i class="el-icon-remove-outline" @click="subdetail(tableData.$index)" style="font-size:26px;" v-show="isshow" />
          </template>
        </el-table-column>
      </el-table>
      <el-row class="operation_btn">
        <el-button @click="insert" size="medium">修改</el-button>
        <el-button @click="back" size="medium">取消</el-button>
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
      add: {
        checkid: "",
        name: "",
        owner: "",
        iseditname: "",
        inspectionDiagram: "",
        id: "",
        detailInfoList: []
      },
      url: "/productAcceptanceDetail/checkdetailinfo",
      tempname: "初始值",
      tableData: [
        {
          acceptanceId: "",
          checkMethod: "",
          equipmentName: "",
          equipmentSpecification: "",
          unit: "",
          minValue: "",
          maxValue: ""
        }
      ],
      pictureList: [],
      search: {
        checkId: "",
        inspectionDiagram: "",
        name: "",
        owner: "",
        id: ""
      }
    };
  },
  created() {
    this.getData();
  },
  methods: {
    minImport (e, i) {
      this.$nextTick(() => {
        if (e.keyCode === 189 || e.keyCode === 109) {
          this.tableData[i].minValue = ''
          this.$refs['input' + i].value = ''
        }
      })
    },
    maxImport (e, i) {
      this.$nextTick(() => {
        if (e.keyCode === 189 || e.keyCode === 109) {
          this.tableData[i].maxValue = ''
          this.$refs['input1' + i].value = ''
        }
      })
    },
    fileUpload(response, file, fileList) {
      if (response.code == 1000) {
        this.add.inspectionDiagram = response.data;
        this.$message.success("上传成功");
      }
    },
    getData() {
      if (this.$route.query.checkId != null) {
        this.search.checkId = this.$route.query.checkId;
        this.search.name = this.$route.query.name;
        this.search.owner = this.$route.query.owner;
        this.search.id = this.$route.query.id;
        this.tempname = this.search.name;
      }
      this.search.inspectionDiagram = "";
      this.$http
        .post("productAcceptance/getById", { id: this.search.checkId })
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.search.inspectionDiagram = res.data.data.inspectionDiagram;

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
            }
          }
        });

      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data;
          this.count = this.tableData.length;
          if (this.count > 1) {
            this.isshow = true;
          }
        }
      });
    },
    // 点击+号时增加一条记录
    adddetail() {
      this.count++;
      this.isshow = true;

      this.tableData.push({
        acceptanceId: this.search.checkId,
        checkName: "",
        equipmentName: "",
        equipmentSpecification: "",
        unit: "",
        minValue: "",
        maxValue: ""
      });
    },
    // 点击-号时减少一条记录
    subdetail(index) {
      //判断详细是否小于1
      if (this.count <= 1) {
        this.isshow = false;
        return;
      } else {
        this.tableData.splice(index, 1);
        this.count--;
        if (this.count < 2) {
          this.isshow = false;
        }
      }
    }, //点击新增后插入数据并回到列表页
    insert() {
      //发送请求

      this.add.detailInfoList = this.tableData;
      this.add.checkid = this.search.checkId;
      this.add.name = this.search.name;
      this.add.owner = this.search.owner;
      this.add.id = this.search.id;
      if (this.tempname != this.add.name) {
        this.add.iseditname = 1;
      } else {
        this.add.iseditname = 0;
      }
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].minValue > this.tableData[i].maxValue) {
          this.$message.warning('最小值不能超过最大值！')
          return
        }
      }
      this.$http
        .post("/productAcceptance/updataAccpetance", this.add)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("修改成功");
            this.tempname = "";
            this.$router.push({
              path: "/checkstandard"
            });
          } else {
            this.$message.error(res.data.message);
          }
        });
    },

    back() {
      this.$router.push({
        path: "/checkstandard"
      });
    }
  },
  watch: {
    $route(to, from) {
      if (from.path == "/editcheck") {
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

