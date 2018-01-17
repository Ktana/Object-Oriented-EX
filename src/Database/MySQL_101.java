package Database;

/** 
 * This is a very simple example representing how to work with MySQL 
 * using java JDBC interface;
 * The example mainly present how to read a table representing a set of WiFi_Scans
 * Note: for simplicity only two properties are stored (in the DB) for each AP:
 * the MAC address (mac) and the signal strength (rssi), the other properties (ssid and channel)
 * are omitted as the algorithms do not use the additional data.
 * 
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Global.CSV_Merged_Row;

//import Tools.Point3D;
//import WiFi_data.WiFi_AP;
//import WiFi_data.WiFi_Scan;
//import WiFi_data.WiFi_Scans;

import java.sql.Statement;

public class MySQL_101 {

	  private  String _ip; //= "5.29.193.52";
	  private  String _url; //= "jdbc:mysql://"+_ip+":3306/oop_course_ariel";
	  private  String _user; //= "oop1";
	  private String _port; //= "3306";
	  private  String _password; //= "Lambda1();";
	  private  Connection _con; //= null;
	  private  String _table; //= "test101";
	  
	  public MySQL_101(String ip, String user, String password, String port, String tableName){
		  this._ip = ip;
		  this._user = user;
		  this._password = password;
		  this._port = port;
		  this._table = tableName;
		  _url = "jdbc:mysql://"+_ip+":"+_port+"/oop_course_ariel";
	  }
      
    public  int test_101(List<CSV_Merged_Row> rowMergeList) {
    	if(_ip.isEmpty() || _user.isEmpty() || _password.isEmpty() || _table.isEmpty()){
    		return -1;
    	}
        Statement st = null;
        ResultSet rs = null;
        int max_id = -1;

        try {     
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();
//            rs = st.executeQuery("SELECT UPDATE_TIME FROM ");
//            if (rs.next()) {
//                System.out.println(rs.getString(1));
//            }
           
//            PreparedStatement pst = _con.prepareStatement("SELECT * FROM "+ _table);
            PreparedStatement pst = _con.prepareStatement("SELECT IFNULL(id,0),"
                    + " IFNULL(time,'') as time, "
                    + " IFNULL(device,'') as device,"
                    + " IFNULL(lat,0) as lat,"
                    + " IFNULL(lon,0) as lon,"
                    + " IFNULL(alt,0) as alt,"
                    + " IFNULL(number_of_ap,0) as number_of_ap,"
                    + " IFNULL(mac0,'') as mac0,"
                    + " IFNULL(rssi0,0) as rssi0,"
                    + " IFNULL(mac1,'') as mac1,"
                    + " IFNULL(rssi1,0) as rssi1,"
                    + " IFNULL(mac2,'') as mac2,"
                    + " IFNULL(rssi2,0) as rssi2,"
                    + " IFNULL(mac3,'') as mac3,"
                    + " IFNULL(rssi3,0) as rssi3,"
                    + " IFNULL(mac4,'') as mac4,"
                    + " IFNULL(rssi4,0) as rssi4,"
                    + " IFNULL(mac5,'') as mac5,"
                    + " IFNULL(rssi5,0) as rssi5,"
                    + " IFNULL(mac6,'') as mac6,"
                    + " IFNULL(rssi6,0) as rssi6,"
                    + " IFNULL(mac7,'') as mac7,"
                     + " IFNULL(rssi7,0) as rssi7,"
                    + " IFNULL(mac8,'') as mac8,"
                    + " IFNULL(rssi8,0) as rssi8,"
                    + " IFNULL(mac9,'') as mac9,"
                    + " IFNULL(rssi9,0) as rssi9"
                    + " FROM "+ _table);


            rs = pst.executeQuery();
            
            while (rs.next()) {
                String prefix = rs.getString("time")+","+rs.getString("device")+","+rs.getString("lon")+","+rs.getString("lat")+","+rs.getString("alt");
                String suffix = rs.getString("rssi0")+","+rs.getString("mac0")+",0,0,"+rs.getString("rssi1")+","+rs.getString("mac1")+",0,0,"+rs.getString("rssi2")+","+rs.getString("mac2")+",0,0,"+
                				rs.getString("rssi3")+","+rs.getString("mac3")+",0,0,"+rs.getString("rssi4")+","+rs.getString("mac4")+",0,0,"+rs.getString("rssi5")+","+rs.getString("mac5")+",0,0,"+
                				rs.getString("rssi6")+","+rs.getString("mac6")+",0,0,"+rs.getString("rssi7")+","+rs.getString("mac7")+",0,0,"+rs.getString("rssi8")+","+rs.getString("mac8")+",0,0,"+
                				rs.getString("rssi9")+","+rs.getString("mac9")+",0,0";
                CSV_Merged_Row mergedRow = new CSV_Merged_Row(prefix, suffix);
                rowMergeList.add(mergedRow);
            }
            
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MySQL_101.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {
                
                Logger lgr = Logger.getLogger(MySQL_101.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return max_id;
    }
    
    public  boolean isConnected() {
    	if(_ip.isEmpty() || _user.isEmpty() || _password.isEmpty() || _table.isEmpty()){
    		return false;
    	}
        Statement st = null;
        ResultSet rs = null;

        try {     
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();
//            rs = st.executeQuery("SELECT UPDATE_TIME FROM ");
            rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = 'ex4_db'");
            if (rs.next()) {
            	System.out.println(rs.getString(1));
                return true;
            }
            
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MySQL_101.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {
                
                Logger lgr = Logger.getLogger(MySQL_101.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }
    
    
}