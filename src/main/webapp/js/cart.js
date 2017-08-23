$(document).ready(function(){
    $("button").click(function() {
        var toolID = this.id;
        alert(toolID);
             $.ajax({
                type: "post",
                url: "/viewCart",
                dataType: "json",
                data: {id: toolID},
                success: function (data) {
                }
                });
    });
});