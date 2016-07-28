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
<title>�к���LNG����վ��˾����Ϣ������ƽ̨</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%

	String Sid   = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String CType = CommUtil.StrToGB2312(request.getParameter("CType"));
	String SN    = CommUtil.StrToGB2312(request.getParameter("SN"));
	
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	ArrayList Lab_Store   = (ArrayList)session.getAttribute("Lab_Store_" + Sid);
	ArrayList Lab_Store_I = (ArrayList)session.getAttribute("Lab_Store_I_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  String Operator = UserInfo.getId();
  
  String Lab_Type      = "";
  String Lab_Mode      = "";
  String Lab_I_Time    = "";
  String Lab_I_Numb    = "";
  String Lab_I_Cnt     = "";
  String Lab_I_Price   = "";
  String Lab_I_Amt     = "";
  String Lab_I_Memo    = "";
  String Operator_Name = "";
  
  String Status         = "";
	String Status_OP_Name = "";
	String Status_Memo    = "";
  if(Lab_Store_I != null)
	{
		Iterator iterator = Lab_Store_I.iterator();
		while(iterator.hasNext())
		{
			LabStoreIBean Bean = (LabStoreIBean)iterator.next();
			if(Bean.getSN().equals(SN))
			{
				Lab_Type      = Bean.getLab_Type();
			  Lab_Mode      = Bean.getLab_Mode();
			  Lab_I_Time    = Bean.getLab_I_Time();
			  Lab_I_Numb    = Bean.getLab_I_Numb();
			  Lab_I_Cnt     = Bean.getLab_I_Cnt();
			  Lab_I_Price   = Bean.getLab_I_Price();
			  Lab_I_Amt     = Bean.getLab_I_Amt();
			  Lab_I_Memo    = Bean.getLab_I_Memo();
			  Operator_Name = Bean.getOperator_Name();
			  
			  Status         = Bean.getStatus();
				Status_OP_Name = Bean.getStatus_OP_Name();
				Status_Memo    = Bean.getStatus_Memo();
				if(null == Status_OP_Name){Status_OP_Name = "";}
				if(null == Status_Memo){Status_Memo = "";}
			}		
		}
	}
  
%>
<body style="background:#CADFFF">
<form name="Lab_Store_I_Edt" action="Lab_Store_I.do" method="post" target="mFrame">
	<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
	<%
	switch(Integer.parseInt(CType))
	{
		case 1:
	%>
				<tr height='30'>
					<td width='25%' align='center'>��Ʒ����</td>
					<td width='75%' align='left'>
						<select name="Lab_Type_Chg" style="width:150px;height:20px">
						<%
						if(null != Lab_Store)
						{
							Iterator typeiter = Lab_Store.iterator();
							while(typeiter.hasNext())
							{
								LabStoreBean typeBean = (LabStoreBean)typeiter.next();										
								String type_Id = typeBean.getLab_Type();
								String type_Name = typeBean.getLab_Type_Name();
								String type_Mode = typeBean.getLab_Mode();
								String type_Model = typeBean.getModel();										
								String type_Mode_Name = "/";
								if(null != type_Model && type_Model.length() > 0)
								{
									String[] List = type_Model.split(",");
									if(List.length >= Integer.parseInt(type_Mode))
										type_Mode_Name = List[Integer.parseInt(type_Mode)-1];
								}
						%>
								<option value='<%=type_Id+type_Mode%>' <%=(Lab_Type+Lab_Mode).equals(type_Id+type_Mode)?"selected":""%>><%=type_Name%>{�ͺ�:<%=type_Mode_Name%>}</option>
						<%										
							}
						}
						%>
						</select>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺ʱ��</td>
					<td width='75%' align='left'>
						<input name='Lab_I_Time' type='text' style='width:148px;height:18px;' value='<%=Lab_I_Time%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺����</td>
					<td width='75%' align='left'>
						<input name='Lab_I_Numb' type='text' style='width:248px;height:18px;' value='<%=Lab_I_Numb%>' maxlength=20>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺����</td>
					<td width='75%' align='left'>
						<input name='Lab_I_Cnt' type='text' style='width:248px;height:18px;' value='<%=Lab_I_Cnt%>' maxlength=4>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺����</td>
					<td width='75%' align='left'>
						<input name='Lab_I_Price' type='text' style='width:248px;height:18px;' value='<%=Lab_I_Price%>' maxlength=8>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺���</td>
					<td width='75%' align='left'>
					  <input name='Lab_I_Amt' type='text' style='width:248px;height:18px;' value='<%=Lab_I_Amt%>' maxlength='8'>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺��ע</td>
					<td width='75%' align='left'>
						<textarea name='Lab_I_Memo' rows='5' cols='33' maxlength=128><%=Lab_I_Memo%></textarea>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�깺��Ա</td>
					<td width='75%' align='left'>
						<%=Operator_Name%>
					</td>
				</tr>
	<%
			break;
		case 2:
	%>
				<input type='hidden' name='Lab_I_Numb' value='<%=Lab_I_Numb%>'>
				<tr height='40'>
					<td width='25%' align='center'>���״̬</td>
					<td width='75%' align='left'>
						<input type='radio' id='radio1' name='radio1' value='1' onclick="doChange(1)">���ͨ��
						&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type='radio' id='radio2' name='radio2' value='2' onclick="doChange(2)">�����Ч
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>��˱�ע</td>
					<td width='75%' align='left'>
						<textarea name='Status_Memo' rows='5' cols='61' maxlength=128><%=Status_Memo%></textarea>
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>�����Ա</td>
					<td width='75%' align='left'>
						<%=Status_OP_Name%>&nbsp;
					</td>
				</tr>
				<tr height='30'>
					<td width='25%' align='center'>һ������</td>
					<td width='75%' align='left'>
						<input type='checkbox' id='checkbox1' name='checkbox1' value='1' checked>ͬһ�깺����һ������
					</td>
				</tr>
	<%
			break;
	}
	%>
		
		<tr height='40'>
			<td width='100%' align='center' colspan=2>
				<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doEdit()'>
			</td>
		</tr>
	</table>
	<input type="hidden" name="Cmd"       value="11">
	<input type="hidden" name="Sid"       value="<%=Sid%>">
	<input type="hidden" name="SN"        value="<%=SN%>">
	<input type="hidden" name="Lab_Type"  value="">
	<input type="hidden" name="Lab_Mode"  value="">
	<input type="hidden" name="Status"    value="">
	<input type="hidden" name="Operator"  value="<%=Operator%>">
	<input type="hidden" name="Status_OP" value="<%=Operator%>">
	<input type="hidden" name="Cpm_Id"   value="">
	<input type="hidden" name="BTime"    value="<%=currStatus.getVecDate().get(0).toString().substring(0,10)%>">
	<input type="hidden" name="ETime"    value="<%=currStatus.getVecDate().get(1).toString().substring(0,10)%>">
	<input type="hidden" name="CurrPage" value="<%=currStatus.getCurrPage()%>">
	<input type="hidden" name="Func_Corp_Id" value="<%=currStatus.getFunc_Corp_Id()%>">
	<input type="hidden" name="Func_Sub_Id"  value="<%=currStatus.getFunc_Sub_Id()%>">
	<input type="hidden" name="Func_Type_Id" value="<%=currStatus.getFunc_Type_Id()%>">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doChange(pIndex)
{
	switch(parseInt(pIndex))
	{
		case 1:
				document.getElementById('radio1').checked = true;
				document.getElementById('radio2').checked = false;
			break;
		case 2:
				document.getElementById('radio1').checked = false;
				document.getElementById('radio2').checked = true;
			break;
	}
}

function doEdit()
{
  switch(parseInt(<%=CType%>))
	{
		case 1:
				if(Lab_Store_I_Edt.Lab_Type_Chg.value.length < 1)
			  {
			  	alert('��ѡ���Ʒ����!');
			  	return;
			  }
				if(Lab_Store_I_Edt.Lab_I_Time.value.length < 1)
			  {
			  	alert('��ѡ���깺ʱ��!');
			  	return;
			  }
			  if(Lab_Store_I_Edt.Lab_I_Numb.value.Trim().length < 1)
			  {
			  	alert('����д�깺����!');
			  	return;
			  }
			  if(Lab_Store_I_Edt.Lab_I_Cnt.value.Trim().length < 1 || Lab_Store_I_Edt.Lab_I_Cnt.value <= 0)
			  {
			  	alert("�깺�����������,���ܵ�ԭ��\n\n  1.Ϊ�ա�\n\n  2.������ֵ��");
					return;
			  }
			  for(var i=0; i<Lab_Store_I_Edt.Lab_I_Cnt.value.length; i++)
				{
					if(isNaN(Lab_Store_I_Edt.Lab_I_Cnt.value.charAt(i)))
				  {
				    alert("�깺����������������������!");
				    return;
				  }
				}
				if(Lab_Store_I_Edt.Lab_I_Price.value.Trim().length < 1 || Lab_Store_I_Edt.Lab_I_Price.value <= 0)
			  {
			  	alert("�깺���۴���,���ܵ�ԭ��\n\n  1.�깺����Ϊ�ա�\n\n  2.�깺���۲�����ֵ��");
					return;
			  }
				for(var i=0; i<Lab_Store_I_Edt.Lab_I_Price.value.length; i++)
				{
					if(Lab_Store_I_Edt.Lab_I_Price.value.charAt(0) == '.' || Lab_Store_I_Edt.Lab_I_Price.value.charAt(Lab_Store_I_Edt.Lab_I_Price.value.length-1) == '.')
					{
						alert("�����깺������������������!");
				    return;
					}
					if(Lab_Store_I_Edt.Lab_I_Price.value.charAt(i) != '.' && isNaN(Lab_Store_I_Edt.Lab_I_Price.value.charAt(i)))
				  {
				    alert("�����깺������������������!");
				    return;
				  }
				}
				if(Lab_Store_I_Edt.Lab_I_Price.value.indexOf(".") != -1)
				{
					if(Lab_Store_I_Edt.Lab_I_Price.value.substring(Lab_Store_I_Edt.Lab_I_Price.value.indexOf(".")+1,Lab_Store_I_Edt.Lab_I_Price.value.length).length >2)
					{
						alert("�깺����С��������ֻ��������λ!");
						return;
					}
				}
				if(Lab_Store_I_Edt.Lab_I_Amt.value.Trim().length < 1 || Lab_Store_I_Edt.Lab_I_Amt.value <= 0)
			  {
			  	alert("�깺������,���ܵ�ԭ��\n\n  1.�깺���Ϊ�ա�\n\n  2.�깺������ֵ��");
					return;
			  }
				for(var i=0; i<Lab_Store_I_Edt.Lab_I_Amt.value.length; i++)
				{
					if(Lab_Store_I_Edt.Lab_I_Amt.value.charAt(0) == '.' || Lab_Store_I_Edt.Lab_I_Amt.value.charAt(Lab_Store_I_Edt.Lab_I_Amt.value.length-1) == '.')
					{
						alert("�����깺�����������������!");
				    return;
					}
					if(Lab_Store_I_Edt.Lab_I_Amt.value.charAt(i) != '.' && isNaN(Lab_Store_I_Edt.Lab_I_Amt.value.charAt(i)))
				  {
				    alert("�����깺�����������������!");
				    return;
				  }
				}
				if(Lab_Store_I_Edt.Lab_I_Amt.value.indexOf(".") != -1)
				{
					if(Lab_Store_I_Edt.Lab_I_Amt.value.substring(Lab_Store_I_Edt.Lab_I_Amt.value.indexOf(".")+1,Lab_Store_I_Edt.Lab_I_Amt.value.length).length >2)
					{
						alert("�깺���С��������ֻ��������λ!");
						return;
					}
				}
				if(Lab_Store_I_Edt.Lab_I_Memo.value.Trim().length < 1)
				{
					alert('���Ҫ��д�깺��ע!');
					return;
				}
				if(Lab_Store_I_Edt.Lab_I_Memo.value.Trim().length > 128)
			  {
			    alert("�깺��ע�������������!");
			    return;
			  }
			  if(confirm("��Ϣ����ȷ���ύ?"))
			  {
			  	Lab_Store_I_Edt.Cmd.value      = 11;
			  	//Lab_Store_I_Edt.Cpm_Id.value   = parent.window.parent.frames.lFrame.document.getElementById('id').value;
			  	Lab_Store_I_Edt.Lab_Type.value = Lab_Store_I_Edt.Lab_Type_Chg.value.substring(0,4);
			  	Lab_Store_I_Edt.Lab_Mode.value = Lab_Store_I_Edt.Lab_Type_Chg.value.substring(4,8);
					Lab_Store_I_Edt.submit();
			  }
			break;
		case 2:
				var Status = '';
				for(var i=1; i<=2; i++)
				{
					if(document.getElementById('radio'+i).checked)
						Status = document.getElementById('radio'+i).value;
				}
				if(Status.length < 1)
				{
					alert('��ѡ����˽��״̬!');
					return;
				}
				if(confirm("��Ϣ����ȷ���ύ?"))
			  {
			  	if(document.getElementById('checkbox1').checked)
					  Lab_Store_I_Edt.Cmd.value  = 13;
					else
						Lab_Store_I_Edt.Cmd.value  = 12;
						
			  	//Lab_Store_I_Edt.Cpm_Id.value = parent.window.parent.frames.lFrame.document.getElementById('id').value;
					Lab_Store_I_Edt.Status.value = Status;
					Lab_Store_I_Edt.submit();
			  }
			break;
	}
}
</SCRIPT>
</html>