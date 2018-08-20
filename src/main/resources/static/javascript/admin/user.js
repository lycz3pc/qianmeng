var dataUrl = '/admin/user/list';
var icon = "<i class='fa fa-times-circle'></i>  ";
var title;
var validate;

var dataQueryParams = function (params){
	var temp = {
		current: params.pageNumber,
		size: params.pageSize,
		keyword : params.searchText
	};
	return temp;
}

var events = {
	'click #rowEditor': function(e, value, row, index) {
		$('#editForm').setForm(row);
		$('#password').val(null);
		edit("修改用户","/admin/user/update");
	},
	'click #rowDelete': function(e, value, row, index) {
		layer.confirm('确认删除?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/user/delete',
				type: 'POST',
				data: {
					ids: row.id
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
	},
	'click #rowUpdateStatus': function(e, value, row, index) {
		var tips = row.status == 1 ? '确认禁用？' : '确认开启？';
		var status = row.status == 1 ? 0 : 1;
		layer.confirm(tips, function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/user/updateStatus',
				type: 'POST',
				data: {
					ids: row.id,
					status: status
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
	},
	'click #rowReset': function(e, value, row, index) {
		layer.confirm('确认重置密码?', function(index) {
			layer.close(index);
			$.ajax({
				url: '/admin/user/resetPassword',
				type: 'POST',
				data: {
					ids: row.id
				},
				dataType: 'json',
				beforeSend: function() {
					loadIndex = layer.load();
				},
				success: function(data) {
					if (data.code == 0) {
						layer.closeAll('page');
						$('#dataTable').bootstrapTable('refresh');
						layer.msg("重置成功,默认密码123456",{icon:1});
					} else {
						layer.msg("重置失败!",{icon:2});	
					}
				},
				complete: function() {
					layer.close(loadIndex);
				}
			});
		});
	},
	
}

var dataColumns = [{
	title: '序号',
    align: 'center',
    valign: 'middle',
    formatter: function (value, row, index) {  
        return index+1;  
    } 
},{
	title: '账号',
    align: 'center',
    field: 'username',
    valign: 'middle'
},{
	title: '姓名',
    align: 'center',
    field: 'realName',
    valign: 'middle'
},{
	title: '手机',
    align: 'center',
    field: 'phone',
    valign: 'middle'
},{
	title: '角色',
    align: 'center',
    field: 'roleId',
    valign: 'middle',
    formatter: function (value, row, index) {
    	if(row.roleId == '1'){
    		return "管理员";
    	}else if(row.roleId == '2'){
    		return "编辑员";
    	}
    }
},{
	title: '状态',
    align: 'center',
    field: 'status',
    valign: 'middle',
    formatter: function (value, row, index) {
    	if(row.status == '1'){
    		return "正常";
    	}
    	return "禁用";
    }
},{
	title: '创建时间',
    align: 'center',
    field: 'createTm',
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
//     var d = '<button id="rowDelete" type="button" class="btn btn-danger btn-xs"><i class="fa fa-ban"></i>删除</button> ';
	 var u = '';
	 if(row.status == 1){
		 u = '<button id="rowUpdateStatus" type="button" class="btn btn-danger btn-xs"><i class="fa fa-ban"></i>禁用</button> ';
	 }else{
		 u = '<button id="rowUpdateStatus" type="button" class="btn btn-primary btn-xs"><i class="fa fa-check"></i>开启</button> ';
	 }
     var f = '<button id="rowReset" type="button" class="btn btn-warning btn-xs"><i class="fa fa-reply" aria-hidden="true"></i>重置密码</button> ';
     return e+u+f;
}

function edit(title,url){
	layer.open({
		id: 'id',
		type: 1,
		title: title,
		area: ['400px', '500px'],
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
					username: $.trim($('input[name="username"]').val()),
					password: $('input[name="password"]').val(),
					realName: $.trim($('input[name="realName"]').val()),
					phone: $('input[name="phone"]').val(),
					roleId: $('select[name="roleId"]').val(),
					status: $('input[name="status"]:checked').val()
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
	//getRoles(null);
	
	$('#add').on('click', function (){
		//重置表单
		$('.form-group [name]').val('');
		edit("新增用户","/admin/user/save");
	});
	
	
	validate = $('#editForm').validate({
		rules: {
		    username: {
		    	required:true,
		    	username:true,
		    	remote:{
	    	    	type:"post",
	    	    	url:"/admin/user/valiUsername",
	    	    	data:{
	    	    		id:function(){
	    	    			return $('input[name="id"]').val();
	    	    		},
	    	    		username:function(){ 
	    	    			return $('input[name="username"]').val();
	    	    		}
	    	    	},
	    	    	dataFilter: function (data, type) {//判断控制器返回的内容
	    	    		var res = JSON.parse(data)
	                     if (res.type != "success") {
	                         return false;   
	                     }
	                         return true;
	                 }
	    	    }
		    },
		    password:{
		    	required:true,
		    	minlength: 6,
			    maxlength:30
		    },
		    newPassword:{
		    	required:true,
		    	minlength: 6,
			    maxlength:30,
			    equalTo: "#password"
		    },
		    phone:{
		    	required:false,
		    	phone:true
		    }
		},
		messages: {
		      username: {
			        required: "不能为空",
			        remote:"账号已存在。"
		      },
		      password: {
			        required: "不能为空",
			        minlength: "密码至少由6个字符组成",
			        maxlength: "密码至多由20个字符组成"
		      },
		      newPassword: {
		    	    required: "不能为空",
			        minlength: "密码至少由6个字符组成",
			        maxlength: "密码至多由20个字符组成",
			        equalTo: "密码输入不一致"
		      }
			}
	});
	
	
	//登录账号规则
	jQuery.validator.addMethod("username", function (value, element) {
	    var username = /^[a-zA-z]\w{3,15}$/;
	    return this.optional(element) || (username.test(value));
	}, "字母、数字、下划线组成，字母开头，4-16位");
	
	//手机验证规则
	jQuery.validator.addMethod("phone", function (value, element) {
	    var phone = /^1\d{10}$/;
	    return this.optional(element) || (phone.test(value));
	}, "手机格式有误");
});

