<template>
    <div class="container">
        <hr class="marginTop" />
        <span class="text">工艺明细:</span>
        <hr class="marginBottom" />
        <el-form :model="form" ref="form" label-width="100px">
            <el-row :gutter="30">
                <el-col :span="12">
                    <el-form-item label="工艺编号">
                        <el-input v-model="form.processCode" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="工艺名称">
                        <el-input v-model="form.processName" disabled></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="说明">
                        <el-input v-model="form.remark" disabled></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <hr class="marginTop" />
            <span class="text">工艺格式:</span>
            <el-form :data="processFormat" border style="width:100%" height="250">
                <el-checkbox  v-for="(project,index) in processFormat" :key="index" :offset="1" :checked="1==1" disabled border>
                    {{project.processFormatName}}
                </el-checkbox>
            </el-form>
            <hr class="marginBottom" />
            <span class="text">辅助工具选择:</span>
            <hr class="marginBottom" />
            <el-table :data="assistToolTypeSelect" border style="width:100%" height="250">
                <el-table-column label="序号" prop="id"></el-table-column>
                <el-table-column label="工具类型" prop="toolTypeName"></el-table-column>
                <el-table-column label="型号" prop="defaultModelNumber">
                    <template slot-scope="scope">
                        <el-checkbox :checked="scope.row.defaultModelNumber == 1" disabled></el-checkbox>
                    </template>
                </el-table-column>
            </el-table>
            <hr class="marginTop"/>
            <el-button @click="goBack">返回</el-button>
        </el-form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    processCode:'',
                    processName:'',
                    remark:'',
                    defaultModelNumber:''
                },
                search:{
                    id:'',
                    pageNum:1,
                    pageSize:20
                },
                processFormat: [],
                assistToolTypeSelect: [],
            };
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            getData() {
                if (this.$route.query.processId != null) {
                    this.search.id = this.$route.query.processId
                    this.$http.post("/process/searchOne", this.search ).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.form = res.data.data;
                    }
                });
                    this.$http.post("/processToolType/searchToolTypeById",this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.assistToolTypeSelect = res.data.data;
                    }
                });
                    this.$http.post("/processFormat/searchProcessFormatById",this.search).then(res => {
                        if (res != undefined && res.data.code == 1000) {
                        this.processFormat = res.data.data;
                    }
                });
                }
            },
            goBack() {
                this.$router.push("/processList");
            },

        },
        watch: {
            '$route' (to, from) {
                if (to.path == '/processSearch') {
                    this.getData();
                }
            }
        },
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
