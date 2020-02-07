package com.zhxh.imms.general;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.zhxh.imms.admin.domain.StartupStatus;
import com.zhxh.imms.admin.domain.RecordCreationType;
import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.logic.SystemRoleLogic;
import com.zhxh.imms.admin.logic.SystemUserLogic;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.startup.ZhxhApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class AdminTest {
	@Autowired
	private SystemUserLogic userLogic;
	@Autowired
	private SystemRoleLogic roleLogic;	
		
	//@Test
	public void testCreateRole() throws Exception {
		SystemRole role1 = new SystemRole();
		role1.setRoleCode("admin");
		role1.setRoleName("管理员");
		role1.setAccountStatus(StartupStatus.NORMAL);
		role1.setRecordCreationType(RecordCreationType.BUILD_IN);		
		Assert.isTrue(roleLogic.create(role1) == 1, "新增角色admin失败");

		SystemRole role2 = new SystemRole();
		role2.setRoleCode("test");
		role2.setRoleName("测试");		
		role2.setAccountStatus(StartupStatus.NORMAL);
		role2.setRecordCreationType(RecordCreationType.BUILD_IN);
		Assert.isTrue(roleLogic.create(role2) == 1, "新增角色test失败");
	}
	
	//@Test
	public void testUpdateRole() throws Exception {
		DbQueryParameter queryParameter = new DbQueryParameter();
		queryParameter.setWhere("role_code = 'admin'");
		List<SystemRole> roles = roleLogic.getAll(queryParameter);
		org.springframework.util.Assert.isTrue(roles.size() == 1, "查询admin失败");
		
		SystemRole role = roles.get(0);
		role.setAccountStatus(StartupStatus.EXPIRED);
		org.springframework.util.Assert.isTrue(roleLogic.update(role) == 1, "更新admin失败");
		
		roles = roleLogic.getAll(queryParameter);
		role = roles.get(0);
		org.springframework.util.Assert.isTrue(roleLogic.getTraceInfos(role).size() == 2, "查询TraceInfo失败");
	}

	//@Test
	public void testCreateUser() throws Exception {
		SystemUser user1 = new SystemUser();
		user1.setUserCode("C00001");
		user1.setDisplayName("刘永红");
		user1.setPwd("123456");
		user1.setAccountStatus(StartupStatus.NORMAL);
		user1.setEmail("yahong@zhxh.com");
		userLogic.create(user1);
		org.springframework.util.Assert.isTrue(user1.getRecordId() > 0, "插入失败");		
		org.springframework.util.Assert.isTrue(userLogic.getTraceInfos(user1).size() == 1, "查询User1 的 TraceInfo失败");

		SystemUser user2 = new SystemUser();
		user2.setUserCode("C00002");
		user2.setDisplayName("徐斯珍");
		user2.setPwd("123456");
		user2.setAccountStatus(StartupStatus.NORMAL);
		user2.setEmail("sizhen@zhxh.com");
		userLogic.create(user2);
		org.springframework.util.Assert.isTrue(user2.getRecordId() > 0, "插入失败");
		org.springframework.util.Assert.isTrue(userLogic.getTraceInfos(user2).size() == 1, "查询User2 的 TraceInfo失败");
	}

	//@Test
	public void testGetAllUser() {
		DbQueryParameter queryParameter = new DbQueryParameter();
		queryParameter.setWhere("user_code = 'C00001'");
		List<SystemUser> users = userLogic.getAll(queryParameter);
		org.springframework.util.Assert.isTrue(users.size() == 1, "查询C00001失败");

		queryParameter.setWhere(null);
		queryParameter.setStart(0);
		queryParameter.setLimit(2);
		queryParameter.setOrderBy("record_id desc");
		users = userLogic.getAll(queryParameter);
		org.springframework.util.Assert.isTrue(users.size() == 2, "分页查询失败");
		org.springframework.util.Assert.isTrue(users.get(0).getRecordId() == 2, "排序失败");
	}	

	//@Test
	public void testUpdateRoles() {
		DbQueryParameter userQueryParameter = new DbQueryParameter();
		userQueryParameter.setWhere("user_code = 'C00001'");
		List<SystemUser> users = userLogic.getAll(userQueryParameter);
		org.springframework.util.Assert.isTrue(users.size() == 1, "查询C00001失败");
		SystemUser currentUser = users.get(0);

		DbQueryParameter roleQueryParameter = new DbQueryParameter();
		roleQueryParameter.setWhere("r.role_code in('admin','test')");
		List<SystemRole> roles = roleLogic.getAll(roleQueryParameter);
		org.springframework.util.Assert.isTrue(roles.size() == 2, "查询角色失败");

		currentUser.getRoles().addAll(roles);
		int updateResult = userLogic.updateUserRoles(currentUser);

		Assert.isTrue(updateResult == roles.size(), "更新roles失败");
	}

	//@Test
	public void testGetUserRoles() {
		SystemUser currentUser = userLogic.get(1L);

		Assert.isTrue(currentUser.getRoles().size() == 2, "user role 获取失败");
		Assert.isTrue("admin".equals(currentUser.getRoles().get(0).getRoleCode()), "role 属性填充失败");
	}

	// @Test
	public void testGetRoleUsers() {
		SystemRole role = roleLogic.get(1L);
		Assert.isTrue(role.getUsers().size() > 0, "获取Role users失败");
	}

	// @Test
	public void testUpdateUserRoles() {
		SystemUser currentUser = userLogic.get(1L);
		currentUser.getRoles().remove(0);
		int updateResult = userLogic.updateUserRoles(currentUser);
		Assert.isTrue(updateResult == 1, "update user role 失败");
	}

	// @Test
	public void testUpdateRoleUsers() {
		SystemUser currentUser = userLogic.get(1L);

		DbQueryParameter roleQueryParameter = new DbQueryParameter();
		roleQueryParameter.setWhere("role_code ='admin'");
		List<SystemRole> roles = roleLogic.getAll(roleQueryParameter);
		roles.forEach(x -> x.getUsers().add(currentUser));

		int updateResult = roleLogic.updateRoleUsers(roles.get(0));
		Assert.isTrue(updateResult == 1, "update role user 失败");
	}
}
