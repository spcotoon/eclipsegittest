package egov.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import com.lib.model.UserVO;
import com.lib.pagination.PaginationInfo;
import com.lib.util.Validation_Form;

import egov.board.dao.BoardMapper;
import egov.board.service.BoardService;

@Service("BoardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService{

	@Resource(name="BoardMapper")
	BoardMapper boardMapper;

	@Override
	public void checkUser(HttpServletRequest request) throws Exception {
		
		if(request.getSession().getAttribute("uservo")==null)
		{
			throw new Exception("로그인안했음");
		}
		
	}
	
	@Override
	public void saveBoard(HttpServletRequest request) throws Exception {
		
		if(request.getSession().getAttribute("uservo")==null)
		{
			throw new Exception("로그인안했음");
		}
		
		//사용자요청을 데이터베이스로 전달
		String title= request.getParameter("title");
		String content =request.getParameter("mytextarea");
		if(title.length()>25)
		{
			throw new Exception("제목을 다시 확인해주세요.");
		}
		
		HashMap<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("in_title", title);
		paramMap.put("in_content", content);
		paramMap.put("in_userid", ((UserVO)request.getSession().getAttribute("uservo")).getUserid());
		paramMap.put("out_state", 0);
		boardMapper.saveBoard(paramMap);
	}

	@Override
	public HashMap<String, Object> showBoard(HttpServletRequest request) throws Exception {

		
		if(request.getSession().getAttribute("uservo")==null)
		{
			throw new Exception("로그인안했음");
		}
		//사용자요청을 데이터베이스로 전달
		String id=((UserVO)request.getSession().getAttribute("uservo")).getUserid();
		String brdid= request.getParameter("brdid");
		boolean validNumber = false;
		validNumber = Validation_Form.validNum(brdid);

		if(validNumber==false)
		{
			throw new Exception("유효성검사실패");
		}
		HashMap<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("in_brdid", brdid);
		paramMap.put("out_state", 0);
		HashMap<String,Object> rusultMap= new HashMap<String,Object>();
		rusultMap=boardMapper.showBoard(paramMap);
		if(rusultMap==null)
		{
			throw new Exception("페이지찾을수없음");
		}
		
		return rusultMap;
	}

	@Override
	public ArrayList<HashMap<String, Object>> showBoardList(HttpServletRequest request) throws Exception {

		if(request.getSession().getAttribute("uservo")==null)
		{
			throw new Exception("로그인안했음");
		}
		String pageNo=request.getParameter("pageNo");
		if(pageNo==null||pageNo.equals(""))
		{
			pageNo="1";
		}
		else
		{
			pageNo=request.getParameter("pageNo");
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(pageNo));
		paginationInfo.setPageSize(10);
		paginationInfo.setRecordCountPerPage(10);
		
		HashMap<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("pi_offset", (paginationInfo.getCurrentPageNo()-1)*paginationInfo.getRecordCountPerPage());
		paramMap.put("pi_recordCountPerPage", paginationInfo.getRecordCountPerPage());
		paramMap.put("out_listcount", 0);
		paramMap.put("out_state", 0);
		ArrayList<HashMap<String,Object>> list= new ArrayList<HashMap<String,Object>>();
		list=boardMapper.showBoardList(paramMap);
		int listCount = Integer.parseInt(paramMap.get("out_listcount").toString());
		paginationInfo.setTotalRecordCount(listCount);
		
		if(list==null)
		{
			throw new Exception("페이지찾을수없음");
		}
		
		HashMap<String,Object> resultMap= new HashMap<String,Object>();
		resultMap.put("paginationInfo", paginationInfo);
		resultMap.put("listCount", listCount);
		list.add(resultMap);
		
		return list;
	}
}
