import Vue from 'vue';
import App from './App';
import router from './router';
import store from "./components/store";
import axios from 'axios';
import Qs from 'qs';
import VueAxios from 'vue-axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";
import { Message } from 'element-ui';
import 'vue-area-linkage/dist/index.css';
import VueAreaLinkage from 'vue-area-linkage';
import { VTable, VPagination } from 'vue-easytable';
import { checkPhone, checkEmail, formatDate, validationPassword } from '../static/js/validator.js';
import VueBarcode from '@xkeshi/vue-barcode'; //导入条形码插件
import VueQriously from 'vue-qriously';
import 'babel-polyfill'


Vue.use(VueQriously);
Vue.use(ElementUI, { size: 'small' });
Vue.component('barcode', VueBarcode);  //声明条形码组件
Vue.use(VueAreaLinkage);
Vue.component(VTable.name, VTable)
Vue.component(VPagination.name, VPagination)
axios.defaults.withCredentials = true;
Vue.prototype.$axios = axios;
Vue.prototype.checkPhone = checkPhone;
Vue.prototype.checkEmail = checkEmail;
Vue.prototype.formatDate = formatDate;
Vue.prototype.validationPassword = validationPassword;
Vue.prototype.$path = API_ROOT.baseURL;
var axios_instance = axios.create({
    baseURL: API_ROOT.baseURL,
    transformRequest: [function (data) {
        data = Qs.stringify(data);
        return data;
    }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    withCredentials: true
});

axios_instance.interceptors.response.use(res => {
    // 处理excel文件
    if (res.headers && (res.headers['content-type'].indexOf('application/octet-stream') >= 0)
        || res.headers['content-type'].indexOf('image') >= 0) {
        return res;
    }
    if (res.data.code == 1002) {
        localStorage.removeItem('ms_username');
        router.push('/login');
    } else if (res.data.code == 1001) {
        Message.error(res.data.message);
    } else if (res.data.code == 1000) {
        return res;
    }
}, error => {
    console.log(error.response);
    Message.error('请求失败');
})
Vue.use(VueAxios, axios_instance);

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    const role = localStorage.getItem('ms_username');
    if (!role && to.path !== '/login') {
        next('/login');
    } else {
        // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
        if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        } else {
            next();
        }
    }
})

router.onError((error) => {
    const pattern = /Loading chunk (\d)+ failed/g;
    const isChunkLoadFailed = error.message.match(pattern);
    const targetPath = router.history.pending.fullPath;
    if (isChunkLoadFailed) {
        router.replace(targetPath);
    }
})
Vue.directive('enterNumber2', {
    inserted: function (el) {
        el.addEventListener("keypress",function(e){
            e = e || window.event;
            let charcode = typeof e.charCode == 'number' ? e.charCode : e.keyCode;
            let re = /\d/;
            if(charcode == 46 || charcode == 109){
                if(el.value.includes('.')){
                    e.preventDefault();
                }
                return;
            }else if(!re.test(String.fromCharCode(charcode)) && charcode > 9 && !e.ctrlKey){
                if(e.preventDefault){
                    e.preventDefault();
                }else{
                    e.returnValue = false;
                }
            }
        });
    }
});

new Vue({
    router,
    render: h => h(App),
    store: store
}).$mount('#app');