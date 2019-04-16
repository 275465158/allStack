<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true" :model="querPrice" class="demo-form-inline">
        <el-form-item label="询价编号:">
          <el-input v-model="querPrice.queryCode"></el-input>
        </el-form-item>

        <!-- <el-form-item label="供应商:">
          <el-input v-model="querPrice.supplierName"></el-input>
        </el-form-item> -->
        <el-form-item label="要求交货日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.deliveryDate" align="right" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>

        </el-form-item>
        <el-form-item label="询价日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.queryDate" align="right" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>

        </el-form-item>
        <el-form-item label="报价截止日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.offerDeadline" align="right" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="是否含税:">
          <el-select placeholder="请选择" v-model="querPrice.taxInclude">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品总量:">
          <el-input v-model="querPrice.productSum"></el-input>
        </el-form-item>

        <el-form-item label="其他要求:">
          <el-input v-model="querPrice.otherRequire"></el-input>
        </el-form-item>

      </el-form>
    </div>
    <div class="handle-box">
      <span class="el-form-item__label">选择收货地址</span>
      <el-radio-group v-model="querPrice.addressId" @change="toggleDeliveryBySelf">
        <el-card class="box-card" v-for="o in spliceAddress" :key="o.id">
          <div slot="header" class="clearfix">
            <el-radio-button :label="o.id" border>{{o.province+o.city+o.county}}</el-radio-button>
          </div>
        </el-card>
      </el-radio-group>
      <br/>
      <el-button round type="primary" @click="addAddress">使用新的地址</el-button>
      <el-checkbox v-model="querPrice.pickUpByCustomer" @change="deliveryBySelf()">自提</el-checkbox>
    </div>

    <span class="el-form-item__label">产品清单</span>
    <el-button @click="selectProduct">
      选择产品
    </el-button>

    <el-dialog title="选择产品" :visible.sync="selectProductVisible" v-if="selectProductVisible" width="80%">

      <template>
        <el-tabs v-model="querPrice.productStandard" @tab-click="handleClick">
          <el-tab-pane label="标准产品" name="标准产品">
            <el-form :inline="true" :model="search" class="demo-form-inline">
              <el-form-item label="产品类型:">
                <el-select v-model="search.type" placeholder="请选择">
                  <el-option v-for="(item, index) in productTypeOptions" :key="index" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="产品名称:">
                <el-input v-model="search.materialName"></el-input>
              </el-form-item>
              <el-form-item label="参数:">
                <el-input v-model="search.materialBomParamValueStr"></el-input>
              </el-form-item>
              <el-button round @click="selectLike">查询</el-button>
            </el-form>

            <el-table :data="productList" border style="width:100%" height="300" @selection-change="handleSelect">
              <el-table-column label="产品类型" prop="materialCode"></el-table-column>
              <el-table-column label="产品名称" prop="materialName"></el-table-column>
              <el-table-column label="参数" prop="materialBomParamValueStr"> -->
              </el-table-column>
              <el-table-column label="单位" prop="materialUnit"></el-table-column>
              <el-table-column type="selection" label="操作">
              </el-table-column>
            </el-table>
            <div class="pagination">
              <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
              </el-pagination>
            </div>
            <template>
              <!-- <span slot="footer" class="dialog-footer"> -->
              <el-button @click="addproduct">确 定</el-button>
              <el-button @click="selectProductVisible = false">取 消</el-button>
              <!-- </span> -->
            </template>
          </el-tab-pane>
          <el-tab-pane label="定制产品" name="定制产品">配置管理</el-tab-pane>
        </el-tabs>
      </template>

    </el-dialog>
    <el-dialog title="添加收货地址" :visible.sync="addAddressVisible" v-if="addAddressVisible" width="80%">

      <el-form :inline="true" :model="editForm" ref="editForm" :rules="editRules" class="demo-form-inline">
        <el-form-item label="收货地址:" prop="">
          <area-cascader :level="1" type="text" :data="pcaa" placeholder="请选择地区" v-model="editForm.address"></area-cascader>
        </el-form-item>

        <el-row :gutter="30">
          <el-form-item label="详细地址:" prop="detailAddress">
            <el-input type="textarea" autosize placeholder="请输入内容" v-model="editForm.detailAddress"></el-input>
          </el-form-item>
        </el-row>
        <el-row :gutter="30">
          <el-form-item label="收货人姓名:" prop="consignee">
            <el-input v-model="editForm.consignee"></el-input>
          </el-form-item>
        </el-row>
        <el-row :gutter="30">
          <el-form-item label="手机号码:" prop="phoneNumber">
            <el-input v-model="editForm.phoneNumber"></el-input>
          </el-form-item>
        </el-row>

        <div class="handle-box">
          <el-checkbox v-model="editForm.checked">设为默认收货地址</el-checkbox>
        </div>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="save('editForm')">
          保存
        </el-button>
        <el-button @click="editAddressVisible = false">取 消</el-button>
      </span>

    </el-dialog>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="序号" sortable>
      </el-table-column>
      <el-table-column prop="type" label="产品类型">
      </el-table-column>
      <el-table-column prop="materialName" label="产品名称">
      </el-table-column>
      <el-table-column prop="materialUnit" label="单位">
      </el-table-column>
      <el-table-column prop="purchaseNumber" label="采购数量">
        <template slot-scope="scope">
          <el-input type="number" v-model.number="scope.row.purchaseNumber" :min="0" v-on:input="recompute()"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="productStandard" label="产品标准">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="交货日期">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.deliveryDate" align="right" type="date" placeholder="选择日期">
          </el-date-picker>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="detail(scope.row)">明细</el-button>
          <el-button size="small" @click="del(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button @click="save()">保存</el-button>
    <el-button @click="goBack()">返回</el-button>
  </div>
