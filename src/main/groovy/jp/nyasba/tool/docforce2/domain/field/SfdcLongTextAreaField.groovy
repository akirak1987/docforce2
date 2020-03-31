package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * ロングテキストエリアフィールド
 */
class SfdcLongTextAreaField implements SfdcField {
  def fieldXml

  def SfdcLongTextAreaField(GPathResult fieldXml){
    this.fieldXml = fieldXml
  }

  @Override
  def String displayLabel(){
    return fieldXml.label
  }

  @Override
  String apiLookupName() {
    return fieldXml.fullName
  }

  @Override
  String type() {
    return fieldXml.type
  }

  @Override
  String length() {
    return fieldXml.length
  }

  @Override
  String defaultValue(){
    return fieldXml.defaultValue
  }

  @Override
  String formula() {
    return "" // 数式は設定できない
  }

  @Override
  String helpText() {
    return fieldXml.inlineHelpText
  }

  @Override
  String required() {
    return  fieldXml.required == "true" ? "○" : ""
  }

  @Override
  String externalId() {
    return "" // 外部IDにはできない
  }

  @Override
  String discription() {
    return fieldXml.description
  }
}
