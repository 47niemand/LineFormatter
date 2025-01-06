package pp.muza.formatter;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ColorCanvasTest {

    static List<AnsiFormat> colors;

    static {
        colors = new ArrayList<>();
        colors.add(new AnsiFormat(Attribute.BRIGHT_RED_TEXT(), Attribute.BLACK_BACK(), Attribute.UNDERLINE()));
        colors.add(new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BLACK_BACK(), Attribute.BOLD()));
        colors.add(new AnsiFormat(Attribute.BLUE_TEXT(), Attribute.YELLOW_BACK(), Attribute.ITALIC()));
        colors.add(new AnsiFormat(Attribute.CYAN_TEXT(), Attribute.MAGENTA_BACK(), Attribute.DIM()));
        colors.add(new AnsiFormat(Attribute.BRIGHT_BLUE_TEXT(), Attribute.BRIGHT_YELLOW_BACK(), Attribute.REVERSE()));
    }

    @Test
    void test() {
        AsciiCanvas canvas = new AsciiCanvas(30, 10);
        canvas.setColorMode(true);

        for (int i = 0; i < 10; i++) {
            canvas.setColor(colors.get(i % colors.size()));
            canvas.drawText(0, i, i + ".Hello, world!");
        }

        System.out.println(canvas.toString());
    }

}
