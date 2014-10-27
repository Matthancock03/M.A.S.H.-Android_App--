package com.example.misanthropic.mash;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Node;

import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class Mash_Game extends Activity implements Pick_Husbands.OnFragmentInteractionListener ,Choose_A_Number.OnFragmentInteractionListener,
        Choose_cars.OnFragmentInteractionListener, Number_Kids.OnFragmentInteractionListener, Results.OnFragmentInteractionListener{
    private String userName = null;
    private String husbands;
    private String car1;
    private String houses;
    private int kids1;
    private int numpick;
    private int numWinners = 0;
    String winnerMessage = null;

    ArrayList<mashNode> board = new ArrayList<mashNode>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mash__game);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    @Override
    public void onBackPressed(){

        if(winnerMessage != null){
            System.exit(0);
        }
        super.onBackPressed();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void getName(View view) throws InterruptedException {
        EditText nameEdit = (EditText)findViewById(R.id.user_name);
        if(!isEmpty(nameEdit)){
        userName = nameEdit.getText().toString();

         board.add(new mashNode("house", "Mansion"));
         board.add(new mashNode("house", "Apartment"));
         board.add(new mashNode("house", "Shack"));
         board.add(new mashNode("house", "House"));

        Pick_Husbands spouse = new Pick_Husbands();
        replaceFragment(spouse);}
        else{Toast.makeText(this,"Please enter your name", Toast.LENGTH_SHORT).show();}

    }

    public void submitHusbands(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.person_love);
        EditText nameEdit2 = (EditText)findViewById(R.id.person_like);
        EditText nameEdit3 = (EditText)findViewById(R.id.person_cute);
        EditText nameEdit4 = (EditText)findViewById(R.id.person_gross);
        if(!isEmpty(nameEdit1) && !isEmpty(nameEdit2) && !isEmpty(nameEdit3) && !isEmpty(nameEdit4)){
        board.add(new mashNode("husband",nameEdit1.getText().toString()));
        board.add(new mashNode("husband",nameEdit2.getText().toString()));
        board.add(new mashNode("husband",nameEdit3.getText().toString()));
        board.add(new mashNode("husband",nameEdit4.getText().toString()));

       Choose_cars cars = new Choose_cars();
       replaceFragment(cars);
    }
    else{
        Toast.makeText(this, "Please fill out all boxes!",Toast.LENGTH_SHORT).show();}
    }

    public void submitCars(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.car1);
        EditText nameEdit2 = (EditText)findViewById(R.id.car2);
        EditText nameEdit3 = (EditText)findViewById(R.id.car3);
        EditText nameEdit4 = (EditText)findViewById(R.id.car4);
        if(!isEmpty(nameEdit1) && !isEmpty(nameEdit2) && !isEmpty(nameEdit3) && !isEmpty(nameEdit4)){
            board.add(new mashNode("car",nameEdit1.getText().toString()));
            board.add(new mashNode("car",nameEdit2.getText().toString()));
            board.add(new mashNode("car",nameEdit3.getText().toString()));
            board.add(new mashNode("car",nameEdit4.getText().toString()));

        Number_Kids kids = new Number_Kids();
        replaceFragment(kids);
        }
        else{
            Toast.makeText(this, "Please fill out all boxes!",Toast.LENGTH_SHORT).show();}
    }

    public void numKids(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.num_kids1);
        EditText nameEdit2 = (EditText)findViewById(R.id.num_kids2);
        EditText nameEdit3 = (EditText)findViewById(R.id.num_kids3);
        EditText nameEdit4 = (EditText)findViewById(R.id.num_kids4);
        if(!isEmpty(nameEdit1) && !isEmpty(nameEdit2) && !isEmpty(nameEdit3) && !isEmpty(nameEdit4)){
            board.add(new mashNode("kids",nameEdit1.getText().toString()));
            board.add(new mashNode("kids",nameEdit2.getText().toString()));
            board.add(new mashNode("kids",nameEdit3.getText().toString()));
            board.add(new mashNode("kids",nameEdit4.getText().toString()));

        Choose_A_Number choose = new Choose_A_Number();
        replaceFragment(choose);
        }
        else{
            Toast.makeText(this, "Please fill out all boxes!",Toast.LENGTH_SHORT).show();}


    }

    public void pickNum(View view){

            EditText nameEdit1 = (EditText) findViewById(R.id.num_roll);
        if(!isEmpty(nameEdit1)){
            numpick = Integer.parseInt(nameEdit1.getText().toString());

        Results result = new Results();
        replaceFragment(result);
        }
        else{
            Toast.makeText(this, "Please fill out all boxes!",Toast.LENGTH_SHORT).show();}
    }

    private void replaceFragment(Fragment frag){

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, frag).addToBackStack(null).commit();

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void gameLogic(View view){
        Button resultbtn = (Button)findViewById(R.id.resultButton);
        resultbtn.setText("Share Your Results");

        resultbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    share();
            }
        });

        boolean gameComplete = false;
        int x = numpick, housesFalse = 0, husbandsFalse = 0, carsFalse = 0, kidsFalse = 0;

        while(!gameComplete) {
           while(x > (board.size() - 1)){x -= board.size();}

           if(!board.get(x).isChecked){
               String y = board.get(x).name;
               board.remove(x);
               if(y == "house"){housesFalse++; if(housesFalse == 3){selectWinner("house");}}
               if(y == "husband"){husbandsFalse++;if(husbandsFalse == 3){selectWinner("husband");}}
               if(y == "kids"){kidsFalse++;if(kidsFalse == 3){selectWinner("kids");}}
               if(y == "car"){carsFalse++;if(carsFalse ==3){selectWinner("car");}}

           }
            x += numpick - 1;
            if(numWinners == 4){
                gameComplete = true;
            }
        }

        winnerMessage = (userName + " you will marry " + husbands + ". \n You will live in a "
        + houses + ".\n You will have " + kids1 + " kids,\n and drive a " + car1 + "!");

        TextView result = (TextView)findViewById(R.id.results);
        result.setText(winnerMessage);

    }



    private void selectWinner(String string){
        for (mashNode s : board) {
            if (s.name == string) {
                if (s.name == "husband") {
                    husbands = s.value;
                    numWinners++;
                } else if (s.name == "house") {
                    houses = s.value;
                    numWinners++;
                } else if (s.name == "car") {
                    car1 = s.value;
                    numWinners++;
                } else if (s.name == "kids") {
                    kids1 = Integer.parseInt(s.value);
                    numWinners++;
                } else {
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                }

                board.remove(s);
                return;
            }
        }

    }

    public static class mashNode{

        private String name;
        private String value;
        private boolean isChecked = false;

        public mashNode(String field, String values ){
            name = field;
            value = values;

        }
    }

    public void share(){
        Toast.makeText(this,"Not ready yet! Try back soon.", Toast.LENGTH_LONG).show();
    }

    protected void sendSMSMessage() {

        /*EditText txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        EditText  txtMessage = (EditText) findViewById(R.id.txtMessage);

        String phoneNo = txtPhoneNo.getText().toString();
        String message = txtMessage.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mash__game, container, false);
            return rootView;
        }
    }
}
