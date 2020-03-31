package jp.nyasba.tool.docforce2.domain.workflow
/**
 * Workflowメタデータの読み取り結果（XML）
 */
class SfdcWorkflow {
  def isExist
  def xml

  def SfdcWorkflow(String rawXml){
    isExist = true
    def slurper = new XmlSlurper()
    xml = slurper.parseText(rawXml)
  }

  // ワークフローが定義されていない場合の入力値
  def SfdcWorkflow(){
    isExist = false
  }

  def List<SfdcWorkflowFieldUpdate> columnAutoUpdateList(){
    if(isExist) {
      return xml.fieldUpdates.collect{ new SfdcWorkflowFieldUpdate(it) }
    }
    return []
  }

  def List<SfdcWorkflowMailAlert> mailAlertList(){
    if(isExist) {
      return xml.alerts.collect{ new SfdcWorkflowMailAlert(it) }
    }
    return []
  }

  def List<SfdcWorkflowRule> workflowRuleList(){
    if(isExist) {
      return xml.rules.collect{ new SfdcWorkflowRule(it) }
    }
    return []
  }
}
