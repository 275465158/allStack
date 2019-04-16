<template>
    <div class="container">
        <div class="handle-box">
            <div class="supplier">
                <span class="text">供应商信息</span>
            </div>
            <el-form :inline="true" :model="form" ref="form" label-width="100px">
                <el-form-item label="供应商名称">
                    <el-input disabled v-model="form.supplierName"></el-input>
                </el-form-item>
                <el-form-item label="联系人">
                    <el-select
                        style="width: 200px;"
                        @change="selectLinkman"
                        v-model="form.supplierContact"
                    >
                        <el-option
                            v-for="(item, index) in options"
                            :key="item.index"
                            :label="item.contactName"
                            :value="item.contactName"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input type="tel" v-model="form.supplierNumber"></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input type="tel" v-model="form.supplierPhone"></el-input>
                </el-form-item>
                <el-form-item label="传真号码">
                    <el-input v-model="form.supplierFaxNumber"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.supplierEmail"></el-input>
                </el-form-item>
            </el-form>
            <div class="supplier">
                <span class="text">采购信息</span>
            </div>
            <el-form
                :inline="true"
                :rules="pruchaseFormRules"
                :model="pruchaseForm"
                ref="pruchaseForm"
                label-width="100px"
            >
                <el-form-item label="采购人">
                    <el-input v-model="pruchaseForm.pruchase" readonly></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input type="tel" v-model="pruchaseForm.purchasePhoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input type="tel" v-model="pruchaseForm.purchasePhone"></el-input>
                </el-form-item>
                <el-form-item label="传真号码">
                    <el-input v-model="pruchaseForm.purchaseFaxNumber"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱">
                    <el-input v-model="pruchaseForm.purchaseEmail"></el-input>
                </el-form-item>
                <el-form-item label="是否含税">
                    <el-select v-model="pruchaseForm.ifTax" style="width: 200px;">
                        <el-option
                            v-for="(item, index) in taxOptoins"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否含运费">
                    <el-select
                        @change="ifFreightFn"
                        v-model="pruchaseForm.ifFreight"
                        style="width: 200px;"
                    >
                        <el-option
                            v-for="(item, index) in taxOptoins"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="产品价格">
                    <el-input disabled v-model="pruchaseForm.productPriceTotal"></el-input>
                </el-form-item>
                <el-form-item label="采购单总价">
                    <el-input disabled v-model="pruchaseForm.subtotal"></el-input>
                </el-form-item>
                <el-form-item label="运费">
                    <el-input
                        :disabled="disabled"
                        @input="summation"
                        type="number"
                        oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);"
                        v-model.number="pruchaseForm.freight"
                    ></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="pruchaseForm.remark"></el-input>
                </el-form-item>
                <el-form-item label="仓库选择" prop="repertoryId">
                    <el-select
                        style="width: 200px;"
                        filterable
                        v-model="pruchaseForm.repertoryId"
                        @change="selectChange"
                        placeholder="请选择"
                    >
                        <el-option
                            v-for="item in this.repertoryList"
                            :key="item.id"
                            :label="item.repertoryName"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div class="supplier">
                <span class="text">采购协议</span>
            </div>
            <el-input
                type="textarea"
                style="width: 50%;"
                :autosize="{ minRows: 2, maxRows: 6 }"
                v-model="pruchaseForm.purchaseAgreement"
            ></el-input>
        </div>
        <div v-if="show">
            <span class="text">选择收获地址</span>
            <a class="alink" @click="dialogFormVisible = true" href="javascript:;">使用新的收获地址</a>
            <hr>
            <div class="address">
                <ul class="clear">
                    <li
                        @click="selectShipping(item, index)"
                        :class="{active: active == index}"
                        class="mt10"
                        v-for="(item, index) in shippingAddress"
                    >
                        <div class="addressItem">
                            <span>{{item.province}}</span>
                            <span>{{item.city}}</span>
                            <span>{{item.consignee}}</span>
                        </div>
                        <div style="padding: 5px;">
                            <span>{{item.county}}</span>
                            <span class="ml10">{{item.detailAddress}}</span>
                            <span class="ml10">{{item.phoneNumber}}</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <el-checkbox class="marginTop" @change="changePick" v-model="checked">自提</el-checkbox>
        <br>
        <span class="text">采购清单</span>
        <hr class="marginBottom">
        <el-table :data="supplierTableData" border style="width:100%">
            <el-table-column prop="materialCode" label="物料编码"></el-table-column>
            <el-table-column prop="type" label="物料类型"></el-table-column>
            <el-table-column prop="materialName" label="物料名称"></el-table-column>
            <el-table-column prop="originalMaterial" label="材料"></el-table-column>
            <el-table-column prop="materialBomParamValueStr" label="参数"></el-table-column>
            <el-table-column prop="drawingCode" label="图号"></el-table-column>
            <el-table-column prop="materialUnit" label="单位"></el-table-column>
            <el-table-column prop="weight" label="重量"></el-table-column>
            <el-table-column label="单价">
                <template slot-scope="scope">
                    <el-input type="number" @input="calculate(scope.row, scope.$index)" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-model.number="scope.row.purchasePrice"></el-input>
                </template>
            </el-table-column>
            <el-table-column label="采购数量">
                <template slot-scope="scope">
                    <el-input type="number" @input="calculate(scope.row, scope.$index)" oninput="this.value=(this.value.replace(/[^0-9.]+/,'')).slice(0,11);" v-model.number="scope.row.purchaseQty"></el-input>
                </template>
            </el-table-column>
            <el-table-column prop="purchaseDays" label="交货日期"></el-table-column>
            <el-table-column prop="subtotal" label="小计"></el-table-column>
            <!--<el-table-column prop="purchasePriceTotal" label="小计"></el-table-column>-->
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="small" @click="removeMaterial(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <hr class="marginTop">
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="insertOrderRecord">下 单</el-button>
            <el-button @click="goBack">取 消</el-button>
        </span>
        <el-dialog title="收货地址" :visible.sync="dialogFormVisible" width="30%">
            <el-form ref="shippingForm" :rules="rules" :model="shippingForm" label-width="100px">
                <el-form-item label="收货人姓名" prop="name">
                    <el-input style="width: 196px;" v-model="shippingForm.name"></el-input>
                </el-form-item>
                <el-form-item label="所在地" prop="address">
                    <area-cascader
                        :level="1"
                        type="text"
                        ref="cascader"
                        :data="pcaa"
                        placeholder="请选择地区"
                        v-model="shippingForm.address"
                    ></area-cascader>
                </el-form-item>
                <el-form-item label="详细地址" prop="detailAddress">
                    <el-input style="width: 196px;" v-model="shippingForm.detailAddress"></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="phoneNumber">
                    <el-input style="width: 196px;" v-model.number="shippingForm.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="number">
                    <el-input style="width: 196px;" v-model.number="shippingForm.number"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addShipping">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import { pca, pcaa } from "area-data";
