//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
$.validator.setDefaults({
	highlight: function(element) {
		$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	},
	success: function(element) {
		element.closest('.form-group').removeClass('has-error').addClass('has-success');
	},
	errorElement: 'span',
	errorPlacement: function(error, element) {
		if (element.is(':radio') || element.is(':checkbox')) {
			error.appendTo(element.parent().parent().parent());
		} else {
			error.appendTo(element.parent());
		}
	},
	errorClass: 'help-block m-b-none',
	validClass: 'help-block m-b-none'
});

// 将form里面的内容序列化成json checkbox的值用逗号拼接起来
$.fn.getForm = function(otherString) {
	var serializeObj = {}, array = this.serializeArray();
	$(array).each(function() {
		if (serializeObj[this.name]) {
			serializeObj[this.name] += ',' + this.value;
		} else {
			serializeObj[this.name] = this.value;
		}
	});
	if (otherString != undefined) {
		var otherArray = otherString.split(';');
		$(otherArray).each(function() {
			var otherSplitArray = this.split(':');
			if (otherSplitArray[1] != '' && otherSplitArray[1] != 'undefined') {
				serializeObj[otherSplitArray[0]] = otherSplitArray[1];
			}
		});
	}
	return serializeObj;
};

// 将josn对象赋值给form
$.fn.setForm = function(jsonValue) {
	var obj = this;
	$.each(jsonValue, function(name, ival) {
		var $oinput = obj.find('input[name=' + name + ']');
		if ($oinput.attr('type') == 'checkbox') {
			if (ival !== null) {
				var checkboxObj = $('input[name=' + name + ']');
				var checkArray = ival.split(';');
				for (var i = 0; i < checkboxObj.length; i++)
					for (var j = 0; j < checkArray.length; j++)
						if (checkboxObj[i].value == checkArray[j])
							checkboxObj[i].click();
			}
		} else if ($oinput.attr('type') == 'radio') {
			$oinput.each(function() {
				var radioObj = $('input[name=' + name + ']');
				radioObj.each(function (index, obj){
					if (Number(obj.value) == Number(ival)) {
						$(obj).attr('checked', 'checked');
					}
				});
			});
		} else {
			obj.find('[name=' + name + ']').val(ival);
		}
	})
}