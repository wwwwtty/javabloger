<%@ page language="java" contentType="text/html; charset=utf-8"     
    pageEncoding="utf-8"%>     
<%@ taglib prefix="s" uri="/struts-tags"%>     
<%@ taglib prefix="pager" uri="/pager-tags"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">     
<html>     
    <head>     
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">     
        <title>pager tag test</title>     
    </head>     
    <body>     
        <div class="pagination">     
            <pager:pager curPage="3" totalPage="88"     
                url="testPager.action?pno={page}" pageLimit="9" showTotalPage="true"     
                totalRecord="1988" showTotalRecord="true" />     
        </div>     
    </body>     
</html>   
