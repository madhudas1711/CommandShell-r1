/*
 * Copyright © 2017 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

// If you change this, make sure to update the exported-packages variable in the pom.xml file
package org.example.plugin;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.ServiceOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;


import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.plugin.PluginConfig;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.action.Action;
import io.cdap.cdap.etl.api.action.ActionContext;







/**
 * Example Action Plugin - This example action plugin can be used as a starting point for
 * developing new Action plugins. For full documentation, please visit:
 * https://docs.cask.co/cdap/4.2.0/en/developer-manual/pipelines/developing-plugins/creating-a-plugin.html
 */
@Plugin(type = Action.PLUGIN_TYPE)
@Name(ExampleAction.PLUGIN_NAME) // <- NOTE: The name of the plugin should match the
                                 //    name of the docs and widget json files.
@Description("This example action plugin should give you a good starting point for building your own plugin.")
public class ExampleAction extends Action {
  // You can use this to log messages for debugging your plugin.
  private static final Logger LOG = LoggerFactory.getLogger(ExampleAction.class);

  /**
   * This plugin name is what will be displayed in the UI when you deploy your plugin.
   * It should also match the name of the Widgets config and the Docs file. For example,
   * there should be files named <project root>/widgets/ExampleAction-action.json and
   * <project root>/docs/ExampleAction-action.md
   */
  public static final String PLUGIN_NAME = "CommandShell";

  /**
   * This variable will hold all the settings that were entered by the user when configuring the plugin.
   * See {@link ExampleActionConfig} class at the bottom of this file for more details.
   */
  private final ExampleActionConfig config;
  

  @VisibleForTesting
  public ExampleAction(ExampleActionConfig config) {
    this.config = config;
  }

