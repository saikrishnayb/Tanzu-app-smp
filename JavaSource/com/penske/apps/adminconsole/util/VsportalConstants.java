package com.penske.apps.adminconsole.util;

public class VsportalConstants {
	public static final String IS_ADMIN = "IS_ADMIN";
	
	/*
	 * STATUS CONSTANTS
	 */
	public static final String CA_APPROVED = "CA APPROVE";
	public static final String CA_PENDED = "CA PEND";
	public static final String CA_REJECT = "CA REJECT";
	public static final String CA_RETURN_TO_SALES = "CA RETURN";
	public static final String ORDERED = "ORDERED";
	public static final String IN_SERVICE = "IN SERVICE";
	public static final String VS_REJECT = "VS REJECT";
	public static final String VS_PEND = "VS PEND";
	public static final String VS_APPROVED = "VS APPROVE";
	public static final String VS_WORKING = "VS WORKING";
	public static final String VS_UNASSIGNED = "VS UNASIGN";
	public static final String VS_QUEUE = "VS QUEUE";
	public static final String DELIVERED = "DELIVERED";
	public static final String REC_PER_PAGE = "8";
	public static final String SEARCH_REC_PER_PAGE = "10";
	public static final String RED = "RED";
	public static final int VS_ADMIN = 5;
	public static final int VS_VSS = 6;
	public static final int VP = 7;
	

	public static final String WINDOWS_ENV 				= "WINDOWS_ENV";
	public static final String AS400_ENV 				= "AS400_ENV";
	 
	/*
	 * Excel transporter upload
	 */
	 public static final int  	PICKUP_VENDOR	=	0	;
	 public static final int  	STUS			=	1	;
	 public static final int  	UNIT_NO			=	2	;
	 public static final int  	REQ_PCKUP_DATE	=	3	;
	 public static final int  	REQ_DELVRY_DATE	=	4	;
	 
	 public static final int  	PLANT_OEM		=	5	;
	 public static final int  	PLANT_CODE		=	6	;
	 
	 public static final int  	PLANT_NAME		=	7	;
	 public static final int  	PLANT_ADRS_1	=	8	;
	 public static final int  	PLANT_ADRS_2	=	9	;
	 public static final int  	PLANT_ADRS_3	=	10	;
	 
	 public static final int  	DEST_CODE		=	11	;
	 
	 public static final int  	DLV_NAME		=	12	;
	 public static final int  	DLV_ADRS_1		=	13	;
	 public static final int  	DLV_ADRS_2		=	14	;
	 public static final int  	DLV_ADRS_3		=	15	;
	 public static final int  	MILEAGE			=	16	;
	 public static final int  	VIN				=	17	;

	 public static final int  	BODY_SERIAL		=	18	;
	 public static final int  	UNIT_GVW		=	19	;
	 public static final int  	CAT				=	20	;
	 public static final int  	SLPR_CODE		=	21	;
	 public static final int  	AERO_ROOF		=	22	;
	 public static final int  	AERO_SIDE		=	23	;
	 public static final int  	AERO_CAB		=	24	;
	 public static final int  	FREIGHT_AMOUNT	=	25	;
	 public static final int  	FUEL_SUR_CHARGE	=	26	;
	 public static final int  	TOTAL_AMOUNT	=	27	;
	 public static final int  	TRNST_PO		=	28	; 
	 public static final int  	ACT_DELVRY		=	29	;
	 
	 public static final int    ACCEPT_AMOUNT 	= 	30	; 
	 public static final int    FLUID_AMOUNT 	= 	31	; 
	 public static final int    BREAKDWN_AMOUNT = 	32	;
	 public static final int    TOLL_AMOUNT 	= 	33	;
	 public static final int    MISC_AMOUNT 	= 	34	;
	 public static final int    MISC_COMMENT 	= 	35	;
	 public static final int    REVISED_AMOUNT 	= 	36	; 
	 
