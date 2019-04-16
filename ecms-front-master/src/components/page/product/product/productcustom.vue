<template>
    <div class="container">
        <el-form :model="form" ref="form" label-width="100px">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="产品类型" prop="productType">
                        <el-select v-model="form.productType" @change="handleTypeSelect">
                            <el-option
                                v-for="item in productTypeList"
                                :key="item.id"
                                :value="item.id"
                                :label="item.type"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="产品名称" prop="productStruct">
                        <el-select v-model="form.productStruct" @change="handleStructSelect">
                            <el-option
                                v-for="item in productStructList"
                                :key="item.id"
                                :value="item.id"
                                :label="item.productName"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="单位" prop="unitName">
                        <el-input v-model="form.unitName"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="数量" prop="qty">
                        <el-input-number :min="0" v-model="form.qty"></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="交货时间" prop="deliveryDate">
                        <el-date-picker v-model="form.deliveryDate"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="8"></el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="其他要求" prop="requireDesc">
                        <el-input type="textarea" rows="2" v-model="form.requireDesc"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr>
            <span class="text">结构信息</span>
            <!-- <el-button size="small" @click="addStruct">添加结构</el-button> -->
            <el-table style="width:100%" :data="structParameter" border :show-header="false">
                <el-table-column label="结构显示名称">
                    <template slot-scope="scope">
                        <el-form-item
                            :label="scope.row.selectorInfo.selectorDisplayName"
                            prop="structId"
                        >
                            <el-select v-model="scope.row.structSelecteds" @change="handleOption">
                                <el-option
                                    v-for="item in scope.row.selectorInfo.options"
                                    :key="item.id"
                                    :value="item.id"
                                    :label="item.optionValue"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <div v-show="scope.row.subSelector">
                            <el-table
                                style="width:100%"
                                :data="scope.row.selection"
                                border
                                :show-header="false"
                            >
                                <el-table-column>
                                    <template slot-scope="optionScope">
                                        <el-form-item :label="optionScope.row.selectorDisplayName">
                                            <el-select
                                                v-model="optionScope.row.structSelecteds"
                                                @change="handleOptionSelected"
                                            >
                                                <el-option
                                                    v-for="item in optionScope.row.options"
                                                    :key="item.id"
                                                    :value="item.id"
                                                    :label="item.optionValue"
                                                ></el-option>
                                            </el-select>
                                        </el-form-item>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="test()">测试</el-button>
        </el-form>
    </div>
</template>
<script>
export default {
    data() {
        return {
            productTypeList: [],
            productStructList: [],
            structParameter: [],
            structSelecteds: [],
            selectedStruct: {},
            form: {
                productType: "",
                productStruct: "",
                unitName: "",
                qty: 0,
                deliveryDate: "",
                requireDesc: "",
                structSelecteds: []
            }
        };
    },
    computed: {},
    created() {
        this.getData();
    },
    methods: {
        test() {
            console.log(this.structSelecteds, this.structParameter, this.form);
        },
        handleOptionSelected(item) {},
        handleOption(item) {
            let optionId = item,
                selectList = [];
            this.structParameter.forEach(param => {
                let optionSelected = param.selectorInfo.options.filter(
                    option => {
                        if (option.id == item) {
                            option.relationInfos.forEach(element => {
                                selectList.push(element.selectorInfo);
                            });
                            return option;
                        }
                    }
                );
                if (
                    optionSelected.length > 0 &&
                    param.id == optionSelected[0].selectorId
                ) {
                    if (selectList.length > 0) {
                        param.subSelector = true;
                        param.selection = selectList;
                    }
                    param.structSelecteds = item;
                }
                return param;
            });
        },
        getData() {
            this.$http.post("/productType/searchList").then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.productTypeList = res.data.data;
                }
            });
        },
        handleTypeSelect(selectedItem) {
            this.$http
                .post("/productStruct/searchStructPage", {
                    pageNum: 1,
                    pageSize: 20,
                    productTypeId: selectedItem
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.form.productStruct = "";
                        this.form.unitName = "";
                        this.productStructList = res.data.data.list;
                    }
                });
        },
        handleStructSelect(selectItem) {
            let structSelected = this.productStructList.filter(struct => {
                //this.selectedStruct
                if (struct.id == selectItem) {
                    this.selectedStruct = struct;
                }
                return this.selectedStruct;
            });
            if (this.selectedStruct != undefined) {
                this.form.unitName = this.selectedStruct.unitName;
                this.structParameter = this.selectedStruct.parameters;
            }
        },
        addStruct() {
            this.structTable.push({});
        }
    }
};
</script>
<style>
</style>