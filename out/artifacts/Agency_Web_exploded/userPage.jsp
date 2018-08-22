<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : theLocale}"
       scope="session"/>
<fmt:setLocale value="${theLocale}"/>
<fmt:setBundle basename="agency.illiaderhun.com.github.i18n"/>

<%@include file="html/header.jsp"%>
<c:choose>
    <c:when test="${sessionScope.userRole==1}">
        <%@include file="html/userPageInfo.jsp"%>
    </c:when>
    <c:when test="${sessionScope.userRole==2}">
        <%@include file="html/managerPage.jsp"%>
    </c:when>
    <c:when test="${sessionScope.userRole==3}">
        <%@include file="html/masterPage.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="html/login.jsp"%>
    </c:otherwise>
</c:choose>
<%@include file="html/footer.html"%>
