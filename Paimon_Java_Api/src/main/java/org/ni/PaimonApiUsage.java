package org.ni;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.CatalogContext;
import org.apache.paimon.catalog.CatalogFactory;
import org.apache.paimon.catalog.Identifier;
import org.apache.paimon.options.Options;
import org.apache.paimon.table.Table;
import org.apache.paimon.types.DataField;
import org.apache.paimon.types.RowType;

import java.io.IOException;

public class PaimonApiUsage {
    public static void main(String[] args) throws Catalog.DatabaseNotExistException, Catalog.TableNotExistException {
        PaimonApiUsage paimonApiUsage = new PaimonApiUsage();
        // init Catalog
        //Catalog hiveCatalog = paimonApiUsage.getHiveCatalog();
        Catalog hiveCatalog = paimonApiUsage.getFileSystemCatalog();

        // print database
        System.out.println(hiveCatalog.listDatabases());

        // print table list
        System.out.println("paimon tables in for_paimon: " + hiveCatalog.listTables("for_paimon"));

        // print table info
        Table table = hiveCatalog.getTable(Identifier.create("for_paimon", "my_table"));
        RowType rowType = table.rowType();
        for (DataField field : rowType.getFields()) {
            System.out.println("field: " + field.name() + " : " + field.type());
        }
        System.out.println("table options: " + table.options());
        System.out.println("primary keys: " + table.primaryKeys());
    }

    public Catalog getFileSystemCatalog() {
        kerberosAuth();
        Options options = new Options();
        options.set("warehouse", "hdfs://nameservice1/user/hive/warehouse/");
        options.set("hadoop-conf-dir", "/opt/dashi_cluster_conf/hadoop_conf");
        CatalogContext context = CatalogContext.create(options);
        return CatalogFactory.createCatalog(context);
    }

    public Catalog getHiveCatalog() {
        kerberosAuth();

        Options options = new Options();
        options.set("metastore", "hive");
        options.set("hive-conf-dir", "/opt/dashi_cluster_conf/hive_conf");
        options.set("hadoop-conf-dir", "/opt/dashi_cluster_conf/hadoop_conf");
        CatalogContext context = CatalogContext.create(options);
        return CatalogFactory.createCatalog(context);
    }

    private void kerberosAuth() {
        try {
            org.apache.hadoop.conf.Configuration hadoopConfiguration = new org.apache.hadoop.conf.Configuration();
            System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
            hadoopConfiguration.set("hadoop.security.authentication", "kerberos");
            UserGroupInformation.setConfiguration(hadoopConfiguration);
            UserGroupInformation.loginUserFromKeytab("idp@TEST.COM", "/opt/idp.keytab");
        } catch (IOException e) {
            throw new RuntimeException("kerberos auth error:", e);
        }
    }
}