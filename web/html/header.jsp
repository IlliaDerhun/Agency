<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="css/header.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-default">

        <div class="container-fluid">
            <div class="navbar-header">
                <a href="index" class="navbar-brand"><fmt:message key="label.title"/></a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index"><fmt:message key="label.mainPage"/></a></li>
                    <li><a href="aboutUs"><fmt:message key="label.about"/></a></li>
                    <li><a href="contact"><fmt:message key="label.contact"/></a></li>
                </ul>
                <ul class="nav navbar-nav login">
                    <c:choose>
                        <c:when test="${sessionScope.userRole>0}">
                            <li><a href="logout.jsp"><fmt:message key="label.logout"/></a></li>
                            <c:url var="userPageLink" value="ControllerDispatcherServlet">
                                <c:param name="command" value="USER_PAGE"/>
                            </c:url>
                            <li><a href="${userPageLink}"><fmt:message key="label.userPage"/></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="login"><fmt:message key="label.login"/></a></li>
                            <li><a href="register"><fmt:message key="label.register"/></a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <ul class="nav navbar-nav language">
                    <li><a href="?theLocale=ru_RU"><img src="images/ru.png" alt="Ru"></a></li>
                    <li><a href="?theLocale=en_US"><img src="images/us.png" alt="Eng"></a></li>
                </ul>
            </div>
        </div>
    </nav>