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
<link type="text/css" href="../../skin/css/style.css" rel="stylesheet"/>
<script type='text/javascript' src='../../skin/js/browser.js' charset='gb2312'></script>
<script type="text/javascript" src="../../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../skin/js/util.js"></script>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	ArrayList User_FP_Role = (ArrayList)session.getAttribute("User_FP_Role_" + Sid);
	UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	String Operator = UserInfo.getId();
	String FpId = UserInfo.getFp_Role();
	String FpList = "";
	if(null != FpId && FpId.length() > 0 && null != User_FP_Role)
	{
		Iterator roleiter = User_FP_Role.iterator();
		while(roleiter.hasNext())
		{
			UserRoleBean roleBean = (UserRoleBean)roleiter.next();
			if(roleBean.getId().equals(FpId) && null != roleBean.getPoint())
			{
				FpList = roleBean.getPoint();
			}
		}
	}
	
	ArrayList Pro_R_Type = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null == Oil_Info){Oil_Info = "";}
	}
	
  CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
  String BDate = currStatus.getVecDate().get(0).toString().substring(0,10);
	String EDate = currStatus.getVecDate().get(1).toString().substring(0,10);
	
  ArrayList Pro_I = (ArrayList)session.getAttribute("Pro_I_" + Sid);
  int sn = 0;
  
