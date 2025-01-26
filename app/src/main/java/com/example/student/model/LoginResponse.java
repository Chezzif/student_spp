package com.example.student.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("hasil")
	private String hasil;

	public void setHasil(String hasil){
		this.hasil = hasil;
	}

	public String getHasil(){
		return hasil;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"hasil = '" + hasil + '\'' + 
			"}";
		}
}