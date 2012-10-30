$(function(){

	$("#nav").aqy106ScrollFixed();
	$("a").aqy106Preview();	
	aqy106AutoScroll("#notices",300,4000);
	aqy106AutoScroll("#slideReplayList",1000,5000);

	$("#loading").animate({width:"100%"}, function() {
		setTimeout(function() {
			$("#loading").hide();
		}, 1000);
	});

	$(".boxLeftIn img").lazyload({   
		placeholder : "wp-content/themes/Qiumin/s/none.gif",  
		effect : "fadeIn"
	});  
	
	$("#sb").click(function(){
		if($("#s").val()==""){
			alert("你是不忘了输入要搜索的内容呢？");
			return false;
		}
	});

	var i =1;
	function changBg() {
		i++;
		$("body").css({"background-position":i +"px"}); 
	}
	setInterval(function(){changBg()},120)

	/*回顶部和到底部*/
	$backToTopEle = $('<div class="bottomFiexed"><a href="#" class="backToTop" title="点击返回顶部">点击返回顶部</a><a href="#" class="toBottom"  title="点击到达底部">点击到达底部</a></div>').appendTo($("body"));
	$backToTopFun = function () {
		var st = $(document).scrollTop(), winh = $(window).height();
//		(st > 0) ? $backToTopEle.show() : $backToTopEle.hide();
		//IE6下的定位
		if (!window.XMLHttpRequest) {
			$backToTopEle.css("top", st + winh - 41);
		}
	};
	$(window).bind("scroll", $backToTopFun);
	$backToTopFun();
	
	 $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $("html") : $("body")) : $("html,body");
    $(".backToTop").mouseover(function() {
        up();
    }).mouseout(function() {
        clearTimeout(fq)
    }).click(function() {
        $body.animate({
            scrollTop: 0
        },800)
    });
    $(".toBottom").mouseover(function() {
        dn();
    }).mouseout(function() {
        clearTimeout(fq)
    }).click(function() {
        $body.animate({
            scrollTop: $(document).height()
        },800)
    });
//    $("#comt").click(function() {
//        $body.animate({
//            scrollTop: $("#comments").offset().top
//        },
//        400)
//    });

//	$('.backToTop').click(function(){$('html,body').animate({scrollTop: '0px'}, 800);}); 
//	$('.toBottom').click(function(){$('html,body').animate({scrollTop:$('.footer ').offset().top}, 800);});
	/*回顶部和到底部*/

	/*weibo*/
	var miniBlogShare = function() {
		//指定位置驻入节点
		$('<img id="imgSinaShare" class="img_share" title="将选中内容分享到新浪微博" src="http://www.aqy106.com/wp-content/themes/Qiumin/s/weibo.gif" /><img id="imgQqShare" class="img_share" title="将选中内容分享到腾讯微博" src="http://www.aqy106.com/wp-content/themes/Qiumin/s/tqq.png" />').appendTo("body");
	   
		//默认样式
		$(".img_share").css({"display" : "none", "position" : "absolute", "cursor" : "pointer", "z-index" : "101"});
	   
		//选中文字
		var funGetSelectTxt = function() {
			var txt = '';
			if(document.selection) {
				txt = document.selection.createRange().text;
			} else {
				txt = document.getSelection();
			}
			return txt.toString();
		};
	   
		//选中文字后显示微博图标
		$("html,body").mouseup(function(e) {
			if (e.target.id == "imgSinaShare" || e.target.id == "imgQqShare") {
				return
			}
			e = e || window.event;
			var txt = funGetSelectTxt(),
				sh = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0,
				left = (e.clientX - 40 < 0) ? e.clientX + 20 : e.clientX - 40,
				top = (e.clientY - 40 < 0) ? e.clientY + sh + 20 : e.clientY + sh - 40;
			if (txt) {
				$('#imgSinaShare').css({"display" : "inline", "left" : left + "px", "top" : top + "px"});
				$('#imgQqShare').css({"display" : 'inline', "left" : left + 30 + "px", "top" : top + "px"});
			} else {
				$('#imgSinaShare').css("display", "none");
				$('#imgQqShare').css("display", "none");
			}
		});
	   
		//点击新浪微博
		$("#imgSinaShare").click(function() {
			var txt = funGetSelectTxt(), title = $('title').html();
			if (txt) {
				window.open('http://v.t.sina.com.cn/share/share.php?title=' + txt + ' —— 转载自：' + title + '&url=' + window.location.href);
			}
		});
	   
		//点击腾讯微博
		$("#imgQqShare").click(function() {
			var txt = funGetSelectTxt(), title = $('title').html();
			if (txt) {
				window.open('http://v.t.qq.com/share/share.php?title=' + encodeURIComponent(txt + ' —— 转载自：' + title) + '&url=' + window.location.href);
			}
		});
	}();

});

