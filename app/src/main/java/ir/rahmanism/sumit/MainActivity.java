package ir.rahmanism.sumit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.rahmanism.general.About;
import ir.rahmanism.general.MyGeneral;

public class MainActivity extends ActionBarActivity {

    private SumIt sumIt;
    // 6 random numbers
    private int[] givenNumbers;

    /// Interface controls
    // result text views
    private TextView markTxt;
    private TextView countTxt;
    private TextView correctTxt;
    private TextView wrongTxt;
    // user answer edit texts
    private EditText r1;
    private EditText r2;
    private EditText r3;
    // message text views
    private TextView r1msg;
    private TextView r2msg;
    private TextView r3msg;
    // text views to show random numbers (to sum)
    private TextView n11;
    private TextView n12;
    private TextView n21;
    private TextView n22;
    private TextView n31;
    private TextView n32;
    // buttons
    private Button resultBtn;
    private Button resetBtn;

    private int defaultBackground;


    public MainActivity() {
        sumIt = new SumIt();
        givenNumbers = new int[6];
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetUIControls();

        ShowHighScore();

        Reset();
    }

    /// Shows high score in result panel
    protected void ShowHighScore() {
        TextView view = (TextView) findViewById(R.id.highScoreTxt);
        view.setText("" + GetHighScore());
    }

