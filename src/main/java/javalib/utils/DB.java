/*
 * DB.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */
package javalib.utils;


import java.sql.*;
import static javalib.utils.Constants.*;

/**
 *
 * @author Leo Chen
 */
public class DB {
    //debug de = new debug(false);
    
    private String driverStr;
    private String connectStr;
    private String account;
    private String password;
    
    private Connection con;
    //private PreparedStatement psmt = null;
    private Statement smt;
    private ResultSet rs = null;
    
    /** Creates a new instance of DB */
    public DB() {
    }
    
    public void setDriverString(String driverStr) {
        this.driverStr = driverStr;
    }
    
    public void setConnectString(String connectStr) {
        this.connectStr = connectStr;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Connection getConnection(){return con;}

    
    public void connect() throws Exception {
        //try {
            Class.forName(driverStr);
            // sqlite support
            if ( dbDriverSQLite.equals(driverStr) ){
                con=DriverManager.getConnection(connectStr);
                smt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            }else{
                con=DriverManager.getConnection(connectStr,account,password);
                smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }
            

        //} catch( Exception ex ) {
        //    de.println( "error in DB.connect() : "+ex.getMessage() );
        //}
    }

    public void disconnect() throws Exception {
        //try {
            
            if (null != rs){
                rs.close();
            }
            smt.close();
            con.close();
            
        //} catch( Exception ex ) {
        //    de.println( "error in DB.disconnect() : "+ex.getMessage() );
        //}
    }

    public ResultSet exeSelectSQL(String sql)throws Exception {
        //try{
        //System.out.println("con.getAutoCommit() ROOT 11 : " + con.getAutoCommit());
        rs=smt.executeQuery(sql);
        //System.out.println("con.getAutoCommit() ROOT 12 : " + con.getAutoCommit());
        //}catch( Exception sqlex ){
        //    de.println("SQL Statement : "+sql);
        //    de.println( "error in DB.exeSelectSQL() : "+sqlex.getMessage() );
        //}
        
        return rs;
    }
    
    public int exeModifySQL(String sql)throws Exception {
        //try{
        int result=smt.executeUpdate(sql);
        //}catch( SQLException sqlex ){
        //    de.println("SQL Statement : "+sql);
        //    de.println( "error in DB.exeModifySQL() : "+sqlex.getMessage() );
        //}
        
        return result;
    }
    
    public int exeModifySQL(String sql, boolean autoCommit)throws Exception{
        //System.out.println("con.getAutoCommit() ROOT 21 : " + con.getAutoCommit());
        con.setAutoCommit(autoCommit);
        int result=smt.executeUpdate(sql);
        //System.out.println("con.getAutoCommit() ROOT 22 : " + con.getAutoCommit());
        return result;
    }
    
}
