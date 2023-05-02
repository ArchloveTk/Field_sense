package com.example.field_sense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.button);
        ExecutorService service = Executors.newSingleThreadExecutor();
        button.setOnClickListener(v -> {

            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                //correct
                Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                openActivity2();
            } else {
                //incorrect
                Toast.makeText(MainActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
public void openActivity2(){
            Intent  intent = new Intent(this, Activity2.class);
            startActivity(intent);
        }

       /* button.setOnClickListener(v -> {
            service.execute(() -> {
                con = connection(connection.ip, connection.port, connection.db, connection.un, connection.pass);
               if (con == null) {
                   runOnUiThread(() -> Toast.makeText(MainActivity.this, "Check Internet connection", Toast.LENGTH_SHORT).show());
                } else {

                    try {
                        String sql = "SELECT * FROM employess WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "' ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            runOnUiThread(() -> Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show());
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Check username and password", Toast.LENGTH_SHORT).show());

                            username.setText("");
                            password.setText("");
                        }
                    } catch (Exception e) {
                        Log.e(" SQL Error : ", e.getMessage());
                    }
                }


            });
            runOnUiThread(() -> {


            });


        });

    }*/

  /* @SuppressLint("StaticFieldLeak")
    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            con = connection(connection.un.toString(), connection.pass.toString(), connection.db.toString(), connection.ip.toString(), connection.port.toString());
            if (con == null) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Check Internet connection", Toast.LENGTH_SHORT).show());
                z = "On Internet Connection ";
            } else {

                try {
                    String sql = "SELECT * FROM employess WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "' ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show());
                        z= "Success";
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Check username and password", Toast.LENGTH_SHORT).show());

                        username.setText("");
                        password.setText("");
                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e(" SQL Error : ", e.getMessage());
                }
            }
                return z;
            }

    }*/

   /* @SuppressLint("NewApi")
    public Connection connection(String ip, String port, String db, String un, String pass) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionURL;
        try {
            Class.forName("com.mysql.jdbc.Driver");  // initialise the driver
            connectionURL = "jdbc:mysql:" + ip + ":" +port+ "/" + db + "," + un + "," + pass+ ";";
            con= DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            Log.e("SQL Connection Error : ", ""+ e.getMessage());
        }  return con;

    }
}*/
    }