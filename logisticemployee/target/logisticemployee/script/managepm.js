var selectedSensor;
var selectedPack;
var drawBool="0";
var chart;
var chartData= [];
var current_tick=0;

$(document).ready(function () {
	initializeGraph();
	// refresh the image at the specified interval
	
	window.setInterval(function() {
		if(drawBool=="1")
			{
				draw();	
			}
	},10000);
	
	$("#packages").change(function() {	
		//detect the selected package
		var packIndex= document.getElementById("packages").selectedIndex;
		if( packIndex<0)
			{
				alert("Select package");
				return;
			}	
		//disable view button
		$("#view").button().attr('disabled', true).addClass( 'ui-state-disabled' );
		
		// clear what was on the chart
		drawBool="0";
		while (chartData.length) 
			{ 
				chartData.pop(); 
			}
		chart.validateData();
			
		//detect the selected package (there is only one
		var selectedpack = null;
		$("#packages option:selected").each(function()
		{
			selectedpack= $(this).val();
		});
		
		// get the sensors attached to the package
		$.ajax({						
			 type : "POST",
			 contentTyepe: "json",
			 url : "getSensors",
			 data: {pack: selectedpack}, 
		  }
	   	 ).done(function(data) {
	   	    
	   		 //update the sensors menu
			var select = document.getElementById("sensors");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			 var i = 0;
			for (i; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}

		$('#sensors').prop('selectedIndex', -1);	
		});	
		
	});				
	
	$("#packagesTrain").change(function() {	
		//detect the selected package
		var packIndex= document.getElementById("packagesTrain").selectedIndex;
		if( packIndex<0)
			{
				alert("Select package");
				return;
			}	
		//disable view button
		//TODO
		$("#viewTrain").button().attr('disabled', true).addClass( 'ui-state-disabled' );
		
		//detect the selected package (there is only one
		var selectedpack = null;
		$("#packagesTrain option:selected").each(function()
		{
			selectedpack= $(this).val();
		});
		
		// get the sensors attached to the package
		$.ajax({						
			 type : "POST",
			 contentTyepe: "json",
			 url : "getSensors",
			 data: {pack: selectedpack}, 
		  }
	   	 ).done(function(data) {
	   	    
	   		 //update the sensors menu
			var select = document.getElementById("sensorsTrain");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			 var i = 0;
			for (i; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}

		$('#sensorsTrain').prop('selectedIndex', -1);	
		});	
		
	});
	
	//define the dialogs
	 $( "#liniar_dialog" ).dialog({
		  autoOpen: false,
		  width: 480,
		  resizable: false,
		  dialogClass:"myDialog",
		 });
	 $( "#m5p_dialog" ).dialog({
		  autoOpen: false,
		  width: 480,
		  resizable: false,
		  dialogClass:"myDialog",
		 });
	 $( "#multilayer_dialog" ).dialog({
		  autoOpen: false,
		  width: 420,
		  resizable: false,
		  dialogClass:"myDialog",
		 });
	 $( "#rbf_dialog" ).dialog({
		  autoOpen: false,
		  width: 420,
		  resizable: false,
		  dialogClass:"myDialog",
		 });
	 
	 /*define functionalities for "Use this model button" for each dialog.
	 Close other dialogs if any open. Get the parameters from the GUI, check if are in the expected 
	 format, send the proper request, close the dialog */
	 $("#save_liniar").click(function() {
		 $('#multilayer_dialog').dialog('close');
		 $('#m5p_dialog').dialog('close');
		 $('#rbf_dialog').dialog('close');
		 
		 $("#linear_options option:selected").each(function()
			{
				selectedOption= $(this).val();
				
				$.ajax({						
					type : "POST",
					url : "saveLinear",
					data: 	{
								linear_options: selectedOption,
								
							}
					}
				)
					.done(function(data) {
						
						//close dialog
						$('#liniar_dialog').dialog('close');
				});	
				
				
			});
		});
	 $("#save_multilayer").click(function() {
		 $('#liniar_dialog').dialog('close');
		 $('#m5p_dialog').dialog('close');
		 $('#rbf_dialog').dialog('close');
		 
		 var hidden_neurons = $('#hidden_neurons').val();	
		 var learning_rate = $('#learning_rate').val();
		 var momentum_coefficient = $('#momentum_coefficient').val();

		 if(isNaN(hidden_neurons) || hidden_neurons.length<1){
			 alert("Hidden neurons parameter must be a number!");
			 return;
		 }
		 if(isNaN(learning_rate) || learning_rate.length<1){
			 alert("Learning rate parameter must be a number!");
			 return;					 
		 }
		 if(isNaN(momentum_coefficient) || momentum_coefficient.length<1){
			 alert("Momentum coefficient parameter must be a number!");
			 return;			 
		 }
		 		 		 
			$.ajax({						
				type : "POST",
				url : "saveMultilayer",
				data: {hidden_neurons: hidden_neurons,
					learning_rate: learning_rate,
					momentum_coefficient: momentum_coefficient,
					
				}}).done(function(data) {
					
					//close dialog
					$('#multilayer_dialog').dialog('close');
			});	
			
		 
		});
	 $("#save_m5p").click(function() {
		 
		 $('#liniar_dialog').dialog('close');
		 $('#multilayer_dialog').dialog('close');
		 $('#rbf_dialog').dialog('close');
		 
		 var minimum_number = $('#minimum_number').val();	
		 if(isNaN(minimum_number) || minimum_number.length<1){
			 alert("Minimum number of instances per leaf parameter must be a number!");
			 return;			 
		 }
			$.ajax({						
				type : "POST",
				url : "saveM5P",
				data: {minimum_number: minimum_number}})
				.done(function(data) {
					
					//close dialog
					$('#m5p_dialog').dialog('close');
			});	
			
			
		});
	 $("#save_rbf").click(function() {
		 
		 $('#liniar_dialog').dialog('close');
		 $('#multilayer_dialog').dialog('close');
		 $('#m5p_dialog').dialog('close');
		 
		 var number_clusters = $('#number_clusters').val();
		 var standard_deviation = $('#standard_deviation').val();
		 
		 if(isNaN(number_clusters) || number_clusters.length<1){
			 alert(" Number of clusters parameter must be a nuumber!");
			 return;			 
		 }
		 if(isNaN(standard_deviation) || standard_deviation.length<1){
			 alert("Minimum standard deviation parameter must be a number!");
			 return;			 
		 }
			$.ajax({						
				type : "POST",
				url : "saveRBF",
				data: {number_clusters: number_clusters,
					standard_deviation:standard_deviation,
					}})
				.done(function(data) {
					
					//close dialog
					$('#rbf_dialog').dialog('close');
			});						 
		});	 	 	 
	 
	 // view the prediction
	$("#view").click(function() {
		//start the request to get the latest measurements
		drawhistory();
		drawBool="1";
	    draw();			
	});
	
	 // view the prediction
	$("#viewTrain").click(function() {
		
		//detect the selected package (there is only one
		var selectedpackTrain = null;
		$("#packagesTrain option:selected").each(function()
		{
			selectedpackTrain= $(this).val();
		});
		
		//detect the selected package (there is only one
		var selectedsensorTrain = null;
		$("#sensorsTrain option:selected").each(function()
		{
			selectedsensorTrain= $(this).val();
		});
		
		// get the sensors attached to the package
		$.ajax({						
			 type : "POST",
			 contentTyepe: "json",
			 url : "startTraining",
			 data: 	{
				 		packID: selectedpackTrain, 
				 		sensorID: selectedsensorTrain,
			 		}}).success(function(response) 
								 {
						   	    
								 			trainCallback(response);
								 }
			 		);			
	});
	
	
	
	// when "Configure" button is clicked check which option is selected and open the desired dialog.
	$("#editModel").click(function() {
		$("#models option:selected").each(function()
		{
			selectedOption= $(this).val();					
			
			if( selectedOption =="linear_regresion")
				 $( "#liniar_dialog" ).dialog( "open" );		
			else if ( selectedOption =="multilayer_perceptron")
				 $( "#multilayer_dialog" ).dialog( "open" );
			else if ( selectedOption =="m5p")
				 $( "#m5p_dialog" ).dialog( "open" );
			else if ( selectedOption =="rbf")
				 $( "#rbf_dialog" ).dialog( "open" );
		});
		
				
	});
	
	// when a model is selected enable the edit button	
	$("#models").change(function() {		
		$("#editModel").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	});
	
	// when a sensor is selected enable the view chart button	
	$("#sensors").change(function() {
		$("#view").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	
	
});
	
	// when a sensor in the train panel is selected enable the view chart button	
	$("#sensorsTrain").change(function() {
		$("#viewTrain").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	
	
});

});	

