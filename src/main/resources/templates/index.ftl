<!doctype html>
<html class="no-js fixed-layout">
<head>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">
    <title>平台业务测试中心</title>
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">
    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="icon" type="image/png" href="assets/i/favicon.png">
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="alternate icon" type="image/png" href="${base}/static/icon/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#0e90d2">
    <link  rel="stylesheet" type="text/css" href="http://cdn.amazeui.org/amazeui/2.7.2/css/amazeui.min.css">
    <link  rel="stylesheet" type="text/css" href="http://amazeui.org/css/admin.css">
</head>
<body>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="//cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<header class="am-topbar am-topbar-inverse admin-header">
    <div class="am-topbar-brand">
        <strong>Reapal</strong> <small>mybatisplus代码生成工具</small><span class="am-badge am-badge-warning" id="env">beta</span>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-hide-sm-only"><a href="${base}/index" ><span class=""></span> <span class="am-icon-arrow-up">首页</span></a></li>
            <li class="am-hide-sm-only"><a href="${base}/logout" ><span class=""></span> <span class="am-icon-power-off">退出</span></a></li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        </ul>
    </div>
</header>

<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">数据库列表</strong>  <small></small></div>
            </div>

            <hr>

            <div class="am-g">
                <div class="am-u-sm-12 am-u-md-6">
                    <div class="am-btn-toolbar">
                        <div class="am-btn-group am-btn-group-xs">

                        </div>
                    </div>
                </div>

                <div class="am-u-sm-14 am-u-md-1">
                    <div class="am-input-group am-input-group-sm">
                        <a class="am-btn am-btn-default am-btn-xs am-hide-sm-only" href="${base}/edit"><span class="am-icon-plus"></span>新增</a>
                    </div>
                </div>
            </div>

            <div class="am-g">
                <div class="am-u-sm-12">
                    <form class="am-form">
                        <table class="am-table am-table-striped am-table-hover table-main">
                            <thead>
                            <tr>
                                <th class="table-check"><input type="checkbox"></th>
                                <th class="table-id">ID</th>
                                <th class="table-title">数据库</th>
                                <th class="table-type">类型</th>
                                <th class="table-author am-hide-sm-only">操作</th>
                                <th class="table-author am-hide-sm-only">选中</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (dbConfigList)?? >

                            <#list dbConfigList as dbConfig >
                            <tr>
                                <td><input type="checkbox"></td>
                                <td>${dbConfig_index+1}</td>
                                <td><a href="#"><#if (dbConfig.url)??>${dbConfig.url}</#if></a></td>
                                <td>${dbConfig.driver!}</td>
                                <td>
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a class="am-btn am-btn-default am-btn-xs am-hide-sm-only" href="${base}/edit?url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}"><span class="am-icon-pencil-square-o"></span>编辑</a>
                                            <a class="am-btn am-btn-default am-btn-xs am-hide-sm-only" href="${base}/delete?url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}"><span class="am-icon-trash-o"></span>删除</a>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a class="am-btn-sm am-btn-danger am-radius" href="${base}/tablelist?url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}">选定</a>
                                </td>
                            </tr>
                            </#list>
                            </#if>


                            </tbody>
                        </table>
                        <hr>
                        <p>注：请不要随意删除</p>
                    </form>
                </div>

            </div>
        </div>

        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-horizontal">© jack-cooper  &nbsp;&nbsp;&nbsp; 当前时间：${.now?string("yyyy-MM-dd HH:mm:ss")}  </p>
        </footer>

    </div>
    <!-- content end -->
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
<script src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="http://amazeui.org/js/app.js"></script>
<div class="amz-toolbar" id="amz-toolbar" style="right: 361px;"><a href="#top" title="回到顶部" class="am-icon-btn am-icon-arrow-up am-active" id="amz-go-top"></a> <a href="/getting-started/faq" title="常见问题" class="am-icon-faq am-icon-btn am-icon-question-circle"></a></div>
</body>
</html>