<template>
    <div class="class">
        <div class="container">
            <div class="handle-box">
                <el-button @click="barCodePrint">打 印</el-button>
                <el-button @click="barBack">关 闭</el-button>
                <div class="print_box" id="print_box">
                    <table border="1" cellspacing="0" cellpadding="0" width="100%">
                        <caption style="font-size: 16px;font-weight: bold;margin-bottom: 20px">发料清单</caption>
                        <tr>
                            <td colspan="2" align="center" width="200">合同编号</td>
                            <td colspan="3" align="center">{{form.contractId}}</td>
                            <td colspan="2" align="center" width="200">生产单号</td>
                            <td colspan="3" align="center">{{form.productionNumber}}</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">物料名称</td>
                            <td colspan="3" align="center">{{form.materialName}}</td>
                            <td colspan="2" align="center">物料编码</td>
                            <td colspan="3" align="center">{{form.materialCode}}</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">物料类型</td>
                            <td colspan="3" align="center">{{form.number}}</td>
                            <td colspan="2" align="center">材料</td>
                            <td colspan="3" align="center">{{form.originalMaterial}}</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">规格</td>
                            <td colspan="3" align="center">{{form.materialBomParamValueStr}}</td>
                            <td colspan="2" align="center">生产数量</td>
                            <td colspan="3" align="center">{{form.materialNum}}</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">工厂内部编码</td>
                            <td colspan="3" align="center">{{form.factoryMaterialCode}}</td>
                            <td colspan="2" align="center">图号</td>
                            <td colspan="3" align="center">{{form.drawingCode}}</td>
                        </tr>
                        <tr>
                            <td align="center">序号</td>
                            <td align="center">物料编号</td>
                            <td align="center">物料名称</td>
                            <td align="center">工厂内部编号</td>
                            <td align="center">材料</td>
                            <td align="center">物料类型</td>
                            <td align="center">规格</td>
                            <td align="center">配套比例</td>
                            <td align="center">应发数量</td>
                            <td align="center">已发数量</td>
                        </tr>
                        <tr v-if="tableData.length == 0">
                            <td height="60" colspan="10" align="center">暂无数据</td>
                        </tr>
                        <tr v-else v-for="(item, index) in tableData" :key="index">
                            <td height="60" align="center">{{index + 1}}</td>
                            <td align="center">{{item.materialBomInfo.materialCode}}</td>
                            <td align="center">{{item.materialBomInfo.materialName}}</td>
                            <td align="center">{{item.materialBomInfo.factoryMaterialCode}}</td>
                            <td align="center">{{item.materialBomInfo.originalMaterial}}</td>
                            <td align="center">{{item.materialBomInfo.type}}</td>
                            <td align="center">{{item.materialBomInfo.materialBomParamValueStr}}</td>
                            <td align="center">1:{{item.quantity}}</td>
                            <td align="center">{{form.materialNum * item.quantity}}</td>
                            <td align="center">{{item.issueQty}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "print",
        data () {
          return {
              form: {},
              tableData: []
          }
        },
        mounted() {
            this.getData()
        },
        methods: {
            getData () {
                let data = JSON.parse(this.$route.query.data)
                this.form = data.form
                this.tableData = data.tableData
            },
            barCodePrint () {
                let subOutputRankPrint = document.getElementById('print_box');
                let newContent =subOutputRankPrint.innerHTML;
                let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                wind.document.body.innerHTML = newContent;
                wind.print();
                wind.location.reload();
                wind.close();
                return false;
                alert("条码打印")
            },
            barBack () {
                let row = JSON.stringify(this.form)
                this.$router.push({path: '/productionIssueInfo', query: {row, closeFlag: 1}})
            }
        },
        watch: {
            '$route' (to, path) {
                if (to.path =='/productionIssuePrint' && this.$route.query.works !== 1) {
                    this.getData()
                }
            }
        }
    }
</script>

<style scoped>

</style>