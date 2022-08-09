package org.ni;


import java.io.Serializable;
import java.sql.SQLException;

public class ReturnValTest {
    public static void main(String[] args) throws SQLException {
        ReturnValTest returnValTest = new ReturnValTest();
        Something a = returnValTest.test("c10");
        System.out.println(a.deserialize("2"));
        //Integer value = (Integer)a.deserialize(Integer.valueOf(10));
        //System.out.println(value);
    }

    private Something test(String flag) {
        switch (flag) {
            case "a":
                return val -> null;
            case "b":
                return val -> val;
            case "c":
                return val -> (Integer) val * 2;
            case "d":
                return val -> (String)val + "OPL";
            default:
                return new SomeClass();
        }
    }


    private interface Something extends Serializable {
        Object deserialize(Object jdbcField) throws SQLException;
    }

    private class SomeClass implements Something{
        @Override
        public Object deserialize(Object jdbcField) throws SQLException {
            return "success";
        }
    }
}
