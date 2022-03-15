package com.topwise.sdk.emv.daoutils.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class uCapk {
    @Id
    private Long id;

    @Unique
    private String ridindex;
    
    private String rid = "";

    private String rindex = "";

    private int hashInd;

    private int arithInd;

    private String modul;

    private String exponent;

    private String expDate;

    private String checkSum;


    @Generated(hash = 782907039)
    public uCapk(Long id, String ridindex, String rid, String rindex, int hashInd, int arithInd, String modul,
            String exponent, String expDate, String checkSum) {
        this.id = id;
        this.ridindex = ridindex;
        this.rid = rid;
        this.rindex = rindex;
        this.hashInd = hashInd;
        this.arithInd = arithInd;
        this.modul = modul;
        this.exponent = exponent;
        this.expDate = expDate;
        this.checkSum = checkSum;
    }

    @Generated(hash = 197961787)
    public uCapk() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRindex() {
        return rindex;
    }

    public void setRindex(String rindex) {
        this.rindex = rindex;
    }

    public int getHashInd() {
        return hashInd;
    }

    public void setHashInd(int hashInd) {
        this.hashInd = hashInd;
    }

    public int getArithInd() {
        return arithInd;
    }

    public void setArithInd(int arithInd) {
        this.arithInd = arithInd;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getRidindex() {
        return ridindex;
    }

    public void setRidindex(String ridindex) {
        this.ridindex = ridindex;
    }
}
