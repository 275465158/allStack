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
                <el-button round type="primary" @click="dialogVisible=true">新建</el-button>
            </div>
            <el-table :data="tableData" border style="width:100%">
                <el-table-column prop="id" label="序号" sortable>
                </el-table-column>
                <el-table-column prop="type" label="显示结构名称">
                </el-table-column>
                 <el-table-column prop="type" label="结构名称">
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
           
            <el-dialog title="新增结构参数" :visible.sync="dialogVisible" width="30%">
                <el-form ref="form" :model="search" :rules="rules" label-width="100px">
                    <el-form-item label="显示结构名称:" prop="showstructname">
                        <el-input v-model="search.showstructname"></el-input>
                    </el-form-item>
                    <el-form-item label="结构名称:" prop="structname">
                        <el-input v-model="search.structname"></el-input>
                    </el-form-item>
                    <el-form-item label="备注:">
                        <el-input v-model="search.type"></el-input>
                    </el-form-item> 
                </el-form>
                 <hr class="marginButtom"/>
                 参数：<el-button  round @click="append(data)">添加</el-button>
                    <el-tree
                        :data="data4"
                        node-key="id"
                        default-expand-all
                        :expand-on-click-node="false"
                        :render-content="renderContent">
                        
                        </el-tree>

      
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                   
                    <el-button type="primary" @click="add">确 定</el-button>
                </span>
            </el-dialog>
            <el-dialog title="修改结构参数" :visible.sync="editVisible" width="30%">
                <el-form ref="form" :model="editsearch" label-width="100px">
                    
                    <el-form-item label="结构名称:" prop="structname" >
                        <el-input value="" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="备注:">
                        <el-input v-model="editsearch.remark"></el-input>
                    </el-form-item> 
                    <el-form-item label="参数个数:" prop="showstructname">
                        <el-input  value="" disabled></el-input>
                    </el-form-item>
                </el-form>
                 <hr class="marginButtom"/>
                 <!-- 参数： -->
                
                    <el-table :data="editData" border style="width: 100%">
                
                <el-table-column prop="detectionname" label=" 参数：" >
                      <template slot-scope="tableData">
                    <span>参数<span v-text="tableData.$index+1"></span> </span>    
                    <el-input v-model="search.showstructname" class="editinput"></el-input>
                    <i class="el-icon-circle-plus-outline" @click="adddetail" style="font-size:26px;"/>
                    <i class="el-icon-remove-outline" @click="subdetail(tableData.$index)" style="font-size:26px;" v-show="isshow"/>
                    
                    </template>
                </el-table-column>
                
            </el-table>
                  

                <span slot="footer" class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                   
                    <el-button type="primary" @click="sendedit">修 改</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
export default {
  data() {
    const data = [
      {
        id: 1,
        label: "参数",
        children: []
      }
      // {
      //   id: 2,
      //   label: "参数2",
      //   children: [
      //     {
      //       id: 5,
      //       label: "二级 2-1"
      //     },
      //     {
      //       id: 6,
      //       label: "二级 2-2"
      //     }
      //   ]
      // },
    ];
    return {
      data4: JSON.parse(JSON.stringify(data)),
      id: 1,
      pages: 1,
      isshow: false,
      paramNum:1,
      editData:[
        {paramlist:'初始参数',
        i:1,},
      ],
      rules: {//添加对话框的rules
        showstructname: {
          required: true,
          message: "请输入显示结构名称",
          trigger: "blur"
        },
        structname: {
          required: true,
          message: "请输入结构名称",
          trigger: "blur"
        },
        parameter:{
          required: false,
        }
      },
      dialogVisible: false,
      editVisible: false,
      search: {  //添加的search
        showstructname: "",
        structname: "",
        remark: "",
        pageNum: 1
      },
      addsearch: {
        //添加对话框下的参数的数据  考虑下要不要合并
        showstructname: "",
        structname: "",
        remark: "",
        pageNum: 1
      },
      editsearch:{//编辑对话框下的search  
      structname:'',
      remark:'',
      countparam:'',
      
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
    adddetail() {
      this.paramNum++;
       this.isshow = true;
       this.editData.push({
        parameter:'',
        i:this.paramNum,
      });
      
    },
    subdetail(index) {
      //判断详细是否小于1
      if (this.paramNum <= 1) {
        this.isshow = false;
        return;
      } else {
        this.editData.splice(index, 1);
        this.paramNum--;
        if (this.paramNum < 2) {
          this.isshow = false;
        }
      }
    },

    append(data) {
      const newChild = { id: this.id++, label: "参数"+this.id, children: [] };
      if (!data.children) {
        this.$set(data, "children", []);
      }
      data.children.push(newChild);
    },

    remove(node, data) {
      const parent = node.parent;
      const children = parent.data.children || parent.data;
      const index = children.findIndex(d => d.id === data.id);
      children.splice(index, 1);
    },
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span><el-input v-model="addsearch.remark" ></el-input>
          <span>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.append(data)}
            >
              Append
            </el-button>
            <el-button
              size="mini"
              type="text"
              on-click={() => this.remove(node, data)}
            >
              Delete
            </el-button>
          </span>
        </span>
      );
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
    //这只是进入修改页面  发送修改请求还要下一个
    edit(row) {
      this.editVisible = true;
    },
    sendedit() {

    },
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
.marginButtom {
  margin-bottom: 20px;
}
.addinput  .el-input__inner {
  width: 30%;
  height: 26px;
}
.editinput {
  width: 70%;
}
</style>

