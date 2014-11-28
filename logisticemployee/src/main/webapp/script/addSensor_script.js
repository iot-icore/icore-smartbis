$(document).ready(function () {
/* when add button is pressed verify if the required 
information is specified. If no then show messages. */
	$("#add").click(function() {
		var sensorId= document.getElementById("sensorId").value;
		if(sensorId.length<5)
			{
				alert ("The sensorID must have at least 5 characters! ");
				return;			
			}
		var sensor ={
			sensorID:sensorId,
			 };							
				$.ajax({						
					type : "POST",
					url : "addSingleSensor", 
					data : JSON.stringify(sensor),
					dataType : 'json',
					contentType : 'application/json',
					
				}).done(function(data) {
					alert("Your sensor has been added successfully!");						
			 });		
	});
	$("#readBytesButtons").click(function() {
		readBlob();
	});
});

// read data from the selected file
function readBlob() {

    var files = document.getElementById('file').files;
    if (!files.length) {
      alert('Please select a file!');
      return;
    }
	var name=  document.getElementById('file').value;
	var ext = name.split('.').pop().toLowerCase();
	console.log("ext: "+ext);
	if(!(ext=="csv"))
		{
		  alert('Please select a CSV file!');
	      return;
	      }
    var file = files[0];
    var start =  0;
    var stop =  file.size - 1;

    var reader = new FileReader();

    // If we use onloadend, we need to check the readyState.
    reader.onloadend = function(evt) {
      if (evt.target.readyState == FileReader.DONE) { // DONE == 2
          data=evt.target.result;
          createSensorList(data);
             }
    };

    var blob = file.slice(start, stop + 1);
    reader.readAsBinaryString(blob);
  }
  function createSensorList(data){
	       var alertString="\n";
	       var lines = data.split(/\r?\n/g);
		   for (var i=0;i<lines.length-1;i++)
		   {
		   		fields=lines[i].split(',');
		   		var sensor ={
		   				sensorID:fields[0],
		   				sensorType:fields[1],
		   				 };
		   		alertString=alertString+ fields[0]+"\n";
		   		$.ajax({						
		   			type : "POST",
		   			url : "addSingleSensor", 
		   			data : JSON.stringify(sensor),
		   			dataType : 'json',
		   			contentType : 'application/json',	   			
		   		});
		   }
	   alert("You've added the following sensors:"+alertString);
	  } 
function initialize() {
	$( "#accordion" ).accordion();
	$( "#add,#cancel,#readBytesButtons,#file" ).button();
};