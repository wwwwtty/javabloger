<?php if (!defined('THINK_PATH')) exit();?><?php $tag['name'] = 'video';$tag['cid'] = ''.$_GET['cid'].'';$tag['limit'] = ''.$_GET['limit'].'';$tag['order'] = 'stars desc,hits desc'; $__LIST__ = get_tag_gxcms($tag); if(is_array($__LIST__)): $i = 0;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$video): ++$i;$mod = ($i % 2 );?><li <?php if(($i)  ==  "7"): ?>class="no"<?php endif; ?>>
<div class="pic"><a href="<?php echo ($video["readurl"]); ?>" target="_blank"><img src="<?php echo ($video["picurl"]); ?>" title="<?php echo ($video["title"]); ?>" onerror="this.src='<?php echo ($webpath); ?>views/images/nophoto.jpg'" border="0" height="140" width="98"></a></div>
<div><a href="<?php echo ($video["readurl"]); ?>" title="<?php echo ($video["title"]); ?>" target="_blank"><?php echo (get_replace_html($video["title"],0,8)); ?></a></div>
</li><?php endforeach; endif; else: echo "" ;endif;unset($__LIST__);unset($tag);?>