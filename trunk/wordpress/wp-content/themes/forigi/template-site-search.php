<?php
/*
Template Name: site-search
*/
?>
<?php get_header(); ?>

<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
	<div class="google_search">
		<div id="cse" style="width: 100%;">正在从Google 加载搜索结果......</div>
		<script src="//www.google.com/jsapi" type="text/javascript"></script>
		<script type="text/javascript"> 
			google.load('search', '1', {language : 'zh-CN'});
			google.setOnLoadCallback(function() {
				var customSearchControl = new google.search.CustomSearchControl('016728113596079249224:j3gjfmmhshs');
				customSearchControl.setResultSetSize(google.search.Search.FILTERED_CSE_RESULTSET);
				customSearchControl.draw('cse');
	
				var match = location.search.match(/q=([^&]*)(&|$)/);
				if(match && match[1]){
					var search = decodeURIComponent(match[1]);
					customSearchControl.execute(search);
				}	
			}, true);
			</script>
		<link rel="stylesheet" href="//www.google.com/cse/style/look/default.css" type="text/css" />
	<div class="clearfix"></div>
	</div>
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