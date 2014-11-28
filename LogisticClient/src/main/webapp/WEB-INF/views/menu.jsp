<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" href="style/jquery-ui.css" />
<script src="script/jquery.min.js"></script>
<script src="script/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" href="style/style.css"></link>
<script>
function initialize() {
	$( ".btn" ).button();
   	$(".btn").focus(function () {
 		 $(this).removeClass("ui-state-focus");
    });
}
</script>
</head>
<body onload="initialize()">

	<div class="container">
		<h2 class="sansserif">SELECT OPTION</h2>
		<hr>
		<div id='content'>
		
			<form method="GET" action="addOrder">
				<input class="btn" type="submit" value="Add package" />
			</form>


			<form method="GET" action="viewDashboard">
				<input class="btn" type="submit" value="View Dashboard" />
			</form>

		</div>

		<div class="class_bar">
			<!--  <a id="go_to_menu" href="menu">Home</a>  -->
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
