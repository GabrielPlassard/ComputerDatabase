<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ attribute name="columnNumber" required="true" type="java.lang.Integer"%>
<%@ attribute name="columnName" required="true" type="java.lang.String"%>
<%@ attribute name="currentSheet" required="true" type="java.lang.Integer"%>
<%@ attribute name="sorting" required="true" type="java.lang.Integer"%>
<%@ attribute name="research" required="true" type="java.lang.String"%>


<c:choose>
    <c:when test="${sorting == columnNumber}">
        <th class="col${columnNumber} header headerSortUp">
            <a href="/computers?p=${currentSheet}&s=${-columnNumber}&f=${research}">${columnName}</a>
        </th>
    </c:when>
    <c:when test="${sorting == -columnNumber}">
        <th class="col${columnNumber} header headerSortDown">
            <a href="/computers?p=${currentSheet}&s=${columnNumber}&f=${research}">${columnName}</a>
        </th>
    </c:when>
    <c:otherwise>
        <th class="col${columnNumber} header">
            <a href="/computers?p=${currentSheet}&s=${columnNumber}&f=${research}">${columnName}</a>
        </th>
    </c:otherwise>
</c:choose>
