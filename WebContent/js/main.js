(function() {


	var doc = $(document);
	var progressWraper = $('#progress-wraper');
	var progress = $('#progress');
	var status = $('#status');
	var video = $('#video');
	var videoForm = $('#videoForm');
	var file = $('#file');
	var submit = $('#submit');
	
	doc.on('change', '#file', function(e){
		
		if(e.target.files[0]){
			
			if(!e.target.files[0].type || e.target.files[0].type.startsWith("video")){
				submit.removeAttr("disabled");
			}else{
				alert("Please choose a video file!");
				submit.attr("disabled", "disabled");
				file.val("");
			}
			
		}else{
			
			submit.attr("disabled", "disabled");
			
		}
	  
	});
	
	var ecodingWorkflow = function(mediaId){
		
		var url = "convert.do?mediaId=" + mediaId;
		
		$.ajax({
			method: "GET",
			url: url,
			error : function(xhr) {
				status.html("An error ocurred with your video conversion, send an email to filipemarruda@gmail.com!");
				progress.removeClass("active");
				progress.addClass("progress-bar-danger");
				videoForm.show();
			},
			success : function(xhr) {
				
				response=JSON.parse(xhr);
				
				if(response.status && response.status!="Finished"){
					
					if(response.status.startsWith("Error")){
						
						status.html("An error ocurred with your video conversion, send an email to filipemarruda@gmail.com!");
						progress.removeClass("active");
						progress.addClass("progress-bar-danger");
						videoForm.show();
						
					}else{
						
						var preg = /.*\(([0-9]+.[0-9]+)\)/;
						var group = preg.exec(response.status);
						
						if(group){
							
							var encodingStatusProgess = parseInt(group[1]);
							var percentComplete = encodingStatusProgess+100;
							var progressStyle = "width: " +(percentComplete/2)+"%";
							progress.attr("aria-valuenow", percentComplete);
							progress.attr("style", progressStyle);

						}

						status.html("Econding.com: "+response.status+"...");
						ecodingWorkflow(mediaId);
						
					}
					
				}else if(response.status == "Finished"){
					
					progressWraper.hide();
					var videoTag = '<video width="320" height="240" controls>'
								  + '<source src="'+response.destination+'" type="video/mp4">'
								  + '<source src="movie.ogg" type="video/ogg">'
								  + 'Your browser does not support the video tag.'
								  + '</video>';
					
					video.html(videoTag);
					
				}else if(response.error){

					progress.removeClass("active");
					progress.addClass("progress-bar-danger");
					status.html(response.error);

				}
				
			}
		});
		
	}
	
	$('form').ajaxForm(
		{
			beforeSend : function() {
				progressWraper.show();
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

				var progressStyle = "width: " +(percentComplete/2)+"%";
				progress.attr("aria-valuenow", percentComplete);
				progress.attr("style", progressStyle);

			},
			error : function(xhr) {
				status.html("An error ocurred with your video conversion, send an email to filipemarruda@gmail.com!");
				progress.removeClass("active");
				progress.addClass("progress-bar-danger");
				videoForm.show();
			},
			complete : function(xhr) {
				
				status.html("Start encoding.com workflow...");
				var response = JSON.parse(xhr.responseText);
				
				if(response.mediaId){
					ecodingWorkflow(response.mediaId);
				}else if(response.error){
					progress.removeClass("active");
					progress.addClass("progress-bar-danger");
					status.html(response.error);
				}
				
			}
		});

})();