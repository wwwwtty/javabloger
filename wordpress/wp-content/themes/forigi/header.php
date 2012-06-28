<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="<?php bloginfo('html_type'); ?>; charset=<?php bloginfo( 'charset' ); ?>" />
<?php include('includes/seo.php'); ?>
<!--[if lt IE 6]>
<?php include('includes/skillie6.php'); ?>
<![endif]-->
<link href="<?php bloginfo('stylesheet_url'); ?>" rel="stylesheet" type="text/css" />
<link href="<?php bloginfo('template_directory'); ?>/css/comments.css" rel="stylesheet" type="text/css" />
<link rel="alternate" type="application/rss+xml" title="<?php bloginfo('name'); ?> RSS Feed" href="<?php bloginfo('rss2_url'); ?>" />
<link rel="alternate" type="application/atom+xml" title="<?php bloginfo('name'); ?> Atom Feed" href="<?php bloginfo('atom_url'); ?>" />
<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />
<link rel="shortcut icon" href="<?php bloginfo('template_directory'); ?>/images/favicon.ico" />
<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/pirobox.min.js"></script>
<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/myscript.js"></script>
<?php wp_head(); ?>
<?php if ( is_singular() ){ ?>
<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/comments-ajax.js"></script>
<?php } ?>
<?php include('includes/lazyload.php'); ?>

</head>
<body>
<div id="wrapper">
<div id="navigation">
	<div id="container">
		<?php if ( function_exists( 'wp_nav_menu' ) ) { // Added in 3.0 ?>
		<?php wp_nav_menu( array(
			'theme_location' => 'primary',
			'container' => false, 
			'menu_id' => 'topnav', 
			'fallback_cb' => 'revert_wp_menu_page'
			)); ?>
		<?php } else { ?>
			<ul id="topnav">
			<li id="<?php if (is_home()) { ?>home<?php } else { ?>page_item<?php } ?>"><a href="<?php bloginfo('url'); ?>" title="Home">Home</a></li>
			<?php wp_list_pages('title_li=&depth=0&orderby=name'); ?>
			</ul><!-- topnav end -->
		<?php } ?>
		<div id="topsearch">
			<form method="get" id="searchform" action="<?php bloginfo('home'); ?>/">
				<input type="text" name="s" id="s" value="输入关键字..." onfocus="if (this.value == '输入关键字...') {this.value = '';}" onblur="if (this.value == '') {this.value = '输入关键字...'}" />
			</form>
			<div class="clearfix"></div>
		</div><!-- topsearch end -->
	<div class="blog_loading"></div>
	<div class="clearfix"></div>
	</div><!-- container end 作用:定位nav-->
</div><!-- navigation end -->

<div id="container">
	<div id="header">
		<div id="siteinfo">
			<?php $header_logo_activate = get_qintag_option('header_logo_activate');$logo_url = get_qintag_option('logo_url');
				if(($logo_url == '') || ($header_logo_activate == 'No')) { ?>
					<div id="site_title">
						<a href="<?php bloginfo('siteurl');?>" title="<?php bloginfo('name');?>"><?php bloginfo('name');?></a>
					</div><!-- site_title end -->	
					<div id="site_description"><?php bloginfo( 'description' ); ?></div>
				<?php } else { ?>
				<a href="<?php echo home_url( '/' ); ?>" title="<?php bloginfo('name'); ?>"><img src="<?php echo get_qintag_option('logo_url'); ?>" alt="<?php bloginfo('name'); ?>" /></a>
			<?php } ?>
		</div><!-- siteinfo end -->
		<div id="topbanner">
			<?php $header_banner = get_qintag_option('header_banner'); if($header_banner == '') { ?>
					<a target="_blank" href="http://www.qintag.com/"><img src="<?php bloginfo('stylesheet_directory');?>/images/ad.jpg" alt="Qintag" width="468" height="60" border="0" /></a>
			<?php } else { ?>
				<?php echo get_qintag_option('header_banner'); ?>
			<?php } ?>
		</div><!-- topbanner end -->
		
		<div id="navigation2">
			<?php include('includes/social.php'); ?><!-- 加载微博、加入收藏以及订阅图标 -->
			<?php if ( function_exists( 'wp_nav_menu' ) ) { // Added in 3.0 ?>
			<?php wp_nav_menu( array(
				'theme_location' => 'secondary',
				'container' => false, 
				'menu_id' => 'topnav2', 
				'fallback_cb' => 'revert_wp_menu_category'
				)); ?>
			<?php } else { ?>
			<ul id="topnav2">
			<?php wp_list_categories('title_li=&orderby=name&number=7'); ?>
				</ul><!-- dropmenu2 end -->
			<?php } ?>
		<div class="clearfix"></div>
		</div><!-- navigation end -->
	<div class="clearfix"></div>
</div><!-- header end -->
<?php include (TEMPLATEPATH . '/includes/breadcrumbs.php'); ?>

<script type="text/javascript">
$(".blog_loading").animate({width:"5%"})
</script>