SET DATABASE SQL SYNTAX DB2 TRUE;
CREATE SCHEMA SMC AUTHORIZATION DBA;

CREATE TABLE SMC.SMC_PO_SUBCATEGORY
(
   PO_SUBCATEGORY_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   PO_SUBCATEGORY varchar(100) NOT NULL,
   DESCRIPTION varchar(50),
   STATUS char(1) NOT NULL,
   CREATED_BY varchar(10) NOT NULL,
   CREATED_DATE timestamp NOT NULL,
   MODIFIED_BY varchar(10),
   MODIFIED_DATE timestamp
);

CREATE TABLE SMC.SMC_PO_CATEGORY
(
   PO_CATEGORY_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   PO_CATEGORY varchar(20) NOT NULL,
   DESCRIPTION varchar(30),
   STATUS char(1) NOT NULL,
   CREATED_BY varchar(10) NOT NULL,
   CREATED_DATE timestamp NOT NULL,
   MODIFIED_BY varchar(10),
   MODIFIED_DATE timestamp
);

CREATE TABLE SMC.SMC_PO_CATEGORY_ASSOC
(
   PO_CATEGORY_ASSOC_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   PO_CATEGORY_ID int NOT NULL,
   PO_SUBCATEGORY_ID int NOT NULL,
   ACTIVE char(1) DEFAULT '1' NOT NULL,
   MAKE_MODEL_YEAR_REQ char(1),
   VEHICLE_TYPE_REQ char(1),
   VEHICLE_SIZE_REQ char(1)
);

CREATE TABLE SMC.SMC_SHIP_THRU_LEAD
(
   LEAD_TIME_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   PO_CATEGORY varchar(20) NOT NULL,
   OEM varchar(3),
   MODEL varchar(50),
   SHIP_THRU_UPFITTER varchar(50),
   DESTINATION_STATE varchar(2),
   LEAD_DAYS int NOT NULL,
   CREATED_BY       VARCHAR(50) NOT NULL,
   CREATED_DATE    TIMESTAMP   NOT NULL,
   MODIFIED_BY      VARCHAR(50)  NOT NULL,
   MODIFIED_DATE      TIMESTAMP   NOT NULL
);

CREATE TABLE SMC.SMC_HELP ( 
	HELP_ID 		int 		GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	USER_TYPE  		VARCHAR(6) 											 NOT NULL, 
	HELP_TEXT  		VARCHAR(32000)  									 NOT NULL
);

CREATE TABLE SMC.SMC_ALERT_HEADER ( 
	HEADER_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	HEADER_KEY 			VARCHAR(25) 								 NOT NULL, 
	HEADER_NAME 		VARCHAR(50) 								 NOT NULL, 
	TAB_ID 				INTEGER 									 NOT NULL, 
	HELP_TEXT 			VARCHAR(100) 								 NULL , 
	DISPLAY_SEQUENCE    INTEGER 									 NOT NULL
);

CREATE TABLE SMC.SMC_ALERTS ( 
	ALERT_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	ALERT_KEY 			VARCHAR(30) 									 NOT NULL , 
	ALERT_NAME 			VARCHAR(100) 									 NOT NULL , 
	TEMPLATE_ID 		INTEGER 		 								 NULL , 
	DISPLAY_SEQUENCE 	INTEGER  										 NULL , 
	HEADER_ID 			INTEGER 										 NOT NULL , 
	VISIBILITY_VENDOR	SMALLINT 										 NOT NULL , 
	VISIBILITY_PENSKE 	SMALLINT 										 NOT NULL , 
	ACTIONABLE 			SMALLINT 										 NOT NULL , 
	HELP_TEXT 			VARCHAR(255) 									 NULL , 
	MEASURE_VALUE		INTEGER  	 									 NULL , 
	COMPLIANCE_TEXT		VARCHAR(255)									 NULL
);

