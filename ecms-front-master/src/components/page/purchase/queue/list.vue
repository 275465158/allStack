<template>
<div class="container">
    <div class="handle-box">
        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="/purchaseQueueList">待采购</el-menu-item>
            <el-menu-item index="/purchaseOrderVerificatList">采购单核销</el-menu-item>
            <el-menu-item index="/purchaseOrderList">采购记录</el-menu-item>
        </el-menu>
    </div>
    <div class="handle-box">
        <el-form @keyup.enter.native="searchLike" :inline="true" :model="search" class="demo-form-inline">
            <!--<el-form-item label="待采购编号">-->
                <!--<el-input v-model="search.purchaseStayCode"></el-input>-->
            <!--</el-form-item>-->
            <el-form-item label="物料编码">
                <el-input v-model="search.materialCode"></el-input>
            </el-form-item>
            <el-form-item label="物料名称">
                <el-input v-model="search.materialName"></el-input>
            </el-form-item>
            <el-form-item label="物料参数">
                <el-input v-model="search.materialBomParamValueStr"></el-input>
            </el-form-item>
            <el-form-item label="原图材料">
                <el-input v-model="search.originalMaterial"></el-input>
            </el-form-item>
            <el-form-item label="供应商名称">
                <el-input v-model="search.supplierName"></el-input>
            </el-form-item>
            <el-form-item label="待采购原因">
                <el-input v-model="search.purchaseReason"></el-input>
            </el-form-item>
            <el-button round @click="searchLike">查询</el-button>
        </el-form>
    </div>
    <div class="handle_box">
        <span class="el-form-item__label">待采购列表</span>
        <el-button round type="primary" @click="addQueue">新增手工采购单</el-button>
        <el-button style="float:right;" :loading="derive" size="small" @click="exportPurchaseStay">导出待采购单
            <i class="el-icon-download el-icon--right"></i>
        </el-button>
    </div>
    <el-table v-loading="loading" :data="tableData" ref="multipleTable" :row-key="getRowKeys" border style="width:100%" @selection-change="handleSelectionChange">
        <el-table-column align="center" prop="id" label="序号" sortable width="80">
            <template slot-scope="scope">
                {{scope.$index+1}}
            </template>
        </el-table-column>
        <el-table-column align="center" prop="materialCode" label="物料编号" >
        </el-table-column>
        <el-table-column align="center" prop="materialName" label="物料名称" >
        </el-table-column>
        <el-table-column align="center" prop="originalMaterial" label="原图材料" >
        </el-table-column>
        <el-table-column align="center" prop="materialBomParamValueStr" label="参数" >
        </el-table-column>
        <el-table-column align="center" prop="materialBomInfo.drawingCode" label="图号" >
        </el-table-column>
        <el-table-column align="center" prop="stayPurQty" label="待采购数量" >
        </el-table-column>
        <el-table-column align="center" prop="purchaseReason" label="待采购原因" >
        </el-table-column>
        <el-table-column align="center" prop="latestPurDate" label="最晚采购日期" >
        </el-table-column>
        <el-table-column align="center" label="供应商" >
            <template slot-scope="scope">
                <el-select v-model="scope.row.supplierId" clearable @change="selectChangn($event, scope.$index,scope.row)">
                    <el-option
                    v-for="(item, index) in scope.row.supplierOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    >
                    </el-option>
                </el-select>
            </template>
        </el-table-column>
        <el-table-column
                :reserve-selection="true"
                type="selection"
                width="55">
        </el-table-column>
        <!--<el-table-column align="center" label="操作" width="100">-->
            <!--<template slot-scope="scope">-->
                <!--<el-button type="text" @click="purchase(scope.row)">下单</el-button>-->
            <!--</template>-->
        <!--</el-table-column>-->
    </el-table>

    <div class="pagination">
        <el-pagination :page-sizes="[20, 50, 100]" @size-change="handleSizeChange" :page-size="20" @current-change="handleCurrentChange" layout="sizes, total, prev, pager, next" :total="pages">
        </el-pagination>
    </div>
    <div class="mt20" align="center">
        <el-button @click.native="purchase" type="primary">下单</el-button>
    </div>
</div>
</template>

<script>
    import fileDownload from 'js-file-download';
