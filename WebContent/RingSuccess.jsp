<%@page import="com.prathima.ringcentral.util.RingStatusObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ring Status</title>
</head>
<body bgcolor="5F9EA0" style="margin-left:30px;margin-top:25px">
<%
Boolean show = false ; 
RingStatusObject ringstatus = (RingStatusObject) request.getAttribute("ringstatus");
Double duration = (Double) request.getAttribute("duration") ;
if( ringstatus != null && ringstatus.getStatus() != null ){	
	out.println("Phone Call has been successfully made."+"<br>");
	out.println("Call Status: "+ ringstatus.getStatus().get("callStatus")+"<br>");
	out.println("Caller Status: " + ringstatus.getStatus().get("callerStatus")+"<br>");
	out.println("Callee Status: " +ringstatus.getStatus().get("calleeStatus")+"<br>");
	application.setAttribute("ringstatus",ringstatus); 
	 show = false ; 
}
else if( duration != null ){
	
	out.println("Duration of the call last made is " + duration.intValue() + " sec");
	
}

else {
	out.println("No Call Status details available");
}

%><br>
<br>
<br>
<br>
<a href="RingCentral.jsp">Click here to go back to input Screen </a>
<br>
<br>
<% if (show) {%>
<form action="./RingStatusController" method="post" >
<input type="submit" value="Click to Check Status" id="submitButton" ">			
</form>
<%}%>
</body>
</html>