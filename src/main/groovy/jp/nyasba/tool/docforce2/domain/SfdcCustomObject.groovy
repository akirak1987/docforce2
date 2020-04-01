package jp.nyasba.tool.docforce2.domain

import jp.nyasba.tool.docforce2.domain.field.SfdcField
import jp.nyasba.tool.docforce2.domain.field.SfdcCustomFieldFactory
import jp.nyasba.tool.docforce2.domain.field.SfdcNameField
import jp.nyasba.tool.docforce2.domain.recordtype.SfdcRecordType
import jp.nyasba.tool.docforce2.domain.recordtype.SfdcRecordTypeFactory
import jp.nyasba.tool.docforce2.domain.validation.SfdcValidation
import jp.nyasba.tool.docforce2.domain.validation.SfdcValidationFactory

/**
 * CustomObjectメタデータの読み取り結果（XML）
 */
class SfdcCustomObject {
  def fileName
  def xml

  def SfdcCustomObject(String fileName, String rawXml){
    this.fileName = fileName
    def slurper = new XmlSlurper()
    xml = slurper.parseText(rawXml)
  }

  def String title(){
   return "${displayLabel()}(${apiLookupName()})"
  }

  def String displayLabel(){
    return xml.label
  }

  def String apiLookupName(){
    return fileName.tokenize('.').get(0)
  }

  def String description(){
    return xml.description
  }

  def SfdcNameField NameField(){
    return new SfdcNameField(xml.nameField)
  }

  def List<SfdcRecordType> recordTypeList(){
    return xml.recordTypes.collect { SfdcRecordTypeFactory.create(it) }
  }

  def List<SfdcField> fieldList() {
    return xml.fields.collect { SfdcCustomFieldFactory.create(it) }
  }

  def List<SfdcValidation> validationRuleList() {
    return xml.validationRules.collect { SfdcValidationFactory.create(it) }
  }
}
