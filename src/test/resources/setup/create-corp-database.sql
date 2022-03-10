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

Create Table CORP.FDSTMST (
	DMCORP                        CHAR(4)               Default ' ',
	DMDIST                        CHAR(4)               Default ' ',
	DMLOC                         CHAR(2)               Default ' ',
	DMRDST                        CHAR(4)               Default ' ',
	DMRLOC                        CHAR(2)               Default ' ',
	DMDIVS                        CHAR(4)               Default ' ',
	DMAREA                        CHAR(4)               Default ' ',
	DMREGN                        CHAR(4)               Default ' ',
	DMADMN                        CHAR(4)               Default ' ',
	DMTYPE                        CHAR(8)               Default ' ',
	DMDLGE                        CHAR(4)               Default ' ',
	DMCAPT                        CHAR(1)               Default ' ',
	DMACTV                        CHAR(1)               Default ' ',
	DMACDT                        DECIMAL(7,0)          Default 0,
	DMIADT                        DECIMAL(7,0)          Default 0,
	DMMAPN                        CHAR(4)               Default ' ',
	DMMAPS                        CHAR(2)               Default ' ',
	DMCPRE                        CHAR(3)               Default ' ',
	DMPDST                        CHAR(4)               Default ' ',
	DMPLOC                        CHAR(2)               Default ' ',
	DMMCTL                        CHAR(6)               Default ' ',
	DMOCLC                        CHAR(6)               Default ' ',
	DMOWNR                        CHAR(4)               Default ' ',
	DMAJAR                        CHAR(4)               Default ' ',
	DMUOMC                        CHAR(1)               Default ' ',
	DMRCUS                        CHAR(3)               Default ' ',
	DMCTRC                        CHAR(3)               Default ' ',
	DMACTC                        CHAR(1)               Default ' ',
	DMPROC                        CHAR(1)               Default ' ',
	DMCUST                        CHAR(6)               Default ' ',
	DMSRPR                        CHAR(12)              Default ' ',
	DMSTTY                        CHAR(12)              Default ' ',
	DMNAME                        CHAR(24)              Default ' ',
	DMADD1                        CHAR(24)              Default ' ',
	DMADD2                        CHAR(24)              Default ' ',
	DMCITY                        CHAR(20)              Default ' ',
	DMSTAT                        CHAR(2)               Default ' ',
	DMZIP                         CHAR(9)               Default ' ',
	DMMUNC                        CHAR(20)              Default ' ',
	DMCNTY                        CHAR(20)              Default ' ',
	DMMAD1                        CHAR(24)              Default ' ',
	DMMAD2                        CHAR(24)              Default ' ',
	DMMCTY                        CHAR(20)              Default ' ',
	DMMST                         CHAR(2)               Default ' ',
	DMMZIP                        CHAR(9)               Default ' ',
	DMRAD1                        CHAR(24)              Default ' ',
	DMRAD2                        CHAR(24)              Default ' ',
	DMRCTY                        CHAR(20)              Default ' ',
	DMRST                         CHAR(2)               Default ' ',
	DMRZIP                        CHAR(9)               Default ' ',
	DMFAID                        CHAR(10)              Default ' ',
	DMOUTQ                        CHAR(10)              Default ' ',
	DMHOLD                        CHAR(5)               Default ' ',
	DMSAVE                        CHAR(5)               Default ' ',
	DMBPIN                        CHAR(1)               Default ' ',
	DMBPAC                        CHAR(15)              Default ' ',
	DMBPNM                        CHAR(24)              Default ' ',
	DMBDIN                        CHAR(1)               Default ' ',
	DMBDAC                        CHAR(15)              Default ' ',
	DMBDNM                        CHAR(24)              Default ' ',
	DMONST                        CHAR(1)               Default ' ',
	DMASNL                        CHAR(1)               Default ' ',
	DMFCCL                        CHAR(1)               Default ' ',
	DMMCCL                        CHAR(1)               Default ' ',
	DMAMIL                        DECIMAL(7,0)          Default 0,
	DMLCDT                        DECIMAL(7,0)          Default 0,
	DMLCUP                        CHAR(10)              Default ' ',
	DMLCIN                        CHAR(3)               Default ' ',
	DM1A01                        CHAR(1)               Default ' ',
	DM2A01                        CHAR(1)               Default ' ',
	DM3A08                        CHAR(8)               Default ' ',
	DM4A04                        CHAR(4)               Default ' ',
	DM5N70                        DECIMAL(7,0)          Default 0,
	DM6N70                        DECIMAL(7,0)          Default 0,
	DM7N72                        DECIMAL(7,2)          Default 0,
	DM8A04                        CHAR(4)               Default ' ',
	DM9A04                        CHAR(4)               Default ' ',
	DMAA04                        CHAR(4)               Default ' ',
	DMBA01                        CHAR(1)               Default ' ',
	DMCA01                        CHAR(1)               Default ' ',
	DMDA01                        CHAR(1)               Default ' ',
	DMEN70                        DECIMAL(7,0)          Default 0,
	DMFN70                        DECIMAL(7,0)          Default 0,
	DMGN70                        DECIMAL(7,0)          Default 0,
	DMHN72                        DECIMAL(7,2)          Default 0,
	DMIN72                        DECIMAL(7,2)          Default 0,
	DMJN72                        DECIMAL(7,2)          Default 0,
	DMKA02                        CHAR(2)               Default ' ',
	DMLA02                        CHAR(2)               Default ' ',
	DMMA02                        CHAR(2)               Default ' ');

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
	MFFDAY                        DECIMAL(3,0)        Default 0
);
	
CREATE TABLE CORP.NVOPODL1 (
	PO_NUMBER		CHAR(7)			NOT NULL,
	PO_DATE			DATE			NOT NULL,
	VENDOR_NUMBER	DECIMAL(7,0)	NOT NULL,
	CORP			CHAR(4)			NOT NULL
);
