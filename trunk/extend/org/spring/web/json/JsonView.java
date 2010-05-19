package org.spring.web.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class JsonView extends AbstractUrlBasedView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

}
