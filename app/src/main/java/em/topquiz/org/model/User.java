package em.topquiz.org.model;

/*
 *   Created by ME on 10/11/2019.
 */public class User {

      //Alt+Inser   -->  generer Ctor Getter & Setter

     private String mPrenom;

     public String getPrenom() {
          return mPrenom;
     }

     public void setPrenom(String pPrenom) {
          mPrenom = pPrenom;
     }

     @Override
     public String toString() {
          return "User{" +
                  "mPrenom='" + mPrenom + '\'' +
                  '}';
     }
}