    /// Gets high score from prefrences
    private int GetHighScore() {
        //getting preferences
        SharedPreferences prefs = this.getSharedPreferences("sumItPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("highScore", 0); //0 is the default value
    }

    /// Stores the high score in preferences
    private void SetHighScore(int score) {
        //setting preferences
        SharedPreferences prefs = this.getSharedPreferences("sumItPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highScore", score);
        editor.commit();
    }

    /// Gets views from layout
    private void GetUIControls() {
        // result text views
        markTxt = (TextView) findViewById(R.id.markTxt);
        countTxt = (TextView) findViewById(R.id.answeredCountTxt);
        correctTxt = (TextView) findViewById(R.id.correctTxt);
        wrongTxt = (TextView) findViewById(R.id.wrongTxt);

        // edit texts with user answers
        r1 = (EditText) findViewById(R.id.r1);
        r2 = (EditText) findViewById(R.id.r2);
        r3 = (EditText) findViewById(R.id.r3);

        // text view for showing message about each result
        r1msg = (TextView) findViewById(R.id.r1msg);
        r2msg = (TextView) findViewById(R.id.r2msg);
        r3msg = (TextView) findViewById(R.id.r3msg);

        // text views to show randomly created numbers
        n11 = (TextView) findViewById(R.id.n11);
        n12 = (TextView) findViewById(R.id.n12);
        n21 = (TextView) findViewById(R.id.n21);
        n22 = (TextView) findViewById(R.id.n22);
        n31 = (TextView) findViewById(R.id.n31);
        n32 = (TextView) findViewById(R.id.n32);

        // buttons
        resultBtn = (Button) findViewById(R.id.resultBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);

        // set button events
        resultBtn.setOnClickListener(resultBtnOnClickListener);
        resetBtn.setOnClickListener(resetBtnOnClickListener);

        defaultBackground = Color.TRANSPARENT;
//                ((ColorDrawable) ((LinearLayout) r1msg.getParent()).getBackground()).getColor();
    }

    private View.OnClickListener resultBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Result", Toast.LENGTH_SHORT).show();
            Results();
        }
    };

    private View.OnClickListener resetBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT).show();
            Reset();
        }
    };

    /// Shows the results in result panel
    private void ShowResults() {
        markTxt.setText(sumIt.mark + " => " + sumIt.markPercent() + "%");
        countTxt.setText("" + sumIt.answeredCount);
        correctTxt.setText("" + sumIt.correct);
        wrongTxt.setText("" + sumIt.wrong);
    }

    /// Resets the given numbers for next round
    private void Reset() {
        // make random numbers under 1000
        for (int i = 0; i < 6; i++)
            givenNumbers[i] = (int) (Math.random() * 1000);

        // show random numbers in form panel
        n11.setText("" + givenNumbers[0]);
        n12.setText("" + givenNumbers[1]);
        n21.setText("" + givenNumbers[2]);
        n22.setText("" + givenNumbers[3]);
        n31.setText("" + givenNumbers[4]);
        n32.setText("" + givenNumbers[5]);

        MakeFormReady();
    }

    /// Makes form ready for new data
    private void MakeFormReady() {
        // free result edit texts
        r1.setText("");
        r2.setText("");
        r3.setText("");

        r1.setEnabled(true);
        r2.setEnabled(true);
        r3.setEnabled(true);

        r1msg.setText("");
        r2msg.setText("");
        r3msg.setText("");

        // remove styles from message text views
        ((LinearLayout) r1msg.getParent()).setBackgroundColor(defaultBackground);
        ((LinearLayout) r2msg.getParent()).setBackgroundColor(defaultBackground);
        ((LinearLayout) r3msg.getParent()).setBackgroundColor(defaultBackground);

        resultBtn.setEnabled(true);

        r1.requestFocus();
    }

    /// Checks the answers and gives appropriate messages
    /// actually the magic (what magic?!) happens here
    private void Results() {
        // 3 answers entered by user
        int[] answers = new int[3];
        // 3 results
        int[] realResults = new int[3];

        // parse user input
        answers[0] = MyGeneral.ParseInt(r1.getText().toString());
        answers[1] = MyGeneral.ParseInt(r2.getText().toString());
        answers[2] = MyGeneral.ParseInt(r3.getText().toString());

        realResults[0] = givenNumbers[0] + givenNumbers[1];
        realResults[1] = givenNumbers[2] + givenNumbers[3];
        realResults[2] = givenNumbers[4] + givenNumbers[5];

        OneResult(r1msg, answers[0], realResults[0]);
        OneResult(r2msg, answers[1], realResults[1]);
        OneResult(r3msg, answers[2], realResults[2]);

        DisableForm();
        CheckAndShowHighScore();
    }

    /// Checks if current mark is greater than high score
    /// changes it and shows the new high score
    private void CheckAndShowHighScore() {
        int currentHighScore = GetHighScore();
        if (sumIt.mark > currentHighScore) {
            SetHighScore(sumIt.mark);
            ShowHighScore();
        }
    }

    /// Disables some views on form (when showing results)
    private void DisableForm() {
        // disable edit texts
        r1.setEnabled(false);
        r2.setEnabled(false);
        r3.setEnabled(false);

        // disable result btn
        resultBtn.setEnabled(false);
    }

    private void OneResult(TextView label, int answer, int realResult) {
        if (answer == -1) {
            label.setTextAppearance(getApplicationContext(), R.style.warning);
            ((LinearLayout) label.getParent()).setBackgroundColor(getResources().getColor(R.color.warning));
            label.setText(getString(R.string.error_whatisit) + " " +
                    getString(R.string.correct_answer_is) + " " + realResult);
        } else {
            boolean success = (answer == realResult);
            if (success) {
                label.setTextAppearance(getApplicationContext(), R.style.correct);
                ((LinearLayout) label.getParent()).setBackgroundColor(getResources().getColor(R.color.correct));
                label.setText(R.string.good_job);
                sumIt.correct++;
            } else {
                label.setTextAppearance(getApplicationContext(), R.style.wrong);
                ((LinearLayout) label.getParent()).setBackgroundColor(getResources().getColor(R.color.wrong));
                label.setText(getString(R.string.wrong_answer) + " " +
                        getString(R.string.correct_answer_is) + " " + realResult);
                sumIt.wrong++;
            }

            sumIt.mark = sumIt.correct - sumIt.wrong;
            sumIt.answeredCount++;
        }

        ShowResults();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.about_menuitem) {
            String appVersion = BuildConfig.VERSION_NAME;
            String msg =
                    getString(R.string.myname) + "\n" +
                            getString(R.string.website) + "\n" +
                            getString(R.string.version) + ": " + appVersion;


//            Toast tst = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
//            tst.setGravity(Gravity.CENTER, tst.getXOffset() / 2, tst.getXOffset() / 2);
//            tst.show();


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

//            About about = new About(getApplicationContext(), msg, getString(R.string.ok));
//            about.Show();
        }

        return super.onOptionsItemSelected(item);
    }


}
