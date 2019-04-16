<template>
  <div class="container">
    <hr class="marginTop" />
    <span class="text">设备程序明细:</span>
    <hr class="marginBottom" />
    <el-form id="myform" :model="form" ref="form" label-width="100px">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="程序编号">
            <el-input v-model="procedurenum" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="程序名称">
            <el-input name="procedureName" v-model="form.procedureName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备名称">
            <el-select v-model="value" placeholder="请选择" @change="showModel">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备型号" v-show="namechecked">
            <el-input name="equipmentModel" disabled type="textarea" autosize v-model="checkedequipment" v-show="namechecked"></el-input>
            <el-button @click="getequipModel" v-show="namechecked">选择</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <hr class="marginBottom" />
      <span class="text">程序文件:</span>
      <hr class="marginBottom" />
      <el-table :data="fileInfo" border style="width:100%">
        <el-table-column label="文件编号" prop="fileid">

        </el-table-column>
        <el-table-column label="文件名称" prop="fileName">
          <!-- <template slot-scope="tableData" >
                    <input  type="text"  class="addcomponent"  v-model="tableData.row.fileName" />
                    </template> -->
        </el-table-column>

        <el-table-column label="版本号" prop="showVersion">

        </el-table-column>
        <el-table-column label="编制人" prop="compileMan">

        </el-table-column>
        <el-table-column label="编制日期">
          <template slot-scope="tableData">
            {{tableData.row.compileDate|formatDate}}
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="remark">
          <template slot-scope="tableData">
            <input type="text" class="addcomponent" v-model="tableData.row.remark" />
          </template>
        </el-table-column>
        <el-table-column label="操作" prop="options">
          <template slot-scope="tableData">
            <el-upload class="upload-demo" :action="$path + '/procedureEqui/uploadSimple'" :on-success="fileUpload" :file-list="fileList" :with-credentials="true">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </template>
        </el-table-column>
      </el-table>
      <el-row class="operation_btn">
        <el-button @click="insert" size="medium">增加</el-button>
        <el-button @click="back" size="medium">取消</el-button>
      </el-row>
    </el-form>
    <el-dialog title="设备型号选择" @close="closeing" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" label-width="100px">
        <el-form-item label="设备型号:">
          <el-input v-model="equiplikesearch.equipmentModel"></el-input>
        </el-form-item>
        <el-button type="primary" @click="getequipModelLike">查询</el-button>

        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="check" label="" width="180">
            <template slot-scope="tableData">

              <el-checkbox v-model="tableData.row.checked"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column prop="equipmentName" label="设备名称" width="180">
          </el-table-column>
          <el-table-column prop="equipmentModel" label="型号">
          </el-table-column>
        </el-table>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>

        <el-button type="primary" @click="selected">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { formatDate } from "../../../common/date.js";
