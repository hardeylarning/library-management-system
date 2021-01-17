import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class Forgot extends javax.swing.JFrame {

Connection conn;
ResultSet rs,stdRs,stfRs;
PreparedStatement pst,stdPs,stfPs;
    private String stdPhone;
    private String one_time;
    String surname, firstname;
    public static String phone;
    private String cstPhone;
    private String stfPhone;
    SmsAuto sms = new SmsAuto();
    public Forgot() {
        super("Forgot Password");
        initComponents();
        conn=javaconnect.ConnectDb();
    }
    //
    public void Forgot(){
             String sql="select * from Account where Phone_no =?";
            String stdsql="select * from Student where Phone_no =?";
             String stfsql="select * from Staff where Phone_no =?";
             Random r=new Random();
            String otp_rand=""+r.nextInt(1000000+1);
            String mobile=phone_no.getText();
        
        // admin values
        
        //Student details
        try {
                stdPs=conn.prepareStatement(stdsql);
                  stdPs.setString(1, phone_no.getText());
                  stdRs=stdPs.executeQuery();
                  if(stdRs.next()){
                   stdPhone = stdRs.getString("Phone_no");
                   surname = stdRs.getString("Surname");
                   firstname = stdRs.getString("Firstname");
                   
                  }
                  stdRs.close();
                   stdPs.close();
                } catch (Exception e) {
              //  JOptionPane.showMessageDialog(null, e);
            }
       // Staff Login details
        try {
                stfPs=conn.prepareStatement(stfsql);
                  stfPs.setString(1, phone_no.getText());
                  stfRs=stfPs.executeQuery();
                  if(stfRs.next()){
                    stfPhone = stfRs.getString("Phone_no");
                    surname = stfRs.getString("Surname");
                    firstname = stfRs.getString("Firstname");
                   
                  }
                  stfRs.close();
                   stfPs.close();
                } catch (Exception e) {
              //  JOptionPane.showMessageDialog(null, e);
            }
        // Customer details
        try {
                pst=conn.prepareStatement(sql);
                  stfPs.setString(1, phone_no.getText());
                  rs=pst.executeQuery();
                  if(rs.next()){
                    cstPhone = rs.getString("Phone_no");
                    surname = rs.getString("Surname");
                    firstname = rs.getString("Firstname");
                   
                  }
                  rs.close();
                   pst.close();
                } catch (Exception e) {
              //  JOptionPane.showMessageDialog(null, e);
            }
        if(mobile.equalsIgnoreCase(stdPhone)){
            sms.sendSms(stdPhone, "Dear, "+firstname+" "+surname+"\n Your ONE TIME PASSWORD is "+otp_rand);
            one_time=otp_rand;
            phone = stdPhone;
            JOptionPane.showMessageDialog(null, "Dear, "+firstname+" "+surname+" \nOne time Password has been Successfully sent to your Registered Phone Number");
        }
        else if(mobile.equalsIgnoreCase(stfPhone)){
            sms.sendSms(stfPhone, "Dear, "+firstname+" "+surname+"\n Your ONE TIME PASSWORD is "+otp_rand);
            one_time=otp_rand;
            phone = stfPhone;
            JOptionPane.showMessageDialog(null, "Dear, "+firstname+" "+surname+" One time Password has been Successfully sent to your Registered Phone Number");
        }
        else if(mobile.equalsIgnoreCase(cstPhone)){
            sms.sendSms(cstPhone, "Dear, "+firstname+" "+surname+"\n Your ONE TIME PASSWORD is "+otp_rand);
            one_time=otp_rand;
            phone = cstPhone;
            JOptionPane.showMessageDialog(null, "Dear, "+firstname+" "+surname+"\nOne time Password has been Successfully sent to your Registered Phone Number");
        }
        else {
            JOptionPane.showMessageDialog(null, "Incorrect or Unregistered Phone Number");
        }
        
    }
    //

    //
    public void Retrieve() {
      //  String sql="select * from Account where Answer='"+answer+"'";
     String otp_confirm = otp.getText();
      if(otp_confirm.equalsIgnoreCase(one_time)){
          JOptionPane.showMessageDialog(null, "One time password suceessfully matched\n You can now reset your password");
          this.setVisible(false);
          ResetPassword rp = new ResetPassword();
          rp.setVisible(true);
      }
      else {
          JOptionPane.showMessageDialog(null, "Incorrect One time password");
      }
    }
    //
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        otp = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        phone_no = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 3, true), "Forget Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(153, 0, 51))); // NOI18N

        jLabel1.setText("Phone No");

        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("One Time Password");

        jButton2.setText("Retrieve");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(otp, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone_no, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton1))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(phone_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(otp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(63, 63, 63)
                .addComponent(jButton3)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Forgot();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //retrieve button
        Retrieve();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Login ob=new Login();
        ob.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel  */
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
// {
//              UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//                
//                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forgot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Forgot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField otp;
    private javax.swing.JTextField phone_no;
    // End of variables declaration//GEN-END:variables
}
