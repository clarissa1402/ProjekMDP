package id.ac.projekmdp.kelas;

import java.text.DateFormat;

public class Transaksi {
    private int id;
    private int idUser;
    private int nikPegawai;
    private String tanggal;
    private int harga;
    private int status;

    public Transaksi(int id, int idUser, int nikPegawai, String tanggal, int harga, int status) {
        this.id = id;
        this.idUser = idUser;
        this.nikPegawai = nikPegawai;
        this.tanggal = tanggal;
        this.harga = harga;
        this.status = status; //0:Declined; 1:Accepted; 2:Pending; 3:Finish
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNikPegawai() {
        return nikPegawai;
    }

    public void setNikPegawai(int nikPegawai) {
        this.nikPegawai = nikPegawai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
