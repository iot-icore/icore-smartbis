var selectedSensor;
var selectedPack;

$(document).ready(function () {
	$("#start").click(function() {
		//detect the selected package
		var packIndex= document.getElementById("packages").selectedIndex;
		if( packIndex<0)
			{
				alert("Select package");
				return;
			}
		var selectedpack = null;
		$("#packages option:selected").each(function()
		{
			selectedpack= $(this).val();
		});
		
		$.ajax({						
			 type : "POST",
			 contentTyepe: "json",
			 url : "getSensors",
			 data: {pack: selectedpack}, 
		  }
	   	 ).done(function(data) {
	   	
	   		 console.log("sensors: "+data);
	   		 console.log("length: "+data.length);
			var select = document.getElementById("sensors");
			var length = select.options.length;
			for ( var i = 0; i < length; i++) {
				select.options[i] = null;
			}
			 var i = 0;
			for (i; i < data.length; i++) {
				select.options[i] = new Option(data[i], data[i]);
			}
			if (i>0)
				{
					select.options[i] = new Option(data[i-1]+"_10_minutes_prediction", data[i-1]+"_10_minutes_prediction");
					select.options[i+1] = new Option(data[i-1]+"_1_hour_prediction", data[i-1]+"_1_hour_prediction");
					select.options[i+2] = new Option(data[i-1]+"_2_hours_prediction", data[i-1]+"_2_hours_prediction");
				}
			 
		$('#sensors').prop('sensors', -1);	
		});	
		
	});
	
	var interval = null;

	$("#view").click(function() {
	
		if (interval != null)
		{
			clearInterval(interval);
		}
		
		var sensorIndex= document.getElementById("sensors").selectedIndex;
		if( sensorIndex<0)
			{
				alert("Select sensor");
				return;
			}
		selectedSensor = null;
		$("#sensors option:selected").each(function()
		{
			selectedSensor= $(this).val();
		});
		
		var packIndex= document.getElementById("packages").selectedIndex;
		if( packIndex<0)
			{
				alert("Select packages");
				return;
			}
		selectedPack = null;
		$("#packages option:selected").each(function()
		{
			selectedPack= $(this).val();
		});
		
		sensorIndex= document.getElementById("sensors").selectedIndex;
		packIndex= document.getElementById("packages").selectedIndex;
		
		ajaxd();
		
		interval = setInterval("ajaxd()",3000);
	});
	
	$("#packages").change(function() {
		$("#start").button().attr('disabled', false).removeClass( 'ui-state-disabled' );
	});
});

function ajaxd() { 
	
	
	if ( selectedSensor.indexOf("minutes") < 0  && selectedSensor.indexOf("_hour") < 0 )
	{
		document.getElementById('sensorheader').textContent = 'Sensor data';
		$.ajax({						
			 type : "POST",
			 dataType: "json",
			 url : "viewDashboardMethod",
			 data:  {sensorId: selectedSensor,
				 packId: selectedPack}, 
		  }
	   	 ).done(function(data) {
			showDashboard(data);
		});	
	}
	
	if (selectedSensor.indexOf("minutes") >= 0)
	{
		document.getElementById('sensorheader').textContent = '10 minute predictions';
		$.ajax({						
			 type : "POST",
			 dataType: "json",
			 url : "viewPredict10Mins",
			 data:  {sensorId: selectedSensor,
				 packId: selectedPack}, 
		  }
	   	 ).done(function(data) {
			showDashboard(data);
		});	
	}
	
	if (selectedSensor.indexOf("1_hour") > -1)
	{
		document.getElementById('sensorheader').textContent = '1 hour predictions';
		$.ajax({						
			 type : "POST",
			 dataType: "json",
			 url : "viewPredict1Hour",
			 data:  {sensorId: selectedSensor,
				 packId: selectedPack}, 
		  }
	   	 ).done(function(data) {
			showDashboard(data);
		});	
	}
	
	if (selectedSensor.indexOf("2_hours") > -1)
	{
		document.getElementById('sensorheader').textContent = '2 hours predictions';
		$.ajax({						
			 type : "POST",
			 dataType: "json",
			 url : "viewPredict2Hours",
			 data:  {sensorId: selectedSensor,
				 packId: selectedPack}, 
		  }
	   	 ).done(function(data) {
			showDashboard(data);
		});	
	}
	
	}

function showDashboard(data)
{
	$("#charts").show();
	
	$("#chart").empty();
	$("#legend").empty();
	
	var graph = new Rickshaw.Graph( {
		element: document.getElementById("chart"),
		width: 600,
		height: 300,
		renderer: 'line',
		series: data
	} );
	
	graph.render();
	
	var slider = new Rickshaw.Graph.RangeSlider({
		graph: graph,
		element: $('#slider')
	});

	var hoverDetail = new Rickshaw.Graph.HoverDetail( {
		graph: graph
	} );

	var legend = new Rickshaw.Graph.Legend( {
		graph: graph,
		element: document.getElementById('legend')

	} );
	
	var shelving = new Rickshaw.Graph.Behavior.Series.Toggle( {
		graph: graph,
		legend: legend
	} );

	var axes = new Rickshaw.Graph.Axis.Time( {
		graph: graph
	} );
	axes.render();
	
	var yAxis = new Rickshaw.Graph.Axis.Y({
	    graph: graph
	});

	yAxis.render();
	
}

function showDashboardOutbounds(data)
{
	$("#charts").show();
	
	$("#chartOutOfBounds").empty();
	$("#legendOutOfBounds").empty();
	
	var graphOutOfBounds = new Rickshaw.Graph( {
		element: document.getElementById("chartOutOfBounds"),
		width: 1000 - 180,
		height: 400,
		renderer: 'scatterplot',
		series: data
	} );
	
	graphOutOfBounds.render();
	
	var slider = new Rickshaw.Graph.RangeSlider({
		graph: graphOutOfBounds,
		element: $('#sliderOutOfBounds')
	});

	var hoverDetail = new Rickshaw.Graph.HoverDetail( {
		graph: graphOutOfBounds
	} );

	var legend = new Rickshaw.Graph.Legend( {
		graph: graphOutOfBounds,
		element: document.getElementById('legendOutOfBounds')

	} );
	
	var shelving = new Rickshaw.Graph.Behavior.Series.Toggle( {
		graph: graphOutOfBounds,
		legend: legend
	} );

	var axes = new Rickshaw.Graph.Axis.Time( {
		graph: graphOutOfBounds
	} );
	axes.render();
	
	var yAxis = new Rickshaw.Graph.Axis.Y({
	    graph: graphOutOfBounds
	});

	yAxis.render();
	
}

function populatePackageList() {
	$.ajax({						
	type : "GET",
	dataType: "text",
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
	$("#start").button().attr('disabled', true).addClass( 'ui-state-disabled' );
	
	populatePackageList();	
};



