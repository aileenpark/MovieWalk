<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>db input</title>
</head>
<body>
	<form enctype="multipart/form-data" name="form" method="post" action="upload.php">
		<table>
		<tr>
			<td>Name of the place:</td>
			<td><textarea name="name"></textarea></td>
		</tr>
		<tr>
			<td>Title:</td>
			<td><textarea name="title"></textarea></td>
		</tr>
		<tr>
			<td>Latitude:</td>
			<td><textarea name="latitude"></textarea></td>
		</tr>
		<tr>
			<td>Longitude:</td>
			<td><textarea name="longitude"></textarea></td>
		</tr>
		<tr>
			<td>Address:</td>
			<td><textarea name="address"></textarea></td>
		</tr>
		<tr>
			<td>Image1:</td>
			<td><input type="file" name="image1" /></td>
		</tr>
		<tr>
			<td>Image2:</td>
			<td><input type="file" name="image2" /></td>
		</tr>
		<tr>
			<td>Information:</td>
			<td><textarea name="information"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="submit" />
			</td>
		</tr>
		</table>
	</form>
</body>
</html>
