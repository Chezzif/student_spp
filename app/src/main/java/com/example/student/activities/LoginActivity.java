package com.example.student.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.student.R;
import com.example.student.model.LoginResponse;
import com.example.student.network.ServiceClient;
import com.example.student.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etNis, etPassword;
    Spinner spTingkatan, spTahunAjaran;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        etNis = findViewById(R.id.et_nis_siswa);
        etPassword = findViewById(R.id.et_password_siswa);
        spTingkatan = findViewById(R.id.sp_tingkatan);
        spTahunAjaran = findViewById(R.id.sp_tahun_ajaran);

        pd = new ProgressDialog(this);
        boolean cekLogin = getSharedPreferences("session",MODE_PRIVATE).getBoolean("login",false);

        if (cekLogin){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        if (etNis.getText().toString().isEmpty()) {
            pd.dismiss();
            Toast.makeText(this, "Nis tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etPassword.getText().toString().isEmpty()) {
            pd.dismiss();
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        String nis = etNis.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String tingkatan = spTingkatan.getSelectedItem().toString();
        String tahunAjaran = spTahunAjaran.getSelectedItem().toString();

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<LoginResponse> requestLogin = service.loginSiswa("loginSiswa", "login", tingkatan, tahunAjaran, nis, password);
        requestLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.body().getHasil().equals("eror")) {
                    pd.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    SharedPreferences sp = getSharedPreferences("session",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("login",true);
                    editor.putString("nis",nis);
                    editor.putString("tingkatan",tingkatan);
                    editor.putString("tahunAjaran",tahunAjaran);
                    editor.putString("kelas",response.body().getHasil());
                    editor.commit();

                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "Koneksi Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