</template>

<script>
import { pca, pcaa } from "area-data";
export default {
  data() {
    return {
      tableData: [],
      pages: 1,
      selectProductVisible: "",
      addAddressVisible: "",
      querPrice: {
        id: "",
        queryCode: "",
        deliveryDate: new Date(),
        queryDate: new Date(),
        offerDeadline: new Date(),
        taxInclude: "",
        productStandard: "标准产品",
        otherRequire: "",
        productSum: 0,
        deliveryAddress: "",
        detailAddress: "",
        addressId: "",
        pickUpByCustomer: false, //是否自提
        productList: []
        // supplierName: ""
      },
      searchType: {
        status: 1,
        // number: "",
        // type: "",
        pageNum: 1,
        pageSize: 20
      },
      search: {
        type: "",
        materialName: "",
        materialBomParamValueStr: "",
        flag: 1,
        status: 1,
        pageNum: 1,
        pageSize: 20
      },
      searchProduct: {
        queryId: 0,
        flag: 1,
        status: 1,
        pageNum: 1,
        pageSize: 20
      },
      productList: [],
      searchAddress: {
        status: 1,
        pageNum: 1
      },
      editRules: {
        detailAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" }
        ],
        consignee: [
          { required: true, message: "请输入收货人名称", trigger: "blur" }
        ],
        phoneNumber: [
          { required: true, message: "请输入手机号码", trigger: "blur" }
        ]
      },
      editForm: {
        phoneNumber: "",
        detailAddress: "",
        address: [],
        consignee: "",
        checked: false
      },
      selectedProductList: [],
      deliveryAddressList: [],
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
      activeIndex: "1",
      productTypeOptions: [],
      url: "/queryPrice/getById?id=",
      spliceAddress: [],
      meiyong: ""
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    toggleDeliveryBySelf() {
      if (this.querPrice.pickUpByCustomer === true) {
        this.querPrice.pickUpByCustomer = false;
      }
    },
    deliveryBySelf() {
      if (this.querPrice.pickUpByCustomer === true) {
        // console.log("--------");
        this.querPrice.addressId = "";
      }
    },
    handleChange(value) {},
    handleClick() {},

    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    recompute() {
      let number = 0;
      this.tableData.forEach(item => {
        number = parseFloat(item.purchaseNumber) + number;
      });
      this.querPrice.productSum = number;
    },
    goBack() {
      this.$router.push({
        path: "/queryprice"
      });
    },
    selectLike() {
      this.$http
        .post("/queryPrice/searchProductList", this.search)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.productList = res.data.data.list;
          }
        });
    },
    addproduct() {
      for (let index = 0; index < this.selectedProductList.length; index++) {
        this.selectedProductList[index].productStandard = "标准产品";
        this.selectedProductList[index].deliveryDate = new Date();
        this.tableData.push(this.selectedProductList[index]);
      }
      this.selectProductVisible = false;
      this.querPrice.productList = this.tableData;
    },
    handleSelect(vals) {
      this.selectedProductList = vals;
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http
        .post("/productType/searchTypeForQuery", this.searchType)
        .then(res => {
          if (res != undefined && res.data.code == 1000) {
            for (let index = 0; index < res.data.data.list.length; index++) {
              let typeOption = {
                label: "",
                value: ""
              };
              typeOption.label = res.data.data.list[index].type;
              typeOption.value = res.data.data.list[index].number;
              this.productTypeOptions.push(typeOption);
            }
          }
        });
      this.$http.post("/deliveryAddress/list", this.searchAddress).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.deliveryAddressList = res.data.data.list;
          this.spliceAddress = [];
          // console.log("this.spliceAddress1", this.spliceAddress);
          for (let index = 0; index < res.data.data.list.length; index++) {
            let spliceAddressinfo = res.data.data.list[index];
            if (spliceAddressinfo.flag === 1) {
              this.querPrice.addressId = spliceAddressinfo.id;
            }
            this.spliceAddress.push(spliceAddressinfo);
          }
          // this.querPrice.deliveryAddress = this.deliveryAddressList[0].address;
          // this.querPrice.detailAddress = this.deliveryAddressList[0].detailAddress;
          // console.log("this.spliceAddress2", this.spliceAddress);
        }
      });
    },

    addAddress() {
      this.addAddressVisible = true;
    },
    selectProduct() {
      this.selectProductVisible = true;
    },
    save() {
      // console.log(
      //   "JSON.stringify(this.tableData)",
      //   JSON.stringify(this.tableData)
      // );
      this.$http.post("/queryPrice/saveOrUpdate", this.querPrice).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("保存成功");
          goBack();
        }
      });
    },

    del(index) {
      this.tableData.splice(index, 1);
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
    $route(to, from) {
      if (to.path == "/addqueryprice") {
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
.box-card {
  width: 150px;
  display: inline-block;
}
</style>
