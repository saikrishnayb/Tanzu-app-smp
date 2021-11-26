package com.penske.apps.adminconsole.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Main Primary Tabs of Supplier Management application
 */
public enum Tab {

    ADMIN_CONSOLE;

    private List<SubTab> subTabs = Arrays.asList(SubTab.SECURITY, SubTab.COMPONENTS, SubTab.APP_CONFIG, SubTab.OEM_BUILD_MATRIX);

    /**
     * Secondary Tabs of a Primary Tab
     */
    public enum SubTab {

        SECURITY(Arrays.asList(LeftNav.PENSKE_USERS,
                LeftNav.VENDOR_USERS,
                LeftNav.MANAGE_ROLES,
                LeftNav.MANAGE_VENDORS,
                LeftNav.MANAGE_ORG)),

        COMPONENTS(Arrays.asList(LeftNav.CATEGORY_ASSOCIATION,
                LeftNav.CATEGORY_MANAGEMENT,
                LeftNav.COMPONENT_MANAGEMENT,
                LeftNav.TEMPLATE_MANAGEMENT)),

        APP_CONFIG(Arrays.asList(
                LeftNav.LOADSHEET_MANAGEMENT,
                LeftNav.LOADSHEET_RULES,
                LeftNav.LOADSHEET_SEQUENCES,
                LeftNav.DYNAMIC_RULES,
                LeftNav.SEARCH_TEMPLATES,
                LeftNav.ALERTS,
                LeftNav.GLOBAL_EXCEPTIONS,
                LeftNav.T_AND_C_MANAGEMENT,
                LeftNav.EXCEL_UPLOADS,
                LeftNav.BUILD_HISTORY,
                LeftNav.BODY_PLANT_CAPABILITIES,
                LeftNav.ATTRIBUTE_MAINTENANCE,
                LeftNav.OEM_MIX_MAINTENANCE,
                LeftNav.PRODUCTION_SLOT_MAINTENACE)),
    	
    	OEM_BUILD_MATRIX(Arrays.asList(
    			LeftNav.BUILD_HISTORY,
				LeftNav.BODY_PLANT_CAPABILITIES,
	            LeftNav.ATTRIBUTE_MAINTENANCE,
	            LeftNav.OEM_MIX_MAINTENANCE,
	            LeftNav.PRODUCTION_SLOT_MAINTENACE));
    	
    	 private final List<LeftNav> leftNavs;

        private SubTab(List<LeftNav> leftNavs) {
            this.leftNavs = leftNavs;
        }

        public List<LeftNav> getLeftNavs() {
            return leftNavs;
        }
    }

    public List<SubTab> getSubTabs() {
        return subTabs;
    }

}
