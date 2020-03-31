package jp.nyasba.tool.docforce2.repository.sheet

import jp.nyasba.tool.docforce2.domain.SfdcCustomObject
import jp.nyasba.tool.docforce2.domain.SfdcObject
import jp.nyasba.tool.docforce2.domain.field.SfdcField
import jp.nyasba.tool.docforce2.repository.CellUtil
import jp.nyasba.tool.docforce2.repository.cellstyle.CellStyleUtil
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.PrintSetup
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

/**
 * Excelの「カスタム項目」シートを作成するためのRepository
 */
class CustomFiledSheetRepository {
  def createSheet(Workbook workbook, SfdcCustomObject customObject) {
    CellStyle[] style = [
      CellStyleUtil.normal(workbook),
      CellStyleUtil.alignCenter(workbook)
    ]

    Sheet customFieldSheet = workbook.getSheet("カスタム項目")

    writeRow(customFieldSheet, 2, customObject.NameField(), style)

    List<SfdcField> customFieldList = customObject.fieldList()
    customFieldList.eachWithIndex {
      f, i -> writeRow(customFieldSheet, i + 3, f, style)
    }

    printConfigure(customFieldSheet)
  }

  private writeRow(Sheet sheet, int rowNumber, SfdcField customField, CellStyle... style) {
    println customField.dump()
    int i = 0
    sheet.createRow(rowNumber)
    CellUtil.setValue(sheet, rowNumber, i++, rowNumber-1, style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.displayLabel(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.apiLookupName(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.type(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.length(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.defaultValue(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.formula(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.helpText(), style[0])
    CellUtil.setValue(sheet, rowNumber, i++, customField.required(), style[1])
    CellUtil.setValue(sheet, rowNumber, i++, customField.externalId(), style[1])
    CellUtil.setValue(sheet, rowNumber, i++, customField.discription(), style[0])
  }

  def void printConfigure(Sheet sheet){
    PrintSetup printSetup = sheet.getPrintSetup()
    printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE)
    printSetup.setLandscape(true) //横向き
    printSetup.setScale(50 as short)
  }
}
