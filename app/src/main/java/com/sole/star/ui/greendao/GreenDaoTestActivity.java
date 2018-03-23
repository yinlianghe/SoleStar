package com.sole.star.ui.greendao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.sole.star.R;
import com.sole.star.application.MyApplication;
import com.sole.star.greendao.bean.User;
import com.sole.star.greendao.gen.DaoSession;
import com.sole.star.greendao.gen.UserDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * greenDao练习
 */

public class GreenDaoTestActivity extends AppCompatActivity {
    private ListView mListView;
    private Query<User> userQuery;
    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_test);

        mListView = findViewById(R.id.list_view);

        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        userDao = daoSession.getUserDao();

//        insertUser();

        userQuery = userDao.queryBuilder().where(UserDao.Properties.Salary.gt(5000)).build();
        List<User> result = userQuery.list();
        for (User user : result) {
            Log.i("aa===", user.getName() + "-" + user.getSalary());
        }
    }

    //插入数据
//    private void insertUser(){
//        User user = new User(null,"jianguotang", "男",18,2000,1);
//        userDao.insert(user);
//        List<User> users = new ArrayList<>();
//        users.add(new User(null, "jianguotang", "男", 18, 2000,2));
//        users.add(new User(null, "addds", "男", 19, 3000,3));
//        users.add(new User(null, "dwwdd", "女", 21, 4000,4));
//        users.add(new User(null, "ews", "女", 22, 5000,5));
//        users.add(new User(null, "dwddsa", "女", 23, 6000,6));
//        users.add(new User(null, "jianguotang", "女", 24, 7000,7));
//        users.add(new User(null, "dfefa", "女", 25, 8000,8));
//        userDao.insertInTx(users);
//    }
    //删除特定位置的数据
    private void deleteUser(){
        userDao.deleteByKey(5l);
    }

    /**
     * 对位置 为position的的数据进行修改
     * @param position
     */
    private void updateUser(Long position){
        //查询id是1位置的数据
        User user = userDao.load(5l);
        //对其进行修改
        user.setName("简国堂");
        userDao.update(user);
    }

    //查询全部的数据
    private List<User> queryList(){
        List<User> users = userQuery.list();
        return users;
    }

    /**
     *  按照属性name和sex来查询user
     * @param name
     */
    private List<User> queryByName(String name,String sex){
        QueryBuilder<User> builder = userDao.queryBuilder();
        Query<User> query = builder
                .where(UserDao.Properties.Name.eq(name),UserDao.Properties.Sex.eq(sex))
                .build();
        List<User> list = query.list();
        return  list;
    }


}
