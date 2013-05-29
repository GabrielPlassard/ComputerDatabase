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
            <a href="computers">Play 2.0 sample application &mdash; Computer database</a>
        </h1>
    </header>

    <section id="main">

        <h1>${totalComputersFound} computer(s) found</h1>

        <c:if test="${alertMessage != null}">
            <div class="alert-message warning">
                <strong>${alertMessage}</strong>
            </div>
        </c:if>

        <div id="actions">
            <form action="/computers?p=${currentSheet}&s=${sorting}" method="GET" >
                <input type="search" id="searchbox" name="f" value="${research}" placeholder="Filter by computer name...">
                <input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
            </form>
            <a class="btn success" id="add" href="/computers/new">Add a new computer</a>
        </div>

        <table class="computers zebra-striped">
            <thead>
                <tr>
                    <tag:tableHeader columnNumber="2" columnName="Computer name" currentSheet="${currentSheet}" research="${research}" sorting="${sorting}" />
                    <tag:tableHeader columnNumber="3" columnName="Introduced" currentSheet="${currentSheet}" research="${research}" sorting="${sorting}" />
                    <tag:tableHeader columnNumber="4" columnName="Discontinued" currentSheet="${currentSheet}" research="${research}" sorting="${sorting}" />
                    <tag:tableHeader columnNumber="5" columnName="Company" currentSheet="${currentSheet}" research="${research}" sorting="${sorting}" />
                </tr>
            </thead>

            <tbody>

                <c:forEach var="computer" items="${computers}">
                    <tr>
                        <td><a href="computers/edit?id=${computer.id}">${computer.name}</a></td>
                        <td>
                            <c:choose>
                                <c:when test="${computer.introduced == null}"><em>-</em></c:when>
                                <c:otherwise><fmt:formatDate value="${computer.introduced}" pattern="dd MMM yyyy" /></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${computer.discontinued == null}"><em>-</em></c:when>
                                <c:otherwise><fmt:formatDate value="${computer.discontinued}" pattern="dd MMM yyyy" /></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${computer.company.name}</td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <div id="pagination" class="pagination">
            <ul>
                <c:choose>
                    <c:when test="${currentSheet > 1}">
                        <li class="prev">
                            <a href="computers?p=${currentSheet - 1}&s=${sorting}&f=${research}">&larr; Previous</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="prev disabled">
                            <a>&larr; Previous</a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <li class="current">
                    <a>Displaying ${firstComputerIndice} to ${lastComputerIndice} of ${totalComputersFound}</a>
                </li>

                <c:choose>
                    <c:when test="${currentSheet < maxSheet}">
                        <li class="next">
                            <a href="computers?p=${currentSheet + 1}&s=${sorting}&f=${research}">Next &rarr;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="next disabled">
                            <a>Next &rarr;</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>

    </section>
    </body>
</html>