package com.infy.idp.plugins.buildsteps

//import java.util.HashMap;
import com.infy.idp.interfaces.IPluginBase;
import org.infy.idp.entities.jobs.IDPJob;
import com.infy.idp.utils.*
import com.infy.idp.creators.*
import com.infy.idp.stages.*
import org.infy.entities.triggerinputs.Build;
import com.infy.idp.customtools.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.*
import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

class Fortify  implements IPluginBase {


    // /*
    //  * This function implements add method of IPluginBase interface
    //  */

     @Override
     public void add(context, IDPJob dataObj) {
         this.addOptions(context, this.performMapping(dataObj))
     }

    // /*
    // * This function implements performMapping method of IPluginBase interface
    // */

    // @Override
     public HashMap<String, String> performMapping(IDPJob dataObj) {
        
         HashMap<String, String> data = new HashMap<String, String>()
         data.put('javaVersion', dataObj.buildInfo.fortifyDetails.javaVersion)
         data.put('dotNetVersion',dataObj.buildInfo.fortifyDetails.dotNetVersion)
         data.put('excludeList', dataObj.buildInfo.fortifyDetails.excludeList)

         String basePath = dataObj.basicInfo.applicationName + '_' + dataObj.basicInfo.pipelineName
         String scanFilePath= 'fortifyxmlreport'
         String technology = dataObj.code.technology 
         String buildServerOS =dataObj.basicInfo.buildServerOS

         data.put('basePath',basePath)
         data.put('scanFilePath',scanFilePath)
         data.put('technology',technology)
         data.put('buildServerOS',buildServerOS)

         return data
     }

     /*
     * This function implements addOptions method of IPluginBase interface
     */

    // @Override
     public void addOptions(context, HashMap<String, String> data) 
     {
        context.with {
            this.addSteps(context,data)
                    }
     }

    
   
    /*
     * This function implements addOptions method of IPluginBase interface
     */
   
    public void addSteps(context,jsonData)
    { 
        context.with{   
			configure { project ->
				project / publishers / 'com.fortify.plugin.jenkins.FortifyPlugin'(plugin: 'fortify@19.1.29') { 
					buildId(jsonData.get('basePath'))
					scanFile(jsonData.get('scanFilePath'))
                    
					runTranslation { 
						translationType(class: 'com.fortify.plugin.jenkins.FortifyPlugin$BasicTranslationBlock') {
                            if(jsonData.get('technology').equalsIgnoreCase('J2EE/Ant') || jsonData.get('technology').equalsIgnoreCase('J2EE/Gradle')  ){
						        appTypeBlock(class: 'com.fortify.plugin.jenkins.FortifyPlugin$BasicJavaTranslationAppTypeBlock'){
                                    javaVersion(jsonData.get('javaVersion'))
                                        sourceFiles('$IDP_WS')
							    }
                            }//tech ends

                            if( jsonData.get('technology').equalsIgnoreCase('angular')  || jsonData.get('technology').equalsIgnoreCase('J2EE/Maven') ||  jsonData.get('technology').equalsIgnoreCase('python') ||  jsonData.get('technology').equalsIgnoreCase('go') || jsonData.get('technology').equalsIgnoreCase('nodeJs')){
							    appTypeBlock(class: 'com.fortify.plugin.jenkins.FortifyPlugin$BasicOtherTranslationAppTypeBlock'){
                                        includesList('$IDP_WS')
							    }
                            }//tech ends

                            if( jsonData.get('technology').equalsIgnoreCase('dotNetCsharp') ){
							    appTypeBlock(class: 'com.fortify.plugin.jenkins.FortifyPlugin$BasicDotNetTranslationAppTypeBlock'){
                                    scanType(class: 'com.fortify.plugin.jenkins.FortifyPlugin$BasicDotNetSourceCodeScanTypeBlock'){
								        dotNetVersion(jsonData.get('dotNetVersion'))
                                            dotNetSrcFiles('$IDP_WS')
                                    }
							    }
                            }//tech ends
                            
							excludeList(jsonData.get('excludeList'))
						}
					}
                    runScan(true)
                    runSCAClean(true)
				}
			}   
        }//contextwith ends
    }
}
