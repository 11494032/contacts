package com.example.administrator.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity{

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById( R.id.lv_list);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        20);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        ArrayList< HashMap< String, String>> arrayList = getSIMContacts();

      //  System.out.println(arrayList);

       listView.setAdapter( new SimpleAdapter(this,arrayList, R.layout.constact_activity, new String[]{"name","number"}, new int[]{R.id.tv_name,R.id.tv_phone}));
    }
    private ArrayList<HashMap<String,String>>  getSIMContacts()
    {
        /*
        Uri uri = Uri.parse("content:// com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content:// com.android.contacts/data");

        ArrayList< HashMap< String, String>> arrayList= new ArrayList<HashMap< String, String>>();
        Cursor rawContactsCursor = getContentResolver().query( uri, new String[]{"contact_id"}, null, null , null );
        if( rawContactsCursor != null )
        {
            while ( rawContactsCursor.moveToNext() )
            {
               String str =  rawContactsCursor.getString(0);
                Cursor dataCursor = getContentResolver().query( dataUri,
                        new String[]{"data1","mimetype"}, "contact_id=?",
                        new String[]{} , null );

                if( dataCursor != null )
                {
                    while( dataCursor.moveToNext() )
                    {
                        HashMap< String, String>  hashMap= new HashMap<String, String>();
                        String data1 = dataCursor.getString(0);
                        String minitype = dataCursor.getString(1);

                        if("vnd.android.cursor.item/phone_v2".equals(minitype))
                        {
                            hashMap.put("phone",data1 );
                        }
                        else if( "vnd.android.cursor.item/name".equals(minitype))
                        {
                            hashMap.put("name",data1 );
                        }
                        arrayList.add( hashMap);
                    }
                }
                dataCursor.close();
            }




            rawContactsCursor.close();
        }

        return arrayList;
*/


        ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  null, null, null, null);
        String phoneNumber;
        String phoneName;
        while (cursor.moveToNext()) {

            HashMap< String, String>  hashMap= new HashMap<String, String>();
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//电话号码
            phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//姓名

            System.out.println("====================>"+phoneName+phoneNumber);
            if( !TextUtils.isEmpty(phoneName) && !TextUtils.isEmpty(phoneNumber) ) {
                hashMap.put("name", phoneName);
                hashMap.put("number", phoneNumber);
                arrayList.add(hashMap);
            }
        }
        cursor.close();
        System.out.println(arrayList);
        return arrayList;
    }


}