CREATE TABLE SMC.SMC_TAB_MASTER ( 
	TAB_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	TAB_KEY 			VARCHAR(15) 									 NOT NULL , 
	TABNAME 			VARCHAR(100) 									 NOT NULL , 
	DISPLAY_SEQUENCE 	INTEGER 										 NOT NULL , 
	IMG_URL 			VARCHAR(150) 									 NULL , 
	TEMPLATE_ID 		INTEGER 										 NOT NULL , 
	DASHBOARD_TAB 		SMALLINT 											NOT NULL, 
	STATUS 				CHAR(1)  										 NOT NULL, 
	CREATED_BY 			VARCHAR(100) 									 NOT NULL , 
	CREATED_DATE 		TIMESTAMP 										 NOT NULL , 
	MODIFIED_BY 		VARCHAR(100) 									 NULL , 
	MODIFIED_DATE 		TIMESTAMP 										 NULL 
);

CREATE TABLE SMC.SMC_SEARCH_TEMPLATES ( 
	TEMPLATE_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	TEMPLATE_KEY 		VARCHAR(25) 								 NOT NULL , 
	TEMPLATE_NAME 		VARCHAR(50) 								 NOT NULL , 
	DISPLAY_SEQUENCE 	SMALLINT									 NOT NULL , 
	VISIBILITY_VENDOR 	CHAR(1) 									 NULL , 
	VISIBILITY_PENSKE 	CHAR(1) 									 NULL , 
	STATUS 				CHAR(1) 									 NOT NULL, 
	CREATED_BY 			VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE		TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY			VARCHAR(100) 								 NULL , 
	MODIFIED_DATE		TIMESTAMP 									 NULL
);

CREATE TABLE SMC.SMC_SECURITY_FUNCTION ( 
	SEC_FUNC_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	NAME 				VARCHAR(50) 								 NOT NULL , 
	DESCRIPTION			VARCHAR(150) 								 NULL , 
	TAB_ID				INTEGER 									 NULL , 
	STATUS				CHAR(1) 									 NOT NULL, 
	CREATED_BY			VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE		TIMESTAMP 								  	 NOT NULL , 
	MODIFIED_BY			VARCHAR(100) 								 NULL , 
	MODIFIED_DATE		TIMESTAMP 									 NULL 
);

CREATE TABLE SMC.SMC_ROLE_SEC_FUNCTIONS ( 
	SEC_FUNC_ID 		INTEGER 									NOT NULL , 
	ROLE_ID 			INTEGER 									NOT NULL   
);

CREATE TABLE SMC.SMC_USER_MASTER ( 
	USER_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	SSO 				VARCHAR(100) 								 NOT NULL , 
	GESSOUID			VARCHAR(100) 								 NOT NULL , 
	FIRST_NAME			VARCHAR(100) 								 NOT NULL , 
	LAST_NAME 			VARCHAR(100) 								 NOT NULL , 
	EMAIL				VARCHAR(100) 								 NOT NULL , 
	PHONE				VARCHAR(10) 								 NULL , 
	EXTENSION			VARCHAR(5) 									 NULL , 
	STATUS				CHAR(1) 									 NOT NULL , 
	TYPE_ID				INTEGER 									 NOT NULL , 
	DEPT_ID				INTEGER                             		 NOT NULL, 
	ROLE_ID				INTEGER 									 NOT NULL , 
	OEM 				VARCHAR(20) 								 NULL , 
	CREATED_BY			VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE		TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY			VARCHAR(100) 								 NULL , 
	MODIFIED_DATE		TIMESTAMP 									 NULL , 
	ORG_ID 				INTEGER 									 NOT NULL , 
	OPTDLYEML 			VARCHAR(1) 			 						 NOT NULL 
);

CREATE TABLE SMC.SMC_VENDOR_MASTER ( 
	VENDOR_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	CORP 					CHAR(4) 									 NOT NULL , 
	VENDOR_NUMBER			INTEGER 									 NOT NULL , 
	VENDOR_NAME				VARCHAR(100) 								 NOT NULL , 
	MFR						VARCHAR(10) 								 NULL , 
	NOTIFICATION_EXCEPTION 	CHAR(1) 									 NOT NULL , 
	ANNUAL_AGREEMENT 		CHAR(1) 									 NOT NULL , 
	PAYMENT_TERMS 			INTEGER 									 NULL , 
	ADDRESS1 				VARCHAR(100) 								 NOT NULL , 
	ADDRESS2 				VARCHAR(100) 								 NULL , 
	CITY 					VARCHAR(50) 								 NOT NULL , 
	STATE 					VARCHAR(50) 								 NOT NULL , 
	ZIP 					VARCHAR(15) 								 NULL , 
	STATUS					CHAR(1) 									 NOT NULL , 
	CREATED_BY 				VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE 			TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY 			VARCHAR(100) 								 NULL , 
	MODIFIED_DATE 			TIMESTAMP 									 NULL , 
	PLANNING_ANALYST 		INTEGER 									 NULL , 
	SUPPLY_SPECIALIST 		INTEGER 									 NULL
);

