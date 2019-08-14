package com.shawn.cap03.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 该测试用例主要是用于学习正则表达式相关规范<br>
 * changeLog:2019-8-14<br>
 */
@Slf4j
public class RegexUtilsTest {

    //后面的3表示后面跟随连续3个数字比如1后面跟234,9后面跟876<br>
    public static String ABCDE  = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3,}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){3,})\\d";

    //通过占位符来描述pattern，  具体思路： ABBABB--> 划分成 AB BABB， 而不应该去找模式ABB  ABB.（A为\\1,B为\\2）
    //只有被()刮起来的才算做占位符<br>
    public static String ABBABB = "^(\\d)(\\d)\\2\\1\\2\\2$";


    //注意，前面[0-9]没有打()就不占位，所以\\1为第一个(\\d)
    public static String ASTERISK_WITH_ABBABB = "[0-9]+(\\d)(\\d)\\2\\1\\2\\2$";

    public static String ASTERISK_WITH_ABBABB1 = "[\\d]+(\\d)(\\d)\\2\\1\\2\\2$";

    public static String ASTERISK_WITH_ABBABB2 = "(\\d)+(\\d)(\\d)\\2\\1\\2\\2$";

    //567 355355 这里 567为\\1, 3为\\2 ,5为\\2
    public static String ASTERISK_WITH_ABBABB4 = "(\\d)+(\\d)(\\d)\\3\\2\\3\\3$";

    public static String AAAB = "^(\\d)\\1\\1(\\d)$";

    // 匹配3335场景，注意"^(\d){3}(\d)$"是不行的<br>
    public static String AAABV1 = "^(\\d)\\1{2}(\\d)$";

    /**
     * 背景：在resources/下增加log4j.properties后日志打印出来了，console/log.out都有日志输出<br>
     * 该测试用例测试 4个连续数字的规则，比如 1234/5678/9876这样的形式<br>
     */
    @Test
    public void testABCDE()
    {
        matcherPrintResult("123458",ABCDE);

        //not matcher 没有达到4位连续<br>
        matcherPrintResult("129",ABCDE);

        //not matcher 没有达到4位连续<br>
        matcherPrintResult("0129",ABCDE);

        //matcher  1).必须要要4位 2).不必要以0开头  3).最后一位不一定要连续，因为最后有/d表示一个数字<br>
        matcherPrintResult("12349",ABCDE);

        matcherPrintResult("01239",ABCDE);

    }
    @Test
    public void testABBABB()
    {
        //matcher 具体思路： ABBABB--> 划分成 AB BABB， 而不应该去找模式ABB  ABB，
        matcherPrintResult("122122",ABBABB);

        //FIXME 没有解决 A!=B 的问题，我们希望把 A=B这种场景去掉<br>
        //matcher
        matcherPrintResult("333333",ABBABB);
    }

    @Test
    public void testABBABBWithAsterisk()
    {
        matcherPrintResult("355355",ASTERISK_WITH_ABBABB);
        //mathcer ,[0-9] 前缀的是可以的,这里的\\1为2 \\2为5
        matcherPrintResult("567255255",ASTERISK_WITH_ABBABB);
        //matcher \\d 前缀也是可以的
        matcherPrintResult("567355355",ASTERISK_WITH_ABBABB1);

        matcherPrintResult("567355355",ASTERISK_WITH_ABBABB2);

        //matcher 说明，第一个(\\d)为\\1  第二个(\\d)为\\2  第三个(\\d)为\\3<br>
        matcherPrintResult("567355355",ASTERISK_WITH_ABBABB4);
    }

    @Test
    public void testAAAB(){
        //matcher
        matcherPrintResult("3335",AAAB);
        //not matcher
        matcherPrintResult("2345",AAAB);
    }

    @Test
    public void testAAABV1(){
        //matcher
        matcherPrintResult("3335",AAABV1);
        //matcher,
        matcherPrintResult("2345",AAABV1);
    }

    private void matcherPrintResult(String input ,String pattern)
    {
        if (RegexUtils.contains(input,pattern))
        {
            log.info("input:{} matcher with pattern:{}",input,pattern);
        }else{
            log.info("input:{} not matcher with pattern:{}",input,pattern);
        }
    }
}
