<template>
    <div class="table">
        <div class="container" :default-active="$route.path">
            <el-form :inline="true"  class="demo-form-inline" :rules="rules">
                    <el-form-item label="产品名称:"  prop="productname">
                      <el-input v-model="productname" ></el-input>
                    </el-form-item>
                    <el-form-item label="产品类型:"  prop="producttype">
                      <el-input v-model="producttype" @keyup.native="select()"></el-input>
                    </el-form-item>
                    <el-form-item label="单位:" prop="unit">
                      <el-input v-model='unit' @keyup.native="select()"></el-input>
                    </el-form-item>
                    
            </el-form>
            <hr class="marginTop" />
            <!-- style="margin-bottom:10px;" -->
        
            <div class="handle-box">
                <span class="text">结构列表</span>
                <el-button @click="add">新增</el-button>
                <!-- 这里需求还不明确 -->
            </div>
            <!-- <el-table :data="tableData" border stripe style="width: 100%">
                <el-table-column prop="id" label="验收编号" sortable></el-table-column>
                <el-table-column prop="name" label="验收名称"></el-table-column>
                <el-table-column prop="owner" label="制作人"></el-table-column>
                <el-table-column prop="created" label="制作日期"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                    <el-button @click="edit(scope.$index, scope.row)">修 改</el-button>
                     发送编辑请求 
                    <el-button size="small" @click="detail(scope.$index, scope.row)">明 细</el-button>
                    <el-button size="small" @click="del(scope.$index, scope.row)">删 除</el-button>
                    </template>
                </el-table-column>
                
            </el-table>
            这里也不明确 -->
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
      rules: {
        productname: {
          required: true,
          message: "请输入产品名称",
          trigger: "blur"
        },
        producttype: {
          required: true,
          message: "请输入产品类型",
          trigger: "blur"
        },
        unit: {
          required: true,
          message: "请输入单位",
          trigger: "blur"
        }
      }
    };
  }
};
</script>