CREATE TABLE SMC.SMC_VENDOR_CONTACT
(
   VENDOR_CONTACT_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   VENDOR_ID int NOT NULL,
   CONTACT_TYPE varchar(10) NOT NULL,
   FIRST_NAME varchar(100) NOT NULL,
   LAST_NAME varchar(100) NOT NULL,
   EMAIL varchar(100) NOT NULL,
   PHONE varchar(10) NOT NULL,
   EXTENSION varchar(5),
   RESPONSIBILITY int NOT NULL,
   CREATED_BY varchar(100) NOT NULL,
   CREATED_DATE timestamp,
   MODIFIED_BY varchar(100) NOT NULL,
   MODIFIED_DATE timestamp
);

CREATE TABLE SMC.SMC_ORG_VENDOR_ASSOC ( 
	ASSOCIATION_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL ,
	ORG_ID 					INTEGER 									 NOT NULL , 
	VENDOR_ID 				INTEGER 									 NOT NULL 
);

CREATE TABLE SMC.SMC_USER_LOGIN ( 
	LOGIN_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	SSO						VARCHAR(100) 								 NOT NULL , 
	SERVER_LOCATION 		VARCHAR(30) 								 NULL , 
	LOGIN_TIME 				TIMESTAMP 									 NOT NULL
);

CREATE TABLE SMC.SMC_VENDOR_FILTER ( 
	VENDOR_FILTER_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	VENDOR_ID				INTEGER 									 NOT NULL , 
	USER_ID					INTEGER 									 NOT NULL , 
	IS_ACTIVE				INTEGER 									 NOT NULL
);

CREATE TABLE SMC.SMC_ORG_MASTER ( 
	ORG_ID 					int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	NAME 					VARCHAR(50) 								 NOT NULL , 
	DESCRIPTION 			VARCHAR(255) 								 NULL , 
	PARENT_ORG_ID			INTEGER 									 NULL , 
	CREATED_BY				VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP  									 NOT NULL , 
	MODIFIED_BY				VARCHAR(100) 								 NULL , 
	MODIFIED_DATE			TIMESTAMP 									 NULL 
);

CREATE TABLE SMC.SMC_LOOKUP ( 
	LOOKUP_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	LOOKUP_NAME				VARCHAR(50) 								 NOT NULL , 
	LOOKUP_VALUE			VARCHAR(500) 								 NOT NULL , 
	LOOKUP_DESC				VARCHAR(100) 								 NOT NULL , 
	LOOKUP_SEQ				SMALLINT 									 NULL , 
	LOOKUP_STATUS			CHAR(1) 									 NOT NULL , 
	CREATED_BY				VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 NULL , 
	MODIFIED_BY				VARCHAR(100) 								 NOT NULL , 
	MODIFIED_DATE			TIMESTAMP 									 NULL 
);

CREATE TABLE SMC.SMC_BUDDIES ( 
	SSO 					VARCHAR(10) 								 NOT NULL , 
	BUDDY_SSO				VARCHAR(10) 								 NOT NULL , 
	USER_DEPT				VARCHAR(20) 								 NOT NULL , 
	CREATED_BY				VARCHAR(10) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 NULL , 
	SELECTION_TYPE			VARCHAR(20) 								 NULL
);

CREATE TABLE SMC.SMC_USER_DEPT ( 
	DEPT_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	USER_DEPT				VARCHAR(20) 								 NOT NULL , 
	DESCRIPTION				VARCHAR(50) 								 NULL , 
	STATUS					CHAR(1) 									 DEFAULT 'A' , 
	CREATED_BY				VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY				VARCHAR(100) 								 DEFAULT NULL , 
	MODIFIED_DATE			TIMESTAMP 									 DEFAULT NULL 
);

