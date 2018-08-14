<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : theLocale}"
       scope="session"/>
<fmt:setLocale value="${theLocale}"/>
<fmt:setBundle basename="agency.illiaderhun.com.github.i18n"/>

<%@include file="html/header.html"%>
<%@include file="html/mainBody.html"%>
<%@include file="html/footer.html"%>

