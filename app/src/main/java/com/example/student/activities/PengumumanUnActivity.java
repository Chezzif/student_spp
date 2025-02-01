package com.example.student.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.student.R;
import com.example.student.model.HasilUN;
import com.example.student.model.PengumumanUnResponse;
import com.example.student.network.ServiceClient;
import com.example.student.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengumumanUnActivity extends AppCompatActivity {
    EditText etNis;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pengumuman_un);

        etNis = findViewById(R.id.et_nis_un);
        pd = new ProgressDialog(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cekHasil(View view) {
        if (etNis.getText().toString().isEmpty()){
            Toast.makeText(this, "NIS tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        pd.setMessage("Load data...");
        pd.setCancelable(false);
        pd.show();

        String nis = etNis.getText().toString().trim();

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<PengumumanUnResponse> requestUN = service.requestHasilUn("un","readPengumumanUN",nis);
        requestUN.enqueue(new Callback<PengumumanUnResponse>() {
            @Override
            public void onResponse(Call<PengumumanUnResponse> call, Response<PengumumanUnResponse> response) {
                pd.dismiss();
                if (response.body().getHasilUN().getNama().equals("Nisn Tidak Ditemukan")){
                    Toast.makeText(PengumumanUnActivity.this, "NISN tidak Ditemukan", Toast.LENGTH_SHORT).show();
                } else {
                    // Ambil data dari hasil UN
                    HasilUN hasil = response.body().getHasilUN();

                    // Masukkan data ke dalam Bundle
                    Bundle b = new Bundle();
                    b.putInt("NISN", hasil.getNISN());
                    b.putString("Nama", hasil.getNama());
                    b.putInt("BahasaInggris", hasil.getBahasaInggris());
                    b.putInt("BahasaIndonesia", hasil.getBahasaIndonesia());
                    b.putInt("Matematika",hasil.getMatematika());
                    b.putInt("Kejuruan",hasil.getKejuruan());

                    // Kirim data ke activity baru
                    Intent i = new Intent(PengumumanUnActivity.this, HasilUnActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                    etNis.setText("");
                }
            }


            @Override
            public void onFailure(Call<PengumumanUnResponse> call, Throwable throwable) {
                pd.dismiss();
                Toast.makeText(PengumumanUnActivity.this, "Koneksi Eror", Toast.LENGTH_SHORT).show();
            }
        });
    }
}