	 /* 
	  * Transporter Constants
	  */	 
	 public static final int  	TRANSPORTER_PICKUP_VENDOR	=	0	;
	 public static final int  	TRANSPORTER_STUS			=	1	;
	 public static final int  	TRANSPORTER_UNIT_NO			=	2	;
	 public static final int  	TRANSPORTER_PICKUP_DATE		=	3	;
	 public static final int  	TRANSPORTER_PROD_DATE		=	4	;
	 public static final int  	TRANSPORTER_EST_DEL_DATE	=	5	;	 
	 public static final int  	TRANSPORTER_HOLD_DATE		=	6	;
	 public static final int  	TRANSPORTER_CANCEL_DATE		=	7	;
	 public static final int  	TRANSPORTER_PLANT_OEM		=	8	;
	 public static final int  	TRANSPORTER_PLANT_CODE		=	9	;
	 public static final int  	TRANSPORTER_PLANT_NAME		=	10	;
	 public static final int  	TRANSPORTER_PLANT_ADRS_1	=	11	;
	 public static final int  	TRANSPORTER_PLANT_ADRS_2	=	12	;
	 public static final int  	TRANSPORTER_PLANT_CITY		=	13	;
	 public static final int  	TRANSPORTER_PLANT_STATE		=	14	;
	 public static final int  	TRANSPORTER_PLANT_ZIP		=	15	;
	 public static final int  	TRANSPORTER_PLANT_CONTACT	=	16	;
	 public static final int  	TRANSPORTER_DEST_CODE		=	17	;
	 public static final int  	TRANSPORTER_DLV_NAME		=	18	;
	 public static final int  	TRANSPORTER_DLV_ADRS_1		=	19	;
	 public static final int  	TRANSPORTER_DLV_ADRS_2		=	20	;
	 public static final int  	TRANSPORTER_DLV_CITY		=	21	;
	 public static final int  	TRANSPORTER_DLV_STATE		=	22	;
	 public static final int  	TRANSPORTER_DLV_ZIP			=	23	;
	 public static final int  	TRANSPORTER_DLV_CONTACT		=	24	;
	 public static final int  	TRANSPORTER_MILEAGE			=	25	;
	 public static final int  	TRANSPORTER_VIN				=	26	;
	 public static final int  	TRANSPORTER_BODY_SERIAL		=	27	;
	 public static final int  	TRANSPORTER_UNIT_GVW		=	28	;
	 public static final int  	TRANSPORTER_CAT				=	29	;
	 public static final int  	TRANSPORTER_VEH_CAT			=	30	;
	 public static final int  	TRANSPORTER_VEH_TYPE		=	31	;
	 public static final int  	TRANSPORTER_FUEL_TYPE		=	32	;
	 public static final int  	TRANSPORTER_SLPR_CODE		=	33	;
	 public static final int  	TRANSPORTER_AERO_ROOF		=	34	;
	 public static final int  	TRANSPORTER_AERO_SIDE		=	35	;
	 public static final int  	TRANSPORTER_AERO_CAB		=	36	;
	 public static final int  	TRANSPORTER_DECKING			=	37	;
	 public static final int  	TRANSPORTER_NUM_DECKING		=	38	;
	 public static final int  	TRANSPORTER_FREIGHT_AMOUNT	=	39	;
	 public static final int  	TRANSPORTER_FUEL_SUR_CHARGE	=	40	;
	 public static final int  	TRANSPORTER_PENSKE_CHARGE	=	41	;
	 public static final int  	TRANSPORTER_TOTAL_AMOUNT	=	42	;
	 public static final int  	TRANSPORTER_TRNST_PO		=	43	;
	 public static final int  	TRANSPORTER_TRNST_PO_DATE	=	44	;
	 public static final int	TRANSPORTER_UNIT_DLV_DATE	=	45	;
	 public static final int  	TRANSPORTER_ACT_DELVRY		=	46	;
	 public static final int    TRANSPORTER_FLUID_AMOUNT 	= 	47	; 
	 public static final int    TRANSPORTER_BREAKDWN_AMOUNT = 	48	;
	 public static final int    TRANSPORTER_TOLL_AMOUNT 	= 	49	;
	 public static final int    TRANSPORTER_MISC_AMOUNT 	= 	50	;
	 public static final int    TRANSPORTER_REVISED_AMOUNT 	= 	51	; 
	 public static final int    TRANSPORTER_ACCEPT_AMOUNT 	= 	52	;
	 public static final int    PENSKE_COMMENT			 	= 	53	;
	 public static final int    TRANSPORTER_COMMENT		 	= 	54	;
	 public static final int	TRANSPORTER_ASSIGN_DATE		=	55	;
	 public static final int    TRANSPORTER_LAST_UPDATED 	= 	56	;
	 public static final int    TRANSPORTER_CORP_CODE	 	= 	57	;
	 public static final int    TRANSPORTER_PARENT_VENDOR 	= 	58	;
	 public static final int    TRANSPORTER_PURCH_PO_NUM 	= 	59	;
	 public static final int    TRANSPORTER_ADV_NOT_SEQ		= 	60	;
	 public static final int    TRANSPORTER_LIFTGATE_MAKE	= 	61	;
	 public static final int    TRANSPORTER_LIFTGATE_MODEL	= 	62	;
	 public static final int    TRANSPORTER_REPORT_ID		= 	63	;
	 
	 
		 
	 public static final String EXCEL  = ".xls";
	 
	 public static final int	TRANSPORT_START_ROW = 1;
	 
	 //Ford Data upload
	 public static final int  	UNIT_NUMBER_EMAIL	=	0	;
	 public static final int  	PO_NUMBER_EMAIL		=	1	;
	 public static final int  	VIN_EMAIL			=	3	;
	 public static final int  	RHF_STOCK_NO_EMAIL	=	2	;
	 public static final int  	SCHEDULE_DATE_EMAIL	=	4	;
	 public static final int  	STATUS_EMAIL		=	5	;
	 public static final int  	DETA_DATE_EMAIL		=	6	;	
	
	 public static final int 	FORD_START_ROW		= 	1;
	 
	 //BodyCompany Upload
	 
	 public static final int UNIT					= 0;	 
	 public static final int CORP_CODE				= 1;
	 public static final int PO_NUMBER				= 2;
	 public static final int PO_DATE				= 3;
	 public static final int OEM					= 4;
	 public static final int MODEL					= 5;
	 public static final int BDYCOMP_VIN			= 6;
	 public static final int BODY_MANUFACTURER		= 7;
	 public static final int BODY_VENDOR			= 8;
	 public static final int BODY_LENGTH			= 9;
	 public static final int BODY_MODEL				= 10;
	 public static final int BDYCMP_BODY_SERIAL 	= 11; 
	 public static final int LIFT_GATE_SERIAL 		= 12; 
	 public static final int REEFER_SERIAL 			= 13; 
	 public static final int CHASSIS_DEL_DATE 		= 14;
	 public static final int STATUS 				= 15;
	 public static final int EST_PROD_DATE  		= 16;
	 public static final int REVISED_BODY_COMPLETION= 17;
	 public static final int DESTINATION_CITY 		= 18;
	 public static final int ACTUAL_PROD_DATE 		= 19;
	 
	 //Vendor Report Upload
	 public static final String PENSKE_REPORT_ID		=	"PENSKE REPORT ID";
	 public static final int 	VENDOR_REPORT_HEADER	=	1;
	 public static final int 	VENDOR_REPORT_CONTENT	=	0;
	 public static final String VENDOR_REPORT_RETURN_MSG=	"IMPORT SUCCESSFUL";
}
