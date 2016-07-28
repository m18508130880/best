<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="bean.*"%>
<%@ page import="util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中海油LNG加气站公司级信息化管理平台</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%

	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	
%>
<body style="background:#CADFFF">
<form name="Aqsc_Labour_Type_Add" action="Aqsc_Labour_Type.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/aqsc_labour_type.gif"></div><br><br><br>
	<div id="right_table_center">
		<table width="60%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='30'>
				<td width='100%' align='right'>
					<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doAdd()'>
					<img src="../skin/images/button10.gif"           style='cursor:hand;' onclick='doNO()'>
				</td>
			</tr>
			<tr height='30'>
				<td width='100%' align='center'>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">		
						<tr height='30'>
							<td width='20%' align='center'>编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
							<td width='30%' align='left'>
								***
							</td>
							<td width='20%' align='center'>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</td>
							<td width='30%' align='left'>
							  <select name="Status" style="width:92%;height:20px;">
							  	<option value='0'>启用</option>
							  	<option value='1'>注销</option>
							  </select>
							</td>
						</tr>
						<tr height='30'>
							<td width='20%' align='center'>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</td>
							<td width='30%' align='left' >
								<input type='text' name='CName' style='width:90%;height:18px;' value='' maxlength='15'>
							</td>
							<td width='20%' align='center'>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</td>
							<td width='30%' align='left'>
								<input type='text' name='Unit' style='width:90%;height:18px;' value='' maxlength='5'>
							</td>							
						</tr>						
						<tr height='30'>
							<td width='20%' align='center'>型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
							<td width='80%' align='left' colspan=3>
								<input type='text' name='Model' style='width:96%;height:18px;' value='' maxlength='128'>
							</td>						
						</tr>
						<tr height='30'>
							<td width='20%' align='center'>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌</td>
							<td width='30%' align='left'>
								<input type='text' name='Brand' style='width:90%;height:18px;' value='' maxlength='15'>
							</td>
							<td width='20%' align='center'>供货单位</td>
							<td width='30%' align='left'>
								<input type='text' name='Seller' style='width:90%;height:18px;' value='' maxlength='32'>
							</td>
						</tr>
						<tr height='30'>
							<td width='20%' align='center'>描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述</td>
							<td width='80%' align='left' colspan=3>
								<textarea name='Des' rows='8' cols='69' maxlength=128></textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<input name="Cmd" type="hidden" value="10">
<input name="Id"  type="hidden" value="***">
<input name="Sid" type="hidden" value="<%=Sid%>">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doNO()
{
	location = "Aqsc_Labour_Type.jsp?Sid=<%=Sid%>";
}

function doAdd()
{
  if(Aqsc_Labour_Type_Add.CName.value.Trim().length < 1)
  {
    alert("请输入名称!");
    return;
  }
  if(Aqsc_Labour_Type_Add.Model.value.Trim().length < 1)
  {
    alert("请输入型号!");
    return;
  }
  if(Aqsc_Labour_Type_Add.Unit.value.Trim().length < 1)
  {
    alert("请输入单位!");
    return;
  }
  if(Aqsc_Labour_Type_Add.Brand.value.Trim().length < 1)
  {
    alert("请输入品牌!");
    return;
  }
  if(Aqsc_Labour_Type_Add.Seller.value.Trim().length < 1)
  {
    alert("请输入供货单位!");
    return;
  }
  if(Aqsc_Labour_Type_Add.Des.value.Trim().length > 128)
  {
    alert("描述过长，请简化!");
    return;
  }
  if(confirm("信息无误,确定提交?"))
  {
  	Aqsc_Labour_Type_Add.submit();
  }
}
</SCRIPT>
</html>