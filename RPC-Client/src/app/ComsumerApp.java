package app;

import service.Calculator;
import service.CalculatorRemoteImpl;

/**
 * @Time : 2018/5/12
 * @Author : Dallon Gao
 * @Project : RPC
 **/
public class ComsumerApp {

    public static void main(String[] args){
        Calculator c = new CalculatorRemoteImpl();
        c.add(3,2);
    }

}
