package prachita;

import java.io.*;
import java.util.*;
	
    import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.conf.*;
	import org.apache.hadoop.io.*;
	import org.apache.hadoop.mapred.*;
    import org.apache.hadoop.util.*;


public class WordFind extends Configured implements Tool{
	

	
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
			     
		public void configure(JobConf job)
	 	{
	 		keyTerm = job.get("keyterm");
			keyWord.set(keyTerm);

	 	}		
		
	private final static IntWritable one = new IntWritable(1);
		             private Text artId  = new Text();
			     String artTitle;
			     String artContent;
			     String checkTerm;
                             private Text keyWord= new Text();	 	
                             String keyTerm;

                   public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			       String line = value.toString();
				
				//Split the line using tab spaces
			       String[] splitdata= line.split("\t");
				
				//Store the array elements in respective articles
				//First element is Article_Unique_Id
			       artId.set(splitdata[0]);
				
				//Second element is Title of the Article
		               artTitle = splitdata[1];
				
				//Fourth element is content of the article
			       artContent = splitdata[3];
                             	
				/*Concatenate the Title and Cpntent of the Article to obtain a string that can be compared with the keyword*/
 				checkTerm = artTitle + " " + artContent;
			      
                                    //Condition to check the occurrence of keyword	
				    if(checkTerm.contains(keyTerm)){
			    	    output.collect(keyWord,one); 
                                  
			     }
			   }
	}
	

	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
     public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			       int sum = 0;
                               IntWritable result = new IntWritable();
			       //Counts the number of articles which contain the keyword
				while (values.hasNext()) {
			         sum +=values.next().get();
			       }  
                              
                              result.set(sum);
			       output.collect(key, result);  
				//Output is the keyword and the number of articles in which the keyword is present
			     }
			   }

       
      
       public int run(String[] args)throws Exception
      {
	//Configuration processd by ToolRunner
       //	Configuration conf= getConf();

        //Create a JobConf using the processed conf	
        JobConf job= new JobConf(WordFind.class);

	//Specify various job-specific parameters 
	job.setJobName("wordfind");
	
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	
	job.setMapperClass(Map.class);
       	job.setReducerClass(Reduce.class);
	
	job.setInputFormat(TextInputFormat.class);
	job.setOutputFormat(TextOutputFormat.class);
	
	FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
 
        job.setNumReduceTasks(1);	
        job.set("keyterm",args[2]);
	 
	//Submit the job
	JobClient.runJob(job);
        return 0;
}
             
	public static void main(String[] args)  throws Exception{
	//Let ToolRunner handle generic command-line options
	int res = ToolRunner.run(new Configuration(), new WordFind(), args);
         System.exit(res);	
}
	}

