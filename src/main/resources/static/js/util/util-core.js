/**
 * 常用公共函数
 *
 * 使用方法
 * $UF.inArray(10, [0, 1, 3, 10]);
 */
var $UF = (function (w, d) {
    var $$ = {};

    /**
     * 数组或对象遍历
     *
     * @param {object} elements
     * @param {function} callback
     * @param {boolean} hasOwnProperty
     */
    $$.each = function (elements, callback, hasOwnProperty) {
        if (!elements) {
            return this;
        }
        if (typeof elements.length === 'number') {
            [].every.call(elements, function (el, idx) {
                return callback.call(el, idx, el) !== false;
            });
        } else {
            for (var key in elements) {
                if (hasOwnProperty) {
                    if (elements.hasOwnProperty(key)) {
                        if (callback.call(elements[key], key, elements[key]) === false) return elements;
                    }
                } else {
                    if (callback.call(elements[key], key, elements[key]) === false) return elements;
                }
            }
        }
        return this;
    };

    var _class2type = {};
    $$.each(['Boolean', 'Number', 'String', 'Function', 'Array', 'Date', 'RegExp', 'Object', 'Error'], function (i, name) {
        _class2type["[object " + name + "]"] = name.toLowerCase();
    });

    var _type = function (obj) {
        return obj == null ? String(obj) : _class2type[{}.toString.call(obj)] || "object";
    };

    /**
     * 检测对象是否为数组
     *
     * @param {object} obj 检测对象
     *
     * @returns {boolean} true 是 | false 否
     */
    $$.isArray = function (obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    };

    /**
     * 验证是否为字符串
     *
     * @param {object} obj 检测对象
     *
     * @returns {boolean} true 是 | false 否
     */
    $$.isString = function (obj) {
        return Object.prototype.toString.call(obj) === '[object String]';
    };

    /**
     * 验证是否为数字 10 | 10.5 | -10 | 0 | -10.5 均为数字
     *
     * @param {object} obj 对象
     *
     * @return {boolean} true 是 | false 否
     */
    $$.isNumber = function (obj) {
        return Object.prototype.toString.call(obj) === '[object Number]';
    };

    /**
     * 判断是否是Boolean对象
     *
     * @param {object} obj 对象
     *
     * @return {boolean} true 是 | false 否
     */
    $$.isBoolean = function (obj) {
        return Object.prototype.toString.call(obj) === '[object Boolean]';
    };

    /**
     * 判断是否是window对象 (需考虑obj为undefined的情况)
     *
     * @param {object} obj 对象
     *
     * @return {boolean} true 是 | false 否
     */
    $$.isWindow = function (obj) {
        return obj != null && obj === obj.window;
    };

    /**
     * 判断是否是function
     *
     * @param {object} obj 对象
     *
     * @return {boolean} true 是 | false 否
     */
    $$.isFunction = function (obj) {
        return _type(obj) === "function";
    };

    /**
     * 判断是否是object
     *
     * @param {object} obj 对象
     *
     * @return {boolean} true 是 | false 否
     */
    $$.isObject = function (obj) {
        return _type(obj) === "object";
    };

    /**
     * 判断数组中是否存在指定元素
     *
     * @param {string} searchString 指定的元素
     * @param {array} array         数组
     *
     * @returns {boolean}
     *      true 存在 | false 不存在
     *      array 不是数组则返回 false
     */
    $$.inArray = function (obj, array) {
        if (!this.isArray(array)) {
            return false;
        }
        for (var i = 0; i < array.length; i++) {
            if (obj == array[i]) return true;
        }
        return false;
    };

    /**
     * 深度拷贝函数，通 jquery 的 extend 方法相同
     * 使用方法：
     * $UF.extend({b: "3",
     *       c:function(){
     *           var cc = 1;
     *       }},
     * {a1:"1", a2:"2"})
     * =>
     * {b: "3", c: function(){..}, a1: "1", a2: "2"}
     *
     * @param {boolean} deep    是否深层拷贝
     * @param {object} target   目标对象
     * @param {object} source   源对象
     *
     * @returns {unresolved}
     */
    $$.extend = function () { //from jquery2
        var _isPlainObject = function (obj) {
            return $$.isObject(obj) && !$$.isWindow(obj) && Object.getPrototypeOf(obj) === Object.prototype;
        };

        var options, name, src, copy, copyIsArray, clone,
            target = arguments[0] || {},
            i = 1,
            length = arguments.length,
            deep = true;

        if (typeof target === "boolean") {
            deep = target;
            target = arguments[i] || {};
            i++;
        }

        if (typeof target !== "object" && !$$.isFunction(target)) {
            target = {};
        }

        if (i === length) {
            target = this;
            i--;
        }

        for (; i < length; i++) {
            if ((options = arguments[i]) != null) {
                for (name in options) {
                    src = target[name];
                    copy = options[name];

                    if (target === copy) {
                        continue;
                    }

                    if (deep && copy && (_isPlainObject(copy) || (copyIsArray = $$.isArray(copy)))) {
                        if (copyIsArray) {
                            copyIsArray = false;
                            clone = src && $$.isArray(src) ? src : [];
                        } else {
                            clone = src && _isPlainObject(src) ? src : {};
                        }

                        target[name] = $$.extend(deep, clone, copy);
                    } else if (copy !== undefined) {
                        target[name] = copy;
                    }
                }
            }
        }

        return target;
    };

    /**
     * 获取url 地址名称
     *
     * @return {string} 返回 http://url?query 的全路径
     */
    $$.getUrl = function () {
        var pageUrl = w.location.href;
        return pageUrl.trim();
    };

    /**
     * 获取 Uri
     *
     * @return {string} 返回 http://url 不带参数
     */
    $$.getUri = function () {
        var pageUrl = w.location.href;
        if (pageUrl.indexOf("?") == -1) {
            return pageUrl;
        } else {
            pageUrl = pageUrl.substring(0, pageUrl.indexOf("?"));
        }
        return pageUrl.trim();
    };

    /**
     * 获取Url QueryString 参数名称
     *
     * @return {string} a=b&c=d 形式的参数
     */
    $$.getUrlQuery = function () {
        var pageUrl = w.location.href;
        if (pageUrl.indexOf("?") == -1) {
            return "";
        } else {
            pageUrl = pageUrl.substring(pageUrl.indexOf("?") + 1);
        }
        return pageUrl.trim();
    };

    /**
     * 获取url参数的值
     * 例如 a=b, 则 getUrlParameter(a) => b
     * 参数不存在则返回""
     *
     * @param {string} name 参数key
     *
     * @returns {string} 返回参数的值
     */
    $$.getUrlParam = function (name) {
        var pageUrl = w.location.href;
        pageUrl = pageUrl.substring(pageUrl.indexOf("?") + 1);
        var para = pageUrl.split("&");
        var tempstr = "";
        for (var i = 0; i < para.length; i++) {
            tempstr = para[i].split("=");
            if (name == tempstr[0]) {
                return tempstr[1];
            }
        }
        return "";
    };


    /**
     * 获取文本框中的字符，返回数组
     * 每一行为一个数组成员
     * 参数不存在则返回""
     *
     * @param {string} text     参数key
     *
     * @returns {string} 返回参数的值,
     */
    $$.getTextAreaArray = function (text) {
        var str = text || '';
        str = str.trim();
        if (str.length === 0) {
            return "";
        }
        var tempStr = "";
        if (str.length > 0) {
            var tempVinList = str.split("\n");
            for (var i = 0; i < tempVinList.length; i++) {
                tempVinList[i] = tempVinList[i].trim().replace(/\s+/g, "");
                if (tempVinList[i].length > 0) {
                    tempStr = tempStr + (tempStr.length > 0 ? "," + tempVinList[i] : tempVinList[i]);
                }
            }
        }
        return tempStr;
    };


    /**
     * 格式化为百分比显示
     *
     * @param {string} s    字符
     * @param {number} n    保留小数位数
     *
     * @returns {string}
     */
    $$.toPercent = function (s, n) {
        if (s == 0) {
            return '0%';
        }
        if (!s) {
            return '';
        }

        n = n >= 0 && n <= 20 ? n : 2;
        if ((s + "").indexOf(".") > -1) {
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        }
        s = s + "";

        var l = s.split(".")[0].split("").reverse(),
            r = s.split(".")[1];
        var t = "";
        for (var i = 0; i < l.length; i++) {
            // t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            t += l[i];
        }
        var left = t.split("").reverse().join("");
        var result = r ? left + "." + r : left;
        return result ? result + "%" : "";
    };

    /**
     * 格式化为百分比显示
     *
     * @param {string} s    字符
     * @param {number} n    保留小数位数
     *
     * @returns {string}
     */
    $$.toPercent2 = function (s, n) {
        if (s == 0) {
            return '0%';
        }
        if (!s) {
            return '';
        }
        return this.toPercent(s * 0.01, n);
    };

    /**
     * 通过 lambda 表达式 筛选数组对象
     *
     * 使用方法
     *
     * var a = [1,2,3,4,5,6,7,8,9,10];
     * $UF.where(a, "( ) => true" ) --> [1,2,3,4,5,6,7,8,9,10]
     * $UF.where(a, "( n, i ) => n % 2 == 0" ) --> [2,4,6,8,10]
     *
     * var products = [
     *      {key: 1, prod: "Chai", cat: "Beverages", units: 39, reorderlevel: 10},
     *      {key: 2, prod: "Chang", cat: "Beverages", units: 17, reorderlevel: 25},
     *      {key: 3, prod: "Aniseed Syrup", cat: "Condiments", units: 13, reorderlevel: 25},
     *      {key: 75, prod: "Rhönbräu Klosterbier", cat: "Beverages", units: 125, reorderlevel: 25},
     *      {key: 76, prod: "Lakkalikööri", cat: "Beverages", units: 57, reorderlevel: 20},
     *      {key: 77, prod: "Original Frankfurter grüne Soße", cat: "Condiments", units: 32, reorderlevel: 15}
     * ] ;
     *
     * $UF.where(products, "( el, i, res, param ) => el.units <= el.reorderlevel && el.cat == param" , "Beverages")
     *      --> [{key: 2, prod: "Chang", cat: "Beverages", units: 17, reorderlevel: 25},
     *          {key: 43, prod: "Ipoh Coffee", cat: "Beverages", units: 17, reorderlevel: 25},
     *          {key: 70, prod: "Outback Lager", cat: "Beverages", units: 15, reorderlevel: 30}]
     *
     * $UF.where(products, "( el, i, res, param ) => res.length <= 6 && param.test( el.cat )", new RegExp( "^con", "i") );
     *      --> [{key: 3, prod: "Aniseed Syrup", cat: "Condiments", units: 13, reorderlevel: 25},
     *          {key: 4, prod: "Chef Anton's Cajun Seasoning", cat: "Condiments", units: 53, reorderlevel: 0},
     *          {key: 5, prod: "Chef Anton's Gumbo Mix", cat: "Condiments", units: 0, reorderlevel: 0},
     *          {key: 6, prod: "Grandma's Boysenberry Spread", cat: "Condiments", units: 120, reorderlevel: 25},
     *          {key: 8, prod: "Northwoods Cranberry Sauce", cat: "Condiments", units: 6, reorderlevel: 0},
     *          {key: 15, prod: "Genen Shouyu", cat: "Condiments", units: 39, reorderlevel: 5},
     *          {key: 16, prod: "Pavlova", cat: "Confections", units: 29, reorderlevel: 10}]
     *
     * var customers = [
     *      {name:"Maria Anders",city:"Berlin",zip:"12209",country:"Germany"},
     *      {name:"Ana Trujillo",city:"México D.F.",zip:"05021",country:"Mexico"},
     *      {name:"Antonio Moreno",city:"México D.F.",zip:"05023",country:"Mexico"},
     *      {name:"Karl Jablonski",city:"Seattle",zip:"98128",country:"USA"},
     *      {name:"Matti Karttunen",city:"Helsinki",zip:"21240",country:"Finland"},
     *      {name:"Zbyszek Piestrzeniewicz",city:"Warszawa",zip:"01-012",country:"Poland"}
     * ];
     *
     * $UF.where(customers, "( el, i, res, param ) => el.country == param", "USA" );
     *      --> [{name:"Howard Snyder",city:"Eugene",zip:"97403",country:"USA"},
     *          {name:"Yoshi Latimer",city:"Elgin",zip:"97827",country:"USA"},
     *          {name:"John Steel",city:"Walla Walla",zip:"99362",country:"USA"},
     *          {name:"Jaime Yorres",city:"San Francisco",zip:"94117",country:"USA"},
     *          {name:"Fran Wilson",city:"Portland",zip:"97219",country:"USA"},
     *          {name:"Rene Phillips",city:"Anchorage",zip:"99508",country:"USA"},
     *          {name:"Paula Wilson",city:"Albuquerque",zip:"87110",country:"USA"},
     *          {name:"Jose Pavarotti",city:"Boise",zip:"83720",country:"USA"},
     *          {name:"Art Braunschweiger",city:"Lander",zip:"82520",country:"USA"},
     *          {name:"Liz Nixon",city:"Portland",zip:"97201",country:"USA"},
     *          {name:"Liu Wong",city:"Butte",zip:"59801",country:"USA"},
     *          {name:"Helvetius Nagy",city:"Kirkland",zip:"98034",country:"USA"},
     *          {name:"Karl Jablonski",city:"Seattle",zip:"98128",country:"USA"}]
     *
     * @param {object} array    数组集合
     * @param {string} lambda   表达式
     *
     * @return {object} 经过筛选的集合
     */
    $$.where = function (array, lambda) {
        var _lambda = function (l) {
            var fn = l.match(/\((.*)\)\s*=>\s*(.*)/);
            var p = [];
            var b = "";

            if (fn.length > 0) fn.shift();
            if (fn.length > 0) b = fn.pop();
            if (fn.length > 0) p = fn.pop().replace(/^\s*|\s(?=\s)|\s*$|,/g, '').split(' ');

            // prepend a return if not already there.
            fn = ((!/\s*return\s+/.test(b)) ? "return " : "") + b;

            p.push(fn);

            try {
                return Function.apply({}, p);
            } catch (e) {
                return null;
            }
        };

        var fn = lambda;
        var a = array;
        // if type of parameter is string
        if (typeof fn == "string") {
            // try to make it into a function
            if ((fn = _lambda(fn)) == null) {
                // if fail, throw exception
                throw "Syntax error in lambda string: " + fn;
            }
        }
        // initialize result array
        var res = [];
        var l = a.length;
        // set up parameters for filter function call
        var p = [0, 0, res];
        // append any pass-through parameters to parameter array
        for (var i = 2; i < arguments.length; i++) p.push(arguments[i]);
        // for each array element, pass to filter function
        for (var i = 0; i < l; i++) {
            // skip missing elements
            if (typeof a[i] == "undefined") continue;
            // param1 = array element
            p[0] = a[i];
            // param2 = current indeex
            p[1] = i;
            // call filter function. if return true, copy element to results
            if (!!fn.apply(a, p)) res.push(a[i]);
        }
        // return filtered result
        return res;
    };

    /**
     * 加法
     * javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     *
     * @param {number} arg1     数字1
     * @param {number} arg2     数字2
     *
     * @return {number} arg1加上arg2的精确结果
     */
    $$.accAdd = function (arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m
    };

    /**
     * 减法
     * javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
     *
     * @param {number} arg1     数字1
     * @param {number} arg2     数字2
     *
     * @return {number} arg1减上arg2的精确结果
     */
    $$.accSub = function (arg1, arg2) {
        return this.accAdd(arg1, -arg2);
    };

    /**
     * 乘法
     * javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
     *
     * @param {number} arg1     数字1
     * @param {number} arg2     数字2
     *
     * @return {number} arg1乘以arg2的精确结果
     */
    $$.accMul = function (arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    };

    /**
     * 除法
     * javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
     *
     * @param {number} arg1     数字1
     * @param {number} arg2     数字2
     *
     * @return {number} arg1除以arg2的精确结果
     */
    $$.accDiv = function (arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    };

    return $$;
})(window, document);

