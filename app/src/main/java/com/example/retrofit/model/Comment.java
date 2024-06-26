package com.example.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Comment{

	@SerializedName("name")
	private String name;

	@SerializedName("postId")
	private int postId;

	@SerializedName("id")
	private int id;

	@SerializedName("body")
	private String body;

	@SerializedName("email")
	private String email;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPostId(int postId){
		this.postId = postId;
	}

	public int getPostId(){
		return postId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Comment{" + 
			"name = '" + name + '\'' + 
			",postId = '" + postId + '\'' + 
			",id = '" + id + '\'' + 
			",body = '" + body + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}