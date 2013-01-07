package org.hsc.novelSpider.web.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author heshencao@163.com 2012-12-20 上午11:07:03
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -1547457232791267780L;
	private Logger logger=LoggerFactory.getLogger(FileUploadServlet.class);
	private String uploadPath; // 上传文件的目录

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
//		String sysPath = SystemConfig.instance.getSystemPath();
		String sysPath ="/";
		if (!sysPath.endsWith(File.separator)) {
			sysPath += File.separator;
		}
		uploadPath = sysPath + "system" + File.separator;
		File root=new File(uploadPath);
		if(!root.exists()){
			root.mkdirs();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	//	DictService dictService = SpringContextUtils.getBean(DictService.class);
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(4194304);// 设置最大文件尺寸，这里是4MB
			List<FileItem> items = upload.parseRequest(request);
			
			// 依次处理每一个文件：
			String[] uploadedFileNames=new String[items.size()];
			for (int i=0;i<items.size();i++) {
				 FileItem fi =items.get(i);
				// 获得文件名，这个文件名包括路径：
				String fileName = fi.getName();
				if(fileName==null){
					continue;
				}
				logger.info("上传文件："+fileName);
				String sufix = ".jpg";
				if (fileName.contains(".")) {
					sufix = fileName.substring(fileName.lastIndexOf("."));
				}
				String nfn=System.currentTimeMillis()+ sufix;
				File pfile = new File(uploadPath + nfn);
				fi.write(pfile);
				uploadedFileNames[i]=nfn;
			}
			String fils=StringUtils.join(uploadedFileNames, "");
			response.getWriter().write("{\"success\":true,\"filename\":\""+fils+"\"}");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}
