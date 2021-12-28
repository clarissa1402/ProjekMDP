package id.ac.projekmdp.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {


    private int id;
    private String email,nama,telepon,alamat,password;
    private int saldo,poin;

    public User(Integer id,String email, String nama, String telepon, String alamat, String password) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.saldo = 0;
        this.poin = 0;
    }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.email);
        dest.writeString(this.nama);
        dest.writeString(this.telepon);
        dest.writeString(this.alamat);
        dest.writeString(this.password);
        dest.writeInt(this.saldo);
        dest.writeInt(this.poin);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.email = source.readString();
        this.nama = source.readString();
        this.telepon = source.readString();
        this.alamat = source.readString();
        this.password = source.readString();
        this.saldo = source.readInt();
        this.poin = source.readInt();
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.email = in.readString();
        this.nama = in.readString();
        this.telepon = in.readString();
        this.alamat = in.readString();
        this.password = in.readString();
        this.saldo = in.readInt();
        this.poin = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
