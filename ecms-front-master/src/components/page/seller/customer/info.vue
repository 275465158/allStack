<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <span class="text">客户资料</span>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-form :inline="true" :model="customerForm" ref="customerForm" :rules="rules" class="demo-form-inline">
                    <el-form-item label="客户编号:">
                        <el-input disabled v-model="customerForm.id"></el-input>
                    </el-form-item>
                    <el-form-item label="公司名称:" prop="customerName">
                        <el-input v-if="customerForm.id != ''" disabled v-model="customerForm.customerName"/>
                        <el-input v-else v-model="customerForm.customerName"/>
                    </el-form-item>
                    <el-form-item label="公司税号:" prop="taxNumber">
                        <el-input v-model="customerForm.taxNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="公司地址:" prop="address">
                        <area-cascader :level="1" type="text" ref="cascader" :data="pcaa" placeholder="请选择地区" v-model="customerForm.address"></area-cascader>
                    </el-form-item>
                    <el-form-item label="门牌号:">
                        <el-input v-model="customerForm.addressDetail"></el-input>
                    </el-form-item>
                    <el-form-item label="是否含税:">
                        <el-select v-model="customerForm.taxInclude" clearable placeholder="请选择">
                            <el-option
                            v-for="item in tax"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <br/>
                    <el-form-item label="付款方式:" prop="payType">
                        <el-select class="minorSelect" v-model="customerForm.payType" clearable placeholder="请选择" @click="getSelectedPayType">
                            <el-option
                            v-for="item in payTypeArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="付款时间:" prop="payRule">
                        <el-select class="minorSelect" v-model="customerForm.payRule" clearable placeholder="请选择" @click="getSelectedPayRule">
                            <el-option
                            v-for="item in payRuleArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="发票类型:" prop="invoice">
                        <el-select class="minorSelect" v-model="customerForm.invoice" clearable placeholder="请选择" @click="getSelectedinvoice">
                            <el-option
                            v-for="item in invoiceArray"
                            :key="item.name"
                            :label="item.name + '---' +item.taxRate"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <!--aaaaa-->
                    <el-form-item label="送货方式:" prop="delivery">
                        <el-select class="minorSelect" v-model="customerForm.delivery" clearable placeholder="请选择" @click="getSelecteddeliver">
                            <el-option
                            v-for="item in deliveryArray"
                            :key="item.name"
                            :label="item.name"
                            :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>

            <hr class="marginTop"/>
            <span class="text">联系人管理</span>
            <el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
                <el-button size="small" @click="addContacterVisible = true">新增联系人</el-button>
            </el-tooltip>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-table :data="contacterList" border stripe>
                    <el-table-column prop="id" label="序号" sortable/>
                    <el-table-column prop="name" label="联系人"/>
                    <el-table-column prop="department" label="部门"/>
                    <el-table-column prop="postName" label="职位"/>
                    <el-table-column prop="phone" label="手机号码"/>
                    <el-table-column prop="faxNumber" label="传真号码"/>
                    <el-table-column prop="email" label="电子邮箱"/>
                    <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button size="small" @click="delContacter(scope.$index, scope.row)">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <hr class="marginTop"/>
            <span class="text">收货地址管理</span>
            <el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
                <el-button size="small" @click="addAddressVisible = true">新增地址</el-button>
            </el-tooltip>
            <hr class="marginBottom"/>
            <div class="handle-box">
                <el-table :data="addressList" border stripe style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable/>
                    <el-table-column prop="name" label="收货人"/>
                    <el-table-column prop="phone" label="联系电话"/>
                    <el-table-column prop="address" label="收货地址"/>
                    <el-table-column label="操作" width="180">
                        <template slot-scope="scope">
                            <el-button size="small" @click="delAddress(scope.$index, scope.row)">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div style="text-align:center;">
                <el-tooltip class="item" effect="dark" content="所有的修改只有点击保存按钮才会生效" placement="right">
                    <el-button size="big" @click="save('customerForm')">保存</el-button>
                </el-tooltip>
            </div>
        </div>

        <!-- 新增联系人 -->
        <el-dialog title="新增联系人" @close="closeing(1)" :visible.sync="addContacterVisible" width="40%">
            <el-form ref="contacterForm" :rules="contacterRules" :model="contacterForm" label-width="100px">
                <el-form-item label="联系人:" prop="name">
                    <el-input v-model="contacterForm.name"/>
                </el-form-item>
                <el-form-item label="部门:" prop="department">
                    <el-input v-model="contacterForm.department"/>
                </el-form-item>
                <el-form-item label="职位:" prop="postName">
                    <el-input v-model="contacterForm.postName"/>
                </el-form-item>
                <el-form-item label="手机号码:" prop="phone">
                    <el-input v-model="contacterForm.phone"/>
                </el-form-item>
                <el-form-item label="传真号码:">
                    <el-input v-model="contacterForm.faxNumber"/>
                </el-form-item>
                <el-form-item label="电子邮箱:" prop="email">
                    <el-input v-model="contacterForm.email"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addContacterVisible = false">取 消</el-button>
                <el-button type="primary" @click="insertContacter('contacterForm')">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增收货地址 -->
        <el-dialog title="新增收货地址" @close="closeing(2)" :visible.sync="addAddressVisible" width="40%">
            <el-form ref="addressForm" :rules="addressRules" :model="addressForm" label-width="100px">
                <el-form-item label="收货人:" prop="name">
                    <el-input v-model="addressForm.name"/>
                </el-form-item>
                <el-form-item label="联系电话:" prop="phone">
                    <el-input v-model="addressForm.phone"/>
                </el-form-item>
                <el-form-item label="收货地址:" prop="address">
                    <el-input v-model="addressForm.address"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addAddressVisible = false">取 消</el-button>
                <el-button type="primary" @click="insertAddress('addressForm')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { pca, pcaa } from 'area-data';
