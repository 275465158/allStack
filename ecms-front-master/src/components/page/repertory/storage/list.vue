<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materialRepertoryList">库存总表</el-menu-item>
                    <el-menu-item index="/repertoryStayDelivery">待收货</el-menu-item>
                    <el-menu-item index="/repertoryStayInbound">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/inboundLogList">入库明细</el-menu-item>
                    <el-menu-item index="/outboundLogList">出库明细</el-menu-item>
                    <!--<el-menu-item index="/sendMaterialLogList">发货记录</el-menu-item>-->
                    <el-menu-item index="/productionIssue">生产发料</el-menu-item>
                    <el-menu-item index="/sendDeliveryList">发货</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="selectByStorageId" :inline="true" :model="search" class="demo-from-inline">
                    <el-form-item label="货架编号：">
                        <el-input v-model="search.storageNum" ></el-input>
                    </el-form-item>
                    <el-button round @click="selectByStorageId">搜索</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">货架列表</span>
                <el-button round type="primary" @click="add">创建货架</el-button>
            </div>
            <el-table v-loading="loading" :data="tableData" border style="width:100%">
                <!--<el-table-column prop="id" label="序号" sortable>
                </el-table-column>-->
                <!-- <el-table-column prop="storageNum" label="货架位编码">
                </el-table-column> -->
                <el-table-column prop="storageNum" label="货架编号">
                </el-table-column>
                <el-table-column prop="storageRow" label="货架层数">
                </el-table-column>
                <el-table-column prop="storageLayer" label="货架排数">
                </el-table-column>
                
                <el-table-column prop="storageColumn" label="货架列数">
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="edit(scope.$index, scope.row)">修改</el-button>
                        <el-button size="small" @click="del(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
                
                
            </el-table>
            <!--<div class="pagination">
                <el-pagination :page-size="40" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>-->
            <el-dialog title="创建货架" :visible.sync="batchEditVisible" width="30%">

                <el-form ref="batchForm" :model="batchForm" label-width="100px">
                    <el-form-item label="货架编号:">
                        <el-input-number v-model="batchForm.storageNum"
                        :min="1" :max="100" size="small" controls-position="right" ></el-input-number>
                        
                    </el-form-item>
                    <el-form-item label="货架层数:">
                        <el-input-number v-model="batchForm.storageRow" :min="1" :max="100" size="small" controls-position="right"></el-input-number>
                        
                    </el-form-item>
                    <el-form-item label="货架排数:">
                        <el-input-number v-model="batchForm.storageLayer"
                        :min="1" :max="100" size="small" controls-position="right"></el-input-number>
                       
                    </el-form-item>
                    
                    <el-form-item label="货架列数:">
                        <el-input-number v-model="batchForm.storageColumn" :min="1" :max="100" size="small" controls-position="right"></el-input-number>
                       
                    </el-form-item>
                   
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="batchEditVisible = false">取 消</el-button>
                    <el-button type="primary" @click="batchInsert">确 定</el-button>
                </span>
            </el-dialog>
            <el-dialog title="编辑货架" :visible.sync="editVisible" width="30%">
                <el-form ref="form" :model="updateForm" label-width="100px">
                    <!-- <el-form-item label="货架位编码:">
                        <el-input v-model="batchForm.storageNum"></el-input>
                    </el-form-item> -->
                    <el-form-item label="货架编号:">
                        <el-input-number v-model="updateForm.storageNum" :min="1" disabled></el-input-number>
                    </el-form-item>
                     <el-form-item label="货架层数:">
                        <el-input-number v-model="updateForm.storageRow" :min="1" :max="10"></el-input-number>
                    </el-form-item>
                    <el-form-item label="货架排数:">
                        <el-input-number v-model="updateForm.storageLayer" :min="1" :max="10"></el-input-number>
                    </el-form-item>
                    <el-form-item label="货架列数:">
                        <el-input-number v-model="updateForm.storageColumn" :min="1" :max="100"></el-input-number>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <!-- 发送编辑请求 -->
                    <el-button type="primary" @click="batchUpdate">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
