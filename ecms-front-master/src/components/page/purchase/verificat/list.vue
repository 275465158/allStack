<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/purchaseQueueList">待采购</el-menu-item>
                    <el-menu-item index="/purchaseOrderVerificatList">采购单核销</el-menu-item>
                    <el-menu-item index="/purchaseOrderList">采购记录</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="采购单号">
                        <el-input v-model="search.purchaseCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料编号">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型">
                        <el-input v-model="search.type"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商">
                        <el-input v-model="search.supplierName"></el-input>
                    </el-form-item>
                    <el-form-item label="材料">
                        <el-input v-model="search.originalMaterial"></el-input>
                    </el-form-item>
                    <el-form-item label="参数">
                        <el-input v-model="search.materialBomParamValueStr"></el-input>
                    </el-form-item>
                    <el-form-item label="采购原因">
                        <el-input v-model="search.purchaseReason"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike">查询</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待核销列表</span>
                <el-button style="float:right;" :loading="derive" size="small" @click="exportPurchaseList">导出待核销单
                    <i class="el-icon-download el-icon--right"></i>
                </el-button>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="purchaseCode" label="采购单号"></el-table-column>
                <el-table-column align="center" prop="supplierName" label="供应商"></el-table-column>
                <el-table-column align="center" prop="materialCode" label="物料编号"></el-table-column>
                <el-table-column align="center" prop="type" label="物料类型"></el-table-column>
                <el-table-column align="center" prop="materialName" label="物料名称"></el-table-column>
                <el-table-column align="center" prop="originalMaterial" label="材料"></el-table-column>
                <el-table-column align="center" prop="materialBomParamValueStr" label="参数"></el-table-column>
                <el-table-column align="center" prop="drawingCode" label="图号" ></el-table-column>
                <el-table-column align="center" prop="purchaseQty" label="采购数量"></el-table-column>
                <el-table-column align="center" prop="purchaseReason" label="采购原因"></el-table-column>
                <el-table-column align="center" prop="receivedQty" label="实际收货数量"></el-table-column>
                <el-table-column align="center" prop="purchaseDate" label="采购日期"></el-table-column>
                <el-table-column align="center" prop="arriveDate" label="交货日期"></el-table-column>
                <el-table-column align="center" prop="purchasePriceTotal" label="采购金额"></el-table-column>
                <el-table-column align="center" prop="verificatStatus" label="核销状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.verificatStatus === 0">待核销</div>
                        <div v-if="scope.row.verificatStatus === 1">已核销</div>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <div v-if="scope.row.verificatStatus === 0">
                            <el-button type="text" @click="verificat(scope.$index,scope.row)">核销</el-button>
                        </div>
                        <el-button type="text" @click="detail(scope.$index,scope.row)">明细</el-button>
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
    import fileDownload from 'js-file-download';
    export default {
  data() {
      return {
          tableData: [],
          derive: false,
          pages: 1,
          search: {
              purchaseCode:'',
              materialCode:'',
              supplierName:'',
              type:'',
              materialName:'',
              originalMaterial:'',
              materialBomParamValueStr:'',
              purchaseReason:'',
              status: 1,
              pageNum: 1,
              pageSize:20,
          },
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
      searchLike(){
          this.search.pageNum = 1;
          this.getData();
      },
      handleSelect(key, keyPath) {
          this.$router.push({
              path: key
          });
          }, // 获取 easy-mock 的模拟数据
      getData() {
          let _this = this
          _this.loading = true
          this.$http.post("/purchaseOrdersList/purchaseOrdersList", this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
              this.tableData = res.data.data.list;
                  this.pages = res.data.data.total;
              }
          _this.loading = false
          })
              .catch(err => {
                  _this.loading = false
              });
          },
      // 分页导航
      handleCurrentChange(val) {
          this.search.pageNum = val;
          this.getData();
          },
      detail(index,row) {
          this.$router.push({
              path: "/purchaseOrderListInfo",
              query: { id: row.id }
          });
      },
      verificat(index,row){
          this.$router.push({
              path: "/purchaseOrderVerificatInfo",
              query: { id: row.id }
          });
      },
      exportPurchaseList() {
          this.derive = true
          this.$http.get('/purchaseOrdersList/export/list',{responseType: 'blob'}).then((res) => {
              fileDownload(res.data, '待核销列表.xls');
              this.derive = false
          })
              .catch(err => {
                  this.derive = false
              })
      },
  },
  watch: {
      $route: "getData"
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
