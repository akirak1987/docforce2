package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 電話番号フィールド
 */
class SfdcPhoneField implements SfdcField {
  def fieldXml

  def SfdcPhoneField(GPathResult fieldXml){
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
    return "-"
  }

  @Override
  String defaultValue(){
    return ""
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
  String description() {
    return fieldXml.description
  }
}
