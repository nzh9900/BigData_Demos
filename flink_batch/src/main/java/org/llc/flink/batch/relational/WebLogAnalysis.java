
package org.llc.flink.batch.relational;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.FunctionAnnotation.ForwardedFieldsFirst;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;
import org.llc.flink.batch.relational.util.WebLogData;

public class WebLogAnalysis {


    public static void main(String[] args) throws Exception {


        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple2<String, String>> documents = getDocumentsDataSet(env);
        DataSet<Tuple3<Integer, String, Integer>> ranks = getRanksDataSet(env);
        DataSet<Tuple2<String, String>> visits = getVisitsDataSet(env);

        DataSet<Tuple1<String>> filterDocs = documents
                .filter((aa)-> aa.f1.contains(" editors "))
                .project(0);


        DataSet<Tuple3<Integer, String, Integer>> filterRanks =
                ranks.filter(new FilterByRank());

        DataSet<Tuple1<String>> filterVisits = visits
                .filter(new FilterVisitsByDate())
                .project(0);

        DataSet<Tuple3<Integer, String, Integer>> joinDocsRanks =
                filterDocs.join(filterRanks)
                        .where(0)
                        .equalTo(1)
                        .projectSecond(0, 1, 2);

        DataSet<Tuple3<Integer, String, Integer>> result =
                joinDocsRanks.coGroup(filterVisits)
                        .where(1)
                        .equalTo(0)
                        .with(new AntiJoinVisits());

        result.print();
    }

    public static class FilterDocByKeyWords implements FilterFunction<Tuple2<String, String>> {

        private static final String[] KEYWORDS = {" editors ", " oscillations "};


        @Override
        public boolean filter(Tuple2<String, String> value) throws Exception {
            String docText = value.f1;
            for (String kw : KEYWORDS) {
                if (!docText.contains(kw)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class FilterByRank implements FilterFunction<Tuple3<Integer, String, Integer>> {

        private static final int RANKFILTER = 40;

        @Override
        public boolean filter(Tuple3<Integer, String, Integer> value) throws Exception {
            return (value.f0 > RANKFILTER);
        }
    }


    public static class FilterVisitsByDate implements FilterFunction<Tuple2<String, String>> {

        private static final int YEARFILTER = 2007;

        @Override
        public boolean filter(Tuple2<String, String> value) {
            String dateString = value.f1;
            int year = Integer.parseInt(dateString.substring(0, 4));
            return (year == YEARFILTER);
        }
    }


    @ForwardedFieldsFirst("*")
    public static class AntiJoinVisits implements CoGroupFunction<
            Tuple3<Integer, String, Integer>,
            Tuple1<String>,
            Tuple3<Integer, String, Integer>> {

        @Override
        public void coGroup(
                Iterable<Tuple3<Integer, String, Integer>> ranks,
                Iterable<Tuple1<String>> visits,
                Collector<Tuple3<Integer, String, Integer>> out) {
            // Check if there is a entry in the visits relation
            if (!visits.iterator().hasNext()) {
                for (Tuple3<Integer, String, Integer> next : ranks) {
                    // Emit all rank pairs
                    out.collect(next);
                }
            }
        }
    }

    private static DataSet<Tuple2<String, String>> getDocumentsDataSet(ExecutionEnvironment env) {
        return WebLogData.getDocumentDataSet(env);
    }

    private static DataSet<Tuple3<Integer, String, Integer>> getRanksDataSet(ExecutionEnvironment env) {
        return WebLogData.getRankDataSet(env);
    }

    private static DataSet<Tuple2<String, String>> getVisitsDataSet(ExecutionEnvironment env) {
        return WebLogData.getVisitDataSet(env);
    }

}
