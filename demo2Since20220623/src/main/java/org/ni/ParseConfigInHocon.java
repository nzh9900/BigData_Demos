package org.ni;


import com.typesafe.config.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.ni.entity.DataGenColumn;

public class ParseConfigInHocon {
    public static void main(String[] args) throws URISyntaxException {
        parse(new File(ParseConfigInHocon.class.getResource("/test.conf").toURI()));
    }

    public static void parse(File configFile) {
        Config config = ConfigFactory
                .parseFile(configFile)
                .resolve(ConfigResolveOptions.defaults().setAllowUnresolved(true))
                .resolveWith(ConfigFactory.systemProperties(),
                        ConfigResolveOptions.defaults().setAllowUnresolved(true));

        ConfigRenderOptions options = ConfigRenderOptions.concise().setFormatted(true);
        System.out.println(config.root().render(options));
        System.out.println("\n\n================================\n\n");

        ArrayList<DataGenColumn> dataGenColumns = new ArrayList<>();
        List<? extends Config> objectList = config.getConfigList("source.DataGenerator.resultTableColumns");

        for (Config object : objectList) {
            DataGenColumn dataGenColumn = new DataGenColumn(object);
            dataGenColumns.add(dataGenColumn);
        }

        System.out.println("\n\n================================\n\n");

        System.out.println(dataGenColumns);
    }
}
