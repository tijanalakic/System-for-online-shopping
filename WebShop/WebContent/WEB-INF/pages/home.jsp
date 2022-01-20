<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="shop.bean.ProductBean" %>
<%@ page import="shop.dto.Product"%>
<%@ page import="shop.bean.UserBean" %>
<jsp:useBean id="productBean" class="shop.bean.ProductBean" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>WebShop	|	HOME</title>
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
justify-content: center;
}
.item-list{
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

.productname, .description, .price, .quantity, .add, h1 {
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
	<h2><i>Hello, <%=session.getAttribute("user") %>!</i></h2>
	
		<div class="list" style="margin-top: 50px;">
		
			<h2><i>Product list:</i></h2>
			<div class="product" >
				<h1> Product name </h1> 
				<h1> Description </h1>	
				<h1> Price </h1>	
			    <h1> Quantity </h1>	
				<h1>  Action </h1>	
						
			</div>
			
			
		
			
			<% for (Product product:ProductBean.getAll()){ %>
			<% if (product.isActive()){ %>
			
			<div class="product">
				
				<form class="product" action="?action=add-item" method="post">
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
	            <div class="quantity">				
			    <input type="number" name="quantity" value="0">
				</div>
				<div class="add">				
			    <button  type="submit" style="margin-top: 20px;">Add</button>
			    </div>
			    </form>
			</div>
					<%}} %>
		</div>
		<form action="?action=buy" method="post">
				
	     <button  type="submit" style="margin-top: 50px; margin-left:500px; margin-right:500px;">Buy</button>
				</form>
	</div>
</body>
</html>