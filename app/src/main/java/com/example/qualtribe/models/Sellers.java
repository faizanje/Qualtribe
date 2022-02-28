package com.example.qualtribe.models;

public class Sellers {
    //define structure of seller
    public String pkg1;
    public String pkg2;
    public String pkg3;
    public String email;
    public String name;
    public String desc1;
    public String desc2;
    public String desc3;
    public String intro;
    public String img;
    public String service;
    public String userId;

    public Sellers(String pkg1, String pkg2, String pkg3, String email, String name, String service, String desc1,
                   String desc2, String desc3, String intro, String img) {
        this.pkg1 = pkg1;
        this.pkg2 = pkg2;
        this.pkg3 = pkg3;
        this.email = email;
        this.name = name;
        this.service = service;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
        this.intro = intro;
        this.img = img;
    }

    public Sellers() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPkg1() {
        return pkg1;
    }

    public void setPkg1(String pkg1) {
        this.pkg1 = pkg1;
    }

    public String getPkg2() {
        return pkg2;
    }

    public void setPkg2(String pkg2) {
        this.pkg2 = pkg2;
    }

    public String getPkg3() {
        return pkg3;
    }

    public void setPkg3(String pkg3) {
        this.pkg3 = pkg3;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Sellers{" +
                "userId='" + userId + '\'' +
                "pkg1='" + pkg1 + '\'' +
                ", pkg2='" + pkg2 + '\'' +
                ", pkg3='" + pkg3 + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", service='" + service + '\'' +
                ", desc1='" + desc1 + '\'' +
                ", desc2='" + desc2 + '\'' +
                ", desc3='" + desc3 + '\'' +
                ", intro='" + intro + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
