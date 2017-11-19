
package testlucasdarwish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestLucasDarwish {
    
    public static void main(String[] args) throws SQLException  {
        
        String url = "jdbc:mysql://127.0.0.1:3308/testback";
        String sql = "insert into tb_customer_account values(?,?,?,?,?)";
        String[] ids={"1478","1763","2348","6855"};
        String[] cpf = {"95627843791","21597889625","35846216057","15788305967"};
        String[] nmc = {"Marcos Junior","Paulo caio","Juan Mendes","Pedro Vargas"};
        String[] act = {"1","1","0","1"};
        String[] vlr = {"430","610","1310","210"};
        try(
            Connection con = DriverManager.getConnection(url,"root","")){
            try(PreparedStatement stm = con.prepareStatement(sql)){
                for(int i =0; i<ids.length; i++){
                    stm.setString(1, ids[i]);
                    stm.setString(2, cpf[i]);
                    stm.setString(3, nmc[i]);
                    stm.setString(4, act[i]);
                    stm.setString(5, vlr[i]);
                    stm.addBatch();
                }stm.executeBatch();
            }catch(SQLException e){}
                sql = "select \n" +
                        "nm_customer Nome_Cliente, \n" +
                        "vl_total Valor_Total,\n" +
                        "(select AVG(vl_total) FROM tb_customer_account\n" +
                        "WHERE vl_total >560 and id_customer BETWEEN 1500 AND 2700 ORDER BY vl_total ASC) AS Media_Valor_Total FROM tb_customer_account\n" +
                      "WHERE vl_total >560 and id_customer BETWEEN 1500 AND 2700 ORDER BY vl_total ASC";
                
                try(PreparedStatement stm2 = con.prepareStatement(sql);
                    ResultSet rs = stm2.executeQuery()){
                    while(rs.next()){
                        System.out.println(rs.getString(1)+ " " +rs.getString(2)+" "+rs.getString(3));
                        }
                }
            }
        }
    }
    

