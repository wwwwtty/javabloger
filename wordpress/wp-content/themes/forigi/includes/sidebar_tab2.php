<div class="tabs2">
		<ul>
			<li class="sel">标签云</li>
			<li>日志归档</li>
			<li>日历表</li>
		</ul>
</div>
<div class="tabscontent">
	<ul class="tabc2">
		<?php wp_tag_cloud('number=84&orderby=count&order=DESC&smallest=16&largest=16&unit=px'); ?>
	</ul>
	
	<ul class="tabc2" style="display:none;">
		<?php wp_get_archives('type=monthly'); ?>
	</ul>

	
	<ul class="tabc2" style="display:none; min-height:240px; _height:240px; ">
		<?php get_calendar(); ?>
	</ul>
</div>
<div class="clearfix"></div>

<script type="text/javascript">
$(".tabs2 li").hover(function(){
	if($(this).attr("class")!="sel")$(this).addClass("on")
},function(){
	$(this).removeClass("on")
}).mouseenter(function(){
	$(this).siblings().removeClass()
	$(this).attr("class","sel")
	$("ul.tabc2:visible").hide(400,function(){
		$("ul.tabc2").eq($(".tabs2 li.sel").index()).show(600)
	})
	//针对当前选项卡的内容显示 $(this).index()
	// mouseenter  click
})
</script>
