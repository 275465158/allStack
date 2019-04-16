function checkPhone(rule,value,callback) {
    let mobile = /^1[3-578]\d{9}$/;
    let tel = /^\d{3,4}-?\d{7,9}$/;
    console.log(tel.test(value));
    if (!tel.test(value) && !mobile.test(value)) {
        return callback(new Error('请填写正确号码'));
    } else {
        return callback();
    }
}

function checkEmail(rule,value,callback) {
    let mailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if (!mailReg.test(value)) {
        return callback(new Error('请填写正确邮箱'));
    } else {
        return callback();
    }
}

function validationPassword(rule, v, callback) {
    var numasc = 0;
    var charasc = 0;
    var otherasc = 0;
    if(0==v.length){
        return callback(new Error('密码不能为空'));
    }else if(v.length<8||v.length>12){
        return callback(new Error('密码至少8位'));
    }else{
        for (var i = 0; i < v.length; i++) {
            var asciiNumber = v.substr(i, 1).charCodeAt();
            if (asciiNumber >= 48 && asciiNumber <= 57) {
                numasc += 1;
            }
            if ((asciiNumber >= 65 && asciiNumber <= 90)||(asciiNumber >= 97 && asciiNumber <= 122)) {
                charasc += 1;
            }
            if ((asciiNumber >= 33 && asciiNumber <= 47)||(asciiNumber >= 58 && asciiNumber <= 64)||(asciiNumber >= 91 && asciiNumber <= 96)||(asciiNumber >= 123 && asciiNumber <= 126)) {
                otherasc += 1;
            }
        }
        if(0==numasc) {
            return callback(new Error('密码必须含有数字'));
        }else if(0==charasc){
            return callback(new Error('密码必须含有字母'));
        }else if(0==otherasc){
            return callback(new Error('密码必须含有特殊字符'));
        }else{
            return callback();
        }
    }
};

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
}

export {
    checkPhone, checkEmail, formatDate, validationPassword
}