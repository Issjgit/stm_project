function openModal(message) {
	const modal = document.getElementById('modal');
	const modalMessage = document.getElementById('modalMessage');
	modalMessage.textContent = message;
	modal.style.display = 'block';
}

function closeModal() {
	const modal = document.getElementById('modal');
	modal.style.display = 'none';
}

function openModal(modalId) {
	const modal = document.getElementById(modalId);
	modal.style.display = 'flex';
}

function closeModal(modalId) {
	const modal = document.getElementById(modalId);
	modal.style.display = 'none';
}


var stmNo = document.getElementById('stmNumber').value;
var stmVersion = document.getElementById('revisionNumber').value;


function updatePdfFileName(textInputId, fileInputId, errorElementId) {
	const textInput = document.getElementById(textInputId);
	const fileInput = document.getElementById(fileInputId);
	//const errorElement = document.getElementById(errorElementId);

	if (fileInput.files.length > 0) {
		const fileName = fileInput.files[0].name;
		const fileExtension = fileName.split('.').pop().toLowerCase();

		if (fileExtension !== 'pdf') {
			textInput.value = '';
			fileInput.value='';
			openModal(errorElementId);
		} else {
			// Construct the new file name (STMStmNoRevisionNo.extension)
			    const formattedRevision = parseInt(stmVersion) + 1;			    
                const paddedRevision = formattedRevision.toString();
                const revision="0"+paddedRevision;               
                const newFileName = "STM" + stmNo + revision + "." + fileExtension;

			// Update the value of the text input
			textInput.value = newFileName;
			closeModal(errorElementId);
		}
	} else {
		textInput.value = '';
		fileInput.value='';
		closeModal(errorElementId);
	}
}
function updateWordFileName(textInputId, fileInputId, errorElementId) {
	const textInput = document.getElementById(textInputId);
	const fileInput = document.getElementById(fileInputId);
	//const errorElement = document.getElementById(errorElementId);

	if (fileInput.files.length > 0) {
		const fileName = fileInput.files[0].name;
		const fileExtension = fileName.split('.').pop().toLowerCase();

		if (fileExtension !== 'doc' && fileExtension !== 'docx') {
			textInput.value = '';
			fileInput.value='';			
			openModal(errorElementId);
		} else {
			// Construct the new file name (STMStmNoRevisionNo.extension)
			    const formattedRevision = parseInt(stmVersion) + 1;			    
                const paddedRevision = formattedRevision.toString();
                const revision="0"+paddedRevision;               
                const newFileName = "STM" + stmNo + revision + "." + fileExtension;

			// Update the value of the text input
			textInput.value = newFileName;
			closeModal(errorElementId);
		}
	} else {
		textInput.value = '';
		fileInput.value='';		
		closeModal(errorElementId);
	}
}



function confirmRevise() {
	console.log("Confirming revise...");
	closeModal('reviseConfirmation');
	document.getElementById('reviseForm').submit();
}


function validateDateFormat(inputField) {
	var enteredDate = inputField.value;
	var dateFormat = /^(\d{4})\/(\d{2})\/(\d{2})$/;

	if (!dateFormat.test(enteredDate)) {
		alert("Invalid date format. Please enter the date in yyyy/MM/dd format.");
		// Clear the input field or perform any other actions as needed
		inputField.value = "";
	}
}

function formatDraftingDate(input) {
        // Remove any non-numeric characters from the input
        var numericInput = input.value.replace(/\D/g, '');

        // Check if the input has at least 8 characters (yyyymmdd)
        if (numericInput.length >= 8) {
            // Extract year, month, and day
            var year = numericInput.substring(0, 4);
            var month = numericInput.substring(4, 6);
            var day = numericInput.substring(6, 8);

            // Format the date as yyyy/mm/dd
            var formattedDate = year + '/' + month + '/' + day;
            // Set the formatted date back to the input
            input.value = formattedDate;
        }
    }
    document.addEventListener("DOMContentLoaded", function() {
        var revisionNumberInput = document.getElementById('revisionNumber');
        var currentRevision = parseInt(revisionNumberInput.value) + 1;
        var formattedRevision = currentRevision.toString();
        var revision="0"+formattedRevision;
        revisionNumberInput.value = revision;
    });


// Set the duration for the messages to be displayed in milliseconds (e.g., 5000 for 5 seconds)
var messageDuration = 5000;

// Function to hide the success message after a certain duration
function hideSuccessMessage() {
	var successMessageDiv = document.getElementById('successMessageDiv');
	if (successMessageDiv) {
		successMessageDiv.style.display = 'none';
	}
}

// Function to hide the error message after a certain duration
function hideErrorMessage() {
	var errorMessageDiv = document.getElementById('errorMessageDiv');
	if (errorMessageDiv) {
		errorMessageDiv.style.display = 'none';
	}
}

// Set timeouts to hide messages after the specified duration
 setTimeout(hideSuccessMessage, messageDuration);
 setTimeout(hideErrorMessage, messageDuration);