<template>
    <div class="container">
        <div class="handle-box">
            <div class="supplier">
                <span class="text">供应商信息</span>
            </div>
            <el-form :inline="true" :model="form" ref="form" label-width="100px">
                <el-form-item label="供应商名称">
                    <el-input disabled v-model="form.supplierName"></el-input>
                </el-form-item>
                <el-form-item label="联系人">
                    <el-input disabled v-model="form.supplierContact"></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input disabled v-model.number="form.supplierNumber"></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input disabled v-model.number="form.supplierPhone"></el-input>
                </el-form-item>
                <el-form-item label="传真号码">
                    <el-input disabled v-model.number="form.supplierFaxNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input disabled v-model="form.supplierEmail"></el-input>
                </el-form-item>
            </el-form>
            <div class="supplier">
                <span class="text">采购信息</span>
            </div>
            <el-form :inline="true" :model="pruchaseForm" ref="pruchaseForm" label-width="100px">
                <el-form-item label="采购人">
                    <el-input disabled v-model="pruchaseForm.pruchase"></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input disabled v-model.number="pruchaseForm.purchasePhoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input disabled v-model.number="pruchaseForm.purchasePhone"></el-input>
                </el-form-item>
                <el-form-item label="传真号码">
                    <el-input disabled v-model.number="pruchaseForm.purchaseFaxNumber"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱">
                    <el-input disabled v-model.number="pruchaseForm.purchaseEmail"></el-input>
                </el-form-item>
                <el-form-item label="是否含税">
                    <el-input disabled v-model="pruchaseForm.ifTax"></el-input>
                </el-form-item>
                <el-form-item label="是否含运费">
                    <el-input disabled v-model="pruchaseForm.ifFreight"></el-input>
                </el-form-item>
                <el-form-item label="产品价格">
                    <el-input disabled v-model="pruchaseForm.productPriceTotal"></el-input>
                </el-form-item>
                <el-form-item label="采购单总价">
                    <el-input disabled v-model="pruchaseForm.subtotal"></el-input>
                </el-form-item>
                <el-form-item label="运费">
                    <el-input disabled type="number" v-model.number="pruchaseForm.freight"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input disabled v-model="pruchaseForm.remark"></el-input>
                </el-form-item>
                <el-form-item label="仓库选择">
                    <el-input disabled v-model="pruchaseForm.repertoryName"></el-input>
                </el-form-item>
            </el-form>


            <hr class="marginTop" />
            <span class="text">物料明细</span>
            <hr class="marginBottom" />
            <el-button style="margin-bottom: 10px" @click="goPrint">打印</el-button>
            <el-table v-loading="loading" :data="materialTable" border style="width:100%">
                <el-table-column prop="id" label="序号">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="materialCode" label="物料编号"></el-table-column>
                <el-table-column prop="materialName" label="物料名称"></el-table-column>
                <el-table-column prop="type" label="物料类型"></el-table-column>
                <el-table-column prop="originalMaterial" label="材料"></el-table-column>
                <el-table-column prop="materialBomParamValueStr" label="参数"></el-table-column>
                <el-table-column prop="drawingCode" label="图号" ></el-table-column>
                <el-table-column prop="arriveDate" label="交货日期"></el-table-column>
                <el-table-column prop="actualDeliveryQty" label="实际到货数"></el-table-column>
                <el-table-column prop="purchaseQty" label="采购数量"></el-table-column>
                <el-table-column prop="purchaseReason" label="采购原因"></el-table-column>
                <el-table-column prop="settlementQty" label="结算数量"></el-table-column>
                <el-table-column prop="purchasePriceTotal" label="金额小计"></el-table-column>
                <el-table-column prop="verificatPerson" label="核销人"></el-table-column>
                <el-table-column prop="verificatDate" label="核销时间"></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="detail(scope.$index,scope.row)">明细</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <hr class="marginTop" />
            <el-button @click="abort">取消</el-button>
        </div>
