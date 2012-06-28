<div class="clearfix"></div>
<p id="back-top" style="display:none;"><a href="#top"><span></span>Back to Top</a></p>
</div><!-- container end -->
<div id="footer">
	<div id="footer_left">
		<?php if ( function_exists( 'wp_nav_menu' ) ) { // Added in 3.0 ?>
		<?php wp_nav_menu( array(
			'theme_location' => 'footernav',
			'container' => false, 
			'menu_id' => 'footer_nav', 
			'fallback_cb' => 'revert_wp_menu_category(title_li=&orderby=name&number=0)')); ?>
		<?php } ?>
    	<div id="power">
		 	基于<a rel="nofollow" href="http://wordpress.org/" title="WordPress.org"> WordPress</a> 技术创建 | Forigi WordPress Theme By <a href="http://www.qintag.com/" target="_blank">Qintag</a>
		</div>
        XHTML 1.1 and <a href "http://jigsaw.w3.org/css-validator/validator?uri=www.qintag.com&profile=css21&usermedium=all&warning=1&vextwarning=&lang=zh-cn">CSS 3</a> | <?php echo get_num_queries();?>querys in <?php echo timer_stop() ;?>seconds
	</div><!-- footer left end -->
	<div id="footer_right">
		<div id="copyright">
			Copyright <?php echo comicpress_copyright(); ?> <a href="<?php bloginfo('siteurl');?>/"><?php bloginfo('name');?></a>. All Rights Reserved.
		</div>
		<?php $icpbeian = get_qintag_option('icpbeian'); if($icpbeian == '') { ?>
			<?php { /* nothing */ } ?>
		<?php } else { ?>
			<a rel="nofollow" href="http://www.miibeian.gov.cn/"><?php echo get_qintag_option('icpbeian'); ?></a>
		<?php } ?>

		<?php $Gzipped_activate = get_qintag_option('Gzipped_activate'); $htmlGzipped = get_qintag_option('htmlGzipped'); 
		if(($htmlGzipped == '') || ($Gzipped_activate == 'No')) { ?>
			<?php { /* nothing */ } ?>
		<?php } else { ?>
			 | <a rel="nofollow" href="<?php echo get_qintag_option('htmlGzipped'); ?>">Gzipped Savings:78.84%</a>
		<?php } ?>
			
		<?php $cnzztongji = get_qintag_option('cnzztongji'); if($cnzztongji == '') { ?>
			<?php { /* nothing */ } ?>
		<?php } else { ?>
			 | <?php echo get_qintag_option('cnzztongji'); ?>
		<?php } ?>
			
		<?php $Creative_activate = get_qintag_option('Creative_activate'); if($Creative_activate == 'No') { ?>
			<?php { /* nothing */ } ?>
		<?php } else { ?>
			<p>本作品采用<a rel="nofollow" href="http://creativecommons.org/licenses/by-nc-sa/2.5/cn/">知识共享署名-非商业性使用-相同方式共享 2.5 中国大陆许可协议</a>进行许可</p>
		<?php } ?>
	</div><!-- footer right end -->
	<div class="clearfix"></div>
</div><!-- footer end -->
<?php wp_footer(); ?>
</div><!-- wrapper end -->
<script type="text/javascript">
$(".blog_loading").animate({width:"100%"},function(){
	setTimeout(function(){$(".blog_loading").fadeOut(1000);},1000);
}); 
</script>
</body>
</html>