<html>
	<head>
		<script>
			function addFile() {
				var form = document.getElementById("files");
				var div = document.createElement("div");
				var id = "_" + Date.now();
				div.innerHTML = '<input name="' + id + '" type="file"><button onclick="removeFile();">Remove</button>';
				div.childNodes[1].onclick = removeFile;
				form.appendChild(div);
			}

			function removeFile(e) {
				var form = document.getElementById("files");
				form.removeChild(e.target.parentNode);
			}

			function init() {
				document.getElementById("addFile").onclick = addFile;
				addFile();
			}

		</script>
	</head>
	<body onload="init();">
		<div style="border-bottom: 1px solid silver;">
			<form id="files" enctype="multipart/form-data" action="rest/sources" method="POST">
				<input type="submit">
			</form>
		</div>
		<button id="addFile">Add File</button>
	</body>
</html>
