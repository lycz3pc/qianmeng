$(function(){
	haschange();
	
	window.onhashchange = haschange;
})

function haschange(){
	var urlTemp = window.location.href;
	var keyword = "example";
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