<div id="sidebar">
	<?php $sidebar_tab_activate = get_qintag_option('sidebar_tab_activate'); if($sidebar_tab_activate == 'NO') { ?>
		<?php { /* nothing */ } ?>
	<?php } else { ?>
		<?php include (TEMPLATEPATH . '/includes/sidebar_tab.php'); ?>
	<?php } ?>
	
	<ul class="sidebar_list">
		<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar(1)) : ?>
		<!-- 如果不是首页则应该显示最新文章 -->
			<?php if( is_home()) { ?><?php } else { ?>
				<li>
					<h3><?php _e('最新文章'); ?></h3>
					<ul>
						<?php some_posts( $orderby = 'date' ); ?>
					</ul>
				</li>
			<?php } ?>	
			<li>
				<h3><?php _e('随机文章'); ?></h3>
				<ul>
					<?php some_posts( $orderby = 'rand' );  ?>
				</ul>
			</li>
			<li>
				<h3><?php _e('标签云'); ?></h3>
				<ul><?php wp_tag_cloud(); ?></ul>
			</li>
			<li>
				<h3><?php _e('日历表'); ?></h3>
				<ul>
					<?php get_calendar(); ?>
				</ul>
			</li>
		<?php endif; ?>
	</ul><!-- sidebarlist end -->
	
	<?php $sidebar_tab2_activate = get_qintag_option('sidebar_tab2_activate'); if($sidebar_tab2_activate == 'NO') { ?>
		<?php { /* nothing */ } ?>
	<?php } else { ?>
		<?php include (TEMPLATEPATH . '/includes/sidebar_tab2.php'); ?>
	<?php } ?>

	<!-- 仅在首页显示友情连接 -->
	<?php if (is_home()) { ?>
		<?php $sidebar_links_activate = get_qintag_option('sidebar_links_activate'); if($sidebar_links_activate == 'NO') { ?>
			<?php { /* nothing */ } ?>
		<?php } else { ?>
			<div class="tabscontent">
				<h3><span><a href="<?php bloginfo('url'); ?>/links" target="_blank" title="申请链接">申请链接&gt;&gt;</a></span><?php _e('友情连接'); ?></h3>
				<ul class="flinks">
					<?php wp_list_bookmarks('title_li=&categorize=0'); ?>
					<!-- 为了保证后期可以得到持续的技术支持，请您保留此连接 -->
			
					<li><a rel="nofollow" href="http://mapeimapei.blog.163.com/" target="_blank">网易博客</a></li>
					<li><a rel="nofollow" href="http://pc12580.blog.163.com/" target="_blank">PC我帮您</a></li>					
					
					
					<li><a href="http://www.qintag.com/" target="_blank">forigi</a></li>
				</ul>
			</div>
		<?php } ?>
	<?php } else { ?>
	<?php } ?>

	<!-- sidebar left start-->
	<div id="sidebar_left">
		<ul class="sidebar_list">
			<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar(2)) : ?>
				<li>
					<h3><?php _e('分类目录'); ?></h3>
					<ul>
						<?php wp_list_categories('orderby=id&show_count=1&title_li=&depth=1'); ?>
					</ul>
				</li>
				<li>
				<h3><?php _e('文章归档'); ?></h3>
					<ul>
						<?php wp_get_archives('type=monthly&limit=12&show_post_count=1'); ?>
					</ul>
				</li>
			<?php endif; ?>
		</ul><!-- sidebarlist end -->
	</div><!-- sidebar left end -->

	<!-- sidebar right start-->
	<div id="sidebar_right">
		<ul class="sidebar_list">
			<?php if (!function_exists('dynamic_sidebar') || !dynamic_sidebar(3)) : ?>
				<li>
					<h3><?php _e('页面'); ?></h3>
					<ul>
						<?php wp_list_pages('title_li=&depth=1'); ?>
					</ul>
				</li>
				<li>
					<h3><?php _e('友情连接'); ?></h3>
					<ul>
						<?php get_links(-1, '<li>', '</li>', ' - '); ?>
						<!-- 为了保证后期可以得到持续的技术支持，请您保留此连接 -->
						<li><a href="http://www.qintag.com/" target="_blank">forigi theme</a></li>
					</ul>
				</li>
				<li>
					<h3><?php _e('功能'); ?></h3>
					<ul>
						<?php wp_register(); ?>
						<li><?php wp_loginout(); ?></li>
						<?php wp_meta(); ?>
					</ul>
				</li>
			<?php endif; ?>
		</ul><!-- sidebarlist end -->
	</div><!-- sidebar right end -->
</div><!-- sidebar end -->

<script type="text/javascript">
$(".blog_loading").animate({width:"85%"})
</script>

