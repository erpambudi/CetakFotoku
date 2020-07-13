package com.ngodingyuk.cetakfotoku.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Foto implements Parcelable {
    private String id;
    private String namaPelanggan;
    private String telepon;
    private String jumlah;
    private String jenisFoto;
    private String totalHarga;
    private String bayar;
    private String sisaBayar;
    private String status;

    public Foto() {
    }

    public Foto(String namaPelanggan, String telepon, String jumlah, String jenisFoto) {
        this.namaPelanggan = namaPelanggan;
        this.telepon = telepon;
        this.jumlah = jumlah;
        this.jenisFoto = jenisFoto;
    }

    public Foto(String namaPelanggan, String telepon, String jumlah, String jenisFoto, String totalHarga, String bayar, String sisaBayar, String status) {
        this.namaPelanggan = namaPelanggan;
        this.telepon = telepon;
        this.jumlah = jumlah;
        this.jenisFoto = jenisFoto;
        this.totalHarga = totalHarga;
        this.bayar = bayar;
        this.sisaBayar = sisaBayar;
        this.status = status;
    }

    protected Foto(Parcel in) {
        id = in.readString();
        namaPelanggan = in.readString();
        telepon = in.readString();
        jumlah = in.readString();
        jenisFoto = in.readString();
        totalHarga = in.readString();
        bayar = in.readString();
        sisaBayar = in.readString();
        status = in.readString();
    }

    public static final Creator<Foto> CREATOR = new Creator<Foto>() {
        @Override
        public Foto createFromParcel(Parcel in) {
            return new Foto(in);
        }

        @Override
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJenisFoto() {
        return jenisFoto;
    }

    public void setJenisFoto(String jenisFoto) {
        this.jenisFoto = jenisFoto;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getSisaBayar() {
        return sisaBayar;
    }

    public void setSisaBayar(String sisaBayar) {
        this.sisaBayar = sisaBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(namaPelanggan);
        parcel.writeString(telepon);
        parcel.writeString(jumlah);
        parcel.writeString(jenisFoto);
        parcel.writeString(totalHarga);
        parcel.writeString(bayar);
        parcel.writeString(sisaBayar);
        parcel.writeString(status);
    }
}
