<?php
/*
Template Name: One column, no sidebar
*/
?>
<?php get_header(); ?>
<div id="fullcontent">
	<div id="post_entry">
		<?php if (have_posts()) : ?>
			<?php while (have_posts()) : the_post(); ?>
				<div id="post_meta" id="post-<?php the_ID(); ?>">
					<div class="post_info">
						<h1><?php the_title(); ?></h1>
					</div><!-- post info end -->
					<div class="post_content">
						<?php the_content(); ?>
						<?php wp_link_pages('before=<div id="page-links">&after=</div>'); ?>
					</div><!-- post content end -->
					<div class="clearfix"></div>
				</div><!-- post meta <?php the_ID(); ?> end -->
			<?php endwhile; ?>
			<?php comments_template( '', true ); ?>
		<?php else : ?>
			<p class="center">抱歉!</p>
			<p class="center">无法搜索到与之相匹配的信息。</p>
		<?php endif; ?>
	</div><!-- post entry end -->
	<?php include (TEMPLATEPATH . '/includes/paginate.php'); ?>
</div><!-- content end -->
<script type="text/javascript">
$(".blog_loading").animate({width:"65%"})
</script>
<?php get_footer(); ?>