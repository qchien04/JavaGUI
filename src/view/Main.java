
package view;

import controller.IOFIile;
import controller.ValidException;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BangChamCong;
import model.CongNhan;
import model.Xuong;

public class Main extends javax.swing.JFrame {
    private List<CongNhan> CNList;
    private List<Xuong> XuongList;
    private List<BangChamCong> BangList;
    private DefaultTableModel tmCN,tmXuong,tmBang;
    private String CNFile,XuongFile,BangFile;
    
    public Main() {
        initComponents();
        
        tmCN=(DefaultTableModel)tbCN.getModel();
        tmXuong=(DefaultTableModel)tbXuong.getModel();
        tmBang=(DefaultTableModel)tbBang.getModel();
        
        XuongFile="src/controller/Xuong.dat";
        CNFile="src/controller/CN.dat";
        BangFile="src/controller/Bang.dat";
        
        if(new File(CNFile).exists()) CNList=IOFIile.read(CNFile);
        else CNList=new ArrayList<>();
        
        if(new File(XuongFile).exists()) XuongList=IOFIile.read(XuongFile);
        else XuongList=new ArrayList<>();
        
        if(new File(BangFile).exists()) BangList=IOFIile.read(BangFile);
        else BangList=new ArrayList<>();
        
        doccn();
        docxuong();
        docbang();
        
        setbuttoncn(true);
        setbuttonxuong(true);
        
        eventCN();
        eventxuong();
        eventBang();
        ButtonThongKe.addActionListener(e->{
            Map<String,Double> sum=BangList.stream().collect(
                                    Collectors.groupingBy(BangChamCong::VietTk,
                                    Collectors.summingDouble(BangChamCong::getThuNhap)));
            
            arenaThongKe.setText(sum.toString());
        });
        
    }
    private void doccn(){
        tmCN.setRowCount(0);
        for(CongNhan i:CNList){
            tmCN.addRow(i.toObject());
        }
    }
    private void docxuong(){
        tmXuong.setRowCount(0);
        for(Xuong i:XuongList){
            tmXuong.addRow(i.toObject());
        }
    }
    private void docbang(){
        tmBang.setRowCount(0);
        for(BangChamCong i:BangList){
            tmBang.addRow(i.toObject());
        }
    }
    private void setbuttoncn(boolean b){
        ButtonThem.setEnabled(b);
        ButtonBoQua.setEnabled(!b);
        ButtonCapNhap.setEnabled(!b);
    }
    private void setbuttonxuong(boolean b){
        ButtonThemX.setEnabled(b);
        ButtonBoQuaX.setEnabled(!b);
        ButtonCapNhapX.setEnabled(!b);
    }
    private void eventCN(){
        ButtonThem.addActionListener(e->{
            TextFieldMaCN.setText(1000+CNList.size()+"");
            TextFieldTenCN.requestFocus();
            setbuttoncn(false);
        });
        ButtonCapNhap.addActionListener(e->{
            try{
                if(!TextFieldSDTCN.getText().matches("\\d+")){
                   throw new ValidException("SDT sai dinh dang");
                }
                CongNhan c=new CongNhan(Integer.parseInt(TextFieldMaCN.getText()),
                                        TextFieldTenCN.getText(),
                                        TextFieldDiaChiCN.getText(),
                                        TextFieldSDTCN.getText(),
                                        Integer.parseInt(BacCN.getSelectedItem().toString()));
                CNList.add(c);
                tmCN.addRow(c.toObject());
                setbuttoncn(true);
            }
            catch(ValidException ex){
                JOptionPane.showMessageDialog(this,ex);
                TextFieldSDTCN.requestFocus();
                TextFieldSDTCN.setText("");
            }
        });
        ButtonXoa.addActionListener(e->{
            int row=tbCN.getSelectedRow();
            if(row>=0&&row<tbCN.getRowCount()){
                CNList.remove(row);
                tmCN.removeRow(row);
            }
            else{
                JOptionPane.showMessageDialog(this,"chon dong xoa");
            }

        });
        ButtonLuuFile.addActionListener(e->{
            IOFIile.write(CNFile, CNList);

        });
        ButtonBoQua.addActionListener(e->{
            setbuttoncn(true);

        });
    }
    private void eventxuong(){
        ButtonSuaX.addActionListener(e->{
            int row=tbXuong.getSelectedRow();
            if(row>=0&&row<tbXuong.getRowCount()){
                Xuong x=new Xuong(Integer.parseInt(TextFieldMaX.getText()),
                                   TextFieldTenX.getText(),
                                   Double.parseDouble(hsoX.getSelectedItem().toString()));
                XuongList.set(row,x);
                tmXuong.removeRow(row);
                tmXuong.insertRow(row, x.toObject());
                

            }
        });
        ButtonThemX.addActionListener(e->{
            TextFieldMaX.setText(1000+XuongList.size()+"");
            TextFieldTenX.requestFocus();
            setbuttonxuong(false);
        });
        ButtonCapNhapX.addActionListener(e->{
            Xuong x=new Xuong(Integer.parseInt(TextFieldMaX.getText()),
                                   TextFieldTenX.getText(),
                                   Double.parseDouble(hsoX.getSelectedItem().toString()));
            XuongList.add(x);
            tmXuong.addRow(x.toObject());
            setbuttonxuong(true);
        });
        ButtonXoaX.addActionListener(e->{
            int row=tbXuong.getSelectedRow();
            if(row>=0&&row<tbXuong.getRowCount()){
                XuongList.remove(row);
                tmXuong.removeRow(row);
            }
            else{
                JOptionPane.showMessageDialog(this,"chon dong xoa");
            }

        });
        ButtonLuuFileX.addActionListener(e->{
            IOFIile.write(XuongFile, XuongList);

        });
        ButtonBoQuaX.addActionListener(e->{
            setbuttonxuong(true);

        });
    }
    private int getSoNgay(int MaCongNhan){
        int so=0;
        for(BangChamCong i:BangList){
            if(i.getCongnhan().getMa()==MaCongNhan)so+=i.getNgay();
        }
        return so;
    }
    private CongNhan getCN(int ma){
        for(CongNhan i:CNList){
            if(i.getMa()==ma) return i;
        }
        return null;
    }
    private Xuong getXuong(int ma){
        for(Xuong i:XuongList){
            if(i.getMa()==ma) return i;
        }
        return null;
    }
    private void eventBang(){
        ButtonSxep.addActionListener(e->{
            int index=ComboboxSxep.getSelectedIndex();
            switch(index){
                case 0:
                    BangList.sort(new Comparator<BangChamCong>(){
                        @Override
                        public int compare(BangChamCong o1, BangChamCong o2) {
                            String[] t1=o1.getCongnhan().getTen().split("\\s+");
                            String[] t2=o2.getCongnhan().getTen().split("\\s+");
                            String ten1=t1[t1.length-1]+o1.getCongnhan().getTen();
                            String ten2=t2[t2.length-1]+o2.getCongnhan().getTen();
                            return ten1.compareToIgnoreCase(ten2);
                        }        
                    });
                    break;
                case 1:
                    BangList.sort(new Comparator<BangChamCong>(){
                        @Override
                        public int compare(BangChamCong o1, BangChamCong o2) {
                            int ngay1=o1.getNgay();
                            int ngay2=o2.getNgay();
                            return ngay1-ngay2;
                        }        
                    });
                    break;
            }
            tmBang.setRowCount(0);
            for(BangChamCong i: BangList){
                tmBang.addRow(i.toObject());
            }
            
        });
        ButtonLamTuoi.addActionListener(e->{
            ComboboxMaCN.removeAllItems();
            for(CongNhan i:CNList){
                ComboboxMaCN.addItem(i.getMa()+"");
            }
            ComboboxMaX.removeAllItems();
            for(Xuong i:XuongList){
                ComboboxMaX.addItem(i.getMa()+"");
            }
            
        });
        ButtonThemVaoBang.addActionListener(e->{
            try{
                int maCN=Integer.parseInt(ComboboxMaCN.getSelectedItem().toString());
                int maXuong=Integer.parseInt(ComboboxMaX.getSelectedItem().toString());
                int ngay=Integer.parseInt(TextFieldNgayLv.getText());
                if(getSoNgay(maCN)+ngay>31){
                    JOptionPane.showMessageDialog(this, "qua so luong ngay");
                }
                else{
                    BangChamCong b=new BangChamCong(getCN(maCN),getXuong(maXuong),ngay);
                    BangList.add(b);
                    tmBang.addRow(b.toObject());
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"nhap so");
            }
        });

        ButtonXoaBang.addActionListener(e->{
            int row=tbBang.getSelectedRow();
            if(row>=0&&row<tbBang.getRowCount()){
                BangList.remove(row);
                tmBang.removeRow(row);
            }
            else{
                JOptionPane.showMessageDialog(this,"chon dong xoa");
            }

        });
        ButtonLuuFileBang.addActionListener(e->{
            IOFIile.write(BangFile, BangList);

        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCN = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TextFieldMaCN = new javax.swing.JTextField();
        TextFieldTenCN = new javax.swing.JTextField();
        TextFieldDiaChiCN = new javax.swing.JTextField();
        TextFieldSDTCN = new javax.swing.JTextField();
        BacCN = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        ButtonThem = new javax.swing.JButton();
        ButtonCapNhap = new javax.swing.JButton();
        ButtonXoa = new javax.swing.JButton();
        ButtonBoQua = new javax.swing.JButton();
        ButtonLuuFile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TextFieldMaX = new javax.swing.JTextField();
        TextFieldTenX = new javax.swing.JTextField();
        hsoX = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        ButtonThemX = new javax.swing.JButton();
        ButtonCapNhapX = new javax.swing.JButton();
        ButtonXoaX = new javax.swing.JButton();
        ButtonBoQuaX = new javax.swing.JButton();
        ButtonLuuFileX = new javax.swing.JButton();
        ButtonSuaX = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbXuong = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbBang = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TextFieldNgayLv = new javax.swing.JTextField();
        ComboboxMaCN = new javax.swing.JComboBox<>();
        ComboboxMaX = new javax.swing.JComboBox<>();
        ComboboxSxep = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        ButtonThemVaoBang = new javax.swing.JButton();
        ButtonLuuFileBang = new javax.swing.JButton();
        ButtonSxep = new javax.swing.JButton();
        ButtonXoaBang = new javax.swing.JButton();
        ButtonLamTuoi = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        ButtonThongKe = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        arenaThongKe = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbCN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ma", "Ten", "Dia chi", "SDT", "Bac"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbCN);
        if (tbCN.getColumnModel().getColumnCount() > 0) {
            tbCN.getColumnModel().getColumn(4).setHeaderValue("Bac");
        }

        jLabel1.setText("Ma");

        jLabel2.setText("Ten");

        jLabel3.setText("Dia chi");

        jLabel4.setText("SDT");

        jLabel5.setText("Bac");

        TextFieldMaCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldMaCNActionPerformed(evt);
            }
        });

        TextFieldSDTCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldSDTCNActionPerformed(evt);
            }
        });

        BacCN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        BacCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BacCNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldTenCN))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldDiaChiCN))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldSDTCN))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BacCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TextFieldMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TextFieldTenCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TextFieldDiaChiCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TextFieldSDTCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(BacCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ButtonThem.setText("Them moi");
        ButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemActionPerformed(evt);
            }
        });

        ButtonCapNhap.setText("Cap nhap");
        ButtonCapNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCapNhapActionPerformed(evt);
            }
        });

        ButtonXoa.setText("Xoa");

        ButtonBoQua.setText("Bo qua");

        ButtonLuuFile.setText("Luu file");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ButtonThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonCapNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonBoQua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ButtonLuuFile, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonThem)
                .addGap(18, 18, 18)
                .addComponent(ButtonCapNhap)
                .addGap(18, 18, 18)
                .addComponent(ButtonXoa)
                .addGap(18, 18, 18)
                .addComponent(ButtonBoQua)
                .addGap(18, 18, 18)
                .addComponent(ButtonLuuFile)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Cong Nhan", jPanel1);

        jLabel6.setText("Ma");

        jLabel7.setText("Ten");

        jLabel8.setText("He So");

        TextFieldMaX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldMaXActionPerformed(evt);
            }
        });

        hsoX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        hsoX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hsoXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldMaX, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldTenX))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hsoX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TextFieldMaX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TextFieldTenX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(hsoX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ButtonThemX.setText("Them moi");
        ButtonThemX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemXActionPerformed(evt);
            }
        });

        ButtonCapNhapX.setText("Cap nhap");
        ButtonCapNhapX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCapNhapXActionPerformed(evt);
            }
        });

        ButtonXoaX.setText("Xoa");

        ButtonBoQuaX.setText("Bo qua");

        ButtonLuuFileX.setText("Luu file");

        ButtonSuaX.setText("Sua");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonThemX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonCapNhapX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonXoaX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonBoQuaX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLuuFileX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonSuaX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonThemX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonCapNhapX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonXoaX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonBoQuaX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonLuuFileX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonSuaX)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        tbXuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ma", "Ten", "He So"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbXuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbXuongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbXuong);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Xuong", jPanel2);

        tbBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ma CN", "Ten CN", "Ma Xuong", "Ngay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbBang);

        jLabel9.setText("Ma CN");

        jLabel10.setText("Ma Xuong");

        jLabel11.setText("So ngay LV");

        jLabel13.setText("Sap xep");

        ComboboxMaCN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboboxMaX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboboxSxep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ten cong nhan", "Theo so ngay lam viec" }));
        ComboboxSxep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboboxSxepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboboxSxep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboboxMaX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboboxMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldNgayLv, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ComboboxMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ComboboxMaX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextFieldNgayLv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ComboboxSxep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        ButtonThemVaoBang.setText("Them moi");

        ButtonLuuFileBang.setText("Luu File");
        ButtonLuuFileBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuFileBangActionPerformed(evt);
            }
        });

        ButtonSxep.setText("Sap xep");
        ButtonSxep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSxepActionPerformed(evt);
            }
        });

        ButtonXoaBang.setText("Xoa");

        ButtonLamTuoi.setText("Lam tuoi");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonLamTuoi)
                    .addComponent(ButtonXoaBang)
                    .addComponent(ButtonSxep)
                    .addComponent(ButtonThemVaoBang)
                    .addComponent(ButtonLuuFileBang))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonLamTuoi)
                .addGap(4, 4, 4)
                .addComponent(ButtonThemVaoBang)
                .addGap(18, 18, 18)
                .addComponent(ButtonSxep)
                .addGap(18, 18, 18)
                .addComponent(ButtonLuuFileBang)
                .addGap(18, 18, 18)
                .addComponent(ButtonXoaBang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Bang Cham Cong", jPanel3);

        ButtonThongKe.setText("ThongKe");
        ButtonThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThongKeActionPerformed(evt);
            }
        });

        arenaThongKe.setEditable(false);
        arenaThongKe.setColumns(20);
        arenaThongKe.setRows(5);
        jScrollPane5.setViewportView(arenaThongKe);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonThongKe)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(ButtonThongKe)
                .addGap(80, 80, 80))
        );

        jTabbedPane1.addTab("Thong ke", jPanel10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldMaCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldMaCNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldMaCNActionPerformed

    private void BacCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BacCNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BacCNActionPerformed

    private void TextFieldSDTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldSDTCNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldSDTCNActionPerformed

    private void ButtonCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCapNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonCapNhapActionPerformed

    private void ButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonThemActionPerformed

    private void TextFieldMaXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldMaXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldMaXActionPerformed

    private void ButtonThemXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonThemXActionPerformed

    private void ButtonCapNhapXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCapNhapXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonCapNhapXActionPerformed

    private void tbXuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbXuongMouseClicked
        int row=tbXuong.getSelectedRow();
        if(row>=0&&row<tbXuong.getRowCount()){
            TextFieldMaX.setText(tmXuong.getValueAt(row,0).toString());
            TextFieldTenX.setText(tmXuong.getValueAt(row,1).toString());
            for(int i=0;i<hsoX.getItemCount();i++){
                if(hsoX.getItemAt(i).toString().equals(tmXuong.getValueAt(row,2).toString())){
                    hsoX.setSelectedIndex(i);
                }
            }
            
        }
            
    }//GEN-LAST:event_tbXuongMouseClicked

    private void hsoXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hsoXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hsoXActionPerformed

    private void ComboboxSxepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboboxSxepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboboxSxepActionPerformed

    private void ButtonLuuFileBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuFileBangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonLuuFileBangActionPerformed

    private void ButtonSxepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSxepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonSxepActionPerformed

    private void ButtonThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThongKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonThongKeActionPerformed

  
    public static void main(String args[]) {
        

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BacCN;
    private javax.swing.JButton ButtonBoQua;
    private javax.swing.JButton ButtonBoQuaX;
    private javax.swing.JButton ButtonCapNhap;
    private javax.swing.JButton ButtonCapNhapX;
    private javax.swing.JButton ButtonLamTuoi;
    private javax.swing.JButton ButtonLuuFile;
    private javax.swing.JButton ButtonLuuFileBang;
    private javax.swing.JButton ButtonLuuFileX;
    private javax.swing.JButton ButtonSuaX;
    private javax.swing.JButton ButtonSxep;
    private javax.swing.JButton ButtonThem;
    private javax.swing.JButton ButtonThemVaoBang;
    private javax.swing.JButton ButtonThemX;
    private javax.swing.JButton ButtonThongKe;
    private javax.swing.JButton ButtonXoa;
    private javax.swing.JButton ButtonXoaBang;
    private javax.swing.JButton ButtonXoaX;
    private javax.swing.JComboBox<String> ComboboxMaCN;
    private javax.swing.JComboBox<String> ComboboxMaX;
    private javax.swing.JComboBox<String> ComboboxSxep;
    private javax.swing.JTextField TextFieldDiaChiCN;
    private javax.swing.JTextField TextFieldMaCN;
    private javax.swing.JTextField TextFieldMaX;
    private javax.swing.JTextField TextFieldNgayLv;
    private javax.swing.JTextField TextFieldSDTCN;
    private javax.swing.JTextField TextFieldTenCN;
    private javax.swing.JTextField TextFieldTenX;
    private javax.swing.JTextArea arenaThongKe;
    private javax.swing.JComboBox<String> hsoX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbBang;
    private javax.swing.JTable tbCN;
    private javax.swing.JTable tbXuong;
    // End of variables declaration//GEN-END:variables
}
