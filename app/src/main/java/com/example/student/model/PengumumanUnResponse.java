package com.example.student.model;

import com.google.gson.annotations.SerializedName;

public class PengumumanUnResponse{

	@SerializedName("HasilUN")
	private HasilUN hasilUN;

	public void setHasilUN(HasilUN hasilUN){
		this.hasilUN = hasilUN;
	}

	public HasilUN getHasilUN(){
		return hasilUN;
	}

	@Override
 	public String toString(){
		return 
			"PengumumanUnResponse{" + 
			"hasilUN = '" + hasilUN + '\'' + 
			"}";
		}
}