<#include "header.ftl">

<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
    <form action="${base}/columnsave" method="post" name="listForm" id="listForm" data-am-validator>
        <input type="hidden" name="_method" id="_method" value=""/>
        <input type="hidden" name="url" id="url" value="<#if (dbConfig.url)??>${dbConfig.url}</#if>"/>
        <input type="hidden" name="driver" id="driver" value="<#if (dbConfig.driver)??>${dbConfig.driver}</#if>"/>
        <input type="hidden" name="username" id="tableName" value="<#if (dbConfig.username)??>${dbConfig.username}</#if>"/>
        <input type="hidden" name="password" id="password" value="<#if (dbConfig.password)??>${dbConfig.password}</#if>"/>
        <input type="hidden" name="schema" id="schema" value="<#if (dbConfig.schema)??>${dbConfig.schema}</#if>"/>

        <input type="hidden" name="tableName" id="tableName" value="<#if (tableInfo.tableName)??>${tableInfo.tableName}</#if>"/>

            <div class="admin-content-body">
            <div class="am-cf am-padding am-padding-bottom-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">表详情</strong> / <small>Table</small></div>
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
                        模块名称：com.reapal.<input type="text" required name="comments" id="comments" value="<#if (tableInfo.comments)??><#if (tableInfo.comments)?index_of("#") &gt; -1 >${(tableInfo.comments)?substring((tableInfo.comments)?index_of("#")+1)}<#else>${tableInfo.comments}test</#if></#if>">
                </div>
            </div>

            <div class="am-g">
                <div class="am-u-sm-12">
                        <table class="am-table am-table-striped am-table-hover table-main">
                            <thead>
                            <tr>
                                <th class="table-author am-hide-sm-only">序号</th>
                                <th class="table-author am-hide-sm-only">字段名</th>
                                <th class="table-author am-hide-sm-only">类型</th>
                                <th class="table-author am-hide-sm-only">备注</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list tableInfo.listColumn as column>
                            <input type="hidden" name="remarks" id="remarks_${column_index+1}" value="<#if (column.colName)??>${column.colName}</#if>">
                            <input type="hidden" name="itemType" id="itemType_${column_index+1}" value="<#if (column.colType)??>${column.colType}</#if>">
                            <tr>
                                <td>${column_index+1}</td>
                                <td><#if (column.colName)??>${column.colName}</#if></td>
                                <td><#if (column.colType)??>${column.colType}</#if></td>
                                <td><input type="text" required name="remark" id="remark_${column_index+1}" value="<#if (column.comments)??><#if (column.comments)?index_of("#") &gt; 0 >${(column.comments)?substring(0,(column.comments)?index_of("#"))}<#else>${column.comments}</#if></#if>"></td>
                            </tr>
                            </#list>

                            
                            </tbody>
                        </table>
                        <hr>
                        <p>注：请完善备注信息，请确保表有主键ID</p>
                </div>

            </div>
        </div>
    </form>
        <footer class="admin-content-footer">
            <hr>
            <p class="am-padding-horizontal">© jack-cooper  &nbsp;&nbsp;&nbsp; 当前时间：${.now?string("yyyy-MM-dd HH:mm:ss")}  </p>
        </footer>

    </div>
    <!-- content end -->
</div>

<#include "footer.ftl">

<script type="text/javascript">
    $(function () {

        //保存
        $("#saveButton").click(function(){
            var $this = $(this);
            var i="1";

            for(var j=0;j<${tableInfo.listColumn?size};j++){
                i = j+1;
                var note = $("#remarks_"+i).val();
                note += "@"+$("#itemType_"+i).val();
                note += "@"+$("#remark_"+i).val();
                $("#remarks_"+i).val(note);
                //alert(note)
            }
            $('#listForm').attr("action","${base}/columnsave");
            $('#listForm').submit();
        });

        //生成
        $("#generateButton").click(function(){
            var $this = $(this);
            //alert("ssss");
            var strAct = "${base}/generate";
            $('#listForm').attr("action",strAct);

            $('#listForm').submit();
        });



    });
</script>
