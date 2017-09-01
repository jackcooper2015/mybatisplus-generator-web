<#include "header.ftl">

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
                    <form class="am-form am-form-horizontal" id="tableForm" action="${base}/save" method="post" data-am-validator>

                        <div class="am-form-group">
                            <label for="dbName" class="am-u-sm-3 am-form-label">名称（唯一）</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="dbName" required name="dbName" value="${dbConfig.dbName!}" placeholder="输入链接名">
                                <small>如：210Base</small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="driver" class="am-u-sm-3 am-form-label">数据库类型</label>
                            <div class="am-u-sm-9">
                                <select name="driver" id="driver" required data-am-selected="{btnSize: 'sm'}" >
                                    <option></option>
                                    <option <#if (dbConfig.driver?? && dbConfig.driver=='com.mysql.jdbc.Driver')>selected</#if> value="com.mysql.jdbc.Driver">mysql</option>
                                    <option <#if (dbConfig.driver?? && dbConfig.driver=='oracle.jdbc.driver.OracleDriver')>selected</#if> value="oracle.jdbc.driver.OracleDriver">oracle</option>
                                </select>
                            </div>
                        </div>


                        <div class="am-form-group">
                            <label for="url" class="am-u-sm-3 am-form-label">URL</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="url" required name="url" value="${dbConfig.url!}" placeholder="输入数据库的url">
                                <small>如:mysql(jdbc:mysql://10.168.16.116:3306/test)   oracle(jdbc:oracle:thin:@10.168.16.210:1521:devps)</small>
                            </div>
                        </div>


                        <div class="am-form-group">
                            <label for="username" class="am-u-sm-3 am-form-label">用户名</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="username" required name="username" value="${dbConfig.username!}" placeholder="输入数据库的用户名">
                                <small></small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="password" class="am-u-sm-3 am-form-label">密码</label>
                            <div class="am-u-sm-9">
                                <input type="password" id="password" required name="password" value="${dbConfig.password!}" placeholder="输入数据库的密码">
                                <small></small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="schema" class="am-u-sm-3 am-form-label">schema</label>
                            <div class="am-u-sm-9">
                                <input type="text" id="schema" required name="schema" value="${dbConfig.schema!}" placeholder="输入数据库的schema">
                                <small></small>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <div class="am-u-sm-9 am-u-sm-push-3">
                                <button type="button" id="test-db" class="am-btn am-btn-default am-radius">测试</button>
                                <button type="submit" class="am-btn am-btn-primary">保存</button>
                            </div>
                        </div>

                        <div class="am-form-group">
                            <label for="result" class="am-u-sm-3 am-form-label">测试结果</label>
                            <div class="am-u-sm-9">
                                <textarea class="" id="result" rows="5" id="doc-ta-1"></textarea>
                            </div>
                        </div>


                    </form>
                </div>
            </div>
        </div>

        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-horizontal">© jack-cooper  &nbsp;&nbsp;&nbsp; 当前时间：${.now?string("yyyy-MM-dd HH:mm:ss")}  </p>
        </footer>

    </div>

    <script type="application/javascript">
        $(function () {
            $("#test-db").click(function () {
                $.ajax({
                    type: "POST",
                    url: "${base}/test",
                    data: $("#tableForm").serialize(),
                    async: false,
                    dataType:"text",
                    success: function(data){
                        if(data == null || data == ''){
                            $("#result").text("数据库连接成功，请保存");
                        }else{
                            $("#result").text("数据库连接失败:"+data);
                        }
                    }
                });
            });
        });
    </script>
    <!-- content end -->
</div>
<#include "footer.ftl">