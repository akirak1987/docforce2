package jp.nyasba.tool.docforce2.domain.workflow

import groovy.util.slurpersupport.NodeChild
import jp.nyasba.tool.docforce2.domain.folder.SfdcFolder

/**
 * メールアラートドメイン
 */
class SfdcWorkflowMailAlert {
  String displayLabel
  String apiLookupName
  String mailTemplate
  String sender
  String receiver

  public SfdcWorkflowMailAlert(NodeChild xml){
    this.displayLabel = xml.description as String
    this.apiLookupName = xml.fullName as String
    this.mailTemplate = xml.template as String
    this.sender = xml.senderType as String
    this.receiver = xml.recipients.collect{
      if(it.type == "owner") { "所有者"}
      else if (it.type == "user") { "ユーザ:${it.recipient}"}
      else "${it.type}"
    }.join("\n")
  }

  public String senderOutputValue(){
    return this.sender.replace("CurrentUser", "現在のユーザ")
  }

  public String mailTemplateOutputValue(){
    return SfdcFolder.convert(this.mailTemplate)
  }
}
