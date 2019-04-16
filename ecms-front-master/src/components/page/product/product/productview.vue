<template>
  <div class="container">
    <el-button @click="goBack">返回</el-button>
    <el-button type="primary" @click="barCodePrint">打印</el-button>
    <hr class="marginBottom">
    <div id="reportPrint">
      <table v-loading="loading" class="containerTable" id="containerTable" style="margin: 0 auto;
  width: 100%;
  border-top: 1px solid #111;
  border-left: 1px solid #111;
  border-right: 1px solid #111;text-align: center" border="1" cellspacing="0" cellpadding="0">
        <thead>
          <tr>
            <th colspan="8" style="background-color: #cccccc;
  text-align: center;
  font-size: 14px;
  height: 23px;
  border-bottom: 1px solid #111;">物料分解清单</th>
          </tr>
          <tr>
            <td class="titleFont" style="width: 12.5%;">
              <span>物料编号：</span>
            </td>
            <td class="contentFont" style="width: 18%">
              <label>{{form.materialCode}}</label>
            </td>

            <td class="titleFont" style="width: 12.5%;">
              <span>工厂物料编号：</span>
            </td>
            <td class="contentFont" style="width: 12.5%;">
              <label>{{form.factoryMaterialCode}}</label>
            </td>
            <td class="titleFont" style="width: 12.5%;">
              <span>物料类型：</span>
            </td>
            <td class="contentFont" style="width: 7%">
              <label>{{form.type}}</label>
            </td>
            <td class="titleFont" style="width: 12.5%;">
              <span>物料名称：</span>
            </td>
            <td class="contentFont" style="width: 12.5%">
              <label>{{form.materialName}}</label>
            </td>
          </tr>
          <tr>
            <td class="titleFont">
              <span>材料：</span>
            </td>
            <td class="contentFont">
              <label>{{form.originalMaterial}}</label>
            </td>
            <td class="titleFont" style="width: 120px;">
              <span>参数：</span>
            </td>
            <td class="contentFont">
              <!--<el-breadcrumb-->
                <!--separator=","-->
                <!--v-for="(item) of dynamicParameterName"-->
                <!--:key="item.id"-->
              <!--&gt;-->
                <!--<el-breadcrumb-item>{{ item.materialParamNameValue }}</el-breadcrumb-item>-->
              <!--</el-breadcrumb>-->
              {{dynamicParameterName}}
            </td>
            <td class="titleFont" style="width: 120px;">
              <span>单位：</span>
            </td>
            <td class="contentFont">
              <label>{{form.materialUnit}}</label>
            </td>
            <td class="titleFont">
              <span>制作人:</span>
            </td>
            <td class="contentFont">
              <label>{{form.author}}</label>
            </td>
          </tr>
        <tr>
          <td>
            <span>时间：</span>
          </td>
          <td colspan="contentFont">
            <label>{{form.materialBomCreated}}</label>
          </td>
          <td>
            <span>物料成本：</span>
          </td>
          <td colspan="contentFont">
            <label>{{form.materialCost}}</label>
          </td>
          <td>
            <span>加工成本：</span>
          </td>
          <td colspan="contentFont">
            <label>{{form.processCost}}</label>
          </td>
          <td>
            <span>成本总价：</span>
          </td>
          <td colspan="contentFont">
            <label>{{form.costTotal}}</label>
          </td>
        </tr>
        </thead>
        <tr>
          <td colspan="8" class="detailTitle" style="background: #cccccc;
  padding-left: 2em;">清单</td>
        </tr>
        <tbody>
          <tr>
            <td colspan="8">
              <table v-if="relationList.length !== 0" style="margin: 0 auto;
  width: 100%;
  border-top: 1px solid #111;
  border-left: 1px solid #111;
  border-right: 1px solid #111;text-align: center;font-size: 12px" cellspacing="0" cellpadding="0" border="1">
                <thead>
                <tr>
                  <td style="width: 4.4%">项次</td>
                  <td style="width: 11%">物料编号</td>
                  <td style="width: 7.7%">工厂内部编码</td>
                  <td style="width: 4.4%">物料类型</td>
                  <td style="width: 7.7%">物料名称</td>
                  <td style="width: 7.7%">材料</td>
                  <td style="width: 11%">参数</td>
                  <td style="width: 14.3%">图号</td>
                  <td style="width: 4.4%">数量</td>
                  <td style="width: 7.7%">制程</td>
                  <td style="width: 4.4%">人数</td>
                  <td style="width: 7.7%">物料单价</td>
                  <td style="width: 7.7%">加工单价</td>
                </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in relationList" style="height: 40px;">
                    <td>{{item.stepId}}</td>
                    <td>{{item.materialProcessNum}}</td>
                    <td>{{item.bomInfo.factoryMaterialCode}}</td>
                    <td>{{item.bomInfo.type}}</td>
                    <td>{{item.bomInfo.materialName}}</td>
                    <td>{{item.bomInfo.originalMaterial}}</td>
                    <td>{{item.bomInfo.materialBomParamValueStr}}</td>
                    <td>{{item.bomInfo.drawingCode}}</td>
                    <td>{{item.qty}}</td>
                    <td>{{item.madeName}}</td>
                    <td v-if="item.processContent!='undefined'">{{item.poNum}}</td>
                    <td>{{item.bomInfo.maxPrice}}</td>
                    <td>{{item.processPrice}}</td>
                  </tr>
                </tbody>
              </table>
              <p v-else style="text-align: center;height: 100px;line-height: 100px">
                暂无数据！
              </p>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      processInfo: [],
      dynamicParameterName: '',
      relationList: [],
      form: {
        id: "",
        stepId: "",
        materialCode: "",
        materialName: "",
        originalMaterial: "",
        materialUnit: "",
        checkId: "",
        drawingId: "",
        mapList: [],
        flag: "0",
        processInfo: [],
        factoryMaterialCode:"",
        materialCost: 0,
        processCost: 0,
        costTotal: 0
      },
      search: {
        id: "",
        materialCode: "",
        materialName: "",
        originalMaterial: "",
        materialBomParamValueStr: "",
        flag: 0,
        pageNum: 1,
        pageSize: 100000
      },
      madeName: '',
      manHour: '',
      poNum: '',
      processPrice:'',
      loading: false
    };
  },
  created() {
    this.getData();
  },
  computed: {},
  methods: {
    getData() {
      this.loading = true
      let _this = this
      if (this.$route.query.materialId != null) {
        this.search.id = this.$route.query.materialId;
        this.$http.post("/materialInfo/detail", this.search).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.form = res.data.data;
            if (!this.form.materialCost) {
              this.form.materialCost = 0
            }
            if (!this.form.processCost) {
              this.form.processCost = 0
            }
            this.$http
              .post("/materialInfo/expandView", this.search)
              .then(res => {
                if (res != undefined && res.data.code == 1000) {
                  this.relationList = res.data.data;
                  // this.form.materialCost
                  for (var i = 0; i < this.relationList.length; i++) {
                    if (this.relationList[i].processInfo != null) {
                      this.relationList[i].processContent = JSON.parse(this.relationList[i].processInfo.jsonParam);
                      _this.relationList[i].madeName = _this.relationList[i].processContent['制程名称']
                      _this.relationList[i].manHour = _this.relationList[i].processContent['额定工时']
                      _this.relationList[i].poNum = _this.relationList[i].processContent['人数']
                      _this.relationList[i].processPrice = this.relationList[i].processInfo.processPrice
                      if (!this.relationList[i].processPrice) {
                        this.relationList[i].processPrice = 0
                      }
                      this.form.processCost = this.form.processCost + (this.relationList[i].qty * this.relationList[i].processPrice)
                    } else {
                      if (!this.relationList[i].bomInfo.maxPrice) {
                        this.relationList[i].bomInfo.maxPrice = 0
                      }
                      this.form.materialCost = this.form.materialCost + (this.relationList[i].qty * this.relationList[i].bomInfo.maxPrice)
                    }
                    this.form.costTotal = this.form.materialCost + this.form.processCost
                  }
                }
              });
          }
          _this.loading = false
        })
                .catch(err => {
                  _this.loading = false
                })
        this.$http.post("/productParameterName/searchList").then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.paramNameList = res.data.data;
          }
        });
        this.$http
          .post("/materialBomParamName/detail", this.search)
          .then(res => {
            if (res != undefined && res.data.code == 1000) {
              this.dynamicParameterName = [];
              for (var i = 0; i < res.data.data.length; i++) {
                this.dynamicParameterName += res.data.data[i].materialParamNameValue + " , ";
              }
              if (this.dynamicParameterName.length!=0){
                this.dynamicParameterName = this.dynamicParameterName.substring(0, this.dynamicParameterName.length - 2)
              }else {
                this.dynamicParameterName = ""
              }
            }
          });
      }
    },
    goBack() {
      this.$router.push({path: "/materialList", query: {closeFlag: 1}});
    },
    barCodePrint() {
      // 打印
      window.print.portrait = false
      let subOutputRankPrint = document.getElementById("reportPrint");
      let newContent = subOutputRankPrint.innerHTML;
      let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
      wind.document.body.innerHTML = newContent;
      wind.print();
      wind.location.reload();
      wind.close();
      return false;
    }
  },
  watch: {
    $route(to, from) {
      if (to.path == "/productView") {
        this.getData();
      }
    }
  }
};
</script>

