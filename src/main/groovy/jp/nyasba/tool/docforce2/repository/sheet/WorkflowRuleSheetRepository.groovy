package jp.nyasba.tool.docforce2.repository.sheet

import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflow
import jp.nyasba.tool.docforce2.domain.workflow.SfdcWorkflowRule
import jp.nyasba.tool.docforce2.repository.CellUtil
import jp.nyasba.tool.docforce2.repository.cellstyle.CellStyleUtil
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.PrintSetup
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress

/**
 * Excelの「ワークフロールール」シートを作成するためのRepository
 */
class WorkflowRuleSheetRepository {
  def createSheet(Workbook workbook, SfdcWorkflow workflow){
    CellStyle normal = CellStyleUtil.normal(workbook)

    Sheet sheet = workbook.getSheet("ワークフロールール")
    int row = 3
    workflow.workflowRuleList().eachWithIndex{ SfdcWorkflowRule rule, int i ->
      row = singleWorkflowRule(sheet, row, rule, i+1, normal)
    }

    printConfigure(sheet)
  }

  private int singleWorkflowRule(Sheet sheet, int row, SfdcWorkflowRule rule, int index, CellStyle style){
    // アクションが紐づいていないパタン
    if(rule.actionList.size() == 0){
      sheet.createRow(row)
      CellUtil.setValue(sheet, row, 0, index, style)
      CellUtil.setValue(sheet, row, 1, rule.displayLabel, style)
      CellUtil.setValue(sheet, row, 2, rule.evaluateTerm, style)
      CellUtil.setValue(sheet, row, 3, rule.triggerType, style)
      CellUtil.setValue(sheet, row, 4, "なし", style)
      CellUtil.setValue(sheet, row, 5, "", style)
      return row+1
    }

    int i = 0
    rule.actionList.each{
      sheet.createRow(row+i)
      CellUtil.setValue(sheet, row+i, 0, index, style)
      CellUtil.setValue(sheet, row+i, 1, rule.displayLabel, style)
      CellUtil.setValue(sheet, row+i, 2, rule.evaluateTerm, style)
      CellUtil.setValue(sheet, row+i, 3, rule.triggerType, style)
      CellUtil.setValue(sheet, row+i, 4, it.type, style)
      CellUtil.setValue(sheet, row+i, 5, it.name, style)
      i++
    }
    if(i > 1){
      (0..3).each { sheet.addMergedRegion(new CellRangeAddress(row, row+i-1, it, it)) }
    }
    return row+i
  }

  def void printConfigure(Sheet sheet){
    PrintSetup printSetup = sheet.getPrintSetup()
    printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE)
    printSetup.setLandscape(true) //横向き
    printSetup.setScale(60 as short)
  }
}
