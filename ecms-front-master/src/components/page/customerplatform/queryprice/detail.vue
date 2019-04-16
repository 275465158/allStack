<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true" :model="querPrice" class="demo-form-inline">
        <el-form-item label="询价编号:">
          <el-input v-model="querPrice.queryCode" disabled></el-input>
        </el-form-item>

        <el-form-item label="供应商:">
          <el-input v-model="querPrice.supplierName" disabled></el-input>
        </el-form-item>
        <el-form-item label="要求交货日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.deliveryDate" align="right" type="date" disabled placeholder="选择日期">
            </el-date-picker>
          </div>

        </el-form-item>
        <el-form-item label="询价日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.queryDate" align="right" type="date" disabled placeholder="选择日期">
            </el-date-picker>
          </div>

        </el-form-item>
        <el-form-item label="报价截止日期:">
          <div class="block">
            <el-date-picker v-model="querPrice.offerDeadline" align="right" type="date" disabled placeholder="选择日期">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="是否含税:">
          <el-select placeholder="请选择" v-model="querPrice.taxInclude" disabled>
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品总量:">
          <el-input v-model="querPrice.productSum" disabled></el-input>
        </el-form-item>

        <el-form-item label="其他要求:">
          <el-input v-model="querPrice.otherRequire" disabled></el-input>
        </el-form-item>

      </el-form>
    </div>
    <div class="handle-box">
      <span class="el-form-item__label">收货地址</span>
    </div>
    <span class="el-form-item__label">产品清单</span>
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
      </el-table-column>
      <el-table-column prop="productStandard" label="产品标准">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="交货日期">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="预计交货日期">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="单价">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="税率">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="总价">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="detail(scope.row)">明细</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button @click="save()">保存</el-button>
    <el-button @click="goBack()">返回</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      pages: 1,
      selectProductVisible: "",
      querPrice: {
        id: "",
        queryCode: "",
        deliveryDate: new Date(),
        queryDate: new Date(),
        offerDeadline: new Date(),
        taxInclude: "",
        otherRequire: "",
        productSum: 0,
        deliveryAddress: "",
        detailAddress: "",
        pickUpByCustomer: false, //是否自提
        productList: [],
        supplierName: ""
      },
      searchType: {
        status: "",
        number: "",
        type: "",
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
      url: "/queryPrice/getById?id="
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
      this.currentId = this.$route.query.id;
      this.$http.get(this.url + this.currentId).then(res => {
        if (res != undefined && res.data.code == 1000) {
          if (res.data.data != null) {
            this.querPrice = res.data.data;
            //询价单接受到了
            //接收物料清单
            this.searchProduct.queryId = this.currentId;
            this.$http
              .post("/productList/list", this.searchProduct)
              .then(res => {
                if (res != undefined && res.data.code == 1000) {
                  console.log("res.data.data.list", res.data.data.list);
                  this.tableData = res.data.data.list;
                }
              });
          }
        }
      });
      this.$http
        .post("/productType/searchTypePage", this.searchType)
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
          // console.log("res.data.list",res.data.data.list);
          this.deliveryAddressList = res.data.data.list;
          //后台模拟专用
          this.querPrice.deliveryAddress = this.deliveryAddressList[0].address;
          this.querPrice.detailAddress = this.deliveryAddressList[0].detailAddress;
          // console.log("this.querPrice.deliveryAddress",this.querPrice.deliveryAddress);
        }
      });
    },

    addaddress() {},
    selectProduct() {
      this.selectProductVisible = true;
    },
    save() {
      console.log(
        "JSON.stringify(this.tableData)",
        JSON.stringify(this.tableData)
      );
      this.$http.post("/queryPrice/saveOrUpdate", this.querPrice).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("保存成功");
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
      if (to.path == "/detialQueryPrice") {
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
