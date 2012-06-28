<?php get_header(); ?>
<div id="content">
	<div id="post_entry">
		<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
		<div id="post_meta" id="post-<?php the_ID(); ?>">
			<div class="post_info">
				<h1><?php the_title(); ?></h1>
			</div><!-- POST INFO END -->
			<div class="post_content">
				<?php the_content(); ?>
				<?php wp_link_pages('before=<div id="page-links">&after=</div>'); ?>
			</div><!-- POST CONTENT END -->
		</div><!-- POST META <?php the_ID(); ?> END -->
		<?php endwhile; ?>
		<?php comments_template( '', true ); ?>
		<?php else : ?>
			<p class="center">抱歉!</p>
			<p class="center">无法搜索到与之相匹配的信息。</p>
		<?php endif; ?>
	</div><!-- POST ENTRY END -->
	<?php include (TEMPLATEPATH . '/includes/paginate.php'); ?>
</div><!-- CONTENT END -->
<script type="text/javascript">
$(".blog_loading").animate({width:"45%"})
</script>
<?php get_sidebar(); ?>
<?php get_footer(); ?>