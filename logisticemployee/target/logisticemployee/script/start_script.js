function initialize() {

	$(".btn").button();

	$("#orders").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#orders").focus(function () {
   		 $(this).removeClass("ui-state-focus");
	});

	$("#select_btn,#stop_btn").button();
	
	populateOrderList();
	
};
$(document).ready(function () {

	$("#select_btn").click(function() {
		//detect the selected package
		var packIndex= document.getElementById("orders").selectedIndex;
		if( packIndex<0)
			{
			alert("Select order");
			return;
			}
		var selectedpack;
		$("#orders option:selected").each(function()
				{
			selectedpack= $(this).val();
				});
		$.ajax({						
			 type : "GET",
			 url : "getRequest",
			 dataType: "xml",
			 data: { orderId: selectedpack} }
	   	 ).done(function(data) {
	   		
	   		forwordRequest(data);
		});			
	});
	
	$("#stop_btn").click(function() {

		$.ajax({						
			 type : "POST",
			 url : serverHost+"/iot/xml/finish",
		}).done(function(data) {
	   		console.log("Stoped transportation !");

		});			
	});

});
function populateOrderList() {
	$.ajax({
		type : "GET",
		dataType : "text",
		url : "getOrders"
	}).done(function(data) {
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

function forwordRequest(data) {
	console.log("forword method");
	stringXml= new XMLSerializer().serializeToString(data);

	var request ={
			parcel :
				{ids : data.getElementsByTagName("ids")[0].childNodes[0].nodeValue,
				receiver : data.getElementsByTagName("receiver")[0].childNodes[0].nodeValue,
				sender : data.getElementsByTagName("sender")[0].childNodes[0].nodeValue,
				job : data.getElementsByTagName("job")[0].childNodes[0].nodeValue,
				withTransportationType: data.getElementsByTagName("withTransportationType")[0].childNodes[0].nodeValue,
				deliveryTime: 
					{ start: data.getElementsByTagName("start")[0].childNodes[0].nodeValue,
				 	  end: data.getElementsByTagName("end")[0].childNodes[0].nodeValue,
					},
	            acceptanceCriteria: {
	            	hardMinTemperature: data.getElementsByTagName("hardMinTemperature")[0].childNodes[0].nodeValue,
	            	hardMaxTemperature: data.getElementsByTagName("hardMaxTemperature")[0].childNodes[0].nodeValue,
	            	hardMinHumidity: data.getElementsByTagName("hardMinHumidity")[0].childNodes[0].nodeValue,
	            	hardMaxHumidity: data.getElementsByTagName("hardMaxHumidity")[0].childNodes[0].nodeValue,
	            	softMinTemperature: data.getElementsByTagName("softMinTemperature")[0].childNodes[0].nodeValue,
	            	softMaxTemperature: data.getElementsByTagName("softMaxTemperature")[0].childNodes[0].nodeValue,
	            	maximumTimeOutOfTemperature: data.getElementsByTagName("maximumTimeOutOfTemperature")[0].childNodes[0].nodeValue,
	            },
	            predictiveModels:{
	            	candidate: [{
	            		id: data.getElementsByTagName("id")[0].childNodes[0].nodeValue,	 	            		 
	            	},
	            	]
	            
	            }
			},
	};
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : serverHost+"/iot/xml/send",
		data:  { requestObject: JSON.stringify(request)},
	}).done(function(data) {
		console.log("FORWORDED!");
	});
}


