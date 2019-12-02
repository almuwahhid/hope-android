package id.co.hope.data.model;

import static lib.almuwahhid.utils.LibUi.monthName;

public class UserModel {
    public String id_user, email, password, nama, jenis_kelamin, tgl_lahir, program_studi, telp, confirmation_code, aktif, semester = "", photo_profil = "", pekerjaan_impian = "", universitas = "";

    public UserModel() {
    }

    public UserModel(String id_user, String email, String password, String nama, String jenis_kelamin, String tgl_lahir, String program_studi, String telp) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.program_studi = program_studi;
        this.telp = telp;
    }

    public UserModel(String email, String password, String nama, String jenis_kelamin, String tgl_lahir) {
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
    }

    public void setUserModel(String id_user, String email, String password, String nama, String jenis_kelamin, String tgl_lahir, String program_studi, String telp) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.program_studi = program_studi;
        this.telp = telp;
    }

    public void setUserModel(String email, String password, String nama, String jenis_kelamin, String telp) {
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.telp = telp;
    }

    public void setUserModel(String nama, String jenis_kelamin, String program_studi, String telp, String semester, String pekerjaan_impian, String universitas) {
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.program_studi = program_studi;
        this.telp = telp;
        this.semester = semester;
        this.pekerjaan_impian = pekerjaan_impian;
        this.universitas = universitas;
    }

    public void setUserModel(String program_studi, String universitas, String semester, String pekerjaan_impian) {
        this.program_studi = program_studi;
        this.semester = semester;
        this.universitas = universitas;
        this.pekerjaan_impian = pekerjaan_impian;
    }

    public String getPhoto_profil() {
        if(photo_profil.equalsIgnoreCase("")){
            return "google.com";
        } else {
            return photo_profil;
        }
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSemester() {
        return semester;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getTgl_real(String tgl_lahir) {
        try{
            return tgl_lahir.split("-")[2]+" "+monthName(Integer.valueOf(tgl_lahir.split("-")[1]))+" "+tgl_lahir.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }

    public String getProgram_studi() {
        return program_studi;
    }

    public String getTelp() {
        return telp;
    }

    public String getPekerjaan_impian() {
        if(pekerjaan_impian.equalsIgnoreCase("")){
            return "Belum memiliki pekerjaan impian";
        }
        return pekerjaan_impian;
    }

    public String getId_user() {
        return id_user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public String getAktif() {
        return aktif;
    }
}
