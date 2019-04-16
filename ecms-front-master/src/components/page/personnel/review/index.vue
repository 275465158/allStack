<template>
    <div class="container">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1">流程变更记录</el-menu-item>
            <el-menu-item index="2">合同评审</el-menu-item>
            <el-menu-item index="3">来料不合格评审</el-menu-item>
            <el-menu-item index="4">生产不合格评审</el-menu-item>
        </el-menu>
        <template v-if="show == 1">
            <div class="form_box">
                <el-form :inline="true" :model="form" class="demo-form-inline">
                    <el-form-item label="流程编号">
                        <el-input :model="form.num"></el-input>
                    </el-form-item>
                    <el-form-item label="流程名称">
                        <el-input :model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="发起人">
                        <el-input :model="form.role"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" size="small">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-row>
                <el-col :span="24">
                    <el-table :data="tableData" border>
                        <el-table-column prop="number" label="流程编号"></el-table-column>
                        <el-table-column prop="name" label="流程名称"></el-table-column>
                        <el-table-column prop="reason" label="变更原由"></el-table-column>
                        <el-table-column prop="version" label="版本号"></el-table-column>
                        <el-table-column prop="initiator" label="发起人"></el-table-column>
                        <el-table-column prop="effectiveDate" label="实施时间"></el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </template>
        <template v-else-if="show == 2">
            合同评审
        </template>
        <template v-else-if="show == 3">
            <material></material>
        </template>
        <template v-else-if="show == 4">
            <manufacture></manufacture>
        </template>
    </div>
</template>

<script>
    import material from './list'
    import manufacture from './list1'
    export default {
        name: "index",
        components: {
            material,
            manufacture
        },
        data () {
            return {
                activeIndex: '1',
                form: {
                    num: '',
                    name: '',
                    role: ''
                },
                tableData: [
                    {
                        number: '1',
                        name: '2',
                        reason: '3',
                        version: '4',
                        initiator: '5',
                        effectiveDate: '6'
                    }
                ],
                show: 1
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                this.show = key;
            }
        }
    }
</script>

<style scoped>
    .form_box{
        margin-top: 20px;
    }
</style>