<?php
/*
Template Name: miniblog
*/
?>
<?php get_header(); ?>
<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<?php $miniblog_template_code = get_qintag_option('miniblog_template_code');if($miniblog_template_code == '') { ?>
				<?php { /* nothing */ } ?>
			<?php } else { ?>
				<?php echo get_qintag_option('miniblog_template_code'); ?>
			<?php } ?>
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