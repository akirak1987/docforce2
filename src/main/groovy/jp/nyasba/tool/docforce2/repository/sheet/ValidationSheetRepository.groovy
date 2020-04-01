package jp.nyasba.tool.docforce2.repository.sheet

import jp.nyasba.tool.docforce2.domain.SfdcCustomObject
import jp.nyasba.tool.docforce2.domain.SfdcObject
import jp.nyasba.tool.docforce2.domain.SfdcObject
import jp.nyasba.tool.docforce2.domain.validation.SfdcValidation
import jp.nyasba.tool.docforce2.repository.CellUtil
import jp.nyasba.tool.docforce2.repository.cellstyle.CellStyleUtil
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.PrintSetup
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

/**
 * Excelの「入力規則」シートを作成するためのRepository
 */
class ValidationSheetRepository {
  def createSheet(Workbook workbook, SfdcCustomObject customObject){
    CellStyle normal = CellStyleUtil.normal(workbook)
    CellStyle inactive = CellStyleUtil.inactive(workbook)

    Sheet validationSheet = workbook.getSheet("入力規則")

    List<SfdcValidation> validationList = customObject.validationRuleList()
    validationList.eachWithIndex {
      v, i ->
        if(v.isActive()){
          writeRow(validationSheet, i+2, v, normal)
        } else {
          writeRow(validationSheet, i+2, v, inactive)
        }
    }

    printConfigure(validationSheet)
  }

  private writeRow(Sheet sheet, int rowNumber, SfdcValidation validation, CellStyle style){
    println validation.dump()
    sheet.createRow(rowNumber)
    CellUtil.setValue(sheet, rowNumber, 0, rowNumber-1, style)
    CellUtil.setValue(sheet, rowNumber, 1, validation.fullName(), style)
    CellUtil.setValue(sheet, rowNumber, 2, validation.errorMessage(), style)
    CellUtil.setValue(sheet, rowNumber, 3, validation.formula(), style)
    CellUtil.setValue(sheet, rowNumber, 4, validation.description(), style)
  }

  def void printConfigure(Sheet sheet){
    PrintSetup printSetup = sheet.getPrintSetup()
    printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE)
    printSetup.setLandscape(true) //横向き
    printSetup.setScale(60 as short)
  }
}
