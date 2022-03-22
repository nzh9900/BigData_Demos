package org.ni.flink.modify.connectors.clickhouse;

import org.apache.flink.table.connector.source.DynamicTableSource;

public class ClickhouseDynamicTableSource implements DynamicTableSource {
    @Override
    public DynamicTableSource copy() {
        return null;
    }

    @Override
    public String asSummaryString() {
        return null;
    }
}
