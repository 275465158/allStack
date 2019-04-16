<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="设备编号:">
                        <el-input v-model="search.equipmentNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="设备名称:">
                        <el-input v-model="search.equipmentName"></el-input>
                    </el-form-item>
                    <el-form-item label="厂家:">
                        <el-input v-model="search.vender"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike" type="primary">查询</el-button>
                    <el-button round @click="clearFrom">清空</el-button>
                </el-form>

            </div>
            <div class="handle-box">
                <span class="el-form-item__label">产品列表</span>
                <el-button round type="primary" @click="add">新增</el-button>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column align="center" prop="equipmentNumber" label="设备编号">
                </el-table-column>
                <el-table-column align="center" prop="equipmentName" label="设备名称">
                </el-table-column>
                <el-table-column align="center" prop="equipmentModel" label="设备型号">
                </el-table-column>
                <el-table-column align="center" prop="vender" label="厂家">
                </el-table-column>
                <el-table-column align="center" prop="deptId" label="所属部门">
                </el-table-column>
                <el-table-column align="center" prop="equipmentStatus" label="状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.equipmentStatus === 0">正常</div>
                        <div v-if="scope.row.equipmentStatus === 1">保养</div>
                        <div v-if="scope.row.equipmentStatus === 2">维修</div>
                        <div v-if="scope.row.equipmentStatus === 3">停用</div>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: '',
        data() {
            return {
                tableData: [],
                url: '/equipmentManage/equiList',
                editVisible: false,
                pages: 1,
                loading: false,
                search: {
                    vender: '',
                    equipmentName: '',
                    equipmentNumber: '',
                    pageNum: 1
                },
                form: {
                    name: '',
                    productStatus: '',
                    date1: '',
                    date2: '',
                    delivery: true,
                    desc: '',
                    options: []
                },
                msg: 'test messsage'
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
                    d.status = status == 0 ? '正常':'失效';
                    d.type = type == 0 ? '普通用户':'管理员';
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
                    if (res.data.code == 1000) {
                        this.tableData = res.data.data.list;
                        this.pages = res.data.data.total;
                    }
                    _this.loading = false
                })
                    .catch(err => {
                        _this.loading = false
                    })
            },
            add() {
                this.editVisible = true;
                this.$router.push({ path: '/equipmentAdd' })
            },
            clearFrom() {
                this.search.vender = '',
                this.search.equipmentName = '',
                this.search.equipmentNumber = ''
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index,row) {
                this.$router.push({
                    path: 'equipmentEdit',
                    name: 'equipmentEdit',
                    params: {
                        rowDataObj: row
                    }
                    /*query: {
                        name: 'name',
                        dataObj: this.msg
                    }*/
                })
                // this.$router.push("/equipmentEdit/"+row)
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/equipmentList') {
                    this.getData();
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

</style>
