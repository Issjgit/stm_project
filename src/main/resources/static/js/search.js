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

//WordDocument Download
 function downloadDocument() {
	 var word = document.getElementById("attachedWord").value;
		console.log("File name "+word)
		// Make a request to download the PDF file
		/*fetch('/STM_File/' + word, {
			method: 'GET',
		})
			.then(response => response.blob())
			.then(blob => {
				// Create a temporary link element
				var link = document.createElement('a');

				// Create a blob URL for the PDF file content
				var blobUrl = window.URL.createObjectURL(blob);

				// Set the link's href attribute to the blob URL
				link.href = blobUrl;

				// Set the download attribute with the desired file name
				link.download = 'Worddownloaded.docx';

				// Append the link to the body
				document.body.appendChild(link);

				// Trigger a click event on the link
				link.click();

				// Remove the link from the body
				document.body.removeChild(link);

				// Release the blob URL
				window.URL.revokeObjectURL(blobUrl);
			})
			.catch(error => console.error('Error downloading file:', error));
			*/
			var formData = {
					DocFileName: word
				}
				console.log("Start")
				$.ajax(
					{
						type: 'POST',
						contentType: 'application/json; charset=utf-8',
						url: 'http://localhost:8080/DocBtntnClick',
						data: JSON.stringify(formData),
						datatype: 'json',
						success: function () {
							window.location = '/stmNo';
						}
					}
					// END
				);
	}

