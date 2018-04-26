package com.thy.project_180327;

/**
 * Created by alofo on 2018-03-27.
 */

public class Item {

    String facName, codeName, phne, addr, imgURL, homepage, closeday, xcoord, ycoord;

    public Item() {
    }

    public Item(String facName, String codeName, String phne, String addr, String imgURL, String homepage, String closeday, String xcoord, String ycoord) {
        this.facName = facName;
        this.codeName = codeName;
        this.phne = phne;
        this.addr = addr;
        this.imgURL = imgURL;
        this.homepage = homepage;
        this.closeday = closeday;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public String getXcoord() {
        return xcoord;
    }

    public void setXcoord(String xcoord) {
        this.xcoord = xcoord;
    }

    public String getYcoord() {
        return ycoord;
    }

    public void setYcoord(String ycoord) {
        this.ycoord = ycoord;
    }

    public String getFacName() {
        return facName;
    }

    public void setFacName(String facName) {
        this.facName = facName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getPhne() {
        return phne;
    }

    public void setPhne(String phne) {
        this.phne = phne;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getCloseday() {
        return closeday;
    }

    public void setCloseday(String closeday) {
        this.closeday = closeday;
    }
}
