package egov.board.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

	void checkUser(HttpServletRequest request)throws Exception;

	void saveBoard(HttpServletRequest request)throws Exception;
//3번변경
	HashMap<String, Object> showBoard(HttpServletRequest request)throws Exception;

	ArrayList<HashMap<String, Object>> showBoardList(HttpServletRequest request)throws Exception;
}
