$(function(){
	haschange();
	
	window.onhashchange = haschange;
})

function haschange(){
	var urlTemp = window.location.href.split('#');
	// 材料产品采购服务
	var index = 0;
	if(urlTemp.length == 2) {
		// 工程施工服务
		if (urlTemp[1] == 'construction') {
			index = 1;
		}
		// 设计服务
		if (urlTemp[1] == 'design') {
			index = 2;
		}
		// 造价咨询
		if (urlTemp[1] == 'cost') {
			index = 3;
		}
		
		// 园林风水
		if (urlTemp[1] == 'park') {
			index = 4;
		}
	}
	$('.team_name li:eq(' + index + ')').addClass('tl_selected').siblings().removeClass('tl_selected');
	$('.team_member>li:eq(' + index + ')').addClass('ct_selected').siblings().removeClass('ct_selected');
}