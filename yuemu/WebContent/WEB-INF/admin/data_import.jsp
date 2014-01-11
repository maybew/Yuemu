<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="content" class="content">
	<div
		style="padding-bottom: 12px; border-bottom: 1px solid #DDD; color: #222; margin-bottom: 20px; font-size: 14px; color: #005179">数据导入</div>

	<ul style="padding-bottom: 20px">
		<li style="margin-bottom: 8px;">你可以导入你的数据库备份文件（*.dat）或者资源备份文件（*.zip）。</li>
		<li>注意：导入数据库会清空现有数据库的内容.</li>
	</ul>
	<div>
		<div class="upload" style="background: #DDD;
width: 500px;
height: 400px;
padding: 20px;">
			<div id="uploader">
				<input type="file" name="uploadify" id="uploadify" />
				<div id="uploader_queue"></div>
			</div>
			<div id="uploader_msg"></div>
		</div>
	</div>
	<script>
		var uploadify_config = {
			'swf' : 'swf/uploadify.swf',
			'uploader' : '/upload',
			'queueID' : 'uploader_queue',
			'auto' : true,
			'removeCompleted' : false,
			'muti' : true,
			'fileSizeLimit' : '20000MB',
			'queueSizeLimit' : 2,
			'buttonText' : '浏览',
			'uploadLimit' : 2,
			'overrideEvents' : [ 'onSelectError' ],
			'fileTypeExts' : '*.dat; *.zip;',
			'onSelect' : function(file) {
				check_upload();
			},
			'onCancel' : function(file) {
				setTimeout(check_upload, 2000);
			},
			'onSelectError' : function(file, errorCode, errorMsg) {
				if (errorCode == SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
					$('#uploader_msg').html("超过上传数目上限");
				}
				if (errorCode == SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT) {
					$('#uploader_msg').html("单个文件不能超过20000M");
				}
				if (errorCode == SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE) {
					$('#uploader_msg').html("文件大小不能为0");
				}
				if (errorCode == SWFUpload.QUEUE_ERROR.INVALID_FILETYPE) {
					$('#uploader_msg').html("不支持的文件类型");
				}
			},
			'onUploadSuccess' : function(file, data, response) {
				$('#' + file.id + '>.cancel').css('visibility', 'hidden');
			}
		};
		$(document).ready(function() {
			$("#uploadify").uploadify(uploadify_config);
		});
	</script>
</div>