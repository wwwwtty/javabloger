<?php get_header(); ?>
<div id="content">
	<div id="post_entry">
		<?php $postcounter = 0; if (have_posts()) : ?>
		<?php while (have_posts()) : $postcounter = $postcounter + 1; the_post(); ?>
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
					<h2><a href="<?php the_permalink(); ?>" rel="bookmark" title="<?php the_title(); ?>"><?php echo cut_str($post->post_title,66); ?></a></h2>
						<div class="post_admin">
					    作者:<?php the_author_posts_link(); ?> /发表于<?php echo human_time_diff(get_the_time('U'), current_time('timestamp')) .'前'; ?> /分类:<?php the_category(', ') ?><?php if(function_exists("the_tags")) : ?><?php the_tags(' /Tag:') ?><?php if(function_exists('the_views')) { echo" /"; the_views(); } ?><?php endif; ?>&nbsp;<?php edit_post_link('[编辑]'); ?>
						</div>
					<div class="clearfix"></div>
				</div><!-- post info end -->
				<div class="post_content">
					<?php include('includes/thumbnail.php'); ?><!-- 给首页每个文章增加一个随机图 -->
					<?php echo mb_strimwidth(strip_tags(apply_filters('the_content', $post->post_content)), 0, 450,"......"); ?>
					<div class="readmore">
						<a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>">Read More</a>
					</div>
					<div class="clearfix"></div>
				</div><!-- post content end -->
			</div><!-- post meta end -->
		<?php endwhile; ?>
		<?php else : ?>
			<p class="center">抱歉!</p>
			<p class="center">无法搜索到与之相匹配的信息。</p>
	<?php endif; ?>
	</div><!-- post entry end -->
	<?php include (TEMPLATEPATH . '/includes/paginate.php'); ?>
</div><!-- content end -->
<script type="text/javascript">
$(".blog_loading").animate({width:"45%"})
</script>
<?php get_sidebar(); ?>
<?php get_footer(); ?>