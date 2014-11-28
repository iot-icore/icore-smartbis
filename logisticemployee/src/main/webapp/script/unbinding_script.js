
function initialize() {

	$( ".newStyle" ).button();
	$("#packages").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#packages").focus(function () {
   		 $(this).removeClass("ui-state-focus");
	});
	$("#orders").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#orders").focus(function () {
   		 $(this).removeClass("ui-state-focus");
	});
	$("#start").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#packages").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	
	//populatePackageList();	
	populateOrderList();
};


$(document).ready(function () {
	$("#start").click(function() {
		//detect the selected package
		var packIndex= document.getElementById("packages").selectedIndex;
		if( packIndex<0)
			{
			alert("Select package");
			return;
			}
		var selectedpack;
		$("#packages option:selected").each(function()
				{
			selectedpack= $(this).val();
				});
		$.ajax({						
			 type : "POST",
			 url : "unbind",
			 data: { pack: selectedpack} }
	   	 ).done(function(data) {
	   		populatePackageList();
			$("#start").button().attr('disabled', true).addClass( 'ui-state-disabled' );				
		});	
		
		
	});
	
	$("#orders").change(function() {
		//detect the selected package
		var orderIndex= document.getElementById("orders").selectedIndex;
		if( orderIndex<0)
			{
			alert("Select Order");
			return;
			}
		var selectedorder;
		$("#orders option:selected").each(function()
				{
			selectedorder= $(this).val();
				});

	   		populatePackageList(selectedorder);		
	});

	$("#packages").change(function() {
		$("#start").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	});

});
function populatePackageList( selectedorder) {
	$.ajax({						
	type : "GET",
	dataType: "text",
    data: { orderId:selectedorder},
	url : "getBoundPackages"})
	.done(function(data) {
		   var data = JSON.parse(data);
			var select = document.getElementById("packages");
			
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			for ( var i = 0; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}
		
		$('#packages').prop('selectedIndex', -1);	
		$("#packages").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
});		
}

function populateOrderList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
	url : "getOrders"})
	.done(function(data) {
		   var data = JSON.parse(data);
			var select = document.getElementById("orders");
			
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			for ( var i = 0; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}
		
		$('#orders').prop('selectedIndex', -1);		
});		
}

