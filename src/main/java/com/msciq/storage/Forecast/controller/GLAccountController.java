package com.msciq.storage.Forecast.controller;

import com.msciq.storage.Forecast.service.GLAccountService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.GLAccount;
import com.msciq.storage.model.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/glaccount")
@Validated
public class GLAccountController {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Autowired
    GLAccountService glAccountService;

    /**
     * Gets all GL Account Data.
     *
     * @return List of GLAccount entity
     * @author mkseenu
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Fun_Master_Data:READ')")
    public SuccessResponse<List<GLAccount>> getAllForecastingTemplates() {
        List<GLAccount> glAccounts = glAccountService.getAllGLAccountData();
        return new SuccessResponse<List<GLAccount>>(String.format(SuccessMessage.SUCCESS, Constants.TEMPLATE)
                , null, Collections.singletonList(glAccounts), HttpStatus.OK);
    }

    /**
     * This method is used to import a new GLAccount data.
     *
     * @param file file
     * @return GLAccount Entity.
     * @author mkseenu
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('Fun_Master_Data:CREATE')")
    public SuccessResponse<List<GLAccount>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (!TYPE.equals(file.getContentType())) {
            return new SuccessResponse<>(String.format(ErrorMessage.ERROR_GL_ACCOUNT_INVALID,Constants.GLACCOUNT),null,null,HttpStatus.BAD_REQUEST);
        }

        return new SuccessResponse<List<GLAccount>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.GLACCOUNT),null,
                        Collections.singletonList(glAccountService.importGLAccountData(file)),
                        HttpStatus.CREATED);
    }



}