export default {
    data() {
        return {
            checked: false,
            show: true,
            addContacterVisible: false,
            options: [],
            form: {
                supplierName: "",
                supplierContact: "",
                supplierNumber: "",
                supplierPhone: "",
                supplierFaxNumber: "",
                supplierEmail: "",
                repertoryId: ""
            },
            pruchaseForm: {
                pruchase: "",
                purchasePhoneNumber: "",
                purchasePhone: "",
                purchaseFaxNumber: "",
                purchaseEmail: "",
                ifTax: 0,
                ifFreight: 0,
                freight: "",
                remark: "",
                purchaseAgreement: "",
                subtotal: "",
                productPriceTotal: 0,
                ifPickUp: "",
                deliveryAddressId: ""
            },
            taxOptoins: [
                {
                    value: 1,
                    label: "是"
                },
                {
                    value: 0,
                    label: "否"
                }
            ],
            search: {
                materialId: "",
                ordered: false
            },
            active: 0,
            shippingAddress: [],
            dialogFormVisible: false,
            shippingForm: {
                address: "",
                name: "",
                detailAddress: "",
                phoneNumber: "",
                number: ""
            },
            pca: pca,
            pcaa: pcaa,
            pruchaseFormRules: {
                repertoryId: [
                    { required: true, message: "请选择仓库", trigger: "blur" }
                ]
            },
            rules: {
                name: [
                    { required: true, message: "请输入姓名", trigger: "blur" }
                ],
                address: [
                    {
                        type: "array",
                        required: true,
                        message: "请选择地区",
                        trigger: "change"
                    }
                ],
                detailAddress: [
                    {
                        required: true,
                        message: "请输入详细地址",
                        trigger: "blur"
                    }
                ],
                phoneNumber: [
                    {
                        required: true,
                        message: "请输入手机号码",
                        trigger: "blur"
                    }
                ],
                number: [
                    {
                        required: true,
                        message: "请输入电话号码",
                        trigger: "blur"
                    }
                ]
            },
            stayPurQtyCopy: "",
            pickSupplier: "",
            supplierTableData: [],
            repertoryList: "",
            invoiceTypes: [],
            paymentPeriods: [],
            paymentTypes: [],
            shippingMethods: [],
            supplierId: ""
        };
    },
    created() {
        this.getData();
    },
    computed: {
        tableData() {
            return this.supplierTableData.filter(d => {
                return d;
            });
        },
        disabled() {
            let ifFreight = this.pruchaseForm.ifFreight ? false : true;
            return ifFreight;
        }
    },
    methods: {
        ifFreightFn(val) {
            if (!val) {
                this.pruchaseForm.freight = 0;
            }
        },
        calculate(row, i) {
            this.supplierTableData.forEach(item => {
                if (
                    row.purchaseDays == item.purchaseDays &&
                    row.materialId == item.materialId
                ) {
                    item.purchasePrice = row.purchasePrice;
                    item.subtotal = item.purchaseQty * item.purchasePrice;
                }
            });
            row.subtotal = row.purchaseQty * row.purchasePrice;
            this.pruchaseForm.productPriceTotal = 0;
            this.supplierTableData.forEach((item, index) => {
                this.pruchaseForm.productPriceTotal += item.subtotal;
            });
            this.pruchaseForm.subtotal =
                this.pruchaseForm.productPriceTotal + this.pruchaseForm.freight;
        },
        selectShipping(item, i) {
            this.active = i;
            this.pruchaseForm.deliveryAddressId = item.id;
        },
        changePick(val) {
            if (val) {
                this.pruchaseForm.ifPickUp = 1;
                this.$set(this.pruchaseForm, "deliveryAddressId", "");
                this.show = false;
            } else {
                this.pruchaseForm.ifPickUp = 2;
                this.show = true;
            }
        },
        summation() {
            this.pruchaseForm.subtotal =
                this.pruchaseForm.productPriceTotal + this.pruchaseForm.freight;
        },
        selectChange(val) {
            this.supplierTableData.forEach((item, index) => {
                item.repertoryId = val;
            });
        },
        addShipping() {
            this.$refs.shippingForm.validate(valid => {
                if (valid) {
                    let params = {
                        province: this.shippingForm.address[0],
                        city: this.shippingForm.address[1],
                        county: this.shippingForm.address[2],
                        detailAddress: this.shippingForm.detailAddress,
                        consignee: this.shippingForm.name,
                        phoneNumber: this.shippingForm.phoneNumber,
                        number: this.shippingForm.number
                    };
                    this.$http
                        .post("deliveryAddress/saveToPurchase", params)
                        .then(res => {
                            if (res.data.code === 1000) {
                                this.$http
                                    .post("deliveryAddress/searchByCompanyId")
                                    .then(res => {
                                        if (res.data.code === 1000) {
                                            this.shippingAddress =
                                                res.data.data;
                                            this.dialogFormVisible = false;
                                        }
                                    });
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        selectLinkman(val) {
            this.options.forEach((item, index) => {
                if (item.contactName === val) {
                    this.form.supplierPhone = item.mobile;
                    this.form.supplierFaxNumber = item.fax;
                    this.form.supplierEmail = item.email;
                }
            });
        },
        getData() {
            let param = JSON.parse(this.$route.query.row);
            this.$http.post('/purchaseOrdersStay/purchaseListByIds', {ids: param}).then(rows => {
                let row = rows.data.data
                this.supplierId = row[0].supplierId;
                this.$http.post("deliveryAddress/searchByCompanyId").then(res => {
                    if (res.data.code === 1000) {
                        if (res.data.data.length !== 0) {
                            this.shippingAddress = res.data.data;
                            this.pruchaseForm.deliveryAddressId = this.shippingAddress[0].id;
                            this.pruchaseForm.ifPickUp = 2;
                            this.show = true;
                        } else {
                            this.checked = true;
                            this.pruchaseForm.ifPickUp = 1;
                            this.$set(this.pruchaseForm, "deliveryAddressId", "");
                            this.show = false;
                        }
                    }
                });
                this.$http
                    .post("supplier/contact/searchById", {
                        supplierId: this.supplierId
                    })
                    .then(res => {
                        if (res.data.code === 1000) {
                            this.options = res.data.data;
                        }
                    });
                this.form.supplierId = row[0].supplierId;
                this.form.supplierCode = row[0].supplierCode;
                this.form.supplierName = row[0].supplierName;
                this.supplierTableData = [];
                row.forEach((item, index) => {
                    let obj = {
                        materialCode: item.materialBomInfo.materialCode,
                        type: item.materialBomInfo.type,
                        supplierName: item.supplierName,
                        materialName: item.materialBomInfo.materialName,
                        originalMaterial: item.materialBomInfo.originalMaterial,
                        materialBomParamValueStr: item.materialBomInfo.materialBomParamValueStr,
                        materialUnit: item.materialBomInfo.materialUnit,
                        purchaseQty: item.stayPurQty,
                        query: 1,
                        purchasePriceTotal: (
                            parseFloat(item.purchasePrice) * Number(item.stayPurQty)
                        ).toFixed(2),
                        factoryMaterialCode:
                        item.materialBomInfo.factoryMaterialCode,
                        supplierId: item.supplierId,
                        materialId: item.materialId,
                        purchaseStayId: item.id,
                        purchaseReason: item.purchaseReason,
                        drawingCode: item.materialBomInfo.drawingCode,
                        subtotal: !item.stayPurQty
                            ? Number(item.purchasePrice) * 0
                            : Number(item.materialBomInfo.smiList[0].price) * Number(item.stayPurQty)
                    };
                    item.materialBomInfo.smiList.forEach((item1, i) => {
                        if (
                            item.materialBomInfo.smiList[i].supplierId ===
                            row[index].supplierId
                        ) {
                            let delivery = new Date();
                            delivery.setTime(
                                delivery.getTime() +
                                Number(
                                    item.materialBomInfo.smiList[i].purchaseDays
                                ) *
                                24 *
                                60 *
                                60 *
                                1000
                            );
                            obj.purchaseDays =
                                delivery.getFullYear() +
                                "-" +
                                (delivery.getMonth() + 1) +
                                "-" +
                                delivery.getDate();
                            obj.weight = Number(
                                item.materialBomInfo.smiList[i].weight
                            );
                            obj.purchasePrice =
                                item.materialBomInfo.smiList[i].price;
                            obj.id = item.materialBomInfo.smiList[i].id;
                        }
                    });
                    this.supplierTableData.push(obj);
                });
                this.pruchaseForm.productPriceTotal = 0;
                this.supplierTableData.forEach((item, index) => {
                    this.pruchaseForm.productPriceTotal += item.subtotal;
                    item.oldPurchaseQty = item.purchaseQty;
                });
                this.pruchaseForm.subtotal =
                    this.pruchaseForm.productPriceTotal + this.pruchaseForm.freight;
                this.$http.post("/repertory/getList").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.repertoryList = res.data.data;
                    }
                });
            })
        },
        removeMaterial(index, row) {
            this.supplierTableData.splice(index, 1);
            this.pruchaseForm.productPriceTotal = 0;
            this.supplierTableData.forEach((item, index) => {
                this.pruchaseForm.productPriceTotal += item.subtotal;
            });
            this.pruchaseForm.subtotal =
                this.pruchaseForm.productPriceTotal + this.pruchaseForm.freight;
        },
        goBack() {
            this.$router.push({
                path: "/purchaseQueueList",
                query: { closeFlag: 1 }
            });
        },
        insertOrderRecord() {
            let params = {};
            let params2 = {
                priceMap: []
            };
            for (let key in this.form) {
                params[key] = this.form[key];
            }
            for (let key in this.pruchaseForm) {
                if (key == "deliveryAddressId") {
                    if (this.pruchaseForm[key] != "") {
                        params[key] = this.pruchaseForm[key];
                    }
                } else {
                    params[key] = this.pruchaseForm[key];
                }
            }
            for (let i = 0; i < this.supplierTableData.length; i++) {
                if (this.supplierTableData[i].purchasePrice < 0) {
                    this.$message.warning('单价不能小于0')
                    return
                } else {
                    params2.priceMap.push({
                        price: this.supplierTableData[i].purchasePrice,
                        id: this.supplierTableData[i].id
                    });
                }
            }
            this.$refs["pruchaseForm"].validate(valid => {
                if (valid) {
                    params.mapList = this.supplierTableData;
                    params.mapList.forEach((item, index) => {
                        item.repertoryId = params.repertoryId;
                        item.purchasePriceTotal = item.subtotal;
                    });
                    if (params.productPriceTotal < 0) {
                        this.$message.warning('采购数量不能小于0')
                        return
                    }
                    this.$http
                        .post("purchaseOrders/savePurchaseOrders", params)
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.$message.success("提交成功");
                                this.$http
                                    .post(
                                        "supplier/material/updatePrice",
                                        params2
                                    )
                                    .then(res => {
                                        if (res.data.code === 1000) {
                                            //跳转到列表页面
                                            this.$router.push({
                                                path: "/purchaseQueueList",
                                                query: { cloaseFlag: 1 }
                                            });
                                        }
                                    });
                            }
                        });
                } else {
                    this.$message.error("请选择仓库");
                    return false;
                }
            });
        }
    },
    watch: {
        //$route: "getData",
        $route(to, info) {
            if (to.path == "/purchaseByStay" && this.$route.query.works !== 1) {
                // Object.assign(this.$data, this.$options.data());
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

.marginTop {
    margin-top: 10px;
    margin-bottom: 10px;
}
.ml10 {
    margin-left: 10px;
}
.mt10 {
    margin-top: 10px;
}

.marginBottom {
    margin-top: 10px;
    margin-bottom: 10px;
}

.text {
    font-size: 12px;
    color: #606266;
    margin-right: 30px;
}

.supplier {
    padding-bottom: 10px;
}
.address {
    margin-top: 10px;
}
.address ul li {
    list-style: none;
    float: left;
    width: 200px;
    height: 100px;
    border: 1px solid #bfcbd9;
    font-size: 12px;
    margin-right: 10px;
    cursor: pointer;
}
.addressItem {
    display: flex;
    height: 20px;
    line-height: 20px;
    border-bottom: 1px solid #bfcbd9;
}
.addressItem span {
    flex: 1;
}
.addressItem span:last-child {
    flex: 2;
}
.address ul .active {
    border: 1px solid black;
}
</style>