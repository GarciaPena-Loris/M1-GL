
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class GroupBy {
	private static final String INPUT_PATH = "input-groupBy/";
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

	// ############ Exercice 3 :
	/*
	public static class Map extends Mapper<LongWritable, Text, Text, DoubleWritable> {

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split(",");
			String customerID = fields[5];
			double profit = Double.parseDouble(fields[20]);
			context.write(new Text(customerID), new DoubleWritable(profit));
		}
	}

	public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		@Override
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
				throws IOException, InterruptedException {
			double sum = 0;
			for (DoubleWritable val : values) {
				sum += val.get();
			}
			context.write(key, new DoubleWritable(sum));
		}
	}
	*/

	/* ############ Exercice 4 - Partie 1 :
	public static class Map extends Mapper<LongWritable, Text, Text, DoubleWritable> {

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split(",");
			if (fields.length > 20) {
				try {
					String date = fields[2];
					String state = fields[10];
					String category = fields[14];
					double sales = Double.parseDouble(fields[17]);
					context.write(new Text("Date: " + date + ", etat: " + state), new DoubleWritable(sales));
					context.write(new Text("Date: " + date + ", categorie" + category), new DoubleWritable(sales));
				} catch (NumberFormatException e) {
					// Rien à faire
				}
			}
		}
	}

	public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		@Override
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
				throws IOException, InterruptedException {
			double sum = 0;
			for (DoubleWritable val : values) {
				sum += val.get();
			}
			context.write(key, new DoubleWritable(sum));
		}
	}
	 */

	// ############ Exercice 4 - Partie 2 :
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split(",");
			if (fields.length > 20) {
				try {
					String orderId = fields[1];
					String productId = fields[13];
					String quantity = fields[18];
					context.write(new Text(orderId), new Text(productId + "," + quantity));
				} catch (NumberFormatException e) {
					// Rien à faire
				}
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int distinctProducts = 0;
			int totalQuantity = 0;
			HashSet<String> products = new HashSet<>();
			for (Text val : values) {
				String[] fields = val.toString().split(",");
				String productId = fields[0];
				int quantity = Integer.parseInt(fields[1]);
				if (!products.contains(productId)) {
					products.add(productId);
					distinctProducts++;
				}
				totalQuantity += quantity;
			}
			context.write(key, new Text("Nombre de produits différents: " + distinctProducts + ", Nombre total d'exemplaires: " + totalQuantity));
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job = new Job(conf, "GroupBy");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setOutputValueClass(DoubleWritable.class); 

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

		job.waitForCompletion(true);
	}
}