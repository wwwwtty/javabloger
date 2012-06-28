<li class="sponsorbox">
<h3><?php _e('赞助商链接'); ?></h3>
<div class="sponsor_links">

<?php $sponsor_banner = get_qintag_option('sponsor_banner_one'); if($sponsor_banner == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<?php echo stripcslashes($sponsor_banner); ?>
<?php } ?>

<?php $sponsor_banner = get_qintag_option('sponsor_banner_two'); if($sponsor_banner == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<?php echo stripcslashes($sponsor_banner); ?>
<?php } ?>

<?php $sponsor_banner = get_qintag_option('sponsor_banner_three'); if($sponsor_banner == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<?php echo stripcslashes($sponsor_banner); ?>
<?php } ?>

<?php $sponsor_banner = get_qintag_option('sponsor_banner_four'); if($sponsor_banner == '') { ?>
<?php { /* nothing */ } ?>
<?php } else { ?>
<?php echo stripcslashes($sponsor_banner); ?>
<?php } ?>
</div><!-- sponsor links end -->
</li><!-- sponsorbox end -->