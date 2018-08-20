//搜索框
searchFlag = true;
$(function() {
	TableInit();
});

function TableInit() {
	// 初始化Table
	$('#dataTable').bootstrapTable({
		url: dataUrl, // 请求后台的URL（*）
		method: 'GET', // 请求方式（*）
		toolbar: '#toolbar', // 工具按钮用哪个容器
		striped: true, // 是否显示行间隔色
		cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true, // 是否显示分页（*）
		sortable: false, // 是否启用排序
		sortOrder: "asc", // 排序方式
		queryParams: dataQueryParams,// 传递参数（*）
		queryParamsType: '', // 默认值为 'limit',在默认情况下传给服务端的参数为
		// {search: undefined, sort: undefined, order: "asc", limit: 10, offset: 0}
		// 设置为 '',在这种情况下传给服务器的参数为：
		// {pageSize: 10, pageNumber: 1, searchText: undefined, sortName: undefined, sortOrder: "asc"}
		sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
		pageNumber: 1, // 初始化加载第一页，默认第一页
		pageSize: 10, // 每页的记录行数（*）
		pageList: [ 10, 25, 50, 100 ],// 可供选择的每页的行数（*）
		searchOnEnterKey: false,
		strictSearch: true,
		search: searchFlag,
		minimumCountColumns: 2, // 最少允许的列数
		iconSize: 'outline',
		showColumns: true, // 是否显示所有的列
		showRefresh: true, // 是否显示刷新按钮
		// height: 700, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		clickToSelect: true, // 是否启用点击选中行
		uniqueId: "id", // 每一行的唯一标识，一般为主键列
		cardView: false, // 是否显示详细视图
		detailView: false, // 是否显示父子表
		columns: dataColumns,
		clickToSelect: false
	});
};