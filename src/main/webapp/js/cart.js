$(document).ready(function(c) {
    $('.close1').on('click', function(c){
        carts.delete(this.id);
	});
});

var carts = {
    add: function (productID) {
        $.ajax({
            type: "post",
            url: "/addToolToCart",
            dataType: "json",
            data: {id: productID},
            success: setCartParameterFromJSON
        });
    },
    clear: function () {
        $.ajax({
            type: "post",
            url: "/clearCart",
            dataType: "json",
            success: setCartParameterFromJSON
        });
    },
    increase: function (productID) {
        $.ajax({
            type: "post",
            url: "/addToolToCart",
            dataType: "json",
            data: {id: productID},
            success: function (data) {
                setCartParameterFromJSON(data);
                setTotalCostSpecificTool(data, productID);
            }
        });
    },
    reduce: function (productID) {
        $.ajax({
            type: "post",
            url: "/reduceQuantityToolInCart",
            dataType: "json",
            data: {id: productID},
            success: function (data) {
                setCartParameterFromJSON(data);
                setTotalCostSpecificTool(data, productID);
            }
        });
    },
    delete: function (productID) {
        $.ajax({
            type: "post",
            url: "/deleteToolFromCart",
            dataType: "json",
            data: {id: productID},
            success: function (data) {
                setCartParameterFromJSON(data);
                removeTool(productID);
            }
        });
    }
};

function setCartParameterFromJSON(data) {
    var totalSum = $("#cartTotal");
    totalSum.text('$ ' + data.cartTotal);
    var totalQuantity = $("#cartQuantity");
    totalQuantity.text(' (' + data.cartQuantity + ' items)');
}

function setTotalCostSpecificTool(data, productID) {
    var totalCostSpecificTool = $("#" + productID + " #totalCostSpecificTool");
    totalCostSpecificTool.text('$ ' + data.totalCostSpecificTool);
}

function removeTool(productID) {
    $('#' + productID + '.cross').fadeOut('slow', function(c){
        $('#' + productID + '.cross').remove();
    });
}