<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign base = request.contextPath/> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>列表</title>

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

						<th>数据库</th>
	
					<th>类型</th>

						<th>操作</th>

					</tr>
				</thead>
				
				<tbody>

               <tr>
                   <td>15</td>
                   <td>tariff210(10.168.16.210:1521/devps)</td>
                   <td>Oracle</td>
                   <td>
                       <#--<a href="${base}/code?dbType=mysql&url=jdbc:mysql://10.168.16.116:3306/test&driverName=com.mysql.jdbc.Driver&username=root&password=devApp2013">选定</a>-->
                       <a href="${base}/code?url=jdbc:mysql://10.168.16.116:3306/test&driver=com.mysql.jdbc.Driver&username=root&password=devApp2013&schema=test">选定</a>
                   </td>
               </tr>

               </tbody>
			</table>
		</div>
	</form>
</body>
</html>