console.log(pcaa);
import Qs from 'qs';
    export default {
        data() {
            return {
                currentId: '',
                addressList: [],
                contacterList: [],
                payTypeArray: [],
                payRuleArray: [],
                invoiceArray: [],
                deliveryArray: [],
                url: '/seller/customer/index?id=',
                addContacterVisible: false,
                addAddressVisible: false,
                tax: [
                    {
                        name: '是',
                        value: 1
                    },
                    {
                        name: '否',
                        value: 0
                    }
                ],
                rules: {
                    contacterPhone: [
                        { required: true, validator:this.checkPhone, trigger: 'blur' }
                    ],
                    customerName: [
                        { required: true, message: '请输入公司名称', trigger: 'blur' }
                    ],
                    payType: [
                        { required: true, message: '请选择付款方式', trigger: 'blur' }
                    ],
                    payRule: [
                        { required: true, message: '请选择付款周期', trigger: 'blur' }
                    ],
                    invoice: [
                        { required: true, message: '请选择发票类型', trigger: 'blur' }
                    ],
                    delivery: [
                        { required: true, message: '请选择送货方式', trigger: 'blur' }
                    ],
                    address: [{
                        type: 'array',
                        required: true,
                        message: '请选择地区',
                        trigger: 'change'
                    }]
                },
                addressRules: {
                    name: [
                        { required: true, message: '请输入收货人', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, validator:this.checkPhone, trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入收货地址', trigger: 'blur' }
                    ]
                },
                contacterRules: {
                    phone: [
                        { required: true, validator:this.checkPhone, trigger: 'blur' }
                    ],
                    email: [
                        { required: true, validator:this.checkEmail, trigger: 'blur' }
                    ]
                    // department: [
                    //     { required: true, message: '请输入部门', trigger: 'blur' }
                    // ],
                    // postName: [
                    //     { required: true, message: '请输入岗位', trigger: 'blur' }
                    // ],
                    // email: [
                    //     { required: true, validator:this.checkEmail, trigger: 'blur' }
                    // ]
                },
                contacterForm: {
                    name: '',
                    department: '',
                    postName: '',
                    phone: '',
                    faxNumber: '',
                    email: ''
                },
                addressForm: {
                    name: '',
                    phone: '',
                    address: ''
                },
                customerForm: {
                    id: '',
                    customerName: '',
                    taxNumber: '',
                    address: [],
                    taxInclude: '',
                    addressDetail: '',
                    contacterName: '',
                    contacterPhone: '',
                    customerPhone: '',
                    payType: '',
                    payRule: '',
                    invoice: '',
                    delivery: ''
                },
                pca: pca,
                pcaa: pcaa
            }
        },
        created() {
            this.getData();
            this.getSelectedPayType();
            this.getSelectedPayRule();
            this.getSelectedinvoice();
            this.getSelecteddeliver();
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/sellerCustomerInfo' && this.$route.query.works !== 1) {
                    this.$refs['customerForm'].resetFields();
                    this.customerForm = {
                            id: '',
                            customerName: '',
                            taxNumber: '',
                            address: [],
                            taxInclude: '',
                            addressDetail: '',
                            contacterName: '',
                            contacterPhone: '',
                            customerPhone: '',
                            payType: '',
                            payRule: '',
                            invoice: '',
                            delivery: ''
                    }
                    this.contacterList = []
                    this.getData();
                }
            }
        },
        methods: {
            closeing (a) {
                if (a === 1) {
                    this.$refs['contacterForm'].resetFields()
                } else {
                    this.$refs['addressForm'].resetFields()
                }
            },
            /**
             * this.payTypeArray = res.data.data.payTypeInfos;
                        this.payRuleArray = res.data.data.payRuleInfos;
                        this.invoiceArray = res.data.data.invoiceInfos;
                        this.deliveryArray = res.data.data.deliveryInfos;
             */
            getSelectedPayType(){
                let url="/sellerPayType/all";
                 this.$http.get(url).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.payTypeArray=res.data.data;
                    }
                 });
            },
            getSelectedPayRule(){
                let url="/sellerPayRule/all";
                 this.$http.get(url).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.payRuleArray=res.data.data;
                    }
                 });

            },
            getSelectedinvoice(){
                let url="/sellerInvoice/all";
                 this.$http.get(url).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        console.log('res.data.data',res.data.data);
                        this.invoiceArray=res.data.data;
                    }
                 });

            },
            getSelecteddeliver(){
                let url="/sellerDelivery/all";
                 this.$http.get(url).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.payRuleArray=res.data.data;
                    }
                 });

            },
            getData() {
                let queryId = this.$route.query.id;
                if(queryId == undefined || queryId == ""){
                    queryId = 0
                }
                this.$http.get(this.url+queryId).then((res) => {
                    if (res != undefined && res.data.code == 1000) {

                        
                        if (res.data.data.customerInfo != null) {
                            this.customerForm = res.data.data.customerInfo;
                        } else {
                            this.customerForm = {};
                            this.customerForm.id = '';
                        }



                        if (res.data.data.addressInfos != null) {
                            this.addressList = res.data.data.addressInfos;
                        } else {
                            this.addressList = [];
                        }


                        if (res.data.data.contacterInfos != null) {
                            this.contacterList = res.data.data.contacterInfos;
                        } else {
                            this.contacterList = [];
                        }

                        this.payTypeArray = res.data.data.payTypeInfos;
                        this.payRuleArray = res.data.data.payRuleInfos;
                        this.invoiceArray = res.data.data.invoiceInfos;
                        this.deliveryArray = res.data.data.deliveryInfos;
                    }
                })
            },
            save(formName) {
                console.log(this.customerForm.address);
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let form = {
                            customerInfo: this.customerForm,
                            contacterInfos: this.contacterList,
                            addressInfos: this.addressList
                        }

                        let config = {
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            withCredentials : true
                        };
                        this.$axios.post(this.$path + '/seller/customer/save', form, config).then((res) => {
                            if (res.data.code == 1000) {
                                this.$message.success('保存成功');
                                this.goBack();
                            } else {
                                this.$message.error(res.data.message);
                            }
                        })
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            },
            delAddress(index, row) {
                this.addressList.splice(index, 1);
            },
            delContacter(index, row) {
                this.contacterList.splice(index, 1);
            },
            insertAddress(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data = JSON.parse(JSON.stringify(this.addressForm));
                        this.addressList.push(data);
                        this.addAddressVisible = false;
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            },
            insertContacter(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data = JSON.parse(JSON.stringify(this.contacterForm));
                        this.contacterList.push(data);
                        this.addContacterVisible = false;
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            },
            goBack() {
                this.$router.push({
                    path: "/sellerCustomer",
                    query: {closeFlag: 1}
                });
            }
        },
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 50px;
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
        text-align: center
    }
    hr {
        border-top: 1px;
    }
    .marginTop {
        margin-top: 10px;
        margin-bottom: 5px;
    }
    .marginBottom {
        margin-top: 5px;
        margin-bottom: 10px;
    }
    .text {
        font-size: 12px;
        color: #606266;
        margin-right: 30px;
    }
    .minorSelect {
        width: 120px;
    }
</style>
    