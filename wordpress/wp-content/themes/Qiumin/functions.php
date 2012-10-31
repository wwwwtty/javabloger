<?php
	$themename = "Qiumin";
	function theme_add_option() {
		global $themename;
		//create new top-level menu under Presentation
		add_theme_page("Qiumin主题设置", "Qiumin主题设置", 'administrator', basename(__FILE__), 'theme_form');
		//call register settings function
		add_action( 'admin_init', 'register_mysettings' );
	}

	function register_mysettings() {
		//register settings
		register_setting( 'theme-settings', 'web_tip');
		register_setting( 'theme-settings', 'web_if_tip');
	}
	////////////////最新日志 热评日志 随机日志 三合一代码//////////////////
	function filter_where($where = '') {
		$where .= " AND post_date > '" . date('Y-m-d', strtotime('-300 days')) . "'";
		return $where;
	}
	function some_posts($orderby = '', $plusmsg = '',$limit = 10) {
		add_filter('posts_where', 'filter_where');
		$some_posts = query_posts('posts_per_page='.$limit.'&ignore_sticky_posts=1&orderby='.$orderby);
		foreach ($some_posts as $some_post) {
			$output = '';
			$post_date = mysql2date('y年m月d日', $some_post->post_date);
			$commentcount = '('.$some_post->comment_count.' 条评论)';
			$post_title = htmlspecialchars(stripslashes($some_post->post_title));
			$permalink = get_permalink($some_post->ID);
			$output = '<li><a href="'.$permalink.'" title="'.$post_title.'">'.cut_str($post_title,44).'</a>'.$$plusmsg.'</li>';
			echo $output;
		}
		wp_reset_query();
	}
	// -------- END -------------------------------------------------------
	function theme_form() {
		global $themename;
		
?>
			<!-- Options Form begin -->
				<div class="setingBox">
					<h2 class="pt10 pb10 pl20">Qiumin主题设置</h2>	       
					<form method="post" action="options.php">
					<?php settings_fields('theme-settings'); ?>
						<div class="setingBoxIn">
							<div class="setingBoxTit">
								<select name="web_if_tip" class="status">
									<option value="1" <?php if (get_option('web_if_tip') == '1') { echo 'selected="selected"'; } ?>>显示</option>
									<option value="0" <?php if (get_option('web_if_tip') == '0') { echo 'selected="selected"'; } ?>>不显示</option>
								</select>
								网站公告设置 
							</div>   
							<p class="notices"><strong>*</strong>每条公告请放在&lt;li&gt;&lt;/li&gt;之间，字数不要超过父层的宽度，可设多条公告前台进行滚动，支持HTML代码。</p>
							<div class="setingBoxInside">
								<div class="text"><textarea name="web_tip"><?php echo get_option('web_tip'); ?></textarea></div>
								<p class="submit"><input type="submit" name="save" id="button-primary" class="button-primary" value="<?php _e('Save Changes') ?>" /></p>
							</div>
						</div>
					</form>
				</div>
				<style type="text/css">
					.setingBox{min-width:255px; width:50%;  float: left; margin-left: 20px; margin-bottom: 20px;}
					.setingBoxIn{background-color: #F5F5F5; background-image: -moz-linear-gradient(center top , #F9F9F9, #F5F5F5); border:#DFDFDF 1px solid; border-radius: 3px 3px 3px 3px; box-shadow: 0 1px 0 #FFFFFF inset;}
					.setingBoxIn	.setingBoxTit{border-bottom-color: #DFDFDF; box-shadow: 0 1px 0 #FFFFFF; text-shadow: 0 1px 0 #FFFFFF; background-color: #F1F1F1; background-image: -moz-linear-gradient(center top , #F9F9F9, #ECECEC); border-bottom:#DFDFDF 1px solid; font-size: 15px; font-weight: normal; line-height:24px; margin:0; padding:4px 5px;}
					.setingBoxIn	 p.notices{color:#999; font-size:12px; line-height:20px; padding-left:15px; margin:5px 0;}
					.setingBoxIn	 p.notices strong{color:#F00;}
					.status{float:right;}
					.setingBoxInside{padding:0 15px 15px 15px;}
					.setingBoxInside .text textarea{width:100%; height:150px;}
					.setingBoxInside p.submit{text-align:right; padding:0;}
				</style>
			<!--Options Form end -->
<?php 
	} 
	// create custom plugin settings menu
	add_action('admin_menu', 'theme_add_option');
?>