/**
 * 数据校验函数，一般用于表单校验
 *
 * 使用方法
 * 校验为null => $UF.validate.null(null);
 * 校验不为空 => $UF.validate.notBlank('fdsf');
 *
 */
(function (w, $$) {
    var validate = {};

    /**
     * 必须为NULL或undefined
     *
     * @param {object} value    数据
     *
     * @returns {boolean} true 为NULL或undefined | false 非NULL或undefined
     */
    validate.null = function (value) {
        if (Object.prototype.toString.call(value) == "[object Null]") {
            return true;
        }
        if (Object.prototype.toString.call(value) == "[object Undefined]") {
            return true;
        }
        return false;
    };

    /**
     * 必须为null或空字符
     *
     * @param {object} value    数据
     *
     * @return {boolean} true 非null或空字符 | false null或空字符
     */
    validate.blank = function (value) {
        if (this.null(value)) {
            return true;
        }
        return (value + "").trim().length == 0;
    };

    /**
     * 不能为NULL或undefined
     *
     * @param {object} value    数据
     *
     * @return {boolean} true 非NULL或undefined | false NULL或undefined
     */
    validate.notNull = function (value) {
        if (Object.prototype.toString.call(value) == "[object Null]") {
            return false;
        }
        if (Object.prototype.toString.call(value) == "[object Undefined]") {
            return false;
        }
        return true;
    };

    /**
     * 不能为空字符串
     *
     * @param {object} value    数据
     *
     * @return {boolean} true 非空字符串 | false 空字符串
     */
    validate.notBlank = function (value) {
        if (this.null(value)) {
            return false;
        }
        return (value + "").trim().length > 0;
    };

    /**
     * 判断数组是否为空
     *
     * @param {object} value    数据
     *
     * @return {boolean} true 是数组 | false 不是数组
     */
    validate.arrayEmpty = function (value) {
        if (this.null(value)) {
            return true;
        }
        if (Object.prototype.toString.call(value) === '[object Array]') {
            return value.length <= 0;
        }
        return true;
    };

    /**
     * 验证数字,包括数字字符串
     *
     * @param {object} value    数据
     *
     * @return {boolean} true 是数字 | false 非数字
     */
    validate.number = function (value) {
        var reg = /^([+-]?)[1-9]\d+(.\d+|)|0.\d*[1-9]\d*|0$/
        return reg.test(value);
    };

    /**
     * 验证是否为非负整数 0 1 "1" 都认为true
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是非负整数 | false 不是非负整数
     */
    validate.isPositiveNumber = function (value) {

        if (0 === value || "0" === value) {
            return true;
        }
        var type = "^[0-9]*[1-9][0-9]*$";
        var r = new RegExp(type);
        return r.test(value);
    };

    /**
     * 正整数（不包括0）
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是正整数 | false 非正整数
     */
    validate.pInteger = function (value) {
        var reg = /^[1-9]\d*$/;
        return reg.test(value);
    };

    /**
     * 正数（正整数和正小数，包括0）
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是正数 | false 非正数
     */
    validate.pNumber = function (value) {
        var reg = /(^[1-9]\d*(\.\d+|)$)|(^0\.\d*[1-9]\d*$)|(^0$)/
        return reg.test(value);
    };

    /**
     * 验证是否是货币
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是货币 | false 非货币
     */
    validate.money = function (value) {
        var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        //000 错
        //0 对
        //0. 错
        //0.0 对
        //050 错
        //00050.12错
        //70.1 对
        //70.11 对
        //70.111错
        //500 正确
        return reg.test(value);
    };

    /**
     * 字母或汉字
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是字母或汉字 | false 非字母或汉字
     */
    validate.safeChar = function (value) {
        return /^[a-zA-Z0-9_.@\-\u4e00-\u9fa5]{1,100}$/g.test(value);
    };

    /**
     * 手机号码验证
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是字手机号 | false 非手机号
     */
    validate.mobile = function (value) {
        // var length = value.length;
        //var reg = /^[1-9]([0-9]{7,11})$/;
        return /^1[3|4|5|7|8][0-9]\d{8}$/.test(value);
    };

    /**
     * 电话号码验证
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是电话号码 | false 非电话号码
     */
    validate.telphone = function (value) {
        return /^(\d{3,4}-?)?\d{7,9}$/g.test(value);
    };

    /**
     * 邮政编码验证
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是邮政编码 | false 非邮政编码
     */
    validate.zipCode = function (value) {
        return /^[0-9]{6}$/.test(value);
    };

    /**
     * 身份证号码验证
     *
     * @param {string} idcard   身份证
     *
     * @return {boolean} true 是身份证号 | false 非身份证号
     */
    validate.idCardNo = function (idcard) {
        // var Errors = new Array("验证通过!", "身份证号码位数不对!",
        // "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!");
        var area = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外"
        };

        var Y, JYM;
        var S, M;
        var idcard_array = new Array();
        idcard_array = idcard.split("");
        // 地区检验
        if (area[parseInt(idcard.substr(0, 2))] == null) {
            return false;
        }

        var ereg = null;
        // 身份号码位数及格式检验
        switch (idcard.length) {
            case 15:
                if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                    ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
                    // 测试出生日期的合法性
                } else {
                    ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
                    // 测试出生日期的合法性
                }
                if (ereg.test(idcard)) {
                    return true;
                } else {
                    return false;
                }
            case 18:
                // 18位身份号码检测
                // 出生日期的合法性检查
                // 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                // 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
                    // 闰年出生日期的合法性正则表达式
                } else {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
                    // 平年出生日期的合法性正则表达式
                }
                if (ereg.test(idcard)) {// 测试出生日期的合法性
                    // 计算校验位
                    S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                        + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                        + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                        + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6
                        + parseInt(idcard_array[9]) * 3;
                    Y = S % 11;
                    M = "F";
                    JYM = "10X98765432";
                    M = JYM.substr(Y, 1);
                    // 判断校验位
                    if (M.toLowerCase() == idcard_array[17].toLowerCase()) {
                        return true;
                        // 检测ID的校验位
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            default:
                return false;
        }
    };

    /**
     * 验证字母或数字
     *
     * @author songw
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是字母或数字 | false 非字母或数字
     */
    validate.alpha_num = function (value) {
        return /^[0-9A-Z]*$/i.test(value);
    };

    /**
     * 数字验证
     *
     * @author songw
     *
     * @param val 待验证的数字
     * @param min 最小值
     * @param max 最大值
     * @param integer
     * @param float
     * @param positive
     * @param negative
     *
     * @returns {boolean} true 合法 | false 不合法
     */
    validate.num_ext = function (val, min, max, integer, float, positive, negative) {
        if (!/^[+-]?\d+(\.\d+)?$/.test(val))
            return false;
        if (max !== undefined) {
            if (+val > +max)
                return false;
        }
        if (min !== undefined) {
            if (+val < +min)
                return false;
        }
        if (integer !== undefined) {
            if (/\./.test(val))
                return false;
        }
        if (float !== undefined) {
            if (!/\./.test(val))
                return false;
        }
        if (positive !== undefined) {
            if (+val <= 0)
                return false;
        }
        if (negative !== undefined) {
            if (+val >= 0)
                return false;
        }
        return true;
    };

    /**
     * 验证字节长度是否在范围之内，中文字两个字节
     *
     * @param {string} val    数据
     * @param {number} minlen   最小长度
     * @param {number} maxlen   最大长度
     *
     * @return {boolean} true 在长度内 | false 不在长度内
     */
    validate.length = function (val, minlen, maxlen) {
        if (this.blank(val)) {
            return true;
        }
        if (this.null(val)) {
            return true;
        }

        var _val = String(val), _result = true;
        if ($$.isNumber(minlen)) {
            _result = _result && minlen <= _val.length;
        }
        if ($UF.isNumber(maxlen)) {
            _result = _result && maxlen >= _val.length;
        }
        return _result;
    };

    /**
     * 验证字节长度是否在范围之内，中文字两个字节
     *
     * @param {string} val    数据
     * @param {number} minlen   最小长度
     * @param {number} maxlen   最大长度
     *
     * @return {boolean} true 在长度内 | false 不在长度内
     */
    validate.lengthDbl = function (val, minlen, maxlen) {
        if (this.blank(val)) {
            return true;
        }
        if (this.null(val)) {
            return true;
        }

        var _val = String(val), _result = true;

        var length = _val.length;
        for (var i = 0; i < _val.length; i++) {
            if (_val.charCodeAt(i) > 127) {
                length++;
            }
        }

        if ($$.isNumber(minlen)) {
            _result = _result && minlen <= length;
        }
        if ($UF.isNumber(maxlen)) {
            _result = _result && maxlen >= length;
        }
        return _result;
    };

    /**
     * 邮箱验证
     *
     * @param {object} value    对象
     *
     * @return {boolean} true 是邮箱 | false 非邮箱
     */
    validate.email = function (value) {
        return /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value);
    };

    $$.validate = validate;
})(window, $UF);

