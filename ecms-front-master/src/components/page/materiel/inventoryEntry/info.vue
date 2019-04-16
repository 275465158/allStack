<template>
    <div class="container">
        <div class="handle-box">
            <el-form label-width="100px" :model="entryForm">
                <el-row :gutter="30">
                    <el-col :span="12">
                        <el-form-item label="采购单号">
                            <span v-if="entryForm.purchaseOrderInfo">
                            {{entryForm.purchaseOrderInfo.orderNumber}}
                            </span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="供应商名称">
                            <span v-if="entryForm.supplier">
                            {{entryForm.supplier.supplierName}}
                            </span>                            
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="30">
                    <el-col :span="12">
                        <el-form-item label="物料编号">
                            <span v-if="entryForm.materialBom">
                            {{entryForm.materialBom.materialCode}}
                            </span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料名称">
                            <span v-if="entryForm.materialBom">
                            {{entryForm.materialBom.materialName}}
                            </span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="30">
                    <el-col :span="12">
                        <el-form-item label="采购数量">
                            {{entryForm.qty}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="收货数量">
                            {{entryForm.arrivedTotalQty}}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="30">
                    <el-col :span="12">
                        <el-form-item label="不合格数量">
                            {{entryForm.unqualifiedTotalQty}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="入库数量">
                            {{entryForm.entryTotalQty}}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="30">
                  <el-col :span="24">
                    <el-form-item label="选择库位">
                        <el-cascader v-model="entryForm.shelfPosition" :options="optionsRepertory">
                        </el-cascader>
                    </el-form-item>
                  </el-col>
                </el-row>
            </el-form>
        </div>
        <hr class="marginTop" />
        <span class="text">到货明细</span>
        <el-button size="small" @click="noticeArrived">新增到货</el-button>
        <hr class="marginBottom" />
        <el-table :data="entryDetailList" border style="width:100%">
            <el-table-column label="到货批次号" prop="arrivedBatch"></el-table-column>
            <el-table-column label="到货数量" prop="arrivedQty"></el-table-column>
            <el-table-column label="到货时间" prop="arrivedDate"></el-table-column>
            <el-table-column label="签收人" prop="consignee"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <span v-if="scope.row.inspectStatus==1">
                    <el-button size="small" @click="noticeInspect(scope.row)">检验</el-button>
                    </span>
                    <span v-if="scope.row.inspectStatus==0&&scope.row.entryStatus!=0&&scope.row.qualifiedRate>0">
                    <el-button size="small" @click="noticeEntry(scope.row)">入库</el-button>
                    </span>
                    <span v-if="scope.row.entryStatus==0&&scope.row.inspectStatus==0">
                    已入库
                    </span>
                </template>
            </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="insertEntryMaterial">保存</el-button>
            <el-button @click="goBack">取 消</el-button>
        </span>

        <el-dialog title="到货通知" :visible.sync="noticeArrivedVisible" width="60%">
            <el-form ref="arrivedForm" :model="arrivedForm" label-width="100px">
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
        <el-dialog title="到货检验" :visible.sync="noticeInspectVisible" width="60%">
            <el-form ref="inspectForm" :model="inspectForm" label-width="100px">
                <el-form-item label="待检验数量">
                    {{inspectForm.arrivedQty}}
                </el-form-item>
                <el-form-item label="不合格数">
                    <el-input v-model="inspectForm.unqualifiedQty" @input="handleUnqualifiedQty"></el-input>
                </el-form-item>
                <el-form-item label="合格数">
                    <el-input v-model="inspectForm.qualifiedQty"></el-input>
                </el-form-item>
                <el-form-item label="合格率">
                    <el-input v-model="inspectForm.qualifiedRate"></el-input>
                </el-form-item>
                <el-form-item label="检验人">
                    <el-input v-model="inspectForm.inspector"></el-input>
                </el-form-item>
                <el-form-item label="检验时间">
                    <el-date-picker v-model="inspectForm.inspectDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertInspect">保存</el-button>
                <el-button @click="noticeInspectVisible = false">取 消</el-button>
            </span>
        </el-dialog>
        <el-dialog title="确定入库" :visible.sync="noticeEntryVisible" width="60%">
            <el-form ref="entryForm" :model="entryDetailForm" label-width="100px">
                <el-form-item label="入库数量">
                    <el-input v-model="entryDetailForm.entryQty" readonly></el-input>
                </el-form-item>
                <el-form-item label="入库时间">
                    <el-date-picker v-model="entryDetailForm.entryDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertEntry">保存</el-button>
                <el-button @click="noticeEntryVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
export default {
  data() {
    return {
      search: {
        repertoryId: 0,
        entryId: 0
      },
      entryForm: {},
      entryDetailList: [],
      noticeArrivedVisible: false,
      arrivedForm: {},
      noticeInspectVisible: false,
      inspectForm: {},
      noticeEntryVisible: false,
      entryDetailForm: {},
      inventoryForm: {},
      optionsRepertory: []
    };
  },
  created() {
    if (this.$route.query.repertoryId != null) {
      this.search.repertoryId = this.$route.query.repertoryId;
    }
    if (this.$route.query.id != null) {
      this.search.entryId = this.$route.query.id;
    }
    this.getData();
  },
  computed: {},
  methods: {
    handleUnqualifiedQty(val) {
      let arrivedQty = parseFloat(this.inspectForm.arrivedQty),
        unqualifiedQty = parseFloat(val);
      if (val == undefined) {
        this.inspectForm.unqualifiedQty = 0;
      }
      if (unqualifiedQty > arrivedQty) {
        this.inspectForm.unqualifiedQty = arrivedQty;
        this.inspectForm.qualifiedQty = 0;
        this.inspectForm.qualifiedRate =
          this.inspectForm.qualifiedQty / this.inspectForm.arrivedQty;
      } else {
        this.inspectForm.qualifiedQty =
          arrivedQty - this.inspectForm.unqualifiedQty;
        this.inspectForm.qualifiedRate =
          this.inspectForm.qualifiedQty / this.inspectForm.arrivedQty;
      }
    },
    getData() {
      this.$http.post("/materiel/entry/info", { id: this.search.entryId }).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.entryForm = res.data.data;
            if (this.entryForm.shelfPosition == undefined) {
              this.entryForm.shelfPosition = [];
            } else {
              this.entryForm.shelfPosition = this.entryForm.shelfPosition.split(
                "-"
              );
            }
          }
        });
      this.$http.post("/materiel/entry/detail/listByEntryId", {id: this.search.entryId}).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.entryDetailList = res.data.data;
          }
        });
      this.$http.post("/storage/getJson", { repertoryId: this.search.repertoryId }).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.optionsRepertory = JSON.parse(res.data.data);
          }
        });
    },
    goBack() {
      this.$router.push({
        path: "/entryList",
        query: { repertoryId: this.search.repertoryId }
      });
    },
    noticeArrived() {
      this.$http.post("/materiel/entry/info", { id: this.search.entryId }).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.arrivedForm = res.data.data;
            this.arrivedForm.id = null;
            //剩余未到货数量包括不合格数量
            this.arrivedForm.arrivedQty =
              res.data.data.qty -
              res.data.data.arrivedTotalQty +
              res.data.data.unqualifiedTotalQty;
            this.arrivedForm.entryId = this.search.entryId;
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
            this.getData();
          }
        });
    },
    noticeInspect(row) {
      this.$http
        .post("/materiel/entry/detail/info", { id: row.id })
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.inspectForm = res.data.data;
            this.inspectForm.entryId = this.search.entryId;
            this.inspectForm.entryInfo = JSON.stringify(
              this.inspectForm.entryInfo
            );
            this.inspectForm.qualifiedQty = res.data.data.arrivedQty;
            this.inspectForm.inspectDate = new Date();
            this.noticeInspectVisible = true;
          }
        });
    },
    insertInspect() {
      this.inspectForm.inspectStatus = "0";
      this.$http
        .post("/materiel/entry/detail/saveOrUpdate", this.inspectForm)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("保存成功!");
            this.noticeInspectVisible = false;
            this.getData();
          }
        });
    },
    noticeEntry(row) {
      this.$http
        .post("/materiel/entry/detail/info", { id: row.id })
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.entryDetailForm = res.data.data;
            this.entryDetailForm.entryId = this.search.entryId;
            this.entryDetailForm.entryInfo = JSON.stringify(
              this.entryDetailForm.entryInfo
            );
            this.entryDetailForm.entryQty =
              res.data.data.arrivedQty - res.data.data.unqualifiedQty;
            this.entryDetailForm.entryDate = new Date();
            this.noticeEntryVisible = true;
          }
        });
    },
    insertEntry() {
      this.$http.post("/materiel/entry/detail/entry", this.entryDetailForm).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$http.post("/materiel/inventory/fromEntry", {entryDetailId: this.entryDetailForm.id}).then(res => {
                this.$message.success("入库成功!");
                this.noticeEntryVisible = false;
                this.getData();
              });
          }
        });
    },
    insertEntryMaterial() {
        this.entryForm.materialBom = JSON.stringify(this.entryForm.materialBom);
        this.entryForm.purchaseOrderInfo = JSON.stringify(
            this.entryForm.purchaseOrderInfo
        );
        this.entryForm.shelfPosition = this.entryForm.shelfPosition.join("-");
        this.entryForm.supplier = JSON.stringify(this.entryForm.supplier);
        this.$http.post("/materiel/entry/saveOrUpdate", this.entryForm).then(res => {
            if (res != undefined && res.data.code == 1000) {
                this.$message.success("入库成功!");
                this.getData();
                this.$router.push("/entryList");
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

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
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
</style>