%>
<body style=" background:#CADFFF">
<form name="Pro_I"  action="Pro_I.do" method="post" target="mFrame">
<div id="down_bg_2">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='25px' class='sjtop'>
			<td width='80%' align='left'>
				燃料类型:
				<select name='Func_Corp_Id' style='width:150px;height:20px' onChange="doSelect()">
					<option value='9999' <%=currStatus.getFunc_Corp_Id().equals("9999")?"selected":""%>>全部</option>
					<%
					if(null != Pro_R_Type)
					{
						Iterator typeiter = Pro_R_Type.iterator();
						while(typeiter.hasNext())
						{
							ProRBean typeBean = (ProRBean)typeiter.next();
							String type_Id = typeBean.getOil_CType();
							String type_Name = "无";
													
							if(Oil_Info.trim().length() > 0)
							{
							  String[] List = Oil_Info.split(";");
							  for(int i=0; i<List.length && List[i].length()>0; i++)
							  {
							  	String[] subList = List[i].split(",");
							  	if(type_Id.equals(subList[0]))
							  	{
							  		type_Name = subList[1];
							  		break;
							  	}
					  		}
					  	}
					%>
					  	<option value='<%=type_Id%>' <%=currStatus.getFunc_Corp_Id().equals(type_Id)?"selected":""%>><%=type_Id%>|<%=type_Name%></option>
					<%				  	
						}
					}
					%>
				</select>
				记录状态:
				<select name='Func_Sub_Id' style='width:90px;height:20px' onChange="doSelect()">
					<option value='2' <%=currStatus.getFunc_Sub_Id() == 2 ? "selected":""%>>待审核</option>
					<option value='0' <%=currStatus.getFunc_Sub_Id() == 0 ? "selected":""%>>审核有效</option>
					<option value='1' <%=currStatus.getFunc_Sub_Id() == 1 ? "selected":""%>>审核无效</option>
				</select>
				<input id='BDate' name='BDate' type='text' style='width:90px;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
				-
				<input id='EDate' name='EDate' type='text' style='width:90px;height:18px;' value='<%=EDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
				磅单编号:<input type='text' name='Func_Type_Id' style='width:100px;height:16px;' value='<%=currStatus.getFunc_Type_Id()%>'>
			</td>
			<td width='20%' align='right'>		
				<img id="img1" src="../../skin/images/mini_button_search.gif" onClick='doSelect()' style='cursor:hand;'>
				<img id="img2" src="../../skin/images/excel.gif"              onClick='doExport()' style="cursor:hand;display:<Limit:limitValidate userrole='<%=FpList%>' fpid='020202' ctype='1'/>">
				<img id="img3" src="../../skin/images/mini_button_add.gif"    onClick='doAdd()'    style="cursor:hand;display:<Limit:limitValidate userrole='<%=FpList%>' fpid='020203' ctype='1'/>">
			</td>
		</tr>
		<tr height='30' valign='middle'>
			<td width='100%' align='center' colspan=2>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
					<tr height='25' valign='middle'>
						<td width='4%'  align='center' class="table_deep_blue">序号</td>
						<td width='10%' align='center' class="table_deep_blue">卸车站点</td>
						<td width='10%' align='center' class="table_deep_blue">燃料类型</td>
						<td width='12%' align='center' class="table_deep_blue">卸车时间</td>
						<td width='24%' align='center' class="table_deep_blue">卸车信息</td>
						<td width='16%' align='center' class="table_deep_blue">运输信息</td>
						<td width='16%' align='center' class="table_deep_blue">记录信息</td>
					</tr>
					<%
					 if(Pro_I != null)
					 {
						Iterator iterator = Pro_I.iterator();
						while(iterator.hasNext())
						{
							ProIBean Bean = (ProIBean)iterator.next();
							String SN = Bean.getSN();
							String Cpm_Name = Bean.getCpm_Name();
							String CTime = Bean.getCTime();
							String Order_Id = Bean.getOrder_Id();
							String Order_Value = Bean.getOrder_Value();
							String Value = Bean.getValue();
							String Value_Gas = Bean.getValue_Gas();
							String Memo = Bean.getMemo();
							String Status = Bean.getStatus();
							String Car_Id = Bean.getCar_Id();
							String Car_Owner = Bean.getCar_Owner();
							String Car_Corp = Bean.getCar_Corp();
							String Oil_CType = Bean.getOil_CType();
							String Worker = Bean.getWorker();
							String Checker_Name = Bean.getChecker_Name();
							String Operator_Name = Bean.getOperator_Name();
							
							if(null == Order_Id){Order_Id = "";}
							if(null == Order_Value){Order_Value = "";}
							if(null == Value){Value = "";}
							if(null == Value_Gas){Value_Gas = "";}
							if(null == Car_Id){Car_Id = "";}
							if(null == Car_Owner){Car_Owner = "";}
							if(null == Car_Corp){Car_Corp = "";}
							if(null == Oil_CType || Oil_CType.trim().length() < 1){Oil_CType = "1000";}
							if(null == Worker){Worker = "";}
							if(null == Memo){Memo = "";}
							if(null == Checker_Name){Checker_Name = "";}
							
							String Oil_CName = "无";
							if(Oil_Info.trim().length() > 0)
							{
							  String[] List = Oil_Info.split(";");
							  for(int i=0; i<List.length && List[i].length()>0; i++)
							  {
							  	String[] subList = List[i].split(",");
							  	if(subList[0].equals(Oil_CType))
							  	{
							  		Oil_CName = subList[1];
							  		break;
							  	}
							  }
							}
							
							String Oil_Detail = "";
							try
							{
								switch(Integer.parseInt(Oil_CType))
								{
									default:
									case 1000://汽油
									case 1010://90#汽油
									case 1011://90#无铅汽油
									case 1012://90#清洁汽油
									case 1020://92#汽油
									case 1021://92#无铅汽油
									case 1022://92#清洁汽油
									case 1030://93#汽油
									case 1031://93＃无铅汽油
									case 1032://93#清洁汽油
									case 1040://95#汽油
									case 1041://95#无铅汽油
									case 1042://95#清洁汽油
									case 1050://97#汽油
									case 1051://97#无铅汽油
									case 1052://97#清洁汽油
									case 1060://120＃汽油
									case 1080://其他车用汽油
									case 1090://98#汽油
									case 1091://98#无铅汽油
									case 1092://98＃清洁汽油
									case 1100://车用汽油
									case 1200://航空汽油
									case 1201://75#航空汽油
									case 1202://95#航空汽油
									case 1203://100#航空汽油
									case 1204://其他航空汽油
									case 1300://其他汽油
									case 2000://柴油
									case 2001://0#柴油
									case 2002://+5#柴油
									case 2003://+10#柴油
									case 2004://+15#柴油
									case 2005://+20#柴油
									case 2006://-5#柴油
									case 2007://-10#柴油
									case 2008://-15#柴油
									case 2009://-20#柴油
									case 2010://-30#柴油
									case 2011://-35#柴油
									case 2015://-50#柴油
									case 2100://轻柴油
									case 2016://其他轻柴油
									case 2200://重柴油
									case 2012://10#重柴油
									case 2013://20#重柴油
									case 2014://其他重柴油
									case 2300://军用柴油
									case 2301://-10#军用柴油
									case 2900://其他柴油
											Oil_Detail = "磅单编号: " + Order_Id + "<br>"
											           + "磅单数量: " + Order_Value + "L<br>"
																 + "卸车数量: " + Value + "L<br>"
								                 + "折合质量: " + Value_Gas + "kg<br>"
								                 + "作业人员: " + Worker;
										break;
									case 3001://CNG
									case 3002://LNG
											Oil_Detail = "磅单编号: " + Order_Id + "<br>"
											           + "磅单数量: " + Order_Value + "kg<br>"
											           + "卸车数量: " + Value + "kg<br>"
								                 + "折合气态: " + Value_Gas + "m3<br>"
								                 + "作业人员: " + Worker;
										break;
								}
							}
							catch(Exception Exp)
							{
								Oil_Detail = "磅单编号: " + Order_Id + "<br>"
											     + "磅单数量: " + Order_Value + "kg<br>"
											     + "卸车数量: " + Value + "kg<br>"
								           + "折合气态: " + Value_Gas + "m3<br>"
								           + "作业人员: " + Worker;
								Exp.printStackTrace();
							}
							
							
							String Car_Detail = "运输车牌: " + Car_Id + "<br>"
							                  + "押运人员: " + Car_Owner + "<br>"
							                  + "承运单位: " + Car_Corp;			
							
							String Rcd_Detail = "";
							switch(Integer.parseInt(Status))
							{
								case 0:
										Rcd_Detail = "录入人员: " + Operator_Name + "<br>"
															 + "审核人员: " + Checker_Name + "<br>"
															 + "记录状态: " + "有效";
									break;
								case 1:
										Rcd_Detail = "录入人员: " + Operator_Name + "<br>"
															 + "审核人员: " + Checker_Name + "<br>"
															 + "记录状态: " + "无效";
									break;
								case 2:
										Rcd_Detail = "录入人员: " + Operator_Name + "<br>"
															 + "审核人员: " + Checker_Name + "<br>"
															 + "记录状态: " + "<a href='#' onClick=\"doAbandon('"+SN+"', '"+CTime+"', '0')\">有效</a>" + " | " + "<a href='#' onClick=\"doAbandon('"+SN+"', '"+CTime+"', '1')\">无效</a>";
									break;
							}
							
							sn++;
					%>
				  <tr <%=((sn%2)==0?"class='table_blue'":"class='table_white_l'")%> title='<%=Memo%>'>
						<%
						if(Status.equals("0"))
						{
						%>
							<td align=center><%=sn%></td>
							<td align=center><%=Cpm_Name%></td>
							<td align=center><%=Oil_CName%></td>
							<td align=center><%=CTime%></td>
							<td align=left valign=top><%=Oil_Detail%></td>
							<td align=left valign=top><%=Car_Detail%></td>
							<td align=left valign=top><%=Rcd_Detail%></td>
						<%
						}
						else if(Status.equals("1"))
						{
						%>
							<td align=center><font color=gray><%=sn%></font></td>
							<td align=center><font color=gray><%=Cpm_Name%></font></td>
							<td align=center><font color=gray><%=Oil_CName%></font></td>
							<td align=center><font color=gray><%=CTime%></font></td>
							<td align=left valign=top><font color=gray><%=Oil_Detail%></font></td>
							<td align=left valign=top><font color=gray><%=Car_Detail%></font></td>
							<td align=left valign=top><font color=gray><%=Rcd_Detail%></font></td>
						<%
						}
						else
						{
						%>
							<td align=center><%=sn%></td>
							<td align=center><%=Cpm_Name%></td>
							<td align=center><%=Oil_CName%></td>
							<td align=center><%=CTime%></td>
							<td align=left valign=top><%=Oil_Detail%></td>
							<td align=left valign=top><%=Car_Detail%></td>
							<td align=left valign=top><%=Rcd_Detail%></td>
						<%
						}
						%>
					</tr>
					<%
						}
					}
					for(int i=0;i<(MsgBean.CONST_PAGE_SIZE - sn);i++)
					{
						if(sn % 2 != 0)
					  {
					%>				  
				      <tr <%=((i%2)==0?"class='table_blue'":"class='table_white_l'")%>>
				      	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
				      </tr>
					<%
						}
					  else
					  {
					%>				
	          <tr <%=((i%2)==0?"class='table_white_l'":"class='table_blue'")%>>
		          <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		        </tr>	        
					<%
       			}
     			}
					%> 
     		 	<tr>
						<td colspan="7" class="table_deep_blue" >
				 			<table width="100%" height="20"  border="0" cellpadding="0" cellspacing="0" >
			    			<tr valign="bottom">
			      			<td nowrap><%=currStatus.GetPageHtml("Pro_I")%></td>
			    			</tr>			    		
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<input name="Cmd"      type="hidden" value="0">
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="Cpm_Id"   type="hidden" value=""/>
<input name="SN"       type="hidden" value=""/>
<input name="Status"   type="hidden" value=""/>
<input name="Checker"  type="hidden" value="<%=Operator%>"/>
<input name="BTime"    type="hidden" value="">
<input name="ETime"    type="hidden" value="">
<input name="CurrPage" type="hidden" value="<%=currStatus.getCurrPage()%>">
<input type="button"   id="CurrButton" onClick="doSelect()" style="display:none">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
if(<%=currStatus.getResult().length()%> > 0)
   alert("<%=currStatus.getResult()%>");
