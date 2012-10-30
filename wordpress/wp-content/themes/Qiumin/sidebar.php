	<?php if ( !function_exists('dynamic_sidebar')|| !dynamic_sidebar('First_sidebar') ) : ?>
	<div class="slidesBox p1 mb5 p5 slideAbout">
		<dl class="lh24 f12 cf">
			<dt class="tc l">
				<img src="<?php bloginfo('template_url'); ?>/s/mlogo.jpg" alt="阿木" width="50" height="50">
				<a href="http://t.qq.com/heshengchao" target="_blank" title="腾讯微博" class="t hh db auto">腾讯微博</a>
			</dt>
			<dd>阿木，专注技术开发、分享经验、心得、感想，这里有不一样的惊喜！</dd>
		</dl>
		<div class="cl"></div>
	</div>
	
	<!-- 如果不是首页则应该显示最新文章 -->
			<?php if( is_home()) { ?><?php } else { ?>
				<li>
					<h3><?php _e('最新文章'); ?></h3>
					<ul>
						<?php some_posts( $orderby = 'date' ); ?>
					</ul>
				</li>
			<?php } ?>	
			<div class="slidesBox p1 mb5 slideNewRadom">  
				<h4>标签云</h4>
				<ul><?php wp_tag_cloud(); ?></ul>
			</div>

	<div class="slidesBox p1 mb5 slideCat">
		<h4>分类</h4>
		<ul class="tc pt5 pb5 cf">
			<?php wp_list_cats('sort_column=name&optioncount=1&depth=-1&use_desc_for_title=0&hide_empty=0&exclude=249'); ?>
		</ul>
		<div class="cl"></div>
	</div>
	<?php endif; ?>

	<?php if (is_single() || is_category()) { ?>
		<div class="slidesBox p1 mb5 slideNewRadom">  
			<h4><?php $category = get_the_category(); echo $category[0]->cat_name; ?>下的最新文章</h4>
			<ul>
			<?php
				$cat = get_the_category();
				$cat_id = $cat[0]->cat_ID;
				query_posts('order=asc&cat='.$cat_id);
				while(have_posts()):the_post();
			?>
				<li><a href="<?php the_permalink();?>" title="<?php the_title(); ?>"><?php echo mb_strimwidth(get_the_title(), 0, 30, '...'); ?></a></li>
			<?php  endwhile; wp_reset_query();?>
			</ul>
		</div>
	<?php } else { ?>
		<div class="slidesBox p1 mb5 slideNewRadom">  
			<h4><?php _e("随机文章"); ?></h4>
			<ul>
				<?php $rand_posts = get_posts('numberposts=10&orderby=rand');  foreach( $rand_posts as $post ) : ?>
				<li><a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>"><?php echo mb_strimwidth(get_the_title(), 0, 30, '...'); ?></a></li>
				<?php endforeach; ?>
			</ul>
		</div>
	<?php } ?>

	<div class="slidesBox p1 mb5 slideReplay">
		<h4>最新评论</h4>
		<div class="slideReplayList ovh mb10" id="slideReplayList">
			<?php
				global $wpdb;
				$sql = "SELECT DISTINCT ID, post_title, post_password, comment_ID,
				comment_post_ID, comment_author, comment_date_gmt, comment_approved,
				comment_type,comment_author_url,
				SUBSTRING(comment_content,1,30) AS com_excerpt
				FROM $wpdb->comments
				LEFT OUTER JOIN $wpdb->posts ON ($wpdb->comments.comment_post_ID =
				$wpdb->posts.ID)
				WHERE comment_approved = '1' AND comment_type = '' AND
				post_password = ''
				ORDER BY comment_date_gmt DESC
				LIMIT 20";
				$comments = $wpdb->get_results($sql);
				$output = $pre_HTML;
				$output .= "\n<ul class='p5 lh22'>";
				foreach ($comments as $comment) {
				$output .= "\n<li class='mb5 pb5'><span class='b'>".strip_tags($comment->comment_author)
				.":" . "</span><a href=\"" . get_permalink($comment->ID) .
				"#comment-" . $comment->comment_ID . "\" title=\"评论了 " .
				$comment->post_title . "\">" . strip_tags($comment->com_excerpt)
				."</a></li>";
				}
				$output .= "\n</ul>";
				$output .= $post_HTML;
				echo $output;
			?>
		</div>
	</div>

	<?php if ( !function_exists('dynamic_sidebar')|| !dynamic_sidebar('Third_sidebar') ) : ?>
		<div class="slidesBox p1 slidecloud mb5">
			<h4>标签云</h4>
			<p class="p10"><?php wp_tag_cloud('smallest=8&largest=22'); ?></p>
		</div>   
	<?php endif; ?>


	<?php if ( !function_exists('dynamic_sidebar')|| !dynamic_sidebar('Fourth_sidebar') ) : ?>        
		<div class="slidesBox p1 mb5 slideArtchive">          
			<h4>存档检索</h4>
			<ul class="tc pt5 pb5 cf">
				<?php wp_get_archives('limit=10'); ?>
			</ul>
			<div class="cl"></div>
		</div>
	<?php endif; ?>
