package com.example.misanthropic.mash;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static java.lang.Thread.sleep;


public class Mash_Game extends Activity implements Pick_Husbands.OnFragmentInteractionListener ,Choose_A_Number.OnFragmentInteractionListener,
        Choose_cars.OnFragmentInteractionListener, Number_Kids.OnFragmentInteractionListener, Results.OnFragmentInteractionListener{
    private String userName = null;
    private String husband1;
    private String husband2;
    private String husband3;
    private String husband4;
    private String car1;
    private String car2;
    private String car3;
    private String car4;
    private int kids1;
    private int kids2;
    private int kids3;
    private int kids4;
    private int numpick;
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

    public void getName(View view) throws InterruptedException {
        EditText nameEdit = (EditText)findViewById(R.id.user_name);
        userName = nameEdit.getText().toString();
        /*nameEdit.setText("Thanks " + userName);
        nameEdit.setFocusable(false);

        sleep(1500);*/
        Pick_Husbands spouse = new Pick_Husbands();
        replaceFragment(spouse);

    }

    public void submitHusbands(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.car1);
        EditText nameEdit2 = (EditText)findViewById(R.id.person_like);
        EditText nameEdit3 = (EditText)findViewById(R.id.person_cute);
        EditText nameEdit4 = (EditText)findViewById(R.id.person_gross);

        husband1 = nameEdit1.getText().toString();
        husband2 = nameEdit2.getText().toString();
        husband3 = nameEdit3.getText().toString();
        husband4 = nameEdit4.getText().toString();

        Choose_cars cars = new Choose_cars();
        replaceFragment(cars);
    }

    public void submitCars(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.car1);
        EditText nameEdit2 = (EditText)findViewById(R.id.car2);
        EditText nameEdit3 = (EditText)findViewById(R.id.car3);
        EditText nameEdit4 = (EditText)findViewById(R.id.car4);

        car1 = nameEdit1.getText().toString();
        car2 = nameEdit2.getText().toString();
        car3 = nameEdit3.getText().toString();
        car4 = nameEdit4.getText().toString();

        Number_Kids kids = new Number_Kids();
        replaceFragment(kids);
    }

    public void numKids(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.num_kids1);
        EditText nameEdit2 = (EditText)findViewById(R.id.num_kids2);
        EditText nameEdit3 = (EditText)findViewById(R.id.num_kids3);
        EditText nameEdit4 = (EditText)findViewById(R.id.num_kids4);

        kids1 = Integer.parseInt(nameEdit1.getText().toString());
        kids2 = Integer.parseInt(nameEdit1.getText().toString());
        kids3 = Integer.parseInt(nameEdit1.getText().toString());
        kids4 = Integer.parseInt(nameEdit1.getText().toString());

        Choose_A_Number choose = new Choose_A_Number();
        replaceFragment(choose);
    }
    public void pickNum(View view){
        EditText nameEdit1 = (EditText)findViewById(R.id.num_roll);
        numpick = Integer.parseInt(nameEdit1.getText().toString());

        Results result = new Results();
        replaceFragment(result);
    }

    private void replaceFragment(Fragment frag){

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, frag).addToBackStack(null).commit();

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
