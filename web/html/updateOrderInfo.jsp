<link href="css/updateOrder.css" rel="stylesheet">

<div class="userOrders">
    <c:if test="${err.equals('regExp')}">
        <span style="color: #ff0000"><fmt:message key="label.wrongFields"/></span>
    </c:if>
    <form action="/ControllerDispatcherServlet" method="get">
        <input type="hidden" name="command" value="UPDATE_ORDER">
        <input type="hidden" name="orderId" value="${tempOrder.orderId}">
        <input type="hidden" name="customerId" value="${tempOrder.customerId}">
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
                <tr>
                    <td>${tempOrder.orderId}</td>
                    <td><input type="text" name="deviceName" required maxlength="45" value="${tempOrder.deviceName}"></td>
                    <td><input type="text" name="description" required maxlength="256" value="${tempOrder.description}"></td>
                    <td>${tempOrder.customerName}</td>
                    <td>${tempOrder.masterName}</td>
                    <td>${tempOrder.date}</td>
                    <td>
                        <input type="number" required name="bons" min="1" max="9999999" value="${bons}">.<input type="number" required max="99" min="0" name="coins" maxlength="2" value="${coins}">
                    </td>
                    <td><input type="submit" value="<fmt:message key='label.edit'/>"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
</div>