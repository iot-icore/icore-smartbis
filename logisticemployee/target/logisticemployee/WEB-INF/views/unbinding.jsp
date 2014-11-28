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

<script type="text/javascript" src="script/unbinding_script.js"></script>
<title>Unbinding</title>

</head>
<body onload="initialize()">
	<div class="container">
		<h2 class="sansserif">UNBIND PACKAGES</h2>
		
		<div id='unbinding'>
			Select order: <br> 
			<select id="orders" class="newStyle" size="1"> </select> 
		</div>
		
		<div id='unbinding'>
			Select package: <br> 
			<select id="packages" class="newStyle" size="1" ></select>
			<input type="submit" class="newStyle" id="start" value="Unbind" style="margin-left: 20px;"  />
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