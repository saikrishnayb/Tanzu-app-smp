package com.penske.apps.adminconsole.model;

import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.penske.apps.smccore.base.util.DateUtil;
/**
 * This class will hold all necessary data
 * on a transport object.  This is to be used
 * for uploading excel information. 
 * @author 600139251
 *
 */
public class Transport{
	
	private static final Logger logger = Logger.getLogger(Transport.class);
	
	public Transport(boolean pilot) {
		super();
		this.pilot = pilot;
	}
	
	// The following items are required for backwards compatibility
	// Do not remove these until we retire the transport pilot process
	private Date 	requestedDeliveryDate;
	private String 	plantAdrs3;		//originContactInfo
	private String 	dlvAdrs3;		//destContactInfo
	private String 	strActDelvry;
	private String	miscComments;	//transporterComment
	private String	reqPickup;
	private String	reqDelvry;

	// Start fields for new transporter process
	private boolean pilot = false; 
	
	private String	pickupVendor;
	private String	unitNo;
	private String	plantName;
	private String	plantAdrs1;
	private String	plantAdrs2;
	private String	dlvName;
	private String	dlvAdrs1;
	private String	dlvAdrs2;
	private String	mileage;
	private String	vin;
	private String	bodySerial;
	private String	unitGvw;
	private String	cat;
	private String	slprCode;
	private String	aeroRoof;
	private String	aeroSide;
	private String	aeroCab;
	private String	trnstPo;
	private Date	actDelvry;
	private String 	status;
	private Date 	requestedPickupDate;
	private Date 	transitDateDelivered;
	private double 	frieghtAmount;
	private double 	fuelSurcharge;
	private double 	totalAmount; 
	private String 	plantCode;
	private String 	destCode;
	private String 	oem;
	private String 	isPOAmountAccepted;
	private double 	fluids;
	private double 	breakDown;
	private double 	tolls;
	private double 	misc;
	private double 	revisedPOAmount;
	
	// December 18, 2012
	// D. Roth - Support for new Advanced Notice project
	private Date 	productionDate;
	private Date 	estimatedDeliveryDate;	
	private Date 	holdNotificationDate;
	private Date 	advanceNoticeCancelDate;
	private String 	originCity;
	private String 	originState;
	private String 	originZipCode;
	private String 	originContactInfo;
	private String 	destCity;
	private String 	destState;
	private String 	destZipCode;
	private String 	destContactInfo;
	private String 	vehicleCategory;
	private String 	vehicleType;
	private String 	fuelType;
	private String 	decking;
	private String 	numDecks;
	private double 	penskeAddOnCharge;
	private Date 	transitPoDate;
	private Date 	actualDeliveryReported;
	private String 	penskeComment;
	private String 	transporterComment;
	private Date 	transporterAssignDate;
	private Date 	lastChangedDate;
	private String 	companyCode;
	private String 	parentVendor;
	private String	purchasePoNum;
	private String 	advancedNoticeSequence;
	private String	liftgateMake;
	private String	liftgateModel;
	private String 	reportId;
	
