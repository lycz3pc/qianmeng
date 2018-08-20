var dataUrl = '/admin/personnel/list';
var icon = "<i class='fa fa-times-circle'></i> ";
var loadIndex;

var dataQueryParams = function(params) {
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		name: params.searchText,
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
		//显示照片
		var $img = $('<img style="margin-bottom: 10px; width: 100px; height: auto; display: block; "/>');
		$('#fileList').empty().append($img);
		$img.attr('src', row.picture);
		
		edit("修改","/admin/personnel/save");
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/personnel/delete',
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
					} else {}
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
	title: '所属团队',
	align: 'center',
	field: 'categoryName',
	valign: 'middle'
}, {
	title: '姓名',
	align: 'center',
	field: 'name',
	valign: 'middle'
},{
	title: '职位',
	align: 'center',
	field: 'position',
	valign: 'middle'
}, {
	title: '照片',
	align: 'center',
	field: 'picture',
	valign: 'middle',
	formatter : function (value, row, index){
		return '<a href="'+value+'" target="_blank"><img width="60px" height="60px" src="'+value+'" title="'+value+'" alt="'+value+'"></a>';
	}
}, {
	title: '排序',
	align: 'center',
	field: 'sort',
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
	title: '更新时间',
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
		edit("新增","/admin/personnel/save");
	});

	//获取团队分类
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
			name:{
				required:true
			},
			position:{
				required:true
			},
		    sort:{
		    	required:false,
		    	digits:true
		    }
		},
		messages:{
			categoryId:{
				required:"请选择团队"
			},
			name:{
				required:"不能为空"
			},
			position:{
				required:"不能为空"
			},
			sort:{
				digits: "必须输入整数"
			}
		}
	});
	
	uploader.on('beforeFileQueued', function(file) {
		uploader.reset();
	});
	
	uploader.on('fileQueued', function(file) {
		var $img = $('<img style="margin-bottom:10px; width:100px; height:auto;"/>');
		$('#fileList').empty();
		$('#fileList').append($img);
		uploader.makeThumb(file, function(error, src) {
			if (error) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			$img.attr('src', src);
		}, 2.4, 110);
	});
	
	//用表单的值覆盖文件对象属性值
	uploader.on('uploadBeforeSend', function(block, data) {
		var temp = $('#editForm').getForm();
		for ( var key in temp) {
			data[key] = temp[key];
		}
	});
	
	uploader.on('uploadSuccess', function(file, result) {
		if (result.code != 0) {
			layer.msg(result.msg);
			var files = uploader.getFiles();
			if (files.length > 0){
				var file = files.pop();
				file.statusText = '';
				uploader.reset();
				uploader.addFiles(file);
			}
		} else {
			uploader.removeFile(file);
			layer.msg("保存成功!");
			$('#dataTable').bootstrapTable('refresh');
			layer.closeAll();
		}
	});

	uploader.on('uploadError', function(file, reason) {
		layer.msg("保存出错,请检查数据!");
	});

	uploader.on('uploadComplete', function(file) {
		layer.close(loadIndex);
	});
	
	uploader.on('uploadFinished', function(file) {
		uploader.reset();
	});
});

//初始化Web Uploader
var uploader = WebUploader.create({
	// swf文件路径
	swf: '/static/plugins/webuploader/Uploader.swf',
	// 文件接收服务端。
	server: '/admin/personnel/save',
	// 选择文件的按钮。可选。
	// 内部根据当前运行是创建，可能是input元素，也可能是flash.
	pick: {
		id: '#filePicker',
		multiple: false,
	},
	// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传!
	resize: false,
	// 只允许选择图片文件。
	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
	}
});

function edit(title,url) {
	// 处理选择图片按钮点击无效
	$('#filePicker div:eq(1)').css('width', '82px').css('height', '39px');
	
	layer.open({
		id: 'id',
		type: 1,
		title: title,
		area: ['600px', '580px'],
		content: $('#editLayer'),
		shade: 0.1,
		btn: [ '保存', '取消' ],
		yes: function(index, layero) {
			if (!$('#editForm').valid()) {
				return $('#editForm').valid();
			}
			//编辑时文件未重新上传，files不存在，不能调用upload(); ajax方法保存
			var files = uploader.getFiles();
			if (files.length > 0){
				uploader.upload();
			}else{
				if($("#id") == ""){
					layer.msg("请选择文件!");
					return false;
				}
				$.ajax({
					url: url,
					type: 'POST',
					data: $('#editForm').getForm(),
					dataType: 'json',
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
			}
		},
		success: function (){ },
		end: function(index, layero) {
			// 图片列表置空
			$('#fileList').empty();
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
			column:'personnel'
		},
		dataType: 'json',
		success: function(data) {
			if(data.type=='success'){
				var opt = '<option value="">请选择团队</option>';
				$.each(data.data, function(index, item){
					opt = opt + '<option value='+item.id+'>'+item.categoryName+'</option>';
				});
				
				$("select[name='categoryId']").html(opt);
				$("select[name='searchCategory']").html(opt);
			}
		}
	});
}