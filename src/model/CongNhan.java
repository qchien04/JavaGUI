
package model;

import java.io.Serializable;

public class CongNhan implements Serializable{
    private int ma;
    private String ten,dc,sdt;
    private int bac;

    public CongNhan() {
        this.ma = 0;
        this.ten = "";
        this.dc = "";
        this.sdt = "";
        this.bac = 0;
    }
    public CongNhan(int ma, String ten, String dc, String sdt, int bac) {
        this.ma = ma;
        this.ten = ten;
        this.dc = dc;
        this.sdt = sdt;
        this.bac = bac;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }
    public Object[] toObject(){
        return new Object[]{
            ma,ten,dc,sdt,bac
        };
    }
    
    
}
