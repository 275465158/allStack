<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/materielList">物料列表</el-menu-item>
                    <el-menu-item index="/entryList">待入库</el-menu-item>
                    <el-menu-item index="/storageList">货架管理</el-menu-item>
                    <el-menu-item index="/materielDetailList">出入库明细</el-menu-item>
                    <el-menu-item index="/pickingList">出库管理</el-menu-item>
                    <el-menu-item index="/deliveryList">发货管理</el-menu-item>
                    <!--<el-menu-item index="/materielProduceList">生产发料</el-menu-item>-->
                </el-menu>
            </div>
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="物料编号：">
                        <el-input v-model="search.materielCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称：">
                        <el-input v-model="search.materielName"></el-input>
                    </el-form-item>
                    <el-button round @click="getData">查询</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">待入库列表</span>
                <el-button round type="primary" @click="add">新建入库单</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column prop="materialBom.materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialBom.materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialBom.originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column prop="purchaseCause" label="采购原因">
                </el-table-column>
                <el-table-column prop="estimatedDate" label="预计到货时间">
                </el-table-column>
                <el-table-column prop="qty" label="采购数量">
                </el-table-column>
                <el-table-column prop="arrivedTotalQty" label="到货数量">
                </el-table-column>
                <el-table-column prop="unqualifiedTotalQty" label="不合格数量">
                </el-table-column>
                <el-table-column prop="entryTotalQty" label="入库数量">
                </el-table-column>
                <el-table-column label="参数列表">
                  <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
                      <template slot-scope="scope">
                          {{scope.row.materialBom.materialParameter[param.parameterName]}}
                      </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                      <span v-if="scope.row.arrivedAll">
                      <el-button size="small" @click="noticeArrived(scope.row)">通知到货检验</el-button>
                      </span>
                      <el-button size="small" @click="edit(scope.row)">明细</el-button>
                    </template>
                </el-table-column>
                
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <el-dialog title="到货通知" :visible.sync="noticeArrivedVisible" width="60%">
              <el-form ref="arrivedForm" :model="arrivedForm" label-width="100px">
                <el-form-item label="物料名称:">
                  <span v-if="arrivedForm.materialBom">{{arrivedForm.materialBom.materialName}}</span>
                </el-form-item>
                <el-form-item label="供应商:">
                  <span v-if="arrivedForm.supplier">{{arrivedForm.supplier.supplierName}}</span>
                </el-form-item>
                <el-form-item label="到货批次号">
                  <el-input v-model="arrivedForm.arrivedBatch"></el-input>
                </el-form-item>
                <el-form-item label="到货数量:">
                  <el-input v-model="arrivedForm.arrivedQty"></el-input>
                </el-form-item>
                <el-form-item label="到货时间:">
                  <el-date-picker v-model="arrivedForm.arrivedDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="保质期:">
                  <el-date-picker v-model="arrivedForm.expiryDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item> 
                <el-form-item label="签收人:">
                  <el-input v-model="arrivedForm.consignee"></el-input>
                </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertArrived">保存</el-button>
                <el-button @click="noticeArrivedVisible = false">取 消</el-button>
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
      url: "/materiel/entry/list",
      pages: 1,
      search: {
        status: 1,
        pageNum: 1,
        repertoryId: 0
      },
      searchParams: [],
      //到货明细
      //对话框现实控制
      noticeArrivedVisible: false,
      //到货明细
      arrivedForm: {}
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        if (d.entryTotalQty < d.qty) {
          d.arrivedAll = true;
        }
        return d;
      });
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key,
        query: { repertoryId: this.search.repertoryId }
      });
    },
    handleChange(value) {
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    getData() {
      if (this.$route.query.repertoryId != null) {
        this.search.repertoryId = this.$route.query.repertoryId;
      } else {
        //this.$router.push("/repertoryList");
      }
      this.$http.post("/material/paramNames").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.searchParams = res.data.data;
        }
      });
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    add() {},
    edit(row) {
      this.$router.push({
        path: "/entryInfo",
        query: { id: row.id, repertoryId: this.search.repertoryId }
      });
    },
    noticeArrived(row) {
      this.$http.post("/materiel/entry/info", { id: row.id }).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.arrivedForm = res.data.data;
          this.arrivedForm.id = null;
          this.arrivedForm.arrivedQty =
            res.data.data.qty - res.data.data.arrivedTotalQty;
          this.arrivedForm.entryId = row.id;
          this.noticeArrivedVisible = true;
        }
      });
    },
    insertArrived() {
      this.$http
        .post("/materiel/entry/detail/saveOrUpdate", this.arrivedForm)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("保存成功!");
            this.noticeArrivedVisible = false;
          }
        });
    }
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
