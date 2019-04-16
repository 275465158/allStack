<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop" />
            <el-form @keyup.enter.native="searchEnterFun" :inline="true" ref="search" :model="search" class="demo-form-inline" style="margin-bottom:20px;">
                <el-form-item label="图纸名称:">
                    <el-input v-model="search.name"></el-input>
                </el-form-item>
                <el-form-item label="图号:">
                    <el-input v-model="search.drawingCode" style="width:100px;"></el-input>
                </el-form-item>
                <el-form-item label="图纸说明:">
                    <el-input v-model="search.drawingRemark"></el-input>
                </el-form-item>
                <el-form-item label="制作人:">
                    <el-input v-model="search.owner"></el-input>
                </el-form-item>
                <el-button @click="searchLike">查询</el-button>
            </el-form>
            <hr class="marginTop" style="margin-top:-32px;" />
            <div class="handle-box">
                <span class="text">图纸列表</span>
                <el-button @click="add()">新增</el-button>
            </div>
            <el-table v-loading="loading" :data="tableData" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable/>
                <el-table-column align="center" prop="drawingCode" label="图号" />
                <el-table-column align="center" prop="name" label="图纸名称" />
                <el-table-column align="center" prop="drawingRemark" label="图纸说明" />
                <el-table-column align="center" prop="owner" label="制作人" />
                <el-table-column align="center" prop="createdDate" label="制作日期" />
                <el-table-column align="center" prop="checkName" label="审批人" />
                <el-table-column align="center" prop="checkDateDisplay" label="审批日期" />
                <el-table-column align="center" prop="checkResultDisplay" label="审批结果" />
                <el-table-column align="center" label="操作" width="250">
                    <template slot-scope="scope">
                        <el-button type="text" @click="updateInfo(scope.$index, scope.row)">
                            修改
                        </el-button>
                        <el-button type="text" @click="detail(scope.$index, scope.row)">
                            明细
                        </el-button>
                        <el-button type="text" @click="remove(scope.$index, scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="search.pageSize" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="图纸" @close="closeing" :visible.sync="editVisible" width="60%">
            <el-form ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="图纸名称:" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="图号:" prop="drawingCode">
                    <el-input v-model="form.drawingCode"></el-input>
                </el-form-item>
                <el-form-item label="图纸说明:">
                    <el-input v-model="form.drawingRemark"></el-input>
                </el-form-item>
                <el-form-item label="工艺说明:">
                    <el-input v-model="form.technologyRemark"></el-input>
                </el-form-item>
                <el-form-item label="审批人:">
                    <el-input v-model="form.checkName"></el-input>
                </el-form-item>
            </el-form>
            <hr class="marginTop" />
            <el-table :data="files" border style="width:100%">
                <el-table-column label="图纸明细">
                    <template slot-scope="scope">
                        <span>{{ scope.row.remark }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="图纸附件">
                    <template slot-scope="scope">
                        <span>{{ scope.row.displayName }}</span>
                        <el-button v-if="scope.row.filePath != '' && !editFlag" @click="downloadFile(scope.row.filePath, scope.$index)">下载</el-button>
                        <el-progress v-if="scope.row.show" :percentage="scope.row.load" :status="scope.row.progress"></el-progress>
                    </template>
                </el-table-column>
                <el-table-column v-if="editFlag" label="操作">
                    <template slot-scope="scope">
                        <el-upload class="upload-demo" :action="$path + '/productDrawing/uploadSimple'" :limit="1" :before-upload="beforeUpload" :on-success="(response, file, fileList)=>fileUpload(response, file, fileList, scope.$index)" :with-credentials="true" :file-list="scope.row.fileList">
                            <el-button size="small">上传</el-button>
                        </el-upload>
                       <!-- <el-button style="margin-left: 10px" size="small" @click="removeFile(scope.$index, scope.row)">删除</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <span v-if="editFlag" slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('form')">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import * as utils from '../../../../../static/js/utils.js';
    import fileDownload from 'js-file-download';
export default {
  data() {
    return {
      tableData: [],
      editVisible: false,
      editFlag: true,
      total: 1,
        loading: false,
      files: [
        { remark: "CAD图纸", name: "", displayName: "", filePath: '', fileList: [], show: false, load: 0, progress: '' },
        { remark: "PDF图纸", name: "", displayName: "", filePath: '', fileList: [], show: false, load: 0, progress: '' },
        { remark: "工艺图纸", name: "", displayName: "", filePath: '', fileList: [], show: false, load: 0, progress: '' }
      ],
      search: {
        name: "",
        drawingRemark: "",
        owner: "",
        pageNum: 1,
        pageSize: 20
      },
      form: {
        id: "",
        name: "",
        drawingRemark: "",
        technologyRemark: "",
        checkName: "",
        drawingCode: "",
        cadFile: "",
        pdfFile: "",
        technologyFile: ""
      },
      rules: {
        name: [{ required: true, message: "请输入图纸名称", trigger: "blur" }],
        drawingCode: [
          { required: true, message: "请输入图号", trigger: "blur" }
        ]
      },
      load: 0,
        progress: '',
        flag: true
    };
  },
  created() {
      // let _this = this
      // setInterval(function () {
      //     _this.load += 1
      //     console.log(_this.load);
      // }, 100)
  this.loading = true
    this.getData();
  },
  methods: {
      beforeUpload () {
          this.flag = false
      },
      searchEnterFun (e) {
          let keyCode = window.event? e.keyCode:e.which;
          if(keyCode == 13){
              this.searchLike()
          }
      },
      closeing () {
          this.$refs['form'].resetFields()
          this.files.forEach((item, index) => {
              item.load = 0;
              item.progress = '';
              item.show = false;
              item.name = '';
              item.file = '';
              item.fileList = [];
          })
      },
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.$http.post("/productDrawing/like", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.total = res.data.data.total;
        }
      });
    },
    add() {
      this.form = {
        id: "",
        name: "",
        drawingRemark: "",
        technologyRemark: "",
        checkName: "",
        drawingCode: "",
        cadFile: "",
        pdfFile: "",
        technologyFile: ""
      };
      this.files[0].name = "";
      this.files[1].name = "";
      this.files[2].name = "";
      this.files[0].displayName = "";
      this.files[1].displayName = "";
      this.files[2].displayName = "";
      this.files[0].fileList = [];
      this.files[1].fileList = [];
      this.files[2].fileList = [];
      this.editVisible = true;
      this.editFlag = true;
    },
      searchLike(){
          this.search.pageNum = 1;
          this.loading = true
          this.getData();
      },
    getData() {
      this.$http.post("/productDrawing/like", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.total = res.data.data.total;
        }
        if (this.loading == true) {
            this.loading = false
        }
      })
          .catch(err => {
              if (this.loading == true) {
                  this.loading = false
              }
          });
    },
    fileUpload(response, file, fileList, index) {
      if (response.code == 1000) {
          this.flag = true
          this.files[index].name = response.data;
        this.files[index].displayName = utils.getFileName(response.data);
      }
    },
    removeFile(index, row) {
      this.files[index].name = "";
      this.files[index].displayName = "";
      this.files[index].file = "";
      this.files[index].fileList = [];
      // this.files = [
      //     { remark: "CAD图纸", name: "", filePath: '', fileList: [], show: false, load: 0, progress: '' },
      //     { remark: "PDF图纸", name: "", filePath: '', fileList: [], show: false, load: 0, progress: '' },
      //     { remark: "工艺图纸", name: "", filePath: '', fileList: [], show: false, load: 0, progress: '' }
      // ]
    },
    detail(index, row) {
      this.editVisible = true;
      this.editFlag = false;
      this.getData()
      this.assembleInfo(row);
    },
    updateInfo(index, row) {
      this.editVisible = true;
      this.editFlag = true;
        this.getData()
      this.assembleInfo(row);
    },
    remove(index, row) {
      this.$confirm("此操作将永久删除该图纸, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http
            .post("/productDrawing/remove", {
              id: row.id
            })
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
              this.loading = true
                this.getData();
                this.$message.success("操作成功");
              } else {
                this.$message.error(res.data.message);
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    downloadFile(path, index) {
        let _this = this;
        let obj = this.files[index];
        obj.load = 0;
        obj.progress = '';
        obj.show = true;
        let schedule = setInterval(function () {
            obj.load > 97 ? obj.load += 0 : obj.load += 1
        }
        , 100);
      this.$http.post('/productDrawing/download', {filePath: path}, {responseType: 'arraybuffer'}).then((res) => {
        fileDownload(res.data, utils.getFileName(path));
          obj.load = 100;
            clearInterval(schedule);
          obj.progress = 'success'
      })
          .catch(err => {
              clearInterval(schedule);
              obj.progress = 'exception'
          })
    },
    assembleInfo(row) {
      this.form = {
        id: row.id,
        name: row.name,
        drawingRemark: row.drawingRemark,
        technologyRemark: row.technologyRemark,
        checkName: row.checkName,
        drawingCode: row.drawingCode
      };
      this.files[0].name = row.cadFile;
      this.files[0].displayName = utils.getFileName(row.cadFile);
      this.files[1].name = row.pdfFile;
      this.files[1].displayName = utils.getFileName(row.pdfFile);
      this.files[2].name = row.technologyFile;
      this.files[2].displayName = utils.getFileName(row.technologyFile);
      this.files[0].filePath = row.cadFile;
      this.files[1].filePath = row.pdfFile;
      this.files[2].filePath = row.technologyFile;
    },
    // 保存编辑
    insert(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
            if (this.flag) {
                this.form.cadFile = this.files[0].name;
                this.form.pdfFile = this.files[1].name;
                this.form.technologyFile = this.files[2].name;

                this.$http
                    .post("/productDrawing/saveDrawing", this.form)
                    .then(res => {
                        if (res.data.code == 1000) {
                            this.$message.success("保存成功");
                            this.editVisible = false;
                            this.loading = true
                            this.getData();
                        } else {
                            this.$message.error(res.data.message);
                        }
                    });
            } else {
                this.$message.warning('有未上传完的文件')
            }
        } else {
          this.$message.error("带*为必填项");
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

.marginTop {
  margin-top: 10px;
  margin-bottom: 5px;
}
.text {
  font-size: 14px;
  color: #606266;
  margin-right: 30px;
}
.upload-demo {
  display: inline-block;
}
</style>
