var dataUrl = '/admin/category/list';
var icon = "<i class='fa fa-times-circle'></i>  ";
var title;
var validate;

var dataQueryParams = function (params){
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		categoryName: params.searchText,
		categoryColumn: $("#searchColumn").val()
	};
	return temp;
}

var events = {
	'click #rowEditor': function(e, value, row, index) {
		$('#editForm').setForm(row);
		edit("修改分类","/admin/category/update");
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/category/delete',
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

var dataColumns = [{
	title: '序号',
    align: 'center',
    valign: 'middle',
    formatter: function (value, row, index) {  
        return index+1;  
    } 
},{
	title: '所属栏目',
	align: 'center',
	field: 'categoryColumn',
    valign: 'middle',
    formatter: function (value, row, index) {
    	if(row.categoryColumn == 'article'){
    		return "新闻动态";
    	}else if(row.categoryColumn == 'case'){
    		return "精品案例";
    	}else if(row.categoryColumn == 'job'){
    		return "人才招聘";
    	}else if(row.categoryColumn == 'personnel'){
    		return "团队展示";
    	}else{
    		return "未知";
    	}
    }
},{
	title: '分类名称',
    align: 'center',
    field: 'categoryName',
    valign: 'middle'
},{
	title: '排序',
	align: 'center',
	field: 'sort',
	valign: 'middle'
},{
	title: '操作',
	align: 'center',
	field: 'Button',
    events: events,
    formatter: buttons,
    valign: 'middle'
}

]

function buttons(value,row,index){
	 var e = '<button id="rowEditor" type="button" class="btn btn-primary btn-xs"><i class="fa fa-edit"></i>编辑</button> ';  
     var d = '<button id="rowDelete" type="button" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>删除</button> ';
     return e+d;
}

function edit(title,url){
	layer.open({
		id: 'id',
		type: 1,
		title: title,
		area: ['400px', '300px'],
		content: $('#editLayer'),
		shade: 0.1,
		btn: ['保存', '取消'],
		yes: function(index, layero){
			var loadIndex;
			$.ajax({
				url: url,
				type: 'POST',
				data: {
					id: $('input[name="id"]').val(),
					categoryColumn: $.trim($('select[name="categoryColumn"]').val()),
					categoryName: $('input[name="categoryName"]').val(),
					sort: $('input[name="sort"]').val()
				},
				dataType: 'json',
				beforeSend: function (){
					if(!$('#editForm').valid()){
						return false;
					};
					loadIndex = layer.load();
				},
				success: function (data){
					if (data.code == 0) {
						layer.closeAll('page');
						$('#dataTable').bootstrapTable('refresh');
					} else {

					}
				},
				complete: function (){
					layer.close(loadIndex);
				}
			});
		},
		end: function(index, layero) {
			$('.form-group [name]').val('');
			$('.form-group').removeClass('has-success').removeClass('has-error').find('span').html('');
		}
	});
}

$(function (){
	$('#add').on('click', function (){
		//重置表单
		$('.form-group [name]').val('');
		edit("新增分类","/admin/category/save");
	});
	
	validate = $('#editForm').validate({
		rules: {
			categoryColumn:{
		    	required:true
		    },
		    categoryName:{
		    	required:true
		    },
		    sort:{
		    	required:false,
		    	digits:true
		    }
		},
		messages: {
			  categoryColumn: {
			        required: "请选择所属栏目"
		      },
		      categoryName: {
			        required: "不能为空"
		      },
		      sort: {
			        digits: "必须输入整数"
		      }
			}
	});
	
	$('#searchForm select').on('change', function (){
	   $('#dataTable').bootstrapTable('selectPage', 1);
	});
});

