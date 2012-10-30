<?php get_header(); ?>

<div class="mainBox">
	<div class="wrapOut cf">
		<div class="boxLeft">
			<div class="boxLeftIn ovh">
				<div class="archiveList">
					<?php if ( is_day() ) : ?>
						<h2 class="pt5 pb5 mb10 archiveTit archiveTitDaily"><?php printf( __( '日度 文档归类: %s' ), '<span>' . get_the_date() . '</span>' ); ?></h2>
					<?php elseif ( is_month() ) : ?>
						<h2 class="pt5 pb5 mb10 archiveTit archiveTitMonthly"><?php printf( __( '月度 文档归类: %s' ), '<span>' . get_the_date( 'F Y' ) . '</span>' ); ?></h2>
					<?php elseif ( is_year() ) : ?>
						<h2 class="pt5 pb5 mb10 archiveTit archiveTitYearly"><?php printf( __( '年度 文档归类: %s' ), '<span>' . get_the_date( 'Y' ) . '</span>' ); ?></h2>
					<?php elseif ( is_tag() ) : ?>
						<h2 class="pt5 pb5 mb10 archiveTit archiveTitTag"><?php printf( __( 'Tag 文档归类: %s' ), '<span>' . single_tag_title( '', false ) . '</span>' ); ?></h2>
					<?php else : ?>
						<h2 class="pt5 pb5 mb10 archiveTit"><?php _e( '文档归类' ); ?></h2>
					<?php endif; ?>
					<div class="archive_list pt10 mr10 ml10 ovh">
						<ul id="post_list" class="cf">
							<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
							<li class="mb20">
								<h3 class="title pt10 pb10 pl5"><a href="<?php the_permalink() ?>" title="<?php the_title(); ?>"><?php the_title(); ?></a></h3>
								<p class="lh20 pt5 pb5 g9 articleInfo"><?php the_time('Y-m-d'); ?> | <?php _e("Author:"); ?><?php the_author(); ?> | <?php _e("Categories:"); ?><?php the_category('、') ?></p>
								<?php the_content(); ?>
								<p class="lh24 lh24 pt5 pb5 g9 ml10 articleInfo meta"><?php _e("Tags:"); ?><?php the_tags(__(' '), '、'); ?></p>
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
			</div>
		</div>
	</div>
</div>

<?php get_footer(); ?>