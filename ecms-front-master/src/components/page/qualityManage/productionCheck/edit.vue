<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form ref="form" class="clear" :model="form" label-width="140px">
                    <el-col :span="6">
                        <el-form-item label="合同编号:">
                            <el-input v-model="form.productBatch" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="生产单号:">
                            <el-input v-model="form.productionNumber" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="生产项次:">
                            <el-input v-model="form.productBatch" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="工厂物料编码:">
                            <el-input v-model="form.factoryMaterialCode" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="生产时间:">
                            <el-input v-model="form.produceDate" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="产品编号:">
                            <el-input v-model="form.materialCode" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="产品名称:">
                            <el-input v-model="form.materialName" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="材料:">
                            <el-input v-model="form.originalMaterial" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="参数:">
                            <el-input v-model="form.materialBomParamValueStr" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="数量:">
                            <el-input v-model="form.completeQty" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="检验比例(%):">
                            <el-input v-model="form.checkPercent" type="number" min="0" max="100" v-on:input="changeNum"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="检验数量:">
                            <el-input v-model="form.checkNum" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="合格数:">
                            <el-input v-model="form.qualifiedNum" id="qualifiedQty" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="不合格数:">
                            <el-input v-model="form.unQualifiedNum" id="unQualifiedQty" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="检测合格率(%):">
                            <el-input v-model="form.checkQualifiedPercent" id="qualifiedPercent" readonly></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="12">
                         <el-form-item label="姓名:" prop="userName">
                             <el-select v-model="form.userName" placeholder="请选择" @change="selectUserName($event)">
                                 <el-option v-for="item in this.userList" :key="item.id" :label="item.name" :value="item.employeeNumber" >
                                 </el-option>
                             </el-select>
                         </el-form-item>
                     </el-col>
                     <el-col :span="12">
                         <el-form-item label="检验时间:">
                             <el-input v-model="form.checkTime"></el-input>
                         </el-form-item>
                     </el-col>-->
                    <el-col :span="6">
                        <el-form-item label="附件:">
                            <template slot-scope="tableData">
                                <input type="file" name="file" @change="setFile" />
                            </template>
                        </el-form-item>
                    </el-col>
                </el-form>
                <!--<div class="handle-box clear">
                    <span class="el-form-item__label">不合格类型:</span>
                    <el-form :data="unqualifiedTypeList" border style="width:100%" height="250">
                        <el-checkbox  v-for="(project,index) in unqualifiedTypeList" :key="index" :offset="1" border @change="checked=>selectFormat(checked,project.unqualifiedType,index)">
                            {{project.unqualifiedType}}
                        </el-checkbox>
                    </el-form>
                </div>-->
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">验收标准</span>
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
                <!--<el-table-column prop="materialNum" label="检测设备编号">
                </el-table-column>-->
                <el-table-column prop="unit" label="单位">
                </el-table-column>
                <el-table-column prop="minValue" label="标准下限值">
                </el-table-column>
                <el-table-column prop="maxValue" label="标准上限值">
                </el-table-column>
                <!--<el-table-column prop="checkResult" label="实测结果">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.checkResult"></el-input>
                    </template>
                </el-table-column>-->
            </el-table>
        </div>
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
                        <el-input v-model="scope.row['key' + (index + 1)]" type="number" @input="changeFlag"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="外观">
                    <template slot-scope="scope">
                        <el-radio v-model="scope.row.radio" label="1" @change="changeFlag">合格</el-radio>
                        <el-radio v-model="scope.row.radio" label="2" @change="changeFlag">不合格</el-radio>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" v-if="this.flag == 1" @click="changeUnNum">检测</el-button>
            <el-button type="primary" v-if="this.flag == 0" @click="saveEdit">保存</el-button>
            <el-button @click="goBack">取 消</el-button>
        </span>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                tableData: [],
                url: '/productAcceptanceDetail/searchAcceptanceDetail',
                checkUrl:'/productionLog/saveCheck',
                formUrl:'/productionLog/getProductionEntity',
                // userList:'',
                search: {
                    productionNumber: '',
                    productBatch: '',
                    materialCode:'',
                    pageNum: 1,
                    pageSize:20,
                    checkStatus:0
                },
                form: {
                    // id: '',
                    productionNumber: '',
                    materialName: '',
                    materialCode:'',
                    materialId: '',
                    productType: '',
                    qualifiedPercent:'',
                    originalMaterial:'',
                    productBatch:'',
                    materialNum: '',
                    qualifiedNum : 0,
                    unQualifiedNum : 0,
                    checkPercent:0,
                    checkNum:0,
                    checkQualifiedPercent:0,
                    checkResult:'',
                    unqualifiedTypeFormList:[]
                },
                file:'',
                item : [],
                unqualifiedTypeList:[],
                unqualifiedTypeFormList:[],
                contentJson: {},
                tableData2: [],
                tableData2Label: [],
                flag: 1,
            }
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
            changeUnNum () {
                if (this.tableData2.length!=0){
                    document.getElementById('unQualifiedQty').value = 0
                    for (let i=0;i<this.tableData2.length;i++){
                        let temp = this.tableData2[i]
                        if (Number(temp.radio)==2){
                            document.getElementById('unQualifiedQty').value = Number(document.getElementById('unQualifiedQty').value)+1
                            continue
                        }
                        for (let j=0;j<this.tableData.length;j++){
                            if (Number(temp['key'+(j+1)])<this.tableData[j].minValue || Number(temp['key'+(j+1)])>this.tableData[j].maxValue) {
                                document.getElementById('unQualifiedQty').value = Number(document.getElementById('unQualifiedQty').value)+1
                                break;
                            }
                        }
                    }
                    document.getElementById('qualifiedQty').value = this.form.completeQty - Number(document.getElementById('unQualifiedQty').value)
                    document.getElementById('qualifiedPercent').value = ((this.form.checkNum-Number(document.getElementById('unQualifiedQty').value))/this.form.checkNum*100)
                    this.form.qualifiedNum = Number(document.getElementById('qualifiedQty').value)
                    this.form.unQualifiedNum = Number(document.getElementById('unQualifiedQty').value)
                    this.form.checkQualifiedPercent = Number(document.getElementById('qualifiedPercent').value).toFixed(2)
                }
                this.flag = 0
            },
            changeFlag(){
                this.flag = 1
            },
            // 获取待检测列表数据
            getData() {
                this.form = this.$route.query.row
                this.$http.post(this.url, {materialId: this.form.materialId}).then((res) => {
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data;
                    }
                })
                //查询不合格类型
                this.$http.post("unqualifiedType/searchList").then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.unqualifiedTypeList = res.data.data;
                    }
                })
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
                /* this.$http.post("/employee/getListEmployee").then(res => {
                     if (res != undefined && res.data.code == 1000) {
                         this.userList = res.data.data;
                     }
                 })*/
            },
            clearData() {
                this.search.productionNumber = '',
                    this.search.productBatch = '',
                    this.search.materialCode = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            selectFormat(checked,val,index){
                if(checked == true){
                    this.multipleSelection = val;
                    this.unqualifiedTypeFormList[index] = val
                }else{
                    this.unqualifiedTypeFormList[index] = ''
                }
            },
            // 保存编辑
            saveEdit() {
                for(var i = 0;i<this.unqualifiedTypeFormList.length;i++){
                    if(this.unqualifiedTypeFormList[i]==''||this.unqualifiedTypeFormList[i]==null||typeof(this.unqualifiedTypeFormList[i])==undefined){
                        this.unqualifiedTypeFormList.splice(i,1);
                        i=i-1;
                    }
                }
                if (this.form.unqualifiedTypeFormList!=[] && this.form.unqualifiedTypeFormList!=""){
                    this.form.unqualifiedTypeFormList = this.unqualifiedTypeFormList
                }else {
                    this.form.unqualifiedTypeFormList = []
                }
                if (this.tableData !=[] && this.tableData!=""){
                    this.form.resultList = this.tableData
                } else{
                    this.form.resultList = []
                }
                var checkNum = this.form.checkNum
                var unQualifiedNum = this.form.unQualifiedNum
                var materialNum = this.form.completeQty
                var qualifiedNum = this.form.qualifiedNum
                let temp = []
                this.tableData2.forEach((item, index) => {
                    temp[index] = JSON.stringify(item)
                })
                this.form.temp = temp
                if (checkNum>materialNum) {
                    this.$message.error("检验数量不能大于总数量")
                }else if(qualifiedNum<0){
                    this.$message.error("合格数不能小于0")
                } else {
                    //验证第几行未填入
                    let arr = [];
                    let labelLength = this.tableData2Label.length
                    for (let i = 0; i < this.form.temp.length; i++) {
                        let a = JSON.parse(this.form.temp[i]);
                        for (let j = 1; j <= labelLength; j++) {
                            if (!a['key' + j]) {
                                arr.push(i + 1);
                                break;
                            }
                        }
                    }
                    if (!this.form.checkPercent) {
                        this.$message.warning('检验比例为必填字段')
                        return
                    } else if (this.form.qualifiedPercent != 0 && this.form.qualifiedPercent == false) {
                        this.$message.warning('检测合格率为空')
                        return
                    }
                    if (arr.length) {
                        arr = arr.join(',')
                        this.$message.warning('检测记录第' + arr + '行有未填写');
                    } else {
                        this.$http.post(this.checkUrl, this.form).then((res) => {
                            if (res.data.code == 1000) {
                                this.$message.success("检测完成");
                                this.goBack();
                            }
                        })
                    }
                }
            },
            goBack(){
                this.$router.push({
                    path: "/production", query: {closeFlag: 1}});
            },
            changeNum(event) {
                if (event>100){
                    this.$message.error("检验比例不能大于100")
                } else {
                    this.form.checkNum = Math.ceil(Number(event * this.form.completeQty / 100))
                    this.tableData2 = []
                    document.getElementById('qualifiedPercent').value = 0
                    this.form.qualifiedPercent = 0
                    for (let i = 0; i < this.form.checkNum; i++ ) {
                        let obj = {...this.contentJson}
                        obj.radio = '1'
                        this.tableData2.push(obj)
                    }
                }
            },
            changeQualifiedNum(event){
                if (event>this.form.checkNum){
                    this.$message.error("不合格数不能大于检测数")
                } else {
                    this.form.qualifiedNum = this.form.completeQty - event
                    this.form.checkQualifiedPercent = (this.form.qualifiedNum/this.form.completeQty*100).toFixed(2)
                }
            },
            setFile(event) {
                this.file = event.target.files[0]; //获取文件
                var windowURL = window.URL || window.webkitURL;
                this.uploadFile('form');
            },
            uploadFile(formName){
                //上传文件
                this.$refs[formName].validate((valid) => {
                    if(valid) {
                        let formdata = new FormData(this.form);
                        formdata.append('file', this.file);
                        let config = {
                            withCredentials : true
                        };
                        this.$axios.post(this.$path + "/materialRepertoryIncomingCheck/uploadFile",formdata,config).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                                this.form.fileUrl = res.data.data
                            }else{
                                this.massage.error("上传失败");
                            }
                        })
                    }
                });
            },
            /*selectUserName(value){
                for(var i=0;i<this.userList.length;i++){
                    if (value == this.userList[i].employeeNumber){
                        this.form.employeeNumber = value;
                        this.form.userName = this.userList[i].name;
                        this.form.employeId = this.userList[i].id;
                        this.form.department = this.userList[i].departmentName
                    }
                }
            }*/
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/productionCheck' && this.$route.query.works !== 1) {
                    let form = this.$route.query.row;
                    this.tableData2 = []
                    this.tableData2Label = []
                    this.form = form;
                    this.flag = 1
                    this.getData();
                }
            },
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
