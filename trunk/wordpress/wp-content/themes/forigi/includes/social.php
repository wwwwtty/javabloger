<!--加载微博、加入收藏以及订阅图标-->
<?php $rss2_url_activate = get_qintag_option('rss2_url_activate'); if(($rss2_url_activate == '') || ($rss2_url_activate == 'No')) { ?>
	<?php { /* nothing */ } ?>
<?php } else { ?>
	<span><a href="<?php echo get_qintag_option('rss2_url_feed'); ?>" target="_blank" class="icon1" title="欢迎订阅<?php bloginfo('name'); ?>"></a></span>
<?php } ?>

<?php $mailqq_activate = get_qintag_option('mailqq_activate'); if($mailqq_activate == 'No') { ?>
	<?php { /* nothing */ } ?>
<?php } else { ?>
	<span><a rel="nofollow" href="http://mail.qq.com/cgi-bin/feed?u=<?php bloginfo('rss2_url'); ?>" target="_blank" class="icon4" title="用QQ邮箱订阅我的博客"></a></span> 
<?php } ?>

<?php $tsina_activate = get_qintag_option('tsina_activate'); if(($tsina_activate == '') || ($tsina_activate == 'No')) { ?>
	<?php { /* nothing */ } ?>
<?php } else { ?>
	<span><a rel="nofollow" href="<?php echo get_qintag_option('tsina_url'); ?>" target="_blank" class="icon3" title="我的新浪微博"></a></span> 
<?php } ?>

<?php $tqq_activate = get_qintag_option('tqq_activate'); if(($tqq_activate == '') || ($tqq_activate == 'No')) { ?>
	<?php { /* nothing */ } ?>
<?php } else { ?>
	<span><a rel="nofollow" href="<?php echo get_qintag_option('tqq_url'); ?>" target="_blank" class="icon2" title="我的腾讯微博"></a></span> 
<?php } ?>

<?php $favourites_activate = get_qintag_option('favourites_activate'); if($favourites_activate == 'No') { ?>
	<?php { /* nothing */ } ?>
<?php } else { ?>
	<span><a href="javascript:;" onclick="addFav()" class="icon5" title="加入收藏"></a></span>
<?php } ?>
