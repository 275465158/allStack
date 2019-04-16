<template>
    <div class="table">
        <el-form :model="form" ref="form" :rules="rules" label-width="100px">
            <el-input type="hidden" v-model="form.id"></el-input>
            <el-row :gutter="30">
                <el-col :span="24">
                    <el-form-item label="照片:">
                        <el-upload
                                class="upload-demo"
                                :action="this.$path + '/seller/order/uploadSimple'"
                                :on-success="fileUpload"
                                accept="image/*"
                                :with-credentials="true"
                        >
                            <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>
                </el-col>
                <el-col :span="24" align="right">
                    <el-form-item label=" ">
                        <img :src="imgUrl" height="100" width="100"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="姓名:" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="出生日期:" prop="birthday">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="form.birthday"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="性别:" prop="sex">
                        <el-select v-model="form.sex" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in sexType"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系电话:" prop="phone">
                        <p style="color: #999999;">（区号加固话号码，如：0001-1234567）</p>
                        <el-input type="tel" v-model="form.phone"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="证件名称:" prop="identificationType">
                        <el-select v-model="form.identificationType" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in IDType"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="证件号码:" prop="identificationNumber">
                        <el-input v-model="form.identificationNumber"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="证件住址:" prop="identificationAddress">
                <el-input v-model="form.identificationAddress"></el-input>
            </el-form-item>
            <el-form-item label="现居住地址:" prop="address">
                <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-row :gutter="30">
                <el-col :span="8">
                    <el-form-item label="一级部门:" prop="rootDepartmentId">
                        <el-select @change="selectFirst" v-model="form.rootDepartmentId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in firstDepartment"
                                    :key="item.id"
                                    :label="item.departmentName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="二级部门:" prop="secondDepartmentId">
                        <el-select @change="selectSecond" v-model="form.secondDepartmentId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in secondDepartment"
                                    :key="item.id"
                                    :label="item.departmentName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="三级部门:" prop="thirdDepartmentId">
                        <el-select @change="selectThird" v-model="form.thirdDepartmentId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in thirdDepartment"
                                    :key="item.id"
                                    :label="item.departmentName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="岗位:" prop="postId">
                        <el-select v-model="form.postId" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in post"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="婚姻状况:" prop="maritalStatus">
                        <el-select v-model="form.maritalStatus" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in marital"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="紧急联系人:">
                        <el-input v-model="form.emergencyContactName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="紧急联系号码:">
                        <el-input type="number" v-model="form.emergencyContactPhone"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="入职日期:" prop="joinDate">
                <el-date-picker
                        value-format="yyyy-MM-dd"
                        v-model="form.joinDate"
                        type="date"
                        placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item size="large">
                <el-button type="primary" @click="save('form')">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                imgUrl: require('../../../../../static/img/149881997658968.png'),
                IDType: ['身份证', '护照'],
                sexType: ['男', '女'],
                marital: ['未婚', '已婚'],
                form: {
                    imgUrl: require('../../../../../static/img/149881997658968.png'),
                },
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    sex: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    phone: [
                        { required: true, validator:this.checkPhone, trigger: 'blur' }
                    ],
                    identificationType: [
                        { required: true, message: '请选择证件名称', trigger: 'change' }
                    ],
                    identificationNumber: [
                        { required: true, message: '请输入证件号码', trigger: 'blur' }
                    ],
                    identificationAddress: [
                        { required: true, message: '请输入证件住址', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入现居住地址', trigger: 'blur' }
                    ],
                    rootDepartmentId: [
                        { required: true, message: '请选择一级部门', trigger: 'change' }
                    ],
                    postId: [
                        { required: true, message: '请选择岗位', trigger: 'change' }
                    ],
                    maritalStatus: [
                        { required: true, message: '请选择婚姻状况', trigger: 'change' }
                    ],
                    birthday: [
                        { required: true, message: '请选择入职日期', trigger: 'change' }
                    ]
                },
                search: {
                    employeeId: ''
                },
                firstDepartment:[],
                secondDepartment:[],
                thirdDepartment:[],
                post:[],
                visibleUpload:false,
                visiblePicture:false,
                file: ''
            }
        },
        mounted () {
            this.search.employeeId = this.$route.query.id;
            this.getData();
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/employeeInfo' && this.$route.query.works !== 1) {
                    this.$refs['form'].resetFields();
                    this.form.emergencyContactName = ''
                    this.form.emergencyContactPhone = ''
                    this.form.joinDate = ''
                    let newId = this.$route.query.id;
                    if (newId == undefined) {
                        newId = 0;
                    }
                    if (this.search.employeeId != newId) {
                        this.search.employeeId = newId;
                        this.getData();
                    }
                }
            }
        },
        computed: {
            // tables(){
            //     return this.tableData.filter((d) => {
            //         let status = d.status;
            //         let type = d.type;
            //         d.statusDisplay = status == 0 ? '正常':'失效';
            //         d.typeDisplay = type == 0 ? '普通用户':'管理员';
            //         return d;
            //     })
            // }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            fileUpload(response, file, fileList){
                if (response.code == 1000) {
                    this.form.employeePic = response.data;
                    this.$http.post('/employee/download', {filePath: this.form.employeePic}, {responseType: 'arraybuffer'}).then((res) => {
                        this.imgUrl = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((res, byte) => res + String.fromCharCode(byte), ''));
                    })
                }
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                if(this.$route.query.id == null){
                    this.visibleUpload = true;
                    this.visiblePicture = false;
                }else{
                    this.visiblePicture = true;
                    this.visibleUpload = false;
                }
                this.$http.post('/employee/info', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.firstDepartment = res.data.data.firstDepartment;
                        this.secondDepartment = res.data.data.secondDepartment;
                        this.thirdDepartment = res.data.data.thirdDepartment;
                        this.post = res.data.data.postInfoList;
                        this.form = res.data.data.employeeInfo;
                        if(this.$route.query.id==undefined){
                            this.form.identificationType="身份证";
                        }
                        if (this.form.employeePic != '' && this.form.employeePic != null) {
                            this.$http.post('/employee/download', {filePath: this.form.employeePic}, {responseType: 'arraybuffer'}).then((res) => {
                                this.imgUrl = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((res, byte) => res + String.fromCharCode(byte), ''));
                            })
                        } else {
                            this.imgUrl = require('../../../../../static/img/149881997658968.png')
                        }
                    }
                })
            },
            save(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.form.departmentId = this.form.thirdDepartmentId != '' ? this.form.thirdDepartmentId
                            : this.form.secondDepartmentId != '' ? this.form.secondDepartmentId
                                : this.form.rootDepartmentId;
                        if (this.form.thirdDepartmentId != '' && this.form.thirdDepartmentId != undefined){
                            this.form.departmentId = this.form.thirdDepartmentId
                        }  else if (this.form.secondDepartmentId != '' && this.form.secondDepartmentId != undefined) {
                            this.form.departmentId = this.form.secondDepartmentId
                        }else {
                            this.form.departmentId = this.form.rootDepartmentId
                        }
                        this.$http.post('/employee/save', this.form).then((res) => {
                            if (res.data != undefined && res.data.code == 1000) {
                                this.$router.push({ path: '/employeeList'})
                                this.$message.success('保存成功');
                            }
                        })
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            },
            selectFirst(e) {
                this.thirdDepartment = [];
                this.secondDepartment = [];
                this.form.secondDepartmentId = '';
                this.form.thirdDepartmentId = '';
                this.form.rootDepartmentId = e;
                if (!isNaN(e) && e != '') {
                    this.$http.get('/department/children?id=' + e).then((res) => {
                        if (res.data != undefined && res.data.code == 1000) {
                            this.secondDepartment = res.data.data;
                        // if(this.form.secondDepartmentId==null||this.form.secondDepartmentId==""){
                        //         if(this.secondDepartment!=null){
                        //      this.form.secondDepartmentId= this.secondDepartment[0];
                        //  }
                        //  }

                        }
                    })
                }
            },
            selectSecond(e) {
                this.thirdDepartment = [];
                this.form.thirdDepartmentId = '';
                this.form.secondDepartmentId = e;
                if (!isNaN(e) && e != '') {
                    this.$http.get('/department/children?id=' + e).then((res) => {
                        if (res != undefined && res.data.code == 1000) {
                            this.thirdDepartment = res.data.data;


                        //      if(this.form.thirdDepartmentId==null||this.form.thirdDepartmentId==""){
                        //         if(this.thirdDepartment!=null){
                        //      this.form.thirdDepartmentId= this.thirdDepartment[0];
                        //  }
                        //  }

                         
                        }
                    })
                }
            },
            selectThird(e) {
                this.form.thirdDepartmentId = e;
            }
        }
    }

</script>

