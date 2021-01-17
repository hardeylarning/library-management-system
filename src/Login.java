
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends javax.swing.JFrame {
Connection conn;
ResultSet rs,stfRs,stdRs;
PreparedStatement pst,stfPs,stdPs;
String matric, surname, firstname, dept, faculty, level, phone,book_id2, 
        book_name2,messages;
int to_return_date, check;

    public Login() {
        super("Login");
        initComponents();
        conn=javaconnect.ConnectDb();
       // autoReminder(1,"You have just a day left","sent1");
    }
    //admin details
        String admin = "libraryadmin";
        String adminpassword = "logindetails";
        String admin2 = "developer";
        String adminpassword2 = "developerdetails";
        // value
        public static String userNo;
        public static String userName;
        String stdMatric,stdSurname;
        String staff_id,staff_surname;
        //
          public void update(String tableName, String userId, String sent){
        try{
            String update="UPDATE '"+tableName+"' SET message=? where '"+userId+"'=?";
            pst=conn.prepareStatement(update);
            pst.setString(1, sent);
            pst.setString(2, userId);
            pst.executeUpdate();
            pst.close(); 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
     public void autoReminder(int checkDate, String tableName, String userId, String message,String sent) { 
        Date mydate  = new Date();
        SimpleDateFormat t= new SimpleDateFormat("yyyyMd");
        int date=Integer.parseInt(t.format(mydate));
        int dateChecker;
        dateChecker = date+checkDate;
        //check = checkDate;
        String sql="select * from '"+tableName+"' where To_return_date=?";
        try {
        pst=conn.prepareStatement(sql);
        pst.setInt(1, dateChecker);
        rs=pst.executeQuery();
        while(rs.next()){
            surname=rs.getString("Surname");
            firstname=rs.getString("Firstname");
            phone=rs.getString("Phone_no");
            book_id2=rs.getString("Book_id");
            book_name2=rs.getString("Book_name");
            to_return_date=rs.getInt("To_return_date");
            messages=rs.getString("message");
            if(messages == null || !messages.equalsIgnoreCase(sent)){
                String warning_message = "Dear " + surname + " " + firstname +
                        " "+" Just to remind you that " + "The Book " +
                        book_name2.toUpperCase() +" "+ " "
                + "you borrowed from Library is due for submission "+ message+ "\n"
                + "Kindly come for submission to avoid been penalized. Thank you";
                String mobile=phone;
                SmsAuto.sendSms(mobile, warning_message);
                update(tableName, userId, sent);
            }
        rs.close();
        pst.close();   
        }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void loginDetails(){
           String sql="select * from Account where Phone_no =? and Password =?";
            String stdsql="select * from Student where Matric_no =? and Password =?";
             String stfsql="select * from Staff where Staff_id =? and Password =?";
        
        // admin values
         String username = jTextField1.getText();
        String password = jPasswordField1.getText();
        userName= jTextField1.getText();
        //Student details
        try {
                stdPs=conn.prepareStatement(stdsql);
                stdPs.setString(1, jTextField1.getText());
                stdPs.setString(2, jPasswordField1.getText());
                stdRs=stdPs.executeQuery();
                if(stdRs.next()){
                    stdMatric = stdRs.getString("Matric_no");
                    stdSurname = stdRs.getString("Password");

                }
                stdRs.close();
                stdPs.close();
            } 
        catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
       // Staff Login details
        try {
                stfPs=conn.prepareStatement(stfsql);
                  stfPs.setString(1, jTextField1.getText());
                  stfPs.setString(2, jPasswordField1.getText());
                  stfRs=stfPs.executeQuery();
                  if(stfRs.next()){
                   staff_id = stfRs.getString("Staff_id");
                   staff_surname = stfRs.getString("Password");
                   
                  }
                  stfRs.close();
                   stfPs.close();
                } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1, jTextField1.getText());
                pst.setString(2, jPasswordField1.getText());
                rs=pst.executeQuery();
                if(rs.next()) {
                    rs.close();
                    pst.close();
                    //
                    
                    setVisible(false);
                    Loading ob=new Loading();
                    ob.setUpLoading();
                    ob.setVisible(true);
                }
      
        else if (username.equals(admin)&& password.equals(adminpassword) || username.equals(admin2)&& password.equals(adminpassword2)){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    autoReminder(3,"stdIssuedBook",phone,"which is remain 3 days","sent3");
                    autoReminder(2,"stdIssuedBook",phone,"which is remain 2 days","sent2");
                    autoReminder(1,"stdIssuedBook",phone,"which is remain just a day","sent1");
                    autoReminder(0,"stdIssuedBook",phone,"you have just few hours left","sent");
                    // staff
                    autoReminder(3,"stfIssuedBook",phone,"which is remain 3 days","sent3");
                    autoReminder(2,"stfIssuedBook",phone,"which is remain 2 days","sent2");
                    autoReminder(1,"stfIssuedBook",phone,"which is remain just a day","sent1");
                    autoReminder(0,"stfIssuedBook",phone,"you have just few hours left","sent");
                    // Customer
                    autoReminder(3,"cstIssuedBook",phone,"which is remain 3 days","sent3");
                    autoReminder(2,"cstIssuedBook",phone,"which is remain 2 days","sent2");
                    autoReminder(1,"cstIssuedBook",phone,"which is remain just a day","sent1");
                    autoReminder(0,"cstIssuedBook",phone,"you have just few hours left","sent");
                }
            }).start();
            
//            
            setVisible(false);
            adminLoading load=new adminLoading();
            load.setUpLoading();
            load.setVisible(true);
        }
        else if (username.equals(stdMatric) && password.equals(stdSurname)){
             setVisible(false);
            StdLoading ob=new StdLoading();
            ob.setUpLoading();
            ob.setVisible(true);
        }
        else if (username.equals(staff_id) && password.equals(staff_surname)){
             setVisible(false);
            StaffLoading ob=new StaffLoading();
            ob.setUpLoading();
            ob.setVisible(true);
        }
          
        else {
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
        }
    } 
    catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    finally{
    try {
        rs.close();
        pst.close();
    }
    catch (Exception e) {
        e.getMessage();
    }
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

        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 204, 255), 2, true), "LOGIN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 0, 204))); // NOI18N

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Reset Password");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("User Id");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("Trouble Login ?");

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\member1.jpg")); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Login");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Password");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Sign up");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPasswordField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel5.setText("Are you a student ?");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton4.setText("REGISTER HERE");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setText("OR");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("A Staff ?");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton5.setText("REGISTER HERE");
        jButton5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel6))
                            .addComponent(jLabel7))))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addGap(36, 36, 36))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2))
                            .addGap(17, 17, 17)
                            .addComponent(jButton3))
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jButton5))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        CustomerRegistration ob=new CustomerRegistration();
        ob.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        autoReminder();
        loginDetails();      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
            setVisible(false);
        Forgot ob=new Forgot();
        ob.setVisible(true);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
            setVisible(false);
            StudentRegistration ob=new StudentRegistration();
            ob.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
            setVisible(false);
            StaffRegistration ob=new StaffRegistration();
            ob.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

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
              /*  if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break; */
              {
              UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                
                }
            }
        } 
catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
