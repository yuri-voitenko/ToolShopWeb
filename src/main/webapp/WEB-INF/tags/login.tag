<%@tag pageEncoding="UTF-8" %>
<%@tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${empty sessionScope.userEntity}">
        <form action="/loginUser" method="post">
            <input type="email" name="email" placeholder="Email" required="">
            <input type="password" name="password"
                   pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$" placeholder="Password"
                   title="Password requirements:&#013;&emsp;* At least one upper case English letter&#013;&emsp;* At least one lower case English letter&#013;&emsp;* At least one digit&#013;&emsp;* At least one special character&#013;&emsp;* Minimum eight in length"
                   required="">
            <input type="submit" value="login">
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
                <h3> <span class="label label-success"
                           style="display: inline-block;">Welcome, ${sessionScope.userEntity.fullName}!</span></h3>
                <ul>
                    <li><i class="glyphicon glyphicon-log-in"></i><a href="/logoutUser">Logout</a></li>
                </ul>
            </li>
        </ul>
    </c:otherwise>
</c:choose>