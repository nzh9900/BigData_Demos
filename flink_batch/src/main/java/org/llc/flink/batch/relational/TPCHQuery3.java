package org.llc.flink.batch.relational;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TPCHQuery3 {

    // *************************************************************************
    //     PROGRAM
    // *************************************************************************

    public static void main(String[] args) throws Exception {

        final ParameterTool params = ParameterTool.fromArgs(args);

        if (!params.has("lineitem") && !params.has("customer") && !params.has("orders")) {
            System.err.println(
                    "  This program expects data from the TPC-H benchmark as input data.");
            System.err.println("  Due to legal restrictions, we can not ship generated data.");
            System.out.println(
                    "  You can find the TPC-H data generator at http://www.tpc.org/tpch/.");
            System.out.println(
                    "  Usage: TPCHQuery3 --lineitem <path> --customer <path> --orders <path> [--output <path>]");
            return;
        }

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<Lineitem> lineitems = getLineitemDataSet(env, params.get("lineitem"));
        DataSet<Customer> customers = getCustomerDataSet(env, params.get("customer"));
        DataSet<Order> orders = getOrdersDataSet(env, params.get("orders"));

        // Filter market segment "AUTOMOBILE"
        customers =
                customers.filter(
                        (FilterFunction<Customer>) c -> c.getMktsegment().equals("AUTOMOBILE"));

        // Filter all Orders with o_orderdate < 12.03.1995
        orders =
                orders.filter(
                        new FilterFunction<Order>() {
                            private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            private final Date date = format.parse("1995-03-12");

                            @Override
                            public boolean filter(Order o) throws ParseException {
                                return format.parse(o.getOrderdate()).before(date);
                            }
                        });

        // Filter all Lineitems with l_shipdate > 12.03.1995
        lineitems =
                lineitems.filter(
                        new FilterFunction<Lineitem>() {
                            private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            private final Date date = format.parse("1995-03-12");

                            @Override
                            public boolean filter(Lineitem l) throws ParseException {
                                return format.parse(l.getShipdate()).after(date);
                            }
                        });

        // Join customers with orders and package them into a ShippingPriorityItem
        DataSet<ShippingPriorityItem> customerWithOrders =
                customers
                        .join(orders)
                        .where(0)
                        .equalTo(1)
                        .with(
                                (JoinFunction<Customer, Order, ShippingPriorityItem>) (c, o) -> new ShippingPriorityItem(
                                        o.getOrderKey(),
                                        0.0,
                                        o.getOrderdate(),
                                        o.getShippriority()));

        // Join the last join result with Lineitems
        DataSet<ShippingPriorityItem> result =
                customerWithOrders
                        .join(lineitems)
                        .where(0)
                        .equalTo(0)
                        .with(
                                (JoinFunction<ShippingPriorityItem, Lineitem, ShippingPriorityItem>) (i, l) -> {
                                    i.setRevenue(l.getExtendedprice() * (1 - l.getDiscount()));
                                    return i;
                                })
                        // Group by l_orderkey, o_orderdate and o_shippriority and compute revenue
                        // sum
                        .groupBy(0, 2, 3)
                        .aggregate(Aggregations.SUM, 1);

        // emit result
        if (params.has("output")) {
            result.writeAsCsv(params.get("output"), "\n", "|");
            // execute program
            env.execute("TPCH Query 3 Example");
        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            result.print();
        }
    }

    // *************************************************************************
    //     DATA TYPES
    // *************************************************************************

    /** Lineitem. */
    public static class Lineitem extends Tuple4<Long, Double, Double, String> {

        public Long getOrderkey() {
            return this.f0;
        }

        public Double getDiscount() {
            return this.f2;
        }

        public Double getExtendedprice() {
            return this.f1;
        }

        public String getShipdate() {
            return this.f3;
        }
    }

    /** Customer. */
    public static class Customer extends Tuple2<Long, String> {

        public Long getCustKey() {
            return this.f0;
        }

        public String getMktsegment() {
            return this.f1;
        }
    }

    /** Order. */
    public static class Order extends Tuple4<Long, Long, String, Long> {

        public Long getOrderKey() {
            return this.f0;
        }

        public Long getCustKey() {
            return this.f1;
        }

        public String getOrderdate() {
            return this.f2;
        }

        public Long getShippriority() {
            return this.f3;
        }
    }

    /** ShippingPriorityItem. */
    public static class ShippingPriorityItem extends Tuple4<Long, Double, String, Long> {

        public ShippingPriorityItem() {}

        public ShippingPriorityItem(
                Long orderkey, Double revenue, String orderdate, Long shippriority) {
            this.f0 = orderkey;
            this.f1 = revenue;
            this.f2 = orderdate;
            this.f3 = shippriority;
        }

        public Long getOrderkey() {
            return this.f0;
        }

        public void setOrderkey(Long orderkey) {
            this.f0 = orderkey;
        }

        public Double getRevenue() {
            return this.f1;
        }

        public void setRevenue(Double revenue) {
            this.f1 = revenue;
        }

        public String getOrderdate() {
            return this.f2;
        }

        public Long getShippriority() {
            return this.f3;
        }
    }

    // *************************************************************************
    //     UTIL METHODS
    // *************************************************************************

    private static DataSet<Lineitem> getLineitemDataSet(ExecutionEnvironment env, String lineitemPath) {
        return env.readCsvFile(lineitemPath)
                .fieldDelimiter("|")
                .includeFields("1000011000100000")
                .tupleType(Lineitem.class);
    }

    private static DataSet<Customer> getCustomerDataSet(ExecutionEnvironment env, String customerPath) {
        return env.readCsvFile(customerPath)
                .fieldDelimiter("|")
                .includeFields("10000010")
                .tupleType(Customer.class);
    }

    private static DataSet<Order> getOrdersDataSet(ExecutionEnvironment env, String ordersPath) {
        return env.readCsvFile(ordersPath)
                .fieldDelimiter("|")
                .includeFields("110010010")
                .tupleType(Order.class);
    }
}
