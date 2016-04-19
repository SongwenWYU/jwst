<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="_res/inc/header.jsp"/>
	<title>java websocket terminal</title>
	<script type="text/javascript">
		$(document).ready(function () {
			
		})
	</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<button id="btn_ssh" type="button" class="btn btn-info" data-toggle="modal" data-target="#dataModal">Composite SSH Terminals</button>
			<button id="btn_scr" type="button" class="btn btn-info">Composite Scripts</button>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" id="dataModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="exampleModalLabel">Enter Information</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label for="recipient-name" class="control-label">Host:</label>
							<input type="text" class="form-control" id="recipient-host">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">Point:</label>
							<input type="text" class="form-control" id="recipient-point">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">User:</label>
							<input type="text" class="form-control" id="recipient-user">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">Password:</label>
							<input type="password" class="form-control" id="recipient-pwd">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" id="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>