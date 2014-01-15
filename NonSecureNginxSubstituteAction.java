import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class NonSecureNginxSubstituteAction
{
  public static void main(String[] args) throws Exception{
	  
	  //Replacing contents in worker.properties file
	  File workerFile = new File(args[0]+"\\conf\\pam-server_template.conf");	
	  BufferedReader reader1 = new BufferedReader(new FileReader(workerFile));
	  StringBuffer buffer1 = new StringBuffer();
	  String line1;
	  while((line1 = reader1.readLine()) != null) {
	      buffer1.append(line1);
	      buffer1.append("\r\n");
	  }
	  reader1.close();
	  Map<String, String> ReplacementMap = new HashMap<String, String>();
	  String numbofNodes = "";
	  if(args.length>3){
		  ReplacementMap.put("Node1HostName", args[2]);	  
	  	  ReplacementMap.put("workerNodename1", args[3]);
	  	  numbofNodes=args[3];
	  }
	  if(args.length>5){
		  ReplacementMap.put("Node2HostName", args[4]);
	  	  ReplacementMap.put("workerNodename2", args[5]);
	  	  numbofNodes=numbofNodes+","+args[5];
	  }
	  if(args.length>7){
		  ReplacementMap.put("Node3HostName", args[6]);
		  ReplacementMap.put("workerNodename3", args[7]);
		  numbofNodes=numbofNodes+","+args[7];
	  }
	  if(args.length>9){
		  ReplacementMap.put("Node4HostName", args[8]);
		  ReplacementMap.put("workerNodename4", args[9]);
		  numbofNodes=numbofNodes+","+args[9];
	  }

	  String toWrite = buffer1.toString();
	  toWrite = toWrite.replaceAll("NumberofNodes",numbofNodes);
	  for (Map.Entry<String, String> entry : ReplacementMap.entrySet()) {
	      toWrite = toWrite.replaceAll(entry.getKey(), entry.getValue());
	  }

	  FileWriter writer1 = new FileWriter(args[0]+"\\conf\\workers.properties");
	  writer1.write(toWrite);
	  writer1.close();	  
	  
	  //Replacing port in httpd.conf file
	  File confFile = new File(args[0]+"\\conf\\httpd_template.conf");
	  BufferedReader reader2 = new BufferedReader(new FileReader(confFile));
	  StringBuffer buffer2 = new StringBuffer();
	  String line2;
	  while((line2 = reader2.readLine()) != null) {
	      buffer2.append(line2);
	      buffer2.append("\r\n");
	  }
	  reader2.close();
	  String httpdContent = buffer2.toString();
	  httpdContent = httpdContent.replaceAll("loadBalancerPort", args[1]);
	  FileWriter writer2 = new FileWriter(args[0]+"\\conf\\httpd.conf");
	  writer2.write(httpdContent);
	  writer2.close();	
	  

	 
  }
}
