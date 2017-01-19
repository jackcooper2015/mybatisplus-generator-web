<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign base = request.contextPath/> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>表列表</title>
	<link href="${base}/common/css/base.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/common/css/common.css" rel="stylesheet" type="text/css"/>
	<script src="${base}/common/js/jquery.js" type="text/javascript"></script>
	<script src="${base}/common/js/common.js" type="text/javascript"></script>
	<script src="${base}/common/js/list.js" type="text/javascript"></script>
</head>
<body>
	<form action="${base}/admin/code" method="get" name="listForm" id="listForm">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton fl">
					<span class="iconSpan refreshIcon">&nbsp;</span>刷新
				</a>
			</div>
		</div>
		
		<div class="body-box">
			<input type="hidden" name="_method" id="_method" value=""/>
			<table id="listTable" class="list">
				<thead>
					<tr>
						<th>序号</th>
						<th>表名</th>
						<th>注解</th>
						<th>操作</th>
					</tr>
				</thead>
				
				<tbody>
		    	<#list tableList as table > 
				<tr>
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
		</div>
	</form>
</body>
</html>
