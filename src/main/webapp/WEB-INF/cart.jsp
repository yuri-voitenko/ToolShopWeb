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
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
				<script type="application/x-javascript">
					addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); }
				</script>
				<!-- //for-mobile-apps -->
				<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
				<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
				<!-- js -->
				<script src="js/jquery.min.js"></script>
				<!-- //js -->
				<!-- cart -->
				<script src="js/simpleCart.min.js"></script>
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
									<button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse" data-target="#bs-megadropdown-tabs">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
									<div class="navbar-brand logo-nav-left wow fadeInLeft animated" data-wow-delay=".5s">
										<h1 class="animated wow pulse" data-wow-delay=".5s"><a href="/viewHomePage">Tool<span>Shop</span></a></h1>
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
						<h3 class="animated wow fadeInRight" data-wow-delay=".5s"><a href="index.html">Home</a><label>/</label>Checkout</h3>
						<div class="clearfix"> </div>
					</div>
				</div>
				<!-- contact -->
				<div class="check-out">
					<div class="container">

						<script>
							$(document).ready(function (c) {
								$('.close1').on('click', function (c) {
									$('.cross').fadeOut('slow', function (c) {
										$('.cross').remove();
									});
								});
							});
						</script>
						<script>
							$(document).ready(function (c) {
								$('.close2').on('click', function (c) {
									$('.cross1').fadeOut('slow', function (c) {
										$('.cross1').remove();
									});
								});
							});
						</script>
						<script>
							$(document).ready(function (c) {
								$('.close3').on('click', function (c) {
									$('.cross2').fadeOut('slow', function (c) {
										$('.cross2').remove();
									});
								});
							});
						</script>
						<table class="table animated wow fadeInLeft" data-wow-delay=".5s">
							<tr>
								<th class="t-head head-it ">Item</th>
								<th class="t-head">Price</th>
								<th class="t-head">Quantity</th>
								<th class="t-head">Total</th>
							</tr>
							<tr class="cross">
								<td class="ring-in t-data">
									<a href="single.html" class="at-in">
					<img src="images/pcc.jpg" class="img-responsive" alt="">
				</a>
									<div class="sed">
										<h5>Sed ut perspiciatis unde</h5>
									</div>
									<div class="clearfix"> </div>
									<div class="close1"> </div>
								</td>
								<td class="t-data">$100.00</td>
								<td class="t-data">
									<div class="quantity">
										<div class="quantity-select">
											<div class="entry value-minus">&nbsp;</div>
											<div class="entry value"><span class="span-1">1</span></div>
											<div class="entry value-plus active">&nbsp;</div>
										</div>
									</div>

								</td>
								<td class="t-data">$100.00</td>

							</tr>
							<tr class="cross1">
								<td class="t-data ring-in"><a href="single.html" class="at-in"><img src="images/pcc2.jpg" class="img-responsive" alt=""></a>
									<div class="sed">
										<h5>Sed ut perspiciatis unde</h5>
									</div>
									<div class="clearfix"> </div>
									<div class="close2"> </div>
								</td>
								<td class="t-data">$200.00</td>
								<td class="t-data">
									<div class="quantity">
										<div class="quantity-select">
											<div class="entry value-minus">&nbsp;</div>
											<div class="entry value"><span class="span-1">1</span></div>
											<div class="entry value-plus active">&nbsp;</div>
										</div>
									</div>
									<!--quantity-->

								</td>
								<td class="t-data">$200.00</td>

							</tr>
							<tr class="cross2">
								<td class="t-data ring-in"><a href="single.html" class="at-in"><img src="images/pcc1.jpg" class="img-responsive" alt=""></a>
									<div class="sed">
										<h5>Sed ut perspiciatis unde</h5>
									</div>
									<div class="clearfix"> </div>
									<div class="close3"> </div>
								</td>
								<td class="t-data">$150.00</td>
								<td class="t-data">
									<div class="quantity">
										<div class="quantity-select">
											<div class="entry value-minus">&nbsp;</div>
											<div class="entry value"><span class="span-1">1</span></div>
											<div class="entry value-plus active">&nbsp;</div>
										</div>
									</div>

								</td>
								<td class="t-data">$150.00</td>

							</tr>
						</table>
						<div class=" cart-total">

							<h5 class="continue">Cart Total</h5>
							<div class="price-details">
								<h3>Price Details</h3>
								<span>Total</span>
								<span class="total1">6200.00</span>
								<span>Discount</span>
								<span class="total1">---</span>
								<span>Delivery Charges</span>
								<span class="total1">150.00</span>
								<div class="clearfix"></div>
							</div>
							<ul class="total_price">
								<li class="last_price">
									<h4>TOTAL</h4>
								</li>
								<li class="last_price"><span>6350.00</span></li>
								<div class="clearfix"> </div>
							</ul>

							<a href="single.html">Produced By Cart</a>

						</div>


					</div>
				</div>
				<!--quantity-->
				<script>
							$('.value-plus').on('click', function () {
								var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10) + 1;
								divUpd.text(newVal);
							});

							$('.value-minus').on('click', function () {
								var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10) - 1;
								if (newVal >= 1) divUpd.text(newVal);
							});
				</script>
				<!--quantity-->
				<!-- footer -->
				<div class="footer">
					<div class="container">
						<div class="footer-top">
							<div class="col-md-9 footer-top1">
								<h4>Duis aute irure dolor in reprehenderit in voluptate </h4>
								<p>Duis aute irure dolor in reprehenderit in voluptate velit esse.Excepteur sint occaecat cupidatat non proident Duis
									aute irure dolor in reprehenderit in voluptate velit esse</p>
							</div>
							<div class="col-md-3 footer-top2">
								<a href="contact.html">Contact Us</a>
							</div>
							<div class="clearfix"> </div>
						</div>
						<div class="footer-grids">
							<div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".5s">
								<h3>About Us</h3>
								<p>Duis aute irure dolor in reprehenderit in voluptate velit esse.<span>Excepteur sint occaecat cupidatat 
						non proident, sunt in culpa qui officia deserunt mollit.</span></p>
							</div>
							<div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".6s">
								<h3>Contact Info</h3>
								<ul>
									<li><i class="glyphicon glyphicon-map-marker"></i>1234k Avenue, 4th block, <span>New York City.</span></li>
									<li class="foot-mid"><i class="glyphicon glyphicon-envelope"></i><a href="mailto:info@example.com">info@example.com</a></li>
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

							<div class="clearfix"> </div>
						</div>

						<div class="copy-right animated wow fadeInUp" data-wow-delay=".5s">
							<p>&copy 2016 Tool Shop. All rights reserved | Design by <a href="http://w3layouts.com/">W3layouts</a></p>
						</div>
					</div>
				</div>
				<!-- //footer -->
			</body>

			</html>