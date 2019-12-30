package com.onTime.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onTime.project.model.domain.Invitation;
import com.onTime.project.model.domain.JsonReq;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;
import com.onTime.project.service.OnTimeService;

import com.google.gson.JsonObject;
import com.onTime.project.loginApi.GoogleLoginApi;
import com.onTime.project.loginApi.KakaoLoginApi;
import com.onTime.project.loginApi.NaverLoginApi;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class OnTimeController {
	
	@Autowired
	public KakaoLoginApi kakaoLoginApi;
	@Autowired
	private NaverLoginApi naverLoginApi;
	@Autowired
	private GoogleLoginApi googleLoginApi;
	
	@Autowired
	private OnTimeService service;

	/* Kakao Login */
	@RequestMapping(value = "/login")
	public String kakaoLogin() {
		return "redirect:" +kakaoLoginApi.getAuthUrl();
		//return kakaoLoginApi.getAuthUrl();
	}
	@RequestMapping(value = "/oauth")
	@ResponseBody
	public String getUserInfo(@RequestParam("code") String code) {
		JsonObject json = (JsonObject)kakaoLoginApi.getUserInfo(kakaoLoginApi.getAccessKakaoToken(code));
		return json.toString();
	}

	/* Naver Login */
	@RequestMapping(value = "/loginNaver")
	public String loginNaver(HttpSession session) {
		return "redirect:" + naverLoginApi.getAuthorizationUrl(session);
	}
	
	@RequestMapping(value = "/callback")
	public String callbackNaver(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException, InterruptedException, ExecutionException {
//		System.out.println(naverLoginApi.getAccessToken(session, code, state).getAccessToken());
		return "redirect:http://localhost:8080/?naver="+naverLoginApi.getAccessToken(session, code, state).getAccessToken();
	}
	
	@RequestMapping(value = "/naverInfo")
	@ResponseBody
	public String getInfoFromNaver(@RequestParam String code) throws IOException, ParseException, InterruptedException, ExecutionException {
		return naverLoginApi.getUserProfile(code).toString();
	}
	
	// return값을 JSONObject에 저장해놓고 유저 id를 세션으로 저장 - 추후 로그아웃 구현시 사용
//		JSONObject apiResult = naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state));
//		session.setAttribute("sessionId", apiResult.get("id").toString());  

	/* Google Login */
	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpSession session) {
		return "redirect:" + googleLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/googleCallback")
	@ResponseBody
	public String callbackGoogle(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException, InterruptedException, ExecutionException {
		return ((JSONObject) googleLoginApi.getUserProfile(googleLoginApi.getAccessToken(session, code, state))).toString();
	}
	
	@GetMapping(value="/user")
	@ResponseBody
	public User findUserById(@RequestBody JsonReq jsonReq) {
		return service.findUserById(jsonReq.getUserId());
	}
	
	@PostMapping(value="/user")
	@ResponseBody
	public boolean createUser(@RequestBody JsonReq jsonReq) {
		return service.createUser(jsonReq.getUserId(), jsonReq.getUserName());
	}
	
	@GetMapping(value="/user/invitation")
	@ResponseBody
	public List<Promise> getMyInvitation(@RequestBody JsonReq jsonReq) {
		return service.getInvitedPromises(jsonReq.getUserId());
	}
	
	@PostMapping(value="/promise/invitation")
	@ResponseBody
	public boolean invite(@RequestBody Invitation invitation) {
		try {
			return service.invite(invitation);
		}catch (Exception e) {
			return false;
		}
	}
	
	@PostMapping(value="/test")
	public String findMyHostedPromise(@RequestBody JsonReq jsonReq){
		System.out.println(jsonReq);
		return jsonReq.getUserId();
	}
	
	@GetMapping(value="/promise")
	@ResponseBody
	public List<Promise> getMyPromises(@RequestParam String userId){
		return service.getMyPromises(userId);
	}
	
	@PostMapping(value="/promise")
	@ResponseBody
	public boolean createPromise(@RequestBody Promise promise) {
//		Promise p = new Promise("술술술", "aaa", "종각", 0.0, 0.0, "2019-12-17 11:43:19", 0);
		return service.createPromise(promise);
	}
	
	@GetMapping(value="/promise/members")
	@ResponseBody
	public List<User> getMembers(@RequestParam int promiseId){
		return service.getMembers(promiseId);
	}
	
	
}