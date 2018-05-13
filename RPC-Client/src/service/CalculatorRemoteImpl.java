package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.CalculateRpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2018/5/12
 * @Author : Dallon Gao
 * @Project : RPC
 **/
public class CalculatorRemoteImpl implements Calculator {

    public static final int PORT = 9090;


    @Override
    public int add(int a, int b) {

        //获取调用列表
        List<String> addressList = lookupProviders("service.Calculator.add");

        //从列表中选取一个调用
        String address = chooseTarget(addressList);

        try {
            Socket socket = new Socket(address,PORT);

            //将请求序列化
            CalculateRpcRequest request = generateRequest(a,b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            //请求发给服务方
            objectOutputStream.writeObject(request);

            //收到回应，反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object o = objectInputStream.readObject();

            System.out.println("response :" + o);


            if(o instanceof Integer){
                return (int) o;
            }else
                throw new InternalError();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return 0;
    }

    private CalculateRpcRequest generateRequest(int a, int b) {
        CalculateRpcRequest request = new CalculateRpcRequest();
        request.setA(a);
        request.setB(b);
        request.setMethod("add");
        return  request;
    }

    private String chooseTarget(List<String> addressList) {
        if (null == addressList || addressList.size() == 0)
            throw new IllegalArgumentException();
        return addressList.get(0);
    }

    private static List<String> lookupProviders(String s) {
        List<String> name = new ArrayList<>();
        name.add("127.0.0.1");
        return name;
    }
}
