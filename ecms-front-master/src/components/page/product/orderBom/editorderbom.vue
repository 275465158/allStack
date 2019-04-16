<template>
  <div class="table">
      <div class="container" v-loading="loading">
          <hr class="marginTop" />
          <span class="text">订单资料</span>
          <hr class="marginBottom" />
          <div class="handle-box">
              <el-form :inline="true" ref="customerForm" :model="orderForm" class="demo-form-inline" :rules="rules" label-width="100px">
                  <el-form-item label="订单编号:">
                      <el-input disabled v-model="orderForm.orderCode" />
                  </el-form-item>
                  <el-form-item label="合同编号:">
                      <el-input v-if="orderForm.id != ''" disabled v-model="orderForm.contractId" />
                      <el-input v-else v-model="orderForm.contractId" />
                  </el-form-item>
                  <el-form-item label="制单人:">
                      <el-input disabled v-model="orderForm.owner" />
                  </el-form-item>
                  <el-form-item label="客户名称:" prop="customerId">
                      <el-select v-if="orderForm.id != ''" disabled v-model="orderForm.customerId" @change="changeCustomer">
                          <el-option v-for="item in customerList" :key="'customer'+item.id" :label="item.customerName" :value="item.id">
                          </el-option>
                      </el-select>
                      <el-select v-else v-model="orderForm.customerId" @change="changeCustomer">
                          <el-option v-for="item in customerList" :key="'customer'+item.id" :label="item.customerName" :value="item.id">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="联系人:" prop="contacterName">
                      <el-select v-model="orderForm.contacterName" disabled>
                          <el-option v-for="item in contacterList" :key="'contacter' + item.id" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="送货地址:" prop="deliveryAddress">
                      <el-select v-model="orderForm.deliveryAddress" disabled placeholder="请选择">
                          <el-option v-for="item in addressList" :key="'address' + item.id" :label="item.address" :value="item.address">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="交货日期:">
                      <el-date-picker v-model="orderForm.deliveryDate" disabled type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期" :picker-options="pickerOptions">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item label="是否含税:">
                      <el-select v-model="orderForm.taxInclude" disabled clearable placeholder="请选择" @change="changeTaxInclude">
                          <el-option v-for="item in tax" :key="item.value" :label="item.name" :value="item.value">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="产品数量:">
                      <el-input disabled v-model="orderForm.productNumber" />
                  </el-form-item>
                  <el-form-item label="运费:">
                      <el-input type="number" disabled v-on:input="recompute" v-model.number="orderForm.freightDisplay" />
                  </el-form-item>
                  <el-form-item label="订单总价:">
                      <el-input disabled v-model="orderForm.totalMoneyDisplay" />
                  </el-form-item>
                  <br/>
                  <el-form-item label="付款方式:" prop="payType">
                      <el-select class="minorSelect" v-model="orderForm.payType" disabled clearable placeholder="请选择">
                          <el-option v-for="item in payTypeArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="付款规则:" prop="payRule">
                      <el-select class="minorSelect" v-model="orderForm.payRule" disabled clearable placeholder="请选择">
                          <el-option v-for="item in payRuleArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="发票类型:" prop="invoice">
                      <el-select class="minorSelect" v-model="orderForm.invoice" disabled clearable placeholder="请选择" @change="changeTaxRate">
                          <el-option v-for="item in invoiceArray" :key="item.name" :label="item.name + '---' + item.taxRate" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="送货方式:" prop="delivery">
                      <el-select class="minorSelect" v-model="orderForm.delivery" disabled clearable placeholder="请选择">
                          <el-option v-for="item in deliveryArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <!--<el-form-item label="附件:">-->
                      <!--<el-upload class="upload-demo" :action="this.$path + '/seller/order/uploadSimple'" :on-success="fileUpload"-->
                         <!--:file-list="fileList"-->
                         <!--:with-credentials="true">-->
                      <!--</el-upload>-->
                  <!--</el-form-item>-->
                  <el-form-item label="附件:">
                      <el-input disabled v-model="orderForm.fileName" disabled/>
                      <el-button if="orderForm.fileName != ''"  @click="downloadFile">下载</el-button>
                  </el-form-item>
              </el-form>
          </div>

          <hr class="marginTop" />
          <span class="text">产品列表</span>
          <el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
          </el-tooltip>
          <hr class="marginBottom" />
          <div class="product_table" v-loading="productLoading">
              <el-table :data="tables" style="width: 100%;" border stripe @selection-change="handleSelect1">
                  <el-table-column align="center" prop="sequenceNumber" label="编号"></el-table-column>
                  <el-table-column align="center" prop="productCode" label="产品编码"></el-table-column>
                  <el-table-column align="center" prop="productName" label="产品名称"></el-table-column>
                  <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编码"></el-table-column>
                  <el-table-column align="center" prop="type" label="产品类型"></el-table-column>
                  <el-table-column align="center" prop="originalMaterial" label="材料"></el-table-column>
                  <el-table-column align="center" prop="materialBomParamValueStr" label="参数"></el-table-column>
                  <el-table-column align="center" prop="unit" label="单位"></el-table-column>
                  <el-table-column align="center" prop="productNumber" label="数量"></el-table-column>
                  <el-table-column align="center" prop="deliveryDate" label="交货日期"></el-table-column>
                  <el-table-column align="center" prop="retailPriceDisplay" label="零售价"></el-table-column>
                  <el-table-column align="center" prop="total" label="总价"></el-table-column>
                  <el-table-column align="center" prop="materialStatusValue" label="状态"></el-table-column>
                  <el-table-column align="center" type="selection" width="80"></el-table-column>
                  <el-table-column align="center" label="操作">
                      <template slot-scope="scope">
                          <el-button @click="addProduct(scope.row.sequenceNumber)" type="text">选择产品</el-button>
                      </template>
                  </el-table-column>
              </el-table>
              <div style="text-align:center;">
                  <el-button size="big" @click="confirm">确认</el-button>
                  <el-button size="big" @click="goBack">返回</el-button>
              </div>
          </div>
          <hr class="marginTop" />
          <span class="text">日志</span>
          <hr class="marginBottom" />
          <div class="handle-box">
              <el-table :data="logList" border stripe style="width: 100%">
                  <el-table-column width="100" prop="id" label="序号" sortable>
                      <template slot-scope="scope">
                          {{scope.$index+1}}
                      </template>
                  </el-table-column>
                  <el-table-column width="150" prop="operator" label="操作人" />
                  <el-table-column width="150" prop="date" label="操作时间" />
                  <el-table-column width="100" prop="operation" label="动作" />
                  <el-table-column prop="content" label="操作内容" />
              </el-table>
            </div>
      </div>
          <el-dialog title="选择产品" :visible.sync="selectedProductTypeVisible">
              <el-button round @click="addProduct">选择已有产品</el-button>
              <el-button round @click="addNewProduct">选择新增产品</el-button>
          </el-dialog>
          <el-dialog title="新增产品" :visible.sync="addNewProductVisible">
              <el-form ref="newProductForm" :rules="rules" :model="newProductForm" label-width="100px">
                  <el-form-item label="项号"  prop="sequenceNumber">
                      <el-input style="width: 196px;" v-model="newProductForm.sequenceNumber"></el-input>
                  </el-form-item>
                  <el-form-item label="数量" prop="productNumber">
                      <el-input style="width: 196px;" v-model.number="newProductForm.productNumber"></el-input>
                  </el-form-item>
                  <el-form-item label="单价" prop="retailPriceDisplay">
                      <el-input style="width: 196px;" v-model="newProductForm.retailPriceDisplay"></el-input>
                  </el-form-item>
                  <el-form-item label="交货日期:">
                      <el-date-picker v-model="newProductForm.deliveryDate" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期" :picker-options="pickerOptions">
                      </el-date-picker>
                  </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                  <el-button @click="addNewProductVisible = false">取 消</el-button>
                  <el-button type="primary" @click="addnewproduct">确 定</el-button>
              </span>
          </el-dialog>
          <el-dialog title="新增产品" @close="closeing" :visible.sync="addProductVisible" width="80%">
              <el-form :inline="true" :model="search" class="demo-form-inline">
                  <el-form-item label="产品编号:">
                      <el-input v-model="search.materialCode"></el-input>
                  </el-form-item>
                  <el-form-item label="产品名称:">
                      <el-input v-model="search.materialName"></el-input>
                  </el-form-item>
                  <el-form-item label="工厂内部编码:">
                      <el-input v-model="search.factoryMaterialCode"></el-input>
                  </el-form-item>
                  <el-button round @click="searchOne">查询</el-button>
              </el-form>
              <el-table :data="productList" border style="width:100%" height="400" @selection-change="handleSelect">
                  <el-table-column label="产品编号" prop="materialCode"></el-table-column>
                  <el-table-column label="工厂内部编码" prop="factoryMaterialCode"></el-table-column>
                  <el-table-column label="产品类型" prop="type"></el-table-column>
                  <el-table-column label="产品名称" prop="materialName"></el-table-column>
                  <el-table-column label="材料" prop="originalMaterial"></el-table-column>
                  <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                  <el-table-column label="单位" prop="materialUnit"></el-table-column>
                  <el-table-column type="selection" label="操作"></el-table-column>
              </el-table>
              <div class="pagination">
                  <el-pagination :page-size="5" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                  </el-pagination>
              </div>
              <span slot="footer" class="dialog-footer">
                  <el-button @click="addproduct">确 定</el-button>
                  <el-button @click="addProductVisible = false">取 消</el-button>
              </span>
      </el-dialog>
  </div>
