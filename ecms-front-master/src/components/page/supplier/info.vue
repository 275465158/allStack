<template>
  <div v-loading="saveLoading" class="table">
    <div class="container">
      <hr class="marginTop" />
      <span class="text">供应商信息</span>
      <hr class="marginBottom" />
      <div class="handle-box">
        <el-form :model="form" ref="form" label-width="100px" :rules="rules">
          <el-input type="hidden" v-model="form.id"></el-input>
          <el-row :gutter="30">
            <el-col :span="6">
              <el-form-item label="供应商编号:">
                <el-input v-model="form.supplierCode" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="供应商名称:" prop="supplierName">
                <el-input v-model="form.supplierName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="公司电话:" prop="">
                <el-input v-model="form.supplierTelphone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="8">
              <el-form-item label="公司地址:" prop="">
                <!--<el-cascader :options="division" v-model="form.region" change-on-select></el-cascader>-->
                <area-cascader :level="1" type="text" :data="pcaa" placeholder="请选择地区" v-model="form.address"></area-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="16">
              <el-form-item label="门牌号:" prop="">
                <el-input v-model="form.houseNumber"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="6">
              <el-form-item label="传真号码:" prop="">
                <el-input v-model="form.fax"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="收货仓库:" prop="">
                <el-select v-model="form.defaultRepertory" placeholder="请选择">
                  <el-option v-for="item in this.repertoryList" :key="item.id" :label="item.repertoryName" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="6">
              <el-form-item label="结算方式:" prop="">
                <el-select v-model="form.paymentType" placeholder="请选择">
                  <el-option v-for="item in this.paymentTypeList" :key="item.id" :label="item.paymentName" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="付款时间:" prop="">
                <el-select v-model="form.paymentPeriod" placeholder="请选择">
                  <el-option v-for="item in this.paymentPeriodList" :key="item.id" :label="item.periodName" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <!--aaaaaa-->
            <el-col :span="6">
              <el-form-item label="发票类型:" prop="">
                <el-select v-model="form.invoiceType" placeholder="请选择">
                  <el-option v-for="item in this.invoiceTypeList" :key="item.id" :label="item.typeName + '---' +item.taxRate" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="送货方式:" prop="">
                <el-select v-model="form.shippingMethod" placeholder="请选择">
                  <el-option v-for="item in this.shippingMethodList" :key="item.id" :label="item.methodName" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="8">
              <el-form-item label="银行账户名称:" prop="">
                <el-input v-model="form.bankName">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="银行账号:" prop="">
                <el-input v-model="form.bankAccount">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="开户银行:" prop="">
                <el-input v-model="form.bank">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="税号:" prop="">
                <el-input v-model="form.taxId">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>
      <hr class="marginTop" />
      <span class="text">联系人管理</span>
      <el-button size="small" @click="addContact">添加联系人</el-button>
      <hr class="marginBottom" />
      <div class="handle-box">
        <el-table :data="contactTableData" border stripe style="width: 100%">
          <el-table-column prop="id" type="index" label="序号" sortable/>
          <el-table-column prop="contactName" label="联系人姓名" />
          <el-table-column prop="departmentName" label="部门" />
          <el-table-column prop="title" label="职位" />
          <el-table-column prop="mobile" label="手机号码" />
          <el-table-column prop="fax" label="传真号码" />
          <el-table-column prop="email" label="电子邮件" />
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button size="small" @click="editContact(scope.$index,scope.row)">
                资料
              </el-button>
              <el-button size="small" @click="delContact(scope.$index)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <hr class="marginTop" />
      <span class="text">物料管理</span>
      <el-button size="small" @click="addMaterial">新增物料</el-button>
      <hr class="marginBottom" />
      <div class="handle-box">
        <el-table :data="supplierMaterialTable" border style="width:100%">
          <el-table-column prop="id" label="序号" type="index" sortable>
          </el-table-column>
          <el-table-column prop="materialCode" label="物料编号">
          </el-table-column>
          <el-table-column prop="materialName" label="物料名称">
          </el-table-column>
          <el-table-column prop="originalMaterial" label="原图材料">
          </el-table-column>
          <el-table-column label="参数列表" prop="materialBomParamValueStr">
          </el-table-column>
          <el-table-column label="价格" prop="price">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="最少采购数量" prop="minQty">
            <template slot-scope="scope">
              <el-input v-model="scope.row.minQty" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="重量(kg)" prop="weight">
            <template slot-scope="scope">
              <el-input v-model="scope.row.weight" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="采购方式" prop="purchaseMethod" :formatter="purchaseMethodFormatter">
            <template slot-scope="scope">
              <el-select v-model="scope.row.purchaseMethod" :value="form[materialDIYform.purchaseMethod]" @change="$set(materialDIYform, materialDIYform.purchaseMethod, $event)">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </template>
          </el-table-column>

          <el-table-column label="到货天数" prop="purchaseDays">
            <template slot-scope="scope">
              <el-input v-model="scope.row.purchaseDays" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <!-- <el-button size="small" @click="editmaterialdiy(scope.row,scope.$index)">修改</el-button> -->
              <el-button size="small" @click="delMaterial(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="insert">保存</el-button>
          <el-button @click="goback">返回</el-button>
        </span>
        <!-- *-----------------------------------------------------------* -->
      </div>
    </div>

    <el-dialog title="联系人管理" @close="closeing" :visible.sync="contactFormEditable" width="30%">
      <el-form ref="contactForm" :model="contactForm" label-width="100px">
        <el-form-item label="联系人姓名">
          <el-input v-model="contactForm.contactName"></el-input>

        </el-form-item>
        <el-form-item label="部门">
          <el-input v-model="contactForm.departmentName"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="contactForm.title"></el-input>
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="contactForm.mobile"></el-input>
        </el-form-item>
        <el-form-item label="传真">
          <el-input v-model="contactForm.fax"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件">
          <el-input v-model="contactForm.email"></el-input>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelContact">取消</el-button>
        <el-button type="primary" @click="insertContact">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="物料管理" @close="closeing2" :visible.sync="materialFormEditable" width="80%">
      <hr class="marginTop" />
      <span class="text">搜索物料:</span>
      <el-form @keyup.enter.native="selectMaterial" ref="materialSearchForm" :model="materialSearchForm" label-width="100px" :inline="true" class="demo-form-inline">
        <el-form-item label="物料名称:">
          <el-input v-model="materialSearchForm.materialName"></el-input>
        </el-form-item>
        <el-form-item label="物料编码:">
          <el-input v-model="materialSearchForm.materialCode"></el-input>
        </el-form-item>
        <el-button @click="selectMaterial">查询</el-button>
      </el-form>
      <hr class="marginBottom" />
      <el-table v-loading="loading" :ref="materialSearchTable" :data="materialSearchTable" border style="width:100%" height="250" @selection-change="handleSelectMaterial">
        <!-- <el-table-column width="55" prop="id" type="selection"></el-table-column> -->
        <el-table-column label="物料编码" prop="materialCode"></el-table-column>
        <el-table-column label="物料名称" prop="materialName"></el-table-column>
        <el-table-column label="原图材料" prop="originalMaterial"></el-table-column>
        <el-table-column label="参数" prop="materialBomParamValueStr">
        </el-table-column>
        <el-table-column label="价格" prop="price">
          <template slot-scope="scope">
            <el-input v-model="scope.row.price" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="最少采购数量" prop="minQty">
          <template slot-scope="scope">
            <el-input v-model="scope.row.minQty" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
          </template>
        </el-table-column>

        <el-table-column label="重量(kg)" prop="weight">
          <template slot-scope="scope">
            <el-input v-model="scope.row.weight" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="number"></el-input>
          </template>
        </el-table-column>

        <el-table-column label="采购方式" prop="purchaseMethod">

          <template slot-scope="scope">
            <el-select v-model="scope.row.purchaseMethod" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>

        </el-table-column>
        <el-table-column label="到货天数" prop="purchaseDays">
          <template slot-scope="scope">
            <el-input v-model="scope.row.purchaseDays" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" type="tel"></el-input>
          </template>
        </el-table-column>

        <el-table-column type="selection" label="操作">
          <!-- <template slot-scope="scope">
                     <el-checkbox v-model="checked2" >添加</el-checkbox>
                </template> -->
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination :page-size="5" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
        </el-pagination>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="materialFormEditable=false">取消</el-button>
        <el-button type="primary" @click="insertSupplierMaterial">确定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
