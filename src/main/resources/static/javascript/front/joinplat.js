$(function(){
	haschange();
	
	window.onhashchange = haschange;
})

function haschange(){
	var urlTemp = window.location.href.split('#');
	//供应商入驻
	var index = 0;
	if(urlTemp.length == 2) {
		// 设计团队入驻
		if (urlTemp[1] == 'design') {
			index = 1;
		}
		// 施工团队入驻
		if (urlTemp[1] == 'construction') {
			index = 2;
		}
		// 维修团队入驻
		if (urlTemp[1] == 'repair') {
			index = 3;
		}
		
	}
	$('.team_name li:eq(' + index + ')').addClass('tl_selected').siblings().removeClass('tl_selected');
	$('.team_member>li:eq(' + index + ')').addClass('ct_selected').siblings().removeClass('ct_selected');
}