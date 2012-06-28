<!-- 版权信息 -->
<div id="post_box">
	<div class="author_text">
		该日志由 <?php the_author() ?> 于<?php the_time('Y年m月d日') ?>发表在<?php the_category(', ') ?>分类下，
		<?php if (('open' == $post-> comment_status) && ('open' == $post->ping_status)) {?>
		你可以<a href="#respond">发表评论</a>，并在保留<a href="<?php the_permalink() ?>" rel="bookmark">原文地址</a>及作者的情况下<a href="<?php trackback_url(); ?>" rel="trackback">引用</a>到你的网站或博客。 | <a href="#" onclick="copy_code('<?php the_permalink() ?>'); return false;"> +复制链接</a>
		<?php } elseif (('open' == $post-> comment_status) && !('open' == $post->ping_status)) { ?>
		通告目前不可用，你可以至底部留下评论。
		<?php } ?><br/>
		转载请注明: <a href="<?php the_permalink() ?>" rel="bookmark" title="本文固定链接 <?php the_permalink() ?>"><?php the_title(); ?></a><br/>
		关键字: <?php the_tags('', ', ', ''); ?>
	</div>
	<div class="clearfix"></div>
</div>

<!-- 上下篇 -->
<div id="post_box">
	<?php previous_post_link('【上一篇】%link') ?><br/>
	<?php next_post_link('【下一篇】%link') ?>
	<div class="clearfix"></div>
</div>

<!-- related content -->
<div id="post_box">
	<h3>您可能感兴趣的文章:</h3>
	<ul>
		<?php related_posts() ?>
	</ul>
	<div class="clearfix"></div>
</div>