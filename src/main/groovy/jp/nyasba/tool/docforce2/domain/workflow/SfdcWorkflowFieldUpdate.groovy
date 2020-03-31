package jp.nyasba.tool.docforce2.domain.workflow

import groovy.util.slurpersupport.NodeChild

/**
 * 項目自動更新ドメイン
 */
class SfdcWorkflowFieldUpdate {
  def displayLabel
  def apiLookupName
  def targetField
  MappingType mapping
  def mappingDetail

  public SfdcWorkflowFieldUpdate(NodeChild xml){
    this.displayLabel = xml.getProperty("name")
    this.apiLookupName = xml.fullName
    this.targetField =  xml.field
    this.mapping = MappingType.convert(xml.operation as String)

    switch (this.mapping){
      case MappingType.LITERAL : this.mappingDetail = xml.literalValue; break
      case MappingType.FORMULA : this.mappingDetail = xml.formula; break
      default: this.mappingDetail = ""
    }
  }

  enum MappingType{
    LITERAL("Literal", "値"),
    FORMULA("Formula", "数式"),
    NULL("Null", "Null更新"),
    ERROR("Error", "")

    String xmlValue
    String outputValue

    public MappingType(String xmlValue, String outputValue){
      this.xmlValue = xmlValue
      this.outputValue = outputValue
    }

    public static MappingType convert(String xmlValue){
      for(MappingType e : values()){
        if(e.xmlValue == xmlValue){
          return e
        }
      }
      return ERROR
    }
  }
}