CREATE TABLE SMC.SMC_TERMS_AND_CONDITIONS ( 
	VERSION_NUMBER 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	START_DATE				DATE 										 NOT NULL , 
	END_DATE				DATE 										 NOT NULL , 
	TC_TEXT					CLOB(1048576) 								 NOT NULL , 
	TEXT_SIZE				DECIMAL(4, 2) 								 NOT NULL , 
	STATUS 					CHAR(1) 									 DEFAULT 'A' , 
	CREATED_BY				VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY				VARCHAR(100) 								 DEFAULT NULL , 
	MODIFIED_DATE			TIMESTAMP 									 DEFAULT NULL 
);

CREATE TABLE SMC.SMC_TEMPLATE_MASTER ( 
	TEMPLATE_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	TEMPLATE_HASH			VARCHAR(32) 								 NULL , 
	TEMPLATE_NAME			VARCHAR(50) 								 NOT NULL , 
	TEMPLATE_DESC			VARCHAR(100) 								 NULL , 
	CREATED_BY				VARCHAR(10) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY				VARCHAR(10) 								 NOT NULL , 
	MODIFIED_DATE			TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	PO_CATEGORY_ASSOC_ID	INTEGER 									 NULL , 
	STATUS					VARCHAR(1) 			 						 NOT NULL 
);

CREATE TABLE SMC.SMC_TEMPLATE_COMPONENTS ( 
	TEMP_COMP_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	TEMPLATE_ID				INTEGER 									 NOT NULL , 
	COMPONENT_ID			INTEGER 									 NOT NULL , 
	COMPONENT_ACCESS		CHAR(1) 									 NOT NULL , 
	AVAIL_TO_OTHER_PO		CHAR(1) 									 NOT NULL , 
	EXPORT_TO_EXCEL			CHAR(1) 									 NOT NULL , 
	CREATED_BY				VARCHAR(10) 								 NOT NULL , 
	CREATED_DATE			TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY				VARCHAR(10) 								 NULL , 
	MODIFIED_DATE			TIMESTAMP 									 NULL  
);

CREATE TABLE SMC.SMC_COMPONENT_GROUPS
(
   COMP_GROUP_ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   GROUP_NAME varchar(100),
   GROUP_SEQUENCE int NOT NULL,
   COMP_SECTION_ID int NOT NULL,
   COMP_GRP_NUMBER int NOT NULL
)
;

CREATE TABLE SMC.V_SMC_PO_CATEGORY_COMBINATIONS (
	CATEGORY				VARCHAR(20)				NOT NULL,
	SUBCATEGORY				VARCHAR(100)			NOT NULL,
	ASSOC_ID				INTEGER					NOT NULL
);

CREATE TABLE SMC.V_SMC_AVAILABLE_COMPONENTS (
	SECTION_NAME			VARCHAR(100)		NULL,
	SECTION_SEQUENCE		INTEGER				NOT NULL,
	GROUP_NAME				VARCHAR(100)		NULL,
	GROUP_SEQUENCE			INTEGER				NOT NULL,
	COMPONENT_ID			INTEGER				NOT NULL,
	DISPLAY_NAME			VARCHAR(100)		NULL,
	FULL_NAME				VARCHAR(203)		NULL,
	COMPONENT_SEQUENCE		INTEGER				NOT NULL,
	COMPONENT_TYPE			CHAR(1)				NOT NULL,
	IS_VEHICLE_COMP			CHAR(1)				NOT NULL,
	GEN_MISSING_COUNT		CHAR(1)				NOT NULL,
	GROUP_NUMBER			VARCHAR(3)			NOT NULL,
	IS_STATIC_SECTION		VARCHAR(1)			NOT NULL
);

CREATE TABLE SMC.SMC_COMPONENT_INFO_DETAILS ( 
	COMPONENT_ID			INTEGER 			NOT NULL , 
	DISPLAY_NAME			VARCHAR(100) 		DEFAULT NULL , 
	COMP_GROUP_ID			INTEGER 			NOT NULL , 
	COMPONENT_TYPE			CHAR(1) 			NOT NULL , 
	IS_VEHICLE_COMP			CHAR(1) 			NOT NULL , 
	VISIBILITY				CHAR(1) 			NOT NULL , 
	COMPONENT_SEQUENCE		INTEGER 			NOT NULL , 
	GEN_MISSING_COUNT		CHAR(1) 			NOT NULL , 
	ALLOW_DUPLICATES		CHAR(1) 			NOT NULL 
);

