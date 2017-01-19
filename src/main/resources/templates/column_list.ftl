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
    <form action="${base}/code" method="post" name="listForm" id="listForm">
        <input type="hidden" name="_method" id="_method" value=""/>
        <input type="hidden" name="url" id="url" value="<#if (dbConfig.url)??>${dbConfig.url}</#if>"/>
        <input type="hidden" name="driver" id="driver" value="<#if (dbConfig.driver)??>${dbConfig.driver}</#if>"/>
        <input type="hidden" name="username" id="tableName" value="<#if (dbConfig.username)??>${dbConfig.username}</#if>"/>
        <input type="hidden" name="password" id="password" value="<#if (dbConfig.password)??>${dbConfig.password}</#if>"/>
        <input type="hidden" name="schema" id="schema" value="<#if (dbConfig.schema)??>${dbConfig.schema}</#if>"/>

        <input type="hidden" name="tableName" id="tableName" value="<#if (tableInfo.tableName)??>${tableInfo.tableName}</#if>"/>

            <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">表格</strong> / <small>Table</small></div>
            </div>

            <hr>

            <div class="am-g">
                <div class="am-u-sm-12 am-u-md-6">
                    <div class="am-btn-toolbar">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" id="saveButton" class="am-btn am-btn-default"><span class="am-icon-save"></span> 保存</button>
                            <button type="button" id="generateButton" class="am-btn am-btn-default"><span class="am-icon-archive"></span> 生成</button>
                        </div>
                    </div>
                </div>

                <div class="am-u-sm-6 am-u-md-6">
                        模块名称：com.reapal.<input type="text" name="comments" id="comments" value="<#if (tableInfo.comments)??><#if (tableInfo.comments)?index_of("#") &gt; -1 >${(tableInfo.comments)?substring((tableInfo.comments)?index_of("#")+1)}<#else>${tableInfo.comments}111</#if></#if>">
                </div>
            </div>

            <div class="am-g">
                <div class="am-u-sm-12">
                    <form class="am-form">
                        <table class="am-table am-table-striped am-table-hover table-main">
                            <thead>
                            <tr>
                                <th class="table-author am-hide-sm-only">序号</th>
                                <th class="table-author am-hide-sm-only">字段名</th>
                                <th class="table-author am-hide-sm-only">类型</th>
                                <th class="table-author am-hide-sm-only">备注</th>
                                <th class="table-author am-hide-sm-only">是否显示</th>
                                <th class="table-author am-hide-sm-only">是否查询</th>
                                <th class="table-author am-hide-sm-only">编辑控件</th>
                                <th class="table-author am-hide-sm-only">校验</th>
                                <th class="table-author am-hide-sm-only">是否外键</th>
                                <th class="table-author am-hide-sm-only">代码转换名称</th>

                            </tr>
                            </thead>
                            <tbody>

                            <#list tableInfo.listColumn as column>
                            <input type="hidden" name="remarks" id="remarks_${column_index+1}" value="<#if (column.colName)??>${column.colName}</#if>">
                            <input type="hidden" name="itemType" id="itemType_${column_index+1}" value="<#if (column.sqlTypeName)??>${column.sqlTypeName}</#if>">
                            <input type="hidden" name="itemSize" id="itemSize_${column_index+1}" value="<#if (column.size)??>${column.size}</#if>">
                            <input type="hidden" name="itemDecimalDigits" id="itemDecimalDigits_${column_index+1}" value="<#if (column.decimalDigits)??>${column.decimalDigits}</#if>">
                            <tr>
                                <td>${column_index+1}</td>
                                <td><#if (column.colName)??>${column.colName}</#if></td>
                                <td><#if (column.colType)??>${column.colType}</#if></td>
                                <td><input type="text" name="remark" id="remark_${column_index+1}" value="<#if (column.comments)??><#if (column.comments)?index_of("#") &gt; 0 >${(column.comments)?substring(0,(column.comments)?index_of("#"))}<#else>${column.comments}</#if></#if>"></td>
                                <td><input type="checkbox" id="isShow_${column_index+1}" name="isShow" value="#OS" <#if (column.comments)??><#if (column.comments)?index_of("#OS") &gt; 0 >checked</#if></#if> /></td>
                                <td><input type="checkbox" id="isSearch_${column_index+1}" name="isSearch" value="#OC" <#if (column.comments)??><#if (column.comments)?index_of("#OC") &gt; 0 >checked</#if></#if> /></td>

                                <td><select id="editControl_${column_index+1}" name="editControl" >
                                    <option value="" <#if (column.comments)??><#if (column.comments)?index_of("#E") &lt; 0 >selected</#if></#if>>单行文本框</option>

                                    <option value="#EPS" <#if (column.comments)??><#if (column.comments)?index_of("#EPS") &gt; 0 >selected</#if></#if>>参数下拉框</option>
                                    <option value="#EPC" <#if (column.comments)??><#if (column.comments)?index_of("#EPC") &gt; 0 >selected</#if></#if>>参数复选框</option>
                                    <option value="#EPR" <#if (column.comments)??><#if (column.comments)?index_of("#EPR") &gt; 0 >selected</#if></#if>>参数单选框</option>

                                    <option value="#ENS" <#if (column.comments)??><#if (column.comments)?index_of("#ENS") &gt; 0 >selected</#if></#if>>普通下拉框</option>
                                    <option value="#ENC" <#if (column.comments)??><#if (column.comments)?index_of("#ENC") &gt; 0 >selected</#if></#if>>普通复选框</option>
                                    <option value="#ENR" <#if (column.comments)??><#if (column.comments)?index_of("#ENR") &gt; 0 >selected</#if></#if>>普通单选框</option>

                                    <option value="#EDS0&" <#if (column.comments)??><#if (column.comments)?index_of("#EDS") &gt; 0 >selected</#if></#if>>部门下拉框</option>
                                    <option value="#EAS0&" <#if (column.comments)??><#if (column.comments)?index_of("#EAS") &gt; 0 >selected</#if></#if>>区域下拉框</option>
                                    <option value="#EOR" <#if (column.comments)??><#if (column.comments)?index_of("#EOR") &gt; 0 >selected</#if></#if>>开关单选框</option>
                                    <option value="#ERT" <#if (column.comments)??><#if (column.comments)?index_of("#ERT") &gt; 0 >selected</#if></#if>>富文本编辑框</option>
                                </select>
                                    <input type="text" name="parameter" class="w60" id="parameter_${column_index+1}" value="<#if (column.comments)??><#if ((column.comments)?index_of("#EP") &gt; 0) || ((column.comments)?index_of("#EN") &gt; 0) >${(column.comments)?substring((column.comments)?index_of("#E")+5,(column.comments)?index_of("）"))}</#if></#if>">
                                </td>
                                <td>
                                    <label><input type="checkbox" id="valid_vnn_${column_index+1}" name="valid_vnn_${column_index+1}" value="#VNN" <#if (column.comments)??><#if (column.comments)?index_of("#VNN") &gt; 0 >checked</#if></#if>>非空</label>
                                    <label><input type="radio" id="valid_vi_${column_index+1}" name="valid_${column_index+1}" value="#VI" <#if (column.comments)??><#if (column.comments)?index_of("#VI") &gt; 0 >checked</#if></#if>>整数</label>
                                    <label><input type="radio" id="valid_vd_${column_index+1}" name="valid_${column_index+1}" value="#VD" <#if (column.comments)??><#if (column.comments)?index_of("#VD") &gt; 0 >checked</#if></#if>>实数</label>
                                    <label><input type="radio" id="valid_ve_${column_index+1}" name="valid_${column_index+1}" value="#VE" <#if (column.comments)??><#if (column.comments)?index_of("#VE") &gt; 0 >checked</#if></#if>>Email</label>
                                    <label><input type="radio" id="valid_vm_${column_index+1}" name="valid_${column_index+1}" value="#VM" <#if (column.comments)??><#if (column.comments)?index_of("#VM") &gt; 0 >checked</#if></#if>>手机号</label>
                                    <label><input type="radio" id="valid_vt_${column_index+1}" name="valid_${column_index+1}" value="#VT" <#if (column.comments)??><#if (column.comments)?index_of("#VT") &gt; 0 >checked</#if></#if>>固话号</label>
                                    <label><input type="radio" id="valid_not_${column_index+1}" name="valid_${column_index+1}" value="">无校验</label>
                                </td>
                                <td><input type="checkbox" id="isFk_${column_index+1}" name="isFk" value="#LFK" <#if (column.comments)??><#if (column.comments)?index_of("#LFK") &gt; 0 >checked</#if></#if> /></td>
                                <td><select id="logic_${column_index+1}" name="logic" >
                                    <option value="" <#if (column.comments)??><#if (column.comments)?index_of("#L") &lt; 0 >selected</#if></#if>>无</option>
                                    <option value="#LCP" <#if (column.comments)??><#if (column.comments)?index_of("#LCP") &gt; 0 >selected</#if></#if>>参数</option>
                                    <option value="#LCD" <#if (column.comments)??><#if (column.comments)?index_of("#LCD") &gt; 0 >selected</#if></#if>>部门</option>
                                    <option value="#LCA" <#if (column.comments)??><#if (column.comments)?index_of("#LCA") &gt; 0 >selected</#if></#if>>区域</option>
                                </select>
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
    </form>
        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
        </footer>

    </div>
    <!-- content end -->
