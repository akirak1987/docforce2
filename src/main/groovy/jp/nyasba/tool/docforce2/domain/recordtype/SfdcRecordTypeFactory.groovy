package jp.nyasba.tool.docforce2.domain.recordtype

import groovy.util.slurpersupport.GPathResult
import jp.nyasba.tool.docforce2.domain.validation.SfdcValidation

class SfdcRecordTypeFactory {
  public static SfdcRecordType create(GPathResult fieldXml){
    return new SfdcRecordType(fieldXml);
  }
}
