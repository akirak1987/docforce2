package jp.nyasba.tool.docforce2.repository.sheet

import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflow
import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflowFieldUpdate
import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflowMailAlert
import jp.nyasba.tool.docforce2.repository.CellUtil
import jp.nyasba.tool.docforce2.repository.cellstyle.CellStyleUtil
import org.apache.poi.ss.usermodel.*

/**
 * Excelの「ワークフローアクション」シートを作成するためのRepository
 */
class WorkflowActionSheetRepository {
  CellStyle sectionTitle
  CellStyle normal
  CellStyle tableHeader

  def createSheet(Workbook workbook, SfdcWorkflow workflow){
    // 利用するスタイルを作成
    sectionTitle = CellStyleUtil.sectionTitle(workbook)
    normal = CellStyleUtil.normal(workbook)
    tableHeader = CellStyleUtil.tableHeader(workbook)

    Sheet sheet = workbook.getSheet("ワークフローアクション")
    int row = 0

    row = columnAutoUpdate(sheet, row, workflow.columnAutoUpdateList()) + 2
    row = mailAlert(sheet, row, workflow.mailAlertList()) + 2

    printConfigure(sheet)
  }

  private int columnAutoUpdate(Sheet sheet, int row, List<SfdcWorkflowFieldUpdate> list){
    // タイトル行
    CellUtil.setValueWithCreateRecord(sheet, row++, 0, "■項目自動更新", sectionTitle, 24 as float )

    // 見出し行
    Row r = sheet.createRow(row)
    CellUtil.setValue(sheet, row, 0, "No", tableHeader)
    CellUtil.setValue(sheet, row, 1, "ラベル", tableHeader)
    CellUtil.setValue(sheet, row, 2, "API参照名", tableHeader)
    CellUtil.setValue(sheet, row, 3, "対象項目", tableHeader)
    CellUtil.setValue(sheet, row, 4, "マッピング種別", tableHeader)
    CellUtil.setValue(sheet, row, 5, "マッピング", tableHeader)
    row++

    // データ行
    list.eachWithIndex{ SfdcWorkflowFieldUpdate fieldUpdate, int i ->
      Row r1 = sheet.createRow(row)
      CellUtil.setValue(sheet, row, 0, i+1, normal)
      CellUtil.setValue(sheet, row, 1, fieldUpdate.displayLabel, normal)
      CellUtil.setValue(sheet, row, 2, fieldUpdate.apiLookupName, normal)
      CellUtil.setValue(sheet, row, 3, fieldUpdate.targetField, normal)
      CellUtil.setValue(sheet, row, 4, fieldUpdate.mapping.outputValue, normal)
      CellUtil.setValue(sheet, row, 5, fieldUpdate.mappingDetail, normal)
      row++
    }

    if(list.isEmpty()){
      Row r1 = sheet.createRow(row)
      CellUtil.setValue(sheet, row, 0, "", normal)
      CellUtil.setValue(sheet, row, 1, "", normal)
      CellUtil.setValue(sheet, row, 2, "", normal)
      CellUtil.setValue(sheet, row, 3, "", normal)
      CellUtil.setValue(sheet, row, 4, "", normal)
      CellUtil.setValue(sheet, row, 5, "", normal)
      row++
    }
    return row
  }

  private int mailAlert(Sheet sheet, int row, List<SfdcWorkflowMailAlert> list){
    // タイトル行
    CellUtil.setValueWithCreateRecord(sheet, row++, 0, "■メールアラート", sectionTitle, 24 as float )

    // 見出し行
    Row r = sheet.createRow(row)
    CellUtil.setValue(sheet, row, 0, "No", tableHeader)
    CellUtil.setValue(sheet, row, 1, "ラベル", tableHeader)
    CellUtil.setValue(sheet, row, 2, "API参照名", tableHeader)
    CellUtil.setValue(sheet, row, 3, "メールテンプレート", tableHeader)
    CellUtil.setValue(sheet, row, 4, "差出人", tableHeader)
    CellUtil.setValue(sheet, row, 5, "受信者", tableHeader)
    row++

    // データ行
    list.eachWithIndex{ SfdcWorkflowMailAlert alert, int i ->
      Row r1 = sheet.createRow(row)
      CellUtil.setValue(sheet, row, 0, i+1, normal)
      CellUtil.setValue(sheet, row, 1, alert.displayLabel, normal)
      CellUtil.setValue(sheet, row, 2, alert.apiLookupName, normal)
      CellUtil.setValue(sheet, row, 3, alert.mailTemplateOutputValue(), normal)
      CellUtil.setValue(sheet, row, 4, alert.senderOutputValue(), normal)
      CellUtil.setValue(sheet, row, 5, alert.receiver, normal)
      row++
    }

    if(list.isEmpty()){
      Row r1 = sheet.createRow(row)
      CellUtil.setValue(sheet, row, 0, "", normal)
      CellUtil.setValue(sheet, row, 1, "", normal)
      CellUtil.setValue(sheet, row, 2, "", normal)
      CellUtil.setValue(sheet, row, 3, "", normal)
      CellUtil.setValue(sheet, row, 4, "", normal)
      CellUtil.setValue(sheet, row, 5, "", normal)
      row++
    }
    return row
  }

  def void printConfigure(Sheet sheet){
    PrintSetup printSetup = sheet.getPrintSetup()
    printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE)
    printSetup.setLandscape(true) //横向き
    printSetup.setScale(60 as short)
  }
}
