<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop" />
            <span class="text">供应商信息</span>
            <hr class="marginBottom" />
            <div class="handle-box">
                <el-form :model="form" ref="form" label-width="100px">
                    <el-input type="hidden" v-model="form.id" disabled ></el-input>
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="供应商编号:">
                                <el-input v-model="form.supplierCode" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="供应商名称:" prop="">
                                <el-input v-model="form.supplierName" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="公司电话:" prop="">
                                <el-input v-model="form.supplierTelphone" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                   <!-- <el-row :gutter="30">
                        <el-col :span="8">
                            <el-form-item label="公司地址:" prop="">
                               <el-input v-model="form.address" disabled  ></el-input>
                                <el-cascader :options="division" v-model="form.region" change-on-select></el-cascader>-->
                                <!-- <area-cascader :level="1" type="text" :data="pcaa" placeholder="请选择地区" v-model="form.address" disabled ></area-cascader>
                            </el-form-item>
                        </el-col> -->

                        <el-col :span="16">
                            <el-form-item label="门牌号:" prop="">
                                <el-input v-model="form.houseNumber" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="联系人:" prop="">
                                <el-input v-model="form.contact" disabled>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系电话:" prop="">
                                <el-input v-model="form.mobile" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="传真号码:" prop="">
                                <el-input v-model="form.fax" disabled ></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="收货仓库:" prop="">
                                <el-select v-model="form.defaultRepertory" placeholder="请选择" disabled>
                                    <el-option v-for="item in this.repertoryList" :key="item.id" :label="item.repertoryName" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="6">
                            <el-form-item label="结算方式:" prop="">
                                <el-select v-model="form.paymentType" placeholder="请选择" disabled>
                                    <el-option v-for="item in this.paymentTypeList" :key="item.id" :label="item.paymentName" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="付款时间:" prop="">
                                <el-select v-model="form.paymentPeriod" placeholder="请选择" disabled>
                                    <el-option v-for="item in this.paymentPeriodList" :key="item.id" :label="item.periodName" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="发票类型:" prop="">
                                <el-select v-model="form.invoiceType" placeholder="请选择" disabled>
                                    <el-option v-for="item in this.invoiceTypeList" :key="item.id" :label="item.typeName" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="送货方式:" prop="">
                                <el-select v-model="form.shippingMethod" placeholder="请选择" disabled>
                                    <el-option v-for="item in this.shippingMethodList" :key="item.id" :label="item.methodName" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="30">
                        <el-col :span="8">
                            <el-form-item label="银行账户名称:" prop="" >
                                <el-input v-model="form.bankName" disabled>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="银行账号:" prop="" >
                                <el-input v-model="form.bankAccount" disabled>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="开户银行:" prop="" >
                                <el-input v-model="form.bank" disabled>
                                </el-input>
                            </el-form-item>
                        </el-col>
                         <el-col :span="8">
                            <el-form-item label="税号:" prop="">
                                <el-input v-model="form.taxId" disabled>
                                </el-input>
                            </el-form-item>
                        </el-col>

                    </el-row>
                </el-form>
               
            </div>
            <hr class="marginTop" />
            <span class="text">联系人管理</span>
            <!-- <el-button size="small" @click="addContact">添加联系人</el-button> -->
            <hr class="marginBottom" />
            <div class="handle-box">
                <el-table :data="contactTableData" border stripe style="width: 100%">
                    <el-table-column prop="id"   type="index" label="序号" sortable/>
                    <el-table-column prop="contactName" label="联系人姓名" />
                    <el-table-column prop="departmentName" label="部门" />
                    <el-table-column prop="title" label="职位" />
                    <el-table-column prop="mobile" label="手机号码" />
                    <el-table-column prop="fax" label="传真号码" />
                    <el-table-column prop="email" label="电子邮件" />
                    <!-- <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button size="small" @click="editContact(scope.$index,scope.row)">
                                资料
                            </el-button>
                            <el-button size="small" @click="delContact(scope.$index)">
                                删除
                            </el-button>
                        </template>
                </el-table-column> -->
            </el-table>
        </div>
        <hr class="marginTop"/>
        <span class="text">物料管理</span>
        <!-- <el-button size="small" @click="addMaterial">新增物料</el-button> -->
        <hr class="marginBottom"/>
        <div class="handle-box">
            <el-table :data="supplierMaterialTable" border style="width:100%">
                <el-table-column prop="id" label="序号"   type="index" sortable>
                </el-table-column>
                <el-table-column prop="materialBom.materialCode" label="物料编号">
                </el-table-column>
                <el-table-column prop="materialBom.materialName" label="物料名称">
                </el-table-column>
                <el-table-column prop="materialBom.originalMaterial" label="原图材料">
                </el-table-column>
                <el-table-column label="参数列表" prop="materialBom.materialBomParamValueStr">
                </el-table-column>
                 <el-table-column label="价格" prop="price">
                </el-table-column>
                <el-table-column label="最少采购数量" prop="minQty">
                </el-table-column>
                <el-table-column label="重量(kg)" prop="weight">
                </el-table-column>
                <el-table-column label="采购方式" prop="purchaseMethod">
                    <template slot-scope="scope">
                    <el-select v-model="scope.row.purchaseMethod"
                        :value="form[materialDIYform.purchaseMethod]"
                        @change="$set(materialDIYform, materialDIYform.purchaseMethod, $event)" disabled>
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="到货天数" prop="purchaseDays">
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="goback">返回</el-button>
            </span>
        </div>
    </div>

