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

/** 0全部查询
 *  2插入 
 *  3修改
 *  4删除 
 *  10～19单个查询
 * @author cui
 */
public class MainServlet extends HttpServlet
{
	public  final static long serialVersionUID = 1000;
	private Rmi           m_Rmi   = null;
	private String        rmiUrl  = null;
	private Connect       connect = null;
	public  ServletConfig Config;
	
	/* 获取 ServletConfig (non-Javadoc)
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
    		request.getSession().setAttribute("ErrMsg", CommUtil.StrToGB2312("RMI服务端未正常运行，无法登陆！"));
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
        
        //首页
        if(strUrl.equals("index.do"))
        {
        	CheckCode.CreateCheckCode(request, response, strSid);
        	return;
        }
        else if(strUrl.equalsIgnoreCase("AdminILogout.do"))                      //第二层:admin安全退出
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
        else if(strUrl.equalsIgnoreCase("ILogout.do"))                           //第二层:user安全退出
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
        else if(strUrl.equalsIgnoreCase("IILogout.do"))                          //第三层:user安全退出
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
        
        /***************************************公用***************************************************/
        else if (strUrl.equalsIgnoreCase("Login.do"))						     //登录
        	new UserInfoBean().Login(request, response, m_Rmi);
        else if (strUrl.equalsIgnoreCase("PwdEdit.do"))						 	 //密码修改
        	new UserInfoBean().PwdEdit(request, response, m_Rmi);
        
