<?php
/*
Template Name: page
*/
?>

<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut cf">
		<div class="boxLeft">
			<div class="boxLeftIn">
				<div class="pageArticle">
					<!-- page page -->
					<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
					<!-- page Title -->
					<h3 class="title pt10 pb10 pl5"><?php the_title(); ?></h3>		
					<!-- page Data -->
					<p class="h24 lh24 pt5 pb5 g9 ml10 articleInfo">
								<span class="mr20">发布时间：<?php the_time('Y-m-d') ?></span>
								<span class="mr20"><?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></span>
								<?php edit_post_link('编辑', ' &nbsp;', ''); ?>
					</p>
					<div class="p10 pageContent">
						<!-- page Content -->
						<?php the_content(); ?>						
						<?php endwhile; ?>
						<?php else : ?>
						<h3 class="pt10 pb10 g3">非常抱歉，该分类下没有文章！</h3>
						<p class="g6">非常抱歉，该分类下没有文章！请移步其他分类下进行查看，谢谢！</p>
						<?php endif; ?>
					</div>
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