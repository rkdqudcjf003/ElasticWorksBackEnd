package kr.co.elasticworks.api.service;

import java.util.List;

import kr.co.elasticworks.api.domain.Popup;

public interface PopupService {

	List<Popup> popupAllList() throws Exception;

	Popup selectOnePopup(int popupIdx) throws Exception;

	int createPopup(Popup popup) throws Exception;

	int updatePopup(Popup popup) throws Exception;

	int deletePopup(int popupIdx) throws Exception;

}