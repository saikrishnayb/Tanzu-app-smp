package com.penske.apps.adminconsole.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import com.penske.apps.adminconsole.domain.OrgVendorAssociation;
import com.penske.apps.smccore.base.util.DateUtil;

public class VendorActivityReportTest {

	@Test
	public void shouldCreateEmptyVendorReport() {
		List<Vendor> vendors = new ArrayList<>();
		
		List<VendorPoInformation> vendorPoInformations = new ArrayList<>();
		
		List<EditableUser> vendorUsers = new ArrayList<>();
		
		List<OrgVendorAssociation> orgVendorAsscoaitions = new ArrayList<>();
		
		VendorActivityReport result = new VendorActivityReport(vendors, vendorPoInformations, vendorUsers, orgVendorAsscoaitions);
		
		SXSSFWorkbook workbook = result.getVendorActivityReport();
		
		assertThat(workbook.getNumberOfSheets(), is(2));
		//should only have header
		assertThat(workbook.getSheet("Vendor Activity").getPhysicalNumberOfRows(), is(1));
		//should only have header
		assertThat(workbook.getSheet("Vendor Access").getPhysicalNumberOfRows(), is(1));
	}
	
	@Test
	public void shouldCreateNewVendorReport() {
		VendorContact vendorContact = mock(VendorContact.class);
		when(vendorContact.getFirstName()).thenReturn("Contact");
		when(vendorContact.getLastName()).thenReturn("Guy");
		when(vendorContact.getPhoneNumber()).thenReturn("5555555555");
		
		EditableUser planningAnalyst = mock(EditableUser.class);
		when(planningAnalyst.getFirstName()).thenReturn("Planning");
		when(planningAnalyst.getLastName()).thenReturn("Analyst");
		
		Vendor vendor1 = mock(Vendor.class);
		when(vendor1.getVendorName()).thenReturn("Big Truck Inc");
		when(vendor1.getVendorNumber()).thenReturn(123);
		when(vendor1.getVendorId()).thenReturn(1);
		when(vendor1.getMfrCodes()).thenReturn(Arrays.asList("FTL"));
		when(vendor1.getPrimaryContact()).thenReturn(vendorContact);
        when(vendor1.getPlanningAnalyst()).thenReturn(planningAnalyst);
        when(vendor1.getAnnualAgreement()).thenReturn("Y");
        when(vendor1.getOrgId()).thenReturn(1);
        
        
        Vendor vendor2 = mock(Vendor.class);
		when(vendor2.getVendorName()).thenReturn("Extra Big Truck Inc");
		when(vendor2.getVendorNumber()).thenReturn(124);
		when(vendor2.getVendorId()).thenReturn(2);
		when(vendor2.getMfrCodes()).thenReturn(Arrays.asList("FTL"));
		when(vendor2.getPrimaryContact()).thenReturn(vendorContact);
        when(vendor2.getPlanningAnalyst()).thenReturn(planningAnalyst);
        when(vendor2.getAnnualAgreement()).thenReturn("N");
        when(vendor2.getOrgId()).thenReturn(1);
        	
        VendorPoInformation vendorPoInformation = mock(VendorPoInformation.class);
        when(vendorPoInformation.getVendorId()).thenReturn(1);
        when(vendorPoInformation.getLastPoDate()).thenReturn(LocalDate.now());
        when(vendorPoInformation.getPosIssuedInLast3Years()).thenReturn(10);
        
        EditableUser vendorUser1 = mock(EditableUser.class);
        when(vendorUser1.getFirstName()).thenReturn("Imma");
    	when(vendorUser1.getLastName()).thenReturn("Vendor");
    	when(vendorUser1.getEmail()).thenReturn("imma.vendor@vendoruser.com");
        when(vendorUser1.getOrg()).thenReturn("BIG ORG");
        when(vendorUser1.getOrgId()).thenReturn(2);
        when(vendorUser1.getFormattedCreatedDate()).thenReturn(DateUtil.formatDateUS(LocalDate.now()));
        when(vendorUser1.getFormattedLastLoginDate()).thenReturn(DateUtil.formatDateUS(LocalDate.now()));
        
        EditableUser vendorUser2 = mock(EditableUser.class);
        when(vendorUser2.getFirstName()).thenReturn("Vendor");
    	when(vendorUser2.getLastName()).thenReturn("User");
    	when(vendorUser2.getEmail()).thenReturn("vendor.user@vendor.com");
        when(vendorUser2.getOrg()).thenReturn("SMOL ORG");
        when(vendorUser2.getOrgId()).thenReturn(3);
        when(vendorUser2.getFormattedCreatedDate()).thenReturn(DateUtil.formatDateUS(LocalDate.now()));
        when(vendorUser2.getFormattedLastLoginDate()).thenReturn(DateUtil.formatDateUS(LocalDate.now()));
        
        OrgVendorAssociation ova1 = mock(OrgVendorAssociation.class);
        when(ova1.getOrgId()).thenReturn(2);
        when(ova1.getVendorId()).thenReturn(1);
        
        OrgVendorAssociation ova2 = mock(OrgVendorAssociation.class);
        when(ova2.getOrgId()).thenReturn(2);
        when(ova2.getVendorId()).thenReturn(2);
        
        OrgVendorAssociation ova3 = mock(OrgVendorAssociation.class);
        when(ova3.getOrgId()).thenReturn(3);
        when(ova3.getVendorId()).thenReturn(1);
		
		List<Vendor> vendors = Arrays.asList(vendor1, vendor2);
		
		List<VendorPoInformation> vendorPoInformations = Arrays.asList(vendorPoInformation);
		
		List<EditableUser> vendorUsers = Arrays.asList(vendorUser1, vendorUser2);
		
		List<OrgVendorAssociation> orgVendorAsscoaitions = Arrays.asList(ova1, ova2, ova3);
		
		VendorActivityReport result = new VendorActivityReport(vendors, vendorPoInformations, vendorUsers, orgVendorAsscoaitions);
		
		SXSSFWorkbook workbook = result.getVendorActivityReport();
		SXSSFSheet vendorActivitySheet = workbook.getSheet("Vendor Activity");
		SXSSFSheet vendorAccessSheet = workbook.getSheet("Vendor Access");
		assertThat(workbook.getNumberOfSheets(), is(2));
		
		// ***** VENDOR ACTIVITY ***** //
		// header row and two vendor rows
		assertThat(vendorActivitySheet.getPhysicalNumberOfRows(), is(3));
		
		assertThat(vendorActivitySheet.getRow(1).getCell(0).getStringCellValue(), is("Big Truck Inc"));
		assertThat(vendorActivitySheet.getRow(1).getCell(3).getStringCellValue(), is("Yes"));
		assertThat(vendorActivitySheet.getRow(1).getCell(7).getStringCellValue(), is(DateUtil.formatDateUS(LocalDate.now())));
		assertThat(vendorActivitySheet.getRow(1).getCell(8).getNumericCellValue(), is(Double.valueOf(10)));
		
		assertThat(vendorActivitySheet.getRow(2).getCell(0).getStringCellValue(), is("Extra Big Truck Inc"));
		assertThat(vendorActivitySheet.getRow(2).getCell(3).getStringCellValue(), is("No"));
		assertThat(vendorActivitySheet.getRow(2).getCell(7).getStringCellValue(), is(""));
		assertThat(vendorActivitySheet.getRow(2).getCell(8).getNumericCellValue(), is(Double.valueOf(0)));
		
		// ***** VENDOR ACCESS ***** //
		// header row, three vendor user rows, 2 blank rows
		assertThat(vendorAccessSheet.getPhysicalNumberOfRows(), is(6));
		
		assertThat(vendorAccessSheet.getRow(1).getCell(0).getStringCellValue(), is("Big Truck Inc"));
		assertThat(vendorAccessSheet.getRow(1).getCell(2).getStringCellValue(), is("Imma"));
		assertThat(vendorAccessSheet.getRow(1).getCell(3).getStringCellValue(), is("Vendor"));
		
		assertThat(vendorAccessSheet.getRow(2).getCell(0).getStringCellValue(), is("Big Truck Inc"));
		assertThat(vendorAccessSheet.getRow(2).getCell(2).getStringCellValue(), is("Vendor"));
		assertThat(vendorAccessSheet.getRow(2).getCell(3).getStringCellValue(), is("User"));
		
		assertThat(vendorAccessSheet.getRow(4).getCell(0).getStringCellValue(), is("Extra Big Truck Inc"));
		assertThat(vendorAccessSheet.getRow(4).getCell(2).getStringCellValue(), is("Imma"));
		assertThat(vendorAccessSheet.getRow(4).getCell(3).getStringCellValue(), is("Vendor"));
		//should only have header
		//assertThat(.getPhysicalNumberOfRows(), is(1));
	}
}
