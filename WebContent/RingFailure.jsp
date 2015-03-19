<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body bgcolor="5F9EA0" style="margin-left:30px;margin-top:25px">
<%
ArrayList<String> arr= (ArrayList<String>) request.getAttribute("errorMsg");
for(String str:arr){
	out.println(str+"<br>");
	
}

%><br>
<a href="RingCentral.jsp">Click here to go back to input Screen </a>

</body>
</html>