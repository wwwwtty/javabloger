<?php get_header(); ?>
<div id="fullcontent">
	<div id="page_error">
			<h3>Error 404 : <?php echo wcs_error_currentPageURL(); ?></h3>
			<img src="<?php bloginfo('template_directory');?>/images/oops_sorry_ani.gif"/>
			<p><b>糟糕</b> ... 我们搞砸了，同时感谢您发现了一个我们的设计缺陷，<b>嗯</b>... 我们并不是完美无暇的，但我们会尝试做的更好。</p>
			<h2>下一步该怎么做呢？下边整理的 10 种提示，希望可以帮到您：</h2>
			<ol>
				<li>
					发送邮件<b><a href="mailto:qintag@sian.com" >通知我们</a></b>。
				</li>

				<li>
					返回 <b><a href="javascript:history.back()">上一页</a></b>。
				</li>

				<li>
					回到网站<b><a href="<?php bloginfo('siteurl');?>">首页</a></b>。

				</li>
				
				<li>
					尝试刷新页面 <b>（按F5）</b>。
				</li>

				<li>
					从顶部导航栏中选择<b>分类菜单</b>进行浏览。
				</li>

				<li>
					自定义搜索或按照页面、月份、分类进行<b>搜索</b>:
					<div class="error_box">
						<span>搜 索:</span>
						<div class="error_extends">
							<form style="margin-left: 5px; margin-bottom: 5px;">
								<input type="text" name="s" id="searchbox" value="输入关键字..." onfocus="if (this.value == '输入关键字...') {this.value = '';}" onblur="if (this.value == '') {this.value = '输入关键字...'}" />
								<input type="submit" id="searchsubmits" value="搜 索"/>
							</form>
						</div>
						<div class="clearfix"></div>
						<span>按页面:</span>
						<div class="error_extends">
							<?php echo wcs_error_pulldown_pages(); ?>
						</div>
						<div class="clearfix"></div>
					
						<span>按月份:</span>
						<div class="error_extends">
							<?php echo wcs_error_pulldown_archives(); ?>
						</div>
						<div class="clearfix"></div>
						<span>按分类:</span>
						<div class="error_extends">
						<?php echo wcs_error_pulldown_categories(); ?>
						</div>
						<div class="clearfix"></div>
					</div>
				</li>

				<li>
					<s>等待一段时间，如果错误页面奇迹般地重定向到一个不同的页面，您便可以浏览了（<i>提示</i>：可能性为0(*^__^*) ）。</s>
				</li>

				<li>
					因为我们无法能满足您的期望，请访问别的网站。
				</li>
				
				<li>
					While you're here anyway, why not <b>buy the t-shirt</b>?
					<div style="background-image:url('<?php bloginfo('template_directory');?>/images/error_t-shirt_purple.png');
						text-align:center; width:300px; min-height:297px !important; margin-top:10px; color:white;
						font-size:16px; line-height:150%;">
					<br/><br/>I Visited<br/>
					<b><?php bloginfo('name')?></b><br/>
					And All I Got<br/>
					Was THIS T-shirt !!!<br/>
					</div>

				</li>

				<li>
					<b>Sing the 404 Song</b>:<br/>
					<div style="margin-top:10px; padding-left:20px;">
					Your web site is one I used to adore.<br/>
					But, I ain't comin' back here no more.<br/>
					Cause you done gave me this darn 404.<br/><br/>

					Surfin' the web shouldn't be such a chore.<br/>
					So where am I gonna go next to explore?<br/>
					Cause you done gave me this darn 404.<br/>
					</div>
				</li><br/>
			</ol>
	</div><!-- PAGE_ERROR END -->
</div><!-- FULLCONTENT END -->
<?php get_footer(); ?>