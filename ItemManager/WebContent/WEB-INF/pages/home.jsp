<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="manager.bean.ProductBean" %>
<%@ page  import="manager.dto.Product"%>
<jsp:useBean id="productBean" class="manager.bean.ProductBean" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ItemManager | HOME</title>
<style type="text/css">
body {
  background-color: lightblue;
  
}
hr {
margin-top:50px;
border:solid 1px #3399FF;
width: 96%;
color: #3399FF;
height: 1px;
}
.page{
display:flex;
flex-direction: column;
margin: 20px;
}
.add-new{
display:flex;
flex-direction: row;
align-items: baseline;
justify-content: center;
}

h2{
margin:10px;
 }
 
.list{
display:flex;
justify-content: center;
flex-direction: column;
}
.header{
display:flex;
flex-direction: column;
justify-content: center;
}
.product{
display:flex;
flex-wrap: wrap;
align-content: space-between;
flex-direction:row;
justify-content: center;

}

.productname, .description, .price, .delete, h1 {
  background-color: #f1f1f1;
  width: 200px;
  margin: 10px;
  text-align: center;
  line-height: 75px;
  font-size: 30px;
}
</style>
</head>

<body>
	<div class="page">
	<form action="?action=logout" method="post">
	<div class="logout" style="text-align: right">
	<button type="submit">Log out</button>
	</div>
	</form>
	<h2><i>Add new product:</i></h2>
		<div class="add-new">
			
			<form action="?action=add-product" class="product" method="post">
			<h2>Product name
			</h2>
			<input type="text" name="productName">
			<h2>Description
			</h2>
			<input type="text" name="description">
			<h2>Price
			</h2>
			<input type="number" name="price" min="1" max="100000">
			<button type="submit" style="margin:10px">Add product</button>
			</form>
		</div>
<hr>
		
		<div class="list" style="margin-top: 50px;">
		
			<h2><i>Product list:</i></h2>
			<div class="product" >
				<h1> Product name </h1> 
				<h1> Description </h1>	
				<h1> Price </h1>	
				<h1>  Action </h1>	
						
			</div>
			
			
		
			
			<% for (Product product:ProductBean.getAll()){ %>
			<% if (product.isActive()){ %>
			
			<div class="product">
				
				<form class="product" action="?action=remove-product" method="post">
				<div class="productname">
				<label>
					<%=product.getName() %>  
				</label>
				</div>
				
				<div class="description">			
				<label>
				    <%=product.getDescription() %>
				</label>
				</div>
					<div class="price">			
				<label>
				    <%=product.getPrice() %>
				</label>
				</div>
				<input type="hidden" name="id" value=<%=product.getId() %>>
	
				<div class="delete">				
			    <button  type="submit" style="margin-top: 20px;">Delete</button>
			    </div>
			    </form>
			</div>
					<%}} %>
		</div>
	</div>
</body>
</html>