package id.ac.projekmdp.kelas;

public class Voucher {
    private int id;
    private String nama,harga,potongan;

    public Voucher(String nama, String harga, String potongan) {
        this.nama = nama;
        this.harga = harga;
        this.potongan = potongan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPotongan() {
        return potongan;
    }

    public void setPotongan(String potongan) {
        this.potongan = potongan;
    }
}
