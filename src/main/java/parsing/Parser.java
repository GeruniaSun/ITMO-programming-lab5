package parsing;

import data.Ticket;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.Collection;
import java.util.List;

public class Parser {
    public static void saveToFile(File file, Collection<Ticket> data) throws IOException{
        XmlMapper mpr = new XmlMapper();
        mpr.enable(SerializationFeature.INDENT_OUTPUT);
        var out = new BufferedWriter(new FileWriter(file));
        mpr.writeValue(out, data);
    }

    public static List<Ticket> getFromFile(File file) throws IOException{
        XmlMapper mpr = new XmlMapper();
        mpr.enable(SerializationFeature.INDENT_OUTPUT);
        var in = new BufferedReader(new FileReader(file));
        return List.of(mpr.readValue(in, Ticket[].class));
    }
}