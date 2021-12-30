package id.ac.projekmdp.kelas;

public class Dchat {
    private int id,id_hchat,pengirim;
    private String chat;

    public Dchat(int id, int id_hchat, int pengirim, String chat) {
        this.id = id;
        this.id_hchat = id_hchat;
        this.pengirim = pengirim;//1 = user , 2=pegawai
        this.chat = chat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hchat() {
        return id_hchat;
    }

    public void setId_hchat(int id_hchat) {
        this.id_hchat = id_hchat;
    }

    public int getPengirim() {
        return pengirim;
    }

    public void setPengirim(int pengirim) {
        this.pengirim = pengirim;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
