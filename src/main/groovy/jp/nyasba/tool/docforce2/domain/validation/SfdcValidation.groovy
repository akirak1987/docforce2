package jp.nyasba.tool.docforce2.domain.validation

import groovy.util.slurpersupport.GPathResult

/**
 * 入力規則
 */
class SfdcValidation {
  def fieldXml

  def SfdcValidation(GPathResult fieldXml){
    this.fieldXml = fieldXml
  }

  def String fullName(){
    return fieldXml.fullName
  }

  def boolean isActive(){
    return fieldXml.active == "true"
  }

  def String discription(){
    return fieldXml.description
  }

  def String errorMessage(){
    return fieldXml.errorMessage
  }

  def String formula(){
    return fieldXml.errorConditionFormula
  }
}
