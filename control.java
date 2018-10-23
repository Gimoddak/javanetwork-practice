package Control;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import Starter.Application;
import View.LoginView;
import View.RegisterView;
public class LoginControler{
   private Application app;
   private LoginView view;
   private ButtonListener listen;
   private RegisterView rv;
   private RegisterControler rc;
   private MouseAdapter mouse;
   private Socket server;
   private DataOutputStream outputstream = null;
   private DataInputStream inputstream = null;
   private String lg = null;
   private int n = 0;
   private int i = 0;
   private ControlerThread ct1;
   // �̺κ��� ������ ������ �̹��� ������ ��ư�� �̺�Ʈ �����ϱ� ���� �κ���. �߿�x
   private class MouseAdapter implements MouseListener, MouseMotionListener{
      // �����ư���� ���콺�� �÷������� ����� ����.
      public void mouseEntered(MouseEvent e){
         if(e.getSource() == view.getExit_bt()) {
            // �����ư�� �������� exitentered_img���� ����.
            view.getExit_bt().setIcon(view.getExitentered_img());
            // �����ư���� Ŀ���� ������ �ڵ�Ŀ���� ����.
            view.getExit_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         } else if(e.getSource() == view.getLogin_bt()) {
            // �α��ι�ư�� �������� loginEntered_img���� ����.
            view.getLogin_bt().setIcon(view.getLoginEntered_img());
            // �α��ι�ư���� Ŀ���� ������ �ڵ�Ŀ���� ����.
            view.getLogin_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         } else if(e.getSource() == view.getJoin_bt()){
            // ���Թ�ư�� �������� joinEntered_img���� ����.
            view.getJoin_bt().setIcon(view.getJoinEntered_img());
            // ���Թ�ư���� Ŀ���� ������ �ڵ�Ŀ���� ����.
            view.getJoin_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
      }
      
      /// �����ư���� ���콺�� ���������� ����� ����.
      public void mouseExited(MouseEvent e){
         if(e.getSource() == view.getExit_bt()) {
            // �����ư���� ���콺�� ���������� �������� exitbasic_img�� ����.
            view.getExit_bt().setIcon(view.getExitbasic_img());
            // �����ư���� ���콺�� �������� Ŀ���� �⺻Ŀ���� ����.
            view.getExit_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         } else if(e.getSource() == view.getLogin_bt()) {
            // �α��ι�ư���� ���콺�� ���������� �������� login_img�� ����.
            view.getLogin_bt().setIcon(view.getLogin_img());
            // �α��ι�ư���� ���콺�� �������� Ŀ���� �⺻Ŀ���� ����.
            view.getLogin_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         } else if(e.getSource() == view.getJoin_bt()){
            // ���Թ�ư���� ���콺�� ���������� �������� join_img�� ����.
            view.getJoin_bt().setIcon(view.getJoin_img());
            // ���Թ�ư���� ���콺�� �������� Ŀ���� �⺻Ŀ���� ����.
            view.getJoin_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      }
      
      // �����ư�� Ŭ���������� ����� ����.
      public void mousePressed(MouseEvent e){
         if(e.getSource() == view.getMenubar_l()) {
            // �̺�Ʈ�� �߻��������� �� ��ǥ�� ����.
            view.setMouseX(e.getX());
            view.setMouseY(e.getY());
         } else if(e.getSource() == view.getExit_bt()){

            // ���� ó��.
            try {
               Thread.sleep(1000);   // ��ư�� �����ڸ��� ������ ȿ������ �鸮�� �ʱ� ������ 1�� ������ �Ⱓ�� ��.
            }catch(InterruptedException ex)
            {
               // ������ �߻��� �޼ҵ��� ȣ�� ����� �����.
               ex.printStackTrace();
            }
            // ���α׷��� �����Ŵ.
            System.exit(0);
         } else {
            // ���� ó��.
            try {
               Thread.sleep(1000);   // ��ư�� �����ڸ��� ������ ȿ������ �鸮�� �ʱ� ������ 1�� ������ �Ⱓ�� ��.
            }catch(InterruptedException ex)
            {
               // ������ �߻��� �޼ҵ��� ȣ�� ����� �����.
               ex.printStackTrace();
            }
         }
      }
      
      public void mouseDragged(MouseEvent e){
         // ���� ��ũ���� ��ǥ�� ����.
         int x = e.getXOnScreen();
         int y = e.getYOnScreen();
         
         // �������� ��ġ�� �巡���Ѱ����� �̵���Ŵ.
         app.setLocation(x - view.getMouseX(), y - view.getMouseY());
      }

      @Override
      public void mouseClicked(MouseEvent e) {
         // TODO Auto-generated method stub
         
      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub
         
      }

      @Override
      public void mouseMoved(MouseEvent e) {
         // TODO Auto-generated method stub
         
      }      
   }
   
   public void setLogin(String lg) {
      this.lg = lg;
   }
   
   public String getLogin() {
      return this.lg;
   }
   private class ControlerThread extends Thread {
      //   private DB db;
         public ControlerThread(Socket s, int num) {
            try {
               n = num;
               outputstream = new DataOutputStream(s.getOutputStream());
                inputstream = new DataInputStream(s.getInputStream());      
            }
            catch(IOException e) {
               e.printStackTrace();
            }
         }
         
      public void run() {
         try {
          
           outputstream.writeUTF(view.getID());
           outputstream.writeUTF(view.getPW());
           
          
             lg = inputstream.readUTF();
             if(lg.equals("true")) {
                  JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�");


                  // Application�� card Layout���� ���ִٰ� ����?
                  // Application ������ �ȿ� ������� �α���, ����, �ΰ����� ����ִµ� changePanel��
                  // �ٷ� ���� ���� �гη� ȭ�� ��ȯ�����ִ¾���
                  app.changePanel();
                  app.setSize(800,600);
                  app.setTitle("����");
                  app.setSize(800,600);
               } else {
                  JOptionPane.showMessageDialog(null, "����");
                  try {
                     Thread.sleep(1000);
                  }catch(InterruptedException e) {
                  }
                  ct1.interrupt();
               }
          
         }
         catch(IOException e){
            e.printStackTrace();
         }
      }
   }
   
   // �̺κк��� ���� ��
   // �α��� �гο��� ��ư�� Ŭ���ϸ� �߻��ϴ� �̺�Ʈ ó�����ֱ� ���� ����Ŭ����
   private class ButtonListener implements ActionListener {
      Socket toWaiting;
      // ��ư ������ �� ��ư�� ���� event�� �Ѿ�´�.
      // ��ư�� �������� ������ ��������� �����ִ� �޼ҵ�

      public void actionPerformed(ActionEvent event){
         // �α��� ��ư ���ý�
         if(event.getSource() == view.getLogin_bt()) {
            
            ct1 = new ControlerThread(server,1);
               ct1.start();
              
              // ct1.run();
           
            
            
            
            //if(lg != null) {
//               if(lg.equals("true")) {
//                  JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�");
//
//
//                  // Application�� card Layout���� ���ִٰ� ����?
//                  // Application ������ �ȿ� ������� �α���, ����, �ΰ����� ����ִµ� changePanel��
//                  // �ٷ� ���� ���� �гη� ȭ�� ��ȯ�����ִ¾���
//                  app.changePanel();
//                  app.setSize(800,600);
//                  app.setTitle("����");
//                  app.setSize(800,600);
//               } else {
//                  JOptionPane.showMessageDialog(null, "����");
//               }
           // }
         // ȸ������ ��ư ����   
         } else {
            // ȸ������ â ����
            rv = new RegisterView();
            // ȸ������ Controler ����
            rc = new RegisterControler(rv);
            // ȸ������ â������ ��ư�� �����ϱ� �³� listener���� ��������ִ� �޼ҵ�
            rc.buttonHandler();
         }
      }
   }
      
   
   // ������ �޼ҵ�
   public LoginControler(Application app, LoginView v, RegisterView rv, RegisterControler rc, Socket s) throws IOException {
     
      this.app = app;
      view = v;
      this.rv = rv;
      this.rc = rc;
      // eventHandler�� buttonHandler�� ������ ���� �޼ҵ忴�µ� �α���â ������ �����鼭
      // �� �̹��� Ŭ�� �̺�Ʈ ó�����ֱ� ���ؼ� MouseAdapter ����� ���ϱ� �³׵� ��������ߵǼ�
      // �̸��� eventHandler�� �ٲ��, �����غ��ϱ� ���� application �����ڸ޼ҵ忡�� ��׸� ȣ����Ѿߵ�
      // ������ ���°Ű��Ƽ� ���� ������ �޼ҵ忡�� ȣ�����ֵ��� �مf��. ���߿� �����̶� �ΰ��ӵ� buttonHandler��
      // ���� ������ �޼ҵ�� �־��ְ� �̸��� eventHandler�� �ٲ� ����
      this.server = s;
      //ct1 = new ControlerThread(server,1);
     
      eventHandler();
   }
   
   
   public void eventHandler() {
      // �̺�Ʈ �Ѱ��ִ� ��ü�� ButtonListener ����� �α���, ȸ������ ��ư�� ��������
      listen = new ButtonListener();
      mouse = new MouseAdapter();
      view.getLogin_bt().addActionListener(listen);
      view.getJoin_bt().addActionListener(listen);
      
      // ��׵� �̹����� ���� �̺�Ʈ ó���� ���ʿ� x
      view.getExit_bt().addMouseListener(mouse);
      view.getLogin_bt().addMouseListener(mouse);
      view.getJoin_bt().addMouseListener(mouse);
      view.getMenubar_l().addMouseListener(mouse);
      view.getMenubar_l().addMouseMotionListener(mouse);
   }
   
   
}