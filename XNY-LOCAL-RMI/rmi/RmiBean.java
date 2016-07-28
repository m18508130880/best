package rmi;

import java.io.Serializable;
import java.sql.ResultSet;
import util.*;

/**RmiBean ʵ���� serializable �����л��ӿ�
 * @author Cui
 * bean������඼�̳� RmiBean
 * ΪʲôҪ�����л�?
 *     ���Ҫͨ��Զ�̵ķ������ã�RMI��ȥ����һ��Զ�̶���ķ��������ڼ����A�е���
 *     ��һ̨�����B�Ķ���ķ�������ô����Ҫͨ��JNDI�����ȡ�����BĿ���������ã�
 *     �������B���͵�A������Ҫʵ�����л��ӿ�
 */
public abstract class RmiBean implements Serializable
{
	public final static String UPLOAD_PATH = "/www/XNY-LOCAL/XNY-LOCAL-WEB/files/excel/";
	
	/**************************system**********************/
	public static final int RMI_MANUFACTURER            = 1;
	public static final int RMI_DEVICE_INFO             = 2;
	public static final int RMI_DEVICE_CMD              = 3;
	public static final int RMI_DEVICE_ATTR             = 4;
	public static final int RMI_DATA_TYPE               = 5;
	
	/**************************admin***********************/
	public static final int RMI_CORP_INFO			    = 11;
	public static final int RMI_DEVICE_DETAIL			= 12;
	public static final int RMI_USER_INFO			 	= 13;
	public static final int RMI_USER_ROLE		   	    = 14;
	public static final int RMI_USER_POSITION			= 18;
	public static final int RMI_CRM_INFO		    	= 27;
	public static final int RMI_CCM_INFO		    	= 28;
	
	/**************************user-data*******************/
	public static final int RMI_DATA			        = 30;
	public static final int RMI_ALARM			        = 31;
	public static final int RMI_ALERT			        = 32;
	public static final int RMI_PRO_I			        = 33;
	public static final int RMI_PRO_O			        = 34;
	public static final int RMI_PRO_L			        = 35;
	public static final int RMI_PRO_R			        = 50;
	public static final int RMI_PRO_L_CRM			    = 62;
	public static final int RMI_PRO_L_CRP			    = 63;
	public static final int RMI_P_L_CRM                 = 64;
	public static final int RMI_DATE_BAO		        = 67;//�¼ӳ�վ�ձ���Ϣ��
	public static final int RMI_PRO_I_TANK		        = 68;//�¼�ж�����ޱ�
	
	
	public MsgBean msgBean = null;
	public String className;
	public CurrStatus currStatus = null;
	
	public RmiBean()
	{
		msgBean = new MsgBean(); 		
	}
	public String getClassName()
	{
		return className;
	}
	
	public abstract long getClassId();
	public abstract String getSql(int pCmd);
	public abstract boolean getData(ResultSet pRs);
}
