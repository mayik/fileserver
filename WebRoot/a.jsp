<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>文件上传</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/form.js"></script>
</head>
<body>

	<form id="fileUpload" enctype="multipart/form-data"> 
        <table style="margin-left:30px;margin-top:30px;" border="0">
        	<tr>
        		<td>选择附件：</td>
        		<td style="height:30px;">
        			<div id="pro${id}" style="width:260px; height:22px; border:#ddd solid 1px;display:none;">
        				<div style="height:22px;width:0%;background-color: green;"></div>
        			</div>
        			<input type="file" name="file" id="file${id}" value="" style="width:260px; height:22px; background:#fff;border:#ddd solid 1px;" />
        		</td>
        	</tr>
        </table>
    	<br/>
	    <div class="dialog-button">
	    	<input id="headerFormSubmit" type="submit" value="上传" style="padding:0px 10px;height:24px;" />
		</div>
	</form>
	
<script type="text/javascript">
	
	
	$(function(){
		//表单提交开始
		var options = {
			beforeSubmit : checkData,
			success : showResponse,
			url : getUrl('${ctx}/file/uploadFj'),
			type : "post",
			dataType : "json",
			timeout :600000
		};
		function checkData() {
			var value = $("#file${id}").val();
			if ($.trim(value).length == 0) {
				$.messager.alert("系统提示","请选择文件","info");
				return false;
			}
			if (!$("#fileUpload",$c).form('validate')) {
				return false;
			}
			commonFujianAdd.progress();
			$("#file${id}",$c).hide();
			pro.show();
			$c.mask("数据保存中...");
			return true;
		}
		function showResponse(data) {
			$c.unmask();
			$.messager.alert("系统提示",data.msg,"info",function(){
				commonFujian.close();
			});
			if (data.flag) {
				commonFujian.refresh();
			}
		}
		
		$("#fileUpload",$c).live('submit', function() {
			$(this).ajaxSubmit(options);
			return false; //阻止表单默认提交  
		});
		//表单提交结束
		
		
		
	})
	
	
	/* commonFujianAdd.progress = function() {
		var r = setInterval(function() {
			$.getJSON(getUrl('${ctx}/file/progress'),function(data) {
				var div = $("div",pro);
				if ($.isEmptyObject(data) || data.percentage == '100.00%') {
					clearInterval(r);
					pro.hide();
					$("#file${id}",$c).show();
				}
				div.html(data.percentage);
				div.animate({
					width: data.percentage
				},200);
			});
		},200);
	}
	 */
	
</script>
</body>
</html>