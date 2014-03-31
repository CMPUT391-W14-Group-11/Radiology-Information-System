/**
 * Javascript for @patient_ist.jsp
 * 
 * 
 * 
 */
function addRow(tableID) {

	var table = document.getElementById(tableID);

	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	var cell0 = row.insertCell(0);
	var element0 = document.createElement("input");
	element0.type = "text";
	element0.name = tableID + "-patientUsernames";
	cell0.appendChild(element0);
	
	var cell1 = row.insertCell(1);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = tableID +  "patientFirstName";
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(2);
	var element2 = document.createElement("input");
	element2.type = "text";
	element2.name = tableID +  "patientLastName";
	cell2.appendChild(element2);

	var cell3 = row.insertCell(3);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = tableID +  "patientAddress";
	cell3.appendChild(element3);

	var cell5 = row.insertCell(4);
	var element4 = document.createElement("input");
	element4.type = "email";
	element4.name = tableID +  "patientEmail";
	cell4.appendChild(element4);

	var cell6 = row.insertCell(5);
	var element5 = document.createElement("input");
	element5.type = "tel";
	element5.name = tableID +  "patientPhone";
	cell5.appendChild(element5);

	var cell7 = row.insertCell(6);
	var element6 = document.createElement("input");
	element6.type = "checkbox";
	element6.name = tableID +  "chkbox";
	cell6.appendChild(element6);
}

function deleteRow(tableID) {
	try {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var confirmation = confirm('Are you sure you want to remove the selected patients(s)?');
		if(confirmation == true) {
			for(var i=0; i<rowCount; i++) {
				var row = table.rows[i];
				var chkbox = row.cells[6].childNodes[0];
				if(null != chkbox && true == chkbox.checked) {
					table.deleteRow(i);
					rowCount--;
					i--;
				}
			}
		}
	} catch(e) {
		alert(e);
	}
}
