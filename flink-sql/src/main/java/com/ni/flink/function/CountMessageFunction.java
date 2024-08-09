package com.ni.flink.function;

import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.FunctionDefinition;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.inference.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CountMessageFunction
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/23 16:47
 * @Version 1.0
 **/
public class CountMessageFunction extends ScalarFunction {
    private transient Counter counter;
    private final String counterName;
    private final String groupName;
    private final DataType dataType;


    public CountMessageFunction(String groupName, String counterName, DataType dataType) {
        this.groupName = groupName;
        this.counterName = counterName;
        this.dataType = dataType;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        MetricGroup metricGroup = context.getMetricGroup().addGroup(groupName);
        this.counter = metricGroup.counter(counterName);
    }

    //@DataTypeHint(version = ExtractionVersion.V1, value = "TIMESTAMP(3)")
    //public Object eval(@DataTypeHint(inputGroup = InputGroup.ANY) Object o) {
    //    counter.inc();
    //    return o;
    //}

    public Object eval(Object o) {
        counter.inc();
        return o;
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        return TypeInference.newBuilder()
                .inputTypeStrategy(new MyInputTypeStrategy())
                .outputTypeStrategy(new MyOutputTypeStrategy())
                .build();
    }

    private class MyInputTypeStrategy implements InputTypeStrategy {
        private MyInputTypeStrategy() {

        }

        @Override
        public ArgumentCount getArgumentCount() {
            return ConstantArgumentCount.any();
        }

        @Override
        public Optional<List<DataType>> inferInputTypes(CallContext callContext, boolean throwOnFailure) {
            return Optional.of(Arrays.asList(dataType));
        }

        @Override
        public List<Signature> getExpectedSignatures(FunctionDefinition definition) {
            return Collections.singletonList(Signature.of(new Signature.Argument[]{Signature.Argument.of("*")}));
        }
    }

    private class MyOutputTypeStrategy implements TypeStrategy {
        private MyOutputTypeStrategy() {
        }

        @Override
        public Optional<DataType> inferType(CallContext callContext) {
            return Optional.of(dataType);
        }
    }

    /*public String eval(String value) {
        counter.inc();
        return value;
    }

    public Integer eval(Integer value) {
        counter.inc();
        return value;
    }

    public Double eval(Double value) {
        counter.inc();
        return value;
    }

    public Long eval(Long value) {
        counter.inc();
        return value;
    }

    public Boolean eval(Boolean value) {
        counter.inc();
        return value;
    }

    public Byte eval(Byte value) {
        counter.inc();
        return value;
    }

    public Short eval(Short value) {
        counter.inc();
        return value;
    }

    public Float eval(Float value) {
        counter.inc();
        return value;
    }

    public LocalDate eval(LocalDate value) {
        counter.inc();
        return value;
    }

    public Date eval(Date value) {
        counter.inc();
        return value;
    }

    public Time eval(Time value) {
        counter.inc();
        return value;
    }

    public LocalTime eval(LocalTime value) {
        counter.inc();
        return value;
    }

    public LocalDateTime eval(LocalDateTime value) {
        counter.inc();
        return value;
    }

    public Timestamp eval(Timestamp value) {
        counter.inc();
        return value;
    }

    public OffsetDateTime eval(OffsetDateTime value) {
        counter.inc();
        return value;
    }

    public Duration eval(Duration value) {
        counter.inc();
        return value;
    }*/
}