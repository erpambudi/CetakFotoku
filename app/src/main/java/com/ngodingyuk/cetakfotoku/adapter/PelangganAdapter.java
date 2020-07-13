package com.ngodingyuk.cetakfotoku.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.model.Foto;
import com.ngodingyuk.cetakfotoku.ui.transaction.DetailPelangganActivity;

import java.util.List;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganAdapter.MyViewHolder> {
    private Activity activity;
    private List<Foto> listPelanggan;

    public PelangganAdapter(Activity activity, List<Foto> listPelanggan) {
        this.activity = activity;
        this.listPelanggan = listPelanggan;
    }

    public List<Foto> getListPelanggan() {
        return listPelanggan;
    }

    public void setListPelanggan(List<Foto> listPelanggan) {
        this.listPelanggan = listPelanggan;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pelanggan_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Foto foto = listPelanggan.get(position);

        holder.tvNamaPelanggan.setText(foto.getNamaPelanggan());
        holder.tvJenisFoto.setText(foto.getJenisFoto());
        holder.tvTotalHarga.setText(foto.getTotalHarga());

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailPelangganActivity.class);
                intent.putExtra(DetailPelangganActivity.DATA, listPelanggan.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPelanggan.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamaPelanggan;
        public TextView tvJenisFoto;
        public TextView tvTotalHarga;
        public CardView cvItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPelanggan = itemView.findViewById(R.id.tv_harga_dua_tiga);
            tvJenisFoto = itemView.findViewById(R.id.tv_harga_dua_r);
            tvTotalHarga = itemView.findViewById(R.id.tv_harga_tiga_r);
            cvItem = itemView.findViewById(R.id.cv_item);

        }

    }
}
