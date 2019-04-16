<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop"/>
            <el-button size="small" >参数规格</el-button>
            <el-button type="primary" size="small" @click="$router.push('/productParameterName');" >参数名称</el-button>
            <hr class="marginTop"/>
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="规格名称:">
                      <el-input v-model="search.name"></el-input>
                    </el-form-item>
                    <el-form-item label="参数名称:">
                      <el-input v-model="search.parameterName"></el-input>
                    </el-form-item>
                    <el-form-item label="状态:">
                      <el-select class="minorSelect" v-model="search.status" clearable placeholder="请选择">
                            <el-option
                            v-for="item in statusArray"
                            :key="item.description"
                            :label="item.description"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button @click="searchLike">搜索</el-button>
                </el-form>
            <hr class="marginTop" style="margin-top:-12px;"/>
            <div class="handle-box">
                <span class="text">参数规格</span>
                <el-button @click="addNewParameter">新增</el-button>
            </div>
            <el-table :data="tables" border stripe style="width: 100%">
                <el-table-column prop="id" label="规格编号" sortable/>
                <el-table-column prop="name" label="规格名称"/>
                <el-table-column prop="parameterName" label="参数名称"/>
                <el-table-column prop="parameterNumber" label="参数个数"/>
                <el-table-column prop="createdDate" label="创建日期"/>
                <el-table-column prop="owner" label="创建人"/>
                <el-table-column prop="updatedDate" label="维护日期"/>
                <el-table-column prop="maintainer" label="维护人"/>
                <el-table-column prop="statusDisplay" label="状态"/>
                <el-table-column label="操作" width="250">
                    <template slot-scope="scope">
                        <el-button size="small" @click="updateStatus(scope.$index, scope.row)">
                            <font v-if="scope.row.status=='1'">停用</font>
                            <font v-else>启用</font>
                        </el-button>
                        <el-button size="small" @click="detail(scope.$index, scope.row)">
                            明细
                        </el-button>
                        <el-button size="small" @click="updateInfo(scope.$index, scope.row)">
                            修改
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="search.pageSize" @current-change="handleCurrentChange" layout="prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="新增" :visible.sync="editVisible" width="50%">
            <el-form :inline="true" ref="form" :model="form" label-width="100px" :rules="rules">
                <el-form-item label="规则名称:" prop="name">
                    <el-input v-if="editFlag" v-model="form.name"></el-input>
                    <el-input v-else readonly v-model="form.name"></el-input>
                </el-form-item>
                <el-button v-if="editFlag" size="small" @click="addParameter()">添加参数</el-button>
                <el-table :data="dynamicParameters" border style="width:100%" height="250">
                    <el-table-column prop="id" label="序号" sortable>
                        <template slot-scope="scope">
                            {{scope.$index+1}}
                        </template>
                    </el-table-column>
                    <el-table-column label="参数名称">
                        <template slot-scope="scope">
                            <span v-if="!editFlag">{{ scope.row.parameterName }}</span>
                            <el-select @change="(value) => selectChange(value, scope.row)" v-if="editFlag" class="minorSelect" v-model="scope.row.parameterName" clearable placeholder="请选择">
                                <el-option
                                v-for="item in parameterList"
                                :key="item.id"
                                :label="item.name"
                                :value="item">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="单位">
                        <template slot-scope="scope">
                            <span>{{ scope.row.parameterUnit }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column v-if="editFlag" label="操作">
                        <template slot-scope="scope">
                            <el-button size="small" @click="removeParam(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <span v-if="editFlag" slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="save('form')">保 存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                dynamicParameters: [],
                parameterList: [],
                statusArray: [
                    {
                        value: 0,
                        description: '停用'
                    },
                    {
                        value: 1,
                        description: '启用'
                    }
                ],
                editVisible: false,
                editFlag: true,
                total: 1,
                search: {
                    name: '',
                    unit: '',
                    pageNum: 1,
                    pageSize: 20
                },
                form: {
                    id: '',
                    name: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入规格名称', trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.getData();
        },
        computed: {
            tables() {
                return this.tableData.filter(d => {
                    d.statusDisplay = d.status == 1 ? '启用' : '停用';
                    return d;
                });
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.search.pageNum = val;
                this.getData();
            },
            addNewParameter() {
                this.editFlag = true;
                this.editVisible = true;
                this.dynamicParameters = [];
                this.form = {name:''};
                this.addParameter();
                this.getParameterNameList();
            },
            getParameterNameList() {
                this.$http.get('/productParameterName/get').then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.parameterList = res.data.data;
                    }
                })
            },
            addParameter() {
                let param = {
                    parameterName: "",
                    parameterUnit: ""
                };
                this.dynamicParameters.push(param);
            },
            selectChange(value, row) {
                row.parameterUnit = value.unit;
                row.parameterName = value.name;
            },
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            getData() {
                this.$http.post('/productParameterSpecification/search', this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.total = res.data.data.total;
                    }
                })
            },
            updateStatus(index, row) {
                this.$http.post('/productParameterSpecification/status/update', {
                    id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.getData();
                        this.$message.success('操作成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            detail(index, row) {
                this.editFlag = false;
                this.assembleInfo(row);
            },
            updateInfo(index, row) {
                this.editFlag = true;
                this.form.id = row.id;
                this.getParameterNameList();
                this.assembleInfo(row);
            },
            assembleInfo(row) {
                this.dynamicParameters = [];
                this.form.name = row.name;
                let nameArray = row.parameterName.split('*');
                let unitArray = row.parameterUnit.split('*');
                let length = row.parameterNumber;
                for (let i=0; i<length; i++) {
                    let param = {
                        parameterName: nameArray[i],
                        parameterUnit: unitArray[i]
                    };
                    this.dynamicParameters.push(param);
                }
                this.editVisible = true;
            },
            removeParam(index, row) {
                this.dynamicParameters.splice(index, 1);
            },
            // 保存编辑
            save(formName) {
                this.$refs[formName].validate((valid) => {
                    let length = this.dynamicParameters.length;
                    if (length == 0) {
                        this.$message.error('至少需要一个参数');
                        return;  
                    }
                    for (let i=0; i<length; i++) {
                        let name = this.dynamicParameters[i].parameterName;
                        if (name == '' || name == undefined) {
                            this.$message.error('参数名称不能为空');
                            return;
                        }
                    }
                    if (valid) {
                        let config = {
                            headers: {
                            "Content-Type": "application/json"
                            },
                            withCredentials: true
                        };
                        this.form.parameterList = this.dynamicParameters;
                        this.$axios.post(this.$path + '/productParameterSpecification/save', this.form,  config).then((res) => {
                            if (res != undefined && res.data.code == 1000) {
                                this.getData();
                                this.editVisible = false;
                                this.$message.success('操作成功');
                            } else {
                                this.$message.error(res.data.message);
                            }
                        })
                    } else {
                        this.$message.error('带*为必填项');
                        return false;
                    }
                });
            }
        }
    }

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
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .text {
        font-size: 14px;
        color: #606266;
        margin-right: 30px;
    }
</style>
