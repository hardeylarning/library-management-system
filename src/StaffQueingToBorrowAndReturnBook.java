import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author HARDEXICO
 */
public class StaffQueingToBorrowAndReturnBook extends javax.swing.JFrame {
 Connection conn;
    PreparedStatement pst;
    ResultSet rs;

//
    String book_id2, book_name2, edition2, author2, isbn2, pages2, rack2, cat2,publisher2, issued_date;
    int to_return_date;
    String staff_id, surname, firstname, dept, title, phone, address, request_date,get_to_return_date,rack_no;
//    Sms sms = new Sms();
    /**
     * Creates new form StudentQueingToBorrowAndReturnBook
     */
    public StaffQueingToBorrowAndReturnBook() {
        initComponents();
         conn = javaconnect.ConnectDb();
        stfRequestToBorrowBook();
        stfRequestToReturnBook();
    }
    //
     public void stfRequestToBorrowBook() {
        try {
            String sql = "select * from stfRequestToBorrowBook ORDER BY Db_id DESC ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            borrow_table.setModel(DbUtils.resultSetToTableModel(rs));
           rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //

    public void insertToBorrowReceivedMessage() {
        try {

            String insert = "insert into stfToBorrowReceivedMessage(Book_id,Book_name,Edition,";
                   insert+="Rack_no,Category,Staff_id,Surname,Firstname,Phone_no,Address,Request_date,Message_time)";
                   insert+="values(?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(insert);
            pst.setString(1, book_id2);
            pst.setString(2, book_name2);
            pst.setString(3, edition2);
            pst.setString(4, rack2);
            pst.setString(5, cat2);
            pst.setString(6, staff_id);
            pst.setString(7, surname);
            pst.setString(8, firstname);
            pst.setString(9, phone);
            pst.setString(10, address);
            pst.setString(11, request_date);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String time = t.toString();
            pst.setString(12, time);
            pst.executeUpdate();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Return details

    public void insertToReturnReceivedMessage() {

        try {

            String insert = "insert into stfToReturnReceivedMessage(Staff_id,Surname,Firstname,Phone_no,";
                   insert+= "Address,Book_id,Book_name,Edition,Category, Issued_date,To_return_date,";
                   insert+= "Request_date,Message_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(insert);
            pst.setString(1, staff_id);
            pst.setString(2, surname);
            pst.setString(3, firstname);
            pst.setString(4, phone);
            pst.setString(5, address);
            pst.setString(6, book_id2);
            pst.setString(7, book_name2);
            pst.setString(8, edition2);
            pst.setString(9, cat2);
            pst.setString(10, issued_date);
            pst.setInt(11, to_return_date);
            pst.setString(12, request_date);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String time = t.toString();
            pst.setString(13, time);
            pst.executeUpdate();
           // JOptionPane.showMessageDialog(null, "Book is being submitted for return\n You will get a message from Library for fully return\n Thank you");
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void update(String option){
        try{
            
            String update="UPDATE Book SET Availability=? where Book_id=? ";
            pst=conn.prepareStatement(update);
            pst.setString(1, option);
            pst.setString(2, book_id2);
            pst.executeUpdate();
            pst.close();
            } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Issue Book to Student
    public void issueBook() {
        //Username='"+username+"'"
        try {

            String insert = "insert into stfIssuedBook(Book_id,Book_name,Edition,";
                    insert+="Category,Staff_id,Surname,Firstname,Phone_no,Address,";
                    insert+="Issued_date,To_return_date) values(?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(insert);
            pst.setString(1, book_id2);
            pst.setString(2, book_name2);
            pst.setString(3, edition2);
            pst.setString(4, cat2);
            pst.setString(5, staff_id);
            pst.setString(6, surname);
            pst.setString(7, firstname);
            pst.setString(8, phone);
            pst.setString(9, address);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String time = t.toString();
            pst.setString(10, time);
            pst.setInt(11, to_return_date);
            pst.executeUpdate();
            pst.close();
            update("no");
            JOptionPane.showMessageDialog(null, "Book has been Successfully Issued Out To\n" + "Fullname: "
                    + "" + surname + " " + firstname + "\n with Staff Id: " + staff_id + "\n Time: " + time);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Sent me
    //
    public void stfRequestToReturnBook() {
        try {
            String sql = "select * from stfRequestToReturnBook ORDER BY Db_id DESC ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Return Book
    public void update(){
        try{
            String update="UPDATE Book SET Rack_no=? , Availability=? where Book_id=? ";
            pst=conn.prepareStatement(update);
            pst.setString(1, rack_no);
            pst.setString(2, "yes");
            pst.setString(3, book_id2);
            pst.executeUpdate();
            pst.close();
            } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void returnBook() {
        //Username='"+username+"'"
        try {

            String insert = "insert into stfReturnedBook(Staff_id,Surname,Firstname,Phone_no,Address,Book_id,";
                    insert+="Book_name,Edition,Category,Issued_date,Returned_date)";
                    insert+="values(?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(insert);
            pst.setString(1, staff_id);
            pst.setString(2, surname);
            pst.setString(3, firstname);
            pst.setString(4, phone);
            pst.setString(5, address);
            pst.setString(6, book_id2);
            pst.setString(7, book_name2);
            pst.setString(8, edition2);
            pst.setString(9, cat2);
            pst.setString(10, issued_date);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            String time = t.toString();
            pst.setString(11, time);
            pst.executeUpdate();
            pst.close();
            update();
            JOptionPane.showMessageDialog(null, "Book has been successfully Returned from\n" + "Fullname: " + surname + " "
                    + firstname + "\n with Staff Id: " + staff_id + "\n Time: " + time);
             
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    //Delete

    public void Delete(String table_name) {
        String sql = "delete from '"+table_name+"' where Staff_id=? AND Book_id=?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, staff_id);
            pst.setString(2, book_id2);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void updateToYes(){
        String sql="UPDATE Book SET Availability=? WHERE Book_id=? ";
                    try {
                        pst=conn.prepareStatement(sql);
                        pst.setString(1, "yes");
                        pst.setString(2, book_id2);
                        pst.executeUpdate();
                        pst.close();
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        borrow_table = new javax.swing.JTable();
        back = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Staffs Queing to Borrow Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Tahoma", 0, 22), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1300, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        borrow_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Db Id", "Book_ID", "Book Name", "Edition", "Rack No", "Category", "Staff Id", "Surname", "Firstname", "Phone No", "Address", "Request Date"
            }
        ));
        borrow_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrow_tableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(borrow_table);
        if (borrow_table.getColumnModel().getColumnCount() > 0) {
            borrow_table.getColumnModel().getColumn(0).setPreferredWidth(10);
            borrow_table.getColumnModel().getColumn(1).setPreferredWidth(70);
            borrow_table.getColumnModel().getColumn(3).setPreferredWidth(15);
            borrow_table.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jPanel1.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1260, 500));

        back.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        back.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editedback.jpg")); // NOI18N
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel1.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 540, -1, -1));

        jTabbedPane1.addTab("Staffs Queing to Borrow Details", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Staffs Queing to Return Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Tahoma", 0, 22), new java.awt.Color(0, 153, 153))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(1300, 700));
        jPanel2.setMinimumSize(new java.awt.Dimension(1251, 639));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Db_id", "Staff Id", "Surname", "Firstname", "Phone No", "Address", "Book Id", "Book Name", "Edition", "Category", "Issue Date", "To Return Date", "Request Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable2.getColumnModel().getColumn(8).setPreferredWidth(15);
        }

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editedback.jpg")); // NOI18N
        jButton8.setText("Back");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1261, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(1155, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addGap(2, 2, 2))
        );

        jTabbedPane1.addTab("Staffs Queing to Return Details", jPanel2);

        jMenuBar1.setBackground(new java.awt.Color(204, 204, 204));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editexit.jpg")); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editlogout.jpg")); // NOI18N
        jMenuItem2.setText("Logout");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem32.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem32.setIcon(new javax.swing.ImageIcon("C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\images\\editedsearch1.jpg")); // NOI18N
        jMenuItem32.setText("SearchBook");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem32);

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

        jMenu3.setText("Student");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Add new student");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Edit student record");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Issue book to student");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Return student borrowed book");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Students borrowed & returned details");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Students queing to borrow & return details");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Students sent messages details");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setText("Delete student");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem12.setText("View student profile");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem31.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem31.setText("Sign in & sign out");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem31);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Staff");

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem13.setText("Add new staff");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem14.setText("Edit staff record");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem15.setText("Issue book to staff");
        jMenu4.add(jMenuItem15);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem16.setText("Return staff borrowed book");
        jMenu4.add(jMenuItem16);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem17.setText("Staffs borrowed & returned details");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem17);

        jMenuItem18.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem18.setText("Staffs queing to borrow & return details");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem18);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem19.setText("Staffs sent messages details");
        jMenu4.add(jMenuItem19);

        jMenuItem20.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem20.setText("Delete staff");
        jMenu4.add(jMenuItem20);

        jMenuItem21.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem21.setText("View staff profile");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem21);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Customer");

