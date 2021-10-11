package com.example.interview.oom;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-10 15:08:51
 */
public class StackOverFlowErrorDemo {
    public static void main(String[] args) {
        StackOverFlowError();
    }

    private static void StackOverFlowError() {
        StackOverFlowError();
    }
}
