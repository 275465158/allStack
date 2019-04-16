<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="程序编号:">
                        <el-input v-model="search.id"></el-input>
                    </el-form-item>
                    <el-form-item label="程序名称:">
                        <el-input v-model="search.procedureName"></el-input>
                    </el-form-item>
                    <el-form-item label="制作人:">
                        <el-input v-model="search.compileMan"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">程序列表</span>
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <el-table :data="tableData" border style="width:100%">
                <el-table-column align="center" label="程序编号" prop="id" sortable>
                </el-table-column>
                <el-table-column align="center" label="程序名称" prop="procedureName">
                </el-table-column>
                <el-table-column align="center" label="设备名称" prop="equipmentName">
                </el-table-column>
                <el-table-column align="center" label="设备型号" prop="equipmentModel">
                </el-table-column>
                <el-table-column align="center" label="版本号" prop="procedureEquiFileInfo.fileVersion">
                </el-table-column>
                <el-table-column align="center" label="编制日期" prop="procedureEquiFileInfo.compileDate">
                </el-table-column>
                <el-table-column align="center" label="编制人" prop="procedureEquiFileInfo.compileMan">
                </el-table-column>
                <el-table-column align="center" label="审批人" prop="procedureEquiFileInfo.approvalMan">
                </el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <!-- <el-button size="small" @click="updateInfo(scope.$index, scope.row)">修改</el-button> -->
                        <el-button type="text" @click="detail(scope.$index, scope.row)">明细</el-button>
                        <el-button type="text" @click="remove(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  data() {
      return {
          tableData: [],
         
          url: "/procedureEqui/searchProcedurePager",
          pages: 1,
          
          
          search: {
              id:'',
              procedureName: '',
              compileMan:'',
              pageNum: 1,
              pageSize:20
          },
          form:{
              id:'',
              procedureName:'',
              equipmentName:'',
              equipmentModel:'',
              fileVersion:'',
              compileDate:'',
              compileMan:'',
              approvalMan:''
          },
      };
  },
  created() {
      this.getData();
  },
  methods: {
      searchEnterFun (e) {
          let keyCode = window.event? e.keyCode:e.which;
          if(keyCode == 13){
              this.searchLike()
          }
      },
      // 分页导航11
      handleCurrentChange(val) {
          this.search.pageNum = val;
          this.getData();
      },
      searchLike(){
          this.search.pageNum = 1;
          this.getData();
      },
      getData() {
          this.$http.post(this.url, this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.tableData = res.data.data.list;
                  for (let index = 0; index < this.tableData.length; index++) {
                      const element = this.tableData[index].procedureEquiFileInfo.fileVersion;
                      const tempelement="v"+element+".0"
                      this.tableData[index].procedureEquiFileInfo.fileVersion=tempelement;
                  }
                  this.pages = res.data.data.total;
              }
          });
      },
      clearData() {
          (this.search.id = ""),
          (this.search.procedureName = ""),
          (this.search.compileMan = "")
          this.getData();
      },
      add() {
         
          this.$router.push({
              path: "/procedureadd",
            
          });

      },
      detail(index,row) {
          this.$router.push({
              path: "/procedureSearch",
              query: {
                  procedureId: row.id
              }
          });
      },
      updateInfo(index,row) {
          this.$router.push({
              path: "/procedureEdit",
              query: {
                  procedureId: row.id
              }
          });
      },
      //删除
      remove(index, row) {
          this.$confirm('此操作将永久删除该设备程序, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              this.$http.post('/procedureEqui/removeProcedure', {
                  id: row.id
              }).then((res) => {
                  if (res != undefined && res.data.code == 1000) {
                      this.getData();
                      this.$message.success('操作成功');
                  } else {
                      this.$message.error(res.data.message);
                  }
              })
          }).catch(() => {
              this.$message({
                  type: 'info',
                  message: '已取消删除'
              });
          });
      },
      // 保存编辑
      insert(formName) {
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  this.$http.post('/procedure/saveProcedure', this.addform).then((res) => {
                      if (res != undefined && res.data.code == 1000) {
                          this.getData();
                          this.addVisible = false;
                          this.$message.success('新增成功');
                      } else {
                          this.$message.error(res.data.message);
                      }
                  })
              } else {
                  this.$message.error('带*为必填项');
                  return false;
              }
          });
      }
  },
    watch: {
        '$route' (to, from) {
            if (to.path == '/procedureList') {
                this.getData();
            }
        }
    },
};
</script>
