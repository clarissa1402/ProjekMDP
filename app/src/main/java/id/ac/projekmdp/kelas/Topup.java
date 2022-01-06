package id.ac.projekmdp.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class Topup implements Parcelable {
    private int id;
    private int saldo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Topup(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
