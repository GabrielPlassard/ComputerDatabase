<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Computers database</title>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css">
    </head>
<body>

    <header class="topbar">
        <h1 class="fill">
            <a href="/computers">Play 2.0 sample application &mdash; Computer database</a>
        </h1>
    </header>

<section id="main">

    <h1>${mode =="edit" ? "Edit" : "Create"} a computer</h1>

    <form action="/computers/${mode}?id=${fieldValues["id"]}" method="POST" >

        <fieldset>
            <div class="clearfix ${errorMessages["name"] == null ? "" : "error" }">
                <label for="name">Computer name</label>
                <div class="input">
                    <input type="text" id="name" name="name" value="${fieldValues["name"]}" >
                    <span class="help-inline"> ${errorMessages["name"] == null ? "Required" : errorMessages["name"] }</span>
                </div>
            </div>

            <div class="clearfix ${errorMessages["introduced"] == null ? "" : "error" }">
                <label for="introduced">Introduced date</label>
                <div class="input">
                    <input type="text" id="introduced" name="introduced" value="${fieldValues["introduced"]}" >
                    <span class="help-inline"> ${errorMessages["introduced"] == null ? "Date (&#x27;yyyy-MM-dd&#x27;)" : errorMessages["introduced"] }</span>
                </div>
            </div>

            <div class="clearfix ${errorMessages["discontinued"] == null ? "" : "error" }">
                <label for="discontinued">Discontinued date</label>
                <div class="input">
                    <input type="text" id="discontinued" name="discontinued" value="${fieldValues["discontinued"]}" >
                    <span class="help-inline"> ${errorMessages["discontinued"] == null ? "Date (&#x27;yyyy-MM-dd&#x27;)" : errorMessages["discontinued"] }</span>
                </div>
            </div>

            <div class="clearfix ">
                <label for="company">Company</label>
                <div class="input">
                    <select id="company" name="company" >
                        <option class="blank" value="">-- Choose a company --</option>
                        <c:forEach var="company" items="${companies}">
                            <option value="${company.id}" ${company.id == fieldValues["company"] ? "selected" : ""}>${company.name}</option>
                        </c:forEach>
                    </select>
                    <span class="help-inline"></span>
                </div>
            </div>
        </fieldset>

        <div class="actions">
            <input type="submit" value="${mode == "edit" ? "Save" : "Create"} this computer" class="btn primary"> or
            <a href="/computers" class="btn">Cancel</a>
        </div>

    </form>

    <c:if test= "${mode == 'edit'}" >
        <form action="/computers/delete?id=${fieldValues["id"]}" method="POST" class="topRight">
            <input type="submit" value="Delete this computer" class="btn danger">
        </form>
    </c:if>

</section>
</body>
</html>