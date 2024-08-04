package org.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        String jsonFilePath = "questions.json";
        String pdfFilePath = "questions.pdf";

        try {
            // Read JSON file
            String jsonContent = readFile(jsonFilePath);
            JSONArray questionsArray = new JSONArray(jsonContent);

            // Create PDF
            createPdf(questionsArray, pdfFilePath);

            System.out.println("PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }

    private static void createPdf(JSONArray questionsArray, String pdfFilePath) throws IOException {
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionObj = questionsArray.getJSONObject(i);
            String question = questionObj.getString("question");
            document.add(new Paragraph((i + 1) + ". " + question));
        }

        document.close();
    }
}