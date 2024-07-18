package com.wf.secondhand.data;

public class SecondhandData{
    //创建默认user成员
    private static final String USER_SUPER ="insert into t_user(username,password,email,phone)" +
            " values('飞马官方','123456','1769755216@qq.com','17775103680')";

    //创建默认product成员
    private static final String PRODUCT1 ="insert into t_product(title,content,author,price)" +
            " values('任天堂 Switch','任天堂 Switch双系统  所有游戏都可以玩 装满游戏发货 \n" +
            " \n" +
            " 128g   可装8款游戏 \n" +
            " 256g   可装25款游戏 \n" +
            " 512g  可装45款游戏 \n" +
            " \n" +
            "*Ns配件 \n" +
            "红蓝手柄一对 \n" +
            "手柄腕带一对 \n" +
            "手柄握把一个 \n" +
            "电视底座一个 \n" +
            "高清线一根 \n" +
            "充电器一个  \n" +
            "钢化膜一张 \n" +
            " \n" +
            "一年保修，全国顺丰到付！ \n" +
            "标价为单板价格，详情请咨询私聊！ \n" +
            "#Nintendo/任天堂 \n" +
            "#Nintendo/任天堂 ','飞马官方',2999.0)";

    private static final String PRODUCT2 ="insert into t_product(title,content,author,price)" +
            " values('iphone13','iphone13，粉色，128gb。购买于今年5月apple直营店，有盒子小票充电线。机子非常新，有保护壳和屏保。" +
            "没有任何划痕。运行流畅。 在保到23年5月12，电池100%。 细节见图。可深圳面交。 \n" +
            "#iPhone 13 #Apple/苹果 ','飞马官方',4999.0)";


    public String[] getUser(){
        String [] a =new String[]{USER_SUPER};
        return a;
    }

    public String[] getProduct(){
        String [] a =new String[]{PRODUCT1,PRODUCT2};
        return a;
    }
}
