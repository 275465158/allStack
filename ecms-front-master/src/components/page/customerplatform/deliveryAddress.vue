<template>
      <div class="container">
          <div class="handle-box">
              <el-form :inline="true" :rules="rules" ref="form" :model="form" class="demo-form-inline">
                  <el-form-item label="收货地址:" prop="">
                  <area-cascader :level="1" type="text" :data="pcaa" placeholder="请选择地区" v-model="form.address"></area-cascader>
                  </el-form-item>

                  <el-row :gutter="30">
                  <el-form-item label="详细地址:" prop="detailAddress">
                      <el-input
                         type="textarea"
                            autosize
                            placeholder="请输入内容"
                            v-model="form.detailAddress"></el-input>
                  </el-form-item>
                  </el-row>     
                     <el-row :gutter="30">
                   <el-form-item label="收货人姓名:" prop="consignee">
                    <el-input v-model="form.consignee"></el-input>
                   </el-form-item>
                    </el-row> 
                   <el-row :gutter="30">
                  <el-form-item label="手机号码:" prop="phoneNumber">
                    <el-input v-model="form.phoneNumber"></el-input>
                  </el-form-item>
                   </el-row> 


              </el-form>
          </div>
          <div class="handle-box">
             
          <el-checkbox v-model="form.checked"  >设为默认收货地址</el-checkbox>
          </div>
          <el-button @click="save('form')">
            保存
          </el-button>

          <el-table :data="tableData" border style="width: 100%">
              <el-table-column prop="id" label="序号" sortable>
              </el-table-column>
              <el-table-column prop="consignee" label="收货人" >
              </el-table-column>
              <el-table-column prop="province" label="省" >
              </el-table-column>
              <el-table-column prop="city" label="市">
              </el-table-column>
              <el-table-column prop="county" label="区">
                  </el-table-column>
              <el-table-column prop="detailAddress" label="详细地址">
              </el-table-column>
              <el-table-column prop="phoneNumber" label="手机号码">
              </el-table-column>
              <el-table-column label="操作">
                  <template slot-scope="scope">
                      <el-button size="small" @click="edit(scope.row)">修改</el-button>
                      <el-button size="small" @click="del(scope.row)">删除</el-button>
                  </template>
              </el-table-column>
               <el-table-column label="默认地址">
                  <template slot-scope="scope">
                      <el-button size="small"  v-if="scope.row.flag==0"  @click="setdefault(scope.row)" >设为默认</el-button>
                      <el-button size="small" type="primary" v-else-if="scope.row.flag==1" >默认地址</el-button>
                  </template>
                
              </el-table-column>
          </el-table>

          
          <el-dialog title="修改收货地址" :visible.sync="editAddressVisible" v-if="editAddressVisible" width="80%">
           
            <el-form :inline="true" :model="editForm" ref="editForm" :rules="editRules" class="demo-form-inline">
                 <el-form-item label="收货地址:" prop="">
                  <area-cascader :level="1" type="text" :data="pcaa" placeholder="请选择地区" 
                  v-model="editForm.address"></area-cascader>
                  </el-form-item>

                  <el-row :gutter="30">
                  <el-form-item label="详细地址:" prop="detailAddress">
                      <el-input
                         type="textarea"
                            autosize
                            placeholder="请输入内容"
                            v-model="editForm.detailAddress"></el-input>
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
                  <el-checkbox v-model="editForm.checked"  >设为默认收货地址</el-checkbox>
                  </div>
         
              
            </el-form>

        <span slot="footer" class="dialog-footer">
                  <el-button @click="save('editForm')">
                保存
              </el-button>
                <el-button @click="editAddressVisible = false">取 消</el-button>
            </span>
        
        </el-dialog>
      </div>
</template>

<script>
import { pca, pcaa } from "area-data";
export default {
  data() {
    return {
      tableData: [],
      pca: pca,
      pcaa: pcaa,
      default:"",
      form: {
        phoneNumber: "",
        detailAddress: "",
        address: [],
        consignee: "",
        checked: false
      },
      editForm: {
        phoneNumber: "",
        detailAddress: "",
        address: [],
        consignee: "",
        checked: false
      },

      rules: {
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
      editRules:{
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
      pages: 1,
      editAddressVisible: "",
      querPrice: {
        deliveryDate: "",
        queryDate: "",
        offerdeadline: "",
        taxInclued: "",
        otherRequire: "",
        deliveryAddress: ""
      },
      search: {
        status: 1,
        pageNum: 1
      },
      productList: [],
      selectedProductList: [],
      options: [
        {
          label: "是",
          value: 1
        },
        {
          label: "否",
          value: 0
        }
      ]
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    handleChange(value) {},
    // 分页导航
    // 获取 easy-mock 的模拟数据
    getData() {
      this.$http.post("/deliveryAddress/list", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          // console.log("res.data.list",res.data.data.list);
          this.tableData = res.data.data.list;
        }
      });
    },
    
    save(formName) {
      console.log("this.$refs[formName].model",this.$refs[formName].model);
      
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$http
            .post("/deliveryAddress/saveOrUpdate",this.$refs[formName].model)
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
                this.$message.success("保存成功");
                this.getData();
              }
            });
        } else {
          this.$message.error("带*为必填项");
          return false;
        }
      });
    },
    edit(row){
      this.editAddressVisible=true;
       this.$http
            .post("/deliveryAddress/getInfo",{id:row.id})
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
                        // console.log("res.data.data.",res.data.data);
                this.editForm=res.data.data;
              }
            });
    },

    setdefault(row) {
       this.$http
            .post("/deliveryAddress/setDefault",{id:row.id})
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
                this.$message.success("设置成功");
                this.getData();
              }
            });
    },
    del(row) {
       this.$http
            .post("/deliveryAddress/logicalDelete",{id:row.id})
            .then(res => {
              if (res != undefined && res.data.code == 1000) {
                this.$message.success("删除成功");
                this.getData();
              }
            });
    }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
   '$route' (to, from) {
                if (to.path == '/deliveryAddress') {
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
