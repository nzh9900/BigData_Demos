package org.llc.flink.batch.relational;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.api.java.utils.ParameterTool;

public class TPCHQuery10 {

    // *************************************************************************
    //     PROGRAM
    // *************************************************************************

    public static void main(String[] args) throws Exception {

        final ParameterTool params = ParameterTool.fromArgs(args);

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        if (!params.has("customer")
                && !params.has("orders")
                && !params.has("lineitem")
                && !params.has("nation")) {
            System.err.println(
                    "  This program expects data from the TPC-H benchmark as input data.");
            System.err.println("  Due to legal restrictions, we can not ship generated data.");
            System.err.println(
                    "  You can find the TPC-H data generator at http://www.tpc.org/tpch/.");
            System.err.println(
                    "  Usage: TPCHQuery10 --customer <path> --orders <path> --lineitem <path> --nation <path> [--output <path>]");
            return;
        }

        // get customer data set: (custkey, name, address, nationkey, acctbal)
        DataSet<Tuple5<Integer, String, String, Integer, Double>> customers =
                getCustomerDataSet(env, params.get("customer"));
        // get orders data set: (orderkey, custkey, orderdate)
        DataSet<Tuple3<Integer, Integer, String>> orders =
                getOrdersDataSet(env, params.get("orders"));
        // get lineitem data set: (orderkey, extendedprice, discount, returnflag)
        DataSet<Tuple4<Integer, Double, Double, String>> lineitems =
                getLineitemDataSet(env, params.get("lineitem"));
        // get nation data set: (nationkey, name)
        DataSet<Tuple2<Integer, String>> nations = getNationsDataSet(env, params.get("nation"));

        // orders filtered by year: (orderkey, custkey)
        DataSet<Tuple2<Integer, Integer>> ordersFilteredByYear =
                // filter by year
                orders.filter(order -> Integer.parseInt(order.f2.substring(0, 4)) > 1990)
                        // project fields out that are no longer required
                        .project(0, 1);

        // lineitems filtered by flag: (orderkey, revenue)
        DataSet<Tuple2<Integer, Double>> lineitemsFilteredByFlag =
                // filter by flag
                lineitems
                        .filter(lineitem -> lineitem.f3.equals("R"))
                        // compute revenue and project out return flag
                        // revenue per item = l_extendedprice * (1 - l_discount)
                        .map(lineitem -> new Tuple2<>(lineitem.f0, lineitem.f1 * (1 - lineitem.f2)))
                        .returns(Types.TUPLE(Types.INT, Types.DOUBLE)); // for lambda with generics

        // join orders with lineitems: (custkey, revenue)
        DataSet<Tuple2<Integer, Double>> revenueByCustomer =
                ordersFilteredByYear
                        .joinWithHuge(lineitemsFilteredByFlag)
                        .where(0)
                        .equalTo(0)
                        .projectFirst(1)
                        .projectSecond(1);

        revenueByCustomer = revenueByCustomer.groupBy(0).aggregate(Aggregations.SUM, 1);

        // join customer with nation (custkey, name, address, nationname, acctbal)
        DataSet<Tuple5<Integer, String, String, String, Double>> customerWithNation =
                customers
                        .joinWithTiny(nations)
                        .where(3)
                        .equalTo(0)
                        .projectFirst(0, 1, 2)
                        .projectSecond(1)
                        .projectFirst(4);

        // join customer (with nation) with revenue (custkey, name, address, nationname, acctbal,
        // revenue)
        DataSet<Tuple6<Integer, String, String, String, Double, Double>> result =
                customerWithNation
                        .join(revenueByCustomer)
                        .where(0)
                        .equalTo(0)
                        .projectFirst(0, 1, 2, 3, 4)
                        .projectSecond(1);

        // emit result
        if (params.has("output")) {
            result.writeAsCsv(params.get("output"), "\n", "|");
            // execute program
            env.execute("TPCH Query 10 Example");
        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            result.print();
        }
    }

    // *************************************************************************
    //     UTIL METHODS
    // *************************************************************************

    private static DataSet<Tuple5<Integer, String, String, Integer, Double>> getCustomerDataSet(
            ExecutionEnvironment env, String customerPath) {
        return env.readCsvFile(customerPath)
                .fieldDelimiter("|")
                .includeFields("11110100")
                .types(Integer.class, String.class, String.class, Integer.class, Double.class);
    }

    private static DataSet<Tuple3<Integer, Integer, String>> getOrdersDataSet(
            ExecutionEnvironment env, String ordersPath) {
        return env.readCsvFile(ordersPath)
                .fieldDelimiter("|")
                .includeFields("110010000")
                .types(Integer.class, Integer.class, String.class);
    }

    private static DataSet<Tuple4<Integer, Double, Double, String>> getLineitemDataSet(
            ExecutionEnvironment env, String lineitemPath) {
        return env.readCsvFile(lineitemPath)
                .fieldDelimiter("|")
                .includeFields("1000011010000000")
                .types(Integer.class, Double.class, Double.class, String.class);
    }

    private static DataSet<Tuple2<Integer, String>> getNationsDataSet(
            ExecutionEnvironment env, String nationPath) {
        return env.readCsvFile(nationPath)
                .fieldDelimiter("|")
                .includeFields("1100")
                .types(Integer.class, String.class);
    }
}
