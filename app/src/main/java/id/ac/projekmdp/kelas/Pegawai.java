package id.ac.projekmdp.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class Pegawai implements Parcelable{
    //private int id;
    private String email,nama,telepon,alamat,password,jasa,deskripsi,url;
    private int saldo,harga,nik;

    public Pegawai(String email, String nama, String telepon, String alamat, String password, String jasa, String deskripsi, String url, int saldo, int harga, int nik) {
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jasa = jasa;
        this.deskripsi = deskripsi;
        this.url = url;
        this.saldo = saldo;
        this.harga = harga;
        this.nik = nik;
    }

    public Pegawai(Integer nik, String email, String nama, String telepon, String alamat, String password, String jasa, String deskripsi) {
        this.nik = nik;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jasa = jasa;
        this.harga = 50000;
        this.deskripsi = deskripsi;
        this.url = "";
        this.saldo = 0;
    }

    public Pegawai(Integer nik, String email, String nama, String telepon, String alamat, String password, String jasa, String deskripsi, int harga, int saldo) {
        this.nik = nik;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jasa = jasa;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.url = "";
        this.saldo = saldo;
    }

    protected Pegawai(Parcel in) {
        email = in.readString();
        nama = in.readString();
        telepon = in.readString();
        alamat = in.readString();
        password = in.readString();
        jasa = in.readString();
        deskripsi = in.readString();
        saldo = in.readInt();
        harga = in.readInt();
        nik = in.readInt();
    }


    public static final Creator<Pegawai> CREATOR = new Creator<Pegawai>() {
        @Override
        public Pegawai createFromParcel(Parcel in) {
            return new Pegawai(in);
        }

        @Override
        public Pegawai[] newArray(int size) {
            return new Pegawai[size];
        }
    };

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    //    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }


    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJasa() {
        return jasa;
    }

    public void setJasa(String jasa) {
        this.jasa = jasa;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(nama);
        parcel.writeString(telepon);
        parcel.writeString(alamat);
        parcel.writeString(password);
        parcel.writeString(jasa);
        parcel.writeString(deskripsi);
        parcel.writeString(url);
        parcel.writeInt(saldo);
        parcel.writeInt(harga);
        parcel.writeInt(nik);
    }
}
