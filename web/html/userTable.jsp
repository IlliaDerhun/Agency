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
            <c:forEach var="j" begin="${start}" end="${finish}">
                <c:choose>
                    <c:when test="${repairOrders[j].price <= 0}">
                        <tr class="danger">
                    </c:when>
                    <c:when test="${repairOrders[j].price > 0 && (tempOrder.report) != null}">
                        <tr class="success">
                    </c:when>
                    <c:when test="${repairOrders[j].report == null && tempOrder.price > 0}">
                        <tr class="warning">
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                <td>${repairOrders[j].orderId}</td>
                <td>${repairOrders[j].deviceName}</td>
                <td>${repairOrders[j].description}</td>
                <td>${repairOrders[j].managerName}</td>
                <td>${repairOrders[j].date}</td>
                <td>${repairOrders[j].price}</td>
                </tr>
                <c:if test="${(repairOrders[j].report) != null}">
                    <tr>
                        <td><fmt:message key="label.report"/></td>
                        <td colspan="6">${repairOrders[j].report}</td>
                    </tr>
                    <c:if test="${(repairOrders[j].feedback) == null}">
                        <tr>
                            <td><fmt:message key="label.comment"/></td>
                            <form action="/ControllerDispatcherServlet" method="get">
                                <input type="hidden" name="command" value="ADD_COMMENT"/>
                                <input type="hidden" name="orderId" value="${repairOrders[j].orderId}"/>
                                <td>
                                    <input type="text" required maxlength="109" name="comment"/>
                                </td>
                                <td><input type="submit" value="<fmt:message key='label.add'/>"></td>
                            </form>
                        </tr>
                    </c:if>
                </c:if>
                <c:if test="${(repairOrders[j].feedback) != null}">
                    <tr>
                        <td><fmt:message key="label.comment"/></td>
                        <td colspan="6">${repairOrders[j].feedback}</td>
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