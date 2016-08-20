package com.example.lxchild.result;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lxchild.healthassistant.R;

/**
 * Created by LXChild on 9/9/15.
 */
public class ResultActivity extends Activity {

    private TextView tv;
    private static int count = 0;
    private static final String[] result_arr = new String[]{
            "检测结果：\n\n" +
                    "肝、心色诊分区呈明显异色\n\n\n" +
                    "可能原因：\n\n" +
                    "酒精过量，会程度不同地造成心率加快\n" +
                    "皮肤升温，神志不清，控制力减弱，动作不协调\n" +
                    "或出现疲劳、恶心、头痛、呕吐\n" +
                    "严重的还会出现酒精中毒现象。\n\n\n" +
                    "贴心建议：\n\n" +
                    "(1)量力而行，适可而止，记住自己的酒量；\n" +
                    "(2)饮酒之前先吃点东西，空腹酣饮是最容易醉倒的；\n" +
                    "(3)合理饮酒，让健康的生活永远伴随着自己",

            "检测结果：\n\n脾虚,平常运动少，思虑过度、\n" +
                    "饮食不规律、营养不均衡\n\n\n" +
                    "贴心建议：\n\n" +
                    "（1）规律运动,尤其是四肢的运动，非常重要，每天都要坚持.\n" +
                    "(2)健康饮食,定时定量，适当吃些山药、\n" +
                    "薏苡仁、莲子肉、桂圆、陈皮等健脾之品.\n" +
                    "(3)工作不忙的时候,来一场说走就走的旅行吧",

            "检测结果:\n\n" +
                    "休息不当,气血亏虚,眼周皮肤血液循环不畅\n\n\n" +
                    "贴心建议:\n\n" +
                    "(1)保持充足的睡眠及正确的仰卧睡姿。\n" +
                    "(2)改正不良的饮食习惯，\n" +
                    "勿摄入过咸的食物和刺激性过大的食物。\n" +
                    "(3)及时治疗所存在的慢性疾病，\n" +
                    "加强营养，适当补充维他命C、A、E。"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reuslt);
        showActionBar();
        initView();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv_result);
        setResult();
    }

    public void setResult() {
       // tv.setText(this.getIntent().getStringExtra("result"));
        tv.setText(result_arr[count++]);
    }

    public void showActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
