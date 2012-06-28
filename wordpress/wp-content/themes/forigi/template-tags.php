<?php
/*
Template Name: tags
*/
?>
<?php get_header(); ?>
<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<div class="page_tags">
				<h2>所有标签</h2>
					<?php wp_tag_cloud('number=0&orderby=count&order=DESC&smallest=18&largest=12&unit=px'); ?>
			</div><!-- POST tags END -->
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