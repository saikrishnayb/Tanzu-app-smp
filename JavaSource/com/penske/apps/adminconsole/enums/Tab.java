package com.penske.apps.adminconsole.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Main Primary Tabs of Supplier Management application
 */
public enum Tab {

    ADMIN_CONSOLE;

    private List<SubTab> subTabs = Arrays.asList(SubTab.SECURITY, SubTab.COMPONENTS, SubTab.APP_CONFIG);

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
                LeftNav.TEMPLATE_MANAGEMENT,
                LeftNav.EXCEL_SEQUENCE,
                LeftNav.COMPONENT_VISIBILITY_OVERRIDE)),

        APP_CONFIG(Arrays.asList(LeftNav.SUBJECT_MANAGEMENT,
                LeftNav.LOADSHEET_MANAGEMENT,
                LeftNav.LOADSHEET_RULES,
                LeftNav.LOADSHEET_SEQUENCES,
                LeftNav.DYNAMIC_RULES,
                LeftNav.SEARCH_TEMPLATES,
                LeftNav.ALERTS,
                LeftNav.GLOBAL_EXCEPTIONS,
                LeftNav.DELAY_REASON_CODES,
                LeftNav.T_AND_C_MANAGEMENT,
                LeftNav.EXCEL_UPLOADS));

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
