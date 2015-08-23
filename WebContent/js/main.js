(function() {

	var loading = $('#loading');
	var status = $('#status');
	var video = $('#video');
	var videoForm = $('#videoForm');

	var ecodingWorkflow = function(mediaId){
		
		var url = "convert.do?mediaId=" + mediaId;
		
		$.ajax({
			method: "GET",
			url: url,
			success : function(xhr) {
				
				response=JSON.parse(xhr);
				
				if(response.status && response.status!="Finished"){
					
					status.html("Econding.com: "+response.status+"...");
					ecodingWorkflow(mediaId);
					
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
					status.html("Uploading...");
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