package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Article;

//http:localhost:8024/usr/article/detail
//http:localhost:8024/usr/article/list
//http://localhost:8024/usr/article/doAdd?regDate=2021-03-26 22:47:32&title=제목3&body=내용3
//http://localhost:8024/usr/article/doDelete?id=1

@Controller
public class UsrArticleController {

	private int articlesLastId;
	private List<Article> articles;
	
	public UsrArticleController() {
		// 맴버변수 초기화 
		articlesLastId = 0;
		articles = new ArrayList<>();
		
		// 게시물 2개 생성 
		articles.add(new Article(++articlesLastId, "2021-03-26 21:31:45", "제목1", "내용1"));
		articles.add(new Article(++articlesLastId, "2021-03-26 21:32:52", "제목2", "내용2"));
	}
	// 게시물 상세보기 	
	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		
		return articles.get(id-1);
	}
	// 게시물 리스트 보기 
	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		
		return articles;
	}
	// 게시물 추가 
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Map<String, Object> doAdd(String regDate, String title, String body) {
		
		articles.add(new Article(++articlesLastId, regDate, title, body));
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S-1");
 		rs.put("msg", "성공하였습니다.");
		rs.put("id", articlesLastId);
		
		return rs;
	}
	
	// 게시물 삭제  
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public Map<String, Object> doDelete(int id) {
		
		boolean deleteArticleRs = deleteArticle(id);
		
		Map<String, Object> rs = new HashMap<>();
		
		if ( deleteArticleRs ) {
			rs.put("resultCode", "S-1");
			rs.put("msg", "성공하였습니다.");
		} 
		else {
			rs.put("resultCode", "F-1");
			rs.put("msg", "해달 게시물은 존재하지 습니다.");
			
		}

		rs.put("id", id);
		
		return rs;
	}
	
	// 게시물 삭제 메서드   	
	private boolean deleteArticle(int id) {
		for ( Article article : articles ) {
			if ( article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
	}
}