</template>

<!--aaaaaa-->
<script>
import "vue-easytable/libs/themes-base/index.css";
import Vue from "vue";
export default {
  data() {
      return {
          productLoading: false,
          loading: false,
          currentId: "",
          orderId:'',
          customerList: [],
          addressList: [],
          contacterList: [],
          productList: [],
          productListCenter: [],
          productListSearch: [],
          selectedProductList: [],
          fileList: [],
          logList: [],
          payTypeArray: [],
          payRuleArray: [],
          invoiceArray: [],
          deliveryArray: [],
          url: "/seller/order/index?id=",
          addProductVisible: false,
          selectedProductTypeVisible:false,
          addNewProductVisible:false,
          sellerOrderProductList:[],
          newProductForm:{
              sequenceNumber:'',
              productNumber:'',
              retailPriceDisplay:'',
              deliveryDate:'',
          },
          materialCode: "",
          materialName: "",
          taxInclude: "",
          taxRate: "",
          file: "",
          pages: 1,
          search: {
              auditingStatus:0,
              materialCode: "",
              materialName: "",
              factoryMaterialCode:'',
              pageSize:5,
              pageNum: 1
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
              ],
              taxInclude: [
                  { required: true, message: "请选择是否含税", trigger: "change" }
              ],
              payType: [
                  { required: true, message: "请选择付款方式", trigger: "change" }
              ],
              payRule: [
                  { required: true, message: "请选择付款规则", trigger: "change" }
              ],
              invoice: [
                  { required: true, message: "请选择发票类型", trigger: "change" }
              ],
              delivery: [
                  { required: true, message: "请选择送货方式", trigger: "change" }
              ]
          },
          addressForm: {
              name: "",
              phone: "",
              address: ""
          },
          productForm: {
              productId: "",
              productCode: "",
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
              deliveryDate: new Date(),
              taxInclude: "",
              productNumber: "",
              freightDisplay: "",
              totalMoneyDisplay: "",
              payType: "",
              payRule: "",
              invoice: "",
              delivery: "",
              fileName: ""
          },
          selectedproductList: [],
          backProductList: {
              productId: "",
              productCode: "",
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
          pickerOptions: {
              disabledDate(time) {
                  return time.getTime() < Date.now() - 8.64e7
              }
          }
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
          if (to.path == "/sellerOrderInfo" && this.$route.query.works !== 1) {
              this.$refs['customerForm'].resetFields()
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
                  delivery: "",
                  fileName: ""
              };
              this.getData();
          }
      }
  },
  methods: {
      deleteFn (row) {
          let _this = this
          this.selectedProductList.forEach((item, index) => {
               if (row.productId === item.productId) {
                  _this.selectedProductList.splice(index, 1)
               }
          })
          this.recompute()
      },
      isObjEqual (o1, o2) {
          var props1 = Object.getOwnPropertyNames(o1);
          var props2 = Object.getOwnPropertyNames(o2);
          if (props1.length != props2.length) {
              return false;
          }
          for (var i = 0,max = props1.length; i < max; i++) {
              var propName = props1[i];
              if (o1[propName] !== o2[propName]) {
                  return false;
              }
          }
          return true;
      },
      closeing () {
          this.search.materialCode = ''
          this.search.materialName = ''
          this.search.factoryMaterialCode = ''
      },
      // 分页导航
      handleCurrentChange(val) {
          this.search.pageNum = val;
          this.selectLike();
      },
      searchOne(){
          this.search.pageNum = 1;
          this.selectLike();
      },
      fileUpload(response, file, fileList){
          if (response.code == 1000) {
              this.orderForm.fileName = response.data;
          }
      },
      handleSelect(vals) {
          this.selectedproductList = vals;
      },
      handleSelect1(vals) {
          this.sellerOrderProductList = vals;
      },
      addnewproduct(){
          if (this.selectedProductList.length!=0){
              for (let i=0;i<this.selectedProductList.length;i++){
                  if (Number(this.newProductForm.sequenceNumber) == Number(this.selectedProductList[i].sequenceNumber)){
                      this.$message.warning("编号不能重复")
                      return
                  }
              }
          }
          this.productForm = {};
          this.productForm.sequenceNumber = this.newProductForm.sequenceNumber
          this.productForm.productNumber = this.newProductForm.productNumber
          this.productForm.retailPriceDisplay = this.newProductForm.retailPriceDisplay
          this.productForm.deliveryDate = this.newProductForm.deliveryDate
          this.selectedProductList.push(this.productForm);
          this.addNewProductVisible = false
          this.recompute();
      },
      addproduct() {
          if (this.selectedproductList.length === 0) {
              this.$message.warning('请勾选产品')
              return
          }
          if (this.selectedproductList.length>1){
              this.$message.warning('请勾选一个产品')
              return
          }
          for (let i=0;i<this.selectedProductList.length;i++){
              if (this.sequenceNumber == this.selectedProductList[i].sequenceNumber) {
                  this.selectedProductList[i].productCode = this.selectedproductList[0].materialCode;
                  this.selectedProductList[i].productId = this.selectedproductList[0].id;
                  this.selectedProductList[i].productName = this.selectedproductList[0].materialName;
                  this.selectedProductList[i].unit = this.selectedproductList[0].materialUnit;
                  this.selectedProductList[i].factoryMaterialCode = this.selectedproductList[0].factoryMaterialCode;
                  this.selectedProductList[i].type = this.selectedproductList[0].type;
                  this.selectedProductList[i].originalMaterial = this.selectedproductList[0].originalMaterial;
                  this.selectedProductList[i].materialBomParamValueStr = this.selectedproductList[0].materialBomParamValueStr;
                  this.addProductVisible = false;
                  this.$http.post("sellerOrderProduct/editNewProduct",{orderId:this.orderId,materialId:this.selectedproductList[0].id,sequenceNumber:this.sequenceNumber}).then(res =>{
                      if (res != undefined && res.data.code == 1000) {
                          this.$message.success("选择成功");
                          this.getData()
                      }
                  })
              }
          }
      },
      selectLike() {
          this.$http.post("/materialInfo/searchMaterialList", this.search).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  this.productList = res.data.data.list;
                  this.pages = res.data.data.total;
                  if (this.productList.length != 0) {
                      for (var i = 0; i < this.productList.length; i++) {
                          if (this.productListCenter.length != 0) {
                              for (var j = 0; j < this.productListCenter.length; j++) {
                                  if (
                                      this.productListCenter[j].id == this.productList[i].id
                                  ) {
                                      this.productList.splice(i, 1);
                                  }
                              }
                          }
                      }
                  }
              }
          });
      },
      changeTaxRate(event) {
          let obj = {};
          obj = this.invoiceArray.find(item => {
              return item.name === event;
          });
          this.taxRate = obj.taxRate;
          if (this.productList.length != 0) {
              for (var i = 0; i < this.productList.length; i++) {
                  this.productList[i].taxRate = obj.taxRate;
              }
          }
          if (this.selectedProductList.length != 0) {
              for (var i = 0; i < this.selectedProductList.length; i++) {
                  this.selectedProductList[i].taxRate = this.taxRate;
                  this.selectedProductList[i].taxesDisplay = this.selectedProductList[i].productNumber * parseFloat(this.selectedProductList[i].retailPriceDisplay) * this.taxRate / 100;
                  this.selectedProductList[i].untaxedPriceDisplay = this.selectedProductList[i].total - this.selectedProductList[i].taxesDisplay;
                }
          }
      },
      changeTaxInclude(event) {
          this.taxInclude = event;
          if (this.productList.length != 0) {
              for (var i = 0; i < this.productList.length; i++) {
                  this.productList[i].taxInclude = event;
              }
          }
      },
      sumNumPrice(row) {
          if (row.taxInclude == 1) {
              if (row.retailPriceDisplay != null) {
                  var sum = (row.productNumber * row.retailPriceDisplay * (row.taxRate / 100)).toFixed(2);
                  for (var i = 0; i < this.productList.length; i++) {
                      if (row.id == this.productList[i].id) {
                          this.productList[i].taxesDisplay = sum;
                      }
                  }
              }
          } else {
              for (var i = 0; i < this.productList.length; i++) {
                  if (row.id == this.productList[i].id) {
                      this.productList[i].taxesDisplay = 0;
                  }
              }
          }
      },
      sumNumber (row) {
          if (!row.productNumber) {
              row.productNumber = 0
          }
          this.recompute()
      },
      sumPrice (row) {
          if (!row.retailPriceDisplay) {
              row.retailPriceDisplay = 0
          }
          this.recompute()
      },
      numberValue (row,index) {
          let sequenceNumber = row.sequenceNumber
          let indexSequenceNumber;
          if(this.tables.length>1){
              for(let i=0;i<this.tables.length;i++){
                  if(i != index){
                      indexSequenceNumber = this.tables[i].sequenceNumber;
                      if(sequenceNumber == indexSequenceNumber){
                          this.$message.error("编号不能重复，请重新输入");
                      }
                  }
              }
          }
      },
      sumRetailPrice(row) {
          if (row.taxInclude == 1) {
              if (row.productNumber != null) {
                  var sum = (row.productNumber * row.retailPriceDisplay * (row.taxRate / 100)).toFixed(2);
                  for (var i = 0; i < this.productList.length; i++) {
                      if (row.id == this.productList[i].id) {
                          this.productList[i].taxesDisplay = sum;
                          this.productList[i].untaxedPriceDisplay = (row.productNumber * row.retailPriceDisplay - sum).toFixed(2);
                      }
                  }
              }
          } else {
              for (var i = 0; i < this.productList.length; i++) {
                  if (row.id == this.productList[i].id) {
                      this.productList[i].taxesDisplay = 0;
                      this.productList[i].untaxedPriceDisplay = (row.productNumber * row.retailPriceDisplay).toFixed(2);
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
          if (!fd) {
              fd = 0;
          }
          this.orderForm.totalMoneyDisplay = total + fd;
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
          this.loading = true
          this.$http.get(this.url + this.currentId).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  if (res.data.data.orderInfo != null) {
                      this.orderForm = res.data.data.orderInfo;
                      this.orderId = this.orderForm.id;
                  }
                  if (res.data.data.customerInfos != null) {
                      this.customerList = res.data.data.customerInfos;
                  } else {
                      this.customerList = [];
                  }
                  if (res.data.data.productInfos != null) {
                      this.selectedProductList = res.data.data.productInfos;
                      for (var i = 0; i < this.selectedProductList.length; i++) {
                          this.selectedProductList[i].index = i + 1;
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
                  if (this.invoiceArray.length != 0) {
                      for (var i = 0; i < this.invoiceArray.length; i++) {
                          if (res.data.data.orderInfo != null) {
                              if (
                                  this.invoiceArray[i].name == res.data.data.orderInfo.invoice
                              ) {
                                  this.taxRate = this.invoiceArray[i].taxRate;
                              }
                              this.taxInclude = res.data.data.orderInfo.taxInclude;
                              this.deliveryDate = res.data.data.orderInfo.deliveryDate;
                          }
                      }
                  }
                  this.loading = false
              }
          })
              .catch(err => {
                  this.loading = false
              });
      },
      changeCustomer(id) {
          this.orderForm.contacterName = ""
          let obj = {};
          obj = this.customerList.find(item => {
              return item.id === id;
          });
          this.taxInclude = obj.taxInclude;
          this.$http.post("/sellerInvoice/searchByName", { name: obj.invoice }).then(res => {
              if (res != undefined && res.data.code == 1000) {
                  if (res.data.data.length != 0) {
                      this.taxRate = res.data.data[0].taxRate;
                      if (this.productList.length != 0) {
                          for (var i = 0; i < this.productList.length; i++) {
                              this.productList[i].taxRate = this.taxRate;
                          }
                      }
                  }
              }
            });
          this.orderForm.customerName = obj.customerName;
          this.orderForm.taxInclude = obj.taxInclude;
          if (this.productList.length != 0) {
              for (var i = 0; i < this.productList.length; i++) {
                  this.productList[i].taxInclude = this.orderForm.taxInclude;
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
      selectProductType(){
          this.selectedProductTypeVisible = true
      },
      addNewProduct(){
          this.newProductForm.sequenceNumber = "",
          this.newProductForm.productNumber = "",
          this.newProductForm.retailPriceDisplay = "",
          this.newProductForm.deliveryDate = "",
          this.addNewProductVisible = true
          this.selectedProductTypeVisible = false
      },

      addProduct(row) {
          this.selectedProductTypeVisible = false
          this.selectedproductList = [];
          this.addProductVisible = true;
          this.productList = [];
          this.search.pageNum = 1;
          this.search.materialCode = "",
          this.search.materialName = "",
          this.sequenceNumber = row
          this.selectLike();
      },
      confirm(){
          if (this.sellerOrderProductList.length == 0){
              this.$message.warning("请勾选产品再点击确认");
              return false;
          }
          let sellerOrderProductIds = []
          if (this.sellerOrderProductList.length!=0){
              for (let i=0;i<this.sellerOrderProductList.length;i++){
                  if (this.sellerOrderProductList[i].materialStatus == 2){
                      this.$message.warning("所选产品中有已确认产品");
                      return false;
                  }
                  sellerOrderProductIds.push(this.sellerOrderProductList[i].id)
              }
          }
          this.$http.post("seller/order/confirmProduct",{id:this.currentId,sellerOrderProductIds:sellerOrderProductIds}).then(res =>{
              if (res != undefined && res.data.code == 1000){
                  this.$message.success("确认成功")
                  this.getData();
              }
          });
      },
      goBack() {
          this.$router.push("/orderBom");
      }
  },
  watch: {
      '$route'(to, path) {
          if (to.path == '/editOrderBom') {
              this.getData()
          }
      }
  }
};
// 自定义列组件
Vue.component("table-operation", {
  template: `<span>
       <el-button @click='deleteRow(rowData,index)'>删除</el-button>
        </span>`,
  //  <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
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
          let params = { type: "delete", index: this.index };
          this.$emit("on-custom-comp", params);
          this.recompute();
      }
  }
});
</script>

<style>
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
  .product_table .el-table .cell{
      padding: 0;
  }
   .product_table .el-table__header-wrapper .el-table__header .has-gutter tr .el-table-column--selection .cell .el-checkbox{
        display: none;
    }
.product_table .el-table__header-wrapper .el-table__header .has-gutter tr .el-table-column--selection .cell:after{
    content: 'bom下发';
}
</style>
    