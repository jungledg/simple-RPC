package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.CalculateRpcRequest;
import service.Calculator;
import service.CalculatorImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Time : 2018/5/13
 * @Author : Dallon Gao
 * @Project : RPC
 **/
public class ProviderApp {


    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
        new ProviderApp().run();
    }


    private void run() throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    Object input = inputStream.readObject();

//                    log.info("request is {}", input);
                    System.out.println("input is :" + input);


                    //调用服务
                    int result = 0;
                    if (input instanceof CalculateRpcRequest) {
                        CalculateRpcRequest request = (CalculateRpcRequest) input;
                        if ("add".equals(request.getMethod()))
                            result = calculator.add(request.getA(), request.getB());
                    } else {
                        throw new UnsupportedOperationException();
                    }

                    //返回结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);

                } catch (Exception e) {
//                    log.error("fail", e);
                    System.out.println("faile :" + e);
                }finally {
                    socket.close();
                }
            }
        }finally {
            listener.close();
        }


    }

}
