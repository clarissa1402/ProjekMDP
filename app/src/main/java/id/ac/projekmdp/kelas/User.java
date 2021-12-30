package id.ac.projekmdp.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String email,nama,telepon,alamat,password,jenis_kelamin;
    private int saldo,poin;

    public User(Integer id,String email, String nama, String telepon, String alamat, String password,String jenis_kelamin) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jenis_kelamin=jenis_kelamin;
        this.saldo = 0;
        this.poin = 0;
    }

    public User(Integer id,String email, String nama, String telepon, String alamat, String password, String jenis_kelamin, int saldo) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jenis_kelamin=jenis_kelamin;
        this.saldo = saldo;
        this.poin = 0;
    }

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        nama = in.readString();
        telepon = in.readString();
        alamat = in.readString();
        password = in.readString();
        jenis_kelamin = in.readString();
        saldo = in.readInt();
        poin = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(nama);
        parcel.writeString(telepon);
        parcel.writeString(alamat);
        parcel.writeString(password);
        parcel.writeString(jenis_kelamin);
        parcel.writeInt(saldo);
        parcel.writeInt(poin);
    }
}
