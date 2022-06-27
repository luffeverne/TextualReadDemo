package com.example.textualreaddemo.room.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.textualreaddemo.room.AppDatabase;
import com.example.textualreaddemo.room.CollectedNews;
import com.example.textualreaddemo.room.CollectedNewsDao;
import com.example.textualreaddemo.room.HistoryNews;
import com.example.textualreaddemo.room.HistoryNewsDao;
import com.example.textualreaddemo.room.User;
import com.example.textualreaddemo.room.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBEngine {
    private UserDao userDao;
    private CollectedNewsDao collectedNewsDao;
    private HistoryNewsDao historyNewsDao;

    public DBEngine(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        collectedNewsDao = appDatabase.getCollectedNewsDao();
        historyNewsDao = appDatabase.getHistoryNewsDao();
        userDao = appDatabase.getUserDao();
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

    public void insertCollectedNews(CollectedNews... collectedNews) {
        new InsertCollectedNewsAsyncTack(collectedNewsDao).execute(collectedNews);
    }
    static class InsertCollectedNewsAsyncTack extends AsyncTask<CollectedNews,Void,Void> {

        private CollectedNewsDao collectedNewsDao;

        public InsertCollectedNewsAsyncTack(CollectedNewsDao collectedNewsDao) {
            this.collectedNewsDao = collectedNewsDao;
        }

        @Override
        protected Void doInBackground(CollectedNews... collectedNews) {
            collectedNewsDao.insertCollectedNews(collectedNews);
            return null;
        }
    }

    public void insertHistoryNews(HistoryNews... historyNews) {
        new InsertHistoryNewsAsyncTack(historyNewsDao).execute(historyNews);
    }
    static class InsertHistoryNewsAsyncTack extends AsyncTask<HistoryNews,Void,Void> {
        private HistoryNewsDao historyNewsDao;

        public InsertHistoryNewsAsyncTack(HistoryNewsDao historyNewsDao) {
            this.historyNewsDao = historyNewsDao;
        }

        @Override
        protected Void doInBackground(HistoryNews... historyNews) {
            historyNewsDao.insertHistoryNews(historyNews);
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

    public void deleteCollectedNewsByNewsID(String newsID) {
        new DeleteCollectedNewsByNewsIDAsyncTack(collectedNewsDao,newsID).execute();
    }
    static class DeleteCollectedNewsByNewsIDAsyncTack extends AsyncTask<Void,Void,Void> {
        private CollectedNewsDao collectedNewsDao;
        private String newsID;

        public DeleteCollectedNewsByNewsIDAsyncTack(CollectedNewsDao collectedNewsDao, String newsID) {
            this.collectedNewsDao = collectedNewsDao;
            this.newsID = newsID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            collectedNewsDao.deleteCollectedNewsByNewID(newsID);
            return null;
        }
    }

    public void deleteHistoryNewsByNewsID(String newsID) {
        new DeleteHistoryNewsByNewsIDAsyncTack(historyNewsDao,newsID).execute();
    }
    static class DeleteHistoryNewsByNewsIDAsyncTack extends AsyncTask<Void,Void,Void> {
        private HistoryNewsDao historyNewsDao;
        private String newsID;

        public DeleteHistoryNewsByNewsIDAsyncTack(HistoryNewsDao historyNewsDao, String newsID) {
            this.historyNewsDao = historyNewsDao;
            this.newsID = newsID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            historyNewsDao.deleteHistoryNewsByNewsID(newsID);
            return null;
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

    public void deleteAllCollectedNews() {
        new DeleteAllCollectedNewsAsyncTack(collectedNewsDao).execute();
    }
    static class DeleteAllCollectedNewsAsyncTack extends AsyncTask<Void,Void,Void> {

        private CollectedNewsDao collectedNewsDao;

        public DeleteAllCollectedNewsAsyncTack(CollectedNewsDao collectedNewsDao) {
            this.collectedNewsDao = collectedNewsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            collectedNewsDao.deleteAllCollectedNews();
            return null;
        }
    }

    public void deleteAllHistoryNews() {
        new DeleteAllHistoryNewsAsyncTack(historyNewsDao).execute();
    }
    static class DeleteAllHistoryNewsAsyncTack extends AsyncTask<Void,Void,Void> {
        private HistoryNewsDao historyNewsDao;

        public DeleteAllHistoryNewsAsyncTack(HistoryNewsDao historyNewsDao) {
            this.historyNewsDao = historyNewsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            historyNewsDao.deleteAllHistoryNews();
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

    public CollectedNews getCollectedNewsByNewsID(String newsID) {
        AsyncTask<Void, Void, CollectedNews> task = new GetCollectedNewsByNewsIDAsyncTack(collectedNewsDao, newsID).execute();
        CollectedNews collectedNews = null;
        try {
            collectedNews = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return collectedNews;
    }
    static class GetCollectedNewsByNewsIDAsyncTack extends AsyncTask<Void,Void,CollectedNews> {
        private CollectedNewsDao collectedNewsDao;
        private String newsID;

        public GetCollectedNewsByNewsIDAsyncTack(CollectedNewsDao collectedNewsDao, String newsID) {
            this.collectedNewsDao = collectedNewsDao;
            this.newsID = newsID;
        }

        @Override
        protected CollectedNews doInBackground(Void... voids) {
            return collectedNewsDao.getCollectedNewsByNewsID(newsID);
        }
    }

    public HistoryNews getHistoryNewsByNewsID (String newsID) {
        AsyncTask<Void, Void, HistoryNews> task = new GetHistoryNewsByNewsIDAsyncTack(historyNewsDao, newsID).execute();
        HistoryNews historyNews = null;
        try {
            historyNews = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return historyNews;
    }
    static class GetHistoryNewsByNewsIDAsyncTack extends AsyncTask<Void,Void,HistoryNews>{
        private HistoryNewsDao historyNewsDao;
        private String newsID;

        public GetHistoryNewsByNewsIDAsyncTack(HistoryNewsDao historyNewsDao, String newsID) {
            this.historyNewsDao = historyNewsDao;
            this.newsID = newsID;
        }

        @Override
        protected HistoryNews doInBackground(Void... voids) {
            return historyNewsDao.getHistoryNewsByNewsID(newsID);
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

    public List<CollectedNews> getAllCollectedNews() {
        AsyncTask<Void, Void, List<CollectedNews>> task = new GetAllCollectedNewsAsyncTack(collectedNewsDao).execute();
        List<CollectedNews> collectedNewsList = null;
        try {
            collectedNewsList = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return collectedNewsList;
    }
    static class GetAllCollectedNewsAsyncTack extends AsyncTask<Void,Void,List<CollectedNews>> {
        private CollectedNewsDao collectedNewsDao;

        public GetAllCollectedNewsAsyncTack(CollectedNewsDao collectedNewsDao) {
            this.collectedNewsDao = collectedNewsDao;
        }

        @Override
        protected List<CollectedNews> doInBackground(Void... voids) {
            return collectedNewsDao.getAllCollectedNews();
        }
    }

    public List<HistoryNews> getAllHistoryNews () {
        AsyncTask<Void, Void, List<HistoryNews>> task = new GetAllHistoryNewsAsyncTAck(historyNewsDao).execute();
        List<HistoryNews> historyNewsList = null;
        try {
            historyNewsList = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return historyNewsList;
    }
    static class GetAllHistoryNewsAsyncTAck extends AsyncTask<Void,Void,List<HistoryNews>> {
        private HistoryNewsDao historyNewsDao;

        public GetAllHistoryNewsAsyncTAck(HistoryNewsDao historyNewsDao) {
            this.historyNewsDao = historyNewsDao;
        }

        @Override
        protected List<HistoryNews> doInBackground(Void... voids) {
            return historyNewsDao.getAllHistoryNews();
        }
    }
}
