/*
 * 0:��ѯȫ�� 
 * 1����ѯ���� 
 * 2������Insert 
 * 3���޸�update case 
 * 4: ɾ�� delete
 * 5: ִ�к���
 * */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;//����ִ��sql�洢���̵Ľӿ�

import util.*;

import java.util.*;

import bean.*;
import oracle.jdbc.OracleTypes;

/** RmiImpl implements Rmi 
 * @author Cui
 * �Ժ󿴵�Rmi, ������ô����
 *  Rmi pRmi = new RmiImpl();
 *  
 * 0: ��ѯ 
 * 1������,����,ɾ��  
 * 2��Function����
 * 3��Package����
 * 4: Producer����
 */
public class RmiImpl extends UnicastRemoteObject implements Rmi
{
	public final static long serialVersionUID = 1001;
	
	private DBUtil m_DBUtil = null;	
	
	/** �ղι�����
	 * @throws RemoteException
	 */
	public RmiImpl() throws RemoteException {
		
	}
	
	/** ��ʼ�����ݿ����ӳ�
	 * @param pDbUtil
	 */
	public void Init(DBUtil pDbUtil) {
		m_DBUtil = pDbUtil;
	}
	
	/** RMI����
	 * @see rmi.Rmi#Test()
	 */
	public boolean Test()throws RemoteException {
		return true;
	}
	
	/** ����cmd���� , �����pBean , CurrPage��ǰҳ�� 
	 **case 0: ��ѯ ;
	 * case 1: ��ɾ�� ;
	 * case 2: Function�������� ;
	 * case 3: Package���� ;   ???oracle.jdbc???
	 * case 4: Producer���� ;
	 * @see rmi.Rmi#RmiExec(int, rmi.RmiBean, int, int)
	 */
	public MsgBean RmiExec(int pCmd, RmiBean pBean, int CurrPage) throws RemoteException
	{
		MsgBean objBean = null;
		ArrayList<?> aList = null;
		String Sql = pBean.getSql(pCmd);		
		int recordCount = 0;
		System.out.println("ClassId:["+pBean.getClassId()+"]"+" ClassName["+pBean.getClassName()+"]"+" Cmd["+ pCmd + "]");
		System.out.println("BSql["+Sql+"]");
		switch(pCmd/10)
		{
			case 0://��ѯ
				if(0 < CurrPage) //����: ��Ҫ��ҳ��ʾʱ,�Ż�ȥ����recordCountֵ  ��ѯ��¼����
				{  
					recordCount = Integer.parseInt(doRecordCount("select count(*) as counts from (" + Sql + ")subselect"));
					System.out.println("RecordCount["+recordCount+"]");
					//��ҳ: �з�ҳ��ʾ,��̨RMI�� �Ż�������´�ӡ
					Sql = Sql + " LIMIT " + MsgBean.CONST_PAGE_SIZE + " OFFSET " + (CurrPage-1)*MsgBean.CONST_PAGE_SIZE;
					System.out.println("ESql["+Sql+"]");
					System.out.println("����["+MsgBean.CONST_PAGE_SIZE+"]");
				}				
				aList = doSelect(Sql, pBean.getClassId());
				if(aList != null && aList.size() > 0) //�������ݷ���  �����ݷ�װ��MsgBean��ȥ.
				{
					objBean = new MsgBean(MsgBean.STA_SUCCESS,  aList, recordCount);
				}
				else                                 //�������ݷ���  MsgBean�еļ��϶�����Ϊ��.
				{
					objBean = new MsgBean(MsgBean.STA_FAILED,  null, recordCount);
				}
				break;
			case 1://����,����,ɾ��				
				if(doUpdate(Sql))    
				{		
					objBean = new MsgBean(MsgBean.STA_SUCCESS, null, recordCount);
				}
				else
				{
					objBean = new MsgBean(MsgBean.STA_FAILED, CommUtil.IntToStringLeftFillSpace(MsgBean.STA_FAILED, 4), recordCount);
				}	
				break;
			case 2://Function����
				String rst = doFunction(Sql); 
				CommUtil.PRINT(rst);
				if(rst != null && rst.substring(0, 4).equals("0000"))    
				{		
					objBean = new MsgBean(MsgBean.STA_SUCCESS, rst, recordCount);
				}
				else
				{
					objBean = new MsgBean(Integer.parseInt(rst.substring(0, 4)), rst.substring(0, 4), recordCount);
				}	
				break;
			case 3://Package����
				aList = Do_Package(Sql, pBean.getClassId());
				if(aList != null && aList.size() > 0)
				{
					objBean = new MsgBean(MsgBean.STA_SUCCESS,  aList, recordCount);
				}
				else
				{
					objBean = new MsgBean(MsgBean.STA_FAILED,  null, recordCount);
				}
				break;
			case 4://Producer����
				doProducer(Sql); 
				rst = "0000";
				CommUtil.PRINT(rst);
				if(rst != null && rst.substring(0, 4).equals("0000"))    
				{		
					objBean = new MsgBean(MsgBean.STA_SUCCESS, rst, recordCount);
				}
				else
				{
					objBean = new MsgBean(Integer.parseInt(rst.substring(0, 4)), rst.substring(0, 4), recordCount);
				}	
				break;
		}			
		return objBean;
	}
	
