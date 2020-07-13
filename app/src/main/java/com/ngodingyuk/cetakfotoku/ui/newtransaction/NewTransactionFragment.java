package com.ngodingyuk.cetakfotoku.ui.newtransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.model.Foto;

public class NewTransactionFragment extends Fragment {

    EditText etNamaPelanggan, etTelepon, etJumlah;
    Spinner spJenis;
    Button btnNext;
    public String namaPelanggan, telepon, jumlah, jenis;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_transaction, container, false);

        btnNext = root.findViewById(R.id.btn_lanjut_bayar);
        etNamaPelanggan = root.findViewById(R.id.et_nama_pelanggan);
        etTelepon = root.findViewById(R.id.et_telepon);
        etJumlah = root.findViewById(R.id.et_jumlah);
        spJenis = root.findViewById(R.id.sp_jenis);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namaPelanggan = etNamaPelanggan.getText().toString();
                telepon = etTelepon.getText().toString();
                jumlah = etJumlah.getText().toString();
                jenis = spJenis.getSelectedItem().toString();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(namaPelanggan)) {
                    isEmptyFields = true;
                    etNamaPelanggan.setError(getResources().getString(R.string.name_not_null));
                }

                if (TextUtils.isEmpty(telepon)) {
                    isEmptyFields = true;
                    etTelepon.setError(getResources().getString(R.string.telepon_not_null));
                }

                if (TextUtils.isEmpty(jumlah)) {
                    isEmptyFields = true;
                    etJumlah.setError(getResources().getString(R.string.quantity_not_null));
                }
                if (spJenis.getSelectedItemPosition() == 0){
                    isEmptyFields = true;
                    Toast.makeText(getActivity(), getResources().getString(R.string.choose_photo_size), Toast.LENGTH_SHORT).show();
                }

                if (!isEmptyFields){

                    Foto foto = new Foto(namaPelanggan, telepon, jumlah, jenis);

                    Intent intent = new Intent(getActivity(), TotalHargaActivity.class);
                    intent.putExtra(TotalHargaActivity.DATA, foto);
                    startActivity(intent);
                }

            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}