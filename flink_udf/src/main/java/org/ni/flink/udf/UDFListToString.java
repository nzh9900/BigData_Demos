package org.ni.flink.udf;
/**
 * @Copyright: Copyright (c) 2015
 * @Company: ND Co., Ltd.
 * @author: zhangjianping
 * @Create Time: 2021/11/17
 * @Description:
 */
import org.apache.flink.table.functions.ScalarFunction;


public class UDFListToString extends ScalarFunction  {

    private final String  separator = ",";
    public String eval(String[] list ){

        try{

            if(null==list){
                return null;
            }
            StringBuffer stringBuffer =new StringBuffer();
            for(int i=0;i< list.length;i++){
                stringBuffer.append(String.format("%s",list[i].toString()));

                if(i!=list.length-1){
                    stringBuffer.append(separator);
                }

            }
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(String.format("UDFListToString %s got some err:%s",list,e.getMessage()));
            return null;
        }


    }

}
