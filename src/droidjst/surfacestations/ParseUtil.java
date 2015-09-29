
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
    /*
    
    !
    ! ...
    !
    ! This file is organized by state alphabetically, then by Canadian
    ! province and then all other stations.  The file was compiled from lists
    ! of the following:
    !      METAR sites
    !      NEXRADs
    !      rawinsonde sites
    !      wind profilers
    !      WFOs, RFCs, NCEP-Centers
    !      AIRMET/SIGMET station list (VORs?)
    !      ARTCCs (Air Route Traffic Control Centers - FAA)
    !      old SAO sites for archive data access  (flagged in a special manner)
    !
    !   Country abbreviations from ISO 3166
    !   source:  ftp://ftp.fu-berlin.de/doc/iso/iso3166-countrycodes.txt
    !   another: http://www.iso.org/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html
    !
    !   CD = 2 letter state (province) abbreviation
    !   STATION = 16 character station long name
    !   ICAO = 4-character international id
    !   IATA = 3-character (FAA) id
    !   SYNOP = 5-digit international synoptic number
    !   LAT = Latitude (degrees minutes)
    !   LON = Longitude (degree minutes)
    !   ELEV = Station elevation (meters)
    !   M = METAR reporting station.   Also Z=obsolete? site
    !   N = NEXRAD (WSR-88D) Radar site
    !   V = Aviation-specific flag (V=AIRMET/SIGMET end point, A=ARTCC T=TAF U=T+V)
    !   U = Upper air (rawinsonde=X) or Wind Profiler (W) site
    !   A = Auto (A=ASOS, W=AWOS, M=Meso, H=Human, G=Augmented) (H/G not yet impl.)
    !   C = Office type F=WFO/R=RFC/C=NCEP Center
    !   Digit that follows is a priority for plotting (0=highest)
    !   Country code (2-char) is last column
    !
    
    */
    
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
