<template>
<div class="container">
    <el-form :model="form" ref="form" :rules="rules" label-width="100px">
      <el-input type="hidden" v-model="form.status" value="1"></el-input>
      <el-input type="hidden" v-model="form.companyId"></el-input>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-form-item label="物料编号:">
              <el-input v-model="materialBom.materialCode" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="物料名称:">
              <el-input v-model="materialBom.materialName" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="单位:">
              <el-input v-model="materialBom.materialUnit" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="原图材料:">
              <el-input v-model="materialBom.originalMaterial" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    <el-row>
        <el-col :span="5">
            <el-form-item  v-for="(project,index) in materialBom.materialParameters" :key="index" :offset="1" :label="project.parameterName==''?('参数'+(index+1)):project.parameterName">
                <el-input v-model="project.parameterValue" disabled></el-input>
            </el-form-item>
        </el-col>
    </el-row>
    <el-row :gutter="30">
        <!--<el-col :span="6">
            <el-form-item label="待收货数:">
                <el-input v-model="form.onhand1" disabled></el-input>
            </el-form-item>
        </el-col>-->
        <el-col :span="6">
            <el-form-item label="待入库数:">
                <el-input v-model="form.stayOnhandQty" disabled></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item label="库存数:">
                <el-input v-model="form.onhand" disabled></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item label="安全库存数:">
                <el-input v-model="form.safeStock" disabled></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item label="订单需求:">
                <el-input v-model="form.requiredByOrders" disabled></el-input>
            </el-form-item>
        </el-col>
    </el-row>
        <div class="handle-box">
            <span class="el-form-item__label">物料明细列表</span>
        </div>
        <div class="handle-box">
            <el-table :data="inventoryDetail" border stripe style="width: 100%">
                <el-table-column prop="id" label="序号" sortable/>
                <el-table-column prop="shelfPosition" label="库存位置"/>
                <el-table-column prop="supplier.supplierName" label="供应商"/>
                <el-table-column prop="entryDetail.arrivedBatch" label="物料批次"/>
                <el-table-column prop="purchaseCause" label="采购原因"/>
                <el-table-column prop="entryDetail.arrivedDate" label="来料时间"/>
                <el-table-column prop="entryDetail.entryDate" label="入库时间"/>
                <el-table-column prop="entryDetail.consignee" label="收货人"/>
                <el-table-column prop="qty" label="数量"/>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </el-form>
</div>
</template>
<script>
import Qs from "qs";
export default {
    data() {
        return {
            form: {
                id: "",
                onhand: 0,
                safeStock: 0,
                minPurchaseNum: 0,
                requiredByOrders: 0,
                purchaseType: "",
                materielUnit: "",
                repertoryId: 0,
                stayOnhandQty:0,
                status: 1
            },
            materialBom:{
                materialCode:"",
                materialName:"",
                materialUnit:"",
                originalMaterial:"",
                materialParameters:[]
            },
            materialCode:'',
            searchParams: [],
            repertoryId: "",
            rules: {},
            pages: 1,
            search: {
                pageNum: 1,
                materielId:''
            },
            entrySearch:{
                pageNum: 1,
                materialId:'',
                repertoryId:''
            },
            entryList:[],
            formData:[],
            purchaseType: ["数量", "批量"],
            inventoryDetail: [],
            inventoryTotal: 0
        };
    },
    created() {
        this.form.safeStock = this.$route.query.row.safeStock
        this.form.requiredByOrders = this.$route.query.row.requiredByOrders
        this.form.onhand = this.$route.query.row.onhand
        this.getData();
    },
    computed: {},
    methods: {
        handleChange(value) {},
        handleSelect(item) {
        },
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        // 获取 easy-mock 的模拟数据
        getData() {
            if (this.$route.query.repertoryId != null) {
                if (this.$route.query.row.materialBom.id != null) {
                    this.search.materielId = this.$route.query.row.materialBom.id
                    this.entrySearch.materialId = this.$route.query.row.materialBom.id
                    this.entrySearch.repertoryId = this.$route.query.repertoryId
                    this.$http.post("/materiel/info", {id: this.$route.query.row.materialBom.id}).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.materialBom = res.data.data.materialBom;
                        }
                    });
                    this.$http.post("/materiel/inventory/list", this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.inventoryDetail = res.data.data.list;
                        }
                    });
                    //获取待入库总数
                    this.$http.post("/materiel/entry/totalList", this.entrySearch).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.entryList = res.data.data;
                            var stayOnhandQty = 0;
                            for (var i=0;i<this.entryList.length;i++){
                                stayOnhandQty += this.entryList[i].qty
                            }
                            this.form.stayOnhandQty = stayOnhandQty;
                        }
                    });
                } else {
                }
            } else {
                this.$router.push("/repertoryList");
            }
        },
        save(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.form.materialBom = JSON.stringify(this.form.materialBom);
                    this.$http.post("/materiel/saveOrUpdate", this.form).then(res => {
                        if (res.data.code == 1000) {
                            this.$message.success("保存成功");
                        }
                    });
                } else {
                    this.$message.error("带*为必填项");
                    return false;
                }
            });
        },
        goBack() {
            this.$router.push({
                path: "/materielList",
                query: { repertoryId: this.$route.query.repertoryId }
            });
        }
    },
    mounted() {},
    watch: {
        // 如果路由发生变化，再次执行该方法
        $route: "getData"
    }
};
</script>

