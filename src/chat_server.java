/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mukul
 */
public class chat_server extends javax.swing.JFrame {

    static ServerSocket serverSocket;
    static Socket clientSocket;
    static DataInputStream in;
    static DataOutputStream out;
    static OutputStream OS;
    private static BufferedReader bufferedReader;
    private static String message;
    static DataInputStream dis;
    static int oldx,oldy ;
    // Variables declaration - do not modify
    static javax.swing.JLabel jLabel1;
    static javax.swing.JScrollPane jScrollPane1;
    static javax.swing.JTextArea msg_area = new javax.swing.JTextArea();
    ;
    static javax.swing.JButton msg_send;
    static javax.swing.JTextField msg_text;
    static boolean flag = false;
    // End of variables declaration
    static boolean isclicked=false;

    /**
     * Creates new form chat_server
     */
    public chat_server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area.setEditable(false);
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_send.setText("send");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(msg_send)))
                                                .addGap(0, 18, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(msg_text)
                                        .addComponent(msg_send, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        try{
                while(clientSocket.isConnected()) {
                    BufferedImage screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    byte[] imageToBytes = toimagearray(screenShot);
                    byte[] imageToBytesWithKey = addkeyarray(imageToBytes);
                    int number = imageToBytes.length;
                    out.write(imageToBytesWithKey);
                    //System.out.println("Sent to Client image with bytes array : " + number);
                    //TimeUnit.SECONDS.sleep(1);
                    out.flush();
                    //OS.flush();
                }

        }
        catch(Exception e)
        {
            //handle the exception here
        }


    }

    private byte[] addkeyarray(byte[] imageToBytes) {
        byte[] key = "Bayou".getBytes();//66 97 121 111 117
        int numberOfImageBytes = imageToBytes.length;
        int numberOfKeyBytes = key.length;
        byte[] result = new byte[numberOfImageBytes + numberOfKeyBytes];
        System.arraycopy(imageToBytes, 0, result, 0, numberOfImageBytes);
        System.arraycopy(key, 0, result, numberOfImageBytes, numberOfKeyBytes);
        int TotalBytes = result.length;
        return result;
    }

    public static byte[] toByteArray(BufferedImage bi, String format)throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }

    public static byte[] toimagearray(BufferedImage screenShot){
        int width = 900;
        int height = 700;
        byte[] imageToBytes = new byte[0];
        try{
            BufferedImage newSizedImage = Thumbnails.of(screenShot)
                    .outputFormat("jpg")
                    .size(width, height)
                    .keepAspectRatio(true)
                    .asBufferedImage();
            imageToBytes = toByteArray(newSizedImage, "jpg");
//            int number = imageToBytes.length;
//            System.out.println(number);
//            while(number>50000){
//                newSizedImage = Thumbnails.of(screenShot)
//                        .outputFormat("jpg")
//                        .size(width-1, height-1)
//                        .keepAspectRatio(true)
//                        .asBufferedImage();
//                imageToBytes = toByteArray(newSizedImage, "jpg");
//                number = bytes.length;
//            }
        }
        catch(Exception e)
        {
            //handle the exception here
        }
        return imageToBytes;

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chat_server().setVisible(true);
            }
        });
        String msgin = "";
        try
        {
            serverSocket = new ServerSocket(4444);
            String event = "Server started";
            msg_area.append("\n"+event);
            event = "Waiting for a client ...";
            msg_area.append("\n"+event);
            clientSocket = serverSocket.accept();
            event = "Client accepted";
            msg_area.append("\n"+event);


            // takes input from the client socket
            in = new DataInputStream(clientSocket.getInputStream());
            //writes on client socket
            out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(in);


            // Receiving data from client
            while(clientSocket.isConnected()){
                String message = dis.readUTF();
                //System.out.println("Recieved from client : "+message);
                if(!message.equals(null)&&(!message.equals("X0Y0"))){
                click(message);
                }

            }
            //echoing back to client



        }
        catch(IOException i)
        {
            System.out.println(i);
        }



    }

    private static void click(String message) {
        try {
            Robot robot = new Robot();
//            if(message.equals("ACTION_POINTER_DOWN")){
//                        System.out.println("d5lt first");
//                        robot.delay(500);
//                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK); // press right click
//                        robot.delay(500);
//                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); //release right click
//                        robot.delay(1000);
//            }

            String[] takeOrder = message.split("%", 2);//[0@1%up] -> [0][1%up]
            if (takeOrder[0].equals("keyboard")){
                String input = takeOrder[1];
                System.out.println(input);
            } else {
                String[] arrOfStr = takeOrder[0].split("@", 2);
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                double width = size.getWidth();
                double height = size.getHeight();
                //System.out.println("X : "+ (Double.parseDouble(arrOfStr[0]) * width)+"Y "+(Double.parseDouble(arrOfStr[1]) * height) );

                int x = (int) (Double.parseDouble(arrOfStr[0]) * width);
                int y = (int) (Double.parseDouble(arrOfStr[1]) * height);
                String order = takeOrder[1];


                switch (order) {
                    case "ACTION_DOWN":
                        if (!isclicked) {
                            robot.delay(50);
                            //System.out.println("down");
                            if(Math.abs(x - oldx) > 20 && Math.abs(y - oldy) > 20) {
                                robot.mouseMove(x, y); // move mouse point to specific location
                            }
                            robot.delay(10);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            isclicked = true;
                        }
                        break;


                    case "ACTION_UP":
//                        robot.mouseMove(x,y); // move mouse point to specific location
                        if (isclicked) {
                            robot.delay(30);
                            //System.out.println("up");
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            isclicked = false;
                            robot.delay(50);
                        }
                        break;

                    case "ACTION_MOVE":
                        if (isclicked) {
                            robot.delay(30);
                            //System.out.println("move");
                            //robot.delay(50);
                            robot.mouseMove(x, y); // move mouse point to specific location
                        }
                        break;
                    case "ACTION_POINTER_DOWN":
                        if (isclicked) {
                            robot.delay(50);
                            //System.out.println("PdOWN");
                            robot.mouseMove(x, y); // move mouse point to specific location
                            robot.delay(20);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK); // press right click
                            //System.out.println("PressdOWN");
                            robot.delay(20);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); //release right click
                            //System.out.println("releasedOWN");

                            robot.delay(50);

                        }
                }
                oldx = x;
                oldy = y;
                //System.out.println("");


            }
            } catch(AWTException e){
                e.printStackTrace();
            }

    }


}
