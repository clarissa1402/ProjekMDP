package id.ac.projekmdp.kelas;

public class Pegawai {
    private int id;
    private String nik,email,nama,telepon,alamat,password,jasa,deskripsi;
    private int saldo;

    public Pegawai(String nik, String email, String nama, String telepon, String alamat, String password, String jasa, String deskripsi) {
        this.nik = nik;
        this.email = email;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
        this.jasa = jasa;
        this.deskripsi = deskripsi;
        this.saldo = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
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
}
