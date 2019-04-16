<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchEnterFun" :inline="true" :model="search" class="demo-form-inline" label-width="100px">
                    <el-form-item label="物料编号:">
                        <el-input v-model="search.materialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="工厂内部编号:">
                        <el-input v-model="search.factoryMaterialCode"></el-input>
                    </el-form-item>
                    <el-form-item label="物料类型:">
                        <el-input v-model="search.type"></el-input>
                    </el-form-item>
                    <el-form-item label="原图材料:">
                        <el-input v-model="search.originalMaterial"></el-input>
                    </el-form-item>
                    <el-form-item label="参数:">
                        <el-input v-model="search.materialBomParamValueStr"></el-input>
                    </el-form-item>
                    <el-form-item label="图号:">
                        <el-input v-model="search.drawingCode"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="来源:">-->
                        <!--<el-select style="width: 200px;" v-model="search.source">-->
                            <!--<el-option label="全部" value=""></el-option>-->
                            <!--<el-option label="自制" value="自制"></el-option>-->
                            <!--<el-option label="外购" value="外购"></el-option>-->
                        <!--</el-select>-->
                    <!--</el-form-item>-->

                    <el-button round @click="searchMaterial">查询</el-button>
                    <el-button round @click="clearData">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box" style="margin-top: 10px">
                <span class="el-form-item__label">物料列表</span>
            </div>
            <div class="materialTableBox">
                <el-table v-loading="loading" :data="tableData" border style="width:100%" height="500">
                    <!--<el-table-column prop="id" label="序号" sortable>-->
                    <!--<template slot-scope="scope">-->
                    <!--{{scope.$index+1}}-->
                    <!--</template>-->
                    <!--</el-table-column>-->
                    <el-table-column label="物料编号" align="center" prop="materialCode">
                    </el-table-column>
                    <el-table-column label="工厂内部编号" align="center" prop="factoryMaterialCode">
                    </el-table-column>
                    <el-table-column label="物料类型" align="center" prop="type" width="120px">
                    </el-table-column>
                    <el-table-column label="物料名称" align="center" prop="materialName">
                    </el-table-column>
                    <el-table-column label="材料" align="center" prop="originalMaterial">
                    </el-table-column>
                    <el-table-column label="参数" align="center" prop="materialBomParamValueStr">
                    </el-table-column>
                    <!--<el-table-column label="添加时间" align="center" prop="materialBomCreated">-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="验收标准" align="center" prop="ifCheck">-->
                    <!--<template slot-scope="scope">-->
                    <!--<div v-if="scope.row.ifCheck === 0">无</div>-->
                    <!--<div v-if="scope.row.ifCheck === 1">有</div>-->
                    <!--</template>-->
                    <!--</el-table-column>-->
                    <el-table-column label="图号" align="center" prop="drawingCode">
                    </el-table-column>
                    <!--<el-table-column label="审核状态" align="center" prop="status">-->
                    <!--</el-table-column>-->
                    <el-table-column label="操作" align="center" width="80">
                        <template slot-scope="scope">
                            <el-button @click="addMerge(scope.row, scope.$index)" type="text">加入合并</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
            <div class="handle-box" style="margin-top: 10px">
                <span class="el-form-item__label">合并列表</span>
                <el-button type="text" @click="tableVisible = true" size="medium">合并</el-button>
            </div>
            <div class="mergeList">
                <el-table :data="mergeData" border style="width: 100%;">
                    <el-table-column label="物料编号" align="center" prop="materialCode">
                    </el-table-column>
                    <el-table-column label="工厂内部编号" align="center" prop="factoryMaterialCode">
                    </el-table-column>
                    <el-table-column label="物料类型" align="center" prop="type" width="120px">
                    </el-table-column>
                    <el-table-column label="物料名称" align="center" prop="materialName">
                    </el-table-column>
                    <el-table-column label="材料" align="center" prop="originalMaterial">
                    </el-table-column>
                    <el-table-column label="参数" align="center" prop="materialBomParamValueStr">
                    </el-table-column>
                    <el-table-column label="图号" align="center" prop="drawingCode">
                    </el-table-column>
                    <!--<el-table-column label="审核状态" align="center" prop="status">-->
                    <!--</el-table-column>-->
                    <el-table-column label="操作" align="center" width="80">
                        <template slot-scope="scope">
                            <el-button @click="removeMerge(scope.$index)" type="text">取消合并</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-button @click="close" type="text">返回</el-button>
            <div class="mergeList">
                <el-dialog title="合并物料" :visible.sync="tableVisible" width="60%">
                    <el-table :data="mergeData" border width="100%">
                        <el-table-column width="40">
                            <template slot-scope="scope">
                                <el-radio :label="scope.$index" @change.native="getCurrentRow(scope.row, scope.$index)" v-model="mergeRadio"></el-radio>
                            </template>
                        </el-table-column>
                        <el-table-column label="物料编号" align="center" prop="materialCode">
                        </el-table-column>
                        <el-table-column label="工厂内部编号" align="center" prop="factoryMaterialCode">
                        </el-table-column>
                        <el-table-column label="物料类型" align="center" prop="type" width="120px">
                        </el-table-column>
                        <el-table-column label="物料名称" align="center" prop="materialName">
                        </el-table-column>
                        <el-table-column label="材料" align="center" prop="originalMaterial">
                        </el-table-column>
                        <el-table-column label="参数" align="center" prop="materialBomParamValueStr">
                        </el-table-column>
                        <el-table-column label="图号" align="center" prop="drawingCode">
                        </el-table-column>
                    </el-table>
                    <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="mergeMaterial">合 并</el-button>
                    <el-button @click="tableVisible = false">关 闭</el-button>
                </span>
                </el-dialog>
            </div>

        </div>
    </div>
