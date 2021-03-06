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
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%
	
	String Sid        = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String Cpm_Id     = CommUtil.StrToGB2312(request.getParameter("Cpm_Id"));
	String Cpm_Name   = CommUtil.StrToGB2312(request.getParameter("Cpm_Name"));
	String Check_Time = CommUtil.StrToGB2312(request.getParameter("Check_Time"));
	String Check_SN   = CommUtil.StrToGB2312(request.getParameter("Check_SN"));
	ArrayList User_User_Info = (ArrayList)session.getAttribute("User_User_Info_" + Sid);
	CurrStatus currStatus    = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	UserInfoBean UserInfo    = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  String Operator = UserInfo.getId();
  String Operator_Name = UserInfo.getCName();
  
%>
<body style="background:#CADFFF">
<form name="Sat_Check_Add_Break" action="Sat_Check_Add_Break.do" method="post" target="mFrame">
	<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'>
			<td width='25%' align='center'>所属场站</td>
			<td width='75%' align='left'>
				<%=Cpm_Name%>
				<input type='hidden' name='Cpm_Id' value='<%=Cpm_Id%>'>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>违章时间</td>
			<td width='75%' align='left'>
				<%=Check_Time%>
				<input type='hidden' name='Break_Time' value='<%=Check_Time%>'>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>违 章 人</td>
			<td width='75%' align='left'>
				<select name="Break_OP" style="width:91%;height:20px">
					<option value='*'>请选择...</option>
					<%
					if(null != User_User_Info)
					{
						Iterator useriter = User_User_Info.iterator();
						while(useriter.hasNext())
						{
							UserInfoBean userBean = (UserInfoBean)useriter.next();
							if(userBean.getStatus().equals("0") && userBean.getDept_Id().equals(Cpm_Id))
							{
					%>
								<option value='<%=userBean.getId()%>'><%=userBean.getCName()%></option>
					<%
							}
						}
					}
					%>
				</select>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>直接管理人</td>
			<td width='75%' align='left'>
				<select name="Manag_OP" style="width:91%;height:20px">
					<option value='*'>请选择...</option>
					<%
					if(null != User_User_Info)
					{
						Iterator useriter = User_User_Info.iterator();
						while(useriter.hasNext())
						{
							UserInfoBean userBean = (UserInfoBean)useriter.next();
							if(userBean.getStatus().equals("0") && (userBean.getDept_Id().equals(Cpm_Id) || userBean.getDept_Id().length() == 2))
							{
					%>
								<option value='<%=userBean.getId()%>'><%=userBean.getCName()%></option>
					<%
							}
						}
					}
					%>
				</select>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>绩效挂钩</td>
			<td width='75%' align='left'>
				<input type='text' name='Break_Point' style="width:90%;height:16px" value=''> 分
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>违章行为<br>(违反条款)</td>
			<td width='75%' align='left'>
				<textarea name='Break_Des' rows='5' cols='32' maxlength=128></textarea>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>录入人员</td>
			<td width='75%' align='left'>
				<%=Operator_Name%>
				<input type='hidden' name='Operator' value='<%=Operator%>'>
			</td>
		</tr>
		<tr height='40'>
			<td width='100%' align='center' colspan=2>
				<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doSave()'>
			</td>
		</tr>
	</table>
</form>
</body>
<SCRIPT LANGUAGE=javascript>
var reqAdd = null;
function doSave()
{
  if(Sat_Check_Add_Break.Break_Time.value.length < 1)
  {
  	alert('请选择违章时间!');
  	return;
  }
  if(Sat_Check_Add_Break.Break_OP.value.length < 1 || Sat_Check_Add_Break.Break_OP.value == '*')
  {
  	alert('请选择违章人!');
  	return;
  }
  if(Sat_Check_Add_Break.Manag_OP.value.length < 1 || Sat_Check_Add_Break.Manag_OP.value == '*')
  {
  	alert('请选择直接管理人!');
  	return;
  }
  if(Sat_Check_Add_Break.Break_Point.value.Trim().length < 1 || Sat_Check_Add_Break.Break_Point.value > 0)
  {
  	alert("绩效挂钩扣分错误,可能的原因：\n\n  1.为空。\n\n  2.不是负值。");
		return;
  }
	for(var i=0; i<Sat_Check_Add_Break.Break_Point.value.length; i++)
	{
		if(Sat_Check_Add_Break.Break_Point.value.charAt(Sat_Check_Add_Break.Break_Point.value.length-1) == '-')
		{
			alert("输入绩效挂钩扣分有误，请重新输入!");
	    return;
		}
		if(Sat_Check_Add_Break.Break_Point.value.charAt(i) == '-' && i != 0)
		{
			alert("输入绩效挂钩扣分有误，请重新输入!");
	    return;
		}
		if(Sat_Check_Add_Break.Break_Point.value.charAt(i) != '-' && isNaN(Sat_Check_Add_Break.Break_Point.value.charAt(i)))
	  {
	    alert("输入绩效挂钩扣分有误，请重新输入!");
	    return;
	  }
	}
	if(Sat_Check_Add_Break.Break_Des.value.Trim().length < 1)
	{
		alert('请简要描述违章行为及违反条款情况!');
		return;
	}
	if(Sat_Check_Add_Break.Break_Des.value.Trim().length > 128)
  {
    alert("违章行为及违反条款描述过长，请简化!");
    return;
  }
  if(confirm("信息无误,确定提交?"))
  {
  	if(window.XMLHttpRequest)
	  {
			reqAdd = new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
			reqAdd = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		//设置回调函数
		reqAdd.onreadystatechange = function()
		{
			var state = reqAdd.readyState;
			if(state == 4)
			{
				if(reqAdd.status == 200)
				{
					var resp = reqAdd.responseText;			
					if(resp != null && resp.substring(0,4) == '0000')
					{
						alert('成功');
						parent.doSelect();
						return;
					}
					else
					{
						alert('失败，请重新操作');
						return;
					}
				}
				else
				{
					alert("失败，请重新操作");
					return;
				}
			}
		};
		var url = 'Sat_Break_Add.do?Cmd=10&Sid=<%=Sid%>&Check_SN=<%=Check_SN%>&Cpm_Id='+Sat_Check_Add_Break.Cpm_Id.value+'&Break_Time='+Sat_Check_Add_Break.Break_Time.value+'&Break_OP='+Sat_Check_Add_Break.Break_OP.value+'&Manag_OP='+Sat_Check_Add_Break.Manag_OP.value+'&Break_Point='+Sat_Check_Add_Break.Break_Point.value.Trim()+'&Break_Des='+Sat_Check_Add_Break.Break_Des.value.Trim()+'&Operator=<%=Operator%>&CurrPage=<%=currStatus.getCurrPage()%>&Func_Corp_Id=<%=currStatus.getFunc_Corp_Id()%>&BTime=<%=currStatus.getVecDate().get(0).toString().substring(0,10)%>&ETime=<%=currStatus.getVecDate().get(1).toString().substring(0,10)%>&currtime='+new Date();
		reqAdd.open("post",url,true);
		reqAdd.send(null);
		return true;
  }
}
</SCRIPT>
</html>