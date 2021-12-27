package id.ac.projekmdp.kelas;

public class User {


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
}
