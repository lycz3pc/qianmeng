var dataUrl = '/admin/job/list';
var icon = "<i class='fa fa-times-circle'></i> ";
var loadIndex;
var ueditor;

var dataQueryParams = function(params) {
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		jobName: params.searchText,
		categoryId: $("#searchCategory").val()
	};
	return temp;
}

function buttons(value, row, index) {
	 var e = '<button id="rowEditor" type="button" class="btn btn-primary btn-xs"><i class="fa fa-edit"></i>编辑</button> ';  
     var d = '<button id="rowDelete" type="button" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>删除</button> ';
     return e+d;
}

var events = {
	'click #rowEditor': function(e, value, row, index) {
		$('#editForm').setForm(row);
		
		//初始化富文本编辑框
		setTimeout(function (){ueditor.setContent(row.jobDetail);}, 666);
		
		edit("修改","/admin/job/update");
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/job/delete',
				type: 'POST',
				data: {
					id: row.id
				},
				dataType: 'json',
				beforeSend: function() {
					loadIndex = layer.load();
				},
				success: function(data) {
					if (data.code == 0) {
						layer.closeAll('page');
						$('#dataTable').bootstrapTable('refresh');
					} else {
							
					}
				},
				complete: function() {
					layer.close(loadIndex);
				}
			});
		});
	}
}

var dataColumns = [ {
	title: '序号',
    align: 'center',
    valign: 'middle',
    formatter: function (value, row, index) {  
        return index+1;  
    } 
}, {
	title: '所属类型',
	align: 'center',
	field: 'categoryName',
	valign: 'middle'
},{
	title: '职位名称',
	align: 'center',
	field: 'jobName',
	valign: 'middle'
}, {
	title: '工作地点',
	align: 'center',
	field: 'jobCity',
	valign: 'middle'
}, {
	title: '创建时间',
	align: 'center',
	field: 'createTime',
	valign: 'middle',
	cellStyle : function (value, row, index) {
        return {css: {"min-width": "150px"}};
    }
}, {
	title: '修改时间',
	align: 'center',
	field: 'updateTime',
	valign: 'middle',
	cellStyle : function (value, row, index) {
        return {css: {"min-width": "150px"}};
    }
}, {
	title: '操作',
	align: 'center',
	field: 'Button',
	valign: 'middle',
	events: events,
	formatter: buttons,
	cellStyle : function (value, row, index) {
        return {css: {"min-width": "220px"}};
    }
}];

$(function() {
	$('#add').on('click', function() {
		edit("新增","/admin/job/save");
	});

	//获取职位类型
	getCategoryList();
	
	//查询
	$('#searchForm select').on('change', function (){
		$('#dataTable').bootstrapTable('selectPage', 1)
	 });

	//类型表单验证
	$("#editForm").validate({
		rules:{
			categoryId:{
				required:true
			},
			jobName:{
				required:true
			},
			jobCity:{
				required:true
			},
			jobDetail:{
				required:true
			}
		},
		messages:{
			categoryId:{
				required:"请选择职位类型"
			},
			jobName:{
				required:"不能为空"
			},
			jobCity:{
				required:"不能为空"
			},
			jobDetail:{
				required:"不能为空"
			}
		}
	});
	
	ueditor = UE.getEditor('ueditor', {
		zIndex : 29891014,
		serverUrl : '/admin/article/upload',
		toolbars: [
		    ['fullscreen', 'source', 'undo', 'redo', 'bold','italic','underline','fontfamily','fontsize','justifyleft',
		      'justifyright','justifycenter','justifyjustify','forecolor','backcolor','insertorderedlist','insertunorderedlist','lineheight']
		],
		initialFrameWidth : '95%',
        initialFrameHeight: 300
	});
});


function edit(title,url) {
	layer.open({
		id: 'id',
		type: 1,
		title: title,
		area: ['100%', '100%'],
		scrollbar: false,
		maxmin: true,
		content: $('#editLayer'),
		shade: 0.1,
		btn: [ '保存', '取消' ],
		yes: function(index, layero) {
			if (!$('#editForm').valid()) {
				return $('#editForm').valid();
			}
			$.ajax({
				url: url,
				type: 'POST',
				data: $('#editForm').getForm(),
				dataType: 'json',
				beforeSend: function() {
					if (!$('#editForm').valid()) {
						return $('#editForm').valid();
					}
					loadIndex = layer.load();
				},
				success: function(data) {
					if (data.code == 0) {
						layer.closeAll();
						$('#dataTable').bootstrapTable('refresh');
					} else {
						layer.close(loadIndex);
						layer.msg(data.msg);
					}
				}
			});
		},
		success: function (){ },
		end: function(index, layero) {
			ueditor.reset();
			$('.form-group [name]').val('');
			$('.form-group').removeClass('has-success').removeClass('has-error').find('span').html('');
		}
	});
}

//获取所有职位类型
function getCategoryList(){
	$.ajax({
		url: '/admin/category/getListByColumn',
		type: 'POST',
		data: {
			column:'job'
		},
		dataType: 'json',
		success: function(data) {
			if(data.type=='success'){
				var opt = '<option value="">请选择职位类型</option>';
				$.each(data.data, function(index, item){
					opt = opt + '<option value='+item.id+'>'+item.categoryName+'</option>';
				});
				
				$("select[name='categoryId']").html(opt);
				$("select[name='searchCategory']").html(opt);
			}
		}
	});
}