<%
currStatus.setResult("");
session.setAttribute("CurrStatus_" + Sid, currStatus);
%>

//ipad禁掉导出
if(1 == fBrowserRedirect() || 2 == fBrowserRedirect())
{
	document.getElementById('img2').style.display = 'none';
}

//window.parent.frames.lFrame.document.getElementById('CurrJsp').innerText = 'Pro_I.jsp';

switch(parseInt(<%=currStatus.getFunc_Sub_Id()%>))
{
	case 2:
			document.getElementById('BDate').style.display = 'none';
			document.getElementById('EDate').style.display = 'none';
		break;
	case 0:
	case 1:
			document.getElementById('BDate').style.display = '';
			document.getElementById('EDate').style.display = '';
		break;
}

function doSelect()
{
	var days = new Date(Pro_I.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I.BDate.value.replace(/-/g, "/")).getTime();
	var dcnt = parseInt(days/(1000*60*60*24));
	if(dcnt < 0)
	{
		alert('截止日期需大于开始日期');
		return;
	}
	if((dcnt + 1) > 31)
	{
		alert('日期跨越不超过31天');
		return;
	}
	
	Pro_I.Cpm_Id.value = window.parent.frames.lFrame.document.getElementById('id').value;
	Pro_I.BTime.value = Pro_I.BDate.value + " 00:00:00";
	Pro_I.ETime.value = Pro_I.EDate.value + " 23:59:59";
	Pro_I.submit();
}

