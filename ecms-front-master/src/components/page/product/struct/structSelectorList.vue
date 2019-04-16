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
            <el-form :inline="true" :model="search" class="demo-form-inline">
                <el-form-item label="产品结构名称:">
                    <el-input v-model="search.selectorName"></el-input>
                </el-form-item>
                <el-form-item label="产品结构显示名称:">
                    <el-input v-model="search.selectorDisplayName"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="search.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <el-button round @click="getData">查询</el-button>
                <el-button round @click="clearData">清空</el-button>
            </el-form>
            <div class="handle-box">
                <el-button round type="primary" @click="add">新增产品结构</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                    <template slot-scope="scope">{{scope.$index+1}}</template>
                </el-table-column>
                <el-table-column label="产品结构名称" prop="selectorName" sortable></el-table-column>
                <el-table-column label="产品结构显示名称" prop="selectorDisplayName"></el-table-column>
                <el-table-column label="状态" prop="statusDisplay"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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

        <!-- 编辑弹出框 -->
        <el-dialog title="产品结构详情" :visible.sync="editVisible" width="70%">
            <el-form ref="editform" :rules="rules" :model="editform" label-width="150px">
                <el-form-item label="产品结构名称:" prop="selectorName">
                    <el-input v-model="editform.selectorName"></el-input>
                </el-form-item>
                <el-form-item label="产品结构显示名称:" prop="selectorDisplayName">
                    <el-input v-model="editform.selectorDisplayName"></el-input>
                </el-form-item>
                <el-form-item label="备注:" prop="comment">
                    <el-input type="textarea" v-model="editform.comment"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="editform.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <hr>
                <el-button size="small" @click="addOption">添加参数</el-button>
                <el-table :data="optionTable" border style="width:100%">
                    <el-table-column label="参数值" prop="optionValue">
                        <template slot-scope="scope">
                            <template v-if="scope.row.type=='option'">
                                <el-input v-model="scope.row.optionValue" style="width:unset"></el-input>
                                <el-button size="small" @click="delOption(scope.$index)">删除</el-button>
                                <el-button
                                    size="small"
                                    @click="addSubSelector('editform',scope.$index)"
                                >添加子级选择器</el-button>
                            </template>
                            <template v-else-if="scope.row.type=='select'">
                                <el-table
                                    :data="scope.row.subSelectorList.subList"
                                    :show-header="false"
                                    border
                                >
                                    <el-table-column>
                                        <template slot-scope="scope_select">
                                            <el-select
                                                v-model="scope_select.row.subSelectorId"
                                                style="margin-left:4em;"
                                            >
                                                <el-option
                                                    v-for="item in selectorList"
                                                    :key="item.id"
                                                    :value="item.id"
                                                    :label="item.selectorDisplayName"
                                                ></el-option>
                                            </el-select>
                                            <el-button
                                                size="small"
                                                @click="delSubSelector(scope.row, scope_select.$index)"
                                            >删除</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </template>
                        </template>
                    </el-table-column>
                    
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="modify('editform')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑弹出框 -->
        <!-- 添加弹出框 -->
        <el-dialog title="产品结构详情" :visible.sync="addVisible" width="70%">
            <el-form ref="addform" :rules="rules" :model="addform" label-width="150px">
                <el-form-item label="产品结构名称:" prop="selectorName">
                    <el-input v-model="addform.selectorName"></el-input>
                </el-form-item>
                <el-form-item label="产品结构显示名称:" prop="selectorDisplayName">
                    <el-input v-model="addform.selectorDisplayName"></el-input>
                </el-form-item>
                <el-form-item label="备注:" prop="comment">
                    <textarea v-model="addform.comment"></textarea>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="addform.status" placeholder="请选择">
                        <el-option value="1" label="启用"></el-option>
                        <el-option value="0" label="停用"></el-option>
                    </el-select>
                </el-form-item>
                <hr>
                <el-button size="small" @click="addOption">添加参数</el-button>
                <el-table :data="optionTable" border style="width:100%">
                    <el-table-column label="参数值" prop="optionValue">
                        <template slot-scope="scope">
                            <template v-if="scope.row.type=='option'">
                                <el-input v-model="scope.row.optionValue"></el-input>
                            </template>
                            <template v-else-if="scope.row.type=='select'">
                                <el-select v-model="scope.row.subSelector" style="margin-left:4em;">
                                    <el-option
                                        v-for="item in selectorList"
                                        :key="item.id"
                                        :value="item.id"
                                        :label="item.selectorDisplayName"
                                    ></el-option>
                                </el-select>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <template v-if="scope.row.type=='option'">
                                <el-button size="small" @click="delOption(scope.$index)">删除</el-button>
                                <el-button
                                    size="small"
                                    @click="addSubSelector('addform', scope.$index)"
                                >添加子级选择器</el-button>
                            </template>
                            <template v-else-if="scope.row.type=='select'">
                                <el-button size="small" @click="delSubSelector(scope.$index)">删除</el-button>
                            </template>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert('addform')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 添加弹出框 -->
    </div>
