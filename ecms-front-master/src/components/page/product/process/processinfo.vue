<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">工艺明细:</span>
        <hr class="marginBottom" />
        <el-form v-loading="loading" :model="form" ref="form" :rules="rules" label-width="100px">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="工艺名称" prop="processName">
                        <el-input v-model="form.processName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="说明">
                        <el-input v-model="form.remark"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginTop" />
            <span class="text">工艺基本格式:</span>
            <el-form :data="processBaseFormat"  ref="processBaseFormatForm" border style="width:100%" height="250">
                <el-checkbox  v-for="(project,index) in processBaseFormat" :key="index" :offset="1" :checked="1==1" disabled border>
                    {{project.processFormatName}}
                </el-checkbox>
            </el-form>
            <hr class="marginTop" />
            <span class="text">工艺选择:</span>
            <el-form :data="processOtherFormat" border style="width:100%" height="250">
                <el-checkbox  v-for="(project,index) in processOtherFormat" :key="index" :offset="1" border @change="checked=>selectFormat(checked,project.processFormatName,index)">
                    {{project.processFormatName}}
                </el-checkbox>
            </el-form>
            <hr class="marginBottom" />
            <span class="text">辅助工具类型选择:</span>
            <hr class="marginBottom" />
            <el-table :data="toolTypeSelect" border style="width:100%" height="250" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="序号" prop="id">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column label="工具类型" prop="toolTypeName"></el-table-column>
                <el-table-column label="型号" prop="defaultModelNumber">
                    <template slot-scope="scope">
                        <el-checkbox @change="checkFn(scope.row)" v-model="scope.row.defaultModelNumber"></el-checkbox>
                    </template>
                </el-table-column>
            </el-table>
            <hr class="marginTop"/>
            <el-button @click="goBack">返回</el-button>
            <el-button round type="primary" @click="save">保 存</el-button>
        </el-form>
    </div>
</template>
<!--aaaaaaaa-->

<script>
    export default {
        data() {
            return {
                flag: [0,0,0,0],
                form: {
                    processName:'',
                    remark:'',
                    defaultModelNumber:0,
                    toolTypeSelectList:[],
                    processOtherFormatList:[],
                },
                processBaseFormatForm:{
                    processFormatName:''
                },
                search:{
                    id:'',
                    pageNum:1,
                    pageSize:20
                },
                processFormat: [],
                toolTypeSelect: [],
                processBaseFormat:[],
                processOtherFormat:[],
                rules: {
                    processName: [
                        { required: true, message: '请输入工艺名称', trigger: 'blur' },
                    ]
                },
                loading: false
            };
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            getData() {
                this.$http.post("/toolType/searchToolTypeList",this.search).then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.toolTypeSelect = res.data.data;
                    }
            });
                this.$http.post("/processBaseFormat/searchBaseFormat").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    this.processBaseFormat = res.data.data;
                }
            });
                this.$http.post("/processBaseFormat/searchFormat").then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.processOtherFormat = []
                    this.processOtherFormat = res.data.data;
                }
            });
            },
            goBack() {
                this.form.processName = ''
                this.form.remark = ''
                this.$router.push({
                    path: "/processList",
                    query: {
                        closeFlag: 1
                    }
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.form.toolTypeSelectList = this.multipleSelection;
            },
            selectFormat(checked,val,index){
                if(checked == true){
                    this.multipleSelection = val;
                    this.form.processOtherFormatList[index] = val
                }else{
                    this.form.processOtherFormatList[index] = ''
                }
            },
            save(){
                this.insert();
            },
            checkFn (row) {
                for (let i = 0; i < this.toolTypeSelect.length; i++) {
                    if (this.toolTypeSelect[i].id === row.id) {
                        if (this.toolTypeSelect[i].defaultModelNumber === true) {
                            this.flag[i] = 1
                        } else if(this.toolTypeSelect[i].defaultModelNumber === false) {
                            this.flag[i] = 0
                        }
                    }
                }
            },
            insert() {
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        for(var i = 0;i<this.form.processOtherFormatList.length;i++){
                            if(this.form.processOtherFormatList[i]==''||this.form.processOtherFormatList[i]==null||typeof(this.form.processOtherFormatList[i])==undefined){
                                this.form.processOtherFormatList.splice(i,1);
                                i=i-1;
                            }
                        }
                        this.loading = true
                        this.$http.post("/process/saveProcess", this.form).then((res) => {
                            if (res.data.code == 1000) {
                                this.$message.success('添加成功');
                                //跳转到列表页面
                                this.$router.push({ path: '/processList'})
                            } else {
                                this.$message.error(res.data.message);
                            }
                            this.loading = false
                        })
                            .catch(err => {
                                this.loading = false
                            })
                    }
                })

            }
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/processInfo' && this.$route.query.works !== 1) {
                    this.form.processOtherFormatList = []
                    this.$refs['form'].resetFields()
                    this.form.remark = ''
                    this.getData()
                }
            }
        }
    };
</script>
<style scoped>
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
</style>
