package servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rmi.*;
import util.*;
import bean.*;

////0ȫ����ѯ 2���� 3�޸� 4ɾ�� 10��19������ѯ
public class MainServlet extends HttpServlet
{
	public final static long serialVersionUID = 1000;
	private Rmi m_Rmi = null;
	private String rmiUrl = null;
	private Connect connect = null;
	public ServletConfig Config;
	
	public final ServletConfig getServletConfig() 
	{
		return Config;
	}
	
	public void init(ServletConfig pConfig) throws ServletException
	{	
		Config = pConfig;
		connect = new Connect();
		connect.config = pConfig;
		connect.ReConnect();
	}		
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    protected void doPut(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    protected void doTrace(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    

    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
    	if(connect.Test()== false)
    	{   
    		request.getSession().setAttribute("ErrMsg", CommUtil.StrToGB2312("RMI�����δ�������У��޷���½��"));
    		response.sendRedirect(getUrl(request) + "error.jsp");
    		return;
    	}
    	
        response.setContentType("text/html; charset=gbk");
        String strUrl = request.getRequestURI();
        String strSid = request.getParameter("Sid");
        String[] str = strUrl.split("/");
        strUrl = str[str.length - 1];
        
        System.out.println("Sid:" + strSid);
        System.out.println("====================" + strUrl);
        
        //��ҳ
        if(strUrl.equals("index.do"))
        {
        	CheckCode.CreateCheckCode(request, response, strSid);
        	return;
        }
        else if(strUrl.equalsIgnoreCase("AdminILogout.do"))                      //�ڶ���:admin��ȫ�˳�
        {
        	request.getSession().removeAttribute("CurrStatus_" + strSid);
        	request.getSession().removeAttribute("Admin_" + strSid);
        	request.getSession().removeAttribute("Corp_Info_" + strSid);
        	request.getSession().removeAttribute("User_Info_" + strSid);
        	request.getSession().removeAttribute("User_Stat_" + strSid);
        	request.getSession().removeAttribute("FP_Role_" + strSid);
        	request.getSession().removeAttribute("Manage_Role_" + strSid);
        	request.getSession().removeAttribute("FP_Info_" + strSid);
        	request.getSession().removeAttribute("Crm_Info_" + strSid);
        	request.getSession().removeAttribute("Ccm_Info_" + strSid);
        	//request.getSession().invalidate();
        	response.getWriter().write("<script language = javascript>window.parent.location.href='../index.jsp'</script>");
        }
        else if(strUrl.equalsIgnoreCase("ILogout.do"))                           //�ڶ���:user��ȫ�˳�
        {
        	request.getSession().removeAttribute("CurrStatus_" + strSid);
        	request.getSession().removeAttribute("UserInfo_" + strSid);
        	request.getSession().removeAttribute("User_Corp_Info_" + strSid);
        	request.getSession().removeAttribute("User_Data_Attr_" + strSid);
        	request.getSession().removeAttribute("User_User_Info_" + strSid);
        	request.getSession().removeAttribute("User_FP_Role_" + strSid);
        	request.getSession().removeAttribute("User_Manage_Role_" + strSid);
        	request.getSession().removeAttribute("Env_" + strSid);
        	request.getSession().removeAttribute("Env_His_" + strSid);
        	request.getSession().removeAttribute("Week_" + strSid);
        	request.getSession().removeAttribute("Month_" + strSid);
        	request.getSession().removeAttribute("Year_" + strSid);
        	request.getSession().removeAttribute("Graph_" + strSid);
        	request.getSession().removeAttribute("Alarm_Info_" + strSid);
        	request.getSession().removeAttribute("Alert_Info_" + strSid);    	
        	request.getSession().removeAttribute("BYear_" + strSid);
        	request.getSession().removeAttribute("BMonth_" + strSid);
        	request.getSession().removeAttribute("BWeek_" + strSid);
        	request.getSession().removeAttribute("EYear_" + strSid);
        	request.getSession().removeAttribute("EMonth_" + strSid);
        	request.getSession().removeAttribute("EWeek_" + strSid);
        	request.getSession().removeAttribute("BDate_" + strSid);
        	request.getSession().removeAttribute("EDate_" + strSid);
        	request.getSession().removeAttribute("Pro_G_" + strSid);        
        	//request.getSession().invalidate();
        	response.getWriter().write("<script language = javascript>window.parent.location.href='../index.jsp'</script>");
        }
        else if(strUrl.equalsIgnoreCase("IILogout.do"))                          //������:user��ȫ�˳�
        {
        	request.getSession().removeAttribute("CurrStatus_" + strSid);
        	request.getSession().removeAttribute("UserInfo_" + strSid);
        	request.getSession().removeAttribute("User_Corp_Info_" + strSid);
        	request.getSession().removeAttribute("User_Device_Detail_" + strSid);
        	request.getSession().removeAttribute("User_Data_Device_" + strSid);
        	request.getSession().removeAttribute("User_Data_Attr_" + strSid);
        	request.getSession().removeAttribute("User_User_Info_" + strSid);
        	request.getSession().removeAttribute("User_FP_Role_" + strSid);
        	request.getSession().removeAttribute("User_Manage_Role_" + strSid);
        	request.getSession().removeAttribute("Env_" + strSid);
        	request.getSession().removeAttribute("Env_His_" + strSid);
        	request.getSession().removeAttribute("Week_" + strSid);
        	request.getSession().removeAttribute("Month_" + strSid);
        	request.getSession().removeAttribute("Year_" + strSid);
        	request.getSession().removeAttribute("Graph_" + strSid);
        	request.getSession().removeAttribute("Alarm_Info_" + strSid);
        	request.getSession().removeAttribute("Alert_Info_" + strSid);
        	request.getSession().removeAttribute("BYear_" + strSid);
        	request.getSession().removeAttribute("BMonth_" + strSid);
        	request.getSession().removeAttribute("BWeek_" + strSid);
        	request.getSession().removeAttribute("EYear_" + strSid);
        	request.getSession().removeAttribute("EMonth_" + strSid);
        	request.getSession().removeAttribute("EWeek_" + strSid);
        	request.getSession().removeAttribute("BDate_" + strSid);
        	request.getSession().removeAttribute("EDate_" + strSid);       
        	//request.getSession().invalidate();
        	response.getWriter().write("<script language = javascript>window.parent.location.href='../../index.jsp'</script>");
        }
        
        /**************************************����***************************************************/
        else if (strUrl.equalsIgnoreCase("Login.do"))						         //��¼
        	new UserInfoBean().Login(request, response, m_Rmi);
        else if (strUrl.equalsIgnoreCase("PwdEdit.do"))						 	     //�����޸�
        	new UserInfoBean().PwdEdit(request, response, m_Rmi);
        
        /**************************************admin***************************************************/
        else if (strUrl.equalsIgnoreCase("Admin_Corp_Info.do"))				         //��˾��Ϣ
        	new CorpInfoBean().ExecCmd(request, response, m_Rmi, false);             
        else if (strUrl.equalsIgnoreCase("Admin_User_Info.do"))				         //��Ա��Ϣ
        	new UserInfoBean().ExecCmd(request, response, m_Rmi, false);       
        else if (strUrl.equalsIgnoreCase("Admin_IdCheck.do"))						 //��Ա��Ϣ-�ʺż��
        	new UserInfoBean().IdCheck(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Admin_User_Role.do"))				         //��ԱȨ��
        	new UserRoleBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Admin_User_RoleOP.do"))				     //��ԱȨ��-�༭
        	new UserRoleBean().RoleOP(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Admin_Project_Info.do"))	                 //��Ŀ��Ϣ����
        	new ProjectInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Project_IdCheck.do"))						 //��ĿID���
        	new ProjectInfoBean().IdCheck(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Admin_Equip_Info.do"))	                 //�豸��Ϣ����
        	new EquipInfoBean().ExecCmd(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Equip_IdCheck.do"))						 //�豸ID���
        	new EquipInfoBean().IdCheck(request, response, m_Rmi, false);

        /**************************************admin-�ܾ�**********************************************/  
        else if (strUrl.equalsIgnoreCase("Admin_ToPo_GJ.do"))					//GIS���-�ܾ�
        	new DevGJBean().ToPo(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Admin_Drag_GJ.do"))					//GIS���-�ܾ�-��������
        	new DevGJBean().doDragging(request, response, m_Rmi, false);          
        else if (strUrl.equalsIgnoreCase("Admin_DevGJ_Info.do"))				//�ܾ���ѯ
        	new DevGJBean().ExecCmd(request, response, m_Rmi, false);           
        else if (strUrl.equalsIgnoreCase("Admin_File_GJ.do"))					//Excel�����루�ɣ�
        	new DevGJBean().GJExcel(request, response, m_Rmi, false, Config);
        else if (strUrl.equalsIgnoreCase("Admin_Import_GJ.do"))					//Excel������ܾ����£�
        	new DevGJBean().ImportExcel(request, response, m_Rmi, false, Config);
        else if (strUrl.equalsIgnoreCase("Admin_Import_GD.do"))					//Excel������ܵ����£�
        	new DevGXBean().ImportExcel(request, response, m_Rmi, false, Config);
        else if (strUrl.equalsIgnoreCase("Admin_Update_GJ.do"))					//Excel�����¹ܾ����£�
        	new DevGJBean().UpdateExcel(request, response, m_Rmi, false, Config);
        else if (strUrl.equalsIgnoreCase("Admin_Update_GD.do"))					//Excel�����¹ܵ����£�
        	new DevGXBean().UpdateExcel(request, response, m_Rmi, false, Config);
        else if (strUrl.equalsIgnoreCase("Admin_File_GJ_Export.do"))			//�ܾ�Excel������
        	new DevGJBean().XLQRExcel(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Admin_File_GX_Export.do"))			//����Excel������
        	new DevGXBean().XLQRExcel(request, response, m_Rmi, false);  
   
       /***************************************admin-����**********************************************/ 
        else if (strUrl.equalsIgnoreCase("Admin_ToPo_GX.do"))						 //GIS���-����
        	new DevGXBean().ToPo(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Admin_DevGX_Info.do"))			         //���߲�ѯ
        	new DevGXBean().ExecCmd(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Admin_DevGX_Suggest.do"))			         //���߲�ѯ
        	new DevGXBean().GXSuggest(request, response, m_Rmi, false); 
        
        /**************************************user-�ܾ�**********************************************/  
        else if (strUrl.equalsIgnoreCase("User_ToPo_GJ.do"))				        //GIS���-�ܾ�
        	new DevGJBean().ToPo(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_ToPo_GX.do"))			            //GIS���-����
        	new DevGXBean().ToPo(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_DevGJ_Info.do"))				        //�ܾ���ѯ
        	new DevGJBean().ExecCmd(request, response, m_Rmi, false);    
        else if (strUrl.equalsIgnoreCase("User_DevGX_Info.do"))				        //���߲�ѯ
        	new DevGXBean().ExecCmd(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("User_Equip_Info.do"))	                    //�豸��ѯ
        	new EquipInfoBean().ExecCmd(request, response, m_Rmi, false); 
    
        else if (strUrl.equalsIgnoreCase("User_DataGJ_His.do"))				        //�ܾ���������
        	new DataGJBean().HistoryData(request, response, m_Rmi, false);
        
        else if (strUrl.equalsIgnoreCase("User_DataGX_His.do"))				        //���߱�������
        	new DataGXBean().HistoryData(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Announce.do"))				        //tab������ʾ
        	new CorpInfoBean().ExecCmd(request, response, m_Rmi, false);    
        
        /***************************************ͼ������*****************************************************/
        else if (strUrl.equalsIgnoreCase("User_Graph_Cut.do"))				        //�ܶ�����ͼ
        	new DevGXBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("User_Graph_Curve.do"))				    //�ܾ�����ͼ
        	new DataGJBean().GraphData(request, response, m_Rmi, false);         
    }
    
    private class Connect extends Thread
	{
    	private ServletConfig config = null;
    	public boolean Test()
    	{
    		int i = 0;
        	boolean ok = false;
        	while(3 > i)
    		{        		
    	    	try
    			{   
    	    		if(i != 0) sleep(500);
    	    		ok = m_Rmi.Test();
    	    		i = 3;
    	    		ok = true;
    			}
    	    	catch(Exception e)
    			{    	    		
    	    		ReConnect();
    	    		i++;
    			}
    		}
    		return ok;
    	}
    	private void ReConnect()
    	{
    		try
    		{
    			rmiUrl = config.getInitParameter("rmiUrl");
    			Context context = new InitialContext();
    			m_Rmi = (Rmi) context.lookup(rmiUrl);
    		}
    		catch(Exception e)
    		{	
    			e.printStackTrace();
    		}
    	}
    }
	public final static String getUrl(HttpServletRequest request)
	{
		String url = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		return url;
	}
	
} 