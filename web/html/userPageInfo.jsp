<link href="css/userPage.css" rel="stylesheet">
<div class="userInfo">
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><fmt:message key="label.name"/></th>
                <th><fmt:message key="label.surname"/></th>
                <th><fmt:message key="label.phone"/></th>
                <th><fmt:message key="label.email"/></th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${sessionScope.firstName}</td>
                    <td>${sessionScope.lastName}</td>
                    <td>${sessionScope.phone}</td>
                    <td>${sessionScope.email}</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="userOrders">
    <h4><fmt:message key="label.yourOrders"/></h4>
    <c:if test="${err.equals('regExp')}">
        <span style="color: #ff0000"><fmt:message key="label.wrongFields"/></span>
    </c:if>
    <div class="table-responsive">

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message key="label.deviceName"/></th>
                    <th><fmt:message key="label.description"/></th>
                    <th><fmt:message key="label.manager"/></th>
                    <th><fmt:message key="label.date"/></th>
                    <th><fmt:message key="label.price"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${!repairOrders.equals('noOrders')}">
                    <c:forEach var="tempOrder" items="${repairOrders}">
                        <c:choose>
                            <c:when test="${tempOrder.price <= 0}">
                                <tr class="danger">
                            </c:when>
                            <c:when test="${tempOrder.price > 0 && (tempOrder.report) != null}">
                                <tr class="success">
                            </c:when>
                            <c:when test="${tempOrder.report == null && tempOrder.price > 0}">
                                <tr class="warning">
                            </c:when>
                            <c:otherwise>
                                <tr>
                            </c:otherwise>
                        </c:choose>
                            <td>${tempOrder.orderId}</td>
                            <td>${tempOrder.deviceName}</td>
                            <td>${tempOrder.description}</td>
                            <td>${tempOrder.managerName}</td>
                            <td>${tempOrder.date}</td>
                            <td>${tempOrder.price}</td>
                        </tr>
                        <c:if test="${(tempOrder.report) != null}">
                            <tr>
                                <td><fmt:message key="label.report"/></td>
                                <td colspan="6">${tempOrder.report}</td>
                            </tr>
                            <c:if test="${(tempOrder.feedback) == null}">
                                <tr>
                                    <td><fmt:message key="label.comment"/></td>
                                    <form action="/ControllerDispatcherServlet" method="get">
                                        <input type="hidden" name="command" value="ADD_COMMENT"/>
                                        <input type="hidden" name="orderId" value="${tempOrder.orderId}"/>
                                        <td>
                                            <input type="text" required maxlength="109" name="comment"/>
                                        </td>
                                        <td><input type="submit" value="<fmt:message key='label.add'/>"></td>
                                    </form>
                                </tr>
                            </c:if>
                        </c:if>
                        <c:if test="${(tempOrder.feedback) != null}">
                            <tr>
                                <td><fmt:message key="label.comment"/></td>
                                <td colspan="6">${tempOrder.feedback}</td>
                            </tr>
                        </c:if>

                    </c:forEach>
                </c:if>
                    <form action="/ControllerDispatcherServlet" method="post">
                        <input type="hidden" name="command" value="ADD_ORDER">
                        <tr>
                            <td></td>
                            <td><input type="text" name="deviceName" maxlength="45" required placeholder="<fmt:message key='label.deviceName'/>"/></td>
                            <td><input type="text" name="description" maxlength="256" required placeholder="<fmt:message key='label.description'/>"/></td>
                            <td></td>
                            <td></td>
                            <td><input type="submit" value="<fmt:message key='label.add'/>"/></td>
                        </tr>
                     </form>
                </tbody>
            </table>

    </div>
</div>