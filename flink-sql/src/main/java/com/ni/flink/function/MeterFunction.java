package com.ni.flink.function;

import org.apache.flink.metrics.*;
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
 * @ClassName MeterFunction
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/29 15:17
 * @Version 1.0
 **/
public class MeterFunction extends ScalarFunction {
    private Meter meter;
    private final String counterName;
    private final String meterName;
    private final String groupName;
    private final DataType dataType;


    public MeterFunction(String groupName, String counterName, String meterName, DataType dataType) {
        this.groupName = groupName;
        this.counterName = counterName;
        this.meterName = meterName;
        this.dataType = dataType;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        MetricGroup metricGroup = context.getMetricGroup().addGroup(groupName);
        Counter counter = metricGroup.counter(counterName, new SimpleCounter());
        this.meter = new MeterView(counter, 60);
        metricGroup.meter(meterName, meter);
    }

    //@DataTypeHint(version = ExtractionVersion.V1, value = "TIMESTAMP(3)")
    //public Object eval(@DataTypeHint(inputGroup = InputGroup.ANY) Object o) {
    //    counter.inc();
    //    return o;
    //}

    public Object eval(Object o) {
        meter.markEvent();
        return o;
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        return TypeInference.newBuilder()
                .inputTypeStrategy(new MeterFunction.MyInputTypeStrategy())
                .outputTypeStrategy(new MeterFunction.MyOutputTypeStrategy())
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
}