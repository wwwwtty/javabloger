<?php
/*
Template Name: Links
*/
?>
<?php get_header(); ?>
<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<div class="links">
					<?php
						if(function_exists(’wp_dtree_get_links’)){
						wp_dtree_get_links();
						}else{
						my_list_bookmarks('title_li=&categorize=1&category=&orderby=rand&show_images=1');
						}
					?>
				<div class="clearfix"></div>
			</div>
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