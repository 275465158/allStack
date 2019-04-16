<template>
    <div class="container">
        <div class="handle-box">
            <span class="el-form-item__label">BOM 制作任务</span>
            <!--<el-button type="primary" @click="add">新增</el-button>-->
        </div>
        <el-table :data="tables" border style="width:100%">
            <el-table-column prop="id" label="序号" sortable></el-table-column>
            <el-table-column prop="orderId" label="订单编号"></el-table-column>
            <el-table-column prop="purchaseId" label="合同编号"></el-table-column>
            <el-table-column prop="taskProgress" label="BOM进度"></el-table-column>
            <el-table-column prop="draftsman" label="BOM制作人"></el-table-column>
            <el-table-column prop="startDate" label="开始时间"></el-table-column>
            <el-table-column prop="completedDate" label="完成时间"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                   <!-- <el-button>指派</el-button>
                    <el-button @click="edit(scope.row)">编辑</el-button>-->
                    <span v-if="scope.row.taskProgress!='完成'"><el-button @click="complete(scope.row)">完成任务</el-button></span>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination :page-size="20" @current-change="handleCurrentChange" layout="total,prev, pager, next" :total="pages">
            </el-pagination>
        </div>
    </div>    
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      url: "/bomtask/list",
      pages: 1,
      search: {
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
    // 分页导航
    handleCurrentChange(val) {
      this.search.pageNum = val;
      this.getData();
    },
    getData() {
      this.$http.post(this.url, this.search).then(res => {
        if (res.data.code == 1000) {
          this.tableData = res.data.data.list;
          this.pages = res.data.data.total;
        }
      });
    },
    add() {
      this.$router.push("/BomTasksInfo");
    },
    edit(row) {
      this.$router.push({
        path: "/BomTasksInfo",
        query: { taskId: row.id }
      });
    },
    complete(row) {
      this.$http.post("/bomtask/complete", { id: row.id }).then(res => {
        if (res != undefined && res.data.code == 1000) {
          this.$message.success("完成任务");
          this.getData();
        }
      });
    }
  },
    watch: {
        '$route' (to, from) {
            if (to.path == '/bomTasksList') {
                this.getData();
            }
        }
    },
};
</script>
<style>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
</style>