        /**************************************admin**************************************************/
        else if (strUrl.equalsIgnoreCase("Corp_Info.do"))				     	 //公司信息
        	new CorpInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Device_Detail.do"))				     //站级信息
        	new DeviceDetailBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Device_doDragging.do"))				 //站级信息-地图拖拽接口
        	new DeviceDetailBean().doDragging(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Info.do"))				         //人员信息
        	new UserInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("IdCheck.do"))						 	 //人员信息-帐号检测
        	new UserInfoBean().IdCheck(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Role.do"))				         //人员权限
        	new UserRoleBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_RoleOP.do"))				     	 //人员权限-编辑
        	new UserRoleBean().RoleOP(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("User_Position.do"))				 	 //人员信息-岗位管理
        	new UserPositionBean().ExecCmd(request, response, m_Rmi, false);
        
        /**************************************user-ToPo*********************************************/
        else if (strUrl.equalsIgnoreCase("ToPo.do"))						     //GIS监控
        	new DeviceDetailBean().ToPo(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("GIS_Deal.do"))	                     //GIS监控-告警处理
        	new AlertInfoBean().GISDeal(request, response, m_Rmi, false);
        
        /**************************************user-log***************************************************/ 
       	else if (strUrl.equalsIgnoreCase("Alarm_Info.do"))	                 	 //联动日志
        	new AlarmInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alarm_Info_Export.do"))	             //联动日志-导出
        	new AlarmInfoBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Info.do"))	                 	 //告警日志
        	new AlertInfoBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Info_Export.do"))	             //告警日志-导出
        	new AlertInfoBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Alert_Deal.do"))	                 	 //告警处理
        	new AlertInfoBean().Deal(request, response, m_Rmi, false);
        
        /**************************************user-env**********************************************/
        else if (strUrl.equalsIgnoreCase("Env.do"))						     	 //实时数据
        	new DataBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Env_His_Export.do"))	             	 //历史明细-导出
        	new DataBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Graph.do"))	                         //数据图表
        	new DataBean().Graph(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Env_File.do"))						 //图片上传
        	new DataBean().DaoFile(request, response, m_Rmi, false);
        
        /**************************************user-pro***************************************************/
        else if (strUrl.equalsIgnoreCase("Pro_R.do"))	                         //实时库存
        	new ProRBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_R_Export.do"))	                 //实时库存-导出
        	new ProRBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_R_Date.do"))	                     //实时库存添加查询
        	new ProRBean().ExDate(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I.do"))	                         //卸车记录
        	new ProIBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I_Export.do"))	                 //卸车记录-导出
        	new ProIBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_I_Detail_Export.do"))	             //卸车记录-明细导出
        	new ProIBean().MxToExcel(request, response, m_Rmi, false);                 
        else if (strUrl.equalsIgnoreCase("Pro_I_Add.do"))	                     //卸车记录-添加
        	new ProIBean().ProIAdd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O.do"))	                         //加注记录
        	new ProOBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Export.do"))	                 //加注记录-导出
        	new ProOBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Add.do"))	                     //加注记录-添加
        	new ProOBean().ProOAdd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_File.do"))	                     //加注记录-生成报表
        	new ProOBean().DoTJ(request, response, m_Rmi, false);       
        else if (strUrl.equalsIgnoreCase("Pro_O_Date.do"))	                     //加注记录-日报查询
        	new DateBaoBean().WxhDate(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_O_Del.do"))	                     //加注记录删除
        	new ProOBean().doDel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Id_Car.do"))	                     //加注记录-车辆信息查询
        	new CcmInfoBean().IdCar(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L.do"))	                         //场站报表
        	new ProLBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Y.do"))	                         //场站报表-年报表
        	new ProLBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Export.do"))	                 //场站报表-月报表
        	new ProLBean().ExportToExcel(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_W_Export.do"))	             	 //场站报表-周报表
        	new ProLBean().ExportToExcel_W(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_D_Export.do"))	             	 //场站报表-日报表
        	new ProLBean().ExportToExcel_D(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Y_Export.do"))	             	 //场站报表-年报表导出
        	new ProLBean().ExportToExcel_Y(request, response, m_Rmi, false);        
        else if (strUrl.equalsIgnoreCase("Pro_G.do"))	             	 		 //场站报表-图表分析
        	new ProLBean().Pro_G(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp.do"))	                     //公司报表
        	new ProLCrpBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_Y_Export.do"))	             //公司报表-年报表
        	new ProLCrpBean().ExportToExcel_Y(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_M_Export.do"))	             //公司报表-月报表
        	new ProLCrpBean().ExportToExcel_M(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_W_Export.do"))	             //公司报表-周报表
        	new ProLCrpBean().ExportToExcel_W(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crp_D_Export.do"))	             //公司报表-日报表
        	new ProLCrpBean().ExportToExcel_D(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Pro_Crp_G.do"))	             	     //公司报表-图表分析
        	new ProLCrpBean().Pro_Crp_G(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Crm.do"))	                     //客户销售量确认
        	new ProLCrmBean().ExecCmd(request, response, m_Rmi, false); 
        else if (strUrl.equalsIgnoreCase("Pro_L_Crm_Export.do"))	             //次数对账表导出
        	new ProLCrmBean().DZBExcel(request, response, m_Rmi, false);                
        else if (strUrl.equalsIgnoreCase("Pro_GX_ZYB.do"))	                     //购销统计站级月报表
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_GX_ZNB.do"))	                     //购销统计站级年报表
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_GX_GYB.do"))	                     //购销统计公司月报表
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_GX_GNB.do"))	                     //购销统计公司年报表
        	new ProGxtjBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_L_Stat.do"))	                     //对账单
        	new ProOBean().ExecCmd(request, response, m_Rmi, false);  
        else if (strUrl.equalsIgnoreCase("Pro_I_CC.do"))	                     //槽车统计表
        	new ProIBean().ExecCmd(request, response, m_Rmi, false);
        else if (strUrl.equalsIgnoreCase("Pro_Stat_Export.do"))	                 //对账表导出
        	new ProOBean().DZBExcel(request, response, m_Rmi, false);              
        else if (strUrl.equalsIgnoreCase("Pro_L_D_Add.do"))	                     //场站报表日报表其他信息添加
        	new DateBaoBean().ExecCmd(request, response, m_Rmi, false);
    }
    
    /** 链接测试类
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
    	    		ok = m_Rmi.Test(); //与RMI交互 ，成功则返回 true
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
    			Context context = new InitialContext(); //初始化jndi
    			m_Rmi = (Rmi) context.lookup(rmiUrl);
    		}
    		catch(Exception e)
    		{	
    			e.printStackTrace();
    		}
    	}
    }
	
    /** 得到当前项目的 URL 
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