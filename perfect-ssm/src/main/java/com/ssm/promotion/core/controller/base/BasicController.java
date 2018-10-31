package com.ssm.promotion.core.controller.base;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.UserService;

/** 
 * @description 
 * @author  liu66 
 * @date 创建时间：2018-6-4 上午10:46:52 
 * @version 1.0 
 * @since  
 */
public class BasicController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取当前用户
	 * @param request
	 * @return
	 */
	public User getSessionUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(Constants.SESSION_USER);
	}
	
	/**
	 * 获取超级管理员
	 * @param request
	 * @return
	 */
	public Boolean validSuperUser(HttpServletRequest request){
		User currentUser = getSessionUser(request);
		User sysUser = null;
		if (null != currentUser) {
			sysUser = userService.getUserByUUID(currentUser.getUuid());
			if (null != sysUser) {
				synchronized (sysUser) {
					if (sysUser.getRoleId() == 1) {
						return Boolean.TRUE;
					}
				}
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 获取客户端ip地址
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request, HttpServletResponse response) {
		 String ip = request.getHeader("x-forwarded-for");     
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
             ip = request.getHeader("Proxy-Client-IP");     
         }     
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
             ip = request.getHeader("WL-Proxy-Client-IP");     
         }     
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
             ip = request.getRemoteAddr();     
             if(ip.equals("127.0.0.1")){       
                 //根据网卡取本机配置的IP       
                 InetAddress inet=null;       
                 try {       
                     inet = InetAddress.getLocalHost();       
                 } catch (Exception e) {       
                     e.printStackTrace();       
                 }       
                 ip= inet.getHostAddress();       
             }    
         }     
         // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割    
         if(ip != null && ip.length() > 15){      
             if(ip.indexOf(",")>0){       
                 ip = ip.substring(0,ip.indexOf(","));       
             }       
         }       
         return ip;     
	}
	
	/**
     * 获取服务器IP地址
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String  getServerIp(){
        String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                SERVER_IP = ip.getHostAddress();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {
                    SERVER_IP = ip.getHostAddress();
                    break;
                } else {
                    ip = null;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    
        return SERVER_IP;
    }
    
    public static String getCurrentOS() {
    	String osName = System.getProperty("os.name");
    	if (StringUtils.isNotBlank(osName)) {
    		if (osName.contains(Constants.OS_WINDOWS)) {
    			return Constants.OS_WINDOWS;
    		} else if (osName.contains(Constants.OS_LINUX)) {
    			return Constants.OS_LINUX;
    		}
    	}  
    	return null;
    }
}
