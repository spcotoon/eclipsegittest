package egov.board.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BoardMapper")
public interface BoardMapper {
	void saveBoard(HashMap<String, Object> paramMap)throws Exception;
//4번번경
	HashMap<String, Object> showBoard(HashMap<String, Object> paramMap)throws Exception;
//5번
	ArrayList<HashMap<String, Object>> showBoardList(HashMap<String, Object> paramMap)throws Exception;
//1번변경
}
