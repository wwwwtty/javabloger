<div class="tabs">
		<ul>
			<li class="sel">热评日志</li>
			<li>最新日志</li>
			<li>最近更新</li>
			<li>随机日志</li>
		</ul>
</div>

<div class="tabscontent">
	<ul class="tabc">
		<?php some_posts( $orderby = 'comment_count' ); ?>
	</ul>
    
	<ul class="tabc" style="display:none;">
		<?php some_posts( $orderby = 'date' ); ?>
	</ul>
	
	<ul class="tabc" style="display:none;">
		<?php recently_updated_posts(); ?>
	</ul>
	
	<ul class="tabc" style="display:none;">
		<?php some_posts( $orderby = 'rand' );  ?>
	</ul>

</div>
<div class="clearfix"></div>
<script type="text/javascript">
$(".tabs li").hover(function(){
	if($(this).attr("class")!="sel")$(this).addClass("on")
},function(){
	$(this).removeClass("on")
}).click(function(){
	$(this).siblings().removeClass()
	$(this).attr("class","sel")
	$("ul.tabc:visible").fadeOut(300,function(){
		$("ul.tabc").eq($(".tabs li.sel").index()).fadeIn(500)
	})
	//针对当前选项卡的内容显示 $(this).index()
	// mouseenter  click
})
</script>


