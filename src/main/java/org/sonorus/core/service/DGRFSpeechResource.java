/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonorus.core.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.sonorus.core.dto.SonorusDTO;
import org.sonorus.core.dto.SpeechServicePaths;
import org.sonorus.core.utils.JSONParseAndDBConn;
import org.sonorus.core.emotion.EmotionCalcService;

/**
 * REST Web Service
 *
 * @author dgrfiv
 */
@Path(SpeechServicePaths.DGRF_SPEECH_BASE)
public class DGRFSpeechResource {

    @Context
    private UriInfo context;
    EmotionCalcService ecs;

    public DGRFSpeechResource() {
        ecs = new EmotionCalcService();
    }

    @Path(SpeechServicePaths.DECIDE_SPEECH_EMO)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String decideEmotion(String speechDTOJSON) {
        SonorusDTO dgrfSpeechDTO = (SonorusDTO) JSONParseAndDBConn.authCreateDB(speechDTOJSON, SonorusDTO.class);
        dgrfSpeechDTO = ecs.decideEmotion(dgrfSpeechDTO);
        return JSONParseAndDBConn.getDTOJSON(dgrfSpeechDTO);

    }

    @Path(SpeechServicePaths.WAV_TO_CSV_AND_UPLOAD)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String convertWavIntoCsv(String speechDTOJSON) {
        SonorusDTO dgrfSpeechDTO = (SonorusDTO) JSONParseAndDBConn.authCreateDB(speechDTOJSON, SonorusDTO.class);
        dgrfSpeechDTO = ecs.uploadWavToDataSeries(dgrfSpeechDTO);
        return JSONParseAndDBConn.getDTOJSON(dgrfSpeechDTO);

    }

    @Path(SpeechServicePaths.DELETE_SPEECH_EMO)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteSpeechInstance(String speechDTOJSON) {
        SonorusDTO dgrfSpeechDTO = (SonorusDTO) JSONParseAndDBConn.authCreateDB(speechDTOJSON, SonorusDTO.class);
        dgrfSpeechDTO = ecs.deleteSpeechEmoInstance(dgrfSpeechDTO);
        return JSONParseAndDBConn.getDTOJSON(dgrfSpeechDTO);

    }
}
