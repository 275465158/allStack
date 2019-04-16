<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">设备程序明细:</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="程序编号">
                        <el-input v-model="this.procedurenum" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="程序名称">
                        <el-input v-model="form.procedureName" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="设备名称">
                        <el-input v-model="form.equipmentName" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="设备型号">
                        <el-input v-model="form.equipmentModel" disabled></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom" />
            <span class="text">当前文件:</span>
            <hr class="marginBottom" />
            <el-table :data="fileInfo" border style="width:100%" >
                <el-table-column label="文件编号" prop="fileid">
                
                </el-table-column>
                <el-table-column label="文件名称" prop="fileName"></el-table-column>
                <el-table-column label="版本号" prop="fileVersion"></el-table-column>
                <el-table-column label="编制人" prop="compileMan"></el-table-column>
                <el-table-column label="编制日期" prop="compileDate"></el-table-column>
                <!--                 
                <el-table-column label="审批人" prop="approvalMan"></el-table-column>
                <el-table-column label="审批结果" prop="approvalResult"></el-table-column>
                <el-table-column label="审批日期" prop="approvalDate"></el-table-column>
                <el-table-column label="使用时间" prop="useDate"></el-table-column> -->
                <el-table-column label="备注" prop="remark "></el-table-column>
                
                <el-table-column label="操作" prop="option">
                    <template slot-scope="tableData" >
                     <a v-bind:href="reqfile">
                    <el-button>下载文件</el-button>
                     </a>
                    </template>
                </el-table-column>

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
      fileInfo: [],
      form: {
        id: "",
        procedureName: "",
        equipmentName: "",
        equipmentModel: ""
      },
      fileid: "", //
      search: {
        id: "",
        pageNum: 1,
        pageSize: 20
      },
      procedurenum: ""
    };
  },

  created() {
    this.getData();
  },
  computed: {
    reqfile() {
      //计算下载文件的地址   移动到服务器上之后前面的链接要改下
      return (
        // 'http://47.99.51.34:8188/ecms'
        // "http://localhost:8088/ecms"
        this.$path + "/procedureEqui/downloadfile?id=" + this.fileInfo[0].id
        //  +this.fileid
      );
    }
  },
  methods: {
    getData() {
      if (this.$route.query.procedureId != null) {
        this.search.id = this.$route.query.procedureId;
        this.$http.post("/procedureEqui/detail", this.search).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.form = res.data.data;
          }
        });
        this.$http.post("/procedureEquiFile/detail", this.search).then(res => {
          //前端格式化版本号
          if (res != undefined && res.data.code == 1000) {
            this.fileInfo = res.data.data;
            this.getProdureOrder();
            for (let index = 0; index < this.fileInfo.length; index++) {
              const element = this.fileInfo[index].fileVersion;
              const tempelement = "v" + element + ".0";
              this.fileInfo[index].fileVersion = tempelement;
            }
          }
        });
      }
    },

    getProdureOrder() {
      //获取将要添加的程序编号    PR0001
      this.$http.post("/procedureEqui/getprocedurenum").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.procedurenum = res.data.data;
        }
      });
    },
    goBack() {
      this.$router.push("/procedureList");
    }
  },
    watch: {
        '$route' (to, from) {
            if (to.path == '/procedureSearch') {
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
.transparency {
  border: none;
}
</style>
