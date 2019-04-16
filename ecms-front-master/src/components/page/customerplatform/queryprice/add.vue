<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true" :model="querPrice" class="demo-form-inline">
        <el-form-item label="询价编号:">
          <el-input v-model="querPrice.queryCode"></el-input>
        </el-form-item>

        <el-form-item label="供应商:">
          <el-input v-model="querPrice.supplierName"></el-input>
        </el-form-item>
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
      <el-button round type="primary" @click="addaddress">使用新的地址</el-button>
      <el-checkbox v-model="querPrice.deliveryAddress">自提</el-checkbox>
    </div>
    <span class="el-form-item__label">产品清单</span>
    <el-button @click="selectProduct">
      选择产品
    </el-button>

    <el-dialog title="选择产品" :visible.sync="selectProductVisible" v-if="selectProductVisible" width="80%">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="1">标准产品</el-menu-item>
        <el-menu-item index="2">定制产品</el-menu-item>
      </el-menu>
      <el-form :inline="true" :model="search" class="demo-form-inline">
        <el-form-item label="产品类型:">
          <el-select v-model="search.type" placeholder="请选择">
            <el-option v-for="item in productTypeOptions" :key="item.value" :label="item.label" :value="item.value">
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
        <el-table-column label="参数" prop="materialBomParamValueStr">
          <!-- <template slot-scope="scope">
                        <el-input type="number" v-model.number="scope.row.productNumber" :min="0" v-on:input="sumNumPrice(scope.row)"></el-input>
                    </template> -->
        </el-table-column>
        <el-table-column label="单位" prop="materialUnit"></el-table-column>
        <el-table-column type="selection" label="操作">
          <!-- <template slot-scope="scope">
                    <el-button>加入产品清单</el-button>
                    </template> -->
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
        </el-pagination>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addproduct">确 定</el-button>
        <el-button @click="selectProductVisible = false">取 消</el-button>
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
          <el-input type="number" v-model.number="scope.row.purchasNumber" :min="0" v-on:input="sumNumPrice(scope.row)"></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="pruductStandard" label="产品标准">
      </el-table-column>
      <el-table-column prop="deliveryDate" label="交货日期">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.purchasNumber" align="right" type="date" placeholder="选择日期">
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
        deliveryDate: "",
        queryDate: "",
        offerDeadline: "",
        taxInclude: "",
        otherRequire: "",
        productSum:0,
        deliveryAddress: "",
        pickUpByCustomer: false, //是否自提
        productList:[],
        supplierName:"",
      },
      searchType: {
        status: "",
        number: "",
        type: "",
        pageNum: 1,
        pageSize: 20
      },
       search: {
        type:"",
        materialName:"",
        materialBomParamValueStr:"",
        flag:1,
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
      deliveryAddressList:[],
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
      productTypeOptions:[],
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
    selectLike() {
      this.$http.post("/queryPrice/searchProductList", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.productList=res.data.data.list;
        }
      });
    },
    addproduct() {
      for (let index = 0; index < this.selectedProductList.length; index++) {
          this.tableData.push(this.selectedProductList[index]);
      }
      this.selectProductVisible=false;
      this.querPrice.productList=this.tableData;
      
      // console.log('this.tableData',this.tableData);
    },
    handleSelect(vals) {
      this.selectedProductList = vals;
    },
    // 获取 easy-mock 的模拟数据
    getData() {
            this.$http.post("/productType/searchTypePage", this.searchType).then(res => {
        if (res != undefined && res.data.code == 1000) {
          for (let index = 0; index < res.data.data.list.length; index++) {
            let typeOption={
              label:"",
              value:""
            };
            typeOption.label=res.data.data.list[index].type;
            typeOption.value=res.data.data.list[index].number;
            this.productTypeOptions.push(typeOption);
          }
        }
      });

      this.$http.post("/deliveryAddress/list", this.searchAddress).then(res => {
        if (res != undefined && res.data.code == 1000) {
          console.log("res.data.list",res.data.data.list);
          this.deliveryAddressList = res.data.data.list;
          //后台模拟专用
          this.querPrice.deliveryAddress=this.deliveryAddressList[0].address;
          // console.log("this.querPrice.deliveryAddress",this.querPrice.deliveryAddress);

        }
      });

       this.$http.post("/company/get").then(res => {
        if (res != undefined && res.data.code == 1000) {
          // console.log("res.data",res.data.data);
          this.querPrice.supplierName=res.data.data.name;
        }
      });
    },
    addaddress() {},
    selectProduct() {
      this.selectProductVisible = true;
    },
    save() {
      // console.log('JSON.stringify(this.tableData)',JSON.stringify(this.tableData));
      this.$http.post("/queryPrice/saveOrUpdate", this.querPrice,).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success('保存成功');
        }
      });
    },
    // add() {
    //   this.$router.push("/repertoryInfo");
    // },
    detail() {
    },

    del(index) {
      this.tableData.splice(index,1);
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
</style>
