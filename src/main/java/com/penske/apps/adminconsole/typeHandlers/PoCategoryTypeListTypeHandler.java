package com.penske.apps.adminconsole.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.penske.apps.adminconsole.enums.PoCategoryType;

/**
 * This is a TypeHandler that is only specific to getting a List of PoCategoryTypes
 * based on manufacturer field codes IN ONLY a comma separated String format.
 * 
 * @author erik.munoz 600139451
 *
 */
public class PoCategoryTypeListTypeHandler extends BaseTypeHandler<List<PoCategoryType>> {

    /**
     * Override: @see org.apache.ibatis.type.EnumTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public List<PoCategoryType> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        
        String columnValueCommaSeprated = resultSet.getString(columnName);
       
        List<PoCategoryType> poCategoryTypesByMfrFieldCodes = getPoCategoryTypesByMfrFieldCodes(columnValueCommaSeprated);
        
        return poCategoryTypesByMfrFieldCodes;
    }

    /**
     * Override: @see org.apache.ibatis.type.EnumTypeHandler#getNullableResult(java.sql.ResultSet, int)
     */
    @Override
    public List<PoCategoryType> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {

        String columnValueCommaSeprated = resultSet.getString(columnIndex);
        
        List<PoCategoryType> poCategoryTypesByMfrFieldCodes = getPoCategoryTypesByMfrFieldCodes(columnValueCommaSeprated);
        
        return poCategoryTypesByMfrFieldCodes;
    }

    /**
     * Override: @see org.apache.ibatis.type.EnumTypeHandler#getNullableResult(java.sql.CallableStatement, int)
     */
    @Override
    public List<PoCategoryType> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        
        String columnValueCommaSeprated = callableStatement.getString(columnIndex);
        
        List<PoCategoryType> poCategoryTypesByMfrFieldCodes = getPoCategoryTypesByMfrFieldCodes(columnValueCommaSeprated);
        
        return poCategoryTypesByMfrFieldCodes;
    }

    /**
     * Override: @see org.apache.ibatis.type.EnumTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Enum, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int columnIndex, List<PoCategoryType> parameter, JdbcType jdbcType) throws SQLException
    {}
    
    /**
     * Returns a list of PoCategoryTypes based on a String comma separated list of manufacturer field codes
     */
    private List<PoCategoryType> getPoCategoryTypesByMfrFieldCodes(String columnValueCommaSeprated) {
        
        String[] columnValueArray = columnValueCommaSeprated.split(",");

        List<String> columnValueList = new ArrayList<String>(Arrays.asList(columnValueArray));

        columnValueList.removeAll(Arrays.asList(""));
        
        List<PoCategoryType> poCategoryTypes = new ArrayList<PoCategoryType>();
        PoCategoryType[] pocaCategoryTypeValues = PoCategoryType.values();
        
        for (String mfrFieldCode : columnValueList) {
           
            for (PoCategoryType poCategoryType : pocaCategoryTypeValues) {
                
                String currentMfrFieldCode = poCategoryType.getMfrFieldCode();
                
                boolean sameFieldCodes = (currentMfrFieldCode == mfrFieldCode || mfrFieldCode.equals(currentMfrFieldCode));
                
                if(sameFieldCodes) poCategoryTypes.add(poCategoryType);
                
            }
            
        }

        return poCategoryTypes;
        
    }

}