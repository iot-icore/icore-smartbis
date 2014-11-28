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

<script type="text/javascript" src="script/managepm.js"></script>
<link rel="stylesheet" href="script/amcharts/style.css"	type="text/css">

<script src="script/amcharts/amcharts.js" type="text/javascript"></script>
<script src="script/amcharts/serial.js" type="text/javascript"></script>
<script src="script/amcharts/amstock.js" type="text/javascript"></script>

<title>Start</title>
<script>
var serverHost='<%=application.getInitParameter("serverHost") %>';
</script>

</head>
<body onload="initialize()">
	<div class="container" style="width: 1000px">
		<h2 class="sansserif"> MANAGE PREDICTION MODEL </h2>

		<div>	
		
		<fieldset>
			<legend> View current model's performances:</legend>	
			<div id='unbinding'>
				
			<table>
				<tr> 
					<td>Package: 
					<td>Sensor:
					<td>
				<tr>
					<td><select id="packages" class="newStyle" size="1"></select>
					<td><select id="sensors" class="newStyle" size="1">  </select> 
					<td><input type="submit" class="newStyle" id="view" value="View Performance" style="margin-left: 20px;" disabled="disabled" />					
				</table>
					 				  
			</div>
			<div id="logger_div">
				<center> <div id="chartdiv" style="width:950px; height:350px; background-color: #fff;  border-radius: 10px;  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.1);  padding: 10px; margin-top: 20px;   margin-bottom: 20px;"></div> </center>
			</div>
		</fieldset>
		
				<fieldset id="modelsFieldset">
					<legend> Change model configurations:</legend>
					<div  style="margin-bottom: 20px; margin-left: 50px;">
						Select model: <br>
						<select id="models" class="newStyle" size="1"></select>
						<input type="submit" class="newStyle" id="editModel" value="Configurate" style="margin-left: 20px;"  disabled="disabled"/>
					</div>
				</fieldset>
				
				<fieldset id="trainData">
					<legend> Select train data:</legend>
					<table>
						<tr> 
							<td>Package:</td><td><select id="packagesTrain" class="newStyle" size="1"></select></td>
						</tr>
						<tr>
							<td>Sensor:</td> <td><select id="sensorsTrain" class="newStyle" size="1">  </select></td>
						</tr>
						<tr>
							<td><input type="submit" class="newStyle" id="viewTrain" value="Start training" disabled="disabled" />					
					</table>
				</fieldset>
													
		</div>
		
		
		<div id="liniar_dialog" class="dialog" title="Liniar Regretion Model">
			<div class="config">
				<p>Attribute selection method:</p>
				<select id="linear_options"> </select>
				<input type="submit" class="newStyle" id="save_liniar" value="Use this model" style="margin-left: 20px;"/>	
			</div>		
		</div>
		
		<div id="multilayer_dialog" class="dialog" title="Multilayer Perceptron Model">
			<div class="config">
			<table>
				<tr>
					<td> Hidden neurons:
					<td> <input type="text" id="hidden_neurons" value="5">
				<tr>
					<td> Learning rate:
					<td> <input type="text" id="learning_rate" value="0.2">
				<tr>
					<td> Momentum coefficient:
					<td> <input type="text" id="momentum_coefficient" value="0.1">		
				<tr>	
					<td></td>
					<td><input type="submit" class="newStyle" id="save_multilayer" value="Use this model" style="margin-left: 20px;"/>	
			</table>
			
			
			</div>		
		</div>
		
		<div id="m5p_dialog" class="dialog" title="M5P Model">
			<div class="config">
				Minimum number of instances per leaf:<br>
				<input type="text" id="minimum_number" value="5">								
				<input type="submit" class="newStyle" id="save_m5p" value="Use this model" style="margin-left: 20px;"/>	
			</div>
		</div>		
	
		
		<div id="rbf_dialog" class="dialog" title="RBF Model">
			<div class="config">
				<table>
				<tr>
					<td> Number of clusters:
					<td> <input type="text" id="number_clusters">
				<tr>
					<td> Minimum standard deviation:
					<td> <input type="text" id="standard_deviation">
				<tr>
					<td> 
					<td> <input type="submit" class="newStyle" id="save_rbf" value="Use this model" style="margin-left: 20px;"/>	
		
			 </table>	
			</div>				
			
		</div>				

		<div class="class_bar" style="width: 1000px;">
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