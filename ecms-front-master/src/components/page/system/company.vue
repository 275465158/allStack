<template>
    <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="公司名称:">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="公司地址:">
            <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="公司logo:">
            <el-upload
                    class="upload-demo"
                    :action="this.$path + '/company/uploadSimple'"
                    :on-success="fileUpload"
                    accept="image/*"
                    list-type="picture"
                    :with-credentials="true"
            >
                <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            <img width="100" height="100" v-if="imgUrl != ''" :src="imgUrl" />
        </el-form-item>
        <div style="text-align:center;">
        <el-button style="align:center;" type="primary" @click="save">修改</el-button>
        </div>
    </el-form>
</template>

<script>
    export default {
        data() {
            return {
                file: '',
                imgUrl: '',
                form: {
                    name: '',
                    address: '',
                    logo: ''
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            fileUpload(response, file, fileList){
                if (response.code == 1000) {
                    this.form.logo = response.data;
                }
            },
            getData() {
                this.$http.get(this.$path + '/company/get').then((res) => {
                    if (res.data.code == 1000) {
                        let company = res.data.data;
                        this.form.name = company.name;
                        this.form.address = company.address;
                        this.form.logo = company.logo;
                        if (company.logo != '' && company.logo != null) {
                            this.$http.post('/company/download', {filePath: company.logo}, {responseType: 'arraybuffer'}).then((res) => {
                                this.imgUrl = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((res, byte) => res + String.fromCharCode(byte), ''));
                            })
                        }
                    }
                })
            },
            save() {
                this.$http.post('/company/save', this.form).then((res) => {
                    if (res.data.code == 1000) {
                        this.$message.success('修改成功');
                    } else {
                        this.$message.error(res.data.message);
                    }
                })
            }
        }
    }

</script>