export default {
   data() {
       return {
           derive: false,
           arr: [],
           tableData: [],
           supplierNameArr: [],
           editVisible: false,
           url: "/purchaseOrdersStay/purchaseList",
           pages: 1,
           search: {
               status: 1,
               pageNum: 1,
               pageSize:20,
               purchaseStayCode:'',
               materialCode:'',
               materialName:'',
               materialType:'',
               materialBomParamValueStr:'',
               originalMaterial:'',
               supplierName:'',
               purchaseReason:'',
               materialBom: {},
               ordered: false
           },
           form: {},
           searchForm: {},
           searchTable: [],
           searchParams: [],
           params: [],
           templateSelection: null,
           searchRadio: "",
           loading: false,
           purchaseOrdersListInfoList: [],
           newarr: [],
           watchnum: [],
           getRowKeys (row) {
               return row.id
           },
           downloadFlag: 1
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
       selectChangn (event, index,row) {
           if (event) {
               // this.tableData[index].supplierId = this.arr[index]
               if (this.tableData[index].supplierId === '') {
                   this.tableData[index].supplierId = 0
               }
               // this.$http.post('supplier/getInfo', {id: this.tableData[index].supplierId}).then(res => {
               //     if (res.data.code===1000 && res != undefined) {
               //         console.log(res);
               //         this.tableData[index].supplierName = res.data.data.supplierName
               //     }
               // })
               this.$http.post('purchaseOrdersStay/saveSupplierId', {purchaseOrdersId: row.id,supplierId: this.tableData[index].supplierId}).then(res => {
                   if (res.data.code===1000 && res != undefined) {}
               })
           }
       },
       handleSizeChange (val) {
           this.search.pageSize = val
           this.getData()
       },
       handleSelectionChange (val) {
           this.purchaseOrdersListInfoList = val
       },
       searchLike(){
           this.search.pageNum = 1;
           this.getData();
       },
       handleSelectedChange(val) {
           this.form.materialBom = JSON.stringify(val);
           this.templateSelection = val;
       },
       handleSelect(key, keyPath) {
           this.$router.push({
               path: key
           });
       },
       handleChange(value) {
       },
       // 分页导航
       handleCurrentChange(val) {
           this.arr = []
           this.watchnum = val
           this.search.pageNum = val;
           this.getData();
       },
       getTemplateRow(index, row) {
           this.form.materialBom = JSON.stringify(row);
           this.templateSelection = row;
           },
       getData() {
           let _this = this
           _this.loading = true
           this.$http.post(this.url, this.search).then(res => {
               if (res != undefined && res.data.code == 1000) {
                   this.tableData = res.data.data.list;
                   res.data.data.list.forEach((item, index) => {
                       if (this.tableData[index].supplierId === 0) {
                           this.tableData[index].supplierId = ''
                       } else if (this.tableData[index].supplierId && this.tableData[index].materialBomInfo.smiList.length==0) {
                           this.tableData[index].supplierId = ''
                       }
                       if (item.materialBomInfo.smiList.length > 0) {
                           this.tableData[index].supplierCode = item.materialBomInfo.smiList[0].supplier.supplierCode
                           this.tableData[index].purchasePrice = item.materialBomInfo.smiList[0].price
                           this.tableData[index].supplierOptions = []
                       }
                       let smiObj = []
                       item.materialBomInfo.smiList.forEach((i, index1) => {
                           smiObj.push({
                                label: i.supplier.supplierName,
                                value: i.supplier.id
                           })
                       })
                       this.tableData[index].supplierOptions = smiObj
                   })
                   this.pages = res.data.data.total;
               }
               _this.loading = false
           })
               .catch(err => {
                   this.$refs.multipleTable.clearSelection();
                   _this.loading = false
               });
       },
       add() {
           this.editVisible = true;
       },
       addQueue() {
           this.$router.push("/purchaseOrdersAdd");
       },
       insert() {
           this.$http.post("/purchase/queue/save", this.form).then(res => {
               if (res != undefined && res.data.code == 1000) {
                   this.$message.success("提交成功");
                   this.editVisible = false;
                   this.getData();
               }
           });
       },
       materialSearch() {
           this.$http.post("/material/search", this.searchForm).then(res => {
               if (res != undefined && res.data.code == 1000) {
                   this.$message.success("完成搜索");
                   this.searchTable = res.data.data;
               }
           });
       },
       purchase() {
           let temp = this.purchaseOrdersListInfoList
           if (temp.length === 0) {
               this.$message.warning('请勾选至少一个物料')
           } else {
               let arr = temp.map(o => o.supplierId)
               let flag = true
               for (let i = 0; i < temp.length; i++) {
                   if (temp[i].supplierId === '') {
                       flag = false
                   }
               }
               if (flag) {
                   if (Math.max.apply(null, arr) === Math.min.apply(null, arr)) {
                       let param = temp.map(m => {
                           return m.id
                       })
                       this.$router.push({
                           path: "/purchaseByStay",
                           query: {row: JSON.stringify(param)}
                       });
                   } else {
                       this.$message.warning('请选择同一个供应商下单')
                   }
               } else {
                   this.$message.warning('请选择供应商')
               }
           }
       },
       handleSearch() {
           this.search.materialBom = JSON.stringify(this.search.materialBom);
           this.getData();
       },
       exportPurchaseStay() {
           this.derive = true
           this.$http.get('/purchaseOrdersStay/export/list',{responseType: 'blob'}).then((res) => {
               fileDownload(res.data, '待采购列表.xls');
               this.derive = false
           })
               .catch(err => {
                   this.derive = false
               })
       },
   },
   watch: {
       '$route' (to, form) {
           if (to.path == '/purchaseQueueList' && this.$route.query.works !== 1) {
               Object.assign(this.$data, this.$options.data())
               this.$refs.multipleTable.clearSelection();
               this.getData()
           }
       },
       watchnum (to, form) {

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

.handle-input {
  width: 300px;
  display: inline-block;
}

.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}

.marginTop {
  margin-top: 10px;
  margin-bottom: 10px;
}

.marginBottom {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
