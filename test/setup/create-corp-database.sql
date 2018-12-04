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