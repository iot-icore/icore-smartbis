<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="style/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="style/graph.css">
<link type="text/css" rel="stylesheet" href="style/detail.css">
<link type="text/css" rel="stylesheet" href="style/legend.css">
<link type="text/css" rel="stylesheet" href="style/lines.css">
<link rel="stylesheet" type="text/css" href="style/style.css"></link>

<script
	src="script/jquery.min.js"></script>
<script src="script/jquery-ui.min.js"></script>
<script type="text/javascript" src="script/viewDashboard_script.js"></script>

<script src="script/d3.v2.js"></script>
<script src="script/rickshaw.js"></script>
<script src="script/Rickshaw.Class.js"></script>

<script src="script/Rickshaw.Compat.ClassList.js"></script>
<script src="script/Rickshaw.Graph.js"></script>
<script src="script/Rickshaw.Graph.Renderer.js"></script>
<script src="script/Rickshaw.Graph.Renderer.Area.js"></script>
<script src="script/Rickshaw.Graph.Renderer.Line.js"></script>
<script src="script/Rickshaw.Graph.Renderer.Bar.js"></script>
<script src="script/Rickshaw.Graph.Renderer.ScatterPlot.js"></script>
<script src="script/Rickshaw.Graph.RangeSlider.js"></script>
<script src="script/Rickshaw.Graph.HoverDetail.js"></script>
<script src="script/Rickshaw.Graph.Annotate.js"></script>
<script src="script/Rickshaw.Graph.Legend.js"></script>
<script src="script/Rickshaw.Graph.Axis.Time.js"></script>
<script src="script/Rickshaw.Graph.Behavior.Series.Toggle.js"></script>
<script src="script/Rickshaw.Graph.Behavior.Series.Order.js"></script>
<script src="script/Rickshaw.Graph.Behavior.Series.Highlight.js"></script>
<script src="script/Rickshaw.Graph.Smoother.js"></script>
<script src="script/Rickshaw.Graph.Unstacker.js"></script>
<script src="script/Rickshaw.Fixtures.Time.js"></script>
<script src="script/Rickshaw.Fixtures.Time.Local.js"></script>
<script src="script/Rickshaw.Fixtures.Number.js"></script>
<script src="script/Rickshaw.Fixtures.RandomData.js"></script>
<script src="script/Rickshaw.Fixtures.Color.js"></script>
<script src="script/Rickshaw.Color.Palette.js"></script>
<script src="script/Rickshaw.Graph.Axis.Y.js"></script>


<title>View dashboard</title>

</head>
<body onload="initialize()">
	<div class="container" style="width: 1000px" id="bigcontainer">
		<h2 class="sansserif">SELECT PACKAGE:</h2>
		<div id='unbinding'>
			Select package: <br> 
			<select id="packages" class="newStyle" size="1"></select>
			 <input type="submit" class="newStyle" id="start" value="Select Package" style="margin-left: 20px;" />
		</div>
		
		<div id='unbinding'>
			Select sensor: <br> 
				<select id="sensors" class="newStyle" size="1">  </select> 
				<input type="submit" class="newStyle" id="view" value="View DashBoard" style="margin-left: 20px;" />
		</div>

		<div>
			<br />
			Transportation report:
			<br />
			<textarea rows="8" cols="100" id="transportationReport" readonly="readonly">
			1
			2
			3
			4
			5
			6
			7
			8
			</textarea>
		</div>

		<div id="charts" style="display: none;">
			<table>
				<tr style="width:100%">
					<td>
						<h3 id="sensorheader" class="sansserif" align="center">Sensor data </h3>
						<div id="chart_container">
							<div id="legend_container">
								<div id="smoother" title="Smoothing"></div>
								<div id="legend"></div>
							</div>
							<div id="chart" style="background-color: white;"></div>
							<div id="slider"></div>
						</div>
					</td>
				</tr>
<!-- 				<tr style="width:100%">		 -->
<!-- 					<td> -->
<!-- 						<h3 id="outofboundsheader" class="sansserif"></h3> -->
<!-- 						<div id="chart_containerOutOfBounds"> -->
<!-- 							<div id="legend_containerOutOfBounds"> -->
<!-- 								<div id="smootherOutOfBounds" title="Smoothing"></div> -->
<!-- 								<div id="legendOutOfBounds"></div> -->
<!-- 							</div> -->
<!-- 							<div id="chartOutOfBounds" style="background-color: white;"></div> -->
<!-- 							<div id="sliderOutOfBounds"></div> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>
		</div>
		<div class="class_bar">
			 <a id="go_to_menu" href="menu">Home</a> 
			 <a id="go_to_logout"href="logout">Logout</a> 
		</div>
		<div id="footer">
			<hr>
			<img alt="Logo" src="images/logo.png" id="icore_logo" /> <img
				alt="Logo" src="images/logo_siemens.png" id="smns_logo" />
		</div>
	</div>

</body>
</html>