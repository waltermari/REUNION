package fr.waltermarighetto.reunion.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
 
        private ArrayList<User> mUserList = new ArrayList<User>();

        public ArrayList<User> getUserList() {
            return mUserList;
        }

        public void setUserList(ArrayList<User> userList) {
            mUserList = userList;
        }

        public void addUserInList(User user, UserList userList) {
            
            if (!mUserList.contains(user))
            mUserList.add(user);
        }
        public void removeUserFromList(User user, UserList userList) {
            if (mUserList.contains(user))
                mUserList.remove(user);
        }
    }
    
    

