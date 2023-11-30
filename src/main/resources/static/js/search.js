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
