<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="style/jquery-ui.css" />

<script src="script/jquery.min.js"></script>
<script src="script/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="style/style.css"></link>

<script type="text/javascript" src="script/start_script.js"></script>
<title>Start</title>
<script>
var serverHost='<%=application.getInitParameter("serverHost") %>';
</script>

</head>
<body onload="initialize()">
	<div class="container">
		<h2 class="sansserif">START TRANSPORTATION</h2>

		<div>
			<div id='unbinding' style="float: left">
				Select order: <br> 
				<select id="orders" class="btn" class="newStyle" size="1" ></select>			
			</div>
		
			<div style="display: inline-block;">
				<input type="submit" class="newStyle" class="btn" id="select_btn" value="Start Transportation" style="margin-left: 20px; margin-bottom: 10px;"  /> <br>
				<input type="submit" class="newStyle" class="btn" id="stop_btn" value="Stop Running Transportation" style="margin-left: 20px;"  />
			</div>
		</div>
		
		<div class="class_bar">
			<a id="go_to_menu" href="menu">Home</a> <a id="go_to_logout"
				href="logout">Logout</a>
		</div>
		<div id="footer">
			<hr>
			<img alt="Logo" src="images/logo.png" id="icore_logo" /> <img
				alt="Logo" src="images/logo_siemens.png" id="smns_logo" />
		</div>
	</div>


</body>
</html>