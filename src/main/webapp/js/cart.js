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
                setNewCost(data, productID);
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
                setNewCost(data, productID);
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

function setNewCost(data, productID) {
    setCartParameterFromJSON(data);
    var totalCostSpecificTool = $("#" + productID + " #totalCostSpecificTool");
    totalCostSpecificTool.text('$ ' + data.totalCostSpecificTool);
}