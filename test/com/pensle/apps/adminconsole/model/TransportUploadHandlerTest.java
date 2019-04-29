package com.pensle.apps.adminconsole.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import com.penske.apps.adminconsole.model.Transport;
import com.penske.apps.adminconsole.model.TransportUploadHandler;

public class TransportUploadHandlerTest {
	
	TransportUploadHandler transportHandler = new TransportUploadHandler();
	public static final String SAMPLE_XLSX_FILE_PATH = "resources/transporter-report-example.xls";
	
	@Test
	public void shouldPopulateExcelData() {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(this.getClass().getClassLoader().getResourceAsStream("resources/transporter-report-example-1.xls"));
		} catch (EncryptedDocumentException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Row row = firstSheet.getRow(1);
	    Transport transport = new Transport(true);
	    
	    Iterator<Cell> cells = row.cellIterator();
		int cellNum = 0;
		String value = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    while (cells.hasNext()) {
			Cell cell = cells.next();
			cellNum = cell.getColumnIndex();

	        // POI 3.15 deprecated both getCellType() and getCellTypeEnum() in preparation for transitioning to
	        // making getCellType() return an enum in 4.0.
	        // As of 2018-11-28, we are using 3.15. If we upgrade to 4.0, this should be switched to use the
	        // getCellType() method which returns an enum and is not deprecated.
	        @SuppressWarnings("deprecation")
			CellType cellType = cell.getCellTypeEnum();
			
			switch (cellType) {
			case NUMERIC:
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					try{
						value = sdf.format(cell.getDateCellValue());
					} catch(Exception e) {
					}
				} else {
					if(cell.getCellStyle().getDataFormat() == 0) {
						value = new HSSFDataFormatter().formatCellValue(cell);
					} else {
						value = String.valueOf(cell.getNumericCellValue());                 
					}
				}
				transportHandler.populateExcelData(value, transport,cellNum,row);
				break;
			case STRING:
				value = String.valueOf(cell.getRichStringCellValue());
				transportHandler.populateExcelData(value, transport,cellNum,row);
				break;
			case BLANK:
				value = "";
				transportHandler.populateExcelData(value,transport,cellNum,row);      
				break;
			case FORMULA:
	            value=String.valueOf(cell.getNumericCellValue());
	            transportHandler.populateExcelData(value,transport,cellNum,row);
	            break;
			default:
				value = "unsuported cell type";
				break;
			}                   
		}
        
        assertThat(transport.getUnitNo(), is("283768"));
        assertThat(transport.getStatus(), is("Pick-up"));
        assertThat(transport.getProductionDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 14).getTime()));
        assertThat(transport.getRequestedPickupDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 26).getTime()));
        assertThat(transport.getEstimatedDeliveryDate(), is(new GregorianCalendar(2019, Calendar.MARCH, 1).getTime()));
        assertThat(transport.getActDelvry(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getActualDeliveryReported(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getTrnstPo(), is(""));
        assertThat(transport.getTransitPoDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getMileage(), is("78"));
        assertThat(transport.getPenskeAddOnCharge(), is(0.00));
        assertThat(transport.getFreightCharge(), is(175.00));
        assertThat(transport.getFuelCharge(), is(0.00));
        assertThat(transport.getTotalAmount(), is(175.00));
        assertThat(transport.getFluids(), is(0.00));
        assertThat(transport.getBreakDown(), is(0.00));
        assertThat(transport.getTolls(), is(0.00));
        assertThat(transport.getMisc(), is(0.00));
        assertThat(transport.getRevisedPOAmount(), is(175.00));
        assertThat(transport.getCanadaTax(), is(0.00));
        assertThat(transport.getIsPOAmountAccepted(), is(""));
        assertThat(transport.getTransporterComment(), is(""));
        assertThat(transport.getPenskeComment(), is(""));
        assertThat(transport.getHoldNotificationDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getAdvanceNoticeCancelDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getOem(), is("SUP"));
        assertThat(transport.getPlantCode(), is("050"));
        assertThat(transport.getPlantName(), is("SUPREME - JONESTOWN"));
        assertThat(transport.getPlantAdrs1(), is("411 JONESTOWN ROAD"));
        assertThat(transport.getPlantAdrs2(), is(""));
        assertThat(transport.getOriginCity(), is("JONESTOWN"));
        assertThat(transport.getOriginState(), is("PA"));
        assertThat(transport.getOriginZipCode(), is("17038"));
        assertThat(transport.getOriginContactInfo(), is(""));
        assertThat(transport.getDestCode(), is("039310"));
        assertThat(transport.getDlvName(), is("PENSKE TRUCK LEASING CO."));
        assertThat(transport.getDlvAdrs1(), is("499 SHOEMAKER RD"));
        assertThat(transport.getDlvAdrs2(), is(""));
        assertThat(transport.getDestCity(), is("KING OF PRUSSIA"));
        assertThat(transport.getDestState(), is("PA"));
        assertThat(transport.getDestZipCode(), is("19406"));
        assertThat(transport.getDestContactInfo(), is("(610) 275-5083"));
        assertThat(transport.getVin(), is("3HAEUMML7LL300515"));
        assertThat(transport.getBodySerial(), is("J0506533"));
        assertThat(transport.getUnitGvw(), is("25999"));
        assertThat(transport.getVehicleCategory(), is("TRUCK"));
        assertThat(transport.getVehicleType(), is("VAN"));
        assertThat(transport.getFuelType(), is("DIESEL"));
        assertThat(transport.getSlprCode(), is("N"));
        assertThat(transport.getAeroRoof(), is("NONE"));
        assertThat(transport.getAeroSide(), is("N"));
        assertThat(transport.getAeroCab(), is("N"));
        assertThat(transport.getDecking(), is("N"));
        assertThat(transport.getNumDecks(), is("0"));
        assertThat(transport.getTransporterAssignDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 22).getTime()));
        assertThat(transport.getLastChangedDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 25).getTime()));
        assertThat(transport.getCompanyCode(), is("HPTL"));
        assertThat(transport.getLiftgateMake(), is("WAL"));
        assertThat(transport.getTransmissionType(), is("A"));
        assertThat(transport.getLiftgateModel(), is("EMWAP33"));
        assertThat(transport.getPickupVendor(), is("17618"));
        assertThat(transport.getPoCategory(), is("BODY"));
        assertThat(transport.getPenskePo(), is("484313"));
        assertThat(transport.getParentVendor(), is("17618"));
        assertThat(transport.getAdvNoticeSeq(), is("0"));
        assertThat(transport.getReportId(), is("2019-04-22 16:31:50.622000"));
        
	}

}
