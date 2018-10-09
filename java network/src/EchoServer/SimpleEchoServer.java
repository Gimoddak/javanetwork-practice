package EchoServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleEchoServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	try {
		@SuppressWarnings("resource")
		ServerSocket server=new ServerSocket(10001);
		System.out.println("waiting connecting");
		Socket sock=server.accept();
		InetAddress inetaddr=sock.getInetAddress();
		System.out.println(inetaddr.getHostAddress()+" 로부터 접속했습니다.");
		OutputStream out=sock.getOutputStream();
		InputStream in=sock.getInputStream();
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(out));
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String line=null;
		while((line=br.readLine())!=null) {
			System.out.println("클라이언트로부터 전송받은 문자열: "+line);
			pw.println(line);
			pw.flush();
		}
		pw.close();
		br.close();
		sock.close();
	}catch(Exception e)
	{
		System.out.println(e);
	}
	}
}