	//April 2, 2018
	//J. Frey - VOD-1848
	private double canadaTax;
	private String transmissionType;
	
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo=="" ? "0" : unitNo;
	}
	public String getIsPOAmountAccepted() {
		return isPOAmountAccepted;
	}
	public void setIsPOAmountAccepted(String isPOAmountAccepted) {
		this.isPOAmountAccepted = isPOAmountAccepted==null ? " " : isPOAmountAccepted.trim();
	}
	public String getAeroCab() {
		return aeroCab;
	}
	public void setAeroCab(String aeroCab) {
		this.aeroCab = aeroCab==null ? " " : aeroCab;
	}
	public String getAeroRoof() {
		return aeroRoof;
	}
	public void setAeroRoof(String aeroRoof) {
		this.aeroRoof = aeroRoof==null ? " " : aeroRoof;
	}
	public String getAeroSide() {
		return aeroSide;
	}
	public void setAeroSide(String aeroSide) {
		this.aeroSide = aeroSide==null ? " " : aeroSide;
	}
	public String getBodySerial() {
		return bodySerial;
	}
	public void setBodySerial(String bodySerial) {
		this.bodySerial = bodySerial==null ? " " : bodySerial;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat=="" ? " " : cat;
	}
	public String getDlvAdrs1() {
		return dlvAdrs1;
	}
	public void setDlvAdrs1(String dlvAdrs1) {
		this.dlvAdrs1 = dlvAdrs1==null ? " " : dlvAdrs1;
	}
	public String getDlvAdrs2() {
		return dlvAdrs2;
	}
	public void setDlvAdrs2(String dlvAdrs2) {
		this.dlvAdrs2 = dlvAdrs2==null ? " " : dlvAdrs2;
	}
	public String getDlvName() {
		return dlvName;
	}
	public void setDlvName(String dlvName) {
		this.dlvName = dlvName==null ? " " : dlvName;
	}
	public String getPlantAdrs1() {
		return plantAdrs1;
	}
	public void setPlantAdrs1(String plantAdrs1) {
		this.plantAdrs1 = plantAdrs1==null ? " " : plantAdrs1;
	}
	public String getPlantAdrs2() {
		return plantAdrs2;
	}
	public void setPlantAdrs2(String plantAdrs2) {
		this.plantAdrs2 = plantAdrs2==null ? " " : plantAdrs2;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName==null ? " " : plantName;
	}
	public String getSlprCode() {
		return slprCode;
	}
	public void setSlprCode(String slprCode) {
		this.slprCode = slprCode==null ? " " : slprCode;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode==null ? " " : destCode;
	}
	public double getFrieghtAmount() {
		return frieghtAmount;
	}
	public String getOem() {
		return oem;
	}
	public void setOem(String oem) {
		this.oem = oem==null ? " " : oem;
	}
	public String getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode==null ? " " : plantCode;
	}
	public String getTrnstPo() {
		return trnstPo;
	}
	public void setTrnstPo(String trnstPo) {
		this.trnstPo = trnstPo==null ? " " : trnstPo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin==null ? " " : vin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status==null ? " " : status;
	}
	
	//really integers
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage=="" ? "0" : mileage;
	}
	public String getPickupVendor() {
		return pickupVendor;
	}
	public void setPickupVendor(String pickupVendor) {
		this.pickupVendor = pickupVendor=="" ? "0" : pickupVendor;
	}
	public String getUnitGvw() {
		return unitGvw;
	}
	public void setUnitGvw(String unitGvw) {
		this.unitGvw = unitGvw=="" ? "0" : unitGvw;
	}
	public double getBreakDown() {
		return breakDown;
	}
	public void setBreakDown(double breakDown) {
		this.breakDown = breakDown;
	}
	public double getFluids() {
		return fluids;
	}
	public void setFluids(double fluids) {
		this.fluids = fluids;
	}
	public double getMisc() {
		return misc;
	}
	public void setMisc(double misc) {
		this.misc = misc;
	}
	public double getRevisedPOAmount() {
		return revisedPOAmount;
	}
	public void setRevisedPOAmount(double revisedPOAmount) {
		this.revisedPOAmount = revisedPOAmount;
	}
	public double getTolls() {
		return tolls;
	}
	public void setTolls(double tolls) {
		this.tolls = tolls;
	}
	public void setFrieghtAmount(double frieghtAmount) {
		this.frieghtAmount = frieghtAmount;
	}
	public double getFuelSurcharge() {
		return fuelSurcharge;
	}
	public void setFuelSurcharge(double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getRequestedPickupDate() {
		return requestedPickupDate;
	}
	public void setRequestedPickupDate(Date requestedPickupDate) {
		if (requestedPickupDate == null)
			this.requestedPickupDate = getAS400DefaultDate();
		else
			this.requestedPickupDate = requestedPickupDate;
	}
	public Date getTransitDateDelivered() {
		return transitDateDelivered;
	}
	public void setTransitDateDelivered(Date transitDateDelivered) {
		if (transitDateDelivered == null)
			this.transitDateDelivered = getAS400DefaultDate();
		else
			this.transitDateDelivered = transitDateDelivered;
	}
	public Date getActDelvry() {
		return actDelvry;
	}
	public void setActDelvry(Date actDelvry) {
		try{
			if (actDelvry == null){
				this.actDelvry = getAS400DefaultDate();
				setStrActDelvry(" ");
			}else{
				this.actDelvry = actDelvry;
				//is this correct?, setting a string to date
				setStrActDelvry(this.actDelvry);
			}
		}catch (Exception e){
			logger.debug(e);
			setStrActDelvry(" ");
		}
	}
	public String getStrActDelvry() {
		return strActDelvry;
	}
	public void setStrActDelvry(Date actDelvry) {
		
		this.strActDelvry = DateFormat.getDateInstance(DateFormat.SHORT).format(actDelvry);
	}
	public void setStrActDelvry(String strActDelvry) {
			
		this.strActDelvry = strActDelvry;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}
	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}
	public Date getHoldNotificationDate() {
		return holdNotificationDate;
	}
	public void setHoldNotificationDate(Date holdNotificationDate) {
		if (holdNotificationDate == null)
			this.holdNotificationDate = getAS400DefaultDate();
		else
			this.holdNotificationDate = holdNotificationDate;
	}
	public Date getAdvanceNoticeCancelDate() {
		return advanceNoticeCancelDate;
	}
	public void setAdvanceNoticeCancelDate(Date advanceNoticeCancelDate) {
		this.advanceNoticeCancelDate = advanceNoticeCancelDate;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public String getOriginZipCode() {
		return originZipCode;
	}
	public void setOriginZipCode(String originZipCode) {
		this.originZipCode = originZipCode;
	}
	public String getOriginContactInfo() {
		return originContactInfo;
	}
	public void setOriginContactInfo(String originContactInfo) {
		this.originContactInfo = originContactInfo;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getDestState() {
		return destState;
	}
	public void setDestState(String destState) {
		this.destState = destState;
	}
	public String getDestZipCode() {
		return destZipCode;
	}
	public void setDestZipCode(String destZipCode) {
		this.destZipCode = destZipCode;
	}
	public String getDestContactInfo() {
		return destContactInfo;
	}
	public void setDestContactInfo(String destContactInfo) {
		this.destContactInfo = destContactInfo;
	}
	public String getVehicleCategory() {
		return vehicleCategory;
	}
	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getDecking() {
		return decking;
	}
	public void setDecking(String decking) {
		this.decking = decking;
	}
	public String getNumDecks() {
		return numDecks;
	}
	public void setNumDecks(String numDecks) {
		this.numDecks = numDecks=="" ? "0" : numDecks;
	}
	public double getPenskeAddOnCharge() {
		return penskeAddOnCharge;
	}
	public void setPenskeAddOnCharge(double penskeAddOnCharge) {
		this.penskeAddOnCharge = penskeAddOnCharge;
	}
	public Date getTransitPoDate() {
		return transitPoDate;
	}
	public void setTransitPoDate(Date transitPoDate) {
		this.transitPoDate = transitPoDate;
	}
	public Date getActualDeliveryReported() {
		return actualDeliveryReported;
	}
	public void setActualDeliveryReported(Date actualDeliveryReported) {
		this.actualDeliveryReported = actualDeliveryReported;
	}
	public String getPenskeComment() {
		return penskeComment;
	}
	public void setPenskeComment(String penskeComment) {
		this.penskeComment = penskeComment;
	}
	public String getTransporterComment() {
		return transporterComment;
	}
	public void setTransporterComment(String transporterComment) {
		this.transporterComment = transporterComment;
	}
	public Date getTransporterAssignDate() {
		return transporterAssignDate;
	}
	public void setTransporterAssignDate(Date transporterAssignDate) {
		this.transporterAssignDate = transporterAssignDate;
	}
	public Date getLastChangedDate() {
		return lastChangedDate;
	}
	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getParentVendor() {
		return parentVendor;
	}
	public void setParentVendor(String parentVendor) {
		this.parentVendor = parentVendor;
	}
	public String getAdvancedNoticeSequence() {
		return advancedNoticeSequence;
	}
	public void setAdvancedNoticeSequence(String advancedNoticeSequence) {
		this.advancedNoticeSequence = advancedNoticeSequence;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public boolean isPilot() {
		return pilot;
	}
	public void setPilot(boolean pilot) {
		this.pilot = pilot;
	}
	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public String getPlantAdrs3() {
		return plantAdrs3;
	}
	public void setPlantAdrs3(String plantAdrs3) {
		this.plantAdrs3 = plantAdrs3==null ? " " : plantAdrs3;	
	}
	public String getDlvAdrs3() {
		return dlvAdrs3;
	}
	public void setDlvAdrs3(String dlvAdrs3) {
		this.dlvAdrs3 = dlvAdrs3==null ? " " : dlvAdrs3;	
	}
	public String getMiscComments() {
		return miscComments;
	}
	public void setMiscComments(String miscComments) {
		this.miscComments = miscComments==null ? " " : miscComments;	
	}
	public String getReqPickup() {
		return reqPickup;
	}
	public void setReqPickup(String reqPickup) {
		this.reqPickup = reqPickup==null ? " " : reqPickup;	
	}
	public String getReqDelvry() {
		return reqDelvry;
	}
	public void setReqDelvry(String reqDelvry) {
		this.reqDelvry = reqDelvry==null ? " " : reqDelvry;		
	}
	public String getPurchasePoNum() {
		return purchasePoNum;
	}
	public void setPurchasePoNum(String purchasePoNum) {
		this.purchasePoNum = purchasePoNum==null ? " " : purchasePoNum;
	}
	public String getLiftgateMake() {
		return liftgateMake;
	}
	public void setLiftgateMake(String liftgateMake) {
		this.liftgateMake = liftgateMake;
	}
	public String getLiftgateModel() {
		return liftgateModel;
	}
	public void setLiftgateModel(String liftgateModel) {
		this.liftgateModel = liftgateModel;
	}
	private Date getAS400DefaultDate()
	{
		return DateUtil.parseDate("0001-01-01");
	}
	public double getCanadaTax() {
		return canadaTax;
	}
	public void setCanadaTax(double canadaTax) {
		this.canadaTax = canadaTax;
	}
	public String getTransmissionType() {
		return transmissionType;
	}
	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}
}
