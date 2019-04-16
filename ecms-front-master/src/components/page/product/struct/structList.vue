<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-demo"
                    mode="horizontal"
                    @select="handleSelect"
                >
                    <el-menu-item index="/structList">产品结构</el-menu-item>
                    <el-menu-item index="/structSelectorList">结构参数</el-menu-item>
                    <el-menu-item index="/producttype">产品类型</el-menu-item>
                </el-menu>
            </div>
            <div class="handle-box"></div>
            <!-- 查询 -->
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="产品名称:">
                    <el-input v-model="search.productName"></el-input>
                </el-form-item>
                <el-form-item label="产品类型:">
                    <el-select v-model="search.productTypeId">
                        <el-option
                            v-for="item in productTypeList"
                            :key="item.id"
                            :value="item.id"
                            :label="item.type"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="search.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <el-button round @click="searchLike">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <div class="handle-box">
                <el-button round type="primary" @click="handleAdd">新增产品结构</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column label="产品名称" prop="productName" sortable></el-table-column>
                <el-table-column label="产品类型" prop="productType"></el-table-column>
                <el-table-column label="单位" prop="unitName"></el-table-column>
                <el-table-column label="状态" prop="statusDisplay"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" @click="handleView(scope.$index, scope.row)">预览</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :page-size="20"
                    @current-change="handleCurrentChange"
                    layout="total,prev, pager, next"
                    :total="pages"
                ></el-pagination>
            </div>
        </div>

        <!-- 新增表单 -->
        <el-dialog title="产品结构详情" :visible.sync="addVisible" width="50%">
            <el-form ref="addForm" :rules="rules" :model="addForm" label-width="100px">
                <el-form-item label="产品名称:" prop="productName">
                    <el-input v-model="addForm.productName"></el-input>
                </el-form-item>
                <el-form-item label="产品类型:" prop="productTypeId">
                    <el-select v-model="addForm.productTypeId">
                        <el-option
                            v-for="item in productTypeList"
                            :key="item.id"
                            :value="item.id"
                            :label="item.type"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="单位" prop="unitName">
                    <el-input v-model="addForm.unitName"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="addForm.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <hr>

                <el-button size="small" @click="addSelector">添加结构</el-button>
                <el-table :data="selectTable" border style="width:100%" :show-header="false">
                    <el-table-column label="参数" prop="selectorName">
                        <template>
                            <el-form-item prop="selectId" :rules="rules.selectId" label-width="0">
                                <el-select v-model="selectId">
                                    <el-option
                                        v-for="item in selectorList"
                                        :key="item.id"
                                        :value="item.id"
                                        :label="item.selectorDisplayName"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="delSelector(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addForm')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑表单 -->
        <el-dialog title="产品结构详情" :visible.sync="editVisible" width="50%">
            <el-form ref="editForm" :rules="rules" :model="editForm" label-width="100px">
                <el-form-item label="产品名称:" prop="productName">
                    <el-input v-model="editForm.productName"></el-input>
                </el-form-item>
                <el-form-item label="产品类型:" prop="productTypeId">
                    <el-select v-model="editForm.productTypeId">
                        <el-option
                            v-for="item in productTypeList"
                            :key="item.id"
                            :value="item.id"
                            :label="item.type"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="单位" prop="unitName">
                    <el-input v-model="editForm.unitName"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="editForm.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <hr>
                <el-button size="small" @click="addSelector">添加结构</el-button>
                <el-table :data="selectTable" border style="width:100%" :show-header="false">
                    <el-table-column label="参数" prop="selectorName">
                        <template slot-scope="scope">
                            <el-form-item prop="selectId" :rules="rules.selectId" label-width="0">
                                <el-select v-model="scope.row.selectId">
                                    <el-option
                                        v-for="item in selectorList"
                                        :key="item.id"
                                        :value="item.id"
                                        :label="item.selectorDisplayName"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="delSelector(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="modify('editForm')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 预览表单 -->
        <el-dialog title="产品结构预览" :visible.sync="viewVisible" width="50%">
            <el-form :model="viewForm" ref="viewForm" label-width="100px">
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="产品类型" prop="productType">{{viewForm.productType}}</el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="产品名称" prop="productStruct">{{viewForm.productName}}</el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="单位" prop="unitName">{{viewForm.unitName}}</el-form-item>
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
                                <el-select
                                    v-model="scope.row.structSelecteds"
                                    @change="handleOption"
                                >
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
                                            <el-form-item
                                                :label="optionScope.row.selectorDisplayName"
                                            >
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
            </el-form>
            <el-button @click="viewVisible=false">关闭</el-button>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {
            idx: 0,
            tableData: [],
            url: "/productStruct/searchStructPage",
            editVisible: false,
            addVisible: false,
            viewVisible: false,
            rules: {
                productName: [
                    {
                        required: true,
                        message: "请输入产品名称",
                        trigger: "blur"
                    }
                ],
                productTypeId: [
                    {
                        required: true,
                        message: "请选择产品类型",
                        trigger: "change"
                    }
                ],
                selectId: [
                    {
                        required: true,
                        message: "请选择结构类型",
                        trigger: "change"
                    }
                ]
            },
            pages: 1,
            search: {
                status: "",
                productName: "",
                productType: "",
                pageNum: 1,
                pageSize: 20
            },
            editForm: {
                id: "",
                status: 0,
                productName: "",
                productType: "",
                unitName: ""
            },
            addForm: {
                id: "",
                status: 1,
                productName: "",
                productType: "",
                unitName: ""
            },
            productTypeList: [],
            productTypeFilter: {},
            selectTable: [],
            selectorList: {},
            productTypeList: [],
            productStructList: [],
            structParameter: [],
            structSelecteds: [],
            selectedStruct: {},
            viewForm: {
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
    created() {
        this.$http.post("/productType/searchList").then(res => {
            if (res != undefined && res.data.code == 1000) {
                this.productTypeList = res.data.data;
            }
        });
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                let status = d.status;
                d.statusDisplay = status == 1 ? "启用" : "停用";
                this.productTypeFilter = d.productTypeInfo;
                let productType = this.productTypeList.filter(type => {
                    var currentType;
                    if (
                        d.productTypeInfo != null &&
                        type.id == d.productTypeInfo.id
                    ) {
                        currentType = type;
                    }
                    return currentType;
                });
                if (productType.length > 0) {
                    d.productType = productType[0].type;
                }

                return d;
            });
        }
    },
    methods: {
        handleSelect(ok) {
            this.$router.push({
                path: ok
            });
        },
        //获取编辑表单数据
        handleEdit(index, row) {
            this.idx = index;
            var item = this.tableData[index];
            this.editForm = {
                id: item.id,
                productName: item.productName,
                productTypeId: item.productTypeInfo.id,
                unitName: item.unitName,
                status: item.status + ""
            };

            this.selectTable = item.parameters;
            this.$http
                .post("/ProductStructSelector/searchList", {})
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.selectorList = res.data.data;
                    }
                });
            this.editVisible = true;
        },
        handleView(index, row) {
            this.$http
                .post("/productStruct/searchStructPage", {
                    pageNum: 1,
                    pageSize: 20,
                    id: row.id
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.viewForm.productType = row.productTypeInfo.type;
                        this.viewForm.productName = row.productName;
                        this.viewForm.unitName = row.unitName;
                        this.productStructList = res.data.data.list;
                        this.structParameter = res.data.data.list[0].parameters;
                    }
                });
            this.viewVisible = true;
        },
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
        handleChange(value) {},
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
            this.getData();
        },
        searchLike(){
            this.search.pageNum = 1;
            this.getData();
        },
        //获取列表数据
        getData() {
            this.$http.post(this.url, this.search).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.tableData = res.data.data.list;
                    this.pages = res.data.data.total;
                }
            });
        },
        //清除查询表单
        clearData() {
            (this.search.productName = ""),
                (this.search.productType = ""),
                (this.search.status = "");
        },
        //获取添加表单数据
        handleAdd() {
            this.addForm = { status: "1" };
            this.addVisible = true;
            this.$http
                .post("/ProductStructSelector/searchList", {})
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.selectorList = res.data.data;
                    }
                });
        },
        //更新记录状态字
        updateStatus(index, row) {
            this.$http
                .post("/productStruct/updateStatus", {
                    id: row.id
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        row.status = row.status == 0 ? 1 : 0;
                        this.$message.success("操作成功");
                    } else {
                        this.$message.error(res.data.message);
                    }
                });
        },
        //追加选择项
        addSelector() {
            this.selectTable.push({});
        },
        //移除选择项
        delSelector(index) {
            let selectItem = this.selectTable[index];

            this.selectTable.splice(index, 1);
        },
        //保存添加表单
        insert(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.addForm.params = this.selectTable;
                    this.$http
                        .post("/productStruct/saveStruct", this.addForm)
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.addVisible = false;
                                this.$message.success("新增成功");
                            } else {
                                this.$message.error(res.data.message);
                            }
                        });
                }
            });
        },
        //保存编辑表单
        modify(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.editForm.params = this.selectTable;
                    this.$http
                        .post("/productStruct/modifyStruct", this.editForm)
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.editVisible = false;
                                this.$message.success("修改成功");
                            } else {
                                this.$message.error(res.data.message);
                            }
                        });
                }
            });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/structList") {
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

.marginTop {
    margin-top: 10px;
    margin-bottom: 5px;
}
.marginBottom {
    margin-top: 5px;
    margin-bottom: 10px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.del-dialog-cnt {
    font-size: 16px;
    text-align: center;
}
.text {
    font-size: 14px;
    color: #606266;
    margin-right: 30px;
}
</style>