import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Join {
    private static final String INPUT_PATH = "input-join/";
    private static final String OUTPUT_PATH = "output/groupBy-";
    private static final Logger LOG = Logger.getLogger(GroupBy.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n%6$s");

        try {
            FileHandler fh = new FileHandler("out.log");
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
        } catch (SecurityException | IOException e) {
            System.exit(1);
        }
    }

    public static class Map extends Mapper<Object, Text, Text, Text> {
        private Text outkey = new Text();
        private Text outvalue = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split("\\|");
            String source = ((FileSplit) context.getInputSplit()).getPath().getName();

            if (source.equals("customers.tbl")) {
                outkey.set(fields[0]);
                outvalue.set("A" + fields[1]);
            } else if (source.equals("orders.tbl")) {
                outkey.set(fields[1]);
                outvalue.set("B" + fields[8]);
            }
            context.write(outkey, outvalue);
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        private Text outvalue = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            List<String> listA = new ArrayList<>();
            List<String> listB = new ArrayList<>();

            for (Text t : values) {
                if (t.charAt(0) == 'A') {
                    listA.add(t.toString().substring(1));
                } else if (t.charAt(0) == 'B') {
                    listB.add(t.toString().substring(1));
                }
            }

            for (String A : listA) {
                for (String B : listB) {
                    outvalue.set(A + ", " + B);
                    context.write(key, outvalue);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = new Job(conf, "Join");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Join.Map.class);
        job.setReducerClass(Join.Reduce.class);

        job.setOutputValueClass(DoubleWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

        job.waitForCompletion(true);
    }
}