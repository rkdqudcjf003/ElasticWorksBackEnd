package kr.co.elasticworks.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.elasticworks.api.domain.Popup;

@Mapper
public interface PopupMapper {

	List<Popup> selectAllPopupList() throws Exception;
	Popup selectOnePopup() throws Exception;
	int createPopup() throws Exception;
	int updatePopup() throws Exception;
	int deletePopup() throws Exception;
}
