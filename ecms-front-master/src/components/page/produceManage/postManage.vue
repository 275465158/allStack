<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="工号:">
                        <el-input v-model="search.id"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名:">
                        <el-input v-model="search.name"></el-input>
                    </el-form-item>
                    <el-form-item label="岗位:">
                        <el-input v-model="search.postId"></el-input>
                    </el-form-item>
                    <el-button round @click="searchLike" type="primary">查询</el-button>
                    <el-button round @click="clearFrom">清空</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">人员列表</span>
            </div>
            <el-table v-loading="loading" :data="tables" border style="width: 100%">
                <el-table-column align="center" prop="id" label="工号">
                </el-table-column>
                <el-table-column align="center" prop="name" label="姓名">
                </el-table-column>
                <el-table-column align="center" prop="postId" label="岗位">
                </el-table-column>
                <el-table-column align="center" label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" type="primary" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
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
                url: '/employee/listEmployee',
                editVisible: false,
                pages: 1,
                search: {
                    id: '',
                    name: '',
                    postId:'',
                    pageNum: 1
                },
                loading: false
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
            // 获取数据
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
            filterTag(value, row) {
                return row.tag === 1;
            },
            handleDetail(index,row) {
                this.$router.push({
                    path: 'postManageEdit',
                    name: 'postManageEdit',
                    params: {
                        rowDataObj: row
                    }
                })
            },
            // 清空搜索
            clearFrom() {
                this.search.id = '',
                this.search.name =  '',
                this.search.postId = ''
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path == '/postManage') {
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
