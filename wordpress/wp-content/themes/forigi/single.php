<?php get_header(); ?>
<div id="content">
	<div id="post_entry">
		<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<div id="post_meta">
				<div class="post_info">
					<div class="post_comments">
						<?php comments_popup_link('0+', '1+', '%+'); ?>
					</div>
					<div class="post_date">
						<div class="day"><?php the_time('d'); ?></div>
						<div class="year_month">
							<span><?php the_time('Y'); ?></span>.<span><?php the_time('m') ?></span>
						</div>
					</div><!-- post date end -->
					<h1><?php echo cut_str($post->post_title,78); ?></h1>
						<div class="post_admin">
					    作者:<?php the_author_posts_link(); ?> /分类:<?php the_category(', ') ?><?php if(function_exists("the_tags")) : ?><?php the_tags(' /Tag:') ?><?php if(function_exists('the_views')) { echo" /"; the_views(); } ?><?php endif; ?>&nbsp;<?php edit_post_link('[编辑]'); ?>
						</div>
					<div class="text_size" style="display:none;">			
                   字号：<span class="text_large">L</span>
						<span class="text_middle">M</span>
						<span class="text_small">S</span>
					</div><!-- text size end -->
					<div class="clearfix"></div>
				</div><!-- post info end -->
			<div class="post_content">
				<div class="post_content_linkad">
					<?php $topad_post_content = get_qintag_option('topad_post_content'); if($topad_post_content == '') { ?>
						<?php { /* nothing */ } ?>
					<?php } else { ?>
						<?php echo get_qintag_option('topad_post_content'); ?>
					<?php } ?>
				</div>
			<div class="post_content_text">
				<?php the_content(); ?>
				<?php wp_link_pages('before=<div id="page-links">&after=</div>'); ?>

				<?php $bottomad_post_content = get_qintag_option('bottomad_post_content'); if($bottomad_post_content == '') { ?>
					<?php { /* nothing */ } ?>
				<?php } else { ?>
					<?php echo get_qintag_option('bottomad_post_content'); ?>
				<?php } ?>
				<div class="clearfix"></div>
			</div>
				<?php if (function_exists('wp_today')) {print wp_today();} ?><!-- 如果存在历史文章，则调用 -->
				
				<div class="post_share">
					<?php $post_share = get_qintag_option('post_share'); if($post_share == '') { ?>
						<?php { /* nothing */ } ?>
					<?php } else { ?>
						<?php echo get_qintag_option('post_share'); ?>
					<?php } ?>
				</div><!-- post share end -->
				<div class="clearfix"></div>
			</div><!-- post content end -->
		</div><!-- post meta end -->
	</div><!-- post entry end -->
	<!-- 调用相关文章 -->
	<?php include (TEMPLATEPATH . '/includes/related.php'); ?>
		<?php endwhile; ?>
		<?php comments_template( '', true ); ?>
		<?php else : ?>
			<p class="center">抱歉!</p>
			<p class="center">无法搜索到与之相匹配的信息。</p>
		<?php endif; ?>
</div><!-- content end -->
<script type="text/javascript">
$(".blog_loading").animate({width:"45%"})
</script>
<?php get_sidebar(); ?>
<?php get_footer(); ?>