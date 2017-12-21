<#include "header.ftl">
<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">数据库</strong> :<small>${dbConfig.dbName}</small></div>
            </div>

            <hr>

            <div class="am-g">

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
                                    <a href="${base}/column/<#if (table.tableName)??>${table.tableName}</#if>?url=<#if (dbConfig.url)??>${dbConfig.url}</#if>&driver=<#if (dbConfig.driver)??>${dbConfig.driver}</#if>&username=<#if (dbConfig.username)??>${dbConfig.username}</#if>&password=<#if (dbConfig.password)??>${dbConfig.password}</#if>&schema=<#if (dbConfig.schema)??>${dbConfig.schema}</#if>">修改</a>
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
            <p class="am-padding-horizontal">© jack-cooper  &nbsp;&nbsp;&nbsp; 当前时间：${.now?string("yyyy-MM-dd HH:mm:ss")}  </p>
        </footer>

    </div>
    <!-- content end -->
</div>
<#include "footer.ftl">