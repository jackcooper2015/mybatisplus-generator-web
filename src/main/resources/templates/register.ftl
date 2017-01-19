<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | Register</title>

    <link href="${base}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${base}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${base}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${base}/static/css/animate.css" rel="stylesheet">
    <link href="${base}/static/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">IN+</h1>

            </div>
            <h3>Register to IN+</h3>
            <p>Create account to see it in action.</p>
            <form class="m-t" role="form" action="/register" method="post">
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="Name" required="true">
                </div>
                <div class="form-group">
                    <input type="email" name="email" class="form-control" placeholder="Email" required="true">
                </div>
                <div class="form-group">
                    <input type="qq" name="qq" class="form-control" placeholder="QQ" required="true">
                </div>
                <div class="form-group">
                    <input type="tel" name="tel" class="form-control" placeholder="tel" required="true">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="Password" required="true">
                </div>
                <div class="form-group">
                        <div class="checkbox i-checks"><label> <input type="checkbox"><i></i> Agree the terms and policy </label></div>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">Register</button>

                <p class="text-muted text-center"><small>Already have an account?</small></p>
                <a class="btn btn-sm btn-white btn-block" href="login.html">Login</a>
            </form>
            <p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>
</body>

</html>
