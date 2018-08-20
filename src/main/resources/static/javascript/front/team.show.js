$(function(){
	haschange();
	
	window.onhashchange = haschange;
})

function haschange(){
	var urlTemp = window.location.href;
	var keyword = "showteam";
	var index = urlTemp.indexOf(keyword);
	var category = urlTemp.substring((index+1) + keyword.length);
	var i= 0;
	$('.team_name li').each(function(index,item){
		if($(item).attr("id") == category){
			i = index;
		}
	})
	$('.team_name li:eq(' + i + ')').addClass('tl_selected').siblings().removeClass('tl_selected');
	$('.team_member li:eq(' + i + ')').addClass('ct_selected').siblings().removeClass('ct_selected');
}

$('.its_example p').on('mouseenter','a>img',function(){
	var newUrl = $(this).attr('data-src');
	$(this).attr('src',newUrl);
})
$('.its_example p').on('mouseleave','a>img',function(){
	var oldUrl = $(this).attr('data-old');
	$(this).attr('src',oldUrl);
})