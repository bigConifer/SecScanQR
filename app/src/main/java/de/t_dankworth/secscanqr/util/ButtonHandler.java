package de.t_dankworth.secscanqr.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


import de.t_dankworth.secscanqr.R;


/**
 * Created by Thore Dankworth
 * Last Update: 03.09.2020
 * Last Update by Thore Dankworth
 *
 * This class handles the functionality of the buttons like share, reset, copy etc.
 */

public class ButtonHandler {

    /**
     * This method resets all the information that were shown on the MainActivty
     */
    public static void resetScreenInformation(){
        //
    }

    /**
     * This method copies the information of the QR-Code to the clipboard
     * @param tv = TextView were the qrcode is shown
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called. Needed for Toats and Clipboard
     */
    public static void copyToClipboard(TextView tv, String qrcode, Activity activity){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tv.getText(), qrcode);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(activity, activity.getResources().getText(R.string.notice_clipoard), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0,300);
        toast.show();
    }

    /**
     * This method handles the sharing functionality
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called. Needed for sharing intent
     */
    public static void shareTo(String qrcode, Activity activity){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, qrcode);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.send_to)));
    }

    /**
     * ONLY for history: The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called.Needed for Toast and web intent
     */
    public static void openInWeb(String qrcode, Activity activity){
        if(qrcode.equals("")){
            Toast.makeText(activity.getApplicationContext(), activity.getResources().getText(R.string.error_scan_first), Toast.LENGTH_SHORT).show();
        } else {
            try {
                if(qrcode.startsWith("URL:")){
                    qrcode = qrcode.replace("URL:", "");
                }
                Uri uri = Uri.parse(qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                String search_engine = prefs.getString("pref_search_engine", "");
                String tempUrl;
                switch (search_engine) {
                    case "1":
                        tempUrl = "https://www.bing.com/search?q=";
                        break;
                    case "2":
                        tempUrl = "https://duckduckgo.com/?q=";
                        break;
                    case "3":
                        tempUrl = "https://www.google.com/search?q=";
                        break;
                    case "4":
                        tempUrl = "https://www.qwant.com/?q=";
                        break;
                    case "5":
                        tempUrl = "https://lite.qwant.com/?q=";
                        break;
                    case "6":
                        tempUrl = "https://www.startpage.com/do/dsearch?query=";
                        break;
                    case "7":
                        tempUrl = "https://search.yahoo.com/search?p=";
                        break;
                    case "8":
                        tempUrl = "https://www.yandex.ru/search/?text=";
                        break;
                    default:
                        tempUrl = "https://www.google.com/search?q=";
                        break;
                }
                Uri uri = Uri.parse(tempUrl + qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * The method will open the qrcode as a website in the browser if it is valid web url. If not it will start a google search request.
     * @param qrcode = the qrcode as a String
     * @param format = format of the code
     * @param activity = Activty were the method was called.Needed for Toast and web intent
     */
    public static void openInWeb(String qrcode, String format, Activity activity){
        if(qrcode.equals("")){
            Toast.makeText(activity.getApplicationContext(), activity.getResources().getText(R.string.error_scan_first), Toast.LENGTH_SHORT).show();
        } else {
            try {
                if(qrcode.startsWith("URL:")){
                    qrcode = qrcode.replace("URL:", "");
                }
                Uri uri = Uri.parse(qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                String search_engine = prefs.getString("pref_search_engine", "");
                String barcode_engine = prefs.getString("pref_barcode_search_engine", "");
                String tempUrl;
                if(barcode_engine.equals("0")){
                    switch (search_engine) {
                        case "1":
                            tempUrl = "https://www.bing.com/search?q=";
                            break;
                        case "2":
                            tempUrl = "https://duckduckgo.com/?q=";
                            break;
                        case "3":
                            tempUrl = "https://www.google.com/search?q=";
                            break;
                        case "4":
                            tempUrl = "https://www.qwant.com/?q=";
                            break;
                        case "5":
                            tempUrl = "https://lite.qwant.com/?q=";
                            break;
                        case "6":
                            tempUrl = "https://www.startpage.com/do/dsearch?query=";
                            break;
                        case "7":
                            tempUrl = "https://search.yahoo.com/search?p=";
                            break;
                        case "8":
                            tempUrl = "https://www.yandex.ru/search/?text=";
                            break;
                        default:
                            tempUrl = "https://www.google.com/search?q=";
                            break;
                    }
                } else {
                    if (format.equals("QR_CODE") || format.equals("AZTEC")) {
                        switch (search_engine) {
                            case "1":
                                tempUrl = "https://www.bing.com/search?q=";
                                break;
                            case "2":
                                tempUrl = "https://duckduckgo.com/?q=";
                                break;
                            case "3":
                                tempUrl = "https://www.google.com/search?q=";
                                break;
                            case "4":
                                tempUrl = "https://www.qwant.com/?q=";
                                break;
                            case "5":
                                tempUrl = "https://lite.qwant.com/?q=";
                                break;
                            case "6":
                                tempUrl = "https://www.startpage.com/do/dsearch?query=";
                                break;
                            case "7":
                                tempUrl = "https://search.yahoo.com/search?p=";
                                break;
                            case "8":
                                tempUrl = "https://www.yandex.ru/search/?text=";
                                break;
                            default:
                                tempUrl = "https://www.google.com/search?q=";
                                break;
                        }
                    } else {
                        switch (barcode_engine) {
                            case "1":
                                tempUrl = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
                                break;
                            case "2":
                                tempUrl = "https://www.codecheck.info/product.search?q=";
                                break;
                            default:
                                tempUrl = "https://www.google.com/search?q=";
                                break;
                        }
                    }
                }

                Uri uri = Uri.parse(tempUrl + qrcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * This method will open the contacts app and will create a contact with the given information
     * @param qrcode = the qrcode as a String
     * @param activity = Activty were the method was called.Needed for Toast and web intent
     */
    public static void createContact(String qrcode, Activity activity) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        String information[] = qrcode.split("\\r?\\n");
        String notes = "";

        for(int i = 0; i < information.length; i++){
            if (information[i].contains("N:")){
                String[] separeted = information[i].split(":");
                String name = separeted[1].replace(";", " ");
                GeneralHandler generalHandler = new GeneralHandler(activity);
                name = generalHandler.reverseName(name);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            } else if(information[i].contains("BDAY")){
                notes = notes + "\n" + information[i];
            }   else if(information[i].contains("ORG")){
                String[] separeted = information[i].split(":");
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, separeted[1]);
            } else if(information[i].contains("URL")){
                String[] separeted = information[i].split(":");
                notes = notes + "\n" + separeted[1];
            } else if(information[i].contains("EMAIL")){
                String[] separeted = information[i].split(":");
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, separeted[1]);
            } else if(information[i].contains("TEL")){
                String[] separeted = information[i].split(":");

                if(separeted[0].contains("CELL")){
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, separeted[1]);
                } else if(separeted[0].contains("WORK")){
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    intent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, separeted[1]);
                } else if(separeted[0].contains("HOME")){
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, separeted[1]);
                } else if(separeted[0].contains("VOICE")){
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_OTHER);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, separeted[1]);
                }
            } else if(information[i].contains("ADR")) {
                String[] separeted = information[i].split(":");
                String[] adr = separeted[1].split(";");
                notes = notes + "\n" + adr[2] + "\n" + adr[3] + "\n" + adr[4] + "\n" + adr[5] + "\n" + adr[6];
            } else if(information[i].contains("NOTE")){
                notes = notes + "\n" + information[i];
            }
            intent.putExtra(ContactsContract.Intents.Insert.NOTES, notes);
        }
        activity.startActivity(intent);
    }
}
