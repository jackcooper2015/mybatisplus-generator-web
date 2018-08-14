
<#include "header.ftl">
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
                                <#--<th class="table-check"><input type="checkbox"></th>-->
                                <th class="table-id">ID</th>
                                <th class="table-id">名称</th>
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
                                <#--<td><input type="checkbox"></td>-->
                                <td>${dbConfig_index+1}</td>
                                <td>${dbConfig.dbName!}</td>
                                <td><a href="#"><#if (dbConfig.url)??>${dbConfig.url}</#if></a></td>
                                <td>${dbConfig.driver!}</td>
                                <td>
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a class="am-btn am-btn-default am-btn-xs am-hide-sm-only" href="${base}/edit?dbName=${dbConfig.dbName}&url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}"><span class="am-icon-pencil-square-o"></span>编辑</a>
                                            <a class="am-btn am-btn-default am-btn-xs am-hide-sm-only" href="${base}/delete?dbName=${dbConfig.dbName}&url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}"><span class="am-icon-trash-o"></span>删除</a>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a class="am-btn-sm am-btn-danger am-radius" href="${base}/tablelist?dbName=${dbConfig.dbName}&url=${dbConfig.url!}&driver=${dbConfig.driver!}&username=${dbConfig.username!}&password=${dbConfig.password!}&schema=${dbConfig.schema!}">选定</a>
                                </td>
                            </tr>
                            </#list>
                            </#if>


                            </tbody>
                        </table>
                        <hr>
                        <p>注：请不要随意删除</p>
                        <p>说明：请确保该系统所在机器能访问你的数据库</p>
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

<#include "footer.ftl">