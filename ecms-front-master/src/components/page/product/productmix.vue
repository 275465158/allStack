<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="/productmix">产品结构</el-menu-item>
                    <el-menu-item index="/structureparameter">结构参数</el-menu-item>
                    <el-menu-item index="/producttype">产品类型</el-menu-item>
                </el-menu>
            </div>
           
            <div class="handle-box">
                <el-button round type="primary" @click="intoadd">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column prop="name" label="产品名称">
                </el-table-column>
                 <el-table-column prop="type" label="产品类型">
                </el-table-column>
                <el-table-column prop="unit" label="单位">
                </el-table-column>
                <el-table-column prop="status" label="状态" :formatter="status_ex">
                  
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template  slot-scope="scope" >
                    <el-button round @click="settoggle(scope.$index, scope.row)" v-if="scope.row.status==1">停用</el-button>
                    <el-button round @click="settoggle(scope.$index, scope.row)" v-else-if="scope.row.status==0" >启用</el-button>
                    <el-button round @click="edit(scope.$row)">修改</el-button>
                    <!-- 这里没有传index,试一试会不会报错 -->
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
      id:1,
      pages: 1,
      dialogVisible: false,
      search: {
        showstructname: "",
        structname: "",
        remark: "",
        pageNum: 1
      },
      tableData: []
    };
  },
  created() {
    this.getData();
  },
  methods: {
    status_ex(row) {
      if (row.status == 1) {
        return "启用";
      } else {
        return "停用";
      }
    },
    renderContent(h, { node, data, store }) {
        return (
          <span class="custom-tree-node">
            <span>{node.label}</span>
            <span>
              <el-button size="mini" type="text" on-click={ () => this.append(data) }>Append</el-button>
              <el-button size="mini" type="text" on-click={ () => this.remove(node, data) }>Delete</el-button>
            </span>
          </span>);
      },
    handleSelect(ok) {
      this.$router.push({
        path: ok
      });
    },
    getData() {
      this.$http.post("/productType/list", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    handleNodeClick() {
        
    },
    intoadd(){
        this.$router.push({
        path: "/addproductmix"
      });
    },
    add() {
      this.search.status = this.value;
      this.$http.post("/productType/inserttype", this.search).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.search.status = "";
          this.search.type = "";
          this.getData();
        }
      });
      this.dialogVisible = false;
    },
    edit(row) {},
    settoggle(index, row) {
      //设置类型可用否  需要获取行数
      if (row.status == 1) {
        row.status = 0;
        this.$http.post("/productType/toggle", row).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.tableData = res.data.data.list;
            this.pages = res.data.data.total;
          }
        });
      } else if (row.status == 0) {
        row.status = 1;
        this.$http.post("/productType/toggle", row).then(res => {
          if (res != undefined && res.data.code == 1000) {
            this.tableData = res.data.data.list;
            this.pages = res.data.data.total;
          }
        });
      }
    },
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    }
  }
};
</script>
<style>
.handle-box {
  margin-bottom: 20px;
}
</style>

