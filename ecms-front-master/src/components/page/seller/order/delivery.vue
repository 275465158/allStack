<template>
    <div class="table">
        <div class="container">
            <span class="text">订单发货</span>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-form :inline="true" :model="form" class="demo-form-inline">
                    <el-form-item label="订单编号:">
                        <el-input v-model="form.orderCode" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="合同编号:">
                        <el-input v-model="form.contractId" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="客户名称:">
                        <el-input v-model="form.customerName" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="产品数量:">
                        <el-input v-model="form.productNumber" disabled></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div style="text-align:center;">
                <el-table :data="tableData" border style="width:100%">
                    <el-table-column label="物料编号" align="center" prop="materialBomInfo.materialCode">
                    </el-table-column>
                    <el-table-column label="工厂物料编号" align="center" prop="materialBomInfo.factoryMaterialCode">
                    </el-table-column>
                    <el-table-column label="物料类型" align="center" prop="materialBomInfo.type">
                    </el-table-column>
                    <el-table-column label="物料名称" align="center" prop="materialBomInfo.materialName">
                    </el-table-column>
                    <el-table-column label="原图材料" align="center" prop="materialBomInfo.originalMaterial">
                    </el-table-column>
                    <el-table-column label="参数" align="center" prop="materialBomInfo.materialBomParamValueStr">
                    </el-table-column>
                    <el-table-column label="来源" align="center" prop="materialBomInfo.source">
                    </el-table-column>
                    <el-table-column label="数量" align="center" prop="productNumber">
                    </el-table-column>
                    <el-table-column label="库存数" align="center" prop="inventoryQty">
                    </el-table-column>
                    <el-table-column label="已发数" align="center" prop="alreadySendQty">
                    </el-table-column>
                    <el-table-column label="发货数" align="center">
                        <template slot-scope="scope">
                            <el-input v-model.number="scope.row.sendMaterialQty" :disabled="scope.row.materialStatus!=2" min="0" :max="scope.row.productNumber" type="number" v-on:input="change(scope.row,scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                </el-table>
                <el-button @click="delivery" type="primary">发货</el-button>
                <el-button @click="goBack">返回</el-button>
            </div>
        </div>
    </div>
</template>

<!--aaaaaa-->
<script>
import "vue-easytable/libs/themes-base/index.css";
import * as utils from '../../../../../static/js/utils.js';
import fileDownload from 'js-file-download';
export default {
  data() {
      return {
          tableData: [],
          search:{
              id:'',
          },
          form:{
              orderCode:'',
              contractId:'',
              customerName:'',
              productNumber:'',
              materialSendList:[]
          },
          insertFlag:1
      };
  },
  created() {
      this.getData();
  },
  computed: {},
  methods: {
      getData() {
          if (this.$route.query.id != undefined) {
              this.form.contractId = this.$route.query.row.contractId;
              this.form.customerName = this.$route.query.row.customerName;
              this.form.productNumber = this.$route.query.row.productNumber;
              this.form.orderCode = this.$route.query.row.orderCode;
              this.search.id = this.$route.query.id;
          }
          this.$http.post("sellerOrderProduct/searchListById",{id:this.search.id}).then((res) => {
              if (res != undefined && res.data.code == 1000) {
                  this.tableData = res.data.data;
                  console.log("this.tableData:",this.tableData)
              }
          })
      },
      delivery(){
          if(this.insertFlag == 1){
              this.$http.post("materialSend/materialSend",this.form).then((res) => {
                  if (res != undefined && res.data.code == 1000) {
                      this.$message.success("发货成功");
                      this.getData();
                  }
              })
          }else{
              this.$message.error("请核对输入");
          }
      },
      goBack() {
          this.$router.push("/sellerOrder");
      },
      change(row,index) {
          var alreadySendQty = row.alreadySendQty
          if(alreadySendQty == null){
              alreadySendQty = 0
          }
          if(parseFloat(row.sendMaterialQty) + parseFloat(alreadySendQty) > parseFloat(row.productNumber)){
              this.$message.error("发货数量不能大于总数量");
              this.insertFlag = 0
          }
          if(parseFloat(row.sendMaterialQty) + parseFloat(alreadySendQty) > parseFloat(row.inventoryQty)){
              this.$message.error("发货数量不能库存数");
              this.insertFlag = 0
          }
          if(parseFloat(row.sendMaterialQty) + parseFloat(alreadySendQty) <= parseFloat(row.productNumber) && parseFloat(row.sendMaterialQty) + parseFloat(alreadySendQty) <= parseFloat(row.inventoryQty)){
              this.insertFlag = 1
          }
          var sendList = {
              productId:row.productId,
              productName:row.productName,
              productCode:row.productCode,
              sendMaterialQty:row.sendMaterialQty,
              sellerOrderProductId:row.id
          }
          this.form.materialSendList.splice(index,1,sendList)
          if(row.sendMaterialQty == null || row.sendMaterialQty == ""){
              this.form.materialSendList.splice(index,1)
          }
      }
  },
  watch: {
      '$route' (to, form) {
          if (to.path == '/sellerOrderDelivery') {
              this.getData();
          }
      }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 50px;
}
.product_table {
  margin-bottom: 20px;
  font-size: 12px;
  color: #606266;
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
.minorSelect {
  width: 120px;
}
</style>
    