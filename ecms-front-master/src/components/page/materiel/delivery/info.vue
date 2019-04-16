<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/materielList">物料列表</el-menu-item>
          <el-menu-item index="/entryList">待入库</el-menu-item>
          <el-menu-item index="/storageList">货架管理</el-menu-item>
          <el-menu-item index="4">出入库明细</el-menu-item>
          <el-menu-item index="/pickingList">出库管理</el-menu-item>
          <el-menu-item index="/deliveryList">发货管理</el-menu-item>
          <!--<el-menu-item index="6">生产发料</el-menu-item>-->
        </el-menu>
      </div>
      <div class="handle-box">
        <el-form :model="deliveryForm" label-width="100px">
          <el-row :gutter="30">
            <el-col :span="12">
              <el-form-item label="发货单号">
                <el-input v-model="deliveryForm.deliveryCode"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="合同编号">
                <el-input v-model="deliveryForm.orderId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="12">
              <el-form-item label="计划发货时间">
                <el-date-picker value-format="yyyy-MM-dd" v-model="deliveryForm.planDate"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="操作人">
                <el-input v-model="deliveryForm.preparedBy"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="12">
              <el-form-item label="发货状态">
                <el-select v-model="deliveryForm.deliveryStatus" placeholder="请选择">
                  <el-option v-for="item in statusOptions"
                  :key="item"
                  :label="item"
                  :value="item"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="handle-box">
        <el-button round type="primary" @click="add">增加物料</el-button>
      </div>
      <el-table border style="width:100%" :data="tableData">
        <el-table-column label="序号" prop="materielInventory.id"></el-table-column>
        <el-table-column label="物料编号" prop="materielInventory.materiel.materialBom.materialCode"></el-table-column>
        <el-table-column label="物料名称" prop="materielInventory.materiel.materialBom.materialName"></el-table-column>
        <el-table-column label="原图材料" prop="materielInventory.materiel.materialBom.originalMaterial"></el-table-column>
        <el-table-column label="参数列表">
          <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
            <template slot-scope="scope">
              {{scope.row.materielInventory.materiel.materialBom.materialParameter[param.parameterName]}}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="供应商名称" prop="materielInventory.supplier.supplierName"></el-table-column>
        <el-table-column label="物料批次" prop="materielInventory.materielBatch"></el-table-column>
        <el-table-column label="数量" prop="qty">
          <template slot-scope="scope">
            <span v-if="!scope.row.editFlag">{{scope.row.qty}}</span>
            <div class="el-input" v-if="scope.row.editFlag">
              <input class="el-input__inner" type="text" v-model="scope.row.qty" @input="handleQtyChange(scope.row, scope.$index)" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="!scope.row.editFlag" @click="editQty(scope.row, scope.$index)">修改</el-button>
            <el-button v-if="scope.row.editFlag" @click="confirmQty(scope.row, scope.$index)">确定</el-button>
            <el-button @click="deleteDetail(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span class="dialog-footer">
        <el-button type="primary" @click="insertDelivery">保存</el-button>
        <el-button @click="goBack">返回</el-button>
      </span>
    </div>
    <el-dialog title="选择物料" :visible.sync="materielListVisible" width="60%">
        <div class="handle-box">
          <el-form :inline="true" :model="materielSearch" class="demo-form-inline">
            <el-form-item label="物料编号：">
              <el-input v-model="materielSearch.materialCode"></el-input>
            </el-form-item>
            <el-form-item label="物料名称：">
              <el-input v-model="materielSearch.materialName"></el-input>
            </el-form-item>
            <el-button round @click="getMaterielData">查询</el-button>
          </el-form>
        </div>
        <el-table :data="materielInventoryTable" border style="width:100%" @selection-change="handleInventorySelection">
          <el-table-column label="选择" type="selection" prop="id">
          </el-table-column>
          <el-table-column label="物料编号" prop="materiel.materialBom.materialCode"></el-table-column>
          <el-table-column label="物料名称" prop="materiel.materialBom.materialName"></el-table-column>
          <el-table-column label="原图材料" prop="materiel.materialBom.originalMaterial"></el-table-column>
          <el-table-column label="参数列表">
            <el-table-column v-for="(param, index) in searchParams" :label="param.parameterName==''?('参数'+(index+1)):param.parameterName" :key="param.parameterName">
              <template slot-scope="scope">
                {{scope.row.materiel.materialBom.materialParameter[param.parameterName]}}
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column label="供应商名称" prop="supplier.supplierName"></el-table-column>
          <el-table-column label="物料批次" prop="materielBatch"></el-table-column>
          <el-table-column label="数量" prop="qty"></el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="materielListVisible = false">取 消</el-button>
          <el-button type="primary" @click="appendDetail">确 定</el-button>
        </span>
      </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/materiel/delivery/info",
      pages: 1,
      search: {
        deliveryId: 0,
        repertoryId: 0,
        pageNum: 1
      },
      materielSearch: {
        pageNum: 1
      },
      //属性列表
      searchParams: [],
      deliveryForm: {},
      //库存明细列表
      materielListVisible: false,
      materielPages: 1,
      materielInventoryTable: [],
      //出库状态
      statusOptions: ["待确认", "完成", "取消"],
      selectedInventory: []
    };
  },
  created() {
    this.getData();
  },
  computed: {},
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
      if (this.$route.query.deliveryId != undefined) {
        this.search.deliveryId = this.$route.query.deliveryId;

        this.$http.post("/material/paramNames").then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.searchParams = res.data.data;
          }
        });
        this.$http
          .post("/materiel/delivery/detail/listByDeliveryId", this.search)
          .then(res => {
            if (res != undefined && res.data.code == 1000) {
              this.tableData = res.data.data.list;
              this.pages = res.data.data.total;
            }
          });
        this.$http.post(this.url, this.search).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.deliveryForm = res.data.data;
          }
        });
      }
    },
    getMaterielData() {
      if (this.$route.query.repertoryId != undefined) {
        this.materielSearch.repertoryId = this.$route.query.repertoryId;
      }
      this.materielSearch.materiel = JSON.stringify(
        this.materielSearch.materiel
      );
      this.$http
        .post("/materiel/inventory/seachByNameAndCode", this.materielSearch)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.materielInventoryTable = res.data.data.list;
            this.materielPages = res.data.data.total;
          }
        });
    },
    add() {
      this.materielListVisible = true;
    },
    appendDetail() {
      let existData = this.tableData;
      this.selectedInventory = this.selectedInventory.filter(function(
        item,
        index,
        arr
      ) {
        var rtn = true;
        existData.forEach(row => {
          if (row.materielInventory.id == item.materielInventory.id) {
            rtn = false;
          }
        });
        return rtn;
      });
      this.selectedInventory.forEach(selectedRow => {
        this.tableData.push(selectedRow);
      });
      this.materielInventoryTable = [];
      this.materielListVisible = false;
    },
    edit(row) {},
    insertDelivery() {
      this.$http
        .post("/materiel/delivery/saveOrUpdate", this.deliveryForm)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.deliveryForm = res.data.data;
            this.tableData.forEach(detail => {
              detail.materielId = detail.materielInventory.id;
              detail.deliveryId = res.data.data.id;
              delete detail.materielInventory;
              delete detail.delivery;
              this.$http
                .post("/materiel/delivery/detail/saveOrUpdate", detail)
                .then(res => {});
            });
            this.$message.success("保存成功");
            this.goBack();
          }
        });
    },
    goBack() {
      this.$router.push({
        path: "/deliveryList",
        query: { repertoryId: this.search.repertoryId }
      });
    },
    handleInventorySelection(vals) {
      this.selectedInventory = [];
      vals.forEach(val => {
        this.selectedInventory.push({
          editFlag: false,
          qty: val.qty,
          materielInventory: val
        });
      });
    },
    editQty: function(row, index) {
      this.$set(this.tableData[index], "editFlag", true);
    },
    confirmQty: function(row, index) {
      this.$set(this.tableData[index], "editFlag", false);
    },
    deleteDetail(row) {
      var index = this.tableData.indexOf(row);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
    },
    handleQtyChange(row, index) {
      var qty = parseFloat(row.qty);
      if (qty > row.materielInventory.qty) {
        this.$set(
          this.tableData[index],
          "qty",
          this.tableData[index].materielInventory.qty
        );
      }
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