package com.mbc.team.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NaverLoginService {
    // Access Token ��û �޼���
    public String getAccessTokenFromNaver(String accessTokenUrl) {
        try {
            // HttpClient ����
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(accessTokenUrl);

            // ��û ������
            org.apache.http.HttpResponse response = client.execute(httpPost);

            // ���� ó��
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            // JSON �Ľ�
            JSONObject jsonResponse = new JSONObject(result);
            return jsonResponse.getString("access_token"); // �׼��� ��ū ��ȯ
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ����� ���� ��û �޼���
    public Map<String, Object> getUserInfoFromNaver(String accessToken) {
        // ���̹� API ȣ���Ͽ� ����� ������ �������� ���� ����
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Authorization", "Bearer " + accessToken);  // ��ū ����� ����

            org.apache.http.HttpResponse response = client.execute(httpPost);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            // JSON �Ľ�
            JSONObject jsonResponse = new JSONObject(result);

            // ����� �������� �ʿ��� �����͸� �����Ͽ� ��ȯ
            Map<String, Object> userInfo = new HashMap<>();
            JSONObject responseObj = jsonResponse.getJSONObject("response");
            userInfo.put("id", responseObj.getString("id"));
            userInfo.put("name", responseObj.getString("name"));
            userInfo.put("email", responseObj.getString("email"));

            return userInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