function aqy106AutoScroll(obj,speed,time) {
	var $ul = $(obj).children("ul");
	var liHeight = $ul.find("li:last").height();	
	function margin() {
		$ul.animate({ marginTop: liHeight },speed, function () {
			$ul.find("li:last").prependTo($ul);
			$ul.find("li:first").hide();
			$ul.css({ marginTop: 0 });
			$ul.find("li:first").fadeIn(speed);			
		});
	};

	var auto = setInterval(function(){margin();},time);
	$(obj).hover(function(){clearInterval(auto);},function(){auto=setInterval(function(){margin();},time);});		
}


$.fn.aqy106ScrollFixed= function() {
    var position = function(element) {
        var top = element.position().top;
        $(window).scroll(function() {
            var scrolls = $(this).scrollTop();
			var barHeight=$("#wpadminbar").height();
            if (scrolls > top) {
                if (window.XMLHttpRequest) {
                    element.css({
                        position: "fixed",
                        top: 0+barHeight+"px",
						zIndex: 100
                    });    
                } else {
                    element.css({
                        top: scrolls,
						zIndex: 100
                    });    
                }
            }else {
                element.css({
                    position: "absolute",
                    top: top,
					zIndex:100
                });    
            }
        });
    };
    return $(this).each(function() {
        position($(this));
    });
};

/*lazyload*/
(function($){$.fn.lazyload=function(options){var settings={threshold:0,failurelimit:0,event:"scroll",effect:"show",container:window};if(options){$.extend(settings,options);}
var elements=this;if("scroll"==settings.event){$(settings.container).bind("scroll",function(event){var counter=0;elements.each(function(){if($.abovethetop(this,settings)||$.leftofbegin(this,settings)){}else if(!$.belowthefold(this,settings)&&!$.rightoffold(this,settings)){$(this).trigger("appear");}else{if(counter++>settings.failurelimit){return false;}}});var temp=$.grep(elements,function(element){return!element.loaded;});elements=$(temp);});}
this.each(function(){var self=this;if(undefined==$(self).attr("original")){$(self).attr("original",$(self).attr("src"));}
if("scroll"!=settings.event||undefined==$(self).attr("src")||settings.placeholder==$(self).attr("src")||($.abovethetop(self,settings)||$.leftofbegin(self,settings)||$.belowthefold(self,settings)||$.rightoffold(self,settings))){if(settings.placeholder){$(self).attr("src",settings.placeholder);}else{$(self).removeAttr("src");}
self.loaded=false;}else{self.loaded=true;}
$(self).one("appear",function(){if(!this.loaded){$("<img />").bind("load",function(){$(self).hide().attr("src",$(self).attr("original"))
[settings.effect](settings.effectspeed);self.loaded=true;}).attr("src",$(self).attr("original"));};});if("scroll"!=settings.event){$(self).bind(settings.event,function(event){if(!self.loaded){$(self).trigger("appear");}});}});$(settings.container).trigger(settings.event);return this;};$.belowthefold=function(element,settings){if(settings.container===undefined||settings.container===window){var fold=$(window).height()+$(window).scrollTop();}else{var fold=$(settings.container).offset().top+$(settings.container).height();}
return fold<=$(element).offset().top-settings.threshold;};$.rightoffold=function(element,settings){if(settings.container===undefined||settings.container===window){var fold=$(window).width()+$(window).scrollLeft();}else{var fold=$(settings.container).offset().left+$(settings.container).width();}
return fold<=$(element).offset().left-settings.threshold;};$.abovethetop=function(element,settings){if(settings.container===undefined||settings.container===window){var fold=$(window).scrollTop();}else{var fold=$(settings.container).offset().top;}
return fold>=$(element).offset().top+settings.threshold+$(element).height();};$.leftofbegin=function(element,settings){if(settings.container===undefined||settings.container===window){var fold=$(window).scrollLeft();}else{var fold=$(settings.container).offset().left;}
return fold>=$(element).offset().left+settings.threshold+$(element).width();};$.extend($.expr[':'],{"below-the-fold":"$.belowthefold(a, {threshold : 0, container: window})","above-the-fold":"!$.belowthefold(a, {threshold : 0, container: window})","right-of-fold":"$.rightoffold(a, {threshold : 0, container: window})","left-of-fold":"!$.rightoffold(a, {threshold : 0, container: window})"});})(jQuery);

