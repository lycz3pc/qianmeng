var dataUrl = '/admin/case/list';

var icon = "<i class='fa fa-times-circle'></i> ";

var editLayer;

var loadIndex;

var categoryId;

var dataQueryParams = function(params) {
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		condition: {
			case_name: params.searchText
		}
	};
	var searchForm = $('#searchForm').serializeArray();
	$(searchForm).each(function (){
		if (this.value != '') {
			temp.condition[this.name] = this.value;
		}
	});
	return temp;
}

function buttons(value, row, index) {
	var temp = '';
	temp += '<button id="rowEditor" type="button" class="btn btn-xs btn-primary" style="margin-right: 10px;"><i class="fa fa-edit"></i> 编 辑</button>';
	temp += '<button id="rowDelete" type="button" class="btn btn-xs btn-danger"><i class="fa fa-trash-o"></i> 删 除</button>';
	return temp;
}

var events = {
	'click #rowEditor': function(e, value, row, index) {
		$('#editForm').setForm(row);
		var arry = row.personnelIds.split(",");
		chosen_multi_select_platformCode(".chosen-select",arry);
		setTimeout(function (){ueditor.setContent(row.caseIntro);}, 666);
		var $img = $('<img style="margin-bottom: 10px;height: 110px;"/>');
		$('#fileList').empty();
		$('#fileList').append($img);
		$img.attr('src', row.caseImage);
		alert
		edit();
		$('#layui-layer' + editLayer + ' .layui-layer-title').html('修改');
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			var data = {id: row.id};
			operation('/admin/case/delete', data);
		});
	}
}

var dataColumns = [ {
	title: '序号',
	align: 'center',
	valign: 'middle',
	formatter: function(value, row, index) {
		return index + 1;
	}
}, {
	title: '分类',
	align: 'center',
	field: 'categoryName',
	valign: 'middle',
	width: 150
}, {
	title: '案例名称',
	align: 'center',
	field: 'caseName',
	valign: 'middle',
	width: 300
},{
	title: '案例图片',
	align: 'center',
	field: 'caseImage',
	valign: 'middle',
	width: 150,
	formatter: function (value, row, index) {
    	return "<a href='"+value+"' target='_blank'><img src='"+row.caseImage+"' width='60' height='60'></a>";
    }
}, {
	title: '项目地点',
	align: 'center',
	field: 'caseAddress',
	valign: 'middle'
}, {
	title: '业主单位',
	align: 'center',
	field: 'caseCompany',
	valign: 'middle'
}, {
	title: '项目规模',
	align: 'center',
	field: 'caseScale',
	valign: 'middle'
}, {
	title: '施工时间',
	align: 'center',
	field: 'caseDate',
	valign: 'middle'
}, {
	title: '创建时间',
	align: 'center',
	field: 'createTime',
	valign: 'middle'
}, {
	title: '修改时间',
	align: 'center',
	field: 'updateTime',
	valign: 'middle'
}, {
	title: '操作',
	align: 'center',
	field: 'Button',
	valign: 'middle',
	events: events,
	formatter: buttons
} ]

