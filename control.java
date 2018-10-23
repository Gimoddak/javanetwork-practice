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
   // 이부분은 디자인 입히고 이미지 입혀진 버튼에 이벤트 전달하기 위한 부분임. 중요x
   private class MouseAdapter implements MouseListener, MouseMotionListener{
      // 종료버튼위에 마우스를 올렸을때의 사건을 구현.
      public void mouseEntered(MouseEvent e){
         if(e.getSource() == view.getExit_bt()) {
            // 종료버튼의 아이콘을 exitentered_img으로 설정.
            view.getExit_bt().setIcon(view.getExitentered_img());
            // 종료버튼위에 커서가 있을시 핸드커서로 변경.
            view.getExit_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         } else if(e.getSource() == view.getLogin_bt()) {
            // 로그인버튼의 아이콘을 loginEntered_img으로 설정.
            view.getLogin_bt().setIcon(view.getLoginEntered_img());
            // 로그인버튼위에 커서가 있을시 핸드커서로 변경.
            view.getLogin_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         } else if(e.getSource() == view.getJoin_bt()){
            // 가입버튼의 아이콘을 joinEntered_img으로 설정.
            view.getJoin_bt().setIcon(view.getJoinEntered_img());
            // 가입버튼위에 커서가 있을시 핸드커서로 변경.
            view.getJoin_bt().setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
      }
      
      /// 종료버튼에서 마우스를 내렸을때의 사건을 구현.
      public void mouseExited(MouseEvent e){
         if(e.getSource() == view.getExit_bt()) {
            // 종료버튼에서 마우스를 내렸을때의 아이콘을 exitbasic_img로 설정.
            view.getExit_bt().setIcon(view.getExitbasic_img());
            // 종료버튼에서 마우스를 내렸을때 커서를 기본커서로 설정.
            view.getExit_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         } else if(e.getSource() == view.getLogin_bt()) {
            // 로그인버튼에서 마우스를 내렸을때의 아이콘을 login_img로 설정.
            view.getLogin_bt().setIcon(view.getLogin_img());
            // 로그인버튼에서 마우스를 내렸을때 커서를 기본커서로 설정.
            view.getLogin_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         } else if(e.getSource() == view.getJoin_bt()){
            // 가입버튼에서 마우스를 내렸을때의 아이콘을 join_img로 설정.
            view.getJoin_bt().setIcon(view.getJoin_img());
            // 가입버튼에서 마우스를 내렸을때 커서를 기본커서로 설정.
            view.getJoin_bt().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      }
      
      // 종료버튼을 클릭했을때의 사건을 구현.
      public void mousePressed(MouseEvent e){
         if(e.getSource() == view.getMenubar_l()) {
            // 이벤트가 발생했을때의 그 좌표를 얻어옴.
            view.setMouseX(e.getX());
            view.setMouseY(e.getY());
         } else if(e.getSource() == view.getExit_bt()){

            // 예외 처리.
            try {
               Thread.sleep(1000);   // 버튼을 누르자마자 꺼지면 효과음이 들리지 않기 때문에 1초 정도의 기간을 줌.
            }catch(InterruptedException ex)
            {
               // 에러가 발생한 메소드의 호출 기록을 출력함.
               ex.printStackTrace();
            }
            // 프로그램을 종료시킴.
            System.exit(0);
         } else {
            // 예외 처리.
            try {
               Thread.sleep(1000);   // 버튼을 누르자마자 꺼지면 효과음이 들리지 않기 때문에 1초 정도의 기간을 줌.
            }catch(InterruptedException ex)
            {
               // 에러가 발생한 메소드의 호출 기록을 출력함.
               ex.printStackTrace();
            }
         }
      }
      
      public void mouseDragged(MouseEvent e){
         // 현재 스크린의 좌표를 얻어옴.
         int x = e.getXOnScreen();
         int y = e.getYOnScreen();
         
         // 프레임의 위치를 드래그한곳으로 이동시킴.
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
                  JOptionPane.showMessageDialog(null, "로그인 되었습니다");


                  // Application이 card Layout으로 되있다고 했지?
                  // Application 프레임 안에 순서대로 로그인, 대기실, 인게임이 들어있는데 changePanel이
                  // 바로 다음 순서 패널로 화면 전환시켜주는애임
                  app.changePanel();
                  app.setSize(800,600);
                  app.setTitle("대기실");
                  app.setSize(800,600);
               } else {
                  JOptionPane.showMessageDialog(null, "실패");
              
               }
          
         }
         catch(IOException e){
            e.printStackTrace();
         }
      }
   }
   
   // 이부분부터 보면 됨
   // 로그인 패널에서 버튼들 클릭하면 발생하는 이벤트 처리해주기 위한 내부클래스
   private class ButtonListener implements ActionListener {
      Socket toWaiting;
      // 버튼 누르면 그 버튼에 대한 event가 넘어온다.
      // 버튼이 눌렸을때 무엇을 해줘야할지 정해주는 메소드

      public void actionPerformed(ActionEvent event){
         // 로그인 버튼 선택시
         if(event.getSource() == view.getLogin_bt()) {
            
            ct1 = new ControlerThread(server,1);
               ct1.start();
               ct1=null;
              // ct1.run();
           
            
            
            
            //if(lg != null) {
//               if(lg.equals("true")) {
//                  JOptionPane.showMessageDialog(null, "로그인 되었습니다");
//
//
//                  // Application이 card Layout으로 되있다고 했지?
//                  // Application 프레임 안에 순서대로 로그인, 대기실, 인게임이 들어있는데 changePanel이
//                  // 바로 다음 순서 패널로 화면 전환시켜주는애임
//                  app.changePanel();
//                  app.setSize(800,600);
//                  app.setTitle("대기실");
//                  app.setSize(800,600);
//               } else {
//                  JOptionPane.showMessageDialog(null, "실패");
//               }
           // }
         // 회원가입 버튼 선택   
         } else {
            // 회원가입 창 생성
            rv = new RegisterView();
            // 회원가입 Controler 연결
            rc = new RegisterControler(rv);
            // 회원가입 창에서도 버튼은 있으니까 걔네 listener들을 연결시켜주는 메소드
            rc.buttonHandler();
         }
      }
   }
      
   
   // 생성자 메소드
   public LoginControler(Application app, LoginView v, RegisterView rv, RegisterControler rc, Socket s) throws IOException {
     
      this.app = app;
      view = v;
      this.rv = rv;
      this.rc = rc;
      // eventHandler가 buttonHandler랑 원래는 같은 메소드였는데 로그인창 디자인 입히면서
      // 그 이미지 클릭 이벤트 처리해주기 위해서 MouseAdapter 만들다 보니까 걔네도 연결해줘야되서
      // 이름을 eventHandler로 바꿨고, 생각해보니까 굳이 application 생성자메소드에서 얘네를 호출시켜야될
      // 이유가 없는거같아서 각자 생성자 메소드에서 호출해주도록 바f음. 나중에 대기실이랑 인게임도 buttonHandler를
      // 각자 생성자 메소드로 넣어주고 이름도 eventHandler로 바꿀 예정
      this.server = s;
      //ct1 = new ControlerThread(server,1);
     
      eventHandler();
   }
   
   
   public void eventHandler() {
      // 이벤트 넘겨주는 객체인 ButtonListener 만들고 로그인, 회원가입 버튼을 연결해줌
      listen = new ButtonListener();
      mouse = new MouseAdapter();
      view.getLogin_bt().addActionListener(listen);
      view.getJoin_bt().addActionListener(listen);
      
      // 얘네도 이미지에 따른 이벤트 처리라 알필요 x
      view.getExit_bt().addMouseListener(mouse);
      view.getLogin_bt().addMouseListener(mouse);
      view.getJoin_bt().addMouseListener(mouse);
      view.getMenubar_l().addMouseListener(mouse);
      view.getMenubar_l().addMouseMotionListener(mouse);
   }
   
   
}
