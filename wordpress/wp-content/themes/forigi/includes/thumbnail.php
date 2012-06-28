<div class="thumb">
<?php if ( has_post_thumbnail() ) { ?>
<a href="<?php the_permalink() ?>" rel="bookmark" title="<?php the_title(); ?>"><?php the_post_thumbnail('thumbnail', array( 'alt' => trim(strip_tags( $post->post_title )), 'title' => trim(strip_tags( $post->post_title )),'class' => 'alignleft')); ?></a>
<?php } else { ?>
<a href="<?php the_permalink() ?>" rel="bookmark" title="<?php the_title(); ?>"><img src="<?php echo get_featcat_image(); ?>" class="alignleft" alt="<?php the_title(); ?>" /></a>
<?php } ?>
</div>