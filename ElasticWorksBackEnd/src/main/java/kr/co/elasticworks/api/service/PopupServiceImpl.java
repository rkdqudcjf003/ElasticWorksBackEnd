package kr.co.elasticworks.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.elasticworks.api.domain.Popup;
import kr.co.elasticworks.api.mapper.PopupMapper;

@Service(value = "popup")
public class PopupServiceImpl implements PopupService {

	@Autowired
	PopupMapper popupMapper;
	
	@Override
	public Popup popupAllList() throws Exception {
		return popupMapper.selectAllPopupList();
	}
}
