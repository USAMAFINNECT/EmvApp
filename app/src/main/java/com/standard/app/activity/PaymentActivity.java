package com.standard.app.activity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;


public class PaymentActivity {

    private String STORE_ID;
    private String HASH_KEY;

    private String POST_BACK_URL1;
    private String POST_BACK_URL2;

    private String TRANSACTION_POST_URL1="https://easypay.easypaisa.com.pk/easypay/index.jsf";
    private String TRANSACTION_POST_URL2;


    String orderId= "abc123";
    String storeId= "43";
    String transactionAmount= "1.23";
    String transactionType= "OTC";
    String msisdn= "03458508726";
    String emailAddress= "testEmail@gmail.com";
    String tokenExpiry= "20190723 232722";

    OkHttpClient client = new OkHttpClient();

    String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // post request code here

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    // test data
    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException {

        // issue the Get request
        PaymentActivity example = new PaymentActivity();
        String getResponse = example.doGetRequest("https://www.vogella.com/");
        System.out.println(getResponse);


        // issue the post request

        String json = example.bowlingJson("Jesse", "Jake");
        String postResponse = example.doPostRequest("http://www.roundsapp.com/post", json);
        System.out.println(postResponse);
    }

}
