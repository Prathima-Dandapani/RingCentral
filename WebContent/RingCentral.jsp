<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ring A Phone</title>
</head>
<body>
<style type="text/css">
table { border-collapse: collapse;
	width: 45%;
}
</style>
</head>
<h1 align=center>RING A PHONE</h1>

<body bgcolor="5F9EA0" style="margin-left:30px;margin-top:25px">
	<form action="./RingController" method="post" >
		<table>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;Caller:</td>
					<td><input  type="text" name="caller" size="20" value="" /></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;Callee:</td>
					<td><input type="text" name="callee"  size="20" value="" /></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
				<tr>
					<td><input type="submit" name ="action" value="Dial"  ></td>
					<td><input type="submit" name = "action" value="Get Duration"  ></td>
				</tr>		
											
		</table>
				
		
	</form>
</body>
</html>