export default {
  data() {
      return {
          tableData: [],
          url: "/storage/getStorageList",
          pages: 1,
          search: {
              storageNum: "",
              status: 1,
              pageNum: 1,
              repertoryId: ""
          },
          info:{
              repertoryId:'',
              storageNum:'',
          },
          batchEditVisible: false,
          editVisible: false,
          batchForm: {
              id:"",
              storageNum: "",
              storageLayer: "",
              storageRow: "",
              storageColumn: "",
              repertoryId: "",
              status: 1
          },
          updateForm:{
              storageNum: "",
              storageLayer: "",
              storageRow: "",
              storageColumn: "",
              repertoryId: "",
              status: 1
          },
          repertoryName: '',
          loading: false
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
      getData() {
          if (this.$route.query.repertoryId != null) {
              this.search.repertoryId = this.$route.query.repertoryId;
              this.batchForm.repertoryId = this.$route.query.repertoryId;
              this.repertoryName = this.$route.query.repertoryName
          }
          this.$http.post(this.url, this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.tableData = res.data.data;
              }
          });
          },
      selectByStorageId(){
          let _this = this
          _this.loading = true
          this.$http.post("/storage/getStorageList",this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.tableData = res.data.data;
                  //this.pages = res.data.data.total;
              }
              _this.loading = false
          })
              .catch(err => {
                  console.log(err);
                  _this.loading = false
              });
      },
      handleSelect(key, keyPath) {
          this.$router.push({
              path: key,
              query: { repertoryId: this.search.repertoryId, repertoryName: this.repertoryName }
          });
          },
      handleChange(value) {
      },
      // 分页导航
      handleCurrentChange(val) {
          this.search.pageNum = val
          this.getData();
          },
      add() {//此方法是点击按钮后弹出的响应事件  并不是提交到服务器的请求
          // 发送请求是为了获取数据库中的信息显示在页面上
          this.$http.post("/storage/lastStorage", {repertoryId: this.$route.query.repertoryId }).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  let startcolumn = res.data.data.storageColumn;
                  if (startcolumn > 0) {
                      this.batchForm.startstorageNum = res.data.data.storageNum;
                      this.batchForm.startstorageLayer = res.data.data.storageLayer;
                      this.batchForm.startstorageRow = res.data.data.storageRow;
                      this.batchForm.startstorageColumn = parseInt(startcolumn) + 1;
                      this.batchForm.maxstorageNum = res.data.data.storageNum;
                      this.batchForm.maxstorageLayer = res.data.data.storageLayer;
                      this.batchForm.maxstorageRow = res.data.data.storageRow;
                      this.batchForm.maxstorageColumn = parseInt(startcolumn) + 2;
                  }
              }
          });
          this.batchEditVisible = true;
          },
      edit(index, row) {
          if (this.$route.query.repertoryId != null) {
              this.batchForm.repertoryId = this.$route.query.repertoryId;
              this.info.repertoryId = this.$route.query.repertoryId;
              this.info.storageNum = row.storageNum
          }
          this.$http.post("/storage/getStorageList", this.info).then(res => {
              this.form = res.data.data;
              this.updateForm = res.data.data[0]
          });
          this.editVisible = true;
          },
      del(index, row) {
          row.repertoryId = this.$route.query.repertoryId;
          this.$confirm("此操作将永久删除当前库位, 是否继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
          }).then(() => {this.$http.post("/storage/deleteStorage", row).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.getData();
                  this.$message({
                      type: "success",
                      message: "删除成功!"
                  });
              }
          });
          }).catch(() => {
              this.$message({
                  type: "info",
                  message: "已取消删除"
              });
          });
          },
      batchInsert() {
          this.$http.post("/storage/insertbatch", this.batchForm).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.getData();
                  this.$message.success("增加库位成功");
                  this.batchEditVisible=false
              } else {
                  this.$message.error(res.data.message);
              }
          });
          },
      batchUpdate(){
          this.updateForm.repertoryId = this.$route.query.repertoryId;
          this.$http.post("/storage/updatebatch", this.updateForm).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.editVisible=false
                  this.getData();
                  this.$message.success("修改成功");
              } else {
                  this.$message.error(res.data.message);
              }
          });
      }
  },
  watch: {
      '$route' (to, info) {
          if (to.path == '/storageList') {
              let newId = this.$route.query.repertoryId;
              if (newId == undefined) {
                  newId = 0;
              }
              if (this.search.repertoryId != newId) {
                  this.search.repertoryId = newId;
                  this.getData();
              }
          }
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

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
</style>