  /**
   * This function is executed by the Pipelines framework when the Pipeline is deployed. This
   * is a good place to validate any configuration options the user has entered. If this throws
   * an exception, the Pipeline will not be deployed and the user will be shown the error message.
   */
  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) throws IllegalArgumentException {
    super.configurePipeline(pipelineConfigurer);
    LOG.debug(String.format("Running the 'configurePipeline' method of the %s plugin.", PLUGIN_NAME));
    config.validate();
  }

  /**
   * This is where you add the main logic of the plugin. An Action plugin executes in a single container
   * before or after executing a Spark or MapReduce pipeline. You can add any Java code you'd like here such as
   * validating or moving files, setting additional parameters for the rest of the pipeline, or sending
   * notifications such as Email or external web calls.
   */
  @Override
  public void run(ActionContext context) throws Exception {
    LOG.debug(String.format("Running the 'run' method of the %s plugin.", PLUGIN_NAME));
    // It's a good idea to validate the configuration one last time. This is in case the user
    // entered any macros (E.g. ${file-path}) in the configuration options which can only be
    // validated when the pipeline is executed.
    config.validate();

    // The main logic of your action plugin goes here.

    // This is an example of setting arguments that can be used later by plugins in your Pipeline.
    // This can be useful if you are pulling configuration from an external webservice for example.
    context.getArguments().set("example.action.arg",
                               "This value can be used by other plugins in the Pipeline by " +
                                 "specifying ${example.action.arg}.");
    try{
    	//ProcessBuilder cmd = new ProcessBuilder("sh","-c",shell_output_file_cmd);
    	//Process pcmd = cmd.start();
    	
    	//Path currentRelativePath = Paths.get("");
    	//String s = currentRelativePath.toAbsolutePath().toString();
    	//String filePath_abs = s + "/output.txt";
    	String filePath = new File(System.getProperty("user.home")) + "/output.txt";
    	//String filePath = this.config.getTempLocation() + "output.txt";
    	
    	
    	FileWriter fw = new FileWriter(filePath,true); //append mode=true
    	BufferedWriter output = new BufferedWriter(fw);    	 
    	
    	//String gsutil_cp_cmd = "exec > $(pwd)/output.txt 2>&1;export HOME=$(pwd);" + this.config.getCommand() + ";gsutil cp ./output.txt gs://" + this.config.getTempLocation() + "/;";
    	//String cp_cmd = "exec > $(pwd)/output.txt 2>&1;export HOME=$(pwd);" + this.config.getCommand() + ";cp ./output.txt " + this.config.getTempLocation() + ";";
    	
    	String cmd = this.config.getCommand() + ";";
    	//ProcessBuilder pb = new ProcessBuilder("sh","-c",cmd);
    	//pb.directory(new File(System.getProperty("user.home")));
		Process p = Runtime.getRuntime().exec(new String[]{"sh","-c",cmd});
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    	BufferedReader stderror = new BufferedReader(new InputStreamReader(p.getErrorStream())); 
    	String line;
    	while ((line = reader.readLine()) != null){
    		output.write(line);
    		output.newLine();    		
    	} 
    	output.write(";Error = ");
    	//output.write(";filePath_abs = " + filePath_abs);
    	//output.write(";filePath_user = " + filePath_user);
    	while ((line = stderror.readLine()) != null){
    		output.write(line);
    		output.newLine();
    	}    		
		output.close();     	
    	
    	if (Pattern.matches("^/[a-zA-Z0-9/-_]*", this.config.getTempLocation())){
    		String cp_cmd = "cp " + filePath + " " + this.config.getTempLocation() + ";";
    		Runtime.getRuntime().exec(new String[]{"sh","-c",cp_cmd});
    		
    		LOG.debug(String.format("File copied to GCS storage  %s successfully.", this.config.getCommand()));
    	}else {
    		//Process Pcp = Runtime.getRuntime().exec(new String[]{"sh","-c",gsutil_cp_cmd});
    		
    		String projectId = ServiceOptions.getDefaultProjectId();
        	String bucketName = this.config.getTempLocation();
        	String objectName = "output.txt";
        	
        	//Charset cs = Charset.forName("UTF-8"); 
        	//File outpt = new File(filePath);
        	
        	Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath))); 
    		
    		LOG.debug(String.format("File copied to file storage  %s successfully.", this.config.getCommand()));    		
    	}     	
    	
    }
    catch(IOException e){
    	e.getStackTrace();
    }
  }

  /**
   * The config class for {@link ExampleAction} that contains all properties that need to be filled in by
   * the user when building a Pipeline.
   */
  public static class ExampleActionConfig extends PluginConfig {

    @Macro // <-- Optional annotation that enables the user to enter Macros into the value of the config
           // This means the value of this option might not be known at deploy time.
    @Description("This is the description for the config option that will be displayed in the UI when the " +
                  "user hovers over the 'info' icon.")
    @Nullable // <-- Indicates that the config is Optional.
    private String exampleConfigOption;
    @Macro
    @Description("Write your shell commands here with ; separation")    
    private String CommandConfigOption;
    @Macro
    @Description("Put either the temporary GCS bucket or the local temporary storage dir")    
    private String tempLocationConfigOption;

    @VisibleForTesting
    public ExampleActionConfig(@Nullable String exampleConfigOption, String CommandConfigOption, String tempLocationConfigOption) {
      this.exampleConfigOption = exampleConfigOption;
      this.CommandConfigOption = CommandConfigOption;
      this.tempLocationConfigOption = tempLocationConfigOption;
      //ExampleActionConfig.CommandConfigOption = CommandConfigOption;
      //this.setTempLocationConfigOption(tempLocationConfigOption);
    }
    
    public ExampleActionConfig(){};
    
    public String getCommand() {
    	return CommandConfigOption;
    }
    
    public String getTempLocation(){
    	return tempLocationConfigOption;
    }
    public void setCommand( String CommandConfigOption) {
    	this.CommandConfigOption = CommandConfigOption;
    }
    
    public void setTempLocation(String tempLocationConfigOption){
    	this.tempLocationConfigOption = tempLocationConfigOption;
    }

    /**
     * You can leverage this function to validate the configure options entered by the user.
     */
    public void validate() throws IllegalArgumentException {
      // The containsMacro function can be used to check if there is a macro in the config option.
      // At runtime, the containsMacro function will always return false.
      if (!containsMacro("exampleConfigOption") && !Strings.isNullOrEmpty(exampleConfigOption)) {
        if (exampleConfigOption.contains("test")) {
          throw new IllegalArgumentException("The config value cannot contain the word 'test' for some reason.");
        }
      }
      if (!containsMacro("CommandConfigOption") && !Strings.isNullOrEmpty(CommandConfigOption)) {
          if (CommandConfigOption.contains("test")) {
            throw new IllegalArgumentException("The config value cannot contain the word 'test' for some reason.");
          }
        }
      if (!containsMacro("tempLocationConfigOption") && !Strings.isNullOrEmpty(tempLocationConfigOption)) {
          if (tempLocationConfigOption.contains("test")) {
            throw new IllegalArgumentException("The config value cannot contain the word 'test' for some reason.");
          }
        }
    }
	
  }
}
