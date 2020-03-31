package jp.nyasba.tool.docforce2.domain.recordtype

import groovy.util.slurpersupport.GPathResult

/**
 * レコードタイプ
 */
class SfdcRecordType {
  def fieldXml
  def SfdcRecordType(GPathResult fieldXml){
    this.fieldXml = fieldXml
  }

  def String displayLabel(){
    return fieldXml.label
  }

  def String apiLookupName(){
    return fieldXml.fullName
  }

  def boolean isActive(){
    return fieldXml.active == "true"
  }

  def String discription(){
    return fieldXml.description
  }
}
