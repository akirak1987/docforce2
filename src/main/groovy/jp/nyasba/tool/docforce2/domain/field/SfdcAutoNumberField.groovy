package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 自動採番フィールド
 */
class SfdcAutoNumberField implements SfdcField {
  def fieldXml

  def SfdcAutoNumberField(GPathResult fieldXml){
    this.fieldXml = fieldXml
  }

  @Override
  def String displayLabel(){
    return fieldXml.label
  }

  @Override
  String apiLookupName(){
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
    return ""
  }

  @Override
  String formula() {
    return fieldXml.displayFormat
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
