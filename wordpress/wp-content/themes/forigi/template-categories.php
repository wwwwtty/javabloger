<?php
/*
Template Name: Categories
*/
?>
<?php get_header(); ?>


<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<ul id="temp_categories">
				<h2>分类目录</h2>
				<?php wp_list_categories('orderby=id&title_li=&show_count=1&feed_image=/wp-content/themes/forigi/images/rss.png'); ?>
					<div class="clearfix"></div>
			</ul><!-- POST tags END -->
		<?php endwhile; ?>
		<?php comments_template( '', true ); ?>
	<?php else : ?>
		<p class="center">抱歉!</p>
		<p class="center">无法搜索到与之相匹配的信息。</p>
	<?php endif; ?>
</div><!-- fullcontent END -->

<script type="text/javascript">
$(".blog_loading").animate({width:"65%"})
</script>
<?php get_footer(); ?>

