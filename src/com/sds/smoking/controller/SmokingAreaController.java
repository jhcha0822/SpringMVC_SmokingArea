package com.sds.smoking.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.smoking.domain.SmokingArea;
import com.sds.smoking.model.SmokingAreaService;

@Controller
public class SmokingAreaController {

	@Autowired
	private SmokingAreaService smokingAreaService;

	SmokingArea smokingArea = new SmokingArea();
	
	@ResponseBody
	@GetMapping("/smoking/list")
	public List getList() {
		List smokingList = smokingAreaService.selectAll();
		return smokingList;
	}
	
	@ResponseBody
	@GetMapping("/smoking/getApi")
	public void getApi() throws IOException {
		
		// 1. URL을 만들기 위한 StringBuilder
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/3040000/smokingService/getSmkAreaList"); 								  // URL
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder
        		.encode("serviceKey","UTF-8") + "=5XuhuoQexLdGRNEAvAnhIOWFjP6tCKgUahcCE1nzgw1OlpZmm0p%2B5Le%2FAaef8tPbhXKeRHAvqZzw6SwZZ0aCFQ%3D%3D"); // Service Key
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); 										  // 응답메시지 결과 요청 타입(xml, json, geojson)
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("40", "UTF-8")); 									  // 한 페이지 결과 수
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); 										  // 페이지번호
        urlBuilder.append("&" + URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); 											  // 전체 출력은 빈칸
        // 3. URL 객체 생성
        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET. 
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());
        
        // JSON 파싱
        // JSONObject jsonObj = new JSONObject(sb.toString());
        JSONArray jsonArray = new JSONArray(sb.toString()); // 받은 데이터를 배열에 저장
               
        //JsonArray안엔 json 데이터가 배열로 들어감
        for (int i=0; i<jsonArray.length()-1; i++) {
        	
            JSONObject item = jsonArray.getJSONObject(i);
            
            smokingArea.setArea_nm(item.optString("area_nm"));
            smokingArea.setArea_desc(item.optString("area_desc"));
            smokingArea.setCtprvnnm(item.optString("ctprvnnm"));
            smokingArea.setSigngunm(item.optString("signgunm"));
            smokingArea.setEmdnm(item.optString("emdnm"));
            smokingArea.setArea_se(item.optString("area_se"));
            smokingArea.setArea_ar(Float.parseFloat(item.optString("area_ar")));
            smokingArea.setRdnmadr(item.optString("rdnmadr"));
            smokingArea.setLnmadr(item.optString("lnmadr"));
            smokingArea.setInst_nm(item.optString("inst_nm"));
            smokingArea.setLatitude(Float.parseFloat(item.optString("latitude")));
            smokingArea.setLongitude(Float.parseFloat(item.optString("longitude")));
            smokingArea.setRef_date(item.optString("ref_date"));
            smokingArea.setFclty_knd(item.optString("fclty_knd"));
            
            smokingAreaService.insert(smokingArea);
        }
           
        System.out.println("insert 성공");

    }
}
