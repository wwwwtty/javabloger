<?php 
/**********************************************************************
 Copyright © 2007-2011 秦唐网 (http://qintag.com)
 本作品采用知识共享署名-非商业性使用-相同方式共享 2.5 中国大陆许可协议
 进行许可(http://creativecommons.org/licenses/by-nc-sa/2.5/cn/)
**********************************************************************/

//禁用半角符号自动转换为全角
remove_filter('the_content', 'wptexturize');
//移除管理员工具条(或:后台也有设置项)
//remove_action('init', '_wp_admin_bar_init');

//评论跳转链接添加nofollow
function nofollow_compopup_link(){
  return' rel="nofollow"';
}
add_filter('comments_popup_link_attributes','nofollow_compopup_link');

///*回复某人链接添加nofollow 这个理应是原生的, 可是在wp某次改版后被改动了,现在是仅当开启注册回复时才有nofollow,否则需要自己手动了*/
function nofollow_comreply_link($link){
  return str_replace('&amp;amp;lt;a','&amp;amp;lt;a rel="nofollow"',$link);
}
get_option('comment_registration')||
add_filter('comment_reply_link','nofollow_comreply_link');


//阻止 WordPress 对站内文章的 pingback http://wange.im/no-self-ping-in-wordpress.html
function no_self_ping( &$links ) {
$home = get_option( 'home' );
foreach ( $links as $l => $link )
if ( 0 === strpos( $link, $home ) )
unset($links[$l]);
}
add_action( 'pre_ping', 'no_self_ping' );

/////////////////// 404 ///////////////////////////////////////////////
function wcs_error_currentPageURL()
{
	$pageURL = 'http';
	if ($_SERVER["HTTPS"] == "on") {$pageURL .= "s";}
	$pageURL .= "://";
	if ($_SERVER["SERVER_PORT"] != "80")
	{
		$pageURL .= $_SERVER["SERVER_NAME"] . ":" . $_SERVER["SERVER_PORT"] . $_SERVER["REQUEST_URI"];
	}
	else
	{
		$pageURL .= $_SERVER["SERVER_NAME"] . $_SERVER["REQUEST_URI"];
	}
	return $pageURL;
}

class wcs_error_Walker_PageDropdownEx extends Walker
{
	// returns options list with pages; option value is URL (not page ID)
	var $db_fields = array ('parent' => 'post_parent', 'id' => 'ID');

	function start_el(&$output, $page, $depth, $args)
	{
		$pad = str_repeat('&nbsp;', $depth * 3);

		$output .= "\t<option class=\"level-$depth\" value=\"" . get_permalink($page->ID) . "\"";
		if ($page->ID == $args['selected'])
		{
			$output .= ' selected="selected"';
		}
	$output .= '>';
	$title = esc_html($page->post_title);
	$output .= "$pad$title";
	$output .= "</option>\n";
	}
}
function wcs_error_pulldown_pages()
{
	// init
	$walker = new wcs_error_Walker_PageDropdownEx();
	$args = array();
	$args['echo'] = '0';
	$args['walker'] = $walker;

	// build the pulldown menu
	$options_html = "\t<option>(select a page)</option>";
	$options_html .= wp_list_pages($args);
	$select_html = '<select name="page_url" onChange="document.location.href=this.options[this.selectedIndex].value;">';
	$select_html .= $options_html . '</select>';

	// exit
	return $select_html;
}
function wcs_error_pulldown_archives()
{
	// build the pulldown menu
	$options_html = "\t<option>(select a month)</option>";
	$args['echo'] = '0';
	$args['format'] = 'option';
	$args['type'] = 'monthly';
	$args['show_post_count'] = 'true';

	$wp_post_count = wp_count_posts();
	if ($wp_post_count->publish <= 1)
	{
		$select_html = '(none)';
	}
	else
	{
		$options_html .= wp_get_archives($args);
		$select_html = '<select name="archive_url" onChange="document.location.href=this.options[this.selectedIndex].value;">';
		$select_html .= $options_html . '</select>';
	}

	// exit
	return $select_html;
}
function wcs_error_pulldown_categories()
{
	// build the pulldown menu
	$options_html = "\t<option>(select a topic)</option>";
	$args['echo'] = '0';
	$args['show_count'] = '1';
	$args['hierarchical'] = '1';

	$wp_post_count = wp_count_posts();
	if ($wp_post_count->publish <= 1)
	{
		$select_html = '(none)';
	}
	else
	{
		$options_html .= wp_dropdown_categories($args);
		$options_html = str_replace("<select name='cat' id='cat' class='postform' >", '', $options_html);
		$options_html = str_replace('</select>', '', $options_html);

		$select_html = '<form method="get" action="' . get_bloginfo('url') . '">';
		$select_html .= '<select name="cat" id="cat" onChange="this.form.submit()">';
		$select_html .= $options_html . '</select></form>';
	}

	// exit
	return $select_html;
}
// -------- END -------------------------------------------------------

//////////////////////////////标题文字截断//////////////////////////////
function cut_str($src_str,$cut_length) {
    $return_str='';
    $i=0;
    $n=0;
    $str_length=strlen($src_str);
    while (($n<$cut_length) && ($i<=$str_length)) {
        $tmp_str=substr($src_str,$i,1);
        $ascnum=ord($tmp_str);
        if ($ascnum>=224) {
            $return_str=$return_str.substr($src_str,$i,3);
            $i=$i+3;
            $n=$n+2;
        }
        elseif ($ascnum>=192) {
            $return_str=$return_str.substr($src_str,$i,2);
            $i=$i+2;
            $n=$n+2;
        }
        elseif ($ascnum>=65 && $ascnum<=90) {
            $return_str=$return_str.substr($src_str,$i,1);
            $i=$i+1;
            $n=$n+2;
        }
        else {
            $return_str=$return_str.substr($src_str,$i,1);
            $i=$i+1;
            $n=$n+1;
        }
    }
    if ($i<$str_length) {
        $return_str = $return_str . '';
    }
    if (get_post_status() == 'private') {
        $return_str = $return_str . '（private）';
    }
    return $return_str;
}
// -------- END -------------------------------------------------------

