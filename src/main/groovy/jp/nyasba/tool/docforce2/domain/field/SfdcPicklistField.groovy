package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 選択リストフィールド
 */
class SfdcPicklistField implements SfdcField {
  def fieldXml

  def SfdcPicklistField(GPathResult fieldXml) {
    this.fieldXml = fieldXml
  }

  @Override
  def String displayLabel() {
    return fieldXml.label
  }

  @Override
  String apiLookupName() {
    return fieldXml.fullName
  }

  @Override
  String type() {
    if(fieldXml.picklist.restrictedPicklist == "true"){
      return  "${fieldXml.type}\n(Restricted)"
    }
    return fieldXml.type
  }

  @Override
  String length() {
    return "-"
  }

  @Override
  String defaultValue(){
    // グローバル選択リストを使う場合
    if(fieldXml.globalPicklist.text() != ""){
      return "グローバル選択リスト「${fieldXml.globalPicklist}」を利用"
    }

    // 通常の選択リスト
    return fieldXml.picklist.picklistValues
            .collect { new SfdcPicklistValue(it).toString() }
            .join("\n")
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
    return fieldXml.externalId == "true" ? "○" : ""
  }

  @Override
  String description() {
    return fieldXml.description
  }

  public class SfdcPicklistValue{
    def xml

    def SfdcPicklistValue(GPathResult xml) {
      this.xml = xml
    }

    public String choiceName(){
      return xml.fullName
    }
    public boolean isDefault(){
      return (xml.default == "true")
    }

    public String toString(){
      if (isDefault()){
        return choiceName() + " …default"
      }
      return choiceName()
    }
  }
}
