package com.msciq.storage.Forecast.service.Impl;

import com.msciq.storage.Forecast.repository.GLAccountRepository;
import com.msciq.storage.Forecast.service.GLAccountService;
import com.msciq.storage.model.ForecastingTemplate;
import com.msciq.storage.model.GLAccount;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class GLAccountServiceImpl implements GLAccountService {

    @Autowired
    private GLAccountRepository glAccountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GLAccount> getAllGLAccountData() {
        return glAccountRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */

    static String SHEET = "GLAccounts";
    @Override
    public List<GLAccount> importGLAccountData(MultipartFile file) throws IOException {

            InputStream fileValue = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(fileValue);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<GLAccount> glAccounts = new ArrayList<GLAccount>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                GLAccount glAccount = new GLAccount();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            glAccount.setGlGroupCodeName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            glAccount.setGlAccount((long) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            glAccount.setAccountDescription(currentCell.getStringCellValue());
                            break;
                        case 3:
                            glAccount.setAccountType(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Timestamp sqlDate = new Timestamp(sdf.parse("2000-01-01").getTime());
                    glAccount.setActiveFromDate(sqlDate);
                    glAccount.setInactiveDate(new Timestamp(sdf.parse("9999-12-31").getTime()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


                glAccounts.add(glAccount);
            }
        glAccountRepository.saveAll(glAccounts);
            workbook.close();
        return glAccounts;
    }

}
