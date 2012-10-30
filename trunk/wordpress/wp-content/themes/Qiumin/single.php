<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut cf">
		<div class="boxLeft">
			<div class="boxLeftIn ovh">
				<div class="singleArticle">
					<?php if (have_posts()) : the_post(); update_post_caches($posts); ?>
						<h3 class="title pt10 pb10 pl5"><?php the_title(); ?></h3>		
						<p class="h24 lh24 pt5 pb5 g9 ml10 articleInfo">
							<span class="mr10"><?php _e("Author:"); ?><?php the_author(); ?></span>|
							<span class="ml10 mr10"><?php _e("Categories:"); ?><?php the_category('、') ?></span>|
							<span class="ml10 mr10">发布时间：<?php the_time('Y-m-d') ?></span>|
							<span class="ml10 mr10"><?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></span>|
							<?php edit_post_link('编辑', ' &nbsp;', ''); ?>
						</p>
						<p class="h24 lh24 pt5 pb5 g9 ml10 articleInfo mb10">文档标签：<?php the_tags('', ', ', ''); ?></p>
						<div class="content pt10 pr10 pl10 ovh">
							<?php the_content(); ?>
							<div id="wumiiDisplayDiv"></div><!-- for wumi -->
						</div>
					<?php else : ?>
						<h3 class="title pt10 pb10 g3">非常抱歉，该文章没有找到！</h3>
						<p class="g6">非常抱歉，该文章可能已经被博主删除或移到别的地方去了，你或者可以继续查看博主的其他文章！</p>
					<?php endif; ?>

					<div class="p10"><?php comments_template(); ?></div>
				</div>
			</div>		
		</div>		
		<div class="Boxslide">
			<div class="slideIn">
				<?php get_sidebar(); ?>
			</div>
		</div>
	</div>
</div>

<?php get_footer(); ?>
