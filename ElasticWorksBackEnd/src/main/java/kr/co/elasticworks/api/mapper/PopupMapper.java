package kr.co.elasticworks.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.elasticworks.api.domain.Popup;

@Mapper
public interface PopupMapper {

	Popup selectAllPopupList() throws Exception;
}
