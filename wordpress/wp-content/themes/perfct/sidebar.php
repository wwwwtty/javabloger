<div class="sidebar">
	<ul>
	<?php // 如果没有使用 Widget 才显示以下内容, 否则会显示 Widget 定义的内容
	if ( !function_exists('dynamic_sidebar') || !dynamic_sidebar('Sidebar Index') ) : ?>
	<!-- widget 1 -->
	<li class="cats both bglist">
		<h3>分类</h3>
		<ul>
			<?php wp_list_cats('sort_column=name&optioncount=1&depth=-1&use_desc_for_title=0&hide_empty=0&exclude=249'); ?>
		</ul>
	</li>
	<!-- widget 2 -->
	<li class="randpost">
		<?php if (is_single() || is_category()) { ?>
			<h3><?php $category = get_the_category(); echo $category[0]->cat_name; ?>下的最新文章</h3>
			<ul>
			<?php
				$cat = get_the_category();
				$cat_id = $cat[0]->cat_ID;
				query_posts('order=asc&cat='.$cat_id);
				while(have_posts()):the_post();
			?>
				<li><a href="<?php the_permalink();?>" title="<?php the_title(); ?>"><?php the_title();?></a></li>
			<?php  endwhile; wp_reset_query();?>
			</ul>
		<?php } else { ?>
			<h3><?php _e("随机文章"); ?></h3>
			<ul>
				<?php $rand_posts = get_posts('numberposts=10&orderby=rand');  foreach( $rand_posts as $post ) : ?>
				<li><a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>"><?php echo mb_strimwidth(get_the_title(), 0, 42, '...'); ?></a></li>
				<?php endforeach; ?>
			</ul>
		<?php } ?>
	</li>
	<!-- widget 3 侧边栏广告位-->
	<li class="ads">
		<h3>Google Adsense</h3>
		<div class="padding10">
		<a target="_blank" href="http://faq.wopus.org/"><img alt="wopus-FAQ.wordpress问答" src="http://leotheme.cn/wp-content/uploads/2011/08/ad.png"></a>
		</div>
	</li>
	<!-- widget 4 -->
	<li>
		<h3>最新评论</h3>
		<ul>
			<?php Dreamy_news_comments(); ?>
		</ul>
	</li>
	<!-- widget 5 -->
	<?php wp_list_bookmarks('title_before=<h3>&title_after=</h3>'); ?>

	<?php endif; ?><!-- End sidebar widget -->
	</ul>
</div>