</div>



<script type="text/javascript">
    <!--
    $(function () {

        //提交，最终验证。
        $('#send').click(function(){
            $("form :input").trigger('blur');
            var numError = $('form .onError').length;

            if(numError){
                return false;
            }
            else{
                $('#listForm').submit();
            }
        });


        //保存
        $("#saveButton").click(function(){
            var $this = $(this);
            var i="1";

            for(var j=0;j<${tableInfo.listColumn?size};j++){
                i = j+1;
                var note = $("#remarks_"+i).val();
                note += "@"+$("#itemType_"+i).val();
                note += "@"+$("#remark_"+i).val();
                if($("#isShow_"+i).is(":checked")){
                    note += $("#isShow_"+i).val();
                }
                if($("#isSearch_"+i).is(":checked")){
                    note += $("#isSearch_"+i).val();
                }
                if($("#isFk_"+i).is(":checked")){
                    note += $("#isFk_"+i).val();
                }

                if($("#valid_vnn_"+i).is(":checked")){
                    note += $("#valid_vnn_"+i).val();
                }
                if($("#valid_vi_"+i).is(":checked")){
                    note += $("#valid_vi_"+i).val();
                }
                if($("#valid_vd_"+i).is(":checked")){
                    note += $("#valid_vd_"+i).val();
                }
                if($("#valid_ve_"+i).is(":checked")){
                    note += $("#valid_ve_"+i).val();
                }
                if($("#valid_vm_"+i).is(":checked")){
                    note += $("#valid_vm_"+i).val();
                }
                if($("#valid_vt_"+i).is(":checked")){
                    note += $("#valid_vt_"+i).val();
                }
                note += $("#editControl_"+i+" option:selected").val();
                if((note.indexOf("#EP")>=0) || (note.indexOf("#EN")>=0)){
                    if($("#parameter_"+i).val() != "")
                        note += "（"+$("#parameter_"+i).val()+"）";
                    //note += "&";
                }
                note += $("#logic_"+i+" option:selected").val();

                $("#remarks_"+i).val(note);
                //alert(note)
            }
            var strAct = "${base}/code";
            $('#listForm').attr("action",strAct);
            $('#listForm').submit();
        });

        //生成
        $("#generateButton").click(function(){
            var $this = $(this);
            //alert("ssss");
            var strAct = "${base}/code/generate";
            $('#listForm').attr("action",strAct);

            $('#listForm').submit();
        });



    });

    //-->
</script>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
<script src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="http://amazeui.org/js/app.js"></script>
<div class="amz-toolbar" id="amz-toolbar" style="right: 361px;"><a href="#top" title="回到顶部" class="am-icon-btn am-icon-arrow-up am-active" id="amz-go-top"></a> <a href="/getting-started/faq" title="常见问题" class="am-icon-faq am-icon-btn am-icon-question-circle"></a></div>
</body>
</html>