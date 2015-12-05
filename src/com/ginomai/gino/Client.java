package com.ginomai.gino;

public class Client {
public Client(String email, String user) {
		super();
		this.email = email;
		this.user = user;
	}
@Override
public String toString() {
	
	return "Email="+email+",Name="+user;
}
public Client() {
	// TODO Auto-generated constructor stub
}
private String email;
private String user;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}


}
