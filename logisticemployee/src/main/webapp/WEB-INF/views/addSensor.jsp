<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content ="text/html; charset=utf-8">

<link rel="stylesheet" href="style/jquery-ui.css" />
<script src="script/jquery.min.js"></script>
<script src="script/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" href="style/style.css"></link>
	<script type="text/javascript" src="script/addSensor_script.js"></script>
<title>Add sensor</title>

</head>
<body   onload="initialize()">

<div class="container" >
	<h2 class="sansserif">INSERT SENSOR INFO</h2>
    
	<div id="accordion">
		<h3>Add single sensor</h3>
		<div>
			<p> Id:<input type="text"  id="sensorId"  style="  width: 200px;  margin: 10px;"/> </p>
			 <div align="center">
      			 <input type="submit" id="add" value="Add sensor" style=" margin: 10px;"/>	  

  			 </div>
		</div>
		<h3>Import sensors from file</h3>
			<div>
				<p> File: <input type="file" id='file' name="myfile" accept=".csv">	</p>
				<div align="center">
      				 <input type="submit" id="readBytesButtons" value="Create sensors" style=" margin: 10px;"/>	  
  			    </div>
		</div>
	</div> 
		<div class="class_bar">
			 <a id="go_to_menu" href="menu">Home</a> 
			 <a id="go_to_logout"href="logout">Logout</a> 
		</div>
   
   <div id="footer">
			<hr>		
			<img alt="Logo" src="images/logo.png" id= "icore_logo"  />
			<img alt="Logo" src="images/logo_siemens.png" id= "smns_logo"  />
	</div>
</div>
</body>
</html>