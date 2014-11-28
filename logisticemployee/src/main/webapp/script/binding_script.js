
$(document).ready(function () {
	$("#start").click(function() {
		var packIndex= document.getElementById("packages").selectedIndex;
		if( packIndex<0)
			{
			alert("Select package");
			return;
			}
		populateSensorList();

		$("#start").button().attr('disabled', true).addClass( 'ui-state-disabled' );
		$("#packages").button().attr('disabled', true).addClass( 'ui-state-disabled' );

		$("#cancel").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
		$("#sensors").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
		$("#selectedSensors").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
		$("#select").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
		$("#unselect").button().attr('disabled', false).removeClass( 'ui-state-disabled' );

	});
	$("#cancel").click(function() {
		clearAll();
	});
	$("#select").click(function() {
		var sensorIndex= document.getElementById("sensors").selectedIndex;
		if( sensorIndex<0)
			{
			alert("Select sensor");
			return;
			}
		var sensors = document.getElementById("sensors");
		var sensor_select = sensors.options[sensors.selectedIndex].value;
		var selectedSensors = document.getElementById("selectedSensors");

		sensors.remove(sensorIndex);
		var index= selectedSensors.length;
		selectedSensors.options[index] = new Option(sensor_select, sensor_select);
		$("#finish").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	});
	$("#unselect").click(function() {
		var sensorIndex= document.getElementById("selectedSensors").selectedIndex;
		if( sensorIndex<0)
			{
			alert("Select sensor");
			return;
			}
		var sensors = document.getElementById("selectedSensors");
		var sensor_select = sensors.options[sensors.selectedIndex].value;
		var selectedSensors = document.getElementById("sensors");

		sensors.remove(sensorIndex);
		var index= selectedSensors.length;
		selectedSensors.options[index] = new Option(sensor_select, sensor_select);
		//check if any sensor selected. if no disable finish button
		if(sensors.length<=0)
			$("#finish").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	});
	$("#finish").click(function() {
		var selectedSensorsToBind="";
		var selectedpack;
		$("#packages option:selected").each(function()
				{
			selectedpack= $(this).val();
				});
		$("#selectedSensors option").each(function()
				{
			selectedSensorsToBind=selectedSensorsToBind+ $(this).val()+",";
				});

		$.ajax({						
			 type : "POST",
			 url : "bind",
			 data: { pack:selectedpack,
				 	 selectedSensors:selectedSensorsToBind},} 
	   	 ).done(function(data) {
	   		clearAll();							
		});	
		$("#cancel").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	});
});
function populatePackageList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
	url : "getPackages"})
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
});		
}
function populateSensorList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
	url : "getSensors"})
	.done(function(data) {
		    var data = JSON.parse(data);
			var select = document.getElementById("sensors");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			for ( var i = 0; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}
									
});		
}
function initialize() {

	$( ".newStyle" ).button();
	$("#packages,#sensors,#selectedSensors").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#packages,#sensors,#selectedSensors").focus(function () {
   		 $(this).removeClass("ui-state-focus");

/* 	$(".newStyle").bind('mouseout', function() {
        $(this).removeClass('ui-state-hover');
    }); */
});
	
	populatePackageList();
   //initialize the buttons
	$("#start").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	$("#packages").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	

	$("#finish").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#sensors").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#selectedSensors").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#select").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#unselect").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#cancel").button().attr('disabled', true).addClass( 'ui-state-disabled' );
};
function clearAll(){
	//clear sensors
	document.getElementById("sensors").options.length = 0;
	document.getElementById("selectedSensors").options.length = 0;
	//populate the packages list in order to update the data
	populatePackageList();
	
	$("#start").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	$("#packages").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	
	$("#finish").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#sensors").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#selectedSensors").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#select").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#unselect").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	$("#cancel").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	
}
