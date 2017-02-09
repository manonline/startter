package com.manonline.examples.javabasic.innerclass;

/**
 * Created by davidqi on 2/9/17.
 */
public class AnomynousInner {
}


/** example 1
 * 显示定义了类 － Listener1, Listener2； 基于雷创建了对象，并赋给方法参数；代码不够清晰
 */
// private void setListener()
// {
//     scan_bt.setOnClickListener(new Listener1());
//     history_bt.setOnClickListener(new Listener2());
// }
//
// class Listener1 implements View.OnClickListener{
//     @Override
//     public void onClick(View v) {
//         // TODO Auto-generated method stub
//
//     }
//}
//
// class Listener2 implements View.OnClickListener{
//     @Override
//     public void onClick(View v) {
//         // TODO Auto-generated method stub
//
//     }
// }

/** example 2
 * 直接实现了接口，并覆盖借口的方法，并没有定义类名。
 * 匿名内部类是唯一一种没有构造器的类。
 */
//private void setListener {
//    scan_bt.setOnClickListener(new OnClickListener(){
//        @Override
//        public void onClick(View v){
//            // TODO Auto-generated method stub
//        }
//        });
//
//        history_bt.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//        }
//        });
//
//}