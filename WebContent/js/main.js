(function() {

	var loading = $('#loading');
	var status = $('#status');
	var video = $('#video');
	var videoForm = $('#videoForm');
	var file = $('#file');
	var submit = $('#submit');
	
	file.live('change', function(e){
		
		if(e.srcElement.files[0]){
			
			submit.removeAttr("disabled");
			
		}else{
			
			submit.attr("disabled", "disabled");
			
		}
	  
	});
	
	var ecodingWorkflow = function(mediaId){
		
		var url = "convert.do?mediaId=" + mediaId;
		
		$.ajax({
			method: "GET",
			url: url,
			success : function(xhr) {
				
				response=JSON.parse(xhr);
				
				if(response.status && response.status!="Finished"){
					
					if(response.status == "Error"){
						
						status.html("An error ocurred with your video conversion, send an email to filipemarruda@gmail.com!");
						loading.hide();
						videoForm.show();
						
					}else{
						
						status.html("Econding.com: "+response.status+"...");
						ecodingWorkflow(mediaId);
						
					}
					
				}else if(response.status == "Finished"){
					
					loading.empty();
					status.empty();
					var videoTag = '<video width="320" height="240" controls>'
								  + '<source src="'+response.destination+'" type="video/mp4">'
								  + '<source src="movie.ogg" type="video/ogg">'
								  + 'Your browser does not support the video tag.'
								  + '</video>';
					
					video.html(videoTag);
					
				}else if(response.error){
					
					var errorTag = '<span class="error">'+response.error+'</span>'; 
					status.html(errorTag);
				}
				
			}
		});
		
	}
	
	$('form').ajaxForm(
		{
			beforeSend : function() {
				status.empty();
				videoForm.hide();
			},
			uploadProgress : function(event, position, total,
					percentComplete) {
				if(percentComplete == 100){
					status.html("Sending video to Amazon S3...");
				}else{
					var percentCompleteText = percentComplete+"%";
					status.html("Uploading("+percentCompleteText+")...");
				}
				var loadingTag = '<img src="images/loading.gif">';
				loading.html(loadingTag);
			},
			complete : function(xhr) {
				
				status.html("Start encoding.com workflow...");
				var response = JSON.parse(xhr.responseText);
				
				if(response.mediaId){
					ecodingWorkflow(response.mediaId);
				}else if(response.error){
					status.html(response.error);
				}
				
			}
		});

})();