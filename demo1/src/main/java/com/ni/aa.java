package com.ni;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class aa {
    public static void main(String[] args) throws Exception {
        aa aa = new aa();
        aa.doGETParam();
    }

    public void doGETParam() throws Exception {
        // create HttpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //parameters.add(new BasicNameValuePair("startDate", "2018-04-22 19:30:08"));
        //parameters.add(new BasicNameValuePair("endDate", "2028-04-22 19:30:08"));
        //parameters.add(new BasicNameValuePair("projectId", "0"));

        // define the parameters of the request
        URI uri = new URIBuilder("http://10.24.68.238:8033/octopus/cdp/api/v1/tags/status?tagIds=442")
                .setParameters(parameters)
                .build();

        // create http GET request
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("token", "4ec0d3a8ca2d3426331bcee5660338c5");
        //response object
        CloseableHttpResponse response = null;
        try {
            // execute http get request
            response = httpclient.execute(httpGet);
            // response status code 200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("start--------------->");
                System.out.println(content);
                System.out.println("end----------------->");
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}
