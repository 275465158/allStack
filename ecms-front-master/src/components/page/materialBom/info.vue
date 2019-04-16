<template>
<div class="container">
    <el-form :model="form" ref="form" label-width="100px">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="物料名称:">
              <el-input v-model="form.materielName" disabled></el-input>
          </el-form-item> 
        </el-col>
        <el-col :span="12">
          <el-form-item label="物料编码:">
              <el-input v-model="form.materielCode" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="原图材料:">
                <el-input v-model="form.originalMaterial" disabled></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item  v-for="(project,index) in this.materialParamList" :key="index" :offset="1" :label="project.parameterName==''?('参数'+(index+1)+'：'):project.parameterName">
              <el-input v-model="project.parameterValue" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
        <div class="handle-box">
            <span class="el-form-item__label">物料列表</span>
        </div>
        <div class="handle-box">
            <el-table :data="tables" border style="width: 100%">
                <!--<el-table-column prop="id" label="序号" sortable/>-->
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="repertoryName" label="仓库名称"/>
                <el-table-column prop="totalOnhand" label="库存数量"/>
            </el-table>
        </div>
    </el-form>
    <span class="dialog-footer">
        <el-button @click="goBack">返回</el-button>
    </span>
</div>
</template>
<script>
export default {
    data() {
        return {
            url:"/materiel/getMaterialGroupByRepertory",
            tableData: [],
            form: {
                originalMaterial:'',
                materielName:'',
                materielCode:'',
                status: 1,
                pageNum: 1
            },
            materialParamList:[],
            pages: 1,
        };
    },
    created() {
        this.form.materielName = this.$route.query.row.materialName
        this.form.materielCode = this.$route.query.row.materialCode
        this.form.originalMaterial = this.$route.query.row.originalMaterial
        this.materialParamList = this.$route.query.row.materialParamList
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
        handleChange(value) {
        },
        // 获取 easy-mock 的模拟数据
        getData() {
            this.$http.post(this.url,this.form).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                    this.pages = res.data.data.pages;
                }
            });
        },
        goBack() {
            this.$router.push({
                path: "/materialInfoList"
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