</div>
</template>
<script>
export default {
  data() {
      return {
          loading: false,
          orderForm: [],
          editVisible: false,
          search: {
              id: '',
          },
          materialTable:[],
          form: {
              supplierName: '',
              supplierContact: '',
              supplierNumber: '',
              supplierPhone: '',
              supplierFaxNumber: '',
              supplierEmail: '',
              repertoryId: ''
          },
          pruchaseForm: {
              purchaseCode: '',
              supplierId: '',
              pruchase: '',
              purchasePhoneNumber: '',
              purchasePhone: '',
              purchaseFaxNumber: '',
              purchaseEmail: '',
              ifTax: '',
              ifFreight: '',
              freight: '',
              remark: '',
              purchaseAgreement: '',
              subtotal: '',
              productPriceTotal: 0,
              ifPickUp: '',
              deliveryAddressId: '',
              repertoryName: '',
              deliveryAddressInfo: {}
          },
          rows: {}
      };
  },
  created() {
      this.getData();
  },
  computed: {},
  methods: {
      goPrint () {
          let query = {
              form: this.form,
              orderForm: this.pruchaseForm,
              id: this.search.id
          }
          query = JSON.stringify(query)
          this.$router.push({path: '/purchaseOrderPrint', query: {data: query, rows: this.rows }});
      },
      abort() {
          this.$router.push({path: "/purchaseOrderList", query: {closeFlag: 1}});
          },
      handle_select(row) {
          },
      // 获取 easy-mock 的模拟数据
      getData() {
          this.rows = JSON.parse(this.$route.query.row)
          let rows = this.rows
          if (rows != undefined) {
              this.search.id = rows.id;
              this.orderForm = rows
          }
          this.loading = true
          this.$http.post("/purchaseOrdersList/searchByPurchaseOrdersId",{id:this.search.id}).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.materialTable = res.data.data
              }
              this.loading = false
          }).catch(err => {
              this.loading = false
          });
          this.$http.post('purchaseOrders/searchById', {id:this.search.id}).then(res => {
              if (res.data.code === 1000) {
                  this.form.supplierName = res.data.data.supplierName
                  this.form.supplierContact = res.data.data.supplierContact
                  this.form.supplierNumber = res.data.data.supplierNumber
                  this.form.supplierPhone = res.data.data.supplierPhone
                  this.form.supplierFaxNumber = res.data.data.supplierFaxNumber
                  this.form.supplierEmail = res.data.data.supplierEmail

                  this.pruchaseForm.purchaseCode = res.data.data.purchaseCode
                  this.pruchaseForm.supplierId = res.data.data.supplierId
                  this.pruchaseForm.pruchase = res.data.data.purchasePerson
                  this.pruchaseForm.purchasePhoneNumber = res.data.data.purchasePhoneNumber
                  this.pruchaseForm.purchasePhone = res.data.data.purchasePhone
                  this.pruchaseForm.purchaseFaxNumber = res.data.data.purchaseFaxNumber
                  this.pruchaseForm.purchaseEmail = res.data.data.purchaseEmail
                  this.pruchaseForm.ifTax = res.data.data.ifTax == 1 ? '是' : '否'
                  this.pruchaseForm.ifFreight = res.data.data.ifFreight == 1 ? '是' : '否'
                  this.pruchaseForm.freight = res.data.data.freight
                  this.pruchaseForm.remark = res.data.data.remark
                  this.pruchaseForm.purchaseAgreement = res.data.data.purchaseAgreement
                  this.pruchaseForm.subtotal = res.data.data.purchasePriceTotal
                  this.pruchaseForm.productPriceTotal = res.data.data.productPriceTotal
                  this.pruchaseForm.ifPickUp = res.data.data.ifPickUp
                  this.pruchaseForm.deliveryAddressId = res.data.data.deliveryAddressId
                  this.pruchaseForm.repertoryName = res.data.data.repertoryName
                  this.pruchaseForm.deliveryAddressInfo = res.data.data.deliveryAddressInfo
                  this.pruchaseForm.purchasePerson = res.data.data.purchasePerson
                  this.pruchaseForm.purchaseDate = res.data.data.purchaseDate
              }
          })
      },
      detail(index,row) {
          this.$router.push({
              path: "/purchaseOrderListInfo",
              query: { id: row.id }
          });
      }
  },
  watch: {
      '$route' (to, from) {
          if (to.path == '/purchaseOrderInfo') {
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
.text {
    font-size: 12px;
    color: #606266;
    margin-right: 30px;
}
.supplier{
    padding-bottom: 10px;
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
