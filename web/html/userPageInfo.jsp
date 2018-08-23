<link href="css/userPage.css" rel="stylesheet">
<script type="text/javascript" src="js/userPageInfoPagination.js"></script>
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
        <div class="tab">
            <button class="tablinks" onclick="openTab(event, 'First')"><fmt:message key="label.first"/></button>
            <button class="tablinks" onclick="openTab(event, 'Second')"><fmt:message key="label.second"/></button>
            <button class="tablinks" onclick="openTab(event, 'Third')"><fmt:message key="label.third"/></button>
        </div>

        <div id="First" class="tabcontent">
            <span hidden>${start = 0}</span>
            <span hidden>${finish = amount / 3}</span>
            <%@include file="userTable.jsp"%>
        </div>
        <div id="Second" class="tabcontent" style="display: none;">
            <span hidden>${start = amount / 3}</span>
            <span hidden>${finish = (amount / 3) * 2}</span>
            <%@include file="userTable.jsp"%>
        </div>

        <div id="Third" class="tabcontent" style="display: none;">
            <span hidden>${start = (amount / 3) * 2}</span>
            <span hidden>${finish = amount}</span>
            <%@include file="userTable.jsp"%>
        </div>
</div>