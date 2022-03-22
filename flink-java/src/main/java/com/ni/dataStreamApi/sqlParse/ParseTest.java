package com.ni.dataStreamApi.sqlParse;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserImplFactory;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.util.SourceStringReader;
import org.apache.flink.sql.parser.impl.FlinkSqlParserImpl;

import java.util.*;


public class ParseTest {

    public static void main(String[] args) throws Exception {
        String sql1 = "select \n" +
                "\ta.context_id as test,\n" +
                "\ta.id ,\n" +
                "\ta.t_timestamp ,\n" +
                "\ta.raw_message ,\n" +
                "\ta.user_ip ,\n" +
                "\ta.user_request_url ,\n" +
                "\ta.user_request_method ,\n" +
                "\ta.user_response_code ,\n" +
                "\ta.filesize ,\n" +
                "\ta.username ,\n" +
                "\ta.dbname ,\n" +
                "\ta.filedate ,\n" +
                "\ta.filename ,\n" +
                "\ta.part_init_date ,\n" +
                "\ta.ROWTIME\n" +
                "from\n" +
                " dwd_ngx_log_kfk a LEFT JOIN t_dim_user_auth_kd FOR SYSTEM_TIME AS OF a.proctime AS b \n" +
                "\tON a.username = b.username\n" +
                "where \n" +
                "\tb.username is null";

        String sql2 = "SELECT \n" +
                "\tcontext_id ,\n" +
                "\n" +
                "\tid ,\n" +
                "\n" +
                "\tt_timestamp ,\n" +
                "\n" +
                "\traw_message ,\n" +
                "\n" +
                "\tuser_ip ,\n" +
                "\n" +
                "\tuser_request_url ,\n" +
                "\n" +
                "\tuser_request_method ,\n" +
                "\n" +
                "\tuser_response_code ,\n" +
                "\n" +
                "\tfilesize ,\n" +
                "\n" +
                "\tusername ,\n" +
                "\n" +
                "\tdbname ,\n" +
                "\n" +
                "\tfiledate ,\n" +
                "\n" +
                "\tfilename ,\n" +
                "\n" +
                "\tpart_init_date ,\n" +
                "\n" +
                "\talarm_type \n" +
                "FROM \n" +
                "\tdwd_ngx_log_kfk  \n" +
                "match_recognize(\n" +
                "\tpartition by dbname,filename\n" +
                "\torder by ROWTIME\n" +
                "\tmeasures \n" +
                "\t\tA.context_id as context_id,A.id as id,A.t_timestamp  as t_timestamp,A.raw_message as raw_message,A.user_ip as user_ip,A.user_request_url as user_request_url,\n" +
                "\t\tA.user_request_method as user_request_method,A.user_response_code as user_response_code,A.filesize as filesize,A.username as username,\n" +
                "\t\tA.filedate as filedate,A.part_init_date as part_init_date, cast('too many failds' as VARCHAR) as alarm_type\n" +
                "\tone row per match\n" +
                "\tafter match skip to next row\n" +
                "\tpattern ( A{5} ) within interval '1' minute\n" +
                "\tdefine A as A.user_response_code <> '200'\n" +
                " )";

        String sql3 = " SELECT \n" +
                "\tcontext_id ,\n" +
                "\n" +
                "\tid ,\n" +
                "\n" +
                "\tt_timestamp ,\n" +
                "\n" +
                "\traw_message ,\n" +
                "\n" +
                "\tuser_ip ,\n" +
                "\n" +
                "\tuser_request_url ,\n" +
                "\n" +
                "\tuser_request_method ,\n" +
                "\n" +
                "\tuser_response_code ,\n" +
                "\n" +
                "\tfilesize ,\n" +
                "\n" +
                "\tusername ,\n" +
                "\n" +
                "\tdbname ,\n" +
                "\n" +
                "\tfiledate ,\n" +
                "\n" +
                "\tfilename ,\n" +
                "\n" +
                "\tpart_init_date ,\n" +
                "\n" +
                "\talarm_type \n" +
                "FROM \n" +
                "\tt_no_authorzition_view  \n" +
                "match_recognize(\n" +
                "\tpartition by username\n" +
                "\torder by ROWTIME\n" +
                "\tmeasures \n" +
                "\t\tA.context_id as context_id,A.id as id,A.t_timestamp  as t_timestamp,A.raw_message as raw_message,A.user_ip as user_ip,A.user_request_url as user_request_url,\n" +
                "\t\tA.user_request_method as user_request_method,A.user_response_code as user_response_code,A.filesize as filesize,A.dbname as dbname,A.filename as filename,\n" +
                "\t\tA.filedate as filedate,A.part_init_date as part_init_date, cast('not authorization' as VARCHAR) as alarm_type\n" +
                "\tone row per match\n" +
                "\tafter match skip to next row\n" +
                "\tpattern ( A ) within interval '1' minute\n" +
                "\tdefine A as A.user_response_code = '200'\n" +
                " )";

        String sql4 = "select \n" +
                "\tSPLIT_INDEX(raw_message,' ',0) as user_ip,\n" +
                "\tsubstr(SPLIT_INDEX(raw_message,' ',5),2) as user_request_method,\n" +
                "\tSPLIT_INDEX(raw_message,' ',8) as  user_response_code ,\n" +
                "\tcast(SPLIT_INDEX(raw_message,' ',9) as bigint) as filesize ,\n" +
                "\tSPLIT_INDEX(user_request_url,'/',2) as username ,\n" +
                "\tSPLIT_INDEX(user_request_url,'/',5) as dbname ,\n" +
                "\tSPLIT_INDEX(user_request_url,'/',6) as filedate ,\n" +
                "\tSPLIT_INDEX(user_request_url,'/',7) as filename \n" +
                "from (\n" +
                "\tselect \n" +
                "\t\t* ,\n" +
                "\t\tSPLIT_INDEX(SPLIT_INDEX(raw_message,' ',6),'?',0) as user_request_url \n" +
                "\tfrom \n" +
                "\t\tods_ngx_log_kfk \n" +
                ") a";

        String sql5 = "select\n" +
                "\ttime_min,\n" +
                "\tusername as max_download_username,\n" +
                "\tdownloads_user_num as max_downloads_user_num\n" +
                "from \n" +
                "(\n" +
                "\tselect \n" +
                "\t\ttime_min,\n" +
                "\t\tusername,\n" +
                "\t\tcount(1) as downloads_user_num\n" +
                "\tfrom \n" +
                "\t\tdwd_download_log_view\n" +
                "\tgroup by \n" +
                "\t\ttime_min,username\n" +
                ") a order by downloads_user_num desc limit 1";

        String sql6 = "select \n" +
                "\ta.time_min as time_min ,\n" +
                "\ta.requests as requests,\n" +
                "\ta.errors as errors ,\n" +
                "\tcast(a.errors as float)/a.requests as err_rate ,\n" +
                "\ta.total_filesize as total_filesize ,\n" +
                "\tcast(a.total_filesize as float)/(a.requests-a.errors) as avg_filesize ,\n" +
                "\tb.max_download_username as max_download_username ,\n" +
                "\tb.max_downloads_user_num as max_downloads_user_num ,\n" +
                "\tc.max_download_tablename as max_download_tablename ,\n" +
                "\tc.max_download_table_num as max_download_table_num, \n" +
                "\tCURRENT_TIMESTAMP() as currentTime\n" +
                "FROM \n" +
                "\trequests_view a inner join user_download_view b on a.time_min = b.time_min inner join file_download_view c on a.time_min = c.time_min";

        // 构建sqlParser
        SqlParser.Config parserConfig = createParserConfig(buildDefaultOptions());
        SqlParser sqlParser = SqlParser.create(new SourceStringReader(sql6), parserConfig);

        // 获得字段列表
        try {
            SqlNode sqlNode = sqlParser.parseQuery();
            List<String> result = handlerSQL(sqlNode);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException("sql语句解析错误", e);
        }

    }

