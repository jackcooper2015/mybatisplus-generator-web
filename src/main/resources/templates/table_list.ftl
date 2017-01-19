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
        <strong>Reapal</strong> <small>代码生成工具</small><span class="am-badge am-badge-warning" id="env">beta</span>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-hide-sm-only"><a href="${base}/index" ><span class=""></span> <span class="am-icon-arrow-up">首页</span></a></li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        </ul>
    </div>
</header>

<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">表格</strong> / <small>Table</small></div>
            </div>

            <hr>

            <div class="am-g">
                <div class="am-u-sm-12 am-u-md-6">
                    <div class="am-btn-toolbar">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
                            <button type="button" class="am-btn am-btn-default"><span class="am-icon-save"></span> 保存</button>
                            <button type="button" class="am-btn am-btn-default"><span class="am-icon-archive"></span> 审核</button>
                            <button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span> 删除</button>
                        </div>
                    </div>
                </div>

                <div class="am-u-sm-12 am-u-md-3">
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" class="am-form-field">
                        <span class="am-input-group-btn">
                            <button class="am-btn am-btn-default" type="button">搜索</button>
                        </span>
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
                                <th class="table-title">表名</th>
                                <th class="table-type">注解</th>
                                <th class="table-author am-hide-sm-only">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list tableList as table >
                            <tr>
                                <td><input type="checkbox"></td>
                                <td>${table_index+1}</td>
                                <td><#if (table.tableName)??>${table.tableName}</#if></td>
                                <td><#if (table.comments)??>${table.comments}</#if></td>
                                <td>
                                    <a href="${base}/code/<#if (table.tableName)??>${table.tableName}</#if>?url=<#if (dbConfig.url)??>${dbConfig.url}</#if>&driver=<#if (dbConfig.driver)??>${dbConfig.driver}</#if>&username=<#if (dbConfig.username)??>${dbConfig.username}</#if>&password=<#if (dbConfig.password)??>${dbConfig.password}</#if>&schema=<#if (dbConfig.schema)??>${dbConfig.schema}</#if>">修改</a>
                                </td>
                            </tr>
                            </#list>

                            
                            </tbody>
                        </table>
                        <hr>
                        <p>注：.....</p>
                    </form>
                </div>

            </div>
        </div>

        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
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