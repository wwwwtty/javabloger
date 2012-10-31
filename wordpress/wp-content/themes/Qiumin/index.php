<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut  cf">
		<?php if (get_option('web_if_tip') == '1') { ?>	
			<div class="notices p2">
				<div class="noticesIn ovh" id="notices">
					<ul>
						<?php echo get_option('web_tip'); ?>
					</ul>
				</div>
			</div>
		<?php } else { ?><?php } ?>
		<div class="boxLeft">
			<div class="boxLeftIn">
				<div class="indexArticleList">
					<div class="archive_list pt10 mr10 ml10 ovh">
						<ul class="cf">
							<!-- Blog Post -->
							<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
							<li class="mb20">
								<!-- Post Title -->
								<h3 class="title pt10 pb10 pl5"><a class="tdn" href="<?php the_permalink(); ?>" rel="bookmark" title="<?php the_title(); ?>"><?php the_title(); ?></a></h3>		
								<!-- Post Data -->
								<p class="h24 lh24 pt5 pb5 g9 articleInfo">
										<span class="mr10"><?php _e("Author:"); ?><?php the_author(); ?></span>|
										<span class="ml10 mr10"><?php _e("Categories:"); ?><?php the_category('、') ?></span>|
										<span class="ml10 mr10">发布时间：<?php the_time('Y-m-d') ?></span>|
										<span class="ml10 mr10"><?php comments_popup_link('0 条评论', '1 条评论', '% 条评论', '', '评论已关闭'); ?></span>|
										<?php edit_post_link('编辑', ' &nbsp;', ''); ?>
								</p>
								<!-- Post Content -->
								<?php echo mb_strimwidth(strip_tags(apply_filters('the_content', $post->post_content)), 0, 420,"......"); ?>
								<p class="h24 lh24 pt5 pb5 g9 ml10 articleInfo meta"><?php _e("Tags:"); ?><?php the_tags(__(' '), '、'); ?></p>
							</li>
							<?php endwhile; ?>
							<?php else : ?>
							<h3 class="pt10 pb10 g3">非常抱歉，该分类下没有文章！</h3>
							<p class="g6">非常抱歉，该分类下没有文章！请移步其他分类下进行查看，谢谢！</p>
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
				<?php wp_list_bookmarks('title_before=<h4>&title_after=</h4>&category_before=<div id=%id class="slidesBox p1 mb5 slideLink">&category_after=</div>'); ?>
			</div>
		</div>
	</div>
</div>

<?php get_footer(); ?>