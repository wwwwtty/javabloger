
// 
jQuery(document).ready(function($){
	$('.icon1,.icon2,.icon3,.icon4,.icon5,').wrapInner('<span class="hover"></span>').css('textIndent','0').each(function () {
		$('span.hover').css('opacity', 0).hover(function () {
			$(this).stop().fadeTo(300, 1);
		}, function () {
			$(this).stop().fadeTo(300, 0);
		});
	});
});


//加入收藏夹  
function addFav() {
             if  (document.all) {
                window.external.addFavorite('http://www.qintag.com', '秦唐网');
            }
             else   if  (window.sidebar) {
            window.sidebar.addPanel('秦唐网', 'http://www.qintag.com',  "" );
            }
        }

// 滑动到顶部
$(document).ready(function(){
	// fade in #back-top
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 100) {
				$('#back-top').fadeIn();
			} else {
				$('#back-top').fadeOut();
			}
		});
		// scroll body to 0px on click
		$('#back-top a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 800);
			return false;
		});
	});
});

// 评论贴图
function embedImage() {
  var URL = prompt('请输入图片 URL 地址:', 'http://');
  if (URL) {
		document.getElementById('comment').value = document.getElementById('comment').value + '[img]' + URL + '[/img]';
  }
}
// 控制贴图大小
$(document).ready(function() {
    var maxwidth=150;
    $(".comment-body img").each(function(){
        if (this.width>maxwidth)
         this.width = maxwidth;
    });
});

/* @reply js by zwwooooo */
jQuery(document).ready(function($){	//Begin jQuery
	$('.reply').click(function() {
	var atid = '"#' + $(this).parent().attr("id") + '"';
	var atname = $(this).prevAll().find('cite:first').text();
	$("#comment").attr("value","@" + atname + " ").focus();
});
	$('.cancel-comment-reply a').click(function() {	//点击取消回复评论清空评论框的内容
	$("#comment").attr("value",'');
});
})	//End jQuery

// 链接复制
function copy_code(text) {
  if (window.clipboardData) {
    window.clipboardData.setData("Text", text)
	alert("已经成功将原文链接复制到剪贴板！");
  } else {
	var x=prompt('你的浏览器可能不能正常复制\n请您手动进行：',text);
  }
}

//加载中提示
$(document).ready(function(){
$('h2 a').click(function(){
$(this).html('正在给力加载中...');
window.location = $(this).attr('href');
});
});


//双击滚屏
var currentpos,timer; 
function initialize() 
{ 
timer=setInterval("scrollwindow()",20); 
} 
function sc(){ 
clearInterval(timer); 
} 
function scrollwindow() 
{ 
window.scrollBy(0,1); 
} 
document.onmousedown=sc 
document.ondblclick=initialize


// 回到顶部
/*****************************************
jQuery(document).ready(function($){
// 	$body = (window.opera)?(document.compatMode=="CSS1Compat"?$('html'):$('body')):$('html,body'); // opera fix      
	$('#copyright span').click(function(){
		$('html,body').animate({scrollTop: '0'}, 800);
	return false;
		});
});
*********************************/

// 鼠标滑动文字大小提示渐隐渐显效果
$(function() {
    $('#post_meta').mouseover(function() {
        if (window.willhide) clearTimeout(window.willhide);
        $('.text_size').fadeIn("normal")
    });
    $('#post_meta').mouseout(function() {
        window.willhide = setTimeout(function() {
            $('.text_size').fadeOut("normal")
        },
        100)
    });
});

// 字体调整
jQuery(document).ready(function($){
$(".text_small").click(function(){
	$(".post_content").css("font-size","12px");
	$(".post_content").css("line-height","22px");
});
$(".text_middle").click(function(){
	$(".post_content").css("font-size","13px");
	$(".post_content").css("line-height","22px");
});
$(".text_large").click(function(){
	$(".post_content").css("font-size","16px");
	$(".post_content").css("line-height","26px");
});
});


// JavaScript Document
jQuery(document).ready(function() {
jQuery("#topnav ul").css({display: "none"}); // Opera Fix
jQuery("#topnav li").hover(function(){
		jQuery(this).find('ul:first').css({visibility: "visible",display: "none"}).show(400);
		},function(){
		jQuery(this).find('ul:first').css({visibility: "hidden"});
		});
});

jQuery(document).ready(function() {
jQuery("#topnav2 ul").css({display: "none"}); // Opera Fix
jQuery("#topnav2 li").hover(function(){
		jQuery(this).find('ul:first').css({visibility: "visible",display: "none"}).show(400);
		},function(){
		jQuery(this).find('ul:first').css({visibility: "hidden"});
		});
});



