package jp.nyasba.tool.docforce2.domain.field

import groovy.util.slurpersupport.GPathResult

/**
 * 名前フィールド
 */
class SfdcNameField implements SfdcField{
  SfdcField field

  def SfdcNameField(GPathResult fieldXml){
    this.field = SfdcCustomFieldFactory.create(fieldXml)
  }

  /*
   * API参照名をName固定、必須項目とし、
   * その他はカスタム項目と同じロジックで判断する
   */

  @Override
  def String displayLabel(){
    return this.field.displayLabel()
  }

  @Override
  String apiLookupName() {
    return "Name"
  }

  @Override
  String type() {
    return this.field.type()
  }

  @Override
  String length() {
    return this.field.length()
  }

  @Override
  String discription() {
    return this.field.discription()
  }

  @Override
  String defaultValue(){
    return this.field.defaultValue()
  }

  @Override
  String required() {
    return  "○"
  }

  @Override
  String externalId() {
    return this.field.externalId()
  }

  @Override
  String formula() {
    return this.field.formula()
  }

  @Override
  String helpText() {
    return this.field.helpText()
  }
}