        jMenuItem22.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem22.setText("Add new customer");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem22);

        jMenuItem23.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem23.setText("Edit customer record");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem23);

        jMenuItem24.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem24.setText("Issue book to customer");
        jMenu5.add(jMenuItem24);

        jMenuItem25.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem25.setText("Return customer borrowed book");
        jMenu5.add(jMenuItem25);

        jMenuItem26.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem26.setText("Customers borrowed & returned details");
        jMenu5.add(jMenuItem26);

        jMenuItem27.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem27.setText("Customers queing to borrow & return details");
        jMenu5.add(jMenuItem27);

        jMenuItem28.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem28.setText("Customers sent messages details");
        jMenu5.add(jMenuItem28);

        jMenuItem29.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem29.setText("Delete customer");
        jMenu5.add(jMenuItem29);

        jMenuItem30.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem30.setText("View customer profile");
        jMenu5.add(jMenuItem30);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1296, 691));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void borrow_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrow_tableMouseClicked
        // TODO add your handling code here:
        int i = borrow_table.getSelectedRow();
        TableModel model = borrow_table.getModel();
        try{
            book_id2 = model.getValueAt(i, 1).toString();
            book_name2 = model.getValueAt(i, 2).toString();
            edition2 = model.getValueAt(i, 3).toString();
            rack2 = model.getValueAt(i, 4).toString();
            cat2 = model.getValueAt(i, 5).toString();
            staff_id = model.getValueAt(i, 6).toString();
            surname = model.getValueAt(i, 7).toString();
            firstname = model.getValueAt(i, 8).toString();
            phone = model.getValueAt(i, 9).toString();
            address = model.getValueAt(i, 10).toString();
            request_date = model.getValueAt(i, 11).toString();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        String acceptance_message = "Dear " + surname + " " + firstname + " You can now come to Library for " + book_name2 + " you requested to borrow";
        String rejected_message = "Dear " + surname + " " + firstname + " Your Requested to borrow " + book_name2 + " "
                + "was rejected. You can contact Library for more details";

        int response = JOptionPane.showConfirmDialog(this, "Do you want to continue ? \nYES to send message either to come for "
                + "pick up or to\n Reject their request \n "
            + "NO to Issue Book without message \n CANCEL to go back without any action ", 
                "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            int action=JOptionPane.showConfirmDialog(this, "Do you want to continue? \n YES to accept to come for pick up\n "
                    + "NO to reject the request \n CANCEL to go back without any action", "Confirm",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (action == JOptionPane.YES_OPTION) {
                Sms.sendSms(phone, acceptance_message);
                insertToBorrowReceivedMessage();
                Delete("stfRequestToBorrowBook");
                stfRequestToBorrowBook();

            } else if (action == JOptionPane.NO_OPTION) {
                int option=JOptionPane.showConfirmDialog(this, "Do you want to continue? \n YES to reject request with rejection message\n "
                    + "NO to reject the request without message\n CANCEL to go back without any action", "Confirm",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    Sms.sendSms(phone, rejected_message);
                    //
                    updateToYes();
                    Delete("stfRequestToBorrowBook");
                    stfRequestToBorrowBook();
                }
                else if(option==JOptionPane.NO_OPTION){
                   //
                   updateToYes();
                    Delete("stfRequestToBorrowBook");
                    stfRequestToBorrowBook();
                }

            }

            // JOptionPane.showMessageDialog(null, 5+6);
        } else if (response == JOptionPane.NO_OPTION) {
            int action = JOptionPane.showConfirmDialog(this, "Do you want to Issue Book out without sending message to "
                + "\n" + surname + " " + firstname+" ? \n YES to Issue Book \n NO to cancel", 
                    "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (action == JOptionPane.YES_OPTION) {
                String return_date=JOptionPane.showInputDialog("Enter Expected date to return book");
                to_return_date=Integer.parseInt(return_date);
                issueBook();
                Delete("stfRequestToBorrowBook");
                stfRequestToBorrowBook();

            }
        }
        //        stdRequestToBorrowBook();
        //        toBorrowReceivedMessage();
    }//GEN-LAST:event_borrow_tableMouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int i = jTable2.getSelectedRow();
        TableModel model = jTable2.getModel();
        try{
            staff_id = model.getValueAt(i, 1).toString();
            surname = model.getValueAt(i, 2).toString();
            firstname = model.getValueAt(i, 3).toString();
            phone = model.getValueAt(i, 4).toString();
            address = model.getValueAt(i, 5).toString();
            book_id2 = model.getValueAt(i, 6).toString();
            book_name2 = model.getValueAt(i, 7).toString();
            edition2 = model.getValueAt(i, 8).toString();
            cat2 = model.getValueAt(i, 9).toString();
            issued_date = model.getValueAt(i, 10).toString();
            String date=model.getValueAt(i, 11).toString();
            to_return_date =Integer.parseInt(date);
            request_date = model.getValueAt(i, 12).toString();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        

        String text_message = "Dear " + surname + " " + firstname + " You can now come to Library for " + book_name2 + " you requested to return";

        int response = JOptionPane.showConfirmDialog(this, "Do you want to continue? \nYES to send message\n "
            + "NO to collect Returned Book without message\n CANCEL to go back without any action ",
            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            Sms.sendSms(phone, text_message);
            insertToReturnReceivedMessage();
            Delete("stfRequestToReturnBook");
            stfRequestToReturnBook();

            // JOptionPane.showMessageDialog(null, 5+6);
        } else if (response == JOptionPane.NO_OPTION) {
            int action = JOptionPane.showConfirmDialog(this, "Do you want to collect Returned Book without sending message "
                    + "to \n" + surname + " " + firstname+" ?\n"
                + "YES to collect Returned Book \nNo to CANCEL", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (action == JOptionPane.YES_OPTION) {
                rack_no=JOptionPane.showInputDialog("Enter Rack no to place the returned book");
                returnBook();
                Delete("stfRequestToReturnBook");
                stfRequestToReturnBook();
            }
        }
        //        stdRequestToReturnBook();
        //        toReturnReceivedMessage();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Home ob = new Home();
        ob.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Home ob = new Home();
        ob.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

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

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        search ob = new search();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        About ob=new About();
        ob.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        AddStudent add = new AddStudent();
        add.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        EditStudentRecord edit= new EditStudentRecord();
        edit.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        IssueBookToStudent issue= new IssueBookToStudent();
        issue.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        StudentBorrowedAndReturnedDetails br = new StudentBorrowedAndReturnedDetails();
        br.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        StudentBorrowedAndReturnedDetails br = new StudentBorrowedAndReturnedDetails();
        br.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        StudentQueingToBorrowAndReturnBook qbr = new StudentQueingToBorrowAndReturnBook();
        qbr.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        StudentQueingToBorrowAndReturnBookSentMessage qbr = new StudentQueingToBorrowAndReturnBookSentMessage();
        qbr.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        DeleteStudent del= new DeleteStudent();
        del.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ViewStudentRecord view= new ViewStudentRecord();
        view.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        StudentSignInOut stdin= new StudentSignInOut();
        stdin.setVisible(true);
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        AddStaff add = new AddStaff();
        add.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        //
        setVisible(false);
        EditStaffRecord edit= new EditStaffRecord();
        edit.setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        StaffBorrowedAndReturnedDetails br = new StaffBorrowedAndReturnedDetails();
        br.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        StaffQueingToBorrowAndReturnBook stf = new StaffQueingToBorrowAndReturnBook();
        stf.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        ViewStaffRecord view= new ViewStaffRecord();
        view.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        AddCustomer add = new AddCustomer();
        add.setVisible(true);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem23ActionPerformed

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
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentQueingToBorrowAndReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentQueingToBorrowAndReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentQueingToBorrowAndReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentQueingToBorrowAndReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentQueingToBorrowAndReturnBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JTable borrow_table;
    private javax.swing.JButton jButton8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
