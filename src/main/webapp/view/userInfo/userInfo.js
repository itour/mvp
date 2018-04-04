var parma;
(function() {

	function UserInfo() {

	}
	;

	var userInfo = new UserInfo();

	UserInfo.prototype.initSelectOpts = function(datas, name, id){
		var bus = _.uniq(_.pluck(datas, name));
		var buopt = null;
		_.each(bus, function(value, index){
			if(value != null && value != 'null'){
				buopt+= '<option value="'+ value +'">' + value + '</option>';
			}
		});
		$("#" + id).html(buopt);	
	};
	
	UserInfo.prototype.getPassword = function() {
		$.ajax({
			type : 'POST',
			data : {
				'password' : password
			},
			async : false,
			url : getRootPath() + "/user/updateUser",
			success : function(data) {
				var datas = data.rows;
				userInfo.initSelectOpts(datas, 'user', 'password');
			}
		});
	};

	$(document).ready(function() {
		
//		$('hidUser').readOnly = r;
	});
})();