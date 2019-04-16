<template>
  <div class="table orderInfoBox">
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
                  <el-form-item label="商标:" prop="tradeMark">
                      <el-select v-model="orderForm.tradeMark">
                          <el-option v-for="item in tradeMarkList" :key="item.id" :label="item.tradeMark" :value="item.tradeMark">
                          </el-option>
                      </el-select>
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
                      <el-select v-model="orderForm.contacterName">
                          <el-option v-for="item in contacterList" :key="'contacter' + item.id" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="送货地址:" prop="deliveryAddress">
                      <el-select v-model="orderForm.deliveryAddress" placeholder="请选择">
                          <el-option v-for="item in addressList" :key="'address' + item.id" :label="item.address" :value="item.address">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="交货日期:">
                      <el-date-picker v-model="orderForm.deliveryDate" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期" :picker-options="pickerOptions">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item label="是否含税:">
                      <el-select v-model="orderForm.taxInclude" clearable placeholder="请选择" @change="changeTaxInclude">
                          <el-option v-for="item in tax" :key="item.value" :label="item.name" :value="item.value">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="产品数量:">
                      <el-input disabled v-model="orderForm.productNumber" />
                  </el-form-item>
                  <el-form-item label="运费:">
                      <el-input type="number" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="recompute" min="1" max="5" v-model.number="orderForm.freightDisplay" />
                  </el-form-item>
                  <el-form-item label="订单总价:">
                      <el-input disabled v-model="orderForm.totalMoneyDisplay" />
                  </el-form-item>
                  <br/>
                  <el-form-item label="付款方式:" prop="payType">
                      <el-select class="minorSelect" v-model="orderForm.payType" clearable placeholder="请选择">
                          <el-option v-for="item in payTypeArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="付款规则:" prop="payRule">
                      <el-select class="minorSelect" v-model="orderForm.payRule" clearable placeholder="请选择">
                          <el-option v-for="item in payRuleArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="发票类型:" prop="invoice">
                      <el-select class="minorSelect" v-model="orderForm.invoice" clearable placeholder="请选择" @change="changeTaxRate">
                          <el-option v-for="item in invoiceArray" :key="item.name" :label="item.name + '---' + item.taxRate" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="送货方式:" prop="delivery">
                      <el-select class="minorSelect" v-model="orderForm.delivery" clearable placeholder="请选择">
                          <el-option v-for="item in deliveryArray" :key="item.name" :label="item.name" :value="item.name">
                          </el-option>
                      </el-select>
                  </el-form-item>
                  <el-form-item label="附件:">
                      <el-upload
                         class="upload-demo"
                         :action="this.$path + '/seller/order/uploadSimple'"
                         :on-success="fileUpload"
                         :file-list="fileList"
                         :with-credentials="true">
                          <el-button size="small" type="primary">点击上传</el-button>
                      </el-upload>
                  </el-form-item>
              </el-form>
          </div>

          <hr class="marginTop" />
          <span class="text">产品列表</span>
          <el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
              <el-button size="small" @click="selectProductType()">新增产品</el-button>
          </el-tooltip>
          <hr class="marginBottom" />
          <div class="product_table">
              <el-table :data="tables" style="width: 100%;" border stripe>
                  <el-table-column align="center" prop="sequenceNumber" label="编号">
                    <template slot-scope="scope">
                      <el-input type="number" v-enter-number2 v-model.number="scope.row.sequenceNumber" :min="0" v-on:input="numberValue(scope.row,scope.$index)"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" prop="productCode" label="产品编码"></el-table-column>
                  <el-table-column align="center" prop="productName" label="产品名称"></el-table-column>
                  <el-table-column align="center" prop="factoryMaterialCode" label="工厂内部编码"></el-table-column>
                  <el-table-column align="center" label="数量">
                      <template slot-scope="scope">
                          <el-input style="width: 90%;" :min="1" type="number" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="sumNumber(scope.row)" v-model.number="scope.row.productNumber"></el-input>
                      </template>
                  </el-table-column>
                  <el-table-column align="center" prop="inventoryQty" label="库存数"></el-table-column>
                  <el-table-column align="center" prop="alreadySendQty" label="已发货数"></el-table-column>
                  <el-table-column align="center" prop="stayDeliveryQty" label="待发货数"></el-table-column>
                  <el-table-column align="center" prop="unit" label="单位"></el-table-column>
                  <el-table-column align="center" prop="source" label="来源"></el-table-column>
                  <el-table-column width="240" align="center" label="交货日期">
                      <template slot-scope="scope">
                          <el-date-picker type="date" class="deliveryDate" @input="pickerChange(scope.$index, scope.row.deliveryDate)" v-model="scope.row.deliveryDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
                          <!--<el-input @input="pickerChange" v-model="scope.row.deliveryDate"></el-input>-->
                      </template>
                  </el-table-column>
                  <el-table-column align="center" label="零售价">
                      <template slot-scope="scope">
                          <el-input style="width: 90%;" :min="1" type="number" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-on:input="sumPrice(scope.row)" v-model.number="scope.row.retailPriceDisplay"></el-input>
                      </template>
                  </el-table-column>
                  <el-table-column align="center" prop="taxRate" label="税率"></el-table-column>
                  <el-table-column align="center" prop="taxIncludeDisplay" label="是否含税"></el-table-column>
                  <el-table-column align="center" prop="untaxedPriceDisplay" label="未税价格"></el-table-column>
                  <el-table-column align="center" prop="taxesDisplay" label="税金"></el-table-column>
                  <el-table-column align="center" prop="total" label="总价"></el-table-column>
                  <el-table-column align="center" label="操作">
                      <template slot-scope="scope">
                          <el-button @click="deleteFn(scope.row)" type="text">删 除</el-button>
                      </template>
                  </el-table-column>
              </el-table>
          </div>
          <div style="text-align:center;">
              <el-button size="big" @click="save('customerForm')">保存</el-button>
          </div>
          <hr class="marginTop" />
          <span class="text">日志</span>
          <hr class="marginBottom" />
          <div class="handle-box">
              <el-table :data="logList" border stripe style="width: 100%">
                  <el-table-column width="100" prop="id" label="序号" sortable/>
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
              <el-button type="text" round @click="closeProductType">关闭</el-button>
          </el-dialog>
          <el-dialog title="新增产品" :visible.sync="addNewProductVisible">
              <el-form ref="newProductForm" :rules="rulesProduct" :model="newProductForm" label-width="100px">
                  <el-form-item label="编号"  prop="sequenceNumber">
                      <el-input type="number" style="width: 196px;" v-model="newProductForm.sequenceNumber"></el-input>
                  </el-form-item>
                  <el-form-item label="数量" prop="productNumber">
                      <el-input type="number" style="width: 196px;" min="1" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-model.number="newProductForm.productNumber"></el-input>
                  </el-form-item>
                  <el-form-item label="单价" prop="retailPriceDisplay">
                      <el-input type="number" style="width: 196px;" min="1" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-model.number="newProductForm.retailPriceDisplay"></el-input>
                  </el-form-item>
                  <el-form-item label="交货日期:" prop="deliveryDate">
                      <el-date-picker v-model="newProductForm.deliveryDate" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期" :picker-options="pickerOptions">
                      </el-date-picker>
                  </el-form-item>
              </el-form>
              <span slot="footer" class="dialog-footer">
                  <el-button @click="addNewProductVisible = false">取 消</el-button>
                  <el-button type="primary" @click="vali">确 定</el-button>
              </span>
          </el-dialog>
          <el-dialog title="新增产品" @close="closeing" :visible.sync="addProductVisible" width="80%">
              <el-form :inline="true" @keyup.enter.native="searchOne" :model="search" class="demo-form-inline">
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
                  <el-table-column label="产品名称" prop="materialName"></el-table-column>
                  <el-table-column label="工厂内部编码" prop="factoryMaterialCode"></el-table-column>
                  <el-table-column label="单位" prop="materialUnit"></el-table-column>
                  <el-table-column label="来源" prop="source"></el-table-column>
                  <el-table-column label="数量" prop="productNumber">
                      <template slot-scope="scope">
                          <el-input type="number" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-enter-number2 v-model.number="scope.row.productNumber" :min="0" v-on:input="sumNumPrice(scope.row)"></el-input>
                      </template>
                  </el-table-column>
                  <el-table-column label="交货日期" prop="deliveryDate">
                      <template slot-scope="scope">
                          <el-date-picker type="date" v-model="scope.row.deliveryDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期" :picker-options="pickerOptions"></el-date-picker>
                      </template>
                  </el-table-column>
                  <el-table-column label="零售价" prop="retailPriceDisplay">
                      <template slot-scope="scope">
                          <el-input type="number" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-model.number="scope.row.retailPriceDisplay" :min="0" v-on:input="sumRetailPrice(scope.row)"></el-input>
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
                              <el-option v-for="item in tax" :key="item.value" :label="item.name" :value="item.value">
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
                  <el-table-column type="selection" label="操作">
                  </el-table-column>
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
          loading: false,
          currentId: "",
          customerList: [],
          addressList: [],
          contacterList: [],
          productList: [],
          productListCenter: [],
          productListSearch: [],
          selectedProductList: [],
          tradeMarkList:[],
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
              materialCode: "",
              materialName: "",
              factoryMaterialCode:'',
              auditingStatus:0,
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
              // contacterName: [
              //     { required: true, message: "请选择联系人", trigger: "change" }
              // ],
              // deliveryAddress: [
              //     { required: true, message: "请选择送货地址", trigger: "change" }
              // ],
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
          rulesProduct:{
              sequenceNumber: [
                  { required: true, message: "请输入编号", trigger: "change" }
              ],
              productNumber: [
                  { required: true, message: "请输入数量", trigger: "change" }
              ],
              retailPriceDisplay: [
                  { required: true, message: "请输入单价", trigger: "change" }
              ],
              deliveryDate: [
                  { required: true, message: "请选择交货日期", trigger: "change" }
              ],
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
              fileName: "",
              tradeMark:"",
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
                  d.taxInclude == 1
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
      pickerChange (i, val) {
          let arr = document.getElementsByClassName('deliveryDate')
          arr[i].children[0].value = val
      },
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
      vali() {
          if (this.newProductForm.productNumber < 0 || this.newProductForm.retailPriceDisplay < 0) {
              this.$message.warning('数量和单价不能小于0');
                return
          }
          this.$refs["newProductForm"].validate(valid => {
              if (valid) {
                  this.addnewproduct();
              }
          });
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
          if (this.newProductForm.productNumber <= 0){
              this.$message.warning("数量不能小于等于0")
              return
          }
          this.productForm = {};
          this.productForm.sequenceNumber = this.newProductForm.sequenceNumber
          this.productForm.productNumber = this.newProductForm.productNumber
          this.productForm.retailPriceDisplay = this.newProductForm.retailPriceDisplay
          this.productForm.deliveryDate = this.newProductForm.deliveryDate
          this.productForm.taxRate = this.taxRate
          if (this.taxRate==undefined){
              this.productForm.untaxedPriceDisplay = this.newProductForm.productNumber * this.newProductForm.retailPriceDisplay
              this.productForm.taxesDisplay = 0
          }else{
              this.productForm.untaxedPriceDisplay = ((this.newProductForm.productNumber * this.newProductForm.retailPriceDisplay * (100-this.taxRate))/100).toFixed(2)
              this.productForm.taxesDisplay = ((this.newProductForm.productNumber * this.newProductForm.retailPriceDisplay * this.taxRate)/100).toFixed(2)
          }
          this.productForm.alreadySendQty = 0
          this.productForm.stayDeliveryQty = 0
          this.productForm.inventoryQty = 0
          this.selectedProductList.push(this.productForm);
          this.addNewProductVisible = false
          this.recompute();
      },
      addproduct() {

          if (this.selectedproductList.length === 0) {
              this.$message.warning('请勾选产品')
              return
          } else {
              for (let i = 0; i < this.selectedproductList.length; i++) {
                  if (this.selectedproductList[i].productNumber < 0 || this.selectedproductList[i].retailPriceDisplay < 0){
                      this.$message.warning('数量和单价不能小于0')
                    return
                  }
              }
          }
          for (let i = 0; i < this.productList.length; i++) {
              if (this.productList[i].productNumber != null || this.productList[i].retailPriceDisplay != null) {
                  for (let index = 0; index < this.selectedproductList.length; index++) {
                      if (this.isObjEqual(this.productList[i], this.selectedproductList[index])){
                          this.productForm = {};
                          if (
                              this.selectedproductList[index].productNumber == null ||
                              this.selectedproductList[index].retailPriceDisplay == null
                          ) {
                              this.$message.error("请检查所选产品的价格和数量");
                          } else {
                              this.productForm.productCode = this.selectedproductList[index].materialCode;
                              this.productForm.productId = this.selectedproductList[index].id;
                              this.productForm.productName = this.selectedproductList[index].materialName;
                              this.productForm.deliveryDate = this.selectedproductList[index].deliveryDate;
                              this.productForm.productNumber = this.selectedproductList[index].productNumber;
                              this.productForm.unit = this.selectedproductList[index].materialUnit;
                              this.productForm.source = this.selectedproductList[index].source;
                              this.productForm.taxRate = this.selectedproductList[index].taxRate;
                              this.productForm.taxInclude = this.selectedproductList[index].taxInclude;
                              this.productForm.retailPriceDisplay = this.selectedproductList[index].retailPriceDisplay;
                              this.productForm.untaxedPriceDisplay = this.selectedproductList[index].untaxedPriceDisplay;
                              this.productForm.taxesDisplay = this.selectedproductList[index].taxesDisplay;
                              this.productForm.factoryMaterialCode = this.selectedproductList[index].factoryMaterialCode;
                              this.productForm.alreadySendQty = 0
                              this.productForm.stayDeliveryQty = this.selectedproductList[index].productNumber
                              this.productForm.inventoryQty = this.selectedproductList[index].inventoryNum
                              this.selectedProductList.push(this.productForm);
                              this.addProductVisible = false;
                              this.recompute();
                          }
                      }
                  }
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
                          this.productList[i].taxRate = this.taxRate;
                          this.productList[i].taxInclude = this.taxInclude;
                          if (this.deliveryDate != null) {
                              this.productList[i].deliveryDate = this.deliveryDate;
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
      recompute(event) {
          // var val= event.value;
          // //匹配非数字
          // var reg = new RegExp("([^0-9]*)","g");
          // var ma = val.match(reg);
          // //如果有非数字，替换成""
          // if(ma.length>0){
          //     for(var k in ma){
          //         if(ma[k]!=""){
          //             val = val.replace(ma[k],"");
          //         }
          //     }
          // }
          // //可以为0，但不能以0开头
          // if(val.startsWith("0")&&val.length>1){
          //     val = val.substring(1,val.length);
          // }
          // //赋值，这样实现的效果就是用户按下非数字不会有任何反应
          // event.value = val;


          let number = 0;
          let total = 0;
          this.tables.forEach(item => {
              item.total = item.productNumber * item.retailPriceDisplay
              number += parseFloat(item.productNumber);
              total += parseFloat(item.total);
          });
          this.orderForm.productNumber = number;
          let fd = parseFloat(this.orderForm.freightDisplay);
          if (!fd) {
              fd = 0;
          }
          this.orderForm.totalMoneyDisplay = (total + fd).toFixed(2);
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
              }
          });
          this.$http.post("tradeMark/searchList").then(res =>{
              if (res != undefined && res.data.code == 1000) {
                  console.log(res)
                  this.tradeMarkList = res.data.data
              }
          })
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
      closeProductType(){
          this.selectedProductTypeVisible = false
      },
      addNewProduct(){
          this.newProductForm.sequenceNumber = "",
          this.newProductForm.productNumber = "",
          this.newProductForm.retailPriceDisplay = "",
          this.newProductForm.deliveryDate = "",
          this.addNewProductVisible = true
          this.selectedProductTypeVisible = false
      },

      addProduct() {
          this.selectedProductTypeVisible = false
          this.selectedproductList = [];
          this.addProductVisible = true;
          this.productList = [];
          this.search.pageNum = 1;
          this.search.materialCode = "",
          this.search.materialName = "",
          this.selectLike();
      },
      setFile(event) {
          this.file = event.target.files[0]; //获取文件
          var windowURL = window.URL || window.webkitURL;
          this.file = event.target.files[0];
      },
      save(formName) {
          for (let i = 0; i < this.selectedProductList.length; i++) {
              if (this.selectedProductList[i].productNumber  < 0 || this.selectedProductList[i].retailPriceDisplay  < 0) {
                  this.$message.warning('零售价和数量不能小于0')
                  return
              }
          }
          if (this.orderForm.freightDisplay < 0 || typeof this.orderForm.freightDisplay == 'string'){
              this.$message.warning('运费不能小于0')
              return
          }
          this.$refs[formName].validate(valid => {
              if (valid) {
                  let _this = this

                  this.fileList = [],
                  this.backProductList;
                  let form = {
                      orderInfo: this.orderForm,
                      productInfos: this.selectedProductList,
                      currentId: this.currentId,
                      file: this.file
                  };//sequenceNumber
                  for (let i = 0; i < form.productInfos.length; i++) {
                      if (!form.productInfos[i].sequenceNumber) {
                            this.$message.warning('请输入编号')
                          return
                      }
                  }
                  var formdata = new FormData(form);
                  formdata.append("orderInfo", JSON.stringify(form.orderInfo));
                  formdata.append("productInfos", JSON.stringify(form.productInfos));
                  formdata.append("currentId", form.currentId);
                  let config = {
                      headers: {
                          "Content-Type": "multipart/form-data"
                      },
                      withCredentials: true
                  };
                  _this.loading = true
                  this.$axios.post(this.$path + "/seller/order/save", formdata, config).then(res => {
                      if (res.data.code == 1000) {
                          this.$message.success("保存成功");
                          //跳转到列表页面
                          this.$router.push({ path: "/sellerOrder",query:{closeFlag:1} });
                      } else {
                          this.$message.error(res.data.message);
                      }
                      _this.loading = false
                      }).catch(err => {
                          _this.loading = false
                  });
              } else {
                  this.$message.error("带*为必填项");
                  return false;
              }
          });
      },
      goBack() {
          this.$router.push({path:"/sellerOrder",query: {closeFlag: 1}});
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
    .orderInfoBox input{
        ime-mode:disabled;
    }
</style>
    