/*zoom*/
(function($){
	$.fn.aqy106Preview = function(){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		$(this).each(function(){
			var aTit = $(this).attr("title");
			var imgTit = $(this).children("img").attr("title");
			$(this).hover(function(e){
				if(/.png$|.gif$|.jpg$|.bmp$/.test($(this).attr("href"))){
					$("body").append("<div id='preview'><div><img src='"+$(this).attr('href')+"' /><p>"+$(this).children("img").attr('alt')+"</p></div></div>");
				}else{
					$("body").append("<div id='preview'><div><p>"+$(this).attr('title')+"</p></div></div>");
				}
				$(this).attr("title" , "");
				$(this).children("img").attr("title" , "");
				$("#preview").css({
					position:"absolute",
					padding:"4px",
					border:"1px solid #f3f3f3",
					backgroundColor:"#eee",
					top:(e.pageY - yOffset) + "px",
					zIndex:100000
				});
				$("#preview > div").css({
					padding:"5px",
					backgroundColor:"white",
					border:"1px solid #ccc"
				});
				$("#preview > div > p").css({
					textAlign:"center",
					fontSize:"12px",
					padding:"4px 0 4px"
				});
				if(e.pageX < w/2){
					$("#preview").css({
						left: e.pageX + xOffset + "px",
						right: "auto",
						boxShadow:"4px 4px 6px rgba(33,33,33,.7)"
					}).fadeIn("fast");
				}else{
					$("#preview").css({"right":(w - e.pageX + yOffset) + "px","left":"auto","boxShadow":"-4px 4px 6px rgba(33,33,33,.7)"}).fadeIn("fast");	
				}
			},function(){
				$("#preview").remove();
				$(this).attr("title", aTit);
				$(this).children("img").attr("title" , imgTit);
				if($(this).attr("title")==""){
					$(this).attr("title","该链接没有定义标题");
				}
			}).mousemove(function(e){
				$("#preview").css("top",(e.pageY - xOffset) + "px")
				if(e.pageX < w/2){
					$("#preview").css("left",(e.pageX + yOffset) + "px").css("right","auto");
				}else{
					$("#preview").css("right",(w - e.pageX + yOffset) + "px").css("left","auto");
				}
			});						  
		});
	};
})(jQuery);

/*到顶到底函数*/
function up() {
    $wd = $(window);
    $wd.scrollTop($wd.scrollTop() - 1);
    fq = setTimeout("up()", 50)
};
function dn() {
    $wd = $(window);
    $wd.scrollTop($wd.scrollTop() + 1);
    fq = setTimeout("dn()", 50)
}; 


