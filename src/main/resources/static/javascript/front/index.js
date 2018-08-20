$(function(){
// 顶部轮播图(初始化)
     bannerListFn(
          $(".banner"),
          $(".img-btn-list"),
          $(".left-btn"),
          $(".right-btn"),
          2000,
          true
      );
// 顶部轮播图(适配一屏的高度)
	$(document).ready(function(){
		var winHeight = $(window).height()-100;
		$('.banner').css('height',winHeight);
		$('.banner img').css('height',winHeight);
	});

// 顶部轮播图(屏幕缩放)
	$(window).resize(function(){
		var winW = $(window).width();
		$('.banner').css('width',winW);
		$('.banner img').css('width',winW);
	})

// 我们的服务
	$('.service>ul>li').on('mouseenter',function(){
		var newurl = $(this).find('.service_type').attr('data-src');
		$(this).addClass('service_bg').css("opacity", 0.1).animate({opacity: 1}, 500);
		$(this).find('.service_type').attr('src', newurl);
	})
	$('.service>ul>li').on('mouseleave',function(){
		var oldurl = $(this).find('.service_type').attr('data-old');
		$(this).removeClass('service_bg').css("opacity", 0.1).animate({opacity: 1}, 500);
		$(this).find('.service_type').attr('src', oldurl);
	})

// 我们的优势
	$('.advantage>ul>li').on('mouseenter',function(){
		var newurl = $(this).find('img').attr('data-src');
		$(this).addClass('ad_bg').css("opacity", 0.1).animate({opacity: 1}, 1000);
		$(this).find('img').attr('src', newurl);
	})
	$('.advantage>ul>li').on('mouseleave',function(){
		var oldurl = $(this).find('img').attr('data-old');
		$(this).removeClass('ad_bg').css("opacity", 0.1).animate({opacity: 1}, 1000);
		$(this).find('img').attr('src', oldurl);
	})

// 箭头
	$('.ad_more').on('mouseenter',function(){
		$(this).find('span').addClass('mg_lf');
	});
	$('.ad_more').on('mouseleave',function(){
		$(this).find('span').removeClass('mg_lf');
	});

// 关于千盟(查看详情)
	$('.about_rt>a').on('mouseenter',function(){
		var src = $(this).find('img').attr('src');
		$(this).addClass('check_detail');
		$(this).find('img').attr('src', src.replace('_g.png', '_y.png'));
	});
	$('.about_rt>a').on('mouseleave',function(){
		var src = $(this).find('img').attr('src')
		$(this).removeClass('check_detail');
		$(this).find('img').attr('src', src.replace('_y.png','_g.png'))
	});

// 关于千盟(屏幕缩放)
	$(window).resize(function(){
		var winWidth = $(window).width();
		if(winWidth<1560){
			$('.about>div').css("width",winWidth);
		}else{
			$('.about>div').css("width","1560px");
		}
	})

// 我们的团队
	$('.team_member>li>a').on('mouseenter',function(){
			var src = $(this).find('img').attr('src');
			$(this).addClass('check_detail');
			$(this).find('img').attr('src', src.replace('_g.png', '_y.png'));
		});
	$('.team_member>li>a').on('mouseleave',function(){
			var src = $(this).find('img').attr('src')
			$(this).removeClass('check_detail');
			$(this).find('img').attr('src', src.replace('_y.png','_g.png'))
		});
})


// 全屏滚动第一屏
	$(document).ready(function(){
	    var height = $(window).height()-100;
	    var isFinish = true;
	    var scroll = function(height){
	    	isFinish = false;
		    $("html,body").animate({scrollTop:height},"500","linear",function(){
		        isFinish = true;
		    });
		};
		$(window).on('mousewheel', function(e){
			if(isFinish){
	            var scroll_top = $(document).scrollTop()
	            if(e.deltaY > 0){ 
	            	if(scroll_top < height){           
	                	scroll(0);
	            	}
	            }else if(e.deltaY < 0){
	                if(scroll_top < height){           
	                	scroll(height);
	            	}
	            }
	        }
		});
	});



