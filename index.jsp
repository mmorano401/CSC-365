<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <link href="maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />
  </head>
  <p></p>
  <div class="container-fluid border">
    <h2 class="display-6 center"><center>CSC 365</center></h2>

    <h1 class="display-1 center"><center>Wikipedia Recommendations</center></h1>

    <body><center>Pick an article that interests you to see similar topics!</center></body>

    <select class="form-select" aria-label"Select Article">
      <option selected><center>Pick Article</center></option>
      <%=
        for(String url : urls){
          <option>startup.urls</option>
        }
      %>
    </select>
<p></p>

    <div class="container">
      <center>Option 1: </center>
      <p></p>
      <center>Option 2: </center>
    </div>

  </div>
</html>