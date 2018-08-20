
// 招聘页
	$(".jobs_content").on("click", 'li>ul>:not(:last-child)',function(event) {
		if ($(this).parent().hasClass('inactive')) {
			$(this).parent().find('#apply_button').removeClass('apply_button').addClass('apply_now');
			$(this).parent().find('i').removeClass('icon-arrow-down').addClass('icon-arrow-up');
			$(this).parent().removeClass("inactive").next().slideDown();
			var sibling = $(this).parent().parent().siblings();

			sibling.find('ul').find('#apply_button').removeClass('apply_now').addClass('apply_button');
			sibling.find('ul').find('i').removeClass('icon-arrow-up').addClass('icon-arrow-down');
			sibling.find('ul').addClass("inactive")
			sibling.find('.job_des').slideUp();
		} else {
			$(this).parent().find('#apply_button').removeClass('apply_now').addClass('apply_button');
			$(this).parent().find('i').removeClass('icon-arrow-up').addClass('icon-arrow-down');
			$(this).parent().addClass("inactive").next().slideUp();
		}
	});
	$('.view_top>li').on('click',function(){
		$(this).addClass('job_choosed').siblings('li').removeClass('job_choosed')
	})
	
// 分公司列表
	$('#subscribe #new_about>li').on('mouseenter','ul>li', function(){
		$(this).addClass('hover_color');
		var imgObj=$(this).find('img');	
		imgObj.stop().css({width: "387px",height: "258px",left:"0px",top:"0px"});	
		var wValue=1.1 * imgObj.width();
		var hValue=1.1 * imgObj.height();
		imgObj.animate({width: wValue, height: hValue, left:("-"+(0.1 * imgObj.width())/2), top:("-"+(0.1 * imgObj.height())/2)}, 500);
	});
	$('#subscribe #new_about>li').on('mouseleave','ul>li', function(){
		$(this).removeClass('hover_color');
		$(this).find('img').stop().animate({width: "387px", height: "258px", left:"0px", top:"0px"}, 500);
	});	

// 多行文本显示省略号
	overhidden(".span_overhide",115);
