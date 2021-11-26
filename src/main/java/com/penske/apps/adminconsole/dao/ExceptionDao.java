package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
/**
 * This interface contains the myBatis DAO methods that relay
 * directly to the database to retrieve data and return it to the
 * server.  It pulls data for the Global Exceptions and Unit Exceptions
 * pages.
 * @author 600132441 M.Leis
 *
 */
@DBSmc
public interface ExceptionDao {

    // Global Exception Dao Methods
	@SkipQueryTest("xmlserialize and DATE function")
    public List<GlobalException> getGlobalExceptions(@Param("exceptionId") Integer exceptionId,@Param("unitNumber")String unitNumber,@Param("poNumber")Integer poNumber);

    @NonVendorQuery
    public void deleteGlobalException(int exceptionId);

    @NonVendorQuery
    public void modifyGlobalException(@Param("exceptionId")int exceptionId, @Param("providervendorId")int providervendorId, @Param("poCategoryAssociationId")int poCategoryAssociationId,@Param("modifiedBy")String modifiedBy);
}