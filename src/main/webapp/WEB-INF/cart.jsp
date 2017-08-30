<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTag" %>
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
    <!-- cart -->
    <script src="js/cart.js"></script>
    <!-- cart -->
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
            <div class="header-right animated wow fadeInRight" data-wow-delay=".5s">
                <div class="header-right1 ">
                    <ul>
                        <li><i class="glyphicon glyphicon-book"></i><a href="/viewRegisterForm">Register</a></li>
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
                        <li><a href="/viewHomePage" class="act">Home</a></li>
                        <!-- Mega Menu -->
                        <li class="dropdown">
                            <a href="/viewTools">Tool</a>
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
        <h2 class="animated wow fadeInLeft" data-wow-delay=".5s">Checkout</h2>
        <h3 class="animated wow fadeInRight" data-wow-delay=".5s"><a href="/viewHomePage">Home</a><label>/</label>Checkout
        </h3>
        <div class="clearfix"></div>
    </div>
</div>
<!-- contact -->
<div class="check-out">
    <div class="container">
        <c:choose>
            <c:when test="${empty sessionScope.cart || sessionScope.cart.getContent().size() == 0}">
                <center><img src="images/cart-empty.png" alt=""/></center>
            </c:when>
            <c:otherwise>
                <table class="table animated wow fadeInLeft" data-wow-delay=".5s">
                    <tr>
                        <th class="t-head head-it ">Item</th>
                        <th class="t-head">Price</th>
                        <th class="t-head">Quantity</th>
                        <th class="t-head">Total</th>
                    </tr>
                    <c:forEach var="entry" items="${sessionScope.cart.getContent()}">
                        <tr class="cross" id="${entry.key.id}">
                            <td class="ring-in t-data">
                                <a href="#" class="at-in">
                                    <img src="images/tools/${entry.key.mainImage}" width="100" height="136" alt="">
                                </a>
                                <div class="sed">
                                    <h2>${entry.key.category}</h2>
                                    <h3>${entry.key.manufacturer}</h3>
                                    <h4>${entry.key.name}</h4>
                                </div>
                                <div class="clearfix"></div>
                                <div class="close1" id="${entry.key.id}"></div>
                            </td>
                            <td class="t-data">${entry.key.cost}</td>
                            <td class="t-data">
                                <div class="quantity">
                                    <div class="quantity-select">
                                        <div class="entry value-minus" onclick="carts.reduce(${entry.key.id})">&nbsp;
                                        </div>
                                        <div class="entry value"><span class="span-1">${entry.value}</span></div>
                                        <div class="entry value-plus active" onclick="carts.increase(${entry.key.id})">
                                            &nbsp;
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td id="totalCostSpecificTool" class="t-data">$ ${entry.key.cost*entry.value}</td>
                        </tr>
                    </c:forEach>
                </table>
                <h5 class="continue">Cart Total:
                    <span id="cartTotal" class="simpleCart_total">$ ${sessionScope.cart.getTotalSumPurchase()}</span>
                </h5>
                <div class=" cart-total">
                    <a href="javascript:carts.clear();$(location).attr('href', '/viewCart');">Clear Cart</a>
                    <a href="/viewOrder">Order</a>
                </div>
                <br><br><br>
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