import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

public class CreatePDF {

//    static PDPageContentStream stazz; <-- using stazz should cut down on repetition.

//    public static void main(String[] args) {
    public static void main(Stats info) {

//        Stats testout = new Stats(
//                "London Tipton",
//                "1589-01",
//                "2018-51",
//                "409 Washington Ave",
//                new String[] {"repair", "reside"}
//        );
//        Stats testout = new Stats(
//                name,
//                parcelNum,
//                permitNum,
//                address,
//                null
//        );
        try {
            System.out.println("Creating");
            createpdf(info);
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

        String workingSpace;
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
        workingSpace = "Mayor";
        xoffset = pageWidth - margin - getWidth(workingSpace,font,fontSize);
        yoffset = margin - fontSize;
        stream.newLineAtOffset(xoffset,yoffset);//position for sig
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset,-yoffset);//reset position
        workingSpace="Clerk";
        xoffset = (float) (pageWidth - pdImage.getWidth()*1.25 -getWidth(workingSpace,font,fontSize) - margin);
        stream.newLineAtOffset(xoffset, yoffset);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset, -yoffset);



    // Add Corners
        fontSize=20;
        stream.setFont(font, fontSize);

        workingSpace = "$"+memo.getPrice();
        xoffset = margin;
        yoffset = pageHeight-margin;
        stream.newLineAtOffset(xoffset, yoffset);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset, 0);

        workingSpace = memo.getParcel();
        xoffset = margin + 216 - getWidth(workingSpace,font,fontSize);
        stream.newLineAtOffset(xoffset,0);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset,0);


        workingSpace = memo.getPermitNum();
        xoffset = pageWidth - margin - getWidth(workingSpace, font, fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset, -fontSize);    yoffset -= fontSize;

        workingSpace = "State of Minnesota";
        xoffset = margin;
        stream.newLineAtOffset(xoffset,0);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset,0);

        workingSpace = "City of Villard";
        xoffset = pageWidth - margin - getWidth(workingSpace,font,fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset, -fontSize);    yoffset -= fontSize;

        workingSpace = "County of Pope";
        xoffset = margin;
        stream.newLineAtOffset(xoffset,0);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset,0);

        workingSpace = "Office of Clerk";
        xoffset = pageWidth - margin - getWidth(workingSpace,font,fontSize);
        stream.newLineAtOffset(xoffset, 0);
        stream.showText(workingSpace);


        stream.newLineAtOffset(-xoffset,-yoffset);


    // Add Building Permit
        workingSpace = "Building Permit";
        fontSize = 24;
        xoffset = pageWidth/2- getWidth(workingSpace, font, fontSize)/2;
        stream.newLineAtOffset(
                xoffset, pageHeight - margin - fontSize*3
        );
        stream.setFont(font, fontSize);
        stream.showText(workingSpace);
        stream.newLineAtOffset(-xoffset+ margin, -50);

        fontSize=15;
        stream.setFont(font, fontSize);
        stream.setLeading(fontSize*1.2);
        float defaultSpacing = 0;// ???
        float width = page.getMediaBox().getWidth() - 2*margin;
        LinkedList<String> words = new LinkedList<String>();

        workingSpace =
                "In consideration of the statements and representations made by "+
                "in application therefor duly filed in this office, which application is "+
                "made a part hereof, permission is hereby granted to said "+memo.getName()+" "+
                "as owner to "+memo.getTypes()+" a building described as follows: [KIND OF CONSTRUCTION] "+
                "front (or width) in feet "+memo.getWidth()+"; side (or length) in feet "+memo.getLength()+
                "; height in feet "+memo.getHeight()+"; number of stories: "+memo.getStories()+"; "+
                memo.getArea()+" square feet, upon that tract of land described previously.\n"+
                "This permit is granted upon the express conditions that said owner and the "+
                "contractors, agents, workers, and employees, shall comply in all respects with "+
                "the ordinances of the City of Villard; that it does not cover the use of public "+
                "property, such as streets, sidewalks, alleys, etc; and that it does NOT cover "+
                "electrical work, plumbing, heating, plastering, etc.\n"+
                "Given under the hand of the Mayor of said City and its corporate seal and attested "+
                "by its clerk this "+getDate();
        String[] body = workingSpace.split("\n");
        String[] wordings = body[0].split(" ");

        words.addAll(Arrays.asList(wordings));

        String line = "";
        for (int i = 0 ; i < 9 ; i++) {
            line += words.remove()+" ";
        }
        if (getWidth(line+memo.getName(),font,fontSize) < width) {
//        if (false) {
            stream.setCharacterSpacing(
                    (width - getWidth(line+memo.getName(),font,fontSize)) /
                            (line.length()+memo.getName().length() - 1)
            );
            stream.showText(line);
            stream.setFont(boldFont,fontSize);
            stream.showText(memo.getName());
            stream.setFont(font, fontSize);
            stream.newLine();

            line = "";
            while (getWidth("@ "+line+words.element()+memo.getAddress(),font,fontSize) < width) {
                line += words.remove()+" ";
            }
            stream.setCharacterSpacing(
                    (width - getWidth("@ "+line+memo.getAddress(),font,fontSize)) /
                            ("@ ".length()+line.length()+memo.getAddress().length() - 1)
            );

            stream.showText("@ ");
            stream.setFont(boldFont,fontSize);
            stream.showText(memo.getAddress());
            stream.setFont(font,fontSize);
            stream.showText(" "+line);
        } else {
            stream.setCharacterSpacing(
                    (width - getWidth(line,font,fontSize)+getWidth(" ",font,fontSize)) /
                            (line.length() - 1)
            );
            stream.showText(line);
            stream.newLine();

            line = "";
            while (getWidth(memo.getName()+words.peek()+" @ "+memo.getAddress()+line,font,fontSize) < width) {
                line += words.remove()+ " ";
            }
            stream.setCharacterSpacing(
                    (width - getWidth(memo.getName()+" @ "+memo.getAddress()+line,font,fontSize)) /
                            (memo.getName().length()+" @ ".length()+memo.getAddress().length()+line.length() - 1)
            );
            stream.setFont(boldFont, fontSize);
            stream.showText(memo.getName());
            stream.setFont(font, fontSize);
            stream.showText(" @ ");
            stream.setFont(boldFont, fontSize);
            stream.showText(memo.getAddress()+" ");
            stream.setFont(font, fontSize);
            stream.showText(line);
        }
        stream.newLine();

        body[0] = "";
        for (String x: words) {
            body[0] += x+" ";
        }
