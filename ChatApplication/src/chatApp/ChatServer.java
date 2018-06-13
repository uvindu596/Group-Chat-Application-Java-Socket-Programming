package chatApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {

    

    static ArrayList<String> userNames = new ArrayList<String>();

    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

   

    public static void main(String[] args) throws Exception{

          // TODO Auto-generated method stub

         System.out.println("Waiting for clients..."); 

         ServerSocket ss = new ServerSocket(9806);

         while (true)

         {

           Socket soc = ss.accept();

             System.out.println("Connection established");

           ConversationHandler handler = new ConversationHandler(soc);

           handler.start();

         }

          

    }

}

class ConversationHandler extends Thread

{

  Socket socket;

  BufferedReader in;

  PrintWriter out;

  String name;

  PrintWriter pw;

  static FileWriter fw;

  static BufferedWriter bw;



 public ConversationHandler(Socket socket) throws IOException {

      this.socket = socket;

      fw = new FileWriter("C:\\Users\\DELL\\Desktop\\ChatServer.txt",true);

      bw = new BufferedWriter(fw);

      pw = new PrintWriter(bw,true);

  }

 public void run()

 {

      try

      {

          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          out = new PrintWriter(socket.getOutputStream(), true);

         

          int count = 0;

          while (true)

          {

             if(count > 0)

             {

                  out.println("NAMEALREADYEXISTS");

             }

             else

             {

                  out.println("NAMEREQUIRED");

             }

          

             name = in.readLine();

          

             if (name == null)

             {

                 return;

             }

      

             if (!ChatServer.userNames.contains(name))

             {

                ChatServer.userNames.add(name);

                break;

             }

           count++;

         }

         

          out.println("NAMEACCEPTED"+name);

          ChatServer.printWriters.add(out);

         

          while (true)

          {

              String message = in.readLine();

             

              if (message == null)

              {

                  return;

              }

             

              pw.println(name + ": " + message);

              for (PrintWriter writer : ChatServer.printWriters) {

                  writer.println(name + ": " + message);

              }

          }

         

      }

      catch (Exception e)

      {

          System.out.println(e);

      }

 }

}