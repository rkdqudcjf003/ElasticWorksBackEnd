package kr.co.elasticworks.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.elasticworks.api.domain.Popup;
import kr.co.elasticworks.api.mapper.PopupMapper;

@Service(value = "popup")
public class PopupServiceImpl implements PopupService {

	@Autowired
	PopupMapper popupMapper;
	
	@Override
	public List<Popup> popupAllList() throws Exception {
		return popupMapper.selectAllPopupList();
	}
	
	@Override
	public Popup selectOnePopup() throws Exception{
		return popupMapper.selectOnePopup();
	};
	
	@Override
	public int createPopup() throws Exception{
		return popupMapper.createPopup();
	};
	
	@Override
	public int updatePopup() throws Exception{
		return popupMapper.updatePopup();
	};
	
	@Override
	public int deletePopup() throws Exception{
		return popupMapper.deletePopup();
	};
}
