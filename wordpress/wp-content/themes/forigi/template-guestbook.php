<?php
/*
Template Name: GuestBook
*/
?>
<?php get_header(); ?>
<?php
global $wpdb;
$counts = $wpdb->get_results("SELECT COUNT(comment_author) AS cnt, comment_author, comment_author_url, comment_author_email FROM (SELECT * FROM $wpdb->comments LEFT OUTER JOIN $wpdb->posts ON ($wpdb->posts.ID=$wpdb->comments.comment_post_ID) WHERE comment_date > date_sub( NOW(), INTERVAL 1 MONTH ) AND user_id='0' AND comment_author != 'qintag' AND post_password='' AND comment_approved='1' AND comment_type='') AS tempcmt GROUP BY comment_author ORDER BY cnt DESC");
foreach ($counts as $count) {
					$c_url = $count->comment_author_url;
					if ($c_url == '') $c_url = 'http://www.qintag.com/';
					$mostactive .= '<li>' . '<a href="'. $c_url . '" title="' . $count->comment_author . ' ('. $count->cnt . 'comments)">' . get_avatar($count->comment_author_email, 48) . '</a></li>';
				}
?>
<div id="fullcontent">
	<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>
				<div class="guestbook">
					<h2>他们的留言很给力，您也可以在下面留言，我会尽快回复的。</h2>
					<li>
						<ul>	
							<?php echo stripcslashes($mostactive); ?>
						</ul>
					</li>	
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