$(function(){
		
	ueditor = UE.getEditor('ueditor', {
		zIndex : 29891014,
		serverUrl : '/admin/article/upload',
		initialFrameWidth : '95%',
        initialFrameHeight: 300
	});

	
	iniCategory();
	iniPerson();
	
	$("#personnelIds").trigger("liszt:updated"); 
	
	$('#searchForm select').on('change', function (){
		 $('#dataTable').bootstrapTable('selectPage',1);
	});

	$('#searchForm select').on('change', function() {
		$('#dataTable').bootstrapTable('selectPage', 1);
	});

	$('#addCase').on('click', function() {
		// 重置表单
		edit();
		// refreshZTree();
	});
	
	uploader.on('beforeFileQueued', function(file) {
		uploader.reset();
	});

	uploader.on('fileQueued', function(file) {
		var $img = $('<img style="margin-bottom: 10px;"/>');
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

	uploader.on('uploadBeforeSend', function(block, data) {
		var temp = $('#editForm').getForm('categoryId:' + categoryId);
		for ( var key in temp) {
			data[key] = temp[key];
		}
	});

	uploader.on('uploadSuccess', function(file, result) {
		if (result.code != 0) {
			layer.msg(result.msg);
			var files = uploader.getFiles();
			if (files.length > 0) {
				files[files.length - 1].setStatus('inited');
			}
		} else {
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
	
	
	$('#editForm').validate({
		rules: {
			caseName: {
				required: true,
				maxlength:30
			},
			categoryId: {
				required: true
			},
			sort: {
				required: true,
				digits:true,
		    	max:999
			}
		},
		messages: {}
	});
})

// 初始化Web Uploader
var uploader = WebUploader.create({
	// swf文件路径
	swf: '/plugins/webuploader/Uploader.swf',
	// 文件接收服务端。
	server: '/admin/case/save',
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
		mimeTypes: 'image/*'
	}
});

function operation(url, data){
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
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
}

function iniCategory(){
	$.ajax({
		url: '/admin/category/getListByColumn',
		type: 'POST',
		data:{"column":"case"},
		dataType: 'json',
		success: function (arr){
			var optionstr = '';
			$.each(arr.data,function(index,item){
				optionstr += "<option value='"+item.id+"'>"+item.categoryName+"</option>";
			})
			$("#searchForm select[name='category_id']").append(optionstr);
			$("#editForm select[name='categoryId']").append(optionstr);
		},
		complete: function (){
			layer.close(loadIndex);
		}
	});
}

function iniPerson(){
	$.ajax({
		url: '/admin/case/getListPerson',
		type: 'POST',
		dataType: 'json',
	//	async:false, 
		success: function (obj){
			var optionstr = '';
			$.each(obj.data,function(index,item){
				optionstr += "<option value='"+item.id+"'>"+item.categoryName+"---"+item.name+"</option>";
				console.log(optionstr);
				$("#personnelIds").append(optionstr);
			})
			
			$(".chosen-select").chosen({search_contains:true});
			
			//设置chossen插件的样式
			$('.chosen-container-single').css({"width" : "230px", "margin-left": "12px" });
		},
		complete: function (){
			layer.close(loadIndex);
		}
	});
}

function edit(){
	// 处理选择文件按钮点击无效
	$('#filePicker div:eq(1)').css('width', '82px').css('height', '38px');
	
	editLayer = layer.open({
		id: 'id',
		type: 1,
		area: ['100%', '100%'],
		scrollbar: false,
		maxmin: true,
		title: '新增',
		content: $('#editLayer'),
		shade: 0.1,
		btn: [ '保存', '取消' ],
		yes: function(index, layero) {
			if (!$('#editForm').valid()) {
				return $('#editForm').valid();
			}
			var files = uploader.getFiles();
			if (files.length > 0){
				//loadIndex = layer.load(0, {shade: 0.1, content: '<span class="loading-text">PDF文件为: ' + (Math.ceil(fileSize / 10.24) / 100) + ' MB, 大概需要花费 ' + Math.ceil(fileSize / 12000) + ' 分钟解析成图片, 请耐心等待...</span>'});
				uploader.upload();
			}else{
				if ($.trim(ueditor.getContent()).length == 0) {
					layer.msg('案例简介不能为空');
					return false;
					
				}
				saveContent();
			}
		},
		success: function (){
		},
		end: function(index, layero) {
			// 移除radio选中的<b>样式</b>
		//	$('.iradio_square-green').removeClass('checked').find(':radio').removeAttr('checked');
			$('#fileList').empty();
			uploader.reset();
			// 重置文件选择器
		/*	pdfUploader.reset();*/
			// 移除富文本编辑器
			//KindEditor.remove('#kindeditor');
			// 置空所有input(不包括radio)的值
			$('.form-group [name]:not(:radio)').val('');
			// 移除前端验证样式
			$('.form-group').removeClass('has-success').removeClass('has-error').find('span:not(.input-group-addon)').html('');
		}
	});
}

function saveContent(){
	$.ajax({
		url: '/admin/case/save',
		type: 'POST',
		data: $('#editForm').getForm('categoryId:' + categoryId),
		dataType: 'json',
		beforeSend: function() {
			if (!$('#editForm').valid()) {
				return $('#editForm').valid();
			}
		},
		success: function(data) {
			if (data.code == 0) {
				$('#dataTable').bootstrapTable('refresh');
				layer.closeAll();
			} else {
				layer.msg(data.msg);
			}
		},
		complete: function() {
			layer.close(loadIndex);
		}
	});
}

//选择select回显
function chosen_multi_select_platformCode(select, arr) {
        var length = arr.length;
        var value = '';
        for (i = 0; i < length; i++) {
            value = arr[i];
            $(select + " option[value='" + value + "']").attr('selected', 'selected');
        }
        $(select).trigger("chosen:updated");
}