package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.UnitException;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
// models
/**
 * This interface contains the myBatis DAO methods that relay
 * directly to the database to retrieve data and return it to the
 * server.  It pulls data for the Global Exceptions and Unit Exceptions
 * pages.
 * @author 600132441 M.Leis
 *
 */

public interface ExceptionDao {

    // Global Exception Dao Methods
    @NonVendorQuery
    public String getComponent(int dataId);

    @NonVendorQuery
    public String getVehicle(int dataId);

    public List<GlobalException> getGlobalExceptions(@Param("exceptionId") Integer exceptionId,@Param("unitNumber")String unitNumber,@Param("poNumber")Integer poNumber);

    @NonVendorQuery
    public void deleteGlobalException(int exceptionId);

    @NonVendorQuery
    public String getCreatorFirstName(int id);

    @NonVendorQuery
    public String getCreatorLastName(int id);

    @NonVendorQuery
    public List<String> getSubGroup(String primaryGroup);

    @NonVendorQuery
    public void modifyGlobalException(@Param("exceptionId")int exceptionId, @Param("providervendorId")int providervendorId, @Param("poCategoryAssociationId")int poCategoryAssociationId,@Param("modifiedBy")String modifiedBy);

    // Unit Exception Dao Methods
    @NonVendorQuery
    public List<UnitException> getUnitExceptions();

    @NonVendorQuery
    public UnitException getUnitException(int id);

    @NonVendorQuery
    public void deleteUnitException(@Param("id")int id);

    @NonVendorQuery
    public void modifyUnitException(@Param("id")int id, @Param("provider")String providerPo, @Param("subProvider")String subProviderPo);

    @NonVendorQuery
    public void addGlobalException(UnitException exception);
    
}