// get the latest measurements and draw them
function draw(){	
	selectedSensor = null;
	$("#sensors option:selected").each(function()
	{
		selectedSensor= $(this).val();
	});
	
	  var url = "getChartData?sensorId="+selectedSensor;
		var xhr = createCORSRequest('GET', url);							  
		// Response handlers.
		 xhr.onload = function() {
				var response = xhr.responseText;
				var currentValues = response.split(","); 
				current_tick=current_tick+1;
				chartData.push({
		            tick: current_tick,
		            real:Math.round(parseFloat(currentValues[0]) * 100) / 100 ,
		            p10m: Math.round(parseFloat(currentValues[1]) * 100) / 100 ,
		            p1h: Math.round(parseFloat(currentValues[2]) * 100) / 100 ,
		            p2h: Math.round(parseFloat(currentValues[3]) * 100) / 100 ,
		        });
				
				chart.validateData();
		//		zoomChart();
			};
		xhr.send();			
}	


//get the latest measurements and draw them
function drawhistory(){	
	selectedSensor = null;
	$("#sensors option:selected").each(function()
	{
		selectedSensor= $(this).val();
	});
	
	  var url = "getChartHistory?sensorId="+selectedSensor;
		var xhr = createCORSRequest('GET', url);							  
		// Response handlers.
		 xhr.onload = function() {
				var response = xhr.responseText;
				var series = response.split(";"); 
				var measurements_h = series[0].split(","); 	
				var p10m_h = series[1].split(","); 
				var p1h_h = series[2].split(","); 
				var p2h_h = series[3].split(","); 
				
				current_tick=0;
				for (var i = 0; i < measurements_h.length-1; i++) {
					current_tick=current_tick+1;
				
					chartData.push({
			            tick: current_tick,
			            real:Math.round(parseFloat(measurements_h[i]) * 100) / 100 ,
			            p10m: Math.round(parseFloat(p10m_h[i]) * 100) / 100 ,
			            p1h: Math.round(parseFloat(p1h_h[i]) * 100) / 100 ,
			            p2h: Math.round(parseFloat(p2h_h[i]) * 100) / 100 ,
			        });
				}
				chart.validateData();
		//		zoomChart();
			};
		xhr.send();			
}

