package com.ni.demos;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @ClassName ExceptionParse
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/9/11 11:31
 * @Version 1.0
 **/
public class ExceptionParse {
    public static void main(String[] args) {
        try {
            System.out.println("throw Exception");
            try {
                throw new Exception("Exception A");
            }catch (Exception ce){
                throw new Exception(ce);
            }
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }
}