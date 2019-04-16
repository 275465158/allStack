<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <!--<el-button size="small" @click="editVisible = true">导入产品<i class="el-icon-upload el-icon&#45;&#45;right"></i></el-button>
                <el-button style="float:right;" size="small" @click="exportMaterial">导出产品<i class="el-icon-download el-icon&#45;&#45;right"></i></el-button>
                <el-button style="float:right;" size="small" @click="exportTemplate">模板下载<i class="el-icon-download el-icon&#45;&#45;right"></i></el-button>-->
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="产品编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="产品名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="制作人:">
                        <el-input v-model="search.author"></el-input>
                    </el-form-item>
                    <el-button round @click="getData">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">产品列表</span>
                <el-button round type="primary" @click="add">新增产品</el-button>
            </div>
            <el-table :data="tableData" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="产品编号" prop="materialCode">
                </el-table-column>
                <el-table-column label="工厂物料编号" prop="factoryMaterialCode">
                </el-table-column>
                <el-table-column label="产品名称" prop="materialName">
                </el-table-column>
                <el-table-column label="原图材料" prop="originalMaterial">
                </el-table-column>
                <el-table-column label="参数一" prop="materialBomParamValueStr">
                </el-table-column>
                <el-table-column label="来源" prop="source">
                </el-table-column>
                <el-table-column label="类型" prop="source">
                </el-table-column>
                <el-table-column label="添加时间" prop="materialBomCreated">
                </el-table-column>
                <el-table-column label="制作人" prop="author">
                </el-table-column>
                <el-table-column label="验收标准" prop="ifCheck">
                    <template slot-scope="scope">
                        <div v-if="scope.row.ifCheck === 0">无</div>
                        <div v-if="scope.row.ifCheck === 1">有</div>
                    </template>
                </el-table-column>
                <el-table-column label="图号" prop="drawingCode">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="updateInfo(scope.$index, scope.row)">修改</el-button>
                        <el-button size="small" @click="detail(scope.$index, scope.row)">明细</el-button>
                        <el-button size="small" @click="copyEdit(scope.$index, scope.row)">复制修改</el-button>
                        <el-button size="small" @click="view(scope.$index, scope.row)">分解清单</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <!-- 编辑弹出框 -->
            <el-dialog title="导入产品" :visible.sync="editVisible" width="30%">
                <el-form ref="form" label-width="100px">
                    <el-form-item label="产品列表:">
                        <input type="file" name="file" @change="setFile"/>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="importCustomer">导入</el-button>
            </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    import fileDownload from 'js-file-download';
export default {
  data() {
      return {
          tableData: [],
          url: "/materialInfo/searchMaterialList",
          pages: 1,
          editVisible:false,
          search: {
              flag:'1',
              materialCode: '',
              materialName:'',
              author:'',
              pageNum: 1,
              pageSize:20
          },
          form:{
              status:'',
              ifDrawing:'',
              ifCheck:''
          },
      };
  },
  created() {
    this.getData();
  },
  methods: {
      handleSelect(key, keyPath) {},
      handleChange(value) {
      },
      // 分页导航
      handleCurrentChange(val) {
          this.search.pageNum = val;
          this.getData();
      },
      getData() {
          this.$http.post(this.url, this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.tableData = res.data.data.list;
                  this.pages = res.data.data.total;
              }
          });
      },
      add(){
          this.$router.push({
              path: "/productAdd",
          });
      },
      updateInfo(index,row) {
          this.$router.push({
              path: "/productEdit",
              query: {
                  materialId: row.id
              }
          });
      },
      copyEdit(index,row) {
          this.$router.push({
              path: "/productCopyEdit",
              query: {
                  materialId: row.id
              }
          });
      },
      detail(index,row) {
          this.$router.push({
              path: "/productSearch",
              query: {
                  materialId: row.id
              }
          });
      },
      view(index,row) {
          this.$router.push({
              path: "/productView",
              query: {
                  materialId: row.id
              }
          });
      },
      clearData() {
          (this.search.materialCode = ""),
          (this.search.materialName = ""),
          (this.search.author = "")
      },
      exportMaterial(){
          this.$http.get('/materialInfo/export/list',{responseType: 'blob'}).then((res) => {
              fileDownload(res.data, '产品列表.xls');
          })
      },
      exportTemplate(){
          this.$http.get('/materialInfo/export/template',{responseType: 'blob'}).then((res) => {
              fileDownload(res.data, '产品模板.xls');
          })
      },
      setFile(){
          this.file = event.target.files[0];//获取文件
          var windowURL = window.URL || window.webkitURL;
          this.file = event.target.files[0];
      },
      importCustomer(){
          let formdata = new FormData();
          formdata.append('file',this.file);
          let config = {
              headers: {
                  'Content-Type': 'multipart/form-data'
              },
              withCredentials : true
          };
          this.$axios.post(this.$path + '/materialInfo/upload', formdata, config).then((res) => {
              this.editVisible = false;
              if (res.data.code == 1000) {
                  this.$message.success('导入成功');
                  this.getData();
              } else {
                  this.$message.error(res.data.message);
              }
          })
      },
  },
    watch: {
        '$route' (to, from) {
            if (to.path == '/productList') {
                this.getData();
            }
        }
    },
};
</script>
