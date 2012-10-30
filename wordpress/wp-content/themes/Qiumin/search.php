<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut cf">
		<div class="boxLeft">
			<div class="boxLeftIn ovh">
				<div class="searchPage">
					<h2 class="searchPageTit"><?php _e("Search"); ?>关键字: <?php the_search_query(); ?></h2>
					<div class="searchList pt10 pr10 pl10">
						<ul id="post_list" class="">
							<?php if (have_posts()) : while ( have_posts() ) : the_post(); ?>
							<li <?php post_class(); ?>>
								<h3 class="f16 searchListTit"><a class="tdn" href="<?php the_permalink() ?>"><?php the_title(); ?></a></h3>
								<p class="g9 f12"><?php the_time('Y-m-d'); ?> | <?php _e("Author:"); ?><?php the_author(); ?> | <?php _e("Categories:"); ?><?php the_category('、') ?></p>
								<p class="g9 f12 meta"><?php _e("Tags:"); ?><?php the_tags(__(' '), '、'); ?></p>
							</li>
							<?php endwhile; ?>
							<?php else : ?>
							<li class="tc">
								<h3 class="f16 searchListTit">非常抱歉，没有搜索到关键字为 <span class="hightline"><?php the_search_query(); ?></span> 的文章！</h3>
								<p class="g6 mt20">非常抱歉，没有搜索到关键字为 <span class="hightline"><?php the_search_query(); ?></span> 的文章！<br>请尝试重新填入搜索关键字进行搜索或移步其他分类下进行查看！</p>
							</li>
							<?php endif; ?>
						</ul> 
					</div>
					<div class="countPage">
						<span class="prev"><?php previous_posts_link(__('&laquo; Previous Page')) ?></span>
						<span class="next"><?php next_posts_link(__('Next Page &raquo;')) ?></span>
					</div>
				</div>
			</div>		
		</div>		
		<div class="Boxslide">
			<div class="slideIn">
				<?php get_sidebar(); ?>
			</div>
		</div>
	</div>
</div>

<?php get_footer(); ?>