//////////////////////////////版权年份自动化//////////////////////////////
function comicpress_copyright() {
    global $wpdb;
    $copyright_dates = $wpdb->get_results("
		SELECT
        YEAR(min(post_date_gmt)) AS firstdate,
        YEAR(max(post_date_gmt)) AS lastdate
        FROM
        $wpdb->posts
        WHERE
        post_status = 'publish'");
    $output = '';
    if($copyright_dates) {
        $copyright = "© " . $copyright_dates[0]->firstdate;
        if($copyright_dates[0]->firstdate != $copyright_dates[0]->lastdate) {
            $copyright .= '-' . $copyright_dates[0]->lastdate;
        }
        $output = $copyright;
    }
    return $output;
}
// -------- END -------------------------------------------------------

//////////////////////////////图片暗箱自动添加标签属性/////////////////
add_filter('the_content', 'pirobox_gall_replace');    
function pirobox_gall_replace ($content) {
	global $post;    
    $pattern = "/<a(.*?)href=('|\")([^>]*).(bmp|gif|jpeg|jpg|png)('|\")(.*?)>(.*?)<\/a>/i";    
    $replacement = '<a$1href=$2$3.$4$5 class="pirobox_gall"$6>$7</a>';    
    $content = preg_replace($pattern, $replacement, $content);    
    return $content;    
}
// -------- END -------------------------------------------------------

//////////////////////////////支持外链缩略图///////////////////////////
function get_featcat_image() {
  global $post, $posts;
  $first_img = '';
  ob_start();
  ob_end_clean();
  $output = preg_match_all('/<img.+src=[\'"]([^\'"]+)[\'"].*>/i', $post->post_content, $matches);
  $first_img = $matches [1] [0];

  if(empty($first_img)){ //Defines a default image
		$random = mt_rand(1, 10);
		echo get_bloginfo ( 'stylesheet_directory' );
		echo '/images/random/'.$random.'.jpg';
  }
  return $first_img;
}
// -------- END -------------------------------------------------------

//////////////////////////////彩色标签云///////////////////////////////
function colorCloud($text) {
    $text = preg_replace_callback('|<a (.+?)>|i', 'colorCloudCallback', $text);
    return $text;
}
function colorCloudCallback($matches) {
    $text = $matches[1];
    for($a=0;$a<6;$a++){    //采用#ffffff方法
       $color.=dechex(rand(1,15));//累加随机的数据--dechex()将十进制改为十六进制
    }
    $pattern = '/style=(\'|\")(.*)(\'|\")/i';
    $text = preg_replace($pattern, "style=\"color:#{$color};$2;\"", $text);
    return "</a><a $text>";
    unset($color);//卸载color
}
add_filter('wp_tag_cloud', 'colorCloud', 1);
// -------- END -------------------------------------------------------

///////////////////////历史上的今天（Today on history）/////////////////
function wp_today(){
	$limit = 10;
	$order = "latest";
 
	global $wpdb;
	if(is_single()){
	  $post_year = get_the_time('Y');
	  $post_month = get_the_time('m');
	  $post_day = get_the_time('j');
	}
	else{
	  $post_year = date('Y');
	  $post_month = date('m');
	  $post_day = date('j');
	}
	if($order == "latest"){ $order = "DESC";} else { $order = '';}
 
	$sql = "select ID, year(post_date_gmt) as h_year, post_title, comment_count FROM 
			$wpdb->posts WHERE post_password = '' AND post_type = 'post' AND post_status = 'publish'
			AND year(post_date_gmt)!='$post_year' AND month(post_date_gmt)='$post_month' AND day(post_date_gmt)='$post_day'
			order by post_date_gmt $order limit $limit";
	$histtory_post = $wpdb->get_results($sql);
	if( $histtory_post ){
		foreach( $histtory_post as $post ){
			$h_year = $post->h_year;
			$h_post_title = $post->post_title;
			$h_permalink = get_permalink( $post->ID );
			$h_comments = $post->comment_count;
			$h_post .= "<li>$h_year:&nbsp;&nbsp;<a href='".$h_permalink."' title='Permanent Link to ".$h_post_title."' rel='external nofollow'>$h_post_title($h_comments)</a></li>";
		}
	}
	if ( $h_post ){
		$result = "<ul><h3>历史上的今天:</h3>".$h_post."</ul>";
	}
	return $result;
	wp_reset_query();
}
// -------- END -------------------------------------------------------

//////代码实现WordPress归档页面模板 http://zww.me/archives/25209；/////
function archives_list_SHe() {
     global $wpdb,$month;
     $lastpost = $wpdb->get_var("SELECT ID FROM $wpdb->posts WHERE post_date <'" . current_time('mysql') . "' AND post_status='publish' AND post_type='post' AND post_password='' ORDER BY post_date DESC LIMIT 1");
     $output = get_option('SHe_archives_'.$lastpost);
     if(empty($output)){
         $output = '';
         $wpdb->query("DELETE FROM $wpdb->options WHERE option_name LIKE 'SHe_archives_%'");
         $q = "SELECT DISTINCT YEAR(post_date) AS year, MONTH(post_date) AS month, count(ID) as posts FROM $wpdb->posts p WHERE post_date <'" . current_time('mysql') . "' AND post_status='publish' AND post_type='post' AND post_password='' GROUP BY YEAR(post_date), MONTH(post_date) ORDER BY post_date DESC";
         $monthresults = $wpdb->get_results($q);
         if ($monthresults) {
             foreach ($monthresults as $monthresult) {
             $thismonth    = zeroise($monthresult->month, 2);
             $thisyear    = $monthresult->year;
             $q = "SELECT ID, post_date, post_title, comment_count FROM $wpdb->posts p WHERE post_date LIKE '$thisyear-$thismonth-%' AND post_date AND post_status='publish' AND post_type='post' AND post_password='' ORDER BY post_date DESC";
             $postresults = $wpdb->get_results($q);
             if ($postresults) {
                 $text = sprintf('%s %d', $month[zeroise($monthresult->month,2)], $monthresult->year);
                 $postcount = count($postresults);
                 $output .= '<ul class="archives-list"><li><span class="archives-yearmonth">' . $text . ' &nbsp;(' . count($postresults) . '&nbsp;篇文章)</span><ul class="archives-monthlisting">' . "\n";
             foreach ($postresults as $postresult) {
                 if ($postresult->post_date != '0000-00-00 00:00:00') {
                 $url = get_permalink($postresult->ID);
                 $arc_title    = $postresult->post_title;
                 if ($arc_title)
                     $text = wptexturize(strip_tags($arc_title));
                 else
                     $text = $postresult->ID;
                     $title_text = 'View this post, &quot;' . wp_specialchars($text, 1) . '&quot;';
                     $output .= '<li>' . mysql2date('d日', $postresult->post_date) . ':&nbsp;' . "<a href='$url' title='$title_text'>$text</a>";
                     $output .= '&nbsp;(' . $postresult->comment_count . ')';
                     $output .= '</li>' . "\n";
                 }
                 }
             }
             $output .= '</ul></li></ul>' . "\n";
             }
         update_option('SHe_archives_'.$lastpost,$output);
         }else{
             $output = '<div class="errorbox">Sorry, no posts matched your criteria.</div>' . "\n";
         }
     }
     echo $output;
 }
// -------- END -------------------------------------------------------

////////////////友情连结页面设置+显示网站的Favicon/////////////////////
function my_bookmarks($bookmarks, $args = '' ) {
	$defaults = array(
		'show_updated' => 0, 'show_description' => 0,
		'show_images' => 1, 'show_name' => 0,
		'before' => '
<li>', 'after' => '</li>', 'between' => "\n",
		'show_rating' => 0, 'link_before' => '', 'link_after' => '','nofollow' =>0
	);
	$r = wp_parse_args( $args, $defaults );
	extract( $r, EXTR_SKIP );
	$output = ''; // Blank string to start with.
	foreach ( (array) $bookmarks as $bookmark ) {
		if ( !isset($bookmark->recently_updated) )
			$bookmark->recently_updated = false;
		$output .= $before;
		if ( $show_updated && $bookmark->recently_updated )
			$output .= get_option('links_recently_updated_prepend');
		$the_link = '#';
		if ( !empty($bookmark->link_url) )
			$the_link = clean_url($bookmark->link_url);
		$rel = ' rel="external';
		if ($nofollow)
			$rel .= ' nofollow';
		if ( '' != $bookmark->link_rel )
			$rel .= ' ' . $bookmark->link_rel;
		$rel .= '"';
		$desc = attribute_escape(sanitize_bookmark_field('link_description', $bookmark->link_description, $bookmark->link_id, 'display'));
		$name = attribute_escape(sanitize_bookmark_field('link_name', $bookmark->link_name, $bookmark->link_id, 'display'));
 		$title = $desc;
		if ( $show_updated )
			if ( '00' != substr($bookmark->link_updated_f, 0, 2) ) {
				$title .= ' (';
				$title .= sprintf(__('Last updated: %s'), date(get_option('links_updated_date_format'), $bookmark->link_updated_f + (get_option('gmt_offset') * 3600)));
				$title .= ')';
			}
		if ( '' != $title )
			$title = ' title="' . $title . '"';
		$alt = ' alt="' . $name . '"';
		$target = $bookmark->link_target;
		if ( '' != $target )
			$target = ' target="' . $target . '"';
		$output .= '<a href="' . $the_link . '"' . $rel . $title . $target. '>';
		$output .= $link_before;
		if ( $show_images ) {
			if ( $bookmark->link_image != null) {
				if ( strpos($bookmark->link_image, 'http') !== false )
					$output .= "<img src=\"$bookmark->link_image\" $alt $title />";
				else // If it's a relative path
					$output .= "<img src=\"" . get_option('siteurl') . "$bookmark->link_image\" $alt $title />";
			} else {//否则显示网站的Favicon
				if (preg_match('/^(https?:\/\/)?([^\/]+)/i',$the_link,$URI)) {//提取域名
					$domains = $URI[2];
				}else{//域名提取失败，显示默认小地球
					$domains = "example.com";
				}
				$output .= "<img src=\"http://www.google.com/s2/favicons?domain=$domains\" $alt $title />";
			}
		}
		$output .= $name;
		$output .= $link_after;
		$output .= '</a>';
		if ( $show_updated && $bookmark->recently_updated )
			$output .= get_option('links_recently_updated_append');
 
		if ( $show_description && '' != $desc )
			$output .= $between . $desc;
		if ($show_rating) {
			$output .= $between . sanitize_bookmark_field('link_rating', $bookmark->link_rating, $bookmark->link_id, 'display');
		}
		$output .= "$after\n";
	} // end while
	return $output;
}
function my_list_bookmarks($args = '') {
	$defaults = array(
		'orderby' => 'name', 'order' => 'ASC',
		'limit' => -1, 'category' => '', 'exclude_category' => '',
		'category_name' => '', 'hide_invisible' => 1,
		'show_updated' => 0, 'echo' => 1,
		'categorize' => 1, 'title_li' => __('Bookmarks'),
		'title_before' => '
<h2>', 'title_after' => '</h2>',
		'category_orderby' => 'name', 'category_order' => 'ASC',
		'class' => 'linkcat', 'category_before' => '
<li id="%id" class="%class">',
		'category_after' => '</li>','nofollow' => 0
	);
	$r = wp_parse_args( $args, $defaults );
	extract( $r, EXTR_SKIP );
	$output = '';
	if ( $categorize ) {
		//Split the bookmarks into ul's for each category
		$cats = get_terms('link_category', array('name__like' => $category_name, 'include' => $category, 'exclude' => $exclude_category, 'orderby' => $category_orderby, 'order' => $category_order, 'hierarchical' => 0));
		foreach ( (array) $cats as $cat ) {
			$params = array_merge($r, array('category'=>$cat->term_id));
			$bookmarks = get_bookmarks($params);
			if ( empty($bookmarks) )
				continue;
			$output .= str_replace(array('%id', '%class'), array("linkcat-$cat->term_id", $class), $category_before);
			$catname = apply_filters( "link_category", $cat->name );
			$output .= "$title_before$catname$title_after\n\t
<ul class='xoxo blogroll'>\n";
			$output .= my_bookmarks($bookmarks, $r);
			$output .= "\n\t</ul>\n$category_after\n";
		}
	} else {
		//output one single list using title_li for the title
		$bookmarks = get_bookmarks($r);
		if ( !empty($bookmarks) ) {
			if ( !empty( $title_li ) ){
				$output .= str_replace(array('%id', '%class'), array("linkcat-$category", $class), $category_before);
				$output .= "$title_before$title_li$title_after\n\t
<ul class='xoxo blogroll'>\n";
				$output .= my_bookmarks($bookmarks, $r);
				$output .= "\n\t</ul>\n$category_after\n";
			} else {
				$output .= my_bookmarks($bookmarks, $r);
			}
		}
	}
	$output = apply_filters( 'wp_list_bookmarks', $output );
	if ( !$echo )
		return $output;
	echo $output;
}
// -------- END -------------------------------------------------------

///////////////////////////////////////////////////////////////////////
/////////////////* Mini Pagenavi v1.0 by Willin Kan. */////////////////
if ( !function_exists('pagenavi') ) {
	function pagenavi( $p = 7 ) { // 取当前页前后各 2 页，根据需要改
		if ( is_singular() ) return; // 文章与插页不用
		global $wp_query, $paged;
		$max_page = $wp_query->max_num_pages;
		if ( $max_page == 1 ) return; // 只有一页不用
		if ( empty( $paged ) ) $paged = 1;
		echo '<span class="pages">页数:' . $paged . '/' . $max_page . '</span>'; // 显示页数
		if ( $paged > $p + 1 ) p_link( 1, '最前页' );
		if ( $paged > $p + 2 ) echo '... ';
		for( $i = $paged - $p; $i <= $paged + $p; $i++ ) { // 中间页
			if ( $i > 0 && $i <= $max_page ) $i == $paged ? print "<span class='page-numbers current'>{$i}</span> " : p_link( $i );
		}
		if ( $paged < $max_page - $p - 1 ) echo '... ';
		if ( $paged < $max_page - $p ) p_link( $max_page, '最后页' );
	}
	function p_link( $i, $title = '' ) {
		if ( $title == '' ) $title = "第 {$i} 页";
		echo "<a href='", esc_html( get_pagenum_link( $i ) ), "' title='{$title}'>{$i}</a> ";
	}
}
// -------- END -------------------------------------------------------

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

//////////相关文章代码，来源：http://kan.willin.org/?p=1318////////////
function related_posts() {
$post_num = 12; // 數量設定.
global $post;
$tmp_post = $post;
$tags = ''; $i = 0; // 先取 tags 文章.
$exclude_id = $post->ID;
$posttags = get_the_tags();
if ( $posttags ) {
  foreach ( $posttags as $tag ) $tags .= $tag->name . ',';
  $tags = strtr(rtrim($tags, ','), ' ', '-');
  $myposts = get_posts('numberposts='.$post_num.'&tag='.$tags.'&exclude='.$exclude_id);
  foreach($myposts as $post) {
    setup_postdata($post);
    ?>
    <li><a href="<?php the_permalink(); ?>"><?php echo cut_str($post->post_title,44); ?></a></li>
    <?php
    $exclude_id .= ','.$post->ID; $i ++;
  }
}
if ( $i < $post_num ) { // 當 tags 文章數量不足, 再取 category 補足.
  $post = $tmp_post; setup_postdata($post);
  $cats = ''; $post_num -= $i;
  foreach ( get_the_category() as $cat ) $cats .= $cat->cat_ID . ',';
  $cats = strtr(rtrim($cats, ','), ' ', '-');
  $myposts = get_posts('numberposts='.$post_num.'&category='.$cats.'&exclude='.$exclude_id);
  foreach($myposts as $post) {
    setup_postdata($post);
    ?>
    <li><a href="<?php the_permalink(); ?>"><?php echo cut_str($post->post_title,44); ?></a></li>

    <?php
    $i ++;
  }
}
if ( $i == 0 ) echo '<li>暂无相关文章</li>';
$post = $tmp_post; setup_postdata($post);
}
// -------- END -------------------------------------------------------

//////////////Recently Updated Posts by zwwooooo | zww.me//////////////
function recently_updated_posts($num=10,$days=70) {
   if( !$recently_updated_posts = get_option('recently_updated_posts') ) {
       query_posts('post_status=publish&orderby=modified&posts_per_page=-1');
       $i=0;
       while ( have_posts() && $i<$num ) : the_post();
           if (current_time('timestamp') - get_the_time('U') > 60*60*24*$days) {
               $i++;
               $the_title_value=get_the_title();
               $recently_updated_posts.='<li><a href="'.get_permalink().'" title="'.$the_title_value.'">'.cut_str($the_title_value,44).'</a></li>';
           }
       endwhile;
       wp_reset_query();
       if ( !empty($recently_updated_posts) ) update_option('recently_updated_posts', $recently_updated_posts);
   }
   $recently_updated_posts=($recently_updated_posts == '') ? '<li>None data.</li>' : $recently_updated_posts;
   echo $recently_updated_posts;
}
function clear_cache_zww() {
    update_option('recently_updated_posts', ''); // 清空 recently_updated_posts
}
add_action('save_post', 'clear_cache_zww'); // 新发表文章/修改文章时触发更新
// -------- END -------------------------------------------------------

///////////////////////////////////////////////////////////////////////
////////////////      评论部分      ///////////////////////////////////

//自定义头像
add_filter( 'avatar_defaults', 'fb_addgravatar' );
function fb_addgravatar( $avatar_defaults ) {
$myavatar = get_bloginfo('template_directory') . '/images/gravatar.png';
  $avatar_defaults[$myavatar] = '自定义头像';
  return $avatar_defaults;
}

//评论贴图
function embed_images($content) {
$content = preg_replace('/\[img=?\]*(.*?)(\[\/img)?\]/e', '"<img src=\"$1\" alt=\"" . basename("$1") . "\" />"', $content);
return $content;
}
add_filter('comment_text', 'embed_images');

//留言信息
function WelcomeCommentAuthorBack($email = ''){
	if(empty($email)){
		return;
	}
	global $wpdb;

	$past_30days = gmdate('Y-m-d H:i:s',((time()-(24*60*60*30))+(get_option('gmt_offset')*3600)));
	$sql = "SELECT count(comment_author_email) AS times FROM $wpdb->comments
					WHERE comment_approved = '1'
					AND comment_author_email = '$email'
					AND comment_date >= '$past_30days'";
	$times = $wpdb->get_results($sql);
	$times = ($times[0]->times) ? $times[0]->times : 0;
	$message = $times ? sprintf(__('过去30天内您有<strong>%1$s</strong>条留言，感谢关注!' ), $times) : '您已很久都没有留言了，这次想说点什么？';

	return $message;
}


// 判断管理员
function is_admin_comment( $comment_ID = 0 ) {
$comment = get_comment( $comment_ID );
$admin_comment = false; //设置一个布尔类型的变量用于判断该留言的ID是否为管理员的留言
if($comment->user_id == 1){
$admin_comment = true;
}
return $admin_comment;
}

// 评论回复
function forigi_comment($comment, $args, $depth) {
	$GLOBALS['comment'] = $comment;
//主评论计数器初始化 begin - by zwwooooo http://zww.me/archives/25161
	global $commentcount;
	if(!$commentcount) { //初始化楼层计数器
		$page = get_query_var('cpage')-1;
		$cpp=get_option('comments_per_page');//获取每页评论数
		$commentcount = $cpp * $page;
	}
//主评论计数器初始化 end - by zwwooooo
?>
	<li <?php comment_class(); ?> id="comment-<?php comment_ID() ?>">
	<div id="comment-<?php comment_ID(); ?>">
		<div class="comment-meta commentmetadata">
			<?php printf(__('<cite class="fn">%s</cite>&nbsp;&nbsp在'), get_comment_author_link()) ?>
		
			<a href="<?php echo htmlspecialchars( get_comment_link( $comment->comment_ID ) ) ?>"><?php printf(__('%1$s at %2$s'), get_comment_date(),  get_comment_time()) ?></a>
			<?php edit_comment_link(__('(Edit)'),'  ','') ?><?php/* useragent_output_custom(); */?><!--如要显示UA信息，请先移步http://www.qintag.com/fatal-error-functions-php-on-line-574.html  -->
			 <span class="says">说道:</span>
			
		</div>
		<div class="comment-body"><?php comment_text(); ?></div>
		<?php if ($comment->comment_approved == '0') : ?>
			<font color=#a40000><?php _e('您的评论需要管理员审核...') ?></font>
		<?php endif; ?>
		<div class="reply">
			<?php comment_reply_link(array_merge( $args, array('depth' => $depth, 'max_depth' => $args['max_depth']))) ?>
		</div>
		<div class="floor"><!-- 主评论楼层号 by zwwooooo -->
			<?php if(!$parent_id = $comment->comment_parent) {printf('#%1$s楼', ++$commentcount);} ?><!-- 当前页每个主评论自动+1 -->
		</div>
	</div>
<?php
}
// -------- END -------------------------------------------------------

//////////////////////// Comment And Ping Setup ///////////////////////
function list_pings($comment, $args, $depth) {
	$GLOBALS['comment'] = $comment; ?>
	<li id="comment-<?php comment_ID(); ?>"><?php comment_author_link(); ?>
	<?php }
add_filter('get_comments_number', 'comment_count', 0);
function comment_count( $count ) {
	global $id;
	$comments_by_type = &separate_comments(get_comments('post_id=' . $id));
	return count($comments_by_type['comment']);
}
// -------- END -------------------------------------------------------
/////////////// Comment and pingback separate controls ////////////////
$bm_trackbacks = array();
$bm_comments = array();
function split_comments( $source ) {
	if ( $source ) foreach ( $source as $comment ) {
		global $bm_trackbacks;
		global $bm_comments;
			if ( $comment->comment_type == 'trackback' || $comment->comment_type == 'pingback' ) {
				$bm_trackbacks[] = $comment;
			} else {
			$bm_comments[] = $comment;
			}
		}
	}
// -------- END -------------------------------------------------------


///////////////////////// 评论回应邮件通知 ////////////////////////////

function comment_mail_notify($comment_id) {
  $admin_notify = '1'; // admin 要不要收回复通知 ( '1'=要 ; '0'=不要 )
  $admin_email = get_bloginfo ('admin_email'); // $admin_email 可改为你指定的 e-mail.
  $comment = get_comment($comment_id);
  $comment_author_email = trim($comment->comment_author_email);
  $parent_id = $comment->comment_parent ? $comment->comment_parent : '';
  global $wpdb;
  if ($wpdb->query("Describe {$wpdb->comments} comment_mail_notify") == '')
    $wpdb->query("ALTER TABLE {$wpdb->comments} ADD COLUMN comment_mail_notify TINYINT NOT NULL DEFAULT 0;");
  if (($comment_author_email != $admin_email && isset($_POST['comment_mail_notify'])) || ($comment_author_email == $admin_email && $admin_notify == '1'))
    $wpdb->query("UPDATE {$wpdb->comments} SET comment_mail_notify='1' WHERE comment_ID='$comment_id'");
  $notify = $parent_id ? get_comment($parent_id)->comment_mail_notify : '0';
  $spam_confirmed = $comment->comment_approved;
  if ($parent_id != '' && $spam_confirmed != 'spam' && $notify == '1') {
    $wp_email = 'no-reply@' . preg_replace('#^www\.#', '', strtolower($_SERVER['SERVER_NAME'])); // e-mail 发出点, no-reply 可改为可用的 e-mail.
    $to = trim(get_comment($parent_id)->comment_author_email);
    $subject = '您在 [' . get_option("blogname") . '] 的留言有了回应';
    $message = '
    <div style="background-color:#eef2fa; border:1px solid #d8e3e8; color:#111; padding:0 15px; -moz-border-radius:5px; -webkit-border-radius:5px; -khtml-border-radius:5px;">
      <p>' . trim(get_comment($parent_id)->comment_author) . ', 您好!</p>
      <p>您曾在《' . get_the_title($comment->comment_post_ID) . '》的留言:<br />'
       . trim(get_comment($parent_id)->comment_content) . '</p>
      <p>' . trim($comment->comment_author) . ' 给您的回应:<br />'
       . trim($comment->comment_content) . '<br /></p>
      <p>您可以点击 <a href="' . htmlspecialchars(get_comment_link($parent_id)) . '">查看回应完整內容</a></p>
      <p>欢迎您再度光临 <a href="' . get_option('home') . '">' . get_option('blogname') . '</a></p>
      <p>(此邮件由系统自动发出，请勿回复.)</p>
    </div>';
    $from = "From: \"" . get_option('blogname') . "\" <$wp_email>";
    $headers = "$from\nContent-Type: text/html; charset=" . get_option('blog_charset') . "\n";
    wp_mail( $to, $subject, $message, $headers );
    //echo 'mail to ', $to, '<br/> ' , $subject, $message; // for testing
  }
}
add_action('comment_post', 'comment_mail_notify');

// 自动勾选 
function add_checkbox() {
  echo '<input type="checkbox" name="comment_mail_notify" id="comment_mail_notify" value="comment_mail_notify" checked="checked" class="notify_checked" /><label for="comment_mail_notify" class="notify_p">有人回复时邮件通知我</label>';
}
add_action('comment_form', 'add_checkbox');



// -------- END -------------------------------------------------------

/* <<小牆>> Anti-Spam v1.84 by Willin Kan. */
class anti_spam {
  function anti_spam() {
    if ( !current_user_can('read') ) {
      add_action('template_redirect', array($this, 'w_tb'), 1);
      add_action('init', array($this, 'gate'), 1);
      add_action('preprocess_comment', array($this, 'sink'), 1);
    }
  }
  // 設欄位
  function w_tb() {
    if ( is_singular() ) {
      // 非中文語系
      if ( stripos($_SERVER['HTTP_ACCEPT_LANGUAGE'], 'zh') === false ) {
        add_filter( 'comments_open', create_function('', "return false;") ); // 關閉評論
      } else {
        ob_start(create_function('$input','return preg_replace("#textarea(.*?)name=([\"\'])comment([\"\'])(.+)/textarea>#",
        "textarea$1name=$2w$3$4/textarea><textarea name=\"comment\" cols=\"100%\" rows=\"4\" style=\"display:none\"></textarea>",$input);') );
      }
    }
  }
  // 檢查
  function gate() {
    $w = 'w';
    if ( !empty($_POST[$w]) && empty($_POST['comment']) ) {
      $_POST['comment'] = $_POST[$w];
    } else {
      $request = $_SERVER['REQUEST_URI'];
      $way     = isset($_POST[$w]) ? '手動操作' : '未經評論表格';
      $spamcom = isset($_POST['comment']) ? $_POST['comment'] : '';
      $_POST['spam_confirmed'] = "請求: ". $request. "\n方式: ". $way. "\n內容: ". $spamcom. "\n -- 記錄成功 --";
    }
  }
  // 處理
  function sink( $comment ) {
    // 不管 Trackbacks/Pingbacks
    if ( in_array( $comment['comment_type'], array('pingback', 'trackback') ) ) return $comment;

    // 已確定為 spam
    if ( !empty($_POST['spam_confirmed']) ) {
      // 方法一: 直接擋掉, 將 die(); 前面兩斜線刪除即可.
      //die();
      // 方法二: 標記為 spam, 留在資料庫檢查是否誤判.
      add_filter('pre_comment_approved', create_function('', 'return "spam";'));
      $comment['comment_content'] = "[ 小牆判斷這是Spam! ]\n". $_POST['spam_confirmed'];
      $this->add_black( $comment );
    } else {
      // 檢查頭像
      $f = md5( strtolower($comment['comment_author_email']) );
      $g = sprintf( "http://%d.gravatar.com", (hexdec($f{0}) % 2) ) .'/avatar/'. $f .'?d=404';
      $headers = @get_headers( $g );
      if ( !preg_match("|200|", $headers[0]) ) {
        // 沒頭像的列入待審
        add_filter('pre_comment_approved', create_function('', 'return "0";'));
        //$this->add_black( $comment );
        }
    }
    return $comment;
  }
  // 列入黑名單
  function add_black( $comment ) {
    if (!($comment_author_url = $comment['comment_author_url'])) return;
    if (strpos($comment_author_url, '//')) $comment_author_url = substr($comment_author_url, strpos($comment_author_url, '//') + 2);
    if (strpos($comment_author_url, '/'))  $comment_author_url = substr($comment_author_url, 0, strpos($comment_author_url, '/'));
    update_option('blacklist_keys', $comment_author_url . "\n" . get_option('blacklist_keys'));
  }
}
$anti_spam = new anti_spam();
// -- END ----------------------------------------

//////////////////////////////3.0菜单支持//////////////////////////////
if ( function_exists( 'add_theme_support' ) ) { // Added in 2.9
	add_custom_background(); //添加背景功能支持
	add_theme_support( 'post-thumbnails' ); //添加缩略图功能支持
	set_post_thumbnail_size( 130, 130, true ); // Normal post thumbnails
    // This theme uses wp_nav_menu() in one location.
	register_nav_menus( array(
	'primary' => __( '页眉导航' ),
	'secondary' => __( '主导航' ),
	'footernav' => __( '页脚导航' ),
	) );
    add_theme_support( 'menus' ); // new nav menus for wp 3.0
}

function revert_wp_menu_page() { //revert back to normal if in wp 3.0 and menu not set ?>
<ul id="topnav">
<li id="<?php if (is_home()) { ?>home<?php } else { ?>page_item<?php } ?>"><a href="<?php bloginfo('url'); ?>" title="Home">Home</a></li>
<?php wp_list_pages('title_li=&depth=0&orderby=name'); ?>
</ul>
<?php }
function revert_wp_menu_category() { //revert back to normal if in wp 3.0 and menu not set ?>
<ul id="topnav2">
<li id="<?php if (is_home()) { ?>home<?php } else { ?>page_item<?php } ?>"><a href="<?php bloginfo('url'); ?>" title="首页">首页</a></li>
<?php wp_list_categories('title_li=&orderby=name&number=7'); ?>
</ul>
<?php }

// Filter wp_nav_menu() to add additional links and other output
function addHomeMenuLink($menuItems, $args)
{
	if('primary' == $args->theme_location)
	{
		if ( is_home() || is_front_page() )
			$class = 'id="home"';
		else
			$class = 'id="home"';	
		$homeMenuItem = '<li ' . $class . '>' .
						$args->before .
						'<a href="' . home_url( '/' ) . '" title="Home">' .
							$args->link_before .
							'Home' .
							$args->link_after .
						'</a>' .
						$args->after .
						'</li>';
		$menuItems = $homeMenuItem . $menuItems;
	}
	return $menuItems;
}
add_filter( 'wp_nav_menu_items', 'addHomeMenuLink', 10, 2 );
// -------- END -------------------------------------------------------

///////////////////////////////////////////////////////////////////////
////////////////////////////// 小工具 /////////////////////////////////
if ( function_exists('register_sidebar') ) {

	register_sidebar(array(
	'name'=>'主工具条',
	'before_widget' => '<li id="%1$s" class="widget %2$s">',
	'after_widget' => '</li>',
	'before_title' => '<h3>',
	'after_title' => '</h3>',
	));

	register_sidebar(array(
	'name'=>'左下侧边栏',
	'before_widget' => '<li id="%1$s" class="widget %2$s">',
	'after_widget' => '</li>',
	'before_title' => '<h3>',
	'after_title' => '</h3>',
	));
	
	register_sidebar(array(
	'name'=>'右下侧边栏',
	'before_widget' => '<li id="%1$s" class="widget %2$s">',
	'after_widget' => '</li>',
	'before_title' => '<h3>',
	'after_title' => '</h3>',
	));

}
// -------- END -------------------------------------------------------

////////////////////////////// 我的微博秀//////////////////////////////
function widget_qintag_miniblog() { ?>
<?php $miniblog_code = get_qintag_option('miniblog_code'); if($miniblog_code == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<li>
	<?php echo stripcslashes($miniblog_code); ?>
</li>
<?php } ?>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget(__('我的微博秀'), 'widget_qintag_miniblog');
// -------- END -------------------------------------------------------

////////////////////////////// 160*600摩天柱广告///////////////////////
function widget_qintag_ad160_600() { ?>
<?php $ad160_600 = get_qintag_option('ad160_600'); if($ad160_600 == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<li>
	<h3>广告</h3>
	<ul class="sidebarbox">
		<?php echo get_qintag_option('ad160_600'); ?>
	</ul>
</li>
<?php } ?>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget(__('160_600摩天柱广告'), 'widget_qintag_ad160_600');
// -------- END -------------------------------------------------------

//////////////////////////////300*250Sidebar_advertisement/////////////
function widget_qintag_ad300_250() { ?>
<?php $ad300_250 = get_qintag_option('ad300_250'); if($ad300_250 == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<li>
	<h3>赞助商链接</h3>
	<ul class="sidebarbox">
		<?php echo get_qintag_option('ad300_250'); ?>
	</ul>
</li>
<?php } ?>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget(__('300*250Sidebar广告'), 'widget_qintag_ad300_250');
// -------- END -------------------------------------------------------

////////////////////// 135x135赞助商广告////////////////////
function widget_qintag_sponsors() { ?>
<?php $sponsor_activate = get_qintag_option('sponsor_activate'); if($sponsor_activate == 'No') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<?php include (TEMPLATEPATH . '/includes/sponsor.php'); ?>
<?php } ?>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget(__('135x135赞助商广告'), 'widget_qintag_sponsors');
// -------- end -------------------------------------------------------
//////////////////////////////网站信息/////////////////////////////////
function forigi_qintag_statistics() { ?>
<li>
<h3><?php _e('网站信息'); ?></h3>
<ul>
<li>日志数：<?php global $wpdb; $count_posts = wp_count_posts(); echo $published_posts = $count_posts->publish;?> 篇</li>
<li>评论数：<?php echo $wpdb->get_var("SELECT COUNT(*) FROM $wpdb->comments");?> 条</li>
<li>标签数：<?php echo $count_tags = wp_count_terms('post_tag'); ?> 个</li>
<li>页面数：<?php $count_pages = wp_count_posts('page'); echo $page_posts = $count_pages->publish; ?> 个</li>
<li>链接数：<?php $link = $wpdb->get_var("SELECT COUNT(*) FROM $wpdb->links WHERE link_visible = 'Y'"); echo $link; ?> 条</li>
<li>用户数：<?php $users = $wpdb->get_var("SELECT COUNT(ID) FROM $wpdb->users"); echo $users; ?> 位</li>
<li>共运行：<?php echo floor((time()-strtotime("2007-09-18"))/86400); ?> 天</li>
<li>建站日:2007.9.18</li><!-- 这个地方请根据自己的建站时间修改-->
<li>更新日:<?php $last = $wpdb->get_results("SELECT MAX(post_modified) AS MAX_m FROM $wpdb->posts WHERE (post_type = 'post' OR post_type = 'page') AND (post_status = 'publish' OR post_status = 'private')");$last = date('Y.n.j', strtotime($last[0]->MAX_m));echo $last; ?></li>
<?php wp_register(); ?>
<li><?php wp_loginout(); ?></li>
<?php wp_meta(); ?>
</ul>
</li>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget('网站信息', 'forigi_qintag_statistics');
// -------- END -------------------------------------------------------

///////////////////////Get Recent Comments With Avatar/////////////////
function get_gravatar_recent_comment() {
global $wpdb;
$sql = "SELECT DISTINCT ID, post_title, post_password, comment_ID,
comment_post_ID, comment_author, comment_author_email, comment_date_gmt, comment_approved,
comment_type,comment_author_url,
SUBSTRING(comment_content,1,50) AS com_excerpt
FROM $wpdb->comments
LEFT OUTER JOIN $wpdb->posts ON ($wpdb->comments.comment_post_ID =
$wpdb->posts.ID)
WHERE comment_approved = '1' AND comment_type = '' AND
post_password = ''
ORDER BY comment_date_gmt DESC LIMIT 6";

$comments = $wpdb->get_results($sql);
$output = $pre_HTML;
$gravatar_status = 'on'; /* off if not using */

foreach ($comments as $comment) {
$email = $comment->comment_author_email;
$grav_name = $comment->comment_author;
$grav_url = "http://www.gravatar.com/avatar.php?gravatar_id=".md5($email). "&amp;size=32"; ?>
<?php if($gravatar_status == 'on') { ?>
<div>
<img src="<?php echo $grav_url; ?>" alt="<?php echo $grav_name; ?>" class="alignleft" /><?php } ?>
<span class="author"><?php echo strip_tags($comment->comment_author); ?></span>&nbsp;说道:<br />
<span class="comment"><a href="<?php echo get_permalink($comment->ID); ?>#comment-<?php echo $comment->comment_ID; ?>" title="on <?php echo $comment->post_title; ?>">
<?php echo cut_str($comment->com_excerpt,36); ?>...</a></span>
</div>
<?php
}
}
///Custom Recent Comments With Gravatar Widget///
function forigi_qintag_gravatar() { ?>
<li class="comments_gravatar">
	<h3><span><a href="<?php bloginfo('url'); ?>/guestbook" target="_blank" title="留言板">留言板&gt;&gt;</a></span><?php _e('最新评论'); ?></h3>
	<ul class="box_comment_gravatar">
		<?php get_gravatar_recent_comment(); ?>
	</ul>
</li>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget('最新评论（Gravatar）', 'forigi_qintag_gravatar');
// -------- END -------------------------------------------------------

////////////////////////////////////评论墙/////////////////////////////
function give_force_recent_comment() {
global $wpdb;
$counts = $wpdb->get_results("SELECT COUNT(comment_author) AS cnt, comment_author, comment_author_url, comment_author_email FROM (SELECT * FROM $wpdb->comments LEFT OUTER JOIN $wpdb->posts ON ($wpdb->posts.ID=$wpdb->comments.comment_post_ID) WHERE comment_date > date_sub( NOW(), INTERVAL 1 MONTH ) AND user_id='0' AND comment_author != 'qintag' AND post_password='' AND comment_approved='1' AND comment_type='') AS tempcmt GROUP BY comment_author ORDER BY cnt DESC LIMIT 12");
foreach ($counts as $count) {
					$c_url = $count->comment_author_url;
					if ($c_url == '') $c_url = 'http://www.qintag.com/';
					$mostactive .= '<li>' . '<a href="'. $c_url . '" title="' . $count->comment_author . ' ('. $count->cnt . 'comments)">' . get_avatar($count->comment_author_email, 32) . '</a></li>';
				}
echo $mostactive;
}

///Most give force readers With Gravatar Widget///
function forigi_qintag_give_force_recent() { ?>
<li>
<h3><?php _e('最给力的读者'); ?></h3>
	<ul class="box_comment">
		<?php give_force_recent_comment(); ?>
		<div class="clearfix"></div>
	</ul>
</li>
<?php }
if ( function_exists('register_sidebar_widget') ) register_sidebar_widget('最给力的读者','forigi_qintag_give_force_recent');

// -------- END -------------------------------------------------------

///////////////////////////主题设置////////////////////////////////////
$themename = "Forigi";
$shortname = str_replace(' ', '_', strtolower($themename));

function get_qintag_option($option)
{
	global $shortname;
	return stripslashes(get_option($shortname . '_' . $option));
}

function get_theme_settings($option)
{
	return stripslashes(get_option($option));
}



$options = array (
array( "name" => $themename." Options", "type" => "title"),

array( "name" => "页眉部分", "type" => "section"),
array( "type" => "open"),

array( "name" => "博客LOGO",
	"desc" => "自定义LOGO",
	"id" => $shortname."_header_logo_activate",
	"type" => "select",
	"options" => array("No", "Yes"),
	"std" => "No"),
	
array( "name" => "LOGO URL",
	"desc" => "输入LOGO的URL地址，例如：http://yourdomain.com/wp-content/themes/forigi/images/logo.png",
	"id" => $shortname."_logo_url",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "首页关键词",
	"desc" => "网站首页关键字，例如：秦唐网,小马PE,胖子马,mapeimapei,qintag",
	"id" => $shortname."_indexkeyword",
	"type" => "textarea",
	"std" => ""),

array( "name" => "网站描述",
	"desc" => "例如：秦唐网，小马PE，专注于网站建设、网络营销和电脑技术的研究与分享",
	"id" => $shortname."_indexdescription",
	"type" => "textarea",
	"std" => ""),

array( "name" => "页眉广告位",
	"desc" => "输入您申请的广告代码，推荐尺寸468×60",
	"id" => $shortname."_header_banner",
	"type" => "textarea",
	"std" => ""),

array( "type" => "close"),

//////////////////////////////////////////////////////////////////////////////
array( "name" => "微博、加入收藏以及订阅设置", "type" => "section"),
array( "type" => "open"),

array( "name" => "控制是否显示feed订阅图标",
	"desc" => "默认禁用",
	"id" => $shortname."_rss2_url_activate",
	"type" => "select",
	"options" => array("No", "Yes"),
	"std" => "No"),
	
array( "name" => "输入feed地址",
	"desc" => "输入feed地址,可以是rss2_url地址也可以是您申请的第三方feed地址,",
	"id" => $shortname."_rss2_url_feed",
	"type" => "textarea",
	"std" => "http://feed.qintag.com/"),
	
array( "name" => "控制是否显示腾讯微博",
	"desc" => "默认禁用",
	"id" => $shortname."_tqq_activate",
	"type" => "select",
	"options" => array("No", "YES"),
	"std" => "No"),

array( "name" => "输入腾讯微博地址",
	"desc" => "输入腾讯微博地址的 HTML code.",
	"id" => $shortname."_tqq_url",
	"type" => "textarea",
	"std" => "http://t.qq.com/qintag"),

array( "name" => "控制是否显示新浪微博",
	"desc" => "默认禁用",
	"id" => $shortname."_tsina_activate",
	"type" => "select",
	"options" => array("No", "YES"),
	"std" => "No"),

	
array( "name" => "输入新浪微博地址",
	"desc" => "输入新浪微博地址 的 html code.",
	"id" => $shortname."_tsina_url",
	"type" => "textarea",
	"std" => "http://weibo.com/qintag"),

array( "name" => "是否显示用QQ邮箱订阅博客",
	"desc" => "默认显示",
	"id" => $shortname."_mailqq_activate",
	"type" => "select",
	"options" => array("YES", "NO"),
	"std" => "YES"),
	
array( "name" => "控制是否显示 加入收藏 图标",
	"desc" => "默认显示",
	"id" => $shortname."_favourites_activate",
	"type" => "select",
	"options" => array("YES", "NO"),
	"std" => "YES"),


array(	"type" => "close"),
///////////////////////////////////////////////////////////////////////
array( "name" => "页脚部分", "type" => "section"),
array( "type" => "open"),

array( "name" => "ICP备案号",
	"desc" => "您的ICP备案信息，如：陕ICP备12345678号",
	"id" => $shortname."_icpbeian",
	"type" => "text",
	"std" => "陕ICP备12345678号"),

array( "name" => "是否显示GZig压缩信息",
	"desc" => "详细教程见：http://www.qintag.com/open-the-server-gzip-compression-for-wordpress.html",
	"id" => $shortname."_Gzipped_activate",
	"type" => "select",
	"options" => array("No", "Yes"),
	"std" => "No"),

array( "name" => "GZig查询地址",
	"desc" => "您的GZig查询地址，如：http://www.whatsmyip.org/http_compression/?url=aHR0cDovL3d3dy5xaW50YWcuY29t",
	"id" => $shortname."_htmlGzipped",
	"type" => "textarea",
	"std" => ""),

array( "name" => "统计代码",
	"desc" => "cnzz等网站统计代码，这个就不多解释了",
	"id" => $shortname."_cnzztongji",
	"type" => "textarea",
	"std" => ""),
	
	
array( "name" => "是否显示Creative Commons;",
	"desc" => "如:本作品采用<a rel='license' href='http://creativecommons.org/licenses/by-nc-sa/2.5/cn/'>知识共享署名-非商业性使用-相同方式共享 2.5 中国大陆许可协议</a>进行许可",
	"id" => $shortname."_Creative_activate",
	"type" => "select",
	"options" => array("No", "Yes"),
	"std" => "No"),

array( "type" => "close"),
///////////////////////////////////////////////////////////////////////
array( "name" => "文章页面", "type" => "section"),
array( "type" => "open"),

array( "name" => "文章开始广告条",
	"desc" => "文章最开始的广告条，建议尺寸468*15",
	"id" => $shortname."_topad_post_content",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "文章结尾广告条",
	"desc" => "文章结束后的广告条，建议尺寸468*15",
	"id" => $shortname."_bottomad_post_content",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "文章底部的分享",
	"desc" => "加网、百度分享等等分享代码，ps:请将演示的代码改为您自己的代码",
	"id" => $shortname."_post_share",
	"type" => "textarea",
	"std" => ""),

array( "type" => "close"),

///////////////////////////////////////////////////////////////////////
array( "name" => "侧边栏部分", "type" => "section"),
array( "type" => "open"),

array( "name" => "是否显示热评日志、最新日志、最近更新、随机日志tabs切换",
	"desc" => "默认显示",
	"id" => $shortname."_sidebar_tab_activate",
	"type" => "select",
	"options" => array("YES", "NO"),
	"std" => "YES"),
	
array( "name" => "是否显示日志归档、标签云、日历表tabs切换",
	"desc" => "默认显示",
	"id" => $shortname."_sidebar_tab2_activate",
	"type" => "select",
	"options" => array("YES", "NO"),
	"std" => "YES"),
	
array( "name" => "是否显示友情链接",
	"desc" => "默认显示",
	"id" => $shortname."_sidebar_links_activate",
	"type" => "select",
	"options" => array("YES", "NO"),
	"std" => "YES"),

array( "name" => "微博代码",
	"desc" => "您的微博秀代码，建议宽度改为auto，自适应",
	"id" => $shortname."_miniblog_code",
	"type" => "textarea",
	"std" => ""),
	

array( "name" => "300*250Sidebar广告",
	"desc" => "300*250的广告代码",
	"id" => $shortname."_ad300_250",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "160*600摩天柱广告",
	"desc" => "160*600摩天柱广告，建议放在左下侧边栏",
	"id" => $shortname."_ad160_600",
	"type" => "textarea",
	"std" => ""),

array( "type" => "close"),
//////////////////////////////////////////////////////////////////////////////
array( "name" => "135x135赞助商广告", "type" => "section"),
array( "type" => "open"),
array( "name" => "Activate 135x135 Ads",
	"desc" => "默认禁用，开启之后，在小工具中自行添加",
	"id" => $shortname."_sponsor_activate",
	"type" => "select",
	"options" => array("No", "Yes"),
	"std" => "No"),

array( "name" => "Banner Ads 1",
	"desc" => "Insert banner 1 HTML code.",
	"id" => $shortname."_sponsor_banner_one",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "Banner Ads 2",
	"desc" => "Insert banner 2 HTML code.",
	"id" => $shortname."_sponsor_banner_two",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "Banner Ads 3",
	"desc" => "Insert banner 3 HTML code.",
	"id" => $shortname."_sponsor_banner_three",
	"type" => "textarea",
	"std" => ""),
	
array( "name" => "Banner Ads 4",
	"desc" => "Insert banner 4 HTML code.",
	"id" => $shortname."_sponsor_banner_four",
	"type" => "textarea",
	"std" => ""),
	
array( "type" => "close"),

//////////////////////////////////////////////////////////////////////////////
array( "name" => "其它设置", "type" => "section"),
array( "type" => "open"),

array( "name" => "评论框的邻居",
	"desc" => "大小为180*150的广告，代码，图片随意了，怎么好怎么用,ps:请将演示的代码改为您自己的代码",
	"id" => $shortname."_ad_comment_180_150",
	"type" => "textarea",
	"std" => ""),

array( "name" => "微博模版页面代码",
	"desc" => "微博模版页，新建模版页后看到效果，建议宽度改为auto，自适应，微博功能后期会进一步增强，请暂时将就使用",
	"id" => $shortname."_miniblog_template_code",
	"type" => "textarea",
	"std" => ""),

array( "type" => "close"),

);

//////////////////////////////////////////////////////////////////////////////
function qintag_add_admin() {
	global $themename, $shortname, $options;
		if ( $_GET['page'] == basename(__FILE__) ) {
			if ( 'save' == $_REQUEST['action'] ) {
				foreach ($options as $value) {
					update_option( $value['id'], $_REQUEST[ $value['id'] ] ); }
				foreach ($options as $value) {
					if( isset( $_REQUEST[ $value['id'] ] ) ) { update_option( $value['id'], $_REQUEST[ $value['id'] ]  ); } else { delete_option( $value['id'] ); } }
				header("Location: admin.php?page=functions.php&saved=true");
				die;
				} 
			else if( 'reset' == $_REQUEST['action'] ) {
				foreach ($options as $value) {
					delete_option( $value['id'] ); }
					header("Location: admin.php?page=functions.php&reset=true");
					die;
			}
		}
	add_theme_page($themename." Options", "设置当前主题", 'edit_themes', basename(__FILE__), 'qintag_admin');
}

//////////////////////////////////////////////////////////////////////////////
function qintag_add_init() {
	$file_dir=get_bloginfo('template_directory');
	wp_enqueue_style("functions", $file_dir."/css/functions/functions.css", false, "1.0", "all");
	wp_enqueue_script("rm_script", $file_dir."/css/functions/rm_script.js", false, "1.0");
}

//////////////////////////////////////////////////////////////////////////////
function qintag_admin() {
	global $themename, $shortname, $options;
	$i=0;
		if ( $_REQUEST['saved'] ) echo '<div id="message" class="updated fade"><p><strong>'.$themename.' 设置完成.</strong></p></div>';
		if ( $_REQUEST['reset'] ) echo '<div id="message" class="updated fade"><p><strong>'.$themename.' 重置完成.</strong></p></div>';
?>

<div class="rm_wraps">

<div class="rm_wrap">
	<h2><?php echo $themename; ?> 主题设置</h2>
		<p>当前使用主题: forigi | 设计者:<a href="http://www.qintag.com" target="_blank"> Qintag</a> | <a href="http://www.qintag.com/forigi-update.html" target="_blank">查看主题更新</a></p>
	<div class="rm_opts">
	<form method="post">
			<?php foreach ($options as $value) {
				switch ( $value['type'] ) {
				case "open":
			?>
			<?php break;
				case "close":
			?>
	</div>
</div>
	
<?php break;
case "title":
?>
<div id="announce">
	<h1>感谢您使用 <?php echo "$themename"; ?> 主题</h1>
	<span>浏览以下信息可以是您更方便的使用此主题：(1)<a href="http://www.qintag.com/?p=1949" target="_blank">主题发布</a> (2)<a href="http://www.qintag.com/?p=1951" target="_blank">使用帮助</a> (3)<a href="http://weibo.com/qintag" target="_blank">新浪博客 </a></span>
<p class="note">更多主题请浏览<a href="http://www.qintag.com" target="_blank">我的博客</a>，或<a href="http://feed.feedsky.com/qintag" target="_blank">订阅feed</a></p>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {return;}
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
</div>

<?php break;
case 'text':
?>
<div class="rm_input rm_text">
	<label for="<?php echo $value['id']; ?>"><?php echo $value['name']; ?></label>
 	<input name="<?php echo $value['id']; ?>" id="<?php echo $value['id']; ?>" type="<?php echo $value['type']; ?>" value="<?php if ( get_settings( $value['id'] ) != "") { echo stripslashes(get_settings( $value['id'])  ); } else { echo $value['std']; } ?>" />
	<small><?php echo $value['desc']; ?></small>
	<div class="clearfix"></div>
</div>


<?php
break;
case 'textarea':
?>
<div class="rm_input rm_textarea">
	<label for="<?php echo $value['id']; ?>"><?php echo $value['name']; ?></label>
 	<textarea name="<?php echo $value['id']; ?>" type="<?php echo $value['type']; ?>" cols="" rows=""><?php if ( get_settings( $value['id'] ) != "") { echo stripslashes(get_settings( $value['id']) ); } else { echo $value['std']; } ?></textarea>
	<small><?php echo $value['desc']; ?></small>
	<div class="clearfix"></div>
</div>

<?php
break;
case 'select':
?>
<div class="rm_input rm_select">
	<label for="<?php echo $value['id']; ?>"><?php echo $value['name']; ?></label>
	<select name="<?php echo $value['id']; ?>" id="<?php echo $value['id']; ?>">
		<?php foreach ($value['options'] as $option) { ?>
		<option <?php if (get_settings( $value['id'] ) == $option) { echo 'selected="selected"'; } ?>><?php echo $option; ?></option><?php } ?>
	</select>
	<small><?php echo $value['desc']; ?></small><div class="clearfix"></div>
</div>


<?php
break;
case "checkbox":
?>
<div class="rm_input rm_checkbox">
	<label for="<?php echo $value['id']; ?>"><?php echo $value['name']; ?></label>
	<?php if(get_option($value['id'])){ $checked = "checked=\"checked\""; }else{ $checked = "";} ?>
	<input type="checkbox" name="<?php echo $value['id']; ?>" id="<?php echo $value['id']; ?>" value="true" <?php echo $checked; ?> />
	<small><?php echo $value['desc']; ?></small>
	<div class="clearfix"></div>
</div>

<?php break; 
case "section":
$i++;
?>
<div class="rm_section">
	<div class="rm_title">
		<h3><img src="<?php bloginfo('template_directory')?>/css/functions/trans.png" class="inactive" alt="""><?php echo $value['name']; ?></h3><span class="submit"><input name="save<?php echo $i; ?>" type="submit" value="保存设置" /></span>
		<div class="clearfix"></div>
	</div>

	<div class="rm_options">
		<?php break;
			}
			}
		?>

		<input type="hidden" name="action" value="save" />
</form>
		<form method="post">
			<p class="submit">
				<input name="reset" type="submit" value="恢复默认" /><font color=#ff0000>提示：此按钮将恢复主题初始状态，您的所有设置将消失！</font>
				<input type="hidden" name="action" value="reset" />
			</p>
		</form>
		
		<div id="sthiks">
			<h1>其 它</h1>
			<p>forigi主题的完成，很多代码都来自与Willin Kan、zwwooooo、知更鸟等博客，最后对他们表示感谢，如果您喜欢折腾WordPress主题，可以浏览他们的网站：
				<a rel="nofollow" href="http://codex.wordpress.org.cn/" target="_blank">WordPress中文文档</a> | 
				<a rel="nofollow" href="http://kan.willin.org" target="_blank">Willin Kan</a> | 
				<a rel="nofollow" href="http://zww.me/" target="_blank">zwwooooo</a> | 
				<a rel="nofollow" href="http://zmingcx.com/" target="_blank">知更鸟</a> | 
				<a rel="nofollow" href="http://wange.im/" target="_blank">万戈Life Studio</a> | 
				<a rel="nofollow" href="http://paranimage.com/mastering-wordpress-themes-5/" target="_blank">帕兰映像</a>。
			</p>
			<p>另外本人在forigi主题制作中，也倾入了诸多心血，所以不能保留底部链接的，不尊重我的劳动成果的人，请绕行，不欢迎你使用本主题！</p>
			<p class="note">本作品采用<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/cn/">知识共享署名-非商业性使用-相同方式共享 2.5 中国大陆许可协议</a>进行许可。</p>
		</div>
	</div> 
</div>
<div id="other">
	<h1>捐助：</h1>
	<p>您的支持，是对他最大的肯定和鼓励！<br />
	<strong>支付宝：<font color=#21759b>qintag@sina.com</font></strong> 姓名:马沛</p>
	<a target="_blank" href='http://me.alipay.com/qintag'> <img src='https://img.alipay.com/sys/personalprod/style/mc/btn-index.png' /> </a>

	<h2>主题说明(Theme introduction)：</h2>
	<p>
		1、自动SEO；<br />
		2、共有三个自定义菜单，支持多级下拉菜单；<br />
		3、置顶文章区别显示；<br />
		4、内置了知更鸟的图片暗箱特效；<br />
		5、支持外链缩略图；<br />
		6、历史上的今天（Today on history）；<br />
		7、热评日志/最新日志/最近更新/随机日志的tab切换；<br />
		8、彩色标签云；<br />
		9、评论嵌套回复、Ajax评论<br />
		10、<<小牆>> Anti-Spam v1.84 by Willin Kan；<br />
		11、只在WP主评论加上楼层号 by zwwooooo；<br />
		12、用 jQuery 实现点击回复之后显示@用户名的效果 by zwwooooo;<br />
		13、区分访客评论、博主评论和引用评论，让评论数更加清楚 by Willin Kan；<br />
		14、友情连结页面设置+显示网站的Favicon；<br />
		15、10块可自定义广告代码的博客广告系统；<br />
		16、版权年份自动化；<br />
		17、自定义微博、订阅、加入收藏、分享功能；<br />
		18、Google站内搜索功能；<br />
		19、jQuery页面载入进度条，效果见博博客顶部导航栏。
	</p>
	<h2>赞助商链接</h2>
	<div id="qintag_fund">
		<script type="text/javascript"><!--
			google_ad_client = "ca-pub-1548080245603453";
			/* 评论 */
			google_ad_slot = "6458301436";
			google_ad_width = 180;
			google_ad_height = 150;
			//-->
		</script>
		<script type="text/javascript" src="http://pagead2.googlesyndication.com/pagead/show_ads.js"></script>
	</div>
</div>
<div class="clearfix"></div>
</div>
<?php
}
?>
<?php
add_action('admin_init', 'qintag_add_init');
add_action('admin_menu', 'qintag_add_admin');
?>