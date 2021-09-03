package kr.co.elasticworks.api.service;

import java.util.List;

import kr.co.elasticworks.api.domain.Popup;

public interface PopupService {

	List<Popup> popupAllList() throws Exception;

	Popup selectOnePopup() throws Exception;

	int createPopup() throws Exception;

	int updatePopup() throws Exception;

	int deletePopup() throws Exception;

}