package com.example.textualreaddemo.room.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.textualreaddemo.room.AppDatabase;
import com.example.textualreaddemo.room.News;
import com.example.textualreaddemo.room.NewsDao;
import com.example.textualreaddemo.room.User;
import com.example.textualreaddemo.room.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBEngine {
    private UserDao userDao;
    private NewsDao newsDao;

    public DBEngine(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDao = appDatabase.getUserDao();
        newsDao = appDatabase.getNewsDao();
    }

    public void insertUsers(User... users){
        new InsertUsersAsyncTack(userDao).execute(users);
    }
    static class InsertUsersAsyncTack extends AsyncTask<User,Void,Void> {
        private UserDao userDao;

        public InsertUsersAsyncTack(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUsers(users);
            return null;
        }
    }

    public void deleteUserByUserID(String userID) {
        new DeleteUserByUserIDAsyncTack(userDao,userID).execute();
    }
    static  class DeleteUserByUserIDAsyncTack extends AsyncTask<Void,Void,Void> {
        private UserDao userDao;
        private String userID;

        public DeleteUserByUserIDAsyncTack(UserDao userDao, String userID) {
            this.userDao = userDao;
            this.userID = userID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteUserByUserID(userID);
            return null;
        }
    }

    public void deleteUserByUserName(String userName) {
        new DeleteUserByUserNameAsyncTack(userDao,userName).execute();
    }
    static class DeleteUserByUserNameAsyncTack extends AsyncTask<Void,Void,Void> {
        private UserDao userDao;
        private String userName;

        public DeleteUserByUserNameAsyncTack(UserDao userDao, String userName) {
            this.userDao = userDao;
            this.userName = userName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteUserByUserName(userName);
            return null;
        }
    }

    public void updateUsers(User... users) {
        new UpdateUsersAsyncTack(userDao).execute(users);
    }
    static class UpdateUsersAsyncTack extends AsyncTask<User,Void,Void> {
        private UserDao userDao;

        public UpdateUsersAsyncTack(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUsers(users);
            return null;
        }
    }
    public User getUserByUserID(String userID) {
        AsyncTask<Void, Void, User> task = new GetUserByUserIDAsyncTack(userDao, userID).execute();
        User user = null;
        try {
            user = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
    static class GetUserByUserIDAsyncTack extends AsyncTask<Void,Void,User> {
        private UserDao userDao;
        private String userID;

        public GetUserByUserIDAsyncTack(UserDao userDao, String userID) {
            this.userDao = userDao;
            this.userID = userID;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getUserByUserID(userID);
        }
    }
    public User getUserByName(String userName) {
        AsyncTask<Void, Void, User> task = new GetUserByNameAsyncTack(userDao, userName).execute();
        User user = null;
        try {
            user = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
    static class GetUserByNameAsyncTack extends AsyncTask<Void,Void,User> {
        private UserDao userDao;
        private String userName;

        public GetUserByNameAsyncTack(UserDao userDao, String userName) {
            this.userDao = userDao;
            this.userName = userName;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.getUserByName(userName);
        }
    }
    public void deleteAllUsers() {
        new DeleteAllUsersAsyncTack(userDao).execute();
    }
    static class DeleteAllUsersAsyncTack extends AsyncTask<Void,Void,Void> {
        private UserDao userDao;

        public DeleteAllUsersAsyncTack(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            return null;
        }
    }
    public List<User> getAllUsers () {
        AsyncTask<Void, Void, List<User>> task = new GetAllUsersAsyncTack(userDao).execute();
        List<User> userList = null;
        try {
            userList = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userList;
    }
    static class GetAllUsersAsyncTack extends AsyncTask<Void,Void,List<User>> {
        private UserDao userDao;

        public GetAllUsersAsyncTack(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }
    }

    public void insertNews(News... news) {
        new InsertNewsAsyncTack(newsDao).execute(news);
    }
    static class InsertNewsAsyncTack extends AsyncTask<News,Void,Void> {
        private NewsDao newsDao;

        public InsertNewsAsyncTack(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.insertNews(news);
            return null;
        }
    }

    public void deleteNews(News... news) {
        new DeleteNewsAsyncTack(newsDao).execute(news);
    }
    static class DeleteNewsAsyncTack extends AsyncTask<News,Void,Void> {
        private NewsDao newsDao;

        public DeleteNewsAsyncTack(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.deleteNews(news);
            return null;
        }
    }

    public void updateNews(News... news) {
        new UpdateNewsAsyncTack(newsDao).execute(news);
    }
    static class UpdateNewsAsyncTack extends AsyncTask<News,Void,Void> {
        private NewsDao newsDao;

        public UpdateNewsAsyncTack(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.updateNews(news);
            return null;
        }
    }

    public News getNewsByNewsID(String newsID) {
        AsyncTask<Void, Void, News> task = new GetNewsByNewsIDAsyncTack(newsDao, newsID).execute();
        News news = null;
        try {
            news = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return news;
    }
    static class GetNewsByNewsIDAsyncTack extends AsyncTask<Void,Void,News> {
        private NewsDao newsDao;
        private String newsID;

        public GetNewsByNewsIDAsyncTack(NewsDao newsDao, String newsID) {
            this.newsDao = newsDao;
            this.newsID = newsID;
        }

        @Override
        protected News doInBackground(Void... voids) {
            return newsDao.getNewsByNewsID(newsID);
        }
    }

    public News getNewsByUserID (String userID) {
        AsyncTask<Void, Void, News> task = new GetNewsByUserIDAsyncTack(newsDao, userID).execute();
        News news = null;
        try {
            news = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return news;
    }
    static class GetNewsByUserIDAsyncTack extends AsyncTask<Void,Void,News> {
        private NewsDao newsDao;
        private String userID;

        public GetNewsByUserIDAsyncTack(NewsDao newsDao, String userID) {
            this.newsDao = newsDao;
            this.userID = userID;
        }

        @Override
        protected News doInBackground(Void... voids) {
            return newsDao.getNewsByUserID(userID);
        }
    }

    public void deleteAllNews () {
        new DeleteAllNewsAsyncTack(newsDao).execute();
    }
    static class DeleteAllNewsAsyncTack extends AsyncTask<Void,Void,Void> {
        private NewsDao newsDao;

        public DeleteAllNewsAsyncTack(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAllNews();
            return null;
        }
    }

    public List<News> getAllNews() {
        AsyncTask<Void, Void, List<News>> task = new GetAllNewsAsyncTack(newsDao).execute();
        List<News> newsList = null;
        try {
            newsList = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsList;
    }
    static class GetAllNewsAsyncTack extends AsyncTask<Void,Void,List<News>> {
        private NewsDao newsDao;

        public GetAllNewsAsyncTack(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected List<News> doInBackground(Void... voids) {
            return newsDao.getAllNews();
        }
    }
}
