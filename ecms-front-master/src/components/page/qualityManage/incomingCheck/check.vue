<template>
    <div class="container">
        <div clas="handle-box clear">
            <el-form ref="form" class="clear" :model="form" label-width="140px">
                <el-col :span="6">
                    <el-form-item label="来料检测单号:">
                        <el-input v-model="form.productionNumber" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="收货单号:">
                        <el-input v-model="form.deliveryCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="供应商名称:">
                        <el-input v-model="form.supplierName" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料批次:">
                        <el-input v-model="form.materialBatch" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料编号:">
                        <el-input v-model="form.materialCode" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="物料名称:">
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
                        <el-input v-model="form.paramValue" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="数量:">
                        <el-input v-model="form.inventoryQty" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="检验比例(%):">
                        <el-input
                            v-model="form.checkPercent"
                            type="number"
                            min="0"
                            max="100"
                            v-on:input="changeNum"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="检验数量:">
                        <el-input v-model="form.checkQty" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="合格数:">
                        <el-input id="qualifiedQty" v-model="form.qualifiedQty" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="不合格数:">
                        <el-input id="unqualifiedQty" readonly v-model="form.unqualifiedQty"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="检测合格率(%):">
                        <el-input id="qualifiedPercent" v-model="form.qualifiedPercent" readonly></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="附件:">
                        <template slot-scope="tableData">
                            <input ref="file" type="file" name="file" @change="setFile">
                        </template>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="不合格描述:">
                        <el-input v-model="form.unqualifiedRemark"></el-input>
                    </el-form-item>
                </el-col>
            </el-form>
            <div class="handle-box clear">
                <span class="el-form-item__label">验收标准:</span>
            </div>
            <el-table :data="tables" border style="width: 100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column prop="checkName" label="检测名称"></el-table-column>
                <el-table-column prop="checkMethod" label="检测方式"></el-table-column>
                <el-table-column prop="equipmentName" label="检测设备"></el-table-column>
                <el-table-column prop="equipmentSpecification" label="设备规格"></el-table-column>
                <el-table-column prop="unit" label="单位"></el-table-column>
                <el-table-column prop="minValue" label="标准下限值"></el-table-column>
                <el-table-column prop="maxValue" label="标准上限值"></el-table-column>
            </el-table>
            <div>
                <div class="handle-box clear">
                    <span class="el-form-item__label">检验记录:</span>
                </div>
                <el-table :data="tableData2" border style="width: 100%">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">{{scope.$index+1}}</template>
                    </el-table-column>
                    <el-table-column
                        :key="index"
                        v-for="(item, index) in tableData2Label"
                        :prop="'key' + (index + 1)"
                        :label="item['key' + (index + 1)]"
                    >
                        <template slot-scope="scope">
                            <el-input
                                v-model="scope.row['key' + (index + 1)]"
                                type="text"
                                @input="changeFlag"
                            ></el-input>
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
            <el-button type="primary" v-if="this.flag == 1" @click="changeUnNum">检测</el-button>
            <el-button type="primary" v-if="this.flag == 0" @click="saveEdit">保存</el-button>
            <el-button @click="goBack">取 消</el-button>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            radio: "1",
            tableData: [],
            url: "/productAcceptanceDetail/searchAcceptanceDetail",
            form: {
                paramValue: "",
                productionNumber: "",
                materialName: "",
                materialCode: "",
                materialId: "",
                qualifiedPercent: "",
                originalMaterial: "",
                materialBatch: "",
                inventoryQty: "",
                qualifiedNum: 10,
                unQualifiedNum: 0,
                checkPercent: 0,
                checkQty: 0,
                checkQualifiedPercent: 0,
                resultList: [],
                unqualifiedTypeFormList: [],
                file: "",
                fileUrl: "",
                unqualifiedRemark: "",
                unqualifiedQty: 0,
                qualifiedQty: 0
            },
            file: "",
            item: [],
            unqualifiedTypeList: [],
            unqualifiedTypeFormList: [],
            contentJson: {},
            tableData2: [],
            tableData2Label: [],
            flag: 1
        };
    },
    created() {
        this.form = this.$route.query.row;
        this.getData();
        this.form.unqualifiedQty = 0;
        this.form.qualifiedQty = 0;
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                return d;
            });
        }
    },
    methods: {
        // 获取待检测列表数据
        changeUnNum() {
            if (this.tableData2.length != 0) {
                let unqualifiedQtyVal = document.getElementById(
                    "unqualifiedQty"
                ).value;
                document.getElementById("unqualifiedQty").value = 0;
                for (let i = 0; i < this.tableData2.length; i++) {
                    let temp = this.tableData2[i];
                    if (Number(temp.radio) == 2) {
                        document.getElementById("unqualifiedQty").value =
                            Number(
                                document.getElementById("unqualifiedQty").value
                            ) + 1;
                        continue;
                    }
                    for (let j = 0; j < this.tableData.length; j++) {
                        if (
                            Number(temp["key" + (j + 1)]) <
                                this.tableData[j].minValue ||
                            Number(temp["key" + (j + 1)]) >
                                this.tableData[j].maxValue
                        ) {
                            document.getElementById("unqualifiedQty").value =
                                Number(
                                    document.getElementById("unqualifiedQty")
                                        .value
                                ) + 1;
                            break;
                        }
                    }
                }
                document.getElementById("qualifiedQty").value =
                    this.form.inventoryQty -
                    Number(document.getElementById("unqualifiedQty").value);
                document.getElementById("qualifiedPercent").value =
                    ((this.form.checkQty -
                        Number(
                            document.getElementById("unqualifiedQty").value
                        )) /
                        this.form.checkQty) *
                    100;
                this.form.qualifiedQty = Number(
                    document.getElementById("qualifiedQty").value
                );
                this.form.unqualifiedQty = Number(
                    document.getElementById("unqualifiedQty").value
                );
                this.form.qualifiedPercent = Number(
                    document.getElementById("qualifiedPercent").value
                ).toFixed(2);
            }
            this.flag = 0;
        },
        changeFlag() {
            this.flag = 1;
        },
        getData() {
            this.$http
                .post(this.url, { materialId: this.form.materialId })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data;
                    }
                });
            //查询不合格类型
            this.$http.post("unqualifiedType/searchList").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.unqualifiedTypeList = res.data.data;
                }
            });
            this.$http
                .post("productAcceptance/searchAccpetDetailByMaterialId", {
                    materialId: this.form.materialId
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        let obj = JSON.parse(res.data.data.contentJson);
                        let i = 0;
                        for (let key in obj) {
                            i++;
                            this.contentJson["key" + i] = "";
                            let o = {};
                            o["key" + i] = key;
                            this.tableData2Label.push(o);
                        }
                    }
                });
        },
        clearData() {
            (this.search.productionNumber = ""),
                (this.search.productBatch = ""),
                (this.search.materialCode = "");
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        selectFormat(checked, val, index) {
            if (checked == true) {
                this.multipleSelection = val;
                this.unqualifiedTypeFormList[index] = val;
            } else {
                this.unqualifiedTypeFormList[index] = "";
            }
        },
        // 保存编辑
        saveEdit() {
            for (var i = 0; i < this.unqualifiedTypeFormList.length; i++) {
                if (
                    this.unqualifiedTypeFormList[i] == "" ||
                    this.unqualifiedTypeFormList[i] == null ||
                    typeof this.unqualifiedTypeFormList[i] == undefined
                ) {
                    this.unqualifiedTypeFormList.splice(i, 1);
                    i = i - 1;
                }
            }
            if (
                this.form.unqualifiedTypeFormList != [] &&
                this.form.unqualifiedTypeFormList != ""
            ) {
                this.form.unqualifiedTypeFormList = this.unqualifiedTypeFormList;
            } else {
                this.form.unqualifiedTypeFormList = [];
            }
            if (this.tableData != [] && this.tableData != "") {
                this.form.resultList = this.tableData;
            } else {
                this.form.resultList = [];
            }
            var checkNum = this.form.checkNum;
            var unQualifiedNum = this.form.unQualifiedNum;
            var materialNum = this.form.completeQty;
            var qualifiedNum = this.form.qualifiedNum;

            let temp = [];
            this.tableData2.forEach((item, index) => {
                temp[index] = JSON.stringify(item);
            });
            this.form.temp = temp;
            if (checkNum > materialNum) {
                this.$message.error("检验数量不能大于总数量");
            } else if (qualifiedNum < 0) {
                this.$message.error("合格数不能小于0");
            } else {
                //验证第几行未填入
                let arr = [];
                let labelLength = this.tableData2Label.length;
                for (let i = 0; i < this.form.temp.length; i++) {
                    let a = JSON.parse(this.form.temp[i]);
                    for (let j = 1; j <= labelLength; j++) {
                        if (!a["key" + j]) {
                            arr.push(i + 1);
                            break;
                        }
                    }
                }

                if (!this.form.checkPercent) {
                    this.$message.warning("检验比例为必填字段");
                    return;
                } else if (
                    this.form.qualifiedPercent != 0 &&
                    this.form.qualifiedPercent == false
                ) {
                    this.$message.warning("检测合格率为空");
                    return;
                }
                if (arr.length) {
                    arr = arr.join(",");
                    this.$message.warning("检测记录第" + arr + "行有未填写");
                } else {
                    this.$http
                        .post(
                            "materialRepertoryIncomingCheck/saveIncomingCheckout",
                            this.form
                        )
                        .then(res => {
                            if (res.data.code == 1000) {
                                this.$message.success("检测完成");
                                this.goBack();
                            }
                        });
                }
            }
        },
        goBack() {
            this.$router.push({
                path: "/incomingInspection",
                query: { closeFlag: 1 }
            });
        },
        uploadFile(formName) {
            //上传文件
            this.$refs[formName].validate(valid => {
                if (valid) {
                    let formdata = new FormData(this.form);
                    formdata.append("file", this.file);
                    let config = {
                        withCredentials: true
                    };
                    this.$axios
                        .post(
                            this.$path +
                                "/materialRepertoryIncomingCheck/uploadFile",
                            formdata,
                            config
                        )
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.form.fileUrl = res.data.data;
                            } else {
                                this.massage.error("上传失败");
                            }
                        });
                }
            });
        },
        changeNum(event) {
            if (event > 100) {
                this.$message.error("检验比例不能大于100");
            } else {
                this.form.checkQty = Math.ceil(
                    Number((event * this.form.inventoryQty) / 100)
                );
                this.tableData2 = [];
                document.getElementById("qualifiedPercent").value = 0;
                this.form.qualifiedPercent = 0;
                for (let i = 0; i < this.form.checkQty; i++) {
                    let obj = { ...this.contentJson };
                    obj.radio = "1";
                    this.tableData2.push(obj);
                }
            }
        },
        setFile(event) {
            console.log(event);
            this.file = event.target.files[0]; //获取文件
            var windowURL = window.URL || window.webkitURL;
            this.uploadFile("form");
        }
    },
    watch: {
        $route(to, form) {
            if (to.path == "/incomingCheck" && this.$route.query.works !== 1) {
                this.file = "";
                this.$refs.file.type = "text";
                this.$refs.file.type = "file";
                let form = this.$route.query.row;
                this.tableData2Label = [];
                this.tableData2 = [];
                this.form = form;
                this.flag = 1;
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
