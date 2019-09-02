package resources;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarConstant {

    public static String LOGGED_IN = "Logged In";
    public static String USER_TYPE = "User_Type";
    public static String CONSUMER = "Consumer";
    public static String CARWASHER = "Carwasher";

    static ProgressDialog progressDoalog = null;
    public static Map<String, List<String>> carModelmap = new HashMap<>();

    public static List<String> carMakeList = new ArrayList<>(Arrays.asList("Select Maker","Ariel","Ashok Leyland","Aston Martin","Audi"
    ,"Bajaj","Bently","BMW","Bugatti","Chevrolet","Datsun","Eicher","Ferrari","Fiat","Force Motor","Ford","Hindustan Motor"
    ,"Honda","Hyundai","Jaguar","Jeep","Lamborghini","Land Rover","Lexus","Mahindra","Mahindra Reva","Maruti Suzuki"
    ,"Mercedes-Benz","Mini","Mitsubishi","Nissan","Opel","Porshe","Premier","Renault","Rolls-Royce","Skoda","TATA","Toyoto"
    ,"Volkswagen","Volvo"));

    static {
        carModelmap.put("Ariel",new ArrayList<>(Arrays.asList("Atom")));
        carModelmap.put("Ashok Leyland",new ArrayList<>(Arrays.asList("Stile")));
        carModelmap.put("Aston Martin",new ArrayList<>(Arrays.asList("DB11","DBS","Rapide","V12 Vantage","V8 Vantage","Vanquish")));
        carModelmap.put("Audi",new ArrayList<>(Arrays.asList("A3","A4","A6","A8L","Q3","Q5","Q7","R8","RS5","RS6","RS7","S4","S5 Sportback","S6","TT")));
        carModelmap.put("Bajaj",new ArrayList<>(Arrays.asList("Qute RE60")));
        carModelmap.put("Bently",new ArrayList<>(Arrays.asList("Bentayga","Continental","Flying spur","Mulsanne")));
        carModelmap.put("BMW",new ArrayList<>(Arrays.asList("1-Series","3-Series","3-Series GT","5-Series","6-Series","6-Series Gran Coupe","7-Series","i8","M3","M5","M6","X1","X3","X5","X6","Z4")));
        carModelmap.put("Bugatti",new ArrayList<>(Arrays.asList("Veyron")));
        carModelmap.put("Chevrolet",new ArrayList<>(Arrays.asList("Beat","Cruze","Enjoy","Sail Hatchback","Sail Sedan","Sail U-VA","Spark","Tavera","Trailblazer")));
        carModelmap.put("Datsun",new ArrayList<>(Arrays.asList("Go Plus","Redi-Go")));
        carModelmap.put("Eicher",new ArrayList<>(Arrays.asList("Polaris Multix")));
        carModelmap.put("Ferrari",new ArrayList<>(Arrays.asList("458","488","California","F12Berlinetta","FF","GTC4Lusso")));
        carModelmap.put("Fiat",new ArrayList<>(Arrays.asList("500","Abarth Punto EVO","Avventura","Avventura Abarth","Avventura Urban Cross","Linea","Linea Classics","Punto Evo","Punto Pure")));
        carModelmap.put("Force Motor",new ArrayList<>(Arrays.asList("Gurkha","Traveller","Trax","Trump")));
        carModelmap.put("Ford",new ArrayList<>(Arrays.asList("Ecosport","Endeavour","Fiesta","Figo aspire","Figo Hatchback","Figo Sport","Fusion","Ikon","Mustang")));
        carModelmap.put("Hindustan Motor",new ArrayList<>(Arrays.asList("Ambassador")));
        carModelmap.put("Honda",new ArrayList<>(Arrays.asList("Accord","Amaze","BR-V","Brio","City","Civic","CR-V","CR-V Fourth Generation","Jazz","Mobilio","WR-V")));
        carModelmap.put("Hyundai",new ArrayList<>(Arrays.asList("Accent","Creta","Eilian i20","Elantra","Eon","Fluidic Varna","Getz","Grand i10","i10","i20","i20 Active","Santa Fe","Santro","Sonata","Tuscon","Verna","Xcent")));
        carModelmap.put("Jaguar",new ArrayList<>(Arrays.asList("F-Pace","F-Type","XE","XF","XJ","XKR")));
        carModelmap.put("Jeep",new ArrayList<>(Arrays.asList("Compass","Grand Cherokee","Wrangler Ultimate")));
        carModelmap.put("Lamborghini",new ArrayList<>(Arrays.asList("Aventador","Huracan")));
        carModelmap.put("Land Rover",new ArrayList<>(Arrays.asList("Discovery4","Discovery Sport","Freelander","Range Rover","Range Rover Evoque","Range Rover Sport")));
        carModelmap.put("Lexus",new ArrayList<>(Arrays.asList("ES300","LX 450d","RX 450h")));
        carModelmap.put("Mahindra",new ArrayList<>(Arrays.asList("Bolero","e2o Plus","Jeep Classic","KUV 100","Marazzo","Nuvosport","Quanto","Scorpio","Thar","TUV300","Verito","Verito Vieb","XUV500","Xylo")));
        carModelmap.put("Mahindra Reva",new ArrayList<>(Arrays.asList("e2o Plus","Reva-i")));
        carModelmap.put("Maruti Suzuki",new ArrayList<>(Arrays.asList("800","A-Star","Alto 800","Alto K10","Baleno","Celerio","Ciaz","Dzire","Eeco","Ertiga","Esteem","Gypsi","ignis","Omni","Ritz","S-Cross","Swift","SX4","Vitara Brezza","WagonR","Zen","Zen Estillo")));
        carModelmap.put("Mercedes-Benz",new ArrayList<>(Arrays.asList("A-Class","AMG GT S","AMG SLC 43","B-Class","C 63 AMG","C-Class","C-Class Cabriolet","CLA class","CLS 63 AMG","CLS Class","E Class MY16","E-Class","E63 AMG","G-Class","G63 AMG","GLA Class","GLC","GLE Class","GLS Class","M-Class","R-Class","S-Class","S-Class Cabriolet","SLK","SLS AMG")));
        carModelmap.put("Mini",new ArrayList<>(Arrays.asList("3 Door","5 Door","Clubman","Copper Convertible","Countryman")));
        carModelmap.put("Mitsubishi",new ArrayList<>(Arrays.asList("Cedia","Lancer","Montero","Outlander","Pajero")));
        carModelmap.put("Nissan",new ArrayList<>(Arrays.asList("370Z","Evalia","GT-R","Micra","Micra Active","Micra Facelift","Serena","Sunny","Teana","Teranno","X-Trail")));
        carModelmap.put("Opel",new ArrayList<>(Arrays.asList("Astra","Corsa")));
        carModelmap.put("Porshe",new ArrayList<>(Arrays.asList("911","Boxster","Cayenne","Cayman","Macan","Panamera")));
        carModelmap.put("Premier",new ArrayList<>(Arrays.asList("Rio")));
        carModelmap.put("Renault",new ArrayList<>(Arrays.asList("Capture","Duster","KWID","Lodgy","Logan","Pulse","Scala")));
        carModelmap.put("Rolls-Royce",new ArrayList<>(Arrays.asList("Dwan","Ghost","Phantom","Wraith")));
        carModelmap.put("Skoda",new ArrayList<>(Arrays.asList("Fabia","Laura","Octavia","Rapid","Superb","Yeti")));
        carModelmap.put("TATA",new ArrayList<>(Arrays.asList("Aria","Bolt","Hexa","Indica eV2","Indica V2","Indica Vista","Indigo eCS","Manza","Nano","Nano GenX","Nexon","Safari Strome","Sierra","Sumo Gold","Tiago","Tigor","Winger","Xenon ST","Xenon Yodha","Zest")));
        carModelmap.put("Toyoto",new ArrayList<>(Arrays.asList("Camry","Corolla Altis","Etios Cross","Etios Liva","Fortuner","Innova","Innova Crysta","Land Cruiser 200","Land Cruiser Prado","Plantinum Etios","Prius","Qualis","Yaris")));
        carModelmap.put("Volkswagen",new ArrayList<>(Arrays.asList("Ameo","Beetle","Cross Polo","Jetta","Passat","Polo","Polo GTI","Tiguan","Vento")));
        carModelmap.put("Volvo",new ArrayList<>(Arrays.asList("S60","S60 Cross Country","S80","S90","V40","V40 Cross Country","V90 Cross Country","XC60","XC90")));
    }

    public static ProgressDialog getProgressDialog(Context context ,String msg){

        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMax(100);
        progressDoalog.setCancelable(false);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setMessage(msg);
        progressDoalog.setTitle("Please Wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progressDoalog;
    }
}
