package com.example.student.model;

import com.google.gson.annotations.SerializedName;

public class SppResponse{

	@SerializedName("hasil")
	private Hasil hasil;

	public void setHasil(Hasil hasil){
		this.hasil = hasil;
	}

	public Hasil getHasil(){
		return hasil;
	}

	@Override
 	public String toString(){
		return 
			"SppResponse{" + 
			"hasil = '" + hasil + '\'' + 
			"}";
		}
}