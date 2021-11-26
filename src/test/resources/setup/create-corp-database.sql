SET DATABASE SQL SYNTAX DB2 TRUE;
CREATE SCHEMA CORP AUTHORIZATION DBA;

CREATE TABLE CORP.FPURVND(
	PVCORP                        CHAR(4)             Default ' ',
	PVNBR                         DECIMAL(6,0)        Default 0,
	PVTRMS                        DECIMAL(3,0)        Default 0,
	PVONAM                        CHAR(24)            Default ' ',
	PVOAD1                        CHAR(24)            Default ' ',
	PVOAD2                        CHAR(24)            Default ' ',
	PVOCTY                        CHAR(24)            Default ' ',
	PVOST                         CHAR(2)             Default ' ',
	PVOZIP                        CHAR(9)             Default ' ',
	PVSAD1                        CHAR(24)            Default ' ',
	PVSAD2                        CHAR(24)            Default ' ',
	PVSCTY                        CHAR(24)            Default ' ',
	PVSST                         CHAR(2)             Default ' ',
	PVSZIP                        CHAR(9)             Default ' '
);

CREATE TABLE CORP.VEHCMPGRF (
	COMPONENT_GROUP_ID			DECIMAL(3)		NOT NULL,
	COMPONENT_GROUP				CHAR(15)		NOT NULL,
	MFR_SEARCH_CODE				CHAR(1)			NOT NULL,
	VEHMODF_CODE				CHAR(8)			NOT NULL,
	ENTER_DATE					DATE			NOT NULL,
	ENTERED_TIME				TIME			NOT NULL,
	ENTERD_BY					CHAR(10)		NOT NULL,
	LAST_CHG_DATE				DATE			NOT NULL,
	LAST_CHG_TIME				TIME			NOT NULL,
	LAST_CHG_BY					CHAR(10)		NOT NULL,
	EXTRA_2ALPH					CHAR(2)			NOT NULL,
	EXTRA_6ALPH					CHAR(6)			NOT NULL,
	EXTRA_8ALPH					CHAR(8)			NOT NULL
);

CREATE TABLE CORP.VEHCMPSGF (
	COMPONENT_ID				DECIMAL(9)	DEFAULT	0           	NOT NULL,	
	COMPONENT_GROUP				CHAR(15)	DEFAULT	' '         	NOT NULL,	
	SUB_GROUP					CHAR(15)	DEFAULT	' '         	NOT NULL,	
	SUB_COMPONENT_NAME			CHAR(15)	DEFAULT	' '         	NOT NULL,	
	COMPONENT_GROUP_ID			NUMERIC(3)	DEFAULT	0           	NOT NULL,	
	DISPLAY_SEQUENCE			DECIMAL(4)	DEFAULT	0           	NOT NULL,	
	COMPONENT_TYPE				CHAR(1)		DEFAULT	' '         	NOT NULL,	
	UNIT_OF_MEASUREMENT			CHAR(9)		DEFAULT	' '         	NOT NULL,	
	VALIDATION_PROGRAM			CHAR(10)	DEFAULT	' '         	NOT NULL,	
	PARAMETER_LIST				CHAR(2)		DEFAULT	' '         	NOT NULL,	
	COMPONENT_FILE_FIELD		CHAR(10)	DEFAULT	' '         	NOT NULL,	
	INDICATOR_VEH_INQ			NUMERIC(2)	DEFAULT	0           	NOT NULL,	
	ENTER_DATE					DATE								NOT NULL,	
	ENTER_TIME					TIME		  						NOT NULL,	
	ENTERED_BY					CHAR(10)	DEFAULT	' '         	NOT NULL,	
	LAST_CHG_DATE				DATE								NOT NULL,	
	LAST_CHG_TIME				TIME		  						NOT NULL,	
	LAST_CHG_BY					CHAR(10)	DEFAULT	' '         	NOT NULL,	
	EXTRA_3ALPH					CHAR(3)		DEFAULT	' '         	NOT NULL,	
	EXTRA_6ALPH					CHAR(6)		DEFAULT	' '         	NOT NULL,	
	EXTRA_ALPHA					CHAR(10)	DEFAULT	' '         	NOT NULL	
);

CREATE TABLE CORP.FVEHMFR (
	MFCODE                        CHAR(3)             Default ' ',
	MFNAME                        CHAR(20)            Default ' ',
	MFTYPC                        CHAR(1)             Default ' ',
	MFTYPB                        CHAR(1)             Default ' ',
	MFTYPR                        CHAR(1)             Default ' ',
	MFTYPL                        CHAR(1)             Default ' ',
	MFTYP5                        CHAR(1)             Default ' ',
	MFTYPM                        CHAR(1)             Default ' ',
	MFTYPE                        CHAR(1)             Default ' ',
	MFTYPT                        CHAR(1)             Default ' ',
	MFTYPA                        CHAR(1)             Default ' ',
	MFTYPW                        CHAR(1)             Default ' ',
	MFTYPX                        CHAR(1)             Default ' ',
	MFTYPZ                        CHAR(1)             Default ' ',
	MFTYPY                        CHAR(1)             Default ' ',
	MFAADJ                        DECIMAL(3,0)        Default 0,
	MFFDAY                        DECIMAL(3,0)        Default 0);