CREATE TABLE SMC.SMC_COMPONENT_RULE_OVERRIDES ( 
	CMP_RULES_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	CMP_VISIBLE_ID 			INTEGER 								   	 DEFAULT NULL , 
	RULE_ID					INTEGER 									 NOT NULL , 
	PRIORITY				INTEGER 									 DEFAULT NULL , 
	VISIBILITY_OVERRIDE		CHAR(1) 									 DEFAULT 'N' NOT NULL , 
	CREATED_BY				VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE			TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY				VARCHAR(10) 								 DEFAULT NULL , 
	MODIFIED_DATE			TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP , 
	TEMP_COMP_ID			INTEGER 									 DEFAULT NULL 
);

CREATE TABLE SMC.UNIT_COMPONENTS_TEMPLATE_SEQUENCE_FOR_EXCEL ( 
	"ID" 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	COMPONENT_ID 		INTEGER 									 DEFAULT NULL , 
	TEMPLATE_ID 		INTEGER 									 DEFAULT NULL , 
	COMPONENT_SEQUENCE 	INTEGER 									 DEFAULT NULL  
);

CREATE TABLE SMC.SMC_DYNAMIC_RULES( 
	DYNAMIC_RULE_ID 	int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	CORP 				VARCHAR(20) 								 DEFAULT NULL , 
	MANUFACTURER		VARCHAR(150) 								 DEFAULT NULL , 
	MODEL				VARCHAR(150) 								 DEFAULT NULL , 
	MODEL_YEAR			INTEGER 									 DEFAULT NULL , 
	PRIORITY			INTEGER 									 NOT NULL , 
	STATUS				CHAR(1) 									 DEFAULT 'A' , 
	CREATED_BY			VARCHAR(100) 								 NOT NULL , 
	CREATED_DATE		TIMESTAMP 									 NOT NULL , 
	MODIFIED_BY			VARCHAR(100) 								 DEFAULT NULL , 
	MODIFIED_DATE		TIMESTAMP 									 DEFAULT NULL , 
);

CREATE TABLE SMC.SMC_SPEC_HEADER(
	SPEC_HEADER_ID                INTEGER             IDENTITY,
	DCN_DETAIL_ID                 INTEGER             NOT NULL,
	CORP                          CHAR(4),
	RRN                           INTEGER,
	DCN                           INTEGER,
	SCH_A_NO                      VARCHAR(10),
	SPEC_SEQ                      INTEGER,
	CUSTOMER_NUMBER               VARCHAR(6),
	BILL_GROUP                    CHAR(2),
	CUSTOMER_NAME                 VARCHAR(50),
	PO_CATEGORY                   VARCHAR(15),
	PO_SUB_CATEGORY               VARCHAR(100),
	OEM                           VARCHAR(3),
	MAKE                          VARCHAR(50),
	MODEL                         VARCHAR(50),
	MODEL_YEAR                    INTEGER,
	BUYER_NAME                    VARCHAR(100),
	FROM_UNIT                     CHAR(10),
	TO_UNIT                       CHAR(10),
	DISTRICT_NAME                 VARCHAR(30),
	VEHICLE_TYPE                  VARCHAR(30),
	VEHICLE_USE                   VARCHAR(10),
	DEAL_ID                       INTEGER,
	DEAL_ITEM_ID                  INTEGER,
	SALESMAN_ID                   INTEGER,
	SALESMAN_NAME                 VARCHAR(30),
	MACHINE_ID                    INTEGER,
	SPEC_ID                       INTEGER,
	SPEC_SOURCE                   VARCHAR(20),
	ADMIN_OWNER                   VARCHAR(10),
	STATUS                        CHAR(1)             Default 'Y',
	SUBMIT_DATE                   DATE,
	SPECIAL_PURCHASE              VARCHAR(30),
	GROSS_VEH_WEIGHT              INTEGER,
	SETBACK                       INTEGER,
	ORDER_CODE                    VARCHAR(20),
	SALESNET_QTY                  INTEGER,
	PRCSSD_QTY                    INTEGER,
	SALESNET_COST                 DECIMAL(15,2),
	BASE_PRICE                    DECIMAL(15,2),
	FREIGHT_AMOUNT                DECIMAL(15,2),
	SPECIAL_DISCOUNT              DECIMAL(15,2),
	FET_AMOUNT                    DECIMAL(15,2),
	FAIR_VALUE                    DECIMAL(15,2),
	DESCRIPTION_1                 VARCHAR(50),
	OTHER_PRICE_1                 DECIMAL(15,2),
	DESCRIPTION_2                 VARCHAR(50),
	OTHER_PRICE_2                 DECIMAL(15,2),
	DESCRIPTION_3                 VARCHAR(50),
	OTHER_PRICE_3                 DECIMAL(15,2),
	DESCRIPTION_4                 VARCHAR(50),
	OTHER_PRICE_4                 DECIMAL(15,2),
	DESCRIPTION_5                 VARCHAR(50),
	OTHER_PRICE_5                 DECIMAL(15,2),
	DESCRIPTION_6                 VARCHAR(50),
	OTHER_PRICE_6                 DECIMAL(15,2),
	DESCRIPTION_7                 VARCHAR(50),
	OTHER_PRICE_7                 DECIMAL(15,2),
	DESCRIPTION_8                 VARCHAR(50),
	OTHER_PRICE_8                 DECIMAL(15,2),
	BBC                           INTEGER,
	CA_ACT                        INTEGER,
	CA_EFF                        INTEGER,
	INTER                         INTEGER,
	OHEAD                         INTEGER,
	RSMDL_ID                      INTEGER,
	WHEEL_BASE                    INTEGER,
	WEIGHT                        INTEGER,
	LAST_PRINT_DATE               TIMESTAMP,
	LAST_PRINT_USER               VARCHAR(10),
	FET_CODE                      CHAR(1),
	CURR_CODE                     CHAR(3),
	EXCHG_RATE                    DECIMAL(15,2),
	CREATED_BY                    VARCHAR(10),
	CREATED_DATE                  TIMESTAMP,
	MODIFIED_BY                   VARCHAR(10),
	MODIFIED_DATE                 TIMESTAMP,
	FET_RATE					  DECIMAL(9,4),
	COMMENTS 					  VARCHAR(500)
);

