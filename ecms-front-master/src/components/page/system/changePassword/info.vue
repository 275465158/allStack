<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <p style="font-size: 12px;margin-bottom: 10px;color: #999999;">密码需包含字母、数字、特殊符号（如*、_等），长度大于8位</p>
                <el-form :inline="true" ref="form" :model="form" class="demo-form-inline" :rules="rules">
                    <el-form-item label="账号:">
                        <el-input v-model="form.userAccount" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="输入新密码:" prop="password">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码:" prop="checkPassword">
                        <el-input v-model="form.checkPassword"></el-input>
                    </el-form-item>
                    <el-button round @click="changePassword" type="primary">修改</el-button>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                url: '/user/getUserInfoList',
                changePasswordUrl: '/user/changePassword',
                form: {
                    userAccount: '',
                    password:'',
                    checkPassword:''
                },
                rules: {
                    password: [
                        { required: true, validator:this.validationPassword, trigger: 'blur' }
                    ],
                    checkPassword: [
                        { required: true, validator:this.validationPassword, trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    let status = d.status;
                    let type = d.type;
                    d.statusDisplay = status == 0 ? '正常':'失效';
                    d.typeDisplay = type == 0 ? '普通用户':'管理员';
                    return d;
                })
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$http.post(this.url).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.form.userAccount = res.data.data.list[0].userAccount;
                        this.form.id = res.data.data.list[0].id;
                    }
                })
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            // 保存编辑
            changePassword() {
                this.$refs['form'].validate(val => {
                    if (val) {
                        if(this.form.password != this.form.checkPassword){
                            this.$message.error("两次密码输入不一样，请重新输入");
                        }else{
                            this.$http.post(this.changePasswordUrl,this.form).then((res) => {
                                if (res != undefined && res.data.code == 1000) {
                                    this.$message.success("密码修改成功");
                                    this.$router.push('/login');
                                }
                            })
                        }
                    } else {
                        return false
                    }
                })
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
</style>
