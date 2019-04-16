<template>
    <div class="table">
        <div class="container">
            <hr class="marginTop" />
            <el-button size="small" @click="add">新增员工</el-button>
            <el-button size="small" @click="editVisible = true">导入员工
                <i class="el-icon-upload el-icon--right"></i>
            </el-button>
            <el-button style="float:right;" size="small" @click="exportEmployee">导出员工
                <i class="el-icon-download el-icon--right"></i>
            </el-button>
            <el-button style="float:right;" size="small" @click="exportTemplate">模板下载
                <i class="el-icon-download el-icon--right"></i>
            </el-button>
            <hr class="marginBottom" />
            <div class="text">人员列表</div>
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="工号:">
                        <el-input v-model="search.employeeNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名:">
                        <el-input v-model="search.name"></el-input>
                    </el-form-item>
                    <el-form-item label="部门:">
                        <el-input v-model="search.departmentName"></el-input>
                    </el-form-item>
                    <el-form-item label="岗位:">
                        <el-input v-model="search.postName"></el-input>
                    </el-form-item>
                    <el-form-item label="在职状态:">
                        <el-select v-model="search.status" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in status"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button round @click="searchLike">搜索</el-button>
                </el-form>
            </div>
            <el-table v-loading="loading" :data="tables" border stripe style="width: 100%">
                <el-table-column align="center" prop="id" label="序号">
                    <template slot-scope="scope">
                        {{scope.$index+1}}
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="employeeNumber" label="工号" />
                <el-table-column align="center" prop="name" label="姓名" />
                <el-table-column align="center" prop="sex" label="性别" />
                <el-table-column align="center" prop="age" label="年龄" />
                <el-table-column align="center" prop="jobAge" label="工龄" />
                <el-table-column align="center" prop="firstDepartmentName" label="一级部门" />
                <el-table-column align="center" prop="secondDepartmentName" label="二级部门" />
                <el-table-column align="center" prop="thirdDepartmentName" label="三级部门" />
                <el-table-column align="center" prop="postName" label="岗位" />
                <el-table-column align="center" prop="maritalStatus" label="婚姻状况" />
                <el-table-column align="center" prop="joinDate" label="入职时间" />
                <el-table-column align="center" prop="dimissionDate" label="离职时间" />
                <el-table-column align="center" prop="statusDisplay" label="状态" />
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="detail(scope.$index, scope.row)">
                            查看
                        </el-button>
                        <el-button type="text" @click="dimission(scope.$index, scope.row)">
                            离职
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
        <el-dialog title="导入员工" :visible.sync="editVisible" width="30%">
            <el-form ref="form" label-width="100px">
                <el-form-item label="员工列表:">
                    <input type="file" name="file" @change="setFile" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="importEmployee">导入</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import fileDownload from 'js-file-download';
    export default {
        data() {
            return {
                file: '',
                tableData: [],
                editVisible: false,
                loading: false,
                status:[
                    {
                        name:'在职',
                        value: 1
                    },
                    {
                        name:'离职',
                        value:0
                    }
                ],
                url: '/employee/listIncludePost',
                pages: 1,
                search: {
                    employeeNumber: '',
                    name: '',
                    postName: '',
                    departmentName: '',
                    status: '',
                    pageNum: 1
                }
            }
        },
        created() {
            this.getData();
        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/employeeList') {
                    this.getData();
                }
            }
        },
        computed: {
            tables(){
                return this.tableData.filter((d) => {
                    // d.statusDisplay = d.dimissionDate != '' ? '离职':'在职';
                    let status = d.status;
                    d.statusDisplay = status == 0 ? '离职':'在职';
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
            searchLike(){
                this.search.pageNum = 1;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                let _this = this
                _this.loading = true
                this.$http.post(this.url, this.search).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        // console.log("this.tableData",this.tableData[1]);
                        
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    })
            },
            add() {
                this.$router.push("/employeeInfo");
            },
            detail(index, row) {
                this.$router.push({path:'/employeeInfo',query:{id:row.id}});
            },
            dimission(index, row) {
                this.$http.post('/employee/status/update', {
                    id: row.id
                }).then((res) => {
                    if (res != undefined && res.data.code == 1000) {
                        row.status = 0;
                        let date = new Date();
                        let seperator1 = "-";
                        let year = date.getFullYear();
                        let month = date.getMonth() + 1;
                        let strDate = date.getDate();
                        if (month >= 1 && month <= 9) {
                            month = "0" + month;
                        }
                        if (strDate >= 0 && strDate <= 9) {
                            strDate = "0" + strDate;
                        }
                        let currentdate = year + seperator1 + month + seperator1 + strDate;
                        row.dimissionDate = currentdate;
                        this.$message.success('操作成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            setFile(event){
                this.file = event.target.files[0];//获取文件
                var windowURL = window.URL || window.webkitURL;
                this.file = event.target.files[0];
            },
            importEmployee() {
                let formdata = new FormData();
                formdata.append('file',this.file);
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    withCredentials : true
                };
                this.$axios.post(this.$path + '/employee/upload', formdata, config).then((res) => {
                    this.editVisible = false;
                    if (res.data.code == 1000) {
                        this.$message.success('导入成功');
                        this.getData();
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            },
            exportEmployee() {
                this.$http.get('/employee/export/list',{responseType: 'blob'}).then((res) => {
                    fileDownload(res.data, '员工列表.xls');
                })
            },
            exportTemplate() {
                this.$http.get('/employee/export/template',{responseType: 'blob'}).then((res) => {
                    fileDownload(res.data, '员工模板.xls');
                })
            }
        }
    }

</script>

<style>
.handle-box {
  margin-bottom: 20px;
}
.marginTop {
  margin-top: 10px;
  margin-bottom: 5px;
}
.marginBottom {
  margin-top: 5px;
  margin-bottom: 20px;
}
.text {
  font-size: 12px;
  color: #606266;
  margin-bottom: 10px;
}
</style>