<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-form :inline="true" :model="search" class="demo-form-inline">
                    <el-form-item label="产品编号:">
                        <el-input v-model="search.materialCode"></el-input> 
                    </el-form-item>
                    <el-form-item label="成品编号:">
                        <el-input v-model="search.productCode"></el-input>
                    </el-form-item>
                    <el-form-item label="产品名称:">
                        <el-input v-model="search.materialName"></el-input>
                    </el-form-item>
                    <el-form-item label="制作人:">
                        <el-input v-model="search.author"></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间:">
                        <el-date-picker v-model="search.created"></el-date-picker>
                    </el-form-item>
                    <el-button round @click="getData">查询</el-button>
                </el-form>
            </div>
            <div class="handle-box">
                <span class="el-form-item__label">物料列表</span>
                <el-button round type="primary" @click="add">新建物料明细</el-button>
            </div>
            <el-table :data="tables" border style="width:100%">
                <el-table-column label="序号" prop="id" sortable >
                </el-table-column>
                <el-table-column label="产品编号" prop="materialCode">
                </el-table-column>
                <el-table-column label="成品编号" prop="productCode">
                </el-table-column>
                <el-table-column label="产品名称" prop="materialName"></el-table-column>
                <el-table-column label="制作人" prop="author"></el-table-column>
                <el-table-column label="制作时间" prop="created"></el-table-column>
                <el-table-column label="合同号"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="edit(scope.row)">修改</el-button>
                        <el-button @click="searchOne(scope.row)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="prev, pager, next" :total="pages">
                </el-pagination>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/material/list",
      pages: 1,
      search: {
        status: 1,
        pageNum: 1
      }
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tables() {
      return this.tableData.filter(d => {
        return d;
      });
    }
  },
  methods: {
    handleSelect(key, keyPath) {},
    handleChange(value) {
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    add() {
      this.$router.push({
        path: "/materialBomInfo"
      });
    },
    edit(row) {
          this.$router.push({
              path: "/materialBomInfo",
              query: {
                  materialId: row.id
              }
          });
      },
      searchOne(row) {
          this.$router.push({
              path: "/materialBomSecrch",
              query: {
                  materialId: row.id
              }
          });
      }
  },
  watch: {
    // 如果路由发生变化，再次执行该方法
    $route: "getData"
  }
};
</script>
