<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="询价编号:">
          <el-input v-model="search.queryCode" disabled></el-input>
        </el-form-item>

        <el-form-item label="客户名称:">
          <el-input v-model="search.customerName" disabled></el-input>
        </el-form-item>

        <el-form-item label="询价日期:">
          <el-date-picker v-model="search.queryDate" align="right" type="date" placeholder="选择日期" disabled>
          </el-date-picker>
        </el-form-item>

        <el-form-item label="报价截止日期:">
          <div class="block">
            <el-date-picker v-model="search.offerDeadline" align="right" type="date" placeholder="选择日期" disabled>
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="要求交货日期:">
          <div class="block">
            <el-date-picker v-model="search.requireDeliveryDate" align="right" type="date" placeholder="选择日期" disabled>
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="计划交货日期:">
          <div class="block">
            <el-date-picker v-model="search.plannedDeliveryDate" align="right" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="是否含税:">
          <el-select v-model="search.taxInclude" disabled>
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品总量:">
          <el-input v-model="search.productSum" disabled></el-input>
        </el-form-item>

        <el-form-item label="其他费用:">
          <el-input v-model="search.otherCost" v-on:input="calc()"></el-input>
        </el-form-item>

        <el-form-item label="合同编号:">
          <el-input v-model="search.contractNumber"></el-input>
        </el-form-item>
        <el-form-item label="付款方式:">
          <el-select v-model="search.paymentType">
            <el-option v-for="item in payTypeArray" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="付款规则:">
          <el-select v-model="search.paymentRule">
            <el-option v-for="item in payRuleArray" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发票类型:">
          <el-select v-model="search.invoiceType">
            <el-option v-for="item in invoiceArray" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单总价:">
          <el-input v-model="search.sumCost" disabled></el-input>
        </el-form-item>
        <el-form-item label="其他要求:">
          <el-input v-model="search.otherRequire" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="search.remark"></el-input>
        </el-form-item>

      </el-form>
    </div>
    <div class="handle-box">
      <span class="el-form-item__label">收货地址:</span>{{this.search.detailAddress}}
      <span class="el-form-item__label" v-if="this.search.detailAddress===null">自提</span>
      <br/>
    </div>
    <span class="el-form-item__label">产品清单</span>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="序号" sortable>
      </el-table-column>
      <el-table-column prop="productType" label="产品类型">
      </el-table-column>
      <el-table-column prop="materialName" label="产品名称">
      </el-table-column>
      <el-table-column prop="materialUnit" label="单位">
      </el-table-column>
      <el-table-column prop="purchaseNumber" label="采购数量">
      </el-table-column>
      <el-table-column prop="productStandard" label="产品标准">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="交货日期">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.deliveryDate" align="right" type="date" placeholder="选择日期">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="plannedDeliveryDate" label="预计交货日期">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.plannedDeliveryDate" align="right" type="date" placeholder="选择日期">
          </el-date-picker>
        </template>
      </el-table-column>

      <el-table-column prop="price" label="单价">
      </el-table-column>
      <el-table-column prop="taxRate" label="税率">
      </el-table-column>
      <el-table-column prop="sumPrice" label="总价">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- <el-button size="small" @click="detail(scope.row)">明细</el-button> -->
          <el-button size="small" @click="offerPrice(scope.$index,scope.row)" v-if="">报价</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="报价" :visible.sync="offerPriceVisible" width="60%">
      <el-form ref="offerPriceForm" :model="offerPriceForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="产品类型:">
              <el-input v-model="offerPriceForm.productType" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称:">
              <el-input v-model="offerPriceForm.materialName" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="参数">
          <el-input v-model="offerPriceForm.materialBomParamNameStr " disabled></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单位">
              <el-input v-model="offerPriceForm.materialUnit" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购数量:">
              <el-input v-model="offerPriceForm.purchaseNumber" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">

          <el-col :span="12">
            <el-form-item label="要求交货日期:">
              <div class="block">
                <el-date-picker v-model="search.deliveryDate" align="right" type="date" disabled>
                </el-date-picker>
              </div>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="产品标准:">
              <el-input v-model="offerPriceForm.productStandard" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-form-item label="产品说明">
            <el-input v-model="offerPriceForm.description"></el-input>
          </el-form-item>
        </el-row>
        <br/>
        <hr/>
        <el-row :gutter="20">
          <el-col :span="12">

            <el-form-item label="单价：">
              <el-input v-model="offerPriceForm.price" v-on:input="recompute()"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税率：">
              <el-input v-model="offerPriceForm.taxRate" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">

            <el-form-item label="总价：">
              <el-input v-model="offerPriceForm.sumPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税金：">
              <el-input v-model="offerPriceForm.taxMoney" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="计划交货日期:">
          <div class="block">
            <el-date-picker v-model="search.deliveryDate" align="right" type="date" disabled>
            </el-date-picker>
          </div>

        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="报价结果：">
              <el-input v-model="offerPriceForm.description"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注：">
              <el-input v-model="offerPriceForm.description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="savePrice">保存</el-button>
        <el-button @click="offerPriceVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-button type="primary">转为合同评审</el-button>
    <el-button type="primary" @click="offer()">完成报价</el-button>
    <el-button @click="save()">保存</el-button>
    <el-button @click="goBack()">返回</el-button>
    <el-button type="danger" @click="reject()">拒绝报价</el-button>

    <div class="pagination">
      <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/offerPrice/getOfferInfo?id=",
      pages: 1,
      offerPriceVisible: false,
      offerPriceForm: {
        materialName: "",
        sumPrice: "",
        taxMoney: "",
        price: "",
        productType: "",
        deliveryDate: "",
        materialUnit: "",
        purchaseNumber: "",
        productStandard: "",
        taxRate: "",
        materialBomParamNameStr: "",
        description: ""
      },
      options: [
        {
          label: "是",
          value: 1
        },
        {
          label: "否",
          value: 0
        }
      ],
      payTypeArray: [],
      payRuleArray: [],
      invoiceArray: [],
      search: {
        customerName: "",
        offerPriceFlag: "",
        requireDeliveryDate: "",
        taxInclude: "",
        taxNumber: "",
        otherRequire: "",
        paymentType: "",
        paymentRule: "",
        invoiceType: "",
        queryCode: "",
        queryDate: "",
        offerDeadline: "",
        remark: "",
        detailAddress: null,
        otherCost: "",
        productList: [],
        plannedDeliveryDate: ""
      },
      queryId: "",
      tableIndex: "",
      searchProduct: {
        flag: 1,
        status: 1,
        pageNum: 1,
        pageSize: 20
      }
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    handleChange(value) {},
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    recompute() {
      this.offerPriceForm.sumPrice =
        this.offerPriceForm.purchaseNumber * this.offerPriceForm.price;
      this.offerPriceForm.taxMoney =
        this.offerPriceForm.sumPrice * this.offerPriceForm.taxRate / 100;

      // *this.offerPriceForm.taxRate
    },
    savePrice() {
      this.tableData[this.tableIndex].sumPrice = this.offerPriceForm.sumPrice;
      this.tableData[this.tableIndex].price = this.offerPriceForm.price;
      this.offerPriceVisible = false;
      this.offerPriceForm = {};
      this.calc();
    },
    calc() {
      let number = 0;
      this.tableData.forEach(item => {
        number = parseFloat(item.sumPrice) + number;
      });
      this.search.sumCost = number + parseFloat(this.search.otherCost);
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      this.getSelectedPayType();
      this.getSelectedPayRule();
      this.getSelectedinvoice();
      this.currentId = this.$route.query.id;
      this.queryId = this.$route.query.queryId;
      this.$http.post(this.url + this.currentId).then(res => {
        if (res != undefined && res.data.code == 1000) {
          if (res.data.data != null) {
            // console.log("报价明细2222", res.data.data);
            this.search = res.data.data;
            //接收物料清单
            this.searchProduct.queryId = this.queryId;
            // console.log(
            //   "this.searchProduct.queryId",
            //   this.searchProduct.queryId
            // );
            this.$http
              .post("/productList/list", this.searchProduct)
              .then(res => {
                if (res != undefined && res.data.code == 1000) {
                  // console.log("产品清单", res.data.data);
                  this.tableData = res.data.data.list;
                  this.search.productList = this.tableData;
                }
              });
          }
        }
      });
      this.$http
        .post("/offerPrice/getDefaultSelect", { currentId: this.currentId })
        .then(res => {
          if (res.data.code == 1000) {
            // console.log("getDefaultSelect--------", res.data.data);
            // this.tableData = res.data.data.list;
            this.search.invoiceType = res.data.data.invoiceType;
            this.search.paymentRule = res.data.data.paymentRule;
            this.search.paymentType = res.data.data.paymentType;
          }
        });
    },
    addaddress() {},

    offerPrice(index, row) {
      this.offerPriceVisible = true;
      this.tableIndex = index;
      // console.log("index", index);
      this.offerPriceForm.materialName = row.materialName;
      this.offerPriceForm.productStandard = row.productStandard;
      this.offerPriceForm.productType = row.productType;
      this.offerPriceForm.materialUnit = row.materialUnit;
      this.offerPriceForm.deliveryDate = row.deliveryDate;
      this.offerPriceForm.purchaseNumber = row.purchaseNumber;
      this.offerPriceForm.materialBomParamNameStr = row.materialBomParamNameStr;
      this.offerPriceForm.taxRate = row.taxRate;
    },
    getSelectedPayType() {
      let url = "/sellerPayType/all";
      this.$http.get(url).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.payTypeArray = res.data.data;
          // console.log("res.data.data", res.data.data);
        }
      });
    },
    getSelectedPayRule() {
      let url = "/sellerPayRule/all";
      this.$http.get(url).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.payRuleArray = res.data.data;
        }
      });
    },
    getSelectedinvoice() {
      let url = "/sellerInvoice/all";
      this.$http.get(url).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.invoiceArray = res.data.data;
        }
      });
    },
    reject() {
      this.search.offerPriceFlag = 0;
      this.$http.post("/offerPrice/saveOrUpdate", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("已拒绝报价");
        }
      });
      this.goBack();
    },
    offer() {
      this.search.offerPriceFlag = 2;
      this.$http.post("/offerPrice/saveOrUpdate", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("报价成功");
        }
      });
      this.goBack();
    },
    goBack() {
      this.$router.push({
        path: "/listoffer"
      });
    },
    edit(row) {},
    save() {
      this.search.productList = this.tableData;
      console.log("this.search", this.search);

      this.$http.post("/offerPrice/saveOrUpdate", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("保存成功");
        }
      });
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
    $route(to, from) {
      if (to.path == "/editoffer") {
        //这里路径到时候再改
        this.getData();
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