	/** ���ݿ��ѯ����
	 * @param pSql
	 * @param pClass
	 *    bean����ֵ
	 * @return
	 */
	public ArrayList<?> doSelect(String pSql, long pClass) //Bean Type
	{
		ArrayList<Object> alist = new ArrayList<Object>();
		RmiBean rmiBean = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(pSql);
			rs = pStmt.executeQuery();
			while(rs.next())
			{
				switch((int)pClass)
				{
				/******************system****************/
				/******************admin*****************/
					case RmiBean.RMI_CORP_INFO:
						rmiBean = new CorpInfoBean();
						break;
					case RmiBean.RMI_USER_INFO:
						rmiBean = new UserInfoBean();
						break;
					case RmiBean.RMI_USER_ROLE:
						rmiBean = new UserRoleBean();
						break;
					case RmiBean.RMI_DEVICE_DETAIL:
				 		rmiBean = new DeviceDetailBean();
				 		break;
					
					case RmiBean.RMI_USER_POSITION:
						rmiBean = new UserPositionBean();
						break;
					
					case RmiBean.RMI_CRM_INFO:
						rmiBean = new CrmInfoBean();
						break;
					case RmiBean.RMI_CCM_INFO:
						rmiBean = new CcmInfoBean();
						break;
				/******************user*****************/
					case RmiBean.RMI_DATA:
						rmiBean = new DataBean();
						break;
					case RmiBean.RMI_PRO_I:
						rmiBean = new ProIBean();
						break;
					case RmiBean.RMI_PRO_O:
						rmiBean = new ProOBean();
						break;
					case RmiBean.RMI_PRO_L:
						rmiBean = new ProLBean();
						break;
					case RmiBean.RMI_PRO_R:
						rmiBean = new ProRBean();
						break;
					case RmiBean.RMI_PRO_L_CRM:
						rmiBean = new ProLCrmBean();
						break;
					case RmiBean.RMI_PRO_L_CRP:
						rmiBean = new ProLCrpBean();
						break;
					case RmiBean.RMI_P_L_CRM:
						rmiBean = new PLCrmBean();
						break;
					case RmiBean.RMI_DATE_BAO:
						rmiBean = new DateBaoBean();
						break;
					case RmiBean.RMI_PRO_I_TANK:
						rmiBean = new ProITankBean();
						break;
					case RmiBean.RMI_ALARM:
						rmiBean = new AlarmInfoBean();
						break;
					case RmiBean.RMI_ALERT:
						rmiBean = new AlertInfoBean();
						break;
						
				}
				rmiBean.getData(rs);
				alist.add(rmiBean);
			}
		} catch (SQLException sqlExp)
		{
			sqlExp.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{				
					rs.close();
					rs = null;
				}
				if(pStmt != null)
				{				
					pStmt.close();
					pStmt = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		return alist;
	}
	
	/** ���ݿ�� ��\ɾ\�� ����
	 * @param pSql
	 * @return
	 *     ����boolean,��ʾ�Ƿ�����ɹ�
	 */
	public boolean doUpdate(String pSql)
	{
		boolean IsOK = false;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(pSql);
			if (pStmt.executeUpdate() > 0) 
			{
				IsOK = true;
				conn.commit();
			} 
		} 
		catch (SQLException sqlExp) 
		{
			sqlExp.printStackTrace();
		}
		finally
		{
			try
			{
				if(pStmt != null)
				{				
					pStmt.close();
					pStmt = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		return IsOK;
	}

	/** ���ݿ��Function��������
	 * @param pSql
	 * @return 
	 *     �������ݿ⺯����? 
	 */
	public String doFunction(String pSql)
	{
		String rslt = null;
		Connection conn = null;
		CallableStatement cstat = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			conn.setAutoCommit(false);
			cstat = conn.prepareCall(pSql);
			cstat.registerOutParameter(1, java.sql.Types.VARCHAR);
			cstat.execute();
			rslt = cstat.getString(1);
			conn.commit();
			//System.out.println("rslt:" + rslt);
		}
		catch(SQLException sqlExp)
		{
			sqlExp.printStackTrace();
			return  CommUtil.IntToStringLeftFillSpace(MsgBean.STA_FAILED, 4);
		}
		finally
		{
			try
			{
				if(cstat != null)
				{				
					cstat.close();
					cstat = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return rslt;
	}
	
	/** ���ݿ��Producer�洢���̵���
	 * @param pSql
	 * @return 
	 *     �������ݿ�洢���̵�?����ֵ? 
	 */
	public String doProducer(String pSql)
	{
		String rslt = null;
		Connection conn = null;
		CallableStatement cstat = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			conn.setAutoCommit(false);
			cstat = conn.prepareCall(pSql);
			cstat.execute();
			conn.commit();
			//System.out.println("rslt:" + rslt);
		}
		catch(SQLException sqlExp)
		{
			sqlExp.printStackTrace();
			return  CommUtil.IntToStringLeftFillSpace(MsgBean.STA_FAILED, 4);
		}
		finally
		{
			try
			{
				if(cstat != null)
				{				
					cstat.close();
					cstat = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return rslt;
	}
	
	/** ���ݿ�  ��¼������ѯ
	 * @param pSql
	 * @return String
	 *   ��intֵת��String���ͷ���
	 */
	public String doRecordCount(String pSql)
	{
		String rslt = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(pSql);
			rs = pStmt.executeQuery();		
			while(rs.next())
			{
				rslt = rs.getString("counts");
			}
		} 
		catch (SQLException sqlExp) 
		{
			sqlExp.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{				
					rs.close();
					rs = null;
				}
				if(pStmt != null)
				{				
					pStmt.close();
					pStmt = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		return rslt;
	}
	
	/** oracle.jdbc
	 * @param pSql
	 * @param pClass
	 * @return
	 */
	public ArrayList<?> Do_Package(String pSql, long pClass) 
	{
		ArrayList<Object> alist = new ArrayList<Object>();
		RmiBean rmiBean = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try 
		{
			conn = m_DBUtil.objConnPool.getConnection();
			cstmt = conn.prepareCall(pSql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			while(rs.next())
			{
				switch((int)pClass)
				{
					//case RmiBean.RMI_SURROUNDINGS_INFO:
							//rmiBean = new SurroundingsInfoBean();
						//break;
				}
				rmiBean.getData(rs);						
				alist.add(rmiBean);	
			}
		} catch (SQLException sqlExp) 
		{
		sqlExp.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)
				{				
					rs.close();
					rs = null;
				}
				if(cstmt != null)
				{				
					cstmt.close();
					cstmt = null;
				}
				if(conn != null)
				{
					conn.close();	
					conn = null;
				}
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		return alist;
	}
}
