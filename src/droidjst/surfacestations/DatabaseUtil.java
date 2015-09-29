
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
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil
{
    public void parseFileToSQLite() throws IOException, SQLException
    {
        String uri = new File("").getAbsolutePath() + Const.FILE_SEP + "stations";
        
        File file = new File(uri);
        
        Connection connection = null;
        Statement statement = null;
        
        Database database = new Database();
        
        Object[] tuple = database.getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        BufferedReader breader = null;
        
        ParseUtil parseutil = new ParseUtil();
        Coordinates coords = new Coordinates();
        
        try
        {
            breader = new BufferedReader(new FileReader(file), 16 * 1024);
            
            String format = 
                "INSERT INTO stations (cd, station, icao, iata, synop, lat, lon, elev, m, n, v, u, a, c, p, cc)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', %f, %f, %d, '%s', '%s', '%s', '%s', '%s', '%s', %d, '%s')";
            
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
                
                String sql_insert = 
                    String.format(format, 
                            parseutil.getStateAbbrv(),
                            parseutil.getStationName().replace("'", "''"),
                            parseutil.getICAOId(),
                            parseutil.getIATAId(),
                            parseutil.getSynopticNumber(),
                            coords.convertToDecimalDegrees(parseutil.getLatitude()), 
                            coords.convertToDecimalDegrees(parseutil.getLongitude()),
                            Integer.parseInt(parseutil.getElevation()),
                            parseutil.getMETARStationValue(), 
                            parseutil.getNEXRADSiteValue(), 
                            parseutil.getAviationSpecificFlagValue(), 
                            parseutil.getUpperAirOrWindProfilerSiteValue(), 
                            parseutil.getAutoValue(), 
                            parseutil.getOfficeTypeValue(),
                            Integer.parseInt(parseutil.getPlottingPriority()),
                            parseutil.getCountryCode());
                
                statement = connection.createStatement();
                
                statement.executeUpdate(sql_insert);
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
            try
            {
                breader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            database.finalizeConnection(connection, statement);
        }
    }
}
