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

/** 0ȫ����ѯ
 *  2���� 
 *  3�޸�
 *  4ɾ�� 
 *  10��19������ѯ
 * @author cui
 */
public class MainServlet extends HttpServlet
{
	public  final static long serialVersionUID = 1000;
	private Rmi           m_Rmi   = null;
	private String        rmiUrl  = null;
	private Connect       connect = null;
	public  ServletConfig Config;
	
	/* ��ȡ ServletConfig (non-Javadoc)
	 * @see javax.servlet.GenericServlet#getServletConfig()
	 */
	public final ServletConfig getServletConfig() 
	{
		return Config;
	}
	
	/*  (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig pConfig) throws ServletException
	{	
		Config   = pConfig;
		connect  = new Connect();
		connect.config = pConfig;
		connect.ReConnect();
	}		
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
        	request.getSession().removeAttribute("Device_Detail_" + strSid);
        	request.getSession().removeAttribute("User_Info_" + strSid);
        	request.getSession().removeAttribute("User_Stat_" + strSid);
        	request.getSession().removeAttribute("FP_Role_" + strSid);
        	request.getSession().removeAttribute("Manage_Role_" + strSid);
        	request.getSession().removeAttribute("FP_Info_" + strSid);
        	request.getSession().removeAttribute("User_Position_" + strSid);
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
        	request.getSession().removeAttribute("Pro_R_" + strSid);       	
        	request.getSession().removeAttribute("Pro_R_Buss_" + strSid);
        	request.getSession().removeAttribute("Pro_R_Type_" + strSid);
        	request.getSession().removeAttribute("Pro_I_" + strSid);
        	request.getSession().removeAttribute("Pro_O_" + strSid);
        	request.getSession().removeAttribute("Pro_L_" + strSid);
        	request.getSession().removeAttribute("Pro_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_W_" + strSid);
        	request.getSession().removeAttribute("Pro_Y_W_" + strSid);
        	request.getSession().removeAttribute("Pro_C_W_" + strSid);
        	request.getSession().removeAttribute("Pro_L_D_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crm_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_Y_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_D_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_D_Crm_" + strSid);
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
        	request.getSession().removeAttribute("Pro_R_" + strSid);
        	request.getSession().removeAttribute("Pro_R_Buss_" + strSid);
        	request.getSession().removeAttribute("Pro_R_Type_" + strSid);
        	request.getSession().removeAttribute("Pro_I_" + strSid);
        	request.getSession().removeAttribute("Pro_O_" + strSid);
        	request.getSession().removeAttribute("Pro_L_" + strSid);
        	request.getSession().removeAttribute("Pro_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_W_" + strSid);
        	request.getSession().removeAttribute("Pro_Y_W_" + strSid);
        	request.getSession().removeAttribute("Pro_C_W_" + strSid);
        	request.getSession().removeAttribute("Pro_L_D_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crm_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_M_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_Y_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_C_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_W_Y_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_D_" + strSid);
        	request.getSession().removeAttribute("Pro_L_Crp_D_Crm_" + strSid);
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
        	response.getWriter().write("<script language = javascript>window.parent.location.href='../../index.jsp'</script>");
        }
        
        /***************************************����***************************************************/
        else if (strUrl.equalsIgnoreCase("Login.do"))						     //��¼
        	new UserInfoBean().Login(request, response, m_Rmi);
        else if (strUrl.equalsIgnoreCase("PwdEdit.do"))						 	 //�����޸�
        	new UserInfoBean().PwdEdit(request, response, m_Rmi);
        