</div>

</template>

<script>
import { pca, pcaa } from "area-data";
export default {
  data() {
    return {
      materialDIYform:[],
      purchaseMethod:'',
        pages: 1,
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
        taxId:"",
      },
      iseditContact: false,
      //联系人是否是编辑的flag
      whichrow: 0,
      //记录编辑哪行的联系人
      tempcontact: "",
      search: {
        supplierId: null,
          pageNum:1,
          pageSize:20,
      },
      check2: "", //是否批量添加
      contactSearch: {
        supplierId: null
      },
      supplierMaterialList:'',
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
        id: "",
        materialCode: "",
        materialName: "",
        flag: 0,
        pageNum: 1,
        pageSize: 100000
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
    this.getData();
    
  },
  watch: {
    $route(to, from) {
      if (to.path == "/supplierdetail") {
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
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    aaaa(row, column) {
      //物料表格格式化函数
      if (row.purchaseMethod == 0) {
        return "按数量";
      } else {
        return "按批量";
      }
    },
      // 分页导航
      handleCurrentChange(val) {
          this.search.pageNum = val;
          this.getData();
      },
    // 获取 easy-mock 的模拟数据
    getData() {
        if (this.$route.query.supplierId != null) {
            this.search.supplierId = this.$route.query.supplierId;
            this.contactSearch.supplierId = this.$route.query.supplierId;
            this.contactForm.supplierId = this.$route.query.supplierId;
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
        this.$http.post("/supplier/material/searchPagerBySupplierId", this.search).then(res => {
            this.supplierMaterialTable = [];
            if (res != undefined && res.data.code == 1000) {
                this.supplierMaterialTable = res.data.data.list;
                this.pages = res.data.data.total;
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
         
        }
      });

      this.$http.post("/purchase/shipping/method/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.shippingMethodList = res.data.data;
        }
      });

      this.$http.post("/purchase/payment/type/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.paymentTypeList = res.data.data;
           
        }
      });

      this.$http.post("/purchase/payment/period/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.paymentPeriodList = res.data.data;
        }
      });

      this.$http.post("/purchase/invoice/type/getList").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.invoiceTypeList = res.data.data;
        }
      });
    },
      aaaa(row,column){
        // if(row.purchaseMethod== 0){
        //     return "按数量"
        // }else{
        //     return "按批量"
        // }
    },
    save() {
      this.$http.post("/supplier/saveOrUpdate", this.form).then(res => {
        if (res.data != undefined && res.data.code == 1000) {
          this.contactForm.supplierId = res.data.data.id;
          // for (let index = 0; index <  this.supplierMaterialForm.materialList.length; index++) {
          //     this.materialDIY.supplierId=res.data.data.id;
          //     // this.materialDIY.price=this.supplierMaterialForm.materialList[index].price;
          //     // this.materialDIY.minQty=this.supplierMaterialForm.materialList[index].minQty;
          //     // this.materialDIY.purchaseMethod=this.supplierMaterialForm.materialList[index].purchaseMethod;
          //     // this.materialDIY.purchaseDays=this.supplierMaterialForm.materialList[index].purchaseDays;
          //     this.materialList.push(this.materialDIY);
          // }
          // this.materialDIY.supplierId=res.data.data.id;
          this.materialDIY.materialList = this.supplierMaterialForm.materialList;

          JSON.stringify(this.materialDIY);
          this.$http
            .post("/supplier/material/saveSupplierMaterial", {
              jsonStr: JSON.stringify(this.materialDIY.materialList),
              supplierId: res.data.data.id
            })
            .then(res => {});
          this.$http
            .post("/supplier/contact/saveOrUpdate", this.contactForm)
            .then(res => {});
          this.$message.success("保存成功");
          this.goback();
        }
      });
    },
    goback() {
      this.$router.push("/supplierList");
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
      this.materialFormEditable = true;
      this.$http.post("/material/paramNames").then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.searchParams = res.data.data;
        }
      });
    },
    materialSearch() {
      //   this.$http.post("/material/search", this.materialSearchForm).then(res => {
      this.$http
        .post("/materialInfo/searchMaterialList", this.materialSearchForm)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.$message.success("完成搜索");
            this.materialSearchTable = res.data.data.list;
            // for (let index = 0; index < this.materialSearchTable.length; index++) {
            //     this.materialDIYlist.push(this.materialDIY);
            // }
          }
        });
    },
    insertSupplierMaterial() {
      //点击确定后没发送请求，把dialog中的数据加到form中
      

      this.supplierMaterialTable = this.supplierMaterialForm.materialList;
      this.materialFormEditable = false;
    },
    delMaterial(row) {
      //这里也不能发请求
      //   this.$http.post("/supplier/material/delete", { id: row.id }).then(res => {
      //     if (res.data != undefined && res.data.code == 1000) {
      //       this.$message.success("删除成功");
      //       this.getData();
      //     }
      //   });
      this.supplierMaterialForm.materialList.shift(row);
    },
    handleSelectMaterial(vals) {
      vals.forEach(val => {
        val.supplierId = this.$route.query.supplierId;
        val.materialId = val.id;
      });
      //   this.supplierMaterialForm.materialList = JSON.stringify(vals);
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

