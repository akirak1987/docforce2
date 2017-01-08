package jp.nyasba.tool.docforce2.controller

import jp.nyasba.tool.docforce2.domain.SfdcCustomObject
import jp.nyasba.tool.docforce2.domain.approvalprocess.SfdcApprovalProcess
import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflow
import jp.nyasba.tool.docforce2.repository.ObjectDesignExcelBookRepository
import org.yaml.snakeyaml.Yaml

import java.nio.file.Paths

/**
 * エクセルを作成するためのエンドポイントとなるController
 */
class SfdcGenerateExcelController {
    
    static void main(String[] args){

        def yamlPath = Paths.get("C:/dev/sfdc/docforce2/input/setting.yml")
        def yaml = new Yaml().load(yamlPath.text)

        String inputBaseDir = getInputBaseDir(yaml)
        String outputDir = getOutputDir(yaml)
    
        yaml.resources.collect{ createResouceDocument(inputBaseDir, outputDir, it) }
    }
    
    private static String getInputBaseDir(def yaml){
        assert yaml.inputBaseDir : "'inputBaseDir' parameter is not set"
        String inputBaseDir = yaml.inputBaseDir
        if(!inputBaseDir.endsWith("/")){
            inputBaseDir = inputBaseDir + "/"
        }
        return inputBaseDir
    }
    
    private static String getOutputDir(def yaml){
        return yaml.outputDir ?: "output"
    }
    
    private static void createResouceDocument(String inputBaseDir, String outputDir, def resourceYaml){
        SfdcCustomObject object = createObject(inputBaseDir, resourceYaml.resource?.object)
        List<SfdcApprovalProcess> approvalProcessList = createApprovalProcessList(inputBaseDir, resourceYaml.resource?.approvalProcesses)
        SfdcWorkflow workflow = createWorkflow(inputBaseDir, resourceYaml.resource?.workflow)
    
        new ObjectDesignExcelBookRepository(outputDir).save(object, approvalProcessList, workflow)
    }
    
    private static SfdcCustomObject createObject(String inputBaseDir, String param){
        assert param : "'object' parameter is not set"
        def xmlFile = getPathIfExists(inputBaseDir + param)
        def fileName = xmlFile.getName().find('[^/]*$')
        return new SfdcCustomObject(fileName, xmlFile.text)
    }
    
    private static List<SfdcApprovalProcess> createApprovalProcessList(String inputBaseDir, def paramList){
        if(paramList == null || !(paramList instanceof List)){
            return []
        }
        return paramList.collect{
            def xmlFile = getPathIfExists(inputBaseDir + it)
            def fileName = xmlFile.getName().find('[^/]*$')
            return new SfdcApprovalProcess(fileName, xmlFile.text)
        }
    }
    
    private static SfdcWorkflow createWorkflow(String inputBaseDir, String param){
        assert param : "'workflow' parameter is not set"
        def xmlFile = getPathIfExists(inputBaseDir + param)
        return new SfdcWorkflow(xmlFile.text)
    }
    
    
    private static File getPathIfExists(String stringPath){
        File file = new File(stringPath)
        assert file.exists() : "file not found : ${file.getPath()}"
        return file
    }
}