function GoPage(pPage)
{
	var days = new Date(Pro_I.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I.BDate.value.replace(/-/g, "/")).getTime();
	var dcnt = parseInt(days/(1000*60*60*24));
	if(dcnt < 0)
	{
		alert('截止日期需大于开始日期');
		return;
	}
	if((dcnt + 1) > 31)
	{
		alert('日期跨越不超过31天');
		return;
	}
	
	if(pPage == "")
	{
		 alert("请输入目标页面的数值!");
		 return;
	}
	if(pPage < 1)
	{
	   	alert("请输入页数大于1");
		  return;	
	}
	if(pPage > <%=currStatus.getTotalPages()%>)
	{
		pPage = <%=currStatus.getTotalPages()%>;
	}
	Pro_I.Cpm_Id.value = window.parent.frames.lFrame.document.getElementById('id').value;
	Pro_I.BTime.value = Pro_I.BDate.value + " 00:00:00";
	Pro_I.ETime.value = Pro_I.EDate.value + " 23:59:59";
	Pro_I.CurrPage.value = pPage;
	Pro_I.submit();
}

function doAbandon(pSN, pCTime, pStatus)
{
	if('<Limit:limitValidate userrole='<%=FpList%>' fpid='020204' ctype='1'/>' == 'none')
	{
		alert('您无权限审核卸车记录!');
		return;
	}
	
	var TDay = new Date().format("yyyy-MM-dd");
	if(pCTime.substring(0,10) != TDay)
	{
		alert('只可操作当天流水记录!');
		return;
	}
	
	var currFirm = "";
	switch(parseInt(pStatus))
	{
		case 0:
				currFirm = "确认将当前卸车记录置为有效?"
			break;
		case 1:
				currFirm = "确认将当前卸车记录置为无效?"
			break;
	}
	
	if(confirm(currFirm))
	{
		Pro_I.Cmd.value = 11;
		Pro_I.Cpm_Id.value = window.parent.frames.lFrame.document.getElementById('id').value;
		Pro_I.SN.value = pSN;
		Pro_I.Status.value = pStatus;
		Pro_I.BTime.value = Pro_I.BDate.value + " 00:00:00";
		Pro_I.ETime.value = Pro_I.EDate.value + " 23:59:59";
		Pro_I.submit();
	}
}