</template>

<script>
    export default {
        name: "materialMerge",
        data () {
          return {
              url: "/materialInfo/searchMaterialList",
              search: {
                  materialCode: '',
                  materialName:'',
                  pageNum: 1,
                  pageSize:20,
                  factoryMaterialCode: '',
                  type: '',
                  materialBomParamValueStr: '',
                  source: '',
                  drawingCode: '',
                  originalMaterial: '',
              },
              tableData: [],
              loading: false,
              pages: 1,
              mergeData: [],
              tableVisible: false,
              mergeRadio: '',
              materialId:'',
              form:{
                  id:'',
                  materialIds:[],
              }
          }
        },
        mounted () {
            this.getData()
        },
        methods: {
            getCurrentRow (row, i) {
                this.materialId = row.id
            },
            addMerge (row, i) {
                let flag = true
                for (let j = 0; j < this.mergeData.length; j++) {
                    if (row.id === this.mergeData[j].id) {
                        this.$message.warning('已存在，无需加入！')
                        flag = false
                        return
                    }
                }
                if (flag) {
                    this.mergeData.push(row)
                }
            },
            removeMerge (i) {
                this.mergeData.splice(i, 1)
            },
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            clearData() {
                (this.search.materialCode = ""),
                    (this.search.materialName = ""),
                    (this.search.factoryMaterialCode = ""),
                    (this.search.type = ""),
                    (this.search.materialBomParamValueStr = ""),
                    (this.search.source = ""),
                    (this.search.drawingCode = ""),
                    (this.search.originalMaterial = "")
            },
            searchEnterFun (e) {
                let keyCode = window.event? e.keyCode:e.which;
                if(keyCode == 13){
                    this.searchMaterial()
                }
            },
            searchMaterial(){
                this.search.pageNum = 1;
                this.getData()
            },
            mergeMaterial(){
                if (this.materialId == false) {
                    this.$message.warning('请勾选一条！')
                    return
                }
                let materialIds = []
                if (this.mergeData.length != 0){
                    for (let i=0;i<this.mergeData.length;i++){
                        if (this.materialId != this.mergeData[i].id) {
                            materialIds.push(this.mergeData[i].id)
                        }
                    }
                }
                this.form.id = this.materialId
                this.form.materialIds = materialIds
                let _this = this
                _this.loading = true
                this.$http.post("materialInfo/materialMerge",this.form).then(res=>{
                    if (res != undefined && res.data.code == 1000){
                        this.$message.success('合并成功！')
                        this.tableVisible = false
                        this.mergeData = []
                    }
                    _this.loading = false
                })
            },
            getData () {
                let _this = this
                _this.loading = true
                _this.search.source = '外购';
                this.$http.post(this.url, this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.tableData.forEach(item => {
                            item.status = item.status == 1 ? '正常' : '失效';
                        })
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    });
            },
            close(){
                this.$router.push({path: '/materialList', query: {closeFlag: 1}});
            }
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/materialMerge') {
                    this.getData();
                }
            }
        }
    }
</script>

<style>
    .handle-box .el-button--medium{
        padding: 0;
        line-height: 40px;
        box-sizing: border-box;
    }
    .mergeList .el-radio__label{
        display: none;
    }
</style>