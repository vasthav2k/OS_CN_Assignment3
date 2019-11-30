import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
public class Chatserver extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
    static ServerSocket s;
    static Socket sock;
    static DataInputStream din;
    static DataOutputStream dout;
    
    public Chatserver() {
        new NewJPanel();
        initComponents();
    }                     
    private void initComponents() {
        this.setTitle("Server");

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        send = new javax.swing.JButton("send");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        area.setColumns(20);
        area.setRows(5);
        jScrollPane2.setViewportView(area);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(send)
                    )
                .addContainerGap(24, Short.MAX_VALUE))
        );
        send.setText("send");
        pack();
        setArea();
        
    }                        
    private static void setArea(){
        try{
            area.setText("");
            String q="select * from chatServer";
            Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","harish1","harish1");
                    Statement S=con.createStatement();
            ResultSet rs=S.executeQuery(q);
            while(rs.next()){
                String s1;
                s1="\n"+rs.getString(1)+rs.getString(2);
                area.setText(area.getText().trim()+s1);
            }
        }catch(Exception e){
            
        }
    }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {     
    }                                           

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {      
        try{
            String msg = "";
            if(!msg.equals(jTextField1.getText().trim())){
                msg = jTextField1.getText().trim();
                dout.writeUTF(msg);
                jTextField1.setText("");
                String username="harish1";
                String password="harish1";
                String url="jdbc:mysql://localhost:3306/assignment";
                String u="jdbc:oracle:thin:@localhost:1521:xe";
                
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","harish1","harish1");
                Statement S=con.createStatement();
                String q="insert into chatServer(type,msg) values('Server: ','"+msg+"')";
                S.executeUpdate(q);
                setArea();
            }
        }catch(Exception e){
            System.out.println("error in server");
        }
    }                                    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chatserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chatserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chatserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chatserver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chatserver().setVisible(true);
            }
        });
        String msg="";
        try{
            s = new ServerSocket(1201);
            sock = s.accept();
            din = new DataInputStream(sock.getInputStream());
            dout =new DataOutputStream(sock.getOutputStream());
            while(!msg.equals("exit")){
                msg = din.readUTF();
                setArea();
            }
        }catch(Exception e)
        {
        	
        }
    }

    // Variables declaration - do not modify                     
    private static javax.swing.JTextArea area;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton send;
    // End of variables declaration                   
}