    private static Map<String, Object> buildDefaultOptions() {
        final Map<String, Object> m = new HashMap<>();
        m.put("quoting", Quoting.BACK_TICK);
        m.put("quotedCasing", Casing.UNCHANGED);
        m.put("unquotedCasing", Casing.UNCHANGED);
        m.put("caseSensitive", true);
        m.put("enableTypeCoercion", false);
        m.put("conformance", SqlConformanceEnum.DEFAULT);
        m.put("operatorTable", SqlStdOperatorTable.instance());
        m.put("parserFactory", FlinkSqlParserImpl.FACTORY);
        return Collections.unmodifiableMap(m);
    }

    private static SqlParser.Config createParserConfig(Map<String, Object> options) {
        return SqlParser.configBuilder()
                .setQuoting((Quoting) options.get("quoting"))
                .setUnquotedCasing((Casing) options.get("unquotedCasing"))
                .setQuotedCasing((Casing) options.get("quotedCasing"))
                .setConformance((SqlConformance) options.get("conformance"))
                .setCaseSensitive((boolean) options.get("caseSensitive"))
                .setParserFactory((SqlParserImplFactory) options.get("parserFactory"))
                .build();
    }

    private static List<String> handlerSQL(SqlNode sqlNode) {
        SqlKind kind = sqlNode.getKind();
        List<String> result = new ArrayList<>();
        switch (kind) {
            case SELECT:
                result.addAll(handlerSelect(sqlNode));
                break;
            case UNION:
                ((SqlBasicCall) sqlNode).getOperandList().forEach(node -> {
                    result.addAll(handlerSQL(node));
                });
                break;
            case ORDER_BY:
                result.addAll(handlerOrderBy(sqlNode));
                break;
        }
        return result;
    }

