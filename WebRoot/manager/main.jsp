<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	int port = request.getServerPort();
	String portStr = "";
	if (port != 80)
		portStr = ":" + port;
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + portStr	+ request.getContextPath()+"/";
%>
<%@ taglib prefix="decorator" uri="/WEB-INF/tld/sitemesh-decorator.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>TinyCms测试环境</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/site.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resource/css/ext-all.css" />
	<script type="text/javascript" src="<%=basePath%>resource/js/ext-base.js"></script>
	<script type="text/javascript" src="<%=basePath%>resource/js/ext-all-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>resource/js/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>resource/js/log.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<script type="text/javascript">
			window.basePath = '<%=basePath%>';
			Ext.Ajax.timeout = "90000";
			Ext.BLANK_IMAGE_URL = "<%=basePath%>resource/images/default/s.gif";
	
			if (!Ext.grid.GridView.prototype.templates) {
				Ext.grid.GridView.prototype.templates = {};
			}
			Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
					'<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',
					'<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
					'</td>');
			function dochange(theme) {
				Ext.util.CSS.swapStyleSheet("theme", "./resources/css/xtheme-" + theme
						+ ".css");
			}
			Ext.onReady( function() {
				if (Ext.get("loading") != null)
					Ext.get("loading").dom.style.display = 'none';
				var exttheme = window.top.document.getElementById("exttheme");
				if (exttheme != null) {
					Ext.util.CSS.swapStyleSheet("theme", "./resources/css/xtheme-"
							+ exttheme.value + ".css");
				}
				Ext.Ajax.on('requestexception', function() {
					Ext.Msg.alert("～错误～", "数据获取失败，请核实查询条件后重试或联系维护人员！");
				}, this);
				Ext.Ajax.on('beforerequest', function() {
					Ext.Ajax.extraParams = {
						'ajax_extraParams' : 'true'
					};
				}, this);
				Ext.Ajax.on('requestcomplete', function(conn, response) {
					var tmp = response.responseText;
					if (tmp != null && tmp != ''
							&& tmp.indexOf("ajax_session_invalid") != -1) {
						if (Ext.decode(tmp).ajax_session_invalid) {
							alert("您未操作系统时间过长， 请重新登陆!")
							window.top.location = basePath + "/login.jsp";
						}
					}
				}, this);
			});
	</script>
		<decorator:head />
</head>
<body> 
	

	<div id="header"><h1>Ext Layout Browser</h1></div>
	<div id="loading">
			<div>
				<img src="<%=basePath%>resource/images/loading.gif">
				<span style="margin-left: 8px; color: #225588;"> TinyCMS 内容管理系统</span>
				<br />
				<span style="margin-left: 8px; color: #225588;" id="loading-msg">正在加载网页相关信息...</span>
			</div>
		</div>
<decorator:body />
</body>
</html>