CREATE TABLE SMC.SMC_GLOBAL_EXCEPTIONS ( 
	GLOBAL_EXCEPTION_ID 				int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	COMPONENT_ID						INTEGER 									 NOT NULL , 
	PROVIDER_PO_CATEGORY_ASSOC_ID		INTEGER 									 NOT NULL , 
	PROVIDER_VENDOR_ID					INTEGER										 NOT NULL , 
	CREATED_BY							VARCHAR(100)								 DEFAULT NULL , 
	NEW_EXCEPTION						VARCHAR(1) 			 						 DEFAULT 'N' , 
	UNIT_SIGNATURE						VARCHAR(255) 								 NOT NULL , 
	CREATED_DATE						TIMESTAMP 									 NOT NULL , 
	MODIFIED_DATE						TIMESTAMP									 NOT NULL , 
	MODIFIED_BY							VARCHAR(100) 								 NOT NULL 
);

CREATE TABLE SMC.SMC_GLOBAL_SETTINGS ( 
	SETTING_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	KEY_NAME 		VARCHAR(50) 								 NOT NULL , 
	"VALUE"			VARCHAR(50) 								 DEFAULT NULL , 
	DESCRIPTION		VARCHAR(100) 								 DEFAULT NULL 
);

CREATE TABLE SMC.SMC_COMPONENT_VISIBILITY ( 
	CMP_VISIBLE_ID 			int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	CAT_TYPE_ID				INTEGER 									 NOT NULL , 
	COMPONENT_ID			DECIMAL(9, 0) 								 NOT NULL , 
	V2B_VISIBILITY			CHAR(1) 									 DEFAULT 'N' NOT NULL , 
	LOADSHEET_VISIBILITY	CHAR(1) 									 DEFAULT 'N' NOT NULL , 
	CREATED_BY				VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE			TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY				VARCHAR(10) 								 DEFAULT NULL , 
);
	