    private static List<String> handlerSelect(SqlNode select) {
        List<String> result = new ArrayList<>();
        SqlSelect sqlSelect = (SqlSelect) select;
        List<String> tableNames = getTableName(sqlSelect.getFrom());

        SqlNodeList selectList = sqlSelect.getSelectList();
        //字段信息
        selectList.getList().forEach(list -> {
            result.add(handlerField(list, tableNames));
        });
        return result;
    }

    private static List<String> handlerOrderBy(SqlNode node) {
        SqlOrderBy sqlOrderBy = (SqlOrderBy) node;
        SqlNode query = sqlOrderBy.query;
        return handlerSQL(query);
    }

    private static List<String> getTableName(SqlNode from) {
        List<String> tableName = new ArrayList<>();
        SqlKind kind = from.getKind();
        switch (kind) {
            case IDENTIFIER:
                //最终的表名
                SqlIdentifier sqlIdentifier = (SqlIdentifier) from;
                tableName.add(sqlIdentifier.toString());
                break;
            case AS:
                SqlBasicCall sqlBasicCall = (SqlBasicCall) from;
                SqlNode selectNode = sqlBasicCall.getOperandList().get(1);
                tableName.add(selectNode.toString());
                break;
            case JOIN:
                SqlJoin sqlJoin = (SqlJoin) from;

                tableName.addAll(getTableName(sqlJoin.getLeft()));

                SqlNode right = sqlJoin.getRight();
                tableName.addAll(getTableName(right));
                break;
            case SELECT:
                handlerSQL(from);
                break;
        }
        return tableName;
    }

    private static String handlerField(SqlNode field, List<String> tableNameCollection) throws IllegalArgumentException {
        String result = "";
        SqlKind kind = field.getKind();
        switch (kind) {
            case IDENTIFIER:
                //表示当前为子节点
                SqlIdentifier sqlIdentifier = (SqlIdentifier) field;
                String fieldName = sqlIdentifier.toString();
                result = fieldName;
                if (fieldName.equals("*")) {
                    throw new IllegalArgumentException("select查询的字段不能使用'*'号");
                }
                for (String tableName : tableNameCollection) {
                    if (fieldName.startsWith(tableName + ".")) {
                        result = fieldName.substring(fieldName.indexOf(".") + 1);
                        break;
                    }
                }
                break;
            case AS:
                SqlNode[] operands_as = ((SqlBasicCall) field).operands;
                SqlNode rightAs = operands_as[1];
                result = handlerField(rightAs, tableNameCollection);
                break;
            default:
                if (field instanceof SqlBasicCall) {
                    SqlNode[] nodes = ((SqlBasicCall) field).operands;
                    for (int i = 0; i < nodes.length; i++) {
                        result = handlerField(nodes[i], tableNameCollection);
                    }
                }
                break;
        }
        return result;
    }
}