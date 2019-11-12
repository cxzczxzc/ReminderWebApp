<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#task').validate({ 
	    rules: {
	    	phoneNumber: {
	            required: true,
	            phoneUS: true
	        },
	     dueDate : {
	    	 	required : true,
	     }, 
	      name : {
	    	 	required : true,
	     },
	        description : {
	    	 	required : true,
	     },
	    }
	});
});
</script>
<style>
.error {
    color:red;
}
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schedule Tasks</title>
</head>
<body>
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
          <a class="navbar-brand text-uppercase" href="#"><span class="glyphicon glyphicon-time"></span>&nbsp;Schedule New Task</a>
        </div>
      </div>
    </nav>
</div>
<div class="container-fluid" style="padding-top: 50px;overflow: hidden;">
<h3>Enter the task details below</h3>
<hr>
<c:url value="/saveTask" var="url" />
<form:form modelAttribute="task" method="post" action="${url}" cssClass="form-horizontal">
<div class="row" style="padding-top: 40px">
<!-- Id (hidden) -->
<input type="hidden" id="id" name="id" value="${task.id}">
<!-- Name -->
<div class="col-lg-3">
<label for="name">Name</label>
 <form:input path="name" cssClass="form-control" placeholder="Enter your name"/>
</div>
<!-- Phone # -->
<div class="col-lg-3">
<label for="phoneNumber">Phone Number</label>
 <form:input path="phoneNumber" cssClass="form-control" placeholder = "Enter your phone number"/>
</div>
<!-- Date -->
<div class="col-lg-3">
<label for="date">Due Date & Time</label>
<fmt:parseDate value="${task.dueDate}" var="parsedDate" pattern="yyyy-MM-dd hh:mm"/>
<fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd'T'hh:mm" var="string2"/>
 <form:input path="dueDate" type="datetime-local" cssClass="form-control" placeholder = "Choose the due date" value="${string2}"/>
</div>
<!-- Priority -->
<div class="col-lg-3">
<label for="date">Priority</label>
<form:select path="priority" items="${task.priorities}" cssClass="form-control"/><br />
</div>
</div>
<div class="row" style="padding-top:20px">
<!-- Description -->
<div class="col-lg-6 col-lg-offset-3">
<label for="description">Describe the task</label>
<form:textarea path="description" cssClass="form-control" rows="6"></form:textarea>
</div>
</div>
<div class="col-lg-4 col-lg-offset-4" style="padding-top:20px">
<input type="submit" value="Schedule Task!" class="btn btn-primary form-control"/>
</div>
</form:form>
<div class="col-lg-4 col-lg-offset-4" style="padding-top:5px">
<c:url value="/viewTask" var="taskurl" />
<form:form  method="get" action="${taskurl}" cssStyle="padding-top:20px" >
<input type="submit" value="View tasks" class="btn btn-info form-control" />

</form:form> 
</div>
</div>
<hr>

</body>
</html>