import { pca, pcaa } from "area-data";
export default {
  data() {
    return {
      loading: false,
      saveLoading: false,
        rules: {
            supplierName: [
                { required: true, message: "请输入供应商名称", trigger: "blur" }
            ]
        },
      //采购方式下拉框
      options: [
        {
          value: "0",
          label: "按数量"
        },
        {
          value: "1",
          label: "按批量"
        }
      ],
      materialDIYform: {},
      updateEditable: false,
      tempdata: "", //新增联系人  用来接收json数据的缓存变量
      supplierId: null,
      pca: pca,
      pcaa: pcaa,
      //供应商主表单
      form: {
        id: "",
        address: "",
        supplierCode: "",
        supplierName: "",
        supplierTelphone: "",
        houseNumber: "",
        contact: "",
        mobile: "",
        fax: "",
        defaultRepertory: "",
        paymentType: "",
        paymentPeriod: "",
        invoiceType: "",
        shippingMethod: "",
        bankName: "",
        bankAccount: "",
        bank: "",
        taxId: ""
      },
      pages: 1,
      iseditContact: false,
      //联系人是否是编辑的flag
      whichrow: 0,
      //记录编辑哪行的联系人
      tempcontact: "",
      search: {
        supplierId: null
      },
      check2: "", //是否批量添加
      //   searchz: {
      //     id: "",
      //     materialCode: "",
      //     materialName: "",
      //     flag: 0,
      //     pageNum: 1,
      //     pageSize: 100000
      //   }, //郑力接口的参数

      contactSearch: {
        supplierId: null
      },
      supplierMaterialList: "",
      //供应商联系人列表
      contactTableData: [],
      //所属地列表
      //division: [],
      //联系人列表
      supplierContact: [],
      //供应商名下物料
      materiels: [],
      //相关设置数据
      invoiceTypeList: [],
      paymentPeriodList: [],
      paymentTypeList: [],
      shippingMethodList: [],
      //相关默认仓库
      repertoryList: [],
      //联系人表单控制字段
      contactFormEditable: false,
      //联系人表单
      contactForm: {
        supplierId: 0,
        contactName: "",
        departmentName: "",
        title: "",
        mobile: "",
        fax: "",
        email: ""
      },
      //供应商物料列表
      supplierMaterialTable: [],
      //物料管理表单
      //控制字段
      materialFormEditable: false,
      //查询结果表格
      materialSearchTable: [],
      //物料需要手输的内容
      materialDIY: {
        supplierId: "",
        materialList: []
      },

      materialList: [],
      //查询模型
      materialSearchForm: {
        supplierid: "",
        materialCode: "",
        materialName: "",
        // flag: 0,
        pageNum: 1,
        pageSize: 5
      },
      //物料提交表单
      supplierMaterialForm: {
        //supplierId: null,
        materialList: [],
        price: "",
        minQty: "",
        purchaseMethod: "",
        purchaseDays: "",
        repertoryId: ""
      },
      materialForm: {
        rowIndex: null
      },
      //物料属性字段列表
      searchParams: []
    };
  },
  created() {
    if (this.$route.query.supplierId != undefined) {
      //this.supplierMaterialForm.supplierId = this.$route.query.supplierId;
      this.search.supplierId = this.$route.query.supplierId;
      this.contactSearch.supplierId = this.$route.query.supplierId;
      this.contactForm.supplierId = this.$route.query.supplierId;
    }
    this.getData();
    // console.log('this.pcaa',this.pcaa);
  },
  watch: {
    $route(to, from) {
      if (to.path == "/supplierInfo" && this.$route.query.works !== 1) {
        let id = this.$route.query.id;
        if (id != undefined) {
          this.search.supplierId = id;
        }
        Object.assign(this.$data, this.$options.data());
        this.getData();
      }
    }
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        return d;
      });
    }
  },
  methods: {
    closeing2() {
      this.materialSearchForm.materialName = "";
      this.materialSearchForm.materialCode = "";
    },
    closeing() {
      this.contactForm.contactName = "";
      this.contactForm.departmentName = "";
      this.contactForm.title = "";
      this.contactForm.mobile = "";
      this.contactForm.fax = "";
      this.contactForm.email = "";
    },
    // 分页导航
    handleCurrentChange(val) {
      this.materialSearchForm.pageNum = val;
      this.materialSearch();
    },
    editmaterialdiy(row, index) {
      this.materialDIYform.row = index;
      this.materialDIYform.price = row.price;
      this.materialDIYform.minQty = row.minQty;
      this.materialDIYform.purchaseDays = row.purchaseDays;
      this.materialDIYform.purchaseMethod = row.purchaseMethod;
      this.updateEditable = true;
    },
    updatediy() {
      this.supplierMaterialTable[
        this.materialDIYform.row
      ].purchaseDays = this.materialDIYform.purchaseDays;
      this.supplierMaterialTable[
        this.materialDIYform.row
      ].purchaseMethod = this.materialDIYform.purchaseMethod;
      this.supplierMaterialTable[
        this.materialDIYform.row
      ].price = this.materialDIYform.price;
      this.supplierMaterialTable[
        this.materialDIYform.row
      ].minQty = this.materialDIYform.minQty;
      this.updateEditable = false;
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      if (this.$route.query.supplierId != null) {
        this.$http
          .post("/supplier/getInfo", { id: this.$route.query.supplierId })
          .then(res => {
            if (res != undefined && res.data.code == 1000) {
              this.form = res.data.data;
            }
          });
        this.$http
          .post("/supplier/contact/listBySupplierId", {
            supplierId: this.$route.query.supplierId
          })
          .then(res => {
            if (res != undefined && res.data.code == 1000) {
              this.contactTableData = res.data.data;
            }
          });
        this.$http
          .post("/supplier/material/listBySupplierId", {
            supplierId: this.$route.query.supplierId
          })
          .then(res => {
            this.supplierMaterialTable = [];
            if (res != undefined && res.data.code == 1000) {
              this.supplierMaterialList = res.data.data;
              if (this.supplierMaterialList.length != 0) {
                for (var i = 0; i < this.supplierMaterialList.length; i++) {
                  this.supplierMaterialTable.push(
                    this.supplierMaterialList[i].materialBom
                  );
                }
              }
            }
          });
      }
      this.$http.post("/material/paramNames").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.searchParams = res.data.data;
        }
      });

      this.$http.post("/repertory/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.repertoryList = res.data.data;
          this.form.defaultRepertory = res.data.data[0].id;
        }
      });

      this.$http.post("/purchase/shipping/method/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.shippingMethodList = res.data.data;
          if (this.shippingMethodList.length != 0) {
            this.form.shippingMethod = res.data.data[0].id;
          }
        }
      });

      this.$http.post("/purchase/payment/type/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.paymentTypeList = res.data.data;
          if (this.paymentTypeList.length != 0) {
            this.form.paymentType = res.data.data[0].id;
          }
        }
      });

      this.$http.post("/purchase/payment/period/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.paymentPeriodList = res.data.data;
          if (this.paymentPeriodList.length != 0) {
            this.form.paymentPeriod = res.data.data[0].id;
          }
        }
      });

      this.$http.post("/purchase/invoice/type/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.invoiceTypeList = res.data.data;
          if (this.invoiceTypeList.length != 0) {
            this.form.invoiceType = res.data.data[0].id;
          }
        }
      });
    },
    purchaseMethodFormatter(row, column) {
      if (row.purchaseMethod == 0) {
        return "按数量";
      } else {
        return "按批量";
      }
    },
      insert(){
          this.$refs["form"].validate(valid => {
              if (valid) {
                  this.save();
              }
          });
      },
    save() {
      this.saveLoading = true;
      this.$http
        .post("/supplier/saveOrUpdateP", this.form)
        .then(res => {
          if (res.data != undefined && res.data.code == 1000) {
            this.contactTableData.forEach(item => {
              item.supplierId = res.data.data.id;
            });
            this.materialDIY.materialList = this.supplierMaterialForm.materialList;
            JSON.stringify(this.materialDIY);
            this.$http
              .post("/supplier/material/saveSupplierMaterial", {
                jsonStr: JSON.stringify(this.supplierMaterialTable),
                supplierId: res.data.data.id
              })
              .then(res => {});
            console.log(this.contactForm);
            this.$http
              .post("/supplier/contact/saveContactList", {
                contactTableData: this.contactTableData
              })
              .then(res => {});
            this.$message.success("保存成功");
            this.goback();
          }
          this.saveLoading = false;
        })
        .catch(err => {
          this.saveLoading = false;
        });
    },
    goback() {
      this.$router.push({ path: "/supplierList", query: { closeFlag: 1 } });
    },
    addContact() {
      this.contactFormEditable = true;
    },
    insertContact() {
      this.contactFormEditable = false;
      //进来先查询有没有供应商id，有供应商id则说明是修改，否则为新增
      //   var tempdata="";
      if (this.contactTableData == null) {
        this.contactTableData = [];
      }
      if (this.iseditContact) {
        this.data = JSON.parse(JSON.stringify(this.contactForm));
        this.contactTableData.splice(this.whichrow, 1, this.data);
      } else {
        this.data = JSON.parse(JSON.stringify(this.contactForm));
        this.contactTableData.push(this.data);
      }
      this.data = "";
      this.iseditContact = false;
    },
    editContact(row, rowcontent) {
      this.contactForm = rowcontent;
      this.tempcontact = rowcontent;
      this.iseditContact = true;
      this.whichrow = row;
      this.contactFormEditable = true;
    },
    delContact(row) {
      this.contactTableData.splice(row, 1);
    },
    cancelContact() {
      this.contactFormEditable = false;
      this.iseditContact = false;
      // this.contactForm=this.tempcontact
    },
    addMaterial() {
      this.selectMaterial();
      this.materialFormEditable = true;
      this.$http.post("/material/paramNames").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.searchParams = res.data.data;
        }
      });
    },
    selectMaterial() {
      this.materialSearchForm.pageNum = 1;
      this.materialSearch();
    },
    materialSearch() {
      this.loading = true;
      this.materialSearchForm.supplierid = this.$route.query.supplierId;
      this.$http
        .post(
          "/materialInfo/searchMaterialAndProductList",
          this.materialSearchForm
        )
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("完成搜索");
            this.materialSearchTable = res.data.data.list;
            this.pages = res.data.data.total;
          }
          this.loading = false;
        })
        .catch(err => {
          console.log(err);
          this.loading = false;
        });
    },
    insertSupplierMaterial() {
      for (
        let index = 0;
        index < this.supplierMaterialForm.materialList.length;
        index++
      ) {
        if (this.supplierMaterialForm.materialList[index].price == null) {
          this.$message.error("价格不能为空");
        } else {
          this.supplierMaterialTable.push(
            this.supplierMaterialForm.materialList[index]
          );
          this.materialFormEditable = false;
        }
      }
    },
    delMaterial(row) {
      // this.supplierMaterialForm.materialList.shift(row);
      this.supplierMaterialTable.splice(row, 1)
    },
    handleSelectMaterial(vals) {
      vals.forEach(val => {
        val.supplierId = this.$route.query.supplierId;
        val.materialId = val.id;
      });
      this.supplierMaterialForm.materialList = vals;
    },
    editHandle(val, index) {
      this.materialForm = val;
      this.materialForm.rowIndex = index;
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 50px;
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

