package com.softtek.mdm.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.softtek.mdm.model.AuthModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrgManagerRelationModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.AuthService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.OrgManagerRelationService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.SecurityEventLogService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.AuthStatus;

import jodd.util.StringUtil;


/**
 * 处理权限验证将
 * 将用户对应的权限放入GrantedAuthority中
 * @author color.wu
 * @version 1.0
 * @time 2016/03/10
 *
 */
public class MyUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private AuthService authService;
	
	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private OrgManagerRelationService orgManagerRelationService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SecurityEventLogService securityEventLogService;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		/**
		 * 通过不同的登录页面来区分不同的角色
		 * 获取机构id
		 * 管理员的话机构id为""
		 * 普通用户和部门管理员登录  机构id有值，
		 */
		int lastIndex=name.lastIndexOf(MyUsernamePasswordAuthenticationFilter.USERNAME_ORGID_SPLIT);
		String org_id=name.substring(lastIndex+1);
		name=name.substring(0, lastIndex);
		
		List<GrantedAuthority> auths = null;
		String mString="";
		
		if(StringUtil.isBlank(org_id)){
			//从管理员入口登录，只能是机构管理员/超级管理员
			ManagerModel manager=managerService.findOneInstitution(name);
			
			if(manager!=null){
				//============检测license情况   start=============
				List<OrgManagerRelationModel> list=orgManagerRelationService.findRecordsByManagerId(manager.getId());
				if((!CollectionUtils.isEmpty(list))&&manager.getUser_type()!=Integer.parseInt(AuthStatus.SOFTTEK_AMDIN.toString())){
					@SuppressWarnings("unchecked")
					List<Integer> ids=(List<Integer>) CollectionUtils.collect(list, new Transformer() {
						@Override
						public Object transform(Object input) {
							if(input instanceof OrgManagerRelationModel){
								OrgManagerRelationModel org=(OrgManagerRelationModel) input;
								return org.getOrganization()!=null?org.getOrganization().getId():null;
							}
							return null;
						}
					});
					
					checkLicense(ids);
				}
				//============检测license情况   end=============
				
				if(manager.getLogin_count()>(Integer)2){
					DateTime dTime=new DateTime(manager.getUpdateTime());
					if(dTime.plusMinutes(5).isAfterNow()){
						mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.lockonfive", null, LocaleContextHolder.getLocale());
						throw new UsernameNotFoundException(mString);
					}
				}
				
				//判断机构管理员是否已经被禁用
				if("0".equals(manager.getStatus())){
					mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.disabled", null, LocaleContextHolder.getLocale());
					throw new UsernameNotFoundException(mString);
				}
				
				if(manager.getUser_type()==(Integer)2){
					//检测管理机构问题
					List<OrganizationModel> orgList = organizationService.findEnableOrganizationRecordsByManagerId(manager.getId());
					if(CollectionUtils.isEmpty(orgList)){
						mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.organization.nomanage", null, LocaleContextHolder.getLocale());
						throw new UsernameNotFoundException(mString);
					}
				}
				
				//该帐号是管理帐号
				//获取机构管理员的权限信息
				auths=obtainAuths(manager.getUser_type());
				
				ManagerModel temp=new ManagerModel();
				temp.setId(manager.getId());
				temp.setLogin_count((manager.getLogin_count()==null?0:manager.getLogin_count())+1);
				managerService.update(temp);
				if(temp.getLogin_count() > (Integer)1){
					//保存错误密码的日志
					  SecurityEventLogModel securityEventLog = new SecurityEventLogModel();
					  Object[] obj = {manager.getUsername()};
					  String operateContent = messageSource.getMessage("logs.system.manager.error.password",obj,LocaleContextHolder.getLocale());
					  securityEventLog.setEventType("4");
					  securityEventLog.setLevel("1");
					  securityEventLog.setOperateContent(operateContent);
					  securityEventLog.setCreateBy(manager.getId());
					  securityEventLog.setUpdateBy(manager.getId());
					  securityEventLogService.insertSecurityEventLog(securityEventLog);
				}
				
				return new User(manager.getUsername(), manager.getPassword(), true, true, true, true, auths);
			
			}else{
				//普通用户/部门管理员从该入口登录
				 manager=managerService.findOneByName(name);
				 if(manager!=null){
					 Object[] objects={name};
					 mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.sigin.error.enter", objects, LocaleContextHolder.getLocale());
					 throw new UsernameNotFoundException(mString);
				 }
				Object[] objs={name};
				String UsernameNotFound=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.notexists", objs, LocaleContextHolder.getLocale());
				throw new UsernameNotFoundException(UsernameNotFound);
			}
		}else{
			List<Integer> ids=new ArrayList<>();
			ids.add(Integer.valueOf(org_id));
			checkLicense(ids);
			
			//从普通用户/部门管理员入口登录
			//查询是否是部门管理员
			ManagerModel manager=managerService.findOneByOrgAndName(Integer.valueOf(org_id), name);
			if(manager!=null){
				if(manager.getUser()!=null){
					//是部门管理员，帐号存在，保存权限
					auths=obtainAuths(manager.getUser_type());
					
					ManagerModel temp=new ManagerModel();
					temp.setId(manager.getId());
					temp.setLogin_count((manager.getLogin_count()==null?0:manager.getLogin_count())+1);
					managerService.update(temp);
					
					UserModel user=userService.findOne(manager.getUser().getId());
					if(user!=null){
						if(user.getIs_active()==(Integer)0){
							Object[] objects={name};
							mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.unavtive", objects, LocaleContextHolder.getLocale());
							throw new UsernameNotFoundException(mString);
						}
						if(user.getIs_lock()==(Integer)1){
							Object[] objects={name};
							mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.lock", objects, LocaleContextHolder.getLocale());
							throw new UsernameNotFoundException(mString);
						}
					}
					return new User(manager.getUsername(), manager.getPassword(), true, true, true, true, auths);
				
				}else{
					if(manager.getLogin_count()>=(Integer)3){
						DateTime dTime=new DateTime(manager.getUpdateTime());
						if(dTime.plusMinutes(5).isAfterNow()){
							mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.lockonfive", null, LocaleContextHolder.getLocale());
							throw new UsernameNotFoundException(mString);
						}
					}
					Object[] obts={name};
					mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.sign.error.login", obts, LocaleContextHolder.getLocale());
					throw new UsernameNotFoundException(mString);
				}
				
			}
			//不是部门管理员则查询是否是普通用户
			UserModel user=userService.findUser(name,Integer.parseInt(org_id));
			if(user!=null){
				if(user.getLogin_count()>(Integer)2){
					DateTime dTime=new DateTime(user.getUpdateTime());
					if(dTime.plusMinutes(5).isAfterNow()){
						mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.lockonfive", null, LocaleContextHolder.getLocale());
						throw new UsernameNotFoundException(mString);
					}
				}
				if(user.getIs_active()==(Integer)0){
					Object[] objects={name};
					mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.unavtive", objects, LocaleContextHolder.getLocale());
					throw new UsernameNotFoundException(mString);
				}
				if(user.getIs_lock()==(Integer)1){
					Object[] objects={name};
					mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.lock", objects, LocaleContextHolder.getLocale());
					throw new UsernameNotFoundException(mString);
				}
				//是普通用户，帐号存在，保存权限
				auths=obtainAuths(user.getType());
				
				UserModel temp=new UserModel();
				temp.setId(user.getId());
				temp.setLogin_count((user.getLogin_count()==null?0:user.getLogin_count())+1);
				userService.update(temp);
				
				return new User(user.getUsername(), user.getPassword(), true, true, true, true, auths);
			}else{
				
				//帐号不存在
				//如果帐号输入错误，也就是没有该用户抛出异常
				Object[] objs={name};
				String UsernameNotFound=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.account.notexists", objs, LocaleContextHolder.getLocale());
				throw new UsernameNotFoundException(UsernameNotFound);
			}
		}
	}
	
	/**
	 * 检测license
	 * @param ids
	 */
	private void checkLicense(List<Integer> ids){
		String mString="";
		switch (licenseService.checkLicense(ids)) {
		case 1:
			mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.license.beyond.date", null, LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(mString);
		case 2:
			mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.license.beyond.count", null, LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(mString);
		case 3:
			mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.license.notexists", null, LocaleContextHolder.getLocale());
			throw new UsernameNotFoundException(mString);
		default:
			break;
		}
	}
	
	/**
	 * 获取用户权限
	 * @param type
	 * @return
	 */
	private List<GrantedAuthority> obtainAuths(int type){
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<AuthModel> authList=(List<AuthModel>) authService.findOneByUserType(type);
		if(!CollectionUtils.isEmpty(authList)){
			for (AuthModel auth : authList) {
				SimpleGrantedAuthority sgAuth=new SimpleGrantedAuthority(auth.getAuth_name());
				auths.add(sgAuth);
			}
		}
		return auths;
	}

}
