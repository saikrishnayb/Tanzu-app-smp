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
			workbook = WorkbookFactory.create(this.getClass().getClassLoader().getResourceAsStream("resources/transporter-report-example.xls"));
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
        assertThat(transport.getPickupVendor(), is("800473"));
        assertThat(transport.getStatus(), is("Pending Approval"));
        assertThat(transport.getUnitNo(), is("277359"));
        assertThat(transport.getRequestedPickupDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 13).getTime()));
        assertThat(transport.getProductionDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime()));
        assertThat(transport.getEstimatedDeliveryDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime()));
        assertThat(transport.getHoldNotificationDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getAdvanceNoticeCancelDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getOem(), is("MOR"));
        assertThat(transport.getPlantCode(), is("N/F"));
        assertThat(transport.getPlantName(), is("MORGAN CANADA CORP"));
        assertThat(transport.getPlantAdrs1(), is("12 CHELSEA LANE"));
        assertThat(transport.getPlantAdrs2(), is(""));
        assertThat(transport.getOriginCity(), is("BRAMPTON"));
        assertThat(transport.getOriginState(), is("ON"));
        assertThat(transport.getOriginZipCode(), is("L6T 3Y4"));
        assertThat(transport.getOriginContactInfo(), is("905 791-8100"));
        assertThat(transport.getDestCode(), is("023132"));
        assertThat(transport.getDlvName(), is("HIWAY REFRIGERATION LTD"));
        assertThat(transport.getDlvAdrs1(), is("1462 MUSTANG PLACE"));
        assertThat(transport.getDlvAdrs2(), is("604-944-0119"));
        assertThat(transport.getDestCity(), is("PORT COQUITLAM"));
        assertThat(transport.getDestState(), is("BC"));
        assertThat(transport.getDestZipCode(), is("V3C 6L2"));
        assertThat(transport.getDestContactInfo(), is("905 791-8100"));
        assertThat(transport.getMileage(), is("2699"));
        assertThat(transport.getVin(), is("JALE5W161K7302368"));
        assertThat(transport.getBodySerial(), is("CB06718001001"));
        assertThat(transport.getUnitGvw(), is("19500"));
        assertThat(transport.getVehicleCategory(), is("TRUCK"));
        assertThat(transport.getVehicleType(), is("REEFER"));
        assertThat(transport.getFuelType(), is("DIESEL"));
        assertThat(transport.getSlprCode(), is("N"));
        assertThat(transport.getAeroRoof(), is("NONE"));
        assertThat(transport.getAeroSide(), is("N"));
        assertThat(transport.getAeroCab(), is("N"));
        assertThat(transport.getDecking(), is("N"));
        assertThat(transport.getNumDecks(), is("0"));
        assertThat(transport.getPenskeAddOnCharge(), is(4420.00));
        assertThat(transport.getTotalAmount(), is(4420.00));
        assertThat(transport.getTrnstPo(), is(""));
        assertThat(transport.getTransitPoDate(), is(new GregorianCalendar(1, Calendar.JANUARY, 1).getTime()));
        assertThat(transport.getActDelvry(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime()));
        assertThat(transport.getActualDeliveryReported(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 20).getTime()));
        assertThat(transport.getFluids(), is(0.00));
        assertThat(transport.getBreakDown(), is(0.00));
        assertThat(transport.getTolls(), is(0.00));
        assertThat(transport.getMisc(), is(0.00));
        assertThat(transport.getRevisedPOAmount(), is(4420.0));
        assertThat(transport.getIsPOAmountAccepted(), is(""));
        assertThat(transport.getPenskeComment(), is(""));
        assertThat(transport.getTransporterComment(), is("Delivered-Incorrect delivery address"));
        assertThat(transport.getTransporterAssignDate(), is(new GregorianCalendar(2019, Calendar.FEBRUARY, 12).getTime()));
        assertThat(transport.getLastChangedDate(), is(new GregorianCalendar(2018, Calendar.NOVEMBER, 12).getTime()));
        assertThat(transport.getCompanyCode(), is("2000"));
        assertThat(transport.getLiftgateMake(), is(""));
        assertThat(transport.getLiftgateModel(), is(""));
        assertThat(transport.getReportId(), is("2019-03-19 14:47:44.906000"));
        assertThat(transport.getCanadaTax(), is(0.00));
        assertThat(transport.getTransmissionType(), is("A"));
	}

}
