package jobs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

@DisallowConcurrentExecution
public class SimpleFilePruneJob implements Job {
  
  public final static String PARAM_FOLDER = "FolderAbsPath";
  public final static String PARAM_FILENAME = "Filename";
  public final static String PARAM_TRIGGER = "CronExpression";	
  
  public final static String PARAM_FILENAME_FROM_WEB = "FilenameFromWeb";
  
  private final String className = this.getClass().getSimpleName();
  private Logger logger = Logger.getLogger(SimpleFilePruneJob.class);

  private static Pattern patternFolderAbsPath = Pattern.compile("FolderAbsPath=(.:.*);");
  private static Pattern patternFilename = Pattern.compile("Filename=(.+);");
  private static Pattern patternFolderCronExpression = Pattern.compile("CronExpression=(.+);");
  
  public SimpleFilePruneJob() {

  }
  
  public void execute(JobExecutionContext context) throws JobExecutionException {
    
	  String methodName = className + ".execute()";
    
	  logger.info(String.format("%1$s: >>>>>>>>>>>>>>>>> starting execution...", methodName));
    
	  JobKey key = context.getJobDetail().getKey();

	  JobDataMap dataMap = context.getJobDetail().getJobDataMap();

	  String folderToWatch = dataMap.getString(PARAM_FOLDER);
	  String filenameToPrune = dataMap.getString(PARAM_FILENAME);
	  	  	  
	  String filenameFromWeb;// sólo se va a usar en la traza
	  try {
		filenameFromWeb = (String) context.getScheduler().getContext().get(PARAM_FILENAME_FROM_WEB);
		logger.debug(String.format("%1$s: Filename from web.xml = %2$s.", methodName, filenameFromWeb));
	  } catch (SchedulerException e1) {
		  logger.debug(String.format("%1$s: error getting filename from web.xml).", methodName), e1);
	  }
		
	  
	  logger.debug(String.format("%1$s: Instance %2$s of SimpleFilePruneJob (folder=%3$s; filename=%4$s).", methodName,
			  key, folderToWatch, filenameToPrune));
    
	  Path folderPath = Paths.get(folderToWatch);
	  if (Files.isDirectory(folderPath)) {
		  
		  try {
			  List<Path> files = Files.walk(folderPath)
	                                  .filter(Files::isRegularFile)
	                                  .filter(x -> x.getFileName().toString().equals(filenameToPrune))
	                                //.peek(x -> System.out.println("equals " + x.getFileName())) // peek for debuggin purposes
	                                  .collect(Collectors.toList());
			  
			  if (files.isEmpty()) {
				  logger.info(String.format("%1$s: No files to prune.", methodName));
			  }
			  
			  for (Path path : files) {
				  // Solo debería de haber como máximo 1
				  Files.delete(path);
				  logger.info(String.format("%1$s: Pruned file %2$s.", methodName, path.getFileName()));
			  }
			  
		  } catch (IOException e) {
			  logger.error(String.format("%1$s: error pruning files in folder %2$s.", methodName, folderToWatch));
		  }
	  } else {
		  logger.error(String.format("%1$s: Folder %2$s is not a valid directory.", methodName, folderToWatch));
	  }
	  
	  logger.info(String.format("%1$s: >>>>>>>>>>>>>>>>> execution finished.", methodName));
    
  }
  
  public static String getCronExpressionParam() {
	  return PARAM_TRIGGER;
  }
  
  public static Map<String, Pattern> getConfParameterPatternMap() {
	  
	  Map<String, Pattern> patternMap = new HashMap<String, Pattern>();
	  patternMap.put(PARAM_FOLDER, patternFolderAbsPath);
	  patternMap.put(PARAM_FILENAME, patternFilename);
	  patternMap.put(PARAM_TRIGGER, patternFolderCronExpression);
    
	  return patternMap;
	  
  }
}
