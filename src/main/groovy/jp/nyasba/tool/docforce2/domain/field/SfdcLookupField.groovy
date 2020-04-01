package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult
import jp.nyasba.tool.docforce2.domain.condition.SfdcOperation

/**
 * 参照関係フィールド
 */
class SfdcLookupField implements SfdcField {
  def fieldXml

  def SfdcLookupField(GPathResult fieldXml){
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
    return "${fieldXml.type}(${fieldXml.referenceTo})"
  }

  @Override
  String length() {
    return "-"
  }

  @Override
  String defaultValue(){
    return filter()
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
    return fieldXml.required == "true" ? "○" : ""
  }

  @Override
  String externalId() {
    return "" // 外部IDにはできない
  }

  @Override
  String description() {
    return fieldXml.description
  }

  def String filter(){
    if(fieldXml.lookupFilter == null || fieldXml.lookupFilter.active != "true"){
      return ""
    }
    def filterItemMsg = fieldXml.lookupFilter.filterItems.collect { "${it.field} ${SfdcOperation.convert(it.operation)} ${it.value}${it.valueField}" }.join("\n")
    return "[filter]\n" + filterItemMsg
  }
}
