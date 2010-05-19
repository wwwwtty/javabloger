<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8">
		<title>Untitled Document</title>
		<script type="text/javascript" src="log4j.js"> </script>
		<script type="text/javascript" src="TreeLayer.js"> </script>
		<script type="text/javascript" src="TreeComboBox.js"> </script>
		<script type="text/javascript" src="comboxTree.js"> </script>
		<script type="text/javascript" src="test.js"> </script>
	</head>
	<body>
    <form id="form1" runat="server">
    <div>
        <div id="comboxtree">
        </div>

    </div>
    </form>
	</body>
</html>
