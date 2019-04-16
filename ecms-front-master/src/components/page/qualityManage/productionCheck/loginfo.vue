<template>
    <div class="container">
        <div clas="handle-box clear">
            <el-form ref="form" class="clear" :model="form" label-width="140px">
                <el-col :span="8">
                    <el-form-item label="生产检测单号:">
                        <el-input v-model="form.checkNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="合同编号:">
                        <el-input v-model="form.productBatch" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="生产单号:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="工厂物料编码:">
                        <el-input v-model="form.factoryMaterialCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="生产时间:">
                        <el-input v-model="form.produceDate" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="产品编号:">
                        <el-input v-model="form.materialCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="产品名称:">
                        <el-input v-model="form.materialName" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="材料:">
                        <el-input v-model="form.originalMaterial" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="参数:">
                        <el-input v-model="form.materialBomParamValueStr" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="数量:">
                        <el-input v-model="form.materialNum" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="检验比例(%):">
                        <el-input v-model="form.checkPercent" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="检验数量:">
                        <el-input v-model="form.checkNum" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="合格数:">
                        <el-input v-model="form.qualifiedNum" id="qualifiedQty" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="不合格数:">
                        <el-input v-model="form.unQualifiedNum" id="unQualifiedQty" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="检测合格率(%):">
                        <el-input v-model="form.qualifiedPercent" id="qualifiedPercent" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="附件:">
                        <el-input v-model="form.fileUrl" readonly></el-input>
                        <el-button type="text" v-if="form.fileUrl != ''" @click="downloadFile(form.fileUrl)">下载</el-button>
                    </el-form-item>
                </el-col>
            </el-form>
            <div class="handle-box clear">
                <span class="el-form-item__label">检验标准:</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column prop="checkName" label="检测名称">
                </el-table-column>
                <el-table-column prop="checkMethod" label="检测方式">
                </el-table-column>
                <el-table-column prop="equipmentName" label="检测设备">
                </el-table-column>
                <el-table-column prop="equipmentSpecification" label="设备规格">
                </el-table-column>
                <el-table-column prop="unit" label="单位">
                </el-table-column>
                <el-table-column prop="minValue" label="标准下限值">
                </el-table-column>
                <el-table-column prop="maxValue" label="标准上限值">
                </el-table-column>
            </el-table>
            <div>
                <div class="handle-box clear">
                    <span class="el-form-item__label">检验记录:</span>
                </div>
                <el-table :data="tableData2" border style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column :key="index" v-for="(item, index) in tableData2Label" :prop="'key' + (index + 1)" :label="item['key' + (index + 1)]">
                        <template slot-scope="scope">
                            <el-input readonly v-model="scope.row['key' + (index + 1)]" type="text"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="外观">
                        <template slot-scope="scope">
                            <el-radio readonly v-model="scope.row.radio" label="1">合格</el-radio>
                            <el-radio readonly v-model="scope.row.radio" label="2">不合格</el-radio>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-button @click="goBack">取 消</el-button>
        </div>
</div>
</template>
<script>
    import * as utils from '../../../../../static/js/utils.js';
    import fileDownload from 'js-file-download';
export default {
  data() {
      return {
          tableData: [],
          url: '/productionCheckLog/getByCheckId',
          checkUrl:'/productAcceptanceDetail/searchAcceptanceDetail',
          form: {
              id:'',
              materialId:'',
          },
          file:'',
          item : [],
          unqualifiedTypeFormList:[],
          contentJson: {},
          tableData2: [],
          tableData2Label: []
      };
  },
  created() {
      this.getData();
  },
   computed: {
       tables(){
           return this.tableData.filter((d) => {
               return d;
           })
       }
   },
    methods: {
        // 获取待检测列表数据
        getData() {
            if(this.$route.query.row.id){
                this.form.id = this.$route.query.row.id
            }
            this.form.materialId = this.$route.query.row.materialId
            this.$http.post(this.url, {id: this.form.id}).then((res) => {
                console.log(res);
                if (res != undefined && res.data.code == 1000) {
                    this.form = res.data.data;
                }
            })
            this.$http.post(this.checkUrl, {materialId: this.form.materialId}).then((res) => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data;
                }
            })
            this.$http.post("checkLog/searchById", {id: this.form.id,checkFlag:1}).then((res) => {
                if (res != undefined && res.data.code == 1000) {
                res.data.data.forEach((item, index) => {
                    this.tableData2.push(JSON.parse(item.checkResult))
                })
            }
            this.$http.post("productAcceptance/searchAccpetDetailByMaterialId", {materialId: this.form.materialId}).then((res) => {
                if (res != undefined && res.data.code == 1000) {
                    let obj = JSON.parse(res.data.data.contentJson);
                let i = 0;
                    for (let key in obj) {
                        i++
                        this.contentJson['key' + i] = ''
                        let o = {}
                        o['key' + i] = key
                        this.tableData2Label.push(o)
                    }
                }
            })
        })
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        goBack(){
            this.$router.push({ path: '/productionRecord'})
        },
        downloadFile(path) {
            let _this = this;
            let obj = this.form;
            obj.load = 0;
            obj.progress = '';
            obj.show = true;
            let schedule = setInterval(function () {
                    obj.load > 97 ? obj.load += 0 : obj.load += 1
                }
                , 100);
            this.$http.post('/productDrawing/download', {filePath: path}, {responseType: 'arraybuffer'}).then((res) => {
                fileDownload(res.data, utils.getFileName(path));
            obj.load = 100;
            clearInterval(schedule);
            obj.progress = 'success'
        })
        .catch(err => {
                clearInterval(schedule);
            obj.progress = 'exception'
        })
        },
    },
    watch: {
        '$route' (to, form) {
            if (to.path == '/productionLogInfo') {
                this.tableData2 = []
                this.tableData2Label = []
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

</style>
