package com.ffg.rrn.utils;

import net.sf.jasperreports.engine.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jasperreports.engine.JasperExportManager.exportReportToHtmlFile;

public class JasperReportsUtils {

    public static void exportSampleReport() {

        String SQL_GETALLPROPERTIES = "select prop_name, city, state, county," +
                " unit, unit_fee, total_residents, resident_council, service_provider, active from property " +
                "order by prop_name asc";

        try {
            Class.forName("org.postgresql.Driver");
            Connection cnx = DriverManager
                    .getConnection("jdbc:postgresql://axon1:5432/rrhope",
                            "admin", "stonewall");
            PreparedStatement ps = cnx.prepareStatement(SQL_GETALLPROPERTIES);
            ResultSet rs = ps.executeQuery();
            JRDataSource dataSource = new JRResultSetDataSource(rs);
            assert(dataSource != null);

            Map parameters = new HashMap();
            String sourceFileName = "c:\\tmp\\RRN_Hope_sample2.jasper";
            String fillFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, dataSource);
            assert(fillFileName!=null);
            System.out.println("fillFileName:"+fillFileName);
            String pdfFileName = JasperExportManager.exportReportToPdfFile(fillFileName);
            assert(pdfFileName!=null);
            System.out.println("PDF File:"+pdfFileName);
        }
        catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("Testing export sample report...");
        exportSampleReport();
    }
}
