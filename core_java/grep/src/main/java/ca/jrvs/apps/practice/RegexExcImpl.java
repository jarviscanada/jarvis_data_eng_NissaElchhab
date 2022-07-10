package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImpl implements RegexExc{
    private final String JpegStr;
    private final String IpStr;
    private final String EmptyLineStr;
    {
        JpegStr = "[\\w|\\d]+\\.jpe?g$"; // NOTE: file extension implies valid file name, this [[alphanum]]. before jpg
        IpStr = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; // NOTE: this doesn't consider word boundaries for now
        EmptyLineStr = "^\\s*$";
    }

    /**
     * return true if file extension is `jpeg` or `jpg` (case-insensitive)
     *
     * @param filename
     * @return
     */
    @Override
    public boolean matchJpeg(String filename) {
        final Pattern p = Pattern.compile(JpegStr, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(filename);
        return m.matches();
    }

    /**
     * return true if ip is valid
     * to simplify the problem, an ip address ranges from 0.0.0.0 to 999.999.999.999
     *
     * @param ip
     * @return
     */
    @Override
    public boolean matchIp(String ip) {
        final Pattern p = Pattern.compile(IpStr);
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    /**
     * return true if line is empty (e.g. empty, white space, tabs, etc...)
     *
     * @param line
     * @return
     */
    @Override
    public boolean isEmptyLine(String line) {
        final Pattern p = Pattern.compile(EmptyLineStr);
        Matcher m = p.matcher(line);
        return m.matches();
    }
}
