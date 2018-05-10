import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class CreatePDF {
    public static void main(String[] args) {

        Stats testout = new Stats(
                "London Tipton",
                "1589-01",
                153,
                "409 Washington Ave",
                new String[]{"havana", "lying"}
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
        words = "City of Villard";
        PDFont font = PDType1Font.COURIER;
        short size = 20;
        stream.beginText();
        stream.setFont(font, size);
        short margin = 80;


        // Change PDRectangle.A4 occurances to page.getMediaBox();
        float indentDist = page.getMediaBox().getWidth()/2-getsize(words, font, size)/2;
        stream.newLineAtOffset(
                indentDist,
                page.getMediaBox().getHeight()-100
        );
        stream.showText(words);
        stream.newLineAtOffset(-indentDist, -22);

        words = "Building Permit";
        indentDist = PDRectangle.A4.getHeight()/2-getsize(words, font, size)/2;
        stream.newLineAtOffset(
                indentDist, 0
        );
        stream.showText(words);
        stream.newLineAtOffset(-indentDist+ margin, -60);

        String wordings = "In consideration of the statements and representations made by "+
                memo.name+" @ "+memo.address+" "+
                "in application therefor duly filed in this office, which application is "+
                "made a part hereof, permission is hereby granted to said "+memo.name+" "+
                "as owner to "+memo.types+" a building described as follows: kind of construction "+
                "front (or width) in feet ###; side (or length) in feet ###; height in feet ###;"+
                "number of stories: #; #### square feet, upon that tract of land described "+
                "as follows: "+memo.parcel+" which tract is of the size and area specified previously.\n"+
                "This permit is granted upon the express conditions that said owner and the "+
                "contractors, agents, workers, and employees, shall comply in all respects with "+
                "the ordinances of the City of Villard; that it does not cover the use of public "+
                "property, such as streets, sidewalks, alleys, etc; and that it does not cover"+
                "electrical work, plumbing, heating, plastering, etc.\n"+
                "Given under the hand of the Mayor of said City and its corporate seal and attested "+
                "by its clerk this"+"ADD DATE HERE";
//        stream.showText("This is where the Permit's body starts");

//        stream.setLeading(20);
        size=15;
        stream.setFont(font, size);
        stream.setLeading(size*1.2);
        String[] body = wordings.split("\n");
        float defaultSpacing = 0;

        float width = page.getMediaBox().getWidth() - 2*margin;
        for (String para: body) {
            String[] wordplay = para.split(" ");
            String line = "";
            for (String word: wordplay) {
                String test = line.concat(word);
                if (getsize(test,font,size) < width) {
                    line +=" "+ word;//line.concat(word); not working.
//                    System.out.println(line);
                } else {
                    stream.setCharacterSpacing(
                            (width - getsize(line,font,size)) / (line.length() - 1)
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

    private static float getsize(String foo, PDFont font, short fontSize) throws Exception {
        return font.getStringWidth(foo) / 1000 * fontSize;
    }
}
