import CSV.Combo.ComboLine;
import CSV.Combo.ComboLines;
import CSV.Data.AP_WifiData;
import CSV.Data.GeoPoint;
import javafx.geometry.Point3D;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Boaz_MySQL {

    private static String _ip = "5.29.193.52";
    private static String _url = "jdbc:mysql://"+_ip+":3306/oop_course_ariel";
    private static String _user = "oop1";
    private static String _password = "Lambda1();";
    private static Connection _con = null;

    public static void main(String[] args) {
        int max_id = test_ex4_db();
        //  	insert_table1(max_id);
    }
    public static int test_101() {
        Statement st = null;
        ResultSet rs = null;
        int max_id = -1;
        //String ip = "localhost";
        // String ip = "192.168.1.18";

        try {
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();
            rs = st.executeQuery("SELECT UPDATE_TIME FROM ");
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

            PreparedStatement pst = _con.prepareStatement("SELECT * FROM test101");
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                if(id>max_id) {max_id=id;}
                System.out.print(id);
                System.out.print(": ");
                System.out.print(rs.getString(2));
                System.out.print(" (");
                double lat = rs.getDouble(3);
                System.out.print(lat);
                System.out.print(", ");
                double lon = rs.getDouble(4);
                System.out.print(lon);
                System.out.println(") ");
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return max_id;
    }

    public static int test_ex4_db() {
        Statement st = null;
        ResultSet rs = null;
        int max_id = -1;

        try {
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();
            rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = 'ex4_db'");
            if (rs.next()) {
                System.out.println("**** Update: "+rs.getString(1));
            }

            PreparedStatement pst = _con.prepareStatement("SELECT * FROM ex4_db");
            rs = pst.executeQuery();
            int ind=0;
            while (rs.next()) {
                int size = rs.getInt(7);
                int len = 7+2*size;
                if(ind%100==0) {
                    for(int i=1;i<=len;i++){
                        System.out.print(ind+") "+rs.getString(i)+",");
                    }
                    System.out.println();
                }
                ind++;
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return max_id;
    }

    public static void insert_table(int max_id) {
        Statement st = null;
        ResultSet rs = null;
        //String ip = "localhost";
        // String ip = "192.168.1.18";

        try {
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();
            Date now = null;
            for(int i=0;i<5;i++) {
                int curr_id = 1+i+max_id;
                String str = "INSERT INTO test101 (ID,NAME,pos_lat,pos_lon, time, ap1, ap2, ap3) "
                        + "VALUES ("+curr_id+",'test_name"+curr_id+"',"+(32+curr_id)+",35.01,"+now+",'mac1"+curr_id+"', 'mac2', 'mac3')";
                PreparedStatement pst = _con.prepareStatement(str);
                pst.execute();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    public static void insert_table2(int max_id, ComboLines comboLines) {
        Statement st = null;
        ResultSet rs = null;

        try {
            _con = DriverManager.getConnection(_url, _user, _password);
            st = _con.createStatement();

            int size = comboLines.size();
            for(int i=0;i<size;i++) {
                int curr_id = 1+i+max_id;
                ComboLine c = comboLines.get(i);
                String sql = creat_sql(c, curr_id);
                PreparedStatement pst = _con.prepareStatement(sql);
                System.out.println(sql);
                pst.execute();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (st != null) { st.close(); }
                if (_con != null) { _con.close();  }
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(Boaz_MySQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    private static String creat_sql(ComboLine w, int id) {
        String ans = "INSERT INTO ex4_db (ID,time, device,lat,lon,alt, number_of_ap";
        String str1 = "", str2="";
        GeoPoint pos = w.getLocation();
        int n = w.size();
        String in = " VALUES ("+id+",'"+w.getFirstSeen()+"','"+w.getId()+"',"+w.getLocation().toString()+","+n;
        for(int i=0;i<n;i++) {
            str1+=",mac"+i+",rssi"+i;
            AP_WifiData a = w.get(i);
            str2+=",'"+a.mac+"',"+(int)a.wifiSpectrum.rssi;
        }
        ans +=str1+")"+in+str2+")";
        return ans;
    }
}