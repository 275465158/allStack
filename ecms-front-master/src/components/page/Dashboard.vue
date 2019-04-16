<template>
    <div>
        <span>Welcome back, {{name}}, 请扫描下方二维码下载android手机端app</span>
        <div style="margin-top:20px;">
            <img src="/static/img/download.png"/>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                name: localStorage.getItem('ms_username')
            }
        },
        created() {
            this.$http.get('/dashboard');
        }
    }
</script>