/**
 * 货币处理函数
 *
 * 使用方法
 * 格式化货币显示 => $UF.money.display(12345.99, 2, true);
 * 分转换为元 => $UF.money.fenToYuan(1234);
 */
(function (w, $$) {
    var money = {};

    /**
     * 将数字或字符串数字格式化为货币格式显示
     * 例子:
     * $UF.money.display(123455, 2, true) => "￥123455.00";
     * $UF.money.display(123455.233, 2, true) => "￥123455.23";
     * $UF.money.display(123455.233, 2, false) => "123455.23";
     *
     * @param {number} yuan     货币数字或字符串（单位:元）
     * @param {number} scale    小数位数
     * @param {boolean} symbol   是否显示"￥"标记
     *
     * @returns {string}
     */
    money.display = function (yuan, scale, symbol) {

        var smb = symbol ? "￥" : "";
        if (yuan == null) {
            return "";
        }
        var str = Number(yuan);
        if (str === 0) {
            return smb + "0.00";
        }
        if (!str) {
            return "";
        }
        var minus = false;
        if (str < 0) {
            minus = true;
            str = Math.abs(str);
        }

        // if (f) {
        //     s = s * 0.01;
        // }
        // str = str * 0.01;
        scale = $$.isNumber(scale) ? scale : 2;
        scale = scale >= 0 && scale <= 20 ? scale : 2;
        // var n = 2;
        str = parseFloat((str + "").replace(/[^\d\.-]/g, "")).toFixed(scale) + "";
        var l = str.split(".")[0].split("").reverse(),
            r = str.split(".")[1];
        var t = "";
        for (var i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        var left = t.split("").reverse().join("");
        var result = r ? left + "." + r : left;
        return result ? (minus ? smb + '-' + result : smb + result) : "";
    };

    /**
     * 将数字或字符串数字格式化为货币格式显示
     *
     * @param {number} fen      货币数字或字符串（单位:分）
     * @param {boolean} symbol  是否显示"￥"标记
     * @param {number} scale    小数位数
     *
     * @returns {string}
     */
    money.displayFen = function (fen, symbol, scale) {
        var yuan = this.fenToYuan(fen);
        return this.display(yuan, scale, symbol)
    };

    /**
     * 元转为分，返回数字
     *
     * @param {number} yuan 元
     *
     * @returns {number}
     */
    money.yuanToFen = function (yuan) {
        yuan = Number(yuan);
        if (yuan === 0) {
            return 0;
        }
        if (!yuan) {
            return 0;
        }
        return $$.accMul(yuan, 100);
    };

    /**
     * 分转为元，返回数字
     *
     * @param {number} fen  分
     *
     * @returns {number}
     */
    money.fenToYuan = function (fen) {
        fen = Number(fen);
        if (fen === 0) {
            return 0;
        }
        if (!fen) {
            return 0;
        }
        return $$.accMul(fen, 0.01);
    };

    $$.money = money;
})(window, $UF);

/**
 * 日期格式转换函数
 *
 * 使用方法
 * $UF.date.format(new Date(), "YYYY-MM-DD HH:mm:ss");
 */
(function (w, $$) {
    var date = {};

    /**
     * 时间格式化,与 fotmat 方法不同的是传入的 date 参数为字符串
     *
     * @param {object} date  符合日期格式的字符串
     *
     * @return {Date} 格式化后的日期字符串
     */
    date.parse = function (date) {
        if (Object.prototype.toString.call(date) === "[object Date]") {
            return date;
        }
        if ($$.isNumber(date)) {

            if ((parseInt(date) + '').length == 10) {
                date = date * 1000
            }

            return new Date(date);
        }
        var d;
        date = date || "";
        date = date + "";

        if (date.length <= 0) {
            return null;
        }

        try {
            var a = date.split(/[^0-9]/);
            d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
            // d = new Date(date);
        }
        catch (e) {
            d = null;
        }
        return d;
    };

    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * 例子：
     * (new Date()).Format("YYYY-MM-DD HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
     * (new Date()).Format("YYYY-M-D h:m:s.S")      ==> 2006-7-2 8:9:4.18
     *
     * @param {object} date  日期对象
     * @param {string} fmt   参数
     *
     * @return {string} 格式化后的日期字符串
     */
    date.format = function (date, fmt) {

        function c(d) {
            var show = new moment(d).format(fmt);

            return show === "Invalid date" ? "" : show;
        };

        if ($$.validate.null(date)) {
            return "";
        }

        if (Object.prototype.toString.call(date) === "[object Date]") {
            return c(date);
        }

        if (!isNaN(date)) {
            timestamp = parseInt(date);
            if ((timestamp + "").length == 10) {
                timestamp = timestamp * 1000;
            }

            return c(timestamp);
        }

        return c(date);


        // date = date || null;
        //
        // if (Object.prototype.toString.call(date) != "[object Date]") {
        //     return this.formatFromString(date, fmt);
        // }
        //
        // var o = {
        //     "M+": date.getMonth() + 1, //月份
        //     "d+": date.getDate(), //日
        //     "H+": date.getHours(), //小时
        //     "m+": date.getMinutes(), //分
        //     "s+": date.getSeconds(), //秒
        //     "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        //     "S": date.getMilliseconds() //毫秒
        // };
        // if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        // for (var k in o)
        //     if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        // return fmt;
    };

    /**
     * 时间格式化，返回长日期字符串
     *
     * @param {object} date 日期对象
     *
     * @return {string} 格式化后的日期字符串
     */
    date.formatLong = function (date) {
        return this.format(date, "YYYY-MM-DD HH:mm:ss");
    };

    /**
     * 时间格式化，返回短日期字符串
     *
     * @param {object} date 日期对象
     *
     * @return {string} 格式化后的日期字符串
     */
    date.formatShort = function (date) {
        return this.format(date, "YYYY-MM-DD");
    };

    /**
     * 时间格式化,与 fotmat 方法不同的是传入的 date 参数为字符串
     *
     * @param {object} date  符合日期格式的字符串
     * @param {string} fmt   参数
     *
     * @return {string} 格式化后的日期字符串
     */
    date.formatFromString = function (date, fmt) {

        return this.format(date, fmt);

        //return new moment(date).format(fmt);

        // if($$.validate.null(date)){
        //     return "";
        // }
        // var d;
        //
        // // typeof value === 'number' && isNaN(value);
        //
        // if (!isNaN(date)) {
        //
        //     var timestamp = parseInt(date);
        //
        //     if ((timestamp + '').length == 10) {
        //         date = date * 1000
        //     }
        //
        //     if ((timestamp + '').length != 13) {
        //         return "";
        //     }
        //
        //     d = new Date(timestamp);
        //     return this.format(d, fmt);
        // }
        //
        // // d = new Date(date);
        // // if (String(d) != "Invalid Date") {
        // //     return this.format(d, fmt);
        // // }
        //
        // date = date || "";
        // date = date + "";
        //
        // if (date.length <= 0) {
        //     return "";
        // }
        //
        // try {
        //     var a = date.split(/[^0-9]/);
        //     d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
        //     // d = new Date(date);
        // }
        // catch (e) {
        //     d = null;
        // }
        // return this.format(d, fmt);
    };

    /**
     * 时间格式化,与 fotmatLong 方法不同的是传入的 date 参数为字符串
     *
     * @param {object} date 符合日期格式的字符串
     *
     * @return {string} 格式化后的日期字符串
     */
    date.formatLongFromString = function (date) {
        return this.formatFromString(date, "YYYY-MM-DD HH:mm:ss");
    };

    /**
     * 时间格式化,与 fotmatShort 方法不同的是传入的 date 参数为字符串
     *
     * @param {object} date 符合日期格式的字符串
     *
     * @return {string} 格式化后的日期字符串
     */
    date.formatShortFromString = function (date) {
        return this.formatFromString(date, "YYYY-MM-DD");
    };

    /**
     * 为日期类型设置时分秒, 返回日期类型
     *
     * @param {object} date      设置的日期
     * @param {number} hour      时
     * @param {number} minute    分
     * @param {number} second    秒
     *
     * @returns {date} 返回 date 对象
     */
    date.multSet = function (date, hour, minute, second) {
        date = date || null;
        if (Object.prototype.toString.call(date) != "[object Date]") {
            return null;
        }
        date.setHours(hour);
        date.setMinutes(minute);
        date.setSeconds(second);

        return date;
    };

    /**
     * 为日期类型设置时分秒，返回长日期字符串
     *
     * @param {object} date      设置的日期
     * @param {number} hour      时
     * @param {number} minute    分
     * @param {number} second    秒
     *
     * @return {string} 格式化后的日期字符串
     */
    date.multSetLong = function (date, hour, minute, second) {
        var date = this.multSet(date, hour, minute, second);
        return this.formatLong(date);
    };

    /**
     * 为日期类型设置时分秒，返回短日期字符串
     *
     * @param {object} date      设置的日期
     * @param {number} hour      时
     * @param {number} minute    分
     * @param {number} second    秒
     *
     * @return {string} 格式化后的日期字符串
     */
    date.multSetShort = function (date, hour, minute, second) {
        var date = this.multSet(date, hour, minute, second);
        return this.formatShort(date);
    };

    $$.date = date;
})(window, $UF);

/**
 * 对象扩展属性
 *
 * 使用方法
 * 字符串去空格： " string ".trim() => "string"
 * 日期格式化：(new Date()).format("YYYY-MM-DD HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
 */
(function (undefined) {

    /**
     * 去空格
     * 使用方法:
     * var a = " string  ";
     * a.trim() => "string"
     *
     * @returns {string} 去空格后的字符串
     */
    if (String.prototype.trim === undefined) { // fix for iOS 3.2
        String.prototype.trim = function () {
            return this.replace(/^\s+|\s+$/g, '');
        };
    }
    Object.setPrototypeOf = Object.setPrototypeOf || function (obj, proto) {
        obj['__proto__'] = proto;
        return obj;
    };

    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * 例子：
     * (new Date()).format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
     * (new Date()).format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18
     *
     * @param fmt 格式化字符
     *
     * @returns {string} 格式化后的日期
     */
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    /**
     * 为日期类型重新设置时分秒
     *
     * @param   hour 时
     * @param   minute 分
     * @param   second 秒
     *
     * @returns {Date} 返回日期对象
     */
    Date.prototype.setTime = function (hour, minute, second) {
        this.setHours(hour);
        this.setMinutes(minute);
        this.setSeconds(second);

        return this;
    };

})();

/**
 * UI操作层面的通用方法
 * VUE布局、弹窗、ajax操作、页面组件等等
 * 页面中需要设置contextPath，相当于 httpServletRequest.getContextPath()
 * <script>
 *   $UC = {
 *       ctxPath: ""
 *   };
 *   </script>
 */
var $UU = (function (w, d) {
    var $$ = {};

    //全局对象
    var global = {};

    //全局vue参数
    global.vue_options = {};

    var _vue_count = 0;

    /**
     * 初始化页面的 vue 组件,返回生成的 vue 对象
     * 例子:
     * var vue = $UU.init({
     *       data:{
     *       },
     *       methods:{
     *          fun1:function(){},
     *          fun2:function(){}
     *       }
     *       created:function(){
     *           console.log("vue created");
     *       }
     * });
     *
     * @param options 参数
     * options.data:
     * options.created:
     * options.mounted:
     * options.methods:
     * options.computed:
     * options.watch:
     *
     * @return {object} vue 对象
     */
    $$.init = function (options) {
        options = options || {};
        options.loading = !$UF.isBoolean(options.loading) ? true : options.loading;
        options.appid = options.appid || "#app";

        var __this = this;
        if (options.loading) {
            this.loading("show");
        }

        var settings = {
            data: {
                //element-ui 日期格式控件1
                datePickerOptions1: {
                    disabledDate: function (time) {
                        return time.getTime() < Date.now() - 8.64e7;
                    }
                },
                //element-ui 日期格式控件2
                datePickerOptions2: {
                    shortcuts: [{
                        text: '今天',
                        onClick: function (picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick: function (picker) {
                            var date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick: function (picker) {
                            var date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                //分页
                pagination: {
                    index: 1, //当前页码
                    size: 10, //每页记录数
                    total: 0 //记录总数
                }
            },
            methods: {},
            computed: {},
            watch: {},
            components: {}
        };

        //加载自定义公共vue对象
        if ($UF.isFunction(this.global.vue_options.data)) {
            this.global.vue_options.data = $UF.extend(settings.data, this.global.vue_options.data());
            delete this.global.vue_options.data;
        }
        settings = $UF.extend(settings, this.global.vue_options);

        //加载页面vue对象
        if ($UF.isFunction(options.data)) {
            settings.data = $UF.extend(settings.data, options.data());
            delete options.data;
        }
        settings = $UF.extend(settings, options);

        //暴露data对象到windows，供调试使用
        _vue_count++;
        var n = _vue_count > 1 ? _vue_count : "";
        w["$data" + n] = settings.data;

        //创建Vue对象
        w["$vue" + n] = new Vue({
            el: options.appid,
            created: function () {
                if ($UF.isFunction(settings.created)) {
                    settings.created.call(this);
                }
                if ($UF.isFunction(__this.global.vue_options.created)) {
                    __this.global.vue_options.created.call(this);
                }
            },
            mounted: function () {
                if (options.loading) {
                    __this.loading("hide");
                }
                if ($UF.isFunction(settings.mounted)) {
                    settings.mounted.call(this);
                }
                if ($UF.isFunction(__this.global.vue_options.mounted)) {
                    __this.global.vue_options.mounted.call(this);
                }
            },
            data: settings.data,
            methods: settings.methods,
            computed: settings.computed,
            watch: settings.watch,
            components: settings.components
        });
        return w["$vue" + n];
    };

    var _dom = function (str) {
        if (typeof (str) !== 'string') {
            if ((str instanceof Array) || (str[0] && str.length)) {
                return [].slice.call(str);
            } else {
                return [str];
            }
        }
        if (!$$.__create_dom_div__) {
            $$.__create_dom_div__ = document.createElement('div');
        }
        $$.__create_dom_div__.innerHTML = str;
        return [].slice.call($$.__create_dom_div__.childNodes);
    };

    /**
     * 显示或隐藏页面加载动画
     * 例子:
     * $UU.loading("show")
     * $UU.loading("hide")
     *
     * @param action show | hide
     */
    $$.loading = function (action) {
        var loading1 = function (action) {
            var load = '<div class="ui-loading-selector ui-loading fn-hide">\
                <div class="ui-loading-container">\
                <div class="ui-loading-circular_1 ui-loading-circular"></div>\
                <div class="ui-loading-circular_2 ui-loading-circular"></div>\
                <div class="ui-loading-circular_3 ui-loading-circular"></div>\
                <div class="ui-loading-circular_4 ui-loading-circular"></div>\
                <div class="ui-loading-circular_5 ui-loading-circular"></div>\
                <div class="ui-loading-circular_6 ui-loading-circular"></div>\
                <div class="ui-loading-circular_7 ui-loading-circular"></div>\
                <div class="ui-loading-circular_8 ui-loading-circular"></div>\
                <div class="fn-clear"></div>\
            </div></div>';
            var doc = d.querySelector(".ui-loading-selector");
            if (!doc) {
                doc = _dom(load)[0];
                // var first = d.body.lastChild;
                d.body.appendChild(doc);
            }

            // var supportPageOffset = window.pageXOffset !== undefined;
            // var isCSS1Compat = ((document.compatMode || "") === "CSS1Compat");
            //
            // var x = supportPageOffset ? window.pageXOffset : isCSS1Compat ? document.documentElement.scrollLeft : document.body.scrollLeft;
            // var y = supportPageOffset ? window.pageYOffset : isCSS1Compat ? document.documentElement.scrollTop : document.body.scrollTop;

            // var w = document.documentElement.offsetWidth || document.body.offsetWidth ;
            var h = document.documentElement.clientHeight || document.body.clientHeight;
            doc.style.height = h + "px";
            if (action == "show") {
                doc.classList.remove("fn-hide");
            } else if (action == "hide") {
                doc.classList.add("fn-hide");
            }
        };

        var loading2 = function (action) {
            var load = '<div class="ui-loading-selector el-loading-mask">\
               <div class="el-loading-spinner">\
               <svg viewBox="25 25 50 50" class="circular">\
                   <circle cx="50" cy="50" r="20" fill="none" class="path"></circle>\
               </svg>\
               </div>\
            </div>';

            var doc = d.querySelector(".ui-loading-selector");
            if (!doc) {
                doc = _dom(load)[0];
                d.body.appendChild(doc);
            }

            var dch = document.documentElement.clientHeight || document.body.clientHeight;
            var doh = document.documentElement.offsetHeight || document.body.offsetHeight;
            // console.log(document.documentElement.clientHeight);
            // console.log(document.documentElement.offsetHeight);
            // console.log(document.body.clientHeight);
            // console.log(document.body.offsetHeight);
            // console.log(document.documentElement.scrollHeight);
            // console.log(document.body.scrollHeight);
            // console.log(document.documentElement.scrollTop);
            // console.log(document.body.scrollTop);
            // console.log(window.pageYOffset);
            doc.style.height = (dch > doh ? dch : doh) + "px";

            if (action == "show") {
                doc.classList.remove("fn-hide");
            } else if (action == "hide") {
                doc.classList.add("fn-hide");
            }
        };

        loading2(action);
    };

    $$.global = global;

    return $$;
}(window, document));

/**
 * ajax函数
 *
 */
(function (w, v, $$) {
    var http = {};

    /**
     * ajax get 请求
     * 例子:
     * $UU.http.get(
     *      "http:url", {p1:xxx, p2:xxx},
     *      function(response){
     *          var data = response.data;
     *      },{
     *          loading:        true | false
     *          before:         function(){},
     *          after:          function(){}
     *      }
     * );
     *
     * @param url 请求地址
     * @param data request对象
     * @param success 回调成功对象
     * @param options 其它参数
     *              requestBody {boolean}   body请求
     *              globalError {boolean}   全局处理业务异常
     *              loading     {boolean}   是否显示通用loading动画
     *              before      {function}   请求钱执行的方法
     *              after       {function}   请求后执行的方法
     */
    http.get = function (url, data, success, options) {
        options = options || {};
        if ($UF.isFunction(options.before)) {
            options.before();
        }
        var o = {};
        // o.emulateJSON = !!!options.json;
        var globalError = !!options.globalError;
        o.params = data;
        var loading = !!options.loading;

        if (loading) {
            $$.loading("show");
        }
        v.http.get($UC.ctxPath + url, o).then(function (response) {
            if (globalError && response.data.code && response.data.code != 0) {
                v.prototype.$message.error('错误信息：' + response.body.msg + '');
            } else {
                success(response);
            }

            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        }, function (response) {
            var msg = "发生错误";
            if (response.body) {
                msg = response.data.msg || "";
                if (response.data.data) {
                    msg += "," + (response.data.data.message || "");
                }
            }
            v.prototype.$message.error(msg);
            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        });
    };

    /**
     * ajax post 请求
     * 例子:
     * $UU.http.post(
     *      "http:url", {p1:xxx, p2:xxx},
     *      function(response){
     *          var data = response.data;
     *      },{
     *          requestBody:    true | false
     *          loading:        true | false
     *          before:         function(){},
     *          after:          function(){}
     *      }
     * );
     *
     * @param url 请求地址
     * @param data request对象
     * @param success 回调成功对象
     * @param options 其它参数
     *              requestBody {boolean}   body请求
     *              globalError {boolean}   全局处理业务异常
     *              loading     {boolean}   是否显示通用loading动画
     *              before      {function}  请求钱执行的方法
     *              after       {function}  请求后执行的方法
     */
    http.post = function (url, data, success, options) {
        options = options || {};
        if ($UF.isFunction(options.before)) {
            options.before();
        }
        var o = {};
        o.emulateJSON = !!!options.requestBody;
        var globalError = !!options.globalError;
        var loading = !!options.loading;

        if (loading) {
            $$.loading("show");
        }
        // o.params = data;
        v.http.post($UC.ctxPath + url, data, o).then(function (response) {
            if (globalError && response.data.code && response.data.code != 0) {
                v.prototype.$message.error('错误信息：' + response.body.msg + '');
            } else {
                success(response);
            }

            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        }, function (response) {
            var msg = "发生错误";
            if (response.body) {
                msg = response.data.msg || "";
                if (response.data.data) {
                    msg += "," + (response.data.data.message || "");
                }
            }
            v.prototype.$message.error(msg);
            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        });
    };

    /**
     * ajax put 请求
     * 例子:
     * $UU.http.put(
     *      "http:url", {p1:xxx, p2:xxx},
     *      function(response){
     *          var data = response.data;
     *      },{
     *          requestBody:    true | false
     *          loading:        true | false
     *          before:         function(){},
     *          after:          function(){}
     *      }
     * );
     *
     * @param url 请求地址
     * @param data request对象
     * @param success 回调成功对象
     * @param options 其它参数
     *              requestBody {boolean}   body请求
     *              globalError {boolean}   全局处理业务异常
     *              loading     {boolean}   是否显示通用loading动画
     *              before      {function}  请求钱执行的方法
     *              after       {function}  请求后执行的方法
     */
    http.put = function (url, data, success, options) {
        options = options || {};
        if ($UF.isFunction(options.before)) {
            options.before();
        }
        var o = {};
        o.emulateHTTP = false;
        o.emulateJSON = !!!options.requestBody;
        var globalError = !!options.globalError;
        var loading = !!options.loading;

        if (loading) {
            $$.loading("show");
        }
        // o.params = data;
        v.http.put($UC.ctxPath + url, data, o).then(function (response) {
            if (globalError && response.data.code && response.data.code != 0) {
                v.prototype.$message.error('错误信息：' + response.body.msg + '');
            } else {
                success(response);
            }

            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        }, function (response) {
            var msg = "发生错误";
            if (response.body) {
                msg = response.data.msg || "";
                if (response.data.data) {
                    msg += "," + (response.data.data.message || "");
                }
            }
            v.prototype.$message.error(msg);
            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        });
    };

    /**
     * ajax delete 请求
     * 例子:
     * $UU.http.delete(
     *      "http:url", {p1:xxx, p2:xxx},
     *      function(response){
     *          var data = response.data;
     *      },{
     *          loading:        true | false
     *          before:         function(){},
     *          after:          function(){}
     *      }
     * );
     *
     * @param url 请求地址
     * @param data request对象
     * @param success 回调成功对象
     * @param options 其它参数
     *              requestBody {boolean}   body请求
     *              globalError {boolean}   全局处理业务异常
     *              loading     {boolean}   是否显示通用loading动画
     *              before      {function}   请求钱执行的方法
     *              after       {function}   请求后执行的方法
     */
    http.delete = function (url, data, success, options) {
        options = options || {};
        if ($UF.isFunction(options.before)) {
            options.before();
        }
        var o = {};
        o.emulateHTTP = false;
        var globalError = !!options.globalError;
        o.params = data;
        var loading = !!options.loading;

        if (loading) {
            $$.loading("show");
        }
        v.http.delete($UC.ctxPath + url, o).then(function (response) {
            if (globalError && response.data.code && response.data.code != 0) {
                v.prototype.$message.error('错误信息：' + response.body.msg + '');
            } else {
                success(response);
            }

            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        }, function (response) {
            var msg = "发生错误";
            if (response.body) {
                msg = response.data.msg || "";
                if (response.data.data) {
                    msg += "," + (response.data.data.message || "");
                }
            }
            v.prototype.$message.error(msg);
            if ($UF.isFunction(options.after)) {
                options.after();
            }
            if (loading) {
                $$.loading("hide");
            }
        });
    };

    $$.http = http;

    //全局拦截器
    v.http.interceptors.push(function (request, next) {
        // console.log("interceptors before");
        // $$.loading("show");
        next(function (response) {
            // $$.loading("hide");
            // console.log("interceptors after");
            if (!response.ok) {
                v.prototype.$message.error("网络异常，错误码:" + response.status + "");
            }
            //自定义逻辑错误
            //TODO
            return response;
        })
    })
})(window, Vue, $UU);

/**
 * Vue表单校验组件,比官方的校验更加灵活，可以脱离<el-form>对象，在任意表单对象中使用
 * 例子：
 *
 */
Vue.use(
    //Vue 自定义表单校验插件
    (function ($) {
        var $$ = {};

        var _current_group = "common";

        var _config = {
            validate_group: {
                common: []
            },

            //验证函数, 返回 true 验证成功，返回 false 验证失败
            validate: function (group) {
                group = group || _current_group;
                // console.log(group);

                var items = this.validate_group[group] || null;
                // console.log(items);

                if (Object.prototype.toString.call(items) != "[object Array]") {
                    return true;
                }

                var v = true;
                for (var i = 0; i < items.length; i++) {
                    if (!_validate_item(items[i])) {
                        v = false;
                    }
                }
                return v;
            },
            //清除验证
            clear: function (group) {
                group = group || _current_group;
                var items = this.validate_group[group] || null;
                if (Object.prototype.toString.call(items) != "[object Array]") {
                    return true;
                }
                for (var i = 0; i < items.length; i++) {
                    _clearItem(items[i]);
                }
            }
        };

        var _get_input = function (el) {
            var $el = $(el), $fi = [];
            if ($el.hasClass("el-input")
                || $el.hasClass("el-autocomplete")
                || $el.hasClass("el-select")
            ) {
                $fi = $el.find("input:first");
                $fi.fitype = "input";
            }
            if ($el.hasClass("el-textarea")) {
                $fi = $el.find("textarea:first");
                $fi.fitype = "textarea";
            }
            if ($el.hasClass("el-radio-group")) {
                $fi = $el;
                $fi.fitype = "radio-group";
            }
            if ($el.hasClass("el-input-number")) {
                $fi = $el;
                $fi.fitype = "input-number";
            }
            return $fi;
        };

        var _get_value = function (fi) {
            if (fi.fitype == "radio-group") {
                return fi.find("label.is-checked input").val() || "";
            }
            if (fi.fitype == "input-number") {
                return fi.find("input").val() || "";
            }
            return fi.val() || "";
        };

        //单个表单项的验证
        var _validate_item = function (item) {
            // console.log(item);

            var $el = $(item.el), $fi = _get_input(item.el), rules = item.rules;

            var condition = true;
            if ($UF.isFunction(item.condition)) {
                condition = item.condition.call(item.context, item.index);
            } else if ($UF.isBoolean(item.condition)) {
                condition = item.condition;
            }

            $el.parent().removeClass("ui-is-error");
            $el.next().remove();

            if ($fi.length == 0) {
                return true;
            }

            if (!condition) {
                return true;
            }
            // var val = $fi.val().trim() || "";
            // var val = $fi.val() || "";
            var val = _get_value($fi);
            var result = true, err_rule = null;

            if (Object.prototype.toString.call(rules) != "[object Array]") {
                result = _validate_rule(rules, val);
            }

            for (var i = 0; i < rules.length; i++) {
                if (!_validate_rule(rules[i], val)) {
                    result = false;
                    err_rule = rules[i];
                    break;
                }
            }

            //校验失败
            if (!result) {
                if ($fi.fitype != "radio-group"
                    && $fi.fitype != "input-number"
                    && $fi.fitype != "textarea"
                ) {
                    $fi.addClass("el-input__inner");
                }
                if ($fi.fitype == "textarea") {
                    $el.after("<div class='ui-item__error__textarea' style='width:" + $el.css("width") + "; text-align:left;'>" + err_rule.message + "</div>");
                } else {
                    $el.after("<div class='ui-item__error' style='width:" + $el.css("width") + "; text-align:left;'>" + err_rule.message + "</div>");
                }
                $el.parent().addClass("ui-is-error");
            }

            return result;
        };

        //单个规则的验证
        var _validate_rule = function (rule, val) {
            var a = {
                //不能为null和undefined
                not_null: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    return $UF.validate.notNull(val);
                },
                //验证非空
                required: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    return $UF.validate.notBlank(val);
                },
                //验证为货币
                money: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    if ($UF.validate.blank(val)) {
                        return true;
                    }
                    return $UF.validate.money(val.trim());
                },
                //验证为非负数（包括正整数、正小数、和0）
                p_number: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    if ($UF.validate.blank(val)) {
                        return true;
                    }
                    return $UF.validate.pNumber(val.trim());
                },
                //不能为0
                not_zero: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    if ($UF.validate.blank(val)) {
                        return true;
                    }
                    return !(0 === Number((val + "").trim()));
                },
                //字母，数字验证
                alpha_num: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    return $UF.validate.alpha_num(val);
                },
                //数字验证
                number: function (arg) {
                    if (!arg) {
                        return true;
                    }

                    if ($UF.isBoolean(arg)) {
                        return $UF.validate.number(val);
                    }

                    return $UF.validate.num_ext(val, arg.min, arg.max, arg.integer, arg.float, arg.positive, arg.negative);
                },
                //验证邮箱
                email: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    if ($UF.validate.blank(val)) {
                        return true;
                    }
                    return $UF.validate.email(val.trim());
                },
                //数字范围校验
                range: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    if ($UF.validate.blank(val)) {
                        return true;
                    }
                    if (!$UF.validate.number(val)) {
                        return false;
                    }

                    var _val = Number(val), _result = true;
                    if ($UF.isNumber(arg.min)) {
                        _result = _result && arg.min <= _val;
                    }
                    if ($UF.isNumber(arg.max)) {
                        _result = _result && arg.max >= _val;
                    }
                    return _result;
                },
                //字符长度验证
                length: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    return $UF.validate.length(val, arg.min, arg.max);
                },
                //字符长度验证,汉字占2个字符
                length_dbl: function (arg) {
                    if (!arg) {
                        return true;
                    }
                    return $UF.validate.lengthDbl(val, arg.min, arg.max);
                }
            };

            var result = true;
            for (var key in rule) {
                //执行自定义校验功能,不支持异步校验
                if ($UF.isFunction(rule[key])) {
                    var v = rule[key](val);
                    if ($UF.isBoolean(v)) {
                        result = result && v;
                    } else if ($UF.isObject(v)) {
                        result = result && v.result;
                        if ($UF.validate.notBlank(v.message)) {
                            rule.message = v.message;
                        }
                    }
                    return result;
                }

                if (key === "message") {
                    continue;
                }

                if (!(key in a)) {
                    continue;
                }

                result = result && a[key](rule[key]);
            }
            return result;
        };

        var _getGroup = function (rule) {
            return rule.group || "common";
        };

        var _addItem = function (uid, el, rule, context, index) {
            // console.log(el);
            var group_name = _getGroup(rule);
            var rules = rule.rules || [];
            var condition = rule.condition || null;
            var index = index || 0;

            if (!(group_name in _config.validate_group)) {
                _config.validate_group[group_name] = [];
            }
            _config.validate_group[group_name].push({uid: uid, el: el, context: context, rules: rules, condition: condition, index: index});

            //绑定blur事件
            var $fi = _get_input(el);
            var f = function () {
                _validate_item({el: el, rules: rules})
            };
            $fi.bind("blur", f);
        };

        var _removeItem = function (uid, rule) {
            var group_name = _getGroup(rule);
            for (var i = 0, flag = true, len = _config.validate_group[group_name].length; i < len; flag ? i++ : i) {
                if (_config.validate_group[group_name][i] && _config.validate_group[group_name][i].uid == uid) {
                    _config.validate_group[group_name].splice(i, 1);
                    flag = false;
                } else {
                    flag = true;
                }
            }
        };

        //清除验证错误的信息
        var _clearItem = function (item) {
            var $el = $(item.el), $fi = _get_input(item.el), rules = item.rules;

            $el.parent().removeClass("ui-is-error");
            $el.next().remove();
        };

        Vue.prototype.$ui_validate = _config;

        $$.install = function (Vue, options) {
            //自定义表单验证指令,只适用于element-ui表单元素
            Vue.directive('ui-rule', {
                // 当绑定元素插入到 DOM 中。
                inserted: function (el, binding, vnode) {
                    // console.log(el);
                    // console.log(binding);
                    // console.log("inserted: uid -> " + vnode.componentInstance._uid);
                    // var context = vnode.context;
                    // console.log(binding.value);
                    var rule = binding.value;

                    if ($UF.isNumber(rule.index)) {
                        _addItem(vnode.componentInstance._uid, el, rule.rule, vnode.context, rule.index);
                    } else {
                        _addItem(vnode.componentInstance._uid, el, rule, vnode.context);
                    }

                },
                update: function (el, binding, vnode, oldVnode) {
                },
                componentUpdated: function (el, binding, vnode, oldVnode) {
                },
                unbind: function (el, binding, vnode, oldVnode) {
                    // console.log("unbind: uid -> " + vnode.componentInstance._uid);
                    var rule = binding.value;

                    if ($UF.isNumber(rule.index)) {
                        _removeItem(vnode.componentInstance._uid, rule.rule);
                    } else {
                        _removeItem(vnode.componentInstance._uid, rule);
                    }
                }
            });

            // Vue.directive('ui-rule-index', {
            //     // 当绑定元素插入到 DOM 中。
            //     inserted: function (el, binding, vnode) {
            //         console.log(binding.value);
            //     }
            // });

            // console.log(this);
            // console.log(Vue);

            // Vue.directive('ui-rule-check', {
            //     inserted: function (el, binding, vnode) {
            //         var value = binding.value;
            //         _current_group = value;
            //     },
            //     update: function (el, binding, vnode) {
            //         var oldValue = binding.oldValue;
            //         var value = binding.value;
            //         _current_group = value;
            //         if (oldValue != value) {
            //             //_current_group = value;
            //         }
            //     }
            // });
        };

        return $$;
    })(jQuery)
);

