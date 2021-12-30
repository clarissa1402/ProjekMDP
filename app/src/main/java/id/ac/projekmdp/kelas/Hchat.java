package id.ac.projekmdp.kelas;

public class Hchat{
    private int id,user_id,pegawai_nik;

    public Hchat(int id, int user_id, int pegawai_nik) {
        this.id = id;
        this.user_id = user_id;
        this.pegawai_nik = pegawai_nik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPegawai_nik() {
        return pegawai_nik;
    }

    public void setPegawai_nik(int pegawai_nik) {
        this.pegawai_nik = pegawai_nik;
    }

}
