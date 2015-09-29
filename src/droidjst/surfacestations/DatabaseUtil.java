
/*
    Copyright 2015 Joseph Tranquillo {name of copyright owner}

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */


package droidjst.surfacestations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil
{
    private final String PATH = new File("").getAbsolutePath();
    
    public void parseFileToSQLite() throws IOException, SQLException
    {
        Connection connection = null;
        Statement statement = null;
        
        Database database = new Database();
        
        Object[] tuple = database.getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        BufferedReader breader = null;
        
        ParseUtil parseutil = new ParseUtil();
        
        try
        {
            breader = new BufferedReader(new FileReader(PATH + Const.FILE_SEP + "stations"), 16 * 1024);
            
            String line = null;
            
            while((line = breader.readLine()) != null)
            {
                if(line.length() != 83)
                {
                    continue;
                }
                
                if(line.equals("") || 
                   line.substring(0, 1).equals("!") ||
                   line.matches(".*([\\d]{2}-[A-Z]{3}-[\\d]{2}[\\s]*)"))
                {
                    continue;
                }
                
                parseutil.parseLine(line, true);
                
                statement = connection.createStatement();
                
                statement.executeUpdate(createInsertStatement(parseutil));
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            ReaderUtil.finalizeReaders(breader);
            
            DatabaseUtil.finalizeConnection(connection, statement);
        }
    }
    
    private String createInsertStatement(ParseUtil parseutil)
    {
        String format = 
            "INSERT INTO %s (cd, station, icao, iata, synop, lat, lon, elev, m, n, v, u, a, c, p, cc)" +
            " VALUES ('%s', '%s', '%s', '%s', '%s', %f, %f, %d, '%s', '%s', '%s', '%s', '%s', '%s', %d, '%s')";
        
        String sql_insert = 
            String.format(format, 
                    Const.DB_TABLE,
                    parseutil.getStateAbbrv(),
                    parseutil.getStationName().replace("'", "''"),
                    parseutil.getICAOId(),
                    parseutil.getIATAId(),
                    parseutil.getSynopticNumber(),
                    Coordinates.convertToDecimalDegrees(parseutil.getLatitude()), 
                    Coordinates.convertToDecimalDegrees(parseutil.getLongitude()),
                    Integer.parseInt(parseutil.getElevation()),
                    parseutil.getMETARStationValue(), 
                    parseutil.getNEXRADSiteValue(), 
                    parseutil.getAviationSpecificFlagValue(), 
                    parseutil.getUpperAirOrWindProfilerSiteValue(), 
                    parseutil.getAutoValue(), 
                    parseutil.getOfficeTypeValue(),
                    Integer.parseInt(parseutil.getPlottingPriority()),
                    parseutil.getCountryCode());
        
        return sql_insert;
    }
    
    public static void finalizeConnection(Connection connection, Statement ... statement)
    {
        if(statement != null)
        {
            for(Statement _statement : statement)
            {
                try
                {
                    _statement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        if(connection != null)
        {
            try
            {
                connection.commit();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void finalizeStatement(Statement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void finalizeResultSet(ResultSet resultset)
    {
        if(resultset != null)
        {
            try
            {
                resultset.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