//configure the chart
function initializeGraph(){
chart = AmCharts.makeChart("chartdiv", {
  "type": "serial",
  "theme": "none",
  "pathToImages": "http://www.amcharts.com/lib/3/images/",
  "legend": {
      "useGraphSettings": false
  },
  "dataProvider": chartData,
  "valueAxes": [{
      "id":"v1",
      "axisColor": "#EDEDED",
      "axisThickness": 2,
      "gridAlpha": 0,
      "axisAlpha": 1,
      "position": "left",
      "precision":"2",
      "baseValue":"0",
      "min":"0",
      "minimum":"0",
      "showFirstLabel":"true"
  }],
  "graphs": [{
      "valueAxis": "v1",
      "lineColor": "#FF6600",
     /* "bullet": "round",
      "bulletBorderThickness": 1,
      "hideBulletsCount": 31,*/
      "title": "Real Value",
      "valueField": "real",
		"fillAlphas": 0,
		"baseValue":"0",
		// "type": "smoothedLine",
		 "lineThickness": 3
  }, {
      "valueAxis": "v1",
      "lineColor": "#FCD202",
   /*   "bullet": "square",
      "bulletBorderThickness": 1,
      "hideBulletsCount": 31,*/
      "title": "10 min prediction",
      "valueField": "p10m",
		"fillAlphas": 0,
		"baseValue":"0",
		// "type": "smoothedLine",
		 "lineThickness": 3
  }, {
      "valueAxis": "v1",
      "lineColor": "#B0DE09",
     /* "bullet": "triangleUp",
      "bulletBorderThickness": 1,
      "hideBulletsCount": 31,*/
      "title": "1 hour prediction",
      "valueField": "p1h",
		"fillAlphas": 0,
		"baseValue":"0",
		// "type": "smoothedLine",
		 "lineThickness": 3
  },{
      "valueAxis": "v1",
      "lineColor": "#36AFFB",
     /* "bullet": "triangleUp",
      "bulletBorderThickness": 1,
      "hideBulletsCount": 31,*/
      "title": "2 hours prediction",
      "valueField": "p2h",
      "baseValue":"0",
		"fillAlphas": 0,
		// "type": "smoothedLine",
		 "lineThickness": 3
  }],
  
 /* "chartScrollbar": {
	  "resizeEnabled": false,
  },*/
  "chartCursor": {
      "cursorPosition": "mouse"
  },
  "categoryField": "tick",
  "categoryAxis": {
      "parseDates": false,
      "axisColor": "#DADADA",
      "minorGridEnabled": false,
      
  }
});

//chart.addListener("dataUpdated", zoomChart);

/*	chart.addListener("zoomCompleted", function (event) {
	chart.dataProvider.zoomLevel = chart.zoomLevel();
	});*/

//zoomChart();
}