        /**************************************admin**************************************************/
        else if (strUrl.equalsIgnoreCase("Corp_Info.do"))				     	 //��˾��Ϣ
        	new CorpInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Device_Detail.do"))				     //վ����Ϣ
        	new DeviceDetailBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Device_doDragging.do"))				 //վ����Ϣ-��ͼ��ק�ӿ�
        	new DeviceDetailBean().doDragging(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Info.do"))				         //��Ա��Ϣ
        	new UserInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("IdCheck.do"))						 	 //��Ա��Ϣ-�ʺż��
        	new UserInfoBean().IdCheck(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Role.do"))				         //��ԱȨ��
        	new UserRoleBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_RoleOP.do"))				     	 //��ԱȨ��-�༭
        	new UserRoleBean().RoleOP(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Position.do"))				 	 //��Ա��Ϣ-��λ����
        	new UserPositionBean().ExecCmd(request, response, m_Rmi, false);
        
        /**************************************user-ToPo*********************************************/
        else if (strUrl.equalsIgnoreCase("ToPo.do"))						     //GIS���
        	new DeviceDetailBean().ToPo(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("GIS_Deal.do"))	                     //GIS���-�澯����
        	new AlertInfoBean().GISDeal(request, response, m_Rmi, false);
        
        /**************************************user-log***************************************************/ 
       	else if (strUrl.equalsIgnoreCase("Alarm_Info.do"))	                 	 //������־
        	new AlarmInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alarm_Info_Export.do"))	             //������־-����
        	new AlarmInfoBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Info.do"))	                 	 //�澯��־
        	new AlertInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Info_Export.do"))	             //�澯��־-����
        	new AlertInfoBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Deal.do"))	                 	 //�澯����
        	new AlertInfoBean().Deal(request, response, m_Rmi, false);
        
        /**************************************user-env**********************************************/
        else if (strUrl.equalsIgnoreCase("Env.do"))						     	 //ʵʱ����
        	new DataBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Env_His_Export.do"))	             	 //��ʷ��ϸ-����
        	new DataBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Graph.do"))	                         //����ͼ��
        	new DataBean().Graph(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Env_File.do"))						 //ͼƬ�ϴ�
        	new DataBean().DaoFile(request, response, m_Rmi, false);
        
        /**************************************user-pro***************************************************/
        else if (strUrl.equalsIgnoreCase("Pro_R.do"))	                         //ʵʱ���
        	new ProRBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_R_Export.do"))	                 //ʵʱ���-����
        	new ProRBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_R_Date.do"))	                     //ʵʱ�����Ӳ�ѯ
        	new ProRBean().ExDate(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I.do"))	                         //ж����¼
        	new ProIBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I_Export.do"))	                 //ж����¼-����
        	new ProIBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I_Detail_Export.do"))	             //ж����¼-��ϸ����
        	new ProIBean().MxToExcel(request, response, m_Rmi, false);                 
        else if (strUrl.equalsIgnoreCase("Pro_I_Add.do"))	                     //ж����¼-���
        	new ProIBean().ProIAdd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O.do"))	                         //��ע��¼
        	new ProOBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Export.do"))	                 //��ע��¼-����
        	new ProOBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Add.do"))	                     //��ע��¼-���
        	new ProOBean().ProOAdd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_File.do"))	                     //��ע��¼-���ɱ���
        	new ProOBean().DoTJ(request, response, m_Rmi, false);       
        else if (strUrl.equalsIgnoreCase("Pro_O_Date.do"))	                     //��ע��¼-�ձ���ѯ
        	new DateBaoBean().WxhDate(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Del.do"))	                     //��ע��¼ɾ��
        	new ProOBean().doDel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Id_Car.do"))	                     //��ע��¼-������Ϣ��ѯ
        	new CcmInfoBean().IdCar(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L.do"))	                         //��վ����
        	new ProLBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Y.do"))	                         //��վ����-�걨��
        	new ProLBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Export.do"))	                 //��վ����-�±���
        	new ProLBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_W_Export.do"))	             	 //��վ����-�ܱ���
        	new ProLBean().ExportToExcel_W(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_D_Export.do"))	             	 //��վ����-�ձ���
        	new ProLBean().ExportToExcel_D(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Y_Export.do"))	             	 //��վ����-�걨����
        	new ProLBean().ExportToExcel_Y(request, response, m_Rmi, false);        
        else if (strUrl.equalsIgnoreCase("Pro_G.do"))	             	 		 //��վ����-ͼ�����
        	new ProLBean().Pro_G(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp.do"))	                     //��˾����
        	new ProLCrpBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_Y_Export.do"))	             //��˾����-�걨��
        	new ProLCrpBean().ExportToExcel_Y(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_M_Export.do"))	             //��˾����-�±���
        	new ProLCrpBean().ExportToExcel_M(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_W_Export.do"))	             //��˾����-�ܱ���
        	new ProLCrpBean().ExportToExcel_W(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_D_Export.do"))	             //��˾����-�ձ���
        	new ProLCrpBean().ExportToExcel_D(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Pro_Crp_G.do"))	             	     //��˾����-ͼ�����
        	new ProLCrpBean().Pro_Crp_G(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crm.do"))	                     //�ͻ�������ȷ��
        	new ProLCrmBean().ExecCmd(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Pro_L_Crm_Export.do"))	             //�������˱���
        	new ProLCrmBean().DZBExcel(request, response, m_Rmi, false);                
        else if (strUrl.equalsIgnoreCase("Pro_GX_ZYB.do"))	                     //����ͳ��վ���±���
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_GX_ZNB.do"))	                     //����ͳ��վ���걨��
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_GX_GYB.do"))	                     //����ͳ�ƹ�˾�±���
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_GX_GNB.do"))	                     //����ͳ�ƹ�˾�걨��
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Stat.do"))	                     //���˵�
        	new ProOBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_I_CC.do"))	                     //�۳�ͳ�Ʊ�
        	new ProIBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Stat_Export.do"))	                 //���˱���
        	new ProOBean().DZBExcel(request, response, m_Rmi, false);              
        else if (strUrl.equalsIgnoreCase("Pro_L_D_Add.do"))	                     //��վ�����ձ���������Ϣ���
        	new DateBaoBean().ExecCmd(request, response, m_Rmi, false);
    }
    
    /** ���Ӳ�����
     * @author iwant
     *
     */
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
    	    		ok = m_Rmi.Test(); //��RMI���� ���ɹ��򷵻� true
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
    			Context context = new InitialContext(); //��ʼ��jndi
    			m_Rmi = (Rmi) context.lookup(rmiUrl);
    		}
    		catch(Exception e)
    		{	
    			e.printStackTrace();
    		}
    	}
    }
	
    /** �õ���ǰ��Ŀ�� URL 
     * @param request
     * @return
     */
    public final static String getUrl(HttpServletRequest request)
	{
    	// http://121.41.52.236/xny/
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		return url;
	}
	
} 