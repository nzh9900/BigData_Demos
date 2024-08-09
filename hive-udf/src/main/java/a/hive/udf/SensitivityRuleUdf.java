package a.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorConverter;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

@Description(name = "sensitivity", value = "_FUNC_(column, sensitivity rule id)")
public class SensitivityRuleUdf extends GenericUDF {
    private PrimitiveObjectInspectorConverter.StringConverter stringConverter;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {


        // 1. 校验接收参数的数量
        if (arguments.length != 2) {
            throw new UDFArgumentException();
        }
        // 2. 校验接收参数类型
        ObjectInspector nickName = arguments[0];
        if (nickName.getCategory().equals(ObjectInspector.Category.PRIMITIVE)) {
            PrimitiveObjectInspector ruleId2 = (PrimitiveObjectInspector) nickName;
            if (!ruleId2.getPrimitiveCategory().equals(PrimitiveObjectInspector.PrimitiveCategory.STRING)) {
                throw new UDFArgumentTypeException();
            }
        }
        ObjectInspector column = arguments[1];
        stringConverter = new PrimitiveObjectInspectorConverter.StringConverter((PrimitiveObjectInspector) column);
        if (!column.getCategory().equals(ObjectInspector.Category.PRIMITIVE)) {
            throw new UDFArgumentTypeException();
        }
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }

    @Override
    public String evaluate(DeferredObject[] arguments) throws HiveException {
        try {
            return (String) stringConverter.convert(arguments[1].get());
        } catch (Exception e) {
            throw new HiveException(e);
        }
    }

    @Override
    public String getDisplayString(String[] children) {
        return null;
    }

}
