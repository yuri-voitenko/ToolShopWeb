<%@tag pageEncoding="UTF-8" %>
<%@tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="ToolShopWeb"/>
<c:choose>
    <c:when test="${empty sessionScope.userEntity}">
        <form action="/loginUser" method="post">
            <input type="hidden" name="failCount" value="${requestScope.logEntity.failCount}"/>
            <input type="email" name="email" placeholder="<fmt:message key="email"/>" required="">
            <input type="password" name="password"
                   pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
                   placeholder="<fmt:message key="password"/>"
                   title="Password requirements:&#10;&emsp;* At least one upper case English letter&#10;&emsp;* At least one lower case English letter&#10;&emsp;* At least one digit&#10;&emsp;* At least one special character&#10;&emsp;* Minimum eight in length"
                   required="">
            <input type="submit" value="<fmt:message key="login"/>">
        </form>
    </c:when>
    <c:otherwise>
        <ul>
            <li>
                <div class="avatar">
                    <img src="/images/avatars/${sessionScope.userEntity.avatar}" alt="">
                </div>
            </li>
            <li>
                <h3> <span class="label label-success" style="display: inline-block;">
                    <fmt:message key="welcome"/>, ${sessionScope.userEntity.fullName}!</span></h3>
                <ul>
                    <li><i class="glyphicon glyphicon-log-in"></i>
                        <a href="/logoutUser"><fmt:message key="logout"/></a>
                    </li>
                </ul>
            </li>
        </ul>
    </c:otherwise>
</c:choose>