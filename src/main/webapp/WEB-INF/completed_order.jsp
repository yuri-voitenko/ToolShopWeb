<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="ToolShopWeb"/>
<!DOCTYPE html>
<html>
<head>
    <title>Tool Shop | Cart</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <!-- //for-mobile-apps -->
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- js -->
    <script src="js/jquery.min.js"></script>
    <!-- //js -->
    <!-- for bootstrap working -->
    <script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
    <!-- //for bootstrap working -->
    <!-- animation-effect -->
    <link href="css/animate.min.css" rel="stylesheet">
    <script src="js/wow.min.js"></script>
    <script>
        new WOW().init();
    </script>
    <!-- //animation-effect -->
    <link href='//fonts.googleapis.com/css?family=Cabin:400,500,600,700' rel='stylesheet' type='text/css'>
    <link href='//fonts.googleapis.com/css?family=Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
</head>
<body>
<!-- header -->
<div class="header">
    <div class="header-grid">
        <div class="container">
            <div class="header-left animated wow fadeInLeft" data-wow-delay=".5s">
                <myTag:login/>
            </div>
            <myTag:switch_locale/>
            <div class="header-right animated wow fadeInRight" data-wow-delay=".5s">
                <div class="header-right1 ">
                    <ul>
                        <li><i class="glyphicon glyphicon-book"></i><a href="/viewRegisterForm"><fmt:message key="register"/></a></li>
                    </ul>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="container">
        <div class="logo-nav">
            <nav class="navbar navbar-default">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header nav_2">
                    <button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse"
                            data-target="#bs-megadropdown-tabs">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="navbar-brand logo-nav-left wow fadeInLeft animated" data-wow-delay=".5s">
                        <h1 class="animated wow pulse" data-wow-delay=".5s"><a
                                href="/viewHomePage">Tool<span>Shop</span></a></h1>
                    </div>
                </div>
                <div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
                    <ul class="nav navbar-nav">
                        <li><a href="/viewHomePage" class="act"><fmt:message key="home"/></a></li>
                        <!-- Mega Menu -->
                        <li class="dropdown">
                            <a href="/viewTools"><fmt:message key="tools"/></a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>
<!-- //header -->
<!--banner-->
<div class="banner-top">
    <div class="container">
        <h2 class="animated wow fadeInLeft" data-wow-delay=".5s"><fmt:message key="order_result"/></h2>
        <h3 class="animated wow fadeInRight" data-wow-delay=".5s"><a href="/viewHomePage"><fmt:message key="home"/></a>
        <label>/</label><fmt:message key="order_result"/>
        </h3>
        <div class="clearfix"></div>
    </div>
</div>
<!-- contact -->
<div class="check-out">
    <div class="container">
        <c:choose>
            <c:when test="${empty sessionScope.cart}">
                <center><img src="images/cart-empty.png" alt=""/></center>
            </c:when>
            <c:otherwise>
                <c:set var="countTotalSum" value="0" scope="page"/>
                <h5 class="continue"><fmt:message key="order_result"/></h5>
                <table class="table">
                    <thead>
                    <tr class="success">
                        <th rowspan="2">#</th>
                        <th colspan="3"><fmt:message key="product"/></th>
                        <th rowspan="2"><fmt:message key="price"/></th>
                        <th rowspan="2"><fmt:message key="quantity"/></th>
                        <th rowspan="2"><fmt:message key="total"/></th>
                    </tr>
                    <tr class="success">
                        <th><fmt:message key="category"/></th>
                        <th><fmt:message key="manufacturer"/></th>
                        <th><fmt:message key="name"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="countRow" value="1" scope="page"/>
                    <c:set var="countPlace" value="0" scope="page"/>
                    <c:forEach items="${sessionScope.listOrderedTools}" var="orderedTool">
                        <c:choose>
                            <c:when test="${countRow % 2 == 0}">
                                <tr class="active">
                            </c:when>
                            <c:otherwise>
                                <tr>
                            </c:otherwise>
                        </c:choose>
                        <th scope="row">${countRow}</th>
                        <td>${orderedTool.electricTool.category}</td>
                        <td>${orderedTool.electricTool.manufacturer}</td>
                        <td>${orderedTool.electricTool.name}</td>
                        <td>${orderedTool.unitPrice}</td>
                        <td>${orderedTool.amount}</td>
                        <td>$ ${orderedTool.unitPrice*orderedTool.amount}</td>
                        </tr>
                        <c:set var="countRow" value="${countRow + 1}" scope="page"/>
                        <c:set var="countPlace" value="${countPlace + orderedTool.amount}" scope="page"/>
                        <c:set var="countTotalSum"
                               value="${countTotalSum + (orderedTool.unitPrice*orderedTool.amount)}" scope="page"/>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <div class="price-details">
                        <h3><fmt:message key="details"/></h3>
                        <span><fmt:message key="order_id"/></span>
                        <span class="total1">${requestScope.orderEntity.id}</span>
                        <span><fmt:message key="order_status"/></span>
                        <span class="total1">${requestScope.orderEntity.status}</span>
                        <span><fmt:message key="places"/></span>
                        <span class="total1">${countPlace}</span>
                        <span><fmt:message key="delivery"/></span>
                        <span class="total1">${requestScope.delivery}</span>
                        <span><fmt:message key="client"/></span>
                        <span class="total1">${requestScope.orderEntity.user.fullName}</span>
                        <div class="clearfix"></div>
                    </div>
                    <ul class="total_price">
                        <li class="last_price"><h3><fmt:message key="total"/></h3></li>
                        <span id="cartTotal" class="simpleCart_total">$ ${countTotalSum}</span>
                        <div class="clearfix"></div>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<!-- footer -->
<div class="footer">
    <div class="container">
        <div class="footer-grids">
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".5s">
                <h3>About Us</h3>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse.<span>Excepteur sint occaecat cupidatat
						non proident, sunt in culpa qui officia deserunt mollit.</span></p>
            </div>
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".6s">
                <h3>Contact Info</h3>
                <ul>
                    <li><i class="glyphicon glyphicon-map-marker"></i>1234k Avenue, 4th block,
                        <span>New York City.</span></li>
                    <li class="foot-mid"><i class="glyphicon glyphicon-envelope"></i><a href="mailto:info@example.com">info@example.com</a>
                    </li>
                    <li><i class="glyphicon glyphicon-earphone"></i>+1234 567 567</li>
                </ul>
            </div>
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".7s">
                <h3>Sign up for newsletter </h3>
                <form>
                    <input type="text" placeholder="Email" required="">
                    <input type="submit" value="Submit">
                </form>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="copy-right animated wow fadeInUp" data-wow-delay=".5s">
            <p>&copy 2016 Tool Shop. All rights reserved | Design by <a href="http://w3layouts.com/">W3layouts</a></p>
        </div>
    </div>
</div>
<!-- //footer -->
</body>
</html>