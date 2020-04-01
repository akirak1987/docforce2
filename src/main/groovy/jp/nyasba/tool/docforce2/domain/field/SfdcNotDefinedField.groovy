package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 未定義フィールド
 */
class SfdcNotDefinedField implements SfdcField {
  def fieldXml

  def SfdcNotDefinedField(GPathResult fieldXml){
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
    return "${fieldXml.type}(未定義)"
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
    return fieldXml.formula
  }

  @Override
  String helpText() {
    return fieldXml.inlineHelpText
  }

  @Override
  String required() {
    return fieldXml.required == "true" ? "○" : ""
  }

  @Override
  String externalId() {
    return fieldXml.externalId == "true" ? "○" : ""
  }

  @Override
  String description() {
    return fieldXml.description
  }
}
