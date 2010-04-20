package org.cms.model.authorize;

import org.cms.doamin.auth.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;


/**
 * @author Administrator
 *	权限对象的具体实现(角色);
 */
public class Authority extends Role implements GrantedAuthority{

	private static final long serialVersionUID = 2617758420234815170L;


    //~ Constructors ===================================================================================================
    public Authority(String role) {
        super.setRoleCode(role);
    }

    //~ Methods ========================================================================================================

    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(super.getRoleCode());
        }

        if (obj instanceof GrantedAuthority) {
            GrantedAuthority attr = (GrantedAuthority) obj;

            return super.getRoleCode().equals(attr.getAuthority());
        }

        return false;
    }

    public String getAuthority() {
        return super.getRoleCode();
    }

    public int hashCode() {
        return super.getRoleCode().hashCode();
    }

    public String toString() {
    	 return super.getRoleCode();
    }

}
