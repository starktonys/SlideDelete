package com.zidiv.slidedelete;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private Context context = this;
  private SwipeMenuListView listView;
  private List<String> list = new ArrayList<>();
  private MyAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    listView = ((SwipeMenuListView) findViewById(R.id.listView));
    //虚拟数据源
    for (int i = 0; i < 30; i++) {
      list.add("item" + i);
    }
    adapter = new MyAdapter(context, list);
    listView.setAdapter(adapter);
    //点击事件一样写
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "你点击了" + list.get(position), Toast.LENGTH_SHORT).show();
      }
    });
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "longClick" + list.get(position), Toast.LENGTH_SHORT).show();
        return true;
      }
    });
    //加入侧滑显示的菜单
    //1.首先实例化SwipeMenuCreator对象
    SwipeMenuCreator creater = new SwipeMenuCreator() {
      @Override public void create(SwipeMenu menu) {
        // create置顶item
        SwipeMenuItem item1 = new SwipeMenuItem(context);
        // set item background
        item1.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
        // set item width
        item1.setWidth(dp2px(90));
        // set item title
        item1.setTitle("置顶");
        // set item title fontsize
        item1.setTitleSize(18);
        // set item title font color
        item1.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(item1);

        //同理create删除item
        SwipeMenuItem deleteItem = new SwipeMenuItem(context);
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(dp2px(90));
        // set a icon
        deleteItem.setIcon(R.mipmap.ic_delete);
        // add to menu
        menu.addMenuItem(deleteItem);
      }
    };
    // set creator
    listView.setMenuCreator(creater);

    //2.菜单点击事件
    listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        switch (index) {
          case 0:
            //置顶的逻辑
            if (position == 0) {
              Toast.makeText(context, "此项已经置顶", Toast.LENGTH_SHORT).show();
              return false;
            }
            String str = list.get(position);
            for (int i = position; i > 0; i--) {
              String s = list.get(i - 1);
              list.remove(i);
              list.add(i, s);
            }
            list.remove(0);
            list.add(0, str);
            adapter.notifyDataSetChanged();
            break;
          case 1:
            //删除的逻辑
            list.remove(position);
            adapter.notifyDataSetChanged();
            break;
        }
        return false;
      }
    });
  }

  private int dp2px(int dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        getResources().getDisplayMetrics());
  }
}