export default {
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd");
    }
  },
  data() {
    return {
      optionslength: 0,
      lengthdiff: 0, //equipList的长度和当前options的长度差
      equipList: [], //数据库里获取的设备数据
      temp: "", //存储设备对象
      procedurenum: "", //程序编号
      namechecked: true, //记录设备类型是否已经选择
      dialogVisible: false,
      fileList: [],

      tableData: [], //选择设备型号的表格数据源
      checkedequipment: "", //input中显示多选的设备型号
      fileInfo: [
        //只是表格的数据来源  真正发送到请求到后台的参数是form
        //程序文件对象
        {
          id: "",
          fileName: "",
          showVersion: "v1.0", //只是为了显示
          fileVersion: 1,
          compileMan: "",
          compileDate: "",
          remark: ""
        }
      ],

      equipsearch: {
        //去数据库搜索设备名称时传递的对象
        vender: "",
        equipmentName: "",
        equipmentNumber: "",
        pageNum: 1
      },
      equiplikesearch: {
        //去数据库模糊搜索设备型号时传递的对象
        equipmentName: "",
        equipmentModel: "",
        pageNum: 1,
        pageSize: 5
      },
      form: {
        //设备程序对象
        id: 1,
        procedureName: "",
        equipmentName: "",
        equipmentModel: "",
        // procedureEquiFileInfo: [],

        fileid: "",
        fileName: "",
        fileVersion: "",
        compileMan: "xc",
        compileDate: "",
        remark: ""

        // approvalMan: ""
      },
      options: [
        {
          value: "ok",
          label: "暂无数据，请去设备管理界面添加"
        }
      ],
      value: "" //select选择之后的值
    };
  },
  methods: {
    closeing() {
      this.$refs["form"].resetFields();
    },
    getData() {
      this.getequipname();
      this.getProdureOrder();
      var myDate = new Date();
      this.fileInfo[0].compileDate = myDate.toLocaleDateString();
      this.fileInfo[0].compileMan = this.username;
      this.fileList = [];
    },
    fileUpload(response, file, fileList) {
      if (response.code == 1000) {
        this.fileInfo[0].fileName = response.data;
      }
    },
    getFileId() {
      //添加中的获取文件id不用去数据库查找，肯定不存在的

      this.fileInfo[0].fileid = this.procedurenum + "-0" + 1; //后期要把"-01"换成变量
    },
    getProdureOrder() {
      //获取将要添加的程序编号    PR0001
      this.$http.post("/procedureEqui/getprocedurenum").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.procedurenum = res.data.data;
        }
        this.getFileId();
      });
    },
    showModel() {
      //select内容改变时调用，调用后设备型号显现，可以选择
      this.namechecked = true;
    },
    getequipname() {
      //select中获取设备名字
      this.$http
        .post("/equipmentManage/getUniqueEquipment", this.equipsearch)
        .then(res => {
          if (res !== undefined && res.data.code == 1000) {
            //获取设备管理
            this.equipList = res.data.data.list;
            this.pages = res.data.data.total;
          }
          this.optionslength = this.equipList.length;
          if (this.optionslength != 0) {
            if (this.options.length < this.optionslength) {
              //options.length下拉列表的长度，optionslength查询出的数据的长度
              //若下拉列表数组长度不够需要临时添加
              this.lengthdiff = this.optionslength - this.options.length;
              for (let index = 0; index < this.lengthdiff; index++) {
                this.options.push({
                  value: "",
                  label: ""
                });
              }
            }
            for (let index = 0; index < this.optionslength; index++) {
              this.temp = this.equipList[index];
              this.options[index].value = this.temp;
              this.options[index].label = this.temp;
            }
            this.value = this.options[0].value;
          }
        });
      this.equipList = []; //清空列表
    },

    getequipModel() {
      this.tableData = []; //清空
      this.equipsearch.equipmentName = this.value;
      this.$http
        .post("/equipmentManage/equiList", this.equipsearch)
        .then(res => {
          if (res !== undefined && res.data.code == 1000) {
            //获取设备型号
            this.tableData = res.data.data.list;
            this.pages = res.data.data.total;
          }
        });
      this.dialogVisible = true;
    },
    getequipModelLike() {
      this.equiplikesearch.equipmentName = this.value;
      this.$http
        .post("/equipmentManage/like", this.equiplikesearch)
        .then(res => {
          if (res !== undefined && res.data.code == 1000) {
            //模糊查询设备型号

            this.equipList = res.data.data.list;
            this.pages = res.data.data.total;
            this.optionslength = this.equipList.length;
            this.tableData = this.equipList;
          }
        });
    },
    selected() {
      this.checkedequipment = "";
      for (let index = 0; index < this.tableData.length; index++) {
        if (this.tableData[index].checked) {
          //显示出勾选的设备型号
          this.checkedequipment =
            this.tableData[index].equipmentModel + ";" + this.checkedequipment;
        }
      }

      this.dialogVisible = false;
    },
    back() {
      this.$refs["form"].resetFields();
      this.$router.push({
        path: "/procedureList",
        query: { closeFlag: 1 }
      });
    },
    insert() {
      this.form.equipmentName = this.value;
      this.form.id = parseInt(this.subs);
      this.form.equipmentModel = this.checkedequipment;
      this.form.file = this.file;
      var formdata = new FormData(myform);
      formdata.append("equipmentName", this.value);
      formdata.append("equipmentModel", this.checkedequipment);
      formdata.append("fileId", this.fileInfo[0].fileid);
      formdata.append("fileName", this.fileInfo[0].fileName);
      formdata.append("fileVersion", this.fileInfo[0].fileVersion);
      formdata.append("compileMan", this.fileInfo[0].compileMan);
      formdata.append("compileDate", this.fileInfo[0].compileDate);
      formdata.append("remark", this.fileInfo[0].remark);

      let config = {
        headers: {
          "Content-Type": "multipart/form-data"
        },
        withCredentials: true
      };
      let _this = this;
      this.$axios
        .post(this.$path + "/procedureEqui/saveProcedure", formdata, config)
        // this.$http.post("/procedureEqui/saveProcedure", formdata)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("添加成功");
            _this.back();
          } else {
            this.$message.error(res.data.message);
          }
        });
      this.form.procedureEquiFileInfo = "";
      this.fileList = [];
      this.$router.push("/procedurelist");
      //axios和http有啥区别？
    }
  },
  created() {
    this.getData();
  },
  computed: {
    subs() {
      //截取字符串
      return this.procedurenum.substr(2, 4);
    },
    username() {
      //获取当前用户名
      let username = localStorage.getItem("ms_username");
      return username;
    }
  },
  watch: {
    $route(to, from) {
      if (to.path == "/procedureadd") {
        Object.assign(this.$data, this.$options.data());
        this.getData();
      }
    }
  }
};
</script>
<style scoped>
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

