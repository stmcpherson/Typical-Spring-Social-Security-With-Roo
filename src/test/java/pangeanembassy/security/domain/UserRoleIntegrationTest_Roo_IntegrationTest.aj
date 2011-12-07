// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package pangeanembassy.security.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pangeanembassy.security.domain.UserRoleDataOnDemand;

privileged aspect UserRoleIntegrationTest_Roo_IntegrationTest {
    
    declare @type: UserRoleIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: UserRoleIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: UserRoleIntegrationTest: @Transactional;
    
    @Autowired
    private UserRoleDataOnDemand UserRoleIntegrationTest.dod;
    
    @Test
    public void UserRoleIntegrationTest.testCountUserRoles() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", dod.getRandomUserRole());
        long count = pangeanembassy.security.domain.UserRole.countUserRoles();
        org.junit.Assert.assertTrue("Counter for 'UserRole' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void UserRoleIntegrationTest.testFindUserRole() {
        pangeanembassy.security.domain.UserRole obj = dod.getRandomUserRole();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to provide an identifier", id);
        obj = pangeanembassy.security.domain.UserRole.findUserRole(id);
        org.junit.Assert.assertNotNull("Find method for 'UserRole' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'UserRole' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void UserRoleIntegrationTest.testFindAllUserRoles() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", dod.getRandomUserRole());
        long count = pangeanembassy.security.domain.UserRole.countUserRoles();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'UserRole', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<pangeanembassy.security.domain.UserRole> result = pangeanembassy.security.domain.UserRole.findAllUserRoles();
        org.junit.Assert.assertNotNull("Find all method for 'UserRole' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'UserRole' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void UserRoleIntegrationTest.testFindUserRoleEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", dod.getRandomUserRole());
        long count = pangeanembassy.security.domain.UserRole.countUserRoles();
        if (count > 20) count = 20;
        java.util.List<pangeanembassy.security.domain.UserRole> result = pangeanembassy.security.domain.UserRole.findUserRoleEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'UserRole' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'UserRole' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void UserRoleIntegrationTest.testFlush() {
        pangeanembassy.security.domain.UserRole obj = dod.getRandomUserRole();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to provide an identifier", id);
        obj = pangeanembassy.security.domain.UserRole.findUserRole(id);
        org.junit.Assert.assertNotNull("Find method for 'UserRole' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyUserRole(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'UserRole' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void UserRoleIntegrationTest.testMerge() {
        pangeanembassy.security.domain.UserRole obj = dod.getRandomUserRole();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to provide an identifier", id);
        obj = pangeanembassy.security.domain.UserRole.findUserRole(id);
        boolean modified =  dod.modifyUserRole(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        pangeanembassy.security.domain.UserRole merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'UserRole' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void UserRoleIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", dod.getRandomUserRole());
        pangeanembassy.security.domain.UserRole obj = dod.getNewTransientUserRole(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'UserRole' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'UserRole' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void UserRoleIntegrationTest.testRemove() {
        pangeanembassy.security.domain.UserRole obj = dod.getRandomUserRole();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'UserRole' failed to provide an identifier", id);
        obj = pangeanembassy.security.domain.UserRole.findUserRole(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'UserRole' with identifier '" + id + "'", pangeanembassy.security.domain.UserRole.findUserRole(id));
    }
    
}
