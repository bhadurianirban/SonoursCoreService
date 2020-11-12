/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonorus.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hedwig.cloud.client.DataConnClient;
import org.hedwig.cloud.dto.HedwigAuthCredentials;
import org.hedwig.cloud.dto.DataConnDTO;
import org.hedwig.cloud.response.HedwigResponseCode;

import org.hedwig.cms.dto.CMSDTO;
import org.sonorus.core.util.DatabaseConnection;

/**
 *
 * @author bhaduri
 */
public class JSONParseAndDBConn {
    public static String getDTOJSON(Object DTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String responseTermDTOJSON = objectMapper.writeValueAsString(DTO);
            return responseTermDTOJSON;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JSONParseAndDBConn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Object authCreateDB(String termDTOJSON, Class valueType) {

        ObjectMapper objectMapper = new ObjectMapper();
        //String responseTermDTOJSON;
        try {
            Object DTO = objectMapper.readValue(termDTOJSON, valueType);
            CMSDTO cmsdto = (CMSDTO) DTO;

            //DataConnDTO dataConnDTO = CMSAuthentication.authenticateSubcription(cmsdto.getAuthCredentials());
            HedwigAuthCredentials authCredentials = cmsdto.getAuthCredentials();
//            int productId = authCredentials.getProductId();
//            int tenantId = authCredentials.getTenantId();
            DataConnClient dataConnClient = new DataConnClient();
            DataConnDTO dataConnDTO = new DataConnDTO();
            dataConnDTO.setCloudAuthCredentials(authCredentials);
            dataConnDTO = dataConnClient.getDataConnParams(dataConnDTO);
            if (dataConnDTO.getResponseCode() != 0) {
                cmsdto.setResponseCode(dataConnDTO.getResponseCode());
                cmsdto.setResponseMessage(dataConnDTO.getResponseMessage());
            } else {
                cmsdto.setResponseCode(HedwigResponseCode.SUCCESS);

            }

            DatabaseConnection dc = new DatabaseConnection(dataConnDTO.getDbAdminUser(), dataConnDTO.getDbAdminPassword(), dataConnDTO.getDbConnUrl());
            return DTO;
        } catch (IOException ex) {
            Logger.getLogger(JSONParseAndDBConn.class.getName()).log(Level.SEVERE, null, ex);
            CMSDTO cmsdto = new CMSDTO();
            cmsdto.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return cmsdto;
        }

    }
}
