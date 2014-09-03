package com.changhong.system.web.excel;

import com.changhong.system.web.facade.dto.AnswerDTO;
import com.changhong.system.web.facade.dto.ExaminationDTO;
import com.changhong.system.web.facade.dto.QuestionDTO;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-7-28
 * Time: 上午9:38
 */
public class ExaminationStatisticExcelView extends AbstractJExcelView {

    private WritableFont FONT_TITLE = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
    private WritableCellFormat CELL_TITLE = new WritableCellFormat(FONT_TITLE);

    private WritableFont FONT_CONTENT = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
    private WritableCellFormat CELL_CONTENT = new WritableCellFormat(FONT_CONTENT);

    private ExaminationDTO examination;

    private List<QuestionDTO> questions;

    public ExaminationStatisticExcelView(ExaminationDTO examination, List<QuestionDTO> questions) throws Exception {
        this.examination = examination;
        this.questions = questions;

        CELL_TITLE.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
        CELL_TITLE.setBackground(Colour.GOLD);

        CELL_CONTENT.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
    }

    @Override
    protected void buildExcelDocument(Map map, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        buildSheetItems(workbook);
    }

    private void buildSheetItems(WritableWorkbook workbook) throws Exception {
        DecimalFormat format = new DecimalFormat("#,##0.0");

        //设置SHEET的名称
        workbook.createSheet("川网问卷调查统计表", 0);
        WritableSheet sheet = workbook.getSheet(0);
        sheet.setRowView(0, 400);

        //设置每一列的宽度
        setColumnWidth(sheet);

        //设置需要MERGE的列和行
        setMergeCell(sheet);

        //设置所有的标题
        setTitles(sheet);

        int j = 2;
        for (QuestionDTO question : questions) {
            sheet.addCell(new Label(0, j, "问题" + question.getSequence() + ":" + question.getTitle(), CELL_TITLE));
            j++;

            for (AnswerDTO answer : question.getAnswers()) {
                if (StringUtils.hasText(answer.getResult())) {
                    sheet.addCell(new Label(0, j, answer.getAnswer(), CELL_CONTENT));
                    double percentage = 0;
                    if (question.getTotalAnswerTimes() > 0) {
                        percentage = answer.getAnswerTimes() * 100 / question.getTotalAnswerTimes();
                    }
                    sheet.addCell(new Label(1, j, "总" + answer.getAnswerTimes() + "次, 占" + format.format(percentage) + "%", CELL_CONTENT));
                    j++;
                }
            }

            j++;
        }
    }

    private void setTitles(WritableSheet sheet) throws WriteException {
        sheet.addCell(new Label(0, 0, "问卷调查:" + examination.getTitle(), CELL_TITLE));
    }

    private void setColumnWidth(WritableSheet sheet) {
        sheet.setColumnView(0, 100);
        sheet.setColumnView(1, 50);
    }

    private void setMergeCell(WritableSheet sheet) throws WriteException {
        sheet.mergeCells(0, 0, 1, 0);
    }
}
