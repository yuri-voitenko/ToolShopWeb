<%@tag pageEncoding="UTF-8" %>
<%@tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="ToolShopWeb"/>
<div class="header-right2">
    <div class="cart box_1">
        <a href="/viewCart">
            <h3>
                <div class="total">
                    <c:choose>
                        <c:when test="${empty sessionScope.cart}">
                            <span id="cartTotal" class="simpleCart_total">$ 0.00</span>
                            <span id="cartQuantity" class="simpleCart_quantity">(0 items)</span>
                        </c:when>
                        <c:otherwise>
                            <span id="cartTotal"
                                  class="simpleCart_total">$ ${sessionScope.cart.getTotalSumPurchase()}</span>
                            <span id="cartQuantity"
                                  class="simpleCart_quantity">(${sessionScope.cart.getTotalQuantityProducts()} items)</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <img src="images/cart.png" alt=""/>
            </h3>
        </a>
        <p><a href="javascript:carts.clear();" class="simpleCart_empty"><fmt:message key="empty_cart"/></a></p>
        <div class="clearfix"></div>
    </div>
</div>