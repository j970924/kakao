package com.example.kgitbank.kakao;

public class Member {
    @Override
    public String toString() {
        return "Member{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    int seq;
    String name, pass, email, phone, addr, photo;

    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddr() {
        return addr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    
}
