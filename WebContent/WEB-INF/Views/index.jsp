<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#phone').validate({ 
	    rules: {
	    	phoneNumber: {
	            required: true,
	            phoneUS: true
	        }
	    }
	});
});
</script>
<style>
.error {
    color:red;
}
</style>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome!</title>
</head>
<body>
<!-- Navigation Bar -->
<div>
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand text-uppercase" href="#">Welcome! This is your task reminder application &nbsp;&nbsp;</a>
        </div>
      </div>
    </nav>
</div>
<!-- Main Body -->

<div class="content container-fluid" style="padding-top: 50px;overflow: hidden;">
<h3>Register your phone number</h3>
<hr>
<c:url value="/registerNumber" var="url" />
<form:form modelAttribute="phone" method="post" action="${url}" cssClass="form-horizontal">
<div class="row" style="padding-top: 50px">
<div class="col-lg-6 col-lg-offset-3">
<label for="phoneNumber">Enter your phone number:</label>
 <form:input path="phoneNumber" cssClass="form-control"/>
</div>
<c:if test="${not empty phone.code}">
<div class="col-lg-6 col-lg-offset-3">
<br>
<label style="color:red" for="code">${phone.code}</label>
</div>
</c:if>
</div>
<c:if test="${empty phone.code}">
<div class="col-lg-6 col-lg-offset-3" style="padding-top:20px">
<input type="submit" value="Register!" class="btn btn-primary form-control"/>
</div>
</c:if>
</form:form>
<c:if test="${not empty phone.code}">
<c:url value="/createTask" var="taskurl" />
<form:form  method="get" action="${taskurl}">
<div class="col-lg-6 col-lg-offset-3" style="padding-top:20px">
<input type="submit" value="Proceed to a more discplined life!" class="btn btn-success form-control"/>
</div>
</form:form>

</c:if>
<c:if test="${empty phone.code}">
<c:url value="/createTask" var="taskurl" />
<div class="col-lg-4 col-lg-offset-4" style="padding-top:20px">
<form:form  method="get" action="${taskurl}" cssStyle="padding-top:20px" >

<input type="submit" value="Already have a registered phone number?" class="btn btn-info form-control" />

</form:form> 
</div>

</c:if>
</div>

</body>
</html>