<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut cf">
		<div class="boxLeft">
			<div class="boxLeftIn">
				<div class="rel errPage">
					<a href="<?php echo get_option('home'); ?>/" title="点击返回首页" class="abs errTo">返回首页</a>
				</div>
				<div id="wumiiDisplayDiv"></div><!-- for wumi -->
			</div>		
		</div>
		<div class="Boxslide">
			<div class="slideIn">
				<?php get_sidebar(); ?>

				<?php if ( !function_exists('dynamic_sidebar')|| !dynamic_sidebar('Third_sidebar') ) : ?>
					<div class="slidesBox p1 slidecloud mb5">
						<h4>标签云</h4>
						<p class="p10"><?php wp_tag_cloud('smallest=8&largest=22'); ?></p>
					</div>   
				<?php endif; ?>

				<?php if ( !function_exists('dynamic_sidebar')|| !dynamic_sidebar('Fourth_sidebar') ) : ?>        
					<div class="slidesBox p1 mb5 slideArtchive">          
						<h4>存档检索</h4>
						<ul class="tc pt5 pb5">
							<?php wp_get_archives('limit=10'); ?>
						</ul>
						<div class="cl"></div>
					</div>
				<?php endif; ?>

			</div>
		</div>
	</div>
</div>

<?php get_footer(); ?>