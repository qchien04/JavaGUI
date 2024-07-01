
package model;

import java.io.Serializable;

/**
 *
 * @author chien
 */
public class BangChamCong implements Serializable{
    private CongNhan congnhan;
    private Xuong xuong;
    private int ngay;

    public BangChamCong(CongNhan congnhan, Xuong xuong, int ngay) {
        this.congnhan = congnhan;
        this.xuong = xuong;
        this.ngay = ngay;
    }

    public BangChamCong() {
    }

    public CongNhan getCongnhan() {
        return congnhan;
    }

    public void setCongnhan(CongNhan congnhan) {
        this.congnhan = congnhan;
    }

    public Xuong getXuong() {
        return xuong;
    }

    public void setXuong(Xuong xuong) {
        this.xuong = xuong;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }
    public Object[] toObject(){
        return new Object[]{
            congnhan.getMa(),congnhan.getTen(),
            xuong.getMa(),ngay
        };
    }
    public String VietTk(){
        return congnhan.getMa()+":"+congnhan.getTen();
    }
    public double getThuNhap(){
        return ngay*xuong.getHso()*140000;
    }
    
}
