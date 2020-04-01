package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 数値フィールド
 */
class SfdcNumberField implements SfdcField {
  def fieldXml

  def SfdcNumberField(GPathResult fieldXml){
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
    return "${fieldXml.precision},${fieldXml.scale}"
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
