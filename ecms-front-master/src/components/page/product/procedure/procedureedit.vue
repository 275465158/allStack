<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">设备程序明细:</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="程序编号">
                        <el-input v-model="form.id" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="程序名称">
                        <el-input v-model="form.procedureName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="设备名称">
                        <el-select v-model="value" placeholder="请选择" @change="showModel">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="设备型号" v-show="namechecked">
                        <el-input name="equipmentModel" disabled type="textarea"
                          autosize v-model="checkedequipment" v-show="namechecked"></el-input>
                         <el-button @click="getequipModel" v-show="namechecked">选择</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom" />
            <span class="text">程序文件:</span>
            <hr class="marginBottom" />
            <el-table :data="fileInfo" border style="width:100%" >
                <el-table-column label="文件编号" prop="id">

                </el-table-column>
                <el-table-column label="文件名称" prop="fileName">

                </el-table-column>
                <el-table-column label="版本号" prop="fileVersion">

                </el-table-column>
                <el-table-column label="编制人" prop="compileMan">

                </el-table-column>
                <el-table-column label="编制日期" prop="compileDate">

                </el-table-column>
                <!-- <el-table-column label="审批人" prop="approvalMan">

                </el-table-column>
                <el-table-column label="审批结果" prop="approvalResult">

                </el-table-column>
                <el-table-column label="审批日期" prop="approvalDate">

                </el-table-column>
                <el-table-column label="使用时间" prop="useDate">

                </el-table-column> -->
                <el-table-column label="备注" prop="remark">
                   <template slot-scope="tableData" >
                    <input  type="text" class="editcomponent" v-model="tableData.row.remark"/>
                    </template>
                </el-table-column>
                <el-table-column label="操作" prop="option">
                    <template slot-scope="tableData" >
                     <a v-bind:href="reqfile">
                    <el-button @click="downloadfile">下载文件</el-button>
                     </a>
                    </template>
                </el-table-column>

            </el-table>
            <hr class="marginTop"/>
            <!-- <span class="text">历史文件:</span><el-button @click="goBack">新增</el-button>
             <el-table :data="fileInfo" border style="width:100%" >
                <el-table-column label="文件编号" prop="id"></el-table-column>
                <el-table-column label="文件名称" prop="fileName"></el-table-column>
                <el-table-column label="版本号" prop="fileVersion"></el-table-column>
                <el-table-column label="编制人" prop="compileMan"></el-table-column>
                <el-table-column label="编制日期" prop="compileDate"></el-table-column>
                <el-table-column label="审批人" prop="approvalMan"></el-table-column>
                <el-table-column label="审批结果" prop="approvalResult"></el-table-column>
                <el-table-column label="审批日期" prop="approvalDate"></el-table-column>
                <el-table-column label="使用时间" prop="useDate"></el-table-column>
                <el-table-column label="备注" prop="remark"></el-table-column>
                <el-table-column label="操作" prop="option">
                    <template slot-scope="tableData" >
                    <el-button @click="downloadfile">下载文件</el-button>
                    </template>
                </el-table-column>
            </el-table>-->
            <el-button @click="goBack">返回</el-button>
            <el-button round type="primary" @click="save">保存修改</el-button> 
        </el-form>
    </div>
</template>

<script>
export default {
  data() {
    return {
      fileInfo: [],
      form: {
        id: "",
        procedureName: "",
        equipmentName: "",
        equipmentModel: ""
      },
      search: {
        id: "",
        pageNum: 1,
        pageSize: 20
      },
      options: [
        {
          value: "ok",
          label: "暂无数据，请去设备管理界面添加"
        }
      ],
      fileUrl: "", //下载文件的地址 这个好像已经废止了
      value: "", //select选择之后的值
      namechecked: false, //记录设备类型是否已经选择
      checkedequipment: "" //input中显示多选的设备型号
    };
  },
  created() {
    this.getData();
  },
  computed: {
    reqfile() {
      //计算下载文件的地址
      return (
        "http://localhost:8080/"+"ecms/procedureEqui/downloadfile?id=" +
        this.fileInfo[0].id
      );
    }
  },
  methods: {
    showModel() {
      //select内容改变时调用，调用后设备型号显现，可以选择
      this.namechecked = true;
    },
    downloadfile() {
    },
    getequipModel() {
      this.tableData = []; //清空
      this.equipsearch.equipmentName = this.value;
      this.$http
        .post("/equipmentManage/equiList", this.equipsearch)
        .then(res => {
          if (res !== undefined && res.data.code == 1000) {
            //获取设备管理
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
            //获取设备型号（模糊）

            this.equipList = res.data.data.list;
            this.pages = res.data.data.total;
            this.optionslength = this.equipList.length;
            this.tableData = this.equipList;
          }
        });
    },
    getData() {
      if (this.$route.query.procedureId != null) {
        this.search.id = this.$route.query.procedureId;
        this.$http.post("/procedureEqui/detail", this.search).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.form = res.data.data;
          }
        });
        this.$http.post("/procedureEquiFile/detail", this.search).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.fileInfo = res.data.data;
          }
        });
      }
    },
    goBack() {
      this.$router.push("/procedureList");
    },
    save() {
      this.insert();
    },
    insert() {}
  },
    watch: {
        '$route' (to, from) {
            if (to.path == '/procedureEdit') {
                this.getData();
            }
        }
    },
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
.editcomponent {
  border: none;
  height: 30px;
  width: 90%;
}
</style>
