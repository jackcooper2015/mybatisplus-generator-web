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
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">数据库</strong> / <small>${dbConfig.url!}</small></div>
            </div>

            <hr>

            <div class="am-g">
                <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">

                </div>

                <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
                    <form class="am-form am-form-horizontal" action="${base}/save" method="post">

                        <div class="am-form-group">
                            <label for="url" class="am-u-sm-3 am-form-label">URL</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="url" name="url" value="${dbConfig.url!}" placeholder="输入数据库的url">
                                <small>如:jdbc:mysql://10.168.16.116:3306/test</small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="driver" class="am-u-sm-3 am-form-label">driver</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="driver" name="driver" value="${dbConfig.driver!}" placeholder="输入数据库的driver">
                                <small>如:com.mysql.jdbc.Driver</small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="username" class="am-u-sm-3 am-form-label">username</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="username" name="username" value="${dbConfig.username!}" placeholder="输入数据库的driver">
                                <small></small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="password" class="am-u-sm-3 am-form-label">password</label>
                            <div class="am-u-sm-9">
                                <input type="password" id="password" name="password" value="${dbConfig.password!}" placeholder="输入数据库的driver">
                                <small></small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="schema" class="am-u-sm-3 am-form-label">schema</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="schema" name="schema" value="${dbConfig.schema!}" placeholder="输入数据库的driver">
                                <small></small>
                            </div>
                        </div>-

                        <div class="am-form-group">
                            <div class="am-u-sm-9 am-u-sm-push-3">
                                <button type="submit" class="am-btn am-btn-primary">保存修改</button>
                            </div>
                        </div>
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