var req = null;
function doExport()
{	
	var days = new Date(Pro_I.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I.BDate.value.replace(/-/g, "/")).getTime();
	var dcnt = parseInt(days/(1000*60*60*24));
	if(dcnt < 0)
	{
		alert('截止日期需大于开始日期');
		return;
	}
	if((dcnt + 1) > 31)
	{
		alert('日期跨越不超过31天');
		return;
	}
	
	if(0 == <%=sn%>)
	{
		alert('当前无记录!');
		return;
	}
	if(65000 <= <%=currStatus.getTotalRecord()%>)
	{
		alert('记录过多，请分批导出!');
		return;
	}
	if(confirm("确定导出?"))
  {
		if(window.XMLHttpRequest)
	  {
			req = new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		//设置回调函数
		req.onreadystatechange = callbackForName;
		var url = "Pro_I_Export.do?Sid=<%=Sid%>&Cpm_Id="+window.parent.frames.lFrame.document.getElementById('id').value+"&BTime="+Pro_I.BDate.value+" 00:00:00"+"&ETime="+Pro_I.EDate.value+" 23:59:59" + "&Func_Sub_Id=<%=currStatus.getFunc_Sub_Id()%>&Func_Corp_Id=<%=currStatus.getFunc_Corp_Id()%>&Func_Type_Id=<%=currStatus.getFunc_Type_Id()%>&CurrPage=<%=currStatus.getCurrPage()%>";
		req.open("post",url,true);
		req.send(null);
		return true;
	}
}
function callbackForName()
{
	var state = req.readyState;
	if(state==4)
	{
		var resp = req.responseText;			
		var str = "";
		if(resp != null)
		{
			location.href = "../../files/excel/" + resp + ".xls";
		}
	}
}

function doAdd()
{
	location = "Pro_I_Add.jsp?Sid=<%=Sid%>";
}
</SCRIPT>
</html>