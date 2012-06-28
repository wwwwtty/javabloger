<?php
/*
Template Name: Archives
*/
?>
<?php get_header(); ?>

<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
			<ul id="archives">
				<li>
					<a id="expand_collapse" href="#"><h2>全部展开/收缩</h2></a>
					<ul><?php archives_list_SHe(); ?></ul>
				</li>
			</ul><!-- ARCHIVES END -->
			<div class="clearfix"></div>
		<?php endwhile; ?>
		<?php comments_template( '', true ); ?>
	<?php else : ?>
		<p class="center">抱歉!</p>
		<p class="center">无法搜索到与之相匹配的信息。</p>
	<?php endif; ?>
</div><!-- fullcontent END -->

<!--  存档页面 jQ伸缩  -->
<script type="text/javascript">
$('#expand_collapse,.archives-yearmonth').css({cursor:"pointer"});
$('#archives ul li ul.archives-monthlisting').hide();
$('#archives ul li ul.archives-monthlisting:first').show();
$('#archives ul li span.archives-yearmonth').click(function(){$(this).next().slideToggle('fast');return false;});
//以下下是全局的操作
$('#expand_collapse').toggle(
function(){
$('#archives ul li ul.archives-monthlisting').slideDown('fast');
},
function(){
$('#archives ul li ul.archives-monthlisting').slideUp('fast');
});
</script>
<script type="text/javascript">
$(".blog_loading").animate({width:"65%"})
</script>
<?php get_footer(); ?>