<?php
/**
 * @package WordPress
 * @subpackage Default_Theme
 */
// Do not delete these lines
	if (!empty($_SERVER['SCRIPT_FILENAME']) && 'comments.php' == basename($_SERVER['SCRIPT_FILENAME']))
		die ('Please do not load this page directly. Thanks!');
	if ( post_password_required() ) { ?>
		<p class="nocomments">该文章设置了密码保护，请先输入密码再查看评论！</p>
	<?php
		return;
	}
?>
<!-- You can start editing here. -->
<div id="commentpost">
	<?php if ( have_comments() ) : ?>
		<?php if ( ! empty($comments_by_type['comment']) ) : ?>
			<h4>
				<?php
				//代码来源：http://kan.willin.org/?p=1318 感谢Willin.
					$my_email = get_bloginfo ( 'admin_email' );
					$str = "SELECT COUNT(*) FROM $wpdb->comments WHERE comment_post_ID = $post->ID
					AND comment_approved = '1' AND comment_type = '' AND comment_author_email";
					$count_t = $post->comment_count;
					$count_v = $wpdb->get_var("$str != '$my_email'");
					$count_h = $wpdb->get_var("$str = '$my_email'");
					echo $count_t, " 篇回应 (访客:", $count_v, " 篇, 博主:", $count_h, " 篇";
					$count_p = count($comments_by_type['pings']);
					if ( $count_p ) echo ", 引用:", $count_p, " 篇";
					$count_e = ($count_t - $count_v - $count_h - $count_p);
					if ( $count_e ) echo ", 其它:", $count_e, " 篇";
					echo ")";
				?>
			</h4>
			<div class="pnav">
				<div class="alignleft"><?php previous_comments_link(); ?></div>
				<div class="alignright"><?php next_comments_link(); ?></div>
				<div class="clearfix"></div>
			</div>
			<ol class="commentlist">
				<?php wp_list_comments( array( 'callback' => 'forigi_comment' ) ); ?>
			</ol>
			<div class="youtube_paginate">
				<?php paginate_comments_links(); ?>
			</div>
		<?php endif; ?>
		<?php if ( ! empty($comments_by_type['pings']) ) : ?>
			<div id="pingbox">
				<h4 id="pings">Trackbacks/Pingbacks</h4>
				<ol class="pinglist">
					<?php wp_list_comments('type=pings&callback=list_pings'); ?>
				</ol>
				<div class="clearfix"></div>
			</div>
		<?php endif; ?>
	<?php else : // this is displayed if there are no comments so far ?>
		<?php if ('open' == $post->comment_status) : ?>
			<!-- If comments are open, but there are no comments. -->

		<?php else : // comments are closed ?>
			<!-- If comments are closed. -->
			<p class="nocomments">抱歉，评论被关闭。</p>
		<?php endif; ?>
	<?php endif; ?>
	<?php if ('open' == $post->comment_status) : ?>
		<div id="respond">
			<h4>发表评论</h4>
			<div class="cancel-comment-reply">
				<?php cancel_comment_reply_link(); ?>
			</div>
			<?php if ( get_option('comment_registration') && !$user_ID ) : ?>
				<p>
					您必须 <a href="<?php echo get_option('siteurl'); ?>/wp-login.php?redirect_to=<?php echo urlencode(get_permalink()); ?>">登录</a>后才能发表评论。
				</p>
			<?php else : ?>
				<form action="<?php echo get_option('siteurl'); ?>/wp-comments-post.php" method="post" id="commentform" class="comment_form">
				<?php if ( $user_ID ) : ?>
					<p>
						您现在以 <a href="<?php echo get_option('siteurl'); ?>/wp-admin/profile.php"><?php echo $user_identity; ?></a>的身份登录。<a href="<?php echo wp_logout_url(get_permalink()); ?>" title="Log out of this account">[退出登录]</a>
						<?php elseif ( '' != $comment_author ): ?>
						<div class="author">
							<?php printf(__('欢迎 <strong>%s</strong>'), $comment_author); ?> 再次光临
							<a href="javascript:toggleCommentAuthorInfo();" id="toggle-comment-author-info">[ 更改 ]</a>
							<?php echo WelcomeCommentAuthorBack($comment_author_email); ?>
						</div>
						<script type="text/javascript" charset="utf-8">
							//<![CDATA[
							var changeMsg = "[ 更改 ]";
							var closeMsg = "[ 隐藏 ]";
							function toggleCommentAuthorInfo() {
								jQuery('#comment-author-info').slideToggle('slow', function(){
									if ( jQuery('#comment-author-info').css('display') == 'none' ) {
									jQuery('#toggle-comment-author-info').text(changeMsg);
									} else {
									jQuery('#toggle-comment-author-info').text(closeMsg);
									}
								});
							}
								jQuery(document).ready(function(){
								jQuery('#comment-author-info').hide();
							});
							//]]>
						</script>
					</p>
					<?php endif; ?>
				<?php if ( ! $user_ID ): ?>
					<div id="comment-author-info">
						<p>
							<input type="text" class="name" name="author" id="author" value="<?php echo $comment_author; ?>" size="22" tabindex="1" />
							<label for="author">昵称 <?php if ($req) echo "(*必填)"; ?></label>
						</p>
						<p>
							<input type="text" class="mail" name="email" id="email" value="<?php echo $comment_author_email; ?>" size="22" tabindex="2" />
							<label for="email">邮件 <?php if ($req) echo "(*必填，但不会公开)"; ?></label>
						</p>
						<p>
							<input type="text" class="home" name="url" id="url" value="<?php echo $comment_author_url; ?>" size="22" tabindex="3" />
							<label for="url">网址 (选填)</label>
						</p>
					</div>
				<?php endif; ?>
				
				<!--<p id="allowtags"><small><strong>XHTML:</strong> You can use these tags: <code><?php echo allowed_tags(); ?></code></small></p>-->
						<p>
							<textarea name="comment" id="comment" cols="50%" rows="8" class="comment_textarea" onkeydown="if(event.ctrlKey&amp;&amp;event.keyCode==13){document.getElementById('submit').click();return false};" onfocus="if (this.value == '留言是美德') {this.value = ''};" onblur="if (this.value == '') {this.value = '留言是美德'};">留言是美德</textarea>
						</p>

						<p>
							<input name="submit" type="submit" class="tinput" id="submit" value="发表评论(Ctrl+Enter)" />
							<input name="reset" type="reset" class="tinput" id="reset" value="重写" />
						</p>
						<?php comment_id_fields(); ?>
					<?php do_action('comment_form', $post->ID); ?>
				</form>
				<div class="clearfix"></div>
			<?php endif; // If registration required and not logged in ?>
		</div>
	<?php endif; // if you delete this the sky will fall on your head ?>
	<div class="clearfix"></div>
</div>