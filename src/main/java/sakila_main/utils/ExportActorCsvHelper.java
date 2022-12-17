package sakila_main.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


import org.springframework.web.multipart.MultipartFile;
import sakila_main.model.ActorModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class ExportActorCsvHelper {

   /* public static String TYPE ="text/csv";
    String[] HEADERS = {
            "* actorId",
                "* firstName",
                "* lastName",
                "* lastUpdate"
    };

    public static boolean hasCSVFormat(MultipartFile file) {
        if(TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")){
            return true;
        }
        return false;
    }*/

    public static ByteArrayInputStream exportHelperToCsv(List<ActorModel> actorModelList) {
        final CSVFormat format = CSVFormat.RFC4180.withHeader(
                "* actorId",
                "* firstName",
                "* lastName",
                "* lastUpdate"
                );
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter =new CSVPrinter(new PrintWriter(out), format);) {
            for (ActorModel exportActorModel: actorModelList) {
                List<? extends Serializable> data = Arrays.asList(
                        exportActorModel.getActorId(),
                        exportActorModel.getFirstName(),
                        exportActorModel.getLastName(),
                        exportActorModel.getLastUpdate());
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to export data to CSV file: " + e.getMessage());
        }
    }
}
