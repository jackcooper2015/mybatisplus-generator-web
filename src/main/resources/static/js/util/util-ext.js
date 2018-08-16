/**
 * 项目业务全局资源
 * 该方法中的所有内容均可保留或删除
 */
(function () {
    /**
     * 全局Vue 对象属性
     * 这里设置的属性和方法会当做全局对象和方法被继承到具体页面中Vue对象中
     * 如果没有全局对象和方法请删除
     */
    $UU.global.vue_options = {
        data: function () {
            return {
                //全局 Vue data
            };
        },
        created: function () {
        },
        mounted: function () {
        },
        methods: {
            //全局 Vue method
        }
    };

    //公共数据对象

    /**
     * 设定公共枚举映射路径
     * 如果不设置，则默认值为 "/resource/enums/list";
     */
    $UD.global.resource.ENUMS_URL = "/resource/enums/list";


    /**
     * 示例：增加资源读取省集合
     *
     * 使用方法:
     * $UD.province.list()
     * $UD.province.get('beijing')
     */
    $UD.addRemoteResource("appName", "/resource/appName/list", function (getJson) {
        return {
            list: function () {
                return getJson();
            },
            get: function (key) {
                return getJson()[key];
            }
        }
    });

    $UD.addRemoteResource("events", "/events", function (getJson) {
        return {
            list: function () {
                return getJson();
            },
            get: function (key) {
                return getJson()[key];
            }
        }
    });


})();