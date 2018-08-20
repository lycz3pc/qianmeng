$(function(){
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

// 置顶漂浮框
	$(".info_box>li").on('mouseenter',function(){
		$(".open_box").css({display: 'block',});
		var index = $(this).index();
		$(".open_box>li").eq(index).addClass('showinfo').siblings().removeClass('showinfo')
	})
	$(".float_box").on('mouseleave',function(){
		$(".open_box").css({display: 'none',});
	})

	$(".top_floor").click(function() {
		$("html, body").animate({
		    scrollTop: '$(window).scrollTop()' }, {duration: 1000,easing: "swing"});
		return false;
	  });


// tab切换
	$('.team_name').on('click','li',function(){
		$(this).addClass('tl_selected').siblings().removeClass('tl_selected');
		var index = $(this).index();
		$('.team_member>li').eq(index).addClass('ct_selected').siblings().removeClass('ct_selected');
	});

// 服务团队动画
	$('.team_member>li').on('mouseenter','ul>li', function(){
		$(this).addClass('man_selected');
		var imgObj=$(this).find('img');	
		imgObj.stop().css({width: "386px",height: "257px",left:"0px",top:"0px"});	
		var wValue=1.1 * imgObj.width();
		var hValue=1.1 * imgObj.height();
		imgObj.animate({width: wValue, height: hValue, left:("-"+(0.1 * imgObj.width())/2), top:("-"+(0.1 * imgObj.height())/2)}, 500);
	});
	$('.team_member>li').on('mouseleave','ul>li', function(){
		$(this).removeClass('man_selected');
		$(this).find('img').stop().animate({width: "386px", height: "257px", left:"0px", top:"0px"}, 500);
	});	

// 分页
	// $("#page").paging({
	// 		pageNo:1,
	// 		totalPage: 12,
	// 		totalSize: 300,
	// 		callback: function(num) {
	// 			// alert(num)
	// 		}
	// 	});
    /*
		// 模拟ajax数据用以下方法，方便用户更好的掌握用法
		//参数为当前页
		ajaxTest(1);
		
		function ajaxTest(num) {
			$.ajax({
				url: "table.json",
				type: "get",
				data: {},
				dataType: "json",
				success: function(data) {
					console.log(data);
					//分页
					$("#page").paging({
						pageNo: num,
						totalPage: data.totalPage,
						totalSize: data.totalSize,
						callback: function(num) {
							ajaxTest(num)
						}
					})
				}
			})
		}
		*/
})


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

