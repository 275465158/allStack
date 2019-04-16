<template>
<div class="container">
    <div class="handle-box">
        <el-form :inline="true" :model="search" class="demo-form-inline">
            <el-form-item label="物料编号:">
                <el-input v-model="form.materialCode" disabled></el-input>
            </el-form-item>
            <el-form-item label="物料名称:">
                <el-input v-model="form.materialName" disabled></el-input>
            </el-form-item>
            <el-form-item label="单位:">
                <el-input v-model="form.materialUnit" disabled></el-input>
            </el-form-item>
            <el-form-item label="原图材料:">
                <el-input v-model="form.originalMaterial" disabled></el-input>
            </el-form-item>
            <el-form-item label="参数:">
                <el-input v-model="form.materialParam" disabled></el-input>
            </el-form-item>
            <el-form-item label="库存数:">
                <el-input v-model="form.inventoryQty" disabled></el-input>
            </el-form-item>
            <el-form-item label="安全库存数:">
                <el-input v-model="form.inventorySafeQty" disabled></el-input>
            </el-form-item>
            <el-form-item label="订单需求:">
                <el-input v-model="form.orderNeedQty" disabled></el-input>
            </el-form-item>
        </el-form>
    </div>
    <div class="handle-box">
        <span class="el-form-item__label">物料明细列表</span>
    </div>
    <div class="handle-box">
        <el-table :data="tables" border stripe style="width: 100%">
            <el-table-column prop="id" label="序号" sortable>
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="repertoryName" label="仓库"/>
            <el-table-column prop="inventoryQty" label="数量"/>
        </el-table>
        <div class="pagination">
            <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
            </el-pagination>
        </div>
    </div>
</div>
</template>
<script>
import Qs from "qs";
export default {
    data() {
        return {
            tableData: [],
            pages:1,
            form: {
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materialUnit:"",
                materialParam: "",
                inventoryQty: "",
                inventorySafeQty: "",
                orderNeedQty:"",
            },
            search:{
                materialId:'',
                pageNum:1,
                status:1
            }
        };
    },
    created() {
        this.form = this.$route.query.row
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                return d;
            });
        }
    },
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
            this.form = this.$route.query.row
            this.search.materialId = this.$route.query.row.materialId
            //获取物料列表
            this.$http.post("/materialRepertoryDetail/getDetailList", this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                    this.pages = res.data.data.pages;
                }
            });
        },
        purchaseReasonFormat(row, column) {
            if (row.purchaseReason == 1) {
                return '数量'
            } else if (row.purchaseReason == 2) {
                return '批量'
            }
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
        '$route' (to, search) {
            if (to.path == '/materialTableInfo') {
                let newId = this.$route.query.row.materialId;
                let newForm = this.$route.query.row
                if (newId == undefined) {
                    newId = 0;
                }
                if (newForm == undefined) {
                    newForm = ""
                }
                if (this.search.materialId != newId) {
                    this.search.materialId = newId;
                    this.form = this.$route.query.row
                    this.getData();
                }
            }
        }
    }
};
</script>