</template>

<script>
export default {
    data() {
        return {
            idx:0,
            tableData: [],
            optionTable: [],
            url: "/ProductStructSelector/searchSelectorPage",
            editVisible: false,
            addVisible: false,
            rules: {
                selectorName: [
                    {
                        required: true,
                        message: "请输入产品结构名称",
                        trigger: "blur"
                    }
                ],
                selectorDisplayName: [
                    {
                        required: true,
                        message: "请输入产品结构显示名称",
                        trigger: "blur"
                    }
                ]
            },
            pages: 1,
            search: {
                status: "",
                selectorName: "",
                selectorDisplayName: "",
                pageNum: 1,
                pageSize: 20
            },
            editform: {
                id: "",
                status: "",
                selectorName: "",
                selectorDisplayName: "",
                comment: ""
            },
            addform: {
                status: "",
                selectorName: "",
                selectorDisplayName: "",
                comment: ""
            },
            selectorList: []
        };
    },
    created() {
        this.getData();
    },
    computed: {
        tables() {
            return this.tableData.filter(d => {
                let status = d.status;
                d.statusDisplay = status == 1 ? "启用" : "停用";
                return d;
            });
        }
    },
    methods: {
        //切换页面标签
        handleSelect(ok) {
            this.$router.push({
                path: ok
            });
        },
        //获取编就表单数据
        handleEdit(index, row) {
            this.idx = index;
            var item = this.tableData[index];
            this.$http
                .post("/ProductStructSelector/excludeSearch", {
                    id: item.id
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.selectorList = res.data.data;
                    }
                });
            this.editform = {
                id: item.id,
                selectorName: item.selectorName,
                selectorDisplayName: item.selectorDisplayName,
                comment: item.comment,
                status: item.status
            };
            for (var i = 0; i < item.options.length; i++) {
                if (item.options[i].type == "option") {
                    if (
                        item.options[i + 1] == undefined ||
                        item.options[i + 1].type == "option"
                    ) {
                        item.options.splice(i + 1, 0, { type: "select" });
                        if (item.options[i + 1].subSelectorList == undefined) {
                            item.options[i + 1].subSelectorList = {
                                optionId: item.options[i].id,
                                optionIndex: i,
                                subList: []
                            };
                        }
                        this.$http
                            .post("/ProductStructRelation/searchList", {
                                optionId: item.options[i].id
                            })
                            .then(res => {
                                if (res != undefined && res.data.code == 1000) {
                                    if (res.data.data.length > 0) {
                                        for (
                                            var k = 0;
                                            k < res.data.data.length;
                                            k++
                                        ) {
                                            for (
                                                var j = 0;
                                                j < item.options.length;
                                                j++
                                            ) {
                                                if (
                                                    item.options[j].type ==
                                                        "select" &&
                                                    item.options[j]
                                                        .subSelectorList
                                                        .optionId ==
                                                        res.data.data[k]
                                                            .optionId
                                                ) {
                                                    item.options[
                                                        j
                                                    ].subSelectorList.subList.push(
                                                        {
                                                            subSelectorId:
                                                                res.data.data[k]
                                                                    .selectId
                                                        }
                                                    );
                                                }
                                            }
                                        }
                                    } else {
                                    }
                                    // this.optionTable = item.options;
                                }
                            });
                    }
                }
            }
            this.optionTable = item.options;
            this.editVisible = true;
        },
        handleChange(value) {},
        // 分页导航
        handleCurrentChange(val) {
            this.search.pageNum = val;
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
        //清除查询表单内容
        clearData() {
            (this.search.selectorName = ""),
                (this.search.selectorDisplayName = ""),
                (this.search.status = "");
        },
        //获取添加表单数据
        add() {
            this.addform = { status: "1" };
            this.optionTable = [];
            this.addVisible = true;
        },
        //更新记录状态字
        updateStatus(index, row) {
            this.$http
                .post("/ProductStructSelector/updateStatus", {
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
        //追加参数
        addOption() {
            this.optionTable.push({
                type: "option",
                optionValue: ""
            });
        },
        //移除参数
        delOption(index) {
            let optionItem = this.optionTable[index];
            this.$http
                .post("/ProductStructOption/delete", { id: optionItem.id })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                    }
                });
            this.optionTable.splice(index, 1);
        },
        //移除子级结构
        delSubSelector(optionRow, index) {
            console.log(
                optionRow.subSelectorList.subList[index],
                optionRow.subSelectorList.optionId
            );
            this.$http.post("/ProductStructRelation/deleteByOptionSelectId", {
                optionId: optionRow.subSelectorList.optionId,
                selectorId:
                    optionRow.subSelectorList.subList[index].subSelectorId
            });
            optionRow.subSelectorList.subList.splice(index, 1);
            if (optionRow.subSelectorList.subList.length == 0) {
                this.optionTable.splice(
                    optionRow.subSelectorList.optionIndex + 1,
                    1
                );
            }
        },
        //追加子级结构
        addSubSelector(formName, index) {
            let optionItem = this.optionTable[index];
            this.$http
                .post("/ProductStructSelector/excludeSearch", {
                    id: this.$refs[formName].model.id
                })
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.selectorList = res.data.data;
                    }
                });
            if (
                this.optionTable[index + 1] == undefined ||
                this.optionTable[index + 1].type == "option"
            ) {
                this.optionTable.splice(index + 1, 0, { type: "select" });
                if (this.optionTable[index + 1].subSelectorList == undefined) {
                    this.optionTable[index + 1].subSelectorList = {
                        optionId: optionItem.id,
                        optionIndex: index,
                        subList: []
                    };
                }
            }
            this.optionTable[index + 1].subSelectorList.subList.push({});
        },
        // 保存添加表单
        insert(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.addform.optionList = this.optionTable;
                    this.$http
                        .post(
                            "/ProductStructSelector/saveStructSelector",
                            this.addform
                        )
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.addVisible = false;
                                this.$message.success("新增成功");
                            } else {
                                this.$message.error(res.data.message);
                            }
                        });
                } else {
                    this.$message.error("带*为必填项");
                    return false;
                }
            });
        },
        //参数项过滤器
        optionFilter(item) {
            return item.type == "option";
        },
        //子级选择器过滤器
        selectFilter(item) {
            return item.type == "select";
        },
        //保存编辑表单
        modify(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.editform.optionList = this.optionTable.filter(
                        this.optionFilter
                    );
                    this.editform.selectList = [];
                    var selectList = this.optionTable.filter(this.selectFilter);
                    selectList.forEach(element => {
                        var subSelectList = element.subSelectorList.subList;
                        for (var i = 0; i < subSelectList.length; i++) {
                            this.editform.selectList.push({
                                optionId: element.subSelectorList.optionId,
                                optionIndex:
                                    element.subSelectorList.optionIndex,
                                selectId: subSelectList[i].subSelectorId
                            });
                        }
                    });
                    this.$http
                        .post(
                            "/ProductStructSelector/modifyStructSelector",
                            this.editform
                        )
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.editVisible = false;
                                this.$message.success("修改成功");
                            } else {
                                this.$message.error(res.data.message);
                            }
                        });
                } else {
                    this.$message.error("带*为必填项");
                    return false;
                }
            });
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/structSelectorList") {
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
