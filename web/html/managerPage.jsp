<link href="css/managerPage.css" rel="stylesheet">

<div class="userOrders">
    <h4><fmt:message key="label.yourOrders"/></h4>
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="label.deviceName"/></th>
                <th><fmt:message key="label.description"/></th>
                <th><fmt:message key="label.customer"/></th>
                <th><fmt:message key="label.master"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.price"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${!repairOrders.equals('noOrders')}">
                <c:forEach var="tempOrder" items="${repairOrders}">

                    <c:url var="deleteLink" value="/ControllerDispatcherServlet">
                        <c:param name="command" value="DELETE_ORDER"/>
                        <c:param name="orderId" value="${tempOrder.orderId}"/>
                    </c:url>

                    <c:url var="tempLink" value="/ControllerDispatcherServlet">
                        <c:param name="command" value="LOAD_ORDER"/>
                        <c:param name="orderId" value="${tempOrder.orderId}"/>
                    </c:url>

                    <c:url var="userInfo" value="/ControllerDispatcherServlet">
                        <c:param name="command" value="USER_INFO"/>
                        <c:param name="customerId" value="${tempOrder.customerId}"/>
                    </c:url>

                    <c:url var="masterInfo" value="/ControllerDispatcherServlet">
                        <c:param name="command" value="USER_INFO"/>
                        <c:param name="customerId" value="${tempOrder.masterId}"/>
                    </c:url>

                    <c:choose>
                        <c:when test="${tempOrder.price <= 0}">
                            <tr class="danger">
                        </c:when>
                        <c:otherwise>
                            <tr>
                        </c:otherwise>
                    </c:choose>
                        <td>${tempOrder.orderId}</td>
                        <td>${tempOrder.deviceName}</td>
                        <td>${tempOrder.description}</td>
                        <td><a href="${userInfo}">${tempOrder.customerName}</a></td>
                        <td><a href="${masterInfo}">${tempOrder.masterName}</a></td>
                        <td>${tempOrder.date}</td>
                        <td>${tempOrder.price}</td>
                        <td>
                            <a href="${tempLink}"><fmt:message key="label.edit"/></a>
                            <c:if test="${tempOrder.report==null}">
                                |
                                <a href="${deleteLink}"><fmt:message key="label.delete"/></a>
                            </c:if>

                        </td>
                    </tr>
                    <c:if test="${(tempOrder.report) != null}">
                        <tr>
                            <td><fmt:message key="label.report"/></td>
                            <td colspan="6">${tempOrder.report}</td>
                        </tr>
                    </c:if>
                    <c:if test="${(tempOrder.feedback) != null}">
                        <tr>
                            <td><fmt:message key="label.comment"/></td>
                            <td colspan="6">${tempOrder.feedback}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>