package org.infy.idp.controller.appservice;

import com.google.gson.Gson;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.infy.idp.entities.DeployArtifact;
import org.infy.idp.entities.JobInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApplicationServices.class, secure = false)
public class ApplicationServicesTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private org.infy.idp.schedulerservice.GetJobParameter getJobParameter;
    @MockBean
    private PostGreSqlDbContext postGreSqlDbContext;
    @MockBean
    private org.infy.idp.schedulerservice.NexusDetails nexusDetails;

    @Test
    public void updateScheduleDataBase() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/schedulerService/updateScheduleDataBase").contentType(MediaType.APPLICATION_JSON);
        ((MockHttpServletRequestBuilder) requestBuilder).content("{\"jsonObj\":\"abc\",\"buildId\":\"1.0.1.release\"}");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map json = getJsonMapFromResponse(result,Map.class);
        assertEquals("status should be SUCCESS","SUCCESS",json.get("status"));
        verify(getJobParameter, Mockito.times(1)).getJobParameter(eq("abc"), eq("1.0.1.release"));

    }

    @Test
    public void updateScheduleDataBasePersistFails() throws Exception {
        doThrow(Exception.class)
                .when(getJobParameter)
                .getJobParameter(eq("abc"), eq("1.0.1.release"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/schedulerService/updateScheduleDataBase").contentType(MediaType.APPLICATION_JSON);
        ((MockHttpServletRequestBuilder) requestBuilder).content("{\"jsonObj\":\"abc\",\"buildId\":\"1.0.1.release\"}");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map json = getJsonMapFromResponse(result,Map.class);
        assertEquals("status should be FAILURE","FAILURE",json.get("status"));
        verify(getJobParameter, Mockito.times(1)).getJobParameter(eq("abc"), eq("1.0.1.release"));

    }

    @Test
    public void getArtifactDetails() throws Exception{
        JobInfo jobInfo = new JobInfo();
        jobInfo.setApplicationName("testApp");
        jobInfo.setEnvironmentName("PROD");
        jobInfo.setPipelineName("test-app-pipe-line");
        jobInfo.setReleaseNumber("2.4.2.SNAPSHOT");
        String jsonStr = new Gson().toJson(jobInfo);
        Mockito.when(nexusDetails.getArtifact(jobInfo.getApplicationName(), jobInfo.getPipelineName(),
                jobInfo.getReleaseNumber(),jobInfo.getEnvironmentName())).thenReturn(getDummyArtifactList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/schedulerService/getArtifactDetails").contentType(MediaType.APPLICATION_JSON);
        ((MockHttpServletRequestBuilder) requestBuilder).content(jsonStr);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map json = getJsonMapFromResponse(result,DeployArtifact.class);
        assertEquals("artifact should be of latest version","testApp-2018102-101"
                ,((DeployArtifact)json.get("resource")).getArtifactName());

    }

    private Map getJsonMapFromResponse(MvcResult mvcResult,Class resourceClass) throws Exception{
        Map json = new Gson().fromJson(mvcResult.getResponse().getContentAsString(),Map.class);
        Object resource = json.get("resource");
        if(resource != null){
            json.put("resource", new Gson().fromJson(resource.toString(), resourceClass));
        }
        return json;
    }

    private List<DeployArtifact> getDummyArtifactList(){
        List<DeployArtifact> artifactList = new ArrayList<>();

        DeployArtifact artifact1 = new DeployArtifact();
        artifact1.setArtifactName("testApp-2018102-100");
        artifactList.add(artifact1);

        DeployArtifact artifact2 = new DeployArtifact();
        artifact2.setArtifactName("testApp-2018102-101");
        artifactList.add(artifact2);


        return artifactList;
    }
}