/**
 * @version: v1.0.3
 * @date:    2018-05-02 14:55:56
 */
/**
 * 数据操作类库
 * 包含服务器枚举映射数据，省市地区等
 *
 */
var $UD = (function (w, $) {
    var $$ = {};

    //全局对象
    var global = {};

    //全局vue参数
    global.resource = {
        ENUMS_URL: '/resource/enums/list'
    };

    var _getList = function (url) {
        var str = $.get({
            url: $UC.ctxPath + url,
            data: {},
            async: false
        }).responseText;
        var json;
        try {
            json = eval("(" + str + ")");
        } catch (e) {
        }
        return json;
    };

    var _dataCache = {};

    //枚举操作库
    $$.enums = function () {
        var __this = this;
        var _getJson = function () {
            if (!_dataCache.enums) {
                _dataCache.enums = _getList(global.resource.ENUMS_URL);
            }
            return _dataCache.enums;
        };

        return {
            /**
             * 获取枚举Json列表
             *
             * @returns {object} 返回所有json集合
             */
            list: function () {
                return _getJson();
            },
            /**
             * 获取单个枚举json对象
             *
             * @param enumName 枚举名
             * @returns {object} 返回单个枚举json对象
             */
            one: function (enumName) {
                enumName = enumName || "";
                if (enumName.length <= 0) {
                    return null;
                }

                var find = _getJson()[enumName] || null;
                return find;
            },
            /**
             * 获取单个枚举对象的集合形式
             *
             * @param enumName 枚举名
             * @returns {array}
             */
            listOne: function (enumName) {
                var find = this.one(enumName);
                if (find == null) {
                    return [];
                }

                var list = [];
                for (var key in find) {
                    list.push(find[key]);
                }

                return list;
            },
            /**
             * 获取单个枚举对象的value的集合形式
             *
             * @param enumName 枚举名
             * @return {array}
             */
            listOneValue: function (enumName) {
                var find = this.one(enumName);
                if (find == null) {
                    return [];
                }

                var list = [];
                for (var key in find) {
                    list.push(find[key].value);
                }

                return list;
            },
            /**
             * 根据value获取某个枚举项的值
             *
             * @param enumName 枚举名
             * @param value 枚举的值
             * @returns {object}
             */
            fromValue: function (enumName, value) {
                var find = this.one(enumName);
                if (find == null) {
                    return null;
                }
                for (var key in find) {
                    if (find[key].value == value) {
                        return find[key];
                    }
                }
                return null;
            },
            /**
             * 根据text获取某个枚举项的值
             *
             * @param enumName 枚举名
             * @param value 枚举的值描述
             * @returns {object}
             */
            fromText: function (enumName, text) {
                var find = this.one(enumName);
                if (find == null) {
                    return null;
                }
                for (var key in find) {
                    if (find[key].text == text) {
                        return find[key];
                    }
                }
                return null;
            },
            /**
             * 根据value获取某个枚举项的text值
             *
             * @param enumName 枚举名
             * @param value 枚举的值
             * @param defaultValue 默认值
             * @return {string}
             */
            getText: function (enumName, value, defaultValue) {
                defaultValue = defaultValue || "";
                var item = this.fromValue(enumName, value);
                if (item == null) {
                    return defaultValue;
                }
                return item.text;
            },
            /**  根据枚举名和枚举项name获取某个枚举项的value值 
             *
             *  @param enumName 枚举名 
             *  @param enumItemName  枚举某一项的name
             *  @param defaultValue 默认值 
             *  @return {string} 
             *  */
            getValue: function (enumName, enumItemName, defaultValue) {
                defaultValue = defaultValue || "";
                var _enum = this.one(enumName);
                if (!_enum) {
                    return defaultValue;
                }
                return _enum[enumItemName] ? _enum[enumItemName].value : defaultValue;
            }
        };
    }();

    /**
     * 添加自定义资源
     *
     * @param name 资源名称
     * @param url 资源获取地址
     * @param methodCall 回调处理方法
     * @param force 强制从服务器查询数据
     */
    $$.addRemoteResource = function (name, url, methodCall, force) {
        var _getJson = function () {
            if (force || !_dataCache[name]) {
                _dataCache[name] = _getList(url);
            }
            return _dataCache[name];
        };

        $$[name] = $UF.extend({}, methodCall(_getJson));
    };

    $$.global = global;
    return $$;
})(window, jQuery);