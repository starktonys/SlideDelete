# SlideDelete
仿QQ滑动删除和置顶

### 效果图 ###


![](http://img.blog.csdn.net/20160225135154500?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

### 实现步骤 ###

- 1.这种效果的ListView是自定义的控件,开源库的下载地址是进入[github](https://github.com/baoyongzhang/SwipeMenuListView "github")地址,大家新建工程后依赖,然后就可以使用这个自定义控件,依赖库在后面的demo中,后面会提供demo的下载地址

- 2.布局文件,引入这个自定义控件

	
		<?xml version="1.0" encoding="utf-8"?>  
		<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/	android"  
	    	xmlns:tools="http://schemas.android.com/tools"  
		    android:layout_width="match_parent"  
		    android:layout_height="match_parent"  
		    tools:context="com.zidiv.slidedelete.MainActivity">  
	  
		    <com.baoyz.swipemenulistview.SwipeMenuListView  
		        android:id="@+id/listView"  
		        android:layout_width="match_parent"  
		        android:layout_height="match_parent" />  
		</RelativeLayout>  
	

- 3.在activity中找到布局中的控件,这个控件和源生的ListView一样可以设置条目点击和长点击事件

		listView = ((SwipeMenuListView) findViewById(R.id.listView));  
	        //虚拟数据源  
	        for (int i = 0; i < 30; i++) {  
	            list.add("item" + i);  
	        }  
	        adapter = new MyAdapter(context, list);  
	        listView.setAdapter(adapter);  
	        //点击事件一样写  
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
	            @Override  
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
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

- 4.向ListView中加入左滑出现的删除置顶菜单
	
		//加入侧滑显示的菜单  
	        //1.首先实例化SwipeMenuCreator对象  
	        SwipeMenuCreator creater = new SwipeMenuCreator() {  
	            @Override  
	            public void create(SwipeMenu menu) {  
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


- 5.给删除和置顶菜单添加点击事件


		//2.菜单点击事件  
	        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {  
	            @Override  
	            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {  
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