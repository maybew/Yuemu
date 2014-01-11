function publish(id) {
	if (confirm("是否确认要发布？")) {
		$.post("manage", {
			"action" : "publish",
			"id" : id
		}, function() {
			$('#r_' + id).remove();
		});
	}
}

function remove(id) {
	if (confirm("是否确认要删除？")) {
		$.post("manage", {
			"action" : "remove",
			"id" : id
		}, function() {
			$('#r_' + id).remove();
		});
	}
}

function user_tonormal(id) {
	if (confirm("是否取消该用户的管理员权限？")) {
		$.post("manage", {
			"action" : "user_tonormal",
			"id" : id
		}, function() {
			$('#u_' + id).remove();
		});
	}
}

function user_toadmin(id) {
	if (confirm("是否将此用户设为管理员？")) {
		$.post("manage", {
			"action" : "user_toadmin",
			"id" : id
		}, function() {
			$('#u_' + id).remove();
		});
	}
}

function user_remove(id) {
	if (confirm("是否删除该用户？")) {
		$.post("manage", {
			"action" : "user_remove",
			"id" : id
		}, function() {
			$('#u_' + id).remove();
		});
	}
}