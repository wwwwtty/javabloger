package org.spring.web.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessorMatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cms.core.utils.WebUtils;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.json.JsonStringWriter;
import org.springframework.web.servlet.view.json.JsonWriterConfiguratorTemplateRegistry;
import org.springframework.web.servlet.view.json.writer.jsonlib.AllwaysMatchingValueProcessorMatcher;
import org.springframework.web.servlet.view.json.writer.jsonlib.JsonlibJsonWriterConfiguratorTemplate;
import org.springframework.web.servlet.view.json.writer.jsonlib.NullPropertyFilter;
import org.springframework.web.servlet.view.json.writer.jsonlib.PropertyEditorRegistryValueProcessor;

public class JsonlibJsonStringWriter implements JsonStringWriter {
	protected final Log logger = LogFactory.getLog(getClass());

	private boolean enableJsonConfigSupport;
	
	private boolean convertAllMapValues;
	
	private boolean keepNullProperties;
	
	@SuppressWarnings("unchecked")
	public void convertAndWrite(Map model, JsonWriterConfiguratorTemplateRegistry configuratorTemplateRegistry, Writer writer, BindingResult br) throws IOException{
		
		JsonConfig jsonConfig = null;
		
		JsonlibJsonWriterConfiguratorTemplate configuratorTemplate = (JsonlibJsonWriterConfiguratorTemplate) configuratorTemplateRegistry.findConfiguratorTemplate(JsonlibJsonWriterConfiguratorTemplate.class.getName());
		
		if(enableJsonConfigSupport && configuratorTemplate != null ){
			jsonConfig = (JsonConfig) configuratorTemplate.getConfigurator();
		}

		JSON json =null;
		if(jsonConfig==null){
			 json = JSONSerializer.toJSON(model.get(WebUtils.Data_alias) ); 
		}
		else{
			json= JSONSerializer.toJSON(model, jsonConfig ); 
		}
		if(logger.isDebugEnabled())
			logger.debug(json.toString());
		
		json.write(writer);
		writer.flush();
	}

	public boolean isEnableJsonConfigSupport() {
		return enableJsonConfigSupport;
	}

	public void setEnableJsonConfigSupport(boolean enableJsonConfigSupport) {
		this.enableJsonConfigSupport = enableJsonConfigSupport;
	}

	public boolean isConvertAllMapValues() {
		return convertAllMapValues;
	}

	public void setConvertAllMapValues(boolean convertAllMapValues) {
		this.convertAllMapValues = convertAllMapValues;
	}

	public boolean isKeepNullProperties() {
		return keepNullProperties;
	}

	public void setKeepNullProperties(boolean keepNullProperties) {
		this.keepNullProperties = keepNullProperties;
	}
}