function zoomChart(){
  chart.zoomToIndexes(chart.dataProvider.length - 30, chart.dataProvider.length - 1);
}

function populatePackageList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
	url : "getAllPackages"})
	.done(function(data) {
		   var data = JSON.parse(data);
			var select = document.getElementById("packages");
			var selectTrain = document.getElementById("packagesTrain");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
				selectTrain.options[i] = null;
			}
			for ( var i = 0; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
				selectTrain.options[i] = new Option(data[i], data[i]);
			}
		
		$('#packages').prop('selectedIndex', -1);	
		$('#packagesTrain').prop('selectedIndex', -1);					
});		
}

function initialize() {
	$( ".newStyle" ).button();
	$("#packages").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#packages").focus(function () {
   		 $(this).removeClass("ui-state-focus");
	});
	$("#packagesTrain").mouseover(function() {
	    $(this).removeClass("ui-state-hover");
	  });
	$("#packagesTrain").focus(function () {
   		 $(this).removeClass("ui-state-focus");
	});
	$("#start").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	
	var select = document.getElementById("models");		
	select.options[0] = new Option( "Linear Regresion", "linear_regresion");
	select.options[1] = new Option( "Multilayer Perceptron", "multilayer_perceptron");
	select.options[2] = new Option( "M5P", "m5p");
	//select.options[3] = new Option( "RBF", "rbf");
	
	$('#models').prop('selectedIndex', -1);	
	
	select = document.getElementById("linear_options");		
	select.options[0] = new Option("M5 method", "m5_method");
	select.options[1] = new Option("Greedy method", "greedy_method");
	select.options[2] = new Option("No attribute selection", "no_atribute");

	$('#linear_options').prop('selectedIndex', -1);	
	
	
	populatePackageList();	
};


//used to make CORS requests
function createCORSRequest(method, url) {
	var xhr = new XMLHttpRequest();
	if ("withCredentials" in xhr) {
		// XHR for Chrome/Firefox/Opera/Safari.
		xhr.open(method, url, true);
	} else if (typeof XDomainRequest != "undefined") {
		// XDomainRequest for IE.
		xhr = new XDomainRequest();
		xhr.open(method, url);
	} else {
		// CORS not supported.
		xhr = null;
	}
	return xhr;
}

function trainCallback(message)
{
	alert(message);
}

