<link href="css/masterPage.css" rel="stylesheet">

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
                    <th><fmt:message key="label.report"/></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${!repairOrders.equals('noOrders')}">
                    <c:forEach var="tempOrder" items="${repairOrders}">

                        <c:choose>
                            <c:when test="${tempOrder.price <= 0}">
                                <tr class="warning">
                            </c:when>
                            <c:when test="${tempOrder.price > 0}">
                                <tr class="success">
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
                        <c:choose>
                            <c:when test="${tempOrder.price <= 0}">
                                <form action="/ControllerDispatcherServlet" method="get">
                                    <input type="hidden" name="command" value="FIX_ORDER"/>
                                    <input type="hidden" name="orderId" value="${tempOrder.orderId}"/>
                                    <td><input type="text" name="report"></td>
                                    <td><input type="submit" value="<fmt:message key='label.add'/>"/></td>
                                </form>
                            </c:when>
                            <c:when test="${tempOrder.price > 0}">
                                <tr class="success">
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>

                        </tr>
                        <tr>
                            <td colspan="5">${tempOrder.report}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
    </div>
</div>