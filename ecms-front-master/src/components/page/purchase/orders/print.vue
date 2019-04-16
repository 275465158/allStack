<template>
    <div class="container">
        <div class="purchaseOrderPrint">
            <el-row>
                <el-button @click.native="onPring">打印</el-button>
                <el-button @click.native="goBack">关闭</el-button>
            </el-row>
            <div id="subOutputRank-print">
            <div class="title" style="margin-top: 10px;display: flex;">
                <div style="flex: 1;">
                    <img class="logo" style="width: 90px;height: 90px;border: none;" v-if="imgUrl != 'data:image/png;base64,'" :src="imgUrl" />
                </div>
                <div style="width: 70%;text-align: center;">
                    采购订单
                </div>
                <div style="text-align: left;flex: 2;">订单号：{{purchaseCode}}</div>
            </div>
            <div>
                <table style="width:100%;margin-top: 10px;font-size: 12px;
        text-align: center;
        border-right:1px solid black;border-bottom:1px solid black" border="0" cellspacing="0" cellpadding="0">
                    <!--<thead>-->
                        <tr>
                            <td colspan="8" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;height: 24px;">采购信息</td>
                        </tr>
                    <!--</thead>-->
                    <!--<tbody>-->
                    <tr>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%;height: 24px;">是否含税：</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%;">{{pruchaseForm.ifTax}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">是否含运费：</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">{{pruchaseForm.ifFreight}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">运费：</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">{{pruchaseForm.freight}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">采购单总价：</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;width: 12.5%">{{pruchaseForm.subtotal.toFixed(2)}}</td>
                    </tr>
                    <!--</tbody>-->
                </table>
                <table width="100%" align="center" style="width: 100%;font-size: 12px;
        text-align: center;
        border-right:1px solid black;" class="table"  border="0" cellspacing="0" cellpadding="0">
                    <!--<thead>-->
                    <tr style="font-size: 12px;text-align: left;">
                        <td style="padding-left: 5px;border-left:1px solid black;height: 24px;width:3%">序号</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 11%;">物料编号</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 11%;">物料名称</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 12%;">材料</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 10%;">参数</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 12%;">图号</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 3%;">单位</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 5%;">数量</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 5%;">单价</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 3%;">单重</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 6%;">金额小计</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 8%;">交货日期</td>
                        <td style="padding-left: 5px;border-left:1px solid black;width: 11%;">采购原因</td>
                    </tr>
                    <!--</thead>-->
                    <!--<tbody>-->
                        <tr v-for="(item, index) in order" style="font-size: 12px;text-align: left">
                            <td height="24" style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black;">{{index+1}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.materialCode}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.materialName}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.originalMaterial}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.materialBomParamValueStr}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.drawingCode}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.unit}}</td>
                            <td style="padding-right: 5px;text-align: right;border-left:1px solid black;border-top:1px solid black">{{item.purchaseQty}}</td>
                            <td style="padding-right: 5px;text-align: right;border-left:1px solid black;border-top:1px solid black">{{item.purchasePrice.toFixed(2)}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.weight}}</td>
                            <td style="padding-right: 5px;text-align: right;border-left:1px solid black;border-top:1px solid black">{{item.purchasePriceTotal.toFixed(2)}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.arriveDate}}</td>
                            <td style="padding-left: 5px;border-left:1px solid black;border-top:1px solid black">{{item.purchaseReason}}</td>
                        </tr>
                        <tr>
                            <td height="24" colspan="4" align="center" style="border-left:1px solid black;border-top:1px solid black;">产品总价</td>
                            <td height="24" colspan="9" align="center" style="padding-left: 10px;border-left:1px solid black;border-top:1px solid black;text-align: left">{{subtotal}}</td>
                        </tr>
                        <tr>
                            <td height="24" colspan="2" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">备注</td>
                            <td colspan="11" style="padding-left: 10px;border-left:1px solid black;border-top:1px solid black;text-align: left">{{pruchaseForm.remark}}</td>
                        </tr>
                    <!--</tbody>-->
                </table>
                <table style="width:100%;font-size: 12px;text-align: center;border-right:1px solid black;border-bottom:1px solid black;border-top: 1px solid black" border="0" cellspacing="0" cellpadding="0">
                    <!--<thead>-->
                    <tr>
                        <td colspan="2" style="background-color: #DCDCDC;border-left:1px solid black;height: 24px;">供应方信息</td>
                        <td colspan="2" style="background-color: #DCDCDC;border-left:1px solid black;height: 24px;">需方信息</td>
                        <td colspan="2" style="background-color: #DCDCDC;border-left:1px solid black;height: 24px;">物流信息</td>
                    </tr>
                    <!--</thead>-->
                    <!--<tbody>-->
                    <tr>
                        <td height="24" width="10%" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">供方名称</td>
                        <td width="23.3%" style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierName}}</td>
                        <td width="10%" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">需方名称</td>
                        <td width="23.3%" style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.name}}</td>
                        <td width="10%" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">送货方式</td>
                        <td width="23.3%" style="border-left:1px solid black;border-top:1px solid black;width: 300px;">{{logisticeForm.ifPickUp}}</td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">地址</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.address}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">地址</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.address}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">收货人</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{logisticeForm.consignee}}</td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">联系人</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierContact}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">采购人</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.pruchase}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">收货地址</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{logisticeForm.address}}</td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">电话号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierNumber}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">电话号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.purchasePhoneNumber}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">手机号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{logisticeForm.phoneNumber}}</td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">手机号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierPhone}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">手机号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.purchasePhone}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">传真号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierFaxNumber}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">传真号码</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.purchaseFaxNumber}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                    </tr>
                    <tr>
                        <td height="24" style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">电子邮件</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{form.supplierEmail}}</td>
                        <td style="background-color: #DCDCDC;border-left:1px solid black;border-top:1px solid black;">电子邮件</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;">{{pruchaseForm.purchaseEmail}}</td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                        <td style="border-left:1px solid black;border-top:1px solid black;"></td>
                    </tr>

                    <!--</tbody>-->
                </table>
                <table width="100%" style="font-size: 12px;text-align: center;border-right:1px solid black;border-bottom:1px solid black;border-top: 1px solid black" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td colspan="6" style="background-color: #DCDCDC;border-left:1px solid black;text-align: left;padding-left: 10px" height="24">采购协议（签字后生效）</td>
                    </tr>
                    <tr>
                        <td height="24" colspan="6" style="border-left:1px solid black;border-top:1px solid black;text-align: left;padding-left: 10px;">{{pruchaseForm.purchaseAgreement}}</td>
                    </tr>
                    <tr>
                        <td colspan="6" height="24" style="border-left:1px solid black;border-top:1px solid black;text-align: left;display: flex;line-height: 24px">
                            <span style="flex: 1;padding-left: 5px">供方签字：</span><span style="flex: 1;padding-left: 5px">日期：</span>
                            <span style="flex: 1;padding-left: 5px">制单：{{purchasePerson}}</span><span style="flex: 1;padding-left: 5px">日期：{{purchaseDate}}</span>
                            <span style="flex: 1;padding-left: 5px">审批：</span><span style="flex: 1;padding-left: 5px">日期：</span>
                        </td>
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
                id: '',
                supplierId: '',
                purchaseCode: '',
                purchasePerson: '',
                purchaseDate: '',
                order: [],
                materialTable: [],
                supplierPartner: {
                    address: '',
                    supplierName: '',
                    supplierTelphone: '',
                    fax: ''
                },
                file: '',
                imgUrl: '',
                form: {
                    supplierName: '',
                    supplierContact: '',
                    supplierNumber: '',
                    supplierPhone: '',
                    supplierFaxNumber: '',
                    supplierEmail: '',
                    address: ''
                },
                pruchaseForm: {
                    name: '',
                    address: '',
                    freight: '',
                    ifFreight: '',
                    ifTax: '',
                    subtotal: '',
                    purchasePhoneNumber: '',
                    purchasePhone: '',
                    purchaseFaxNumber: '',
                    purchaseEmail: '',
                    purchaseAgreement: ''
                },
                logisticeForm: {
                    ifPickUp: '',
                    consignee: '',
                    address: '',
                    phoneNumber: ''
                },
                subtotal: 0
            }
        },
        mounted() {
            this.getData()
        },
        methods: {
            goBack () {
                let rows = JSON.stringify(this.$route.query.rows)
                this.$router.push({path: '/purchaseOrderInfo', query: {closeFlag: 1, row: rows}})
            },
            getData () {
                let data = JSON.parse(this.$route.query.data)

                if (data.orderForm != null && data.id != null){
                    this.supplierId = data.orderForm.supplierId
                    this.id = data.id
                    this.$http.post('purchaseOrdersList/purchasePrint', {id: this.id}).then(res => {
                        if (res.data.code === 1000) {
                            this.materialTable = res.data.data
                            this.order = []
                            this.materialTable.forEach((item, index) => {
                                let obj = {
                                    materialCode: item.materialCode,
                                    materialName: item.materialName,
                                    originalMaterial: item.originalMaterial,
                                    materialBomParamValueStr: item.materialBomParamValueStr,
                                    materialUnit: item.materialUnit,
                                    purchaseQty: item.purchaseQty,
                                    purchasePrice: item.purchasePrice,
                                    weight: item.weight,
                                    purchasePriceTotal: item.purchasePriceTotal,
                                    purchaseReason: item.purchaseReason,
                                    arriveDate: item.arriveDate,
                                    drawingCode: item.drawingCode,
                                    unit: item.unit,
                                }
                                this.order.push(obj)
                                this.subtotal += item.purchasePriceTotal
                            })
                            this.subtotal = this.subtotal.toFixed(2)
                        }
                    })
                    this.$http.post('supplier/getInfo', {id: this.supplierId}).then(res=> {//获取供方信息
                        if (res.data.code === 1000 && res) {
                            res.data.data.address.forEach(item => {
                                this.form.address += item
                            })
                        }
                    })
                    //供方信息
                    this.form.supplierName = data.form.supplierName
                    this.form.supplierContact = data.form.supplierContact
                    this.form.supplierNumber = data.form.supplierNumber
                    this.form.supplierPhone = data.form.supplierPhone
                    this.form.supplierFaxNumber = data.form.supplierFaxNumber
                    this.form.supplierEmail = data.form.supplierEmail


                    //物流信息
                    this.logisticeForm.ifPickUp = data.orderForm.ifPickUp == 1 ? '自提' : '不自提'
                    if(data.orderForm.deliveryAddressInfo) {
                        this.logisticeForm.consignee = data.orderForm.deliveryAddressInfo.consignee
                        this.logisticeForm.address = data.orderForm.deliveryAddressInfo.province + data.orderForm.deliveryAddressInfo.city +  data.orderForm.deliveryAddressInfo.county
                        this.logisticeForm.phoneNumber = data.orderForm.deliveryAddressInfo.phoneNumber
                    }


                    //需方信息
                    this.pruchaseForm.pruchase = data.orderForm.pruchase
                    this.pruchaseForm.purchasePhoneNumber = data.orderForm.purchasePhoneNumber
                    this.pruchaseForm.purchasePhone = data.orderForm.purchasePhone
                    this.pruchaseForm.purchaseFaxNumber = data.orderForm.purchaseFaxNumber
                    this.pruchaseForm.purchaseEmail = data.orderForm.purchaseEmail
                    this.pruchaseForm.purchaseAgreement = data.orderForm.purchaseAgreement



                    this.pruchaseForm.freight = data.orderForm.freight
                    this.pruchaseForm.ifFreight = data.orderForm.ifFreight
                    this.pruchaseForm.ifTax = data.orderForm.ifTax
                    this.pruchaseForm.subtotal = data.orderForm.subtotal
                    this.pruchaseForm.remark = data.orderForm.remark
                    this.purchaseCode = data.orderForm.purchaseCode
                    this.purchasePerson = data.orderForm.purchasePerson
                    this.purchaseDate = data.orderForm.purchaseDate
                    this.$http.get(this.$path + '/company/get').then((res) => {
                        if (res.data.code == 1000) {
                            let company = res.data.data;
                            this.pruchaseForm.address = res.data.data.address
                            this.pruchaseForm.name = res.data.data.name
                            if (company.logo != '' && company.logo != null) {
                                this.$http.post('/company/download', {filePath: company.logo}, {responseType: 'arraybuffer'}).then((res) => {
                                    this.imgUrl = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((res, byte) => res + String.fromCharCode(byte), ''));
                                })
                            }
                        }
                    })
                }
            },
            onPring () {
                let subOutputRankPrint = document.getElementById('subOutputRank-print');
                let newContent =subOutputRankPrint.innerHTML;
                let wind = window.open("",'newwindow', 'height='+(window.screen.availHeight)+ ', width='+ (window.screen.availWidth)+', top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                wind.document.body.innerHTML = newContent;
                wind.print();
                wind.location.reload();
                wind.close();
                return false;
                alert("条码打印")
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/purchaseOrderPrint') {
                    Object.assign(this.$data, this.$options.data())
                    this.getData()
                }
            }
        }
    }
</script>

<style scoped>

</style>