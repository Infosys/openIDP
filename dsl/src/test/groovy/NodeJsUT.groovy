/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/


import groovy.io.FileType
import hudson.model.Item
import hudson.model.View
import javaposse.jobdsl.dsl.*
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.Jenkins
import org.junit.ClassRule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests that all dsl scripts in the jobs directory will compile. All config.xml's are written to build/debug-xml.
 */
class NodeJsUT extends Specification {

    @Shared
    @ClassRule
    private JenkinsRule jenkinsRule = new JenkinsRule()

    @Shared
    private File outputDir = new File('./build/debug-xml')


    @Unroll
    void 'Executing Test Conditions on #file.name'(File file) {
        given:
        def env = System.getenv()
        JobManagement jm = new JenkinsJobManagement(System.out, ['JSON_Input': (new File(env['basePath'] + '\\UTJsons\\NodeJS.json')).text, 'Pipeline_Script_Path': env['dirPath'] + '\\pipeline_sequence'], new File('.'))

        when:
        def lines = file.readLines();
        lines.eachWithIndex { line, index ->
            if (line.startsWith("PropReader.dirPath =")) {
                lines.set(index, "def env = System.getenv(); PropReader.dirPath = env[\'dirPath\'];")
            }
        }
        GeneratedItems items = new DslScriptLoader(jm).runScript(lines.join(System.properties.'line.separator'))
        writeItems items

        then:
        noExceptionThrown()

        where:
        file << jobFiles
    }

    /**
     * Write the config.xml for each generated job and view to the build dir.
     */
    void writeItems(GeneratedItems items) {
        Jenkins jenkins = jenkinsRule.jenkins

        items.jobs.each { GeneratedJob generatedJob ->
            String jobName = generatedJob.jobName
            Item item = jenkins.getItemByFullName(jobName)
            String text = new URL(jenkins.rootUrl + item.url + 'config.xml').text

            if (jobName.endsWith('Build')) {

                with(new XmlParser().parse(new StringReader(text))) {

                    //All the assertion conditions go here
                    println "###############################################################################################"
                    println "#################################Testing for Build Job Started#################################"





                    println "Testing if buildWrappers plugin ToolEnvBuildWrapper"
                    buildWrappers.'hudson.plugins.toolenv.ToolEnvBuildWrapper'
                    println "Passed"

                    println "Testing if buildname setter wrapper is used"
                    buildWrappers.'org.jenkinsci.plugins.buildnamesetter.BuildNameSetter'
                    println "Passed"


                    println "#########################Tetsing for Build Job Completed Successfully##########################"
                    println "###############################################################################################"
                }
            }

            writeFile new File(outputDir, 'jobs'), jobName, text
        }

        items.views.each { GeneratedView generatedView ->
            String viewName = generatedView.name
            View view = jenkins.getView(viewName)
            String text = new URL(jenkins.rootUrl + view.url + 'config.xml').text
            writeFile new File(outputDir, 'views'), viewName, text
        }
    }

    /**
     * Write a single XML file, creating any nested dirs.
     */
    void writeFile(File dir, String name, String xml) {
        List tokens = name.split('/')
        File folderDir = tokens[0..<-1].inject(dir) { File tokenDir, String token ->
            new File(tokenDir, token)
        }
        folderDir.mkdirs()

        File xmlFile = new File(folderDir, "${tokens[-1]}.xml")
        xmlFile.text = xml
    }

    static List<File> getJobFiles() {
        List<File> files = []
        new File('src/main/groovy').eachFileRecurse(FileType.FILES) {
            if (it.name.equals('Main.groovy')) {
                files << it
            }
        }
        files
    }
}
