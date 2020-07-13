package com.ngodingyuk.cetakfotoku.ui.newtransaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.model.Foto;

public class TotalHargaActivity extends AppCompatActivity {
    public static final String DATA = "DATA";

    Button btnNext;
    TextView tvTotalHarga, tvJumlah, tvJenis;
    EditText etBayar;

    public String jumlah, jenis, sisaBayar;

    public Foto foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_harga);

        foto = getIntent().getParcelableExtra(DATA);

        btnNext = findViewById(R.id.btn_lanjut_detail);
        tvTotalHarga = findViewById(R.id.tv_harga_tiga_r);
        tvJumlah = findViewById(R.id.tv_harga_empat_enam);
        tvJenis = findViewById(R.id.tv_jenis);
        etBayar = findViewById(R.id.et_bayar);

        if (savedInstanceState == null){
            setData(foto);
        }else{
            onRestoreInstanceState(savedInstanceState);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaPelanggan = foto.getNamaPelanggan();
                String telepon = foto.getTelepon();
                String jumlah = foto.getJumlah();
                String jenis = foto.getJenisFoto();
                String totalHarga = tvTotalHarga.getText().toString().trim();
                String bayar = etBayar.getText().toString();
                String status = "Proses";

                if (TextUtils.isEmpty(bayar)) {
                    etBayar.setError(getResources().getString(R.string.payment_not_null));
                }else {
                    hitungSisaBayar(bayar, totalHarga);

                    Foto foto = new Foto(namaPelanggan, telepon, jumlah, jenis, totalHarga, bayar, sisaBayar, status);

                    Intent intent = new Intent(TotalHargaActivity.this, DetailTambahPesananActivity.class);
                    intent.putExtra(DetailTambahPesananActivity.DATA, foto);
                    startActivity(intent);
                }

            }
        });
    }

    private void setData(Foto foto){
        jumlah = foto.getJumlah();
        jenis = foto.getJenisFoto();

        tvJumlah.setText(jumlah);
        tvJenis.setText(jenis);

        //harga 1 lembar
        int duaX3 = 1000;
        int tigaX4 = 1500;
        int empatX6 = 2000;
        int duaR = 2500;
        int tigaR = 3000;
        int empatR = 4000;
        int limaR = 6000;
        int enamR = 8000;
        int delapanR = 10000;
        int sepuluhR = 12000;

        int jumlahCetak;

        try {
            jumlahCetak = Integer.parseInt(jumlah);
        }catch (NumberFormatException nfe){
            return;
        }

        int totalHargaduaX3 = jumlahCetak * duaX3;
        int totalHargatigaX4 = jumlahCetak * tigaX4;
        int totalHargaEmpatX6 = jumlahCetak * empatX6;
        int totalHargaDuaR = jumlahCetak * duaR;
        int totalHargaTigaR = jumlahCetak * tigaR;
        int totalHargaEmpatR = jumlahCetak * empatR;
        int totalHargaLimaR = jumlahCetak * limaR;
        int totalHargaEnamR = jumlahCetak * enamR;
        int totalHargaDelapanR = jumlahCetak * delapanR;
        int totalHargaSepuluhR = jumlahCetak * sepuluhR;

        switch (jenis) {
            case "2 x 3":
                tvTotalHarga.setText(String.valueOf(totalHargaduaX3));
                break;
            case "3 x 4":
                tvTotalHarga.setText(String.valueOf(totalHargatigaX4));
                break;
            case "4 x 6":
                tvTotalHarga.setText(String.valueOf(totalHargaEmpatX6));
                break;
            case "2 R":
                tvTotalHarga.setText(String.valueOf(totalHargaDuaR));
                break;
            case "3 R":
                tvTotalHarga.setText(String.valueOf(totalHargaTigaR));
                break;
            case "4 R":
                tvTotalHarga.setText(String.valueOf(totalHargaEmpatR));
                break;
            case "5 R":
                tvTotalHarga.setText(String.valueOf(totalHargaLimaR));
                break;
            case "6 R":
                tvTotalHarga.setText(String.valueOf(totalHargaEnamR));
                break;
            case "8 R":
                tvTotalHarga.setText(String.valueOf(totalHargaDelapanR));
                break;
            case "10 R":
                tvTotalHarga.setText(String.valueOf(totalHargaSepuluhR));
                break;
        }

    }

    private void hitungSisaBayar(String pBayar, String pTotalHarga){
        int jumlahBayar;
        int totalHarga;

        try {
            jumlahBayar = Integer.parseInt(pBayar);
            totalHarga = Integer.parseInt(pTotalHarga);
        }catch (NumberFormatException nfe){
            return;
        }

        int vSisaBayar = jumlahBayar - totalHarga;
        sisaBayar = String.valueOf(vSisaBayar);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DATA, foto);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Foto foto;
        foto = savedInstanceState.getParcelable(DATA);
        setData(foto);

    }

}