package com.ngodingyuk.cetakfotoku.ui.transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.model.Foto;

public class DetailPelangganActivity extends AppCompatActivity {
    public static final String DATA = "DATA";

    TextView tvNamaPelanggan, tvTelepon, tvJumlah, tvJenis, tvTotalHarga, tvBayar, tvSisaBayar, tvStatus;
    Spinner spStatus;
    Button btnUpdate, btnDelete;
    ProgressDialog loading;

    private DatabaseReference database;

    public Foto foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        foto = getIntent().getParcelableExtra(DATA);

        tvNamaPelanggan = findViewById(R.id.tv_harga_dua_tiga);
        tvTelepon = findViewById(R.id.tv_harga_tiga_empat);
        tvJumlah = findViewById(R.id.tv_harga_empat_enam);
        tvJenis = findViewById(R.id.tv_harga_dua_r);
        tvTotalHarga = findViewById(R.id.tv_harga_tiga_r);
        tvBayar = findViewById(R.id.tv_harga_empat_r);
        tvSisaBayar = findViewById(R.id.tv_harga_lima_r);
        tvStatus = findViewById(R.id.tv_harga_enam_r);
        spStatus = findViewById(R.id.sp_status);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        database = FirebaseDatabase.getInstance().getReference();

        if (savedInstanceState == null){
            setData(foto);
        }else{
            onRestoreInstanceState(savedInstanceState);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPelangganActivity.this);
                builder.setMessage(getResources().getString(R.string.dialog_update_transaction));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String namaPelanggan = foto.getNamaPelanggan();
                        String telepon = foto.getTelepon();
                        String jumlah = foto.getJumlah();
                        String jenis = foto.getJenisFoto();
                        String totalHarga = foto.getTotalHarga();
                        String bayar = foto.getBayar();
                        String sisaBayar = foto.getSisaBayar();
                        String status = spStatus.getSelectedItem().toString().trim();

                        if (spStatus.getSelectedItemPosition() == 0){
                            Toast.makeText(DetailPelangganActivity.this, getResources().getString(R.string.select_transaction_status), Toast.LENGTH_SHORT).show();
                        }else {
                            loading = ProgressDialog.show(
                                    DetailPelangganActivity.this,
                                    null,
                                    getResources().getString(R.string.loading),
                                    true,
                                    false
                            );

                            updateTransaksi(new Foto(namaPelanggan, telepon, jumlah, jenis, totalHarga, bayar, sisaBayar, status), foto.getId());
                        }
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPelangganActivity.this);
                builder.setMessage(getResources().getString(R.string.dialog_delete_transaction));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        loading = ProgressDialog.show(
                                DetailPelangganActivity.this,
                                null,
                                getResources().getString(R.string.loading),
                                true,
                                false
                        );

                        deleteTransaksi(foto.getId());
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

    private void updateTransaksi(Foto foto, String id){
        database.child("Pelanggan")
                .child(id)
                .setValue(foto)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        Toast.makeText(DetailPelangganActivity.this, getResources().getString(R.string.dialog_done_update), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void deleteTransaksi(String id){
        database.child("Pelanggan")
                .child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        Toast.makeText(DetailPelangganActivity.this, getResources().getString(R.string.dialog_done_delete), Toast.LENGTH_SHORT).show();
                        finish();
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