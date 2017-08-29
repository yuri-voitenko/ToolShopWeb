<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="captcha" uri="tld/captcha.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="ToolShopWeb"/>
<!DOCTYPE html>
<html>
<head>
    <title>Tool Shop | Tools</title>
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
            <myTag:switch_locale/>
            <div class="header-right animated wow fadeInRight" data-wow-delay=".5s">
                <div class="header-right1 ">
                    <ul>
                        <li><i class="glyphicon glyphicon-book"></i><a href="/viewRegisterForm">
                            <fmt:message key="register"/></a></li>
                    </ul>
                </div>
                <myTag:cart/>
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
                        <li class="active"><a href="/viewHomePage" class="act"><fmt:message key="home"/></a></li>
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
        <h2 class="animated wow fadeInLeft" data-wow-delay=".5s"><fmt:message key="tools"/></h2>
        <h3 class="animated wow fadeInRight" data-wow-delay=".5s"><a
                href="/viewHomePage"><fmt:message key="home"/></a><label>/</label><fmt:message key="tools"/></h3>
        <div class="clearfix"></div>
    </div>
</div>
<!--content-->
<div class="product">
    <form name="filter" id="filter" action="/viewTools" method="GET">
    </form>
    <div class="container">
        <div class="col-md-3 product-bottom">
            <div class="price">
                <h3><fmt:message key="name"/></h3>
                <div class="price-head">
                    <div class="col-md-6 price-head1">
                        <div class="price-top1" style="width: 250px;">
                            <span class="price-top">#</span>
                            <input form="filter" type="text" name="nameTool" placeholder="<fmt:message key="name_tool"/>"
                                   value="${requestScope.filterEntity.nameTool}">
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <!--categories-->
            <div class="categories">
                <h3><fmt:message key="category"/></h3><br>
                <select form="filter" name="category" style="width: 250px;">
                    <option disabled selected><fmt:message key="select_type_tool"/></option>
                    <c:forEach items="${requestScope.categories}" var="type">
                        <c:choose>
                            <c:when test="${type == requestScope.filterEntity.category}">
                                <option value="${type}" selected>${type}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${type}">${type}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <br>
            <div class="categories">
                <h3><fmt:message key="manufacturer"/></h3>
                <c:forEach items="${requestScope.manufacturers}" var="manufacturer">
                    <c:choose>
                        <c:when test="${empty requestScope.filterEntity.manufacturers}">
                            &emsp;<input form="filter" type="checkbox" name="manufacturer" value="${manufacturer}"
                            />&emsp;${manufacturer}
                        </c:when>
                        <c:otherwise>
                            <c:set var="isSelected" value="false"/>
                            <c:forEach items="${requestScope.filterEntity.manufacturers}" var="selectedManufacturer">
                                <c:if test="${manufacturer == selectedManufacturer}">
                                    <c:set var="isSelected" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${isSelected}">
                                    &emsp;<input form="filter" type="checkbox" name="manufacturer" value="${manufacturer}"
                                    checked/>&emsp;${manufacturer}
                                </c:when>
                                <c:otherwise>
                                    &emsp;<input form="filter" type="checkbox" name="manufacturer" value="${manufacturer}"
                                    />&emsp;${manufacturer}
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <br>
                </c:forEach>
            </div>
            <!--//menu-->
            <!--price-->
            <div class="price">
                <h3><fmt:message key="price"/></h3>
                <div class="price-head">
                    <div class="col-md-6 price-head1">
                        <div class="price-top1">
                            <span class="price-top">$</span>
                            <input form="filter" type="text" name="lowPrice" placeholder="min" pattern="\d*"
                                   value="${requestScope.filterEntity.lowPrice}">
                        </div>
                    </div>
                    <div class="col-md-6 price-head2">
                        <div class="price-top1">
                            <span class="price-top">$</span>
                            <input form="filter" type="text" name="highPrice" placeholder="max" pattern="\d*"
                                   value="${requestScope.filterEntity.highPrice}">
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <!--//price-->
            <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="apply"/>" form="filter">&emsp;&emsp;
            <a href="/viewTools" lass="act"><span class="label label-warning"><fmt:message
                    key="reset_filter"/></span></a>
        </div>
        <div class="col-md-9 animated wow fadeInRight" data-wow-delay=".5s">
            <div class="mens-toolbar">
                <c:set var="indexStartToolOnPage"
                       value="${(requestScope.filterEntity.numberPage*requestScope.filterEntity.numberToolsOnPage)}"
                />
                <c:set var="indexFinishToolOnPage"
                       value="${indexStartToolOnPage+requestScope.filterEntity.numberToolsOnPage}"/>
                <c:if test="${indexFinishToolOnPage > requestScope.numberSuitableTools}">
                    <c:set var="indexFinishToolOnPage" value="${requestScope.numberSuitableTools}"/>
                </c:if>
                <p>

                    <fmt:message key="showing"/> ${indexStartToolOnPage+1}–${indexFinishToolOnPage} <fmt:message key="of"/> ${requestScope.numberSuitableTools}
                    <fmt:message key="results"/></p>
                <p class="showing"><fmt:message key="sorting_by"/>
                    <select form="filter" name="orderKey">
                        <option value="name"><fmt:message key="name"/></option>
                        <c:choose>
                            <c:when test="${requestScope.filterEntity.orderKey == 'cost'}">
                                <option value="cost" selected><fmt:message key="price"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="cost"><fmt:message key="price"/></option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <select form="filter" name="orderDirection">
                        <option value="ASC"><fmt:message key="up"/></option>
                        <c:choose>
                            <c:when test="${requestScope.filterEntity.orderDirection == 'DESC'}">
                                <option value="DESC" selected><fmt:message key="down"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="DESC"><fmt:message key="down"/></option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </p>
                <p><fmt:message key="items_on_the_page"/>
                    <select form="filter" name="numberToolsOnPage">
                        <c:forEach begin="3" end="27" step="3" varStatus="loop">
                            <c:choose>
                                <c:when test="${requestScope.filterEntity.numberToolsOnPage == loop.index}">
                                    <option value="${loop.index}" selected> ${loop.index}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${loop.index}"> ${loop.index}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </p>
                <div class="clearfix"></div>
            </div>
            <div class="mid-popular">
                <c:forEach items="${requestScope.tools}" var="curTool">
                    <div class="col-sm-4 item-grid item-gr  simpleCart_shelfItem">
                        <div class="grid-pro">
                            <div class=" grid-product ">
                                <figure>
                                    <a href="#">
                                        <div class="grid-img">
                                            <img src="images/tools/${curTool.additionalImage}" class="img-responsive"
                                                 alt="">
                                        </div>
                                        <div class="grid-img">
                                            <img src="images/tools/${curTool.mainImage}" class="img-responsive" alt="">
                                        </div>
                                    </a>
                                </figure>
                            </div>
                            <div class="women">
                                <h5><fmt:message key="name"/>:${curTool.name}</h5>
                                <h5><fmt:message key="category"/>:${curTool.category}</h5>
                                <h5><fmt:message key="manufacturer"/>:${curTool.manufacturer}</h5>
                                <h5><fmt:message key="power"/>:${curTool.power}</h5>
                                <h5><fmt:message key="max_rotation_speed"/>:${curTool.maxRotationSpeed}</h5>
                                <h5><fmt:message key="weight"/>:${curTool.weight}</h5>
                                <em class="item_price">$${curTool.cost}</em>
                                <p>
                                    <a href="javascript:carts.add(${curTool.id})"
                                       data-text="<fmt:message key="add_to_cart"/>"
                                       class="but-hover1 item_add"><fmt:message key="add_to_cart"/></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="clearfix"></div>
            </div>
            <input form="filter" type="hidden" name="numberPage" id="numberPage" value="0">
            <c:choose>
                <c:when test="${requestScope.numberSuitableTools == 0}">
                    <div class="alert alert-warning" role="alert">
                        <strong>Sorry!</strong> But nothing found for this query :(
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${requestScope.amountPages != 1}">
                        <div class="pagination">
                            <c:forEach begin="0" end="${requestScope.amountPages-1}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${requestScope.filterEntity.numberPage == loop.index}">
                                        <a href="#" onclick="numberPage.value=${loop.index}; filter.submit();"
                                           class="active">${loop.index + 1}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="#"
                                           onclick="numberPage.value=${loop.index}; filter.submit();">${loop.index + 1}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!--//products-->
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
            <p>&copy 2017 Tool Shop. All rights reserved | Design by <a href="http://w3layouts.com/">W3layouts</a></p>
        </div>
    </div>
</div>
<!-- //footer -->
</body>
</html>