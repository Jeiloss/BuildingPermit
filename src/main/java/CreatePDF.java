import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class CreatePDF {

//    static PDPageContentStream stazz; <-- using stazz should cut down on repetition.

    public static void main(String[] args) {

        Stats testout = new Stats(
                "London Tipton",
                "1589-01",
                153,
                "409 Washington Ave",
                new String[] {"repair", "reside"}
        );
        try {
            System.out.println("Creating");
            createpdf(testout);
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createpdf(Stats memo) throws Exception {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(
                        PDRectangle.A4.getHeight(),
                        PDRectangle.A4.getWidth()
        ));

        PDPageContentStream stream = new PDPageContentStream(doc,page);

        String words;
        PDFont font = PDType1Font.COURIER;
        PDFont boldFont = PDType1Font.COURIER_BOLD;
        short fontSize;
        short margin = 70;
        float pageHeight = page.getMediaBox().getHeight();
        float pageWidth = page.getMediaBox().getWidth();

        float xoffset; float yoffset;
        PDImageXObject pdImage = PDImageXObject.createFromFile(
                "src/main/resources/underline.png", doc
        );

    // Adds Signature lines.
        stream.drawImage(
                pdImage,
                (float) (pageWidth - pdImage.getWidth()*2.25 -margin),
                margin
        );
        stream.drawImage(
                pdImage,
                pageWidth - pdImage.getWidth() -margin,
                margin
        );


        stream.beginText();

    //Add Signature Titles.
        fontSize = 12;
        stream.setFont(font, fontSize);
        words = "Mayor";
        xoffset = pageWidth - margin - getWidth(words,font,fontSize);
        yoffset = margin - fontSize;
        stream.newLineAtOffset(xoffset,yoffset);//position for sig
        stream.showText(words);
        stream.newLineAtOffset(-xoffset,-yoffset);//reset position
        words="Clerk";
        xoffset = (float) (pageWidth - pdImage.getWidth()*1.25 -getWidth(words,font,fontSize) - margin);
        stream.newLineAtOffset(xoffset, yoffset);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset, -yoffset);



    // Add Corners
        fontSize=20;
        stream.setFont(font, fontSize);

        words = "$"+memo.getPrice();
        xoffset = margin;
        yoffset = pageHeight-margin;
        stream.newLineAtOffset(xoffset, yoffset);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset, 0);

        words = memo.getParcel();
        xoffset = margin + 216 - getWidth(words,font,fontSize);
        stream.newLineAtOffset(xoffset,0);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset,0);


        words = memo.getPermitNum();
        xoffset = pageWidth - margin - getWidth(words, font, fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset, -fontSize);    yoffset -= fontSize;

        words = "State of Minnesota";
        xoffset = margin;
        stream.newLineAtOffset(xoffset,0);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset,0);

        words = "City of Villard";
        xoffset = pageWidth - margin - getWidth(words,font,fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset, -fontSize);    yoffset -= fontSize;

        words = "County of Pope";
        xoffset = margin;
        stream.newLineAtOffset(xoffset,0);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset,0);

        words = "Office of Clerk";
        xoffset = pageWidth - margin - getWidth(words,font,fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(words);


        stream.newLineAtOffset(-xoffset,-yoffset);


    // Add Building Permit
        words = "Building Permit";
        short tmp = fontSize;
        fontSize = 24;
        xoffset = pageWidth/2- getWidth(words, font, fontSize)/2;
        stream.newLineAtOffset(
                xoffset, pageHeight - margin - fontSize*3
        );
        stream.setFont(font, fontSize);
        stream.showText(words);
        stream.newLineAtOffset(-xoffset+ margin, -50);

        fontSize=15;
        stream.setFont(font, fontSize);
        stream.setLeading(fontSize*1.2);
        float defaultSpacing = 0;

        String wordings =
                "In consideration of the statements and representations made by "+
                memo.getName()+" @ "+memo.getAddress()+" "+
                "in application therefor duly filed in this office, which application is "+
                "made a part hereof, permission is hereby granted to said "+memo.getName()+" "+
                "as owner to "+memo.getTypes()+" a building described as follows: [KIND OF CONSTRUCTION] "+
                "front (or width) in feet "+memo.getWidth()+"; side (or length) in feet "+memo.getLength()+"; height in feet "+memo.getHeight()+";"+
                "number of stories: "+memo.getStories()+"; "+memo.getArea()+" square feet, upon that tract of land described "+
                "as follows: "+memo.getParcel()+" which tract is of the size and area specified previously.\n"+
                "This permit is granted upon the express conditions that said owner and the "+
                "contractors, agents, workers, and employees, shall comply in all respects with "+
                "the ordinances of the City of Villard; that it does not cover the use of public "+
                "property, such as streets, sidewalks, alleys, etc; and that it does NOT cover "+
                "electrical work, plumbing, heating, plastering, etc.\n"+
                "Given under the hand of the Mayor of said City and its corporate seal and attested "+
                "by its clerk this"+"ADD DATE HERE";
        //USE Date class & Calendar clas for importing the date.

        String[] body = wordings.split("\n");

        float width = page.getMediaBox().getWidth() - 2*margin;
        for (String para: body) {
            String[] wordplay = para.split(" ");
            String line = "";
            for (String word: wordplay) {
                String test = line.concat(word);
                if (getWidth(test,font,fontSize) < width) {
                    line +=" "+ word;
                } else {
                    stream.setCharacterSpacing(
                            (width - getWidth(line,font,fontSize)) / (line.length() - 1)
                    );
                    stream.showText(line);
                    line = word;
                    stream.newLine();
                    stream.setCharacterSpacing(defaultSpacing);
                }
            }
            stream.showText(line);
            stream.newLine();
            stream.newLineAtOffset(0,-8);
        }


        stream.endText();
        stream.close();
        doc.addPage(page);
        try {
            doc.save("WorkingPermit.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param foo
     * @param font
     * @param fontSize
     * @return
     * @throws IOException
     */
    private static float getWidth(String foo, PDFont font, short fontSize) throws IOException {
        return font.getStringWidth(foo) / 1000 * fontSize;
    }
}
