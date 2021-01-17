
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HARDEXICO
 */
public class CustomerRequestToBorrowBook extends javax.swing.JFrame {
Connection conn;
ResultSet rs,catRs;
PreparedStatement pst,catPst;
    //
String surname,firstname,occupation,address,rack;
String book_id,book_name,edition,author,publisher,isbn,pages,request_date,category,availability;
Login d = new Login();
 String user=d.userName;
    public CustomerRequestToBorrowBook() {
        super("BORROW BOOK");
        initComponents();
        conn=javaconnect.ConnectDb();
        showAllBook();
//        searchForBook();
    }
    
    //delete function
 
    //
    public void info(){
         String sql="select * from Account where Phone_no=?";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, user);
            rs=pst.executeQuery();
            if (rs.next()) {
            surname=rs.getString("Surname");
            firstname=rs.getString("Firstname");
            address=rs.getString("Address");
            rs.close();
            pst.close();
            }
            else {
                JOptionPane.showMessageDialog(null, "Connection Failed");
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Select All From Book
    public void showAllBook() {
        try {
            String sql = "select Book_id,Book_name,Edition,Author,Publisher,Pages,ISBN,Category,Availability from Book ORDER BY b_id DESC ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Search by Name
     public void searchForBook() {
        try {
            
            String search=searchByName.getText();
            if((search.length()>1)){
                String sql="select Book_id,Book_name,Edition,Author,Publisher,Pages,ISBN,Category,Availability from Book where Book_name LIKE '%"+search+"%' OR  Category LIKE '%"+search+"%' ORDER BY b_id DESC ";
                pst=conn.prepareStatement(sql); 
        //            pst.setString(1, searchByName.getText());
        //             pst.setString(2, searchByName.getText());
                    rs=pst.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                rs.close();
                pst.close();
            }
            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     //
     public void update(String value){
         try{
              String update="UPDATE Book SET Availability=? where Book_id=? ";
                pst=conn.prepareStatement(update);
                pst.setString(1, value);
                pst.setString(2, book_id);
                pst.executeUpdate();
                pst.close();      
            }
                catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
         }
     public void borrowBook(){
         try{
                String sql="insert into cstRequestToBorrowBook(Book_id,Book_name,Edition,Rack_no,Category,Phone_no,Surname,Firstname,Address,Request_date) values(?,?,?,?,?,?,?,?,?,?)";
                pst=conn.prepareStatement(sql);
                pst.setString(1, book_id);
                pst.setString(2, book_name);
                pst.setString(3, edition);
                pst.setString(4, rack);
                pst.setString(5, category);
                pst.setString(6, user);
                pst.setString(7, surname);
                pst.setString(8, firstname);
                pst.setString(9, address);
                Timestamp t= new Timestamp(System.currentTimeMillis());
                request_date =t.toString();
                pst.setString(10, request_date);
                pst.executeUpdate();
                pst.close();
                update("requested");
                JOptionPane.showMessageDialog(null, "Your request is being submitted to borrow "+book_name+"\n You will receive message from Library for pick up\n Thank you");
                      
            }
                catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
    }
     //
     
     public void setLimitation(){
         
         String sql="SELECT * FROM cstIssuedBook WHERE Phone_no = ? ";
         try{
              info();
             int i=0;
           pst=conn.prepareStatement(sql);
           pst.setString(1, user);
           rs=pst.executeQuery();
           while(rs.next()){   
              i++;
           }
           rs.close();
           pst.close();
            if(i>=2){
                   JOptionPane.showMessageDialog(null, "You have Exceeded the book "
                           + "you can borrow in the Library\n Kindly return already borrowed book\n"
                           + "To have a privilege to borrow another book. Thank you. ");
               }
               else{
                   requestToBorrowBook();
               }
                
         }
         catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
         
     }
//Request to borrow Book
     public void requestToBorrowBook(){
            try{
                String rack_no ="SELECT Rack_no FROM Book where Book_id=? ";
                pst=conn.prepareStatement(rack_no);
                pst.setString(1, book_id);
                rs=pst.executeQuery();
                if(rs.next()){
                    rack=rs.getString("Rack_no");
                    rs.close();
                    pst.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
           
        try {
            String available ="SELECT Availability FROM Book where Book_id=? ";
                pst=conn.prepareStatement(available);
                pst.setString(1, book_id);
                rs=pst.executeQuery();
                if(rs.next()){
                    availability=rs.getString("Availability");
                    if(availability.equalsIgnoreCase("no")){
                        JOptionPane.showMessageDialog(null, "The Book is not available to borrow.\n "
                                + "It has been issued out to another user\n Check back later. Thank you");
                    }
                    else if(availability.equalsIgnoreCase("yes")){
                          int response = JOptionPane.showConfirmDialog(this, "Do you want to continue? \nYES to submit request to borrow book\n "
                + "NO to go back without any action ",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(response==JOptionPane.YES_OPTION){
                        borrowBook();
                        showAllBook();
                        searchForBook();
                  }
                        
                      
                    }
                    else if(availability.equalsIgnoreCase("requested")){
                        JOptionPane.showMessageDialog(null, "The Book is not available to borrow.\n "
                                + "Another user has placed a request to borrow the book\n Check back in 24hours later. Thank you");
                    }
                    rs.close();
                    pst.close();
                }
            
           
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        searchByName = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setType(java.awt.Window.Type.UTILITY);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 2, true), "BOOK DETAIL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Book ID", "Book Name", "Edition", "Author", "Publisher", "Pages", "ISBN", "Category", "Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setIntercellSpacing(new java.awt.Dimension(4, 4));
        jTable1.setRowHeight(15);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        searchByName.setToolTipText("Enter book name here");
        searchByName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editedsearch1.jpg")); // NOI18N
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("SEARCH FOR BOOK EITHER BY NAME OR BY CATEGORY");

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 2, true), "BOOK DETAIL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(0, 102, 102))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Book ID", "Book Name", "Edition", "Author", "Publisher", "Pages", "ISBN", "Category", "Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setIntercellSpacing(new java.awt.Dimension(4, 4));
        jTable2.setRowHeight(15);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(30);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(30);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("CLICK ON BOOK OF YOUR CHOICE TO BORROW FROM LIBRARY");

        jMenuBar1.setBackground(new java.awt.Color(204, 204, 204));

        jMenu1.setText("File");

        jMenuItem4.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\about us.png")); // NOI18N
        jMenuItem4.setText("Profile");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem1.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editexit.jpg")); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editlogout.jpg")); // NOI18N
        jMenuItem2.setText("Logout");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\usingicon.png")); // NOI18N
        jMenuItem3.setText("About us");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Book");

        jMenuItem5.setText("Borrow Book");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem7.setText("Return Book");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setText("Search for Book");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem6.setText("View Books");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(260, 260, 260)
                            .addComponent(searchByName, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(48, 48, 48)
                            .addComponent(jButton2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel7)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchByName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(42, 42, 42)
                        .addComponent(jButton4)
                        .addGap(37, 37, 37))))
        );

        setSize(new java.awt.Dimension(1321, 718));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerInterface ob=new CustomerInterface();
        ob.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        searchForBook();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
         int i = jTable2.getSelectedRow();
        TableModel model = jTable2.getModel();
         try{
            book_id = model.getValueAt(i, 0).toString();
            book_name = model.getValueAt(i, 1).toString();
            edition = model.getValueAt(i, 2).toString();
            author = model.getValueAt(i, 3).toString();
            publisher = model.getValueAt(i, 4).toString();
            pages = model.getValueAt(i, 5).toString();
            isbn = model.getValueAt(i, 6).toString();
            category = model.getValueAt(i, 7).toString();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setLimitation();
//        searchForBook();
//        showAllBook();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int i = jTable1.getSelectedRow();
        TableModel model = jTable1.getModel();
         try{
            book_id = model.getValueAt(i, 0).toString();
            book_name = model.getValueAt(i, 1).toString();
            edition = model.getValueAt(i, 2).toString();
            author = model.getValueAt(i, 3).toString();
            publisher = model.getValueAt(i, 4).toString();
            pages = model.getValueAt(i, 5).toString();
            isbn = model.getValueAt(i, 6).toString();
            category = model.getValueAt(i, 7).toString();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setLimitation();
        
//        int response = JOptionPane.showConfirmDialog(this, "Do you want to continue? \nYES to submit request to borrow book\n "
//                + "NO to go back without any action ",
//                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if(response==JOptionPane.YES_OPTION){
//            requestToBorrowBook();
//            searchForBook();
//            showAllBook();
//      }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerProfile stdpro= new CustomerProfile();
        stdpro.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Login ob=new Login();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        userAbout ob=new userAbout();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerRequestToBorrowBook ob=new CustomerRequestToBorrowBook();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerRequestToReturnBook ob=new CustomerRequestToReturnBook();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        cstsearch ob = new cstsearch();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerViewBooks sv = new CustomerViewBooks();
        sv.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */ 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
//                {
//              UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//                
//                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentRequestToBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentRequestToBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentRequestToBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentRequestToBorrowBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentRequestToBorrowBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField searchByName;
    // End of variables declaration//GEN-END:variables
}
