<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>缓存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='./views/css/admin_style.css'>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="./views/js/jquery.js"></script>
</head>
<body>
<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
  <form action="?s=Admin/Cache/Delhtml" method="post" name="gxform">
    <tr class="table_title">
      <td>更新静态网页缓存</td>
    </tr>     
     <tr class="tr">
      <td width="48%"><p><input name="other" type="submit" class="bginput" value="清空所有静态缓存"> 清空所有静态缓存,  temp/Html/*</p></td>
    </tr>
    <tr class="ji">
      <td ><input name="index" type="submit" class="bginput" value="更新网站首页缓存"> 清空首页HTML缓存,  temp/Html/index</td>
    </tr>
    <tr class="tr">
      <td><input name="vodshow" type="submit" class="bginput" value="更新视频列表缓存"> 清除视频列表页HTML缓存, temp/Html/Video_lists</td>
    </tr>
    <tr class="ji">
      <td><input name="vodread" type="submit" class="bginput" value="更新视频内容缓存"> 清除视频内容页HTML缓存, temp/Html/Video_detail</td>
    </tr>
    <tr class="tr">
      <td><input name="vodplay" type="submit" class="bginput" value="更新视频播放缓存"> 清除视频播放页HTML缓存, temp/Html/Video_play</td>
    </tr>       
     <tr class="ji">
      <td><input name="newsshow" type="submit" class="bginput" value="更新新闻列表缓存"> 清除文章列表页HTML缓存, temp/Html/Info_lists</td>
    </tr>
    <tr class="tr">
      <td><input name="newsread" type="submit" class="bginput" value="更新新闻内容缓存"> 清除文章内容页HTML缓存, temp/Html/Info_detail</td>
    </tr>
    <tr class="tr">
      <td><input name="myshow" type="submit" class="bginput" value="更新自定义模板缓存"> 清除自定义模板页HTML缓存, temp/Html/My_show</td>
    </tr>                              
  </form>
</table>   
<style>
#footer, #footer a:link, #footer a:visited {
	clear:both;
	color:#0072e3;
	font:12px/1.5 Arial;
	margin-top:10px;
	text-align:center;
	white-space:nowrap;
}
</style>
<div id="footer">程序版本：<?php echo C("cms_var");?>&nbsp;&nbsp;&nbsp;&nbsp;Copyright © 2010-2011 All rights reserved</div>
</body>
</html>