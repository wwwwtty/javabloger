<?php if (!defined('THINK_PATH')) exit();?><html>
<head>
<title>站点安装-许可协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='./views/css/install.css'>
</head>
<body >
<form id=gxform action="<?php echo C("cms_admin");?>?s=Admin/Install/Third" method="post">
<input name="setup" type="hidden" value="<?php echo $_POST["setup"];?>">
<div id='installbox'>
<div class='msgtitle'><?php echo C("cms_name");?><?php echo C("cms_var");?> 安装向导</div>
<table width="573" height="23" border="0" cellpadding="0" cellspacing="0" style="text-align:center;font-weight:bold;font-size:12pt;background:url(views/images/install/set02_top_nav.gif); margin:10px 0 0 10px;">
  <tr>
    <td style="color:#666; text-align:center"><span style="display:block; float:left; width:20%; font-size:12px;">1. 许可协议</span><span style="display:block; float:left; width:25%; font-size:12px; color: #FFF;">2. 环境检测</span><span style="display:block;float:left;width:25%;font-size:12px;">3. 数据库设置</span><span style="display:block;float:left;width:25%;font-size:12px;">4. 安装完成</span></td>
  </tr>
</table>
<h3>服务器环境检测：</h3>
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="tableoutline" style="text-align:left;font-size:12px; color:#666;">
 <tr>
 <td width="44%" bgcolor="#f4faff"><strong>需开启的变量或函数</strong></td>
 <td width="20%" bgcolor="#f4faff"><strong>要求</strong></td>
 <td width="36%" bgcolor="#f4faff" ><strong>实际状态及建议</strong></td>
 </tr>         
 <tr bgcolor="#FFFFFF">
 <td >allow_url_fopen</td>
 <td >On</td>
 <td ><?php
    if(ini_get('allow_url_fopen')){
        echo '<font color=green>[√]On</font>';
    }else{
        echo '<font color=red>[×]Off</font>';
    }?></td>
 </tr>
 <tr bgcolor="#F4faff">
 <td >safe_mode</td>
 <td >Off</td>
 <td ><?php
    if(ini_get('safe_mode')){
        echo '<font color=red>[×]On</font>';
    }else{
        echo '<font color=green>[√]Off</font>';
    }?></td>
 </tr>
 <tr bgcolor="#FFFFFF">
 <td >GD 支持</td>
 <td >On<span style="color: #999999;">(>=2.0.34)</span></td>
 <td > <font color=green>[√]</font><?php if(function_exists('gd_info')){
        $gd = gd_info();
        echo @$gd['GD Version'];
    }else{
        echo "不支持";
    } ?></td>
 </tr>
  <tr bgcolor="#F4faff">
 <td >MySQL 支持</td>
 <td >On</td>
 <td ><?php
    if(function_exists("mysql_close")){
        echo '<font color=green>[√]On</font>';
    }else{
        echo '<font color=red>[×]Off</font>';
    }?></td>
 </tr>
</table>
<h3>目录权限检测：</h3>
<table width="98%" border="0" cellpadding="5" cellspacing="1" class="tableoutline" align="center" style="font-size:12px; color:#666">
<tr bgcolor="#f4faff">
     <td width="250"><strong>目录名</strong></td>
     <td width="100"><strong>读取权限</strong></td>
     <td><strong>写入权限</strong></td><?php $dirs = array('/','/temp/*','/uploads');?>   
</tr>
<?php 
    foreach($dirs as $d){
?>
  <tr><td><?php echo $d; ?></td>
  <?php
    $fulld = './'.str_replace('/*','',$d);
    $rsta = (is_readable($fulld) ? '<font color=green>[√]读</font>' : '<font color=red>[×]读</font>');
    $wsta = (testwrite($fulld) ? '<font color=green>[√]写</font>' : '<font color=red>[×]写</font>');
    echo "<td>$rsta</td><td>$wsta</td>";
?> </tr>
<?php
    };
?>
<tr bgcolor="#FFFFFF" class="tb_head">
    <td height="70" colspan="3" align="center">
    <input style="width:120px; height:30px;" type="button" class="btn"  value="<< 上一步"  onClick="history.back();"/>
    <input style="width:120px; height:30px;" type="submit" name="submit" value="下一步 >>" class="btn">
    </td>
</tr>             
</table>
<div id='msgbottom'>Powered By <?php echo C("cms_name");?> <?php echo C("cms_var");?></div>
</div>
</form>
</body>
</html>