<!DOCTYPE HTML>
<html dir="ltr" lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="author" content="黄锦诚">
<title><?php
	if ( is_home() ) {
        bloginfo('name'); echo " - "; bloginfo('description');
    } elseif ( is_category() ) {
        single_cat_title(); echo " - "; bloginfo('name');
    } elseif (is_single() || is_page() ) {
        single_post_title();echo " - "; bloginfo('name');
    } elseif (is_search() ) {
        echo "搜索结果列表"; echo " - "; bloginfo('name');
    } elseif (is_404() ) {
        echo '页面未找到!';echo " - "; bloginfo('name');
    } else {
        wp_title('',true);echo " - "; bloginfo('name');
    } ?></title>
<link rel="shortcut icon" type="image/x-icon" href="<?php bloginfo('template_url'); ?>/s/favicon.ico">
<link rel="stylesheet" type="text/css" media="all" href="<?php bloginfo('stylesheet_url'); ?>">
<link rel="stylesheet" type="text/css" media="all" href="<?php bloginfo('template_url'); ?>/s/aqy106_lib.css">
<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/jquery.js"></script>
<script type="text/javascript" src="<?php bloginfo('template_url'); ?>/js/aqy106basic.js"></script>
<!--rss-->
<link rel="alternate" type="application/rss+xml" title="RSS 2.0 - 所有文章" href="<?php echo get_bloginfo('rss2_url'); ?>">
<link rel="alternate" type="application/rss+xml" title="RSS 2.0 - 所有评论" href="<?php bloginfo('comments_rss2_url'); ?>">
<!--pingback-->
<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>">
<!--用于显示登录后的控制BAR-->
<?php wp_head(); ?>
</head>
<!--刷新缓存-->
<?php flush(); ?>
<body>
<header>
	<div class="head rel">
		<div class="loading abs" id="loading"></div>
		<div class="mainBox cf">
			<h1 class="l logo"><a class="db" title="<?php bloginfo('name'); ?>--<?php bloginfo('description'); ?>" href="<?php echo get_option('home'); ?>/"><img src="<?php bloginfo('template_url'); ?>/s/logo.png" alt="<?php bloginfo('name'); ?>--<?php bloginfo('description'); ?>"></a></h1>
			<form id="searchform" method="get" action="<?php bloginfo('home'); ?>" class="cf">
				<div class="r topSearch p1">
					<input type="text" value="<?php the_search_query(); ?>" name="s" id="s" class="searchIpt l" size="30" placeholder="请输入搜索的关键字">
					<input type="submit" name="search" id="sb" class="search l" value="<?php _e("Search"); ?>">
				</div>
			</form>
		</div>
	</div>
</header>
<nav>
	<div class="nav">
		<div class="nav" id="nav">
			<div class="mainBox cf">
				<ul class="b l">
					<li <?php if (is_home()) { echo 'class="current"';} ?>><a href="<?php echo get_option('home'); ?>/" title="<?php bloginfo('name'); ?>">首页</a></li>
					<?php
					$currentcategory = '';
					// 以下这行代码用于在导航栏添加分类列表，
					if  (!is_page() && !is_home()) {
						$catsy = get_the_category();
						$myCat = $catsy[0]->cat_ID;
						$currentcategory = '&current_category='.$myCat;
					}
					wp_list_categories('depth=1&title_li=&show_count=0&hide_empty=0&child_of=0'.$currentcategory);
					// 以下这行代码用于在导航栏添加页面列表
					wp_list_pages('depth=1&title_li=&sort_column=menu_order');
					?>
				</ul>
				<a href="<?php echo get_bloginfo('rss2_url'); ?>" target="_blank" class="rss r oh mt5" title="<?php bloginfo('name'); ?>的RSS订阅">RSS</a>
			</div>
		</div>
	</div>
</nav>