<style>
  /*@page {*/
    /*size: landscape;*/
  /*}*/
  #reportPrint {
    width: 100%;
  }
/*table.containerTable {*/
  /*margin: 0 auto;*/
  /*width: 100%;*/
  /*border-top: 1px solid #111;*/
  /*border-left: 1px solid #111;*/
  /*border-right: 1px solid #111;*/
/*}*/
/*table.containerTable td {*/
  /*border-bottom: 1px solid #111;*/
/*}*/
/*table.containerTable thead td {*/
  /*width: 12.5%;*/
/*}*/
/*table.containerTable thead td label {*/
  /*width: 25%;*/
  /*word-break: break-all;*/
  /*word-wrap: none;*/
/*}*/
/*table.containerTable td.detailTitle {*/
  /*background: #cccccc;*/
  /*padding-left: 2em;*/
/*}*/
/*table.containerTable tbody td {*/
  /*width: 50%;*/
/*}*/
/*table.containerTable thead th {*/
  /*background-color: #cccccc;*/
  /*text-align: center;*/
  /*font-size: 14px;*/
  /*height: 23px;*/
  /*border-bottom: 1px solid #111;*/
/*}*/
table.detailTable thead th {
  background-color: #eee;
  word-wrap: none;
  word-break: keep-all;
}
/*th.tlabel-xh {*/
  /*width: 5%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-wlbh {*/
  /*width: 20%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-wlmc {*/
  /*width: 30%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-sl {*/
  /*width: 10%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-zc {*/
  /*width: 15%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-rs {*/
  /*width: 10%;*/
  /*background-color: #fff !important;*/
/*}*/
/*th.tlabel-sb {*/
  /*width: 10%;*/
  /*background-color: #fff !important;*/
/*}*/
/*table.detailTable tbody td {*/
  /*word-wrap: none;*/
  /*word-break: keep-all;*/
  /*text-align: center;*/
/*}*/
/*table.processTable tbody td {*/
  /*width: 33%;*/
/*}*/
/*td.titleFont {*/
  /*text-align: right;*/
  /*width: 12.5%;*/
  /*height: 100%;*/
  /*background-color: #eeeeee;*/
/*}*/
/*td.contentFont {*/
  /*padding-left: 8px;*/
/*}*/
/*table.containerTable thead tr:nth-child(2) td, table.containerTable thead tr:nth-child(3) td{*/
  /*border-right: 1px solid black;*/
/*}*/
/*.marginBottom {*/
  /*margin-top: 5px;*/
  /*margin-bottom: 10px;*/
/*}*/
</style>
