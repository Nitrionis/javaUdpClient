package com.company;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try(Socket socket = new Socket("localhost", 8080);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {
            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

            // проверяем живой ли канал и работаем если живой
            while(!socket.isOutputShutdown()){

                // ждём консоли клиента на предмет появления в ней данных
                if(br.ready()){

                    // данные появились - работаем
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

                    // пишем данные с консоли в канал сокета для сервера
                    //todo oos.writeUTF(clientCommand);
                    String msg = "test string\n";
                    byte[] buf = msg.getBytes();
                    //byte[] buf = new byte[1024];
                    oos.write(buf);
                    //oos.writeUTF(msg);
                    oos.flush();
                    //todo System.out.println("Clien sent message " + clientCommand + " to server.");
	                System.out.println("Clien sent message test string to server.");
                    Thread.sleep(1000);
                    // ждём чтобы сервер успел прочесть сообщение из сокета и ответить

                    // проверяем условие выхода из соединения
                    if(clientCommand.equalsIgnoreCase("quit")){

                        // если условие выхода достигнуто разъединяемся
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);

                        // смотрим что нам ответил сервер на последок перед закрытием ресурсов
                        if(ois.read() > -1)     {
                            System.out.println("reading...");
                            //String in = ois.readUTF();
	                        ois.read(buf, 0, 1024);
                            //System.out.println(in);
	                        System.out.println(buf.toString());
                        }

                        // после предварительных приготовлений выходим из цикла записи чтения
                        break;
                    }

                    // если условие разъединения не достигнуто продолжаем работу
                    System.out.println("Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);

                    // проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть ответить)
                    if(ois.read() > -1) {
                        // если успел забираем ответ из канала сервера в сокете и сохраняем её в ois переменную,  печатаем на свою клиентскую консоль
                        System.out.println("reading...");
                        //String in = ois.readUTF();
                        //System.out.println(in);
                        byte[] b = new byte[1024];
                        ois.read(b, 0, 4);
                        System.out.println(new String(b));
                    }
                }
            }
            // на выходе из цикла общения закрываем свои ресурсы
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
