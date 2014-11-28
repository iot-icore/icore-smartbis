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
	<script type="text/javascript" src="script/addOrder_script.js"></script>
<title>Order</title>

</head>
<body onload="initialize()">


<div class="container"  >
	<h2 class="sansserif">INSERT PACKAGE INFO</h2>
   <table align="center" cellspacing="5px">
   <tr>
		<td>Order ID:</td>
		<td> <input type="text" id="id" style="width: 380px;" disabled="disabled"/></td>
	</tr>
	  <tr>
		<td>Number of parcels:</td>
		<td> <input type="text" id="nr_pack" style="width: 380px;"/></td>
	</tr>
	<tr>
		<td>Product type:</td>
		<td><select id="productTypes" size="1" style="width: 390px;"></select></td>
	</tr>
	<tr>
		<td>Sender:</td>
		<td><textarea id="sender" rows="4" cols="45"></textarea> </td>
	</tr>
	<tr>
		<td>Receiver:</td>
		<td><textarea id="receiver" rows="4" cols="45"></textarea> </td>
	</tr>
	<tr>
		<td>Arrival time:</td>
		<td> Hour: <input type="text" id="hours" style="width: 40px;"/> Minutes: <input type="text" id="minutes" style="width: 40px;" /> Date: <input type="text" id="datepicker" /> </td>
	</tr>
   </table>
   <div align="center">
       <input type="submit" id="add" value="Submit order" style="margin-top: 20px;"/>	  

  	 </div>
  	 
  	 
  	 		<div id="pack_dialog" class="dialog" title="Order added!">
			<div class="config">
			Order successfully added !!	
			<table style="margin-bottom:  20px;">
				<tr>
				<td><input type="submit" class="newStyle" id="add_new" value="Add new order" />	 </td>
				<td>
					<form method="get" action="menu">
				 		 <input type="submit" class="newStyle" id="back_btn" value="Back to menu" />	
				    </form>
				</td>
			
			</table>					
				
				
				
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