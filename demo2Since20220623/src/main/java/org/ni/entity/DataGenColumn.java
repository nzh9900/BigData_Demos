package org.ni.entity;

import com.typesafe.config.Config;
import lombok.Getter;

@Getter
public class DataGenColumn extends Column {

    private final Long min;
    private final Long max;
    private final Long length;

    public DataGenColumn(String name, String type, Long min, Long max, Long length) {
        super(name, type);
        this.min = min;
        this.max = max;
        this.length = length;
    }

    public DataGenColumn(Config config) {
        super(config);
        this.min = config.hasPath("min") ? config.getLong("min") : 0;
        this.max = config.hasPath("max") ? config.getLong("max") : 0;
        this.length = config.hasPath("length") ? config.getLong("length") : 0;
    }

    @Override
    public String toString() {
        return "DataGenColumn{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", length=" + length +
                '}';
    }
}
