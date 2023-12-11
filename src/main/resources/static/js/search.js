function resetcall() {
	var x = document.getElementById('searchTable');

	x.style.display = "none";
};

function searchcall() {
	var x = document.getElementById('searchTable');
	{
		if (x.style.display === "none") {
			x.style.display = "block";
		} else {
			x.style.display = "none";
		}
	}
};

function onLoadcall(value) {
	console.log("Value is : " + value);
	var x = document.getElementById('searchTable');
	if (value == "1") {
		x.style.display = "none";
		console.log(value);
	}
	window.onload = onLoadcall;
};

// Function to show and hide the popup 
function togglePopup() {
	$(".content").toggle();
}

$(document).ready(function submiytData() {
	$(".content").click(function() {
		$(this).toggle();
	});
});

$(function() {
	$("table tr").dblclick(function() {
		console.log("on Double Click")
		console.log(this.rowIndex);

		var formData = {
			rowIndex: this.rowIndex
		}
		console.log("Start")
		$.ajax(
			{
				type: 'POST',
				contentType: 'application/json; charset=utf-8',
				url: 'http://localhost:8080/doubleClickTableRow',
				data: JSON.stringify(formData),
				datatype: 'json',
				success: function() {
					window.location = '/Modify';
				}
			}
		);
	})
});

//PDF Download function
function downloadPDF() {
	var pdf = document.getElementById("pdfFileNameHid").value;
	console.log("File name " + pdf);
	fetch("http://localhost:8080/downloadattachmentpdf?file=" + pdf)
		.then(response => response.blob())             
		.then(blob => {									
			var link = document.createElement('a');		
														
			var blobUrl = window.URL.createObjectURL(blob);	

			link.href = blobUrl;
			link.download = 'downloaded_file.pdf';
			link.download = pdf;
			document.body.appendChild(link);
			link.click();

			document.body.removeChild(link);
			window.URL.revokeObjectURL(blobUrl);
		})
		.catch(error => console.error('Error downloading file:', error));

	var formData = {
		pdfFile: pdf
	}
	console.log("Start")
}


//Word Document Download
 function downloadDocument() {
	 var word = document.getElementById("docFileNameHid").value;
     console.log("File name: " + word);

   //var url = 'http://localhost:8080/generateWord.docx?word=' + encodeURIComponent(word);
           fetch("http://localhost:8080/downloadattachmentpdf?file=" + word)
                .then(response => response.blob())
                .then(blob => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download =word;
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                })
                .catch(error => console.error('Error:', error));
        }