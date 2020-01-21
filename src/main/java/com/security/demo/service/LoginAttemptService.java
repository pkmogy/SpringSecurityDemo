package com.security.demo.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

/**
 *
 * @author 李羅
 */
@Service
public class LoginAttemptService {

	private final int MAX_ATTEMPT = 5; //設定最大嘗試次數
	private LoadingCache<String, Integer> attemptsCache;

	//建構方法
	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder().
			expireAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Integer>() {
			public Integer load(String key) {
				return 0;
			}
		});
	}

	/**
	 * 登入成功，將快取清除
	 * @param key 用戶 IP
	 */
	public void loginSucceeded(String key) {
		attemptsCache.invalidate(key); //清除快取
	}

	/**
	 * 登入失敗，累積嘗試次數
	 * @param key 用戶 IP
	 */
	public void loginFailed(String key) {
		System.err.println("loginFailed key:"+key);
		int attempts = 0;
		try {
			attempts = attemptsCache.get(key);//取得快取 IP 的次數
		} catch (ExecutionException e) {
			attempts = 0;//找不到快取
		}
		attempts++;
		System.err.println("loginFailed attempts:"+attempts);
		attemptsCache.put(key, attempts);//加入快取
		
	}

	/**
	 * 檢查是否超過嘗試次數
	 * @param key
	 * @return 
	 */
	public boolean isBlocked(String key) {
		System.err.println("key:"+key);
		try {
			return attemptsCache.get(key) >= MAX_ATTEMPT;
		} catch (ExecutionException e) {
			return false;
		}
	}
}