//        System.out.println(body[0]);

//        String[] body = wordings.split("\n");
//
//
//
//
//

        for (String para: body) {
            String[] wordplay = para.split(" ");
            line = "";
            for (String word: wordplay) {
                String test = line.concat(word);
                if (getWidth(test,font,fontSize) < width) {
                    line +=word+" ";
                } else {
                    stream.setCharacterSpacing(
                            (width+getWidth(" ",font,fontSize) - getWidth(line,font,fontSize)) / (line.length() - 1)
                    );
                    stream.showText(line);
                    line = word+" ";
                    stream.newLine();
                    stream.setCharacterSpacing(defaultSpacing);
                }
            }
            stream.showText(line);
            stream.newLine();
//            stream.newLineAtOffset(0,-8);
        }


        stream.endText();
        stream.close();
        doc.addPage(page);
        try {
            doc.save("WorkingPermit.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();
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

    /** returns DD month YYYY format according to specs
     *
     * @return date
     */
    private static String getDate() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String rmonth = "";
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String rday = String.valueOf(day);

        switch (day) {
            case 1:
            case 21:
            case 31:
                rday += "st";
                break;
            case 2:
            case 22:
                rday += "nd";
                break;
            case 3:
            case 23:
                rday += "rd";
                break;
            default:
                rday += "th";
        }
        switch (month) {
            case 0:
                rmonth = "January";
                break;
            case 1:
                rmonth = "February";
                break;
            case 2:
                rmonth = "March";
                break;
            case 3:
                rmonth = "April";
                break;
            case 4:
                rmonth = "May";
                break;
            case 5:
                rmonth = "June";
                break;
            case 6:
                rmonth = "July";
                break;
            case 7:
                rmonth = "August";
                break;
            case 8:
                rmonth = "September";
                break;
            case 9:
                rmonth = "October";
                break;
            case 10:
                rmonth = "November";
                break;
            case 11:
                rmonth = "December";
                break;
        }

        return rday+" day of "+rmonth+" Year "+year;
    }
}
