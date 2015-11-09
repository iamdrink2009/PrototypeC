import java.io.*;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 1. 本程式必須與 UdpServer.java 程式搭配執行，先執行 UdpServer 再執行本程式。
// 2. 本程式必須有一個參數，指定伺服器的 IP。
// 用法範例： java UdpClient 127.0.0.1
 
public class UdpClient extends Thread {
    int port;            // port : 連接埠
    InetAddress server; // InetAddress 是 IP, 此處的 server 指的是伺服器 IP
    String msg;// 欲傳送的訊息，每個 UdpClient 只能傳送一個訊息。
    static String str1,str2;
    static public int position = 0,counter = 10;
 
    public static void main(String args[]) throws Exception {  	   	
    	Console console=System.console();//宣告comsole物件
    	
    	System.out.println("Please input ip address 1:");
    	str1 = console.readLine();
    	System.out.println("Please input ip address 2:");
    	str2 = console.readLine();//輸入ip
    	
    	while(true){   		    		    		
    		try{
    			counter--;
    			position = MoveObject(position,counter);
    			if (counter==0)
    				counter = 10;

    			UdpClient client1 = new UdpClient(str1, 5555, "Now coordinate:("+ position + "," + position + ")");
    			client1.SendtoServer();
    			UdpClient client2 = new UdpClient(str2, 5555, "Now coordinate:("+ position + "," + position + ")");
    			client2.SendtoServer();
    			Thread.sleep(200);
    			
    		}catch(InterruptedException e){}
    	}
    }
    
    public static int MoveObject(int position,int counter){
    	//System.out.println( "position = " + position + "counter = " + counter);
    	if (counter == 0){
    		position = position + 1;
            if(position==100)
        	    position = 0;
    	}   	
    	return position;
    }
 
    public UdpClient(String pServer, int pPort, String pMsg) throws Exception {
        port = pPort;                             // 設定連接埠
        server = InetAddress.getByName(pServer); // 將伺服器網址轉換為 IP。
        msg = pMsg;                                 // 設定傳送訊息。
    }
 
    public void SendtoServer() {
      try {
        byte buffer[] = msg.getBytes();                 // 將訊息字串 msg 轉換為位元串。
        // 封裝該位元串成為封包 DatagramPacket，同時指定傳送對象。
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, server, port); 
        DatagramSocket socket = new DatagramSocket();    // 建立傳送的 UDP Socket。
        socket.send(packet);                             // 傳送
        socket.close();                                 // 關閉 UDP socket.
      } catch (Exception e) { e.printStackTrace(); }    // 若有錯誤產生，列印函數呼叫堆疊。
    }
    
}
