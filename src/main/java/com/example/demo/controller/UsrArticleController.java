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
	// 게시물 조회 번호가 없는 번호인 경우 처리 코드 추가 필요
	// 현재는 단순히 인덱스로 조회를 하고 있어 입력값과 id값을 대조해서 존재하는지를 먼저 판단하도록 수정이 필요
	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {

		return articles.get(id - 1);
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

		if (deleteArticleRs) {
			rs.put("resultCode", "S-1");
			rs.put("msg", "성공하였습니다.");
		} else {
			rs.put("resultCode", "F-1");
			rs.put("msg", "해당 게시물은 존재하지 않습니다.");

		}

		rs.put("id", id);

		return rs;
	}

	// 게시물 삭제 메서드
	private boolean deleteArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
	}

	// 게시물 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Map<String, Object> doModify(int id, String title, String body) {

		Article selectedArticle = null;
		
		for (Article article : articles) {
			if (article.getId() == id) {
				selectedArticle = article;
				break;
			}
		}
		
		Map<String, Object> rs = new HashMap<>();
		
		if ( selectedArticle == null ) {
			rs.put("resultCode", "F-1");
			rs.put("msg", String.format("%d번 게시물은 존재하지 않습니다.", id));
			return rs;
		} 
		else {
			selectedArticle.setTitle(title);
			selectedArticle.setBody(body);
			
			rs.put("resultCode", "S-1");
			rs.put("msg", String.format("%d번 게시물이 수정되었습니다.", id));
			rs.put("id", id);
			return rs;
		}
	}
}