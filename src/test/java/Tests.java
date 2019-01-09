import MagicNumber.MagicNumber;
import TypeAhead.InvalidSearchTermException;
import TypeAhead.TypeAhead;
import org.junit.*;

public class Tests {

    @Test
    public void testMagic() {
        String expected =
                "147\n" +
                "174\n" +
                "258\n" +
                "285\n" +
                "417\n" +
                "471\n" +
                "528\n" +
                "582\n" +
                "714\n" +
                "741\n" +
                "825\n" +
                "852";
        String actual = MagicNumber.run("100 1000");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMagic_NoMagic() {
        String expected = "";
        String actual = MagicNumber.run("20 30");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTypeAhead() {
        String expected = "lamb,0.375;teacher,0.250;children,0.125;eager,0.125;rule,0.125";
        String actual = TypeAhead.run("2, the");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTypeAhead_Trigram() {
        String expected = "children laugh,0.125;eager children,0.125;lamb love,0.125;lamb was,0.125;lamb you,0.125;rule it,0.125;teacher did,0.125;teacher turned,0.125";
        String actual = TypeAhead.run("3, the");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidSearchTermException.class)
    public void testTypeAhead_NoGram() {
        TypeAhead.run("2, invalid");
    }

}
