
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

public class ParseUtil
{
    private String cd;
    private String station;
    private String icao;
    private String iata;
    private String synop;
    private String lat;
    private String lon;
    private String elev;
    private String m, n, v, u, a, c;
    private String p;
    private String cc;
    
    public String getStateAbbrv()
    {
        return cd;
    }
    
    public String getStationName()
    {
        return station;
    }
    
    public String getICAOId()
    {
        return icao;
    }
    
    public String getIATAId()
    {
        return iata;
    }
    
    public String getSynopticNumber()
    {
        return synop;
    }
    
    public String getLatitude()
    {
        return lat;
    }
    
    public String getLongitude()
    {
        return lon;
    }
    
    public String getElevation()
    {
        return elev;
    }
    
    public String getMETARStationValue()
    {
        return m;
    }
    
    public String getNEXRADSiteValue()
    {
        return n;
    }
    
    public String getAviationSpecificFlagValue()
    {
        return v;
    }
    
    public String getUpperAirOrWindProfilerSiteValue()
    {
        return u;
    }
    
    public String getAutoValue()
    {
        return a;
    }
    
    public String getOfficeTypeValue()
    {
        return c;
    }
    
    public String getPlottingPriority()
    {
        return p;
    }
    
    public String getCountryCode()
    {
        return cc;
    }
    
    public void parseLine(String line, boolean trim_whitespace)
    {
        cd = line.substring(0, 2);
        
        station = line.substring(3, 19);
        
        icao = line.substring(20, 24);
        
        iata = line.substring(26, 29);
        
        synop = line.substring(32, 37);
        
        lat = line.substring(39, 45);
        lon = line.substring(47, 54);
        
        elev = line.substring(55, 59);
        
        m = line.substring(62, 63);
        n = line.substring(64, 66);
        v = line.substring(68, 69);
        u = line.substring(71, 72);
        a = line.substring(74, 75);
        c = line.substring(77, 78);
        
        p = line.substring(79, 80);
        
        cc = line.substring(81, 83);
        
        if(trim_whitespace)
        {
            cd = cd.trim();
            
            station = station.trim();
            
            icao = icao.trim();
            iata = iata.trim();
            
            synop = synop.trim();
            
            lat = lat.trim();
            lon = lon.trim();
            
            elev = elev.trim();
            
            m = m.trim();
            n = n.trim();
            v = v.trim();
            u = u.trim();
            a = a.trim();
            c = c.trim();
            
            p = p.trim();
            
            cc = cc.trim();
        }
    }
}
