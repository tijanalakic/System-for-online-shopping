package admin.dto;

public enum Role {
	ADMIN("admin"),
	ADMIN_PRODUCT("admin_product"),
	CUSTOMER("customer");
	
	String role;
	
	private Role(String role) {
		this.role=role;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role=role;
	}
}
