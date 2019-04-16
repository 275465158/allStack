<template>
    <div class="container">
        <hr class="marginTop">
        <span class="text">物料明细:</span>
        <hr class="marginBottom">
        <el-form :model="form" ref="form" label-width="100px" v-loading="loading">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="物料编号">
                        <el-input v-model="form.materialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="物料名称">
                        <el-input v-model="form.materialName" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="工厂内部编号">
                        <el-input v-model="form.factoryMaterialCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="物料类型">
                        <el-input v-model="form.type" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="原图材料">
                        <el-input v-model="form.originalMaterial" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="单位">
                        <el-input v-model="form.materialUnit" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="来源">
                        <el-input v-model="form.source" disabled></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginBottom">
            <span class="text">参数名称:</span>
            <hr class="marginBottom">
            <el-table :data="paramInfo" border style="width:100%">
                <el-table-column label="参数值" prop="materialParamNameValue"></el-table-column>
            </el-table>
            <hr class="marginBottom">
            <div v-show="local">
                <span class="text">工艺制程</span>
                <br>
                <span class="text">工艺名称:{{this.processName}}</span>
                <el-table :data="processInfo" border style="width:100%" id="processInfoTable">
                    <el-table-column
                        v-for="(process, index) in processInfoTableHead"
                        :label="process.processFormatName==''?('参数'+(index+1))
                                     :process.processFormatName"
                        :key="process.processFormatName"
                    >
                        <template slot-scope="scope">
                            <el-input v-model="scope.row[scope.column.label]" disabled></el-input>
                        </template>
                    </el-table-column>
                </el-table>
                <hr class="marginBottom">
                <span class="text">物料清单</span>
                <el-table :data="materialInfo" border style="width:100%">
                    <el-table-column label="序号" prop="id">
                        <template slot-scope="scope">{{scope.$index+1}}</template>
                    </el-table-column>
                    <el-table-column label="物料编号" prop="materialBomInfo.materialCode"></el-table-column>
                    <el-table-column label="物料名称" prop="materialBomInfo.materialName"></el-table-column>
                    <el-table-column label="原图材料" prop="materialBomInfo.originalMaterial"></el-table-column>
                    <el-table-column label="参数" prop="materialBomParamValueStr"></el-table-column>
                    <el-table-column label="单位" prop="materialBomInfo.materialUnit"></el-table-column>
                    <el-table-column label="数量" prop="quantity"></el-table-column>
                    <el-table-column label="来源" prop="materialBomInfo.source"></el-table-column>
                </el-table>
            </div>
            <hr class="marginBottom">
            <span class="text">验收标准</span>
            <el-table :data="checkInfo" border style="width:100%" height="250">
                <el-table-column label="验收编号" prop="checkId"></el-table-column>
                <el-table-column label="验收名称" prop="productAcceptanceInfo.name"></el-table-column>
                <el-table-column label="制作人" prop="productAcceptanceInfo.owner"></el-table-column>
            </el-table>
            <hr class="marginBottom">
            <span class="text">图纸</span>
            <el-table :data="drawingInfo" border style="width:100%" height="250">
                <el-table-column label="图纸编号" prop="productDrawingInfo.drawingCode"></el-table-column>
                <el-table-column label="图纸名称" prop="productDrawingInfo.name"></el-table-column>
                <el-table-column label="制作人" prop="productDrawingInfo.owner"></el-table-column>
            </el-table>
            <template v-if="reviewFormVisible==true">
                <hr class="marginBottom">
                <span class="text">评审意见</span>
                <el-form v-model="log">
                    <el-form-item label="当前评审意见">
                        <el-input type="textarea" v-model="log.taskComment"></el-input>
                    </el-form-item>
                </el-form>
                <el-button round type="primary" @click="approve">同意</el-button>
                <el-button round @click="reject">不同意</el-button>
            </template>
            <hr class="marginBottom">
            <span class="text">审批历史</span>
            <el-table :data="table" border style="width:100%">
                <el-table-column prop="author" label="评审人"></el-table-column>
                <el-table-column prop="createTime" label="评审时间"></el-table-column>
                <el-table-column prop="comment" label="评审意见"></el-table-column>
            </el-table>
            <hr class="marginTop">
            <el-button @click="goBack">返回</el-button>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            loading: false,
            checkInfo: [],
            drawingInfo: [],
            materialInfo: [],
            processInfo: [],
            processFormatInfo: [],
            paramInfo: [],
            processInfoTableHead: [],
            processName: [],
            local: true,
            form: {
                id: "",
                materialCode: "",
                materialName: "",
                originalMaterial: "",
                materielUnit: "",
                source: ""
            },
            search: {
                id: "",
                pageNum: 1,
                pageSize: 20
            },
            log: { comment: "", taskId: "", id: "" },
            taskLogs: [],
            viewType: "review",
            reviewFormVisible: true
        };
    },
    created() {},
    mounted() {
        this.getData();
    },
    computed: {
        table() {
            return this.taskLogs.filter(d => {
                return d;
            });
        }
    },
    methods: {
        approve() {
            this.loading = true;
            this.log.taskId = this.$route.query.taskId;
            this.log.id = this.$route.query.businessKey;
            this.$http
                .post("/materialInfo/completeTask", this.log)
                .then(res => {
                    if (res != undefined && res.data.code == 1000) {
                        this.goBack();
                    }
                    this.loading = false;
                });
        },
        reject() {
            this.loading = true;
            this.log.taskId = this.$route.query.taskId;
            this.log.id = this.$route.query.businessKey;
            this.$http.post("/materialInfo/rejectTask", this.log).then(res => {
                if (res != undefined && res.data.code == 1000) {
                    this.goBack();
                }
                this.loading = false;
            });
        },
        getData() {
            this.local = true;
            if (this.$route.query.viewType != undefined) {
                this.viewType = this.$route.query.viewType;
            }
            if (this.$route.query.taskId != null) {
                this.search.taskId = this.$route.query.taskId;
            }
            if (this.$route.query.businessKey != null) {
                this.search.id = this.$route.query.businessKey;
                this.$http
                    .post("/materialInfo/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.form = res.data.data;
                            this.processName = res.data.data.processName;
                            if (this.form.source == "外购") {
                                this.local = false;
                            }
                        }
                    });
                this.$http
                    .post("/materialCheck/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.checkInfo = res.data.data;
                        }
                    });
                this.$http
                    .post("/materialDrawing/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            console.log("this.drawingInfo:", this.drawingInfo);
                            this.drawingInfo = res.data.data;
                        }
                    });
                this.$http
                    .post("/materialRelation/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.materialInfo = res.data.data;
                        }
                    });
                this.$http
                    .post("/materialProcess/detail", this.search)
                    .then(res => {
                        this.processInfo = [];
                        if (res != undefined && res.data.code == 1000) {
                            for (var i = 0; i < res.data.data.length; i++) {
                                this.processInfo.push(
                                    JSON.parse(res.data.data[i].jsonParam)
                                );
                            }
                        }
                    });
                this.$http
                    .post("/materialBomParamName/detail", this.search)
                    .then(res => {
                        if (res != undefined && res.data.code == 1000) {
                            this.paramInfo = res.data.data;
                        }
                    });
                this.$http
                    .post("/processFormat/searchByMaterialId", this.search)
                    .then(res => {
                        this.processInfoTableHead = [];
                        if (res != undefined && res.data.code == 1000) {
                            this.processInfoTableHead = res.data.data;
                        }
                    });
                if (this.viewType == "review") {
                    this.$http
                        .post("/activiti/queryTaskLogs", {
                            taskId: this.$route.query.taskId
                        })
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.taskLogs = res.data.data;
                            }
                        });
                    this.reviewFormVisible = true;
                } else if (this.viewType == "detail") {
                    this.$http
                        .post("/activiti/queryTaskHistoryLogs", {
                            taskId: this.$route.query.taskId
                        })
                        .then(res => {
                            if (res != undefined && res.data.code == 1000) {
                                this.taskLogs = res.data.data;
                            }
                        });
                    this.reviewFormVisible = false;
                }
            }
        },
        goBack() {
            if (this.viewType == "review") {
                this.$router.push({
                    path: "/taskList",
                    query: { closeFlag: 1 }
                });
            } else if (this.viewType == "detail") {
                this.$router.push({
                    path: "/taskHistoryList",
                    query: { closeFlag: 1 }
                });
            }
        }
    },
    watch: {
        $route(to, from) {
            if (to.path == "/MaterialBomReview") {
                this.getData();
                this.log = { comment: "", taskId: "", id: "" };
            }
        }
    }
};
</script>
<style scoped>
hr {
    border-top: 1px;
}
.marginTop {
    margin-top: 10px;
    margin-bottom: 5px;
}
.marginBottom {
    margin-top: 5px;
    margin-bottom: 10px;
}
.text {
    font-size: 12px;
    color: #606266;
    margin-right: 30px;
}
</style>
