<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="admin.bean.UserBean" %>
<%@ page  import="admin.dto.User"%>
<jsp:useBean id="userBean" class="admin.bean.UserBean" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AdminApp | HOME</title>
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
.user{
display:flex;
flex-wrap: wrap;
align-content: space-between;
flex-direction:row;
justify-content: center;

}

.username, .role, .delete, h1 {
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
	<h2><i>Add new account:</i></h2>
		<div class="add-new">
			
			<form action="?action=add-user" class="user" method="post">
			<h2>Username
			</h2>
			<input type="text" name="newusername">
			<h2>Password
			</h2>
			<input type="password" name="password">
			<h2>User role
			</h2>
			<select name="role">
				<option value="admin">admin</option>
				<option value="admin_product">admin_product</option>
				<option value="customer">customer</option>
			</select>
			<button type="submit" style="margin:10px">Add account</button>
			</form>
		</div>
<hr>
		
		<div class="list" style="margin-top: 50px;">
		
			<h2><i>Account list:</i></h2>
			<div class="user" >
				<h1>Username  	  </h1> 
				<h1> Role 		 </h1>	
				<h1>  Action 	 </h1>	
						
			</div>
			
			
		
			
			<% for (User user:userBean.getAll()){ %>
			<div class="user">
				
				<form class="user" action="?action=remove-user" method="post">
				<div class="username">
				<label>
					<%= user.getUsername()%>  
				</label>
				</div>
				
				<div class="role">			
				<label>
				    <%=user.getRole().getRole() %>
				</label>
				</div>
				<input type="hidden" name="id" value=<%=user.getId() %>>
	
				<div class="delete">				
			    <button  type="submit" style="margin-top: 20px;">Delete</button>
			    </div>
			    </form>
			</div>
					<%} %>
		</div>
	</div>
</body>
</html>