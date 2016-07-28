package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import util.*;

/**Զ�̷������ýӿ�
 * @author Cui
 *���� public MsgBean RmiExec(int pCmd, RmiBean pBean, int CurrPage) ����
 */
public interface Rmi extends Remote
{
	
	public int Lock_Pro_Day_Text = 0;
	
	public boolean Test()throws RemoteException;
	
	/**����cmd���� , �����pBean , CurrPage��ǰҳ�� 
	 **case 0: ��ѯ ;
	 * case 1: ��ɾ�� ;
	 * case 2: Function�������� ;
	 * case 3: Package���� ;   ?oracle.jdbc����?
	 * case 4: Producer���� ;
	 * @param PageSize TODO
	 * @see rmi.Rmi#RmiExec(int, rmi.RmiBean, int, int)
	 */
	public MsgBean RmiExec(int pCmd, RmiBean pBean, int CurrPage) throws RemoteException;
}