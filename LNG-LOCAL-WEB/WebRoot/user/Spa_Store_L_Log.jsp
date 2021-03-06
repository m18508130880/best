<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<%@ page import="util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.math.*" %>
<%@ page import="java.text.*" %>
<%@ taglib uri="/WEB-INF/limitvalidatetag.tld" prefix="Limit"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中海油LNG加气站公司级信息化管理平台</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
  CurrStatus currStatus     = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
  ArrayList Spa_Store_Type  = (ArrayList)session.getAttribute("Spa_Store_Type_" + Sid);
  ArrayList Spa_Store_L_Log = (ArrayList)session.getAttribute("Spa_Store_L_Log_" + Sid);
  int sn = 0;
  
%>
<body style="background:#CADFFF">
<form name="Spa_Store_L_Log"  action="Spa_Store_L.do" method="post" target="_self">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='25px'>
			<td width='100%' align='left'>
				备品类型:
				<select name='Func_Corp_Id' style='width:220px;height:20px' onChange="doSelect()">
					<option value='9999' <%=currStatus.getFunc_Corp_Id().equals("9999")?"selected":""%>>全部</option>
					<%
					if(null != Spa_Store_Type)
					{
						Iterator typeiter = Spa_Store_Type.iterator();
						while(typeiter.hasNext())
						{
							AqscSpareTypeBean typeBean = (AqscSpareTypeBean)typeiter.next();
					%>
							<option value='<%=typeBean.getId()%>' <%=currStatus.getFunc_Corp_Id().equals(typeBean.getId())?"selected":""%>><%=typeBean.getCName()%></option>
					<%
						}
					}
					%>
				</select>
				备品类别:
				<select name='Func_Sub_Id' style='width:70px;height:20px' onChange="doSelect()">
					<option value='1' <%=currStatus.getFunc_Sub_Id() == 1 ? "selected":""%>>电气类</option>
					<option value='2' <%=currStatus.getFunc_Sub_Id() == 2 ? "selected":""%>>工艺类</option>
				</select>
			</td>
		</tr>
		<tr height='25px'>
			<td width='100%' align='center'>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
					<tr height='25'>
						<td width='5%'  align='center' class="table_deep_blue">序号</td>
						<td width='12%' align='center' class="table_deep_blue">备品备件</td>
						<td width='12%' align='center' class="table_deep_blue">规格型号</td>
						<td width='8%'  align='center' class="table_deep_blue">调整上期</td>
						<td width='8%'  align='center' class="table_deep_blue">调整进库</td>
						<td width='8%'  align='center' class="table_deep_blue">调整出库</td>
						<td width='8%'  align='center' class="table_deep_blue">调整本期</td>
						<td width='12%' align='center' class="table_deep_blue">调整时间</td>
						<td width='12%' align='center' class="table_deep_blue">调整原因</td>
						<td width='8%'  align='center' class="table_deep_blue">调整人员</td>
					</tr>
					<%
					if(Spa_Store_L_Log != null)
					{
						Iterator iterator = Spa_Store_L_Log.iterator();
						while(iterator.hasNext())
						{
							SpaStoreLBean Bean = (SpaStoreLBean)iterator.next();
							String Spa_Type_Name = Bean.getSpa_Type_Name();
							String Spa_Mode      = Bean.getSpa_Mode();
							String Model         = Bean.getModel();
							String Old_R_Cnt     = Bean.getOld_R_Cnt();
							String Now_R_Cnt     = Bean.getNow_R_Cnt();
							String Now_I_Cnt     = Bean.getNow_I_Cnt();
							String Now_O_Cnt     = Bean.getNow_O_Cnt();
							String ITime         = Bean.getITime();
							String Memo          = Bean.getMemo();
							String Operator      = Bean.getOperator();
							String Spa_Mode_Name = "/";
							if(null != Model && Model.length() > 0)
							{
								String[] List = Model.split(",");
								if(List.length >= Integer.parseInt(Spa_Mode))
									Spa_Mode_Name = List[Integer.parseInt(Spa_Mode)-1];
							}
							
							sn++;
							
					%>
							<tr <%=((sn%2)==0?"class='table_blue'":"class='table_white_l'")%>>
								<td align=center><%=sn%></td>
								<td align=left><%=Spa_Type_Name%></td>
						    <td align=left><%=Spa_Mode_Name%></td>
						    <td align=center><%=Old_R_Cnt%></td>
						    <td align=center><%=Now_I_Cnt%></td>
						    <td align=center><%=Now_O_Cnt%></td>
						    <td align=center><%=Now_R_Cnt%></td>
						    <td align=center><%=ITime%></td>
						    <td align=left><%=Memo%></td>
						    <td align=left><%=Operator%></td>
							</tr>
					<%
						}
					}
					if(0 == sn)
					{
					%>
						<tr height='30'>
							<td width='100%' align=center colspan=10>无!</td>
						</tr>
					<%
					}
					%>
				</table>
			</td>
		</tr>
	</table>
	<input type="hidden" name="Cmd"         value="1">
	<input type="hidden" name="Sid"         value="<%=Sid%>">
	<input type="hidden" name="Cpm_Id"      value="">
	<input type="hidden" name="Func_Sel_Id" value="<%=currStatus.getFunc_Sel_Id()%>">
</form>
</body>
<script language='javascript'>
function doSelect()
{
	//Spa_Store_L_Log.Cpm_Id.value = parent.window.parent.frames.lFrame.document.getElementById('id').value;
	Spa_Store_L_Log.submit();
}
</script>
</html>