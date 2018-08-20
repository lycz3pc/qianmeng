var dataUrl = '/admin/subcompany/list';
var icon = "<i class='fa fa-times-circle'></i> ";
var loadIndex;
var ueditor;

var provinces = new Array('北京','天津','上海','重庆','河北','山西','辽宁','吉林','黑龙江',
		'江苏','浙江','安徽','福建','江西','山东','河南','湖北','湖南','广东','甘肃','四川','贵州',
		'海南','云南','青海','陕西','广西','西藏','宁夏','新疆','内蒙古','香港','澳门','台湾');

var dataQueryParams = function(params) {
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		name: params.searchText,
		province: $("#searchProvince").val()
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
		
		//初始化富文本编辑框
		setTimeout(function (){ueditor.setContent(row.detail);}, 666);
		
		edit("修改","/admin/subcompany/save");
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/subcompany/delete',
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
	title: '名称',
	align: 'center',
	field: 'name',
	valign: 'middle'
},{
	title: '省份',
	align: 'center',
	field: 'province',
	valign: 'middle'
}, {
	title: '图片',
	align: 'center',
	field: 'picture',
	valign: 'middle',
	formatter : function (value, row, index){
		return '<a href="'+value+'" target="_blank"><img width="60px" height="60px" src="'+value+'"></a>';
	}
}, {
	title: '简介',
	align: 'center',
	field: 'summary',
	valign: 'middle'
}, {
	title: '排序',
	align: 'center',
	field: 'sort',
	valign: 'middle'
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
		edit("新增","/admin/subcompany/save");
	});

	//初始化省份下拉框
	var opt = '<option value="">请选择省份</option>';
	$.each(provinces, function(index, item){
		opt = opt + '<option value='+item+'>'+item+'</option>';
	});
	$("select[name='province']").html(opt);
	$("select[name='searchProvince']").html(opt);
	
	//查询
	$('#searchForm select').on('change', function (){
		$('#dataTable').bootstrapTable('selectPage', 1)
	 });

	//类型表单验证
	$("#editForm").validate({
		rules:{
			name:{
				required:true
			},
			province:{
				required:true
			},
			summary:{
				required:false,
				maxlength:200
			},
		    sort:{
		    	required:false,
		    	digits:true
		    }
		},
		messages:{
			name:{
				required:"不能为空"
			},
			province:{
				required:"请选择省份"
			},
			summary:{
				maxlength:"简介不能超过200字"
			},
			sort:{
				digits: "必须输入整数"
			}
		}
	});
	
	ueditor = UE.getEditor('ueditor', {
		zIndex : 29891014,
		serverUrl : '/admin/article/upload',
		initialFrameWidth : '95%',
        initialFrameHeight: 300
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
	server: '/admin/subcompany/save',
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
			uploader.reset();
			ueditor.reset();
			// 图片列表置空
			$('#fileList').empty();
			$('.form-group [name]').val('');
			$('.form-group').removeClass('has-success').removeClass('has-error').find('span').html('');
		}
	});
}