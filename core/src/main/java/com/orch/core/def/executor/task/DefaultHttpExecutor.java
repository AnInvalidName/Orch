package com.orch.core.def.executor.task;

import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Executor;
import com.orch.common.runtime.JobInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class DefaultHttpExecutor implements Executor {


//    public static String post(String url, String jsonRequestBody) throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(url);
//        //httpGet.addHeader();
//
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.setEntity(new StringEntity(jsonRequestBody));
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        HttpEntity entity = response.getEntity();
//        String responseBody = EntityUtils.toString(entity, "UTF-8");
//        response.close();
//        httpClient.close();
//        return responseBody;
//    }

    /**
     * HTTP TaskDef:
     * {
     *
     * }
     *
     */

    @Override
    public void execute(JobInfo jobInfo, ExecutionResult executionResult) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = (String) jobInfo.getExecutorData().get("url");
            url = this.addUrlParams(url, (Map)jobInfo.getExecutorData().get("urlParams"));
            String method = (String) jobInfo.getExecutorData().get("method");
            HttpUriRequest httpUriRequest = null;
            if("GET".equals(method))
                httpUriRequest = new HttpGet(url);
            else if("PUT".equals(method))
                httpUriRequest = new HttpPut(url);
            else if("DELETE".equals(method))
                httpUriRequest = new HttpDelete(url);
            else
                httpUriRequest = new HttpPost(url);
            HttpUriRequest finalHttpUriRequest = httpUriRequest;
            Optional.ofNullable((Map<String,String>)jobInfo.getExecutorData().get("headers"))
                    .ifPresent(headers -> headers.forEach((name, value)->{
                        finalHttpUriRequest.addHeader(name, value);
                    }));
            if(httpUriRequest instanceof HttpEntityEnclosingRequest){
                String jsonStrEntity = this.parseRequestEntity(jobInfo.getInputData());
                HttpEntity entity = new StringEntity(jsonStrEntity);
                ((HttpEntityEnclosingRequest)httpUriRequest).setEntity(entity);
            }
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            String outputStr = EntityUtils.toString(response.getEntity());
            response.close();
            httpClient.close();
            executionResult.setOutputData(this.parseResponseEntity(outputStr));
            executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
        } catch (Exception e){
            executionResult.setStatus(ExecutionResult.ExecutionStatus.FAILED);
        }

    }

    private String addUrlParams(String url, Map<String, Object> urlParams){
        return url;
    }

    private String parseRequestEntity(Object params){
        return null;
    }

    private Map<String, Object> parseResponseEntity(String entityStr){
        return null;
    }


}
