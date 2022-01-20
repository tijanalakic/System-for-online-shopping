<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page  import="shop.dto.Product"%>
<%@ page import="shop.bean.UserBean" %>
<%@ page import="shop.bean.TransactionBean" %>
<%@ page import="shop.dto.Item" %>
<jsp:useBean id="transactionBean" class="shop.bean.TransactionBean" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>WebShop	|	BILL</title>

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

.productname, .description, .price, .quantity, .amount, h1 {
  background-color: #f1f1f1;
  width: 200px;
  margin: 10px;
  text-align: center;
  line-height: 75px;
  font-size: 30px;
}
</style>
</head>

<body onload="attack()">
	<div class="page">
	<form action="?action=logout" method="post">
	<div class="logout" style="text-align: right">
	<button type="submit">Log out</button>
	</div>
	</form>
	<h2><i>Thank you for shoping with us <%=session.getAttribute("user") %>, see your order below, subtotal is <%=((TransactionBean)(session.getAttribute("transactionBean"))).getTotal() %></i></h2>

		<div class="list" style="margin-top: 50px;">
		
			<h2><i>Item list:</i></h2>
			<div class="product" >
				<h1> Item name </h1> 
				<h1> Description </h1>	
				<h1> Unit Price </h1>	
			    <h1> Quantity </h1>	
				<h1>  Amount </h1>	
						
			</div>
			
			
		
			
			<% for (Item item:((TransactionBean)(session.getAttribute("transactionBean"))).getItems()){ %>
			
			<div class="product">
				
				<div class="productname">
				<label>
					<%=item.getProduct().getName() %>  
				</label>
				</div>
				
				<div class="description">			
				<label>
				    <%=item.getProduct().getDescription() %>
				</label>
				</div>
				<div class="price">			
				<label>
				    <%=item.getProduct().getPrice() %>
				</label>
				</div>
				<input type="hidden" name="id" value=<%=item.getProduct().getId() %>>
				
	            <div class="quantity">				
       			<label>
				    <%=item.getQuantity() %>
				</label>			
				</div>
				
				<div class="amount">				
				<label>
				    <%=item.getPrice()%>
				</label>			 
			   </div>
			 
			</div>
					<% } %>
		</div>
		
		  <form action="?action=back" method="post">
				
	    	 <button  type="submit" style="margin-top: 50px; margin-left:500px; margin-right:500px;">Back</button>
		</form>
	</div>
</body>
</html>