<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文件上传</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/form.js"></script>
</head>
<body>
	<div>
		<form id="fileUpload" enctype="multipart/form-data"> 
	        <table style="margin-left:30px;margin-top:30px;" border="0">
	        	<tr>
	        		<td>选择附件：</td>
	        		<td style="height:30px;">
	        			<input type="file" name="file" id="file" value="" style="width:260px; height:22px; background:#fff;border:#ddd solid 1px;" />
	        		</td>
	        	</tr>
	        	<tr>
	        		<td> </td>
	        		<td><input id="headerFormSubmit" type="submit" value="上传" style="padding:0px 10px;height:24px;" /></td>
	        	</tr>
	        	<tr>
	        		<td> </td>
	        		<td><input id="headerFormSubmit" type="button" value="下载" 
	        			onclick="location.href = '${pageContext.request.contextPath}/file/download.do?path=/2016/06/27/1467010302730.jpg&name=测试' " /></td>
	        	</tr>
	        </table>
	    	
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			//表单提交开始
			var options = {
				beforeSubmit : checkData,
				success : showResponse,
				url : '${pageContext.request.contextPath}/file/uploadFj.do',
				//url : 'http://111.11.195.109/fileserver/file/uploadFj.do',
				type : "post",
				dataType : "json",
				timeout :600000
			};
			function checkData() {
				var value = $("#file").val();
				//debugger;
				if ($.trim(value).length == 0) {
					alert("请选择文件");
					return false;
				}
				
				//alert($("#fileUpload").form('validate'));
				
				//if (!$("#fileUpload").form('validate')) {
				//	return false;
				//}
				return true;
			}
			function showResponse(data) {
				console.info(data);
			}
			
			$("#fileUpload").live('submit', function() {
				$(this).ajaxSubmit(options);
				return false; //阻止表单默认提交  
			});
			//表单提交结束
		})
	</script>
</body>
</html>