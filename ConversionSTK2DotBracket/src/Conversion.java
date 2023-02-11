import java.io.IOException;

public class Conversion
{
    public static void main(String[] args) throws IOException, STKFileException {
        String file="C:\\Users\\USER\\Desktop\\supervisors_wild&Jess\\Eddy\\conus-1.0.tar\\conus-1.0\\data\\benchmark.stk";//stk file path
        Converter convert = new Converter(file);
    }
}
