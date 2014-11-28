<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content ="text/html; charset=utf-8">

<link rel="stylesheet" href="style/jquery-ui.css" />
<script src="script/jquery.min.js"></script>
<script src="script/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" href="style/style.css"></link>
	<script type="text/javascript" src="script/home.js"></script>
	<title>Employee Login page</title>
</head>
<body>

<div class="container"  >
	<h2 class="sansserif">EMPLOYEE LOGIN PAGE</h2>

		<form method="post" action="login">
		   <table>			
					<tr> 
						<td> Username: </td>
						<td> <input type="text" id="username" name="username" value="" />  </td>
					</tr>
					
					<tr> 
						<td> Password: </td>
						<td> <input type="password" id="password" name="password" value="" />  </td>
					</tr>
					<tr>
					<td></td>
						 <td> <input type="submit" name="login" id="login" class="btn" value="Login" /> </td>
					</tr> 
		   </table>
		</form>
   		<div id="footer">
			<hr>		
			<img alt="Logo" src="images/logo.png" id= "icore_logo"  />
			<img alt="Logo" src="images/logo_siemens.png" id= "smns_logo"  />
		</div>
</div>


				
</body>
</html>
