/**
 * 数据操作类库
 * 包含服务器枚举映射数据，省市地区等
 *
 */
var $UD = (function (w, $) {
    var $$ = {};

    var _getList = function (url) {
        var str = $.get({
            url: $UC.ctxPath + url,
            //data: {code: code},
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

    //枚举操作库
    $$.enums = function () {
        var _json = null;
        var _getJson = function () {
            _json = _json || _getList('/resource/enums/list');
            return _json;
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
            }
        };
    }();

    return $$;
})(window, jQuery)