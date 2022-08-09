package org.ni.entity;

import com.typesafe.config.Config;
import lombok.Getter;

@Getter
public class Column {
    final String name;
    final String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Column(Config config) {
        this.name = config.getString("name");
        this.type = config.getString("type");
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
