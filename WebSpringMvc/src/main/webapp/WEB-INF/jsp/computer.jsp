<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Computers database</title>
        <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/bootstrap.min.css"/>"/>
        <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/main.css"/>"/>
    </head>
<body>

    <header class="topbar">
        <h1 class="fill">
            <a href="<c:url value="/computers"/>"/>Play 2.0 sample application &mdash; Computer database</a>
        </h1>
    </header>

<section id="main">

    <h1>${mode =="edit" ? "Edit" : "Create"} a computer</h1>

    <form:form commandName="computer" action="/computers/${mode.concat('?id=').concat(computer.id)}" method="post">
        <fieldset>
            <div class="clearfix ${empty result.getFieldError("name") ? "" : "error" }">
                <label for="name">Computer name</label>
                <div class="input">
                    <form:input type="text" id="name" path="name" />
                    <span class="help-inline"> Required</span>
                </div>
            </div>
            <div class="clearfix ${empty result.getFieldError("introduced") ? "" : "error" }">
                <label for="introduced">Introduced date</label>
                <div class="input">
                    <form:input type="text" id="introduced" path="introduced"/>
                    <span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
                </div>
            </div>
            <div class="clearfix ${empty result.getFieldError("discontinued") ? "" : "error" }">
                <label for="discontinued">Discontinued date</label>
                <div class="input">
                    <form:input type="text" id="discontinued" path="discontinued" />
                    <span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
                </div>
            </div>

            <div class="clearfix ">
                <label for="company">Company</label>
                <div class="input">
                    <form:select id="company" path="company" >
                        <option class="blank" value="">-- Choose a company --</option>
                        <c:forEach var="company" items="${companies}">
                            <option value="${company.id}" ${company.id == fieldValues["company"] ? "selected" : ""}>${company.name}</option>
                        </c:forEach>
                    </form:select>
                    <span class="help-inline"></span>
                </div>
            </div>
        </fieldset>

        <div class="actions">
            <form:button type="submit" class="btn primary"> ${mode == "edit" ? "Save" : "Create"} this computer </form:button> or
            <a href="<c:url value="/computers"/>" class="btn">Cancel</a>
        </div>
    </form:form>

    <c:if test= "${mode == 'edit'}" >
        <form action="<c:url value="/computers/delete?id=${computer.id}"/>" method="POST" class="topRight">
            <input type="submit" value="Delete this computer" class="btn danger">
        </form>
    </c:if>

</section>
</body>
</html>