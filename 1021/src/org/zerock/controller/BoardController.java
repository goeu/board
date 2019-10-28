package org.zerock.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.zerock.dao.BoardDAO;
import org.zerock.dao.BoardDAOImpl;
import org.zerock.domain.BoardVO;
import org.zerock.dto.PageMaker;
import org.zerock.dto.PagingDTO;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")	// board로 시작하는 모든 애들 다받음
@MultipartConfig(
		maxFileSize = 1024*1024*10, maxRequestSize = 1024*1024*50,
		fileSizeThreshold = 1024*1024, location = "c:\\zzz" )
public class BoardController extends BasicController {
	private static final long serialVersionUID = 1L;
       
	private BoardDAO dao;
	
	public BoardController() {
		dao = new BoardDAOImpl();
	}

	
	@RequestMapping(value = "/board/register", type = "GET")
	public String add(HttpServletRequest req, HttpServletResponse res) throws Exception {
		

		System.out.println("add...............");
		return "/board/register" ;
	}
	
	@RequestMapping(value = "/board/list", type = "GET")
	public String list(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//파라미터 수집 -> pageDTO 변환
		PagingDTO dto = new PagingDTO(req.getParameter("page"), req.getParameter("amount"));
		//Model 완성
		req.setAttribute("list", dao.getList(dto));
		req.setAttribute("pm", new PageMaker(dao.getCount(),dto));
		System.out.println("list...............");
		return "/board/list";
	}
	
	@RequestMapping(value = "/board/register", type = "POST")
	public String addPost(HttpServletRequest req,
			HttpServletResponse res)throws Exception {
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String writer = req.getParameter("writer");
		
		
//		vo.setTitle(title);
//		vo.setContent(content);
//		vo.setWriter(writer);
//		boolean insert = dao.insert(vo);
//		System.out.println(insert);
		
		System.out.println("add.post.................");
		
		BoardVO vo = new BoardVO();
		
		vo.setTitle(req.getParameter("title"));
		vo.setContent(req.getParameter("content"));
		vo.setWriter(req.getParameter("writer"));
		
		Collection<Part>parts = req.getParts();
		
		for(Part part : parts) {
			
			if(part.getName().equals("fs") == false) {
				continue;
			}
			
			String fname = part.getSubmittedFileName();
			System.out.println("fname: " + fname);
			
			String saveName = System.currentTimeMillis() + "_" + fname;
			
			File file = new File("C:\\zzz\\upload", saveName);
			
			try {
				InputStream in = part.getInputStream();
				
				FileUtils.copyInputStreamToFile(in, file);
				
				vo.addFileName(saveName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println("=================");
		System.out.println(vo);
		
		dao.insert(vo);
		
		return "redirect:/list";
	}
	@RequestMapping(value="/board/delete", type="get")
	public String deleteGet(HttpServletRequest req, HttpServletResponse res) {
		String bno = req.getParameter("bno");
		BoardVO vo = new BoardVO();
		Long bnos = Long.parseLong(bno);
		vo.setBno(bnos);
		boolean delete = dao.delete(bnos);
		System.out.println(delete);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/board/read", type="GET")
	public String Read(HttpServletRequest req, HttpServletResponse res) {
		
		//tagetCookie에 
		Cookie targetCookie = checkCookieExist(req, "RecentView");
		boolean existCookie = targetCookie != null;
		
		boolean already = checkCookieValue(targetCookie, req.getParameter("bno"),"%");
		
		System.out.println("targetCookie" +  targetCookie);
		System.out.println("already : "+already);
		
		
		//Cookie targetCookie = checkCookieExist (req,쿠키이름)
		//existCookie = targetCookie == null;
		//already = checkCookieValue(bno,'%');
		
		//조회수 쿠키를 확인한다. -RecentViewCookie =>existCookies
		//값을 가져오고 %로 분리
		//해당 bno값이 있는지 확인 - already
		
		//if already가 false라면 
		//조회시에 update하고 가져온다.
		// else
		long bno = Long.parseLong(req.getParameter("bno"));
		
		if(already==false) {
			dao.updateViewCnt(bno);
		}
		req.setAttribute("board", dao.selectOne(bno));
		//exisCookiew  false
		//RecentViewCookie 생성하고 response추가
		if(existCookie == false) {
			Cookie ck = new Cookie("RecentView", bno+"%");
			ck.setMaxAge(60*60*24); //a day
			ck.setPath("/board");
			res.addCookie(ck);
			System.out.println("신규쿠키발행");
		}else {//있음
			String value = targetCookie.getValue();
			
			value += bno+"%";
			targetCookie.setValue(value);
			
			res.addCookie(targetCookie);
		}
		
		
		
		return "/board/read";
	}

	
	

}