<?php get_header(); ?>
<div id="content">

<!-- 要使用幻灯片，请先移步：http://www.qintag.com/?p=2286   -->
<?php /* if((is_home()) && (is_front_page()) && ($paged < 1)) { ?>
<?php include (ABSPATH . '/wp-content/plugins/wp-featured-content-slider/content-slider.php'); ?>
<?php } */ ?>

	<div id="post_entry">
		<?php $temp = $wp_query; $wp_query= null; $wp_query = new WP_Query('showposts=&cat=&paged=' . $paged);?>
		<?php $postcounter = 0; if ($wp_query->have_posts()) : ?>
		<?php while ($wp_query->have_posts()) : $postcounter = $postcounter + 1; $wp_query->the_post();?>
		
		<?php if(is_sticky()) : ?>
			<div id="post_meta">
				<div id="post_sticky">
					<div class="post_comments">
						<?php comments_popup_link('0+', '1+', '%+'); ?>
					</div>
					<span><?php {echo '<img src="'.get_bloginfo('template_directory').'/images/sticky.gif" />';}?>[置顶]</span> <h2><a href="<?php the_permalink(); ?>" rel="bookmark" title="<?php the_title(); ?>"><?php echo cut_str($post->post_title,80); ?></a></h2>
						<div class="post_admin">
					        作者:<?php the_author_posts_link(); ?> /发表于<?php the_time('Y-m-d') ?>/分类:<?php the_category(', ') ?><?php if(function_exists("the_tags")) : ?><?php the_tags(' /Tag:') ?><?php if(function_exists('the_views')) { echo"/"; the_views(); } ?><?php endif; ?>&nbsp;<?php edit_post_link('[编辑]'); ?>
						</div>
				</div><!--"POST STICKY END -->
			</div><!-- POST INFO END -->
		<?php else : ?>
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
					</div><!-- POST DATE END -->
					<h2><a href="<?php the_permalink(); ?>" rel="bookmark" title="<?php the_title(); ?>"><?php echo cut_str($post->post_title,80); ?><?php $t1=$post->post_date; $t2=date("Y-m-d H:i:s"); $diff=(strtotime($t2)-strtotime($t1))/3600; if($diff<24)
					{echo '<img src="'.get_bloginfo('template_directory').'/images/new.gif" />';}?></a></h2>
						<div class="post_admin">
					    作者:<?php the_author_posts_link(); ?> /发表于<?php echo human_time_diff(get_the_time('U'), current_time('timestamp')) .'前'; ?> /分类:<?php the_category(', ') ?><?php if(function_exists("the_tags")) : ?><?php the_tags(' /Tag:') ?><?php if(function_exists('the_views')) { echo" /"; the_views(); } ?><?php endif; ?>&nbsp;<?php edit_post_link('[编辑]'); ?>
						</div>
					<div class="clearfix"></div>
				</div><!-- POST INFO END -->
				<div class="post_content">
					<?php include('includes/thumbnail.php'); ?><!-- 给首页每个文章增加一个随机图 -->
					<?php echo mb_strimwidth(strip_tags(apply_filters('the_content', $post->post_content)), 0, 420,"......"); ?>
					<div class="readmore">
						<a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>">Read More</a>
					</div>
					<div class="clearfix"></div>
				</div><!-- POST CONTENT END -->
			</div><!-- POST META END -->
		<?php endif; ?>	
		<?php endwhile; ?>
		<?php else : ?>
			<p class="center">抱歉!</p>
			<p class="center">无法搜索到与之相匹配的信息。</p>
		<?php endif; ?>
		<?php $wp_query = null; $wp_query = $temp; ?>
	</div><!-- POST ENTRY END -->
	<?php include (TEMPLATEPATH . '/includes/paginate.php'); ?>
</div><!-- CONTENT END -->

<script type="text/javascript">
$(".blog_loading").animate({width:"45%"})
</script>

<?php get_sidebar(); ?>
<?php get_footer(); ?>