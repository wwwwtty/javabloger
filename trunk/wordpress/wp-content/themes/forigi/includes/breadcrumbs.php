<?php
$category = get_the_category();
$current_cat = $category[0]->cat_ID;
?>

<?php if (is_single()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;<?php $category = get_the_category(); if ($category) { echo '<a href="' . get_category_link( $category[0]->term_id ) . '" title="' . sprintf( __( "View all posts in %s" ), $category[0]->name ) . '" ' . '>' . $category[0]->name.'</a> '; } ?>&raquo;<?php the_title(); ?>
	<div class="clearfix"></div>
	</div>
	
<?php } else if (is_home()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;
	<div class="clearfix"></div>
	</div>
	
<?php } else if (is_category()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;所有属于&quot; <?php single_cat_title(); ?>&quot; 分类文章<div class="clearfix"></div>
	</div>

<?php } else if (is_tag()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;所有属于&quot; <?php single_cat_title(); ?>&quot; 分类文章<div class="clearfix"></div>
	</div>
	
<?php } else if (is_page()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;<?php the_title(); ?><div class="clearfix"></div>
	</div>
	
	
<?php } else if (is_404()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo; <?php _e('未找到指定的页面( ERROR 404 )'); ?><div class="clearfix"></div>
	</div>
	
<?php } else if (is_archive()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;<?php $post = $posts[0]; // Hack. Set $post so that the_date() works. ?>
			<?php /* If this is a category archive */ if (is_day()) { ?>
				所有&quot;<?php the_time('Y年m月d日'); ?>&quot; 的文章
			<?php /* If this is a monthly archive */ } elseif (is_month()) { ?>
				所有&quot;<?php the_time('Y年m月'); ?>&quot; 的文章
			<?php /* If this is a yearly archive */ } elseif (is_year()) { ?>
				所有&quot;<?php the_time('Y年'); ?>&quot; 的文章
			<?php } ?>
	<div class="clearfix"></div>
	</div>

<?php } else if (is_search()) { ?>
	<div id="breadcrumbs">
		<span><?php include('time.php'); ?></span>当前位置&nbsp;:&nbsp;<a href="<?php bloginfo('home'); ?>" title="首页">首页</a>&nbsp;&raquo;&quot;<?php the_search_query(); ?>&quot; 的搜索结果	<div class="clearfix"></div>
	</div>
	
<?php } else { ?>
<?php { /* nothing */ } ?>
<?php } ?>