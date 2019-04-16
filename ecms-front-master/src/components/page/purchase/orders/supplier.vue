<template>
    <div class="container">
        <div class="handle-box">
            <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="/purchaseByMaterial">按物料下单</el-menu-item>
                <el-menu-item index="/purchaseBySupplier">按供应商下单</el-menu-item>
            </el-menu>
        </div>
        <div class="handle-box">
            <el-form :v-model="selectionMaterials" class="demo-form-inline" :inline="true">
                <el-form-item label="采购单号">
                  <el-input v-model="selectionMaterials.orderNumber"></el-input>
                </el-form-item>
                <el-form-item label="采购日期">
                  <el-date-picker v-model="selectionMaterials.orderDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="付款时间">
                  <el-date-picker v-model="selectionMaterials.paymentDate" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="采购人">
                  <el-input v-model="selectionMaterials.buyer"></el-input>
                </el-form-item>
                <el-form-item label="预计到货日期:">
                  <el-date-picker v-model="selectionMaterials.estimatedTime" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
            </el-form>
        </div>
        <hr class="marginTop" />
        <span class="text">待采购列表</span>
        <hr class="marginBottom" />
        <el-table :data="materialTableData" border
        style="width:100%" height="230" @selection-change="handleSelectMaterial">
            <el-table-column type="selection" prop="id"></el-table-column>
            <el-table-column label="物料编码" prop="materialBom.materialCode"></el-table-column>
            <el-table-column label="物料名称" prop="materialBom.materialName"></el-table-column>
            <el-table-column label="采购原因" prop="reason"></el-table-column>
            <el-table-column label="采购数量" prop="qty"></el-table-column>
            <el-table-column label="最晚采购时间" prop="lastestTime"></el-table-column>
        </el-table>
        <hr class="marginTop" />
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="insertOrderRecord">确 定</el-button>
            <el-button @click="goBack">取 消</el-button>
        </span>
    </div>
</template>
<script>
export default {
  data() {
    return {
      search: {
        id: null,
        materialId: null,
        pageNum: 1,
        ordered: false
      },
      materialTableData: [],
      selectionMaterials: {
        supplierId: "",
        queueIds: []
      }
    };
  },
  created() {
    if (this.$route.query.queueId != undefined) {
      this.search.id = this.$route.query.queueId;
    }
    if (this.$route.query.materialId != undefined) {
      this.search.materialId = this.$route.query.materialId;
    }
    this.getData();
  },
  computed: {},
  methods: {
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key,
        query: { materialId: this.search.materialId, queueId: this.search.id }
      });
    },
    getData() {
      this.$http
        .post("/purchase/queue/listBySupplierMaterial", this.search)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.materialTableData = res.data.data;
          }
        });
    },
    goBack() {
      this.$router.push("/purchaseQueueList");
    },
    handleSelectMaterial(vals) {
      this.selectionMaterials.queueIds = [];
      vals.forEach(val => {
        this.selectionMaterials.queueIds.push(val.id);
      });
    },
    insertOrderRecord() {
      this.$http
        .post("/purchase/order/orderByMaterial", this.selectionMaterials)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("提交成功");
            this.getData();
          }
        });
      this.goBack();
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

.marginTop {
  margin-top: 10px;
  margin-bottom: 10px;
}

.marginBottom {
  margin-top: 10px;
  margin-bottom: 10px;
}

.text {
  font-size: 12px;
  color: #606266;
  margin-right: 30px;
}
</style>