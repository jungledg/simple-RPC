package service;

/**
 * @Time : 2018/5/13
 * @Author : Dallon Gao
 * @Project : RPC
 **/
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a-b;
    }
}