CREATE TABLE SMC.SMC_CAT_TYPE_MASTER ( 
	CAT_TYPE_ID 	int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	PO_CATEGORY		VARCHAR(8) 									 NOT NULL , 
	"TYPE"			VARCHAR(8) 									 DEFAULT ' ' NOT NULL , 
	USES_DEFAULT	VARCHAR(1) 									 DEFAULT ' ' , 
	CREATED_BY		VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE	TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY		VARCHAR(10) 								 DEFAULT NULL , 
	MODIFIED_DATE	TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SMC.SMC_LOADSHEET_SEQ_MASTER ( 
	SEQ_MASTER_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	NAME 				CHAR(50) 									 DEFAULT ' ' NOT NULL , 
	DESCRIPTION			CHAR(100) 									 DEFAULT ' ' NOT NULL , 
	PO_CATEGORY			VARCHAR(8) 									 DEFAULT ' ' NOT NULL , 
	"TYPE"				VARCHAR(8) 									 DEFAULT ' ' NOT NULL , 
	OEM 				VARCHAR(3) 									 DEFAULT ' ' NOT NULL , 
	CREATED_BY			VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE		TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY			VARCHAR(10) 								 DEFAULT NULL , 
	MODIFIED_DATE		TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP  
);

CREATE TABLE SMC.SMC_CMP_VISIBILITY_RULES_MASTER ( 
	RULE_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	NAME			CHAR(50) 									 DEFAULT ' ' NOT NULL , 
	DESCRIPTION		CHAR(100) 									 DEFAULT ' ' NOT NULL , 
	CREATED_BY		VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE	TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY		VARCHAR(10) 								 DEFAULT NULL , 
	MODIFIED_DATE	TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP , 
	RULE_TYPE		VARCHAR(1) 									 NOT NULL 
);

CREATE TABLE SMC.SMC_CMP_VISIBILITY_RULES_DEFINITION ( 
	RULE_DEF_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	RULE_ID 			INTEGER										 NOT NULL , 
	CRITERIA_GROUP 		INTEGER										 NOT NULL , 
	COMPONENT_ID		DECIMAL(9, 0)								 NOT NULL , 
	COMPONENT_TYPE 		VARCHAR(1) 									 NOT NULL , 
	OPERAND 			VARCHAR(2) 									 NOT NULL , 
	COMPONENT_VALUE 	VARCHAR(30)									 DEFAULT ' ' NOT NULL , 
	CREATED_BY			VARCHAR(10) 								 DEFAULT NULL , 
	CREATED_DATE		TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY 		VARCHAR(10) 								 DEFAULT NULL , 
	MODIFIED_DATE		TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP  
);

CREATE TABLE SMC.SMC_LOADSHEET_GROUP_MASTER (
   GRP_MASTER_ID	int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   SEQ_MASTER_ID 	int 										 NOT NULL,
   NAME 			char(50) 									 NOT NULL,
   DISPLAY_SEQ 		int 										 NOT NULL,
   CREATED_BY 		varchar(10),
   CREATED_DATE 	timestamp 									 NOT NULL,
   MODIFIED_BY 		varchar(10),
   MODIFIED_DATE 	timestamp
);

CREATE TABLE SMC.SMC_LOADSHEET_CMP_GROUP_SEQ (
   CMP_GRP_SEQ_ID 	int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
   GRP_MASTER_ID 	int 										 NOT NULL,
   COMPONENT_ID 	decimal(9,0)                        		 NOT NULL,
   DISPLAY_SEQ 		int 										 NOT NULL,
   CREATED_BY 		varchar(10),
   CREATED_DATE 	timestamp 									 NOT NULL,
   MODIFIED_BY 		varchar(10),
   MODIFIED_DATE 	timestamp
);

CREATE TABLE SMC.SMC_USER_ROLES ( 
	ROLE_ID 		int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	ROLE_NAME 		VARCHAR(20) 								 DEFAULT '' NOT NULL , 
	DESCRIPTION		VARCHAR(50) 								 DEFAULT NULL , 
	BASE_ROLE_ID	INTEGER 									 DEFAULT NULL , 
	STATUS			CHAR(1) 									 DEFAULT 'A' , 
	OEM 			VARCHAR(20) 								 DEFAULT NULL , 
	CREATED_BY		VARCHAR(100) 								 DEFAULT '' NOT NULL , 
	CREATED_DATE	TIMESTAMP 									 DEFAULT CURRENT_TIMESTAMP NOT NULL , 
	MODIFIED_BY		VARCHAR(100) 								 DEFAULT NULL , 
	MODIFIED_DATE	TIMESTAMP 									 DEFAULT NULL 
);

CREATE TABLE SMC.SMC_COMPONENT_HOLD_PAYMENT (
	HOLD_PAYMENT_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	COMPONENT_ID 	INTEGER  		NOT NULL,
	VENDOR_ID 		INTEGER 		NOT NULL,
	CREATED_BY 		VARCHAR(100)  	NOT NULL,
	CREATED_DATE 	TIMESTAMP 		NOT NULL,
	MODIFIED_BY 	VARCHAR(100)	NOT NULL,
	MODIFIED_DATE 	TIMESTAMP		NOT NULL
);

CREATE TABLE SMC.SMC_COST_ADJUSTMENT_OPTIONS (
	OPTION_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	ORDER_CODE VARCHAR(20) NOT NULL
);

CREATE TABLE SMC.SMC_COST_TOLERANCES (
	TOLERANCE_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	PO_CATEGORY	VARCHAR(15) NOT NULL,
	MF_CODE		CHAR(3),
	TOLERANCE	DECIMAL(15,2) NOT NULL,
	PO_VENDOR_NUMBER INTEGER
);

CREATE TABLE SMC.SMC_PURCHASING_HEADER (
	HEADER_ID             		  INTEGER             NOT NULL,
	DCN                           INTEGER,
	SCH_A_NO                      VARCHAR(10),
	CORP                          CHAR(4)             NOT NULL,
	PO_NUMBER                     INTEGER             NOT NULL,
	PO_CATEGORY                   VARCHAR(15),
	PO_SUB_CATEGORY               VARCHAR(100),
	IS_DATA_CONFLICT_CHECK        CHAR(1)             NOT NULL,
	CUSTOMER_NUMBER               VARCHAR(10),
	CUSTOMER_NAME                 VARCHAR(100),
	VENDOR_NUMBER                 VARCHAR(10),
	VENDOR_NAME                   VARCHAR(100),
	OEM                           VARCHAR(3),
	MODEL                         VARCHAR(100),
	MODEL_YEAR                    VARCHAR(4),
	VEHICLE_TYPE                  VARCHAR(20),
	VEHICLE_USE                   VARCHAR(20),
	DEAL_APPROVED_BY              VARCHAR(100)        NOT NULL,
	COM_REQUEST_BUYER             VARCHAR(10)         NOT NULL,
	CREATED_BY                    VARCHAR(10),
	CREATED_DATE                  TIMESTAMP,
	IS_COMP_GENERATED             CHAR(1));

CREATE VIEW SMC.V_SMC_ALERTS ( 
	TAB_ID , 
	TAB_KEY, 
	TABNAME, 
	HEADER_KEY , 
	HEADER_NAME , 
	ALERT_KEY , 
	ALERT_NAME , 
	TEMPLATE_ID , 
	VISIBILITY_VENDOR , 
	VISIBILITY_PENSKE , 
	ACTIONABLE , 
	HELP_TEXT , 
	HEADER_SEQUENCE , 
	DISPLAY_SEQUENCE , 
	MEASURE_VALUE , 
	COMPLIANCE_TEXT ) 
	AS 
	SELECT TAB.TAB_ID, TAB.TAB_KEY, TAB.TABNAME, HDR.HEADER_KEY, HDR.HEADER_NAME, ALRT.ALERT_KEY, ALRT.ALERT_NAME,  
						ALRT.TEMPLATE_ID, ALRT.VISIBILITY_VENDOR, ALRT.VISIBILITY_PENSKE, ALRT.ACTIONABLE, ALRT.HELP_TEXT,  
				HDR.DISPLAY_SEQUENCE AS HEADER_SEQUENCE, ALRT.DISPLAY_SEQUENCE, ALRT.MEASURE_VALUE, ALRT.COMPLIANCE_TEXT  
					FROM SMC.SMC_TAB_MASTER TAB  
					INNER JOIN SMC.SMC_ALERT_HEADER HDR  
					ON HDR.TAB_ID = TAB.TAB_ID  
					INNER JOIN SMC.SMC_ALERTS ALRT  
					ON ALRT.HEADER_ID = HDR.HEADER_ID  ;
					
CREATE TABLE SMC.SMC_USER_OTP(
   USER_ID INTEGER NOT NULL,
   OTP VARCHAR(100) NOT NULL
);;