	// tab切换
	$('.team_name').on('click','li',function(){
		$(this).addClass('tl_selected').siblings().removeClass('tl_selected');
		var index = $(this).index();
		$('.team_member>li').eq(index).addClass('ct_selected').siblings().removeClass('ct_selected');
	})

	// 头部下拉列表
	$('.top_nav>li').on('click',function(){
		$(this).addClass('nav_selected').siblings().removeClass('nav_selected');
		$(this).find('.about_list').hide();
	});
	$('.top_nav>li').on('mouseenter',function(){
		$(this).find('.about_list').stop(true, false).slideDown();
	});
	$('.top_nav>li').on('mouseleave',function(){
		$(this).find('.about_list').stop(true, false).slideUp();
	});

	// 新闻列表页
	$('#new_about>li').on('mouseenter','ul>li', function(){
		$(this).addClass('hover_color');
		var imgObj=$(this).find('img');	
		imgObj.stop().css({width: "283px",height: "190px",left:"0px",top:"0px"});	
		var wValue=1.1 * imgObj.width();
		var hValue=1.1 * imgObj.height();
		imgObj.animate({width: wValue, height: hValue, left:("-"+(0.1 * imgObj.width())/2), top:("-"+(0.1 * imgObj.height())/2)}, 500);
	});
	
	$('#new_about>li').on('mouseleave','ul>li', function(){
		$(this).removeClass('hover_color');
		$(this).find('img').stop().animate({width: "283px", height: "190px", left:"0px", top:"0px"}, 500);
	});	
	
	// 多行文本显示省略号
	function overhidden(element,maxLength){
		$(element).each(function(){
			if($(this).text().length>maxLength){
		         //截取字符串
		        $(this).text($(this).text().substring(0,maxLength));
		        //多余的用省略号显示
		        $(this).html($(this).html()+'......');
	    	}
	    })
	}
	
	// 文本超出隐藏
	overhidden(".span_overhide",86);


