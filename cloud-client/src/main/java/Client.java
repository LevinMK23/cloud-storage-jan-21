import java.io.*;
import java.net.Socket;
import java.net.URL;

public class Client {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    public Client() {
        open("HomeWork.md");
     }

    private void open(String src) {
        try {
            socket = new Socket("127.0.0.1", 8082);
            System.out.println(socket);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                        try {
                            //в дальнейшем вынести в отдельный класс fileHandler, доступный и серверу, и клиенту

                            InputStream is = new FileInputStream(getClass().getResource(src).getPath());
                            int read;
                            byte[] buffer = new byte[1024];
                            while ((read = is.read(buffer)) != -1) {
                                out.write(buffer, 0, read);
                            }
                            is.close();
                            System.out.println("Client: File was transfered");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            closeConnection();
                        }
                }
            })
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client successfully disconnected");
    }


}
