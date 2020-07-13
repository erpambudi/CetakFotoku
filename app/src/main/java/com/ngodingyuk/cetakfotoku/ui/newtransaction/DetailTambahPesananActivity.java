package com.ngodingyuk.cetakfotoku.ui.newtransaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ngodingyuk.cetakfotoku.MainActivity;
import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.model.Foto;

public class DetailTambahPesananActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    public Foto foto;

    private DatabaseReference database;

    TextView tvNamaPelanggan, tvTelepon, tvJumlah, tvJenis, tvTotalHarga, tvBayar, tvSisaBayar, tvStatus;
    Button btnSubmit;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tambah_pesanan);

        foto = getIntent().getParcelableExtra(DATA);

        tvNamaPelanggan = findViewById(R.id.tv_harga_dua_tiga);
        tvTelepon = findViewById(R.id.tv_harga_tiga_empat);
        tvJumlah = findViewById(R.id.tv_harga_empat_enam);
        tvJenis = findViewById(R.id.tv_type);
        tvTotalHarga = findViewById(R.id.tv_harga_tiga_r);
        tvBayar = findViewById(R.id.tv_harga_empat_r);
        tvSisaBayar = findViewById(R.id.tv_harga_lima_r);
        tvStatus = findViewById(R.id.tv_harga_enam_r);
        btnSubmit = findViewById(R.id.btn_submit);

        database = FirebaseDatabase.getInstance().getReference();

        if (savedInstanceState == null){
            setData(foto);
        }else{
            onRestoreInstanceState(savedInstanceState);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTambahPesananActivity.this);
                builder.setMessage(getResources().getString(R.string.dialog_add_transaction));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loading = ProgressDialog.show(
                                DetailTambahPesananActivity.this,
                                null,
                                getResources().getString(R.string.loading),
                                true,
                                false
                        );

                        String namaPelanggan = foto.getNamaPelanggan().toLowerCase();
                        String telepon = foto.getTelepon();
                        String jumlah = foto.getJumlah();
                        String jenis = foto.getJenisFoto();
                        String totalHarga = foto.getTotalHarga();
                        String bayar = foto.getBayar();
                        String sisaBayar = foto.getSisaBayar();
                        String status = foto.getStatus();

                        submitTransaksi(new Foto(namaPelanggan, telepon, jumlah, jenis, totalHarga, bayar, sisaBayar, status));
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    private void setData(Foto foto){
        String namaPelanggan = foto.getNamaPelanggan();
        String telepon = foto.getTelepon();
        String jumlah = foto.getJumlah();
        String jenis = foto.getJenisFoto();
        String totalHarga = foto.getTotalHarga();
        String bayar = foto.getBayar();
        String sisaBayar = foto.getSisaBayar();
        String status = foto.getStatus();

        tvNamaPelanggan.setText(namaPelanggan);
        tvTelepon.setText(telepon);
        tvJumlah.setText(jumlah);
        tvJenis.setText(jenis);
        tvTotalHarga.setText(totalHarga);
        tvBayar.setText(bayar);
        tvSisaBayar.setText(sisaBayar);
        tvStatus.setText(status);
    }

    private void submitTransaksi(Foto foto){
        database.child("Pelanggan")
                .push()
                .setValue(foto)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        Intent i = new Intent(DetailTambahPesananActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        Toast.makeText(DetailTambahPesananActivity.this, getResources().getString(R.string.data_added), Toast.LENGTH_SHORT).show();

                    }
                });
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