package jp.nyasba.tool.docforce2.domain.field

interface SfdcField {
  def String displayLabel()
  def String apiLookupName()
  def String type()
  def String length()
  def String defaultValue()
  def String formula()
  def String helpText()
  def String required()
  def String externalId()
  def String description()
}
