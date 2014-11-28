
$(document).ready(function () {
	
	//define the dialogs
	 $( "#pack_dialog" ).dialog({
		  autoOpen: false,
		  width: 420,
		  resizable: false,
		  dialogClass:"myDialog",
		 });
	 
	 
	 $("#add_new").click(function() {
		 $( "#pack_dialog" ).dialog( "close" );
	 });
	 

	 
/* when add button is pressed verify if the required 
information is specified. If no then show messages. */
$("#add").click(function() {
	var orderID= document.getElementById('id').value;	
	var nr_pack= document.getElementById('nr_pack').value;
	var e = document.getElementById("productTypes");
	try{
	var type = e.options[e.selectedIndex].value;
	}catch(err)
	  {
		alert("Error! Product type can not be null!");
		return;
	  }
	var sender= document.getElementById('sender').value;
	var receiver= document.getElementById('receiver').value;
	var hours= document.getElementById('hours').value;
	if(!isNumber(hours)){
		alert("Error! Hours must be a number!");
		return;
	}
	if((hours<0) || (hours>24)){
		alert("Error! Hours must be a number between 0 and 24!");
		return;
	}
	var minutes= document.getElementById('minutes').value;	
	if(!isNumber(minutes)){
		alert("Error! Minutes must be a number!");	
		return;
	}
	if((minutes<0) || (minutes>59)){
		alert("Error! Minutes must be a number between 0 and 59!");
		return;
	}
	var date = $('#datepicker').datepicker( "getDate" );
	date.setHours(date.getHours()+hours);		
	date.setMinutes(date.getMinutes()+minutes);			

	var addOrderRequest ={
		orderId:orderID,
		nr_pack: parseInt(nr_pack),
		productType: type,
		sender: sender,
		receiver: receiver,
		arrivalTime: date.getTime()
		 };
	
	$.ajax({						
		type : "POST",
		url : "addOrder", 
		data : JSON.stringify(addOrderRequest),
		contentType : 'application/json',
		
	}).done(function(data) {
	//	alert("Your order has been added successfully!");
		 $( "#pack_dialog" ).dialog( "open" );
		refresh();
    });					
});

	function isNumber(n) {
	  return !isNaN(parseFloat(n)) && isFinite(n);
	}
});

function populateProductTypeList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
	url : "getProductTypes"})
	.done(function(data) {
		   var data = JSON.parse(data);
			var select = document.getElementById("productTypes");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			for ( var i = 0; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}		
		$('#productTypes').prop('selectedIndex', -1);								
});		
}
function initialize() {
	$( "#datepicker" ).datepicker();
	$( "#add,#cancel" ).button();
   	$("#add,#cancel").focus(function () {
		 $(this).removeClass("ui-state-focus");
		 });
	populateProductTypeList();
	generatePackID();
}
function refresh(){
	generatePackID();
	$('#productTypes').prop('selectedIndex', -1);
	$("#sender").val("") ;
	$("#receiver").val("") ;
	$("#hours").val("") ;
	$("#minutes").val("") ;
	$("#datepicker").val("") ;
}
function generatePackID(){
	$.ajax({						
		type : "GET",
		dataType: "text",
		url : "getOrderId"})
		.done(function(data) {
			$("#id").val(data);							
	});		
	
}
