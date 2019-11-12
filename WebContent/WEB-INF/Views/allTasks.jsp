<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <jsp:useBean id="now" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Phonebook</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script type="text/javascript">

</script>
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
           <a class="navbar-brand text-uppercase" href="/A2SaadAhmad/createTask?"><span class="glyphicon glyphicon-arrow-left"></span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   All Tasks</a>
          
        </div>
      </div>
    </nav>
</div>
<!-- Main content -->
<div class="container-fluid" style="padding-top: 50px;overflow: hidden;">
<h3>All tasks</h3>
<hr>
<table class="table table-dark" style="padding-top: 40px">
<thead class="thead-dark">
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Due Date</th>
      <th scope="col">Priority</th>
      <th scope="col">Phone Number</th>
      <th>Options</th>
    </tr>
  </thead>
  <c:forEach var="task" items="${taskList}">
  <tbody>
    <tr>
      <td><p>${task.name}</p></td>
      <td><p>${task.description} </p></td>
        <td><p>
    <fmt:parseNumber value="${now.time / (1000*60*60*24) }" integerOnly="true" var="nowDays" scope="request"/>
    <fmt:parseDate value="${task.dueDate}" pattern="yyyy-MM-dd HH:mm" type="both" dateStyle="full" var="submitDate"/>
    <fmt:parseNumber value="${ submitDate.time / (1000*60*60*24) }" integerOnly="true" var="otherDays" scope="page"/>
        <c:set value="${nowDays - otherDays}" var="dateDiff"/>
        <c:choose>
    <c:when test="${dateDiff eq 0}"><p style="color:green">Due today! ${submitDate}</p></c:when>
    <c:when test="${dateDiff gt 0}"><p style = "color:red">Was due ${dateDiff} days ago on ${submitDate} </p></c:when>
    <c:otherwise><p style="color:blue">Due in ${dateDiff * -1} days</p></c:otherwise>
    </c:choose>
</p></td>
       <td><p>${task.priority}</p></td>
       <td><p>${task.phoneNumber}</p></td>
      <td><c:url value="/editTask/${task.id}" var="editUrl"/>
		<a class="btn btn-primary" href="${editUrl}">Edit Task</a>
		&nbsp;&nbsp;
		<c:url value="/deleteTask/${task.id}" var="deleteUrl"/>
		<a class="btn btn-danger" href="${deleteUrl}">Delete Task</a>
		   &nbsp;&nbsp;
	   </td>
	   <td></td>
    </tr>
    </c:forEach>
  </tbody>
</table>

</div>
</body>
</html>