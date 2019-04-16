<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <span class="text">订单资料</span>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-form :inline="true" ref="customerForm" :model="orderForm" class="demo-form-inline" :rules="rules">
                    <el-form-item label="订单编号:">
                        <el-input disabled v-model="orderForm.orderCode"/>
                    </el-form-item>
                    <el-form-item label="合同编号:">
                        <el-input v-if="orderForm.id != ''" disabled v-model="orderForm.contractId"/>
                        <el-input v-else v-model="orderForm.contractId"/>
                    </el-form-item>
                    <el-form-item label="制单人:">
                        <el-input disabled v-model="orderForm.owner"/>
                    </el-form-item>
                    <el-form-item label="客户名称:" prop="customerId">
                        <el-select v-if="orderForm.id != ''" disabled  v-model="orderForm.customerId" @change="changeCustomer">
                            <el-option
                            v-for="item in customerList"
                            :key="'customer'+item.id"
                            :label="item.customerName"
                            :value="item.id">
                            </el-option>
                        </el-select>
                        <el-select v-else v-model="orderForm.customerId" @change="changeCustomer">
                            <el-option
                            v-for="item in customerList"
                            :key="'customer'+item.id"
                            :label="item.customerName"
                            :value="item.id"
                            disabled>
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="联系人:" prop="contacterName">
                        <el-select v-model="orderForm.contacterName" disabled>
                            <el-option
                            v-for="item in contacterList"
                            :key="'contacter' + item.id"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="送货地址:" prop="deliveryAddress">
                        <el-select v-model="orderForm.deliveryAddress" placeholder="请选择" disabled>
                            <el-option
                            v-for="item in addressList"
                            :key="'address' + item.id"
                            :label="item.address"
                            :value="item.address">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="交货日期:">
                        <el-date-picker
                        v-model="orderForm.deliveryDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期" disabled>
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="是否含税:">
                        <el-select v-model="orderForm.taxInclude" clearable placeholder="请选择" @change="changeTaxInclude" disabled>
                            <el-option
                            v-for="item in tax"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="产品数量:">
                        <el-input disabled v-model="orderForm.productNumber"/>
                    </el-form-item>
                    <el-form-item label="运费:">
                        <el-input v-on:input="recompute" v-model="orderForm.freightDisplay" disabled/>
                    </el-form-item>
                    <el-form-item label="订单总价:">
                        <el-input disabled v-model="orderForm.totalMoneyDisplay" disabled/>
                    </el-form-item>
                    <br/>
                    <el-form-item label="付款方式:">
                        <el-select class="minorSelect" v-model="orderForm.payType" clearable placeholder="请选择" disabled>
                            <el-option
                            v-for="item in payTypeArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="付款规则:">
                        <el-select class="minorSelect" v-model="orderForm.payRule" clearable placeholder="请选择" disabled>
                            <el-option
                            v-for="item in payRuleArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="发票类型:">
                        <el-select class="minorSelect" v-model="orderForm.invoice" clearable placeholder="请选择" @change="changeTaxRate" disabled>
                            <el-option
                            v-for="item in invoiceArray"
                            :key="item.name"
                            :label="item.name + '---' + item.taxRate"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="送货方式:">
                        <el-select class="minorSelect" v-model="orderForm.delivery" clearable placeholder="请选择" disabled>
                            <el-option
                            v-for="item in deliveryArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="附件:">
                        <el-input disabled v-model="orderForm.fileName" disabled/>
                        <el-button if="orderForm.fileName != ''"  @click="downloadFile">下载</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <hr class="marginTop"/>
            <span class="text">产品列表</span>
            <!--<el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
                <el-button size="small" @click="addProduct()">新增产品</el-button>
            </el-tooltip>-->
            <hr class="marginBottom"/>
            <div class="product_table">
                <!--<v-table-->
                    <!--is-horizontal-resize-->
                    <!--style="width:100%"-->
                    <!--:columns="columns"-->
                    <!--:table-data="tables"-->
                    <!--row-hover-color="#eee"-->
                    <!--row-click-color="#edf7ff"-->
                    <!--:cell-edit-done="cellEditDone"-->
                    <!--@on-custom-comp="customCompFunc"-->
                <!--/>-->
                <el-table :data="tables" style="width: 100%;" border stripe>
                    <el-table-column align="center" prop="sequenceNumber" label="编号"></el-table-column>
                    <el-table-column align="center" prop="productCode" label="产品编码"></el-table-column>
                    <el-table-column align="center" prop="productName" label="产品名称"></el-table-column>
                    <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编码"></el-table-column>
                    <el-table-column align="center" prop="productNumber" label="数量"></el-table-column>
                    <el-table-column align="center" prop="inventoryQty" label="库存数"></el-table-column>
                    <el-table-column align="center" prop="alreadySendQty" label="已发货数"></el-table-column>
                    <el-table-column align="center" prop="stayDeliveryQty" label="待发货数"></el-table-column>
                    <el-table-column align="center" prop="unit" label="单位"></el-table-column>
                    <el-table-column align="center" prop="source" label="来源"></el-table-column>
                    <el-table-column align="center" prop="deliveryDate" label="交货日期"></el-table-column>
                    <el-table-column align="center" prop="retailPriceDisplay" label="零售价"></el-table-column>
                    <el-table-column align="center" prop="taxRate" label="税率"></el-table-column>
                    <el-table-column align="center" prop="taxIncludeDisplay" label="是否含税"></el-table-column>
                    <el-table-column align="center" prop="untaxedPriceDisplay" label="未税价格"></el-table-column>
                    <el-table-column align="center" prop="taxesDisplay" label="税金"></el-table-column>
                    <el-table-column align="center" prop="total" label="总价"></el-table-column>
                    <el-table-column align="center" prop="materialStatusValue" label="状态"></el-table-column>
                </el-table>
            </div>
            <div style="text-align:center;">
                <!--<el-button size="big" @click="save('customerForm')">返回</el-button>-->
                <el-button @click="goBack">返回</el-button>
            </div>
            <hr class="marginTop"/>
            <span class="text">日志</span>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-table :data="logList" border stripe style="width: 100%">
                    <el-table-column width="100" prop="id" label="序号" sortable/>
                    <el-table-column width="150" prop="operator" label="操作人"/>
                    <el-table-column width="150" prop="date" label="操作时间"/>
                    <el-table-column width="100" prop="operation" label="动作"/>
                    <el-table-column prop="content" label="操作内容"/>
                </el-table>
            </div>
        </div>


        <el-dialog title="新增产品" :visible.sync="addProductVisible" v-if="addProductVisible" width="80%">
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="产品编号:">
                    <el-input v-model="search.materialCode"></el-input>
                </el-form-item>
                <el-form-item label="产品名称:">
                    <el-input v-model="search.materialName"></el-input>
                </el-form-item>
                <el-button round @click="searchLike">查询</el-button>
            </el-form>
            <el-table :data="productList" border style="width:100%" height="400">
                <el-table-column label="产品编号" prop="materialCode"></el-table-column>
                <el-table-column label="产品名称" prop="materialName"></el-table-column>
                <el-table-column label="单位" prop="materialUnit"></el-table-column>
                <el-table-column label="数量" prop="productNumber">
                    <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.productNumber" :min="0" v-on:input="sumNumPrice(scope.row)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="交货日期" prop="deliveryDate">
                    <template slot-scope="scope">
                        <el-date-picker type="date" clearable :editable="false" v-model="scope.row.deliveryDate"></el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column label="零售价" prop="retailPriceDisplay">
                    <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.retailPriceDisplay" :min="0" v-on:input="sumRetailPrice(scope.row)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="税率" prop="taxRate">
                    <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.taxRate" :min="0" disabled></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="是否含税" prop="taxInclude">
                    <template slot-scope="scope">
                        <el-select v-model="scope.row.taxInclude" placeholder="请选择" disabled>
                            <el-option
                                v-for="item in tax"
                                :key="item.value"
                                :label="item.name"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="未税金额" prop="untaxedPriceDisplay">
                    <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.untaxedPriceDisplay" :min="0" disabled></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="税金" prop="taxesDisplay">
                    <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.taxesDisplay" :min="0" disabled></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="insertProduct(scope.$index, scope.row)">新增</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addProductVisible = false">取 消</el-button>
            </span>
        </el-dialog>
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
          currentId: "",
          customerList: [],
          addressList: [],
          contacterList: [],
          productList: [],
          productListCenter:[],
          productListSearch:[],
          selectedProductList: [],
          logList: [],
          payTypeArray: [],
          payRuleArray: [],
          invoiceArray: [],
          deliveryArray: [],
          url: "/seller/order/index?id=",
          addProductVisible: false,
          materialCode:'',
          materialName:'',
          taxInclude:'',
          taxRate:'',
          search:{
              materialCode:'',
              materialName:'',
              flag:1,
              pageNum:1,
              pageSize:100000,
          },
          tax: [
              {
                  name: "是",
                  value: 1
              },
              {
                  name: "否",
                  value: 0
              }
          ],
          rules: {
              customerId: [
                  { required: true, message: "请选择客户名称", trigger: "change" }
                  ],
              contacterName: [
                  { required: true, message: "请选择联系人", trigger: "change" }
                  ],
              deliveryAddress: [
                  { required: true, message: "请选择送货地址", trigger: "change" }
                  ]
          },
          addressForm: {
              name: "",
              phone: "",
              address: ""
          },
          productForm: {
              productId: "",
              productCode:"",
              productName: "",
              deliveryDate: new Date(),
              productNumber: "",
              unit: "",
              taxRate: "",
              taxInclude: "",
              retailPriceDisplay: "",
              untaxedPriceDisplay: "",
              taxesDisplay: ""
          },
          orderForm: {
              id: "",
              contractId: "",
              customerId: "",
              customerName: "",
              owner: "",
              contacterName: "",
              deliveryAddress: "",
              deliveryDate:new Date(),
              taxInclude: "",
              productNumber: "",
              freightDisplay: "",
              totalMoneyDisplay: "",
              payType: "",
              payRule: "",
              invoice: "",
              delivery: ""
          },
          columns: [
              {
                  field: "index",
                  title: "序号",
                  width: 30,
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true
              },
              {
                  field: "productCode",
                  width: 30,
                  title: "产品编码",
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true
              },
              {
                  field: "productName",
                  width: 30,
                  title: "产品名称",
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true
              },
              {
                  field: "productNumber",
                  width: 30,
                  title: "数量",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "unit",
                  width: 30,
                  title: "单位",
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true
              },
              {
                  field: "deliveryDate",
                  width: 30,
                  title: "交货日期",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "retailPriceDisplay",
                  width: 30,
                  title: "零售价",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "taxRate",
                  width: 30,
                  title: "税率",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "taxIncludeDisplay",
                  width: 30,
                  title: "是否含税",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "untaxedPriceDisplay",
                  width: 30,
                  title: "未税价格",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "taxesDisplay",
                  width: 30,
                  title: "税金",
                  titleAlign: "center",
                  columnAlign: "center",
                  isEdit: false,
                  isResize: true
              },
              {
                  field: "total",
                  width: 30,
                  title: "总价",
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true
              },
              /*{
                  field: "custome-adv",
                  width: 30,
                  title: "操作",
                  titleAlign: "center",
                  columnAlign: "center",
                  isResize: true,
                  componentName: "table-operation"
              }*/
          ]
      };
  },
  created() {
      this.getData();
  },
  computed: {
      tables() {
          return this.selectedProductList.filter(d => {
              d.taxIncludeDisplay = d.taxInclude == 0 ? "否" : "是";
              let number = parseFloat(d.productNumber);
              let price = parseFloat(d.retailPriceDisplay);
              d.taxRate = d.taxRate == '' ? 0 : d.taxRate
              let rate = parseFloat(d.taxRate);
              d.total =
                  d.taxInclude == 0
                      ? (number * price * (100 + rate) / 100).toFixed(2)
                      : (number * price).toFixed(2);
              return d;
          });
      }
  },
  watch: {
      $route(to, from) {
          if (to.path == "/orderBomSearch") {
              this.getData();
          }
      }
  },
  methods: {
      searchLike(){
          this.search.pageNum = 1;
          this.selectLike();
      },
      selectLike(){
          this.$http.post("/materialInfo/searchMaterialList", this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.productList = res.data.data.list;
                  this.pages = res.data.data.total;
                  if (this.productList.length!=0){
                      for (var i=0;i<this.productList.length;i++){
                          if (this.productListCenter.length!=0){
                              for (var j=0;j<this.productListCenter.length;j++){
                                  if (this.productListCenter[j].id == this.productList[i].id){
                                      this.productList.splice(i,1)
                                  }
                              }
                          }
                          this.productList[i].taxRate = this.taxRate
                          this.productList[i].taxInclude = this.taxInclude
                      }
                  }
              }
          });
      },
      downloadFile() {
          this.$http.post('/seller/order/download', {filePath: this.orderForm.fileName}, {responseType: 'arraybuffer'}).then((res) => {
              fileDownload(res.data, utils.getFileName(this.orderForm.fileName));
          })
      },
      changeTaxRate(event){
          let obj = {};
          obj = this.invoiceArray.find((item)=>{
              return item.name === event;
          });
          this.taxRate = obj.taxRate
          if (this.productList.length!=0){
              for (var i=0;i<this.productList.length;i++){
                  this.productList[i].taxRate = obj.taxRate
              }
          }
      },
      changeTaxInclude(event){
          this.taxInclude = event
          if (this.productList.length!=0){
              for (var i=0;i<this.productList.length;i++){
                  this.productList[i].taxInclude = event
              }
          }
      },
      sumNumPrice(row){
          if (row.taxInclude ==1){
              if (row.retailPriceDisplay!=null){
                  var sum = row.productNumber * row.retailPriceDisplay * (row.taxRate/100)
                  for (var i=0;i<this.productList.length;i++){
                      if (row.id == this.productList[i].id) {
                          this.productList[i].taxesDisplay = sum
                          this.productList[i].untaxedPriceDisplay = row.productNumber * row.retailPriceDisplay - sum
                      }
                  }
              }
          } else {
              for (var i=0;i<this.productList.length;i++){
                  if (row.id == this.productList[i].id) {
                      this.productList[i].taxesDisplay = 0
                      this.productList[i].untaxedPriceDisplay = row.productNumber * row.retailPriceDisplay
                  }
              }
          }
      },
      sumRetailPrice(row){
          if (row.taxInclude ==1) {
              if (row.productNumber!=null){
                  var sum = row.productNumber * row.retailPriceDisplay * (row.taxRate/100)
                  for (var i=0;i<this.productList.length;i++){
                      if (row.id == this.productList[i].id) {
                          this.productList[i].taxesDisplay = sum
                          this.productList[i].untaxedPriceDisplay = row.productNumber * row.retailPriceDisplay - sum
                      }
                  }
              }
          }else {
              for (var i=0;i<this.productList.length;i++){
                  if (row.id == this.productList[i].id) {
                      this.productList[i].taxesDisplay = 0
                      this.productList[i].untaxedPriceDisplay = row.productNumber * row.retailPriceDisplay
                  }
              }
          }
      },
      recompute() {
          let number = 0;
          let total = 0;
          this.tables.forEach(item => {
              number += parseFloat(item.productNumber);
              total += parseFloat(item.total);
          });
          this.orderForm.productNumber = number;
          let fd = parseFloat(this.orderForm.freightDisplay);
          if (this.orderForm.freightDisplay == "") {
              fd = 0;
          }
          this.orderForm.totalMoneyDisplay = total + fd;
          },
      cellEditDone(newValue, oldValue, rowIndex, rowData, field) {
          this.selectedProductList[rowIndex][field] = newValue;
          this.recompute();
          },
      customCompFunc(params) {
          this.selectedProductList.splice(params.index, 1);
          this.recompute();
          },
      getData() {
          this.currentId = this.$route.query.id;
          if (this.currentId == undefined) {
              this.currentId = 0;
          }
          this.$http.get(this.url + this.currentId).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  if (res.data.data.orderInfo != null) {
                      this.orderForm = res.data.data.orderInfo;
                  } else {
                      this.orderForm = {
                          id: "",
                          contractId: "",
                          customerId: "",
                          customerName: "",
                          owner: "",
                          contacterName: "",
                          deliveryAddress: "",
                          deliveryDate: new Date(),
                          taxInclude: "",
                          productNumber: "",
                          freightDisplay: "",
                          totalMoneyDisplay: "",
                          payType: "",
                          payRule: "",
                          invoice: "",
                          delivery: ""
                      };
                  }
                  if (res.data.data.customerInfos != null) {
                      this.customerList = res.data.data.customerInfos;
                  } else {
                      this.customerList = [];
                  }
                  if (res.data.data.materialBomInfos != null) {
                      this.productList = res.data.data.materialBomInfos;
                  } else {
                      this.productList = [];
                  }
                  if (res.data.data.productInfos != null) {
                      this.selectedProductList = res.data.data.productInfos;
                      for (var i=0;i<this.selectedProductList.length;i++){
                          this.selectedProductList[i].index = i+1
                          if (this.selectedProductList[i].materialStatus==0) {
                              this.selectedProductList[i].materialStatusValue = 'BOM制作'
                          }else if (this.selectedProductList[i].materialStatus==1){
                              this.selectedProductList[i].materialStatusValue = 'BOM待下发'
                          }else {
                              this.selectedProductList[i].materialStatusValue = 'BOM已下发'
                          }
                      }
                  } else {
                      this.selectedProductList = [];
                  }
                  this.logList = res.data.data.logInfos;
                  this.payTypeArray = res.data.data.payTypeInfos;
                  this.payRuleArray = res.data.data.payRuleInfos;
                  this.invoiceArray = res.data.data.invoiceInfos;
                  this.deliveryArray = res.data.data.deliveryInfos;
              }
          });
          },
      changeCustomer(id) {
          let obj = {};
          obj = this.customerList.find(item => {
              return item.id === id;
          });
          this.taxInclude = obj.taxInclude
          this.$http.post("/sellerInvoice/searchByName", {name:obj.invoice}).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  if (res.data.data.length != 0){
                      this.taxRate = res.data.data[0].taxRate;
                      if (this.productList.length!=0){
                          for (var i=0;i<this.productList.length;i++){
                              this.productList[i].taxRate = this.taxRate
                          }
                      }
                  }
              }
          });
          this.orderForm.customerName = obj.customerName;
          this.orderForm.taxInclude = obj.taxInclude;
          if (this.productList.length!=0){
              for (var i=0;i<this.productList.length;i++){
                  this.productList[i].taxInclude = this.orderForm.taxInclude
              }
          }
          this.orderForm.delivery = obj.delivery;
          this.orderForm.invoice = obj.invoice;
          this.orderForm.payType = obj.payType;
          this.orderForm.payRule = obj.payRule;
          this.$http.get("/seller/customer/additional?id=" + id).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  if (res.data.data.addressInfos != null) {
                      this.addressList = res.data.data.addressInfos;
                  } else {
                      this.addressList = [];
                  }
                  if (res.data.data.contacterInfos != null) {
                      this.contacterList = res.data.data.contacterInfos;
                  } else {
                      this.contacterList = [];
                  }
              }
          });
          },
      addProduct() {
          this.addProductVisible = true;
          },
      save(formName) {
          this.$refs[formName].validate(valid => {
              if (valid) {
                  let form = {
                      orderInfo: this.orderForm,
                      productInfos: this.selectedProductList
                  };
                  let config = {
                      headers: {
                          "Content-Type": "application/json"
                      },
                      withCredentials: true
                  };
                  this.$axios.post(this.$path + "/seller/order/save", form, config).then(res => {
                      if (res.data.code == 1000) {
                          this.$message.success("保存成功");
                          //跳转到列表页面
                          this.$router.push({ path: '/sellerOrder'})
                          } else {
                          this.$message.error(res.data.message);
                          }
                          });
              } else {
                  this.$message.error("带*为必填项");
                  return false;
              }
          });
          },
      insertProduct(index,row) {
          this.productList.splice(index,1)
          this.productListCenter.push({id:row.id})
          this.productForm.productCode = row.materialCode
          this.productForm.productId = row.id
          this.productForm.productName = row.materialName
          this.productForm.deliveryDate = row.deliveryDate
          this.productForm.productNumber = row.productNumber
          this.productForm.unit = row.materialUnit
          this.productForm.taxRate = row.taxRate
          this.productForm.taxInclude = row.taxInclude
          this.productForm.retailPriceDisplay = row.retailPriceDisplay
          this.productForm.untaxedPriceDisplay= row.untaxedPriceDisplay
          this.productForm.taxesDisplay = row.taxesDisplay
          let data = JSON.parse(JSON.stringify(this.productForm));
          this.selectedProductList.push(data);
          this.addProductVisible = false;
          this.recompute();
          },
      goBack() {
          this.$router.push({path: "/orderBom", query: {closeFlag: 1}});
      },
  }
};
// 自定义列组件
/*Vue.component("table-operation", {
  props: {
    rowData: {
      type: Object
    },
    field: {
      type: String
    },
    index: {
      type: Number
    }
  },
  methods: {
    deleteRow() {
      let params = { type: "delete", index: this.index, rowData: this.rowData };
      this.$emit("on-custom-comp", params);
    }
  }
});*/
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
    